package ilu.surveytool.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveymanager.data.Option;
import ilu.surveymanager.data.QDependence;
import ilu.surveymanager.data.QDependenceValue;
import ilu.surveymanager.data.OptionsGroupSurveyManager;
import ilu.surveymanager.handler.LogicGoToHandler;
import ilu.surveymanager.handler.OptionHandler;
import ilu.surveymanager.handler.QDependenceHandler;
import ilu.surveymanager.handler.QuotaHandler;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.constants.DBFieldNames;

@Path("/QCService")
public class QuestionCreatorService {
    
    /*@GET
    @Path("/insertOption/{param}")
    @Produces(MediaType.TEXT_HTML)
    public String getSaludoHTML(@PathParam("param") String nombre) {
        return "<html> " + "<title>" + "Hola Mundo" + "</title>"  
             + "<body><h1>" + "Hola Mundo en HTML : " + nombre 
             + "</body></h1>" + "</html> ";
    }*/
    
    /*@GET
	@Path("/insertOption")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getSaludoPlain() {
    	System.out.println("Opción: ");
    	JSONObject json = new JSONObject();
    	try {
			json.put("ogid", "Hello world!");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //return "{\"ogid\": \"Hello World!\"}";
    	String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + json;
    	System.out.println("Result: " + result);
		return Response.status(200).entity(result).build();
    }*/
	
	@POST
	@Path("/insertOption")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String insertOption(String req) {
    	System.out.println("Opción (insertOption): " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			Option option = new Option(json.getString("text"), 
					Integer.parseInt(json.getString("index")), 
					Integer.parseInt(json.getString("qid")), 
					Integer.parseInt(json.getString("ogid")),
					Integer.parseInt(json.getString("oid")),
					json.getString("otype"),
					json.getString("lang"));
			System.out.println("Opción: " + option.toString());
			OptionHandler optionHandler = new OptionHandler();
			response = optionHandler.saveOption(option);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@POST
	@Path("/insertOptionMatrix")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String insertOptionMatrix(String req) {
    	System.out.println("Opción (insertOptionMatrix): " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			Option option = new Option(json.getString("text"), 
					Integer.parseInt(json.getString("index")), 
					Integer.parseInt(json.getString("qid")), 
					0,
					Integer.parseInt(json.getString("oid")),
					json.getString("otype"),
					json.getString("lang"));
			System.out.println("Opción: " + option.toString());
			OptionHandler optionHandler = new OptionHandler();
			response = optionHandler.saveOptionMatrix(option);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@POST
	@Path("/insertOptionsGroupMatrix")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String insertOptionsGroupMatrix(String req) {
    	System.out.println("Opción (insertOptionsGroupMatrix): " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			OptionsGroupSurveyManager option = new OptionsGroupSurveyManager(json.getString("text"), 
					Integer.parseInt(json.getString("index")), 
					Integer.parseInt(json.getString("qid")), 
					Integer.parseInt(json.getString("ogid")),
					json.getString("otype"),
					json.getString("lang"));
			System.out.println("Opción(insertOptionsGroupMatrix-option): " + option.toString());
			OptionHandler optionHandler = new OptionHandler();
			response = optionHandler.saveOptionsGroupMatrix(option);
			System.out.println("Opción (insertOptionsGroupMatrix-response): " + response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@POST
	@Path("/insertDependence")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String insertDependence(String req) {
    	System.out.println("Opción (insertDependence): " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			
			List<QDependenceValue> qDependenceValue = new ArrayList<QDependenceValue>();
			qDependenceValue.add(new QDependenceValue(Integer.parseInt(json.getString("itemindex")),
					Integer.parseInt(json.getString("qid")),
					Integer.parseInt(json.getString("ogid")),
					Integer.parseInt(json.getString("oid"))));
			QDependence qDependence;
			if(json.has("deptype")){
			qDependence = new QDependence(Integer.parseInt(json.getString("index")),
					Integer.parseInt(json.getString("questionid")),
   					1,
   					json.getString("deptype"),
   					qDependenceValue);
			}else{
				qDependence = new QDependence(Integer.parseInt(json.getString("index")),
						Integer.parseInt(json.getString("questionid")),
	   					1,
	   					"",
	   					qDependenceValue);
			}
			QDependenceHandler qDependenceHandler = new QDependenceHandler();
			response = qDependenceHandler.saveQDependence(qDependence);
			
			System.out.println("Opción (insertDependence-response): " + response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@POST
	@Path("/updateDependenceType")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateDependenceType(String req) {
    	System.out.println("Opción (updateDependenceType): " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			
			QDependenceHandler qDependenceHandler = new QDependenceHandler();
			qDependenceHandler.saveQDependenceType(Integer.parseInt(json.getString("index")), json.getString("deptype"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@POST
	@Path("/removeDependence")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String removeDependence(String req) {
    	System.out.println("Opción (removeDependence): " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			QDependenceHandler qDependenceHandler = new QDependenceHandler();
			qDependenceHandler.removeQDependence(Integer.parseInt(json.getString("index")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@POST
	@Path("/removeDependenceValue")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String removeDependenceValue(String req) {
    	System.out.println("Opción (removeDependence): " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			QDependenceHandler qDependenceHandler = new QDependenceHandler();
			qDependenceHandler.removeQDependenceValue(Integer.parseInt(json.getString("itemindex")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@DELETE
	@Path("/{qid}/{param}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String deleteDependenceValue(@PathParam("qid") int qindex,@PathParam("param") int itemindex) {
		String response = "";
		System.out.println("Opción (removeDependence by param): " + itemindex);
    	QDependenceHandler qDependenceHandler = new QDependenceHandler();
    	response = String.valueOf(qDependenceHandler.removeQDependenceValue(itemindex));
		return response;
    }
	
	@DELETE
	@Path("/All/{qid}/{param}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String deleteDependences(@PathParam("qid") int qindex,@PathParam("param") int index) {
		String response = "";
		System.out.println("Opción (removeAllDependence by param): " + index);
    	QDependenceHandler qDependenceHandler = new QDependenceHandler();
    	response = String.valueOf(qDependenceHandler.removeQDependence(index));
		return response;
    }
	
	@POST
	@Path("/removeAllDependences")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String removeAllDependences(String req) {
    	System.out.println("Opción (removeAllDependences): " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			QDependenceHandler qDependenceHandler = new QDependenceHandler();
			qDependenceHandler.removeAllQDependences(Integer.parseInt(json.getString("questionId")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@POST
	@Path("/insertLogic")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String insertLogic(String req) {
    	System.out.println("insertLogic: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			LogicGoToHandler logicGoToHandler = new LogicGoToHandler();
			if (json.has("questionid")){
				System.out.println("Exists questionId");
				logicGoToHandler.insertLogic(Integer.parseInt(json.getString("qid")),Integer.parseInt(json.getString("ogid")),Integer.parseInt(json.getString("oid")), Integer.parseInt(json.getString("questionid")));
			}else{
				System.out.println("Not exist questionId");
				logicGoToHandler.removeLogicGoTo(Integer.parseInt(json.getString("qid")),Integer.parseInt(json.getString("ogid")),Integer.parseInt(json.getString("oid")));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@DELETE
	@Path("/AllLogic/{qid}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String deleteAllLogicGoTo(@PathParam("qid") int qid) {
		String response = "";
		System.out.println("deleteAllLogic");
		LogicGoToHandler logicGoToHandler = new LogicGoToHandler();
    	response = String.valueOf(logicGoToHandler.removeAllLogicGoTo(qid));
		return response;
    }

}
