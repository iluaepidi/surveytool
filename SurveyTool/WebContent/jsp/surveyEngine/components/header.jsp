<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.LoginResponse"%>

<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
				
				
				<div class="container-fluid">
	  				<h1>
	  					<div class="logo"><a href="InitialServlet"><img src="img/p4aicon.jpeg" alt="logo de prosperity4all"/></a></div>
	  					<div class="title-header"><%= lang.getContent("master.header.title") %>
	  						<p><%= lang.getContent("master.header.subtitle") %></p>
	  					</div>
	  				</h1>
	  				
  				</div>
<%
lang.close();
%>