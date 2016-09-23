					
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
				Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO);
				
				Language lang = (Language) request.getAttribute(Attribute.s_SURVEY_LANGUAGE);
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
				</script>
				<div class="container-fluid">
	  				<div class="title-content">
	  					<%
	  					String surveyTitle = "";
	  					if(survey.getContents() != null && !survey.getContents().isEmpty() && survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE) != null) surveyTitle = survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
	  					%>
	  					<h2><%= surveyTitle %></h2>	  					
	  				</div>	  				
	  				<div class="content">
	  					<form action="<%= Address.s_SERVLET_SURVEY_PROCESS %>" method="POST">
	  						<input type="hidden" name="sid" value="<%= survey.getSurveyId() %>" />
	  						<input type="hidden" name="secid" value="<%= survey.getSections().get(0).getSectionId() %>" />
	  						<input type="hidden" name="numPag" value="<%= survey.getSections().get(0).getPages().get(0).getNumPage() %>" />
	  						<div class="survey-form" >
	  							<%
	  							String surveyDesc = "";
	  		  					if(survey.getContents() != null && !survey.getContents().isEmpty() && survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION) != null) surveyDesc = survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText();
	  							%>
	  							<p><%= surveyDesc %></p>
	  							
	  							<% 
	  							String token = "/";
	  							int index = 1;
	  							for(Section section : survey.getSections())
	  							{
	  								for(Page pag : section.getPages())
	  								{
		  								for(Question question : pag.getQuestions())
		  								{
		  									request.setAttribute(Attribute.s_QUESTION, question);
	  							%>	  							
	  								<jsp:include page="<%= token + question.getFormPage() %>">
	  									<jsp:param name="index" value="<%= index %>" />
	  								</jsp:include>
	  							<%
	  										index++;
		  								}
	  								}
	  							}
	  							%>
	  						</div>
	  						<div class="center">
	  							<input type="submit" class="btn btn-primary btn-submit-page" Value="<%= lang.getContent("button.submit") %>"/>
	  						</div>
	  					</form>
	  				</div>
	  			</div>
	  			
<%
lang.close();
%>
	  			