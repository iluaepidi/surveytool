
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.language.Language"%>

	<%
	Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);    								
	%>
	<div class="form-question scale" id="form-question">
		<fieldset>
			<legend>
				<span class="number">{{question.index}}</span> <span class="text">{{getJsonArrayElement(question.contents, "contentType", "title").text}}</span> <span class="mandatory">*<span class="sr-only"><%= lang.getContent("question.mandatory") %></span></span>
			</legend>
			
			<p>{{getJsonArrayElement(question.contents, "contentType", "description").text}}</p>
					
			
			<jsp:include page="fqComponents/fqResources.jsp" />
			

			<div class="form-question-content">
				<div class="likert-options">
				 	<div class="likert-options-frame">
				 		<span>
				 			<input type="radio" name="{{question.questionId}}" value="1" id="likert1" ng-model="question.response"/>
				 			<label for="likert1">1</label>
				 		</span>
				 		<span>
				 			<input type="radio" name="{{question.questionId}}" value="2" id="likert2" ng-model="question.response"/>
				 			<label for="likert2">2</label>
				 		</span>
				 		<span>
				 			<input type="radio" name="{{question.questionId}}" value="3" id="likert3" ng-model="question.response"/>
				 			<label for="likert3">3</label>
				 		</span>
				 		<span>
				 			<input type="radio" name="{{question.questionId}}" value="4" id="likert4" ng-model="question.response"/>
				 			<label for="likert4">4</label>
				 		</span>
				 		<span>
				 			<input type="radio" name="{{question.questionId}}" value="5" id="likert5" ng-model="question.response"/>
				 			<label for="likert5">5</label>
				 		</span>
				 		<span>
				 			<input type="radio" name="{{question.questionId}}" value="6" id="likert6" ng-model="question.response"/>
				 			<label for="likert6">6</label>
				 		</span>
				 		<span>
				 			<input type="radio" name="{{question.questionId}}" value="7" id="likert7" ng-model="question.response"/>
				 			<label for="likert7">7</label>
				 		</span>
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