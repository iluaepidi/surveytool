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
												
												<div class="form-question-content">
													<label for="{{question.questionId}}" class="visuallyhidden"><%= lang.getContent("accesibility.question.shorttextAnswer") %></label>
													
							  							<input type="number" ng-show='getJsonArrayElement(question.parameters, "name", "formFieldType").value == "formFieldTypeNumber"' step='{{getDecimals(getJsonArrayElement(question.parameters, "name", "decimals"))}}' pattern="[0-9]+([\,|\.][0-9]+)?" name="{{question.questionId}}" placeholder="<%= lang.getContent("placeholder.type_here")%>" id="{{question.questionId}}" min='{{getMinValue(getJsonArrayElement(question.parameters, "name", "minValue"))}}' max='{{getMaxValue(getJsonArrayElement(question.parameters, "name", "maxValue"))}}'></input>
							  						
							  							<input type="text" ng-show='getJsonArrayElement(question.parameters, "name", "formFieldType").value != "formFieldTypeNumber"' class="form-control" id="{{question.questionId}}" name="{{question.questionId}}" placeholder="<%= lang.getContent("placeholder.type_here")%>" maxlength='{{getMaxLength(getJsonArrayElement(question.parameters, "name", "textLength"))}}'></textarea>
							  						
												</div>	
												
											</fieldset>																						
										</div>
<%
lang.close();
%>