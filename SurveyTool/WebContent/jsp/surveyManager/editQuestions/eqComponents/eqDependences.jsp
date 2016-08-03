							  					
												<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="org.codehaus.jettison.json.JSONObject"%>
<%@page import="org.codehaus.jettison.json.JSONArray"%>
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
												
												Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
												String title = "";
			    								if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
			    									title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
			    								}
												
												boolean withLogic = Boolean.parseBoolean(request.getParameter("withLogic"));
												System.out.println("withLogic: " + withLogic);
												
												int numPage = (int) request.getAttribute(Attribute.s_NUM_PAGE);
												JSONArray pagesJson = (JSONArray) request.getAttribute(Attribute.s_JSON_PAGES);
							  					%>
												
							  					<div class="question-frame">
							  						<h6><%= lang.getContent("question.edit.dependences.title") %></h6>
							  						
							  						<div class="dependences-frame">
							  							<button class="btn-transparent btn-close-aspa btn-close-dependences hidden"><span class="visuallyhidden"><%= lang.getContent("button.close.dependences") %></span><i class="fa fa-times-circle fa-2x" aria-hidden="true"></i></button>
							  							<div class="dependences-button center">
							  								<button class="btn btn-primary btn-sm active"><%= lang.getContent("button.add_dependence") %></button>
							  							</div>
							  							<div class="dependences-settings hidden">
							  								<ul class="dependences-list form-inline">

																<jsp:include page="eqDependenceItem.jsp">
																	<jsp:param value="" name="title"/>
																	<jsp:param value="" name="index"/>
																	<jsp:param value="true" name="hidden"/>
																</jsp:include>
																
																<jsp:include page="eqDependenceItem.jsp">
																	<jsp:param value="<%= title %>" name="title"/>
																	<jsp:param value="1" name="index"/>
																	<jsp:param value="false" name="hidden"/>
																</jsp:include>							  									

							  								</ul>
							  								<div class="dependences-add-button center">
								  								<button class="btn btn-primary btn-sm active"><%= lang.getContent("button.add_dependence") %></button>
								  							</div>
							  							</div>
							  						</div>
							  						
							  						<% if(withLogic) 
							  						   {							  								
							  						%>
							  						<div class="logic-frame">
							  							<button class="btn-transparent btn-close-aspa btn-close-logic hidden"><span class="visuallyhidden"><%= lang.getContent("button.close.logic") %></span><i class="fa fa-times-circle fa-2x" aria-hidden="true"></i></button>
							  							<div class="logic-button center">
							  								<button class="btn btn-primary btn-sm active"><%= lang.getContent("button.add_logic") %></button>
							  							</div>
							  							<div class="logic-settings hidden">
							  							<%
							  							if(!question.getOptionsGroups().isEmpty())
					  									{
							  							%>
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
							  							<%
					  									}
					  									else
					  									{
					  									%>
					  										<p><%= lang.getContent("question.edit.logic.no_option") %></p>
					  									<%	
					  									}					  									
							  							%>
							  							</div>
							  						</div>		
							  						<% } %>
							  					
							  					</div>						  					

												<%
												lang.close();
												%>
							  					