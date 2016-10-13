<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.language.Language"%>
    
    								<%
    								Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
    								%>
										<div class="form-question" id="form-question">
											<fieldset>
												<legend>

													{{question.index}}. {{getJsonArrayElement(question.contents, "contentType", "title").text}}													

												</legend>
												
							  					<p>{{getJsonArrayElement(question.contents, "contentType", "description").text}}</p>
							  					
	
												<jsp:include page="fqComponents/fqResources.jsp" />
												
												<div class="form-question-content" >
													<label for="{{question.questionId}}" class="visuallyhidden"><%= lang.getContent("accesibility.question.shorttextAnswer") %></label>
														<input type="text" ng-show='getJsonArrayElement(question.parameters, "name", "formFieldType").value == "formFieldTypeNumber"' step='{{getDecimals(getJsonArrayElement(question.parameters, "name", "decimals"))}}'  name="decimal" placeholder="<%= lang.getContent("placeholder.type_here")%>" id="{{question.questionId}}" min='{{getMinValue(getJsonArrayElement(question.parameters, "name", "minValue"))}}' max='{{getMaxValue(getJsonArrayElement(question.parameters, "name", "maxValue"))}}' ng-model="question.response" currentVal="" ng-pattern='decimalRegex(getJsonArrayElement(question.parameters, "name", "decimals"))'></input>
                                                        <div ng-show="survey.$submitted || survey.decimal.$touched">                                                                                         
                                                            <span id="inputError1Status" class="error-label" role="alert" ng-show="survey.decimal.$error.pattern"><%= lang.getContent("msg.error.inputNumber")%> {{getDecimals(getJsonArrayElement(question.parameters, "name", "decimals"))}}</span>
                                                        	<span id="inputError2Status" class="error-label" role="alert" ng-show="survey.numeric.$error.min || survey.numeric.$error.max"><%= lang.getContent("msg.error.minmax1")%> {{getMinValue(getJsonArrayElement(question.parameters, "name", "minValue"))}} <%= lang.getContent("msg.error.minmax2")%> {{getMaxValue(getJsonArrayElement(question.parameters, "name", "maxValue"))}}</span>							  							
							  							</div>
							  						
							  							<input type="text" ng-show='getJsonArrayElement(question.parameters, "name", "formFieldType").value != "formFieldTypeNumber"' class="form-control" id="{{question.questionId}}" name="{{question.questionId}}" placeholder="<%= lang.getContent("placeholder.type_here")%>" maxlength='{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength"))}}' ng-model="question.response"></input>
							  						
												</div>	
												
											</fieldset>																						
										</div>
<%
lang.close();
%>