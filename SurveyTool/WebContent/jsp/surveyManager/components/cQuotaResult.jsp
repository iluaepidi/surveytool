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
	colorProgress="progress-bar-info";
}

if(quota.getMaxResponses()>0 && quota.getValueProgress()>=quota.getMaxResponses()){
	colorProgress="progress-bar-success";
}


%>




                		<div class="form-group col-md-12" style="margin:0px;" id="optionquota">
                			<fieldset class="form-group col-md-12" style="width:100%">
                				<legend class="col-md-4" style="border:0px;font-size:16px;"><%=quota.getNameOption()%></legend>
                				<div class="form-group col-md-8">
                					<%if(quota.getMaxResponses()>0){ %>
                					<div style="width: 100%;">
                						<div style="width: 100%;float: left;text-align: right;"><span style="position: relative;top: 8px;left: 24px;">max <%=quota.getMaxResponses()%></span> <span class="glyphicon glyphicon-triangle-bottom" aria-hidden="true" style="position: relative;top: 25px;left:6px;"></span></div>
                						<div style="width: 0%;">&nbsp;</div>
                					</div>
                					<%} %>
                					<div style="width: 100%;">
                						<span class="pull-left">0</span>
                						<span class="pull-right"></span>
                					</div>
                 					<div class="progress" style="width: 100%;margin-bottom:0px !important;">
                 						<div class="progress-bar <%=colorProgress%>" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: <%=quotaprogress%>%;">
						    				<%=quota.getValueProgress()%> surveys
						    			</div>
						    			
									</div>
									<div style="width: 100%;display: inline-block;height: 12px;">
                						<div style="width:<%=quotaMinPercent%>%;float: left;text-align: right;">
                							<span class="glyphicon glyphicon-triangle-top" aria-hidden="true" style="<%=styleMin%>"></span>
                							<!-- <span style="position: relative;top: 15px;left: 18px;">min </%=quota.getMinResponses()%></span>  -->
                						</div>
                						<div style="width:<%=quotaMinPercetRestant%>%;float: left;display: inline-flex;">&nbsp;</div>
                					</div>
                					<div style="width: 100%;display: inline-block;">
                						<div style="width: 100%;text-align: left;padding-left: <%=quotaMinPercent%>%;position: relative;left: -12px;"> 
                							<span style="display: inline-block;width: 120px;text-align: left;">min <%=quota.getMinResponses()%></span>
                						</div>
                					</div>
                 				</div>
                 			
                			</fieldset>
                		</div>
                		