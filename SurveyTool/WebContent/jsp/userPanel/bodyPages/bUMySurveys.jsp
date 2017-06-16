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
%>
				<div class="container-fluid">
	  				<div class="title-content">
	  					<h2><%= lang.getContent("userpanel.mysurveys.title") %></h2>				
	  				</div>
	  				<div class="content">
	  					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi id malesuada ipsum, accumsan aliquam libero. Mauris mauris mauris, vulputate nec neque vehicula, ornare aliquet neque. Phasellus venenatis ligula sit amet ligula tristique suscipit. Etiam viverra elit eu sapien eleifend, nec eleifend lectus finibus.</p>
	  						
	  					<jsp:include page="../components/cSurveysList.jsp" />  						
  					</div>	  				
	  			</div>
	  			
	  			
<%
lang.close();				  									
%>
