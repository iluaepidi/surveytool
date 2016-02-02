<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    								
							  					
					  					<%
					  					Resource resource = (Resource) request.getAttribute(Attribute.s_RESOURCE);
					  					String action = (String) request.getAttribute(Attribute.s_ACTION);
					  					
					  					if(action.equals("file"))
					  					{
					  					%>
					  													        
					  					<input type="hidden" name="rid" id="rid" value="<%= resource.getResourceId() %>" />
					  					
								        <div class="form-group">
								        	<label for="uploadedFile">Image selected</label>
								            <div class="previewFileUpliaded" id="previewFileUploaded">
								            	<img src="<%= resource.getPathFile() %>" alt="Preview image. Add alternative text in the next field" />
								            </div>
								        </div>
										<div class="form-group">
								            <label for="resourceTitle">Title</label>
								            <input type="text" id="resourceTitle" class="form-control" name="resourceTitle" placeholder="Type here_"/>
								        </div>
								        <div class="form-group">									            
								            <label for="resourceAltText">Texto alternativo</label>
								            <textarea rows="2" id="resourceAltText" name="resourceAltText" class="form-control" placeholder="Type here_"></textarea>
								        </div>
					  					
										<div class="center">
											<button name="upload" id="btnImportFile" class="btn btn-primary" aria-label="Import">Import</button> 
										</div>	
										
										<%
					  					}
					  					else
					  					{
										%>
											<div class="previewFileUpliaded" id="previewFileUploaded">
								            	<img src="<%= resource.getPathFile() %>" alt="Preview image. Add alternative text in the next field" />
								            </div>
										<%
					  					}
										%>									