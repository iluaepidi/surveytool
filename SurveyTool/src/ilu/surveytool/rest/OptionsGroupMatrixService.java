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

@Path("/OptionsGroupMatrixService")
public class OptionsGroupMatrixService {
   
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
	
	@DELETE
	@Path("/{param}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String deleteOption(@PathParam("param") String optionsgroupId) {
    	System.out.println("Opción (delete): " + optionsgroupId);
    	
    	String response = "false";
    	
    	OptionHandler optionHandler = new OptionHandler();
    	response = String.valueOf(optionHandler.removeOptionsGroupMatrix(Integer.parseInt(optionsgroupId)));
    	System.out.println("Se ha borrado: " + response);
    	return response;
    }
}
