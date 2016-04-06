package ilu.surveytool.rest;

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
import ilu.surveymanager.handler.OptionHandler;

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
    	System.out.println("Opción: " + req);
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
}
