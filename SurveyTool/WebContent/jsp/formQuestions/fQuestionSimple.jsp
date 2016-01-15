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
    								%>
										<div class="form-question" id="form-question">
											<label for="<%= questionId %>">Question <%= index %>. <%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></label>
											<div class="form-question-content">
											<%
							  				for(OptionsGroup optionsGroup : question.getOptionsGroups())
							  				{
							  				%>
												<ul class="form-options">
												<%
						  						for(Option option : optionsGroup.getOptions())
						  						{
						  							String id = "optionsRadios" + option.getIndex();
						  						%>
												 	<li class="radio">
													  <input type="radio" name="<%= questionId + "-" + optionsGroup.getId() %>" id="<%= id %>" value="<%= option.getId() %>" checked>
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
										</div>