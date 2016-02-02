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

import ilu.surveytool.constants.Parameter;
import ilu.surveytool.data.Option;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.orchestrator.OptionOrch;
import ilu.surveytool.orchestrator.QuestionOrch;
import ilu.surveytool.orchestrator.ResourceOrch;
import ilu.surveytool.orchestrator.SurveysOrch;

@Path("/QuestionService")
public class QuestionService {
   
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
			int questionId = Integer.parseInt(json.getString(Parameter.s_QID));
			Content content = new Content(0, 
					json.getString(Parameter.s_LANGUAGE_LAN), 
					json.getString(Parameter.s_CONTENT_TYPE), 
					json.getString(Parameter.s_TEXT));
			System.out.println("content: " + content.toString());
			
			QuestionOrch questionOrch = new QuestionOrch();
			response = String.valueOf(questionOrch.updateContent(questionId, content));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@PUT
	@Path("/updateMandatory")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateMandatory(String req) {
    	System.out.println("Opción: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int questionId = Integer.parseInt(json.getString(Parameter.s_QID));
			int pageId = Integer.parseInt(json.getString(Parameter.s_PID));
			
			QuestionOrch questionOrch = new QuestionOrch();
			response = String.valueOf(questionOrch.updateMandatory(questionId, pageId));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@DELETE
	@Path("/{qid}/{pid}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String remove(@PathParam("qid") String qid, @PathParam("pid") String pid) {
    	System.out.println("Opción qid: " + qid + " - pid: " + pid);
    	JSONObject json = null;
    	String response = "";
    	//try {
			//json = new JSONObject(req);
			int questionId = Integer.parseInt(qid);
			int pageId = Integer.parseInt(pid);
			
			QuestionOrch questionOrch = new QuestionOrch();
			response = String.valueOf(questionOrch.removeQuestionByPage(questionId, pageId));
			
		/*} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	return response;
    }

}
