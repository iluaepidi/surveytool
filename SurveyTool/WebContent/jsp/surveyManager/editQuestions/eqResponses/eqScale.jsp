<%@page import="ilu.surveytool.databasemanager.DataObject.Content"%>
<%@page import="java.util.HashMap"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.language.Language"%>
<%
	Language lang = new Language(getServletContext().getRealPath("/")); 
	lang.loadLanguage(Language.getLanguageRequest(request));
	
	String scaleType = (String) request.getAttribute(Attribute.s_SCALE_TYPE);
	HashMap<String, Content> contents = (HashMap<String, Content>) request.getAttribute(Attribute.s_CONTENTS);
	int questionId = (int) request.getAttribute(Attribute.s_QID);
%>					
															<fieldset>
																<legend><%= lang.getContent("question.form.scale.liker.point_label.legend") %></legend>
																<ul class="option-list" id="option-list">
																	<% 
																	int numPoints = scaleType.equals("11") ? 10 : Integer.parseInt(scaleType);
																	int initPoint = numPoints == 10 ? 0 : 1;
																	for(int i = initPoint; i <= numPoints; i++) { 
																		String value = contents.containsKey("label" + i) ? contents.get("label" + i).getText() : "";
																	%>
																		<li class="option-item" id="option-item">
																			<div class="circle-info circle-grey fleft"><%= i %></div>
																			<label for="option<%= questionId %>-<%= i %>" class="visuallyhidden"><%= lang.getContent("question.form.scale.liker.point_label") %>  <%= i %></label>
									  										<input id="option<%= questionId %>-<%= i %>" autocomplete="off" type="text" maxlength="10000" class="point-label form-control fleft option" index="<%= i %>" placeholder="<%= lang.getContent("question.form.scale.liker.point_label") %> <%= i %>" value="<%= value %>"/>
																		</li>
																	<% } %>
																</ul>
															</fieldset>