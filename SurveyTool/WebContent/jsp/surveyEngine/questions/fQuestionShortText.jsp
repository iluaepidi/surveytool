<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.language.Language"%>
    
										<%
										Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
										String min = request.getParameter("min");
										String max = request.getParameter("max");
										boolean hasMinMax = min == null || max == null;
										System.out.println("Max: " + max + " - Min: " + min);
										%>
										<div class="form-question" id="form-question" ng-click="setIndexQuestion(question.index)">
											<jsp:include page="fqComponents/fqMandatoryError.jsp" />
	
											<fieldset>
											
												<jsp:include page="fqComponents/fqHeader.jsp" />
												
												<p>{{getJsonArrayElement(question.contents, "contentType", "description").text}}</p>
														
												<jsp:include page="fqComponents/fqResources.jsp" />
												
												<div class="form-question-content">
													<label for="{{question.questionId}}" class="sr-only"><%= lang.getContent("accesibility.question.shorttextAnswer") %></label>
													
													<!-- NUMERIC -->
													<div ng-show='(survey.$submitted || survey["decimal-" + question.questionId].$touched)'>
														<div class="msg-error" ng-show='survey["decimal-" + question.questionId].$error.pattern'>
															<div class="error">
																<p class="msg-title"><%= lang.getContent("survey.process.title.numFormat") %></p>
																<p role="alert" ng-show='survey["decimal-" + question.questionId].$error.pattern'><span class="visuallyHidden"><%= lang.getContent("msg.error.error_in") %> <%= lang.getContent("general.question") %> {{question.numQuestion}}: </span><%= lang.getContent("survey.process.desc.numFormat1") %> <span ng-show='getJsonArrayElement(question.parameters, "name", "decimals").value'><%= lang.getContent("survey.process.desc.numFormat2") %> {{getJsonArrayElement(question.parameters, "name", "decimals").value}} <%= lang.getContent("survey.process.desc.numFormat3") %></span></p>
															</div>
														</div>
														<div class="msg-error" ng-show='(getJsonArrayElement(question.parameters, "name", "minValue").value || getJsonArrayElement(question.parameters, "name", "maxValue").value) && isOutOfRange(question.response, getMinValue(getJsonArrayElement(question.parameters, "name", "minValue")), getMaxValue(getJsonArrayElement(question.parameters, "name", "maxValue")))'>
															<div class="error">
																<p class="msg-title"><%= lang.getContent("survey.process.title.numLimits") %></p>
																<p role="alert" ng-show='isOutOfRange(question.response, getMinValue(getJsonArrayElement(question.parameters, "name", "minValue")), getMaxValue(getJsonArrayElement(question.parameters, "name", "maxValue")))'><span class="visuallyHidden"><%= lang.getContent("msg.error.error_in") %> <%= lang.getContent("general.question") %> {{question.numQuestion}}: </span><%= lang.getContent("survey.process.desc.numLimits") %> {{getMinValue(getJsonArrayElement(question.parameters, "name", "minValue"))}} <%= lang.getContent("survey.process.desc.numLimits.and") %> {{getMaxValue(getJsonArrayElement(question.parameters, "name", "maxValue"))}}.</p>
															</div>
														</div>
							  						</div>
							  						<div class="char-counter" ng-show='getJsonArrayElement(question.parameters, "name", "formFieldType").value == "formFieldTypeNumber"'>
							  							<span ng-show='getJsonArrayElement(question.parameters, "name", "decimals").value'>{{getJsonArrayElement(question.parameters, "name", "decimals").value}} <%= lang.getContent("survey.process.decimalsMax") %></span> 
							  							<span ng-show='getJsonArrayElement(question.parameters, "name", "minValue").value || getJsonArrayElement(question.parameters, "name", "maxValue").value'><%= lang.getContent("survey.process.numLimits") %> {{getMinValue(getJsonArrayElement(question.parameters, "name", "minValue"))}} <%= lang.getContent("survey.process.desc.numLimits.and") %> {{getMaxValue(getJsonArrayElement(question.parameters, "name", "maxValue"))}}.</span>
							  						</div>												
													<input type="text" class="form-control" ng-if='getJsonArrayElement(question.parameters, "name", "formFieldType").value == "formFieldTypeNumber"' step='{{getDecimals(getJsonArrayElement(question.parameters, "name", "decimals"))}}'  name="decimal-{{question.questionId}}" placeholder="<%= lang.getContent("placeholder.type_here")%>" id="number-{{question.questionId}}" <%if(hasMinMax){ %>min='{{getMinValue(getJsonArrayElement(question.parameters, "name", "minValue"))}}' max='{{getMaxValue(getJsonArrayElement(question.parameters, "name", "maxValue"))}}' <%}%> ng-model="question.response" ng-pattern='decimalRegex(getJsonArrayElement(question.parameters, "name", "decimals"))' ng-focus="setIndexQuestion(question.index)"></input>
							  																			
													<!-- SHORT TEXT -->
													<div class="msg-alert" ng-show='getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) == question.response.length'>
														<div class="error">
															<p class="msg-title">{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength"))}} <%= lang.getContent("survey.process.title.charLimit") %></p>
															<p role="alert"><%= lang.getContent("survey.process.desc.charLimit") %></p>
														</div>
													</div>
													<div class="char-counter" ng-show='getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) < 9999' >
														{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength")) - question.response.length}} <%= lang.getContent("survey.process.charCounter") %>
													</div>
													<input type="text" ng-if='getJsonArrayElement(question.parameters, "name", "formFieldType").value != "formFieldTypeNumber"' class="form-control" id="text-{{question.questionId}}" name="text-{{question.questionId}}" placeholder="<%= lang.getContent("placeholder.type_here")%>" maxlength='{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength"))}}' ng-model="question.response" ng-focus="setIndexQuestion(question.index)"></input>
												</div>	
												
											</fieldset>																						
										</div>
	<%
	lang.close();
	%>
