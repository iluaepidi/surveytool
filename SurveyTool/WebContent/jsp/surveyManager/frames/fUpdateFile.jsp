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
				
				<script>
				 var playText = "<%= lang.getContent("file.player.yt.button.play") %>";
				 var muteText = "<%= lang.getContent("file.player.yt.button.mute") %>";
				 var volDownText = "<%= lang.getContent("file.player.yt.button.volDown") %>";
				 var volUpText = "<%= lang.getContent("file.player.yt.button.volUp") %>";
				 var rewText = "<%= lang.getContent("file.player.yt.button.rew") %>";
				 var fwdText = "<%= lang.getContent("file.player.yt.button.fwd") %>";
				 var pauseText = "<%= lang.getContent("file.player.yt.button.pause") %>";
				 var positionText = "<%= lang.getContent("file.player.yt.dt.position") %>";
				 var secondsText = "<%= lang.getContent("file.player.yt.dd.seconds") %>";
				 var durationText = "<%= lang.getContent("file.player.yt.dt.duration") %>";
				 var unknownText = "<%= lang.getContent("file.player.yt.dd.unknown") %>";
				 var skipVideoText = "<%= lang.getContent("file.player.yt.a.skip_video") %>";
				 var watchYtText = "<%= lang.getContent("file.player.yt.a.watch_yt") %>";
				 var openNewWindowText = "<%= lang.getContent("file.player.yt.a.open_new_window") %>";
				 var finishedText = "<%= lang.getContent("file.player.yt.alert.video_finished") %>";
				 var playingText = "<%= lang.getContent("file.player.yt.alert.playing") %>";
				 var pausedText = "<%= lang.getContent("file.player.yt.alert.paused") %>";
				 var loadingText = "<%= lang.getContent("file.player.yt.alert.loading") %>";
				 var soundActivatedText = "<%= lang.getContent("file.player.yt.alert.sound_activated") %>";
				 var soundDeactivatedText = "<%= lang.getContent("file.player.yt.alert.sound_deactivated") %>";
				 var volumeMaxText = "<%= lang.getContent("file.player.yt.alert.volume_max") %>";
				 var volumeAdjustText = "<%= lang.getContent("file.player.yt.alert.volume_adjust") %>";
				 var volumeMinText = "<%= lang.getContent("file.player.yt.alert.volume_min") %>";
				</script>
				
				<div class="modal fade survey-win" id="updateFile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog import-file-win-dialog" role="document">
				    	<div class="panel panel-primary left"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title"><%= lang.getContent("file.edit.title") %></h2> 
				    		</div> 
				    		<div class="panel-body" id="updateFilesSection">
				    						    		
				    			<div class="form-group" id="previewResource">
								    <label><%= lang.getContent("file.edit.image")%></label>
								    <div id="previewImage" class="previewFileUpliaded hidden">
								    	<img id="imageFilePreview" src="" alt="Preview image. Change alternative text in the next fields." />
								    </div>
								    <div id="previewVideo" class="previewFileUpliaded hidden">
									    <div class="reproductor-youtube">														
											<iframe id="reproductor_preview" type="text/html" src="" height="250" width="400" allowfullscreen="always" frameborder="0" data-title="" tabindex="-1"></iframe>
										</div>
									</div>
						        </div>
								<div class="form-group">
						            <label for="rTitle"><%= lang.getContent("file.edit.titleImagen")%></label>
						            <input type="text" id="rTitle" class="form-control" name="resourceTitle" placeholder="Type here_" value=""/>
						        	<span  id='rTitle-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style='top:30px;right: 20px'></span>
			  						<span id='rTitle-error' class='error hidden' style=''><%= lang.getContent("msg.error.title.text") %></span>	
								</div>
						        <div id="resourceAltText" class="form-group hidden">									            
						            <label for="rAltText"><%= lang.getContent("file.edit.altTextImage")%></label>
						            <textarea rows="2" id="rAltText" name="resourceAltText" class="form-control" placeholder="Type here_"></textarea>
						        	<span  id='rAltText-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style='top:30px;right: 20px'></span>
			  						<span id='rAltText-error' class='error hidden' style=''><%= lang.getContent("msg.error.alttext.text") %></span>	
								</div>
						        <div id="resDescText" class="form-group hidden">									            
						            <label for="rDescText"><%= lang.getContent("file.edit.descTextVideo")%></label>
						            <textarea rows="2" id="rDescText" name="resourceDescText" class="form-control" placeholder="<%= lang.getContent("placeholder.type_here") %>"></textarea>
						        	<span  id='rDescText-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style='top:30px;right: 20px'></span>
			  						<span id='rDescText-error' class='error hidden' style=''><%= lang.getContent("msg.error.desc.text") %></span>	
								</div>
			  					
								<div class="center">
									<button name="upload" id="btnCancelUpdateFile" class="btn btn-default"><%= lang.getContent("button.cancel")%></button>
									<button name="upload" id="btnUpdateFile" class="btn btn-primary"><%= lang.getContent("button.done")%></button> 
								</div>				    			
				    		</div>
				    	</div>
				  	</div>
				</div>
<%
lang.close();
%>
	  			