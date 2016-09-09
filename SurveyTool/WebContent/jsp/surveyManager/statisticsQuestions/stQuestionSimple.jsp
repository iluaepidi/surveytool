<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Content"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.OptionsGroup"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.OptionsByGroup"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveymanager.statistics.Statistics"%>
<%@page import="ilu.surveymanager.statistics.StatisticsQuestion"%>
<%@page import="ilu.surveymanager.statistics.Statistics"%>
<%@page import="ilu.surveymanager.statistics.StatisticsQuestion"%>
<%@page import="ilu.surveymanager.handler.SurveysHandler"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
String title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
if(title==null)
	title = "";
String description = "";
if(question.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION))
{
	description = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText(); 
} else
	description="";

int index = Integer.parseInt(request.getParameter("index"));

Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
//System.out.println(lang);

StatisticsQuestion sQ = (StatisticsQuestion) request.getAttribute(Attribute.s_SURVEY_STATISTIC);
List<OptionsByGroup> obg = sQ.getOptionsByGroup();
List<Option> o = sQ.getOptions();
%>



<h3><%= title%></h2>
<h4><%= description%></h3>

		<div class="row single-questions-row">
	        <div class="small-box bg-aqua">
	            <div class="inner">
	              <h3><%= sQ.getNumResponses()%></h3>
	              <p><%= lang.getContent("statistics.boxes.numAnswers")%></p>
	            </div>
	          </div>
	      </div>
	      
	      <div class="row single-questions-row">
	      		<div class="nav-tabs-custom no-block text">
	            	<!-- Tabs within a box -->
	            	<p class="graph-title"> <%= lang.getContent("statistics.boxes.numAnswersByOption")%></p>
	            	<div class="tab-content no-padding">
	            		<div class="chart tab-pane active" id="visits-chart">
		              		<canvas id="myChartSingle" width="550" height="250" style="width: 550px; height: 250px;"></canvas>
							<script>
							
							var labels = [];
							var bars = [];
							<%
							//System.out.println("Size of options:"+o.size());
							int numResponsesExtra = sQ.getNumResponses();
							for(int i = 0; i<o.size();i++){
								int idoption = ((Option)(o.get(i))).getId();
    		    				//System.out.println("New push de labels");
							%>
								labels.push("<%=((Content)(((Option)(o.get(i))).getContents().get("text"))).getText()%>");
	        		    		
	        		    		<%
	        		    		for(int j=0;j<obg.size();j++){
	        		    			if ((((OptionsByGroup)(obg.get(j))).getOptionId()) == idoption){
	        		    				//System.out.println("New push de bars");
	        		    				numResponsesExtra = numResponsesExtra - ((OptionsByGroup)(obg.get(j))).getNumResponses();
	        		    				%>
	        		    				bars.push(<%= Math.round((((((OptionsByGroup)(obg.get(j))).getNumResponses()*1.0)/(sQ.getNumResponses()*1.0))*100.0)*100.0)/100.0%>);
	        		    			<%}
	        		    			}
								}
							
							
							%>
							
								  var data = {
								    labels: labels,
								    datasets: [
								      {
								    	  label: "My First dataset",
									        fillColor: "#00884b",
									        strokeColor: "#00884b",
									        highlightFill: "#fff",
									        highlightStroke: "#00884b",
								        data: bars
								      }
								    ]
								  };
								
								  // Get the context of the canvas element we want to select
								  var ctx = document.getElementById("myChartSingle").getContext("2d");
								
								  var myChartSingle = new Chart(ctx).Bar(data);
							</script>
						</div>
	            	</div>
	          	</div>	      	
	    	</div>