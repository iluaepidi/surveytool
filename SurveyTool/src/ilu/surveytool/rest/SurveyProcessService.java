package ilu.surveytool.rest;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveyengine.handler.SurveyProcessHandler;
import ilu.surveymanager.data.Option;
import ilu.surveymanager.handler.OptionHandler;
import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.DataObject.AnonimousUser;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.language.Language;

@Path("/SurveyProcessService")
public class SurveyProcessService {
   
	@POST
	@Path("/responseProcess")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public String responseProcess(String req, @Context HttpServletRequest request, @Context ServletContext context) {
    	System.out.println("SurveyProcess (responseProcess): " + req);
    	System.out.println("Request (responseProcess): " + request.getRequestURL());
    	JSONObject json = null;
    	JSONObject response = new JSONObject();
    	
    	try {
        	Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
			SurveyProcessHandler surveyProcessHandler = new SurveyProcessHandler();    	    	
			json = new JSONObject(req);
			
			boolean isPreview = false;
			if(json.has("isPreview")) isPreview = json.getBoolean("isPreview");
			System.out.println("Preview: " + isPreview);
			
	    	if(lang ==  null)
	    	{
	    		lang = new Language(context.getRealPath("/")); 
	    		lang.loadLanguage(json.getString("lang")); 
	    		request.getSession().setAttribute(Attribute.s_SURVEY_LANGUAGE, lang);
	    	}    	   	
	    	
			AnonimousUser anonimousUser = (AnonimousUser) request.getSession().getAttribute(Attribute.s_ANONIMOUS_USER);
			if(anonimousUser == null)
			{
				anonimousUser = new AnonimousUser();
				anonimousUser.setIpAddress(request.getRemoteAddr());
				anonimousUser.setSurveyId(json.getInt("surveyId"));
				anonimousUser = surveyProcessHandler.existAnonimousUser(anonimousUser, isPreview);
			}
			
			boolean stored = surveyProcessHandler.anonimousResponseProcess(anonimousUser, json, isPreview);
			
			if(stored && anonimousUser.getId() == 0)
			{
				anonimousUser = surveyProcessHandler.existAnonimousUser(anonimousUser, isPreview);
			}
			
			response.put("stored", stored);
			
			if(stored)
			{
				int numPage = json.getJSONObject("page").getInt("numPage");	
				String action = json.getString("action");
				
				numPage = surveyProcessHandler.getPageNumber(json.getInt("surveyId"), numPage, action, json.getJSONObject("page").getJSONArray("questions"), anonimousUser.getId(), lang.getCurrentLanguage(), false);
											
				anonimousUser.setCurrentPage(numPage);
				surveyProcessHandler.updateAnonimousUserCurrentPage(anonimousUser.getId(), numPage);				
				
				JSONObject survey = surveyProcessHandler.getCurrentPageJson(json.getString("publicId"), anonimousUser, lang.getCurrentLanguage());
				System.out.println(survey);
				
				if(survey.has("page"))
				{
					//The body content is a question, so this while considers that the final page contains one question (the body content with the thanks message)
					while((survey.getJSONObject("section").getJSONObject("page").has("questions")) && (survey.getJSONObject("section").getJSONObject("page").getJSONArray("questions").length()==0)){
						numPage = survey.getJSONObject("section").getJSONObject("page").getInt("numPage");	
						numPage = surveyProcessHandler.getPageNumber(survey.getInt("surveyId"), numPage, action, survey.getJSONObject("section").getJSONObject("page").getJSONArray("questions"), anonimousUser.getId(), lang.getCurrentLanguage(), false);
													
						anonimousUser.setCurrentPage(numPage);
						surveyProcessHandler.updateAnonimousUserCurrentPage(anonimousUser.getId(), numPage);				
						
						survey = surveyProcessHandler.getCurrentPageJson(survey.getString("publicId"), anonimousUser, lang.getCurrentLanguage());
						System.out.println("In while: "+survey);
					}
				}
				response.put("page", survey);
				
				request.getSession().setAttribute(Attribute.s_ANONIMOUS_USER, anonimousUser);
				int numPages = json.getInt("numPages");
				if(numPage > numPages || (numPage == numPages && surveyProcessHandler.isOnlyTextPage(survey.getJSONObject("section").getJSONObject("page").getJSONArray("questions"))))
				{
					surveyProcessHandler.updateAnonimousUserFinished(anonimousUser.getId(), true);
				}
			}
						
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response.toString();
    }
	
}
