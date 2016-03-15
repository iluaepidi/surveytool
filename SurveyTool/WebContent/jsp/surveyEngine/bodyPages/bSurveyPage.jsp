					
<%@page import="ilu.surveytool.databasemanager.DataObject.Page"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Section"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
				<%
				Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO);
				
				Language lang = new Language(getServletContext().getRealPath("/")); 
				lang.loadLanguage(Language.getLanguageRequest(request));
				%>	
								
				<div class="container-fluid">
	  				<div class="title-content">
	  					<h2><%= survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></h2>	  					
	  				</div>	  				
	  				<div class="content">
	  					<form action="<%= Address.s_SERVLET_SURVEY_PROCESS %>" method="POST">
	  						<input type="hidden" name="sid" value="<%= survey.getSurveyId() %>" />
	  						<div class="survey-form" >
	  							<p><%= survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText() %></p>
	  							
	  							<% 
	  							String token = "/";
	  							int index = 1;
	  							for(Section section : survey.getSections())
	  							{
	  								for(Page pag : section.getPages())
	  								{
		  								for(Question question : pag.getQuestions())
		  								{
		  									request.setAttribute(Attribute.s_QUESTION, question);
	  							%>	  							
	  								<jsp:include page="<%= token + question.getFormPage() %>">
	  									<jsp:param name="index" value="<%= index %>" />
	  								</jsp:include>
	  							<%
	  										index++;
		  								}
	  								}
	  							}
	  							%>
	  						</div>
	  						<div class="center">
	  							<input type="submit" class="btn btn-primary btn-submit-page" Value="<%= lang.getContent("button.submit") %>"/>
	  						</div>
	  					</form>
	  				</div>
	  			</div>
	  			
<%
lang.close();
%>
	  			