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
%>  
    								<%
    								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    								String index = request.getParameter(Parameter.s_INDEX);
    								int questionId = question.getQuestionId();
    								List<Resource> resources = question.getResources();
    								%>
										<div class="form-question" id="form-question">
											<fieldset>
												<legend>
													Question <%= index %>. <%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %>													
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
													<div class="likert-options">
													 	<div class="likert-options-frame">
													 		<div>
													 			<label for="likert1">1</label>
													 			<input type="radio" name="<%= questionId %>" value="1" id="likert1"/>
													 		</div>
													 		<div>
													 			<label for="likert2">2</label>
													 			<input type="radio" name="<%= questionId %>" value="2" id="likert2"/>
													 		</div>
													 		<div>
													 			<label for="likert3">3</label>
													 			<input type="radio" name="<%= questionId %>" value="3" id="likert3"/>
													 		</div>
													 		<div>
													 			<label for="likert4">4</label>
													 			<input type="radio" name="<%= questionId %>" value="4" id="likert4"/>
													 		</div>
													 		<div>
													 			<label for="likert5">5</label>
													 			<input type="radio" name="<%= questionId %>" value="5" id="likert5"/>
													 		</div>
													 		<div>
													 			<label for="likert6">6</label>
													 			<input type="radio" name="<%= questionId %>" value="6" id="likert6"/>
													 		</div>
													 		<div>
													 			<label for="likert7">7</label>
													 			<input type="radio" name="<%= questionId %>" value="7" id="likert7"/>
													 		</div>
													 	</div>
													 </div>	
													 <div class="likert-legend">
													 	<div><%= lang.getContent("question.form.scale.liker_legend.t_disgree") %></div>
													 	<div><%= lang.getContent("question.form.scale.liker_legend.t_indiferent") %></div>
													 	<div><%= lang.getContent("question.form.scale.liker_legend.t_agree") %></div>
													 </div>
												</div>	
											</fieldset>																						
										</div>
<%
lang.close();
%>