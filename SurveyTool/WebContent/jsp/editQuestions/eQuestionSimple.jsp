<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.OptionsGroup"%>
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
												<button id="display-question-panel" class="display-question-arrow" aria-label="hide question: <%= title %>"><i class="fa fa-caret-down fa-2x"></i></button>				
												<h3 class="panel-title"><input type="text" class="survey-section-title-unselected" id="survey-question-title" value="<%= title %>" /></h3>
												<div class="panel-section-buttons right">
													<button class="btn btn-question-head btn-sm active" id="mandatoryButton" active="<%= question.isMandatory() %>"><i class="fa fa-asterisk red"></i><span>Mandatory</span></button>
													<button class="btn btn-transparent red" id="removeQuestion" aria-label="Remove question: <%= title %> " title="remove section 1"><i class="fa fa-trash fa-2x"></i></button>
												</div>
											</div>
											
											<div class="panel-body">									
							  					
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
							  					List<Resource> resources = question.getResources();
							  					%>
							  					<div class="question-frame question-frame-multimedia" id="multimediaFrame">
							  					
							  						<h4>Question Files</h4>
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
							  						
							  						<button class="btn btn-primary btn-sm active" id="btn-question-import-file" active="false" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o"></i><span>Add file</span></button>
							  					</div>
							  					
							  					<div class="question-frame">
							  						<h4>Options</h4>
							  						<div class="row">
							  							<div class="col-md-4">
								  							<label for="type-question">Type</label>
								  							<select class="form-control" id="type-question">
															  	<!-- <option value="tx">Text</option>
															  	<option value="ls">Likert scale</option> -->
															    <option value="sim" selected>Simple</option>
															    <!-- <option value="mul">Multiple</option>
																<option value="ma">Matrix</option> -->
															</select>
														</div>
														<fieldset class="col-md-8">
															 <legend>Example Q.1 <%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></legend>
															 <ul>
															 	<li class="radio">
																  <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
																  <label for="optionsRadios1" id="optionRadioLabel1">
																    Option 1
																  </label>
																</li>
																<li class="radio">
																  <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
																  <label for="optionsRadios2" id="optionRadioLabel2">
																    Option 2
																  </label>
																</li>
															 </ul>
														</fieldset>													 
							  						</div>						  						
							  					</div>
							  					<%
							  				if(question.getOptionsGroups().size() > 0)
							  				{
							  					for(OptionsGroup optionsGroup : question.getOptionsGroups())
							  					{
							  					%>
							  					<div class="question-frame">
							  						<h4>Response settings</h4>
							  												  						
							  						<ul class="option-list" id="option-list" ogid="<%= optionsGroup.getId() %>" otype="radio">
							  						<%
							  						for(Option option : optionsGroup.getOptions())
							  						{
							  							int index = option.getIndex();
							  							String text = option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
							  						%>
							  							<li class="option-item" id="option-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft"><%= index %></div>
						  									<input type="text" class="option-title form-control fleft" index="<%= index %>" oid="<%= option.getId() %>" placeholder="Option <%= index %>" value="<%= text %>"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-option" aria-label="remove option: <%= text %>"><i class="fa fa-trash fa-2x"></i></button>
							  								</div>
							  							</li>
							  						<%
							  						}
							  						
							  						int size = optionsGroup.getOptions().size();
							  						if(size < 2)
							  						{
							  							for(int i = size; i < 2; i++)
							  							{
							  								int index = i + 1;
							  						%>
							  							<li class="option-item" id="option-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft"><%= index %></div>
						  									<input type="text" class="option-title form-control fleft" index="<%= index %>" oid="0" placeholder="Option <%= index %>"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-option" aria-label="remove option"><i class="fa fa-trash fa-2x"></i></button>
							  								</div>
							  							</li>
							  							
							  						<%
							  							}
							  						}
							  						%>
							  							<li class="center" id="li-add-option<%= question.getQuestionId() %>">
							  								<button class="btn btn-primary btn-sm active" id="btn-add-option" ><i class="fa fa-plus-square"></i><span>Add option</span></button>
							  							</li>
							  						</ul>
							  					</div>
							  					<%
							  					}
							  				}
							  				else
							  				{
							  					%>
							  					<div class="question-frame">
							  						<h4>Response settings</h4>
							  												  						
							  						<ul class="option-list" id="option-list" ogid="0" otype="radio">
							  						
							  							<li class="option-item" id="option-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft">1</div>
						  									<input type="text" class="option-title form-control fleft" index="1" oid="0" placeholder="Option 1"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-option" aria-label="remove option"><i class="fa fa-trash fa-2x"></i></button>
							  								</div>
							  							</li>
							  							
							  							<li class="option-item" id="option-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft">2</div>
						  									<input type="text" class="option-title form-control fleft" index="2" oid="0" placeholder="Option 2"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-option" aria-label="remove option"><i class="fa fa-trash fa-2x"></i></button>
							  								</div>
							  							</li>
							  						
							  							<li class="center" id="li-add-option<%= question.getQuestionId() %>">
							  								<button class="btn btn-primary btn-sm active" id="btn-add-option" ><i class="fa fa-plus-square"></i><span>Add option</span></button>
							  							</li>
							  						</ul>
							  					</div>
							  					<%
							  				}
							  					%>
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
										</div>
										
										