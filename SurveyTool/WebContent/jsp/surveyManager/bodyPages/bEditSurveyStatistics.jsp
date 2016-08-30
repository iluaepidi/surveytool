<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.databasemanager.ResponsesDB"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Page"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Section"%>
<%@page import="ilu.surveymanager.statistics.Statistics"%>
<%@page import="ilu.surveymanager.statistics.StatisticsQuestion"%>
<script type="text/javascript">
	surveyTree = <%= request.getAttribute(Attribute.s_JSON_PAGES) %>;
</script>
<script language="JavaScript" src="http://www.geoplugin.net/javascript.gp" type="text/javascript"></script><!-- Esto es para obtener la IP y la localización -->
<%
Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO);

Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
System.out.println(lang);

Statistics surveyStatistic = (Statistics) request.getAttribute(Attribute.s_SURVEY_STATISTIC);
%>
				
<div class="hidden" id="statistics">
	<div class="main-sidebar">
		<ul class="sidebar-menu">
        	<li class="treeview active" id="general-menu"><a href="#"><%= lang.getContent("statistics.menu.general")%></a></li>
        	<li class="treeview">
          		<span><%= lang.getContent("statistics.menu.questions")%></span>
          		<ul class="treeview-menu">
          		<% boolean first= true;
          		for(Section section : survey.getSections()){
					for(Page pag : section.getPages()){
						for(Question question : pag.getQuestions()){
							String title = "";
							if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
								title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
							}
							if(first){
								%>
								<li class="tree-item" id="<%= "question-"+question.getQuestionId() %>"><a href="#"><%= title %></a></li>
								<%
								first=false;
							}
							else{
								%>
								<li class="tree-item" id="<%= "question-"+question.getQuestionId() %>"><a href="#"><%= title %></a></li>
								<%
							}
						%>
            		
            		<%
						}
					}
          		}
          		%>
          		</ul>
        	</li> 
      	</ul>
  	</div>
  	
  <div class="content-wrapper">
      <div class="content-statistics" id="general-info">
	      <div class="row">
	        <div class="col-md-4">
	          <div class="small-box bg-aqua">
	            <div class="inner">
	              <h3><%= surveyStatistic.getNumVisits()%></h3>
	              <p><%= lang.getContent("statistics.boxes.numVisits")%></p>
	            </div>
	          </div>
	        </div>
	        
	        <!-- <div class="col-lg-3 col-xs-6">
	          <div class="small-box bg-yellow">
	            <div class="inner">
	              <h3><%= 4%></h3>
	              <p><%= lang.getContent("statistics.boxes.numAnswers")%></p>
	            </div>
	          </div>
	        </div>-->
	        
	        <div class="col-md-4">
	          <div class="small-box bg-green">
	            <div class="inner">
	            <%
	            if(surveyStatistic.getNumVisits()>0){
	            	System.out.println(surveyStatistic.getNumCompleteResponses());
	            %>
	              <h3><%=(int)((surveyStatistic.getNumCompleteResponses()*1.0/surveyStatistic.getNumVisits()*1.0)*100) %><sup>%</sup></h3>
	            <%
	            } else{
	            %>
	              <h3> - <sup>%</sup></h3>
	            <%
	            }
	            %>
	              <p><%= lang.getContent("statistics.boxes.bounceRate")%></p>
	            </div>
	          </div>
	        </div>
	         
	        <div class="col-md-4">
	          <div class="small-box bg-red">
	            <div class="inner">
	               <%
	            if(surveyStatistic.getNumVisits()>0){
	            	System.out.println(surveyStatistic.getNumCompleteMandatoryResponses());
	            %>
	              <h3><%= (int)((surveyStatistic.getNumCompleteMandatoryResponses()*1.0/surveyStatistic.getNumVisits()*1.0)*100) %><sup>%</sup></h3>
	            <%
	            } else{
	            %>
	              <h3> - <sup>%</sup></h3>
	            <%
	            }
	            %>
	              <p><%= lang.getContent("statistics.boxes.bounceRateMandatory")%></p>
	            </div>
	          </div>
	        </div>
	          
	      </div>
	      
	      <div class="row">
	      	<section class="col-lg-6 connectedSortable ui-sortable">
	      		<div class="nav-tabs-custom">
	            	<!-- Tabs within a box -->
	            	<p class="graph-title"><i class="fa fa-inbox"></i> Sales</p>
	            	<div class="tab-content no-padding">
	            		
		              <div class="chart tab-pane active" id="visits-chart">
		              	 <svg xmlns="http://www.w3.org/2000/svg">
		              	 <polyline
     fill="none"
     stroke="#0074d9"
     stroke-width="3"
     points="
		              	 <%
		              	Map<Date, Integer> visits = surveyStatistic.getVisitsByDay();
		              	
		              	int maxY = 0;
		              	long maxX = 0;
		              	int	height = 300;
		              	long width = 623;
		              	
		              	Iterator it = visits.entrySet().iterator();
		        		while (it.hasNext()) {
		        		    Map.Entry pair = (Map.Entry)it.next();
		        		    
		        		    if(((Integer)(pair.getValue())).intValue()>maxY)
		        		    	maxY=((Integer)(pair.getValue())).intValue();
		        		    
		        		    if(((Date)(pair.getKey())).getTime()>maxX)
		        		    	maxX=((Date)(pair.getKey())).getTime();
		        		    
		        		}
		        		
		        		it = visits.entrySet().iterator();
		        		while (it.hasNext()) {
		        		    Map.Entry pair = (Map.Entry)it.next();
		        		    
		        		    double y = ((((Integer)(pair.getValue())).intValue())*1.0)*((height*1.0)/(maxY*1.0));		        		    
		        		    long x = (((Date)(pair.getKey())).getTime())*width/maxX;
		        		    %>
		        		    <%= x%>,<%= y%>
		        		    <%
		        		    
		        		}
		              	 %>"/>
		              		<!--  <text x="50.5" y="261">
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
		              	--></svg>
		              </div>
	            	</div>
	          	</div>
	      	</section>
	      	<section class="col-lg-6 connectedSortable ui-sortable">
	      		<div class="nav-tabs-custom">
	            	<!-- Tabs within a box -->
	            	<p class="graph-title"><i class="fa fa-inbox"></i> Sales</p>
	            	<div class="tab-content no-padding">
		              <div class="chart tab-pane active" id="responses-chart">
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
	      		</section>
	    	</div>
      	</div>
      
 		<% 
 		String token = "/";
 		for(Section section : survey.getSections()){
					for(Page pag : section.getPages()){
						for(Question question : pag.getQuestions()){
							request.setAttribute(Attribute.s_QUESTION, question);
	  							%>
								<div class="content-statistics hidden" id="single-question-<%= question.getQuestionId() %>">
	  								<jsp:include page="<%= token + question.getStatisticsPage() %>">
										<jsp:param name="index" value="<%= question.getQuestionId() %>" />
									</jsp:include>
    							</div>
								<%
						}
					}
          		}
          		%>     
      	
    
	</div>
</div>

<%
lang.close();
%>
	  			