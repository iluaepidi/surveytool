
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

										<div class="form-question" id="form-question">
											<fieldset>
												<legend>

													{{question.index}}. {{getJsonArrayElement(question.contents, "contentType", "title").text}}													

												</legend>
												
							  					<p>{{getJsonArrayElement(question.contents, "contentType", "description").text}}</p>
							  					
												
												<jsp:include page="fqComponents/fqResources.jsp" />
		
												<div class="form-question-content">
										        	<table>
										            	<thead>
										                	<tr >
										                    	<th></th>
										                    	
										                    	<th class="matrix-title" ng-repeat="option in question.optionsGroups[0].options">{{getJsonArrayElement(option.contents, "contentType", "title").text}}</th>                    		
										                	</tr>
										            	</thead>
										            	<tbody>
										            		
									                    	<tr ng-repeat="optionsGroup in question.optionsGroups">
									                    		<td class="matrix-title">{{getJsonArrayElement(optionsGroup.contents, "contentType", "title").text}}</td>
									                    		
									                    		<th class="matrix-title" ng-repeat="option in optionsGroup.options"><input type='{{getMatrixOptionType(getJsonArrayElement(question.parameters, "name", "matrixType"))}}' name="{{question.questionId}}-{{optionsGroup.optionGroupId}}-{{option.optionId}}" id="options-{{optionsGroup.optionGroupId}}-{{option.optionId}}" value="{{option.optionId}}"></th>
									                    	</tr>
										                    
										            	</tbody>
										        	</table>
										    	</div>
											</fieldset>																						
										</div>