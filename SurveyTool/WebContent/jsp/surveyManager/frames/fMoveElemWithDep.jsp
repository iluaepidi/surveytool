				
				<!-- Modal -->
				<%@page import="ilu.surveytool.language.Language"%>
				<%@page import="ilu.surveytool.constants.Attribute"%>
				<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
				<%
				Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO); 
				
				Language lang = new Language(getServletContext().getRealPath("/")); 
				lang.loadLanguage(Language.getLanguageRequest(request));
				%>
				<div class="modal fade remove-win" id="moveQuestion" tabindex="-1" role="dialog" aria-labelledby="moveElementTitle">
					<div class="modal-dialog remove-win-dialog" role="document">
				    	<div class="panel panel-primary left"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title" id="moveElementTitle"><%= lang.getContent("confirmation.title") %></h2> 
				    		</div> 
				    		<div class="panel-body">
				    			<div class="survey-frame" id="import-multimedia-file">
				    				<h3><%= lang.getContent("confirmation.move.title") %></h3>
				    				<input type="hidden" id="moveType"/>
			    					<p><%= lang.getContent("confirmation.move.message.withdep") %></p>
			    					
			    					<div class="remove-frame-buttons">
			    						<button class="btn btn-danger" data-dismiss="modal"><%= lang.getContent("button.cancel") %></button>
			    						<button class="btn btn-success btn-accept-move-element" id="acceptMoveElement"><%= lang.getContent("button.accept") %></button>
			    					</div>
				    								    				
				    			</div>				    			
				    		</div>				    		
				    	</div>
				  	</div>
				</div>
				<%
				lang.close();
				%>
	  			