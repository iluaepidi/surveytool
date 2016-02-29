<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.LoginResponse"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%
LoginResponse loginResponse = (LoginResponse) request.getAttribute(Attribute.s_LOGIN_RESPONSE);
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
				<div class="container-fluid">
	  				<div class="title-content">
	  					<h2><%= lang.getContent("home.title") %></h2>	  					
	  				</div>
	  				<div class="login-content">
  						<div class="login-frame">
	  						<form method="post" id="loginForm" action="LoginServlet">
	  							<fieldset>
		  							<!-- <h3>Login</h3> -->
		  							<legend><%= lang.getContent("home.login.title") %></legend>
		  							<%
		  							if(loginResponse != null && !loginResponse.isValid())
		  							{
		  							%>
		  							<div class="error-msg"><p><%= loginResponse.getErrorMsg() %></p></div>
		  							<%
		  							}		  							
		  							%>
		  							<div class="form-group">
		  								<label class="control-label" for="username"> <%= lang.getContent("home.login.label.username") %></label>
			  							<input type="text" id="username" name="<%= Parameter.s_USERNAME %>" class="form-control"/>
			  							<span class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>
			  							<span id='username-error' class='error hidden'><%= lang.getContent("msg.error.login.empty.username") %></span>
			  						</div>
			  						<div class="form-group">
			  							<label class="control-label" for="password"> <%= lang.getContent("home.login.label.password") %></label>
			  							<input type="password" id="password" name="<%= Parameter.s_PASSWORD %>" class="form-control"/>			  							
			  							<span class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>
			  							<span id='password-error' class='error hidden'><%= lang.getContent("msg.error.login.empty.password") %></span>
		  							</div>	
		  							<div class="right">
		  								<input type="button" id="loginSubmit" class="btn btn-primary btn-survey" value="<%= lang.getContent("button.send") %>" />
		  							</div>  	
		  						</fieldset>						
	  						</form>
	  					</div>
  					</div>	  				
	  			</div>
<%
lang.close();
%>