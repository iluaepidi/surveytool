							  					
												<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
												<%@page import="java.util.List"%>
												<%@page import="ilu.surveytool.language.Language"%>
												<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
												<%@page import="ilu.surveytool.constants.Attribute"%>
												<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
												<%
												Language lang = new Language(getServletContext().getRealPath("/")); 
												lang.loadLanguage(Language.getLanguageRequest(request));
												
												boolean withLogic = Boolean.parseBoolean(request.getParameter("withLogic"));
												System.out.println("withLogic: " + withLogic);
							  					%>
												
							  					<div class="question-frame">
							  						<h6><%= lang.getContent("question.edit.dependences.title") %></h6>
							  						
							  						<div class="dependences-frame">
							  							<button class="btn-transparent btn-close-aspa btn-close-dependences hidden"><span class="visuallyhidden"><%= lang.getContent("button.close.dependences") %></span><i class="fa fa-times-circle fa-2x" aria-hidden="true"></i></button>
							  							<div class="dependences-button center">
							  								<button class="btn btn-primary btn-sm active"><%= lang.getContent("button.add_dependence") %></button>
							  							</div>
							  							<div class="dependences-settings">
							  								<ul class="dependences-list">
							  									<li>
							  										<label for="dependence-action-<%= "1" %>"><%= lang.getContent("question.edit.dependence.action.label") %></label>
							  										<select>
							  											<option value="show" selected>Show</option>
							  											<option value="hide" >Hide</option>
							  										</select>
							  									</li>
							  								</ul>
							  							</div>
							  						</div>
							  						
							  						<% if(withLogic) 
							  						   {
							  								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
							  						%>
							  						<div class="logic-frame">
							  							<button class="btn-transparent btn-close-aspa btn-close-logic hidden"><span class="visuallyhidden"><%= lang.getContent("button.close.logic") %></span><i class="fa fa-times-circle fa-2x" aria-hidden="true"></i></button>
							  							<div class="logic-button center">
							  								<button class="btn btn-primary btn-sm active"><%= lang.getContent("button.add_logic") %></button>
							  							</div>
							  							<div class="logic-settings hidden">
							  								<ul class="logic-option-list form-inline">
							  									<%
							  									for(Option option : question.getOptionsGroups().get(0).getOptions())
							  									{
							  									%>
							  									<li class="logic-option">
							  										<label for="logic-option-goto-<%= option.getId() %>"  oid="<%= option.getId() %>">
							  											<%= lang.getContent("question.edit.logic.option.label") %>
							  											<span class="form-control"><%= option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></span>
							  											<%= lang.getContent("question.edit.logic.option.goto.label") %>
							  										</label>
							  										<select id="logic-option-goto-<%= option.getId() %>" class="form-control logic-option-goto">
							  											<option selected><%= lang.getContent("question.edit.logic.option.goto.continue") %></option>
							  										</select>
							  									</li>
							  									<%
							  									}
							  									%>
							  								</ul>
							  							</div>
							  						</div>		
							  						<% } %>
							  					
							  					</div>						  					

												<%
												lang.close();
												%>
							  					