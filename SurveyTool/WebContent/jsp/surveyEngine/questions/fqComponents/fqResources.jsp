<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        											
    											<%
    											Language lang = (Language) request.getAttribute(Attribute.s_SURVEY_LANGUAGE);
    											%>
    											    											
    											<%
    											Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    											List<Resource> resources = question.getResources();
    											
												if(!resources.isEmpty())
												{
												%>
												<div class="previewFileUpliaded" id="previewFileUploaded">
													<%
													for(Resource resource : resources)
													{
														if(resource.getType().equals(DBConstants.s_VALUE_RESOURCE_TYPE_IMAGE))
														{
															String alt = "";
															if(!resource.getContents().isEmpty() && resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT) != null)
															{
																alt = resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT).getText();
															}
													%>
													<!-- <label for="img" class="visuallyhidden"><%= lang.getContent("accesibility.question.image") %></label> -->
													<div>
									            		<img src="<%= resource.getPathFile() %>" alt="<%= alt %>" />
									            	</div>
									            	<%
														}
														else if(resource.getType().equals(DBConstants.s_VALUE_RESOURCE_TYPE_VIDEO))
														{
															String title = "";
															if(!resource.getContents().isEmpty() && resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE) != null)
															{
																title = resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
															}
													%>
													
													<div class="reproductor-youtube">														
														<iframe id="reproductor_<%= resource.getResourceId() %>" type="text/html" src="https://www.youtube.com/embed/<%= resource.getPathFile() %>?enablejsapi=1" height="250" width="400" allowfullscreen="always" frameborder="0" data-title="<%= title %>" tabindex="-1"></iframe>
													</div>
													<%		
														}
													}
										            %>
									            </div>
									            <%
												}
									            %>
							  					