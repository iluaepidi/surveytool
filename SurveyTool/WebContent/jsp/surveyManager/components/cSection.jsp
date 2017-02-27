<%@page import="ilu.surveytool.databasemanager.DataObject.Page"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Section"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%> 
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.language.Language"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));

//int pageId = (int) request.getAttribute(Attribute.s_PAGE_ID);
//Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO);

								Section section = (Section) request.getAttribute(Attribute.s_SECTION);			
								int i = (Integer) request.getAttribute(Attribute.s_INDEX);

								String title = lang.getContent("survey.edit.section.list") + " " + i;
								if(section.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE))
								{
									title = section.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
								}
%>
								    								
								<li class="panel-section" id="panel-section1" scid="<%= section.getSectionId() %>">
									<div class="panel-heading">	
										<button id="panel-heading-display" class="section-head btn-transparent panel-heading-display-arrow" aria-label="<%= lang.getContent("button.hide_section") %>: <%= title %>"><i class="fa fa-caret-down fa-2x" aria-hidden="true"></i></button>				
										<h3 class="panel-title">
											<div class="col-sm-12">
											 	<div class="form-group heading-title">
													<!-- <span  class="noEditingTitle"><%=lang.getContent("survey.edit.section.listQuestions")%></span> -->
													<input type="text" class="survey-section-title-unselected survey-section-title" id="survey-section-title-<%= section.getSectionId() %>" value="<%= title %>" aria-label="<%= lang.getContent("survey.edit.section.title") %>"/>
													<span  id='survey-section-title-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style="color: #a94442;right: 20px"></span>
					  								<span id='survey-section-title-error' class='error hidden' style='top: 0px'><%= lang.getContent("msg.error.section.title") %></span>
												</div>
												<button class="btn-transparent btn-edit"><i class="fa fa-pencil" aria-hidden="true"></i><span class="visuallyHidden"><%= lang.getContent("button.edit_title") %>: <%= title %></span></button>
											</div>
										</h3>
										<div class="panel-section-buttons right">
											<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
												<button class="btn-transparent remove-section" id="removeSection<%= section.getSectionId() %>" aria-label="<%= lang.getContent("button.remove_section") %>: <%= title %>"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
											<% } %>
										</div>
									</div>
									<% request.setAttribute(Attribute.s_SECTION, section); %>
									<jsp:include page="cPages.jsp" />
																								
								</li>
						
<%								
								i++;
							
%>
											
<%							
lang.close();
%>