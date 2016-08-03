<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

																
																<%
																Language lang = new Language(getServletContext().getRealPath("/")); 
																lang.loadLanguage(Language.getLanguageRequest(request));
																
																String title = request.getParameter(Parameter.s_TITLE);
																String index = request.getParameter(Parameter.s_INDEX);
																boolean hidden = Boolean.parseBoolean(request.getParameter("hidden"));
																
																if(hidden)
																{
																%>																
							  									<li class="dependence-item hidden">
							  									<%
																}else{
																%>
																<li class="dependence-item">
																<%	
																}
							  									%>
							  										<fieldset>
							  											<legend class="visuallyhidden"><%= index %>º <%= lang.getContent("question.edit.dependence.legend") %> <%= title %></legend>
								  										<label for="dependence-action-<%= index %>" class="dependence-action-label visuallyhidden"><%= lang.getContent("question.edit.dependence.action.label") %></label>
								  										<select id="dependence-action-<%= index %>" class="form-control dependence-action">
								  											<option value="show" selected><%= lang.getContent("question.edit.dependence.action.option.show") %></option>
								  											<option value="hide" ><%= lang.getContent("question.edit.dependence.action.option.hide") %></option>
								  										</select>
								  										<label for="dependence-question-<%= index %>" class="dependence-question-label">
								  											<span class="visuallyhidden"><%= lang.getContent("question.edit.dependence.question.label_hidden") %></span>
								  											<%= lang.getContent("question.edit.dependence.question.label_shown") %>
								  											<span class="visuallyhidden">(<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>)</span>
								  										</label>
								  										<select id="dependence-question-<%= index %>" class="form-control dependence-question">
								  											<option value="none" class="default-option" selected><%= lang.getContent("question.edit.dependence.question.label_help_hidden") %></option>								  																	  										
								  										</select>
								  										<label for="dependence-option-<%= index %>" class="dependence-option-label">
								  											<span class="visuallyhidden"><%= lang.getContent("question.edit.dependence.option.label_hidden") %></span>
								  											<%= lang.getContent("question.edit.dependence.option.label_shown") %>
								  											<span class="visuallyhidden">(<%= lang.getContent("question.edit.dependence.option.label_help_hidden") %>)</span>
								  										</label>
								  										<select id="dependence-option-<%= index %>" class="form-control dependence-option">
								  											<option value="none" class="default-option" selected><%= lang.getContent("question.edit.dependence.option.label_help_hidden") %></option>
								  										</select>
								  									</fieldset>
							  									</li>