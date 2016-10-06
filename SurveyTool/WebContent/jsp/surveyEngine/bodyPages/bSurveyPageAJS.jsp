					
<%@page import="org.codehaus.jettison.json.JSONObject"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Page"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Section"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
				<%
				//Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO);
				
				Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
				JSONObject sInfo = (JSONObject) request.getAttribute(Attribute.s_SURVEY_INFO);
				%>	
				<script>
				 var playText = "<%= lang.getContent("file.player.yt.button.play") %>";
				 var muteText = "<%= lang.getContent("file.player.yt.button.mute") %>";
				 var volDownText = "<%= lang.getContent("file.player.yt.button.volDown") %>";
				 var volUpText = "<%= lang.getContent("file.player.yt.button.volUp") %>";
				 var rewText = "<%= lang.getContent("file.player.yt.button.rew") %>";
				 var fwdText = "<%= lang.getContent("file.player.yt.button.fwd") %>";
				 var pauseText = "<%= lang.getContent("file.player.yt.button.pause") %>";
				 var positionText = "<%= lang.getContent("file.player.yt.dt.position") %>";
				 var secondsText = "<%= lang.getContent("file.player.yt.dd.seconds") %>";
				 var durationText = "<%= lang.getContent("file.player.yt.dt.duration") %>";
				 var unknownText = "<%= lang.getContent("file.player.yt.dd.unknown") %>";
				 var skipVideoText = "<%= lang.getContent("file.player.yt.a.skip_video") %>";
				 var watchYtText = "<%= lang.getContent("file.player.yt.a.watch_yt") %>";
				 var openNewWindowText = "<%= lang.getContent("file.player.yt.a.open_new_window") %>";
				 var finishedText = "<%= lang.getContent("file.player.yt.alert.video_finished") %>";
				 var playingText = "<%= lang.getContent("file.player.yt.alert.playing") %>";
				 var pausedText = "<%= lang.getContent("file.player.yt.alert.paused") %>";
				 var loadingText = "<%= lang.getContent("file.player.yt.alert.loading") %>";
				 var soundActivatedText = "<%= lang.getContent("file.player.yt.alert.sound_activated") %>";
				 var soundDeactivatedText = "<%= lang.getContent("file.player.yt.alert.sound_deactivated") %>";
				 var volumeMaxText = "<%= lang.getContent("file.player.yt.alert.volume_max") %>";
				 var volumeAdjustText = "<%= lang.getContent("file.player.yt.alert.volume_adjust") %>";
				 var volumeMinText = "<%= lang.getContent("file.player.yt.alert.volume_min") %>";
				 
				 sInfo = <%= sInfo.toString() %>;
				</script>
				<div class="container-fluid" ng-app="survey" ng-controller="surveyController">
	  				<div class="title-content">	  					
	  					<h2>{{getJsonArrayElement(currentSurvey.info.contents, "contentType", "title").text}}</h2>	  					
	  				</div>	  				
	  				<div class="content">
	  					<form name="survey" role="form" ng-show="currentSurvey.info.section.page.questions">
	  						
	  						<div class="survey-form">	  							
	  							<p>{{getJsonArrayElement(currentSurvey.info.contents, "contentType", "description").text}}</p>
	  							
	  							
	  							<ul>
	  								<li ng-repeat="question in currentSurvey.info.section.page.questions" >
	  									<ng-include src="question.questionJspPath"></ng-include>
	  								</li>
	  							</ul>
	  							
	  						</div>
	  						<div class="btn-submit-page">
	  							<div class="col-md-3">
	  								<button class="btn btn-default btn-submit-page-back" ng-show="currentSurvey.info.section.page.numPage > 1 && showButtonLastPage()" ng-click='nextPage("back")'><%= lang.getContent("button.back") %></button>
	  							</div>
	  							<div class="col-md-6 num-pages"><div ng-show="showButtonLastPage()"><%= lang.getContent("survey.process.pageInfo1") %> {{currentSurvey.info.section.page.numPage}} <%= lang.getContent("survey.process.pageInfo2") %> {{currentSurvey.info.numPages}}</div></div>
	  							<div class="col-md-3">
	  								<button type="submit" class="btn btn-primary btn-submit-page-continue" ng-show="!showStartButton() && showButtonLastPage()" ng-click='nextPage("next")'><%= lang.getContent("button.continue") %></button>
	  								<button type="submit" class="btn btn-primary btn-submit-page-continue" ng-show="showStartButton()" ng-click='nextPage("next")'><%= lang.getContent("button.start") %></button>
	  								
	  							</div>
	  						</div>
	  					</form>
	  					
	  					<div ng-show="currentSurvey.info.isFinishPage">
	  						<ng-include src="'jsp/surveyEngine/bodyPages/bSurveyFinishPage.jsp'"></ng-include>
	  					</div>
	  					
	  				</div>
	  			</div>
	  			
<%
lang.close();
%>
	  			