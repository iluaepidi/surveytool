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
List<Resource> resources = question.getResources();
%>
<div class="row" type="global" id="multimediaFrameQuestion">
	
	<div id="div_files">
		<div class="question-files-frame <%if(resources.isEmpty()){ %>hidden<% } %>">
			<label><%= lang.getContent("question.edit.files.title") %></label>
		
			<ul class="multimedia-list" id="multimediaFilesList">
				<%							  						
				for(Resource resource : resources)
				{
					request.setAttribute(Attribute.s_RESOURCE, resource);
				%>
				
				<jsp:include page="../../components/cMultimediaItem.jsp">
					<jsp:param value="false" name="option"/>
				</jsp:include>
				<%
				}
				%>
			</ul>
		</div>
		<div>
			<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
			<button class="btn btn-primary btn-sm active" id="btn-question-import-file" active="false" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o"></i><span><%= lang.getContent("button.add_file") %></span></button>
			<% } %>
		</div>
	</div>
</div>	
		
<%
lang.close();
%>
		