<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.LoginResponse"%>

<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
				
				<script>
				function alertLogOut(){
				    bootbox.dialog({
						message: "<%= lang.getContent("header.log.out.message") %>",
						title: "<%= lang.getContent("header.log.out") %>",
						buttons: {
						success: {
						label: "<%= lang.getContent("header.log.out.yes") %>",
						className: "btn-success",
							callback: function() {
							   window.location.href = "InitialServlet?logout=true&site=manager";
							
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
	  				<h1 class="col-sm-9 col-xs-9">
	  					<span class="logo inBlock"><a href="InitialServlet"><img src="img/p4aicon.jpeg" alt="logo de prosperity4all"/></a></span>
	  					<span class="title-header inBlock"><%= lang.getContent("master.header.title") %>
	  						<span><%= lang.getContent("master.header.subtitle") %></span>
	  					</span>
	  				</h1>
	  				<!-- <div class="header-buttons">
	  					<a href="#"><i class="fa fa-cog fa-5x"></i></a>
	  					<a href="#"><i class="fa fa-user fa-5x"></i></a>	  					
	  				</div> -->
	  				
	  				<% HttpSession sessions = request.getSession(false); 
	  				LoginResponse loginResp = (LoginResponse)sessions.getAttribute(Attribute.s_USER_SESSION_INFO);
	  				if(loginResp!=null){ %>
	  				<ul class="col-sm-2 col-sm-offset-1 col-xs-3 nopadding nomargin nav navbar-nav navbar-right user-profile-list">
			            <li class="dropdown">
			                <a href="#" class="dropdown-toggle userZone btn-icon-text" data-toggle="dropdown" aria-expanded="false"><i class="glyphicon glyphicon-user" aria-hidden="true"></i><%= lang.getContent("header.hello") %>, <%=loginResp.getUserName()  %> <b class="caret"></b></a>
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