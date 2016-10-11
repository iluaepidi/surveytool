<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.language.Language"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
<div class="container-fluid">
	  				<div class="title-content">
	  					<h2><%= lang.getContent("user_panel.title") %></h2>
	  				</div>
	  				
	  				<div class="content">
	  					<%= lang.getContent("user_panel.description") %>
	  					<div class="user-panel-menu">
		  					<ul class="row row-menu">
		  						<li class="col-md-3 center"><a href="<%= Address.s_SERVLET_USER_PANEL_HOME + "?" + Parameter.s_UPOPTION + "=" + Address.s_BODY_SURVEYS %>"><i class="fa fa-list-alt fa-5x"></i><div><%= lang.getContent("user_panel.link.survey") %></div></a></li>
		  						<li class="col-md-3 center"><i class="fa fa-users fa-5x" aria-label="User list disabled"></i><p><%= lang.getContent("user_panel.link.users") %></p></li>
		  						<li class="col-md-3 center"><i class="fa fa-area-chart fa-5x" aria-label="Panel statistics disabled"></i><p><%= lang.getContent("user_panel.link.statistics") %></p></li>
		  						<li class="col-md-3 center"><i class="fa fa-cogs fa-5x" aria-label="Panel settings disabled"></i><p><%= lang.getContent("user_panel.link.settings") %></p></li>
		  						<!-- <li class="col-md-3 center"><a href="#"><i class="fa fa-users fa-5x"></i><div>User list</div></a></li>
		  						<li class="col-md-3 center"><a href="#"><i class="fa fa-area-chart fa-5x"></i><div>Panel statistics</div></a></li>
		  						<li class="col-md-3 center"><a href="#"><i class="fa fa-cogs fa-5x"></i><div>Panel settings</div></a></li> -->
		  					</ul>
		  				</div>
	  				</div>
	  			</div>
<%
lang.close();
%>