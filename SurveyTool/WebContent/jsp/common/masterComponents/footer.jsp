<%@page import="ilu.surveytool.language.Language"%>
<%@page import="java.util.Properties"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
				<div class="container-fluid">
		  			<div class="footer-links">
		  				<ul class="row row-footer nopadding nomargin">
		  					<li class="col-sm-3 col-xs-3 center"><a href="#"><%= lang.getContent("master.footer.link.about") %></a></li>
		  					<li class="col-sm-2 col-xs-2 center"><a href="#"><%= lang.getContent("master.footer.link.legal") %></a></li>
		  					<li class="col-sm-2 col-xs-2 center"><a href="#"><%= lang.getContent("master.footer.link.accessibility") %></a></li>
		  					<li class="col-sm-2 col-xs-2 center"><a href="#"><%= lang.getContent("master.footer.link.cookies") %></a></li>
		  					<li class="col-sm-3 col-xs-3 center"><a href="#"><%= lang.getContent("master.footer.link.contact") %></a></li>
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