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
												<textarea class="form-control" id="<%= questionId %>" name="<%= questionId %>" rows="3" placeholder="Type here_"></textarea>
											</div>																							
										</div>