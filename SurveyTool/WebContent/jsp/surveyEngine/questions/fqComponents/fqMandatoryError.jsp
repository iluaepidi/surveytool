
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
    								<%
    								Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
    								%>
    								  
   												<div class="msg-error" ng-show="showMandatoryErrorMsg(question)">
													<div class="required">
														<p class="msg-title"><%= lang.getContent("survey.question.error.mandatory") %></p>
														<p role="alert"><span class="visuallyHidden"><%= lang.getContent("general.question") %> {{question.numQuestion}} <%= lang.getContent("msg.error.mandatory") %>. </span> <%= lang.getContent("survey.question.error.comment.mandatory") %></p>
													</div>
												</div>		
<%
lang.close();
%>			  					