<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.LoginResponse"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%
LoginResponse loginResponse = (LoginResponse) request.getAttribute(Attribute.s_LOGIN_RESPONSE);
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage("en");
%>
				<div class="container-fluid">
	  				<div class="title-content">
	  					<h2><%= lang.getContent("home.title") %></h2>	  					
	  				</div>
	  				<div class="login-content">
  						<div class="login-frame">
	  						<form method="post" action="LoginServlet">
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
		  								<label for="username"> <%= lang.getContent("home.login.label.username") %>
			  								<input type="text" id="username" name="<%= Parameter.s_USERNAME %>" class="form-control"/>
			  							</label>
			  						</div>
			  						<div class="form-group">
			  							<label for="password"> <%= lang.getContent("home.login.label.password") %>
			  								<input type="password" id="username" name="<%= Parameter.s_PASSWORD %>" class="form-control"/>
			  							</label>
		  							</div>	
		  							<div class="right">
		  								<input type="submit" class="btn btn-primary btn-survey" value="<%= lang.getContent("button.send") %>" />
		  							</div>  	
		  						</fieldset>						
	  						</form>
	  					</div>
  					</div>	  				
	  			</div>
<%
lang.close();
%>