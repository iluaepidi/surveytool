<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
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
															  	<option value="ls" selected>Text</option>
															  	<!-- <option value="ls">Likert scale</option>
															    <option value="sim">Simple</option>
															    <option value="mul">Multiple</option>
																<option value="ma">Matrix</option> -->
															</select>
														</div>
														<div class="col-md-8">
															 <label for="qresponse"	>Example Q.1 <%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></label>
														     <textarea class="form-control" id="qresponse" rows="5" placeholder="Type here_"></textarea>
														</div>													 
							  						</div>						  						
							  					</div>
							  									
											</div>																							
										</div>