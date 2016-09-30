package ilu.surveytool.rest;

import java.io.File;

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
    public String responseProcess(String req, @Context HttpServletRequest request) {
    	System.out.println("SurveyProcess (responseProcess): " + req);
    	JSONObject json = null;
    	JSONObject response = new JSONObject();
    	Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);   	
    	
		SurveyProcessHandler surveyProcessHandler = new SurveyProcessHandler();
    	
    	try {
			json = new JSONObject(req);
			AnonimousUser anonimousUser = (AnonimousUser) request.getSession().getAttribute(Attribute.s_ANONIMOUS_USER);
			if(anonimousUser == null)
			{
				anonimousUser.setIpAddress(request.getRemoteAddr());
				anonimousUser = surveyProcessHandler.existAnonimousUser(anonimousUser);
			}
			
			boolean stored = surveyProcessHandler.anonimousResponseProcess(anonimousUser, json);
			
			if(stored && anonimousUser.getId() == 0)
			{
				anonimousUser = surveyProcessHandler.existAnonimousUser(anonimousUser);
			}
			
			response.put("stored", stored);
			
			if(stored)
			{
				int numPage = json.getJSONObject("page").getInt("numPage");	
				String action = json.getString("action");
				
				if(action.equals("next")) numPage++;
				else numPage--;
				
				anonimousUser.setCurrentPage(numPage);
				surveyProcessHandler.updateAnonimousUserCurrentPage(anonimousUser.getId(), numPage);				
				
				JSONObject survey = surveyProcessHandler.getCurrentPageJson(json.getString("publicId"), 1, anonimousUser, lang.getCurrentLanguage());
				response.put("page", survey);
				
				request.getSession().setAttribute(Attribute.s_ANONIMOUS_USER, anonimousUser);
			}
						
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response.toString();
    }
	
}
