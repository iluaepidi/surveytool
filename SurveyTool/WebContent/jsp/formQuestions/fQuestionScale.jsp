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
    								%>
										<div class="form-question" id="form-question">
											<label for="likert1">Question <%= index %>. <%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></label>
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
												 	<div>Totally disgree</div>
												 	<div>Indiferent</div>
												 	<div>Totally agree</div>
												 </div>
											</div>																							
										</div>