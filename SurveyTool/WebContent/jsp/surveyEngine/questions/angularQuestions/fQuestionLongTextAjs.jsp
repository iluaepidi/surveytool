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
    								Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
    								
    								%>
										<div class="form-question" id="form-question">
											<fieldset>
												<legend>
													<%= lang.getContent("survey_engine.question.title") %>													
												</legend>
												
							  						<p>sí</p>
							  					
												<div class="form-question-content">
													<label for="" class="visuallyhidden"><%= lang.getContent("accesibility.question.longtextAnswer") %></label>
							  						<textarea class="form-control" id="1" name="1" rows="3" placeholder='lala' maxlength="120"></textarea>
												</div>	
												
											</fieldset>																						
										</div>