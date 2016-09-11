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

<div class="edit-fees-frame survey-info" id="survey-quota-new" sid="survey-quota-new" quota="survey-quota-new" style="display:none;">
		  							<ul class="survey-sections" id="survey-sections" style="overflow: hidden;">
			  						<li style="display:block;">
	
			  						<div class="widthTitleSurveyCollapsed" id="survey-div-title-fees" style="width: 90%;" qid="" sid="<%=survey.getSurveyId()%>">
			  							<div class="form-group" style="margin:0px;">
			  								<div class="form-group">
								                <label class="col-md-4 control-label profileLabel" for="language">Selecciona una pregunta</label>
								                <div class="col-md-8">     
								                	<div class="form-group">                   
								                    	<select id="selquestionforfees-new" name="selquestionforfees-new" class="selquestionforfees form-control" onchange="changeoptionsfees();">
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
				  						
				                    				<div id="optionsquotanew" class="optionsquota" ogid="">
					                    			
				                    					<div class="form-group" style="margin:0px;display: inline-flex;" id="optionquota">
							  									<div class="form-group">
							  							     		<label class="col-md-4 control-label profileLabel" for="language"></label>
											                	</div>
											                	<div class="form-group col-md-4">
											                		<label class="col-md-4 control-label profileLabel" for="language">Max</label>
											                		<input id="max" name="max" type="text" placeholder="" class="form-control-small col-md-8" value="" index="" oid="" >
											                	</div>
											               		<div class="form-group col-md-4">
											                		<label class="col-md-4 control-label profileLabel" for="language">Min</label>
											                		<input id="min" name="min" type="text" placeholder="" class="form-control-small col-md-8" value="" index="" oid="" >
											                	</div>
														</div>
				                    				
				                    				</div>
										
				  					</div>
				  					<div class="panel-section-buttons right" style="margin-top:10px;">
										<button class="btn-transparent btn-remove" id="removeQuota" aria-label="Remove question: question typo formField"><i class="fa fa-trash fa-2x"></i></button>
									</div>					
									</li>
								</ul>
		  					</div>	