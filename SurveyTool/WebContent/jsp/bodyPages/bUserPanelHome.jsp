				<%@page import="ilu.surveytool.constants.Parameter"%>
<%@page import="ilu.surveytool.constants.Address"%>
<div class="container-fluid">
	  				<div class="title-content">
	  					<h2>User Panel</h2>
	  				</div>
	  				
	  				<div class="content">
	  					<p>Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen. No sólo sobrevivió 500 años, sino que tambien ingresó como texto de relleno en documentos electrónicos, quedando esencialmente igual al original. Fue popularizado en los 60s con la creación de las hojas "Letraset", las cuales contenian pasajes de Lorem Ipsum, y más recientemente con software de autoedición, como por ejemplo Aldus PageMaker, el cual incluye versiones de Lorem Ipsum.</p>
	  					<p>Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen.</p>
	  					<div class="user-panel-menu">
		  					<ul class="row row-menu">
		  						<li class="col-md-3 center"><a href="<%= Address.s_SERVLET_USER_PANEL_HOME + "?" + Parameter.s_UPOPTION + "=" + Address.s_BODY_SURVEYS %>"><i class="fa fa-list-alt fa-5x"></i><div>Survey</div></a></li>
		  						<li class="col-md-3 center"><a href="#"><i class="fa fa-users fa-5x"></i><div>User list</div></a></li>
		  						<li class="col-md-3 center"><a href="#"><i class="fa fa-area-chart fa-5x"></i><div>Panel statistics</div></a></li>
		  						<li class="col-md-3 center"><a href="#"><i class="fa fa-cogs fa-5x"></i><div>Panel settings</div></a></li>
		  					</ul>
		  				</div>
	  				</div>
	  			</div>