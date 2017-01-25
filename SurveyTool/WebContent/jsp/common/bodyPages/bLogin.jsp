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
	  				<!-- <div class="title-content">
	  					<h2><%= lang.getContent("home.title") %></h2>	  					
	  				</div>  -->
	  				<div class="login-content">
	  					<div class="col-xs-6 col-sm-7 col-md-6 login-content-text">
	  						<%= lang.getContent("user_panel.description") %>
	  					</div>
  						<div class="col-xs-6 col-sm-5 col-md-4 login-frame" id="loginDivForm">
	  						<form method="post" id="loginForm" action="LoginServlet">
	  							<fieldset>
		  							<!-- <h3>Login</h3> -->
		  							<legend><%= lang.getContent("home.login.title") %></legend>
		  							<%
		  							if(loginResponse != null && !loginResponse.isValid())
		  							{
		  								System.out.println("Login response: " + loginResponse.toString());
		  							%>
		  							<div class="error-msg"><p><%= lang.getContent(loginResponse.getErrorMsg()) %></p></div>
		  							<%
		  							}		  							
		  							%>
		  							<div class="form-group">
		  								<label class="control-label" for="username"> <%= lang.getContent("home.login.label.username") %></label>
			  							<input type="text" id="username" name="<%= Parameter.s_USERNAME %>" class="form-control"/>
			  							<span  id='username-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
			  							<span id='username-error' class='error hidden'><%= lang.getContent("msg.error.login.empty.username") %></span>
			  						</div>
			  						<div class="form-group">
			  							<label class="control-label" for="password"> <%= lang.getContent("home.login.label.password") %></label>
			  							<input type="password" id="password" name="<%= Parameter.s_PASSWORD %>" class="form-control"/>			  							
			  							<span id='password-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
			  							<span id='password-error' class='error hidden'><%= lang.getContent("msg.error.login.empty.password") %></span>
		  							</div>	
		  							<div class="login-button">
		  								<input type="button" id="loginSubmit" class="btn btn-primary btn-block btn-survey" value="<%= lang.getContent("button.login") %>" />
		  							</div>  
		  							
		  							<div class="login-button">
		  								<input type="button" id="registerFormBtn" class="btn btn-block btn-survey" value="<%= lang.getContent("button.register") %>" onclick="showHideRegister(true)"/>
		  							</div>  	
		  						</fieldset>						
	  						</form>
	  					</div>
	  					
	  					<%
						RegisterResponse registerResponse = (RegisterResponse) request.getAttribute(Attribute.s_REGISTER_RESPONSE);
			  								
						%>
	  					<div class="col-xs-6 col-sm-5 col-md-4 register-frame" id="registerDivForm">
	  						<form method="post" id="registerForm" action="RegisterServlet">
	  							<fieldset>
		  							<!-- <h3>Login</h3> -->
		  							<legend><%= lang.getContent("home.register.title") %></legend>
		  							<%
		  							if(registerResponse != null && !registerResponse.isValid())
		  							{
		  							%>
		  							<div class="error-msg"><p><%= lang.getContent(registerResponse.getErrorMsg()) %></p></div>
		  							<%
		  							}		  							
		  							%>
		  							<div class="form-group">
		  								<label class="control-label" for="usernameregister"> <%= lang.getContent("home.register.label.username") %></label>
			  							<input type="text" id="usernameregister" name="<%= Parameter.s_USERNAME %>" class="form-control"/>
			  							<span  id='usernameregister-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
			  							<span id='usernameregister-error' class='error hidden'><%= lang.getContent("msg.error.login.empty.username") %></span>
			  							<span id='usernameregisterlength-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
			  							<span id='usernameregisterlength-error' class='error hidden'><%= lang.getContent("msg.error.login.username.length") %></span>
			  						</div>
		  							<div class="form-group">
		  								<label class="control-label" for="email"> <%= lang.getContent("home.register.label.email") %></label>
			  							<input type="text" id="email" name="<%= Parameter.s_EMAIL %>" class="form-control"/>
			  							<span  id='email-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
			  							<span id='email-error' class='error hidden'><%= lang.getContent("msg.error.login.email") %></span>
			  						</div>
			  						<div class="form-group">
			  							<label class="control-label" for="password"> <%= lang.getContent("home.register.label.password") %></label>
			  							<input type="password" id="passwordregister" name="<%= Parameter.s_PASSWORD %>" class="form-control"/>			  							
			  							<span id='passwordregister-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
			  							<span id='passwordregister-error' class='error hidden'><%= lang.getContent("msg.error.login.empty.password") %></span>
		  							</div>	
		  							<div class="form-group">
			  							<label class="control-label" for="repassword"> <%= lang.getContent("home.register.label.repassword") %></label>
			  							<input type="password" id="repasswordregister" name="<%= Parameter.s_REPASSWORD %>" class="form-control"/>			  							
			  							<span id='repasswordregister-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
			  							<span id='repasswordregister-error' class='error hidden'><%= lang.getContent("msg.error.login.repassword") %></span>
		  							</div>	
		  							<div class="login-button">
		  								<input type="button" id="registerSubmit" class="btn btn-primary btn-block btn-survey" value="<%= lang.getContent("button.register") %>" />
		  							</div>  	
		  							<div class="login-button">
		  								<input type="button" id="backLogin" class="btn btn-block btn-survey" value="<%= lang.getContent("button.login") %>" onclick="showHideRegister(false)"/>
		  							</div>  
		  						</fieldset>						
	  						</form>
	  					</div>
  					</div>	  				
	  			</div>
	  			
	  			
	  			
<%
lang.close();
if(registerResponse!=null){				  									
%>
<script type="text/javascript">
showHideRegister(true);
</script>

<%}%>
