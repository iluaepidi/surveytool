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
											<div class="row">	
											<div class="col-md-4">
												<label for="type-matrix"><%=lang.getContent("question.form.responsesettings") %></label>
								  				<select class="form-control" id="type-matrix">
													<option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_SIMPLE%>" <%if(matrixType.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_SIMPLE)){%> selected <%}%>><%=lang.getContent("question.edit.matrix.type.simple")%></option>
													<option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_MULTIPLE%>" <%if(matrixType.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_MULTIPLE)){%> selected <%}%>><%=lang.getContent("question.edit.matrix.type.multiple")%></option>
													<!-- <option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_PULLDOWN%>" <%if(matrixType.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_PULLDOWN)){%> selected <%}%>><%=lang.getContent("question.edit.matrix.type.pulldown")%></option>
													<option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_FREE%>" <%if(matrixType.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_FREE)){%> selected <%}%>><%=lang.getContent("question.edit.matrix.type.free")%></option> -->
												</select>
												</div>
							  				</div>
							  				<hr>			
											<div class="row">
												<label for="matrix-row-<%= question.getIndex() %>"><%=lang.getContent("question.matrix.rowoption") %></label>
							  				<%
							  				if(question.getOptionsGroups().size() > 0)
							  				{
							  					%>						  						
						  						<ul class="option-list" id="optionsgroupmatrix-list" otype="<%= otype %>">
						  						<%
							  					for(OptionsGroup optionsGroup : question.getOptionsGroups())
							  					{
							  						int index = optionsGroup.getIndex();
							  						String text = optionsGroup.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
							  						%>
							  							<li class="option-item" id="optionsgroupmatrix-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft"><%= index %></div>
						  									<input type="text" class="option-title form-control fleft" index="<%= index %>" ogid="<%= optionsGroup.getId() %>" placeholder="<%= lang.getContent("question.edit.placeholder.option") %> <%= index %>" value="<%= text %>" aria-label="<%= lang.getContent("question.edit.aria_label.option") %> <%= index %>"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-optionsgroupmatrix" aria-label="<%= lang.getContent("button.remove_option") %>: <%= text %>"><i class="fa fa-trash fa-2x"></i></button>
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
					  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
					  									<div class="circle-info circle-grey fleft"><%= index %></div>
					  									<input type="text" class="option-title form-control fleft" index="<%= index %>" ogid="0" placeholder="<%= lang.getContent("question.edit.placeholder.option") %> <%= index %>" aria-label="<%= lang.getContent("question.edit.placeholder.option") %> <%= index %>"/>
					  									<div class="option-icons fleft">
						  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
						  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
						  									<button class="btn btn-transparent fleft red" id="remove-optionsgroupmatrix" aria-label="<%= lang.getContent("button.remove_option") %> <%= index %>"><i class="fa fa-trash fa-2x"></i></button>
						  								</div>
						  							</li>
						  							
						  						<%
						  							}
						  						}
						  						%>
					  							<li class="center" id="li-add-optionsgroupmatrix<%= question.getQuestionId() %>">
					  								<button class="btn btn-primary btn-sm active" id="btn-add-optionsgroupmatrix"><i class="fa fa-plus-square"></i><span><%= lang.getContent("button.add_row") %></span></button>
					  							</li>
					  						</ul>
					  					<%
							  				}
							  				else
							  				{
							  					%>
							  											  						
							  						<ul class="option-list" id="optionsgroupmatrix-list" otype=<%= otype %>>
							  						
							  							<li class="option-item" id="optionsgroupmatrix-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft">1</div>
						  									<input type="text" class="option-title form-control fleft" index="1" ogid="0" placeholder="<%= lang.getContent("question.edit.placeholder.option") %> 1" aria-label="<%= lang.getContent("question.edit.aria_label.option") %> 1"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-optionsgroupmatrix" aria-label="r<%= lang.getContent("button.remove_option") %> 1"><i class="fa fa-trash fa-2x"></i></button>
							  								</div>
							  							</li>
							  							
							  							<li class="option-item" id="optionsgroupmatrix-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft">2</div>
						  									<input type="text" class="option-title form-control fleft" index="2" ogid="0" placeholder="<%= lang.getContent("question.edit.placeholder.option") %> 2" aria-label="<%= lang.getContent("question.edit.aria_label.option") %> 2"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-optionsgroupmatrix" aria-label="<%= lang.getContent("button.remove_option") %> 2"><i class="fa fa-trash fa-2x"></i></button>
							  								</div>
							  							</li>
							  							
							  							<li class="center" id="li-add-optionsgroupmatrix<%= question.getQuestionId() %>">
							  								<button class="btn btn-primary btn-sm active" id="btn-add-optionsgroupmatrix" ><i class="fa fa-plus-square"></i><span><%= lang.getContent("button.add_row") %></span></button>
							  							</li>
							  						</ul>
							  					<%
							  				}%>
							  				</div>
							  				
							  				<hr>
							  				<div class="row">
												<label  for="matrix-column-<%= question.getIndex() %>"><%=lang.getContent("question.matrix.columnoption") %></label>
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
							  							String text = option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
							  						%>
							  							<li class="option-item" id="optionmatrix-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft"><%= index %></div>
						  									<input type="text" class="option-title form-control fleft" index="<%= index %>" oid="<%= option.getId() %>" placeholder="<%= lang.getContent("question.edit.placeholder.option") %> <%= index %>" value="<%= text %>" aria-label="<%= lang.getContent("question.edit.aria_label.option") %> <%= index %>"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-optionmatrix" aria-label="<%= lang.getContent("button.remove_option") %>: <%= text %>"><i class="fa fa-trash fa-2x"></i></button>
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
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft"><%= index %></div>
						  									<input type="text" class="option-title form-control fleft" index="<%= index %>" oid="0" placeholder="<%= lang.getContent("question.edit.placeholder.option") %> <%= index %>" aria-label="<%= lang.getContent("question.edit.placeholder.option") %> <%= index %>"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-optionmatrix" aria-label="<%= lang.getContent("button.remove_option") %> <%= index %>"><i class="fa fa-trash fa-2x"></i></button>
							  								</div>
							  							</li>
							  							
							  						<%
							  							}
							  						}
							  						%>
							  							<li class="center" id="li-add-optionmatrix<%= question.getQuestionId() %>">
							  								<button class="btn btn-primary btn-sm active" id="btn-add-optionmatrix"><i class="fa fa-plus-square"></i><span><%= lang.getContent("button.add_column") %></span></button>
							  							</li>
							  						</ul>
							  					<%
							  				}
							  				else
							  				{
							  					%>
							  											  						
							  						<ul class="option-list" id="optionmatrix-list" otype=<%= otype %>>
							  						
							  							<li class="option-item" id="optionmatrix-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft">1</div>
						  									<input type="text" class="option-title form-control fleft" index="1" oid="0" placeholder="<%= lang.getContent("question.edit.placeholder.option") %> 1" aria-label="<%= lang.getContent("question.edit.aria_label.option") %> 1"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-optionmatrix" aria-label="r<%= lang.getContent("button.remove_option") %> 1"><i class="fa fa-trash fa-2x"></i></button>
							  								</div>
							  							</li>
							  							
							  							<li class="option-item" id="optionmatrix-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft">2</div>
						  									<input type="text" class="option-title form-control fleft" index="2" oid="0" placeholder="<%= lang.getContent("question.edit.placeholder.option") %> 2" aria-label="<%= lang.getContent("question.edit.aria_label.option") %> 2"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-optionmatrix" aria-label="<%= lang.getContent("button.remove_option") %> 2"><i class="fa fa-trash fa-2x"></i></button>
							  								</div>
							  							</li>
							  							
							  							<li class="center" id="li-add-optionmatrix<%= question.getQuestionId() %>">
							  								<button class="btn btn-primary btn-sm active" id="btn-add-optionmatrix" ><i class="fa fa-plus-square"></i><span><%= lang.getContent("button.add_column") %></span></button>
							  							</li>
							  						</ul>
							  					<%
							  				}%>
							  				</div>
							  				
							  				
							  			<%lang.close();							  				
							  			%>