<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.OptionsGroup"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.language.Language"%>
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
    								%>
    								
										<li class="panel-question" id="panel-question1" qid="<%= question.getQuestionId() %>" index="<%= question.getIndex() %>">
											
											<jsp:include page="eqComponents/eqHead.jsp" />
					
											<div class="panel-body question-options">
					  							<div class="col-md-1">
						  							<label class="type-tittle" for="type-question-<%= question.getIndex() %>"><%=lang.getContent("question.edit.type")%></label>								  							
												</div>	
												<div class="col-md-4">	
													<p class="question-type-aux"><%=lang.getContent("question.new.simple")%></p>														
						  							<!-- <select class="form-control" id="type-question-<%= question.getIndex() %>">
													  	<option value="f"><%=lang.getContent("question.new.formfield")%></option>
													    <option value="p"><%=lang.getContent("question.new.paragraph")%></option>
													    <option value="m"><%=lang.getContent("question.new.multiple")%></option>
													    <option value="s" selected><%=lang.getContent("question.new.simple")%></option>
													    <option value="o"><%=lang.getContent("question.new.ordering")%></option>
													    <option value="g"><%=lang.getContent("question.new.grading")%></option>
													    <option value="ma"><%=lang.getContent("question.new.matrix")%></option>
													    <option value="sc"><%=lang.getContent("question.new.scale")%></option>
													    <option value="c"><%=lang.getContent("question.new.code")%></option>
													</select> -->
												</div>
												<div class="right col-md-7">
													<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
													<label for="mandatoryButton" class="visuallyhidden"><%= lang.getContent("accesibility.question.mandatory") %></label>														
													<button class="btn btn-question-head btn-sm active mandatory-button" id="mandatoryButton" active="<%= question.isMandatory() %>"><i class="fa fa-asterisk red"></i><span><%= lang.getContent("question.mandatory") %></span></button>
													<%} %>
												</div>

						  					</div>
											
											<div class="panel-body">							  					
							  					<div class="question-frame">
							  					<h6><%=lang.getContent("question.edit.statementSetting.title")%></h6>
							  						<jsp:include page="eqComponents/eqDescription.jsp" />	
							  					
							  						<jsp:include page="eqComponents/eqFiles.jsp" />
							  					</div>							  					
							  					
												<jsp:include page="eqComponents/eqResponseSettings.jsp" >
													<jsp:param name="response" value="../eqResponses/eqSimple.jsp" />
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
													<jsp:param value="true" name="withLogic"/>
												</jsp:include>
												<%} %>						  							  					
											</div>
																																		
										</li>
										
										