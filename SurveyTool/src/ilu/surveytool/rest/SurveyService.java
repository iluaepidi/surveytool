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

import ilu.surveymanager.data.Option;
import ilu.surveymanager.handler.OptionHandler;
import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.language.Language;

@Path("/SurveyService")
public class SurveyService {
   
	@PUT
	@Path("/updateContent")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateSurveyContent(String req) {
    	System.out.println("Opción: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int surveyId = Integer.parseInt(json.getString(Parameter.s_SID));
			Content content = new Content(0, 
					json.getString(Parameter.s_LANGUAGE_LAN), 
					json.getString(Parameter.s_CONTENT_TYPE), 
					json.getString(Parameter.s_TEXT));
			System.out.println("content: " + content.toString());
			
			SurveysHandler surveysHandler = new SurveysHandler();
			response = String.valueOf(surveysHandler.updateContent(surveyId, content));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@PUT
	@Path("/updateProject")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateSurveyProject(String req) {
    	System.out.println("Opción: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int surveyId = Integer.parseInt(json.getString(Parameter.s_SID));
			String projectName = json.getString(Parameter.s_PROJECT);
			
			SurveysHandler surveysHandler = new SurveysHandler();
			surveysHandler.updateProject(surveyId, projectName);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
		
	@GET
	@Path("/export/{param}")
	@Produces("application/vnd.ms-excel")
	public Response getOption(@PathParam("param") String surveyId, @Context HttpServletRequest request) {
	   	System.out.println("Opción: " + surveyId);
	   	
	   	LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
	   	String userLang = userSessionInfo.getIsoLanguage();
	   	System.out.println("Language: " + userLang);
	   	
	   	SurveysHandler surveysHandler = new SurveysHandler();
	   	File file = surveysHandler.exportResults(Integer.parseInt(surveyId), userLang);
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=" + file.getName());
		
		//file.delete();
		
		return response.build();
	}
}
