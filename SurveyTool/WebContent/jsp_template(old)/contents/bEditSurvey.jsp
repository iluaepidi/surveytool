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
				  						
				  						<div class="panel-question" id="panel-question1">
											<div class="panel-heading">	
												<div class="col-sm-1 left"><a id="display-question-panel" title="diplay section 1"><i class="fa fa-caret-down fa-2x"></i></a></div>				
												<h3 class="col-sm-10 panel-title"><input type="text" class="survey-section-title-unselected" id="survey-question-title" value="What is your favourite film?" /></h3>
												<div class="col-sm-1 panel-section-delete right"><a href="#" title="remove section 1"><i class="fa fa-trash fa-2x"></i></a></div>
												
											</div>
											
											<div class="panel-body">									
							  					<div class="panel-question-basic-setting row">
							  						<div class="alt-question col-md-5">
														<label for="main-version" class="col-md-5" ><i class="fa fa-language fa-2x"></i><span>Alternatives</span></label>
														<select class="form-control-small col-md-6" id="main-version">
														  	<option value="en" selected>Main (English)</option>
														    <option value="es">Spanish</option>
														    <option value="fr">French</option>
														    <option value="el">Greek</option>
														  </select>
													</div>
													<div class="col-md-2">
														<button class="btn mandatory-question" selected="false"><i class="fa fa-asterisk red"></i><span>Mandatory</span></button>													
													</div>
													<div class="col-md-2">
														<button class="btn" selected="false"><i class="fa fa-question-circle fa-2x"></i><span>Help text</span></button>
													</div>
							  					</div>
							  					
							  					<div class="question-frame">
							  						<p>Instructions /Help text for selectiong option</p>
							  					</div>	
							  					
							  					<div class="question-frame">
							  						<h4>Options</h4>
							  						<div class="row">
							  							<div class="col-md-4">
								  							<label for="type-question">Type</label>
								  							<select class="form-control" id="type-question">
															  	<option value="ls" selected>Likert scale</option>
															    <option value="sim">Simple</option>
															    <option value="mul">Multiple</option>
																<option value="ma">Matrix</option>
															</select>
														</div>
														<div class="col-md-8 center">
															 <div class="likert-options">
															 	<div class="likert-options-frame">
															 		<div>
															 			<label for="likert1">1</label>
															 			<input type="radio" name="likert" id="likert1"/>
															 		</div>
															 		<div>
															 			<label for="likert2">2</label>
															 			<input type="radio" name="likert" id="likert2"/>
															 		</div>
															 		<div>
															 			<label for="likert3">3</label>
															 			<input type="radio" name="likert" id="likert3"/>
															 		</div>
															 		<div>
															 			<label for="likert4">4</label>
															 			<input type="radio" name="likert" id="likert4"/>
															 		</div>
															 		<div>
															 			<label for="likert5">5</label>
															 			<input type="radio" name="likert" id="likert5"/>
															 		</div>
															 		<div>
															 			<label for="likert6">6</label>
															 			<input type="radio" name="likert" id="likert6"/>
															 		</div>
															 		<div>
															 			<label for="likert7">7</label>
															 			<input type="radio" name="likert" id="likert7"/>
															 		</div>
															 	</div>
															 </div>	
															 <div class="likert-legend">
															 	<div>Totally disgree</div>
															 	<div>Indiferent</div>
															 	<div>Totally agree</div>
															 </div>
														</div>													 
							  						</div>						  						
							  					</div>
							  					
							  					<div class="question-frame">
							  						<h4>Response settings</h4>
							  						
							  						<ul class="option-list">
							  							<li class="option-item">
						  									<button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button>
						  									<div class="circle-info circle-grey fleft">1</div>
						  									<input type="text" class="option-title form-control fleft" />
						  									<div class="option-icons fleft">
							  									<a class="btn btn-transparent fleft"><i class="fa fa-file-image-o fa-2x"></i></a>
							  									<a class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></a>
							  									<a class="btn btn-transparent fleft red"><i class="fa fa-trash fa-2x"></i></a>
							  								</div>
							  							</li>
							  							<li class="option-item">
						  									<button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button>
						  									<div class="circle-info circle-grey fleft">2</div>
						  									<input type="text" class="option-title form-control fleft" />
						  									<div class="option-icons fleft">
							  									<a class="btn btn-transparent fleft"><i class="fa fa-file-image-o fa-2x"></i></a>
							  									<a class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></a>
							  									<a class="btn btn-transparent fleft red"><i class="fa fa-trash fa-2x"></i></a>
							  								</div>
							  							</li>
							  							<li class="center">
							  								<a class="btn-plus-small"><i class="fa fa-plus-square"></i></a>
							  							</li>
							  						</ul>
							  					</div>
							  					
							  					<div class="question-frame">
							  						<h4>Routes</h4>
							  						<div class="routes-select">
								  						<label for="dependencies">Dependencies</label>
							  							<select class="form-control-small" id="dependencies">
														  	<option value="q25" selected>To Q25 - Sports</option>
														    <option value="q21">To Q21 - TV</option>
														    <option value="q23">To Q23 - Internet</option>
														</select>
														<i class="fa fa-cogs fa-2x"></i>
													</div>
							  					</div>
							  							  					
											</div>																							
										</div>	
										
										<div class="add-menu-frame" id="add-menu-frame2">
											<div class="col-sm-5 add-vacio"></div>
				  							<a class="btn-add col-sm-12" title="create new section"><i class="fa fa-plus-circle fa-2x"></i></a>
				  							<div class="col-sm-5 add-menu">
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
	  			</div>
	  			
	  			<jsp:include page="../frames/fNewQuestion.jsp" />