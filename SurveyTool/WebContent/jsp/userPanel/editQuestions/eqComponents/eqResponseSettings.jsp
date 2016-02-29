							  					
							  					<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
												<%@page import="java.util.List"%>
												<%@page import="ilu.surveytool.language.Language"%>
												<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
												<%@page import="ilu.surveytool.constants.Attribute"%>
												<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
												<%
												Language lang = new Language(getServletContext().getRealPath("/")); 
												lang.loadLanguage(Language.getLanguageRequest(request));
												
												String resp = request.getParameter("response");
							  					%>
												
							  					<div class="question-frame">
							  						<h4><%= lang.getContent("question.edit.response_settings.title") %></h4>
							  						
							  						<jsp:include page="<%= resp %>" />
							  							
							  					</div>						  					

												<%
												lang.close();
												%>
							  					