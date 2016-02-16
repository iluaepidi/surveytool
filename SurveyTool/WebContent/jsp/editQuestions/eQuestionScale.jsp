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
										<div class="panel-question" id="panel-question1" qid="<%= question.getQuestionId() %>">
											<div class="panel-heading">	
												<!-- <div class="col-sm-1 left"><a id="display-question-panel" title="diplay section 1"><i class="fa fa-caret-down fa-2x"></i></a></div> -->				
												<h3 class="col-sm-11 panel-title"><input type="text" class="survey-section-title-unselected" id="survey-question-title" value="<%= title %>" /></h3>
												<div class="col-sm-1 panel-section-delete right"><button class="btn btn-transparent red" id="removeQuestion" aria-label="Remove question: <%= title %> " title="remove section 1"><i class="fa fa-trash fa-2x"></i></button></div>
											</div>
											
											<div class="panel-body">									
							  					<div class="panel-question-basic-setting row">
							  						<div class="alt-question col-md-5">
													</div>
													<div class="col-md-2">
														<!-- <button class="btn btn-question-basic-settings" id="helpTextButton" active="<%= question.isHelpText() %>" data-toggle="modal" data-target="#setHelpText"><i class="fa fa-question-circle fa-2x"></i><span>Help text</span></button> -->													
													</div>
													<div class="col-md-2">
														<button class="btn btn-question-basic-settings mandatory-question" id="mandatoryButton" active="<%= question.isMandatory() %>"><i class="fa fa-asterisk red"></i><span>Mandatory</span></button>
													</div>
													<div class="col-md-2">
														<button class="btn btn-question-basic-settings" id="btn-question-import-file" active="false" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i><span>Import multimedia file</span></button>
													</div>
							  					</div>
							  					
							  					<%
											    String descriptionText = "";
							  					if(question.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION))
							  					{
							  						descriptionText = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText(); 
							  					}
							  					%>
							  					<div class="question-frame question-frame-help" id="question-frame-help">
							  						<h4>Description (Optional)</h4>
							  						<textarea class="form-control" id="survey-question-description-text" rows="2" placeholder="Type here_" aria-label="Description question"><%= descriptionText %></textarea>
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
										</div>