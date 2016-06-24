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
    								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    								String index = request.getParameter(Parameter.s_INDEX);
    								int questionId = question.getQuestionId();
    								List<Resource> resources = question.getResources();
    								
    								String text = "";
    								if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
    									text = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
    								}
    								
    								Language lang = new Language(getServletContext().getRealPath("/")); 
    								lang.loadLanguage(Language.getLanguageRequest(request));
    								
    								%>
										<div class="form-question" id="form-question">
											<fieldset>
												<legend>
													<%= lang.getContent("survey_engine.question.title") %> <%= index %>. <%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %>													

												</legend>
												
							  						<p><%= text %></p>
							  					<% 
							  					
							  					
												if(!resources.isEmpty())
												{
												%>
												<div class="previewFileUpliaded" id="previewFileUploaded">
													<%
													for(Resource resource : resources)
													{
														
														String textResource = "";
														if(resource!=null &&  resource.getContents()!=null && resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT)!=null){
															textResource = resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT).getText();
					    								}
														
													%>
									            	<img src="<%= resource.getPathFile() %>" alt="<%= textResource %>" />
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
													 	<div><%= lang.getContent("question.form.scale.liker_legend.indiferent") %></div>
													 	<div><%= lang.getContent("question.form.scale.liker_legend.t_agree") %></div>
													 </div>
												</div>	
											</fieldset>																						
										</div>
<%
lang.close();
%>