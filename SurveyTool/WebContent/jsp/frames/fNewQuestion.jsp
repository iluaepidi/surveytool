				
				<!-- Modal -->
				<div class="modal fade survey-win" id="newQuestionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog survey-win-dialog" role="document">
				    	<div class="panel panel-primary"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title">Add new question</h2> 
				    		</div> 
				    		<div class="panel-body">
				    			<div class="survey-frame">
				    				<h3>Question database</h3>
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
				    				<h3>Select question type</h3>
									<div class="row">
										<div class="col-sm-1 center btn-qtype">
											<a><i class="fa fa-align-center fa-2x btn btn-default"></i> <span class="qtype-icon-text">Text</span></a>
										</div>
										<div class="col-sm-1 center btn-qtype">
											<a><i class="fa fa-list-ul fa-2x btn btn-default"></i> <span class="qtype-icon-text">Simple</span></a>
										</div>
										<div class="col-sm-1 center btn-qtype">
											<a><i class="fa fa-list fa-2x btn btn-default"></i> <span class="qtype-icon-text">Multiple</span></a>
										</div>
										<div class="col-sm-1 center btn-qtype">
											<a><i class="fa fa-arrows-h fa-2x btn btn-default"></i> <span class="qtype-icon-text">Scale</span></a>
										</div>
										<div class="col-sm-1 center btn-qtype">
											<a><i class="fa fa-sort-numeric-asc fa-2x btn btn-default"></i> <span class="qtype-icon-text">Ordering</span></a>
										</div>
										<div class="col-sm-1 center btn-qtype">
											<a><i class="fa fa-star-half-o fa-2x btn btn-default"></i> <span class="qtype-icon-text">Grading</span></a>
										</div>
										<div class="col-sm-1 center btn-qtype">
											<a><i class="fa fa-th fa-2x btn btn-default"></i> <span class="qtype-icon-text">Matrix</span></a>
										</div>
										<div class="col-sm-1 center btn-qtype">
											<a><i class="fa fa-file-code-o fa-2x btn btn-default"></i> <span class="qtype-icon-text">Code</span></a>
										</div>
									</div>
				    			</div>
				    			<div class="survey-frame">
				    				<h3>Basic settings</h3>
				    				<div class="close-frame">
				    					<a class="btn-close-aspa" id="basic-settings" title="Close basic settings"><i class="fa fa-times-circle fa-2x"></i></a>
				    				</div>
				    				<div class="row">
				    					<form>
				    						<div class="col-md-7">
				    							<label for="qstatement" >Question statement</label>
										     	<textarea class="form-control" id="qstatement" rows="5" placeholder="Type here_"></textarea>
											</div>
											<div class="col-md-5 basic-settings-right-options">
												<div class="row">
													<label for="main-version" class="col-md-5" ><i class="fa fa-language fa-2x"></i><span>Main version</span></label>
													<select class="form-control-small col-md-6" id="sel1">
													  	<option value="" disabled selected>Browser languages</option>
													    <option>English</option>
													    <option>Spanish</option>
													    <option>French</option>
													    <option>Greek</option>
													  </select>
												</div>
												<div class="row">
													<label for="main-version" class="col-md-5" ><i class="fa fa-asterisk red"></i><span>Mandatory</span></label>
													<select class="form-control-small col-md-6" id="sel1">
													  	<option value="" disabled selected>Browser languages</option>
													    <option>English</option>
													    <option>Spanish</option>
													    <option>French</option>
													    <option>Greek</option>
													  </select>
												</div>
												<div class="row">
													<label for="main-version" class="col-md-5" ><i class="fa fa-question-circle fa-2x"></i><span>Help text</span></label>
													<select class="form-control-small col-md-6" id="sel1">
													  	<option value="" disabled selected>Browser languages</option>
													    <option>English</option>
													    <option>Spanish</option>
													    <option>French</option>
													    <option>Greek</option>
													  </select>
												</div>
											</div>
				    					</form>
				    				</div>
				    				
				    			</div>				    			
				    		</div> 
				    	</div>
				  	</div>
				</div>
	  			