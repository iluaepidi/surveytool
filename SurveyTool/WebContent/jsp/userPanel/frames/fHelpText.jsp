				
				<!-- Modal -->
				<%@page import="ilu.surveytool.constants.Attribute"%>
				<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
				<%
				Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO); 
				%>
				<div class="modal fade survey-win" id="setHelpText" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog survey-win-dialog" role="document">
				    	<div class="panel panel-primary left"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title">Help text</h2> 
				    		</div> 
				    		<div class="panel-body">
				    			<div class="survey-frame" id="import-multimedia-file">
				    				<h3>Type the help text</h3>

			    					<div class="form-group">									            
							            <label for="helpText">Help text</label>
							            <textarea rows="2" id="helpText" name="helpText" class="form-control" placeholder="Type here_"></textarea>
							        </div>
							        
							        <div class="center">
										<button name="upload" id="btnSendHelpText" class="btn btn-primary" aria-label="Send">Send</button> 
									</div>
				    								    				
				    			</div>				    			
				    		</div>
				    		
				    	</div>
				  	</div>
				</div>
	  			