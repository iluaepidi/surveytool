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
    								String inputMode = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE);
    								String inputType = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE);
    								String decimals = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_DECIMALS);
    								if(decimals.equals("")){
    									decimals="0.01";
    								}
    								else{
    									int afterComma = Integer.parseInt(decimals);
    									decimals="0.";
    									for(int i=1;i<afterComma;i++)
    										decimals=decimals+"0";
    									decimals=decimals+"1";
    								}
    								
    								String minValue = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_MINVALUE);
    								if(minValue.equals(""))
    									minValue="0";
    								
    								String maxValue = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_MAXVALUE);
    								if(maxValue.equals(""))
    									maxValue="9999";
    								
    								int questionId = question.getQuestionId();
    								List<Resource> resources = question.getResources();
    								
    								String textLength = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_TEXTLENGTH);    								
    								if(textLength.equals(""))
    									textLength = "9999";    								
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
									            	<img src="<%= resource.getPathFile() %>" alt="<%= resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT).getText() %>" />
									            	<%
													}
										            %>
									            </div>
									            <%
												}
									            %>
							  					
												<div class="form-question-content">
													<label for="<%= questionId %>" class="visuallyhidden"><%= lang.getContent("accesibility.question.shorttextAnswer") %></label>
													<%if(inputType.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_NUMBER)){
														System.out.println("TextLength: "+textLength+", min: "+minValue+", max: "+maxValue);
													%>
							  							<input type="number" step="<%= decimals%>" pattern="[0-9]+([\,|\.][0-9]+)?" name="<%= questionId %>" placeholder=<%= lang.getContent("placeholder.type_here")%> id="<%= questionId %>" min="<%=minValue%>" max="<%=maxValue%>"></input>
							  						<%} else{ %>
							  							<input type="text" class="form-control" id="<%= questionId %>" name="<%= questionId %>" placeholder=<%= lang.getContent("placeholder.type_here")%> maxlength="<%= textLength%>"></textarea>
							  						<%} %>
												</div>	
												
											</fieldset>																						
										</div>