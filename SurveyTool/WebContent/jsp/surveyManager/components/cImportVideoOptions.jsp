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
					  					%>
					  					
										<div class="form-group">
								            <label for="resourceTitle"><%= lang.getContent("file.import.label.title") %></label>
								            <input type="text" id="resourceTitle" class="form-control" name="resourceTitle" placeholder="<%= lang.getContent("placeholder.type_here") %>"/>
								        </div>
								        <div class="form-group">									            
								            <label for="resourceDescText"><%= lang.getContent("file.import.label.description") %></label>
								            <textarea rows="2" id="resourceDescText" name="resourceDescText" class="form-control" placeholder="<%= lang.getContent("placeholder.type_here") %>"></textarea>
								        </div>
								        <div class="form-group">									            
								            <label for="resourceUrl"><%= lang.getContent("file.import.label.video_url") %></label>
								            <input type="url" id="resourceUrl" class="form-control" name="resourceUrl" placeholder="<%= lang.getContent("placeholder.type_here") %>"/>
								        </div>
					  					
										<div class="center">
											<button name="upload" id="btnImportFile" class="btn btn-primary"><%= lang.getContent("button.import") %></button> 
										</div>	
										
										<%
					  					lang.close();
										%>									