				<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
				<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
				<%
				Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO);
				%>
				
				<div class="container-fluid">
	  				<div class="title-content-no-underline">
	  					<h2>User Panel > Surveys > Survey title</h2>
	  					<ul class="nav nav-tabs nav-tabs-right nav-tab-edit">						  	
						  	<li role="presentation" class="statistic-tab"><a href="#" title="go to survey statistics"><i class="fa fa-bar-chart fa-2x"></i></a></li>
						  	<li role="presentation" class="share-tab"><a href="#" title="go to share survey"><i class="fa fa-share-alt fa-2x"></i></a></li>
						  	<li role="presentation" class="edit-tab active"><a href="#" title="go to edit survey"><i class="fa fa-pencil-square-o fa-2x"></i></a></li>
						</ul>
	  				</div>
	  				<div class="content-box-tabs edit-content">
	  					<div class="browser-left">Hello</div>	  						  		
	  					<div class="edit-content-center">
		  					<div class="edit-survey-frame survey-info" id="survey-info">
		  						<div class="sect-arrow">
		  							<i class="fa fa-caret-right fa-2x"></i>
		  						</div>
		  						 
		  						<input type="text" class="survey-info-title" id="survey-info-title" value="<%= survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %>" />
		  						
		  						<div class="survey-info-project">
									<label for="surveyTitle" class="col-sm-4 control-label"> for project </label>
							   		<div class="col-sm-8">
							   			<input type="text" class="form-control" name="project" value="<%= survey.getProject() %>" />
							   		</div>
								</div>
								
								<div class="survey-info-description">
									<label for="surveyDescription" class="col-sm-3 control-label left">Short description</label>
							   		<div class="col-sm-9">
							     			<textarea class="form-control" id="surveyDescription" rows="2"><%= survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText() %></textarea>
							   		</div>
								</div>
		  					</div>	
		  					
		  					<div class="survey-sections" id="survey-sections">
		  					
			  					<div class="add-frame">
		  							<a href="#" class="btn-add" title="create new section"><i class="fa fa-plus-circle fa-2x"></i></a>  							
		  						</div>
			  					
			  					<div class="panel-section" id="panel-section1">
									<div class="panel-heading">	
										<div class="col-sm-1 left"><a id="display-section-panel" title="diplay section 1"><i class="fa fa-caret-down fa-2x"></i></a></div>				
										<h3 class="col-sm-10 panel-title"><input type="text" class="survey-section-title-unselected center" id="survey-section-title" value="Section 1:" /></h3>
										<div class="col-sm-1 panel-section-delete right"><a href="#" title="remove section 1"><i class="fa fa-trash fa-2x"></i></a></div>
									</div>
									
									<div class="panel-body">									
					  					<div class="add-menu-frame" id="add-menu-frame1">
					  						<div class="col-sm-5 add-vacio"></div>
				  							<a class="btn-add col-sm-2" title="create new section"><i class="fa fa-plus-circle fa-2x"></i></a>
				  							<div class="col-sm-5 add-menu">
				  								<div class="col-sm-1">...</div>
				  								<div class="btn-group col-sm-11" role="group" aria-label="...">
													<button type="button" class="btn btn-add-menu" id="btn-question" data-toggle="modal" data-target="#newQuestionModal">Question</button>
													<button type="button" class="btn btn-add-menu" id="btn-body-content">Body content</button>
												</div>
				  							</div>  							
				  						</div>		
				  							
										
											  					
									</div>																							
								</div>			
														
			  					<div class="add-frame">
		  							<a href="#" class="btn-add" title="create new section"><i class="fa fa-plus-circle fa-2x"></i></a>  							
		  						</div>
		  						
		  					</div>	
						</div>
	  				</div>
	  			</div>
	  			
	  			<jsp:include page="../frames/fNewQuestion.jsp" />