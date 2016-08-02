<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>

<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));

Option option = (Option) request.getAttribute(Attribute.s_OPTION);
List<Resource> resources = option.getResources();
%>
<div class="row margin-top-40" type="global" id="multimediaFrame">
	<label><%= lang.getContent("question.edit.files.option.title") %></label>
	<div id="div_files">
		<div class="option-files-frame <%if(resources.isEmpty()){ %>hidden<% } %>">
			<ul class="multimedia-list" id="multimediaFilesList">
				<%							  						
				for(Resource resource : resources)
				{
					request.setAttribute(Attribute.s_RESOURCE, resource);
				%>
				<jsp:include page="../../components/cMultimediaItem.jsp" />
				<%
				}
				%>
			</ul>
		</div>
		
	</div>
</div>	
		
<%
lang.close();
%>
		