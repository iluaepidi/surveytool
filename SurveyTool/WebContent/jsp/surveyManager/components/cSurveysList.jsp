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
%>    								


						<div id="surveys-list" style="margin-bottom: 20px;">	    					
							<h3><%= lang.getContent("survey_manager.surveys.title") %></h3>							
							<%= lang.getContent("survey_manager.surveys.description") %>
		  					<div class="user-panel-surveys">
		  						
		  						<div class="surveys-create-button">
		  							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newSurveyModal"><%= lang.getContent("button.create_new") %></button>
		  						</div>
		  						
		  						
		  						
		  						<%
			  					List<SurveyTableInfo> surveys = (List<SurveyTableInfo>) request.getAttribute(Attribute.s_SURVEYS);
			  					if(!surveys.isEmpty())
			  					{
			  					%>
			  					<div class="surveys-table">
			  						<table class="table table-bordered" sumary="List of surveys where ..." id="surveys-table"  data-page-length='25'>
			  							<caption><%= lang.getContent("survey_manager.surveys.table.caption") %></caption>
			  							<thead>
										<tr class="info" id="titles">
											<th class="center"><%= lang.getContent("survey_manager.surveys.table.column.deadline") %></th>
											<th class="center"><%= lang.getContent("survey_manager.surveys.table.column.survey") %></th>
											<th class="center"><%= lang.getContent("survey_manager.surveys.table.column.progress") %></th>
											<th class="center"><%= lang.getContent("survey_manager.surveys.table.column.actions") %></th>
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
										%>
										<tr id="resultdevice">
											<td class="center"><%= deadLine %></td>
											<td><a href="<%= Address.s_SERVLET_SURVEYS_SERVLET + "?" + Parameter.s_SURVEY_ID + "=" + survey.getSurveyId() %>"><%= survey.getTitle() %></a></td>
											<td>
												<div class="progress percent-bar">
													<%
													float percentage = 0;
													if(survey.getNumUsers() > 0)
													{
														percentage = ((survey.getNumUsersFinished() * 100) / survey.getNumUsers());	
													}
													%>
													<div class="progress-bar" role="progressbar" aria-valuenow="<%= survey.getNumUsersFinished() %>" aria-valuemin="0" aria-valuemax="<%= survey.getNumUsers() %>" style="width: <%=percentage%>%;"><%= survey.getNumUsersFinished() %>/<%= survey.getNumUsers() %></div>
												</div>
											</td>
											<td>
												<ul class="row">
													<!-- <li class="col-sm-3 center"><a href="#" title="clone survey"><i class="fa fa-clone fa-2x"></i></a></li>
								  					<li class="col-sm-2 center"><a href="#" title="statistics"><i class="fa fa-bar-chart fa-2x"></i></a></li>
								  					<li class="col-sm-2 center"><a href="#" title="settings"><i class="fa fa-cogs fa-2x"></i></a></li>
								  					<li class="col-sm-2 center"><a href="#" title="download"><i class="fa fa-download fa-2x"></i></a></li>
								  					<li class="col-sm-3 center"><a href="#" title="pause survey"><i class="fa fa-pause-circle-o fa-2x"></i></a></li> -->
								  					<li class="col-sm-3 center"><i class="fa fa-clone fa-2x"></i></li>
								  					<li class="col-sm-2 center"><i class="fa fa-bar-chart fa-2x"></i></li>
								  					<li class="col-sm-2 center"><i class="fa fa-cogs fa-2x"></i></li>
								  					<li class="col-sm-2 center"><i class="fa fa-download fa-2x"></i></li>
								  					<li class="col-sm-3 center"><i class="fa fa-pause-circle-o fa-2x"></i></li>
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

<script> 
 
    $(document).ready(function() {
        
    	//$('#surveys-table').DataTable();
    	
        $('#surveys-table').dataTable({
        	"iDisplayLength": 10,
        	"pagingType": "full_numbers",
            "scrollCollapse": false,
            "searching": true,
            "ordering": false,
            "language": {
            	"url": "js/dataTables.<%=Language.getLanguageRequest(request)%>.lang"
            }
            
        });
        
        
       
    });
    
    
    $(window).load(function() {
    	 $('[name="surveys-table_length"]').val("10");
    	 $('[name="surveys-table_length"]').change();
    	 
    	 $('#surveys-table_filter label').prepend("<i class='fa fa-search' aria-hidden='true'></i>&nbsp;");
    });
    
    
    
</script> 