
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String bodyPage = (String) request.getAttribute(Attribute.s_BODY_PAGE);
String title = (String) request.getAttribute(Attribute.s_POLL_TITLE);
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
<!DOCTYPE html>
<html lang="<%= lang.getCurrentLanguage() %>">
	<head>
		<jsp:include page="jsp/common/masterComponents/headPoll.jsp" />
	</head>
  	<body>
  		<div class="main-container-poll st-styles">
  			<h1><%= title %></h1>
  			
  			<jsp:include page="<%= bodyPage %>" />
	  			
 		</div> 
  	</body>
</html>