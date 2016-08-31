<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveymanager.statistics.Statistics"%>
<%@page import="ilu.surveymanager.statistics.StatisticsQuestion"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
//String title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();

Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));

Statistics surveyStatistic = (Statistics) request.getAttribute(Attribute.s_SURVEY_STATISTIC);
int index = Integer.parseInt(request.getParameter("index"));
System.out.println(index);
StatisticsQuestion sQ = surveyStatistic.getStatisticsByQuestion(index);

%>



		<div class="row single-questions-row">
	        <div class="small-box bg-aqua">
	            <div class="inner">
	              <h3><%= sQ.getNumResponses()%></h3>
	              <p><%= lang.getContent("statistics.boxes.numAnswers")%></p>
	            </div>
	          </div>
	      </div>
	      
	      <div class="row connectedSortable ui-sortable single-questions-row ">
	      		<div class="nav-tabs-custom">
	            	<!-- Tabs within a box -->
	            	<p class="graph-title"><i class="fa fa-inbox"></i> Sales</p>
	            	<div class="tab-content no-padding">
	            	
	            	
	            	<!--<div id="canvas-holder" class="canvas-holder">
						<canvas id="chart-area" width="100%" height="100%"/>		                
			  		</div>
	            	<script>
	  		<%
	  		String[] grafColors = {"#576C99", "#885f00", "#d6090c", "#686868", "#926b4d", "#5a7f51", "#2676af", "#8360ad", "#151C25"};
	  		String[] highlight = {"#4A5C82", "#805b03", "#a8272a", "#494949", "#836652", "#657b5f", "#407aa4", "#746783", "#12181F"};
			index = 0;
			String graf = "";
			for(int i=0;i<9;i++)
			{
				graf += "{value: " + i + ", " +
						"color: \"" + grafColors[i] + "\", " +
						"highlight: \"" +highlight[i] + "\", " +
						"label: \"" + i + "\"}";
			}
			
			%>
				var pieData = [
					     <%= graf %>   	
					];
		
					window.onload = function(){
						var ctx = document.getElementById("chart-area").getContext("2d");
						window.myPie = new Chart(ctx).Pie(pieData);
					};
		
		
		
			</script>-->
		               <div class="chart tab-pane active" id="visits-chart">
		              	
<canvas id="myChart" width="550" height="250" style="width: 550px; height: 250px;"></canvas>
<script>
  var data = {
    labels: ["January", "February", "March", "April", "May", "June", "July"],
    datasets: [
      {
        label: "My First dataset",
        fillColor: "rgba(0,0,0,0)",
        strokeColor: "#00884b",
        pointColor: "#00884b",
        pointStrokeColor: "#fff",
        pointHighlightFill: "#fff",
        pointHighlightStroke: "#00884b",
        data: [65, 59, 80, 81, 56, 55, 40]
      },
      {
        label: "My Second dataset",
        fillColor: "rgba(0,0,0,0)",
        strokeColor: "#B60000",
        pointColor: "#B60000",
        pointStrokeColor: "#fff",
        pointHighlightFill: "#fff",
        pointHighlightStroke: "#B60000",
        data: [28, 48, 40, 19, 86, 27, 90]
      }
    ]
  };

  // Get the context of the canvas element we want to select
  var ctx = document.getElementById("myChart").getContext("2d");

  // Instantiate a new chart using 'data'
  var myChart = new Chart(ctx).Line(data);
</script>

		              </div>
	            	</div>
	          	</div>	      	
	    	</div>