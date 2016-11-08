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
	  					<h2><a href="InitialServlet"><%= lang.getContent("user_panel.title") %></a> > <a href="UserPanelHomeServlet?upoption=surveys&tab=poll"><%= lang.getContent("survey_manager.title") %></a> > <%= lang.getContent("poll.preview.title") %></h2>
	  					<ul class="nav nav-tabs nav-tabs-right nav-tab-edit">		
						  	<li role="presentation" class="statistic-tab" id="statistic-tab"><a href='<%= Address.s_SERVLET_POLL_STATISTICS + "?" + Parameter.s_POLL_ID + "=" + poll.getPollId() %>' aria-label="<%= lang.getContent("survey.edit.tab.go_statistics") %>" title="<%= lang.getContent("survey.edit.tab.go_statistics") %>" id="tab-display-statistics"><i class="fa fa-bar-chart fa-2x"></i></a></li>
						  	<li role="presentation" class="share-tab active" id="share-tab"><a href="#" title="<%= lang.getContent("survey.edit.tab.go_edit") %>"><i class="fa fa-users fa-2x"></i></a></li>
						  	<li role="presentation" class="edit-tab" id="edit-tab"><a href="<%= Address.s_SERVLET_POLLS_SERVLET + "?" + Parameter.s_POLL_ID + "=" + poll.getPollId() %>" aria-label="<%= lang.getContent("survey.edit.tab.go_edit") %>" title="<%= lang.getContent("survey.edit.tab.go_edit") %>" id="tab-display-questions"><i class="fa fa-pencil-square-o fa-2x"></i></a></li>
						</ul>	  					
	  				</div>
	  				<div class="content-box-tabs edit-content">
	  					<div class="code-poll-frame">
  							<h3><%= lang.getContent("poll.preview.section.preview") %></h3>
	  						<div class="iframe-example">
	  							<iframe id="iframe" title="<%= lang.getContent("poll.preview.iframe.title") %>" src="<%= pollUrl %>" frameborder="0" aria-atomic="true" aria-live="assertive" style="width: <%= width %>px; height: <%= height %>px;"></iframe>
	  						</div>
  						</div>
  						<div class="code-poll-frame">
  							<h3><%= lang.getContent("poll.preview.section.insert") %></h3>
  							<div>
	  							<label for="pollCode" class="sr-only"><%= lang.getContent("poll.preview.label.code") %></label>
	  							<textarea id="pollCode" class="form-control" rows="3"><%= pollCode %></textarea>
	  						</div>
	  						<fieldset class="poll-size">
	  							<legend><%= lang.getContent("poll.preview.legend.size") %></legend>
	  							<input type="number" id="widthPoll" aria-label="<%= lang.getContent("poll.preview.size.label.width") %>" class="form-control" value="<%= width %>">
	  							<span>x</span>
	  							<input type="number" id="heightPoll" aria-label="<%= lang.getContent("poll.preview.size.label.height") %>" class="form-control" value="<%= height %>">
	  							<%= lang.getContent("poll.preview.size.label.pixels") %>
	  						</fieldset>
  						</div>
	  					<div class="code-poll-frame">
  							<h3><%= lang.getContent("poll.preview.section.url") %></h3>
  							<div>
	  							<label for="pollCode" class="sr-only"><%= lang.getContent("poll.preview.label.url") %></label>
	  							<input type="text" id="pollUrl" class="form-control" value="<%= pollUrl %>" />
	  						</div>
  						</div>
	  				</div>
	  			</div>
	  			
				<%
				lang.close();
				%>
	  			