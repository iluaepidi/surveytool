<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.language.Language"%>
    
    								<%
    								Language lang = new Language(getServletContext().getRealPath("/")); 
    								lang.loadLanguage("en");
    								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    								String index = request.getParameter(Parameter.s_INDEX);
    								int questionId = question.getQuestionId();
    								List<Resource> resources = question.getResources();
    								String lines = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_TEXTLINES);
    								if(lines.equals(""))
    									lines = "1";
    								%>
										<div class="form-question" id="form-question">
											<fieldset>
												<legend>
													<%= lang.getContent("survey_engine.question.title") %> <%= index %>. <%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %>													
												</legend>
												<%
							  					if(question.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION))
							  					{
							  					%>
							  						<p><%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText() %></p>
							  					<% 
							  					}
							  					
												if(!resources.isEmpty())
												{
												%>
												<div class="previewFileUpliaded" id="previewFileUploaded">
													<%
													for(Resource resource : resources)
													{
													%>
									            	<img id="img" src="<%= resource.getPathFile() %>" alt="<%= resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT).getText() %>" />
									            	<%
													}
										            %>
									            </div>
									            <%
												}
									            %>
							  					
												<div class="form-question-content">
													<label for="<%= questionId %>" class="visuallyhidden"><%= lang.getContent("accesibility.question.longtextAnswer") %></label>
							  						<textarea class="form-control" id="<%= questionId %>" name="<%= questionId %>" rows="<%= lines %>" placeholder="Type here_"></textarea>
												</div>	
												
											</fieldset>																						
										</div>