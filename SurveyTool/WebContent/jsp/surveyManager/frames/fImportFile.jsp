<%@page import="ilu.surveymanager.handler.ResourceHandler"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.ResourceType"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Content"%>
<%@page import="java.util.HashMap"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%
Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO); 

Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
System.out.println("fImportFile opened");
%>		
				<!-- Modal -->
			
				<div class="modal fade survey-win" id="importFile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog import-file-win-dialog" role="document">
				    	<div class="panel panel-primary left"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title"><%= lang.getContent("file.import.title") %></h2> 
				    		</div> 
				    		<div class="panel-body">
				    			<div class="survey-frame" id="import-multimedia-file">
				    				<h3><%= lang.getContent("file.import.selection.title") %></h3>

			    					<form method="POST" action="ImportFileServlet" id="importFileForm" enctype="multipart/form-data">
			    						<div id="selectFiteType" class="form-group file-type">
			    							<label for="fileType"><%= lang.getContent("file.import.label.select.type") %></label>
			    							<select id="fileType" class="form-control resource-type-select">
			    								<option value="none"><%= lang.getContent("file.import.label.select.none") %></option>
			    							<%
			    								ResourceHandler resourceHandler = new ResourceHandler(); 
			    								List<ResourceType> resourceTypes = resourceHandler.getResourceTypes();
			    								for(ResourceType resourceType : resourceTypes)
			    								{
			    							%>
			    								<option value="<%= resourceType.getName() %>"><%= resourceType.getName().substring(0, 1).toUpperCase() + resourceType.getName().substring(1) %></option>			    								
			    							<%
			    								}
			    							%>
			    							</select>
			    						</div>			    					
			    						<div id="selectFile" class="hidden">			    						
									        <div class="form-group">
									        	<label for="uploadedFile"><%= lang.getContent("file.import.label.input") %></label>
									            <input type="file" name="uploadedFile" class="form-control" id="uploadedFile" />
									        </div>
										</div>
										<div id="optionsFile" class="hidden">
											
										</div>
										<div id="optionsVideoFile" class="hidden">
											<jsp:include page="../components/cImportVideoOptions.jsp" />
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
	  			