<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String footerPath = "jsp/common/masterComponents/footer.jsp";
String bodyPage = (String) request.getAttribute(Attribute.s_BODY_PAGE);
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
String logged = (String) request.getSession().getAttribute(Attribute.s_LOGGED);
%>
<!DOCTYPE html>
<html lang="<%= lang.getCurrentLanguage() %>">
	<head>
		<jsp:include page="jsp/userPanel/components/head.jsp" />
	</head>
  	<body>
  		<div class="main-container">
  			<div class="page-header">
  				<jsp:include page="jsp/userPanel/components/header.jsp">
  					<jsp:param value="<%= logged %>" name="logged"/>
  				</jsp:include>
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