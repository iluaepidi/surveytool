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

			<div class="form-question-content" ng-class="{center: option.resource}">
				Response: {{question.optionsGroups[0].response}} - {{question.optionsGroups[0].response == otherOptionValue}}
				<ul class="form-options option-list">
					<li class="radio" ng-class="{img: hasResource(question.optionsGroups[0].options), fourImg: question.optionsGroups[0].options.length == 4}" ng-repeat="option in question.optionsGroups[0].options">
					  <input type="radio" name="{{question.questionId}}-{{question.optionsGroups[0].optionGroupId}}" id="optionsRadios{{option.optionId}}" ng-value="{{option.optionId}}" ng-model="question.optionsGroups[0].response" ng-focus="setIndexQuestion(question.index)">
					  <label for="optionsRadios{{option.optionId}}">
					  	<div class="option-image">
					  		<div class="sub-option-image">
					  			<img src="{{option.resource.urlPath}}" alt='{{getJsonArrayElement(option.resource.contents, "contentType", "altText").text}}' ng-if="option.resource"/>
					  		</div>
					  	</div>
					    <span ng-show='getJsonArrayElement(option.contents, "contentType", "title").text'>{{getJsonArrayElement(option.contents, "contentType", "title").text}}</span>
					  </label>
					</li>
					
					<li class="radio" ng-show="question.optionsGroups[0].otherOption">
					  <input type="radio" name="{{question.questionId}}-{{question.optionsGroups[0].optionGroupId}}" id="optionsRadios-other-{{question.questionId}}" ng-value="{{otherOptionValue}}" ng-model="question.optionsGroups[0].response" ng-focus="setIndexQuestion(question.index)">
					  <label for="optionsRadios-other-{{question.questionId}}">					  	
					    <span ng-if='getJsonArrayElement(question.optionsGroups[0].contents, "contentType", "otherLabel").text'>{{getJsonArrayElement(question.optionsGroups[0].contents, "contentType", "otherLabel").text}}</span>
					    <span ng-if='!getJsonArrayElement(question.optionsGroups[0].contents, "contentType", "otherLabel").text'><%= lang.getContent("accesibility.question.option.legend.other") %></span>
					    <span class="sr-only">(<%= lang.getContent("accesibility.question.option.longtextOther") %>)</span>
					  </label>
					  <div class="form-question-content other-option-text-div">
						<div class="msg-alert" ng-show='getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) == question.optionsGroups[0].responseOtherText.length'>
							<div class="error">
								<p class="msg-title">{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength"))}} <%= lang.getContent("survey.process.title.charLimit") %></p>
								<p role="alert"><%= lang.getContent("survey.process.desc.charLimit") %></p>
							</div>
						</div>
						<div class="char-counter" ng-show='getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) < 9999'>{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) - question.response.length}} <%= lang.getContent("survey.process.charCounter") %></div>
						<label for="{{question.questionId}}" class="sr-only"><%= lang.getContent("accesibility.question.textArea.longtextOther") %></label>				
						<textarea class="form-control other-option-text" 
							id="{{question.questionId}}" 
							name="{{question.questionId}}" 
							rows='{{getLines(getJsonArrayElement(question.parameters, "name", "textLines"), getJsonArrayElement(question.parameters, "name", "textLength"))}}' 
							placeholder='<%= lang.getContent("placeholder.type_here")%>' 
							maxlength='{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength"))}}' 
							ng-focus="setOtherSimpleFocus(question.optionsGroups[0])" ng-readonly="question.optionsGroups[0].response != -1"
							ng-model="question.optionsGroups[0].responseOtherText"></textarea>
					  </div>
					</li>								
				</ul>
			</div>	
		</fieldset>																						
	</div>
