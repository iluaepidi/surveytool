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

<div id="survey-quota-new">
		  						<li class="quota-item hidden" id="survey-quota-" sid="survey-quota-new" quota="survey-quota-new">
			  						<div class="widthTitleSurveyCollapsed with90percent" id="survey-div-title-fees" qid="" sid="<%=survey.getSurveyId()%>">
			  							<div class="form-group nomargin">
			  								<div class="form-group">
								                <div class="col-md-8">     
								                	<div class="form-group">  
								                		<h5 id="questionquotaname<%=index%>" class="quotaname"></h5>                 
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
					                    			
				                    					<div class="form-group" class="quoteoption" id="optionquota">
							  									
														</div>
				                    				
				                    				</div>
										
				  					</div>
				  					<div class="panel-section-buttons remove-quota right" class="margintop10">
										<button class="btn-transparent btn-remove quotaremovebtn" id="removeQuota" aria-label="Remove question: question typo formField"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
									</div>					
									</li>
		  					</div>	