<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    								<%
    								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    								%>
										<div class="panel-question" id="panel-question1" qid="<%= question.getQuestionId() %>">
											<div class="panel-heading">	
												<div class="col-sm-1 left"><a id="display-question-panel" title="diplay section 1"><i class="fa fa-caret-down fa-2x"></i></a></div>				
												<h3 class="col-sm-10 panel-title"><input type="text" class="survey-section-title-unselected" id="survey-question-title" value="<%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %>" /></h3>
												<div class="col-sm-1 panel-section-delete right"><a href="#" title="remove section 1"><i class="fa fa-trash fa-2x"></i></a></div>
											</div>
											
											<div class="panel-body">									
							  					<div class="panel-question-basic-setting row">
							  						<div class="alt-question col-md-5">
														<label for="main-version" class="col-md-5" ><i class="fa fa-language fa-2x"></i><span>Alternatives</span></label>
														<select class="form-control-small col-md-6" id="main-version">
														  	<option value="en" selected>Main (English)</option>
														    <option value="es">Spanish</option>
														    <option value="fr">French</option>
														    <option value="el">Greek</option>
														  </select>
													</div>
													<div class="col-md-2">
														<button class="btn mandatory-question" selected="<%= question.isMandatory() %>"><i class="fa fa-asterisk red"></i><span>Mandatory</span></button>													
													</div>
													<div class="col-md-2">
														<button class="btn" selected="<%= question.isHelpText() %>"><i class="fa fa-question-circle fa-2x"></i><span>Help text</span></button>
													</div>
													<div class="col-md-2">
														<button class="btn btn-question-basic-settings" id="btn-question-import-file" selected="false" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i><span>Import multimedia file</span></button>
													</div>
							  					</div>
							  					
							  					<div class="question-frame question-frame-help">
							  						<p>Instructions /Help text for selectiong option</p>
							  					</div>	
							  					
							  					<%
							  					String hidden = "";
							  					List<Resource> resources = question.getResources();
							  					if(resources.isEmpty())
							  					{
							  						hidden = "hidden";
							  					}
							  					%>
							  					<div class="question-frame question-frame-multimedia <%= hidden %>" id="multimediaFrame">
							  					
							  						<h4>Question Multimedia Files</h4>
							  						<ul class="multimedia-list" id="multimediaFilesList">
							  						<%							  						
							  						for(Resource resource : resources)
							  						{
							  							request.setAttribute(Attribute.s_RESOURCE, resource);
							  						%>
							  							<jsp:include page="../components/cMultimediaItem.jsp" />
							  						<%
							  						}
							  						%>
							  						</ul>
							  					</div>
							  					
							  					<div class="question-frame">
							  						<h4>Options</h4>
							  						<div class="row">
							  							<div class="col-md-4">
								  							<label for="type-question">Type</label>
								  							<select class="form-control" id="type-question">
															  	<option value="tx" >Text</option>
															  	<option value="ls" selected>Likert scale</option>
															    <option value="sim">Simple</option>
															    <option value="mul">Multiple</option>
																<option value="ma">Matrix</option>
															</select>
														</div>
														<div class="col-md-8">
															<label for="qresponse">Q.1 <%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></label>
															 <div class="likert-options">
															 	<div class="likert-options-frame">
															 		<div>
															 			<label for="likert1">1</label>
															 			<input type="radio" name="likert" id="likert1"/>
															 		</div>
															 		<div>
															 			<label for="likert2">2</label>
															 			<input type="radio" name="likert" id="likert2"/>
															 		</div>
															 		<div>
															 			<label for="likert3">3</label>
															 			<input type="radio" name="likert" id="likert3"/>
															 		</div>
															 		<div>
															 			<label for="likert4">4</label>
															 			<input type="radio" name="likert" id="likert4"/>
															 		</div>
															 		<div>
															 			<label for="likert5">5</label>
															 			<input type="radio" name="likert" id="likert5"/>
															 		</div>
															 		<div>
															 			<label for="likert6">6</label>
															 			<input type="radio" name="likert" id="likert6"/>
															 		</div>
															 		<div>
															 			<label for="likert7">7</label>
															 			<input type="radio" name="likert" id="likert7"/>
															 		</div>
															 	</div>
															 </div>	
															 <div class="likert-legend">
															 	<div>Totally disgree</div>
															 	<div>Indiferent</div>
															 	<div>Totally agree</div>
															 </div>
														</div>													 
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
										</div>