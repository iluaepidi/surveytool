<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    								<%
    								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    								//String title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
    								
    								Language lang = new Language(getServletContext().getRealPath("/")); 
									lang.loadLanguage(Language.getLanguageRequest(request));
								  									
									%>
										<li class="panel-question" id="panel-question1" qid="<%= question.getQuestionId() %>" index="<%= question.getIndex() %>">
											
											<jsp:include page="eqComponents/eqMoveButtons.jsp" />
					  						
											<div class="panel-question-content">
											
												<jsp:include page="eqComponents/eqHead.jsp" />
												
												<jsp:include page="eqComponents/eqQuestionOptions.jsp" />
													
												<div class="panel-body">									
								  					
								  					<div class="question-frame">
								  					<h6><%=lang.getContent("question.edit.statementSetting.title")%></h6>
								  						<jsp:include page="eqComponents/eqDescription.jsp" />	
								  					
								  						<jsp:include page="eqComponents/eqFiles.jsp" />
								  						
								  						
								  					</div>						  					
								  					
								  					
								  					
								  					<jsp:include page="eqComponents/eqResponseSettings.jsp" >
													    <jsp:param name="response" value="../eqResponses/eqMultiple.jsp" />
													</jsp:include>												
								  					
								  					
								  					<!-- <%
															String otherText = "";
								  							if(question.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_OTHER))
								  							{
								  								otherText = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_OTHER).getText(); 
								  							}
								  							%>
															<div class="row">	
																<label for="other-option-<%= question.getIndex() %>"><%=lang.getContent("question.form.options.other")%></label>																															  							
									  							<textarea class="form-control" id="survey-question-other-text" rows="1" placeholder="<%= lang.getContent("placeholder.type_label") %>" aria-label="<%= lang.getContent("question.edit.description.aria_label") %>" maxlength="1000"><%= otherText %></textarea>
								  							</div> -->
								  					<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>		
								  					<jsp:include page="eqComponents/eqDependences.jsp">
														<jsp:param value="false" name="withLogic"/>
													</jsp:include>
								  					<%} %>	  					
												</div>
											</div>																							
										</li>