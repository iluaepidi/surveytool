<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
<%
Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO); 

Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
System.out.println("fImportFile opened");
%>		
				<!-- Modal -->
			
				<div class="modal fade survey-win" id="importFile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog survey-win-dialog" role="document">
				    	<div class="panel panel-primary left"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title"><%= lang.getContent("file.import.title") %></h2> 
				    		</div> 
				    		<div class="panel-body">
				    			<div class="survey-frame" id="import-multimedia-file">
				    				<h3><%= lang.getContent("file.import.selection.title") %></h3>

			    					<form method="POST" action="ImportFileServlet" id="importFileForm" enctype="multipart/form-data">
			    						<div id="selectFile">			    						
									        <div class="form-group">
									        	<label for="uploadedFile"><%= lang.getContent("file.import.label.input") %></label>
									            <input type="file" name="uploadedFile" class="form-control" id="uploadedFile" />
									        </div>
										</div>
										<div class="hidden" id="optionsFile">
										
										</div>
			    					</form>
				    								    				
				    			</div>				    			
				    		</div>
				    	</div>
				  	</div>
				</div>
<%
lang.close();
%>
	  			