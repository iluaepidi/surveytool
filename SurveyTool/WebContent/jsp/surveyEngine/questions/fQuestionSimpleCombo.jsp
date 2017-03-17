<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="ilu.surveytool.language.Language"%>
<%@ page import="ilu.surveytool.constants.Attribute"%>
	<%
	Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
	%>
	<div class="form-question" id="form-question" ng-click="setIndexQuestion(question.index)">
		
		<jsp:include page="fqComponents/fqMandatoryError.jsp" />
	
		<fieldset>
		
			<jsp:include page="fqComponents/fqHeader.jsp" />
			
			<p>{{getJsonArrayElement(question.contents, "contentType", "description").text}}</p>
					
			<jsp:include page="fqComponents/fqResources.jsp" />

			<div class="form-question-content question-select" ng-class="{center: option.resource}">
				<label for="option-list-select-{{question.questionId}}"><%= lang.getContent("accesibility.question.select.label") %></label>
				<select class="form-control option-list-select" ng-model="question.optionsGroups[0].response" name="{{question.questionId}}-{{question.optionsGroups[0].optionGroupId}}" id="option-list-select-{{question.questionId}}">
					<option value="">-- <%= lang.getContent("accesibility.question.select.option.default") %> --</option>
					<option value="{{option.optionId}}" ng-repeat="option in question.optionsGroups[0].options">{{getJsonArrayElement(option.contents, "contentType", "title").text}}</option>											
				</select>
			</div>	
		</fieldset>																						
	</div>
