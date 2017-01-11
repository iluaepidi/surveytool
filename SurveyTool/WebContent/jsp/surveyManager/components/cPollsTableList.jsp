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
lang.loadLanguage(Language.getLanguageRequest(request));
%>
    								
						
			  					<%
			  					List<PollTableInfo> polls = (List<PollTableInfo>) request.getAttribute(Attribute.s_POLLS);
			  					if(polls != null && !polls.isEmpty())
			  					{
			  					%>
			  					<div class="polls-table">
			  						<table class="table table-bordered table-surveys display" id="" sumary="List of surveys where ..." data-page-length='25'>
			  							<caption><%= lang.getContent("survey_manager.polls.table.caption") %></caption>
			  							<thead>
											<tr class="info">
												<!-- <th class="poll-table-tr-th-deadline center"><%= lang.getContent("survey_manager.polls.table.column.deadline") %></th> -->
												<th class="center middle"><%= lang.getContent("survey_manager.polls.table.column.poll") %></th>
												<th class="poll-table-tr-th-responses center middle"><%= lang.getContent("survey_manager.polls.table.column.url") %></th>
												<th class="poll-table-tr-th-numresp center middle"><%= lang.getContent("survey_manager.polls.table.column.num_responses") %></th>
												<th class="poll-table-tr-th-actions center middle"><%= lang.getContent("survey_manager.polls.table.column.actions") %></th>
											</tr>
										</thead>
										<tbody>
										<%
										System.out.println("Servlet: " + Address.s_SERVLET_SURVEYS_SERVLET);
										for(PollTableInfo poll : polls)
										{
											request.setAttribute(Attribute.s_POLL_INFO, poll);
										%>
										
										<jsp:include page="cPollRow.jsp" />
										
										<%
										}
										%>
										</tbody>
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
			  					
			  					<div id="no-polls-msg"><p><%= lang.getContent("survey_manager.polls.msg.no_polls") %></p></div>
			  					
			  					<%
			  					}
			  					%>
			  				
<%
lang.close();
%>
