						  					
											<%@page import="ilu.surveytool.language.Language"%>
											<%@page import="ilu.surveytool.constants.Attribute"%>
											<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
											<%
											Language lang = new Language(getServletContext().getRealPath("/")); 
											lang.loadLanguage(Language.getLanguageRequest(request));
 
											Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
											
											String toggleClass = "";
											if(!question.isMandatory()) toggleClass = "off";
											
						  					%>
						  								<label for="mandatoryToggle-<%= question.getQuestionId() %>" class="mandatory-toggle-label"><%= lang.getContent("accesibility.question.mandatory") %></label>
														<input id="mandatoryToggle-<%= question.getQuestionId() %>" type="checkbox" class="visuallyHidden mandatoryToggle" <% if(question.isMandatory()){ %>checked<%} %> autocomplete="off">
														<div data-toggle="toggle" class="toggle btn btn-primary mandatory-toggle-div <%= toggleClass %>"  tabindex="0">
															<div class="toggle-group">
																<label  for="mandatoryToggle-<%= question.getQuestionId() %>" class="btn btn-primary toggle-on mandatory-toggle-on" aria-hidden="true"><%= lang.getContent("question.mandatory.yes") %></label>
																<label for="mandatoryToggle-<%= question.getQuestionId() %>" class="btn btn-default active toggle-off mandatory-toggle-off" aria-hidden="true"><%= lang.getContent("question.mandatory.no") %></label>
																<span class="toggle-handle btn btn-default" aria-hidden="true"></span>
															</div>
														</div>
																												
														<!--  <button class="btn btn-question-head btn-sm active mandatory-button" id="mandatoryButton" active="<%= question.isMandatory() %>"><i class="fa fa-asterisk red" aria-hidden="true"></i><span><%= lang.getContent("question.mandatory") %></span></button>-->
											<%											
											lang.close();
											%>
							  					