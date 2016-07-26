<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Page"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Section"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));

Section section = (Section) request.getAttribute(Attribute.s_SECTION);
%>

									
									<ul class="section-pages" id="section-pages">
									<%
									for(Page pag : section.getPages())
									{
										request.setAttribute(Attribute.s_PAGE, pag);
									%>									
										<jsp:include page='cPage.jsp'/>
									<%
									}
									%>		
									</ul>
									