				<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.SurveyTableInfo"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
String tab = (String) request.getAttribute(Attribute.s_TAB);
%>   
				<div class="container-fluid">
	  				<div class="title-content-no-underline">
	  					<h2><a href="InitialServlet"><%= lang.getContent("user_panel.title") %></a> > <%= lang.getContent("survey_manager.title") %></h2>
	  					<div class="right">
		  					<ul class="nav nav-tabs nav-tabs-right nav-tabs-survey-manager">
							  <li role="presentation" id="nav-tabs-li-surveys" <%if(tab.equals("survey")){%>class="active"<%}%>><a href="#" id="tab-display-surveys"><%= lang.getContent("survey_manager.tab.surveys") %></a></li>
							  <li role="presentation" id="nav-tabs-li-polls" <%if(tab.equals("poll")){%>class="active"<%}%>><a href="#" id="tab-display-polls"><%= lang.getContent("survey_manager.tab.polls") %></a></li>
							</ul>
						</div>
						<div class="gray-padding"></div>
	  				</div>
	  					
	  				<div class="content">
	  					
	  					<jsp:include page="../components/cSurveysList.jsp" />
	  					
	  					<jsp:include page="../components/cPollsList.jsp" />
	  					
	  				</div>
	  			</div>
<%
lang.close();
%>	  			
	  			<jsp:include page="../frames/fNewSurvey.jsp" />
	  			
	  			<jsp:include page="../frames/fNewPoll.jsp" />
	  			

<script> 
 
    $(document).ready(function() {

    	var table = $('table.display').dataTable({
    		"aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
        	"pagingType": "full_numbers",
            "scrollCollapse": false,
            "searching": true,
            "ordering": false,
            "bLengthChange" : true,
            "language": {
            	"url": "js/dataTables.<%=Language.getLanguageRequest(request)%>.lang"
            }
            
        });        
        
    	$(document).on('draw.dt', function () {
    		//$('[name="surveys-table_length"]').val("10");
    	 	$('#DataTables_Table_0_filter label').append("<i class='fa fa-search' aria-hidden='true'></i>");
    	 	$('#DataTables_Table_1_filter label').append("<i class='fa fa-search' aria-hidden='true'></i>");
    	 	$('table').removeAttr("role");
    	 	$('tr').removeAttr("role");
        });
       
    });
    
</script> 
	  			