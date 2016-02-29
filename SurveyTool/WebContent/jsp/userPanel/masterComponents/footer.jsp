<%@page import="ilu.surveytool.language.Language"%>
<%@page import="java.util.Properties"%>
<%
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
				<div class="container-fluid">
		  			<div class="footer-links">
		  				<ul class="row row-footer">
		  					<li class="col-md-3 center"><a href="#"><%= lang.getContent("master.footer.link.about") %></a></li>
		  					<li class="col-md-2 center"><a href="#"><%= lang.getContent("master.footer.link.legal") %></a></li>
		  					<li class="col-md-2 center"><a href="#"><%= lang.getContent("master.footer.link.accessibility") %></a></li>
		  					<li class="col-md-2 center"><a href="#"><%= lang.getContent("master.footer.link.cookies") %></a></li>
		  					<li class="col-md-3 center"><a href="#"><%= lang.getContent("master.footer.link.contact") %></a></li>
		  				</ul>	  			
		  			</div>
		  			<div class="content footer-logos">
		  				<p><%= lang.getContent("master.footer.content") %></p>
		  			</div>
		  		</div>
<%
lang.close();
%>