<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.OptionsGroup"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.language.Language"%>

<%
Language lang = (Language) request.getAttribute(Attribute.s_SURVEY_LANGUAGE);
Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
String index = request.getParameter(Parameter.s_INDEX);
int questionId = question.getQuestionId();
List<Resource> resources = question.getResources();
    								
String otype = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE);
									
if (otype.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_SIMPLE))
	otype="radio";
else if (otype.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_MULTIPLE))
	otype="checkbox";
else
	otype="radio";


String title = "";
if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
	title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
}

%>
<div class="form-question" id="form-question">
	<fieldset>
		<legend>
			<%= lang.getContent("survey_engine.question.title") %> <%= index %>. <%= title %>													
		</legend>
		<%
		if(question!=null && question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION)!=null)
		{
		%>
			<p><%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText() %></p>
		<% 
		}
		%>
		
		<jsp:include page="fqComponents/fqResources.jsp" />
		
		<div class="form-question-content">
        	<table>
            	<thead>
                	<tr >
                    	<th></th>
                    	<%
                    	List<OptionsGroup> optionsGroup = question.getOptionsGroups();
                    	if (!optionsGroup.isEmpty()){
                    		for(Option option: optionsGroup.get(0).getOptions()){
                    			String opTitle = "";
                    			if(!option.getContents().isEmpty() && option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE) != null) opTitle = option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText(); 
                    		%>
                    			<th class="matrix-title"><%= opTitle %></th>
                    		<%
                    		}
                    	}
                    	%>                  
                    
                	</tr>
            	</thead>
            	<tbody>
            		<%
                    for (OptionsGroup optionGroup: optionsGroup){
                    	%>
                    	<tr>
                    		<td class="matrix-title"><%= optionGroup.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText()%></td>
                    		<%
                    		for(Option option: optionGroup.getOptions()){
                    			String id = "options-" + optionGroup.getId() + "-" + option.getId();
                    			%>
                    			<th class="matrix-title"><input type="<%= otype%>" name="<%= questionId + "-" + optionGroup.getId() + "-" + option.getId()%>" id="<%= id %>" value="<%= option.getId() %>"></th>
                    			<%
                    		}
                    		%>
                    	</tr>
                    <%
                    }
                    %> 
            	</tbody>
        	</table>
    	</div>
	</fieldset>																						
</div>
<%
lang.close();
%>