<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="ilu.surveytool.constants.Attribute"%>
<%@ page import="ilu.surveytool.language.Language"%>

	<%
	Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);    								
	%>
	<div class="form-question scale" id="form-question" ng-click="setIndexQuestion(question.index)">
		<jsp:include page="fqComponents/fqMandatoryError.jsp" />
	
		<fieldset>
		
			<jsp:include page="fqComponents/fqHeader.jsp" />
			
			<div class="scale-content">
			
				<p>{{getJsonArrayElement(question.contents, "contentType", "description").text}}</p>
						
				
				<jsp:include page="fqComponents/fqResources.jsp" />
				
	
				<div class="form-question-content">
					<div class="likert-options">
					 	<div class="likert-options-frame">
					 		<span>
					 			<input type="radio" name="{{question.questionId}}" ng-value="1" id="likert1-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert1-{{question.questionId}}">1</label>
					 		</span>
					 		<span>
					 			<input type="radio" name="{{question.questionId}}" ng-value="2" id="likert2-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert2-{{question.questionId}}">2</label>
					 		</span>
					 		<span>
					 			<input type="radio" name="{{question.questionId}}" ng-value="3" id="likert3-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert3-{{question.questionId}}">3</label>
					 		</span>
					 		<span>
					 			<input type="radio" name="{{question.questionId}}" ng-value="4" id="likert4-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert4-{{question.questionId}}">4</label>
					 		</span>
					 		<span>
					 			<input type="radio" name="{{question.questionId}}" ng-value="5" id="likert5-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert5-{{question.questionId}}">5</label>
					 		</span>
					 		<span>
					 			<input type="radio" name="{{question.questionId}}" ng-value="6" id="likert6-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert6-{{question.questionId}}">6</label>
					 		</span>
					 		<span>
					 			<input type="radio" name="{{question.questionId}}" ng-value="7" id="likert7-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert7-{{question.questionId}}">7</label>
					 		</span>
					 	</div>
					 </div>	
					 <div class="likert-legend clearfix">
					 	<div class="disagree"><%= lang.getContent("question.form.scale.liker_legend.t_disgree") %></div>
					 	<div><%= lang.getContent("question.form.scale.liker_legend.indiferent") %></div>
					 	<div class="agree"><%= lang.getContent("question.form.scale.liker_legend.t_agree") %></div>
					 </div>
				</div>	
			</div>
		</fieldset>																						
	</div>
<%
lang.close();
%>