<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="ilu.surveytool.language.Language"%>
<%@ page import="ilu.surveytool.constants.Attribute"%>
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
				<ul class="form-options">
					<li class="radio img" ng-repeat="option in question.optionsGroups[0].options">
					  <input type="radio" name="{{question.questionId}}-{{question.optionsGroups[0].optionGroupId}}" id="optionsRadios{{option.optionId}}" value="{{option.optionId}}" ng-model="question.optionsGroups[0].response">
					  <label for="optionsRadios{{option.optionId}}">
					  	<img src="img/tmp/question.jpg" alt=""/>
					    <span>{{getJsonArrayElement(option.contents, "contentType", "title").text}}</span>
					  </label>
					</li>								
				</ul>
			</div>	
		</fieldset>																						
	</div>
