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
RegisterResponse registerResponse = (RegisterResponse) request.getAttribute(Attribute.s_REGISTER_RESPONSE);
%>
				<div class="container-fluid">
	  				<div class="title-content">
	  					<h2><%= lang.getContent("userpanel.registration.conditions.title") %></h2>	  					
	  				</div>
	  				<div class="login-content">
	  					<div class="col-xs-12 col-sm-12 col-md-12 login-content-text">
	  					
	  						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
	  						
	  						<p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p>
	  						
	  						<div class="col-xs-12 col-sm-12" id="registerDivForm">
		  						<form method="post" id="registerForm" action="<%= Address.s_SERVLET_REGISTER_USER_PANEL %>">
		  							<fieldset>
			  							<legend class="visuallyhidden"><%= lang.getContent("home.register.title") %></legend>
			  							<%
			  							if(registerResponse != null && !registerResponse.isValid())
			  							{
			  							%>
			  							<div class="error-msg"><p><%= lang.getContent(registerResponse.getErrorMsg()) %></p></div>
			  							<%
			  							}		  							
			  							%>
			  							<div class="horizontal-form-group-controls">
				  							<div class="col-sm-6">
				  								<label class="col-sm-5 control-label left" for="email"> <%= lang.getContent("home.register.label.email") %></label>
				  								<div class="col-sm-7 no-padding">
						  							<%
						  							String email = "";
						  							if(registerResponse != null)
						  							{
						  								email = registerResponse.getEmail();
						  							}		  							
						  							%>
						  							<input type="email" id="email" name="<%= Parameter.s_EMAIL %>" class="form-control" value="<%= email %>"/>
						  							<span  id='email-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
						  							<span id='email-error' class='herror hidden'><%= lang.getContent("msg.error.login.email") %></span>
						  						</div>
					  						</div>
					  						<div class="col-sm-6">
				  								<label class="col-sm-5 control-label left" for="reemail"> <%= lang.getContent("home.register.label.reemail") %></label>
				  								<div class="col-sm-7 no-padding">
				  									<%
						  							String reemail = "";
						  							if(registerResponse != null)
						  							{
						  								reemail = registerResponse.getReemail();
						  							}		  							
						  							%>
						  							<input type="email" id="reemail" name="<%= Parameter.s_REEMAIL %>" class="form-control" value="<%= reemail %>"/>
						  							<span  id='reemail-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
						  							<span id='reemail-error' class='herror hidden'><%= lang.getContent("msg.error.login.reemail") %></span>
						  						</div>
					  						</div>
				  						</div>
				  						<div class="horizontal-form-group-controls">
				  							<div class="col-sm-6">
					  							<label class="col-sm-5 control-label left" for="password"> <%= lang.getContent("home.register.label.password") %></label>
					  							<div class="col-sm-7 no-padding">
						  							<input type="password" id="passwordregister" name="<%= Parameter.s_PASSWORD %>" class="form-control"/>			  							
						  							<span id='passwordregister-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
						  							<span id='passwordregister-error' class='herror hidden'><%= lang.getContent("msg.error.login.empty.password") %></span>
						  						</div>
				  							</div>	
				  							<div class="col-sm-6">
					  							<label class="col-sm-5 control-label left" for="repassword"> <%= lang.getContent("home.register.label.repassword") %></label>
					  							<div class="col-sm-7 no-padding">
						  							<input type="password" id="repasswordregister" name="<%= Parameter.s_REPASSWORD %>" class="form-control"/>			  							
						  							<span id='repasswordregister-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
						  							<span id='repasswordregister-error' class='herror hidden'><%= lang.getContent("msg.error.login.repassword") %></span>
						  						</div>
				  							</div>	
				  						</div>
				  						<!-- PENDIENTE DE DECIDIR 
				  						<div class="horizontal-form-group-controls">
				  							<div class="col-sm-6">
					  							<label class="col-sm-5 control-label left" for="password"> <%= lang.getContent("home.register.label.password") %></label>
					  							<div class="col-sm-7">
						  							<input type="date" id="passwordregister" name="<%= Parameter.s_PASSWORD %>" class="form-control"/>			  							
						  							<span id='passwordregister-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
						  							<span id='passwordregister-error' class='error hidden'><%= lang.getContent("msg.error.login.empty.password") %></span>
						  						</div>
				  							</div>	
				  						</div>
				  						<div class="horizontal-form-group-controls">
				  							<formfield class="col-sm-6 horizontal-formfield">
					  							<legend class="col-sm-5 control-label left" for="password"> <%= lang.getContent("home.register.label.password") %></legend>
					  							<div class="col-sm-7">
					  								<div class="col-sm-6">
							  							<label>
														    <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
														    Female
														</label>		  							
							  							<span id='passwordregister-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
							  							<span id='passwordregister-error' class='error hidden'><%= lang.getContent("msg.error.login.empty.password") %></span>
						  							</div>
					  								<div class="col-sm-6">
							  							<label>
														    <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
														    Male
														</label>			  							
							  							<span id='passwordregister-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
							  							<span id='passwordregister-error' class='error hidden'><%= lang.getContent("msg.error.login.empty.password") %></span>
						  							</div>
						  						</div>
				  							</formfield>	
				  						</div> -->
			  							<div class="account-register-button-div">
			  								<input type="button" id="accountSubmit" class="btn btn-primary btn-lg" value="<%= lang.getContent("button.register") %>" />
			  							</div>  
			  						</fieldset>						
		  						</form>
		  					</div>											
	  					</div>
  						
  					</div>	  				
	  			</div>  			
	  			
	  			
<%
lang.close();				  									
%>
