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
	  					<h2><%= lang.getContent("userpanel.registration.conditions.title") %></h2>	  					
	  				</div>
	  				<div class="login-content">
	  					<div class="col-xs-12 col-sm-12 col-md-10 login-content-text">
	  						<form method="post" id="conditionsForm" action="<%= Address.s_SERVLET_LOGIN_USER_PANEL %>">
		  						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
		  						
		  						<p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p>
		  						
		  						<ul>
		  							<li>Condition</li>
		  							<li>Condition</li>
		  							<li>Condition</li>
		  							<li>Condition</li>
		  							<li>Condition</li>
		  							<li>Condition</li>
		  						</ul>
		  						
		  						<div class="checkbox">
								    <label for="acceptConditions">
								    	<input name="acceptedConditions" type="checkbox" id="acceptConditions"> <%= lang.getContent("userpanel.registration.conditions.checkbox.label") %>								    	
								    </label>
								</div>  	
								
								<div class="center">
									<input type="button" id="registrateConditions" class="btn btn-primary btn-lg" value="<%= lang.getContent("userpanel.registration.conditions.button") %>">
								</div>	
							</form>				
	  					</div>
  						
  					</div>	  				
	  			</div>
	  			
	  			<div class="modal fade conditions-win" id="notAcceptConditions" tabindex="-1" role="dialog" aria-labelledby="notAcceptConditionsTitle">
					<div class="modal-dialog conditions-win-dialog" role="document">
				    	<div class="panel panel-primary"> 
				    		<div class="panel-heading">
				        		<button type="button" class="close" data-dismiss="modal" aria-label="<%= lang.getContent("notAcceptConditions.window.button.close") %>"><span aria-hidden="true">&times;</span></button>
				        		<h2 id="notAcceptConditionsTitle" class="modal-title"><%= lang.getContent("notAcceptConditions.window.title") %></h2>
				      		</div>
				      		<div class="modal-body">
				        		<p><%= lang.getContent("notAcceptConditions.window.content") %></p>
				      		</div>
				      		<div class="modal-footer">
				        		<button type="button" class="btn btn-primary" data-dismiss="modal"><%= lang.getContent("notAcceptConditions.window.button") %></button>
				      		</div>
				    	</div><!-- /.modal-content -->
				  	</div><!-- /.modal-dialog -->
				</div><!-- /.modal -->
	  			
	  			
	  			
<%
lang.close();				  									
%>
