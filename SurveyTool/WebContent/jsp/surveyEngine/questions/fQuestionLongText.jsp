
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.language.Language"%>
    
<%
Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
%>
	<div class="form-question" id="form-question">
		<fieldset>
			<legend>
				<span class="number">{{question.index}}</span> <span class="text">{{getJsonArrayElement(question.contents, "contentType", "title").text}}</span> <span class="mandatory">*<span class="sr-only"><%= lang.getContent("question.mandatory") %></span></span>
			</legend>
			
			<p>{{getJsonArrayElement(question.contents, "contentType", "description").text}}</p>

			<jsp:include page="fqComponents/fqResources.jsp" />
			
			<div class="form-question-content">
				<div class="msg-error">
					<div class="error">
						<p class="msg-title">1000 characters max.</p>
						<p>Please select an option before to continue.</p>
					</div>
				</div>
				<label for="{{question.questionId}}" class="visuallyhidden"><%= lang.getContent("accesibility.question.longtextAnswer") %></label>
				<textarea class="form-control" id="{{question.questionId}}" name="{{question.questionId}}" rows='{{getLines(getJsonArrayElement(question.parameters, "name", "textLines"), getJsonArrayElement(question.parameters, "name", "textLength"))}}' placeholder='<%= lang.getContent("placeholder.type_here")%>' maxlength='{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength"))}}' ng-model="question.response"></textarea>
			</div>	
			
		</fieldset>																						
	</div>
<%
lang.close();
%>