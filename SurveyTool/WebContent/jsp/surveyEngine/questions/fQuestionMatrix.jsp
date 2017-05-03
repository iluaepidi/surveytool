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
	        	<table>
	            	<thead>
	                	<tr >
	                    	<td></td>
	                    	<th ng-repeat="option in question.optionsGroups[0].options">{{getJsonArrayElement(option.contents, "contentType", "title").text}}</th>                    		
	                	</tr>
	            	</thead>
	            	<tbody>
	            		
                    	<tr ng-repeat="optionsGroup in question.optionsGroups">
                    		<th class="matrix-title">{{getJsonArrayElement(optionsGroup.contents, "contentType", "title").text}}</th>
                    		
                    		<td ng-class="{'mradio': optionsGroup.optionType == 'radio', 'mcheckbox': optionsGroup.optionType != 'radio'}" ng-repeat="option in optionsGroup.options">
                    			<input type='{{getMatrixOptionType(getJsonArrayElement(question.parameters, "name", "matrixType"))}}' name="{{question.questionId}}-{{optionsGroup.optionGroupId}}-{{option.optionId}}" id="options-{{optionsGroup.optionGroupId}}-{{option.optionId}}" ng-value="{{option.optionId}}" ng-if="optionsGroup.optionType == 'radio'" ng-model="optionsGroup.response" ng-focus="setIndexQuestion(question.index)">
                    			<input type='{{getMatrixOptionType(getJsonArrayElement(question.parameters, "name", "matrixType"))}}' name="{{question.questionId}}-{{optionsGroup.optionGroupId}}-{{option.optionId}}" id="options-{{optionsGroup.optionGroupId}}-{{option.optionId}}" ng-if="optionsGroup.optionType != 'radio'" ng-model="option.response" ng-focus="setIndexQuestion(question.index)">
                    			<label for="options-{{optionsGroup.optionGroupId}}-{{option.optionId}}"><span class="sr-only">{{getJsonArrayElement(optionsGroup.contents, "contentType", "title").text}} - {{getJsonArrayElement(option.contents, "contentType", "title").text}}</span></label>
                    		</td>
                    	</tr>
	                    
	            	</tbody>
	        	</table>
	    	</div>
		</fieldset>																						
	</div>
