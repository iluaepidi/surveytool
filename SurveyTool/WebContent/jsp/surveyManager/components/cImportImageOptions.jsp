<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    								
							  					
					  					<%					  					
					  					Language lang = new Language(getServletContext().getRealPath("/")); 
					  					lang.loadLanguage(Language.getLanguageRequest(request));
					  					
					  					Resource resource = (Resource) request.getAttribute(Attribute.s_RESOURCE);
					  					String action = (String) request.getAttribute(Attribute.s_ACTION);
					  					
					  					System.out.println("En cImportImageOptions");
					  					
					  					if(action.equals("file"))
					  					{
					  					%>
					  													        
					  					<input type="hidden" name="rid" id="rid" value="<%= resource.getResourceId() %>" />
					  					
								        <div class="form-group">
								        	<label><%= lang.getContent("file.import.label.img_selected") %></label>
								            <div class="previewFileUpliaded" id="previewFileUploaded">
								            	<img src="<%= resource.getPathFile() %>" alt="<%= lang.getContent("file.import.alt.preview") %>" />
								            </div>
								        </div>
										<div class="form-group">
								            <label for="resourceTitle"><%= lang.getContent("file.import.label.title") %></label>
								            <input type="text" id="resourceTitle" class="form-control" name="resourceTitle" placeholder="<%= lang.getContent("placeholder.type_here") %>"/>
								        </div>
								        <div class="form-group">									            
								            <label for="resourceAltText"><%= lang.getContent("file.import.label.alt") %></label>
								            <textarea rows="2" id="resourceAltText" name="resourceAltText" class="form-control" placeholder="<%= lang.getContent("placeholder.type_here") %>"></textarea>
								        </div>
					  					
										<div class="center">
											<button name="upload" id="btnImportFile" class="btn btn-primary"><%= lang.getContent("button.import") %></button> 
										</div>	
										
										<%
					  					}
					  					else
					  					{
										%>
											<div class="previewFileUpliaded" id="previewFileUploaded">
								            	<img src="<%= resource.getPathFile() %>" alt="<%= lang.getContent("file.import.alt.preview") %>" />
								            </div>
										<%
					  					}
					  					
					  					lang.close();
										%>									