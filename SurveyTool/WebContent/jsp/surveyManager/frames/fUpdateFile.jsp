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
System.out.println("fUpdateFile opened");
%>		
				<!-- Modal -->
			
				<div class="modal fade survey-win" id="updateFile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog survey-win-dialog" role="document">
				    	<div class="panel panel-primary left"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title"><%= lang.getContent("file.edit.title") %></h2> 
				    		</div> 
				    		<div class="panel-body" id="updateFilesSection">
				    		
				    		
				    			<div class="form-group" id="">
								    <label><%= lang.getContent("file.edit.image")%></label>
								    <div class="previewFileUpliaded" id="previewFileUploaded">
								    	<img id="imageFilePreview" src="" alt="Preview image. Change alternative text in the next fields." />
								    </div>
								        </div>
										<div class="form-group">
								            <label for="resourceTitle"><%= lang.getContent("file.edit.titleImagen")%></label>
								            <input type="text" id="resourceTitle" class="form-control" name="resourceTitle" placeholder="Type here_" value=""/>
								        </div>
								        <div class="form-group">									            
								            <label for="resourceAltText"><%= lang.getContent("file.edit.altTextImage")%></label>
								            <textarea rows="2" id="resourceAltText" name="resourceAltText" class="form-control" placeholder="Type here_"></textarea>
								        </div>
					  					
										<div class="center">
											<button name="upload" id="btnUpdateFile" class="btn btn-primary"><%= lang.getContent("file.edit.change")%></button> 
										</div>				    			
				    		</div>
				    	</div>
				  	</div>
				</div>
<%
lang.close();
%>
	  			