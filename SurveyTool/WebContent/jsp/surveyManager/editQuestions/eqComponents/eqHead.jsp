						  					
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
											String title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
						  					%>
						  					
											<div class="panel-heading">	
												<div class="col-md-6">
													<button id="panel-heading-display" class="btn-transparent panel-heading-display-arrow" aria-label="<%= lang.getContent("button.hide_question") %>: <%= title %>"><i class="fa fa-caret-down fa-2x"></i></button>				
													<h3 class="panel-title"><input type="text" maxlength="1000" class="survey-section-title-unselected" id="survey-question-title" value="<%= title %>" aria-label="<%= lang.getContent("question.statement") %>"/></h3>
												</div>
												<div class="panel-section-buttons right col-md-6">
													<button class="btn-transparent btn-remove" id="removeQuestion" aria-label="<%= lang.getContent("button.remove_question") %>: <%= title %>"><i class="fa fa-trash fa-2x"></i></button>
												</div>
											</div>	
						  					
											<%
											lang.close();
											%>
							  					