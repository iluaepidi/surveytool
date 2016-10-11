				<%@page import="ilu.surveytool.language.Language"%>
				<%@page import="ilu.surveytool.constants.Attribute"%>
				<%
				String pid = (String) request.getAttribute(Attribute.s_POLL_ID);
				String pollUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/SurveyTool/poll?pid=" + pid;
				
				Language lang = new Language(getServletContext().getRealPath("/")); 
				lang.loadLanguage(Language.getLanguageRequest(request));
				
				int width = 445;
				int height = 232;
				
				String pollCode = "<iframe id=\"iframe\" title=\"" + lang.getContent("poll.preview.iframe.title") + "\" src=\"" + pollUrl + "\" frameborder=\"0\" aria-atomic=\"true\" aria-live=\"assertive\" style=\"width: " + width + "px; height: " + height + "px;\"></iframe>";
				
				%>		
				
				<div class="container-fluid">
					<div class="title-content">
	  					<h2><a href="InitialServlet"><%= lang.getContent("user_panel.title") %></a> > <a href="UserPanelHomeServlet?upoption=surveys"><%= lang.getContent("survey_manager.title") %></a> > <%= lang.getContent("poll.preview.title") %></h2>	  					
	  				</div>
	  				<div class="content">
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
	  			