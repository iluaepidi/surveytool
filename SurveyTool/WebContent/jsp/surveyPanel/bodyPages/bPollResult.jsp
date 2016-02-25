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
			  				<li>
			  					<i class="fa fa-square cian"></i> Jaws 39 %
			  				</li>
			  				<li>
			  					<i class="fa fa-square red" ></i> NVDA 30 %
			  				</li>
			  				<li>
			  					<i class="fa fa-square gray"></i> Orca 21 %
			  				</li>
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

				var pieData = [
						{
							value: 30,
							color:"#F7464A",
							highlight: "#FF5A5E",
							label: "NVDA"
						},
						{
							value: 21,
							color:"#c5c5c5",
							highlight: "#c6c6c6",
							label: "Orca"
						},
						{
							value: 39,
							color: "#46BFBD",
							highlight: "#5AD3D1",
							label: "Jaws"
						}
		
					];
		
					window.onload = function(){
						var ctx = document.getElementById("chart-area").getContext("2d");
						window.myPie = new Chart(ctx).Pie(pieData);
					};
		
		
		
			</script>