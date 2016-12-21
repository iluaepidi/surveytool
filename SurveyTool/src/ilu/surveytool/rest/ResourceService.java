package ilu.surveytool.rest;

import java.util.ArrayList;
import java.util.List;

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

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveymanager.data.Option;
import ilu.surveymanager.handler.OptionHandler;
import ilu.surveymanager.handler.QuestionHandler;
import ilu.surveymanager.handler.ResourceHandler;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Content;

@Path("/ResourceService")
public class ResourceService {
   
	@GET
	@Path("/{param}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String getResource(@PathParam("param") String resourceId) {
    	System.out.println("Opción: " + resourceId);
    	JSONObject json = null;
    	String response = "false";
    	
    	System.out.print("Resource id: " + resourceId);
    	
    	return response;
    }
	
	@PUT
	@Path("/updateContent")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String updateContent(String req) {
    	System.out.println("Resource info: " + req);
    	JSONObject json = null;
    	String response = "";
    	try {
    		List<Content> contents = new ArrayList<Content>();
    		ResourceHandler resourceHandler = new ResourceHandler();
			json = new JSONObject(req);
			int resourceId = Integer.parseInt(json.getString(Parameter.s_RID));
			JSONArray contentsObj = json.getJSONArray(Parameter.s_CONTENTS);
			for(int i = 0; i < contentsObj.length(); i++)
			{
				JSONObject contentObj = contentsObj.getJSONObject(i);
				Content content = new Content(0, 
					contentObj.getString(Parameter.s_LANGUAGE_LAN), 
					contentObj.getString(Parameter.s_CONTENT_TYPE), 
					contentObj.getString(Parameter.s_TEXT));
				System.out.println("content: " + content.toString());	
				contents.add(content);
			}
			
			response = String.valueOf(resourceHandler.updateContent(resourceId, contents));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
	
	@DELETE
	@Path("/{param}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String getDeleteResource(@PathParam("param") String resourceId) {
    	System.out.println("Opción: " + resourceId);
    	JSONObject json = null;
    	String response = "false";
    	
    	ResourceHandler resourceHandler = new ResourceHandler();
    	response = String.valueOf(resourceHandler.removeResource(Integer.parseInt(resourceId)));
    	
    	return response;
    }
}
