
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
												
													<ul class="form-options">
													 	<li class="checkbox" ng-repeat="option in question.optionsGroups[0].options">
														  <input type="checkbox" name="{{question.questionId}}-{{question.optionsGroups[0].optionGroupId}}-{{option.optionId}}" id="optionsChecks{{option.optionId}}" ng-model="option.response">
														  <label for="optionsChecks{{option.optionId}}">
														    {{getJsonArrayElement(option.contents, "contentType", "title").text}}
														  </label>
														</li>
													
													</ul>
												
												</div>	
											</fieldset>																						
										</div>