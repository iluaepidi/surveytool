<%@page import="ilu.surveytool.databasemanager.DataObject.Page"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Section"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@page import="java.util.ArrayList"%>

<%Integer index = (Integer)request.getAttribute("index");
Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO);
String titleQuestion="";
List<Question> listQuestionFees = (ArrayList<Question>) request.getAttribute("listQuestionFees");

Integer quotaid = (Integer)request.getAttribute("quotaid");%>

<!-- <div class="survey-info quotadiv" id="survey-quota-<%=index%>" sid="<%=index%>" quota="<%=index%>"> -->
		  						<li class="quota-item" id="survey-quota-<%=index%>" sid="<%=index%>" quota="<%=index%>">
	
			  						<div class="widthTitleSurveyCollapsed with90percent" id="survey-div-title-fees" qid="" sid="<%=survey.getSurveyId()%>">
			  							<div class="form-group nomargin">
			  								<div class="form-group">
								                <div class="col-md-8">     
								                	<div class="form-group">
								                		<h5 id="questionquotaname<%=index%>" class="quotaname"></h5>
								                		<select id="selquestionforfees<%=index%>" name="selquestionforfees<%=index%>" class="selquestionforfees form-control" onchange="changeoptionsfees(<%=index%>);" style="display:none;">
								                    		<% for(Question question : listQuestionFees){
								                    			if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
			    													titleQuestion = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
					    										}
					    									%>
								                    		<option value="<%=question.getQuestionId() %>"><%=titleQuestion %></option>
								                    		<%} %>
								                    	</select>
								                	</div>
								                </div>
								            </div>
				  						</div>
				  						
				                    				<div id="optionsquota<%=index%>" class="optionsquota" ogid="">
					                    			
				                    					<div class="form-group" class="quoteoption" id="optionquota">
				                    							
														</div>
				                    				
				                    				</div>
										
				  					</div>
				  					<div class="panel-section-buttons remove-quota right" class="margintop10">
										<button class="btn-transparent btn-remove quotaremovebtn" id="removeQuota" aria-label="Remove question: question typo formField"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
									</div>					
								</li>
		  					<!-- </div> -->	