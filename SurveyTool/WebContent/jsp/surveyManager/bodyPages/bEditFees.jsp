<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="java.util.HashMap"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.LoginResponse"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Page"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Section"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.OptionsGroup"%>

<script type="text/javascript">
function changeoptionsfees(){
	var valuesel = $("#selquestionforfees1").val();
	$('.optionsfees').css('display','none');
	$('#optionsfees'+valuesel).css('display','');
}

function createnewfee(){
	var $button = $('#survey-fees-1').clone();
	$('#listcompletefees').appendTo($button);
}
</script>


				<%
				Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO);
				Survey surveyDefaultLanguage = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO_DEFAULT_LANGUAGE);
				int pageId = (int) request.getAttribute(Attribute.s_PAGE_ID);
				String description = "";
				String descriptionplaceholder="";
				if(survey!=null && survey.getContents()!=null && survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION)!=null && !survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText().equals("")){
					description = survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText();
				}else if(surveyDefaultLanguage!=null && surveyDefaultLanguage.getContents()!=null && surveyDefaultLanguage.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION)!=null && !surveyDefaultLanguage.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText().equals("")){
					description = surveyDefaultLanguage.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText();
				}
				
				String title = "";
				String titleplaceholder = "";
				if(survey!=null && survey.getContents()!=null && survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null && !survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText().equals("")){
					title = survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
				}else if(surveyDefaultLanguage!=null && surveyDefaultLanguage.getContents()!=null && surveyDefaultLanguage.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
					title = surveyDefaultLanguage.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
				}
				
				Language lang = new Language(getServletContext().getRealPath("/")); 
				lang.loadLanguage(Language.getLanguageRequest(request));
				
				HttpSession sessions = request.getSession(false); 
  				LoginResponse loginResp = (LoginResponse)sessions.getAttribute(Attribute.s_USER_SESSION_INFO);
				%>
				
				<div class="container-fluid">
	  				<div class="title-content-no-underline">
	  					<h2><a href="InitialServlet"><%= lang.getContent("user_panel.title") %></a> > <a href="UserPanelHomeServlet?upoption=surveys"><%= lang.getContent("survey_manager.title") %></a> > <%= lang.getContent("survey.edit.title") %></h2>
	  					<ul class="nav nav-tabs nav-tabs-right nav-tab-edit">						  	
						  	<li role="presentation" class="statistic-tab"><a href="#" aria-label="go to survey statistics" title="go to survey statistics"><i class="fa fa-bar-chart fa-2x"></i></a></li>
						  	<li role="presentation" class="share-tab active"><a href="#" title="go to share survey"><i class="fa fa-share-alt fa-2x"></i></a></li>
						  	<li role="presentation" class="edit-tab"><a href="SurveysServlet?surveyid=<%=survey.getSurveyId() %>" aria-label="<%= lang.getContent("survey.edit.tab.go_edit") %>" title="<%= lang.getContent("survey.edit.tab.go_edit") %>"><i class="fa fa-pencil-square-o fa-2x"></i></a></li>
						</ul>
	  				</div>
	  				<div class="content-box-tabs edit-content" id="listcompletefees">
	  					<div class="browser-left">Hello</div>	  						  		
	  						<div class="edit-survey-head">
		  						<div class="survey-preview">
		  							
		  						</div>
		  							
		  						<div class="survey-language" id="survey-language">
		  							<form class="" id="survey-language-form">
		  								<label for="survey-language-version" class="" ><i class="fa fa-sliders fa-2x"></i><span><%= lang.getContent("survey.fees.label.objective") %></span></label>
										<input id="email" name="email" type="text" placeholder="" class="form-control-small" value="">
										<%= lang.getContent("survey.fees.label.surveys") %>
		  							</form>
		  						</div>
		  					</div>
		  					
		  					
		  					<%
		  						List<Question> listQuestionFees = new ArrayList();
		  						String titleQuestion="";
		  						List<Section> sections = survey.getSections();
								int i = 1;
								for(Section section : sections)
								{					
									String title22 = "Section " + i;
									if(section.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE))
									{
										title = section.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
									}
									for(Page pag : section.getPages())
									{
										String token = "/";
			  							List<Question> questions = pag.getQuestions();
			  							if(questions != null && !questions.isEmpty()){
			  								for(Question question : questions){
			  									if(question.getQuestionType().equals("simple")||question.getQuestionType().equals("multiple")){
			  										listQuestionFees.add(question);
			  									}
			  									
			  									
			  								}
			  							
			  							}
									}
									
									i++;
								}
							 %>
		  						<div class="edit-fees-frame survey-info" id="survey-fees-1" sid="1">
		  							<ul class="survey-sections" id="survey-sections" style="overflow: hidden;">
			  						<li style="display:block;">
	
			  						<div class="widthTitleSurveyCollapsed" id="survey-div-title-fees" style="width: 90%;" qid="50" sid="<%=survey.getSurveyId()%>">
			  							<div class="form-group" style="margin:0px;">
			  								<div class="form-group">
								                <label class="col-md-4 control-label profileLabel" for="language">Selecciona una pregunta</label>
								                <div class="col-md-8">     
								                	<div class="form-group">                   
								                    	<select id="selquestionforfees1" name="selquestionforfees1" class="form-control" onchange="changeoptionsfees();">
								                    		<% for(Question question : listQuestionFees){
								                    			if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
			    													titleQuestion = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
					    										}
					    									%>
								                    		<option value="<%=question.getQuestionId() %>"><%=titleQuestion %></option>
								                    		<%} %>
								                    	</select>
								                	</div>
								                </div>
								            </div>
				  						</div>
				  						<% for(Question question : listQuestionFees){
				                    			if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
   													titleQuestion = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
	    										}%>
				                    			<% for(OptionsGroup optionsGroup : question.getOptionsGroups()){%>
				                    				<div id="optionsfees<%=question.getQuestionId()%>" style="display:none;" class="optionsfees"  ogid="<%= optionsGroup.getId() %>">
					                    			
				                    				<% 
				                    				for(Option option : optionsGroup.getOptions()){
				                    					int index = i + 1;
					    							%>
							  						<div class="form-group" style="margin:0px;display: inline-flex;" id="optionquota">
							  									<div class="form-group">
							  							     		<label class="col-md-4 control-label profileLabel" for="language"><%= option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></label>
											                	</div>
											                	<div class="form-group col-md-4">
											                		<label class="col-md-4 control-label profileLabel" for="language">Max</label>
											                		<input id="max<%= option.getId() %>" name="max" type="text" placeholder="" class="form-control-small col-md-8" value="" index="<%= index %>" oid="<%= option.getId() %>" >
											                	</div>
											               		<div class="form-group col-md-4">
											                		<label class="col-md-4 control-label profileLabel" for="language">Min</label>
											                		<input id="min<%= option.getId() %>" name="min" type="text" placeholder="" class="form-control-small col-md-8" value="" index="<%= index %>" oid="<%= option.getId() %>" >
											                	</div>
													</div>
												<%}%>
				                    				
				                    		</div>
												<%}%>
				                    	<%}%>
										
				  					</div>
				  					<div class="panel-section-buttons right" style="margin-top:10px;">
										<button class="btn-transparent btn-remove" id="removeQuestion" aria-label="Remove question: question typo formField"><i class="fa fa-trash fa-2x"></i></button>
									</div>					
									</li>
								</ul>
		  					</div>	
							
							<div class="center" id="add-fees">											
								<label for="btn-add-option" class="visuallyhidden">Añadir Cuota</label>
								<button class="btn btn-primary btn-sm active" id="btn-add-option" onclick="createnewfee();"><i class="fa fa-plus-square"></i><span>Añadir Cuota</span></button>
							</div>
		  					
						</div>
						
	  				</div>
	  				
					<script type="text/javascript">
						$('#selquestionforfees1').trigger("change");
						
						$("#selquestionforfees1").change(function(){
							var currentNode = $(this);
							currentNode.closest('.widthTitleSurveyCollapsed').attr('qid',$('#selquestionforfees1').val());
						});
					</script>
	  				
	  				
	  				
<%
lang.close();
%>
	  			