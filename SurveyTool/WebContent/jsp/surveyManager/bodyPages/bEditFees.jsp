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
				
				<div class="container-fluid">
	  				<div class="title-content-no-underline">
	  					<h2><a href="InitialServlet"><%= lang.getContent("user_panel.title") %></a> > <a href="UserPanelHomeServlet?upoption=surveys"><%= lang.getContent("survey_manager.title") %></a> > <%= lang.getContent("survey.edit.title") %></h2>
	  					<ul class="nav nav-tabs nav-tabs-right nav-tab-edit">						  	
						  	<li role="presentation" class="statistic-tab"><a href="#" aria-label="go to survey statistics" title="go to survey statistics"><i class="fa fa-bar-chart fa-2x"></i></a></li>
						  	<li role="presentation" class="share-tab active"><a href="#" title="go to share survey"><i class="fa fa-share-alt fa-2x"></i></a></li>
						  	<li role="presentation" class="edit-tab"><a href="SurveysServlet?surveyid=<%=survey.getSurveyId() %>" aria-label="<%= lang.getContent("survey.edit.tab.go_edit") %>" title="<%= lang.getContent("survey.edit.tab.go_edit") %>"><i class="fa fa-pencil-square-o fa-2x"></i></a></li>
						</ul>
	  				</div>
	  				<div class="content-box-tabs edit-content" id="listcompletequotas">
	  					<div class="browser-left">Hello</div>	  						  		
	  						<div class="edit-survey-head">
		  						<div class="survey-preview">
		  							
		  						</div>
		  							
		  						<div class="survey-objetive" id="survey-objetive">
		  								<label for="survey-language-version" class="" ><i class="fa fa-sliders fa-2x"></i><span><%= lang.getContent("survey.fees.label.objective") %></span></label>
										<input id="objetivesurveys" name="objetivesurveys" type="text" placeholder="" class="form-control-small" value="<%=survey.getObjetive() %>" sid="<%=survey.getSurveyId() %>">
										<%= lang.getContent("survey.fees.label.surveys") %>
		  						</div>
		  					</div>
		  					
		  					
		  					<%
		  						List<Question> listQuestionFees = new ArrayList();
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
			  										listQuestionFees.add(question);
			  									}
			  									
			  									
			  								}
			  							
			  							}
									}
									
									i++;
								}
							 %>
							 
							 	<%  List<Quota> listQuotas = (List<Quota>) request.getAttribute(Attribute.s_LIST_QUOTAS);
							 	Quota quotaold=null,quota=null;
							 	

						 		request.setAttribute("listQuestionFees",listQuestionFees);
						 		
							 	for(int index=1;index<=listQuotas.size();index++){
							 		quota  = (Quota) listQuotas.get(index-1);
							 		
							 		if(index==1){
							 			indexquestion++;
							 			request.setAttribute("index",indexquestion);
							 			
							 			listIdQuestion.put(indexquestion, quota.getIdQuestion());
							 			%>
							 			<jsp:include page="../components/cQuota.jsp" />
							 			<script type="text/javascript">
							 				loadvaluequestion(<%=indexquestion%>);
							 			</script>
							 		<% }else{ %>
							 			<% if(quota.getIdQuestion()!=quotaold.getIdQuestion()){
							 				indexquestion++;
							 				request.setAttribute("index",indexquestion);
							 				listIdQuestion.put(indexquestion, quota.getIdQuestion());
							 			%>
							 			
							 			<jsp:include page="../components/cQuota.jsp" />
							 			<script type="text/javascript">
							 				loadvaluequestion(<%=indexquestion%>);
							 			</script>
							 			<%} 
							 		}
							 		quotaold = (Quota) listQuotas.get(index-1);
							 		
							 		%>
		  						
		  					<%} %>
		  					
		  					
		  					<jsp:include page="../components/cQuotaNew.jsp" />
							
							<div class="center" id="add-fees">											
								<label for="btn-add-quota" class="visuallyhidden">Añadir Cuota</label>
								<button class="btn btn-primary btn-sm active" id="btn-add-quota"><i class="fa fa-plus-square"></i><span>Añadir Cuota</span></button>
							</div>
		  					
						</div>
						
	  				</div>
	  				
					<script type="text/javascript">
						loadquotas('<%=request.getAttribute(Attribute.s_JSON_QUOTAS)%>');
						$(document).ready(function() {
							<%for(int id=1;id<=indexquestion;id++){%>
								
								$('#selquestionforfees<%=id%>').val(<%=listIdQuestion.get(id)%>);
								loadvaluequestion(<%=id%>);
							<%}%>
							
							
						});
					</script>
	  				
	  				
	  				
<%
lang.close();
%>

<jsp:include page="../frames/fDeleteElement.jsp" />
<jsp:include page="../frames/fNewQuota.jsp" />
	  			