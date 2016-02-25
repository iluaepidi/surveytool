<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String footerPath = "jsp/userPanel/masterComponents/footer.jsp";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<jsp:include page="jsp/userPanel/masterComponents/head.jsp" />
	</head>
  	<body>
  		<div class="main-container">
  			<div class="page-header">
  				<jsp:include page="jsp/userPanel/masterComponents/header.jsp" />
  			</div>
  			
  			<div class="body-content">
	  			<jsp:include page="jsp/common/bodyPages/bLogin.jsp" />
	  		</div>
	  		
	  		<footer>
	  			<jsp:include page="<%= footerPath %>" />
	  		</footer>	
 		</div> 
  	</body>
</html>