<%@page import="ilu.surveytool.databasemanager.DataObject.Page"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Section"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%> 
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.language.Language"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));

//int pageId = (int) request.getAttribute(Attribute.s_PAGE_ID);
Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO);
%>
							<ul class="survey-sections" id="survey-sections">
							
<%
							List<Section> sections = survey.getSections();
							int i = 1;
							for(Section section : sections)
							{					
								request.setAttribute(Attribute.s_SECTION, section);
								request.setAttribute(Attribute.s_INDEX, i);
%>
								    								
								<jsp:include page="../components/cSection.jsp" />
						
<%								
								i++;
							}
%>
							</ul>		
							
							<div class="div-add-section">
								<button type="button" class="btn btn-primary btn-new-section" id="btn-new-section"><%= lang.getContent("button.section.new") %></button>
							</div>				
<%							
lang.close();
%>