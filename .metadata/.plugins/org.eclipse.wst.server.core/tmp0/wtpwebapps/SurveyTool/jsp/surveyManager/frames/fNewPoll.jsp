<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.language.Language"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>

	  			<!-- Modal -->
				
				<div class="modal fade survey-win" id="newPollModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog poll-win-dialog" role="document">
				    	<div class="panel panel-primary"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="<%= lang.getContent("button.close") %>"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title"><%= lang.getContent("poll.new.title") %></h2> 
				    		</div> 
				    		<div class="panel-body">
			    				<form method="POST" id="newPollForm" class="form-horizontal">
			    					<fieldset class="survey-frame">
				    					<legend><%= lang.getContent("poll.new.legend.info") %></legend>
									
										<div class="row">
											<div class="col-sm-7 margin-bottom-10">
												<label for="pollTitle" class="col-sm-2 control-label left"><%= lang.getContent("poll.new.label.poll_title") %></label>
										   		<div class="col-sm-10">
										     		<div class="form-group" style="margin:0px;">
										     			<input type="text" class="form-control" id="pollTitle" name="title" placeholder="<%= lang.getContent("poll.new.placeholder.poll_title") %>">
										     			<span  id='pollTitle-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
			  											<span id='pollTitle-error' class='error hidden' style='top: 0px;'><%= lang.getContent("msg.error.poll.title") %></span>
										   			</div>
										   		</div>
											</div>
											<div class="col-sm-5">
												<label for="pollProject" class="col-sm-3 control-label left"> <%= lang.getContent("poll.new.label.project") %> </label>
										   		<div class="col-sm-9">
										   			<div class="form-group" style="margin:0px;">
										   				<input type="text" class="form-control" id="pollProject" name="project" placeholder="<%= lang.getContent("poll.new.placeholder.project") %>"/>
										   				<span  id='pollProject-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
			  											<span id='pollProject-error' class='error hidden' style='top: 0px;'><%= lang.getContent("msg.error.poll.project") %></span>															
										   			</div>
										   		</div>
											</div>
										</div>										
									</fieldset>
									
									<fieldset class="survey-frame">
				    					<legend><%= lang.getContent("poll.new.legend.question") %></legend>
									
										<div class="margin-bottom-10">
											<div class="form-group" style="margin:0px;">
												<label for="qstatement" ><%= lang.getContent("question.statement") %></label>
									     		<textarea class="form-control" id="qstatement" rows="3" placeholder="<%= lang.getContent("placeholder.type_here") %>"></textarea>									     	
												<span  id='qstatement-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style='top:30px;right: 20px'></span>
			  									<span id='qstatement-error' class='error hidden' style=''><%= lang.getContent("msg.error.qstatement.text") %></span>	
											</div>
										</div>
										
										<fieldset>
								     		<legend><%= lang.getContent("question.edit.legend.options") %></legend>
								     		<ul class="option-list" id="option-list" otype="radio">							  						
					  							<li class="option-item" id="option-item">
				  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
				  									<div class="circle-info circle-grey fleft">1</div>
				  									<div class="col-sm-8">
				  										<div class="form-group" style="margin:0px;">
				  											<input type="text" id='option1' class="form-control fleft" index="1" placeholder="<%= lang.getContent("question.edit.placeholder.option") %> 1" aria-label="<%= lang.getContent("question.edit.aria_label.option") %> 1" style="width: 100%; !important;"/>
				  											<span  id='option1-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style="color:#A94442;right: 20px;"></span>
			  												<span  id='option1-error' class='error hidden' style='top: 0px;right: -180px;'><%= lang.getContent("msg.error.poll.option") %></span>	
				  										</div>
				  									</div>
				  									<div class="option-icons fleft">
					  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
					  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
					  									<button class="btn btn-transparent fleft red" id="remove-option-poll" aria-label="<%= lang.getContent("button.remove_option") %> 1"><i class="fa fa-trash fa-2x"></i></button>
					  								</div>
					  							</li>
					  							
					  							<li class="option-item" id="option-item">
				  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
				  									<div class="circle-info circle-grey fleft">2</div>
				  									<div class="col-sm-8">
				  										<div class="form-group" style="margin:0px;">
				  											<input type="text" id='option2' class="form-control fleft" index="2" placeholder="<%= lang.getContent("question.edit.placeholder.option") %> 2" aria-label="<%= lang.getContent("question.edit.aria_label.option") %> 2" style="width: 100%; !important;"/>
				  											<span  id='option2-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style="color:#A94442;right: 20px;"></span>
			  												<span  id='option2-error' class='error hidden' style='top: 0px;right: -180px;'><%= lang.getContent("msg.error.poll.option") %></span>	
				  										</div>
				  									</div>
				  									<div class="option-icons fleft">
					  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
					  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
					  									<button class="btn btn-transparent fleft red" id="remove-option-poll" aria-label="<%= lang.getContent("button.remove_option") %> 2"><i class="fa fa-trash fa-2x"></i></button>
					  								</div>
					  							</li>
					  						
					  							<li class="center" id="li-add-option-poll">
					  								<button class="btn btn-primary btn-sm active" id="btn-add-option-poll" ><i class="fa fa-plus-square"></i><span><%= lang.getContent("button.add_option") %></span></button>
					  							</li>
					  						</ul>								     		
								     	</fieldset>
								     	
									</fieldset>
									
									
							     	<fieldset class="survey-frame">
							     		<legend><%= lang.getContent("poll.new.legend.result") %></legend>
							     		
							     		<div class="margin-bottom-10">
											<label for="ackText" class="control-label left"><%= lang.getContent("poll.new.label.acknowledgment") %></label>
									   		<textarea class="form-control" id="ackText" rows="3" placeholder="<%= lang.getContent("poll.new.placeholder.acknowledgment") %>"></textarea>
										</div>
										
										<fieldset>
											<legend><%= lang.getContent("poll.new.legend.call_survey") %></legend>
											<div class="results-page-call">
												<div class="margin-bottom-10">
													<label for="pollCallText" ><%= lang.getContent("poll.new.label.call") %></label>
											     	<textarea class="form-control" id="pollCallText" rows="3" placeholder="<%= lang.getContent("poll.new.placeholder.call") %>"></textarea>									     	
												</div>
												<div class="margin-bottom-10">
													<label for="pollLinkLabel" ><%= lang.getContent("poll.new.label.link_label") %></label>
											     	<input type="text" class="form-control" id="pollLinkLabel" name="title" placeholder="<%= lang.getContent("poll.new.placeholder.link_label") %>">									     	
												</div>
												<div class="margin-bottom-10">
													<label for="pollLinkUrl" ><%= lang.getContent("poll.new.label.link_url") %></label>
											     	<input type="text" class="form-control" id="pollLinkUrl" name="title" placeholder="<%= lang.getContent("poll.new.placeholder.link_url") %>">									     	
												</div>
											</div>
										</fieldset>
										
							     	</fieldset>
							     	
							     	<div class="center">
							     	    <input type="button" name="createNewPoll" id="btnCreateNewPoll" class="btn btn-primary" value="<%= lang.getContent("button.create") %>"/> 
									</div>
								</form>
				    		</div> 
				    	</div>
				  	</div>
				</div>
<%
lang.close();
%>