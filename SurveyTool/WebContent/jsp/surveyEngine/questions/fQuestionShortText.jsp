<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.language.Language"%>
    
	<%
	Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
	%>
	<div class="form-question" id="form-question">
		<div class="msg-error">
			<div class="required">
				<p class="msg-title">This question is mandatory.</p>
				<p>Please select an option before to continue.</p>
			</div>
		</div>
		<fieldset>
			<legend>
				<span class="number">{{question.index}}</span> <span class="text">{{getJsonArrayElement(question.contents, "contentType", "title").text}}</span> <span class="mandatory">*<span class="sr-only"><%= lang.getContent("question.mandatory") %></span></span>
			</legend>
			
			<p>{{getJsonArrayElement(question.contents, "contentType", "description").text}}</p>
					
			<jsp:include page="fqComponents/fqResources.jsp" />
			
			<div class="form-question-content">
				<label for="{{question.questionId}}" class="visuallyhidden"><%= lang.getContent("accesibility.question.shorttextAnswer") %></label>
				
				<input type="number" ng-show='getJsonArrayElement(question.parameters, "name", "formFieldType").value == "formFieldTypeNumber"' step='{{getDecimals(getJsonArrayElement(question.parameters, "name", "decimals"))}}' pattern="[0-9]+([\,|\.][0-9]+)?" name="{{question.questionId}}" placeholder="<%= lang.getContent("placeholder.type_here")%>" id="{{question.questionId}}" min='{{getMinValue(getJsonArrayElement(question.parameters, "name", "minValue"))}}' max='{{getMaxValue(getJsonArrayElement(question.parameters, "name", "maxValue"))}}' ng-model="question.response"></input>
			
				<input type="text" ng-show='getJsonArrayElement(question.parameters, "name", "formFieldType").value != "formFieldTypeNumber"' class="form-control" id="{{question.questionId}}" name="{{question.questionId}}" placeholder="<%= lang.getContent("placeholder.type_here")%>" maxlength='{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength"))}}' ng-model="question.response"></input>
			</div>	
			
		</fieldset>																						
	</div>
	<%
	lang.close();
	%>
