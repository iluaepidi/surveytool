<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="java.util.HashMap"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.LoginResponse"%>
				<%@page import="java.util.Map"%>
				<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
				<script type="text/javascript">
					surveyTree = <%= request.getAttribute(Attribute.s_JSON_PAGES) %>;
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
						  	<li role="presentation" class="share-tab"><a href="#" title="go to share survey"><i class="fa fa-share-alt fa-2x"></i></a></li>
						  	<li role="presentation" class="edit-tab active"><a href="#" aria-label="<%= lang.getContent("survey.edit.tab.go_edit") %>" title="<%= lang.getContent("survey.edit.tab.go_edit") %>"><i class="fa fa-pencil-square-o fa-2x"></i></a></li>
						</ul>
	  				</div>
	  				<div class="content-box-tabs edit-content">
	  					<div class="browser-left">Hello</div>	  						  		
	  					<div class="edit-content-center">
	  						<div class="edit-survey-head">
		  						<div class="survey-preview">
		  							<button class="btn btn-primary" id="survey-preview_btn"><%= lang.getContent("button.survey_preview") %></button>
		  						</div>
		  							
		  						<div class="survey-language" id="survey-language">
		  							<form class="" id="survey-language-form">
		  								<label for="survey-language-version" class="" ><i class="fa fa-language fa-2x"></i><span><%= lang.getContent("survey.edit.label.lang_version") %></span></label>
										<select class="form-control-small" id="survey-language-version">
											<%
					                    	for (Map.Entry<String, String> entry : loginResp.getListLanguage().entrySet()) {%>
					                    		<option value="<%=entry.getKey() %>"><%=entry.getValue() %></option>
					                    		
					                    	<%}%>
										    
										</select>
		  							</form>
		  						</div>
		  					</div>
		  					
		  					<script type="text/javascript">
		  						
			  					if(window.location.href.indexOf("langsurvey") > -1) {
			  						$("#survey-language-version").val(window.location.href.substring(window.location.href.indexOf("langsurvey=")+11));
			  					}else{
			  				    	$("#survey-language-version").val('<%=survey.getDefaultLanguage() %>');
			  				    }
			            		
			            		
			            		$('#survey-preview_btn').click(function(publicid){
			            			
			            			langselect = $('#survey-language-version').val();
			            			window.open('http://<%=request.getServerName() %>:<%= request.getServerPort() %>/SurveyTool/survey?sid=<%=survey.getPublicId()%>&langsurvey='+langselect,'_blank');
			            			
			            		});
			            		
			            			$('#survey-language-version').change(function(event) {
			            				
			            				var loc = "http://<%=request.getServerName() %>:<%= request.getServerPort() %>/SurveyTool/SurveysServlet?surveyid=<%=survey.getSurveyId()%>&langsurvey="+$("#survey-language-version").val();
			            				//if(location.href.indexOf("&") !=-1)loc=loc.substring(0,loc.indexOf('&'));
			            				
			            				 window.location=loc;
			            			});
			            			
			            		
			            	</script>
			                
	  						
		  					<div class="edit-survey-frame survey-info" id="survey-info" sid="<%= survey.getSurveyId() %>">
		  						<button class="display-default-arrow" id="display-survey-settings" display="false" aria-label="<%= lang.getContent("survey.edit.info.aria_label.display") %>">
		  							<i class="fa fa-caret-right fa-2x"></i>
		  						</button>

		  						<div class="widthTitleSurveyCollapsed" id="survey-div-title">
		  							<div class="form-group" style="margin:0px;">
		  								<input type="text" class="survey-info-title" id="survey-info-title" aria-label="<%= lang.getContent("survey.edit.info.aria_label.title") %>" value="<%= title %>" placeholder="<%= titleplaceholder %>"/>
		  								<span  id='survey-info-title-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
			  							<span id='survey-info-title-error' class='error hidden' style='top: 0px'><%= lang.getContent("msg.error.newsurvey.title") %></span>	
			  						</div>
			  					</div>					

		  						<div class="survey-info-project">
									<label for="surveyProject" class="col-sm-4 control-label"> <%= lang.getContent("survey.edit.info.label.project") %> </label>
							   		<div class="col-sm-8">
							   			<div class="form-group" style="margin:0px;">
							   				<input type="text" class="form-control" id="surveyProject" name="project" value="<%= survey.getProject() %>" />
							   				<span  id='surveyProject-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style='right: 20px'></span>
			  								<span id='surveyProject-error' class='error hidden' style='top: 0px;'><%= lang.getContent("msg.error.newsurvey.project") %></span>	
							   			</div>
							   		</div>
								</div>
								
								<div class="survey-info-description">
									<label for="surveyDescription" class="col-sm-3 control-label left"><%= lang.getContent("survey.edit.info.label.short_description") %></label>
							   		<div class="col-sm-9">
							     			<textarea class="form-control" id="surveyDescription" rows="2" placeholder="<%=descriptionplaceholder%>"><%= description %></textarea>
							   		</div>
								</div>
								
								<!-- <div class="survey-info-url">
									<label for="clipboard-text" class="col-sm-3 control-label left"><%= lang.getContent("survey.edit.info.label.url") %></label>
							   		<div class="col-sm-8">
							     			<input type="text" class="form-control" name="clipboard-text" id="clipboard-text" value="http://<%= request.getServerName() %>:<%= request.getServerPort() %>/SurveyTool/survey?sid=<%= survey.getPublicId() %>" disabled />
							     			
							   		</div>
							   		<button class="col-sm-1 btn-transparent btn-copy-clipboard" id="target-to-copy" data-clipboard-target="clipboard-text" title="<%= lang.getContent("survey.edit.info.aria_label.copy") %>" aria-label="<%= lang.getContent("survey.edit.info.aria_label.copy") %>"><i class="fa fa-clipboard fa-2x"></i></button>
								</div> -->
		  					</div>	
		  					  					
		  					
			  				<jsp:include page="../components/cSections.jsp" />		
								
						</div>
	  				</div>
	  			</div>
<%
lang.close();
%>
	  			
	  			<jsp:include page="../frames/fNewQuestion.jsp" />
	  			
	  			<jsp:include page="../frames/fImportFile.jsp" />
	  			
	  			<jsp:include page="../frames/fUpdateFile.jsp" />
	  			
	  			<jsp:include page="../frames/fDeleteElement.jsp" />
	  			