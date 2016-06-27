package ilu.surveytool.rest;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import ilu.surveymanager.handler.PollHandler;

@Path("/PollService")
public class PollService {
 
	@GET
	@Path("/export/{param}")
	@Produces("application/vnd.ms-excel")
	public Response getOption(@PathParam("param") String pollId) {
	   	System.out.println("Opción: " + pollId);
	   	
	   	PollHandler pollHandler = new PollHandler();
	   	File file = pollHandler.exportResults(Integer.parseInt(pollId));
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=" + file.getName());
		
		//file.delete();
		
		return response.build();
	}
}
