<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.language.Language"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage("en");
%>
										<div class="add-menu-frame" id="add-menu-frame">
											<div class="col-sm-5 add-vacio"></div>
				  							<a class="btn-add col-sm-2" href="#btn-question" aria-label="<%= lang.getContent("button.aria_label.add_content") %>"><i class="fa fa-plus-circle fa-2x"></i></a>
				  							<div class="col-sm-5 add-menu">
				  								<div class="col-sm-1">...</div>
				  								<div class="btn-group col-sm-11" role="group" aria-label="...">
													<button type="button" class="btn btn-add-menu" id="btn-question" data-toggle="modal" data-target="#newQuestionModal"><%= lang.getContent("button.add_content.question") %></button>
													<button type="button" class="btn btn-add-menu" id="btn-body-content" disabled="disabled"><%= lang.getContent("button.add_content.body") %></button>
												  	<button type="button" class="btn btn-add-menu" id="btn-page-break" disabled="disabled"><%= lang.getContent("button.add_content.page_break") %></button>
												</div>
				  							</div>  							
				  						</div>
<%
lang.close();
%>