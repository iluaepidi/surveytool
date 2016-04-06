<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="java.util.List"%>
<%
	String title = "";
	String pageTitle = (String) request.getAttribute(Attribute.s_PAGE_TITLE);
	if(pageTitle != null && !pageTitle.isEmpty()) title = ": " + pageTitle; 
%>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Poll<%= title %></title>
		
		<!-- CSS de Bootstrap -->
		<link href="css/stylePoll.css" rel="stylesheet" media="screen">
		<link href="css/bootstrap-accessibility.css" rel="stylesheet" media="screen">
		<link href="css/bootstrap.css" rel="stylesheet" media="screen">
		<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" media="screen">
		
		<!-- Librería jQuery requerida por los plugins de JavaScript -->
		<script src="js/jquery-2.1.4.js"></script>
		
		<script src="js/bootstrap.min.js"></script>
		<script src="js/bootstrap-accessibility.min.js"></script>
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