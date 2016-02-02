<%@page import="ilu.surveytool.databasemanager.DataObject.Content"%>
<%@page import="java.util.HashMap"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    								
							  					
							  					<%
							  					Resource resource = (Resource) request.getAttribute(Attribute.s_RESOURCE);
							  					HashMap<String, Content> contents = resource.getContents();
							  					String title = "";
							  					if(!contents.isEmpty())
							  					{
							  						title = contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
							  					}
							  					%>
							  							<li class="multimedia-item" rid="<%= resource.getResourceId() %>">
							  								<i class="fa fa-file-image-o"></i>
							  								<a href="#"><%= title + " - " + resource.getPathFile() %></a>
							  								<button id="removeMultimediaFile" class="btn btn-transparent fright red" aria-label="Remove file: <%= title %>"><i class="fa fa-trash"></i></button>
							  							</li>
							  					