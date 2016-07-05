<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    								<%
    								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    								//String title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
    								%>
										<li class="panel-question" id="panel-question1" qid="<%= question.getQuestionId() %>" index="<%= question.getIndex() %>">
											
											<jsp:include page="eqComponents/eqHead.jsp" />
											
											<div class="panel-body">									
							  					
							  					<jsp:include page="eqComponents/eqDescription.jsp" />	
							  					
							  					<jsp:include page="eqComponents/eqFiles.jsp" />
							  					
							  					<div class="question-frame">
							  						<h4>Options</h4>
							  						<div class="row">
							  							<div class="col-md-4">
								  							<label for="type-question-<%= question.getIndex() %>">Type</label>
								  							<select class="form-control" id="type-question-<%= question.getIndex() %>">
															  	<option value="tx">Long Text</option>
																<option value="gra">Short Text</option>
															  	<option value="ls" selected>Numerical</option>
															    <option value="sim">Simple</option>
															    <option value="mul">Multiple</option>
																<option value="ma">Matrix</option>
															    <option value="ord">Ordering</option>
															</select>
														</div>														
															
														<%
														Language lang = new Language(getServletContext().getRealPath("/")); 
														lang.loadLanguage(Language.getLanguageRequest(request));
								  									
											    		String otherText = "";
							  							if(question.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_OTHER))
							  							{
							  								otherText = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_OTHER).getText(); 
							  							}
							  							
							  							String minValue = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_MINVALUE);
							  							String maxValue = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_MAXVALUE);
							  							%>
														<div class="col-md-8">	
															 <div class="row">
								  								<div class="col-md-6">
								  									<label for="min-value-<%= question.getIndex() %>">Min value</label>																															  							
								  									<textarea class="form-control" id="survey-minValue" rows="1" placeholder="<%= lang.getContent("placeholder.type_minValue") %>" aria-label="<%= lang.getContent("question.edit.description.aria_label") %>" maxlength="1000" onkeypress="return isNumber(event)"><%= minValue %></textarea>
								  							 	</div>
								  								<div class="col-md-6"> 
								  									<label for="max-value-<%= question.getIndex() %>">Max value</label>																															  							
								  									<textarea class="form-control" id="survey-maxValue" rows="1" placeholder="<%= lang.getContent("placeholder.type_maxValue") %>" aria-label="<%= lang.getContent("question.edit.description.aria_label") %>" maxlength="1000" onkeypress="return isNumber(event)"><%= maxValue %></textarea>
								  								 </div>
								  							</div>
								  							
								  							<label for="other-option-<%= question.getIndex() %>">Other field</label>																															  							
								  							<textarea class="form-control" id="survey-question-other-text" rows="1" placeholder="<%= lang.getContent("placeholder.type_label") %>" aria-label="<%= lang.getContent("question.edit.description.aria_label") %>" maxlength="1000"><%= otherText %></textarea>
								  							
							  							</div>											 
							  						</div>						  						
							  					</div>
							  							  					
											</div>																							
										</li>