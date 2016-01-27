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
	
	var clientTarget = new ZeroClipboard( $("#target-to-copy"), {
	    moviePath: "http://www.paulund.co.uk/playground/demo/zeroclipboard-demo/zeroclipboard/ZeroClipboard.swf",
	    debug: false
	} );

	clientTarget.on( "load", function(clientTarget)
	{
	    $('#flash-loaded').fadeIn();

	    clientTarget.on( "complete", function(clientTarget, args) {
	        clientTarget.setText( args.text );
	        $('#target-to-copy-text').fadeIn();
	    } );
	});

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
		$('#qstatement').val("");
		//$('#main-version').val("none");
		$('#mandatory').val("false");
		$('#help-text').val("false");
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
		$('#qstatement').val("");
		//$('#main-version').val("none");
		$('#mandatory').val("false");
		$('#help-text').val("false");
		$('#' + qtypeId + " i").css("background-color","#fff");
		$('#frame-basic-Settings').css('display', 'none');
	});
	
	$('#panel-body').on("click", '#btn-question', function(){
		currentAddNode = $(this).parent().parent().parent();
	});
	
	$('#panel-body').on("keyup", "#option-list #option-item input", function(e){
		e.stopPropagation();
		var index = $(this).attr('index');
		$(this).closest('div[id=panel-question1]').find('label[id=optionRadioLabel' + index + ']').text($(this).val());
		//console.log("Option text field!!!! " + $(this).parent().parent().children("li").size());
	});
	
	$('#panel-body').on("focusout", "#option-list #option-item input", function(e){
		e.stopPropagation();
		console.log("TExt: " + $(this).val() + " - qid: " + $(this).attr('index') + " - qid: " + $(this).closest('div[id=panel-question1]').attr('qid') + " - ogid: " + $(this).closest('ul').attr('ogid'));
		var req = {};
		var currentNode = $(this);
		req.text = currentNode.val();
		req.oid = currentNode.attr('oid');
		req.index = currentNode.attr('index');
		req.qid = currentNode.closest('div[id=panel-question1]').attr('qid');
		req.ogid = currentNode.closest('ul').attr('ogid');
		req.otype = currentNode.closest('ul').attr('otype');
		req.lang = currentNode.closest('div[id=panel-body]').find('select[id=main-version]').val();
		
		$.ajax({ 
		   type: "POST",
		   dataType: "text",
		   contentType: "text/plain",
		   url: "http://localhost:8080/SurveyTool/api/QCService/insertOption",
		   data: JSON.stringify(req),
		   success: function (data) {
			   console.log(data);
			   if(data != '')
			   {
				   var json = JSON.parse(data);
				   if(json.hasOwnProperty('oid'))
				   {
					   console.log("hello oid: " + json.oid);
					   currentNode.attr('oid', json.oid);
				   }
				   
				   if(json.hasOwnProperty('ogid'))
				   {
					   console.log("hello ogid: " + json.ogid);
					   currentNode.closest('ul').attr('ogid', json.ogid);
				   }
			   }
		   },
		   error: function (xhr, ajaxOptions, thrownError) {
			   console.log(xhr.status);
			   console.log(thrownError);
			   console.log(xhr.responseText);
			   console.log(xhr);
		   }
		});
	});
	
	$('#panel-body').on("click", "#option-list #btn-add-option", function(e){
		var index = $(this).parent().parent().children("li").size();
		var optionHtml = '<li class="option-item" id="option-item">' +
															'<button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> ' +		
						  									'<div class="circle-info circle-grey fleft">' + index + '</div> ' + 
						  									'<input type="text" class="option-title form-control fleft" index="' + index + '" oid="0" placeholder="Option ' + index + '"/> ' +
						  									'<div class="option-icons fleft"> ' +
							  									'<a class="btn btn-transparent fleft"><i class="fa fa-file-image-o fa-2x"></i></a> ' +
							  									'<a class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></a> ' +
							  									'<a class="btn btn-transparent fleft red"><i class="fa fa-trash fa-2x"></i></a> ' +
							  								'</div> ' +
							  							'</li>';
		$(this).parent().before(optionHtml);
	});
	
	//$('#uploadedFile').change(function(e) {
	$('#selectFile').on("change", "#uploadedFile", function(e){
		console.log( "uploadedFile" );
		console.log($("#uploadedFile").val());
		var fileValue = $('uploadedFile').val();
		 var formData = new FormData(document.getElementById("importFileForm"));
         formData.append("uploadedFile", document.getElementById('uploadedFile').files[0]);
         
         //alert($('#optionsFile').hasClass('hidden'));
         if($('#optionsFile').hasClass('hidden') == true){
        	 formData.append("qid", currentQuestion);
        	 formData.append("action", "file");
         } else {
        	 formData.append("rid", $('#rid').val());
        	 formData.append("action", "fileUpdate");
         }
         
         $.ajax({
             url: "ImportFileServlet",
             type: "post",
             dataType: "html",
             data: formData,
             cache: false,
             contentType: false,
             processData: false
         }).done(function(res){
             console.log("Respuesta: " + res);
             //$('#selectFile').addClass('hidden');
             if($('#optionsFile').hasClass('hidden')){
            	 $('#optionsFile').removeClass('hidden');
                 $('#optionsFile').append(res);
             }
             else
             {
            	 $('#previewFileUploaded').replaceWith(res);
             }
             
         });
	});
	
	$('#optionsFile').on("click", "#btnImportFile", function(e){
		$('#importFileForm').on("submit", function(e){
			e.preventDefault();
			if(pending)
			{
				return;
			}
			pending = true;
	        $.post('ImportFileServlet', {
	        	action : "options",
	        	resourceTitle: $('#resourceTitle').val(),
	  			resourceAltText: $('#resourceAltText').val(),
	  			mainVersion: currentLanguage,
	  			rid: $('#rid').val()
	  		}, function(res) {
	  			$('#importFileForm')[0].reset();
	              $("#importFile").modal("hide");
	              var multimediaFrame = $("div[qid=" + currentQuestion + "]").find("div[id=multimediaFrame]");
	              multimediaFrame.removeClass("hidden");
	              multimediaFrame.find("ul[id=multimediaFilesList]").append(res);		
	              $('#optionsFile').empty();
	              $('#optionsFile').addClass('hidden');
	              pending = false;
	  		});
		});
	});
	
	$('#panel-body').on("click", "#btn-question-import-file", function(e){
		currentQuestion = $(this).closest('div[id=panel-question1]').attr('qid');
		currentLanguage = $(this).closest('div[id=panel-question1]').find('select[id=main-version]').val();
		console.log("current question: " + currentQuestion + " - language: " + currentLanguage);
	});
		
});



