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
import ilu.surveymanager.handler.QuestionHandler;
import ilu.surveymanager.handler.QuotaHandler;
import ilu.surveymanager.handler.ResourceHandler;
import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.constants.DBConstants;

@Path("/QuotaService")
public class QuotaService {
   
	
	
	@DELETE
	@Path("/{sid}/{qid}/{r}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String removeQuota(@PathParam("sid") String sid, @PathParam("qid") String qid,@PathParam("r") String r) {
    	System.out.println("Opción sid: " + sid + " - qid: " + qid);
    	JSONObject json = null;
    	String response = "";
    	//try {
			//json = new JSONObject(req);
			int questionId = Integer.parseInt(qid);
			int surveyId = Integer.parseInt(sid);
			
			QuotaHandler quotaHandler = new QuotaHandler();
			response = String.valueOf(quotaHandler.deteleQuotaQuestion(surveyId, questionId));
			
		/*} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	return response;
    }

}
