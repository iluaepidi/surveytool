package ilu.surveytool.rest;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
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
import ilu.surveymanager.data.OptionsGroupSurveyManager;
import ilu.surveymanager.handler.OptionHandler;
import ilu.surveymanager.handler.QuotaHandler;
import ilu.surveytool.databasemanager.DataObject.Content;

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
	@Path("/insertQuota")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String insertQuota(String req) {
    	System.out.println("Opción (insertQuota): " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			Option option = new Option(json.getString("text"), 
					Integer.parseInt(json.getString("index")), 
					Integer.parseInt(json.getString("qid")), 
					Integer.parseInt(json.getString("ogid")),
					Integer.parseInt(json.getString("oid")),
					null,
					null);
			int max=0,min=0;
			int idSurvey = Integer.parseInt(json.getString("sid"));
			System.out.println("Opción: " + option.toString());
			QuotaHandler quotaHandler = new QuotaHandler();
			response = quotaHandler.saveQuotaOption(idSurvey, option,max,min);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
}
