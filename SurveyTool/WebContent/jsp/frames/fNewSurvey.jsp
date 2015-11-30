
	  			<!-- Modal -->
				<div class="modal fade survey-win" id="newSurveyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog survey-win-dialog" role="document">
				    	<div class="panel panel-primary"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title">New survey</h2> 
				    		</div> 
				    		<div class="panel-body">
				    			<div class="survey-frame">
				    				<h3>Clone from survey database</h3>
				    				<div class="row">
				    					<div class="col-sm-6 center">
				    						<div class="input-group">
										      <input type="text" class="form-control" placeholder="Search for...">
										      <span class="input-group-btn">
										        <button class="btn btn-default btn-search" type="button"><i class="fa fa-search"></i></button>
										      </span>
										    </div><!-- /input-group -->
				    					</div>
				    					<div class="col-sm-6 center">
				    						<div class="form-group">
											  <select class="form-control" id="sel1">
											  	<option value="" disabled selected>Browser survey categories</option>
											    <option>Profile</option>
											    <option>Accessibility</option>
											    <option>Blind</option>
											    <option>Deaf</option>
											  </select>
											</div>
										</div>
				    				</div>
				    			</div>
				    			<div class="center">- OR -</div>
				    			<div class="survey-frame">
				    				<h3>Create from scratch</h3>
									<form class="form-horizontal">
										<div class="row">
											<div class="col-sm-7 margin-bottom-10">
												<label for="surveyTitle" class="col-sm-2 control-label left">Survey title</label>
										   		<div class="col-sm-10">
										     			<input type="text" class="form-control" id="surveyTitle" placeholder="">
										   		</div>
											</div>
											<div class="col-sm-5">
												<label for="surveyTitle" class="col-sm-3 control-label left"> for project </label>
										   		<div class="col-sm-9">
										   			<select class="form-control" id="sel1">
														<option value="" disabled selected>Select a related project (if any)</option>
													  	<option>Apsis4ALL</option>
													  	<option>Cloud4ALL</option>
													  	<option>Inredis</option>
													</select>
										   		</div>
											</div>
										</div>
										
										<div class="row">
											<div class="col-sm-9 margin-bottom-10">
												<label for="surveyTitle" class="col-sm-1 control-label left">Short description</label>
										   		<div class="col-sm-11">
										     			<textarea class="form-control" rows="2" placeholder="Type here_"></textarea>
										   		</div>
											</div>
											<div class="col-sm-3 center">
												<input type="submit" class="btn btn-primary" value="Create" />
											</div>
										</div>
									</form>
				    			</div>
				    		</div> 
				    	</div>
				  	</div>
				</div>