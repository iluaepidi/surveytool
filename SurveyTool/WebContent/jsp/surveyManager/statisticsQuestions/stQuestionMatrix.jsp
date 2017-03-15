<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%> 
<%@page import="java.util.ArrayList"%>
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
String[] colors = {"cian", "yellow", "red", "gray", "brown", "green", "blue", "purple", "black"};
String[] grafColors = {"#576C99", "#FFB506", "#F7464A", "#c5c5c5", "#B28B6E", "#ADC6A6", "#57A4DA", "#E3DBEC", "#151C25"};
String[] highlight = {"#4A5C82", "#D99A05", "#D23C3F", "#A7A7A7", "#97765E", "#93A88D", "#4A8BB9", "#C1BAC9", "#12181F"};

Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
String otype = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE);

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
	              <h3 aria-hidden="true"><%= sQ.getNumResponses()%></h3>
	              <p><%= lang.getContent("statistics.boxes.numAnswers")%></p><span class="visuallyhidden">: <%= sQ.getNumResponses()%></span>
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

    						//System.out.println("In for of og.size");
	            			//System.out.println("En bucle de og");
	            			%>
	            			<p class="subgraph-title no-block"> <%= ((Content)(((OptionsGroup)(og.get(j))).getContents().get("text"))).getText()%></p>
	            			<span class="visuallyhidden">
							<%
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
									<%=label%>, <%=(Math.round(((numResponses*1.0)/(sQ.getNumResponses()*1.0))*10000.0))/100.0%><%="\n"%>%
									<%
								}
							}
							
							%>
							</span>
							<%
								List<String> labels = new ArrayList<String>();
								List<String> indexes = new ArrayList<String>();
								List<Integer> values = new ArrayList<Integer>();
	            					int numTotalAnswers = 0;
	            					int indexCount = 0;
	            					for(int i = 0; i<obg.size();i++){
	            						//System.out.println("In for of obg.size:obg.og.id="+(((OptionsByGroup)obg.get(i)).getOptionsGroupId())+", og.id="+((OptionsGroup)(og.get(j))).getId());
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
											//System.out.println("label="+label+", index="+Character.toString((char)(65+indexCount)));
											labels.add(label);
											values.add((int)Math.round(((((numResponses*1.0)/(sQ.getNumResponses()*1.0))*10000.0))/100.0));
											indexes.add(Character.toString((char)(65+indexCount)));											
				        		    		numTotalAnswers =numTotalAnswers + numResponses;
				        		    		indexCount++;
										}
	            					}
	            					
	            					
	            					
	            					if(obg.size()==0){
	            						for(int k=0;k<o.size();k++){
	            							//System.out.println("options:"+((Content)(((Option)(o.get(k))).getContents().get("text"))).getText());
	            							labels.add(((Content)(((Option)(o.get(k))).getContents().get("text"))).getText());
											values.add(0);
											indexes.add(Character.toString((char)(65+k)));
										}
	            						
	            						labels.add(lang.getContent("statistics.matrix.none"));
										values.add(0);
										indexes.add(Character.toString((char)(65+o.size())));
	            					}
	            					else{
	            						labels.add(lang.getContent("statistics.matrix.none"));
										values.add((int)(Math.round(((((sQ.getNumResponses()-numTotalAnswers)*1.0)/(sQ.getNumResponses()*1.0))*10000.0))/100.0));
										indexes.add(Character.toString((char)(65+indexCount)));
	            					}
	            					%>
							
							<div class="no-block">	
					
					<%
					if(otype.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_MULTIPLE)){
					%>
		            		<div class="tab-content no-padding in-block">
	            			<div class="chart tab-pane active" id="visits-chart<%= ((OptionsGroup)(og.get(j))).getId() %>">
		              			<canvas id="myChartMatrix<%= question.getQuestionId() %>-<%= ((OptionsGroup)(og.get(j))).getId() %>" width="550" height="250" style="width: 550px; height: 250px;"></canvas>
								<script>
							
									var labels = [];
									var bars = [];
	            					<%
	            					for(int i=0;i<labels.size();i++){
	            						%>
										labels.push("<%=indexes.get(i)%>");
			        		    		bars.push(<%= values.get(i) %>);
			        		    		<%
	            					}%>
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
							  		var ctx = document.getElementById("myChartMatrix<%= question.getQuestionId() %>-<%= ((OptionsGroup)(og.get(j))).getId() %>").getContext("2d");
							
							  		var myChartMatrix<%= ((OptionsGroup)(og.get(j))).getId() %> = new Chart(ctx).Bar(data, {scaleLabel: function (valuePayload) {
							  		    return Number(valuePayload.value) + '%';
							  		}, tooltipTemplate: function (valuePayload) {
							  		    return valuePayload.label +": "+ Number(valuePayload.value) + '%';
							  		}});
						</script>
					</div>
		            	</div>
		            	
		            	<%
					} else {
						%>
						<div class="tab-content in-block">
			  		<div id="canvas-holder" class="chart tab-pane active">
						<canvas id="myChartMatrix<%= question.getQuestionId() %>-<%= ((OptionsGroup)(og.get(j))).getId() %>" width="250" height="250" style="width: 250px; height: 250px;"/>		                
				  	</div>
				  	<script>
				  	<%
					
					
					
					String graf = "";
					for(int i=0;i<labels.size();i++)
					{
						graf += "{value: " + values.get(i) + ", " +
								"color: \"" + grafColors[i%9] + "\", " +
								"highlight: \"" +highlight[i%9] + "\", " +
								"label: \"" + indexes.get(i) + "\"}";
						if(i < (labels.size()-1))graf += ",";
					}
					
					%>
	        var pieData<%= question.getQuestionId() %> = [
					     <%= graf %>   	
					];
		
	        var ctx = document.getElementById("myChartMatrix<%= question.getQuestionId() %>-<%= ((OptionsGroup)(og.get(j))).getId() %>").getContext("2d");
			
	  		window.myPie<%= ((OptionsGroup)(og.get(j))).getId() %> = new Chart(ctx).Pie(pieData<%= question.getQuestionId() %>,{percentageInnerCutout : 40, tooltipTemplate: function (valuePayload) {
	  		    return valuePayload.label +": "+ Number(valuePayload.value) + '%';
	  		}, scaleLabel: function (valuePayload) {
				  		    return Number(valuePayload.value) + '%';
				  		}});
					</script>
			  	</div>
						<%
					}
		            	%>
		            	<div class="legendSurvey in-block">
		            		<h4><%= lang.getContent("statistics.results")%></h4>
						  	<ul>
						  	<%for(int i=0;i<labels.size();i++)
							{
								%>
								<li>
								  			<b><%= indexes.get(i)%></b>. <%=labels.get(i) %>: <%= values.get(i) %>%
								</li>
								<%
							}
							
							%>
						  	</ul>
						 </div>
					 </div>
					
	            		<%
							}
	            		
	            		%>
	            	
	            	</div>
	          	</div>	      	
	    	</div>