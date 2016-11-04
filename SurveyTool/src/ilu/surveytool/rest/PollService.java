package ilu.surveytool.rest;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveymanager.handler.PollHandler;
import ilu.surveymanager.handler.QuestionHandler;
import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Content;

@Path("/PollService")
public class PollService {
 
	@GET
	@Path("/export/{param}")
	@Produces("application/vnd.ms-excel")
	public Response getOption(@PathParam("param") String pollId) {
	   	System.out.println("Opción: " + pollId);
	   	
	   	PollHandler pollHandler = new PollHandler();
	   	File file = pollHandler.exportResults(Integer.parseInt(pollId));
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=" + file.getName());
		
		//file.delete();
		
		return response.build();
	}
	
	@PUT
	@Path("/updateContent")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateContent(String req) {
    	System.out.println("Poll: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int pollId = Integer.parseInt(json.getString(Parameter.s_POID));
			Content content = new Content(0, 
					json.getString(Parameter.s_LANGUAGE_LAN), 
					json.getString(Parameter.s_CONTENT_TYPE), 
					json.getString(Parameter.s_TEXT));
			System.out.println("content: " + content.toString());
			
			PollHandler pollHandler = new PollHandler();
			response = String.valueOf(pollHandler.updateContent(pollId, content));
			
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
			int pollId = Integer.parseInt(json.getString(Parameter.s_POID));
			String projectName = json.getString(Parameter.s_PROJECT);
			
			PollHandler pollHandler = new PollHandler();
			pollHandler.updateProject(pollId, projectName);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }

	@PUT
	@Path("/updateCallUrl")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateCallUrlProject(String req) {
    	System.out.println("Opción: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int pollId = Integer.parseInt(json.getString(Parameter.s_POID));
			String callUrl = json.getString(Parameter.s_LINK_URL);
			
			PollHandler pollHandler = new PollHandler();
			pollHandler.updateCallUrl(pollId, callUrl);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
		
}
