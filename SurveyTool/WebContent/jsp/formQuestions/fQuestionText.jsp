<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    								<%
    								//Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    								%>
										<div class="form-question" id="form-question">
											<label for="qresponse">Question 1. What do you think about this?</label>
											<textarea class="form-control" id="qresponse" rows="3" placeholder="Type here_"></textarea>																							
										</div>