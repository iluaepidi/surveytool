package ilu.surveytool.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveymanager.data.Option;
import ilu.surveymanager.handler.OptionHandler;
import ilu.surveymanager.handler.QuestionHandler;
import ilu.surveymanager.handler.ResourceHandler;
import ilu.surveymanager.handler.SectionHandler;
import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Content;

@Path("/SectionService")
public class SectionService {
   
	@PUT
	@Path("/updateContent")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateContent(String req) {
    	System.out.println("Opción: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int sectionId = Integer.parseInt(json.getString(Parameter.s_SCID));
			Content content = new Content(0, 
					json.getString(Parameter.s_LANGUAGE_LAN), 
					json.getString(Parameter.s_CONTENT_TYPE), 
					json.getString(Parameter.s_TEXT));
			System.out.println("content: " + content.toString());
			
			SectionHandler sectionHandler = new SectionHandler();
			response = String.valueOf(sectionHandler.updateContent(sectionId, content));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	/*@PUT
	@Path("/updateIndex")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateIndex(String req) {
    	System.out.println("Opción: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int questionId = Integer.parseInt(json.getString(Parameter.s_QID));
			int pageId = Integer.parseInt(json.getString(Parameter.s_PID));
			int prevQuestionId = Integer.parseInt(json.getString(Parameter.s_PREV_ID));
			
			QuestionHandler questionHandler = new QuestionHandler();
			response = String.valueOf(questionHandler.updateIndex(questionId, prevQuestionId, pageId));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }*/
	
	@DELETE
	@Path("/{scid}/{sid}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String remove(@PathParam("scid") String scid, @PathParam("sid") String sid) {
    	System.out.println("Opción scid: " + scid + " - sid: " + sid);
    	String response = "";
    	
		int sectionId = Integer.parseInt(scid);
		int surveyId = Integer.parseInt(sid);
		
		SectionHandler sectionHandler = new SectionHandler();
		response = String.valueOf(sectionHandler.removeSection(sectionId, surveyId));
		
    	return response;
    }

}
