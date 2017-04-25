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
				
	
				<!-- <div class="form-question-content">
					<div class="likert-options">
					 	<div class="likert-options-frame">
					 		<span>
					 			<input type="radio" name="{{question.questionId}}" ng-value="1" id="likert1-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert1-{{question.questionId}}"><span>1</span></label>
					 		</span>
					 		<span>
					 			<input type="radio" name="{{question.questionId}}" ng-value="2" id="likert2-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert2-{{question.questionId}}"><span>2</span></label>
					 		</span>
					 		<span>
					 			<input type="radio" name="{{question.questionId}}" ng-value="3" id="likert3-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert3-{{question.questionId}}"><span>3</span></label>
					 		</span>
					 		<span>
					 			<input type="radio" name="{{question.questionId}}" ng-value="4" id="likert4-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert4-{{question.questionId}}"><span>4</span></label>
					 		</span>
					 		<span>
					 			<input type="radio" name="{{question.questionId}}" ng-value="5" id="likert5-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert5-{{question.questionId}}"><span>5</span></label>
					 		</span>
					 		<span>
					 			<input type="radio" name="{{question.questionId}}" ng-value="6" id="likert6-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert6-{{question.questionId}}"><span>6</span></label>
					 		</span>
					 		<span>
					 			<input type="radio" name="{{question.questionId}}" ng-value="7" id="likert7-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert7-{{question.questionId}}"><span>7</span></label>
					 		</span>
					 	</div>
					 </div>	
					 <div class="likert-legend clearfix">
					 	<div class="disagree"><%= lang.getContent("question.form.scale.liker_legend.t_disgree") %></div>
					 	<div><%= lang.getContent("question.form.scale.liker_legend.indiferent") %></div>
					 	<div class="agree"><%= lang.getContent("question.form.scale.liker_legend.t_agree") %></div>
					 </div>
				</div>-->
				
				<div class="form-question-content">
					<div class="likert-options">
					 	<ul class="likert-options-frame col-sm-12 col-xs-12">
					 		<li ng-repeat='n in [] | range:getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value)' class="float-left nopadding col-sm-1 col-xs-1" ng-class='{"col-sm-offset-3 col-xs-offset-3" : n == 1 && getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 5, "col-sm-offset-2 col-xs-offset-2" : n == 1 && getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 7}'>
					 			<input type="radio" name="{{question.questionId}}" ng-value="{{n}}" id="likert{{n}}-{{question.questionId}}" ng-model="question.response" ng-focus="setIndexQuestion(question.index)"/>
					 			<label for="likert{{n}}-{{question.questionId}}"><span>{{n}}</span></label>
					 		</li>
					 	</ul>
					 </div>					 
					 <div class="clearfix nopadding" ng-class='{"col-sm-5 col-xs-5 col-sm-offset-3 col-xs-offset-3" : getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 5, "col-sm-7 col-xs-7 col-sm-offset-2 col-xs-offset-2" : getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 7, "col-sm-11 col-xs-11" : getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 11}'>
					 	<div class="div-likert-line" ng-class='{"div-likert-line-5" : getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 5, "div-likert-line-7" : getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 7, "div-likert-line-11" : getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 11}'>
					 		<div class="liker-line"></div>
					 	</div>
					 </div>
					 <div class="likert-legend clearfix nopadding">
					 	<div class="col-sm-1 col-xs-1"  ng-class='{"col-sm-offset-3 col-xs-offset-3" : getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 5, "col-sm-offset-2 col-xs-offset-2" : getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 7}'><%= lang.getContent("question.form.scale.liker_legend.t_disgree") %></div>
					 	<div class="col-sm-1 col-xs-1"  ng-class='{"col-sm-offset-1 col-xs-offset-1" : getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 5, "col-sm-offset-2 col-xs-offset-2" : getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 7, "col-sm-offset-4 col-xs-offset-4" : getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 11}'><%= lang.getContent("question.form.scale.liker_legend.indiferent") %></div>
					 	<div class="col-sm-1 col-xs-1"  ng-class='{"col-sm-offset-1 col-xs-offset-1" : getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 5, "col-sm-offset-2 col-xs-offset-2" : getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 7, "col-sm-offset-4 col-xs-offset-4" : getNumber(getJsonArrayElement(question.parameters, "name", "scaleType").value) == 11}'><%= lang.getContent("question.form.scale.liker_legend.t_agree") %></div>
					 </div>
				</div>	
				
			</div>
		</fieldset>																						
	</div>
<%
lang.close();
%>