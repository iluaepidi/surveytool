<%@page import="ilu.surveytool.language.Language"%>
<%@page import="java.util.Properties"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
		<div class="col-md-10 col-md-push-1">
			<p id="powered"><img src="img/powered.png" alt="Powered by ILUNION"/></p>
  		</div>
<%
lang.close();
%>