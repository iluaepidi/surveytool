<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Content"%>
<%@page import="java.util.HashMap"%>
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
							  					HashMap<String, Content> contents = resource.getContents();
							  					String title = "";

							  					if(!contents.isEmpty() && contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE) != null)
							  					{
							  						title = contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
							  					}
							  					String path = resource.getPathFile();
							  					
							  					if(resource.getType().equals("image"))
							  					{
								  					String altText = "";
								  					if(!contents.isEmpty() && contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT) != null)
								  					{
								  						altText = contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT).getText();
								  					}
							  					
							  					%>
							  							<li class="multimedia-item" rid="<%= resource.getResourceId() %>">
							  								<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
							  								<button id="removeMultimediaFile" class="btn btn-transparent fright red" aria-label="<%= lang.getContent("button.remove_file") %>: <%= title %>"><i class="fa fa-trash"></i></button>
							  								<%} %>
							  								<i class="fa fa-file-image-o" aria-hidden="true"></i>
							  								<a class="active" active="false" id="editFile" data-image='{"rId":"<%= resource.getResourceId()%>","tittle":"<%=title %>","altText":"<%=altText %>","path":"<%=path %>", "rType":"<%= resource.getType() %>"}' href="#"><%= title + " - " + path %></a>
							  								
							  							</li>
												<%
							  					}
							  					else if(resource.getType().equals("video"))
							  					{
							  						String descText = "";
								  					if(!contents.isEmpty() && contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION) != null)
								  					{
								  						descText = contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText();
								  					}	
								  					
							  					%>
							  							<li class="multimedia-item" rid="<%= resource.getResourceId() %>">
							  								<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
							  								<button id="removeMultimediaFile" class="btn btn-transparent fright red" aria-label="<%= lang.getContent("button.remove_video") %>: <%= title %>"><i class="fa fa-trash"></i></button>
							  								<%} %>
							  								<i class="fa fa-file-video-o" aria-hidden="true"></i>
							  								<a class="active" active="false" id="editFile" data-image='{"rId":"<%= resource.getResourceId()%>","tittle":"<%=title %>","descText":"<%=descText %>","path":"<%=path %>", "rType":"<%= resource.getType() %>"}' href="#"><%= title + " - " + path %></a>
							  								
							  							</li>
												<%
							  					}
							  					
												lang.close();
												%>
							  					