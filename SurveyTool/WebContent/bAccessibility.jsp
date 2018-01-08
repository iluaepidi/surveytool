<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.language.Language"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String footerPath = "jsp/common/masterComponents/footer.jsp";
Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="<%= lang.getCurrentLanguage() %>">
	<head>
		<jsp:include page="jsp/common/masterComponents/head.jsp" />
	</head>
  	<body>
  		<div class="main-container">
  			<div class="page-header">
  				<jsp:include page="jsp/common/masterComponents/header.jsp" />
  			</div>
  			
  			<div class="body-content st-styles">
	  			<div class="survey-free-content content" >
	  			<%if(lang.getCurrentLanguage().equals("es")) { %>
					<h2>Accesibilidad</h2>
					<p>El objetivo de ILUNION Accesibilidad, Estudios y Proyectos S.A.U. y del consorcio Prosperity4all es que todas las personas, independientemente de sus capacidades, su edad o de si acceden a la web desde tecnologías poco convencionales, puedan navegar por las páginas de este sitio web sin encontrar dificultades de acceso.</p>
					<p>A tal fin, esta web cumple con las Pautas de Accesibilidad al Contenido en la Web 2.0 establecidas por el W3C (Consorcio de la World Wide Web).</p>
					
					<div class="accLogos">
						<img src="img/w3c_aa_grande2.gif" alt="WCAG logo"/>
						<img src="img/w3c_xhml_grande.gif" alt="XHTML logo"/>
						<img src="img/w3c_css_grande.gif" alt="CSS logo"/>
					</div>
					
					<p>Algunas de las funcionalidades implementadas en la web para permitirle acceder a todos los contenidos de la web con mayor facilidad son:</p>
					
					<ul class="normal-list">
						<li>Las características visuales del portal (tipo de letra, color de fuente y fondo, etc.) se definen mediante la hoja de estilos para que el usuario pueda ajustar el texto a sus preferencias.</li>
						<li>Los tamaños de las fuentes se han definido con unidades relativas para que pueda ampliar o disminuir el tamaño de la fuente desde las opciones del navegador.</li>
						<li>Si un usuario accede a la página con un navegador que no soporta hoja de estilos, no supondrá una dificultad, ya que de igual manera se puede acceder a todos los contenidos con la CSS desactivada.</li>
						<li>Las páginas tienen una estructura clara tanto para el usuario que puede ver todo el contenido, como para el que lee la información con un lector de pantalla, usuarios que desactivan la hoja de estilos, etc. Bajo este objetivo, se han definido mediante el código HTML los encabezados de sección, las listas y todos los elementos que ayudan a la comprensión general del sitio web.</li>
					</ul>
					
					<p>El código HTML y CSS empleado se ajusta a las gramáticas formales para garantizar la correcta visualización de los contenidos en distintos navegadores.</p>
					 
					
				<% } else { %>
					<h2>Accessibility</h2>
					<p>The objective of ILUNION Accesibilidad, Estudios y Proyectos S.A.U. and the Prosperity4all consortium is that all people, regardless of their abilities or age, can navigate the pages of this website without access difficulties.</p>
					<p>To this end and to the best of our knowledge, this website is compliant with the Web 2.0 Content Accessibility Guidelines established by the W3C (World Wide Web Consortium). </p>
					
					<div class="accLogos">
						<img src="img/w3c_aa_grande2.gif" alt="WCAG logo"/>
						<img src="img/w3c_xhml_grande.gif" alt="XHTML logo"/>
						<img src="img/w3c_css_grande.gif" alt="CSS logo"/>
					</div>
					
					<p>Some of the functionalities implemented in the web to allow you to access all the contents of the web more easily are:</p>
					
					<ul class="normal-list">
						<li>The visual characteristics of the portal (font, font color and background, etc.) are defined by the style sheet so that the user can adjust the text to their preferences.</li>
						<li>Font sizes have been defined with relative units so you can zoom in or out from the browser options.</li>
						<li>If a user accesses the page with a browser that does not support style sheet, it will not be a difficulty, since all the content can be accessed with the CSS disabled.</li>
						<li>The pages have a clear structure both for the user who can see all the content, as for the one who reads the information with a screen reader, users that deactivate the style sheet, etc. For this purpose, the section headers, the lists and all the elements that help the general understanding of the website have been defined by the HTML code.</li>
					</ul>
					
					<p>The HTML and CSS code used conforms to formal grammars to ensure the correct visualization of the contents in different browsers.</p>
					   			
					
				<% } %>									
				</div>
	  		</div>
	  		
	  		<footer class="footer">
	  			<jsp:include page="<%= footerPath %>" />
	  		</footer>	
 		</div> 
  	</body>
</html>
				
<%
lang.close();
%>