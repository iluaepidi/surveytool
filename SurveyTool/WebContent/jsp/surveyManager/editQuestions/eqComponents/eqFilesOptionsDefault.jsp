<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>

<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));

int index = (Integer) request.getAttribute(Attribute.s_OPTION);
%>
<div class="row" type="global" id="multimediaFrame" style="margin-top: 40px;">
	<div id="div_files">
		<div class="option-files-frame hidden">
			<label><%= lang.getContent("question.edit.files.option.title") %></label>
	
			<ul class="multimedia-list" id="multimediaFilesList">
				<jsp:include page="../../components/cMultimediaItem.jsp" />
			</ul>
		</div>
		
	</div>
</div>	
		
<%
lang.close();
%>
		