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
		
				