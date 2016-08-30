<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.OptionsGroup"%>
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
	            	<p class="graph-title"> <%= lang.getContent("statistics.single.title")%></p>
	            	<div class="tab-content no-padding">
		              <ul>
		              <%
		              List<OptionsGroup> list = sQ.getOptionsGroup();
		              for (int i =0;i<list.size();i++){
		            	  String text = list.get(i).getContents().get("text").getText();
		            	  if (!text.equals("")){
		            	  %>
		            	  <li><i class="enum fa fa-circle-o"></i><%= text %></li>
		            	  <%
		            	  }
		              }
		              %>
		              </ul>
	            	</div>
	          	</div>	      	
	    	</div>