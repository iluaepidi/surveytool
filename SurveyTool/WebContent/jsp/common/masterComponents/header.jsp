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
	  				<h1>
	  					<div class="logo"><a href="InitialServlet"><img src="img/p4aicon.jpeg" alt="logo de prosperity4all"/></a></div>
	  					<div class="title-header"><%= lang.getContent("master.header.title") %>
	  						<p><%= lang.getContent("master.header.subtitle") %></p>
	  					</div>
	  				</h1>
	  				<div class="header-buttons">
	  					<!-- <a href="#"><i class="fa fa-cog fa-5x"></i></a>
	  					<a href="#"><i class="fa fa-user fa-5x"></i></a> -->	  					
	  				</div>
	  				
	  				<% HttpSession sessions = request.getSession(false); 
	  				LoginResponse loginResp = (LoginResponse)sessions.getAttribute(Attribute.s_USER_SESSION_INFO);
	  				if(loginResp!=null){ %>
	  				<ul class="nav navbar-nav navbar-right">
			            <li class="dropdown">
			                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;&nbsp;<%= lang.getContent("header.hello") %>, <%=loginResp.getUserName()  %> <b class="caret"></b></a>
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