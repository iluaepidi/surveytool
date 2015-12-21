
	  			<!-- Modal -->
				<%@page import="ilu.surveytool.constants.Address"%>
<div class="modal fade survey-win" id="newSurveyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog survey-win-dialog" role="document">
				    	<div class="panel panel-primary"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title">New survey</h2> 
				    		</div> 
				    		<div class="panel-body">				    			
				    			<div class="survey-frame">
				    				<h3>Create from scratch</h3>
									<form action="<%= Address.s_SERVLET_CREATE_SURVEY_SERVLET %>" method="POST" class="form-horizontal">
										<div class="row">
											<div class="col-sm-7 margin-bottom-10">
												<label for="surveyTitle" class="col-sm-2 control-label left">Survey title</label>
										   		<div class="col-sm-10">
										     			<input type="text" class="form-control" id="surveyTitle" name="title" placeholder="">
										   		</div>
											</div>
											<div class="col-sm-5">
												<label for="surveyTitle" class="col-sm-3 control-label left"> for project </label>
										   		<div class="col-sm-9">
										   			<input type="text" class="form-control" name="project" placeholder="Insert project name_"/>														
										   		</div>
											</div>
										</div>
										
										<div class="row">
											<div class="col-sm-9 margin-bottom-10">
												<label for="surveyDescription" class="control-label left">Short description</label>
										   		<textarea class="form-control" id="surveyDescription" name="description" rows="2" placeholder="Type here_"></textarea>
											</div>
											<div class="col-sm-3 center">
												<input type="submit" class="btn btn-primary btn-create-survey" value="Create" />
											</div>
										</div>
									</form>
				    			</div>
				    		</div> 
				    	</div>
				  	</div>
				</div>