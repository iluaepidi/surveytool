						  					
						  					<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
											<%@page import="java.util.List"%>
											<%@page import="ilu.surveytool.language.Language"%>
											<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
											<%@page import="ilu.surveytool.constants.Attribute"%>
											<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
											<%
											Language lang = new Language(getServletContext().getRealPath("/")); 
											lang.loadLanguage(Language.getLanguageRequest(request));
 
											Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
											String title = lang.getContent("bcontent.title");
											%>
						  					
											<div class="panel-heading">	

												<button id="display-question-<%= question.getQuestionId() %>" class="btn-transparent panel-heading-display-arrow display-question-arrow" aria-label="<%= lang.getContent("button.hide_question") %>: <%= title %>"><i class="fa fa-caret-down fa-2x" aria-hidden="true"></i></button>				
												<h5 class="panel-title">
													<div class="col-sm-12">
														<div class="form-group" style="margin:0px;">
															<input type="text" class="hidden" id="survey-question-title" value="<%= title %>"/>
															<span id="bcontent-title" class="noEditingTitle"><%= title%></span>
						  								</div>
													</div>
												</h5>
												<div class="panel-section-buttons right">
												<% if(request.getAttribute(Attribute.s_ADD_QUESTIONS)==null || (boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
													<button class="btn-transparent btn-remove" id="removeQuestion" aria-label="<%= lang.getContent("button.remove_question") %>: <%= title %>"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
												<%} %>
												</div>
												
											</div>	
						  					
											<%
											lang.close();
											%>
							  					