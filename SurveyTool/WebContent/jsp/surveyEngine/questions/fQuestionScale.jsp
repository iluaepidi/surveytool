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

    								String title = "";
    								if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
    									title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
    								}
    								
    								Language lang = (Language) request.getAttribute(Attribute.s_SURVEY_LANGUAGE);
    								
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
												%>
	
												<jsp:include page="fqComponents/fqResources.jsp" />
												
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