<%@page import="ilu.surveytool.databasemanager.DataObject.OptionsGroup"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    								<%
    								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    								String title = "";
    								if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
    									title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
    								}
    								
    								Language lang = new Language(getServletContext().getRealPath("/")); 
    								lang.loadLanguage(Language.getLanguageRequest(request));
    								String qtype = "question.new." + question.getQuestionType();
    								System.out.println("Pregunta: " + qtype);
    								%>
												<div class="panel-body question-options">
													<div class="col-sm-3 col-xs-4">
							  							<p class="question-type-aux"><%=lang.getContent("question")%>: <%=lang.getContent(qtype)%></p>
													</div>
													<%
													String offset = "col-sm-offset-1";
													String optionType = question.getQuestionType();
													if(optionType.equals(DBConstants.s_VALUE_QUESTIONTYPE_SIMPLE_RADIO) || optionType.equals(DBConstants.s_VALUE_QUESTIONTYPE_SIMPLE_COMBO))
													{
													%>
													<div class="col-sm-5 col-xs-4">
														<div class="col-sm-3 col-xs-4">
								  							<label class="type-tittle" for="type-simple-response-<%= question.getIndex() %>"><%=lang.getContent("question.edit.type")%></label>								  							
														</div>
														<div class="col-sm-9 col-xs-8">
								  							<select class="form-control type-simple-answer" autocomplete="off" id="type-simple-response-<%= question.getIndex() %>">
								  								<option value="radio" <%if(optionType.equals(DBConstants.s_VALUE_QUESTIONTYPE_SIMPLE_RADIO)){%>selected<%}%>><%=lang.getContent("question.edit.type.answer.radio")%></option>
															    <option value="select" <%if(optionType.equals(DBConstants.s_VALUE_QUESTIONTYPE_SIMPLE_COMBO)){%>selected<%}%>><%=lang.getContent("question.edit.type.answer.select")%></option>
								  							</select>								  							
														</div>
													</div>
													<%
													}
													else offset = "col-sm-offset-6";
													%>
													<div class="right col-sm-3 <%= offset %> col-xs-4">
														<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
														<jsp:include page="eqMandatoryToggle.jsp" />	
														<%} %>
													</div>
							  					</div>
							  		
						  			<%
						  			lang.close();							  				
						  			%>