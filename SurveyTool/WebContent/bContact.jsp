<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String footerPath = "jsp/common/masterComponents/footer.jsp";
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	  			<div class="survey-free-content content" >
					<h2><%= lang.getContent("survey.footer.link.page.contact.title") %></h2>
					<p><%= lang.getContent("survey.footer.link.page.contact.body") %></p>  							
				</div>
	  		</div>
	  		
	  		<footer class="footer">
	  			<jsp:include page="<%= footerPath %>" />
	  		</footer>	
 		</div> 
  	</body>
</html>
				
<%
lang.close();
%>