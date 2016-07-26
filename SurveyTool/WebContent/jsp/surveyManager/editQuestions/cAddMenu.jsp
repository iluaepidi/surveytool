<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.language.Language"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
String pageId = request.getParameter("pageId");

%>
										<div class="add-menu-frame" id="add-menu-frame">
				  							<div class="btn-add-element">
				  								<button class="btn-transparent btn-add" aria-label="<%= lang.getContent("button.aria_label.add_content") %>"><i class="fa fa-plus-circle fa-2x"></i></button>
				  							</div>
				  							<div class="add-menu">
				  								<div class="add-menu-points">...</div>
				  								<div class="btn-group" role="group" aria-label="...">
													<button type="button" class="btn btn-add-menu" id="btn-question" active="false" data-toggle="modal" data-target="#newQuestionModal"><%= lang.getContent("button.add_content.question") %></button>
													<button type="button" class="btn btn-add-menu" id="btn-body-content" disabled="disabled"><%= lang.getContent("button.add_content.body") %></button>
												  	<button type="button" class="btn btn-add-menu btn-page-break" id="btn-page-break-<%= pageId %>"><%= lang.getContent("button.add_content.page_break") %></button>
												</div>
				  							</div>  							
				  						</div>
<%

lang.close();
%>