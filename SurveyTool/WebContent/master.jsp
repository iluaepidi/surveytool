<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String footerPath = "jsp/common/masterComponents/footer.jsp";
String bodyPage = (String) request.getAttribute(Attribute.s_BODY_PAGE);
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
<!DOCTYPE html>
<html lang="<%= lang.getCurrentLanguage() %>">
	<head>
		<jsp:include page="jsp/common/masterComponents/head.jsp" />
	</head>
  	<body>
  		<div class="main-container">
  			<div class="page-header">
  				<jsp:include page="jsp/common/masterComponents/header.jsp" />
  			</div>
  			
  			<div class="body-content st-styles">
	  			<jsp:include page="<%= bodyPage %>" />
	  		</div>
	  		
	  		<footer class="footer" style="position:relative">
	  			<jsp:include page="<%= footerPath %>" />
	  		</footer>	
 		</div> 
  	</body>
</html>