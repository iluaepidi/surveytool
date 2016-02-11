
	  			<!-- Modal -->
				<%@page import="ilu.surveytool.constants.Address"%>
				<div class="modal fade survey-win" id="newPollModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog poll-win-dialog" role="document">
				    	<div class="panel panel-primary"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title">New Poll</h2> 
				    		</div> 
				    		<div class="panel-body">
			    				<form method="POST" id="newPollForm" class="form-horizontal">
			    					<fieldset class="survey-frame">
				    					<legend>Poll info</legend>
									
										<div class="row">
											<div class="col-sm-7 margin-bottom-10">
												<label for="pollTitle" class="col-sm-2 control-label left">Poll title</label>
										   		<div class="col-sm-10">
										     			<input type="text" class="form-control" id="pollTitle" name="title" placeholder="Insert poll title here_">
										   		</div>
											</div>
											<div class="col-sm-5">
												<label for="pollProject" class="col-sm-3 control-label left"> for project </label>
										   		<div class="col-sm-9">
										   			<input type="text" class="form-control" id="pollProject" name="project" placeholder="Insert project name here_"/>														
										   		</div>
											</div>
										</div>										
									</fieldset>
									
									<fieldset class="survey-frame">
				    					<legend>Question settings</legend>
									
										<div class="margin-bottom-10">
											<label for="qstatement" >Question statement</label>
									     	<textarea class="form-control" id="qstatement" rows="3" placeholder="Type here_"></textarea>									     	
										</div>
										
										<fieldset>
								     		<legend>Options</legend>
								     		<ul class="option-list" id="option-list" otype="radio">							  						
					  							<li class="option-item" id="option-item">
				  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
				  									<div class="circle-info circle-grey fleft">1</div>
				  									<input type="text" class="option-title form-control fleft" index="1" placeholder="Option 1"/>
				  									<div class="option-icons fleft">
					  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
					  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
					  									<button class="btn btn-transparent fleft red" id="remove-option-poll" aria-label="remove option"><i class="fa fa-trash fa-2x"></i></button>
					  								</div>
					  							</li>
					  							
					  							<li class="option-item" id="option-item">
				  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
				  									<div class="circle-info circle-grey fleft">2</div>
				  									<input type="text" class="option-title form-control fleft" index="2" placeholder="Option 2"/>
				  									<div class="option-icons fleft">
					  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
					  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
					  									<button class="btn btn-transparent fleft red" id="remove-option-poll" aria-label="remove option"><i class="fa fa-trash fa-2x"></i></button>
					  								</div>
					  							</li>
					  						
					  							<li class="center" id="li-add-option-poll">
					  								<a class="btn-plus-small" id="btn-add-option-poll" href="#li-add-option-poll" aria-label="Add option"><i class="fa fa-plus-square"></i></a>
					  							</li>
					  						</ul>								     		
								     	</fieldset>
								     	
									</fieldset>
									
									
							     	<fieldset class="survey-frame">
							     		<legend>Results page</legend>
							     		
							     		<div class="margin-bottom-10">
											<label for="ackText" class="control-label left">Acknowledgment text</label>
									   		<textarea class="form-control" id="ackText" rows="3" placeholder="Type here_ (e.g. Thank you for your participation)"></textarea>
										</div>
										
										<fieldset>
											<legend>Insert call to survey / user panel</legend>
											<div class="results-page-call">
												<div class="margin-bottom-10">
													<label for="pollCallText" >Call text</label>
											     	<textarea class="form-control" id="pollCallText" rows="3" placeholder="Type here_ (e.g. If you want to participate in other surveys, fill in the registar form)"></textarea>									     	
												</div>
												<div class="margin-bottom-10">
													<label for="pollLinkLabel" >Link label</label>
											     	<input type="text" class="form-control" id="pollLinkLabel" name="title" placeholder="Type here_ (e.g. Go to register form)">									     	
												</div>
												<div class="margin-bottom-10">
													<label for="pollLinkUrl" >Link URL</label>
											     	<input type="text" class="form-control" id="pollLinkUrl" name="title" placeholder="Type here_ (e.g. http://www.surveytool.com)">									     	
												</div>
											</div>
										</fieldset>
										
							     	</fieldset>
							     	
							     	<div class="center">
										<button name="createNewPoll" id="btnCreateNewPoll" class="btn btn-primary" aria-label="Create new poll">Create</button> 
									</div>
								</form>
				    		</div> 
				    	</div>
				  	</div>
				</div>