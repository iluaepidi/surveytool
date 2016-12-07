<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%
											Language lang = new Language(getServletContext().getRealPath("/")); 
											lang.loadLanguage(Language.getLanguageRequest(request));
 
											Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
											String title = "";
		    								if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
		    									title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
		    								}
						  					
											if(request.getAttribute(Attribute.s_ADD_QUESTIONS)==null || (boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
											
    										<div class="up-down-buttons">
												<button id="moveup-question-<%= question.getQuestionId() %>" class="btn-transparent moveup-question-arrow" aria-label="<%= lang.getContent("button.question.move.up") %>: <%= title %>"><i class="fa fa-caret-up fa-2x" aria-hidden="true"></i></button>
												<button id="movedown-question-<%= question.getQuestionId() %>" class="btn-transparent movedown-question-arrow" aria-label="<%= lang.getContent("button.question.move.down") %>: <%= title %>"><i class="fa fa-caret-down fa-2x" aria-hidden="true"></i></button>
											</div>
											
											<%} %>