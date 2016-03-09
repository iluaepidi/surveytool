<%@page import="ilu.surveytool.databasemanager.DataObject.PollResultResume"%>
<%@page import="java.util.List"%>
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
	List<PollResultResume> results = (List<PollResultResume>) request.getAttribute(Attribute.s_RESPONSES_INFO);
	String[] colors = {"cian", "yellow", "red", "gray", "brown", "green", "blue", "purple", "black"};
	String[] grafColors = {"#576C99", "#FFB506", "#F7464A", "#c5c5c5", "#B28B6E", "#ADC6A6", "#57A4DA", "#E3DBEC", "#151C25"};
	String[] highlight = {"#4A5C82", "#D99A05", "#D23C3F", "#A7A7A7", "#97765E", "#93A88D", "#4A8BB9", "#C1BAC9", "#12181F"};
	//Question question = poll.getQuestion();
%>
			<div class="content-poll">
				<%
	            if(poll.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_ACKNOWLEDGMENT_TEXT))
	            {
	            %>			
	            <div>	           
	            	<p><%= poll.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ACKNOWLEDGMENT_TEXT).getText() %></p>
	            </div>
	            <%
	            }
	            %>
		  		
		  		<div class="graphic-poll">
		  			
			  		<div class="legendPoll">
			  			<ul>
			  			<%
			  			int index = 0;
			  			for(PollResultResume result : results)
			  			{
			  			%>
			  				<li>
			  					<i class="fa fa-square <%= colors[index] %>"></i> <%= result.getValue() + " " + result.getPercentage() + " %" %>
			  				</li>
			  			<%
			  				index++;
			  			}
			  			%>
			  				<!--  <li>
			  					<i class="fa fa-square red" ></i> NVDA 30 %
			  				</li>
			  				<li>
			  					<i class="fa fa-square gray"></i> Orca 21 %
			  				</li>-->
			  			</ul>
			  		</div>
		  		
			  		<div id="canvas-holder" class="canvas-holder">
						<canvas id="chart-area" width="100%" height="100%"/>		                
			  		</div>
			  		
			  	</div>
			  	
		  		
		  		<%
		  		String callText = "";
	            if(poll.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_CALL_TEXT))
	            {	           	
		  			callText = poll.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_CALL_TEXT).getText();
	            }
	            if(poll.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_LABEL))
	            {	        
	            	String label = poll.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_LABEL).getText();
		  			callText += " <a href=" + poll.getCallUrl() + " target='_blanck' title='go to " + label + "'>" + label + "</a>";		  		
	            }
	            if(!callText.isEmpty()) 
	            {
	            	callText = "<p>" + callText + "</p>";
	            %>
	            <div>
	            	<%= callText %>
	            </div>
	            <%
	            }
	            %>
		  		
		  	</div>
	  		
	  		<script>
	  		<%
			index = 0;
			String graf = "";
			for(PollResultResume result : results)
			{
				graf += "{value: " + results.get(index).getPercentage() + ", " +
						"color: \"" + grafColors[index] + "\", " +
						"highlight: \"" +highlight[index] + "\", " +
						"label: \"" + results.get(index).getValue() + "\"}";
				if(index < results.size() - 1)graf += ",";
				
				index++;
			}
			
			%>
				var pieData = [
					     <%= graf %>   	
					];
		
					window.onload = function(){
						var ctx = document.getElementById("chart-area").getContext("2d");
						window.myPie = new Chart(ctx).Pie(pieData);
					};
		
		
		
			</script>