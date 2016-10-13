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
													<label for="{{question.questionId}}" class="sr-only"><%= lang.getContent("accesibility.question.shorttextAnswer") %></label>
													
													<!-- NUMERIC -->
													<div ng-show='(survey.$submitted || survey.decimal.$touched)'>
														<div class="msg-error" ng-show="survey.decimal.$error.pattern">
															<div class="error">
																<p class="msg-title"><%= lang.getContent("survey.process.title.numFormat") %></p>
																<p role="alert" ng-show="survey.decimal.$error.pattern"><%= lang.getContent("survey.process.desc.numFormat1") %> <span ng-show='getJsonArrayElement(question.parameters, "name", "decimals").value'><%= lang.getContent("survey.process.desc.numFormat2") %> {{getJsonArrayElement(question.parameters, "name", "decimals").value}} <%= lang.getContent("survey.process.desc.numFormat3") %></span></p>
															</div>
														</div>
														<div class="msg-error" ng-show='isOutOfRange(question.response, getMinValue(getJsonArrayElement(question.parameters, "name", "minValue")), getMaxValue(getJsonArrayElement(question.parameters, "name", "maxValue")))'>
															<div class="error">
																<p class="msg-title"><%= lang.getContent("survey.process.title.numLimits") %></p>
																<p role="alert" ng-show='isOutOfRange(question.response, getMinValue(getJsonArrayElement(question.parameters, "name", "minValue")), getMaxValue(getJsonArrayElement(question.parameters, "name", "maxValue")))'><%= lang.getContent("survey.process.desc.numLimits") %> {{getMinValue(getJsonArrayElement(question.parameters, "name", "minValue"))}} <%= lang.getContent("survey.process.desc.numLimits.and") %> {{getMaxValue(getJsonArrayElement(question.parameters, "name", "maxValue"))}}.</p>
															</div>
														</div>
							  						</div>
							  						<div class="char-counter" ng-show='getJsonArrayElement(question.parameters, "name", "decimals").value'><span ng-show='getJsonArrayElement(question.parameters, "name", "decimals").value'>{{getJsonArrayElement(question.parameters, "name", "decimals").value}} <%= lang.getContent("survey.process.decimalsMax") %></span> <span ng-show='getJsonArrayElement(question.parameters, "name", "minValue").value || getJsonArrayElement(question.parameters, "name", "maxValue").value'><%= lang.getContent("survey.process.numLimits") %> {{getMinValue(getJsonArrayElement(question.parameters, "name", "minValue"))}} <%= lang.getContent("survey.process.desc.numLimits.and") %> {{getMaxValue(getJsonArrayElement(question.parameters, "name", "maxValue"))}}.</span></div>												
													<input type="text" class="form-control" ng-show='getJsonArrayElement(question.parameters, "name", "formFieldType").value == "formFieldTypeNumber"' step='{{getDecimals(getJsonArrayElement(question.parameters, "name", "decimals"))}}'  name="decimal" placeholder="<%= lang.getContent("placeholder.type_here")%>" id="{{question.questionId}}" min='{{getMinValue(getJsonArrayElement(question.parameters, "name", "minValue"))}}' max='{{getMaxValue(getJsonArrayElement(question.parameters, "name", "maxValue"))}}' ng-model="question.response" ng-pattern='decimalRegex(getJsonArrayElement(question.parameters, "name", "decimals"))'></input>
							  																			
													<!-- SHORT TEXT -->
													<div class="msg-error" ng-show='getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) == question.response.length'>
														<div class="error">
															<p class="msg-title">{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength"))}} <%= lang.getContent("survey.process.title.charLimit") %></p>
															<p role="alert"><%= lang.getContent("survey.process.desc.charLimit") %></p>
														</div>
													</div>
													<div class="char-counter" ng-show='getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) < 9999'>{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) - question.response.length}} <%= lang.getContent("survey.process.charCounter") %></div>
													<input type="text" ng-show='getJsonArrayElement(question.parameters, "name", "formFieldType").value != "formFieldTypeNumber"' class="form-control" id="{{question.questionId}}" name="{{question.questionId}}" placeholder="<%= lang.getContent("placeholder.type_here")%>" maxlength='{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength"))}}' ng-model="question.response"></input>
												</div>	
												
											</fieldset>																						
										</div>
	<%
	lang.close();
	%>
