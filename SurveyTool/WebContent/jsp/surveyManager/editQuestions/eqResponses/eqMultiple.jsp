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
										
							  				if(question.getOptionsGroups().size() > 0)
							  				{
							  					for(OptionsGroup optionsGroup : question.getOptionsGroups())
							  					{
							  					%>						  						
							  						<ul class="option-list" id="option-list" ogid="<%= optionsGroup.getId() %>" otype="checkbox">
							  						<%
							  						
							  						for(Option option : optionsGroup.getOptions())
							  						{
							  							int index = option.getIndex();
							  							String text = option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
							  						%>
							  							<li class="option-item" id="option-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft"><%= index %></div>
						  									<input type="text" class="option-title form-control fleft" index="<%= index %>" oid="<%= option.getId() %>" placeholder="<%= lang.getContent("question.edit.placeholder.option") %> <%= index %>" value="<%= text %>" aria-label="<%= lang.getContent("question.edit.aria_label.option") %> <%= index %>"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-option" aria-label="<%= lang.getContent("button.remove_option") %>: <%= text %>"><i class="fa fa-trash fa-2x"></i></button>
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
							  							<li class="option-item" id="option-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft"><%= index %></div>
						  									<input type="text" class="option-title form-control fleft" index="<%= index %>" oid="0" placeholder="<%= lang.getContent("question.edit.placeholder.option") %> <%= index %>" aria-label="<%= lang.getContent("question.edit.placeholder.option") %> <%= index %>"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-option" aria-label="<%= lang.getContent("button.remove_option") %> <%= index %>"><i class="fa fa-trash fa-2x"></i></button>
							  								</div>
							  							</li>
							  							
							  						<%
							  							}
							  						}
							  						%>
							  							<li class="center" id="li-add-option<%= question.getQuestionId() %>">
							  								<button class="btn btn-primary btn-sm active" id="btn-add-option"><i class="fa fa-plus-square"></i><span><%= lang.getContent("button.add_option") %></span></button>
							  							</li>
							  						</ul>
							  					<%
							  					}
							  				}
							  				else
							  				{
							  					%>
							  											  						
							  						<ul class="option-list" id="option-list" ogid="0" otype="checkbox">
							  						
							  							<li class="option-item" id="option-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft">1</div>
						  									<input type="text" class="option-title form-control fleft" index="1" oid="0" placeholder="<%= lang.getContent("question.edit.placeholder.option") %> 1" aria-label="<%= lang.getContent("question.edit.aria_label.option") %> 1"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-option" aria-label="r<%= lang.getContent("button.remove_option") %> 1"><i class="fa fa-trash fa-2x"></i></button>
							  								</div>
							  							</li>
							  							
							  							<li class="option-item" id="option-item">
						  									<!-- <button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> -->
						  									<div class="circle-info circle-grey fleft">2</div>
						  									<input type="text" class="option-title form-control fleft" index="2" oid="0" placeholder="<%= lang.getContent("question.edit.placeholder.option") %> 2" aria-label="<%= lang.getContent("question.edit.aria_label.option") %> 2"/>
						  									<div class="option-icons fleft">
							  									<!-- <button class="btn btn-transparent fleft" data-toggle="modal" data-target="#importFile"><i class="fa fa-file-image-o fa-2x"></i></button>
							  									<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> -->
							  									<button class="btn btn-transparent fleft red" id="remove-option" aria-label="<%= lang.getContent("button.remove_option") %> 2"><i class="fa fa-trash fa-2x"></i></button>
							  								</div>
							  							</li>
							  							
							  							<li class="center" id="li-add-option<%= question.getQuestionId() %>">
							  								<button class="btn btn-primary btn-sm active" id="btn-add-option" ><i class="fa fa-plus-square"></i><span><%= lang.getContent("button.add_option") %></span></button>
							  							</li>
							  						</ul>
							  					<%
							  				}
							  				
							  			lang.close();							  				
							  			%>