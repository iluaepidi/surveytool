<%@page import="ilu.surveytool.databasemanager.DataObject.UserSurveyResume"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.LoginResponse"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.RegisterResponse"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%
LoginResponse loginResponse = (LoginResponse) request.getAttribute(Attribute.s_LOGIN_RESPONSE);
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));

UserSurveyResume userSurveyResume = (UserSurveyResume) request.getAttribute(Attribute.s_USER_SURVEY_RESUME);
%>
				<div class="container-fluid">
	  				<div class="title-content">
	  					<h2><%= lang.getContent("userpanel.home.title") %></h2>				
	  				</div>
	  				<div class="content">
	  					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi id malesuada ipsum, accumsan aliquam libero. Mauris mauris mauris, vulputate nec neque vehicula, ornare aliquet neque. Phasellus venenatis ligula sit amet ligula tristique suscipit. Etiam viverra elit eu sapien eleifend, nec eleifend lectus finibus.</p>
	  						
	  					<div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1">
	  						<div class="col-sm-9">
	  							<div class="uphome-section-frame">
	  								<h3>My Surveys</h3>
	  								<div class="new-survey-div">
	  									<p><span class="new-survey-span">New!</span> <%= userSurveyResume.getLastSurveyTitel() %></p>
	  								</div>
	  								<div class="survey-resume">
	  									<div class="col-sm-4 col-xs-4 center survey-resume-available">
	  										<span class="survey-resume-counter"><%= userSurveyResume.getAvailableSurveys() %></span>
	  										<span class="survey-resume-label">available surveys</span>
	  									</div>
	  									<div class="col-sm-4 col-xs-4 center survey-resume-completed">
	  										<span class="survey-resume-counter"><%= userSurveyResume.getCompletedSurveys() %></span>
	  										<span class="survey-resume-label">Surveys completed</span>
	  									</div>
	  									<div class="col-sm-4 col-xs-4 center survey-resume-link">	  										
	  										<a href="<%= Address.s_SERVLET_USER_PANEL_MYSURVEYS %>">Wiew all surveys <i class="fa fa-angle-double-right" aria-hidden="true"></i></a>
	  									</div>
	  								</div>
	  							</div>
	  						</div>
	  						
	  						<div class="col-sm-3">
	  							<div class="uphome-section-frame">
	  								<h3>Rewards</h3>
	  								<div>
	  									<p>You have earned <span class="rewards-counter">7.5 €</span></p>
	  								</div>
	  								<div class="rewards-link">
  										<a href="">Manage your rewards <i class="fa fa-angle-double-right" aria-hidden="true"></i></a>
  									</div>
	  							</div>
	  						</div>		
	  						
	  						<div class="col-sm-12">
	  							<div class="uphome-section-frame">
	  								<h3>My Profile</h3>
	  								<div class="basic-profile-resume">
	  									<p>
	  										<span class="basic-profile-resume-label">Basic profile</span> 
	  										<span class="basic-profile-resume-completed">Completed!</span>
	  										<a href="">Edit details <i class="fa fa-angle-double-right" aria-hidden="true"></i></a>
	  									</p>
	  								</div>
	  								<div class="advanced-profile-resume">
	  									<span class="advanced-profile-resume-label">Advanced profile</span> 
	  									<div class="progress">
										  <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
										    <span class="sr-only">60% Complete</span>
										  </div>
										</div>
	  									<span>Complete your full profile and earn extra €</span>	  									
	  								</div>
	  								<div class="myprofile-options">
	  									<div class="myprofile-option">
	  										<a href="">
		  										<i class="fa fa-graduation-cap fa-2x" aria-hidden="true"></i>	  										
		  										Education
	  										</a>
	  									</div>
	  									<div class="myprofile-option">
	  										<a href="">
		  										<i class="fa fa-laptop fa-2x" aria-hidden="true"></i>	  										
		  										Technology
	  										</a>
	  									</div>
	  									<div class="myprofile-option">
	  										<a href="">
		  										<i class="fa fa-medkit fa-2x" aria-hidden="true"></i>	  										
		  										Health
	  										</a>
	  									</div>
	  									<div class="myprofile-option">
	  										<a href="">
		  										<i class="fa fa-universal-access fa-2x" aria-hidden="true"></i>	  										
		  										Accessibility & Disability
	  										</a>
	  									</div>
	  								</div>	  								
	  								<div class="myprofile-link">
  										<a href="">View full profile <i class="fa fa-angle-double-right" aria-hidden="true"></i></a>
  									</div>
	  							</div>
	  						</div>		
	  					</div>  						
  					</div>	  				
	  			</div>
	  			
	  			
<%
lang.close();				  									
%>
