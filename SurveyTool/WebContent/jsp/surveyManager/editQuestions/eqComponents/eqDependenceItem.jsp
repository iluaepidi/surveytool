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
							  									<li class="dependence-item hidden" index="<%= index %>">
							  									<%
																}else{
																%>
																<li class="dependence-item" index="<%= index %>">
																<%	
																}
							  									%>
							  										<fieldset id="fieldset-dependence" class="disable">
							  											<legend class="visuallyhidden" tabindex="-1"><%= index %>º <%= lang.getContent("question.edit.dependence.legend") %> <%= title %></legend>
								  										<!--<label for="dependence-action-<%= index %>" class="dependence-action-label visuallyhidden"><%= lang.getContent("question.edit.dependence.action.label") %></label>
								  										<select id="dependence-action-<%= index %>" class="form-control dependence-action">
								  											<option value="show" selected><%= lang.getContent("question.edit.dependence.action.option.show") %></option>
								  											<option value="hide" ><%= lang.getContent("question.edit.dependence.action.option.hide") %></option>
								  										</select>-->
								  										<label for="dependence-condition-<%= index %>" class="dependence-condition-label visuallyhidden"><%= lang.getContent("question.edit.dependence.condition.label") %></label>
								  										<div class="form-group div-dependence-condition"  style="margin:0px !important;">
									  										
									  										<select id="dependence-condition-<%= index %>" class="form-control dependence-condition hidden">
									  											<option value="and" selected><%= lang.getContent("question.edit.dependence.condition.option.and") %></option>
									  											<option value="or" ><%= lang.getContent("question.edit.dependence.condition.option.or") %></option>
									  										</select>
									  										
									  										<span class="static-condition-and hidden"><%= lang.getContent("question.edit.dependence.condition.option.and") %></span>
									  										<span class="static-condition-or hidden"><%= lang.getContent("question.edit.dependence.condition.option.or") %></span>
									  										
								  										</div>
								  										<label for="dependence-question-<%= index %>" class="dependence-question-label visuallyhidden">
								  											<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>
								  										</label>
								  										<label for="dependence-question-<%= index %>" class="next-dependence-question-label visuallyhidden hidden">
								  											<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>
								  										</label>
								  										<div class="form-group div-dependence-question"  style="margin:0px !important;">
									  										<select id="dependence-question-<%= index %>" class="form-control dependence-question">
									  											<option value="none" class="default-option" selected><%= lang.getContent("question.edit.dependence.question.label_help_hidden") %></option>								  																	  										
									  										</select>
								  										</div>
								  										<label for="dependence-option-<%= index %>" class="dependence-option-label">
								  											<span class="visuallyhidden"><%= lang.getContent("question.edit.dependence.option.label_hidden") %></span>
								  											<%= lang.getContent("question.edit.dependence.option.label_shown") %>
								  											<span class="visuallyhidden">(<%= lang.getContent("question.edit.dependence.option.label_help_hidden") %>)</span>
								  										</label>
								  										
								  										<div class="form-group div-dependence-option"  style="margin:0px !important;">
				    														<select id="dependence-option-<%= index %>" class="form-control dependence-option">
								  												<option value="none" class="default-option" selected><%= lang.getContent("question.edit.dependence.option.label_help_hidden") %></option>
								  											</select>
				    														<span id='dependence-option-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style='top:0px;right: 10px'></span>
			  																<span id='dependence-option-error' class='error hidden' style='top:0px;left: 160px'><%= lang.getContent("msg.error.dependence-option.text") %></span>	
																		</div>
								  										
								  										<div class="option-icons div-remove-dependence">
							  												<label for="remove-dependence" class="visuallyhidden"><%= lang.getContent("accesibility.question.remove.dependence") %>  <%= index %></label>
							  												<button class="btn btn-transparent red" id="remove-dependence" aria-label="<%= lang.getContent("button.remove_dependence") %> <%= index %>"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
							  											</div>
								  									</fieldset>
							  									</li>