<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    								<%
    								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    								String index = request.getParameter(Parameter.s_INDEX);
    								int questionId = question.getQuestionId();
    								List<Resource> resources = question.getResources();
    								%>
										<div class="form-question" id="form-question">
											<label for="<%= questionId %>">
												Question <%= index %>. <%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %>
												<%
												if(!resources.isEmpty())
												{
												%>
												<div class="previewFileUpliaded" id="previewFileUploaded">
													<%
													for(Resource resource : resources)
													{
													%>
									            	<img src="<%= resource.getPathFile() %>" alt="<%= resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT).getText() %>" />
									            	<%
													}
										            %>
									            </div>
									            <%
												}
									            %>
											</label>
											<div class="form-question-content">
												<textarea class="form-control" id="<%= questionId %>" name="<%= questionId %>" rows="3" placeholder="Type here_"></textarea>
											</div>																							
										</div>