/**
 * 
 */
var qtypeId;
var numQuestions = 0;
var currentNode = 0;
var addMenuFrameCad = "add-menu-frame-";

$(function() {

	$('body_').click(function() {
				
    });
	
	//survey-info  #e6e6e6
	$('.btn-qtype a').click(function(){
		$('#frame-basic-Settings').css('display', 'inherit');
		$('#' + qtypeId + " i").css("background-color","#fff");
		qtypeId = $(this).attr('id');
		$('#' + qtypeId + ' i').css("background-color","#e6e6e6");
		$('#qtypevalue').val(qtypeId);
	});
	
	$('#basic-settings-close').click(function(){
		$('#frame-basic-Settings').css('display', 'none');
		$('#' + qtypeId + " i").css("background-color","#fff");
	});
	
	$('#create-question').click(function(event) {
		// Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
		$.post('CreateQuestionServlet', {
			qtype : $('#qtypevalue').val(),
			qstatement: $('#qstatement').val(),
			mainVersion: $('#main-version').val(),
			mandatory: $('#mandatory').val(),
			helpText: $('#help-text').val(),
			surveyid: $('#surveyid').val(),
			pageid: $('#pageid1').val()
		}, function(responseText) {
			//$('#tabla').html(responseText);
			console.log("Question Simple: " + responseText);
			$('#' + addMenuFrameCad + currentNode).after(responseText);
		});
		//console.log("Page Id: " + $('#pageid1').val());
	});
	
	$('#btn-question-0').click(function(){
		var btnId = $(this).attr('id');
		var aux = btnId.split('-');
		currentNode = parseInt(aux[2]);
		console.log("btn id: " + currentNode);
	});
	
});

