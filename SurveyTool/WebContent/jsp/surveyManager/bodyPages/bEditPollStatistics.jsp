

<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Poll"%>

<!--<script language="JavaScript" src="http://www.geoplugin.net/javascript.gp" type="text/javascript"></script> Esto es para obtener la IP y la localización -->
<%
Poll poll = (Poll) request.getAttribute(Attribute.s_POLL_INFO);
Question question = poll.getQuestion();
String token = "/";
Language lang = new Language(getServletContext().getRealPath("/")); 
String languageId = Language.getLanguageRequest(request);
lang.loadLanguage(languageId);

%>

<div class="container-fluid">




	<div class="title-content-no-underline">
		<h2 id="title-header-edit"><a href="UserPanelHomeServlet?upoption=surveys&tab=poll"><%= lang.getContent("survey_manager.title") %></a> > <%= lang.getContent("survey.statistic.title") %></h2>
		<div class="right">
			<ul class="nav nav-tabs nav-tabs-right nav-tab-edit">
			  	<li role="presentation" class="edit-tab" id="edit-tab"><a href="<%= Address.s_SERVLET_POLLS_SERVLET + "?" + Parameter.s_POLL_ID + "=" + poll.getPollId() %>" aria-label="<%= lang.getContent("poll.edit.tab.go_edit") %>" title="<%= lang.getContent("poll.edit.tab.go_edit") %>" id="tab-display-questions"><i class="fa fa-pencil-square-o fa-2x" aria-hidden="true"></i></a></li>
			  	<li role="presentation" class="share-tab" id="share-tab"><a href="pollcode?pid=<%= poll.getPublicUrl() %>" title="<%= lang.getContent("poll.edit.tab.go_share") %>"><i class="fa fa-users fa-2x" aria-hidden="true"></i></a></li>						  	
			  	<li role="presentation" class="statistic-tab active" id="statistic-tab"><a href="#" aria-label="<%= lang.getContent("poll.edit.tab.go_statistics") %>" title="<%= lang.getContent("poll.edit.tab.go_statistics") %>" id="tab-display-statistics"><i class="fa fa-bar-chart fa-2x" aria-hidden="true"></i></a></li>
			</ul>
		</div>
	</div>
	  				
	<div class="content-box-tabs edit-content">
				
		<div class="survey-statistics-title">
			<div class="col-md-10 col-md-offset-2">
				<h3> <%= poll.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></h3>
			</div>	
		</div>	
	  	<% request.setAttribute(Attribute.s_QUESTION, poll.getQuestion()); %>
		<div id="statistics">
			<div class="main-sidebar"></div>
			<div class="content-wrapper">					 		
				<div class="content-statistics" id="single-question-<%= question.getQuestionId() %>">
					<jsp:include page="<%= token + poll.getQuestion().getStatisticsPage() %>">
						<jsp:param name="index" value="<%= poll.getQuestion().getQuestionId() %>" />
					</jsp:include>
				</div>													
			</div>
		</div>
	</div>
</div>
<%
lang.close();
%>
	  			