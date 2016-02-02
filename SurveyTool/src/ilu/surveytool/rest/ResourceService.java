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

import ilu.surveytool.data.Option;
import ilu.surveytool.orchestrator.OptionOrch;
import ilu.surveytool.orchestrator.ResourceOrch;

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
	
	@DELETE
	@Path("/{param}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
    public String getDeleteResource(@PathParam("param") String resourceId) {
    	System.out.println("Opción: " + resourceId);
    	JSONObject json = null;
    	String response = "false";
    	
    	ResourceOrch resourceOrch = new ResourceOrch();
    	response = String.valueOf(resourceOrch.removeResource(Integer.parseInt(resourceId)));
    	
    	return response;
    }
}
