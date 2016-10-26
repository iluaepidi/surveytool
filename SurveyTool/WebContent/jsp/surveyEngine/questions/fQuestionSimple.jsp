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

			<div class="form-question-content">
				<ul class="form-options">
					<li class="radio" ng-class="{img: option.resource}" ng-repeat="option in question.optionsGroups[0].options">
					  <input type="radio" name="{{question.questionId}}-{{question.optionsGroups[0].optionGroupId}}" id="optionsRadios{{option.optionId}}" ng-value="{{option.optionId}}" ng-model="question.optionsGroups[0].response" ng-focus="setIndexQuestion(question.index)">
					  <label for="optionsRadios{{option.optionId}}">
					  	<img src="{{option.resource.urlPath}}" alt='{{getJsonArrayElement(option.resource.contents, "contentType", "altText").text}}' ng-if="option.resource"/>
					    <span>{{getJsonArrayElement(option.contents, "contentType", "title").text}}</span>
					  </label>
					</li>								
				</ul>
			</div>	
		</fieldset>																						
	</div>
