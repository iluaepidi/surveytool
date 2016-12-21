
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
    								<%
    								Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
    								%>
    								  
   												<legend>

													<span class="number">{{question.numQuestion}}</span> 
													<span class="text">{{getJsonArrayElement(question.contents, "contentType", "title").text}}</span> 
													<span class="mandatory" ng-show="question.mandatory">*<span class="sr-only"><%= lang.getContent("survey.question.label.mandatory") %></span></span>
											
												</legend>		
<%
lang.close();
%>			  					