<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.language.Language"%> 
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Page"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));

Page pag = (Page) request.getAttribute(Attribute.s_PAGE);
request.setAttribute(Attribute.s_NUM_PAGE, pag.getNumPage());
%>


										<li class="page" id="page" pid="<%= pag.getPageId() %>" index="<%= pag.getNumPage() %>">
											<div class="page-head row">
												<div class="col-md-3">
													<button id="page-display-<%= pag.getPageId() %>" class="page-display btn-transparent panel-heading-display-arrow display-page-arrow"><span class="visuallyhidden">Hide page: Page <%= pag.getNumPage() %></span><i class="fa fa-caret-down fa-2x" aria-hidden="true"></i></button>
												</div>
												<h4 class="col-md-6"><%= lang.getContent("survey.edit.page.title") + " " + pag.getNumPage() %></h4>
												<div class="col-md-3 right">
													<% if(pag.getNumPage() > 1) { %>
													<button class="remove-page-break">
														<i class="fa fa-trash" aria-hidden="true"></i>
														<%= lang.getContent("button.remove_pagebreak") %>
													</button>
													<% } %>
												</div>
											</div>
											<div class="page-body">
												<input type="hidden" id="pageid1" value="<%= pag.getPageId() %>" />
												<ul class="panel-body page-items connectedSortable" id="page-items">									
								  										  						
							  						<%
							  							String token = "/";
							  							List<Question> questions = pag.getQuestions();
							  							if(questions != null && !questions.isEmpty())
							  							{
							  								for(Question question : questions)
							  								{
							  									request.setAttribute(Attribute.s_QUESTION, question);
							  									request.setAttribute(Attribute.s_TEMPLATE_FILE, question.getTemplatePage());
							  						%>
							  									<jsp:include page="<%= token + Address.s_EDIT_QUESTION_MASTER %>" />
							  						<%			
							  								}
							  							}
							  						%>
							  						
							  											  							  					
												</ul>
											
												
												
												<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
													<jsp:include page='../editQuestions/cAddMenu.jsp'>
														<jsp:param name="pageId" value="<%= pag.getPageId() %>" />
													</jsp:include>
												<% } %>
											</div>				
										</li>