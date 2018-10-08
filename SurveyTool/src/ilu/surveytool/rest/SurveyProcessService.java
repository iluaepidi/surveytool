package ilu.surveytool.rest;

//import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveyengine.handler.SurveyProcessHandler;
//import ilu.surveymanager.data.Option;
//import ilu.surveymanager.handler.OptionHandler;
//import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.constants.Attribute;
//import ilu.surveytool.constants.Parameter;
//import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.DataObject.SurveyUser;
import ilu.surveytool.databasemanager.constants.DBConstants;
//import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.language.Language;

@Path("/SurveyProcessService")
public class SurveyProcessService {
   
	@POST
	@Path("/responseProcess")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response responseProcess(String req, @Context HttpServletRequest request, @Context ServletContext context, @CookieParam("surveytool") String ckvalue) {
    	System.out.println("SurveyProcess (responseProcess): " + req);
    	System.out.println("Request (responseProcess): " + request.getRequestURL());
    	JSONObject json = null;
    	JSONObject response = new JSONObject();
    	LoginResponse loginResp = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
    	SurveyUser surveyUser = new SurveyUser();
    	int cookieTimeout = 604800;
    	
    	try {
    		json = new JSONObject(req);
    		boolean isUser = false;
    		if(json.has("isUser")) isUser = json.getBoolean("isUser");    				
        	
        	if(isUser && loginResp == null)
        	{
        		response.put("valid", false);
        		//return response.toString();
        		return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
        	}
        	else
        	{
	        	Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
				SurveyProcessHandler surveyProcessHandler = new SurveyProcessHandler();    	    	
				
				
				boolean isPreview = false;
				if(json.has("isPreview")) isPreview = json.getBoolean("isPreview");
				System.out.println("Preview: " + isPreview);
				
		    	if(lang ==  null)
		    	{
		    		lang = new Language(context.getRealPath("/")); 
		    		lang.loadLanguage(json.getString("lang")); 
		    		request.getSession().setAttribute(Attribute.s_SURVEY_LANGUAGE, lang);
		    	}    	   	
		    	
		    	int currentSurveyId = json.getInt("surveyId");
				surveyUser = (SurveyUser) request.getSession().getAttribute(Attribute.s_SURVEY_USER);
				if(surveyUser == null || surveyUser.getSurveyId() != currentSurveyId)
				{
					if(ckvalue == null || ckvalue.isEmpty())
					{						
						surveyUser = new SurveyUser();
						surveyUser.setIpAddress(request.getRemoteAddr());
						surveyUser.setSurveyId(currentSurveyId);
						surveyUser.setCurrentPage(1);
						
						if(loginResp != null && loginResp.isValid() && loginResp.getRol().equals(DBConstants.s_VALUE_ROLNAME_USER))
						{
							surveyUser = surveyProcessHandler.existSurveyUser(surveyUser, loginResp.getUserId());
						}
						else
						{
							surveyUser.setAnonymousUser(true);
						}
					}
					else
					{
						surveyUser = new SurveyUser(ckvalue);
					}
				}
				
				boolean stored = false;
	//IN METHOD ANONYMOUS (DONE)				
				stored = surveyProcessHandler.surveyResponseProcess(surveyUser, json, isPreview);
				
				/*if(stored && anonimousUser.getId() == 0)
				{
					anonimousUser = surveyProcessHandler.existAnonimousUser(anonimousUser, isPreview);
				}*/
				
				response.put("stored", stored);
				
				if(stored)
				{
					int numPage = json.getJSONObject("page").getInt("numPage");	
					String action = json.getString("action");
	//IF ANONYMOUS (DONE)
					numPage = surveyProcessHandler.getPageNumber(json.getInt("surveyId"), numPage, action, json.getJSONObject("page").getJSONArray("questions"), surveyUser.getId(), lang.getCurrentLanguage(), false, surveyUser.isAnonymousUser());
												
					surveyUser.setCurrentPage(numPage);
	//IF ANONYMOUS (DONE)
					surveyProcessHandler.updateSurveyUserCurrentPage(surveyUser.getId(), numPage, surveyUser.isAnonymousUser());				
	
	//IN METHOD ANONYMOUS (DONE)				
					JSONObject survey = surveyProcessHandler.getCurrentPageJson(json.getString("publicId"), surveyUser, lang.getCurrentLanguage());
					System.out.println(survey);
					
					/*if(survey.getJSONObject("section").has("page"))
					{*/
						//The body content is a question, so this while considers that the final page contains one question (the body content with the thanks message)
						while(survey.getJSONObject("section").has("page") && (survey.getJSONObject("section").getJSONObject("page").has("questions")) && (survey.getJSONObject("section").getJSONObject("page").getJSONArray("questions").length()==0)){
							numPage = survey.getJSONObject("section").getJSONObject("page").getInt("numPage");	
							numPage = surveyProcessHandler.getPageNumber(survey.getInt("surveyId"), numPage, action, survey.getJSONObject("section").getJSONObject("page").getJSONArray("questions"), surveyUser.getId(), lang.getCurrentLanguage(), false, surveyUser.isAnonymousUser());
														
							surveyUser.setCurrentPage(numPage);
	//IN METHOD ANONYMOUS (DONE)
							surveyProcessHandler.updateSurveyUserCurrentPage(surveyUser.getId(), numPage, surveyUser.isAnonymousUser());				
	//IN METHOD ANONYMOUS (DONE)							
							survey = surveyProcessHandler.getCurrentPageJson(survey.getString("publicId"), surveyUser, lang.getCurrentLanguage());
							System.out.println("In while: "+survey);
						}
					//}
					response.put("page", survey);
					response.put("valid", true);
					
					request.getSession().setAttribute(Attribute.s_SURVEY_USER, surveyUser);
					int numPages = json.getInt("numPages");
					if(numPage > numPages || (numPage == numPages && surveyProcessHandler.isOnlyTextPage(survey.getJSONObject("section").getJSONObject("page").getJSONArray("questions"))))
					{
	//IN METHOD ANONYMOUS (DONE)		
						surveyProcessHandler.updateSurveyUserFinished(surveyUser.getId(), true, surveyUser.isAnonymousUser());
						cookieTimeout = 0;
					}
				}
				
        	}			

        	response.put("cookieContent", surveyUser.toCoockie());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//httpResponse.addCookie(ck);
    	Cookie ck = new Cookie("surveytool", surveyUser.toCoockie(), "/", null);
    	
    	return Response.ok(response.toString(), MediaType.APPLICATION_JSON).cookie(new NewCookie(ck, "", cookieTimeout, false)).build();
    }
	
}
