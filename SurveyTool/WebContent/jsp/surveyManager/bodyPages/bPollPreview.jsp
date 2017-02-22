				<%@page import="ilu.surveytool.databasemanager.DataObject.Poll"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.language.Language"%>
				<%@page import="ilu.surveytool.constants.Attribute"%>
				<%
				Poll poll = (Poll) request.getAttribute(Attribute.s_POLL_INFO);
				String pollUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/SurveyTool/poll?pid=" + poll.getPublicUrl();
				
				Language lang = new Language(getServletContext().getRealPath("/")); 
				lang.loadLanguage(Language.getLanguageRequest(request));
				
				int width = 445;
				int height = 232;
				
				String pollCode = "<iframe id=\"iframe\" title=\"" + lang.getContent("poll.preview.iframe.title") + "\" src=\"" + pollUrl + "\" frameborder=\"0\" aria-atomic=\"true\" aria-live=\"assertive\" style=\"width: " + width + "px; height: " + height + "px;\"></iframe>";
				
				%>		
				
				<div class="container-fluid">
					<div class="title-content-no-underline">
	  					<h2><a href="UserPanelHomeServlet?upoption=surveys&tab=poll"><%= lang.getContent("survey_manager.title") %></a> > <%= lang.getContent("poll.preview.title") %></h2>
	  					<ul class="nav nav-tabs nav-tabs-right nav-tab-edit">		
						  	<li role="presentation" class="statistic-tab" id="statistic-tab"><a href="#" aria-label="<%= lang.getContent("survey.edit.tab.go_statistics") %>" title="<%= lang.getContent("survey.edit.tab.go_statistics") %>" id="tab-display-statistics"><i class="fa fa-bar-chart fa-2x" aria-hidden="true"></i></a></li>
						  	<li role="presentation" class="share-tab active" id="share-tab"><a href="#" title="<%= lang.getContent("survey.edit.tab.go_edit") %>"><i class="fa fa-users fa-2x" aria-hidden="true"></i></a></li>
						  	<li role="presentation" class="edit-tab" id="edit-tab"><a href="<%= Address.s_SERVLET_POLLS_SERVLET + "?" + Parameter.s_POLL_ID + "=" + poll.getPollId() %>" aria-label="<%= lang.getContent("survey.edit.tab.go_edit") %>" title="<%= lang.getContent("survey.edit.tab.go_edit") %>" id="tab-display-questions"><i class="fa fa-pencil-square-o fa-2x" aria-hidden="true"></i></a></li>
						</ul>	  					
	  				</div>
	  				<div class="content-box-tabs edit-content">
	  					<div class="code-poll-frame">
  							<h3><%= lang.getContent("poll.preview.section.preview") %></h3>
	  						<div class="iframe-example">
	  							<iframe id="iframe" title="<%= lang.getContent("poll.preview.iframe.title") %>" src="<%= pollUrl + "&preview" %>" frameborder="0" aria-atomic="true" aria-live="assertive" style="width: <%= width %>px; height: <%= height %>px;"></iframe>
	  						</div>
  						</div>
	  				</div>
	  			</div>
	  			
				<%
				lang.close();
				%>
	  			