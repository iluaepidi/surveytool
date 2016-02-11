				<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.SurveyTableInfo"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="java.util.List"%>
<div class="container-fluid">
	  				<div class="title-content-no-underline">
	  					<h2><a href="InitialServlet">User Panel</a> > Survey Manager</h2>
	  				</div>
	  					<ul class="nav nav-tabs nav-justified">
						  <li role="presentation" id="nav-tabs-li-surveys" class="active"><a href="#" id="tab-display-surveys">Surveys</a></li>
						  <li role="presentation" id="nav-tabs-li-polls"><a href="#" id="tab-display-polls">Polls</a></li>
						</ul>
	  				<div class="content">
	  					
	  					<jsp:include page="../components/cSurveysList.jsp" />
	  					
	  					<jsp:include page="../components/cPollsList.jsp" />
	  					
	  				</div>
	  			</div>
	  			
	  			<jsp:include page="../frames/fNewSurvey.jsp" />
	  			
	  			<jsp:include page="../frames/fNewPoll.jsp" />