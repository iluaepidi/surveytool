/**
 * 
 */
var qtypeId;
var numQuestions = 0;
var currentAddNode;
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
			//$('#' + addMenuFrameCad + currentNode).after(responseText);
			currentAddNode.after(responseText);			
		});
	});
	
	$('#panel-body').on("click", '#btn-question', function(){
		currentAddNode = $(this).parent().parent().parent();
	});
	
	$('#option-list').on("keyup", "#option-item input", function(e){
		e.stopPropagation();
		var id = $(this).attr('id');
		$(this).closest('div[id=panel-question1]').find('label[id=optionRadioLabel' + id + ']').text($(this).val());
		//console.log("Option text field!!!! " + $(this).parent().parent().children("li").size());
	});
	
	$('#option-list').on("focusout", "#option-item input", function(e){
		e.stopPropagation();
		console.log("TExt: " + $(this).val() + " - qid: " + $(this).attr('index') + " - qid: " + $(this).closest('div[id=panel-question1]').attr('qid') + " - ogid: " + $(this).closest('ul').attr('ogid'));
		var req = {};
		req.text = $(this).val();
		req.index = $(this).attr('index');
		req.qid = $(this).closest('div[id=panel-question1]').attr('qid');
		req.ogid = $(this).closest('ul').attr('ogid');
		$.ajax({ 
		   type: "POST",
		   dataType: "text",
		   contentType: "text/plain",
		   url: "http://localhost:8080/SurveyTool/api/QCService/insertOption",
		   data: JSON.stringify(req),
		   success: function (data) {
			   console.log(data);
		   },
		   error: function (xhr, ajaxOptions, thrownError) {
			   console.log(xhr.status);
			   console.log(thrownError);
			   console.log(xhr.responseText);
			   console.log(xhr);
		   }
		});
	});
	
});
