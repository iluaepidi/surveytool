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
							  					String altText = "";
							  					if(!contents.isEmpty())
							  					{
							  						title = contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
							  						altText = contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT).getText();
							  					}
							  					String path = resource.getPathFile();
							  					%>
							  							<li class="multimedia-item" rid="<%= resource.getResourceId() %>">
							  								<i class="fa fa-file-image-o"></i>
							  								<a class="active" active="false" id="editFile" data-image='{"rId":"<%= resource.getResourceId()%>","tittle":"<%=title %>","altText":"<%=altText %>","path":"<%=path %>"}' href="#"><%= title + " - " + path %></a>
							  								<button id="removeMultimediaFile" class="btn btn-transparent fright red" aria-label="<%= lang.getContent("button.remove_file") %>: <%= title %>"><i class="fa fa-trash"></i></button>
							  							</li>
												<%
												lang.close();
												%>
							  					