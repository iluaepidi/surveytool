<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
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
  				
  				int numQuestion = 1;
  				request.setAttribute(Attribute.s_NUM_QUESTION, numQuestion);
				%>
				
				<script type="text/javascript">
				hideText = "<%= lang.getContent("general.hide") %>";
				displayText = "<%= lang.getContent("general.display") %>";
				sectionText = "<%= lang.getContent("general.section") %>";
				pageText = "<%= lang.getContent("general.page") %>";
				questionText = "<%= lang.getContent("general.question") %>";
				surveyInfoText = "<%= lang.getContent("general.survey_info") %>";
				
				console.log("hideText: " + hideText);
				</script>
				
	  				<div id="questions">
	  					<div class="browser-left"></div>	  						  		
	  					<div class="edit-content-center">
	  						<div class="edit-survey-head">
		  						<div class="survey-preview">
		  							<button class="btn btn-primary" id="survey-preview_btn"><%= lang.getContent("button.survey_preview") %></button>
		  						</div>
		  							
		  						<div class="survey-language" id="survey-language">
		  							<form class="" id="survey-language-form">
		  								<label for="survey-language-version" class="" ><i class="fa fa-language fa-2x" aria-hidden="true"></i><span><%= lang.getContent("survey.edit.label.lang_version") %></span></label>
										<select class="form-control-small" id="survey-language-version">
											<%
					                    	for (Map.Entry<String, String> entry : loginResp.getListLanguage().entrySet()) {
					                    		if(entry.getKey().equals(surveyDefaultLanguage.getDefaultLanguage())){
						                    	%>
						                    		<option value="<%=entry.getKey() %>"><%=entry.getValue() %> (<%=lang.getContent("survey_manager.surveys.default")%>)</option>
						                    	<%
						                    	} else {%>
						                    		<option value="<%=entry.getKey() %>"><%=entry.getValue() %></option>
						                    		
						                    	<%
						                    	}
					                    	}%>
										    
										</select>
		  							</form>
		  						</div>
		  					</div>
		  					<%
							String hostname = request.getServletContext().getInitParameter("baseUrl");
							%>
		  					<script type="text/javascript">
		  						
			  					if(window.location.href.indexOf("langsurvey") > -1) {
			  						$("#survey-language-version").val(window.location.href.substring(window.location.href.indexOf("langsurvey=")+11));
			  					}else{
			  				    	$("#survey-language-version").val('<%=survey.getDefaultLanguage() %>');
			  				    }
			            		
			            		
			            		$('#survey-preview_btn').click(function(publicid){
			            			
			            			langselect = $('#survey-language-version').val();
			            			window.open('<%= hostname %>/surveyajs?sid=<%=survey.getPublicId()%>&langsurvey=' + langselect + '&preview','_blank');
			            			
			            		});
			            		
			            			$('#survey-language-version').change(function(event) {
			            				
			            				var loc = "http://<%=request.getServerName() %>:<%= request.getServerPort() %>/SurveyTool/SurveysServlet?surveyid=<%=survey.getSurveyId()%>&langsurvey="+$("#survey-language-version").val();
			            				//if(location.href.indexOf("&") !=-1)loc=loc.substring(0,loc.indexOf('&'));
			            				
			            				 window.location=loc;
			            			});
			            			
			            		
			            	</script>
			                
	  						
	  						<% if(request.getAttribute(Attribute.s_ADD_QUESTIONS)!=null && !(boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
							<div class="no-default-survey-frame">
								<p class="no-default-info-title"><span><b><%= lang.getContent("survey_manager.surveys.notice") %></b></span> <%= lang.getContent("survey_manager.surveys.no_default_language") %></p>
							</div>
							<%} %>
	  						
		  					<div class="edit-survey-frame survey-info" id="survey-info" sid="<%= survey.getSurveyId() %>" fid="<%= survey.getFormaId() %>">
		  						<button class="display-default-arrow" id="display-survey-settings" display="false" aria-label="<%= lang.getContent("survey.edit.info.aria_label.display") %>">
		  							<i class="fa fa-caret-right fa-2x" aria-hidden="true"></i>
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
								
								<div class="survey-info-url">
									<label for="clipboard-text" class="col-sm-3 control-label left"><%= lang.getContent("survey.edit.info.label.url") %></label>
							   		<div class="col-sm-9">
							     			<input type="text" class="form-control" name="clipboard-text" id="clipboard-text" value="<%= hostname %>/surveyajs?sid=<%= survey.getPublicId() %>&langsurvey=<%= lang.getCurrentLanguage() %>" readonly="readonly" />							     			
							   		</div>
							   		<!-- <button class="col-sm-1 btn-transparent btn-copy-clipboard" id="target-to-copy" data-clipboard-target="clipboard-text" title="<%= lang.getContent("survey.edit.info.aria_label.copy") %>" aria-label="<%= lang.getContent("survey.edit.info.aria_label.copy") %>"><i class="fa fa-clipboard fa-2x" aria-hidden="true"></i></button> -->
								</div>
								
								<div class="survey-info-ipfilter">
									<div class="checkbox">
									    <label>
									      <input type="checkbox" id="ipFilter" <%if(survey.isIpFilter()){%>checked<%}%>> <%= lang.getContent("survey.edit.info.label.ipfilter") %>
									    </label>
									</div>
								</div>
		  					</div>	
		  					  					
		  					
			  				<jsp:include page="../components/cSections.jsp" />		
								
						</div>
	  				</div>
<%
lang.close();
%>
	  			