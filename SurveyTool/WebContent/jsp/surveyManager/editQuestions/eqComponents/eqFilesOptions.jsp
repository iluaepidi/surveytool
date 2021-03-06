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
if(option==null){
	%>
	<div class="row margin-top-40 hidden" type="global" id="multimediaFrame">
		<div id="div_files">
			<div class="options-files-frame hidden">
				<label><%= lang.getContent("question.edit.files.option.title") %></label>
		
				<ul class="multimedia-list" id="multimediaFilesList">
					
					<jsp:include page="../../components/cMultimediaItem.jsp">
						<jsp:param value="true" name="option"/>
					</jsp:include>
					
				</ul>
			</div>
			
		</div>
	</div>	
			
	<%
}
else{
List<Resource> resources = option.getResources();
%>
<div class="row margin-top-40<% if(resources.isEmpty()){ %> hidden <%}%>" type="global" id="multimediaFrame">
	<div id="div_files">
		<div class="options-files-frame <%if(resources.isEmpty()){ %>hidden<% } %>">
			<label><%= lang.getContent("question.edit.files.option.title") %></label>
	
			<ul class="multimedia-list" id="multimediaFilesList">
				<%							  						
				for(Resource resource : resources)
				{
					request.setAttribute(Attribute.s_RESOURCE, resource);
				%>
				<jsp:include page="../../components/cMultimediaItem.jsp">
					<jsp:param value="true" name="option"/>
				</jsp:include>
				<%
				}
				%>
			</ul>
		</div>
		
	</div>
</div>	
		
<%
}
lang.close();
%>
		