<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    								<%
    								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    								String title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
    								%>
										<li class="panel-question" id="panel-question1" qid="<%= question.getQuestionId() %>" index="<%= question.getIndex() %>">
											
											<jsp:include page="eqComponents/eqHead.jsp" />
											
											<div class="panel-body">									
							  					
							  					<jsp:include page="eqComponents/eqDescription.jsp" />	
							  					
							  					<jsp:include page="eqComponents/eqFiles.jsp" />
							  					
							  					<div class="question-frame">
							  						<h4>Options</h4>
							  						<div class="row">
							  							<div class="col-md-4">
								  							<label for="type-question-<%= question.getIndex() %>">Type</label>
								  							<select class="form-control" id="type-question-<%= question.getIndex() %>">
															  	<!-- <option value="tx" >Text</option> -->
															  	<option value="ls" selected>Likert scale</option>
															    <!-- <option value="sim">Simple</option>
															    <option value="mul">Multiple</option>
																<option value="ma">Matrix</option> -->
															</select>
														</div>
														<fieldset class="col-md-8">
															<legend>Example Q.1 <%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></legend>
															 <div class="likert-options">
															 	<div class="likert-options-frame">
															 		<div>
															 			<label for="example-<%= question.getIndex() %>-1">1</label>
															 			<input type="radio" name="likert" id="example-<%= question.getIndex() %>-1"/>
															 		</div>
															 		<div>
															 			<label for="example-<%= question.getIndex() %>-2">2</label>
															 			<input type="radio" name="likert" id="example-<%= question.getIndex() %>-2"/>
															 		</div>
															 		<div>
															 			<label for="example-<%= question.getIndex() %>-3">3</label>
															 			<input type="radio" name="likert" id="example-<%= question.getIndex() %>-3"/>
															 		</div>
															 		<div>
															 			<label for="example-<%= question.getIndex() %>-4">4</label>
															 			<input type="radio" name="likert" id="example-<%= question.getIndex() %>-4"/>
															 		</div>
															 		<div>
															 			<label for="example-<%= question.getIndex() %>-5">5</label>
															 			<input type="radio" name="likert" id="example-<%= question.getIndex() %>-5"/>
															 		</div>
															 		<div>
															 			<label for="example-<%= question.getIndex() %>-6">6</label>
															 			<input type="radio" name="likert" id="example-<%= question.getIndex() %>-6"/>
															 		</div>
															 		<div>
															 			<label for="example-<%= question.getIndex() %>-7">7</label>
															 			<input type="radio" name="likert" id="example-<%= question.getIndex() %>-7"/>
															 		</div>
															 	</div>
															 </div>	
															 <div class="likert-legend">
															 	<div>Totally disgree</div>
															 	<div>Indiferent</div>
															 	<div>Totally agree</div>
															 </div>
														</fieldset>													 
							  						</div>						  						
							  					</div>
							  					
							  					<!-- <div class="question-frame">
							  						<h4>Response settings</h4>
							  						
							  						<ul class="option-list">
							  							<li class="option-item">
						  									<button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button>
						  									<div class="circle-info circle-grey fleft">1</div>
						  									<input type="text" class="option-title form-control fleft" />
						  									<div class="option-icons fleft">
							  									<a class="btn btn-transparent fleft"><i class="fa fa-file-image-o fa-2x"></i></a>
							  									<a class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></a>
							  									<a class="btn btn-transparent fleft red"><i class="fa fa-trash fa-2x"></i></a>
							  								</div>
							  							</li>
							  							<li class="option-item">
						  									<button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button>
						  									<div class="circle-info circle-grey fleft">2</div>
						  									<input type="text" class="option-title form-control fleft" />
						  									<div class="option-icons fleft">
							  									<a class="btn btn-transparent fleft"><i class="fa fa-file-image-o fa-2x"></i></a>
							  									<a class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></a>
							  									<a class="btn btn-transparent fleft red"><i class="fa fa-trash fa-2x"></i></a>
							  								</div>
							  							</li>
							  							<li class="center">
							  								<a class="btn-plus-small"><i class="fa fa-plus-square"></i></a>
							  							</li>
							  						</ul>
							  					</div>
							  					
							  					<div class="question-frame">
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