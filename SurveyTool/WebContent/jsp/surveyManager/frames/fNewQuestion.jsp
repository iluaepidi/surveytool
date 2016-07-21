<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
<%
Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO); 

Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>	
				<!-- Modal -->
				<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
				<div class="modal fade survey-win" id="newQuestionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog survey-win-dialog" role="document">
				    	<div class="panel panel-primary"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="<%= lang.getContent("button.close") %> <%= lang.getContent("question.new.title") %>"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title"><%= lang.getContent("question.new.title") %></h2> 
				    		</div> 
				    		<div class="panel-body">
				    			<fieldset class="survey-frame">
				    				<legend><%= lang.getContent("question.new.question_type") %></legend>
									<ul class="row qtype-list">
										<li class="col-sm-1 center btn-qtype">
											<button class="btn-transparent" id="shortText"><i class="fa fa-square-o fa-2x btn btn-default"></i> <span class="qtype-icon-text"><%=lang.getContent("question.new.formfield")%></span></button>
										</li>
										<li class="col-sm-1 center btn-qtype">
											<button class="btn-transparent" id="longText"><i class="fa fa-align-justify fa-2x btn btn-default"></i> <span class="qtype-icon-text"><%=lang.getContent("question.new.paragraph")%></span></button>
										</li> 
										<li class="col-sm-1 center btn-qtype">
											<button class="btn-transparent" id="multiple"><i class="fa fa-list fa-2x btn btn-default"></i> <span class="qtype-icon-text"><%=lang.getContent("question.new.multiple")%></span></button>
										</li>
										<li class="col-sm-1 center btn-qtype">
											<button class="btn-transparent" id="simple"><i class="fa fa-list-ul fa-2x btn btn-default"></i> <span class="qtype-icon-text"><%=lang.getContent("question.new.simple")%></span></button>
										</li>
										<!-- <li class="col-sm-1 center btn-qtype">
											<button class="btn-transparent" id="ordering"><i class="fa fa-sort-numeric-asc fa-2x btn btn-default"></i> <span class="qtype-icon-text"><%=lang.getContent("question.new.ordering")%></span></button>
										</li> -->
										<!-- <li class="col-sm-1 center btn-qtype">
											<button class="btn-transparent" id="grading"><i class="fa fa-sort-numeric-asc fa-2x btn btn-default"></i> <span class="qtype-icon-text"><%=lang.getContent("question.new.grading")%></span></button>
										</li> -->
										<li class="col-sm-1 center btn-qtype">
											<button class="btn-transparent" id="matrix"><i class="fa fa-th fa-2x btn btn-default"></i> <span class="qtype-icon-text"><%=lang.getContent("question.new.matrix")%></span></button>
										</li>
										<li class="col-sm-1 center btn-qtype">
											<button class="btn-transparent" id="scale"><i class="fa fa-th fa-2x btn btn-default"></i> <span class="qtype-icon-text"><%=lang.getContent("question.new.scale")%></span></button>
										</li>
										<!--  <div class="col-sm-1 center btn-qtype">
											<a id="code"><i class="fa fa-file-code-o fa-2x btn btn-default"></i> <span class="qtype-icon-text"><%= lang.getContent("question.new.code")%></span></a>
										</div>--> 
									</ul>
				    			</fieldset>
				    			<div class="survey-frame frame-basic-Settings" id="frame-basic-Settings">
				    				<h3><%= lang.getContent("question.new.basic_settings.title") %></h3>
				    				<div class="close-frame">
				    					<button class="btn-transparent btn-close-aspa" id="basic-settings-close" title="Close basic settings" aria-label="<%= lang.getContent("button.close") %> <%= lang.getContent("question.new.basic_settings.title") %>"><i class="fa fa-times-circle fa-2x"></i></button>
				    				</div>
				    				<div class="row">
				    					<form>
				    						<input type="hidden" id="qtypevalue" name="qtype" value="" />
				    						<input type="hidden" id="surveyid" name="surveyid" value="<%= survey.getSurveyId() %>" />
				    						<div class="col-md-7">
				    							<div class="form-group" style="margin:0px;">
				    								<label for="qstatement" ><%= lang.getContent("question.statement") %></label>
										     		<textarea class="form-control" id="qstatement" rows="5" placeholder="<%= lang.getContent("placeholder.type_here") %>"></textarea>
													<span  id='qstatement-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style='top:30px;right: 20px'></span>
			  										<span id='qstatement-error' class='error hidden' style=''><%= lang.getContent("msg.error.qstatement.text") %></span>	
												</div>
											</div>
											<div class="col-md-5 basic-settings-right-options">
												<div class="div-create-question-mandatory row">
													<label for="mandatory" class="col-md-5" ><i class="fa fa-asterisk red"></i><span><%= lang.getContent("question.mandatory") %></span></label>
													<select class="form-control-small col-md-6" id="mandatory">
													  	<option value="false" selected><%= lang.getContent("label.no") %></option>
													    <option value="true"><%= lang.getContent("label.yes") %></option>
													  </select>
												</div>
												<!-- <div class="div-create-question-optional-answer row">
													<label for="askAlways" class="col-md-5" ><i class="fa fa-unlock-alt red"></i><span><%= lang.getContent("question.notAnswerRequeried") %></span></label>
													<select class="form-control-small col-md-6" id="askAlways">
													  	<option value="false" selected><%= lang.getContent("label.no") %></option>
													    <option value="true"><%= lang.getContent("label.yes") %></option>
													  </select>
												</div> -->
												<div class="div-btn-create-question center">
													<input type="button" value="<%= lang.getContent("button.create") %>" class="btn btn-primary" id="create-question"/>
												</div>
											</div>
				    					</form>
				    				</div>				    				
				    			</div>				    			
				    		</div> 
				    	</div>
				  	</div>
				</div>
	  			