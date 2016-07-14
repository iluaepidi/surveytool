				<%@page import="ilu.surveytool.language.Language"%>
				<%@page import="ilu.surveytool.constants.Attribute"%>
				<%@page import="ilu.surveytool.databasemanager.DataObject.LoginResponse"%>
				<%@page import="ilu.surveytool.constants.Parameter"%>
				<%@page import="java.util.HashMap"%>
				<%@page import="java.util.Map"%>
				<%
				String pid = (String) request.getAttribute(Attribute.s_POLL_ID);
				
				Language lang = new Language(getServletContext().getRealPath("/")); 
				lang.loadLanguage(Language.getLanguageRequest(request));
				
				HttpSession sessions = request.getSession(false); 
  				LoginResponse loginResp = (LoginResponse)sessions.getAttribute(Attribute.s_USER_SESSION_INFO);
  				
  				String confMsg = (String)request.getAttribute("ConformationUpdateProfile");
  				if(confMsg!=null){
				%>		
				
				<script type="text/javascript">
					function alertUpdateProfile(){
					    bootbox.dialog({
							message: "<%= lang.getContent("profile.confirm.update.msg") %>",
							title: "<%= lang.getContent("profile.confirm.update.title") %>",
							buttons: {
							success: {
							label: "<%= lang.getContent("profile.button.accept") %>",
							className: "btn-success",
								callback: function() {
								   window.location.href = "InitialServlet";
								
								}
							}}
						});
					}
					
					alertUpdateProfile();
				</script>
				<% } %>
				
				
				<div class="container-fluid">
					<div class="title-content">
		  					<h2><%= lang.getContent("profile.title") %></h2>
		  			</div>
				<div class="content">
				<form method="post" id="profileForm" action="ProfileServlet">
					<input type="hidden" name="savesubmitvalue" value="yes" />
					<fieldset>

			            <!-- Text input-->
			            <div class="form-group">
			                <label class="col-md-4 control-label" for="usernameprofile" style="width: 33%;text-align: right;"><%= lang.getContent("profile.username") %></label>  
			                <div class="col-md-4">
			                    <input id="usernameprofile" name="<%= Parameter.s_USERNAME %>" type="text" placeholder="" readonly="yes" class="form-control input-md" value="<%=loginResp.getUserName() %>">
			                </div>
			            </div>
			            
			              <!-- Textarea -->
			            <div class="form-group" style="margin-top: 50px;">
			                <label class="col-md-4 control-label" for="descripcion" style="width: 33%;text-align: right;"><%= lang.getContent("profile.email") %></label>
			                <div class="col-md-4"> 
			                	<div class="form-group">                    
			                    	<input id="email" name="<%= Parameter.s_EMAIL %>" type="text" placeholder="" class="form-control" value="<%=loginResp.getEmail() %>">
			                		<span id='email-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style="margin-left: -30px;"></span>
			  						<span id='email-error' class='error-profile hidden'><%= lang.getContent("msg.error.login.email") %></span>
			  					</div>
			                </div>
			                
			            </div>
			
			            <!-- Textarea -->
			            <div class="form-group" style="margin-top: 100px;">
			                <label class="col-md-4 control-label" for="descripcion" style="width: 33%;text-align: right;"><%= lang.getContent("profile.password") %></label>
			                <div class="col-md-4">
			                	<div class="form-group">                      
				                    <input id="password" name="<%= Parameter.s_PASSWORD %>" type="password" placeholder="" class="form-control input-md" value="<%=loginResp.getPassword() %>">
				                    <span id='password-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
				  					<span id='password-error' class='error-profile hidden'><%= lang.getContent("msg.error.login.empty.password") %></span>
				  				</div>
			                </div>
			            </div>
			            
			           <div class="form-group" style="margin-top: 150px;">
			                <label class="col-md-4 control-label" for="descripcion" style="width: 33%;text-align: right;"><%= lang.getContent("profile.repeat.password") %></label>
			                <div class="col-md-4">     
			                	<div class="form-group">                   
			                    	<input id="repassword" name="<%= Parameter.s_REPASSWORD %>" type="password" placeholder="" class="form-control input-md" value="<%=loginResp.getPassword() %>">
			                    	<span id='repassword-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
			  						<span id='repassword-error' class='error-profile hidden'><%= lang.getContent("msg.error.login.repassword") %></span>
			                	</div>
			                </div>
			            </div>
			                
			           <div class="form-group" style="margin-top: 200px;">
			                <label class="col-md-4 control-label" for="language" style="width: 33%;text-align: right;"><%= lang.getContent("profile.select.language") %></label>
			                <div class="col-md-4">     
			                	<div class="form-group">                   
			                    	<select id="language" name="language" class="form-control">
			                    	<%
			                    	for (Map.Entry<String, String> entry : loginResp.getListLanguage().entrySet()) {%>
			                    		<option value="<%=entry.getKey() %>"><%=entry.getValue() %></option>
			                    		
			                    	<%}%>
			                    	</select>
			                	</div>
			                </div>
			            </div>
			            <script type="text/javascript">
			            	$("#language").val('<%=loginResp.getIsoLanguage() %>');
			            </script>
			                
			           
			            <div class="form-group" style="margin-top: 200px;">
			                <label class="col-md-4 control-label" for="guardar" style="width: 36%;"></label>
			                <div class="col-md-4">
			                    <button type="button" id="cancelprofile" name="cancelprofile" class="btn btn-default" style="width: 150px;"><%= lang.getContent("profile.cancel") %></button>
			                    <button id="savesubmit" name="savesubmit" class="btn btn-primary" style="width: 150px;"><%= lang.getContent("profile.save") %></button>
			                 </div>
			            </div>
			
			        </fieldset>
			        </form>
			        </div>
	  			</div>
	  			
				<%
				lang.close();
				%>
	  			