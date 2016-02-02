				
				<!-- Modal -->
				<%@page import="ilu.surveytool.constants.Attribute"%>
				<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
				<%
				Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO); 
				%>
				<div class="modal fade survey-win" id="newQuestionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog survey-win-dialog" role="document">
				    	<div class="panel panel-primary"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title">Add new question</h2> 
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
				    				<h3>Basic settings</h3>
				    				<div class="close-frame">
				    					<a class="btn-close-aspa" id="basic-settings-close" title="Close basic settings" aria-label="Close basic settings"><i class="fa fa-times-circle fa-2x"></i></a>
				    				</div>
				    				<div class="row">
				    					<form>
				    						<input type="hidden" id="qtypevalue" name="qtype" value="" />
				    						<input type="hidden" id="surveyid" name="surveyid" value="<%= survey.getSurveyId() %>" />
				    						<div class="col-md-7">
				    							<label for="qstatement" >Question statement</label>
										     	<textarea class="form-control" id="qstatement" rows="5" placeholder="Type here_"></textarea>
										     	<!-- <div class="center">
										     		<a class="btn btn-add-media"><i class="fa fa-picture-o fa-2x"></i><span>Add media</span></a>
										     	</div> -->
											</div>
											<div class="col-md-5 basic-settings-right-options">
												<div class="row">
													<label for="main-version" class="col-md-5" ><i class="fa fa-language fa-2x"></i><span>Main version</span></label>
													<select class="form-control-small col-md-6" id="main-version">
													  	<!-- <option value="none" disabled selected>Browser languages</option> -->
													    <option value="en" selected>English</option>
													    <!-- <option value="es">Spanish</option>
													    <option value="fr">French</option>
													    <option value="el">Greek</option> -->
													</select>
												</div>
												<div class="row">
													<label for="mandatory" class="col-md-5" ><i class="fa fa-asterisk red"></i><span>Mandatory</span></label>
													<select class="form-control-small col-md-6" id="mandatory">
													  	<option value="false" selected>No</option>
													    <option value="true">Sí</option>
													  </select>
												</div>
												<div class="row">
													<label for="help-text" class="col-md-5" ><i class="fa fa-question-circle fa-2x"></i><span>Help text</span></label>
													<select class="form-control-small col-md-6" id="help-text">
													  	<option value="false" selected>No</option>
													    <option value="true">Sí</option>
													  </select>
												</div>
												<div class="center">
													<button class="btn btn-primary" id="create-question" data-dismiss="modal" aria-label="Close">Create</button>
												</div>
											</div>
				    					</form>
				    				</div>				    				
				    			</div>				    			
				    		</div> 
				    	</div>
				  	</div>
				</div>
	  			