							  					
												<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="org.codehaus.jettison.json.JSONObject"%>
<%@page import="org.codehaus.jettison.json.JSONArray"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.LogicGoTo"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.QDependence"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.QDependenceValue"%>
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
												QDependence qdependence = question.getQDependence();
												
												boolean withLogic = Boolean.parseBoolean(request.getParameter("withLogic"));
												
												int numPage = (int) request.getAttribute(Attribute.s_NUM_PAGE);
												JSONArray pagesJson = (JSONArray) request.getAttribute(Attribute.s_JSON_PAGES);
							  					%>
												
							  					<div class="question-frame">
							  						<h6><%= lang.getContent("question.edit.dependences.title") %></h6>
							  						
							  						<div class="dependences-frame">
							  						<%
															if(qdependence==null){
															%>
							  							<button class="btn-transparent btn-close-aspa btn-close-dependences hidden"><span class="visuallyhidden"><%= lang.getContent("button.close.dependences") %></span><i class="fa fa-times-circle fa-2x" aria-hidden="true"></i></button>
							  							<div class="dependences-button center">
							  								<button class="btn btn-primary btn-sm active"><%= lang.getContent("button.add_dependence") %></button>
							  							</div>
							  							<div class="dependences-settings hidden">
							  								
							  								<ul class="dependences-list form-inline" index = "0">

																<jsp:include page="eqDependenceItem.jsp">
																	<jsp:param value="" name="title"/>
																	<jsp:param value="" name="index"/>
																	<jsp:param value="true" name="hidden"/>
																</jsp:include>
																
																<jsp:include page="eqDependenceItem.jsp">
																	<jsp:param value="<%= title %>" name="title"/>
																	<jsp:param value="-1" name="index"/>
																	<jsp:param value="false" name="hidden"/>
																</jsp:include>
							  								</ul>
							  								<div class="dependences-add-button center">
								  								<button class="btn btn-primary btn-sm active"><%= lang.getContent("button.add_dependence") %></button>
								  							</div>
							  								</div>
							  								<%
															}
															else{
																%>
														<button class="btn-transparent btn-close-aspa btn-close-dependences hidden"><span class="visuallyhidden"><%= lang.getContent("button.close.dependences") %></span><i class="fa fa-times-circle fa-2x" aria-hidden="true"></i></button>
							  							
							  							<div class="dependences-settings">
															<ul class="dependences-list form-inline" index = "<%= qdependence.getId() %>">

																<jsp:include page="eqDependenceItem.jsp">
																	<jsp:param value="" name="title"/>
																	<jsp:param value="" name="index"/>
																	<jsp:param value="true" name="hidden"/>
																</jsp:include>																
																
																<%
																int i=1;
																for (QDependenceValue depval : qdependence.getqdepval()){
																	%>
																<li class="dependence-item" index="<%= depval.getItemId() %>">
																	
								  									<%
																	if(i==1){
																		%>
																	<fieldset id="fieldset-dependence">
							  											<legend class="visuallyhidden"><%= i %>º <%= lang.getContent("question.edit.dependence.legend") + " "+ title%></legend>
							  											<label for="dependence-condition-<%= i %>" class="dependence-condition-label visuallyhidden"><%= lang.getContent("question.edit.dependence.condition.label") %></label>
								  										<div class="form-group"  style="margin:0px !important;">
								  											<select id="dependence-condition-<%= i %>" class="form-control dependence-condition hidden">
								  												<option value="<%= lang.getContent("question.edit.dependence.condition.option.and") %>" selected><%= lang.getContent("question.edit.dependence.condition.option.and") %></option>
								  												<option value="<%= lang.getContent("question.edit.dependence.condition.option.or") %>" ><%= lang.getContent("question.edit.dependence.condition.option.or") %></option>
								  											</select>
								  										</div>
																		<label for="dependence-question-<%= i %>" class="dependence-question-label">
								  											<span class="visuallyhidden"><%= lang.getContent("question.edit.dependence.question.label_hidden") %></span>
								  											<%= lang.getContent("question.edit.dependence.question.label_shown") %>
								  											<span class="visuallyhidden">(<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>)</span>
								  										</label>
								  										<label for="dependence-question-<%= i %>" class="next-dependence-question-label hidden">
								  											<span class="visuallyhidden"><%= lang.getContent("question.edit.dependence.question.label_hidden") %></span>
								  											<%= lang.getContent("question.edit.dependence.question.label_next_shown") %>
								  											<span class="visuallyhidden">(<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>)</span>
								  										</label>
								  										<%
																	}
																	else if(i==2){
																		%>
																	<fieldset id="fieldset-dependence" class="fieldset-second-dependence">
							  											<legend class="visuallyhidden"><%= i %>º <%= lang.getContent("question.edit.dependence.legend") + " "+ title%></legend>
																		<label for="dependence-condition-<%= i %>" class="dependence-condition-label visuallyhidden"><%= lang.getContent("question.edit.dependence.condition.label") %></label>
								  										<div class="form-group"  style="margin:0px !important;">
								  											<select id="dependence-condition-<%= i %>" class="form-control dependence-condition">
								  												<option value="and" selected><%= lang.getContent("question.edit.dependence.condition.option.and") %></option>
								  												<option value="or" ><%= lang.getContent("question.edit.dependence.condition.option.or") %></option>
								  											</select>
								  										</div>
								  										<label for="dependence-question-<%= i %>" class="dependence-question-label hidden">
								  											<span class="visuallyhidden"><%= lang.getContent("question.edit.dependence.question.label_hidden") %></span>
								  											<%= lang.getContent("question.edit.dependence.question.label_shown") %>
								  											<span class="visuallyhidden">(<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>)</span>
								  										</label>
								  										<label for="dependence-question-<%= i %>" class="next-dependence-question-label">
								  											<span class="visuallyhidden"><%= lang.getContent("question.edit.dependence.question.label_hidden") %></span>
								  											<%= lang.getContent("question.edit.dependence.question.label_next_shown") %>
								  											<span class="visuallyhidden">(<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>)</span>
								  										</label>
								  										
																		<%
																	}
																	else{
																		%>
																	<fieldset id="fieldset-dependence" class="fieldset-next-dependences">
							  											<legend class="visuallyhidden"><%= i %>º <%= lang.getContent("question.edit.dependence.legend") + " "+ title%></legend>
							  											<label for="dependence-condition-<%= i %>" class="dependence-condition-label visuallyhidden"><%= lang.getContent("question.edit.dependence.condition.label") %></label>
								  										<div class="form-group"  style="margin:0px !important;">
								  											<select id="dependence-condition-<%= i %>" class="form-control dependence-condition hidden">
								  												<option value="and" selected><%= lang.getContent("question.edit.dependence.condition.option.and") %></option>
								  												<option value="or" ><%= lang.getContent("question.edit.dependence.condition.option.or") %></option>
								  											</select>
								  										</div>
								  										<label for="dependence-question-<%= i %>" class="dependence-question-label hidden">
								  											<span class="visuallyhidden"><%= lang.getContent("question.edit.dependence.question.label_hidden") %></span>
								  											<%= lang.getContent("question.edit.dependence.question.label_shown") %>
								  											<span class="visuallyhidden">(<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>)</span>
								  										</label>
																		<label for="dependence-question-<%= i %>" class="next-dependence-question-label">
								  											<span class="visuallyhidden"><%= lang.getContent("question.edit.dependence.question.label_hidden") %></span>
								  											<%= lang.getContent("question.edit.dependence.question.label_next_shown") %>
								  											<span class="visuallyhidden">(<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>)</span>
								  										</label>
																		<%
																	}
																	%>
																		<div class="form-group"  style="margin:0px !important;">
																			<select id="dependence-question-<%= i %>" class="form-control dependence-question">
								  												<option value="none" class="default-option"><%= lang.getContent("question.edit.dependence.question.label_help_hidden") %></option>								  																	  										
																				<option value="<%= depval.getPid() + "-" + depval.getQid() %>" selected> <%= depval.getQName()%> </option>
								  											</select>
								  										</div>
								  										<label for="dependence-option-<%= i %>" class="dependence-option-label">
								  											<span class="visuallyhidden"><%= lang.getContent("question.edit.dependence.option.label_hidden") %></span>
								  											<%= lang.getContent("question.edit.dependence.option.label_shown") %>
								  											<span class="visuallyhidden">(<%= lang.getContent("question.edit.dependence.option.label_help_hidden") %>)</span>
								  										</label>
								  										
								  										<div class="form-group"  style="margin:0px !important;">
				    														<select id="dependence-option-<%= i %>" class="form-control dependence-option">
								  												<option value="none" class="default-option"><%= lang.getContent("question.edit.dependence.option.label_help_hidden") %></option>
								  												<option value="<%= depval.getOgid() + "-" + depval.getOid() %>" selected> <%= depval.getOName()%> </option>
								  											</select>
				    														<span id='dependence-option-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style='top:0px;right: 10px'></span>
			  																<span id='dependence-option-error' class='error hidden' style='top:0px;left: 160px'><%= lang.getContent("msg.error.dependence-option.text") %></span>	
																		</div>
																		
								  										<div class="option-icons fright">
							  												<label for="remove-dependence" class="visuallyhidden"><%= lang.getContent("accesibility.question.remove.dependence") %>  <%= i %></label>
							  												<button class="btn btn-transparent fright red trash" id="remove-dependence" aria-label="<%= lang.getContent("button.remove_dependence") %> <%= i %>"><i class="fa fa-trash fa-2x"></i></button>
							  											</div>
							  										</fieldset>
							  										
							  									</li>
							  									
																	<%
																	i++;
																}
																%>
							  								</ul>
							  								<div class="dependences-add-button center">
								  								<button class="btn btn-primary btn-sm active"><%= lang.getContent("button.add_dependence") %></button>
								  							</div>
							  								</div>							  							
							  								<%	
															}
															%>
							  								
							  							
							  						</div>
							  						<% if(withLogic) 
							  						   {							  								
							  						%>
							  						<div class="logic-frame">
							  							
							  							
							  							<%
							  							if(!question.getOptionsGroups().isEmpty())
					  									{
							  							
							  									
							  										%>
							  										<button class="btn-transparent btn-close-aspa btn-close-logic hidden"><span class="visuallyhidden"><%= lang.getContent("button.close.logic") %></span><i class="fa fa-times-circle fa-2x" aria-hidden="true"></i></button>
							  											<div class="logic-button center">
							  												<button class="btn btn-primary btn-sm active"><%= lang.getContent("button.add_logic") %></button>
							  										</div>
										  							<div class="logic-settings hidden">
										  								<ul class="logic-option-list form-inline">
										  									<%
							  										for(Option option : question.getOptionsGroups().get(0).getOptions())
							  										{
							  											LogicGoTo logicGoTo = null;
							  											if(!question.getLogicGoTo().isEmpty())
							  											{
							  												for(LogicGoTo lgt : question.getLogicGoTo())
									  										{
							  													if(option.getId() == lgt.getOid()) logicGoTo = lgt;
									  										}
							  											}
							  									%>
							  											<li class="logic-option"  ogid="<%= question.getOptionsGroups().get(0).getId()%>" oid="<%= option.getId() %>">
							  												<label for="logic-option-goto-<%= option.getId() %>">
							  													<%= lang.getContent("question.edit.logic.option.label") %>
							  													<span class="form-control" id="logic-option-<%= option.getId() %>"><%= option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></span>
							  													<%= lang.getContent("question.edit.logic.option.goto.label") %>
							  												</label>
							  												<select id="logic-option-goto-<%= option.getId() %>" class="form-control logic-option-goto">
							  									<%
							  													if(logicGoTo == null)
							  													{
							  									%>
							  													<option value="none" class="default-option" selected><%= lang.getContent("question.edit.logic.option.goto.continue") %></option>
							  									<%
							  													}
							  													else
							  													{
							  									%>
							  													<option value="none" class="default-option"><%= lang.getContent("question.edit.logic.option.goto.continue") %></option>
							  													<option  id="option-goto-<%= logicGoTo.getQDestId() %>" value="<%= logicGoTo.getQDestId() %>" selected><%= logicGoTo.getQDest() %></option>
							  									<%					
							  													}
							  									%>
							  												</select>
							  											</li>
							  									<%
								  									}
										  									%>
											  								</ul>
											  							</div>
											  																  									
							  									<%
					  									}
					  									else
					  									{
					  									%>
					  									<button class="btn-transparent btn-close-aspa btn-close-logic hidden"><span class="visuallyhidden"><%= lang.getContent("button.close.logic") %></span><i class="fa fa-times-circle fa-2x" aria-hidden="true"></i></button>
												  							
					  									<div class="logic-settings hidden">
					  										<p><%= lang.getContent("question.edit.logic.no_option") %></p>
					  									</div>
					  									
					  									<div class="logic-button center">
												  								<button class="btn btn-primary btn-sm active"><%= lang.getContent("button.add_logic") %></button>
												  							</div>
					  									<%	
					  									}					  									
							  							%>
							  							
							  						</div>		
							  						<% } %>
							  					
							  					</div>						  					

												<%
												lang.close();
												%>