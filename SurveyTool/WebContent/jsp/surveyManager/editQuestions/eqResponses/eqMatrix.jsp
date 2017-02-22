											<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
											<%@page import="ilu.surveytool.databasemanager.DataObject.Option"%>
											<%@page import="ilu.surveytool.databasemanager.DataObject.OptionsGroup"%>
											<%@page import="ilu.surveytool.constants.Attribute"%>
											<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
											<%@page import="ilu.surveytool.language.Language"%>
											<%
											Language lang = new Language(getServletContext().getRealPath("/")); 
											lang.loadLanguage(Language.getLanguageRequest(request));
 
											Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
											
											String otype = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE);
											
											if (otype.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_SIMPLE))
												otype="radio";
											else if (otype.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_MULTIPLE))
												otype="checkbox";
											else
												otype="radio";
										
											String matrixType = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE);
														
											if (matrixType.equals(""))
												matrixType=DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_SIMPLE;
							  							
							  				%>
							  				<script>
							  				accesibilityTextItem = "<%= lang.getContent("accesibility.question.matrix.input.row") %>";
							  				accesibilityTextColumn = "<%= lang.getContent("accesibility.question.matrix.input.column") %>";
							  				phItem = "<%= lang.getContent("question.edit.matrix.placeholder.item") %>";
							  				phColumn = "<%= lang.getContent("question.edit.matrix.placeholder.column") %>";
							  				</script>
							  				
							  				<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
											<div class="row">	
												<div class="col-md-4">
													<label><%=lang.getContent("question.form.responsesettings") %></label>
													<label for="type-matrix<%= question.getQuestionId() %>" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.type") %></label>
													<select class="form-control type-matrix" id="type-matrix<%= question.getQuestionId() %>">
														<option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_SIMPLE%>" <%if(matrixType.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_SIMPLE)){%> selected <%}%>><%=lang.getContent("question.edit.matrix.type.simple")%></option>
														<option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_MULTIPLE%>" <%if(matrixType.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_MULTIPLE)){%> selected <%}%>><%=lang.getContent("question.edit.matrix.type.multiple")%></option>
														<!-- <option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_PULLDOWN%>" <%if(matrixType.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_PULLDOWN)){%> selected <%}%>><%=lang.getContent("question.edit.matrix.type.pulldown")%></option>
														<option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_FREE%>" <%if(matrixType.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_FREE)){%> selected <%}%>><%=lang.getContent("question.edit.matrix.type.free")%></option> -->
													</select>
												</div>
							  				</div>
							  				
							  				<hr>			
											<%} %>
											<div class="row" type="global">
											  <fieldset>	
												<legend><%=lang.getContent("question.matrix.rowoption") %></legend>
							  					<%
							  					if(question.getOptionsGroups().size() > 1 || (question.getOptionsGroups().size() == 1 && !question.getOptionsGroups().get(0).getContents().isEmpty()))
							  					{
							  					%>						  						
						  							<ul class="option-list" id="optionsgroupmatrix-list" otype="<%= otype %>">
						  							<%
							  						for(OptionsGroup optionsGroup : question.getOptionsGroups())
							  						{
							  							int index = optionsGroup.getIndex();
							  							String text = "";
							  							if(optionsGroup.getContents() != null && !optionsGroup.getContents().isEmpty() && optionsGroup.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE) != null) text = optionsGroup.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
							  							%>
							  							<li class="option-item" id="optionsgroupmatrix-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x" aria-hidden="true"></i></button> -->
						  									<div class="circle-info circle-grey fleft"><%= index %></div>
						  										<label for="inputRow<%= question.getQuestionId() %>-<%= index %>" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.input.row") %> <%= index %></label>
																<input type="text" id="inputRow<%= question.getQuestionId() %>-<%= index %>" class="option-title form-control fleft" index="<%= index %>" ogid="<%= optionsGroup.getId() %>" placeholder="<%= lang.getContent("question.edit.matrix.placeholder.item") %> <%= index %>" value="<%= text %>" />
						  										<div class="option-icons fleft">
							  										<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x" aria-hidden="true"></i></button>
							  										<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x" aria-hidden="true"></i></button> -->
							  										<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
							  										<label for="remove-optionsgroupmatrix<%= question.getQuestionId() %>-<%= index %>" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.remove.item")%> <%= index %></label>
																	<button class="btn btn-transparent fleft red remove-optionsgroupmatrix" id="remove-optionsgroupmatrix<%= question.getQuestionId() %>-<%= index %>"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
							  										<% } %>
							  									</div>
							  								</li>
							  						<%							  						
							  						
							  						}
						  						
						  							int size = question.getOptionsGroups().size();
						  							if(size < 2)
						  							{
						  								for(int i = size; i < 2; i++)
						  								{
						  									int index = i + 1;
						  							%>
						  									<li class="option-item" id="optionsgroupmatrix-item">
					  											<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x" aria-hidden="true"></i></button> -->
					  											<div class="circle-info circle-grey fleft"><%= index %></div>
					  											<label for="inputRow<%= question.getQuestionId() %>-<%= index %>" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.input.row") %> <%= index %></label>
																<input type="text" id="inputRow<%= question.getQuestionId() %>-<%= index %>" class="option-title form-control fleft" index="<%= index %>" ogid="0" placeholder="<%= lang.getContent("question.edit.matrix.placeholder.item") %> <%= index %>"/>
					  											<div class="option-icons fleft">
						  											<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x" aria-hidden="true"></i></button>
						  											<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x" aria-hidden="true"></i></button> -->
						  											<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
						  											<label for="remove-optionsgroupmatrix<%= question.getQuestionId() %>-<%= index %>" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.remove.item") %> <%= index %></label>
																	<button class="btn btn-transparent fleft red remove-optionsgroupmatrix" id="remove-optionsgroupmatrix<%= question.getQuestionId() %>-<%= index %>"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
						  											<% } %>
						  										</div>
						  									</li>
						  							
						  							<%
						  								}
						  							}
						  							%>
					  								<li class="center" id="li-add-optionsgroupmatrix<%= question.getQuestionId() %>">
					  									<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
					  									<label for="btn-add-optionsgroupmatrix" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.add.item") %></label>
														<button class="btn btn-primary btn-sm active" id="btn-add-optionsgroupmatrix"><i class="fa fa-plus-square" aria-hidden="true"></i><span><%= lang.getContent("button.add_row") %></span></button>
					  									<%} %>
					  								</li>
					  							</ul>
					  					<%
							  				}
							  				else
							  				{
							  					%>
							  											  						
							  						<ul class="option-list" id="optionsgroupmatrix-list" otype=<%= otype %>>
							  						
							  							<li class="option-item" id="optionsgroupmatrix-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x" aria-hidden="true"></i></button> -->
						  									<div class="circle-info circle-grey fleft">1</div>
						  									<label for="inputRow<%= question.getQuestionId() %>-1" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.input.row") + "1"%> </label>
															<input type="text" id="inputRow<%= question.getQuestionId() %>-1" class="option-title form-control fleft" index="1" ogid="0" placeholder="<%= lang.getContent("question.edit.matrix.placeholder.item") %> 1"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x" aria-hidden="true"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x" aria-hidden="true"></i></button> -->
							  									<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
							  									<label for="remove-optionsgroupmatrix<%= question.getQuestionId() %>-1" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.remove.item") +" 1"%></label>
																<button class="btn btn-transparent fleft red remove-optionsgroupmatrix" id="remove-optionsgroupmatrix<%= question.getQuestionId() %>-1"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
							  									<%} %>
							  								</div>
							  							</li>
							  							
							  							<li class="option-item" id="optionsgroupmatrix-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x" aria-hidden="true"></i></button> -->
						  									<div class="circle-info circle-grey fleft">2</div>
						  									<label for="inputRow<%= question.getQuestionId() %>-2" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.input.row") + "2"%></label>
															<input type="text" id="inputRow<%= question.getQuestionId() %>-2" class="option-title form-control fleft" index="2" ogid="0" placeholder="<%= lang.getContent("question.edit.matrix.placeholder.item") %> 2"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x" aria-hidden="true"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x" aria-hidden="true"></i></button> -->
							  									<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
							  									<label for="remove-optionsgroupmatrix<%= question.getQuestionId() %>-2" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.remove.item") +" 2"%></label>
																<button class="btn btn-transparent fleft red remove-optionsgroupmatrix" id="remove-optionsgroupmatrix<%= question.getQuestionId() %>-2"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
							  									<%} %>
							  								</div>
							  							</li>
							  							
							  							<li class="center" id="li-add-optionsgroupmatrix<%= question.getQuestionId() %>">
							  								<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
							  								<label for="btn-add-optionsgroupmatrix" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.add.item") %></label>
															<button class="btn btn-primary btn-sm active" id="btn-add-optionsgroupmatrix" ><i class="fa fa-plus-square" aria-hidden="true"></i><span><%= lang.getContent("button.add_row") %></span></button>
							  								<%} %>
							  							</li>
							  						</ul>
							  					<%
							  				}%>
							  				   </fieldset>
							  				</div>
							  				
							  				<hr>
							  				<div class="row" type="global">
							  				  <fieldset>
												<legend><%=lang.getContent("question.matrix.columnoption") %></legend>
							  				<%
							  				if(question.getOptionsGroups().size() > 0)
							  				{
							  					OptionsGroup optionsGroup = question.getOptionsGroups().get(0);
							  					
							  					%>						  						
							  						<ul class="option-list" id="optionmatrix-list" otype=<%= otype %>>
							  						<%
							  						for(Option option : optionsGroup.getOptions())
							  						{
							  							int index = option.getIndex();
							  							String text = "";
							  							if(option!=null && option.getContents()!=null && option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null){
							  								text = option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
							  							}
							  						%>
							  							<li class="option-item" id="optionmatrix-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x" aria-hidden="true"></i></button> -->
						  									<div class="circle-info circle-grey fleft"><%= index %></div>
						  									<label for="inputCol<%= question.getQuestionId() %>-<%= index %>" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.input.column") %> <%= index %></label>
															<input type="text" id="inputCol<%= question.getQuestionId() %>-<%= index %>" class="option-title form-control fleft" index="<%= index %>" oid="<%= option.getId() %>" placeholder="<%= lang.getContent("question.edit.matrix.placeholder.column") %> <%= index %>" value="<%= text %>"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x" aria-hidden="true"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x" aria-hidden="true"></i></button> -->
							  									<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
							  									<label for="remove-optionmatrix<%= question.getQuestionId() %>-<%= index %>" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.remove.column")%> <%= index %></label>
																<button class="btn btn-transparent fleft red remove-optionmatrix" id="remove-optionmatrix<%= question.getQuestionId() %>-<%= index %>"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
							  									<%} %>
							  								</div>
							  							</li>
							  						<%
							  						}
							  						
							  						int size = optionsGroup.getOptions().size();
							  						if(size < 2)
							  						{
							  							for(int i = size; i < 2; i++)
							  							{
							  								int index = i + 1;
							  						%>
							  							<li class="option-item" id="optionmatrix-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x" aria-hidden="true"></i></button> -->
						  									<div class="circle-info circle-grey fleft"><%= index %></div>
						  									<label for="inputCol<%= question.getQuestionId() %>-<%= index %>" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.input.column") %> <%= index %></label>
															<input type="text" id="inputCol<%= question.getQuestionId() %>-<%= index %>" class="option-title form-control fleft" index="<%= index %>" oid="0" placeholder="<%= lang.getContent("question.edit.matrix.placeholder.column") %> <%= index %>"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x" aria-hidden="true"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x" aria-hidden="true"></i></button> -->
							  									<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
							  									<label for="remove-optionmatrix<%= question.getQuestionId() %>-<%= index %>" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.remove.column") %> <%= index %></label>
																<button class="btn btn-transparent fleft red remove-optionmatrix" id="remove-optionmatrix<%= question.getQuestionId() %>-<%= index %>"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
							  									<%} %>
							  								</div>
							  							</li>
							  							
							  						<%
							  							}
							  						}
							  						%>
							  							<li class="center" id="li-add-optionmatrix<%= question.getQuestionId() %>">
							  								<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
							  								<label for="btn-add-optionmatrix" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.add.column") %></label>
															<button class="btn btn-primary btn-sm active" id="btn-add-optionmatrix"><i class="fa fa-plus-square" aria-hidden="true"></i><span><%= lang.getContent("button.add_column") %></span></button>
							  								<%} %>
							  							</li>
							  						</ul>
							  					<%
							  				}
							  				else
							  				{
							  					%>
							  											  						
							  						<ul class="option-list" id="optionmatrix-list" otype=<%= otype %>>
							  						
							  							<li class="option-item" id="optionmatrix-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x" aria-hidden="true"></i></button> -->
						  									<div class="circle-info circle-grey fleft">1</div>
						  									<label for="inputCol<%= question.getQuestionId() %>-1" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.input.column")+" 1" %></label>
															<input type="text" id="inputCol<%= question.getQuestionId() %>-1" class="option-title form-control fleft" index="1" oid="0" placeholder="<%= lang.getContent("question.edit.matrix.placeholder.column") %> 1"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x" aria-hidden="true"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x" aria-hidden="true"></i></button> -->
							  									<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
							  									<label for="remove-optionmatrix<%= question.getQuestionId() %>-1" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.remove.column")+" 1" %></label>
																<button class="btn btn-transparent fleft red remove-optionmatrix" id="remove-optionmatrix<%= question.getQuestionId() %>-1"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
							  									<%} %>
							  								</div>
							  							</li>
							  							
							  							<li class="option-item" id="optionmatrix-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x" aria-hidden="true"></i></button> -->
						  									<div class="circle-info circle-grey fleft">2</div>
						  									<label for="inputCol<%= question.getQuestionId() %>-2" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.input.column")+" 2" %></label>
															<input type="text" id="inputCol<%= question.getQuestionId() %>-2" class="option-title form-control fleft" index="2" oid="0" placeholder="<%= lang.getContent("question.edit.matrix.placeholder.column") %> 2"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x" aria-hidden="true"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x" aria-hidden="true"></i></button> -->
							  									<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
							  									<label for="remove-optionmatrix<%= question.getQuestionId() %>-2" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.remove.column")+" 2" %></label>
																<button class="btn btn-transparent fleft red remove-optionmatrix" id="remove-optionmatrix<%= question.getQuestionId() %>-2"><i class="fa fa-trash fa-2x" aria-hidden="true"></i></button>
							  									<%} %>
							  								</div>
							  							</li>
							  							
							  							<li class="center" id="li-add-optionmatrix<%= question.getQuestionId() %>">
							  								<% if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ %>
							  								<label for="btn-add-optionmatrix" class="visuallyhidden"><%= lang.getContent("accesibility.question.matrix.add.column") %></label>
															<button class="btn btn-primary btn-sm active" id="btn-add-optionmatrix" ><i class="fa fa-plus-square" aria-hidden="true"></i><span><%= lang.getContent("button.add_column") %></span></button>
							  								<%} %>
							  							</li>
							  						</ul>
							  					<%
							  				}%>
							  				  </fieldset>
							  				</div>
							  				
							  				
							  			<%lang.close();							  				
							  			%>