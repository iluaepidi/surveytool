<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.LoginResponse"%>

<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
				
				<script type="text/javascript">
				function alertLogOut(){
				    bootbox.dialog({
						message: "<%= lang.getContent("header.log.out.message") %>",
						title: "<%= lang.getContent("header.log.out") %>",
						buttons: {
						success: {
						label: "<%= lang.getContent("header.log.out.yes") %>",
						className: "btn-success",
							callback: function() {
							   window.location.href = "InitialServlet?logout=true";
							
							}
						},
							danger: {
								label: "<%= lang.getContent("header.log.out.no") %>",
								className: "btn-danger",
								callback: function() {
								}
							}
						}
					});
				}
</script>
				<div class="container-fluid">
	  				<h1 class="col-sm-6 col-xs-12">
	  					<div class="title-header"><%= lang.getContent("userpanel.master.header.title") %></div>
	  				</h1>
	  				<div class="col-sm-3 col-xs-12">
  						<div class="myprofile-option">
							<a href="">
								<i class="fa fa-wpforms fa-2x" aria-hidden="true"></i>	  										
								My surveys
							</a>
						</div>
						<div class="myprofile-option">
							<a href="">
								<i class="fa fa-money fa-2x" aria-hidden="true"></i>	  										
								Rewards
							</a>
						</div>
						<div class="myprofile-option">
							<a href="">
								<i class="fa fa-address-card-o fa-2x" aria-hidden="true"></i>	  										
								My profile
							</a>
						</div>	  					
	  				</div>
	  				
	  				<% HttpSession sessions = request.getSession(false); 
	  				LoginResponse loginResp = (LoginResponse)sessions.getAttribute(Attribute.s_USER_SESSION_INFO);
	  				if(loginResp!=null){ %>
	  				<ul class="col-sm-3 col-xs-12 nopadding nomargin nav navbar-nav navbar-right user-profile-list">
			            <li class="dropdown">
			                <a href="#" class="dropdown-toggle userZone btn-icon-text up-user-zone" data-toggle="dropdown" aria-expanded="false"><i class="glyphicon glyphicon-user" aria-hidden="true"></i><%= lang.getContent("header.hello") %>, <%=loginResp.getEmail()  %> <b class="caret"></b></a>
			                <ul class="dropdown-menu">
			                    <li><a href="ProfileServlet?param=1"><%= lang.getContent("header.edit.profile") %></a></li>
			                    <li class="divider"></li>
			                    <li><a href="#" onclick="alertLogOut()"><%= lang.getContent("header.log.out") %></a></li>
			                </ul>
			            </li>
			        </ul>
			        <% } %>
  				</div>
<%
lang.close();
%>