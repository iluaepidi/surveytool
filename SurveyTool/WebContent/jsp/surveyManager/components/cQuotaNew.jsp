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
List<Integer> listQuestionAvaiblesNoVisible = (ArrayList<Integer>) request.getAttribute("listQuestionNoVisible");

Integer quotaid = (Integer)request.getAttribute("quotaid");

Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
				
				%>

<div class="survey-info" id="survey-quota-new" sid="survey-quota-new" quota="survey-quota-new" style="display:none;border-bottom: 1px solid #ccc;">
		  							<ul class="survey-sections" id="survey-sections" style="overflow: hidden;">
			  						<li style="display:block;">
	
			  						<div class="widthTitleSurveyCollapsed" id="survey-div-title-fees" style="width: 90%;" qid="" sid="<%=survey.getSurveyId()%>">
			  							<div class="form-group" style="margin:0px;">
			  								<div class="form-group">
								                <div class="col-md-8">     
								                	<div class="form-group">  
								                		<h5 id="questionquotaname<%=index%>" style="text-align: left;font-size: 18px;"></h5>                 
								                    	<select id="selquestionforfees-new" name="selquestionforfees-new" class="selquestionforfees form-control" onchange="changeoptionsfees();" style="display:none;">
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
							  									
														</div>
				                    				
				                    				</div>
										
				  					</div>
				  					<div class="panel-section-buttons right" style="margin-top:10px;">
										<button class="btn-transparent btn-remove" id="removeQuota" aria-label="Remove question: question typo formField" style="color: #B60000 !important;"><i class="fa fa-trash fa-2x"></i></button>
									</div>					
									</li>
								</ul>
		  					</div>	