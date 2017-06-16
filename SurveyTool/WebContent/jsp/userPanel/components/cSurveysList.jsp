<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.UserSurvey"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.UserSurveyTableInfo"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.SurveyTableInfo"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.language.Language"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));

List<UserSurveyTableInfo> surveys = (List<UserSurveyTableInfo>) request.getAttribute(Attribute.s_SURVEYS);
%>    								


						<div id="surveys-list" style="margin: 20px 0;">
						
		  					<div class="user-panel-surveys">
		  						
		  						<%
			  					/*List<SurveyTableInfo> surveys = (List<SurveyTableInfo>) request.getAttribute(Attribute.s_SURVEYS);
			  					if(surveys != null && !surveys.isEmpty())*/
			  					if(true)
			  					{
			  					%>
			  						
			  					<div class="surveys-table">
			  						<fieldset class="div-filters no-padding col-sm-6">
			  							<legend class="visuallyhidden"><%= lang.getContent("survey_manager.tab.surveys.filter.legend") %></legend>
			  							<div id="divSearchSurvey" class="div-search-survey no-padding col-xs-8">
			  								<label for="searchSurvey" class="col-xs-3"><%= lang.getContent("survey_manager.tab.surveys.search.label") %>:</label>
			  								<div class="col-xs-9">
				  								<input type="search" id="searchSurvey" class="form-control input-sm" placeholder="" aria-controls="DataTables_Table_0">
				  								<i class="fa fa-search" aria-hidden="true"></i>
				  							</div>
			  							</div>	
				  						<div id="divStateFilter" class="div-state-filter no-padding col-xs-4 col-md-4">
				  							<label for="stateFilter" class="visuallyhidden"><%= lang.getContent("survey_manager.tab.surveys.state.filter.label") %></label>
				  							<select class="form-control input-sm state-filter" id="stateFilter">
				  								<option value=""><%= lang.getContent("userpanel.mysurveys.tab.surveys.state.all") %></option>
				  								<option value="<%= lang.getContent("userpanel.mysurveys.tab.surveys.state.new") %>"><%= lang.getContent("userpanel.mysurveys.tab.surveys.state.new") %></option>
				  								<option value="<%= lang.getContent("userpanel.mysurveys.tab.surveys.state.inprogress") %>"><%= lang.getContent("userpanel.mysurveys.tab.surveys.state.inprogress") %></option>
				  								<option value="<%= lang.getContent("userpanel.mysurveys.tab.surveys.state.completed") %>"><%= lang.getContent("userpanel.mysurveys.tab.surveys.state.completed") %></option>
				  							</select>			  							
				  						</div>				  						
			  						</fieldset>		  					
			  						<table class="table table-bordered table-surveys display" sumary="List of surveys where ..." id=""  data-page-length='25'>
			  							<caption><%= lang.getContent("survey_manager.surveys.table.caption") %></caption>
			  							<thead>
										<tr class="info" id="titles">
											<th class="center middle hidden">State</th>
											<th class="center middle"><%= lang.getContent("survey_manager.surveys.table.column.survey") %></th>
											<th class="center middle"><%= lang.getContent("userpanel.mysurveys.table.column.deadline") %></th>
											<th class="center middle"><%= lang.getContent("userpanel.mysurveys.table.column.progress") %></th>
										</tr>
										</thead>
										<tbody>
										
										<%
										System.out.println("Servlet: " + Address.s_SERVLET_SURVEYS_SERVLET);
										for(UserSurveyTableInfo survey : surveys)
										{
											SurveyTableInfo surveyTableInfo = survey.getSurveyTableInfo();
											
											String deadLine = "";											
											if(surveyTableInfo.getDeadLineDate() != null) 
											{												
												Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis()); 
												if(surveyTableInfo.getDeadLineDate().compareTo(currentTime) < 0)
												{
													deadLine = lang.getContent("userpanel.mysurveys.table.column.deadline.closed");
												}
												else
												{
													deadLine = lang.getContent("userpanel.mysurveys.table.column.deadline.until") + " " + new SimpleDateFormat("dd/MM/yyyy").format(surveyTableInfo.getDeadLineDate());
												}
											}
											else if(surveyTableInfo.getState().equals(DBConstants.s_VALUE_SURVEY_STATE_FINISHED))
											{
												deadLine = lang.getContent("userpanel.mysurveys.table.column.deadline.closed");
											}
											else
											{
												deadLine =  lang.getContent("userpanel.mysurveys.table.column.deadline.undefined");
											}
											
											UserSurvey userSurvey = survey.getUserSurvey();
											int progressPercentage = 0;
											String state = lang.getContent("userpanel.mysurveys.tab.surveys.state.new");
											boolean completed = false;
											if(userSurvey != null)
											{
												progressPercentage = userSurvey.getProgress();
												if(userSurvey.isFinished()) {
													state = lang.getContent("userpanel.mysurveys.tab.surveys.state.completed");
													completed = true;
												}
												else {state = lang.getContent("userpanel.mysurveys.tab.surveys.state.inprogress");}
											}
											
											/*String downloadServiceUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/SurveyTool/api/SurveyService/export/" + survey.getSurveyId();
											
											String state = "";
											if(survey.getState().equals(DBConstants.s_VALUE_SURVEY_STATE_ACTIVE)) {state = lang.getContent("survey_manager.tab.surveys.state.active");}
											else if(survey.getState().equals(DBConstants.s_VALUE_SURVEY_STATE_PAUSED)) {state = lang.getContent("survey_manager.tab.surveys.state.paused");}
											else {state = lang.getContent("survey_manager.tab.surveys.state.finished");}*/
										%>
										<tr id="resultdevice" class="survey-list-row" sid="">
											<td class="center hidden"><%= state %></td>
											<td class="col-sm-7">
												<a href=""><%= surveyTableInfo.getTitle() %></a>
												<%if(surveyTableInfo.getDescription() != null && !surveyTableInfo.getDescription().isEmpty()) {%>
												<p><%= surveyTableInfo.getDescription() %> <!-- ... +Details --></p>
												<%} %>
											</td>
											<td class="center col-sm-2"><%= deadLine %></td>
											<td class="center col-sm-3">
												<div class="progress table-progress">
												  <div class="progress-bar <%if(completed){%>table-progress-bar-success<%} %>" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: <%= progressPercentage %>%;">
												    <%if(completed){%>
												    <span class="">Completed</span>
												    <%} %>
												    <span class="sr-only"><%= progressPercentage %>% Completed</span>
												  </div>
												</div>
												<span><%= progressPercentage %>% <%= lang.getContent("userpanel.mysurveys.table.column.progress.completed") %></span>
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
			  					
			  					<div class="empty-table-div"><p><%= lang.getContent("survey_manager.surveys.msg.no_surveys") %></p></div>
			  					
			  					<%
			  					}
			  					%>
			  					  					
			  				</div>
			  			</div>
			  			
<script> 
 
    $(document).ready(function() {

    	var table = $('table.display').DataTable({
    		"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
        	"pagingType": "full_numbers",
            "scrollCollapse": false,
            "searching": true,
            "ordering": false,
            "bLengthChange" : true,
            "language": {
            	"url": "js/dataTables.<%=Language.getLanguageRequest(request)%>.lang"
            }
        });   

    	$('div.surveys-table').find('table').dataTable().on('draw.dt', function () {
    		
    	});
        
    	$(document).on('draw.dt', function () {
    		//$('[name="surveys-table_length"]').val("10");
    	 	//$('#DataTables_Table_0_filter label').append("<i class='fa fa-search' aria-hidden='true'></i>");
    	 	$('#DataTables_Table_0_filter').addClass("hidden");
    	 	$('#DataTables_Table_1_filter label').append("<i class='fa fa-search' aria-hidden='true'></i>");
    	 	$('table').removeAttr("role");
    	 	$('tr').removeAttr("role"); 
    	 	
        });
    	

	 	$('div.surveys-table').find('table').DataTable().columns().every(function (indice) {
			if(indice == 0)
			{
    			var column = this;
                var select = $('#stateFilter').on('change', function() {
                     var val = $.fn.dataTable.util.escapeRegex($(this).val());
                     
                     column.search( val ? '^'+val+'$' : '', true, false).draw();
                 });
 
                /*column.data().unique().sort().each(function(d, j) {
                    select.append('<option value="'+d+'">'+d+'</option>');
                });*/
			}
			else if(indice == 1)
			{
				var column = this;
				 
		        $('#searchSurvey').on('keyup change', function () {
		            if(column.search() !== this.value) {
		            	column.search(this.value).draw();
		            }
		        });
			}
	    }); 
       
    });
    
</script> 
<%
lang.close();
%>
