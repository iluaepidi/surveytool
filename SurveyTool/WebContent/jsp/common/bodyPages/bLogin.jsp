<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.LoginResponse"%>
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
	  					<div class="login-content-text">
	  						<p>This service offers a suite of accessible tools for improving consumer-developers connections. Current version includes:</p>
	  						<ul>
	  							<li>An <strong>online survey tool</strong> for building accessible forms. Developers can create and share accessible surveys to collect consumers information on the context of use, validate product ideas of evaluate low-fi prototypes.</li>
	  							<li>An <strong>online polling tool</strong> allowing accessible electronic voting. Polls are an appealing way of collecting users’ feedback on existing or future products. For example, polls can be used as a "future feature vote" mechanism for gathering new ideas from the end-users. Accessible polls can be embedded in blogs, websites or product pages using iframes, or share through a URL.</li>
	  						</ul>
	  						<p>Future versions of the accessible user feedback tool will include:</p>
	  						<ul>
	  							<li><strong>User reviews widget.</strong> A widget allowing for user star-rating and comments to provide feedback to developers and vendors on their products and services. This is also an accessible mechanism allowing consumers to exchange information among themselves about accessible and/or assistive products and services.</li>
	  							<li><strong>Accessible user panel management.</strong> A system providing a dashboard for managing user database. This may be linked to other user feedback tools to foster and manage user participation.</li>
	  						</ul>
	  					</div>
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
		  						</fieldset>						
	  						</form>
	  					</div>
  					</div>	  				
	  			</div>
<%
lang.close();
%>