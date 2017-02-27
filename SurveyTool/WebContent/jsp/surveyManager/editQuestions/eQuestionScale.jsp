<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    								<%
    								Question question = (Question) request.getAttribute(Attribute.s_QUESTION);

    								String title = "";
    								if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
    									title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
    								}

    								Language lang = new Language(getServletContext().getRealPath("/")); 
    								lang.loadLanguage(Language.getLanguageRequest(request));
    								
    								String text = "";
    								if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
    									text = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
    								}
    								%>

										<li class="panel-question" id="panel-question1" qid="<%= question.getQuestionId() %>" index="<%= question.getIndex() %>">
											
											<jsp:include page="eqComponents/eqMoveButtons.jsp" />
					  						
											<div class="panel-question-content">
											
												<jsp:include page="eqComponents/eqHead.jsp" />
	
												<div class="panel-body question-options">
							  							<div class="col-sm-1 col-xs-2">
								  							<label class="type-tittle" for="type-question-<%= question.getIndex() %>"><%=lang.getContent("question.edit.type")%></label>								  							
														</div>	
														<div class="col-sm-4 col-xs-6">	
															<p class="question-type-aux"><%=lang.getContent("question.new.scale")%></p>
														</div>
													<!--<div class="col-md-4">
								  							<select class="form-control" id="type-question-<%= question.getIndex() %>">
															  	<!-- <option value="f"><%=lang.getContent("question.new.formfield")%></option>
															    <option value="p"><%=lang.getContent("question.new.paragraph")%></option>
															    <option value="m"><%=lang.getContent("question.new.multiple")%></option>
															    <option value="s"><%=lang.getContent("question.new.simple")%></option>
															    <option value="o"><%=lang.getContent("question.new.ordering")%></option>
															    <option value="g"><%=lang.getContent("question.new.grading")%></option>
															    <option value="ma"><%=lang.getContent("question.new.matrix")%></option>
															    <option value="sc" selected><%=lang.getContent("question.new.scale")%></option>
															    <option value="c"><%=lang.getContent("question.new.code")%></option> 
															</select>
														</div>-->
														<div class="right col-sm-7 col-xs-4">
															<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
															<jsp:include page="eqComponents/eqMandatoryToggle.jsp" />
															<%} %>
														</div>
	
							  					</div>
								  					
												<div class="panel-body">
								  					<div class="question-frame">
								  					<h6><%=lang.getContent("question.edit.statementSetting.title")%></h6>
								  						<jsp:include page="eqComponents/eqDescription.jsp" />	
								  					
								  						<jsp:include page="eqComponents/eqFiles.jsp" />
								  					</div>
								  					
								  					<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>			
								  					<jsp:include page="eqComponents/eqDependences.jsp">
														<jsp:param value="false" name="withLogic"/>
													</jsp:include>	
													<%} %>	  					
												</div>
											</div>																							
										</li>