<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="java.util.List"%>
<%
	String title = "";
	String pageTitle = (String) request.getAttribute(Attribute.s_PAGE_TITLE);
	if(pageTitle != null && !pageTitle.isEmpty()) title = ": " + pageTitle; 
%>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Survey Tool<%= title %></title>
		
		<!-- CSS de Bootstrap -->
		<link href="css/style.css" rel="stylesheet" media="screen">
		<link href="css/bootstrap-accessibility.css" rel="stylesheet" media="screen">
		<link href="css/bootstrap.css" rel="stylesheet" media="screen">
		<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" media="screen">
		
		
		<!-- librerías opcionales que activan el soporte de HTML5 para IE8 -->
		<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		  <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		
		<!-- Librería jQuery requerida por los plugins de JavaScript -->
		<script src="js/jquery-2.1.4.js"></script>
		<script src="js/jquery-ui.js"></script>
		
		<!-- Todos los plugins JavaScript de Bootstrap (también puedes
		     incluir archivos JavaScript individuales de los únicos
		     plugins que utilices) -->
		     <%Language lang = new Language(getServletContext().getRealPath("/")); 
				lang.loadLanguage(Language.getLanguageRequest(request));
				%>
		<script>
		 var textErrorPollOption = "<%= lang.getContent("msg.error.poll.option") %>";
		 var textOption = "<%= lang.getContent("survey.type.option") %>";
		</script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/bootstrap-accessibility.min.js"></script>
		<script src="js/stinterface.js"></script>
		<script src="js/stcheckforms.js"></script>
	<%
	List<String> jsFiles = (List<String>) request.getAttribute(Attribute.s_JS_FILES);
	if(jsFiles != null)
	{
		for(String jsFile : jsFiles)
		{
	%>
		<script src="<%= jsFile %>"></script>
	<%
		}
	}
	%>
