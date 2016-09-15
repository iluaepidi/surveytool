/**
 * 
 */
var qtypeId;
var numQuestions = 0;
var currentElement;
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
		var valid = true;
		//check de name project
		if($('#qstatement').val() == ""){
			valid = false;
			showFieldError($('#qstatement'));
		}else{
			hideFieldError($('#qstatement'));
			console.log("num page: " + currentElement.attr('index'));
			// Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
			var request = {
					qtype : $('#qtypevalue').val(),
					qstatement: $('#qstatement').val(),
					mandatory: $('#mandatory').val(),
					helpText: $('#help-text').val(),
					surveyid: $('#surveyid').val(),
					pageid: currentElement.attr('pid'),
					numPage: currentElement.attr('index'),
					langsurvey : $("#survey-language-version").val()
				};
			$.post('CreateQuestionServlet', request, function(responseText) {
				var index = responseText.indexOf("<html");
				if(index >= 0) {window.location.replace(host + "/SurveyTool/SurveysServlet");}
				else {
					var pageItems = currentElement.find('ul.page-items'); 
					pageItems.append(responseText);
					var question = pageItems.find('li.panel-question').last()
					//console.log("Question created: " + pageItems.find('li.panel-question').last().attr('index'));
					question.find('input.survey-question-title').trigger("createQuestionJson", [request.qtype, request.pageid, request.qstatement, question.attr('qid'), question.attr('index')]);
				}
			});
			console.log("after post");
			$('#qstatement').val("");
			//$('#main-version').val("none");
			$('#mandatory').val("false");
			$('#help-text').val("false");
			$('#' + qtypeId + " i").css("background-color","#fff");
			$('#frame-basic-Settings').css('display', 'none');
			
			//$('#modal').modal('hide');
			//$('#newQuestionModal').hide();
			$('#newQuestionModal').modal('toggle');
		}
	});
	
	$('.survey-sections').on("click", '.btn-question', function(){
		currentElement = $(this).closest('li.page');
		$("#newQuestionModal").modal("show");
	});
	
	$('.survey-sections').on("click", ".btn-page-break", function(){
		var node = $(this);
		var req = {};
		req.scid = node.closest('.panel-section').attr('scid');
		req.sid = node.closest('div.edit-content-center').find('div.survey-info').attr('sid');
		req.numPage = node.closest('li.page').attr('index');
		
		$.post('CreatePageServlet', req, function(responseText) {
			//console.log("Create page sectionId: " + node.closest('ul.section-pages').html());
			node.closest('li.page').after(responseText);
			node.closest('ul.section-pages').find('li.page').each(function(indice, elemento) {
				var cads = $(elemento).find('h4').html().split(' ');
				$(elemento).attr('index', indice + 1);
				$(elemento).find('h4').html(cads[0] + " " + (indice + 1));
			});
		});
	});
	
	$('#page-items').on("click", '#editFile', function(){
		currentElement = JSON.parse($(this).attr("data-image"));
		console.log("editfile opening... " + JSON.stringify(currentElement));
		$("#updateFile").attr("rid", currentElement.rId);
		if(currentElement.rType === 'image')
		{
			console.log("Image");
			$("#previewImage").removeClass("hidden");
			$("#imageFilePreview").attr("src", currentElement.path);
			$("#resourceAltText").removeClass("hidden");
			$("#rAltText").val(currentElement.altText);
			$("#previewVideo").addClass("hidden");
			$("#resDescText").addClass("hidden");
		}
		else if(currentElement.rType === 'video')
		{
			console.log("Video");
			$("#previewVideo").removeClass("hidden");
			$("#resDescText").removeClass("hidden");
			$("#reproductor_preview").attr("src", "https://www.youtube.com/embed/" + currentElement.path + "?enablejsapi=1");
			$("#reproductor_preview").attr("data-title", currentElement.tittle);
			$("#rDescText").val(currentElement.descText);
			$("#previewImage").addClass("hidden");
			$("#resourceAltText").addClass("hidden");
		}		
		$("#rTitle").val(currentElement.tittle);				
		$("#updateFile").modal("show");
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
	
	$('.survey-sections').on("focusout", "#option-list #option-item input", function(e){
		e.stopPropagation();
		//console.log("language: " + $('#survey-language-version').val());
		if($(this).val() != "")
		{
			//console.log("TExt: " + $(this).val() + " - qid: " + $(this).attr('index') + " - qid: " + $(this).closest('li[id=panel-question1]').attr('qid') + " - ogid: " + $(this).closest('ul').attr('ogid'));
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
					   
					   currentNode.trigger("goto");
					   currentNode.trigger("setJson");
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
	

	
	$('.survey-sections').on("focusout", "#optionmatrix-list #optionmatrix-item input", function(e){
		e.stopPropagation();
		//console.log("option matrix language: " + $('#survey-language-version').val());
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
	

	
	$('.survey-sections').on("focusout", "#optionsgroupmatrix-list #optionsgroupmatrix-item input", function(e){
		e.stopPropagation();
		//console.log("language: " + $('#survey-language-version').val());
		//console.log("options group matrix lalala valor: " + $(this).val());
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
	
	
	$('.survey-sections').on("click", "#option-list #btn-add-option", function(e){
		var index = $(this).parent().parent().children("li").size();
		var optionHtml = '<li class="option-item" id="option-item">' +
								//'<button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> ' +		
								'<div class="circle-info circle-grey fleft">' + index + '</div> ' + 
								'<input type="text" class="option-title form-control fleft" index="' + index + '" oid="0" placeholder="'+textOption+' ' + index + '" autofocus/> ' +
								'<div class="option-icons fleft"> ' +
									//'<button class="btn btn-transparent fleft"><i class="fa fa-file-image-o fa-2x"></i></button> ' +
									//'<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> ' +
									'<button class="btn btn-transparent fleft red" id="remove-option" aria-label="remove option"><i class="fa fa-trash fa-2x"></i></button> ' +
								'</div> ' +
							'</li>';
		$(this).parent().before(optionHtml);
		//$(this).closest('ul').find('input[index=' + index + ']').focus();
	});
	
	
	$('.survey-sections').on("click", "#optionmatrix-list #btn-add-optionmatrix", function(e){
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
	
	$('.survey-sections').on("click", "#optionsgroupmatrix-list #btn-add-optionsgroupmatrix", function(e){
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
	
	$('#importFile').on('hidden.bs.modal', function () {
	    //alert("close");
	})
	
	$('#importFileForm').on("click", "#btnImportFile", function(e){
		$('#importFileForm').on("submit", function(e){
			console.log("en on(click, #btnImportFile");
			e.preventDefault();
			if(pending)
			{
				return;
			}
			pending = true;

			var type = $('#fileType').val();
			//alert("resource: " + type);
			var req = {};
			req.resourceTitle = $('#resourceTitle').val();
			req.mainVersion= currentLanguage;
			req.resourceType = type;
			
			if(type === "image")
			{
				req.action = "options";
				req.resourceAltText = $('#resourceAltText').val();
				req.rid = $('#rid').val();
			}
			else if(type === "video")
			{
				req.action = type;
				req.resourceDescText = $('#resourceDescText').val();
				req.resourceUrl = $('#resourceUrl').val();
				req.qid = currentQuestion;
			}
			
	        $.post('ImportFileServlet', req, function(res) {
	  			$('#importFileForm')[0].reset();
	              $("#importFile").modal("hide");
	              var multimediaFrame = $("li[qid=" + currentQuestion + "]").find("div[id=multimediaFrame]");
	              multimediaFrame.removeClass("hidden");
	              multimediaFrame.find("div.question-files-frame").removeClass("hidden");
	              multimediaFrame.find("ul[id=multimediaFilesList]").append(res);		
	              $('#optionsFile').empty();
	              $('#optionsFile').addClass('hidden');
	              $('#selectFile').addClass("hidden");
	              $('#optionsVideoFile').addClass("hidden");
	              pending = false;
	  		});
		});
	});
	
	$('#selectFiteType').on("change", "#fileType", function(e){
		var type = $(this).val();
		if(type === "video")
		{
			$('#selectFile').addClass("hidden");
			$('#optionsVideoFile').removeClass("hidden");
		}
		else
		{
			$('#selectFile').removeClass("hidden");
			$('#optionsVideoFile').addClass("hidden");
		}
	});
	
	$('#updateFilesSection').on("click", "#btnUpdateFile", function(e){
		e.stopPropagation();		
		var req = {};		
		req.contents = [];

		var content = {};
		
		var changedTitle = '';
		if($('#rTitle').val() != currentElement.tittle)
		{
			changedTitle = $('#rTitle').val();
			content.contentType = "title";
			content.text = changedTitle;
			content.lan = currentLanguage;
			currentElement.tittle = content.text;
			req.contents.push(content);
		}
		
		if(currentElement.rType === "image" && $('#rAltText').val() != currentElement.altText)
		{
			content = {};
			content.contentType = "altText";
			content.text = $('#rAltText').val();
			content.lan = currentLanguage;
			currentElement.altText = content.text
			req.contents.push(content);
		}
		else if(currentElement.rType === "video" && $('#rDescText').val() != currentElement.descText)
		{
			content = {};
			content.contentType = "description";
			content.text = $('#rDescText').val();
			content.lan = currentLanguage;
			currentElement.descText = content.text
			req.contents.push(content);
		}
		
		if(req.contents.length > 0)
		{
			req.type = currentElement.rType;
			req.rid = currentElement.rId;
			
			var serviceUrl = host + "/SurveyTool/api/ResourceService/updateContent";
			
			console.log("Resource update content: " + JSON.stringify(req));
			
			updateContent(req, serviceUrl);
			
			var multimediaElem = $('li[rid=' + currentElement.rId + ']').find('#editFile');
			multimediaElem.attr('data-image', JSON.stringify(currentElement));
			
			if(changedTitle != '')
			{
				var fileName = multimediaElem.html().split('-')[1];
				multimediaElem.html(changedTitle + " - " + fileName);
			}
			
			
		}		
		
		$("#previewVideo").addClass("hidden");
		$("#resDescText").addClass("hidden");
		$("#previewImage").addClass("hidden");
		$("#resourceAltText").addClass("hidden");					
		$("#updateFile").modal("hide");
		
	});
	
	$('#updateFilesSection').on("click", "#btnCancelUpdateFile", function(e){
		$("#previewVideo").addClass("hidden");
		$("#resDescText").addClass("hidden");
		$("#previewImage").addClass("hidden");
		$("#resourceAltText").addClass("hidden");					
		$("#updateFile").modal("hide");		
	});
	
	/*$('#updateFilesSection').on("click", "#btnUpdateFile", function(e){
			console.log("en on(click, #btnUpdateFile");
			e.preventDefault();
			if(pending)
			{
				return;
			}
			pending = true;
			console.log( $('#resourceTitle').val());
			console.log($('#resourceAltText').val());
			console.log(currentLanguage);
			console.log($(this).closest('.form-group').attr('id'));
	        $.post('ImportFileServlet', {
	        	action : "options",
	        	resourceTitle: $('#resourceTitle').val(),
	  			resourceAltText: $('#resourceAltText').val(),
	  			mainVersion: currentLanguage,
	  			rid: $(this).attr('rid').val()
	  		}, function(res) {
	  			$('#updateFileForm')[0].reset();
	              $("#updateFile").modal("hide");
	              var multimediaFrame = $("li[qid=" + currentQuestion + "]").find("div[id=multimediaFrame]");
	              multimediaFrame.removeClass("hidden");
	              multimediaFrame.find("div.question-files-frame").removeClass("hidden");
	              multimediaFrame.find("ul[id=multimediaFilesList]").append(res);		
	              $('#updateFilesSection').empty();
	              $('#updateFilesSection').addClass('hidden');
	              pending = false;
	  		});
	});*/
	
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
				   if(data == 'Multiple')
				   {			
					   node.closest('div.question-frame').find('ul').attr('otype', 'checkbox');
					   //node.closest('div.question-frame').find('#optionsgroupmatrix-list').attr('otype', 'checkbox');
				   }
				   else
				   {
					   node.closest('div.question-frame').find('ul').attr('otype', 'radio');
					   //node.closest('div.question-frame').find('#optionsgroupmatrix-list').attr('otype', 'radio');
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

	$('.survey-sections').on("click", ".remove-page-break", function(){
		var item = $(this).closest('li.page');
		currentElement = item;
		currentQuestion = item.attr('pid');
		$("#elementToRemoveText").html('"Page-break: ' + item.find('.page-head h4').html() + '"');
		$("#removeElemId").val(currentQuestion + '/' + $('#survey-info').attr('sid'));
		$("#removeElemService").val('PageService');
		$("#removeElement").modal("show");
	});
	
	$('#survey-sections').on("click", "#removeSection", function(e){
		var item = $(this).closest('#panel-section1');
		currentQuestion = item.attr('scid');
		$("#elementToRemoveText").html('"Question: ' + item.find('#survey-section-title').val() + '"');
		$("#removeElemId").val(item.attr('scid') + '/' + $('#survey-info').attr('sid'));
		$("#removeElemService").val('SectionService');
		$("#removeElement").modal("show");
	});
	
	$('.survey-sections').on("click", "#removeQuestion", function(e){
		var item = $(this).closest('#panel-question1');
		currentQuestion = item.attr('qid');
		$("#elementToRemoveText").html('"Question: ' + item.find('#survey-question-title').val() + '"');
		$("#removeElemId").val(item.attr('qid') + '/' + item.closest('li[id=page]').attr('pid'));
		$("#removeElemService").val('QuestionService');
		$("#removeElement").modal("show");
	});
	
	$('.section-pages').on("click", "#removeMultimediaFile", function(e){
		var item = $(this).closest('li.multimedia-item');
		$("#elementToRemoveText").html('"' + item.find('a').text() + '"');
		$("#removeElemId").val(item.attr('rid'));
		$("#removeElemService").val('ResourceService');
		$("#removeElement").modal("show");
	});
	
	$('.section-pages').on("click", "#remove-option", function(e){
		console.log("Remove option");
		currentQuestion = $(this).closest('#panel-question1').attr('qid');		
		var item = $(this).closest('li');
		var ogid = item.closest('ul').attr('ogid');
		var input = item.find('input');
		var oid = input.attr('oid');
		var result = currentQuestion + "/" + ogid + "/" + oid;
		console.log("result: " + result);
		$("#elementToRemoveText").html('"Option: ' + input.val() + '"');
		$("#removeElemId").val(result);
		$("#removeElemService").val('OptionService');
		$("#removeElement").modal("show");		
	});
	
	$('.section-pages').on("click", "#remove-optionmatrix", function(e){
		console.log("Remove option matrix");
		currentQuestion = $(this).closest('#panel-question1').attr('qid');
		var item = $(this).closest('li');
		var input = item.find('input');
		$("#elementToRemoveText").html('"Option: ' + input.val() + '"');
		$("#removeElemId").val(input.attr('oid'));
		$("#removeElemService").val('OptionMatrixService');
		$("#removeElement").modal("show");
	});
	
	$('.section-pages').on("click", "#remove-optionsgroupmatrix", function(e){
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
		//console.log("Resource ID: " + elementId+", service: "+service);
		
		//console.log("number items: " + $('li[rid=' + resourceId + ']').closest("ul").find("li").size());
		
		$.ajax({ 
		   url: host + "/SurveyTool/api/" + service + "/" + elementId,
		   type: "DELETE",
		   success: function (data) {
			   //console.log("data: "+data+", service: "+service);
			   if(data == 'true')
			   {
				   $("#removeElement").modal("hide");
				   if(service == "ResourceService")
				   {
					   if($('#multimediaFilesList li').length<2){
						   $('li[rid=' + elementId + ']').closest('div.question-files-frame').addClass('hidden'); 
					   }
					   $('li[rid=' + elementId + ']').remove();
					   //console.log("Number of elements: "+$('#multimediaFilesList li').length);
					   
				   }
				   else if(service == "QuestionService")
				   {
					   $('li[qid="' + currentQuestion + '"]').trigger('rmvQuestionJson');
					   $('li[qid="' + currentQuestion + '"]').remove();
				   }
				   else if(service == "OptionService")
				   {   
					   var ids = elementId.split('/');
					   var oid = ids[2];
					   var input = $('input[oid=' + oid + ']'); 					   
					   var numItems = input.closest("ul").find("li").size();
					   
					   input.trigger("rmvOptJson");
						
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
						   input.attr('oid', '0');
					   }
					   
					   var logicOptionElement = $('#logic-option-' + oid);
					   removeLogicElement(logicOptionElement);					   
				   }
				   else if(service == "OptionMatrixService")
				   {
					   var input = $('input[oid=' + elementId + ']'); 					   
					   var numItems = input.closest("ul").find("li").size();
					   //console.log("Items: " + numItems);
					   if(numItems > 3)
					   {
						   input.closest("li").remove();
						   $('li[qid=' + currentQuestion + '] ul[id=optionmatrix-list] li[id=optionmatrix-item]').each(function(i, elem)
						   {
							   //console.log("li: " + i + " - elem: " + $(elem).find('input').val());
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
					   //console.log("Items: " + numItems);
					   if(numItems > 3)
					   {
						   input.closest("li").remove();
						   $('li[qid=' + currentQuestion + '] ul[id=optionsgroupmatrix-list] li[id=optionsgroupmatrix-item]').each(function(i, elem)
						   {
							   //console.log("li: " + i + " - elem: " + $(elem).find('input').val());
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
				   else if(service == "PageService")
				   {
					   var pagesList = currentElement.closest('ul.section-pages');
					   var prevElement = currentElement.prev();
					   
					   var currentQuestionList = currentElement.find('ul.page-items');
					   var prevQuestionList = prevElement.find('ul.page-items');
					   var index = prevQuestionList.find('li.panel-question').length + 1;
					   currentQuestionList.find('li.panel-question').each(function(indice, elemento) {
						   $(elemento).attr('index', index);
						   prevQuestionList.append($(elemento));
						   index++;
						});
						   
					   currentElement.remove();
					   
					   pagesList.find('li.page').each(function(indice, elemento) {
							var cads = $(elemento).find('h4').html().split(' ');
							$(elemento).attr('index', indice + 1);
							$(elemento).find('h4').html(cads[0] + " " + (indice + 1));
						});
				   }
				   else if (service == "QCService"){
					   if(elementId.split("/").length==3){
						   //console.log("Close dependences was clicked");
						   
						   var question = $('li.panel-question[qid=' + elementId.split("/")[1] + ']');
						   
						   var depTemp = question.find('ul.dependences-list').find("li:first-child").clone();
						   
						   question.find('ul.dependences-list').empty();						   
						   //question.find('ul.dependences-list').closest("div.dependences-frame").find("button.btn-close-dependences").addClass("hidden");
						   question.find('ul.dependences-list').append(depTemp);
					   }
					   else if(elementId.split("/")[0]===("AllLogic")){
						   //console.log("Close logicGotTo was clicked");
						   var question = $('li.panel-question[qid=' + elementId.split("/")[1] + ']');
						   //console.log("qid="+question.attr("qid"));
						   
						   question.find('ul.logic-option-list').find('li.logic-option').each(function() {
							   var depTemp = $(this).find('select.logic-option-goto').find('option.default-option');
							   //console.log("select: "+depTemp.attr("id")+", this.class="+$(this).attr("class"));
							   $(this).find('select.logic-option-goto').empty();
							   $(this).find('select.logic-option-goto').append(depTemp);
							});
						   
						   
						   question.find('div.logic-frame').find('div.logic-button').removeClass('hidden');
						   question.find('div.logic-frame').find('div.logic-settings').addClass('hidden');
						   question.find('div.logic-frame').find('button.btn-close-aspa').addClass('hidden');
					   }
					   else{
						  
						  var question = $('li.panel-question[qid=' + elementId.split("/")[0] + ']');
						  var item = question.find('ul.dependences-list').find('[index=' + elementId.split("/")[1] + ']');
						  var numItems = item.closest("ul.dependences-list").find("li.dependence-item").length;
						  //console.log("Removed:"+question.attr("qid")+", item="+item.attr("index"));
						  
						  if(numItems <= 2)
						  {
							  console.log("único item a borrar");
							  //item.closest("div.dependences-frame").find("button.btn-close-dependences").addClass("hidden");
							  item.remove();
						  }
						  else if (numItems == 3)
						  {
							  if(item.closest("ul").find("li:nth-child(2)").attr("index") == elementId.split("/")[1]){
								  (item.closest("ul").find("li:nth-child(3)")).find("select.dependence-condition").addClass("hidden");
								  (item.closest("ul").find("li:nth-child(3)")).find("label.dependence-question-label").removeClass("hidden");							  
								  (item.closest("ul").find("li:nth-child(3)")).find("label.next-dependence-question-label").addClass("hidden");
								  (item.closest("ul").find("li:nth-child(3)")).find("#fieldset-dependence").removeClass("fieldset-second-dependence");
							  }
							  item.remove();
						  }
						  else if (numItems > 3)
						  {
							  if(item.closest("ul").find("li:nth-child(2)").attr("index") == elementId.split("/")[1]){
								  (item.closest("ul").find("li:nth-child(3)")).find("select.dependence-condition").addClass("hidden");
								  (item.closest("ul").find("li:nth-child(3)")).find("label.dependence-question-label").removeClass("hidden");
								  (item.closest("ul").find("li:nth-child(3)")).find("label.next-dependence-question-label").addClass("hidden");
								  (item.closest("ul").find("li:nth-child(3)")).find("#fieldset-dependence").removeClass("fieldset-second-dependence");	
								  
								  (item.closest("ul").find("li:nth-child(4)")).find("select.dependence-condition").removeClass("hidden");
								  (item.closest("ul").find("li:nth-child(4)")).find("#fieldset-dependence").removeClass("fieldset-next-dependences");
								  (item.closest("ul").find("li:nth-child(4)")).find("#fieldset-dependence").addClass("fieldset-second-dependence");
							  }
							  else if(item.closest("ul").find("li:nth-child(3)").attr("index") == elementId.split("/")[1]){
								  console.log("Tercer hijo");
								  (item.closest("ul").find("li:nth-child(4)")).find("select.dependence-condition").removeClass("hidden");
								  (item.closest("ul").find("li:nth-child(4)")).find("#fieldset-dependence").removeClass("fieldset-next-dependences");
								  (item.closest("ul").find("li:nth-child(4)")).find("#fieldset-dependence").addClass("fieldset-second-dependence");
							  }
							  item.remove();
						  }
					   }
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
		req.lan = $("#survey-language-version").val();
		req.sid = $(this).closest('#survey-info').attr('sid');		
		var serviceUrl = host + "/SurveyTool/api/SurveyService/updateContent";
		
		//check de title
		var valid = true;
		if(req.text == ""){
			valid = false;
			showFieldError($('#survey-info-title'));
		}else{
			hideFieldError($('#survey-info-title'));
			updateContent(req, serviceUrl);
		}
		
	});
	
	$('#survey-info').on("focusout", "#surveyDescription", function(e){
		e.stopPropagation();		
		var req = {};		
		req.text = $(this).val();
		req.contentType = "description";
		req.lan = $("#survey-language-version").val();
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
		
		var valid = true;
		//check de name project
		if(req.project == ""){
			valid = false;
			showFieldError($('#surveyProject'));
		}else{
			hideFieldError($('#surveyProject'));
			updateContent(req, serviceUrl);
		}
		
	});
	
	$('#survey-sections').on("focusout", "#survey-section-title", function(e){
		e.stopPropagation();	
		var req = {};		
		req.text = $(this).val();
		req.contentType = "title";
		req.lan = $("#survey-language-version").val();
		req.scid = $(this).closest('#panel-section1').attr('scid');		
		var serviceUrl = host + "/SurveyTool/api/SectionService/updateContent";
		
		//check de section title
		var valid = true;
		if(req.text == ""){
			valid = false;
			showFieldError($('#survey-section-title'));
		}else{
			hideFieldError($('#survey-section-title'));
			updateContent(req, serviceUrl);
		}
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
		if(node.is(":checked")){
			node.closest('div.question-response-settings').find('#charsId').attr('class','question-response-settings-sub-inherit');
		}else{
			console.log("Está deseleccionado");
			node.closest('div.question-response-settings').find('#charsId').attr('class','question-response-settings-sub-none');
		}
		node.closest('#genericOptions').find('#survey-question-max-chars').val('');
		
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
		if(node.is(":checked")){
			node.closest('div.question-response-settings').find('#rangeId').attr('class','question-response-settings-sub-inherit');
		}else{
			console.log("Está deseleccionado");
			node.closest('div.question-response-settings').find('#rangeId').attr('class','question-response-settings-sub-none');
		}
		node.closest('#rangeOptions').find('#survey-minValue').val('');
		node.closest('#rangeOptions').find('#survey-maxValue').val('');
		
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
		if(node.is(":checked")){
			node.closest('div.question-response-settings').find('#decimalsDiv').attr('class','question-response-settings-sub-inherit');
		}else{
			console.log("Está deseleccionado");
			node.closest('div.question-response-settings').find('#decimalsDiv').attr('class','question-response-settings-sub-none');
		}
		node.closest('#decimalsOptions').find('#survey-question-decimals').val('');
		
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
	
	$('#page-items').on("change", "#input-type", function(e){
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
			node.closest('div.row').find('#rangeOptions').removeClass('hidden');
			node.closest('div.row').find('#decimalsOptions').removeClass('hidden');
			node.closest('div.row').find('#genericOptions').addClass('hidden');
		}else{
			console.log("isNotNumber");
			node.closest('div.row').find('#rangeOptions').addClass('hidden');
			node.closest('div.row').find('#decimalsOptions').addClass('hidden');
			node.closest('div.row').find('#genericOptions').removeClass('hidden');
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
		node.closest('div.row').find('#lines').attr('class', 'question-response-settings-sub-none');
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = "";

		node.closest('div.row').find('#survey-question-max-lines').val('');
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
		node.closest('div.question-response-settings').find('#lines').attr('class','question-response-settings-sub-inherit');
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = "";

		node.closest('div.question-response-settings').find('#survey-question-max-lines').val('');
		
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
	
	$('.survey-sections').on("focusout", "input#survey-question-title", function(e){
		
		console.log("Question update content entra");
		e.stopPropagation();
		var req = {};		
		req.text = $(this).val();
		req.contentType = "title";
		req.lan = $("#survey-language-version").val();
		req.qid = $(this).closest('#panel-question1').attr('qid');		
		var serviceUrl = host + "/SurveyTool/api/QuestionService/updateContent";
		
		//alert("focusout");
		//check de section title
		var valid = true;
		if(req.text == ""){
			valid = false;
			//showFieldError($('#survey-question-title'+req.qid));
			$('#survey-question-title'+req.qid+'-error').removeClass('hidden');
			$('#survey-question-title'+req.qid+"-feedback").removeClass('hidden');
			
			$(this).prop("class", "survey-section-title-selected");
			
			//$(this).css({'border':"2px solid !important"});
			//$(this).css({'border-color':"#23527C !important"});
			
		}else{
			//hideFieldError($('#survey-question-title'+req.qid));
			$('#survey-question-title'+req.qid+'-error').addClass('hidden');
			$('#survey-question-title'+req.qid+"-feedback").addClass('hidden');
			
			$(this).prop("class", "survey-section-title-unselected");
			
			//$(this).css('border','none !important');			
			
			updateContent(req, serviceUrl, $(this));
		}
				
		
	});

	$('.survey-sections').on("focusout", "#survey-question-description-text", function(e){
		e.stopPropagation();
		var req = {};		
		req.text = $(this).val();
		req.contentType = "description";
		req.lan = $("#survey-language-version").val();
		req.qid = $(this).closest('#panel-question1').attr('qid');		
		var serviceUrl = host + "/SurveyTool/api/QuestionService/updateContent";
		
		updateContent(req, serviceUrl);
	});	
	
	$('.survey-sections').on("focusout", "#survey-question-other-text", function(e){
		e.stopPropagation();
		var req = {};		
		req.text = $(this).val();
		req.contentType = "other";
		req.lan = "en";
		req.qid = $(this).closest('#panel-question1').attr('qid');		
		var serviceUrl = host + "/SurveyTool/api/QuestionService/updateContent";
		
		updateContent(req, serviceUrl);
	});	
	
	/*$('.survey-sections').on("click", ".logic-button > button", function(){
		$(this).parent().addClass('hidden');
		$(this).closest('div.logic-frame').find('div.logic-settings').removeClass('hidden');
		$(this).closest('div.logic-frame').find('button.btn-close-logic').removeClass('hidden');
	});

	$('.survey-sections').on("click", "button.btn-close-logic", function(){
		
		var questionTitle = $(this).closest('li.panel-question').find('input.survey-question-title').val();
		var questionIndex = $(this).closest('li.panel-question').attr("qid");
		
		if($(this).closest("div.logic-frame").find("ul.logic-option-list").length){
			console.log("Remove logicGoTo list");
			$("#elementToRemoveText").html('"LogicGoTo for: ' + questionTitle + '"');
			$("#removeElemId").val("AllLogic" + '/' + questionIndex);
			$("#removeElemService").val('QCService');
			$("#removeElement").modal("show");
		}
		else{
			$(this).closest("div.logic-frame").find('div.logic-button').removeClass('hidden');
			$(this).closest("div.logic-frame").find('div.logic-settings').addClass('hidden');
			$(this).addClass('hidden');
		}
	});

	$('.survey-sections').on("click", ".dependences-button > button", function(){
		$(this).parent().addClass('hidden');
		$(this).closest('div.dependences-frame').find('div.dependences-settings').removeClass('hidden');
		$(this).closest('div.dependences-frame').find('button.btn-close-dependences').removeClass('hidden');
	});

	$('.survey-sections').on("click", "button.btn-close-dependences", function(){
		var index = $(this).closest('div.dependences-frame').find('ul.dependences-list').attr("index");
		var questionTitle = $(this).closest('li.panel-question').find('input.survey-question-title').val();
		var questionIndex = $(this).closest('li.panel-question').attr("qid");
		
		console.log("Remove dependence list");
		$("#elementToRemoveText").html('"Dependences for ' + questionTitle + '"');
		$("#removeElemId").val("All" + '/' + questionIndex + '/' + index);
		$("#removeElemService").val('QCService');
		$("#removeElement").modal("show");
	});*/
	
	$('.main-sidebar').on("click", "#general-menu", function(e){
		
		$('.content-statistics').addClass('hidden');
		
		var generalInfoDiv = $(this).closest("#statistics").find('div.content-wrapper').find('#general-info');
		generalInfoDiv.removeClass('hidden');
		var noneText = 'question-0';
		console.log($(this).closest("ul.sidebar-menu").attr("class"));
		var option = $(this).closest("ul.sidebar-menu").find("#statistics-questions-menu").val(noneText);
	});
	
	$('.main-sidebar').on("change", "#statistics-questions-menu", function(e){
		console.log("Change on statistics-questions-menu:"+$(this).val());
		
		$('.content-statistics').addClass('hidden');
		var idQuestion = $(this).val().split("-")[1];
		if(idQuestion==0){
			console.log("Quitando el hidden del general");
			var generalInfoDiv = $(this).closest("#statistics").find('div.content-wrapper').find('#general-info');
			generalInfoDiv.removeClass('hidden');
		}
		else{
			console.log("Remove hidden of:"+idQuestion);
			var questionDiv = $(this).closest("#statistics").find('div.content-wrapper').find('#single-question-'+idQuestion);
			questionDiv.removeClass('hidden');
		}
	});
	
	//drag and drop
	/*$(".survey-sections .page-items").sortable({
		connectWith:".connectedSortable",
		start:function(){
			console.log("Estas utilizando Drag and Drop: " + $(this).attr('id'));
		},
		stop:function(event, ui){
			var prevId = '0';
			if($(ui.item).prev().length) prevId = $(ui.item).prev().attr('qid');
	
			var req = {};		
			req.qid = $(ui.item).attr('qid');
			req.prevId = prevId;
			req.pid = $(this).closest('li.page').attr('pid');
			console.log("Dragged: " + JSON.stringify(req));
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
	});*/
	
	/*$('#survey-language-version').change(function(event) {
		
		var loc = location.href;
		if(location.href.indexOf("&") !=-1)loc=loc.substring(0,loc.indexOf('&'));
		
		 window.location=loc+"&langsurvey="+$("#survey-language-version").val();
	});*/
	
});



function updateContent(req, serviceUrl, node)
{
	//console.log("updae content - node: " + node.val());
	$.ajax({ 
	   type: "PUT",
	   dataType: "text",
	   contentType: "text/plain",
	   url: serviceUrl,
	   data: JSON.stringify(req),
	   success: function (data) {
		   console.log("update content response: " + data);	

		   if(node != null) node.trigger("setQuestionJson");
	   },
	   error: function (xhr, ajaxOptions, thrownError) {
		   console.log(xhr.status);
		   console.log(thrownError);
		   console.log(xhr.responseText);
		   console.log(xhr);
	   }
	});
}

function limit(element)
{
    var max_chars = 4;

    if(element.value.length >= max_chars) {
        element.value = element.value.substr(0, max_chars);
    }
}

function limitInput(element, max_chars)
{
console.log(max_chars);
    if(element.value.length >= max_chars) {
        element.value = element.value.substr(0, max_chars);
    }
}
