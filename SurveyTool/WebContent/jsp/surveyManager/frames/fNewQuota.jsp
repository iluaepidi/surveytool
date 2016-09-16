<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%> 
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%
Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO); 

Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
List<Question> listQuestionFees = (ArrayList<Question>) request.getAttribute("listQuestionFees");
String titleQuestion="";
%>	
				<!-- Modal -->
				<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
				<div class="modal fade survey-win" id="newQuotaModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog survey-win-dialog" role="document">
				    	<div class="panel panel-primary"> 
				    		<div class="panel-heading"> 
				        		<button type="button" class="close" data-dismiss="modal" aria-label="<%= lang.getContent("button.close") %> <%= lang.getContent("quota.add.new") %>"><span aria-hidden="true">&times;</span></button>
				    			<h2 class="panel-title"><%= lang.getContent("quota.add.new") %></h2> 
				    		</div> 
				    		<div class="panel-body">
				    			
				    			<div class="survey-frame" id="frame-basic-Settings">
				    				<h3><%= lang.getContent("quota.create.new") %></h3>
				    				
				    				<div class="row">
				    					<form>
				    						<input type="hidden" id="qtypevalue" name="qtype" value="" />
				    						<input type="hidden" id="surveyid" name="surveyid" value="<%= survey.getSurveyId() %>" />
				    						<div class="col-md-7">
				    							<div class="form-group" style="margin:0px;">
				    								<label for="qstatement"><%= lang.getContent("quota.select") %></label>
										     		<select id="selquestionnewquota" name="selquestionnewquota" class="selquestionforfees form-control">
								                    		<% for(Question question : listQuestionFees){
								                    			if(question!=null &&  question.getContents()!=null && question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
			    													titleQuestion = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
					    										}
					    									%>
								                    		<option value="<%=question.getQuestionId() %>"><%=titleQuestion %></option>
								                    		<%} %>
								                    </select>
													<span  id='qstatement-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style='top:30px;right: 20px'></span>
			  										<span id='qstatement-error' class='error hidden' style=''><%= lang.getContent("msg.error.qstatement.text") %></span>	
												</div>
											</div>
											<div class="col-md-5">
												
												<div class="div-btn-create-question center">
													<input type="button" value="<%= lang.getContent("button.create") %>" class="btn btn-primary" id="create-quota"/>
												</div>
											</div>
				    					</form>
				    				</div>				    				
				    			</div>				    			
				    		</div> 
				    	</div>
				  	</div>
				</div>
	  			