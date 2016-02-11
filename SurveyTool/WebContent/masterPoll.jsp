<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String bodyPage = (String) request.getAttribute(Attribute.s_BODY_PAGE);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<jsp:include page="jsp/masterComponents/headPoll.jsp" />
	</head>
  	<body>
  		<div class="main-container-poll">
  			
  			<jsp:include page="<%= bodyPage %>" />
	  			
 		</div> 
  	</body>
</html>