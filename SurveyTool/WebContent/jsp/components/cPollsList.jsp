<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="ilu.surveytool.databasemanager.DataObject.PollTableInfo"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.language.Language"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage("en");
%>
    								
						<div class="hidden" id="polls-list">	  					
							<h3><%= lang.getContent("survey_manager.polls.title") %></h3>							
							<%= lang.getContent("survey_manager.polls.description") %>
		  					<div class="user-panel-surveys">
		  						<div class="surveys-create-button">
		  							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newPollModal"><%= lang.getContent("button.create_new") %></button>
		  						</div>
			  					<%
			  					String host = request.getServerName();
			  					int port = request.getServerPort();
			  					List<PollTableInfo> polls = (List<PollTableInfo>) request.getAttribute(Attribute.s_POLLS);
			  					if(polls != null && !polls.isEmpty())
			  					{
			  					%>
			  					<div class="surveys-table">
			  						<table class="table table-bordered" sumary="List of surveys where ...">
			  							<caption><%= lang.getContent("survey_manager.polls.table.caption") %></caption>
										<tr class="info">
											<th class="poll-table-tr-th-deadline center"><%= lang.getContent("survey_manager.polls.table.column.deadline") %></th>
											<th class="center"><%= lang.getContent("survey_manager.polls.table.column.poll") %></th>
											<th class="poll-table-tr-th-responses center"><%= lang.getContent("survey_manager.polls.table.column.url") %></th>
											<th class="poll-table-tr-th-actions center"><%= lang.getContent("survey_manager.polls.table.column.actions") %></th>
										</tr>
										<%
										System.out.println("Servlet: " + Address.s_SERVLET_SURVEYS_SERVLET);
										for(PollTableInfo poll : polls)
										{
											String deadLine = "none";
											if(poll.getDeadLineDate() != null) deadLine = poll.getDeadLineDate().toString();
										%>
										<tr>
											<td class="center"><%= deadLine %></td>
											<td><a href="#"><%= poll.getTitle() %></a></td>
											<td class="center">
												<a href="http://<%= host %>:<%= port %>/SurveyTool/pollcode?pid=<%= poll.getPublicUrl() %>">http://<%= host %>:<%= port %>/SurveyTool/poll?pid=<%= poll.getPublicUrl() %></a>
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
										</tr>
										<%
										}
										%>
			  						</table>
			  					</div>
			  					
			  					<%
				  					//if(surveys.size() > 5)
				  					if(false)
				  					{
				  						int index = 1;
			  					%>
			  					<!-- <div class="btn-pag center">
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
			  					</div> -->
			  					
			  					<%
			  						}
			  					}
			  					else
			  					{
			  					%>
			  					
			  					<div><p><%= lang.getContent("survey_manager.polls.msg.no_polls") %></p></div>
			  					
			  					<%
			  					}
			  					%>
			  					  					
			  				</div>
			  			</div>
<%
lang.close();
%>