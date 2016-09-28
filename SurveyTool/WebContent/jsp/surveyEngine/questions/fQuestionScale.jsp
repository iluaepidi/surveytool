
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
													<div class="likert-options">
													 	<div class="likert-options-frame">
													 		<div>
													 			<label for="likert1">1</label>
													 			<input type="radio" name="{{question.questionId}}" value="1" id="likert1" ng-model="question.response"/>
													 		</div>
													 		<div>
													 			<label for="likert2">2</label>
													 			<input type="radio" name="{{question.questionId}}" value="2" id="likert2" ng-model="question.response"/>
													 		</div>
													 		<div>
													 			<label for="likert3">3</label>
													 			<input type="radio" name="{{question.questionId}}" value="3" id="likert3" ng-model="question.response"/>
													 		</div>
													 		<div>
													 			<label for="likert4">4</label>
													 			<input type="radio" name="{{question.questionId}}" value="4" id="likert4" ng-model="question.response"/>
													 		</div>
													 		<div>
													 			<label for="likert5">5</label>
													 			<input type="radio" name="{{question.questionId}}" value="5" id="likert5" ng-model="question.response"/>
													 		</div>
													 		<div>
													 			<label for="likert6">6</label>
													 			<input type="radio" name="{{question.questionId}}" value="6" id="likert6" ng-model="question.response"/>
													 		</div>
													 		<div>
													 			<label for="likert7">7</label>
													 			<input type="radio" name="{{question.questionId}}" value="7" id="likert7" ng-model="question.response"/>
													 		</div>
													 	</div>
													 </div>	
													 <div class="likert-legend">
													 	<div><%= lang.getContent("question.form.scale.liker_legend.t_disgree") %></div>
													 	<div><%= lang.getContent("question.form.scale.liker_legend.indiferent") %></div>
													 	<div><%= lang.getContent("question.form.scale.liker_legend.t_agree") %></div>
													 </div>
												</div>	
											</fieldset>																						
										</div>
<%
lang.close();
%>