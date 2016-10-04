<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.language.Language"%>


										<div class="free-text">
											<p ng-bind-html='getJsonArrayElement(question.contents, "contentType", "description").text'></p>
										</div>