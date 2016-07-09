/**
 * 
 */
var qtypeId;
var numQuestions = 0;
var currentAddNode;
var currentQuestion = 0;
var currentLanguage = "en";
var addMenuFrameCad = "add-menu-frame-";
var pending;

$(function() {
	
	var host = "http://" + window.location.host;
	console.log("host: " + host);

	$('body_').click(function() {
				
    });
	
	$('#survey-sections').on("click", "#removeSection", function(e){
		var item = $(this).closest('#panel-section1');
		currentQuestion = item.attr('scid');
		$("#elementToRemoveText").html('"Question: ' + item.find('#survey-section-title').val() + '"');
		$("#removeElemId").val(item.attr('scid') + '/' + $('#survey-info').attr('sid'));
		$("#removeElemService").val('SectionService');
		$("#removeElement").modal("show");
	});
});