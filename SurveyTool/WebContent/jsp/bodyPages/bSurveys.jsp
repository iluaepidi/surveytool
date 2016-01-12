				<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.SurveyTableInfo"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="java.util.List"%>
<div class="container-fluid">
	  				<div class="title-content">
	  					<h2>User Panel > Surveys</h2>
	  				</div>
	  				<div class="content">
	  					<p>Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen. No sólo sobrevivió 500 años, sino que tambien ingresó como texto de relleno en documentos electrónicos, quedando esencialmente igual al original. Fue popularizado en los 60s con la creación de las hojas "Letraset", las cuales contenian pasajes de Lorem Ipsum, y más recientemente con software de autoedición, como por ejemplo Aldus PageMaker, el cual incluye versiones de Lorem Ipsum.</p>
	  					<p>Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen.</p>
	  					<div class="user-panel-surveys">
	  						<div class="surveys-create-button">
	  							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newSurveyModal">+ Create new</button>
	  						</div>
		  					<%
		  					List<SurveyTableInfo> surveys = (List<SurveyTableInfo>) request.getAttribute(Attribute.s_SURVEYS);
		  					if(!surveys.isEmpty())
		  					{
		  					%>
		  					<div class="surveys-table">
		  						<table class="table table-bordered" sumary="List of surveys where ...">
		  							<caption>Surveys list</caption>
									<tr class="info">
										<th class="center">*Deadline*</th>
										<th class="center">Survey</th>
										<th class="center">Fullfilment Progress</th>
										<th class="center">Actions</th>
									</tr>
									<%
									System.out.println("Servlet: " + Address.s_SERVLET_SURVEYS_SERVLET);
									for(SurveyTableInfo survey : surveys)
									{
									%>
									<tr>
										<td class="center"><%= survey.getDeadLineDate() %></td>
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
												<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: <%=percentage%>%;"><%= survey.getNumUsersFinished() %>/<%= survey.getNumUsers() %></div>
											</div>
										</td>
										<td>
											<ul class="row">
												<li class="col-sm-3 center"><a href="#" title="clone survey"><i class="fa fa-clone fa-2x"></i></a></li>
							  					<li class="col-sm-2 center"><a href="#" title="statistics"><i class="fa fa-bar-chart fa-2x"></i></a></li>
							  					<li class="col-sm-2 center"><a href="#" title="settings"><i class="fa fa-cogs fa-2x"></i></a></li>
							  					<li class="col-sm-2 center"><a href="#" title="download"><i class="fa fa-download fa-2x"></i></a></li>
							  					<li class="col-sm-3 center"><a href="#" title="pause survey"><i class="fa fa-pause-circle-o fa-2x"></i></a></li>
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
		  					
		  					<div><p>There are not surveys created by you.</p></div>
		  					
		  					<%
		  					}
		  					%>
		  					  					
		  				</div>
	  				</div>
	  			</div>
	  			
	  			<jsp:include page="../frames/fNewSurvey.jsp" />