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
import ilu.surveymanager.handler.ResourceHandler;
import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.constants.DBConstants;

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
			
			QuestionHandler questionHandler = new QuestionHandler();
			response = String.valueOf(questionHandler.updateContent(questionId, content));
			
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
			
			QuestionHandler questionHandler = new QuestionHandler();
			response = String.valueOf(questionHandler.updateMandatory(questionId, pageId));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@PUT
	@Path("/updateOptionalAnswer")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateOptionalAnswer(String req) {
    	System.out.println("Opción: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int questionId = Integer.parseInt(json.getString(Parameter.s_QID));
			int pageId = Integer.parseInt(json.getString(Parameter.s_PID));
			
			QuestionHandler questionHandler = new QuestionHandler();
			response = String.valueOf(questionHandler.updateOptionalAnswer(questionId, pageId));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@PUT
	@Path("/updateTextLength")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateTextLength(String req) {
    	System.out.println("Opción: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int questionId = Integer.parseInt(json.getString(Parameter.s_QID));
			int pageId = Integer.parseInt(json.getString(Parameter.s_PID));
			QuestionHandler questionHandler = new QuestionHandler();
			HashMap<String,String> parameters = new HashMap<String,String>();
			parameters.put(DBConstants.s_VALUE_QUESTIONPARAMETER_TEXTLENGTH,json.getString(Parameter.s_TEXT));
			questionHandler.updateParameters(questionId, pageId, parameters);
			response=json.getString(Parameter.s_TEXT);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@PUT
	@Path("/updateDecimals")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateDecimals(String req) {
    	System.out.println("Opción: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int questionId = Integer.parseInt(json.getString(Parameter.s_QID));
			int pageId = Integer.parseInt(json.getString(Parameter.s_PID));
			QuestionHandler questionHandler = new QuestionHandler();
			HashMap<String,String> parameters = new HashMap<String,String>();
			parameters.put(DBConstants.s_VALUE_QUESTIONPARAMETER_DECIMALS,json.getString(Parameter.s_TEXT));
			questionHandler.updateParameters(questionId, pageId, parameters);
			response=json.getString(Parameter.s_TEXT);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@PUT
	@Path("/updateTextLines")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateTextLines(String req) {
    	System.out.println("Opción: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int questionId = Integer.parseInt(json.getString(Parameter.s_QID));
			int pageId = Integer.parseInt(json.getString(Parameter.s_PID));
			QuestionHandler questionHandler = new QuestionHandler();
			HashMap<String,String> parameters = new HashMap<String,String>();
			parameters.put(DBConstants.s_VALUE_QUESTIONPARAMETER_TEXTLINES,json.getString(Parameter.s_TEXT));
			
			questionHandler.updateParameters(questionId, pageId, parameters);
			response=json.getString(Parameter.s_TEXT);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@PUT
	@Path("/updateInputTypeMode")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateInputType(String req) {
    	System.out.println("Opción: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int questionId = Integer.parseInt(json.getString(Parameter.s_QID));
			int pageId = Integer.parseInt(json.getString(Parameter.s_PID));
			QuestionHandler questionHandler = new QuestionHandler();
			
			questionHandler.removeParametersByQuestionByPage(questionId, pageId);
			
			HashMap<String,String> parameters = new HashMap<String,String>();			
			parameters.put(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_INPUT_MODE,json.getString(Parameter.s_INPUTMODE));
			parameters.put(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE,json.getString(Parameter.s_TEXT));			
			questionHandler.updateParameters(questionId, pageId, parameters);
			response=json.getString(Parameter.s_TEXT);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@PUT
	@Path("/updateMinValue")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateMinValue(String req) {
    	System.out.println("Opción (updateMinValue): " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int questionId = Integer.parseInt(json.getString(Parameter.s_QID));
			int pageId = Integer.parseInt(json.getString(Parameter.s_PID));
			HashMap<String,String> parameters = new HashMap<String,String>();
			parameters.put(DBConstants.s_VALUE_QUESTIONPARAMETER_MINVALUE,json.getString(Parameter.s_TEXT));
			QuestionHandler questionHandler = new QuestionHandler();
			questionHandler.updateParameters(questionId, pageId, parameters);
			response=json.getString(Parameter.s_TEXT);			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@PUT
	@Path("/updateMaxValue")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateMaxValue(String req) {
    	System.out.println("Opción: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int questionId = Integer.parseInt(json.getString(Parameter.s_QID));
			int pageId = Integer.parseInt(json.getString(Parameter.s_PID));
			HashMap<String,String> parameters = new HashMap<String,String>();
			parameters.put(DBConstants.s_VALUE_QUESTIONPARAMETER_MAXVALUE,json.getString(Parameter.s_TEXT));
			QuestionHandler questionHandler = new QuestionHandler();
			questionHandler.updateParameters(questionId, pageId, parameters);
			response=json.getString(Parameter.s_TEXT);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@PUT
	@Path("/updateParameters")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateParameters(String req) {
    	System.out.println("Opción: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			int questionId = Integer.parseInt(json.getString(Parameter.s_QID));
			int pageId = Integer.parseInt(json.getString(Parameter.s_PID));
			HashMap<String,String> parameters = new HashMap<String,String>();
			parameters.put(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE,json.getString(Parameter.s_TEXT));
			QuestionHandler questionHandler = new QuestionHandler();
			questionHandler.updateParameters(questionId, pageId, parameters);
			questionHandler.updateOptionsGroupType(questionId, json.getString(Parameter.s_TEXT));
			response=json.getString(Parameter.s_TEXT);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }

	@PUT
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
			
			QuestionHandler questionHandler = new QuestionHandler();
			response = String.valueOf(questionHandler.removeQuestionByPage(questionId, pageId));
			
		/*} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	return response;
    }

}
