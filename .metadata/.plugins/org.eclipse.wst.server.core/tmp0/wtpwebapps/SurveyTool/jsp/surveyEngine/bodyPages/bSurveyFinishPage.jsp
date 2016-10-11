<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.language.Language"%>
				<%
				Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
				%>
				
  						<div class="survey-finish-content" >
  							<h2><%= lang.getContent("survey.process.finishPage.title") %></h2>
  							<p><%= lang.getContent("survey.process.finishPage.body") %></p>  							
  						</div>
	  			
	  			
<%
lang.close();
%>