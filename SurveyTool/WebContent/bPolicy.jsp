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
					<h2>Política de privacidad</h2>
					<p>A través de la página web de http://survey.e-presentaciones.net/SurveyTool/ no se recoge ningún dato personal sin su conocimiento, ni se ceden a terceros.</p>
					<p>Los datos personales que se soliciten a los usuarios serán los estrictamente imprescindibles para poder proporcionarle los servicios de este sitio web.</p>
					<p>Los datos de carácter personal que se facilitan mediante el formulario disponible quedan registrados en un fichero automatizado responsable de ILUNION Accesibilidad, Estudios y Proyectos S.A.U., con la finalidad de proporcionar los servicios de este sitio web.</p>
					<p>El contacto de la web tiene carácter meramente informativo, sin que, en ningún caso, pueda derivarse de la contestación efecto jurídico vinculante alguno.</p>
					<p>El acceso o utilización de este sitio web por parte del usuario implica su consentimiento y, por tanto, la adhesión a las condiciones expuestas anteriormente.</p>
					<p>Se informa que se pueden ejercitar los derechos de acceso, oposición, rectificación y cancelación ante ILUNION Accesibilidad, Estudios y Proyectos S.A.U. Es necesario aportar fotocopia del D.N.I. o documento equivalente que acredite la identidad y sea considerado válido en derecho, para que el responsable del fichero pueda realizar la comprobación oportuna. También podrá ejercerse a través de representación legal, en cuyo caso, además del DNI del interesado, habrá de aportarse DNI y documento acreditativo auténtico de la representación del tercero.</p>
					<p>Esta política de privacidad sólo es de aplicación a la página web  http://survey.e-presentaciones.net/SurveyTool/, no se garantiza en los accesos a través de enlaces con este sitio, ni a los enlaces desde este sitio con otras webs. Enlaces a páginas externas sobre las que no se tienen control alguno y respecto de las cuales se declina toda responsabilidad.</p>
					
				<% } else { %>
					<h2>Privacy policy</h2>
					<p>Through the http://survey.e-presentaciones.net/SurveyTool/ website, no personal data is collected without your knowledge, nor are they transferred to third parties.</p>
					<p>The personal data requested from users will be strictly necessary to be able to provide the services of this website.</p>
					<p>The personal data that are provided through the available forms are registered in an automated file responsibility of ILUNION Accesibilidad, Estudios y Proyectos S.A.U., in order to provide the services of this website.</p>
					<p>The contact of the web is purely informative, so that no legal binding can be drawn from it.</p>
					<p>The access to or use of this website by the user implies your consent and, therefore, adherence to the conditions set out above.</p>
					<p>The rights of access, opposition, rectification and cancellation can be exercised before ILUNION Accesibilidad, Estudios y Proyectos S.A.U.. It is necessary to provide a photocopy of a legal identity card, so that the person in charge of the file can carry out the timely verification. It may also be exercised through legal representation, in which case, in addition to the legal identity card of the interested party, there must be a legal identity card and authentic document of representation of the third party.</p>
					<p>This privacy policy is only applicable to the http://survey.e-presentaciones.net/SurveyTool/ website, it is not guaranteed in the accesses through links to this site, nor to the links from this site with other websites. Links to external pages over which there is no control and in respect of which all responsibility is declined.</p>
					
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