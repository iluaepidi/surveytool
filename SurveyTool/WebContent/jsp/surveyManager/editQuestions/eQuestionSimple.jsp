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
											
											<div class="panel-body">									
							  					
							  					<jsp:include page="eqComponents/eqDescription.jsp" />	
							  					
							  					<jsp:include page="eqComponents/eqFiles.jsp" />
							  					
							  					<div class="question-frame">
							  						<h4><%= lang.getContent("survey.type.options") %></h4>
							  						<div class="row">
							  							<div class="col-md-4">
								  							<label for="type-question-<%= question.getIndex() %>"><%= lang.getContent("survey.type.type") %></label>
								  							<select class="form-control" id="type-question-<%= question.getIndex() %>">
															  	<!-- <option value="tx">Text</option>
															  	<option value="ls">Likert scale</option> -->
															    <option value="sim" selected><%= lang.getContent("survey.type.simple") %></option>
															    <!-- <option value="mul">Multiple</option>
																<option value="ma">Matrix</option> -->
															</select>
														</div>
														<fieldset class="col-md-8">
															 <legend><%= lang.getContent("survey.type.example") %> <%= title %></legend>
															 <ul>
															 	<li class="radio">
																  <input type="radio" name="exampleOptionsRadios" id="example-<%= question.getIndex() %>-1" value="option1" checked>
																  <label for="example-<%= question.getIndex() %>-1" id="optionRadioLabel1">
																    <%= lang.getContent("survey.type.option") %> 1
																  </label>
																</li>
																<li class="radio">
																  <input type="radio" name="exampleOptionsRadios" id="example-<%= question.getIndex() %>-2" value="option2">
																  <label for="example-<%= question.getIndex() %>-2" id="optionRadioLabel2">
																    <%= lang.getContent("survey.type.option") %> 2
																  </label>
																</li>
															 </ul>
														</fieldset>													 
							  						</div>						  						
							  					</div>
							  					
												<jsp:include page="eqComponents/eqResponseSettings.jsp" >
												    <jsp:param name="response" value="../eqResponses/eqSimple.jsp" />
												</jsp:include>

							  					<!-- <div class="question-frame">
							  						<h4>Routes</h4>
							  						<div class="routes-select">
								  						<label for="dependencies">Dependencies</label>
							  							<select class="form-control-small" id="dependencies">
														  	<option value="q25" selected>To Q25 - Sports</option>
														    <option value="q21">To Q21 - TV</option>
														    <option value="q23">To Q23 - Internet</option>
														</select>
														<i class="fa fa-cogs fa-2x"></i>
													</div>
							  					</div> -->
							  							  					
											</div>																							
										</li>
										
										