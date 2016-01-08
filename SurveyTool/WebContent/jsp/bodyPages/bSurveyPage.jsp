					
				<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Survey"%>
				<%
				Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO);
				%>	
								
				<div class="container-fluid">
	  				<div class="title-content">
	  					<h2><%= survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() %></h2>	  					
	  				</div>	  				
	  				<div class="content">
	  					<form>
	  						<div class="survey-form" >
	  							<p><%= survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText() %></p>
	  							<jsp:include page="/jsp/formQuestions/fQuestionText.jsp" />
	  						</div>
	  					</form>
	  				</div>
	  			</div>
	  			