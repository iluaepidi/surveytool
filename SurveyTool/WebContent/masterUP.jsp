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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="<%= lang.getCurrentLanguage() %>">
	<head>
		<jsp:include page="jsp/userPanel/components/head.jsp" />
	</head>
  	<body>
  		<div class="main-container">
  			<div class="page-header">
  				<jsp:include page="jsp/userPanel/components/header.jsp" />
  			</div>
  			
  			<div id="bodyContent" class="body-content st-styles">
	  			<jsp:include page="<%= bodyPage %>" />
	  		</div>
	  		
	  		<footer class="footer">
	  			<jsp:include page="<%= footerPath %>" />
	  		</footer>	
 		</div> 
  	</body>
</html>