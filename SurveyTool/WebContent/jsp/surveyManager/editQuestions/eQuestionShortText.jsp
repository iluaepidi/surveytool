<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
//String title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
			
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
<li class="panel-question" id="panel-question1" qid="<%= question.getQuestionId() %>" index="<%= question.getIndex() %>">
	<jsp:include page="eqComponents/eqHead.jsp" />
	
	<div class="panel-body question-options">
			
			<div class="col-md-1">
				<label class="type-tittle" for="type-question-<%= question.getIndex() %>"><%=lang.getContent("question.edit.type")%></label>								  							
			</div>
				
			<div class="col-md-4">														
				<select class="form-control" id="type-question-<%= question.getIndex() %>">
					<option value="f" selected><%=lang.getContent("question.new.formfield")%></option>
				    <!-- <option value="p"><%=lang.getContent("question.new.paragraph")%></option>
				    <option value="m"><%=lang.getContent("question.new.multiple")%></option>
				    <option value="s"><%=lang.getContent("question.new.simple")%></option>
				    <option value="o"><%=lang.getContent("question.new.ordering")%></option>
				    <option value="g"><%=lang.getContent("question.new.grading")%></option>
				    <option value="ma"><%=lang.getContent("question.new.matrix")%></option>
				    <option value="sc"><%=lang.getContent("question.new.scale")%></option>
				    <option value="c"><%=lang.getContent("question.new.code")%></option> -->
				</select>
			</div>
			
			<div class="right col-md-7">
				<label for="mandatoryButton" class="visuallyhidden"><%= lang.getContent("accesibility.question.mandatory") %></label>														
				<button class="btn btn-question-head btn-sm active mandatory-button" id="mandatoryButton" active="<%= question.isMandatory() %>"><i class="fa fa-asterisk red"></i><span><%= lang.getContent("question.mandatory") %></span></button>
			</div>
		</div>
	
	<div class="panel-body">	
		<div class="question-frame">
			<h6><%=lang.getContent("question.edit.statementSetting.title")%></h6>
			<jsp:include page="eqComponents/eqDescription.jsp" />	
			<jsp:include page="eqComponents/eqFiles.jsp" />
		</div>
																
		<div class="question-frame">
			<h6><%=lang.getContent("question.edit.response_settings.title")%></h6>
			<%
			String textLength = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_TEXTLENGTH);
			String decimals = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_DECIMALS);
			String inputMode = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_INPUT_MODE);
			String inputType = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE);
			String minValue = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_MINVALUE);
			String maxValue = question.getParameterValue(DBConstants.s_VALUE_QUESTIONPARAMETER_MAXVALUE);							  							
			%>
			<div class="row" style="vertical-align:middle">
				<div class="col-md-3" id="inputMode">
					<label for="input-mode"><%=lang.getContent("question.form.options.text.inputTextBoxInput") %></label>
					<select class="form-control" id="input-mode">
						<option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_INPUT_MODE_FREE%>" <%if(inputMode.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_INPUT_MODE_FREE)){%> selected <%}%>><%=lang.getContent("question.form.options.text.inputTextBoxInput.free")%></option>
						<!-- <option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_INPUT_MODE_PULLDOWN%>" <%if(inputMode.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_INPUT_MODE_PULLDOWN)){%> selected <%}%>><%=lang.getContent("question.form.options.text.inputTextBoxInput.pulldown")%></option>-->
					</select>
				</div>
				
				<div class="col-md-3" id="inputType">
					<label for="input-type"><%=lang.getContent("question.form.options.text.inputTextBoxType") %></label>
					<select class="form-control" id="input-type">

						<%/*System.out.println("inputMode: "+inputMode);
						if(inputMode.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_INPUT_MODE_FREE)){ */%>
						<option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_GENERAL%>" <%if(inputMode.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_GENERAL)){%> selected <%}%>><%=lang.getContent("question.form.options.text.inputTextBoxType.general")%></option>
						<option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_TEXT%>" <%if(inputMode.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_TEXT)){%> selected <%}%>><%=lang.getContent("question.form.options.text.inputTextBoxType.text")%></option>
						<option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_NUMBER%>" <%if(inputMode.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_NUMBER)){%> selected <%}%>><%=lang.getContent("question.form.options.text.inputTextBoxType.numerical")%></option>
						<%/*}
						else{*/%>
						<!-- <option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_RANGE%>" <%if(inputMode.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_RANGE)){%> selected <%}%>><%=lang.getContent("question.form.options.text.inputTextBoxType.range")%></option>
						<option value="<%=DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_CUSTOM%>" <%if(inputMode.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_CUSTOM)){%> selected <%}%>><%=lang.getContent("question.form.options.text.inputTextBoxType.custom")%></option>-->
						<%/*}*/ %>

					</select>
				</div>
				
				<div class="respsettingsCheckbox col-md-6" id="inputOptions" ><!-- <%//if(!inputMode.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_INPUT_MODE_FREE)){%>style="display:none"<%//}%>>-->
					<div id="genericOptions" <%if(inputType.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_NUMBER)){%>class="question-response-settings hidden"<%}else{%>class="question-response-settings"<%} %>>								  									
						<input class= "question-response-settings-options" type="checkbox" name="isLimitedChars-<%= question.getIndex() %>" id="isLimitedChars" <%if(!textLength.equals("")){%> checked <%}%>>
						<label class= "question-response-settings-options" for="isLimitedChars"><%=lang.getContent("question.form.options.text.chars") %></label>
						<div id="charsId" <% if(textLength.equals("")){ %> class="question-response-settings-sub-none" <%} else{%>class="question-response-settings-sub-inherit"<%} %>>
				  			<input type="number" min="0" max="9999" onkeydown="limit(this)" onkeyup="limit(this)" id="survey-question-max-chars" value="<%= textLength %>"></input>
							<label for="survey-question-max-chars"><%=lang.getContent("question.form.options.text.charshelp") %></label>
						</div>
					</div>
								
					<div id="decimalsOptions" <%if(!inputType.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_NUMBER)){%>class="question-response-settings hidden"<%}else{%>class="question-response-settings"<%} %>>								  									
						<input class= "question-response-settings-options" type="checkbox" name="allowDecimals-<%= question.getIndex() %>" id="allowDecimals" <%if(!decimals.equals("")){%> checked <%}%>>
					  	<label class= "question-response-settings-options" for="allowDecimals"><%=lang.getContent("question.form.options.text.decimalValue") %></label>
						<div  id="decimalsDiv" <% if(decimals.equals("")){%> class="question-response-settings-sub-none" <%} else{%> class="question-response-settings-sub-inherit"<%} %>>
				  			<input type="number" min="0" max="9999" onkeydown="limit(this)" onkeyup="limit(this)" id="survey-question-decimals" value="<%= decimals %>"></input>
							<label for="survey-question-decimals"><%=lang.getContent("question.form.options.text.decimalNumbers") %></label>
						</div>
					</div>
								
					<div id="rangeOptions"  <%if(!inputType.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_NUMBER)){%>class="question-response-settings hidden"<%}else{%>class="question-response-settings"<%}%>>	
					 	<input class= "question-response-settings-options" type="checkbox" name="range-<%= question.getIndex() %>" id="range" <%if(!minValue.equals("") || !maxValue.equals("")){%> checked <%}%>>
					  	<label class= "question-response-settings-options" for="range"><%=lang.getContent("question.form.options.text.range") %></label>
						<div id="rangeId" <% if(minValue.equals("") && maxValue.equals("")){ %> class="question-response-settings-sub-none" <%} else{%>class="question-response-settings-sub-inherit"<%} %>>
							<label for="survey-minValue"><%= lang.getContent("question.form.options.text.minNumericalValue") %></label>																															  							
							<input type="number" min="0" max="9999" onkeydown="limit(this)" onkeyup="limit(this)" id="survey-minValue" value="<%=minValue%>"></input>
							<label for="survey-maxValue"><%= lang.getContent("question.form.options.text.maxNumericalValue") %></label>																															  							
							<input type="number" min="0" max="9999" onkeydown="limit(this)" onkeyup="limit(this)" id="survey-maxValue" value="<%=maxValue%>"></input>
						</div>
					</div>
				</div>
			</div>	
		</div>	
		
							  							
		<jsp:include page="eqComponents/eqDependences.jsp">
			<jsp:param value="false" name="withLogic"/>
		</jsp:include>
	</div>																					
</li>