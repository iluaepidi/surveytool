<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
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
												
												<div class="panel-body question-options">
											 			<div class="col-sm-1 col-xs-2">
											  				<label class="type-tittle" for="type-question-<%= question.getIndex() %>"><%=lang.getContent("question.edit.type")%></label>								  							
														</div>	
														
														<div class="col-sm-4 col-xs-6">	
															<p class="question-type-aux"><%=lang.getContent("question.new.paragraph")%></p>
														</div>
													<!--<div class="col-md-4">
											  				<select class="form-control" id="type-question-<%= question.getIndex() %>">
																<option value="f"><%=lang.getContent("question.new.formfield")%></option>
																<option value="p" selected><%=lang.getContent("question.new.paragraph")%></option>
																<option value="m"><%=lang.getContent("question.new.multiple")%></option>
																<option value="s"><%=lang.getContent("question.new.simple")%></option>
																<option value="o"><%=lang.getContent("question.new.ordering")%></option>
																<option value="g"><%=lang.getContent("question.new.grading")%></option>
																<option value="ma"><%=lang.getContent("question.new.matrix")%></option>
																<option value="sc"><%=lang.getContent("question.new.scale")%></option>
																<option value="c"><%=lang.getContent("question.new.code")%></option> 
															</select>
														</div>	-->
																											
														<div class="right col-sm-7 col-xs-4">
															<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
															<label for="mandatoryButton" class="visuallyhidden"><%= lang.getContent("accesibility.question.mandatory") %></label>														
															<button class="btn btn-question-head btn-sm active mandatory-button" id="mandatoryButton" active="<%= question.isMandatory() %>"><i class="fa fa-asterisk red" aria-hidden="true"></i><span><%= lang.getContent("question.mandatory") %></span></button>															
															<% } %>
														</div>
													</div>
														
												<div class="panel-body">									
											 		
											 					
											 		<div class="question-frame">
											 			<h6><%=lang.getContent("question.edit.statementSetting.title")%></h6>
											 			<jsp:include page="eqComponents/eqDescription.jsp" />	
											 			<jsp:include page="eqComponents/eqFiles.jsp" />
											 		</div>
													
													<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>																		
													<div class="question-frame">
														<h6><%=lang.getContent("question.edit.response_settings.title")%></h6>
														<%
														String textLength = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_TEXTLENGTH);
														String lines = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_TEXTLINES);
											  			%>
											  			<div class="row" type="global">
											  				<fieldset>
																<legend><%=lang.getContent("question.long.chars")%></legend>																															  							
												  				<div class="question-response-settings" id="genericOptions">
												  					<input class= "question-response-settings-options" type="checkbox" name="isLimitedChars-<%= question.getIndex() %>" id="isLimitedChars" <%if(!textLength.equals("")){%> checked <%}%>>
																	<label class= "question-response-settings-options" for="isLimitedChars"><%=lang.getContent("question.long.chars.label") %></label>
												  					<div id="charsId" <% if(textLength.equals("")){ %> class="question-response-settings-sub-none" <%} else{%>class="question-response-settings-sub-inherit"<%} %>>
																		<input type="number" min="0" max="9999" onkeydown="limit(this)" onkeyup="limit(this)" id="survey-question-max-chars" value="<%= textLength %>"></input>
																		<label for="survey-question-max-chars"><%=lang.getContent("question.form.options.text.charshelp") %></label>
												 					</div>
												 				</div>
												 			</fieldset>
											 			</div>
											 			
														<hr>
														
														<div class="row" type="global">
															<fieldset>
																<legend><%=lang.getContent("question.long.lines")%></legend>
																<div class="question-response-settings">
																	<input class= "question-response-settings-options" type="radio" name="lines-<%= question.getIndex() %>" id="adjust-lines-adjust" value="adjusted" <%if(lines.equals("")){%> checked <%}%>>
												  					<label class= "question-response-settings-options" for="adjust-lines-adjust"><%=lang.getContent("question.long.lines.numberChars")%></label>
																</div>
																<div class="question-response-settings">																														  							
												  					<input class= "question-response-settings-options" type="radio" name="lines-<%= question.getIndex() %>" id="adjust-lines-set" value="set" <%if(!lines.equals("")){%> checked <%}%>>
												  					<label class= "question-response-settings-options" for="adjust-lines-set"><%=lang.getContent("question.long.lines.set")%></label>
																	<div id="lines" <% if(lines.equals("")){ %> class="question-response-settings-sub-none" <%} else{%>class="question-response-settings-sub-inherit"<%} %>>
																		<input type="number" min="0" max="9999" onkeydown="limit(this)" onkeyup="limit(this)" id="survey-question-max-lines" value="<%= lines %>"></input>
												 						<label for="survey-question-max-lines"><%=lang.getContent("question.long.lines.set.type")%></label>
												  					</div>
												  				</div>
												  			</fieldset>
											  			</div>												 
											 		</div>	
											 			
											 		<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>				  							
													<jsp:include page="eqComponents/eqDependences.jsp">
														<jsp:param value="false" name="withLogic"/>
													</jsp:include>
													<%} %>
											 		<%} %>
												</div>	
											</div>																						
										</li>