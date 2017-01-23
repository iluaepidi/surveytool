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
	  					<h2><%= lang.getContent("userpanel.registration.confirmation.title") %></h2>	  					
	  				</div>
	  				<div class="login-content confirmation-back-login">
	  					<div class="col-xs-12 col-sm-12 col-md-10 login-content-text">
		  						<p><%= lang.getContent("userpanel.registration.confirmation.content1") %></p>
		  						
		  						<p><%= lang.getContent("userpanel.registration.confirmation.content2") %></p>
		  						
								<div class="center">
									<a href="/SurveyTool" class="btn btn-primary btn-lg"><%= lang.getContent("userpanel.registration.confirmation.button") %></a>
								</div>			
	  					</div>
  						
  					</div>	  				
	  			</div>
	  			
<%
lang.close();				  									
%>
