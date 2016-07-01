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
		console.log("Clicking on type query button");
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
		$('#askAlways').val("false");
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
			askAlways: $('#askAlways').val(),
			helpText: $('#help-text').val(),
			surveyid: $('#surveyid').val(),
			pageid: $('#pageid1').val()
		}, function(responseText) {
			var index = responseText.indexOf("<html");
			if(index >= 0) {window.location.replace(host + "/SurveyTool/SurveysServlet");}
			else {currentAddNode.closest('li[id=page]').find('#page-items').append(responseText);}
		});
		$('#qstatement').val("");
		//$('#main-version').val("none");
		$('#mandatory').val("false");
		$('#askAlways').val("false");
		$('#help-text').val("false");
		$('#' + qtypeId + " i").css("background-color","#fff");
		$('#frame-basic-Settings').css('display', 'none');
	});
	
	$('#page').on("click", '#btn-question', function(){
		currentAddNode = $(this).parent().parent().parent();
		$("#newQuestionModal").modal("show");
	});
	
	$('#page-items').on("keyup", "#option-list #option-item input", function(e){
		e.stopPropagation();
		var index = $(this).attr('index');
		$(this).closest('li[id=panel-question1]').find('label[id=optionRadioLabel' + index + ']').text($(this).val());
		//console.log("Option text field!!!! " + $(this).parent().parent().children("li").size());
	});
	
	$('#page-items').on("keyup", "#optionmatrix-list #optionmatrix-item input", function(e){
		e.stopPropagation();
		var index = $(this).attr('index');
		$(this).closest('li[id=panel-question1]').find('label[id=optionRadioLabel' + index + ']').text($(this).val());
		//console.log("Option text field!!!! " + $(this).parent().parent().children("li").size());
	});
	
	$('#page-items').on("keyup", "#optionsgroupmatrix-list #optionsgroupmatrix-item input", function(e){
		e.stopPropagation();
		var index = $(this).attr('index');
		$(this).closest('li[id=panel-question1]').find('label[id=optionRadioLabel' + index + ']').text($(this).val());
		//console.log("Option text field!!!! " + $(this).parent().parent().children("li").size());
	});
	
	$('#page-items').on("focusout", "#option-list #option-item input", function(e){
		e.stopPropagation();
		//console.log("language: " + $('#survey-language-version').val());
		if($(this).val() != "")
		{
			console.log("TExt: " + $(this).val() + " - qid: " + $(this).attr('index') + " - qid: " + $(this).closest('li[id=panel-question1]').attr('qid') + " - ogid: " + $(this).closest('ul').attr('ogid'));
			var req = {};
			var currentNode = $(this);
			req.text = currentNode.val();
			req.oid = currentNode.attr('oid');
			req.index = currentNode.attr('index');
			req.qid = currentNode.closest('li[id=panel-question1]').attr('qid');
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
	

	
	$('#page-items').on("focusout", "#optionmatrix-list #optionmatrix-item input", function(e){
		e.stopPropagation();
		//console.log("language: " + $('#survey-language-version').val());
		if($(this).val() != "")
		{
			console.log("TExt: " + $(this).val() + " - qid: " + $(this).attr('index') + " - qid: " + $(this).closest('li[id=panel-question1]').attr('qid') + " - oid: " + $(this).closest('ul').attr('oid'));
			var req = {};
			var currentNode = $(this);
			req.text = currentNode.val();
			req.oid = currentNode.attr('oid');
			req.ogid = 0;
			req.index = currentNode.attr('index');
			req.qid = currentNode.closest('li[id=panel-question1]').attr('qid');
			req.lang = $('#survey-language-version').val();
			req.otype = currentNode.closest('ul').attr('otype');
			
			$.ajax({ 
			   type: "POST",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QCService/insertOptionMatrix",
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
					   
					   currentNode.closest('li').find('#remove-optionmatrix').attr('aria-label', 'Remove option: ' + req.text);
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
	

	
	$('#page-items').on("focusout", "#optionsgroupmatrix-list #optionsgroupmatrix-item input", function(e){
		e.stopPropagation();
		//console.log("language: " + $('#survey-language-version').val());
		//console.log("option lalala valor: " + $(this).val());
		if($(this).val() != "")
		{
			console.log("TExt (optionsGroupMatrix): " + $(this).val() + " - qid: " + $(this).attr('index') + " - qid: " + $(this).closest('li[id=panel-question1]').attr('qid') + " - ogid: " + $(this).attr('ogid'));
			var req = {};
			var currentNode = $(this);
			req.text = currentNode.val();
			req.index = currentNode.attr('index');
			req.qid = currentNode.closest('li[id=panel-question1]').attr('qid');
			req.ogid = currentNode.attr('ogid');
			req.oid = 0;
			req.lang = $('#survey-language-version').val();
			req.otype = currentNode.closest('ul').attr('otype');
			
			$.ajax({ 
			   type: "POST",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QCService/insertOptionsGroupMatrix",
			   data: JSON.stringify(req),
			   success: function (data) {
				   console.log("Dentro del function: "+data);
				   if(data != '')
				   {
					   var json = JSON.parse(data);
					   if(json.hasOwnProperty('ogid'))
					   {
						   console.log("hello ogid: " + json.ogid);
						   currentNode.attr('ogid', json.ogid);						   
					   }
					   
					   currentNode.closest('li').find('#remove-optionsgroupmatrix').attr('aria-label', 'Remove option: ' + req.text);
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
	
	
	$('#page-items').on("click", "#option-list #btn-add-option", function(e){
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
	
	
	$('#page-items').on("click", "#optionmatrix-list #btn-add-optionmatrix", function(e){
		var index = $(this).parent().parent().children("li").size();
		var optionHtml = '<li class="option-item" id="optionmatrix-item">' +
								//'<button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> ' +		
								'<div class="circle-info circle-grey fleft">' + index + '</div> ' + 
								'<input type="text" class="option-title form-control fleft" index="' + index + '" oid="0" placeholder="Option ' + index + '" autofocus/> ' +
								'<div class="option-icons fleft"> ' +
									//'<button class="btn btn-transparent fleft"><i class="fa fa-file-image-o fa-2x"></i></button> ' +
									//'<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> ' +
									'<button class="btn btn-transparent fleft red" id="remove-optionmatrix" aria-label="remove option"><i class="fa fa-trash fa-2x"></i></button> ' +
								'</div> ' +
							'</li>';
		$(this).parent().before(optionHtml);
		//$(this).closest('ul').find('input[index=' + index + ']').focus();
	});
	
	$('#page-items').on("click", "#optionsgroupmatrix-list #btn-add-optionsgroupmatrix", function(e){
		var index = $(this).parent().parent().children("li").size();
		var optionHtml = '<li class="option-item" id="optionsgroupmatrix-item">' +
								//'<button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> ' +		
								'<div class="circle-info circle-grey fleft">' + index + '</div> ' + 
								'<input type="text" class="option-title form-control fleft" index="' + index + '" ogid="0" placeholder="Option ' + index + '" autofocus/> ' +
								'<div class="option-icons fleft"> ' +
									//'<button class="btn btn-transparent fleft"><i class="fa fa-file-image-o fa-2x"></i></button> ' +
									//'<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> ' +
									'<button class="btn btn-transparent fleft red" id="remove-optionsgroupmatrix" aria-label="remove option"><i class="fa fa-trash fa-2x"></i></button> ' +
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
	              var multimediaFrame = $("li[qid=" + currentQuestion + "]").find("div[id=multimediaFrame]");
	              multimediaFrame.removeClass("hidden");
	              multimediaFrame.find("ul[id=multimediaFilesList]").append(res);		
	              $('#optionsFile').empty();
	              $('#optionsFile').addClass('hidden');
	              pending = false;
	  		});
		});
	});
	
	$('#page-items').on("click", "#btn-question-import-file", function(e){
		currentQuestion = $(this).closest('li[id=panel-question1]').attr('qid');
		currentLanguage = $('#survey-language-version').val();
		console.log("current question: " + currentQuestion + " - language: " + currentLanguage);
	});
		
	$('#page-items').on("change", "#type-matrix", function(e){
		e.stopPropagation();
		var node = $(this); 
		var req = {};
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = $(this).val();
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateParameters",
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

	$('#survey-sections').on("click", "#removeSection", function(e){
		var item = $(this).closest('#panel-section1');
		currentQuestion = item.attr('scid');
		$("#elementToRemoveText").html('"Question: ' + item.find('#survey-section-title').val() + '"');
		$("#removeElemId").val(item.attr('scid') + '/' + $('#survey-info').attr('sid'));
		$("#removeElemService").val('SectionService');
		$("#removeElement").modal("show");
	});
	
	$('#page-items').on("click", "#removeQuestion", function(e){
		var item = $(this).closest('#panel-question1');
		currentQuestion = item.attr('qid');
		$("#elementToRemoveText").html('"Question: ' + item.find('#survey-question-title').val() + '"');
		$("#removeElemId").val(item.attr('qid') + '/' + item.closest('li[id=page]').attr('pid'));
		$("#removeElemService").val('QuestionService');
		$("#removeElement").modal("show");
	});
	
	$('#page-items').on("click", "#removeMultimediaFile", function(e){
		var item = $(this).closest('li.multimedia-item');
		$("#elementToRemoveText").html('"' + item.find('a').text() + '"');
		$("#removeElemId").val(item.attr('rid'));
		$("#removeElemService").val('ResourceService');
		$("#removeElement").modal("show");
	});
	
	$('#page-items').on("click", "#remove-option", function(e){
		console.log("Remove option");
		currentQuestion = $(this).closest('#panel-question1').attr('qid');
		var item = $(this).closest('li');
		var input = item.find('input');
		$("#elementToRemoveText").html('"Option: ' + input.val() + '"');
		$("#removeElemId").val(input.attr('oid'));
		$("#removeElemService").val('OptionService');
		$("#removeElement").modal("show");
	});
	
	$('#page-items').on("click", "#remove-optionmatrix", function(e){
		console.log("Remove option matrix");
		currentQuestion = $(this).closest('#panel-question1').attr('qid');
		var item = $(this).closest('li');
		var input = item.find('input');
		$("#elementToRemoveText").html('"Option: ' + input.val() + '"');
		$("#removeElemId").val(input.attr('oid'));
		$("#removeElemService").val('OptionMatrixService');
		$("#removeElement").modal("show");
	});
	
	$('#page-items').on("click", "#remove-optionsgroupmatrix", function(e){
		console.log("Remove optionsgroup matrix");
		currentQuestion = $(this).closest('#panel-question1').attr('qid');
		var item = $(this).closest('li');
		var input = item.find('input');
		$("#elementToRemoveText").html('"Option: ' + input.val() + '"');
		$("#removeElemId").val(input.attr('ogid'));
		$("#removeElemService").val('OptionsGroupMatrixService');
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
				   }
				   else if(service == "QuestionService")
				   {
					   $('li[qid="' + currentQuestion + '"]').remove();
				   }
				   else if(service == "OptionService")
				   {
					   var input = $('input[oid=' + elementId + ']'); 					   
					   var numItems = input.closest("ul").find("li").size();
					   console.log("Items: " + numItems);
					   if(numItems > 3)
					   {
						   input.closest("li").remove();
						   $('li[qid=' + currentQuestion + '] ul[id=option-list] li[id=option-item]').each(function(i, elem)
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
				   else if(service == "OptionMatrixService")
				   {
					   var input = $('input[oid=' + elementId + ']'); 					   
					   var numItems = input.closest("ul").find("li").size();
					   console.log("Items: " + numItems);
					   if(numItems > 3)
					   {
						   input.closest("li").remove();
						   $('li[qid=' + currentQuestion + '] ul[id=optionmatrix-list] li[id=optionmatrix-item]').each(function(i, elem)
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
				   else if(service == "OptionsGroupMatrixService")
				   {
					   var input = $('input[ogid=' + elementId + ']'); 					   
					   var numItems = input.closest("ul").find("li").size();
					   console.log("Items: " + numItems);
					   if(numItems > 3)
					   {
						   input.closest("li").remove();
						   $('li[qid=' + currentQuestion + '] ul[id=optionsgroupmatrix-list] li[id=optionsgroupmatrix-item]').each(function(i, elem)
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
				   else if(service == "SectionService")
				   {
					   var ids = elementId.split('/');
					   $('li[scid=' + ids[0] + ']').find('ul[id=section-pages]').each(function(indice, elemento) {
						   if(indice == 0)
						   {
							   $(elemento).find('ul[id=page-items]').empty();
						   }
						   else
						   {
							   $(elemento).remove();
						   }
					   });
					   $('li[scid=' + ids[0] + ']').find('input[id=survey-section-title]').val('Section 1');
				   }
			   }
			   else
			   {
				   if(service == "SectionService")
				   {
					   var ids = elementId.split('/');
					   $('li[scid=' + ids[0] + ']').remove();
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
	
	$('#survey-sections').on("focusout", "#survey-section-title", function(e){
		e.stopPropagation();	
		var req = {};		
		req.text = $(this).val();
		req.contentType = "title";
		req.lan = "en";
		req.scid = $(this).closest('#panel-section1').attr('scid');		
		var serviceUrl = host + "/SurveyTool/api/SectionService/updateContent";
		
		updateContent(req, serviceUrl);
	});
	
	$('#page-items').on("click", "#helpTextButton", function(e){
		currentQuestion = $(this).closest('li[id=panel-question1]').attr('qid');
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
					   var qNode = $('li[qid=' + currentQuestion + ']');
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
	
	$('#page-items').on("click", "#mandatoryButton", function(e){
		console.log("mandatory");
		e.stopPropagation();
		var node = $(this); 
		var req = {};
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
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
	
	$('#page-items').on("click", "#askAlwaysButton", function(e){
		console.log("ask always");
		e.stopPropagation();
		var node = $(this); 
		var req = {};
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateOptionalAnswer",
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
	
	$('#page-items').on("focusout", "#survey-question-max-chars", function(e){
		e.stopPropagation();
		var node = $(this); 
		var req = {};
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = $(this).val();
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateTextLength",
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
	
	$('#page-items').on("focusout", "#survey-question-decimals", function(e){
		e.stopPropagation();
		var node = $(this); 
		var req = {};
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = $(this).val();
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateDecimals",
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
	
	$('#page-items').on("change", "#isLimitedChars", function(e){
		e.stopPropagation();
		console.log("isLimitedChars");
		var node = $(this); 
		var req = {};
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = "";
		console.log("node.checked: "+node.is(":checked"));
		if(node.is(":checked"))
			node.closest('div.question-response-settings').find('div.question-response-settings-sub').css('display', 'inherit');
		else
			node.closest('div.question-response-settings').find('div.question-response-settings-sub').css('display', 'none');
		
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateTextLength",
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
	
	$('#page-items').on("change", "#range", function(e){
		e.stopPropagation();
		console.log("range");
		var node = $(this); 
		var req = {};
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = "";
		console.log("node.checked: "+node.is(":checked"));
		if(node.is(":checked"))
			node.closest('div.question-response-settings').find('div.question-response-settings-sub').css('display', 'inherit');
		else
			node.closest('div.question-response-settings').find('div.question-response-settings-sub').css('display', 'none');
		
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateMinValue",
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
		
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateMaxValue",
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
	
	$('#page-items').on("change", "#allowDecimals", function(e){
		e.stopPropagation();
		console.log("allowDecimals");
		var node = $(this); 
		var req = {};
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = "";
		console.log("node.checked: "+node.is(":checked"));
		if(node.is(":checked"))
			node.closest('div.question-response-settings').find('div.question-response-settings-sub').css('display', 'inherit');
		else
			node.closest('div.question-response-settings').find('div.question-response-settings-sub').css('display', 'none');
		
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateDecimals",
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
	
	$('#page-items').on("change", "#input-type-free", function(e){
		e.stopPropagation();
		console.log("change in input type");
		var node = $(this);
		var reqF = {};
		reqF.qid = node.closest('li[id=panel-question1]').attr('qid');
		reqF.pid = node.closest('li[id=page]').attr('pid');
		reqF.inputMode = node.closest('div.row').find('#input-mode').val();
		reqF.text = node.val();
		
		if(node.val()==="formFieldTypeNumber"){
			console.log("isNumber");
			node.closest('div.row').find('#input-type-free').val('formFieldTypeGeneral');
			node.closest('div.row').find('#inputTypeFree').css('display', 'inherit');
			node.closest('div.row').find('#inputTypeFree').css('display', 'inline-block');
			node.closest('div.row').find('#inputTypePullDown').css('display', 'none');
			node.closest('div.row').find('#inputOptionsPullDown').css('display', 'none');
			node.closest('div.row').find('#inputOptionsFree').css('display', 'inherit');
			node.closest('div.row').find('#inputOptionsFree').css('display', 'inline-block');
			node.closest('div.question-frame').find('#inputOptionsCustom').css('display', 'none');
			
			node.closest('div.row').find('#rangeOptions').css('display', 'inherit');
			node.closest('div.row').find('#rangeOptions').css('display', 'inline-block');
			node.closest('div.row').find('#decimalsOptions').css('display', 'inherit');
			node.closest('div.row').find('#decimalsOptions').css('display', 'inline-block');
		}else{
			console.log("isNotNumber");
			console.log("isNumber");
			node.closest('div.row').find('#input-type-free').val('formFieldTypeGeneral');
			node.closest('div.row').find('#inputTypeFree').css('display', 'inherit');
			node.closest('div.row').find('#inputTypeFree').css('display', 'inline-block');
			node.closest('div.row').find('#inputTypePullDown').css('display', 'none');
			node.closest('div.row').find('#inputOptionsPullDown').css('display', 'none');
			node.closest('div.row').find('#inputOptionsFree').css('display', 'inherit');
			node.closest('div.row').find('#inputOptionsFree').css('display', 'inline-block');
			node.closest('div.question-frame').find('#inputOptionsCustom').css('display', 'none');
			
			node.closest('div.row').find('#rangeOptions').css('display', 'none');
			node.closest('div.row').find('#decimalsOptions').css('display', 'none');
		}
		
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateInputTypeMode",
			   data: JSON.stringify(reqF),
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
	
	$('#page-items').on("change", "#input-type-pulldown", function(e){
		e.stopPropagation();
		console.log("change in input type");
		var node = $(this);
		var reqF = {};
		reqF.qid = node.closest('li[id=panel-question1]').attr('qid');
		reqF.pid = node.closest('li[id=page]').attr('pid');
		reqF.inputMode = node.closest('div.row').find('#input-mode').val();
		reqF.text = node.val();
		
		if(node.val()==="formFieldRange"){
			node.closest('div.row').find('#inputTypePullDown').css('display', 'inherit');
			node.closest('div.row').find('#inputTypePullDown').css('display', 'inline-block');
			node.closest('div.row').find('#inputTypeFree').css('display', 'none');
			node.closest('div.row').find('#inputOptionsFree').css('display', 'none');
			node.closest('div.row').find('#inputOptionsPullDown').css('display', 'inherit');
			node.closest('div.row').find('#inputOptionsPullDown').css('display', 'inline-block');
			node.closest('div.question-frame').find('#inputOptionsCustom').css('display', 'none');
		}else{
			node.closest('div.row').find('#inputTypePullDown').css('display', 'inherit');
			node.closest('div.row').find('#inputTypePullDown').css('display', 'inline-block');
			node.closest('div.row').find('#inputTypeFree').css('display', 'none');
			node.closest('div.row').find('#inputOptionsFree').css('display', 'none');
			node.closest('div.row').find('#inputOptionsPullDown').css('display', 'none');
			node.closest('div.question-frame').find('#inputOptionsCustom').css('display', 'inherit');
			node.closest('div.question-frame').find('#inputOptionsCustom').css('display', 'inline-block');
		}
		
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateInputTypeMode",
			   data: JSON.stringify(reqF),
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
	
	$('#page-items').on("change", "#input-mode", function(e){
		e.stopPropagation();
		console.log("change in input type");
		var node = $(this);
		var reqF = {};
		reqF.qid = node.closest('li[id=panel-question1]').attr('qid');
		reqF.pid = node.closest('li[id=page]').attr('pid');
		reqF.inputMode = node.closest('div.row').find('#input-mode').val();		
		
		if(reqF.inputMode==="formFieldInputModeFree"){
			reqF.text = "formFieldTypeGeneral";
			node.closest('div.row').find('#input-type-free').val('formFieldTypeGeneral');
			node.closest('div.row').find('#inputTypeFree').css('display', 'inherit');
			node.closest('div.row').find('#inputTypeFree').css('display', 'inline-block');
			node.closest('div.row').find('#inputTypePullDown').css('display', 'none');
			node.closest('div.row').find('#inputOptionsPullDown').css('display', 'none');
			node.closest('div.row').find('#inputOptionsFree').css('display', 'inherit');
			node.closest('div.row').find('#inputOptionsFree').css('display', 'inline-block');
			node.closest('div.question-frame').find('#inputOptionsCustom').css('display', 'none');
		}
		else{
			reqF.text = "formFieldRange";
			node.closest('div.row').find('#input-type-pulldown').val('formFieldRange');
			node.closest('div.row').find('#inputTypePullDown').css('display', 'inherit');
			node.closest('div.row').find('#inputTypePullDown').css('display', 'inline-block');
			node.closest('div.row').find('#inputTypeFree').css('display', 'none');
			node.closest('div.row').find('#inputOptionsFree').css('display', 'none');
			node.closest('div.row').find('#inputOptionsPullDown').css('display', 'inherit');
			node.closest('div.row').find('#inputOptionsPullDown').css('display', 'inline-block');
			node.closest('div.question-frame').find('#inputOptionsCustom').css('display', 'none');
		}
		
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateInputTypeMode",
			   data: JSON.stringify(reqF),
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
	
	$('#page-items').on("focusout", "#survey-question-max-lines", function(e){
		e.stopPropagation();
		var node = $(this); 
		var req = {};
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = $(this).val();
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateTextLines",
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
	
	$('#page-items').on("change", "#adjust-lines-adjust", function(e){
		e.stopPropagation();
		var node = $(this); 
		var req = {};
		console.log("OnClick on adjust-lines-adjust");
		node.closest('div.row').find('div.question-response-settings-sub').css('display', 'none');
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = "";
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateTextLines",
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
	
	$('#page-items').on("change", "#adjust-lines-set", function(e){
		e.stopPropagation();
		var node = $(this); 
		var req = {};
		console.log("OnClick on adjust-lines-set");
		node.closest('div.question-response-settings').find('div.question-response-settings-sub').css('display', 'inherit');
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = "";
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateTextLines",
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
	
	$('#page-items').on("focusout", "#survey-minValue", function(e){
		e.stopPropagation();
		var node = $(this); 
		var req = {};
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = $(this).val();
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateMinValue",
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
	
	$('#page-items').on("focusout", "#survey-minValue-pull", function(e){
		e.stopPropagation();
		var node = $(this); 
		var req = {};
		
		String maxValue =  
		req.qid = node.closest('div.question-response-settings-sub').find('#survey-maxValue-pull').;
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = $(this).val();
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateMinValue",
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
	
	$('#page-items').on("focusout", "#survey-maxValue", function(e){
		e.stopPropagation();
		var node = $(this); 
		var req = {};
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = $(this).val();
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + "/SurveyTool/api/QuestionService/updateMaxValue",
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
	
	$('#page-items').on("focusout", "#survey-question-title", function(e){
		e.stopPropagation();
		var req = {};		
		req.text = $(this).val();
		req.contentType = "title";
		req.lan = "en";
		req.qid = $(this).closest('#panel-question1').attr('qid');		
		var serviceUrl = host + "/SurveyTool/api/QuestionService/updateContent";
		
		updateContent(req, serviceUrl);
	});

	$('#page-items').on("focusout", "#survey-question-description-text", function(e){
		e.stopPropagation();
		var req = {};		
		req.text = $(this).val();
		req.contentType = "description";
		req.lan = "en";
		req.qid = $(this).closest('#panel-question1').attr('qid');		
		var serviceUrl = host + "/SurveyTool/api/QuestionService/updateContent";
		
		updateContent(req, serviceUrl);
	});	
	
	$('#page-items').on("focusout", "#survey-question-other-text", function(e){
		e.stopPropagation();
		var req = {};		
		req.text = $(this).val();
		req.contentType = "other";
		req.lan = "en";
		req.qid = $(this).closest('#panel-question1').attr('qid');		
		var serviceUrl = host + "/SurveyTool/api/QuestionService/updateContent";
		
		updateContent(req, serviceUrl);
	});		
	
	//drag and drop
	$("#page-items").sortable({
		connectWith:".s1",
		start:function(){
			console.log("Estas utilizando Drag and Drop: " + $(this).attr('id'));
		},
		stop:function(event, ui){
			var prevId = '0';
			if($(ui.item).prev().length) prevId = $(ui.item).prev().attr('qid');
	
			var req = {};		
			req.qid = $(ui.item).attr('qid');
			req.prevId = prevId;
			req.pid = $(this).closest('li[id=page]').attr('pid');
			
			$.ajax({ 
				   type: "PUT",
				   dataType: "text",
				   contentType: "text/plain",
				   url: host + "/SurveyTool/api/QuestionService/updateIndex",
				   data: JSON.stringify(req),
				   success: function (data) {
					   if(data == "")
					   {
						   console.log("llega");
						  /* var qNode = $('li[qid=' + currentQuestion + ']');
						   qNode.find('#question-frame-help').removeClass("hidden");
						   qNode.find('#question-frame-help-text').html(req.text);
						   $("#setHelpText").modal("hide");*/
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

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}


