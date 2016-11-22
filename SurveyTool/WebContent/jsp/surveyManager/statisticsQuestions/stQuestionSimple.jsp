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
	              <h3 aria-hidden="true"><%= sQ.getNumResponses()%></h3>
	              <p><%= lang.getContent("statistics.boxes.numAnswers")%></p><span class="visuallyhidden">: <%= sQ.getNumResponses()%></span>
	            </div>
	          </div>
	      </div>
	      <div class="row single-questions-row">
		      <div class="canvasPie text">
		      <p class="graph-title no-block"> <%= lang.getContent("statistics.boxes.numAnswersByOption")%></p>
	            	<span class="visuallyhidden">
					<% 
					for(int i = 0; i<o.size();i++){
						int idoption = ((Option)(o.get(i))).getId();
						for(int j=0;j<obg.size();j++){
			    			if ((((OptionsByGroup)(obg.get(j))).getOptionId()) == idoption){
			    				if(((Option)(o.get(i))).getContents().get("text") != null)
			    				{%>
			    				<%= ((Content)(((Option)(o.get(i))).getContents().get("text"))).getText()%>, <%= (int)Math.round((((((OptionsByGroup)(obg.get(j))).getNumResponses()*1.0)/(sQ.getNumResponses()*1.0))*100.0))%>
			    				<%
			    				}
			    				else
			    				{
			    				%>
			    				<%= ((Content)(((Option)(o.get(i))).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE))).getText()%>, <%= (int)Math.round((((((OptionsByGroup)(obg.get(j))).getNumResponses()*1.0)/(sQ.getNumResponses()*1.0))*100.0))%>
			    				<%	
			    				}
			    				%>
			    				<%
			    				j=obg.size();
			    			}
			    		}
					}
					%>
					</span>
		      	<div class="no-block">
		      	<div class="tab-content in-block">
			  		<div id="canvas-holder" class="chart tab-pane active">
						<canvas id="chart-area-<%= question.getQuestionId() %>" width="250" height="250" style="width: 250px; height: 250px;"/>		                
				  	</div>
				  	<script>
				  	<%
					List<String> labels = new ArrayList<String>();
					List<Integer> values = new ArrayList<Integer>();
					
					//System.out.println("Size of options:"+o.size());
					for(int i = 0; i<o.size();i++){
						int idoption = ((Option)(o.get(i))).getId();
						if(((Option)(o.get(i))).getContents().get("text") != null) {
							labels.add(((Content)(((Option)(o.get(i))).getContents().get("text"))).getText());
						} else {
							labels.add(((Content)(((Option)(o.get(i))).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE))).getText());
						}
			    		for(int j=0;j<obg.size();j++){
			    			if ((((OptionsByGroup)(obg.get(j))).getOptionId()) == idoption){
			    				values.add((int)Math.round((((((OptionsByGroup)(obg.get(j))).getNumResponses()*1.0)/(sQ.getNumResponses()*1.0))*100.0)));
			    				j=obg.size();
			    			}
			    		}
					}
					
					String graf = "";
					for(int i=0;i<labels.size();i++)
					{
						graf += "{value: " + values.get(i) + ", " +
								"color: \"" + grafColors[i%9] + "\", " +
								"highlight: \"" +highlight[i%9] + "\", " +
								"label: \"" + labels.get(i) + "\"}";
						if(i < (labels.size()-1))graf += ",";
					}
					
					%>
	        var pieData<%= question.getQuestionId() %> = [
					     <%= graf %>   	
					];
		
						var ctx = document.getElementById("chart-area-<%= question.getQuestionId() %>").getContext("2d");
						window.myPie<%= question.getQuestionId() %> = new Chart(ctx).Pie(pieData<%= question.getQuestionId() %>,{percentageInnerCutout : 40});
					</script>
			  	</div>
				<div class="legendSurvey in-block">
					<h4>Resultados</h4>
				  	<ul>
				  	<%for(int i=0;i<labels.size();i++)
					{
						%>
						<li>
						  			<i class="fa fa-square <%= colors[i%9] %>" aria-hidden="true"></i> <%= labels.get(i) %>: <%= values.get(i) %>%
						  		</li>
						<%
					}
					
					%>
				  	</ul>
				 </div>
				 </div>
			  </div>
			</div>
	        
			