				<%@page import="ilu.surveytool.language.Language"%>
				<%@page import="ilu.surveytool.constants.Attribute"%>
				<%
				String pid = (String) request.getAttribute(Attribute.s_POLL_ID);
				String pollUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/SurveyTool/poll?pid=" + pid;
				
				Language lang = new Language(getServletContext().getRealPath("/")); 
				lang.loadLanguage("en");
				%>		
				
				<div class="container-fluid">
					<div class="title-content">
	  					<h2><a href="InitialServlet"><%= lang.getContent("user_panel.title") %></a> > <a href="UserPanelHomeServlet?upoption=surveys"><%= lang.getContent("survey_manager.title") %></a> > <%= lang.getContent("poll.preview.title") %></h2>	  					
	  				</div>
	  				<div class="content">
  						<div class="iframe-example">
  							<iframe id="iframe" title="<%= lang.getContent("poll.preview.iframe.title") %>" src="<%= pollUrl %>" frameborder="0" aria-atomic="true" aria-live="assertive" style="width: 100%; height: 232px;"></iframe>
  						</div>
	  				</div>
	  			</div>
	  			
				<%
				lang.close();
				%>
	  			