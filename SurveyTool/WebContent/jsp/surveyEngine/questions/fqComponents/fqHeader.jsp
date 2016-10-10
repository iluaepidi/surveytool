
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
    								<%
    								Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
    								%>
    								  
   												<legend>

													<div class="form-question-title">{{question.index}}. {{getJsonArrayElement(question.contents, "contentType", "title").text}} </div>
													<div class="form-question-mandatory" ng-show="question.mandatory">
														<span class="visuallyhidden"><%= lang.getContent("survey.question.label.mandatory") %></span>
														<i class="fa fa-asterisk red" aria-hidden="true"></i>
														<span role="alert" class="red" ng-show="showMandatoryErrorMsg(question)"><%= lang.getContent("survey.question.error.mandatory") %></span>
													</div>
											
												</legend>		
<%
lang.close();
%>			  					