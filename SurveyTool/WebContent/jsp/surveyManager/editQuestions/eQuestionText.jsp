<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="ilu.surveytool.language.Language"%>
    
    								<%
    								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    								String title = "";
    								if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
    									title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
    								}
    								
    								Language lang = new Language(getServletContext().getRealPath("/")); 
    								lang.loadLanguage(Language.getLanguageRequest(request));
    								%>
										<li class="panel-question" id="panel-question1" qid="<%= question.getQuestionId() %>" index="<%= question.getIndex() %>">
											
											
											<jsp:include page="eqComponents/eqMoveButtons.jsp" />
					  						
											<div class="panel-question-content">
																						
												<jsp:include page="eqComponents/eqHead.jsp" />
												
												<div class="panel-body">									
								  					
								  					<jsp:include page="eqComponents/eqDescription.jsp" />	
								  					
								  					<jsp:include page="eqComponents/eqFiles.jsp" />
								  					
								  					<div class="question-frame">
								  						<h6><%= lang.getContent("survey.type.options") %></h6>
								  						<div class="row">
								  							<div class="col-sm-4">
									  							<label for="type-question-<%= question.getIndex() %>"><%= lang.getContent("survey.type.type") %></label>
									  							<select class="form-control" id="type-question-<%= question.getIndex() %>">
																  	<option value="ls" selected><%= lang.getContent("survey.type.text") %></option>
																  	<!-- <option value="ls">Likert scale</option>
																    <option value="sim">Simple</option>
																    <option value="mul">Multiple</option>
																	<option value="ma">Matrix</option> -->
																</select>
															</div>
															<div class="col-sm-8">
																 <label for="example-<%= question.getIndex() %>"><%= lang.getContent("survey.type.example") %> <%= title %></label>
															     <textarea class="form-control" id="example-<%= question.getIndex() %>" rows="5" placeholder="<%= lang.getContent("placeholder.type_here") %>"></textarea>
															</div>													 
								  						</div>						  						
								  					</div>
								  					
								  					<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>		
								  					<jsp:include page="eqComponents/eqDependences.jsp">
														<jsp:param value="false" name="withLogic"/>
													</jsp:include>
													<%} %>				
												</div>	
											</div>																						
										</li>