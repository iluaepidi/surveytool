<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.OptionsGroup"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Poll"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%
	Poll poll = (Poll) request.getAttribute(Attribute.s_POLL_INFO);
	Question question = poll.getQuestion();
%>
						
            <form action="#" method="post" target="_blank" id="poll_form" class="">		
            	<input type="hidden" name="pid" value="<%= poll.getPollId() %>" />		
                <fieldset>
                
                	<legend class="poll-question"><%= question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></legend>
                	
                	<%
                	for(OptionsGroup optionsGroup : question.getOptionsGroups())
                	{                		
                	%>
                	<div>                	
                	<%
                		int i = 1;
	                	for(Option option : optionsGroup.getOptions())
	            		{
	                %>
	                	<div class="poll-question-option">
							<input type="radio" name="<%= question.getQuestionId() + "-" + optionsGroup.getId() %>" id="optionsRadios<%= i %>" value="<%= option.getId() %>">
						    <label for="optionsRadios<%= i %>"><%= option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></label>
						</div>
	                <%                	
	                		i++;
	            		}
                	%>                	
                	</div>
                	<%
                	
                	}
                	%>
                	
                	<!-- <div class="poll-question-option">
						<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1">
					    <label for="optionsRadios1">yes</label>
					</div>
					
					<div class="poll-question-option">						  
					    <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
					    <label for="optionsRadios2">no</label>
					</div> -->
                    
                </fieldset>
                  
                <div class="poll-send-button">               				
                	<input name="sendPoll" id="sendPoll" type="submit" value="Vote" class="btn btn-primary">
                </div>
                			
            </form>
	  			