<%@page import="ilu.surveytool.language.Language"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage("en");
%>
				
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
  				</div>
<%
lang.close();
%>