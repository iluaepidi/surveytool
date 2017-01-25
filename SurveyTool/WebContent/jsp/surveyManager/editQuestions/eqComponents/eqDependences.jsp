							  					
												<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="org.codehaus.jettison.json.JSONObject"%>
<%@page import="org.codehaus.jettison.json.JSONArray"%> 
<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
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
												//System.out.println("D1");
			    								if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){

													//System.out.println("D1.1");
			    									title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
			    								}
												//System.out.println("D2");
			    								
												QDependence qdependence = question.getQDependence();

												//System.out.println("D3");
												boolean withLogic = Boolean.parseBoolean(request.getParameter("withLogic"));

												//System.out.println("D4");
												int numPage = (int) request.getAttribute(Attribute.s_NUM_PAGE);

												//System.out.println("D5");
												JSONArray pagesJson = (JSONArray) request.getAttribute(Attribute.s_JSON_PAGES);

												//System.out.println("pagesJson: " + pagesJson.toString());
							  					
												String noDependencesClass = "";
						  						String extraClass = "";
						  						boolean hasPrevQSimple = false;
						  						for(int i = 0; i < numPage - 1; i++)
						  						{
						  						 	JSONArray questionsJson = pagesJson.getJSONObject(i).getJSONArray("questions");
						  						 	for(int j = 0; j < questionsJson.length(); j++)
						  						 	{
						  						 		if(questionsJson.getJSONObject(j).getString("type").equals("simple")) hasPrevQSimple = true;
						  						 	}
						  						}
						  						
						  						if(!hasPrevQSimple) { noDependencesClass = "hidden"; }
						  						
						  						String lastPageClass = "";
						  						//if(!hasPrevQSimple) { extraClass = "noborder"; }
						  						if(numPage == pagesJson.length() || !withLogic) { lastPageClass = "hidden"; }
						  						
						  						String noLogicDependencesClass = "";
						  						if(!hasPrevQSimple && (!lastPageClass.isEmpty() || !question.getQuestionType().equals("simple"))) noLogicDependencesClass = "hidden";						  							
							  					%>
							  					<div class="rules-frame <%= noLogicDependencesClass %>">
							  						<h6 class="visuallyhidden"><%= lang.getContent("question.edit.dependences.title") %></h6>
							  													  						
								  					<div class="nav-config">
									  					<ul class="nav nav-pills">
														  <li role="presentation"><button class="btn-logic <%= lastPageClass %>"><%= lang.getContent("button.logic") %> 
														  <%if(!question.getLogicGoTo().isEmpty()){ %>
														  	<span id="logicCounter" class="badge deplog-counter"><%= question.getLogicGoTo().size() %></span>
														  <%} else {%>
														  	<span id="logicCounter" class="badge deplog-counter hidden">0</span>
														  <%} %>
														  </button></li>
														  <li role="presentation"><button class="btn-dependences <%= noDependencesClass %>"><%= lang.getContent("button.dependences") %> 
														  <%if(qdependence != null && !qdependence.getqdepval().isEmpty()){ %>
														  	<span id="depCounter" class="badge deplog-counter"><%= qdependence.getqdepval().size() %></span>
														  <%} else {%>
														  	<span id="depCounter" class="badge deplog-counter hidden">0</span>
														  <%} %>
														  </button></li>
														</ul>
													</div>
							  						
							  						<fieldset class="dependences-frame hidden">
							  							<legend><%= lang.getContent("question.edit.dependence.question.label_shown") %>:</legend>
							  							<button class="btn-transparent btn-close-aspa btn-close-dependences"><span class="visuallyhidden"><%= lang.getContent("button.close.dependences") %></span><i class="fa fa-times-circle fa-2x" aria-hidden="true"></i></button>
							  						<%
															if(qdependence==null){
															%>
							  							<!-- <div class="dependences-button center">
							  								<button class="btn btn-primary btn-sm active"><%= lang.getContent("button.add_dependence") %></button>
							  							</div> -->
							  							<div class="dependences-settings">
							  								
							  								<ul class="dependences-list form-inline" index = "0">

																<jsp:include page="eqDependenceItem.jsp">
																	<jsp:param value="" name="title"/>
																	<jsp:param value="" name="index"/>
																	<jsp:param value="true" name="hidden"/>
																</jsp:include>
							  								</ul>
							  								<div class="dependences-add-button center">
								  								<button class="btn btn-primary btn-sm active"><i class="fa fa-plus-square" aria-hidden="true"></i><span><%= lang.getContent("button.add_dependence") %></span></button>
								  							</div>
							  							</div>
							  								<%
															}
															else{
																%>
							  							
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
								  										<div class="form-group div-dependence-condition"  style="margin:0px !important;">
								  											<select id="dependence-condition-<%= i %>" class="form-control dependence-condition hidden">
								  												<%if(qdependence.getDependenceType().equals(DBConstants.s_VALUE_DEPENDENCETYPE_AND)){%>
								  												<option value="and" selected><%= lang.getContent("question.edit.dependence.condition.option.and") %></option>
								  												<option value="or" ><%= lang.getContent("question.edit.dependence.condition.option.or") %></option>
								  											<%} else{%>
								  												<option value="and"><%= lang.getContent("question.edit.dependence.condition.option.and") %></option>
								  												<option value="or" selected><%= lang.getContent("question.edit.dependence.condition.option.or") %></option>
								  											<%}%>
								  											</select>
								  										</div>
																		<label for="dependence-question-<%= i %>" class="dependence-question-label visualyhidden hidden">
								  											<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>
								  										</label>
								  										<label for="dependence-question-<%= i %>" class="next-dependence-question-label visuallyhidden">
								  											<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>
								  										</label>
								  										<%
																	}
																	else if(i==2){
																		%>
																	<fieldset id="fieldset-dependence">
							  											<legend class="visuallyhidden"><%= i %>º <%= lang.getContent("question.edit.dependence.legend") + " "+ title%></legend>
																		<label for="dependence-condition-<%= i %>" class="dependence-condition-label visuallyhidden"><%= lang.getContent("question.edit.dependence.condition.label") %></label>
								  										<div class="form-group div-dependence-condition"  style="margin:0px !important;">
								  											<select id="dependence-condition-<%= i %>" class="form-control dependence-condition">
								  											<%if(qdependence.getDependenceType().equals(DBConstants.s_VALUE_DEPENDENCETYPE_AND)){%>
								  												<option value="and" selected><%= lang.getContent("question.edit.dependence.condition.option.and") %></option>
								  												<option value="or" ><%= lang.getContent("question.edit.dependence.condition.option.or") %></option>
								  											<%} else{%>
								  												<option value="and"><%= lang.getContent("question.edit.dependence.condition.option.and") %></option>
								  												<option value="or" selected><%= lang.getContent("question.edit.dependence.condition.option.or") %></option>
								  											<%}%>
								  											</select>
								  										</div>
								  										<label for="dependence-question-<%= i %>" class="dependence-question-label visuallyhidden hidden">							  											
								  											<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>
								  										</label>
								  										<label for="dependence-question-<%= i %>" class="next-dependence-question-label visuallyhidden">
								  											<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>
								  										</label>
								  										
																		<%
																	}
																	else{
																		if(qdependence.getDependenceType().equals(DBConstants.s_VALUE_DEPENDENCETYPE_AND)){%>
																	<fieldset id="fieldset-dependence" condition="and">
																	<%} else {%>
																	<fieldset id="fieldset-dependence" condition="or">
																	<%} %>
							  											<legend class="visuallyhidden"><%= i %>º <%= lang.getContent("question.edit.dependence.legend") + " "+ title%></legend>
							  											<label for="dependence-condition-<%= i %>" class="dependence-condition-label visuallyhidden"><%= lang.getContent("question.edit.dependence.condition.label") %></label>
								  										<div class="form-group div-dependence-condition"  style="margin:0px !important;">
								  											<select id="dependence-condition-<%= i %>" class="form-control dependence-condition hidden">
								  												<%if(qdependence.getDependenceType().equals(DBConstants.s_VALUE_DEPENDENCETYPE_AND)){%>
								  												<option value="and" selected><%= lang.getContent("question.edit.dependence.condition.option.and") %></option>
								  												<option value="or" ><%= lang.getContent("question.edit.dependence.condition.option.or") %></option>
								  											<%} else{%>
								  												<option value="and"><%= lang.getContent("question.edit.dependence.condition.option.and") %></option>
								  												<option value="or" selected><%= lang.getContent("question.edit.dependence.condition.option.or") %></option>
								  											<%}%>
								  											</select>
									  										<span class="static-condition-and"><%= lang.getContent("question.edit.dependence.condition.option.and") %></span>
									  										<span class="static-condition-or"><%= lang.getContent("question.edit.dependence.condition.option.or") %></span>									  										
								  										</div>
								  										<label for="dependence-question-<%= i %>" class="dependence-question-label visuallyhidden hidden">
								  											<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>
								  										</label>
																		<label for="dependence-question-<%= i %>" class="next-dependence-question-label visuallyhidden">
								  											<%= lang.getContent("question.edit.dependence.question.label_help_hidden") %>
								  										</label>
																		<%
																	}
																	%>
																		<div class="form-group div-dependence-question"  style="margin:0px !important;">
																			<select id="dependence-question-<%= question.getQuestionId() %>-<%= i %>" class="form-control dependence-question" autocomplete="off">								  																	  										
																				<option id="question-dependence-<%= depval.getPid() + "-" + depval.getQid() %>" value="<%= depval.getPid() + "-" + depval.getQid() %>" selected="selected"> <%= depval.getQName()%> </option>
								  												<option value="none"><%= lang.getContent("question.edit.dependence.question.label_help_hidden") %></option>
								  											</select>
								  										</div>
								  										<label for="dependence-option-<%= i %>" class="dependence-option-label">
								  											<span class="visuallyhidden"><%= lang.getContent("question.edit.dependence.option.label_hidden") %></span>
								  											<%= lang.getContent("question.edit.dependence.option.label_shown") %>
								  											<span class="visuallyhidden">(<%= lang.getContent("question.edit.dependence.option.label_help_hidden") %>)</span>
								  										</label>
								  										
								  										<div class="form-group div-dependence-option"  style="margin:0px !important;">
				    														<select id="dependence-option-<%= question.getQuestionId() %>-<%= i %>" class="form-control dependence-option" autocomplete="off">
								  												<option id="option-dependence-<%= depval.getOgid() + "-" + depval.getOid() %>" value="<%= depval.getOgid() + "-" + depval.getOid() %>" selected="selected"> <%= depval.getOName()%> </option>
								  												<option value="none"><%= lang.getContent("question.edit.dependence.option.label_help_hidden") %></option>
								  											</select>
				    														<span id='dependence-option-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style='top:0px;right: 10px'></span>
			  																<span id='dependence-option-error' class='error hidden' style='top:0px;left: 160px'><%= lang.getContent("msg.error.dependence-option.text") %></span>	
																		</div>
																		
								  										<div class="option-icons div-remove-dependence">
							  												<label for="remove-dependence" class="visuallyhidden"><%= lang.getContent("accesibility.question.remove.dependence") %>  <%= i %></label>
							  												<button class="btn btn-transparent red" id="remove-dependence" aria-label="<%= lang.getContent("button.remove_dependence") %> <%= i %>"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
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
							  								
							  							
							  						</fieldset>
							  						<% 
							  						
							  						//System.out.println("Num Page: " + numPage + " - pages length: " + pagesJson.length() + " - pagesJson: " + pagesJson.toString());
							  						if(withLogic) 
							  						{							  								
							  						%>
							  						<fieldset class="logic-frame <%= extraClass %> hidden">
							  							<legend><%= lang.getContent("question.edit.logic.option.label") %>:</legend>
							  							<button class="btn-transparent btn-close-aspa btn-close-logic"><span class="visuallyhidden"><%= lang.getContent("button.close.logic") %></span><i class="fa fa-times-circle fa-2x" aria-hidden="true"></i></button>
							  							
							  							<%
							  							if(!question.getOptionsGroups().isEmpty() && !question.getOptionsGroups().get(0).getOptions().isEmpty())
					  									{
							  									
							  										%>							  										
							  										<!-- <div class="logic-button center">
							  											<button class="btn btn-primary btn-sm active"><%= lang.getContent("button.add_logic") %></button>
							  										</div> -->
										  							<div class="logic-settings">
										  								
										  								<p class="hidden"><%= lang.getContent("question.edit.logic.no_option") %></p>
										  								
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
							  											
							  											String optionTitle = "";
							  											if(option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null && !option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText().isEmpty())
							  												optionTitle = option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
							  											else 
							  												optionTitle = option.getResources().get(0).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
							  									%>
								  											<li class="logic-option"  ogid="<%= question.getOptionsGroups().get(0).getId()%>" oid="<%= option.getId() %>">
								  												<label for="logic-option-goto-<%= option.getId() %>">
								  													<span id="logic-option-<%= option.getId() %>"><%= optionTitle %></span>
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
					  									<div class="logic-settings hidden">
					  										<p><%= lang.getContent("question.edit.logic.no_option") %></p>
					  										
					  										<ul class="logic-option-list form-inline hidden">
					  											<li class="logic-option"  ogid="0">
					  												<label for="logic-option-goto-">					  													
					  													<span id="logic-option-"></span>
					  													<%= lang.getContent("question.edit.logic.option.goto.label") %>
					  												</label>
					  												<select id="logic-option-goto-" class="form-control logic-option-goto">
					  													<option value="none" class="default-option" selected><%= lang.getContent("question.edit.logic.option.goto.continue") %></option>					  									
					  												</select>
					  											</li>
					  										</ul>
					  									</div>
					  									
					  									<!-- <div class="logic-button center">
							  								<button class="btn btn-primary btn-sm active"><%= lang.getContent("button.add_logic") %></button>
							  							</div> -->
					  									<%	
					  									}					  									
							  							%>
							  							
							  						</fieldset>		
							  						<% } %>
							  					
							  					</div>						  					

												<%
												lang.close();
												%>