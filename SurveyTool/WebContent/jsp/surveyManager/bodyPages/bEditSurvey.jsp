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
	  					<h2 id="title-header-edit"><a href="InitialServlet"><%= lang.getContent("user_panel.title") %></a> > <a href="UserPanelHomeServlet?upoption=surveys"><%= lang.getContent("survey_manager.title") %></a> > <%= lang.getContent("survey.edit.title") %></h2>
	  					<div class="right">
		  					<ul class="nav nav-tabs nav-tabs-right nav-tab-edit">
							  	<li role="presentation" class="edit-tab active" id="edit-tab"><a href="#" aria-label="<%= lang.getContent("survey.edit.tab.go_edit") %>" title="<%= lang.getContent("survey.edit.tab.go_edit") %>" id="tab-display-questions"><i class="fa fa-pencil-square-o fa-2x" aria-hidden="true"></i></a></li>
							  	<li role="presentation" class="share-tab" id="share-tab"><a href="SurveysFees?surveyid=<%=survey.getSurveyId() %>" title="<%= lang.getContent("survey.edit.tab.go_edit") %>"><i class="fa fa-users fa-2x" aria-hidden="true"></i></a></li>		
							  	<li role="presentation" class="statistic-tab" id="statistic-tab"><a href="SurveyStatisticServlet?surveyid=<%=survey.getSurveyId()%>" aria-label="<%= lang.getContent("survey.edit.tab.go_statistics") %>" title="<%= lang.getContent("survey.edit.tab.go_statistics") %>" id="tab-display-statistics"><i class="fa fa-bar-chart fa-2x" aria-hidden="true"></i></a></li>
							</ul>
						</div>
	  				</div>
	  				
	  				<div class="content-box-tabs edit-content">
	  					<jsp:include page="../bodyPages/bEditSurveyQuestions.jsp" />
	  				</div>
	  			</div>
<%
lang.close();
%>
	  			
	  			<jsp:include page="../frames/fNewQuestion.jsp" />
	  			
	  			<jsp:include page="../frames/fImportFile.jsp" />
	  			
	  			<jsp:include page="../frames/fUpdateFile.jsp" />
	  			
	  			
	  			<jsp:include page="../frames/fAccesibilityEditor.jsp" />
	  			
	  			<jsp:include page="../frames/fDeleteElement.jsp" />
	  			
	  			