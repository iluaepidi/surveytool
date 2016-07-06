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

    								String title = "";
    								if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
    									title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
    								}

    								String textLength = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_TEXTLENGTH);    								
    								if(textLength.equals(""))
    									textLength = "9999";
    								
    								String lines = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_TEXTLINES);    								
    								if(lines.equals("")){
    									double div = Double.parseDouble(textLength)/40.0;
    									if(div>10.0)
    										lines="10";
    									else
    										lines = ""+(int)Math.ceil(div);
    									System.out.println("Lines: "+lines);
    								}

    								%>
										<div class="form-question" id="form-question">
											<fieldset>
												<legend>
													<%= lang.getContent("survey_engine.question.title") %> <%= index %>. <%= title %>													
												</legend>
												<%
							  					if(question!=null && question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION)!=null)
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
							  						<textarea class="form-control" id="<%= questionId %>" name="<%= questionId %>" rows="<%= lines %>" placeholder="Type here_" maxlength="<%= textLength%>"></textarea>
												</div>	
												
											</fieldset>																						
										</div>