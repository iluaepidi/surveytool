<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <%
    String templateFile = (String) request.getAttribute(Attribute.s_TEMPLATE_FILE);
    %>
    
    <jsp:include page="<%= templateFile %>" />
    