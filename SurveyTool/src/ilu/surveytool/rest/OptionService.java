package ilu.surveytool.rest;

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
import ilu.surveymanager.handler.OptionHandler;
import ilu.surveymanager.handler.ResourceHandler;

@Path("/OptionService")
public class OptionService {
   
	@GET
	@Path("/{param}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String getOption(@PathParam("param") String resourceId) {
    	System.out.println("Opción: " + resourceId);
    	JSONObject json = null;
    	String response = "false";
    	
    	System.out.print("Resource id: " + resourceId);
    	
    	return response;
    }
	
	@POST
	@Path("/createOptionOther")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String createOptionOther(String req) {
    	System.out.println("Opción (setOptionOther): " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			
			Option option = new Option("", 
					999, 
					Integer.parseInt(json.getString("qid")), 
					Integer.parseInt(json.getString("ogid")),
					0,
					json.getString("otype"),
					json.getString("lang"));			
			option.setOther(Boolean.parseBoolean(json.getString("isOther")));			
			int pageId =Integer.parseInt(json.getString("pid"));
			
			OptionHandler optionHandler = new OptionHandler();
			response = optionHandler.createOptionOther(option); 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }

	@POST
	@Path("/updateTextOtehrOption")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateTextOtehrOption(String req) {
    	System.out.println("Opción (updateTextOption): " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
			json = new JSONObject(req);
			
			Option option = new Option();
			option.setLanguage(json.getString("lang"));
			option.setOid(Integer.parseInt(json.getString("oid")));
			option.setText(json.getString("text"));
					
			System.out.println("Opción: " + option.toString());
			OptionHandler optionHandler = new OptionHandler();
			response = Boolean.toString(optionHandler.updateTextOtherOption(option));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }

	@DELETE
	@Path("/removeOptionOther")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String removeOptionOther(String req) {
    	String response = "false";
    	JSONObject json = null;
    	
    	try {
			json = new JSONObject(req);

	    	OptionHandler optionHandler = new OptionHandler();
	    	response = String.valueOf(optionHandler.removeOption(Integer.parseInt(json.getString("qid")), Integer.parseInt(json.getString("ogid")), Integer.parseInt(json.getString("oid"))));
	    	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return response;
    }
	@DELETE
	@Path("/{qid}/{ogid}/{oid}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String deleteOption(@PathParam("qid") String questionId, @PathParam("ogid") String optionGroupId, @PathParam("oid") String optionId) {
    	System.out.println("Opción: " + optionId + " - optionGroup: " + optionGroupId + " - quetion: " + questionId);
    	
    	String response = "false";
    	
    	OptionHandler optionHandler = new OptionHandler();
    	response = String.valueOf(optionHandler.removeOption(Integer.parseInt(questionId), Integer.parseInt(optionGroupId), Integer.parseInt(optionId)));
    	
    	return response;
    }
}
