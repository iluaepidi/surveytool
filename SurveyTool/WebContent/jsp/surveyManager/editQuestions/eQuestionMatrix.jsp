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
    								String title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
    								Language lang = new Language(getServletContext().getRealPath("/")); 
									lang.loadLanguage(Language.getLanguageRequest(request));
						  									
							  		%>
										<li class="panel-question" id="panel-question1" qid="<%= question.getQuestionId() %>" index="<%= question.getIndex() %>">
											
											<jsp:include page="eqComponents/eqHead.jsp" />
											
											<div class="panel-body">
							  					<div class="question-options">
							  							<div class="col-md-1">
								  							<label for="type-question-<%= question.getIndex() %>"><%=lang.getContent("question.edit.type")%></label>								  							
														</div>	
														<div class="col-md-4">
								  							<select class="form-control" id="type-question-<%= question.getIndex() %>">
															  	<option value="f"><%=lang.getContent("question.new.formfield")%></option>
															    <option value="p"><%=lang.getContent("question.new.paragraph")%></option>
															    <option value="m"><%=lang.getContent("question.new.multiple")%></option>
															    <option value="s"><%=lang.getContent("question.new.simple")%></option>
															    <!-- <option value="o"><%=lang.getContent("question.new.ordering")%></option>
															    <option value="g"><%=lang.getContent("question.new.grading")%></option> -->
															    <option value="ma" selected><%=lang.getContent("question.new.matrix")%></option>
															    <option value="sc"><%=lang.getContent("question.new.scale")%></option>
															    <!-- <option value="c"><%=lang.getContent("question.new.code")%></option> -->
															</select>
														</div>
														<div class="right col-md-7">
															<button class="btn btn-question-head btn-sm active" id="mandatoryButton" active="<%= question.isMandatory() %>"><i class="fa fa-asterisk red"></i><span><%= lang.getContent("question.mandatory") %></span></button>
														</div>
							  					</div>
							  					
							  					<div class="question-frame">
							  					<h4><%=lang.getContent("question.edit.statementSetting.title")%></h4>
							  						<jsp:include page="eqComponents/eqDescription.jsp" />	
							  					
							  						<jsp:include page="eqComponents/eqFiles.jsp" />
							  					</div>
							  					
							  					
							  					<jsp:include page="eqComponents/eqResponseSettings.jsp" >
												    <jsp:param name="response" value="../eqResponses/eqMatrix.jsp" />
												</jsp:include>
							  					
							  							  					
											</div>																							
										</li>