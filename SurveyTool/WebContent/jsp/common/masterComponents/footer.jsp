<%@page import="ilu.surveytool.language.Language"%>
<%@page import="java.util.Properties"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
				<div class="container-fluid">
		  			<div class="footer-links">
		  				<ul class="row row-footer nopadding nomargin">
		  					<li class="col-sm-2 col-xs-2 center"><a href="bAbout.jsp" target="_blank"><%= lang.getContent("master.footer.link.about") %></a></li>
		  					<li class="col-sm-2 col-xs-2 center"><a href="bLegal.jsp" target="_blank"><%= lang.getContent("master.footer.link.legal") %></a></li>
		  					<li class="col-sm-2 col-xs-2 center"><a href="bAccessibility.jsp" target="_blank"><%= lang.getContent("master.footer.link.accessibility") %></a></li>
		  					<li class="col-sm-2 col-xs-2 center"><a href="bCookies.jsp" target="_blank"><%= lang.getContent("master.footer.link.cookies") %></a></li>
		  					<li class="col-sm-2 col-xs-2 center"><a href="bPolicy.jsp" target="_blank"><%= lang.getContent("master.footer.link.privacy") %></a></li>
		  					<li class="col-sm-2 col-xs-2 center"><a href="bContact.jsp" target="_blank"><%= lang.getContent("master.footer.link.contact") %></a></li>
		  				</ul>	  			
		  			</div>
		  			<div class="content footer-logos">
		  				<div class="footer-logo-content">
			  				<img src="img/euroflag.jpg" />
			  				<p><%= lang.getContent("master.footer.content") %></p>
			  			</div>
		  			</div>
		  		</div>
<%
lang.close();
%>