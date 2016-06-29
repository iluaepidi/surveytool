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
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage("en");
%>  
    								<%
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
    								%>
										<div class="form-question" id="form-question">
											<fieldset>
												<legend>
													<%= lang.getContent("survey_engine.question.title") %> <%= index %>. <%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %>													
												</legend>
												<%
							  					if(question.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION))
							  					{
							  					%>
							  						<p><%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText() %></p>
							  					<% 
							  					}
							  					
												if(!resources.isEmpty())
												{
												%>
												<div class="previewFileUpliaded" id="previewFileUploaded">
													<%
													for(Resource resource : resources)
													{
													%>
									            	<img src="<%= resource.getPathFile() %>" alt="<%= resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT).getText() %>" />
									            	<%
													}
										            %>
									            </div>
									            <%
												}
									            %>
									            
									            
									            
									            <div class="form-question-content">
        <table>
            <thead>
                <tr >
                    <th></th>
                    <%
                    List<OptionsGroup> optionsGroup = question.getOptionsGroups();
                    if (!optionsGroup.isEmpty()){
                    	for(Option option: optionsGroup.get(0).getOptions()){
                    		%>
                    		<th class="matrix-title"><%= option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></th>
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
                    		<th class="matrix-title"><input type="<%= otype%>" name="<%= questionId + "-" + optionGroup.getId()%>" id="<%= id %>" value="<%= option.getId() %>"></th>
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