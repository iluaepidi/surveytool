/**
 * 
 */
var width = 445;
var height = 232;

$(function() {
	
	var host = "http://" + window.location.host;
	console.log("host: " + host);
	var lang = "en"
	
	$('#widthPoll').keyup(function(){
		setWidthPoll($(this));
	});
	
	$('#widthPoll').change(function(){
		setWidthPoll($(this));
	});
	
	$('#widthPoll').focusout(function(){
		var val = $(this).val();
		if(val < 200)
		{
			$(this).val(200);
		}
		setWidthPoll($(this));
	});
	
	$('#heightPoll').keyup(function(){
		setHeightPoll($(this));
	});
	
	$('#heightPoll').change(function(){
		setHeightPoll($(this));
	});
	
	$('#heightPoll').focusout(function(){
		var val = $(this).val();
		if(val < 150)
		{
			$(this).val(150);
		}
		setHeightPoll($(this));
	});

	$('.edit-poll-content').on("focusout", "#pollTitle", function(e){
		e.stopPropagation();		
		console.log("pollTitle: " + $(this).val());
		var req = {};		
		req.text = $(this).val();
		
		req.contentType = "title";
		//req.lan = $("#survey-language-version").val();
		req.lan = "en";
		req.poid = $(this).closest('div.edit-poll-content').attr('poid');		
		var serviceUrl = host + "/SurveyTool/api/PollService/updateContent";
		
		//check de title
		var valid = true;
		if(req.text == ""){
			valid = false;
			//showFieldError($('#survey-info-title'));
		}else{
			hideFieldError($('#survey-info-title'));
			updateContent(req, serviceUrl);
		}
		
	});
	
	$('.edit-poll-content').on("focusout", "#pollProject", function(e){
		e.stopPropagation();		
		var req = {};		
		req.project = $(this).val();
		req.poid = $(this).closest('div.edit-poll-content').attr('poid');
		var serviceUrl = host + "/SurveyTool/api/PollService/updateProject";
		
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

	$('.edit-poll-content').on("focusout", "textarea#qstatement", function(e){
		
		console.log("Question title - textarea: " + $(this).val());
		e.stopPropagation();
		var req = {};		
		req.text = $(this).val();
		req.contentType = "title";
		//req.lan = $("#survey-language-version").val();
		req.lan = lang;
		req.qid = $(this).attr('qid');		
		var serviceUrl = host + "/SurveyTool/api/QuestionService/updateContent";
		
		//alert("focusout");
		//check de section title
		var valid = true;
		if(req.text == ""){
			valid = false;
			
			//$('#survey-question-title'+req.qid+'-error').removeClass('hidden');
			//$('#survey-question-title'+req.qid+"-feedback").removeClass('hidden');
			
		}else{
			//$('#survey-question-title'+req.qid+'-error').addClass('hidden');
			//$('#survey-question-title'+req.qid+"-feedback").addClass('hidden');
			
			updateContent(req, serviceUrl);
		}		
	});
	

	$('.edit-poll-content').on("focusout", "#option-list #option-item input", function(e){
		e.stopPropagation();
		//console.log("language: " + $('#survey-language-version').val());
		if($(this).val() != "")
		{
			console.log("Text: " + $(this).val() + " - oid: " + $(this).attr('oid') + " - qid: " + $("#qstatement").attr('qid') + " - ogid: " + $(this).closest('ul').attr('ogid'));
			var req = {};
			var currentNode = $(this);
			req.text = currentNode.val();
			req.oid = currentNode.attr('oid');
			req.index = currentNode.attr('index');
			req.qid = $("#qstatement").attr('qid');
			req.ogid = currentNode.closest('ul').attr('ogid');
			//req.lang = $('#survey-language-version').val();
			req.lang = lang;
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
		else if ($(this).attr('oid')>0){
			$(this).closest('li.option-item').find("#remove-option").trigger("click");
			console.log("currentText: " + currentText);
			if(currentText != "")
			{
				$(this).val(currentText);
				currentText = "";
			}
		}
	});
	
	$('.edit-poll-content').on("click", "#option-list #btn-add-option", function(e){
		var index = $(this).parent().parent().children("li").size();
		var optionHtml = '<li class="option-item" id="option-item">' +
								//'<button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> ' +		
								'<div class="circle-info circle-grey fleft">' + index + '</div> ' + 
								'<label for="option" class="visuallyhidden">'+accesibilityTextOption+'</label>	'+													
								'<input id="option" type="text" class="option-title form-control fleft" index="' + index + '" oid="0" placeholder="'+textOption+' ' + index + '" autofocus/> ' +
								'<div class="option-icons fleft"> ' +
									//'<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> ' +
									'<button class="btn btn-transparent fleft red remove-option" id="remove-option" aria-label="remove option"><i class="fa fa-trash fa-2x"></i></button> ' +
								'</div> ' +
							'</li>';
		$(this).parent().before(optionHtml);
		//$(this).closest('ul').find('input[index=' + index + ']').focus();
	});

	$('.edit-poll-content').on("click", "button.remove-option", function(e){
		console.log("Remove option");
		currentQuestion = $("#qstatement").attr('qid');		
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

	$('#removeElement').on("click", "#acceptRemoveElement", function(e){
		
		var elementId = $('#removeElemId').val(); 
		var service = $("#removeElemService").val();
		console.log("Resource ID: " + elementId+", service: "+service);
		console.log(host + "/SurveyTool/api/" + service + "/" + elementId);
		//console.log("number items: " + $('li[rid=' + resourceId + ']').closest("ul").find("li").size());
		var removeFileQuestion = false;
		if (service == "ResourceServiceQuestion"){
			removeFileQuestion = true;
			service = "ResourceService";
		}
		console.log("Resource ID: " + elementId+", service: "+service);	
		$.ajax({ 
		   url: host + "/SurveyTool/api/" + service + "/" + elementId,
		   type: "DELETE",
		   success: function (data) {
			   console.log("data: "+data+", service: "+service);
			   if(data == 'true')
			   {
				   $("#removeElement").modal("hide");
				   if(service == "OptionService")
				   {   
					   var ids = elementId.split('/');
					   var oid = ids[2];
					   var input = $('input[oid=' + oid + ']'); 					   
					   var numItems = input.closest("ul").find("li.option-item").size();
					   
					   console.log("Items: " + numItems);
					   if(numItems > 2)
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
						   var multimediaDiv = input.closest('li').find('div.options-files-frame');
						   if(!multimediaDiv.hasClass('hidden'))
						   {
							   multimediaDiv.find('li.multimedia-item').remove();
							   multimediaDiv.addClass('hidden');
							   //multimediaDiv.find('ul#multimediaFilesList').addClass('hidden');
						   }
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

	$('.edit-poll-content').on("focusout", "#ackText", function(e){
		e.stopPropagation();		
		//console.log("pollTitle: " + $(this).val());
		var req = {};		
		req.text = $(this).val();
		
		req.contentType = "ackText";
		//req.lan = $("#survey-language-version").val();
		req.lan = "en";
		req.poid = $(this).closest('div.edit-poll-content').attr('poid');		
		var serviceUrl = host + "/SurveyTool/api/PollService/updateContent";
		
		//check de title
		var valid = true;
		if(req.text == ""){
			valid = false;
			//showFieldError($('#survey-info-title'));
		}else{
			hideFieldError($('#survey-info-title'));
			updateContent(req, serviceUrl);
		}
		
	});

	$('.edit-poll-content').on("focusout", "#pollCallText", function(e){
		e.stopPropagation();		
		//console.log("pollTitle: " + $(this).val());
		var req = {};		
		req.text = $(this).val();
		
		req.contentType = "callText";
		//req.lan = $("#survey-language-version").val();
		req.lan = "en";
		req.poid = $(this).closest('div.edit-poll-content').attr('poid');		
		var serviceUrl = host + "/SurveyTool/api/PollService/updateContent";
		
		//check de title
		var valid = true;
		if(req.text == ""){
			valid = false;
			//showFieldError($('#survey-info-title'));
		}else{
			hideFieldError($('#survey-info-title'));
			updateContent(req, serviceUrl);
		}
		
	});

	$('.edit-poll-content').on("focusout", "#pollLinkLabel", function(e){
		e.stopPropagation();		
		//console.log("pollTitle: " + $(this).val());
		var req = {};		
		req.text = $(this).val();
		
		req.contentType = "label";
		//req.lan = $("#survey-language-version").val();
		req.lan = "en";
		req.poid = $(this).closest('div.edit-poll-content').attr('poid');		
		var serviceUrl = host + "/SurveyTool/api/PollService/updateContent";
		
		//check de title
		var valid = true;
		if(req.text == ""){
			valid = false;
			//showFieldError($('#survey-info-title'));
		}else{
			hideFieldError($('#survey-info-title'));
			updateContent(req, serviceUrl);
		}
		
	});

	$('.edit-poll-content').on("focusout", "#pollLinkUrl", function(e){
		e.stopPropagation();		
		var req = {};		
		req.linkUrl = $(this).val();
		req.poid = $(this).closest('div.edit-poll-content').attr('poid');
		var serviceUrl = host + "/SurveyTool/api/PollService/updateCallUrl";
		
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

});

function setWidthPoll(elem)
{
	width = elem.val();
	var code = $('#pollCode').val();
	$('#pollCode').val(code.replace(/style=".*"/, 'style="width: ' + width + 'px; height: ' + height + 'px;"'));
		
	$('#iframe').css('width', width);
}

function setHeightPoll(elem)
{
	height = elem.val();
	var code = $('#pollCode').val();
	$('#pollCode').val(code.replace(/style=".*"/, 'style="width: ' + width + 'px; height: ' + height + 'px;"'));
	
	$('#iframe').css('height', height);
}

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

