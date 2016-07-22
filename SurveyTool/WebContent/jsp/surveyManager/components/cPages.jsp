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
									%>
									
										<li class="page" id="page" pid="<%= pag.getPageId() %>" index="<%= pag.getNumPage() %>">
											<input type="hidden" id="pageid1" value="<%= pag.getPageId() %>" />
											<ul class="panel-body" id="page-items">									
							  										  						
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
													<jsp:include page='../editQuestions/cAddMenu.jsp' />
												<% } %>
															
										</li>	
									<%
									}
									%>		
									</ul>
									