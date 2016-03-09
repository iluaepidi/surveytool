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
	
	/*var clientTarget = new ZeroClipboard( $("#target-to-copy"), {
	    moviePath: "http://www.paulund.co.uk/playground/demo/zeroclipboard-demo/zeroclipboard/ZeroClipboard.swf",
	    debug: false
	} );

	clientTarget.on( "load", function(clientTarget)
	{
	    $('#flash-loaded').fadeIn();

	    clientTarget.on( "complete", function(clientTarget, args) {
	        clientTarget.setText( args.text );
	        $('#target-to-copy-text').fadeIn();
	    });
	});*/

	$('body_').click(function() {
				
    });
	
	//survey-info  #e6e6e6
	$('.btn-qtype button').click(function(){
		$('#frame-basic-Settings').css('display', 'inherit');
		$('#' + qtypeId + " i").css("background-color","#fff");
		qtypeId = $(this).attr('id');
		$('#' + qtypeId + ' i').css("background-color","#e6e6e6");
		$('#qtypevalue').val(qtypeId);
		$('#qstatement').focus();
		
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
			mandatory: $('#mandatory').val(),
			helpText: $('#help-text').val(),
			surveyid: $('#surveyid').val(),
			pageid: $('#pageid1').val()
		}, function(responseText) {
			var index = responseText.indexOf("<html");
			if(index >= 0) {window.location.replace("http://localhost:8080/SurveyTool/SurveysServlet");}
			else {currentAddNode.before(responseText);}
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
		$("#newQuestionModal").modal("show");
	});
	
	$('#panel-body').on("keyup", "#option-list #option-item input", function(e){
		e.stopPropagation();
		var index = $(this).attr('index');
		$(this).closest('div[id=panel-question1]').find('label[id=optionRadioLabel' + index + ']').text($(this).val());
		//console.log("Option text field!!!! " + $(this).parent().parent().children("li").size());
	});
	
	$('#panel-body').on("focusout", "#option-list #option-item input", function(e){
		e.stopPropagation();
		//console.log("language: " + $('#survey-language-version').val());
		if($(this).val() != "")
		{
			console.log("TExt: " + $(this).val() + " - qid: " + $(this).attr('index') + " - qid: " + $(this).closest('div[id=panel-question1]').attr('qid') + " - ogid: " + $(this).closest('ul').attr('ogid'));
			var req = {};
			var currentNode = $(this);
			req.text = currentNode.val();
			req.oid = currentNode.attr('oid');
			req.index = currentNode.attr('index');
			req.qid = currentNode.closest('div[id=panel-question1]').attr('qid');
			req.ogid = currentNode.closest('ul').attr('ogid');
			req.lang = $('#survey-language-version').val();
			req.otype = currentNode.closest('ul').attr('otype');
			
			$.ajax({ 
			   type: "POST",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QCService/insertOption",
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
					   
					   currentNode.closest('li').find('#remove-option').attr('aria-label', 'Remove option: ' + req.text);
				   }
			   },
			   error: function (xhr, ajaxOptions, thrownError) {
				   console.log(xhr.status);
				   console.log(thrownError);
				   console.log(xhr.responseText);
				   console.log(xhr);
			   }
			});
		}
	});
	
	$('#panel-body').on("click", "#option-list #btn-add-option", function(e){
		var index = $(this).parent().parent().children("li").size();
		var optionHtml = '<li class="option-item" id="option-item">' +
															//'<button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> ' +		
						  									'<div class="circle-info circle-grey fleft">' + index + '</div> ' + 
						  									'<input type="text" class="option-title form-control fleft" index="' + index + '" oid="0" placeholder="Option ' + index + '" autofocus/> ' +
						  									'<div class="option-icons fleft"> ' +
							  									//'<button class="btn btn-transparent fleft"><i class="fa fa-file-image-o fa-2x"></i></button> ' +
							  									//'<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> ' +
							  									'<button class="btn btn-transparent fleft red" id="remove-option" aria-label="remove option"><i class="fa fa-trash fa-2x"></i></button> ' +
							  								'</div> ' +
							  							'</li>';
		$(this).parent().before(optionHtml);
		//$(this).closest('ul').find('input[index=' + index + ']').focus();
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
		currentLanguage = $('#survey-language-version').val();
		console.log("current question: " + currentQuestion + " - language: " + currentLanguage);
	});
	
	$('#panel-body').on("click", "#removeQuestion", function(e){
		var item = $(this).closest('#panel-question1');
		currentQuestion = item.attr('qid');
		$("#elementToRemoveText").html('"Question: ' + item.find('#survey-question-title').val() + '"');
		$("#removeElemId").val(item.attr('qid') + '/' + item.closest('div[id=page]').attr('pid'));
		$("#removeElemService").val('QuestionService');
		$("#removeElement").modal("show");
	});
	
	$('#panel-body').on("click", "#removeMultimediaFile", function(e){
		var item = $(this).closest('li.multimedia-item');
		$("#elementToRemoveText").html('"' + item.find('a').text() + '"');
		$("#removeElemId").val(item.attr('rid'));
		$("#removeElemService").val('ResourceService');
		$("#removeElement").modal("show");
	});
	
	$('#panel-body').on("click", "#remove-option", function(e){
		currentQuestion = $(this).closest('#panel-question1').attr('qid');
		var item = $(this).closest('li');
		var input = item.find('input');
		$("#elementToRemoveText").html('"Option: ' + input.val() + '"');
		$("#removeElemId").val(input.attr('oid'));
		$("#removeElemService").val('OptionService');
		$("#removeElement").modal("show");
	});
	
	$('#removeElement').on("click", "#acceptRemoveElement", function(e){
		
		var elementId = $('#removeElemId').val(); 
		var service = $("#removeElemService").val();
		console.log("Resource ID: " + elementId);
		
		//console.log("number items: " + $('li[rid=' + resourceId + ']').closest("ul").find("li").size());
		
		$.ajax({ 
		   url: host + "/SurveyTool/api/" + service + "/" + elementId,
		   type: "DELETE",
		   success: function (data) {
			   console.log(data);
			   if(data == 'true')
			   {
				   $("#removeElement").modal("hide");
				   if(service == "ResourceService")
				   {
					   $('li[rid=' + elementId + ']').remove();
					  /* var numItems = $('li[rid=' + elementId + ']').closest("ul").find("li").size();
					   console.log("Items: " + numItems);
					   if(numItems == 0)
					   {
						   $('#multimediaFrame').addClass('hidden');
					   }*/
				   }
				   else if(service == "QuestionService")
				   {
					   $('div[qid="' + currentQuestion + '"]').remove();
				   }
				   else if(service == "OptionService")
				   {
					   var input = $('input[oid=' + elementId + ']'); 					   
					   var numItems = input.closest("ul").find("li").size();
					   console.log("Items: " + numItems);
					   if(numItems > 3)
					   {
						   input.closest("li").remove();
						   $('div[qid=' + currentQuestion + '] ul[id=option-list] li[id=option-item]').each(function(i, elem)
						   {
							   console.log("li: " + i + " - elem: " + $(elem).find('input').val());
							   var index = i + 1;
							   $(elem).find('input').attr('index', index);
							   $(elem).find('input').attr('placeholder', "Option " + index)
							   $(elem).find('.circle-grey').text(index);
						   });
					   }
					   else
					   {
						   input.val("");
					   }
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
	
	$('#survey-info').on("focusout", "#survey-info-title", function(e){
		e.stopPropagation();		
		var req = {};		
		req.text = $(this).val();
		req.contentType = "title";
		req.lan = "en";
		req.sid = $(this).closest('#survey-info').attr('sid');		
		var serviceUrl = host + "/SurveyTool/api/SurveyService/updateContent";
		
		updateContent(req, serviceUrl);
	});
	
	$('#survey-info').on("focusout", "#surveyDescription", function(e){
		e.stopPropagation();		
		var req = {};		
		req.text = $(this).val();
		req.contentType = "description";
		req.lan = "en";
		req.sid = $(this).closest('#survey-info').attr('sid');		
		var serviceUrl = host + "/SurveyTool/api/SurveyService/updateContent";
		
		updateContent(req, serviceUrl);
	});
	
	$('#survey-info').on("focusout", "#surveyProject", function(e){
		e.stopPropagation();		
		var req = {};		
		req.project = $(this).val();
		req.sid = $(this).closest('#survey-info').attr('sid');		
		var serviceUrl = host + "/SurveyTool/api/SurveyService/updateProject";
		
		updateContent(req, serviceUrl);
	});
	
	$('#panel-body').on("click", "#helpTextButton", function(e){
		currentQuestion = $(this).closest('div[id=panel-question1]').attr('qid');
		currentLanguage = $('#survey-language-version').val();
		console.log("current question: " + currentQuestion + " - language: " + currentLanguage);
	});
	
	$('#setHelpText').on("click", "#btnSendHelpText", function(e){
		e.stopPropagation();		
		var req = {};		
		req.text = $('#helpText').val();
		req.contentType = "helpText";
		req.lan = currentLanguage;
		req.qid = currentQuestion;	
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateContent",
			   data: JSON.stringify(req),
			   success: function (data) {
				   if(data == "true")
				   {
					   var qNode = $('div[qid=' + currentQuestion + ']');
					   qNode.find('#question-frame-help').removeClass("hidden");
					   qNode.find('#question-frame-help-text').html(req.text);
					   $("#setHelpText").modal("hide");
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
	
	$('#panel-body').on("click", "#mandatoryButton", function(e){
		e.stopPropagation();
		var node = $(this); 
		var req = {};
		req.qid = node.closest('div[id=panel-question1]').attr('qid');
		req.pid = node.closest('div[id=page]').attr('pid');
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateMandatory",
			   data: JSON.stringify(req),
			   success: function (data) {
				   console.log(data);
				   node.attr('active', data);
			   },
			   error: function (xhr, ajaxOptions, thrownError) {
				   console.log(xhr.status);
				   console.log(thrownError);
				   console.log(xhr.responseText);
				   console.log(xhr);
			   }
		});
	});
	
	$('#panel-body').on("focusout", "#survey-question-title", function(e){
		e.stopPropagation();
		var req = {};		
		req.text = $(this).val();
		req.contentType = "title";
		req.lan = "en";
		req.qid = $(this).closest('#panel-question1').attr('qid');		
		var serviceUrl = host + "/SurveyTool/api/QuestionService/updateContent";
		
		updateContent(req, serviceUrl);
	});

	$('#panel-body').on("focusout", "#survey-question-description-text", function(e){
		e.stopPropagation();
		var req = {};		
		req.text = $(this).val();
		req.contentType = "description";
		req.lan = "en";
		req.qid = $(this).closest('#panel-question1').attr('qid');		
		var serviceUrl = host + "/SurveyTool/api/QuestionService/updateContent";
		
		updateContent(req, serviceUrl);
	});	
	
});

function updateContent(req, serviceUrl)
{
	
	$.ajax({ 
	   type: "PUT",
	   dataType: "text",
	   contentType: "text/plain",
	   url: serviceUrl,
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
}

