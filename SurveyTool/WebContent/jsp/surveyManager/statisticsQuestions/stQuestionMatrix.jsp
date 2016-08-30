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
		              <div class="chart tab-pane active" id="visits-chart">
		              	<svg xmlns="http://www.w3.org/2000/svg">
		              		<text x="50.5" y="261">
		              			<tspan>0</tspan>
		              		</text>
		              		<path class="chart-path" d="M63,261H598"></path>
		              		<text x="50.5" y="202">
		              			<tspan>7,500</tspan>
		              		</text>
		              		<path class="chart-path" d="M63,202H598"></path>
		              		<text x="50.5" y="143">
		              			<tspan>15,000</tspan>
		              		</text>
		              		<path class="chart-path" d="M63,143H598"></path>
		              		<text x="50.5" y="84">
		              			<tspan>22,500</tspan>
		              		</text>
		              		<path class="chart-path" d="M63,84.00000000000003H598"></path>
		              		<text x="50.5" y="25">
		              			<tspan>30,000</tspan>
		              		</text>
		              		
		              		<path class="chart-path" d="M63,25.00000000000003H598"></path>
		              		<text class="text-horizontal" x="499.86294364842286" y="273.5" >
		              			<tspan>2013</tspan>
		              		</text>
		              		<text class="text-horizontal" x="261.9286618399068" y="273.5">
		              			<tspan>2012</tspan>
		              		</text>
		              		
		              		<path class="chart-area" d="M63,219.05493333333334C77.94538251227786,219.56626666666668,107.83614753683358,222.6231049981125,122.78153004911144,221.10026666666667C137.73368437041162,219.57673833144582,167.637993013012,209.1350666666667,182.59014733431218,206.86946666666668C197.38655004809883,204.6274666666667,226.9793554756721,204.88256480506598,241.77575818945874,203.06986666666666C256.56538909422306,201.2579981383993,286.1446509037517,194.91349669779092,300.934281808516,192.3712C315.87966432079384,189.80213003112425,345.7704293453496,182.51724094375237,360.71581185762744,182.6244C375.6679661789276,182.73160761041905,405.572274821528,204.17807818499128,420.5244291428282,193.22866666666667C435.32083185661486,182.3933115183246,464.9136372841881,101.94542370540854,479.7100399979748,95.48533333333336C494.3371474862032,89.09915703874188,523.5913624626601,135.1364875658347,538.2184699508884,141.8436C553.1638524631663,148.69665423250134,583.0546174877221,147.7554,598,149.726L598,261L63,261Z"></path>
		              		<path class="chart-line" d="M63,219.05493333333334C77.94538251227786,219.56626666666668,107.83614753683358,222.6231049981125,122.78153004911144,221.10026666666667C137.73368437041162,219.57673833144582,167.637993013012,209.1350666666667,182.59014733431218,206.86946666666668C197.38655004809883,204.6274666666667,226.9793554756721,204.88256480506598,241.77575818945874,203.06986666666666C256.56538909422306,201.2579981383993,286.1446509037517,194.91349669779092,300.934281808516,192.3712C315.87966432079384,189.80213003112425,345.7704293453496,182.51724094375237,360.71581185762744,182.6244C375.6679661789276,182.73160761041905,405.572274821528,204.17807818499128,420.5244291428282,193.22866666666667C435.32083185661486,182.3933115183246,464.9136372841881,101.94542370540854,479.7100399979748,95.48533333333336C494.3371474862032,89.09915703874188,523.5913624626601,135.1364875658347,538.2184699508884,141.8436C553.1638524631663,148.69665423250134,583.0546174877221,147.7554,598,149.726"></path>
		              	</svg>
		              </div>
	            	</div>
	          	</div>	      	
	    	</div>