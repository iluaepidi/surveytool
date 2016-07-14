<%@page import="ilu.surveytool.language.Language"%>
<%@page import="ilu.surveytool.databasemanager.constants.DBConstants"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Resource"%>
<%@page import="java.util.List"%>
<%@page import="ilu.surveytool.constants.Attribute"%>
<%@page import="ilu.surveytool.databasemanager.DataObject.Question"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    											<%
    											Language lang = (Language) request.getAttribute(Attribute.s_SURVEY_LANGUAGE);
    											
    											Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
    											List<Resource> resources = question.getResources();
    											
												if(!resources.isEmpty())
												{
												%>
												<div class="previewFileUpliaded" id="previewFileUploaded">
													<%
													for(Resource resource : resources)
													{
														if(resource.getType().equals(DBConstants.s_VALUE_RESOURCE_TYPE_IMAGE))
														{
															String alt = "";
															if(!resource.getContents().isEmpty() && resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT) != null)
															{
																alt = resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT).getText();
															}
													%>
													<!-- <label for="img" class="visuallyhidden"><%= lang.getContent("accesibility.question.image") %></label> -->
													<div>
									            		<img src="<%= resource.getPathFile() %>" alt="<%= alt %>" />
									            	</div>
									            	<%
														}
														else if(resource.getType().equals(DBConstants.s_VALUE_RESOURCE_TYPE_VIDEO))
														{
															String title = "";
															if(!resource.getContents().isEmpty() && resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE) != null)
															{
																title = resource.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
															}
													%>
													<div class="reproductor-youtube">
														<div class="reproductor-accesible">
															<ul class="element-invisible">
																<li class="liControl"><button class="btnPlayerControl" id="btnPlay_reproductor_lPVLhShXeW8">Reproducir<span class="element-invisible"> el vídeo  "Video destacado"</span></button></li>
																<li class="liControl"><button class="btnPlayerControl" id="btnVolumeDown_reproductor_lPVLhShXeW8">Bajar volumen<span class="element-invisible"> al vídeo  "Video destacado"</span></button></li>
																<li class="liControl"><button class="btnPlayerControl" id="btnVolumeUp_reproductor_lPVLhShXeW8">Subir volumen<span class="element-invisible"> al vídeo  "Video destacado"</span></button></li>
																<li class="liControl"><button class="btnPlayerControl" id="btnMute_reproductor_lPVLhShXeW8">Desactivar / activar sonido<span class="element-invisible"> al vídeo  "Video destacado"</span></button></li>
																<li class="liControl"><button class="btnPlayerControl" id="btnFw30Secs_reproductor_lPVLhShXeW8">Avanzar treinta segundos<span class="element-invisible"> en el vídeo  "Video destacado"</span></button></li>
																<li class="liControl"><button class="btnPlayerControl" id="btnBw30Secs_reproductor_lPVLhShXeW8">Retroceder treinta segundos<span class="element-invisible"> en el vídeo  "Video destacado"</span></button></li>
															</ul>
															<dl class="element-invisible" id="dl_reproductor_lPVLhShXeW8">
																<dt>Posición:</dt>
																<dd id="ddPosicion_reproductor_lPVLhShXeW8">0 segundos</dd>
																<dt>Duración:</dt>
																<dd id="ddDuracion_reproductor_lPVLhShXeW8">Desconocida</dd>
															</dl>
															<p><a target="_blank" class="element-invisible" href="https://www.youtube.com/watch?v=lPVLhShXeW8">Ver<span class="element-invisible"> el vídeo  "Video destacado"</span> en Youtube<span class="element-invisible"> (Abre en ventana nueva)</span></a></p>
															<a href="#saltar_reproductor_lPVLhShXeW8" class="element-invisible">Saltar el flash  del vídeo  "Video destacado"</a>
														</div>
														<iframe id="reproductor_lPVLhShXeW8" type="text/html" src="http://www.youtube.com/embed/lPVLhShXeW8?enablejsapi=1" height="400" width="700" allowfullscreen="" frameborder="0" data-title="Video destacado" tabindex="-1"></iframe>
														<!-- <iframe id="reproductor" type="text/html" src="http://www.youtube.com/embed/k-M9QZkc9YE" height="200" width="350" allowfullscreen="" frameborder="0"></iframe> -->
													</div>
													<%		
														}
													}
										            %>
									            </div>
									            <%
												}
									            %>
							  					