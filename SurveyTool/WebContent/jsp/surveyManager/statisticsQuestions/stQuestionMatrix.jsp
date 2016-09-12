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
List<OptionsGroup> og = sQ.getOptionsGroup();
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
	            	<div class="tab-content no-padding no-block">	
	            		<%
	            		
	            		for(int j=0;j<og.size();j++){
	            			//System.out.println("En bucle de og");
	            			%>
	            			<p class="subgraph-title no-block"> <%= ((Content)(((OptionsGroup)(og.get(j))).getContents().get("text"))).getText()%></p>
	            			<div class="chart tab-pane active no-block" id="visits-chart<%= ((OptionsGroup)(og.get(j))).getId() %>">
		              			<canvas id="myChartMatrix<%= ((OptionsGroup)(og.get(j))).getId() %>" width="550" height="250" style="width: 550px; height: 250px;"></canvas>
								<script>
							
									var labels = [];
									var bars = [];
	            					<%
	            					int numTotalAnswers = 0;
	            					for(int i = 0; i<obg.size();i++){
										if((((OptionsByGroup)obg.get(i)).getOptionsGroupId())==((OptionsGroup)(og.get(j))).getId()){
											int idoption = ((OptionsByGroup)obg.get(i)).getOptionId();
											int numResponses = ((OptionsByGroup)obg.get(i)).getNumResponses();
											String label = "";
											for(int k=0;k<o.size();k++){
												if((((Option)(o.get(k))).getId())==idoption){
													label = ((Content)(((Option)(o.get(k))).getContents().get("text"))).getText();
													k=o.size();
												}
											}
											%>
											labels.push("<%=label%>");
				        		    		bars.push(<%= (Math.round(((numResponses*1.0)/(sQ.getNumResponses()*1.0))*10000.0))/100.0 %>);
				        		    		<%
				        		    		numTotalAnswers =numTotalAnswers + numResponses; 
										}
	            					}
	            					
	            					
	            					
	            					if(obg.size()==0){
	            						for(int k=0;k<o.size();k++){
	            							System.out.println("options:"+((Content)(((Option)(o.get(k))).getContents().get("text"))).getText());
	            							%>
											labels.push("<%=((Content)(((Option)(o.get(k))).getContents().get("text"))).getText()%>");
				        		    		bars.push(0);
				        		    		<%
										}
	            						%>
										labels.push("<%= lang.getContent("statistics.matrix.none")%>");
				        		    	bars.push(0);
				        		    	<%
	            					}
	            					else{
	            						%>
										labels.push("<%= lang.getContent("statistics.matrix.none")%>");
				        		    	bars.push(<%= (Math.round((((sQ.getNumResponses()-numTotalAnswers)*1.0)/(sQ.getNumResponses()*1.0))*10000.0))/100.0 %>);
				        		    	<%
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
							  		var ctx = document.getElementById("myChartMatrix<%= ((OptionsGroup)(og.get(j))).getId() %>").getContext("2d");
							
							  		var myChartMatrix<%= ((OptionsGroup)(og.get(j))).getId() %> = new Chart(ctx).Bar(data);
						</script>
					</div>
					
	            		<%}
	            		
	            		%>
	            	
	            	</div>
	          	</div>	      	
	    	</div>