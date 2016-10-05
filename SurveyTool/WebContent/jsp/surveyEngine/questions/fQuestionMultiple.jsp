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
<%@page import="ilu.surveytool.language.Language"%>
    
    								<%
    								Language lang = (Language) request.getAttribute(Attribute.s_SURVEY_LANGUAGE);
    								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    								String index = request.getParameter(Parameter.s_INDEX);
    								int questionId = question.getQuestionId();
    								List<Resource> resources = question.getResources();

    								String title = "";
    								if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
    									title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
    								}
    								
    								%>
										<div class="form-question" id="form-question">
											<fieldset>
												<legend>
													<%= lang.getContent("survey_engine.question.title") %> <%= index %>. <%= title %>													
												</legend>
												<%
							  					if(question!=null && question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION)!=null)
							  					{
							  					%>
							  						<p><%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText() %></p>
							  					<% 
							  					}
												%>
	
												<jsp:include page="fqComponents/fqResources.jsp" />
												
												<div class="form-question-content">
																			  					
												<%
								  				for(OptionsGroup optionsGroup : question.getOptionsGroups())
								  				{
								  				%>
													<ul class="form-options">
													<%
							  						for(Option option : optionsGroup.getOptions())
							  						{
							  							String id = "optionsChecks" + option.getId();
							  						%>
													 	<li class="checkbox">
														  <input type="checkbox" name="<%= questionId + "-" + optionsGroup.getId() + "-" + option.getId()%>" id="<%= id %>" value="<%= option.getId() %>">
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