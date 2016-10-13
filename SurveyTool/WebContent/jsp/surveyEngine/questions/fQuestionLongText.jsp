
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.language.Language"%>
    
<%
Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
%>
	<div class="form-question" id="form-question">
		<jsp:include page="fqComponents/fqMandatoryError.jsp" />
	
		<fieldset>
		
			<jsp:include page="fqComponents/fqHeader.jsp" />
			
			<p>{{getJsonArrayElement(question.contents, "contentType", "description").text}}</p>

			<jsp:include page="fqComponents/fqResources.jsp" />
			
			<div class="form-question-content">
				<div class="msg-error" ng-show='getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) == question.response.length'>
					<div class="error">
						<p class="msg-title">{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength"))}} <%= lang.getContent("survey.process.title.charLimit") %></p>
						<p role="alert"><%= lang.getContent("survey.process.desc.charLimit") %></p>
					</div>
				</div>
				<div class="char-counter" ng-show='getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) < 9999'>{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) - question.response.length}} <%= lang.getContent("survey.process.charCounter") %></div>
				<label for="{{question.questionId}}" class="sr-only"><%= lang.getContent("accesibility.question.longtextAnswer") %></label>				
				<textarea class="form-control" id="{{question.questionId}}" name="{{question.questionId}}" rows='{{getLines(getJsonArrayElement(question.parameters, "name", "textLines"), getJsonArrayElement(question.parameters, "name", "textLength"))}}' placeholder='<%= lang.getContent("placeholder.type_here")%>' maxlength='{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength"))}}' ng-model="question.response"></textarea>
				
			</div>	
			
		</fieldset>																						
	</div>
<%
lang.close();
%>