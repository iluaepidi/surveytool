<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Poll"%>
<%
				Poll poll = (Poll) request.getAttribute(Attribute.s_POLL_INFO);
				
				String host = request.getServerName();
				int port = request.getServerPort();
				
				Language lang = new Language(getServletContext().getRealPath("/")); 
				lang.loadLanguage(Language.getLanguageRequest(request));
				
				/*HttpSession sessions = request.getSession(false); 
  				LoginResponse loginResp = (LoginResponse)sessions.getAttribute(Attribute.s_USER_SESSION_INFO);*/
				%>
				<div class="container-fluid">
	  				<div class="title-content-no-underline">
	  					<h2 id="title-header-edit"><a href="InitialServlet"><%= lang.getContent("user_panel.title") %></a> > <a href="UserPanelHomeServlet?upoption=surveys&tab=poll"><%= lang.getContent("survey_manager.title") %></a> > <%= lang.getContent("poll.edit.title") %></h2>
	  					<div class="right">
		  					<ul class="nav nav-tabs nav-tabs-right nav-tab-edit">
							  	<li role="presentation" class="edit-tab active" id="edit-tab"><a href="#" aria-label="<%= lang.getContent("survey.edit.tab.go_edit") %>" title="<%= lang.getContent("survey.edit.tab.go_edit") %>" id="tab-display-questions"><i class="fa fa-pencil-square-o fa-2x" aria-hidden="true"></i></a></li>
							  	<li role="presentation" class="share-tab" id="share-tab"><a href="http://<%= host %>:<%= port %>/SurveyTool/pollcode?pid=<%= poll.getPublicUrl() %>" title="<%= lang.getContent("survey.edit.tab.go_edit") %>"><i class="fa fa-users fa-2x" aria-hidden="true"></i></a></li>		
							  	<li role="presentation" class="statistic-tab" id="statistic-tab"><a href='<%= Address.s_SERVLET_POLL_STATISTICS + "?" + Parameter.s_POLL_ID + "=" + poll.getPollId() %>' aria-label="<%= lang.getContent("survey.edit.tab.go_statistics") %>" title="<%= lang.getContent("survey.edit.tab.go_statistics") %>" id="tab-display-statistics"><i class="fa fa-bar-chart fa-2x" aria-hidden="true"></i></a></li>
							</ul>
						</div>
	  				</div>
	  				
	  				<div class="content-box-tabs edit-content">
	  					<div class="edit-poll-content" poid="<%= poll.getPollId() %>">
	  						<div role="form" id="editPollForm" class="form-horizontal">
		    					<fieldset class="survey-frame">
			    					<legend><%= lang.getContent("poll.new.legend.info") %></legend>
								
									<div class="row">
										<div class="col-sm-7 margin-bottom-10">
											<label for="pollTitle" class="col-sm-2 control-label left"><%= lang.getContent("poll.new.label.poll_title") %></label>
									   		<div class="col-sm-10">
									     		<div class="form-group" style="margin:0px;">
									     			<input type="text" class="form-control" id="pollTitle" name="title" placeholder="<%= lang.getContent("poll.new.placeholder.poll_title") %>" value="<%= poll.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %>">
									     			<span  id='pollTitle-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
		  											<span id='pollTitle-error' class='error hidden' style='top: 0px;'><%= lang.getContent("msg.error.poll.title") %></span>
									   			</div>
									   		</div>
										</div>
										<div class="col-sm-5">
											<label for="pollProject" class="col-sm-3 control-label left"> <%= lang.getContent("poll.new.label.project") %> </label>
									   		<div class="col-sm-9">
									   			<div class="form-group" style="margin:0px;">
									   				<input type="text" class="form-control" id="pollProject" name="project" placeholder="<%= lang.getContent("poll.new.placeholder.project") %>" value="<%= poll.getProject() %>"/>
									   				<span  id='pollProject-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true'></span>
		  											<span id='pollProject-error' class='error hidden' style='top: 0px;'><%= lang.getContent("msg.error.poll.project") %></span>															
									   			</div>
									   		</div>
										</div>
									</div>										
								</fieldset>
								
								<fieldset class="survey-frame">
			    					<legend><%= lang.getContent("poll.new.legend.question") %></legend>
								
									<div class="margin-bottom-10">
										<div class="form-group" style="margin:0px;">
											<label for="qstatement" ><%= lang.getContent("question.statement") %></label>
								     		<textarea class="form-control" id="qstatement" qid="<%= poll.getQuestion().getQuestionId() %>" rows="3" placeholder="<%= lang.getContent("placeholder.type_here") %>"><%= poll.getQuestion().getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></textarea>									     	
											<span  id='qstatement-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style='top:30px;right: 20px'></span>
		  									<span id='qstatement-error' class='error hidden' style=''><%= lang.getContent("msg.error.qstatement.text") %></span>	
										</div>
									</div>
									
									<fieldset>
							     		<legend><%= lang.getContent("question.edit.legend.options") %></legend>
					     				<% request.setAttribute(Attribute.s_QUESTION, poll.getQuestion()); %>
										<jsp:include page="../editQuestions/eqComponents/eqResponseSettings.jsp" >
											<jsp:param name="response" value="../eqResponses/eqSimple.jsp" />
										</jsp:include>
																		     		
							     	</fieldset>
							     	
								</fieldset>
								
								
						     	<fieldset class="survey-frame">
						     		<legend><%= lang.getContent("poll.new.legend.result") %></legend>
						     		<%
						     		String ackText = "";
						     		if(poll.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_ACKNOWLEDGMENT_TEXT) && poll.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ACKNOWLEDGMENT_TEXT).getText() != null) ackText = poll.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ACKNOWLEDGMENT_TEXT).getText();
						     		%>
						     		<div class="margin-bottom-10">
										<label for="ackText" class="control-label left"><%= lang.getContent("poll.new.label.acknowledgment") %></label>
								   		<textarea class="form-control" id="ackText" rows="3" placeholder="<%= lang.getContent("poll.new.placeholder.acknowledgment") %>"><%= ackText %></textarea>
									</div>
									
									<fieldset>
										<legend><%= lang.getContent("poll.new.legend.call_survey") %></legend>
										<div class="results-page-call">
											<div class="margin-bottom-10">
												<%
										     	String callText = "";
										     	if(poll.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_CALL_TEXT) && poll.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_CALL_TEXT).getText() != null) callText = poll.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_CALL_TEXT).getText();
										     	%>
												<label for="pollCallText" ><%= lang.getContent("poll.new.label.call") %></label>
										     	<textarea class="form-control" id="pollCallText" rows="3" placeholder="<%= lang.getContent("poll.new.placeholder.call") %>"><%= callText %></textarea>									     	
											</div>
											<div class="margin-bottom-10">
												<%
										     	String labelLinkText = "";
										     	if(poll.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_LABEL) && poll.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_LABEL).getText() != null) labelLinkText = poll.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_LABEL).getText();
										     	%>
												<label for="pollLinkLabel" ><%= lang.getContent("poll.new.label.link_label") %></label>
										     	<input type="text" class="form-control" id="pollLinkLabel" name="title" placeholder="<%= lang.getContent("poll.new.placeholder.link_label") %>" value="<%= labelLinkText %>">									     	
											</div>
											<div class="margin-bottom-10">
												<label for="pollLinkUrl" ><%= lang.getContent("poll.new.label.link_url") %></label>
										     	<input type="text" class="form-control" id="pollLinkUrl" name="title" placeholder="<%= lang.getContent("poll.new.placeholder.link_url") %>" value="<%= poll.getCallUrl() %>">									     	
											</div>
										</div>
									</fieldset>
									
						     	</fieldset>
						     	
							</div>
						</div>
	  				</div>
	  			</div>
<%
lang.close();
%>
	  			
	  			<jsp:include page="../frames/fDeleteElement.jsp" />
	  			
	  			