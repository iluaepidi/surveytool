<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
<%
Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO); 

Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage("en");
%>	
				<!-- Modal -->
				
				<div class="modal fade survey-win" id="newQuestionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog survey-win-dialog" role="document">
				    	<div class="panel panel-primary"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="<%= lang.getContent("button.close") %> <%= lang.getContent("question.new.title") %>"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title"><%= lang.getContent("question.new.title") %></h2> 
				    		</div> 
				    		<div class="panel-body">
				    			<div class="survey-frame">
				    				<h3>Select question type</h3>
									<ul class="row qtype-list">
										<li class="col-sm-1 center btn-qtype">
											<button class="btn-transparent" id="text"><i class="fa fa-align-center fa-2x btn btn-default"></i> <span class="qtype-icon-text">Text</span></button>
										</li>
										<li class="col-sm-1 center btn-qtype">
											<button class="btn-transparent" id="simple"><i class="fa fa-list-ul fa-2x btn btn-default"></i> <span class="qtype-icon-text">Simple</span></button>
										</li>
										<!-- <div class="col-sm-1 center btn-qtype">
											<a id="multiple"><i class="fa fa-list fa-2x btn btn-default"></i> <span class="qtype-icon-text">Multiple</span></a>
										</div> -->
										<li class="col-sm-1 center btn-qtype">
											<button class="btn-transparent" id="scale"><i class="fa fa-arrows-h fa-2x btn btn-default"></i> <span class="qtype-icon-text">Scale</span></button>
										</li>
										<!-- <div class="col-sm-1 center btn-qtype">
											<a id="ordering"><i class="fa fa-sort-numeric-asc fa-2x btn btn-default"></i> <span class="qtype-icon-text">Ordering</span></a>
										</div>
										<div class="col-sm-1 center btn-qtype">
											<a id="grading"><i class="fa fa-star-half-o fa-2x btn btn-default"></i> <span class="qtype-icon-text">Grading</span></a>
										</div>
										<div class="col-sm-1 center btn-qtype">
											<a id="matrix"><i class="fa fa-th fa-2x btn btn-default"></i> <span class="qtype-icon-text">Matrix</span></a>
										</div>
										<div class="col-sm-1 center btn-qtype">
											<a id="code"><i class="fa fa-file-code-o fa-2x btn btn-default"></i> <span class="qtype-icon-text">Code</span></a>
										</div> -->
									</ul>
				    			</div>
				    			<div class="survey-frame frame-basic-Settings" id="frame-basic-Settings">
				    				<h3><%= lang.getContent("question.new.basic_settings.title") %></h3>
				    				<div class="close-frame">
				    					<a class="btn-close-aspa" id="basic-settings-close" title="Close basic settings" aria-label="<%= lang.getContent("button.close") %> <%= lang.getContent("question.new.basic_settings.title") %>"><i class="fa fa-times-circle fa-2x"></i></a>
				    				</div>
				    				<div class="row">
				    					<form>
				    						<input type="hidden" id="qtypevalue" name="qtype" value="" />
				    						<input type="hidden" id="surveyid" name="surveyid" value="<%= survey.getSurveyId() %>" />
				    						<div class="col-md-7">
				    							<label for="qstatement" ><%= lang.getContent("question.statement") %></label>
										     	<textarea class="form-control" id="qstatement" rows="5" placeholder="<%= lang.getContent("placeholder.type_here") %>"></textarea>
											</div>
											<div class="col-md-5 basic-settings-right-options">
												<div class="div-create-question-mandatory row">
													<label for="mandatory" class="col-md-5" ><i class="fa fa-asterisk red"></i><span><%= lang.getContent("question.mandatory") %></span></label>
													<select class="form-control-small col-md-6" id="mandatory">
													  	<option value="false" selected><%= lang.getContent("label.no") %></option>
													    <option value="true"><%= lang.getContent("label.yes") %></option>
													  </select>
												</div>
												<div class="div-btn-create-question center">
													<button class="btn btn-primary" id="create-question" data-dismiss="modal"><%= lang.getContent("button.create") %></button>
												</div>
											</div>
				    					</form>
				    				</div>				    				
				    			</div>				    			
				    		</div> 
				    	</div>
				  	</div>
				</div>
	  			