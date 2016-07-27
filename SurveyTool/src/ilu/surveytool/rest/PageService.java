package ilu.surveytool.rest;

import java.util.HashMap;

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
import ilu.surveymanager.handler.PageHandler;
import ilu.surveymanager.handler.QuestionHandler;
import ilu.surveymanager.handler.ResourceHandler;
import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.constants.DBConstants;

@Path("/PageService")
public class PageService {
   
	/*@POST
	@Path("/createPage")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateContent(String req) {
    	System.out.println("Page: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int sectionId = json.getInt(Parameter.s_SCID);
			int surveyId = json.getInt(Parameter.s_SID);
			int currentNumPage = json.getInt(Parameter.s_NUM_PAGE);

			PageHandler pageHandler = new PageHandler();
			pageHandler.createPage(currentNumPage, sectionId, surveyId);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }*/

	@DELETE
	@Path("/{pid}/{sid}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String remove(@PathParam("pid") String pid, @PathParam("sid") String sid) {
    	System.out.println("Remove pageId: " + pid + " - sid: " + sid);
    	JSONObject json = null;
    	String response = "";
	
		int pageId = Integer.parseInt(pid);
		int surveyId = Integer.parseInt(sid);
		
		PageHandler pageHandler = new PageHandler();
		response = Boolean.toString(pageHandler.removePage(pageId, surveyId));
		
    	return response;
    }

}
