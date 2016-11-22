				<!-- Modal -->
				<%@page import="ilu.surveytool.language.Language"%>
				<%
				
				Language lang = new Language(getServletContext().getRealPath("/")); 
				lang.loadLanguage(Language.getLanguageRequest(request));
				%>
	  			
				<!-- Modal -->
				<div class="modal fade remove-win" id="accesibilityHelpWin" tabindex="-1" role="dialog" aria-labelledby="accessibilityEditorTitle">
					<div class="modal-dialog accesibility-win-dialog" role="document">
				    	<div class="panel panel-primary left"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title" id="accessibilityEditorTitle"><%= lang.getContent("accesibility.window.title") %></h2> 
				    		</div> 
				    		<div class="panel-body">
				    			<div class="survey-frame accesibility" id="import-multimedia-file">
				    				<h3><%= lang.getContent("accesibility.window.subtitle") %></h3>
				    				
				    				<p><%= lang.getContent("accesibility.window.info") %></p>
			    					
			    					<div class="center">
			    						<button class="btn btn-sm btn-primary" data-dismiss="modal"><%= lang.getContent("button.close") %></button>
			    					</div>
				    								    				
				    			</div>				    			
				    		</div>				    		
				    	</div>
				  	</div>
				</div>
				<%
				lang.close();
				%>