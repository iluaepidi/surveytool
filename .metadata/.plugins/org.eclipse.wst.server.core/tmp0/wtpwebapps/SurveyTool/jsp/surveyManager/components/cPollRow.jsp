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
							PollTableInfo poll = (PollTableInfo) request.getAttribute(Attribute.s_POLL_INFO);
							
							String host = request.getServerName();
							int port = request.getServerPort();
							
							String deadLine = "";
							if(poll.getDeadLineDate() != null) 
							{
								deadLine = poll.getDeadLineDate().toString();
							}
							else
							{
								deadLine =  lang.getContent("survey_manager.table.content.none");
							}	

    						String downloadServiceUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/SurveyTool/api/PollService/export/" + poll.getPollId();
%>
						
										<tr>
											<td class="center"><%= deadLine %></td>
											<td><%= poll.getTitle() %></td>
											<td class="center">
												<a href="http://<%= host %>:<%= port %>/SurveyTool/pollcode?pid=<%= poll.getPublicUrl() %>">http://<%= host %>:<%= port %>/SurveyTool/poll?pid=<%= poll.getPublicUrl() %></a>
											</td>
											<td class="center">
												<%= poll.getNumResponses() %> <%= lang.getContent("survey_manager.polls.table.poll_responses") %>
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
								  					<li class="col-sm-2 center"><a href="<%= downloadServiceUrl %>" title="download"><i class="fa fa-download fa-2x"></i></a></li>
								  					<li class="col-sm-3 center"><i class="fa fa-pause-circle-o fa-2x"></i></li>
												</ul>
											</td>
										</tr>
										
<%
							lang.close();
%>