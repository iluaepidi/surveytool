				<%@page import="ilu.surveytool.constants.Attribute"%>
				<%
				String pid = (String) request.getAttribute(Attribute.s_POLL_ID);
				String pollUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/SurveyTool/poll?pid=" + pid;
				%>		
				
				<div class="container-fluid">
					<div class="title-content">
	  					<h2><a href="InitialServlet">User Panel</a> > <a href="UserPanelHomeServlet?upoption=surveys">Survey Manager</a> > Poll Preview</h2>	  					
	  				</div>
	  				<div class="content">
  						<div class="iframe-example">
  							<iframe id="iframe" title="widget poll survey" src="<%= pollUrl %>" frameborder="0" aria-atomic="true" aria-live="assertive" style="width: 100%; height: 232px;"></iframe>
  						</div>
	  				</div>
	  			</div>
	  			