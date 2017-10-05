<%@page import="ilu.surveytool.constants.Address"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.language.Language"%>
				<%
				Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
				String sid = (String) request.getAttribute(Attribute.s_SURVEY_ID);
				%>
				
  						<div class="survey-state-content content" >
  							<h1><%= lang.getContent("survey.process.page.ipfilter.title") %></h1>
  							<form action="<%= Address.s_SERVLET_SURVEYS_AJS %>" class="ip-filter-form">
  								<p><%= lang.getContent("survey.process.page.ipfilter.body") %></p>
  								
  								<input type="hidden" name="sid" value="<%= sid %>" />
  								<input type="hidden" name="langsurvey" value="<%= lang.getCurrentLanguage() %>" />
  								
 								
		    					<div class="ip-filter-buttons col-sm-offset-4 col-sm-4">
		    						<div class="col-sm-4">
		    							<input type="submit" class="btn btn-default" name="cancelIpFilter" id="cancelIpFilter" value="<%= lang.getContent("button.cancel") %>" />
		    						</div>
		    						<div class="col-sm-4 col-sm-offset-4">
		    							<input type="submit" class="btn btn-primary" name="acceptIpFilter" id="acceptIpFilter" value="<%= lang.getContent("button.accept") %>" />
		    						</div>
		    					</div>
			    					
  							</form>  							
  						</div>
	  			
	  			
<%
lang.close();
%>