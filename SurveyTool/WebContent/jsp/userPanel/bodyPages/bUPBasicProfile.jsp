<%@page import="java.sql.Date"%>
<%@page import="java.util.Calendar"%>
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
	  					<h2><%= lang.getContent("userpanel.basicprofile.title") %></h2>				
	  				</div>
	  				<div class="content">
	  					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi id malesuada ipsum, accumsan aliquam libero. Mauris mauris mauris, vulputate nec neque vehicula, ornare aliquet neque. Phasellus venenatis ligula sit amet ligula tristique suscipit. Etiam viverra elit eu sapien eleifend, nec eleifend lectus finibus.</p>
	  						
	  					<form class="form-horizontal" id="basicProfile" method="post" action="<%= Address.s_SERVLET_USER_PANEL_BASIC_PROFILE %>">
							<div class="form-group-horizontal">
						    	<label for="firstName" class="col-sm-2 col-sm-offset-2 col-md-2 col-md-offset-2 col-lg-offset-3 control-label"><%= lang.getContent("userpanel.basicprofile.form.firstname.label") %></label>
						    	<div class="col-sm-6 col-lg-4">
						      		<input type="text" class="form-control" name="firstName" id="firstName" placeholder="<%= lang.getContent("userpanel.basicprofile.form.firstname.placeholder") %>">
						      		<div class="horizontal-error" role="status" aria-live="assertive">
							      		<span  id='firstName-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
					  					<span id='firstName-error' class='error hidden'><%= lang.getContent("msg.error.login.empty.username") %></span>
					  				</div>
						    	</div>
						  	</div> 
						  	 
							<div class="form-group-horizontal">
						    	<label for="lastName" class="col-sm-2 col-sm-offset-2 col-md-2 col-md-offset-2 col-lg-offset-3 control-label"><%= lang.getContent("userpanel.basicprofile.form.lastname.label") %></label>
						    	<div class="col-sm-6 col-lg-4">
						      		<input type="text" class="form-control" name="lastName" id="lastName" placeholder="<%= lang.getContent("userpanel.basicprofile.form.lastname.placeholder") %>">
						      		<div class="horizontal-error" role="status" aria-live="assertive">
							      		<span  id='lastName-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
					  					<span id='lastName-error' class='error hidden'><%= lang.getContent("msg.error.login.empty.username") %></span>
					  				</div>
						    	</div>
						  	</div>
						  	
						  	<div class="form-group-horizontal">
						  		<fieldset>
					        		<legend class="col-sm-2 col-sm-offset-2 col-md-2 col-md-offset-2 col-lg-offset-3 col-xs-3 col-lg-offset-3 birthdate-legend"><%= lang.getContent("userpanel.basicprofile.form.birthdate.legend") %></legend>
					        		<div class="col-sm-6 col-lg-4 col-xs-9">
					        			<div id="birthdate">
									        <div class="col-sm-4 col-lg-4 col-xs-4">
									        	<label for="birthDay" class="control-label"><%= lang.getContent("userpanel.basicprofile.form.birthdate.select.day.label") %></label>								        	
									        	<select id="birthDay" class="form-control" name="day" autocomplete="off">
									        		<option value="none" selected><%= lang.getContent("userpanel.basicprofile.form.birthdate.select.day.default") %></option>
									        	<%for(int i = 1; i < 32; i++){ %>
												  	<option value="<%= i %>"><%= i %></option>
												<%} %>
												</select>											
									        </div>
									        <div class="col-sm-4 col-lg-4 col-xs-4">
									        	<label for="birthMonth" class="control-label"><%= lang.getContent("userpanel.basicprofile.form.birthdate.select.month.label") %></label>
									        	<select id="birthMonth" class="form-control" name="month" autocomplete="off">
									        		<option value="none" selected><%= lang.getContent("userpanel.basicprofile.form.birthdate.select.month.default") %></option>
												  <%for(int i = 1; i < 13; i++){ %>
												  	<option value="<%= i %>"><%= i %></option>
												<%} %>
												</select>											
									        </div>
									        <div class="col-sm-4 col-lg-4 col-xs-4">
									        	<label for="birthYear" class="control-label"><%= lang.getContent("userpanel.basicprofile.form.birthdate.select.year.label") %></label>
									        	<select id="birthYear" class="form-control" name="year" autocomplete="off">
									        		<option value="none" selected><%= lang.getContent("userpanel.basicprofile.form.birthdate.select.year.default") %></option>
												   <% Calendar cal = Calendar.getInstance();
													  for(int i = cal.get(Calendar.YEAR); i > 1900; i--){ %>
													<option value="<%= i %>"><%= i %></option>
													<%} %>
												</select>
									        </div>
									    </div>
								        <div class="col-sm-12 horizontal-birthdate-error" role="status" aria-live="assertive">
						  					<span id='birthdate-error' class='error hidden'><%= lang.getContent("msg.error.basicprofile.dateformat") %></span>
						  				</div>
									</div>	
								</fieldset>
					        </div> 	
					        
					        <div class="form-group-horizontal">
					        	<fieldset>
					        		<legend class="col-sm-2 col-sm-offset-2 col-md-2 col-md-offset-2 col-lg-offset-3 col-xs-3"><%= lang.getContent("userpanel.basicprofile.form.gender.legend") %></legend>
					        		<div class="col-sm-6 col-lg-4 col-xs-9">
								        <label class="col-sm-4 col-xs-4 radio-inline">
										  <input type="radio" name="gender" id="inlineRadio1" value="female"> <%= lang.getContent("userpanel.basicprofile.form.gender.radio.female.label") %>
										</label>
										<label class="col-sm-4 col-xs-4 radio-inline">
										  <input type="radio" name="gender" id="inlineRadio2" value="male"> <%= lang.getContent("userpanel.basicprofile.form.gender.radio.male.label") %>
										</label>
									</div>									
						      		<span  id='firstName-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
				  					<span id='firstName-error' class='error hidden'><%= lang.getContent("msg.error.login.empty.username") %></span>
								</fieldset>
							</div>
							
							<div class="form-group-horizontal">
								<div class="col-sm-8 col-sm-offset-2 col-md-8 col-md-offset-2 col-lg-offset-3">							
									<div  class="col-sm-4 col-xs-4 center skip-button">
										<a href="<%= Address.s_SERVLET_USER_PANEL_BASIC_PROFILE + "?skip" %>" class="btn"><%= lang.getContent("userpanel.basicprofile.form.button.skip") %></a>
									</div>
									<div  class="col-sm-4 col-xs-4 center">
										<input type="submit" value="<%= lang.getContent("userpanel.basicprofile.form.button.done") %>" class="btn btn-primary">
									</div>
								</div>
							</div>
						</form>					
  					</div>	  				
	  			</div>
	  	
<%
lang.close();				  									
%>
