<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.SurveyTableInfo"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.language.Language"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
String tab = (String) request.getAttribute(Attribute.s_TAB);
%>    								


						<div id="surveys-list" <%if(!tab.equals("survey")){%>class="hidden"<%}%> style="margin-bottom: 20px;">	    					
							<h3><%= lang.getContent("survey_manager.surveys.title") %></h3>							
							<%= lang.getContent("survey_manager.surveys.description") %>
		  					<div class="user-panel-surveys">
		  						
		  						<div class="surveys-create-button">
		  							<button type="button" class="btn btn-primary btn-icon-text" data-toggle="modal" data-target="#newSurveyModal"><i class="fa fa-plus" aria-hidden="true"></i> <%= lang.getContent("button.create_new") %></button>
		  						</div>
		  						
		  						
		  						
		  						<%
			  					List<SurveyTableInfo> surveys = (List<SurveyTableInfo>) request.getAttribute(Attribute.s_SURVEYS);
			  					if(!surveys.isEmpty())
			  					{
			  					%>
			  						
			  					<div class="surveys-table">
			  						<fieldset class="div-filters no-padding col-sm-6">
			  							<legend class="visuallyhidden"><%= lang.getContent("survey_manager.tab.surveys.filter.legend") %></legend>
			  							<div id="divSearchSurvey" class="div-search-survey no-padding col-xs-8 col-md-offset-1">
			  								<label for="searchSurvey" class="col-xs-3"><%= lang.getContent("survey_manager.tab.surveys.search.label") %>:</label>
			  								<div class="col-xs-9">
				  								<input type="search" id="searchSurvey" class="form-control input-sm" placeholder="" aria-controls="DataTables_Table_0">
				  								<i class="fa fa-search" aria-hidden="true"></i>
				  							</div>
			  							</div>	
				  						<div id="divStateFilter" class="div-state-filter no-padding col-xs-4 col-md-3">
				  							<label for="stateFilter" class="visuallyhidden"><%= lang.getContent("survey_manager.tab.surveys.state.filter.label") %></label>
				  							<select class="form-control input-sm state-filter" id="stateFilter">
				  								<option value=""><%= lang.getContent("survey_manager.tab.surveys.state.filter.option") %></option>
				  								<option value="<%= lang.getContent("survey_manager.tab.surveys.state.active") %>"><%= lang.getContent("survey_manager.tab.surveys.state.active") %></option>
				  								<option value="<%= lang.getContent("survey_manager.tab.surveys.state.paused") %>"><%= lang.getContent("survey_manager.tab.surveys.state.paused") %></option>
				  								<option value="<%= lang.getContent("survey_manager.tab.surveys.state.finished") %>"><%= lang.getContent("survey_manager.tab.surveys.state.finished") %></option>
				  							</select>			  							
				  						</div>				  						
			  						</fieldset>		  					
			  						<table class="table table-bordered table-surveys display" sumary="List of surveys where ..." id=""  data-page-length='25'>
			  							<caption><%= lang.getContent("survey_manager.surveys.table.caption") %></caption>
			  							<thead>
										<tr class="info" id="titles">
											<th class="center middle"><%= lang.getContent("survey_manager.surveys.table.column.state") %></th>
											<th class="center middle"><%= lang.getContent("survey_manager.surveys.table.column.survey") %></th>
											<th class="center middle"><%= lang.getContent("survey_manager.surveys.table.column.num_responses") %></th>
											<th class="center middle"><%= lang.getContent("survey_manager.surveys.table.column.actions") %></th>
										</tr>
										</thead>
										<tbody>
										
										<%
										System.out.println("Servlet: " + Address.s_SERVLET_SURVEYS_SERVLET);
										for(SurveyTableInfo survey : surveys)
										{
											String deadLine = "";
											if(survey.getDeadLineDate() != null) 
											{
												deadLine = survey.getDeadLineDate().toString();
											}
											else
											{
												deadLine =  lang.getContent("survey_manager.table.content.none");
											}
											
											String downloadServiceUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/SurveyTool/api/SurveyService/export/" + survey.getSurveyId();
											
											String state = "";
											if(survey.getState().equals(DBConstants.s_VALUE_SURVEY_STATE_ACTIVE)) {state = lang.getContent("survey_manager.tab.surveys.state.active");}
											else if(survey.getState().equals(DBConstants.s_VALUE_SURVEY_STATE_PAUSED)) {state = lang.getContent("survey_manager.tab.surveys.state.paused");}
											else {state = lang.getContent("survey_manager.tab.surveys.state.finished");}
											
										%>
										<tr id="resultdevice" sid="<%=survey.getSurveyId()%>">
											<td class="state center col-sm-1" id="cellState<%= survey.getSurveyId() %>"><%= state %></td>
											<td class="col-sm-4"><a href="<%= Address.s_SERVLET_SURVEYS_SERVLET + "?" + Parameter.s_SURVEY_ID + "=" + survey.getSurveyId() %>"><%= survey.getTitle() %></a></td>
											<td class="center col-sm-2">
												<%= survey.getNumUsers() %> <%= lang.getContent("survey_manager.surveys.table.survey_responses") %>
												<!-- <div class="progress percent-bar">
													<%
													float percentage = 0;
													if(survey.getNumUsers() > 0)
													{
														percentage = ((survey.getNumUsersFinished() * 100) / survey.getNumUsers());	
													}
													%>
													<div class="progress-bar" role="progressbar" aria-valuenow="<%= survey.getNumUsersFinished() %>" aria-valuemin="0" aria-valuemax="<%= survey.getNumUsers() %>" style="width: <%=percentage%>%;"><%= survey.getNumUsersFinished() %>/<%= survey.getNumUsers() %></div>
												</div> -->
											</td>
											<td class="actions col-sm-5">
												<ul class="row">
								  					<!-- <li class="col-sm-3 center"><i class="fa fa-clone fa-2x" aria-hidden="true"></i></li> -->
								  					<li class="col-md-3 col-sm-6 center"><a href="SurveyStatisticServlet?surveyid=<%=survey.getSurveyId()%>"><i class="fa fa-bar-chart fa-2x" aria-hidden="true"></i><span><%= lang.getContent("survey_manager.tab.surveys.label.statistics") %></span><span class="visuallyhidden"><%= lang.getContent("survey.edit.tab.go_statistics") %> <%= survey.getTitle() %></span></a></li>
								  					<!-- <li class="col-sm-2 center"><i class="fa fa-cogs fa-2x" aria-hidden="true"></i></li> -->
								  					<li class="col-md-3 col-sm-6 center"><a href="<%= downloadServiceUrl %>"><i class="fa fa-download fa-2x" aria-hidden="true"></i><span><%= lang.getContent("survey_manager.tab.surveys.label.download") %></span><span class="visuallyhidden"><%= lang.getContent("survey_manager.tab.surveys.download") %> <%= survey.getTitle() %></span></a></li>
								  					<li class="col-md-3 col-sm-6 center">
								  					<%if(survey.getState().equals(DBConstants.s_VALUE_SURVEY_STATE_ACTIVE))
								  						{%>
								  						<a class="survey-pause" href="javascript:;"><i class="fa fa-pause-circle-o fa-2x" aria-hidden="true"></i><span><%= lang.getContent("survey_manager.tab.surveys.label.pause") %></span><span class="visuallyhidden"><%= lang.getContent("survey_manager.tab.surveys.pause") %> <%= survey.getTitle() %></span></a>
								  						<a class="survey-play hidden" href="javascript:;"><i class="fa fa-play-circle-o fa-2x" aria-hidden="true"></i><span><%= lang.getContent("survey_manager.tab.surveys.label.activate") %></span><span class="visuallyhidden"><%= lang.getContent("survey_manager.tab.surveys.activate") %> <%= survey.getTitle() %></span></a>								  						
								  					<%} else if(survey.getState().equals(DBConstants.s_VALUE_SURVEY_STATE_PAUSED)){ %>
								  						<a class="survey-pause hidden" href="javascript:;"><i class="fa fa-pause-circle-o fa-2x" aria-hidden="true"></i><span><%= lang.getContent("survey_manager.tab.surveys.label.pause") %></span><span class="visuallyhidden"><%= lang.getContent("survey_manager.tab.surveys.pause") %> <%= survey.getTitle() %></span></a>
								  						<a class="survey-play" href="javascript:;"><i class="fa fa-play-circle-o fa-2x" aria-hidden="true"></i><span><%= lang.getContent("survey_manager.tab.surveys.label.activate") %></span><span class="visuallyhidden"><%= lang.getContent("survey_manager.tab.surveys.activate") %> <%= survey.getTitle() %></span></a>
								  					<%} %>
								  					</li>
								  					<li class="col-md-3 col-sm-6 center">
								  					<%if(!survey.getState().equals(DBConstants.s_VALUE_SURVEY_STATE_FINISHED))
								  						{%>
								  						<a class="survey-finish" href="javascript:;"><i class="fa fa-stop-circle-o fa-2x" aria-hidden="true"></i><span><%= lang.getContent("survey_manager.tab.surveys.label.finish") %></span><span class="visuallyhidden"><%= lang.getContent("survey_manager.tab.surveys.finish") %> <%= survey.getTitle() %></span></a>
								  					<%} %>
								  					</li>
												</ul>
											</td>
										
										<%
										}
										%>
										</tr>
										</tbody>
			  						</table>
			  					</div>
			  					
			  					<%
				  					//if(surveys.size() > 5)
				  					if(false)
				  					{
				  						int index = 1;
			  					%>
			  					<div class="btn-pag center">
			  						<ul class="pagination">
									    <li>
									      <a href="#" aria-label="Previous">
									        <span aria-hidden="true">&laquo;</span>
									      </a>
									    </li>
									    <li><a href="#">1</a></li>
									    <li><a href="#">2</a></li>
									    <li><a href="#">3</a></li>
									    <li><a href="#">4</a></li>
									    <li><a href="#">5</a></li>
									    <li>
									      <a href="#" aria-label="Next">
									        <span aria-hidden="true">&raquo;</span>
									      </a>
									    </li>
									</ul>
			  					</div>
			  					
			  					<%
			  						}
			  					}
			  					else
			  					{
			  					%>
			  					
			  					<div><p><%= lang.getContent("survey_manager.surveys.msg.no_surveys") %></p></div>
			  					
			  					<%
			  					}
			  					%>
			  					  					
			  				</div>
			  			</div>
<%
lang.close();
%>
