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
	              <h3>5</h3>
	              <p><%= lang.getContent("statistics.boxes.numAnswers")%></p>
	            </div>
	          </div>
	      </div>
	      
	      <div class="row connectedSortable ui-sortable single-questions-row ">
	      		<div class="nav-tabs-custom">
	            	<!-- Tabs within a box -->
	            	<p class="graph-title"><i class="fa fa-inbox"></i> Sales</p>
	            	<div class="tab-content no-padding">
		              <div class="chart tab-pane active" id="visits-chart">
		              	<svg class="chart" width="420" height="150" aria-labelledby="title desc" role="img">
  <title id="title">A bar chart showing information</title>
  <desc id="desc">4 apples; 8 bananas; 15 kiwis; 16 oranges; 23 lemons</desc>
  <g class="bar">
    <rect width="40" height="19"></rect>
    <text x="45" y="9.5" dy=".35em">4 apples</text>
  </g>
  <g class="bar">
    <rect width="80" height="19" y="20"></rect>
    <text x="85" y="28" dy=".35em">8 bananas</text>
  </g>
  <g class="bar">
    <rect width="150" height="19" y="40"></rect>
    <text x="150" y="48" dy=".35em">15 kiwis</text>
  </g>
  <g class="bar">
    <rect width="160" height="19" y="60"></rect>
    <text x="161" y="68" dy=".35em">16 oranges</text>
  </g>
  <g class="bar">
    <rect width="230" height="19" y="80"></rect>
    <text x="235" y="88" dy=".35em">23 lemons</text>
  </g>
</svg>


<div class="chart">
                <canvas id="barChart" style="height: 229px; width: 576px;" height="206" width="518"></canvas>
              </div>
		              </div>
	            	</div>
	          	</div>	      	
	    	</div>