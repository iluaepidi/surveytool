<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    								<%
    								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);

    								String title = "";
    								if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
    									title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
    								}

    								Language lang = new Language(getServletContext().getRealPath("/")); 
    								lang.loadLanguage(Language.getLanguageRequest(request));
    								
    								String text = "";
    								if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
    									text = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
    								}
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
								  					
								  					<div class="question-frame">
														<h6><%=lang.getContent("question.edit.response_settings.title")%></h6>
														<%
														String scaleType = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_SCALE_TYPE);
														%>
														<div class="likert-type-div">
															<label for="likertType<%= question.getQuestionId() %>"><%=lang.getContent("question.scale.type")%></label>															
															<select name="likertType" id="likertType<%= question.getQuestionId() %>" class="form-control likertType" autocomplete="off">
																<option value="5" <%if(scaleType.equals("5")){ %>selected<% } %>>5 <%=lang.getContent("question.scale.type.points")%></option>
																<option value="7" <%if(scaleType.equals("7")){ %>selected<% } %>>7 <%=lang.getContent("question.scale.type.points")%></option>
																<option value="11" <%if(scaleType.equals("11")){ %>selected<% } %>>11 <%=lang.getContent("question.scale.type.points")%></option>
															</select>
														</div>	
														<div class="likert-labels-div">
															<fieldset>
																<legend><%= lang.getContent("question.form.scale.liker.point_label.legend") %></legend>
																<ul class="option-list" id="option-list">
																	<% 
																	int numPoints = scaleType.equals("11") ? 10 : Integer.parseInt(scaleType);
																	int initPoint = numPoints == 10 ? 0 : 1;
																	for(int i = initPoint; i <= numPoints; i++) { 
																		String value = question.getContents().containsKey("label" + i) ? question.getContents().get("label" + i).getText() : "";
																	%>
																		<li class="option-item" id="option-item">
																			<div class="circle-info circle-grey fleft"><%= i %></div>
																			<label for="option<%= question.getQuestionId() %>-<%= i %>" class="visuallyhidden"><%= lang.getContent("question.form.scale.liker.point_label") %>  <%= i %></label>
									  										<input id="option<%= question.getQuestionId() %>-<%= i %>" type="text" maxlength="10000" class="point-label form-control fleft option" index="<%= i %>" placeholder="<%= lang.getContent("question.form.scale.liker.point_label") %> <%= i %>" value="<%= value %>"/>
																		</li>
																	<% } %>
																</ul>
															</fieldset>
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