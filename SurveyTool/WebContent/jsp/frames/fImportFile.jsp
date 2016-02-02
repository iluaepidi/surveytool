				
				<!-- Modal -->
				<%@page import="ilu.surveytool.constants.Attribute"%>
				<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
				<%
				Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO); 
				%>
				<div class="modal fade survey-win" id="importFile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog survey-win-dialog" role="document">
				    	<div class="panel panel-primary left"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title">Import multimedia file</h2> 
				    		</div> 
				    		<div class="panel-body">
				    			<div class="survey-frame" id="import-multimedia-file">
				    				<h3>Select a multimedia file</h3>

			    					<form method="POST" action="ImportFileServlet" id="importFileForm" enctype="multipart/form-data">
			    						<div id="selectFile">			    						
									        <div class="form-group">
									        	<label for="uploadedFile">File input</label>
									            <input type="file" name="uploadedFile" class="form-control" id="uploadedFile" />
									        </div>
										</div>
										<div class="hidden" id="optionsFile">
										
										</div>
			    					</form>
				    								    				
				    			</div>				    			
				    		</div>
				    		<!-- <div class="panel-body">
				    			<div class="survey-frame" id="import-multimedia-file">
				    				<h3>Select a multimedia file</h3>
				    				
			    					<form method="POST" action="ImportFileServlet" id="importFileForm" enctype="multipart/form-data">
			    						
			    						<div class="form-group">
								            <label for="resourceTitle">Title</label>
								            <input type="text" id="resourceTitle" class="form-control" name="resourceTitle" placeholder="Type here_"/>
								        </div>
								        <div class="form-group">
								        	<label for="uploadedFile">File input</label>
								            <input type="file" name="uploadedFile" class="form-control" id="uploadedFile" />
								        </div>
								        <div class="form-group">									            
								            <label for="resourceAltText">Texto alternativo</label>
								            <textarea rows="2" id="resourceAltText" name="resourceAltText" class="form-control" placeholder="Type here_"></textarea>
								        </div>
								            
								            <!-- <input type="submit" value="Upload" name="upload" id="upload" />  data-dismiss="modal" aria-label="Close" -->
			    						
										<!--  <div class="center">
											<button name="upload" id="btnImportFile" class="btn btn-primary" aria-label="Import">Import</button> 
										</div>
			    					</form>
				    								    				
				    			</div>				    			
				    		</div> -->
				    	</div>
				  	</div>
				</div>
	  			