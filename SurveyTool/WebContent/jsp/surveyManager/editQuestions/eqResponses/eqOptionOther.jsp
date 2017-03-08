											<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
											<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>
											<%@page import="ilu.surveytool.databasemanager.DataObject.OptionsGroup"%>
											<%@page import="ilu.surveytool.constants.Attribute"%>
											<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
											<%@page import="ilu.surveytool.language.Language"%>
											<%
											Language lang = new Language(getServletContext().getRealPath("/")); 
											lang.loadLanguage(Language.getLanguageRequest(request));
											
											Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
											boolean noOptions = false;											
											int optionGroupId = 0;
											if(!question.getOptionsGroups().isEmpty()) question.getOptionsGroups().get(0).getId();
											
											String title = lang.getContent("accesibility.question.option.legend.other");
											if(!question.getOptionsGroups().isEmpty() && question.getOptionsGroups().get(0).getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_OTHER_LABEL))
											{
												title = question.getOptionsGroups().get(0).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_OTHER_LABEL).getText();
											}
											%>
					  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x" aria-hidden="true"></i></button> -->
					  									<div class="circle-info circle-grey fleft">2</div>
					  									<!-- <label for="option<%= question.getQuestionId() %>-<%= optionGroupId %>-other" class="other-option-label fleft"><%= lang.getContent("accesibility.question.option.other") %></label> -->
					  									<fieldset class="other-option-fieldset fleft">
					  										<legend><%= lang.getContent("accesibility.question.option.legend.other") %></legend>
					  										<label for="otherOptionLabel<%= question.getQuestionId() %>" class="other-option-label fleft"><%= lang.getContent("accesibility.question.option.label.other") %></label>
					  										<input type="text" id="otherOptionLabel<%= question.getQuestionId() %>" class="form-control option-other-title otherOptionTitle" value="<%= title %>">
										  					<%
															String textLength = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_TEXTLENGTH);
															String lines = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_TEXTLINES);
												  			%>
					  										<div class="row" type="global">
												  				<fieldset>
																	<legend><%=lang.getContent("question.long.chars")%></legend>																															  							
													  				<div class="question-response-settings" id="genericOptions">
													  					<input class= "question-response-settings-options isLimitedChars" type="checkbox" autocomplete="off" name="isLimitedChars-<%= question.getIndex() %>" id="isLimitedChars<%= question.getQuestionId() %>" <%if(!textLength.equals("")){%> checked="checked" <%}%>>
																		<label class= "question-response-settings-options" for="isLimitedChars<%= question.getQuestionId() %>"><%=lang.getContent("question.long.chars.label") %></label>
													  					<div id="charsId" <% if(textLength.equals("")){ %> class="question-response-settings-sub-none" <%} else{%>class="question-response-settings-sub-inherit"<%} %>>
																			<input type="number" min="0" max="9999" onkeyup="limit(this)" class="survey-question-max-chars" id="survey-question-max-chars<%= question.getQuestionId() %>" value="<%= textLength %>"></input>
																			<label for="survey-question-max-chars<%= question.getQuestionId() %>"><%=lang.getContent("question.form.options.text.charshelp") %></label>
													 					</div>
													 				</div>
													 			</fieldset>
												 			</div>
												 			
															<div class="row" type="global">
																<fieldset>
																	<legend><%=lang.getContent("question.long.lines")%></legend>
																	<div class="question-response-settings">
																		<input class="question-response-settings-options adjust-lines-adjust" type="radio" autocomplete="off" name="lines-other-<%= question.getQuestionId() %>" id="adjust-lines-other-adjust<%= question.getQuestionId() %>" value="adjusted" <%if(lines.isEmpty()){%> checked="checked" <%}%>>
													  					<label class="question-response-settings-options" for="adjust-lines-adjust<%= question.getQuestionId() %>"><%=lang.getContent("question.long.lines.numberChars")%></label>
																	</div>
																	<div class="question-response-settings">	
																		<input class= "question-response-settings-options adjust-lines-set" type="radio" autocomplete="off" name="lines-other-<%= question.getQuestionId() %>" id="adjust-lines-other-set<%= question.getQuestionId() %>" value="set" <%if(!lines.isEmpty()){%> checked="checked" <%}%>>
													  					<label class= "question-response-settings-options" for="adjust-lines-set<%= question.getQuestionId() %>"><%=lang.getContent("question.long.lines.set")%></label>
																		<div id="lines" <% if(lines.equals("")){ %> class="question-response-settings-sub-none" <%} else{%>class="question-response-settings-sub-inherit"<%} %>>
																			<input type="number" min="0" max="9999" onkeydown="limit(this)" onkeyup="limit(this)" id="survey-question-max-lines<%= question.getQuestionId() %>" class="survey-question-max-lines" value="<%= lines %>"></input>
													 						<label for="survey-question-max-lines<%= question.getQuestionId() %>"><%=lang.getContent("question.long.lines.set.type")%></label>
													  					</div>
													  				</div>
													  			</fieldset>
												  			</div>					  										
					  									</fieldset>
					  									
						  								<!-- <input id="option<%= question.getQuestionId() %>-<%= optionGroupId %>-other" type="text" maxlength="10000" class="option-title-other form-control fleft option" index="0" oid="0" placeholder="<%= lang.getContent("question.edit.placeholder.option.other") %>"/> -->
					  									<div class="option-icons fleft">
						  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x" aria-hidden="true"></i></button>
						  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x" aria-hidden="true"></i></button> -->

						  									<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
						  									<label for="remove-option<%= question.getQuestionId() %>-<%= optionGroupId %>-other" class="visuallyhidden"><%= lang.getContent("accesibility.question.remove.option") %>  <%= lang.getContent("accesibility.question.option.other") %></label>
						  									<button class="btn btn-transparent fleft red removeOptionOther" id="remove-option<%= question.getQuestionId() %>-<%= optionGroupId %>-other" aria-label="<%= lang.getContent("button.remove_option") %> 2"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
						  									<% } %>
						  								</div>
						  							
								  			<%
								  			lang.close();							  				
								  			%>							  			
