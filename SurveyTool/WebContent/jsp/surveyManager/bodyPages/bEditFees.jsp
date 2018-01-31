<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="java.util.HashMap"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.LoginResponse"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Page"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Section"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.OptionsGroup"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Quota"%>
<%@page import="java.util.HashMap"%>
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
  				HashMap<Integer, Integer> listIdQuestion = new HashMap();
  				int indexquestion = 0;
				%>
				
				<script>
						loadquotas('<%=request.getAttribute(Attribute.s_JSON_QUOTAS)%>');
				</script>
					
				<div class="container-fluid">
	  				<div class="title-content-no-underline">
	  					<h2><a href="InitialServlet"><%= lang.getContent("user_panel.title") %></a> > <a href="UserPanelHomeServlet?upoption=surveys"><%= lang.getContent("survey_manager.title") %></a> > <%= lang.getContent("quota.edit.title") %></h2>
	  					<div class="right">
		  					<ul class="nav nav-tabs nav-tabs-right nav-tab-edit">
							  	<li role="presentation" class="edit-tab" id="edit-tab"><a href="SurveysServlet?surveyid=<%=survey.getSurveyId()%>" aria-label="<%= lang.getContent("survey.edit.tab.go_edit") %>" title="<%= lang.getContent("survey.edit.tab.go_edit") %>" id="tab-display-questions"><i class="fa fa-pencil-square-o fa-2x" aria-hidden="true"></i></a></li>
							  	<li role="presentation" class="share-tab active" id="share-tab"><a href="SurveysFees?surveyid=<%=survey.getSurveyId() %>" title="<%= lang.getContent("survey.edit.tab.go_quota") %>"><i class="fa fa-users fa-2x" aria-hidden="true"></i></a></li>
							  	<li role="presentation" class="statistic-tab" id="statistic-tab"><a href="SurveyStatisticServlet?surveyid=<%=survey.getSurveyId()%>" aria-label="<%= lang.getContent("survey.edit.tab.go_statistics") %>" title="<%= lang.getContent("survey.edit.tab.go_statistics") %>" id="tab-display-statistics"><i class="fa fa-bar-chart fa-2x" aria-hidden="true"></i></a></li>
							</ul>
						</div>
	  				</div>
	  				<div class="content-box-tabs edit-content">
	  					<div class="browser-left">Hello</div>	
	  					<div class="edit-survey-frame survey-info noborder" id="survey-name-title" sid="<%= survey.getSurveyId() %>">
		  						
		  						<div class="widthTitleSurveyCollapsed" id="survey-name-title-div">
		  							<div class="form-group nomargin">
		  								<h3 class="textalignleft"><%= title %></h3>
		  							</div>
			  					</div>					

		  					</div>	
	  						<div class="content edit-fees-frame paddingbotton50">
	  								<h4 class="textalignleft"><%= lang.getContent("quota.title.section1") %></h4>
									<p class="textalignleft"><%= lang.getContent("quota.text.section1") %></p>
									<div class="survey-objetive quotaobjetivesurvey" id="survey-objetive">
		  								<label for="survey-language-version" class="" ><span><%= lang.getContent("survey.fees.label.objective") %></span></label>
		  								<% String valueobjetive="";
		  									if(survey.getObjetive()>0){
		  										valueobjetive = (String.valueOf(survey.getObjetive()));
		  									} %>
										<input id="objetivesurveys" name="objetivesurveys" type="number" placeholder="none" class="form-control-small with100px" value="<%=valueobjetive %>" sid="<%=survey.getSurveyId() %>" min="1">
										<%= lang.getContent("survey.fees.label.surveys") %>
		  							</div>
							</div>  						  		
	  						<div class="edit-survey-head">
		  							
		  						
		  					</div>
		  					
		  					<div id="listcompletequotas" class="edit-fees-frame">
		  						<h4 class="textalignleft"><%= lang.getContent("quota.title.section2") %></h4>
								<p class="textalignleft"><%= lang.getContent("quota.text.section2") %></p>
									
		  					<%
		  						List<Question> listQuestionAvaibles = new ArrayList();
		  						String titleQuestion="";
		  						List<Section> sections = survey.getSections();
								int i = 1;
								for(Section section : sections)
								{					
									String title22 = "Section " + i;
									if(section.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE))
									{
										title = section.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
									}
									for(Page pag : section.getPages())
									{
										String token = "/";
			  							List<Question> questions = pag.getQuestions();
			  							if(questions != null && !questions.isEmpty()){
			  								for(Question question : questions){
			  									if(question.getQuestionType().equals("simple")||question.getQuestionType().equals("multiple")){
			  										if(question.getOptionsGroups()!=null && question.getOptionsGroups().size()>0)
			  										listQuestionAvaibles.add(question);
			  									}
			  									
			  									
			  								}
			  							
			  							}
									}
									
									i++;
								}
							 %>
							 
							 <ul class="survey-sections overflowhidden quota-item-list" id="survey-sections">
			  						
							 	<%  List<Quota> listQuotas = (List<Quota>) request.getAttribute(Attribute.s_LIST_QUOTAS);
							 	Quota quotaold=null;
							 	

							 	request.setAttribute("listQuestionFees",listQuestionAvaibles);
						 		
							 	List<Integer> listQuestionAvaiblesNoVisible = new ArrayList();
							 	
							 	for(Quota quota:listQuotas){
							 		if(quotaold==null || (quota.getIdQuestion()!=quotaold.getIdQuestion())){
							 				request.setAttribute("index",quota.getIdQuestion());
							 				listIdQuestion.put(quota.getIdQuestion(), quota.getIdQuestion());
							 				listQuestionAvaiblesNoVisible.add(Integer.valueOf(quota.getIdQuestion()));%>
							 				<jsp:include page="../components/cQuota.jsp" />
								 			<script>
								 				loadvaluequestion(<%=quota.getIdQuestion()%>);
								 			</script>
							 	<%
							 		}
							 		quotaold=quota;
							 	}%>
							 	
							 	<% request.setAttribute("listQuestionNoVisible",listQuestionAvaiblesNoVisible);%>
							 	
		  					
							</ul>
		  					
		  					<jsp:include page="../components/cQuotaNew.jsp" />
							
							
							<div class="center marginbotton10px" id="add-fees">											
								<label for="btn-add-quota" class="visuallyhidden"><%= lang.getContent("quota.add.new") %></label>
								<button class="btn btn-primary btn-sm active" id="btn-add-quota"><i class="fa fa-plus-square" aria-hidden="true"></i><span><%= lang.getContent("quota.add.new") %></span></button>
							</div>
							
							</div>
		  					
						</div>
						
	  				</div>
	  				
	  				<script>
					$(document).ready(function() {
							<%for (Integer key : listIdQuestion.keySet()) {%>
								$('#selquestionforfees<%=key%>').val(<%=listIdQuestion.get(key)%>);
								loadvaluequestion(<%=key%>);
							<%}%>
							
					});
	  				</script>
	  				
	  				
<%
lang.close();
%>

<jsp:include page="../frames/fDeleteElement.jsp" />
<jsp:include page="../frames/fNewQuota.jsp" />
	  			