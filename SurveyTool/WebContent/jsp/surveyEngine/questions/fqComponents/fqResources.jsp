
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
        										
												<div class="previewFileUpliaded" id="previewFileUploaded" ng-show="question.resources.length > 0">
													<div ng-repeat="resource in question.resources">														
													    <div ng-if="resource.resourceType == 'image'">
										            		<img src="{{resource.urlPath}}" alt='{{getJsonArrayElement(resource.contents, "contentType", "altText").text}}' />
										            	</div>
										            	
														<div class="reproductor-youtube" ng-if="resource.resourceType == 'video'">														
															<!-- <iframe id="reproductor_{{resource.resourceId}}" type="text/html" src="{{'https://www.youtube.com/embed/2IICvsuL9Jw?enablejsapi=1'}}" height="250" width="400" allowfullscreen="always" frameborder="0" data-title="{{getJsonArrayElement(resource.contents, 'contentType', 'title').text}}" tabindex="-1"></iframe> -->
															<youtube-video class="embed-responsive-item" video-id="resource.urlPath"></youtube-video>
															
														</div>
													</div>													
									            </div>
									            
							  					