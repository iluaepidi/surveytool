<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
//String title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();

Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));

String descriptionText = "";
if(question.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION))
{
	descriptionText = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText(); 
}
%>


										<li class="panel-question" id="panel-question1" qid="<%= question.getQuestionId() %>" index="<%= question.getIndex() %>">
											
											<script>
												placeholderBContent = "<%= lang.getContent("placeholder.type_here") %>";
											</script>
											
											<jsp:include page="eqComponents/eqMoveButtons.jsp" />
					  						
											<div class="panel-question-content">
											
												<jsp:include page="eqComponents/eqHeadBodyContent.jsp" />
														
												<div class="panel-body">
													<div class="question-frame">
													<h6><%=lang.getContent("question.edit.statementSetting.title")%></h6>									
											 		<div class="container">
											  			<div class="rowPlus">
											  			<%int questionId = question.getQuestionId(); %>
											    		<label name="section<%= question.getQuestionId() %>"><%= lang.getContent("bcontent.statement") %></label>
											    		
											    			<div class="btn-toolbar editor-buttons" data-role="editor-<%=question.getQuestionId()%>-toolbar" data-target="#editor-<%=question.getQuestionId()%>">
											      				<div class="btn-group">
											        				<a href="#section<%= questionId %>" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" title="<%= lang.getContent("bcontent.font")%>" data-original-title="<%= lang.getContent("bcontent.font")%>"><i class="fa fa-font" aria-hidden="true"></i><b class="caret"></b></a>
											          				<ul class="dropdown-menu">
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Serif" title="Serif" data-original-title="Serif" style="font-family:'Serif'">Serif</a></li>
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Sans" title="Sans" data-original-title="Sans" style="font-family:'Sans'">Sans</a></li>
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Arial" title="Arial" data-original-title="Arial" style="font-family:'Arial'">Arial</a></li>
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Arial Black" title="Arial Black" data-original-title="Arial Black" style="font-family:'Arial Black'">Arial Black</a></li>
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Courier" title="Courier" data-original-title="Courier" style="font-family:'Courier'">Courier</a></li>
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Courier New" title="Courier New" data-original-title="Courier New" style="font-family:'Courier New'">Courier New</a></li>
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Comic Sans MS" title="Comic Sans MS" data-original-title="Comic Sans MS" style="font-family:'Comic Sans MS'">Comic Sans MS</a></li>
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Helvetica" title="Helvetica" data-original-title="Helvetica" style="font-family:'Helvetica'">Helvetica</a></li>
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Impact" title="Impact" data-original-title="Impact" style="font-family:'Impact'">Impact</a></li>
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Lucida Grande" title="Lucida Grande" data-original-title="Lucida Grande" style="font-family:'Lucida Grande'">Lucida Grande</a></li>
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Lucida Sans" title="Lucida Sans" data-original-title="Lucida Sans" style="font-family:'Lucida Sans'">Lucida Sans</a></li>
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Tahoma" title="Tahoma" data-original-title="Tahoma" style="font-family:'Tahoma'">Tahoma</a></li>
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Times" title="Times" data-original-title="Times" style="font-family:'Times'">Times</a></li>
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Times New Roman" title="Times New Roman" data-original-title="Times New Roman" style="font-family:'Times New Roman'">Times New Roman</a></li>
											          					<li><a href="#section<%= questionId %>" tabindex="0" data-edit="fontName Verdana" title="Verdana" data-original-title="Verdana" style="font-family:'Verdana'">Verdana</a></li>
											          				</ul>
											      				</div>
											      				<div class="btn-group">
											        				<a href="#section<%= questionId %>" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" title="<%= lang.getContent("bcontent.font.size")%>" data-original-title="<%= lang.getContent("bcontent.font.size")%>"><i class="fa fa-text-height" aria-hidden="true"></i><b class="caret"></b></a>
											          				<ul class="dropdown-menu">
											          					<li><a href="#section<%= questionId %>" data-edit="fontSize 5" title="<%= lang.getContent("bcontent.huge")%>" data-original-title="<%= lang.getContent("bcontent.huge")%>"><font size="5"><%= lang.getContent("bcontent.huge")%></font></a></li>
											          					<li><a href="#section<%= questionId %>" data-edit="fontSize 3" title="<%= lang.getContent("bcontent.normal")%>" data-original-title="<%= lang.getContent("bcontent.normal")%>"><font size="3"><%= lang.getContent("bcontent.normal")%></font></a></li>
											          					<li><a href="#section<%= questionId %>" data-edit="fontSize 1" title="<%= lang.getContent("bcontent.small")%>" data-original-title="<%= lang.getContent("bcontent.small")%>"><font size="1"><%= lang.getContent("bcontent.small")%></font></a></li>
											          				</ul>
											      				</div>
											      				<div class="btn-group">
											        				<a href="#section<%= questionId %>" class="btn btn-primary" data-edit="bold" title="<%= lang.getContent("bcontent.bold")%>" data-original-title="<%= lang.getContent("bcontent.bold")%>"><i class="fa fa-bold" aria-hidden="true"></i></a>
											        				<a href="#section<%= questionId %>" class="btn btn-primary" data-edit="italic" title="<%= lang.getContent("bcontent.italic")%>" data-original-title="<%= lang.getContent("bcontent.italic")%>"><i class="fa fa-italic" aria-hidden="true"></i></a>
											        				<a href="#section<%= questionId %>" class="btn btn-primary" data-edit="strikethrough" title="<%= lang.getContent("bcontent.strikethrough")%>" data-original-title="<%= lang.getContent("bcontent.strikethrough")%>"><i class="fa fa-strikethrough" aria-hidden="true"></i></a>
											        				<a href="#section<%= questionId %>" class="btn btn-primary" data-edit="underline" title="<%= lang.getContent("bcontent.underline")%>" data-original-title="<%= lang.getContent("bcontent.underline")%>"><i class="fa fa-underline" aria-hidden="true"></i></a>
											      				</div>
											      				<div class="btn-group">
															        <a href="#section<%= questionId %>" class="btn btn-primary" data-edit="insertunorderedlist" title="<%= lang.getContent("bcontent.bullet")%>" data-original-title="<%= lang.getContent("bcontent.bullet")%>"><i class="fa fa-list" aria-hidden="true"></i></a>
															        <a href="#section<%= questionId %>" class="btn btn-primary" data-edit="insertorderedlist" title="<%= lang.getContent("bcontent.number")%>" data-original-title="<%= lang.getContent("bcontent.number")%>"><i class="fa fa-list-ol" aria-hidden="true"></i></a>
															        <a href="#section<%= questionId %>" class="btn btn-primary" data-edit="outdent" title="<%= lang.getContent("bcontent.reduce")%>" data-original-title="<%= lang.getContent("bcontent.reduce")%>"><i class="fa fa-outdent" aria-hidden="true"></i></a>
															        <a href="#section<%= questionId %>" class="btn btn-primary" data-edit="indent" title="<%= lang.getContent("bcontent.indent")%>" data-original-title="<%= lang.getContent("bcontent.indent")%>"><i class="fa fa-indent" aria-hidden="true"></i></a>
															    </div>
											      				<div class="btn-group">
											        				<a href="#section<%= questionId %>" class="btn btn-primary" data-edit="justifyleft" title="<%= lang.getContent("bcontent.left")%>" data-original-title="<%= lang.getContent("bcontent.left")%>"><i class="fa fa-align-left" aria-hidden="true"></i></a>
															        <a href="#section<%= questionId %>" class="btn btn-primary" data-edit="justifycenter" title="<%= lang.getContent("bcontent.center")%>" data-original-title="<%= lang.getContent("bcontent.center")%>"><i class="fa fa-align-center" aria-hidden="true"></i></a>
															        <a href="#section<%= questionId %>" class="btn btn-primary" data-edit="justifyright" title="<%= lang.getContent("bcontent.right")%>" data-original-title="<%= lang.getContent("bcontent.right")%>"><i class="fa fa-align-right" aria-hidden="true"></i></a>
															        <a href="#section<%= questionId %>" class="btn btn-primary" data-edit="justifyfull" title="<%= lang.getContent("bcontent.justify")%>" data-original-title="<%= lang.getContent("bcontent.justify")%>"><i class="fa fa-align-justify" aria-hidden="true"></i></a>
											      				</div>
											      				<div class="btn-group">
											      					<a href="#section<%= questionId %>" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" title="<%= lang.getContent("bcontent.hyperlink")%>" data-original-title="<%= lang.getContent("bcontent.hyperlink")%>"><i class="fa fa-link" aria-hidden="true"></i></a>
											        				<div class="dropdown-menu input-append">
											          					<input class="form-control form-edit" placeholder="URL" type="text" data-edit="createLink">
											          					<button class="btn btn-primary btn-add-link" type="button"><%= lang.getContent("bcontent.add")%></button>
											        				</div>
											        				<a href="#section<%= questionId %>" class="btn btn-primary" data-edit="unlink" title="<%= lang.getContent("bcontent.remove")%>" data-original-title="<%= lang.getContent("bcontent.remove")%>"><i class="fa fa-unlink" aria-hidden="true"></i></a>
																</div>
																<div class="btn-group">
															        <a href="#section<%= questionId %>" class="btn btn-primary" data-edit="undo" title="<%= lang.getContent("bcontent.undo")%>" data-original-title="<%= lang.getContent("bcontent.undo")%>"><i class="fa fa-undo" aria-hidden="true"></i></a>
															        <a href="#section<%= questionId %>" class="btn btn-primary" data-edit="redo" title="<%= lang.getContent("bcontent.redo")%>" data-original-title="<%= lang.getContent("bcontent.redo")%>"><i class="fa fa-repeat" aria-hidden="true"></i></a>
											      				</div>
											      				<div class="btn-group">
																	<a href="#section<%= questionId %>" class="btn btn-primary accesibilityHelp" id="accesibilityHelp" aria-label="<%= lang.getContent("button.accesibility_help") %>"><i class="fa fa-question" aria-hidden="true"></i></a>
																</div>
											    			</div>
											    			    			
													    	<!-- <label for="editor-<%=question.getQuestionId()%>" class="visuallyhidden"><%= lang.getContent("accesibility.bcontent") %></label> -->														
														
															<div id="editor-<%=question.getQuestionId()%>" class="wellBContent col-md-9 editor" contenteditable="true" aria-labeledby="exit-help-<%=question.getQuestionId()%>"><%if(descriptionText.equals("")){%><%=lang.getContent("placeholder.type_here")%><%}else{%><%= descriptionText %><%}%></div>
															<div class="editorExitHelp"><p id="exit-help-<%=question.getQuestionId()%>"><%= lang.getContent("bcontent.exithelp") %></p></div>
													    <script>
													    $.getScript('http://mindmup.github.io/bootstrap-wysiwyg/external/jquery.hotkeys.js',function(){
													    	$.getScript('http://mindmup.github.io/bootstrap-wysiwyg/bootstrap-wysiwyg.js',function(){
												
													    	  $('#<%="editor-"+question.getQuestionId()%>').wysiwyg({ toolbarSelector: '[data-role=<%="editor-"+question.getQuestionId()%>-toolbar]'} );
													    	  $('#<%="editor-"+question.getQuestionId()%>').cleanHtml();
											
													    	});
													    });
													    </script>
													    
											  													
													</div>
													
													</div>
											 			
											 			<jsp:include page="eqComponents/eqFiles.jsp" />
											 		</div>
												</div>
											</div>																							
										</li>