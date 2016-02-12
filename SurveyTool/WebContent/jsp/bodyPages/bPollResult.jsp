<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.OptionsGroup"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Poll"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%
	/*Poll poll = (Poll) request.getAttribute(Attribute.s_POLL_INFO);
	Question question = poll.getQuestion();*/
%>
			<div class="content-poll">			
	            <div>
	            	<p>Thank you for your collaboration.</p>
	            </div>
		  		
		  		<div class="graphic-poll">
		  			
			  		<div class="legendPoll"></div>
		  		
			  		<div id="canvas-holder" class="canvas-holder">
						<canvas id="chart-area" width="100%" height="100%"/>		                
			  		</div>
			  		
			  	</div>
			  	
		  		<div>
		  			<p>If you want to participate in other surveys, fill in the register form. <a href="http://localhost:8080/SurveyTool" target="_blanck" title="go to register form">Register form</a></p>
		  		</div>
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
							highlight: "#FF5A5E",
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