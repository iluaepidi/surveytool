<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.LoginResponse"%>

<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
<!--  <header>
	<h1 class="col-sm-9 col-xs-9">
		<span class="logo inBlock"><img src="img/axsurvey.png" alt="axsurvey"/></span>
	</h1>
</header> -->
<%
lang.close();
%>