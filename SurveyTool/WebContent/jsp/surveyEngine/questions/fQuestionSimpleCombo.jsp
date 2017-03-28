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
				<select class="form-control option-list-select" 
						ng-model="question.optionsGroups[0].response" 
						name="{{question.questionId}}-{{question.optionsGroups[0].optionGroupId}}" 
						id="option-list-select-{{question.questionId}}">
					<option value="">-- <%= lang.getContent("accesibility.question.select.option.default") %> --</option>
					<option value="{{option.optionId}}" ng-repeat="option in question.optionsGroups[0].options" ng-if='!option.otherOption || (getJsonArrayElement(option.contents, "contentType", "title").text && getJsonArrayElement(option.contents, "contentType", "title").text != "")'>{{getJsonArrayElement(option.contents, "contentType", "title").text}}</option>
					<option value="{{option.optionId}}" ng-repeat="option in question.optionsGroups[0].options" ng-if='option.otherOption && (!getJsonArrayElement(option.contents, "contentType", "title").text || getJsonArrayElement(option.contents, "contentType", "title").text == "")'><%= lang.getContent("accesibility.question.option.legend.other") %></option>
				</select>
			</div>
			<div class="form-question-content other-option-text-div" ng-if="hasOtherOption(question.optionsGroups[0].options)"  ng-show="question.optionsGroups[0].response == getOtherOptionId(question.optionsGroups[0].options)">
				<div class="msg-alert" ng-show='getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) == question.optionsGroups[0].responseOtherText.length'>
					<div class="error">
						<p class="msg-title">{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength"))}} <%= lang.getContent("survey.process.title.charLimit") %></p>
						<p role="alert"><%= lang.getContent("survey.process.desc.charLimit") %></p>
					</div>
				</div>
				<div class="char-counter" ng-show='getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) < 9999'>{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) - question.optionsGroups[0].responseOtherText.length}} <%= lang.getContent("survey.process.charCounter") %></div>
				<label for="{{question.questionId}}" class="sr-only"><%= lang.getContent("accesibility.question.textArea.longtextOther") %></label>				
				<textarea class="form-control other-option-text" 
					id="textArea{{question.questionId}}" 
					name="{{question.questionId}}" 
					rows='{{getLines(getJsonArrayElement(question.parameters, "name", "textLines"), getJsonArrayElement(question.parameters, "name", "textLength"))}}' 
					placeholder='<%= lang.getContent("placeholder.type_here")%>' 
					maxlength='{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength"))}}' 
					ng-model="question.optionsGroups[0].responseOtherText"></textarea>
			</div>	
		</fieldset>																						
	</div>
