<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.language.Language"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage("en");
%>

	  			<!-- Modal -->
				
				<div class="modal fade survey-win" id="newSurveyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog survey-win-dialog" role="document">
				    	<div class="panel panel-primary"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="<%= lang.getContent("button.close") %> <%= lang.getContent("survey.new.title") %>"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title"><%= lang.getContent("survey.new.title") %></h2> 
				    		</div> 
				    		<div class="panel-body">
			    				<form action="<%= Address.s_SERVLET_CREATE_SURVEY_SERVLET %>" method="POST" class="form-horizontal">
			    					<fieldset class="survey-frame">
				    					<legend><%= lang.getContent("survey.new.legend") %></legend>
									
										<div class="row">
											<div class="col-sm-7 margin-bottom-10">
												<label for="surveyTitle" class="col-sm-2 control-label left"><%= lang.getContent("survey.new.label.survey_title") %></label>
										   		<div class="col-sm-10">
										     			<input type="text" class="form-control" id="surveyTitle" name="title" placeholder="<%= lang.getContent("placeholder.type_here") %>">
										   		</div>
											</div>
											<div class="col-sm-5">
												<label for="surveyProject" class="col-sm-3 control-label left"> <%= lang.getContent("survey.new.label.project") %> </label>
										   		<div class="col-sm-9">
										   			<input type="text" class="form-control" id="surveyProject" name="project" placeholder="<%= lang.getContent("survey.new.placeholder.project") %>"/>														
										   		</div>
											</div>
										</div>
										
										<div class="row">
											<div class="col-sm-9 margin-bottom-10">
												<label for="surveyDescription" class="control-label left"><%= lang.getContent("survey.new.label.short_description") %></label>
										   		<textarea class="form-control" id="surveyDescription" name="description" rows="2" placeholder="<%= lang.getContent("placeholder.type_here") %>"></textarea>
											</div>
											<div class="col-sm-3 center">
												<input type="submit" class="btn btn-primary btn-create-survey" value="<%= lang.getContent("button.create") %>" />
											</div>
										</div>
									</fieldset>
								</form>
				    		</div> 
				    	</div>
				  	</div>
				</div>
<%
lang.close();
%>