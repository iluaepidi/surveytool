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
	  					
	  					<div class="edit-survey-frame survey-info" id="survey-info">
	  						<div class="sect-arrow">
	  							<i class="fa fa-caret-right fa-2x"></i>
	  						</div>
	  						 
	  						<input type="text" class="survey-info-title" id="survey-info-title" value="Title survey" />
	  						
	  						<div class="survey-info-project">
								<label for="surveyTitle" class="col-sm-4 control-label"> for project </label>
						   		<div class="col-sm-8">
						   			<select class="form-control" id="sel1">
										<option value="" disabled selected>Select a related project (if any)</option>
									  	<option>Apsis4ALL</option>
									  	<option>Cloud4ALL</option>
									  	<option>Inredis</option>
									</select>
						   		</div>
							</div>
							
							<div class="survey-info-description">
								<label for="surveyDescription" class="col-sm-3 control-label left">Short description</label>
						   		<div class="col-sm-9">
						     			<textarea class="form-control" id="surveyDescription" rows="2" placeholder="Type here_"></textarea>
						   		</div>
							</div>
	  					</div>	
	  					
	  					<div class="survey-sections" id="survey-sections">
	  					
		  					<div class="add-frame">
	  							<a href="#" class="btn-add" title="create new section"><i class="fa fa-plus-circle fa-2x"></i></a>  							
	  						</div>
		  					
		  					<div class="panel-section" id="panel-section1">
								<div class="panel-heading">	
									<div class="col-sm-1 left"><a href="#" title="diplay section 1"><i class="fa fa-caret-down fa-2x"></i></a></div>				
									<h3 class="col-sm-10 panel-title"><input type="text" class="survey-section-title-unselected" id="survey-section-title" value="Section 1:" /></h3>
									<div class="col-sm-1 panel-section-delete right"><a href="#" title="remove section 1"><i class="fa fa-trash fa-2x"></i></a></div>
								</div>
								
								<div class="panel-body">									
				  					<div class="add-menu-frame" id="add-menu-frame1">
			  							<div class="col-sm-12"><a class="btn-add" title="create new section"><i class="fa fa-plus-circle fa-2x"></i></a></div>
			  							<div class="col-sm-11 add-menu">
			  								<div class="col-sm-1">...</div>
			  								<div class="btn-group col-sm-11" role="group" aria-label="...">
												<button type="button" class="btn btn-add-menu" id="btn-question" data-toggle="modal" data-target="#newQuestionModal">Question</button>
												<button type="button" class="btn btn-add-menu" id="btn-body-content">Body content</button>
											  	<button type="button" class="btn btn-add-menu" id="btn-page-break">page-break</button>
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
	  			
	  			<jsp:include page="../frames/fNewQuestion.jsp" />