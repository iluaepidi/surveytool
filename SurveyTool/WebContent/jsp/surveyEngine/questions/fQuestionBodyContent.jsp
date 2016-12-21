<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.language.Language"%>

<div class="free-text" ng-click="setIndexQuestion(question.index)">
	<jsp:include page="fqComponents/fqResources.jsp" />
	
	<p ng-bind-html='getJsonArrayElement(question.contents, "contentType", "description").text'></p>
</div>