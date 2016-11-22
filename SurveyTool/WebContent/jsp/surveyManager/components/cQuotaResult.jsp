<%@page import="ilu.surveytool.databasemanager.DataObject.Page"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Section"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Quota"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="java.util.ArrayList"%>

<%Integer index = (Integer)request.getAttribute("index");
Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO);
String titleQuestion2="";
Quota quota = (Quota)request.getAttribute("quota");

int quotaMinPercent = 0;
int quotaMinPercetRestant = 0;
int quotaprogress = 0;

Language lang = new Language(getServletContext().getRealPath("/")); 
String languageId = Language.getLanguageRequest(request);
lang.loadLanguage(languageId);


//// Si existe un min y un max, esta bien este sistema.

if(quota.getMaxResponses()>0){
	quotaMinPercent = (int)(((double)(((double)quota.getMinResponses()) / ((double)quota.getMaxResponses())))*100.00);
	quotaMinPercetRestant = 100 - quotaMinPercent;
	quotaprogress = (int)(((double)(((double)quota.getValueProgress()) / ((double)quota.getMaxResponses())))*100.00);
}else if(quota.getValueProgress()>quota.getMinResponses()){
	quotaMinPercent = (int)(((double)(((double)quota.getMinResponses()) / ((double)quota.getValueProgress())))*100.00);
	quotaMinPercetRestant = 100 - quotaMinPercent;
	quotaprogress = (int)(((double)(((double)quota.getValueProgress()) / ((double)quota.getValueProgress())))*100.00);
}else{
	quotaMinPercent = (int)(((double)(((double)quota.getMinResponses()) / ((double)quota.getMinResponses())))*100.00);
	quotaMinPercetRestant = 100 - quotaMinPercent;
	quotaprogress = (int)(((double)(((double)quota.getValueProgress()) / ((double)quota.getMinResponses())))*100.00);
}




String styleMin="";
if(quotaMinPercent>0){
	styleMin="left:5px;";
}else{
	styleMin="left:-5px;";
}

String colorProgress = "progress-bar-warning";
/*
quotaprogress = 100
quotaMinPercent=0

quotaprogress1=0
quotaprogress2=75
*/

if(quotaprogress>=quotaMinPercent){
	colorProgress="";
}

if(quota.getMaxResponses()>0 && quota.getValueProgress()>=quota.getMaxResponses()){
	colorProgress="progress-bar-success";
}


%>


                		<div class="form-group col-md-12 nomargin" id="optionquota">
                			<fieldset class="form-group col-md-12 with100pc nomargin">
                				<legend class="col-sm-4 col-xs-4 quotaresultoption"><%=quota.getNameOption()%></legend>
                				<div class="form-group col-sm-8 col-xs-8 nomargin">
                					<%if(quota.getMaxResponses()>0){ %>
                					<div class="with100pc">
                						<div class="quotaresultoptiondiv1"><span class="quotaresultoptionspan1"><%= lang.getContent("quota.result.max")%> <%=quota.getMaxResponses()%></span> <span class="glyphicon glyphicon-triangle-bottom quotaresultoptionspanmax" aria-hidden="true"></span></div>
                						</div>
                					<%} %>
                					<div class="with100pc">
                						<span class="pull-left">0</span>
                						<span class="pull-right"></span>
                					</div>
                 					<div class="progress quotaresultprogressdiv">
                 						<div class="progress-bar <%=colorProgress%>" role="progressbar" aria-valuenow="<%=quotaprogress%>" aria-valuemin="0" aria-valuemax="100" style="width: <%=quotaprogress%>%;">
						    				<%=quota.getValueProgress()%> <%= lang.getContent("quota.result.surveys")%>
						    			</div>
						    			
									</div>
									<div class="quotaresultoptiondivmin">
                						<div class="quotaresultoptiondivmin2" style="width:<%=quotaMinPercent%>%;">
                							<span class="glyphicon glyphicon-triangle-top" aria-hidden="true" style="<%=styleMin%>"></span>
                						</div>
                						<div style="width:<%=quotaMinPercetRestant%>%;" class="quotaresultoptiondivmin3">&nbsp;</div>
                					</div>
                					<div class="quotaresultoptiondivmintext">
                						<div style="padding-left: <%=quotaMinPercent%>%;" class="quotaresultoptiondivmintext2"> 
                							<span class="quotaresultoptiondivmintext3"><%= lang.getContent("quota.result.min")%> <%=quota.getMinResponses()%></span>
                						</div>
                					</div>
                 				</div>
                 			
                			</fieldset>
                		</div>
                		