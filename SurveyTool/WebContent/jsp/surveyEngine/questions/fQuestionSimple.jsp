<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.OptionsGroup"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    								<%
    								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    								String index = request.getParameter(Parameter.s_INDEX);
    								int questionId = question.getQuestionId();
    								List<Resource> resources = question.getResources();
    								%>
										<div class="form-question" id="form-question">
											<fieldset>
												<legend>
													Question <%= index %>. <%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %>													
												</legend>
												<%
							  					if(question.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION))
							  					{
							  					%>
							  						<p><%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText() %></p>
							  					<% 
							  					}
							  					
												if(!resources.isEmpty())
												{
												%>
												<div class="previewFileUpliaded" id="previewFileUploaded">
													<%
													for(Resource resource : resources)
													{
														System.out.println("Error: " + resource.toString());
														String altText = "";
														if(!resource.getContents().isEmpty()) altText = resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT).getText();
													%>
									            	<img src="<%= resource.getPathFile() %>" alt="<%= altText %>" />
									            	<%
													}
										            %>
									            </div>
									            <%
												}
									            %>
							  					
												<div class="form-question-content">
																			  					
												<%
								  				for(OptionsGroup optionsGroup : question.getOptionsGroups())
								  				{
								  				%>
													<ul class="form-options">
													<%
							  						for(Option option : optionsGroup.getOptions())
							  						{
							  							String id = "optionsRadios" + option.getId();
							  						%>
													 	<li class="radio">
														  <input type="radio" name="<%= questionId + "-" + optionsGroup.getId() %>" id="<%= id %>" value="<%= option.getId() %>">
														  <label for="<%= id %>">
														    <%= option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %>
														  </label>
														</li>
													<%
							  						}
													%>
													</ul>
												<%
								  				}
												%>
												</div>	
											</fieldset>																						
										</div>