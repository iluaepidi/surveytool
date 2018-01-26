/**
 * 
 */
var qtypeId;
var numQuestions = 0;
var currentText = "";
var currentValue;
var currentElement;
var currentAddNode;
var previousPage;
var nextPage;
var currentQuestion = 0;
var currentOption = 0;
var currentOptionGroup = 0;
var currentLanguage = "en";
var addMenuFrameCad = "add-menu-frame-";
var pending;
var jsonquotas;
var quotaid;
var placeholderBContent = "";
var accesibilityTextItem = "";
var accesibilityTextColumn = "";
var accesibilityTextOption = "";
var accesibilityTextButton = "";
var modalFocus = null;
var keyBuffer = {};

var phOption = "";
var phItem = "";
var phColumn = "";

var host = "http://" + window.location.host;
console.log("host: " + host);

$(function() {
	
	host = "http://" + window.location.host;
	
	$('body_').click(function() {
				
    });
	
	//survey-info  #e6e6e6
	$('.btn-qtype button').click(function(){
		//console.log("Clicking on type query button");
		//$('#frame-basic-Settings').css('display', 'inherit');
		//$('#' + qtypeId + " i").css("background-color","#fff");
		$('#' + qtypeId + " i").removeClass("qtype-selected");
		qtypeId = $(this).attr('id');
		$('#' + qtypeId + " i").addClass("qtype-selected");
		//$('#' + qtypeId + ' i').css("background-color","#e6e6e6");
		$('#qtypevalue').val(qtypeId);
		$('#qstatement').focus();
		$("div.qtype-error > span").addClass("hidden");
		
		if(qtypeId === "scale") $("div.div-create-question-type-scale").removeClass("hidden");
		else $("div.div-create-question-type-scale").addClass("hidden");
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
		var qTypeSelected = false;
		$('ul.qtype-list').find("li.btn-qtype").each(function(index, elem){
			qTypeSelected = qTypeSelected || $(elem).find("i").hasClass("qtype-selected");
		});
		
		//check de name project
		if($('#qstatement').val() == ""){
			valid = false;
			showFieldError($('#qstatement'));
		}else if(!qTypeSelected){
			valid = false;
			$("div.qtype-error > span").removeClass("hidden");
		}else{
			hideFieldError($('#qstatement'));
			console.log("num page: " + currentElement.attr('index'));
			// Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
			var qTypeValue = $('#qtypevalue').val();
			var request = {
					qtype : qTypeValue,
					qstatement: $('#qstatement').val(),
					mandatory: $('#mandatory').val(),
					helpText: $('#help-text').val(),
					surveyid: $('#surveyid').val(),
					pageid: currentElement.attr('pid'),
					numPage: currentElement.attr('index'),
					langsurvey : $("#survey-language-version").val()
				};
			if(qTypeValue === 'scale') request.scaleType = $('#nqLikertType').val();
			$.post('CreateQuestionServlet', request, function(responseText) {
				var index = responseText.indexOf("<html");
				if(index >= 0) {window.location.replace(host + urlBase + "/SurveysServlet");}
				else {
					var pageItems = currentElement.find('ul.page-items'); 
					pageItems.append(responseText);
					var question = pageItems.find('li.panel-question').last()
					//console.log("Question created: " + pageItems.find('li.panel-question').last().attr('index'));
					question.find('input.survey-question-title').trigger("createQuestionJson", [request.qtype, request.pageid, request.qstatement, question.attr('qid'), question.attr('index')]);
					modalFocus = question.find("h5.panel-title");
					
					var numQuestion = 1;
					$('#survey-sections').find("li.panel-question").each(function(i, element){
						if(!$(element).hasClass("bcontent"))
						{
							$(element).find('span.num-question').html(numQuestion);
							numQuestion++;
						}
					});
				}
			});
			console.log("after post");
			$('#qstatement').val("");
			//$('#main-version').val("none");
			$('#mandatory').val("false");
			$('#help-text').val("false");
			$('#' + qtypeId + " i").removeClass("qtype-selected");
			//$('#' + qtypeId + " i").css("background-color","#fff");
			//$('#frame-basic-Settings').css('display', 'none');
			
			//$('#modal').modal('hide');
			//$('#newQuestionModal').hide();
			$('#newQuestionModal').modal('toggle');
			$("div.qtype-error > span").addClass("hidden");
		}
	});
	
	$('.survey-sections').on("click", '.btn-question', function(){
		currentElement = $(this).closest('li.page');
		$("#newQuestionModal").modal("show");		
		modalFocus = $(this);
	});
	
	$('.survey-sections').on("click", '.btn-edit', function(){
		$(this).closest('div').find("input").focus();
	});
	
	$('.survey-sections').on("click", ".btn-page-break", function(){
		var node = $(this);
		var req = {};
		req.scid = node.closest('.panel-section').attr('scid');
		req.sid = node.closest('div.edit-content-center').find('div.survey-info').attr('sid');
		req.numPage = node.closest('li.page').attr('index');
		
		$.post('CreatePageServlet', req, function(responseText) {
			var index = responseText.indexOf("<html");
			if(index >= 0) {window.location.replace(host + urlBase + "/SurveysServlet");}
			else {
				//console.log("Create page sectionId: " + node.closest('ul.section-pages').html());
				//var i = parseInt(node.closest('ul.section-pages').find('li.page').first().attr('index'));
				node.closest('li.page').after(responseText);
				/*node.closest('ul.section-pages').find('li.page').each(function(indice, elemento) {
					var cads = $(elemento).find('h4').html().split(' ');
					$(elemento).attr('index', i + indice);
					$(elemento).find('h4').html(cads[0] + " " + (i + indice));
				});*/
				var sectionList = node.closest('ul.survey-sections');

				var i = 1;
				sectionList.find("li.panel-section").each(function(indexSection, section){
				   $(section).find('li.page').each(function(indexPage, page){
					   var cads = $(page).find('h4 > span').html().split(' ');
						$(page).attr('index', i);
						$(page).find('h4 > span').html(cads[0] + " " + i);
						i++;
				   });
				});
				
				insertPageJson($(responseText).attr("index"), $(responseText).attr("pid"));
			}
		});
	});

	$('.edit-content-center').on("click", ".btn-new-section", function(){
		console.log("create section");
		var node = $(this);
		var req = {};
		req.scid = node.closest('.panel-section').attr('scid');
		var surveyInfo = node.closest('div.edit-content-center').find('div.survey-info');
		req.sid = surveyInfo.attr('sid');
		req.fid = surveyInfo.attr('fid');
		//req.numPage = node.closest('li.page').attr('index');
		
		$.post('CreateSectionServlet', req, function(responseText) {
			console.log("create section response: " + responseText);
			var index = responseText.indexOf("<html");
			if(index >= 0) {window.location.replace(host + urlBase + "/SurveysServlet");}
			else {
				//console.log("Create page sectionId: " + node.closest('ul.section-pages').html());
				node.closest('div.edit-content-center').find('ul.survey-sections').append(responseText);	
				node.closest('div.edit-content-center').find('ul.survey-sections').find('li.panel-section').last().find("h3 input").focus();
				var section = node.closest("div.edit-content-center").find("ul.survey-sections").find("li.panel-section").last();
				var newPage = section.find("li.page").first();
				insertPageJson(newPage.attr("index"), newPage.attr("pid"));
			}			
			console.log("create section surveyTree: " + JSON.stringify(surveyTree));
		});
	});

	$('.survey-sections').on("click", ".btn-bcontent", function(){
		var node = $(this);
		var req = {};
		req.scid = node.closest('.panel-section').attr('scid');
		req.sid = node.closest('div.edit-content-center').find('div.survey-info').attr('sid');
		req.numPage = node.closest('li.page').attr('index');
		var request = {
				qtype : "bcontent",
				qstatement: "body content",
				surveyid: $('#surveyid').val(),
				pageid: node.closest('li.page').attr('pid'),
				numPage: node.closest('li.page').attr('index'),
				langsurvey : $("#survey-language-version").val()
			};
		$.post('CreateQuestionServlet', request, function(responseText) {
			var index = responseText.indexOf("<html");
			if(index >= 0) {window.location.replace(host + urlBase + "/SurveysServlet");}
			else {
				var pageItems = node.closest('li.page').find('ul.page-items'); 
				pageItems.append(responseText);
				var question = pageItems.find('li.panel-question').last()
				//console.log("Question created: " + pageItems.find('li.panel-question').last().attr('index'));
				question.find('input.survey-question-title').trigger("createQuestionJson", [request.qtype, request.pageid, request.qstatement, question.attr('qid'), question.attr('index')]);
			}
		});
		console.log("after post");
	});
	
	var newquota = 1000;
	$('#create-quota').click(function(event) {
			$('#newQuotaModal').modal('toggle');
			newquotaid = $("#selquestionnewquota").val();
			console.log("Quota: " + newquotaid);
			
			//Copy survey-quota-new
			if($("#survey-quota-"+newquotaid).length > 0){
				alertNotQuota();
			}else{
				var newQuota=$('#survey-quota-new').find("li").clone();
				//newQuota.css("display","block");
				newQuota.removeClass("hidden");
				newQuota.attr("quota",newquotaid);
				newQuota.attr("sid",newquotaid);
				newQuota.attr("id","survey-quota-"+newquotaid);

				var selectQuota = newQuota.find('select');
				selectQuota.attr("id","selquestionforfees"+newquotaid);
				selectQuota.attr("name","selquestionforfees"+newquotaid);
				selectQuota.attr("onchange","changeoptionsfees("+newquotaid+")");
				
				var h5title =  newQuota.find('h5');
				h5title.attr("id","questionquotaname"+newquotaid);
				
				var divoptions = newQuota.find('#optionsquotanew');
				divoptions.attr("id","optionsquota"+newquotaid);
				
				
				//newQuota.prependTo("#listcompletequotas");
				//newQuota.insertBefore("#survey-quota-new");
				$('ul.quota-item-list').append(newQuota);
				$('#selquestionforfees'+newquotaid).val(newquotaid);
				loadvaluequestion(newquotaid);
				//eliminar opcion del combo
				//$("#selquestionnewquota").find('[value="'+newquota+'"]').remove();
				$('#questionquotaname'+newquotaid).html($("#selquestionforfees"+newquotaid +" option:selected").text());
			}
			
	});
	
	$('#listcompletequotas').on("click", '#btn-add-quota', function(){
		currentAddNode = $(this).parent().parent().parent();
		$("#newQuotaModal").modal("show");
	});
	
	$('.survey-sections').on("click", '#editFile', function(){
		currentElement = JSON.parse($(this).attr("data-image"));
		currentAddNode = $(this).closest('li.option-item').find('input.option');
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
		
		modalFocus = $(this);
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

	$('.survey-sections').on("focusin", "#option-list #option-item input", function(e){
		currentText = $(this).val();
		console.log("Asigned current text: " + currentText);
	});
	
	
	$('.survey-sections').on("focusout", "#option-list #option-item input.option-title", function(e){
		e.stopPropagation();
		//console.log("language: " + $('#survey-language-version').val());
		if($(this).val() != "" || ($(this).val() == "" && $(this).attr('oid')>0 && $(this).closest('li[id=option-item]').find('#multimediaFilesList li').length<1))
		{
			console.log("Text: " + $(this).val() + " - qid: " + $(this).attr('index') + " - qid: " + $(this).closest('li[id=panel-question1]').attr('qid') + " - ogid: " + $(this).closest('ul').attr('ogid'));
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
			   url: host + urlBase + "/api/QCService/insertOption",
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
					   
					   currentNode.closest('li').find('.remove-option').attr('aria-label', 'Remove option: ' + req.text);
					   
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
		else if ($(this).attr('oid')>0 && $(this).closest('li[id=option-item]').find('#multimediaFilesList li').length >= 1)
		{
			/*if($(this).closest('li[id=option-item]').find('#multimediaFilesList li').length<1){
				//$(this).closest('li.option-item').find(".remove-option").trigger("click");
				$(this).trigger("empty");
				console.log("currentText: " + currentText);
				if(currentText != "")
				{
					$(this).val(currentText);
					currentText = "";
				}
			}else{*/
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
				   url: host + urlBase + "/api/QCService/updateTextOption",
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
						   
						   currentNode.closest('li').find('.remove-option').attr('aria-label', 'Remove option: ' + req.text);
						   
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
			//}
		}
	});
	

	$('.section-pages').on("empty", "#option-list #option-item input.option-title", function(e){
		console.log("Empty option");
		currentQuestion = $(this).closest('#panel-question1').attr('qid');		
		var item = $(this).closest('li');
		var ogid = item.closest('ul').attr('ogid');
		var input = $(this);
		var oid = input.attr('oid');
		var elementIndex = input.attr('index');
		var result = currentQuestion + "/" + ogid + "/" + oid;
		console.log("result: " + result);

		$.ajax({ 
		   url: host + urlBase + "/api/OptionService/" + currentQuestion + "/" + ogid + "/" + oid,
		   type: "DELETE",
		   success: function (data) {
			   console.log("data: "+data);
			   if(data == 'true')
			   { 					   
				   var numItems = input.closest("ul").find("li.option-item").size();
				   var list = input.closest("ul.option-list");
				   
				   input.trigger("rmvOptJson");
					
				   console.log("Items: " + numItems);
				   
				   input.val("");
				   input.attr('oid', '0');
					
				   var logicOptionElement = $('#logic-option-' + oid);
				   removeLogicElement(logicOptionElement);				   
			   }
		   }
		});
	});
	
	
	insertValueQuota();
	
	$("#objetivesurveys").focusout(function() {
		//if($(this).val() != ""){
			
			var req = {};
			var currentNode = $(this);
			req.text = currentNode.val();
			req.sid = currentNode.attr('sid');
			req.objetive = currentNode.val();
			
			console.log("sid: " + req.sid + " - objetive: " + req.objetive);
			
			$.ajax({ 
			   type: "POST",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + urlBase + "/api/QuotaService/updateObjetive",
			   data: JSON.stringify(req),
			   success: function (data) {
				   console.log(data);
				   if(data != '')
				   {
					   var jsonresponse = JSON.parse(data);
					   if(jsonresponse.hasOwnProperty('sid'))
					   {
						   
							
						   
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
		//}
	});
	
	$('.survey-sections').on("focusout", "input.otherOptionTitle", function(e){
		var titleOther = $(this).closest("fieldset").find("legend").html();
		var value = $(this).val();
		
		
		/*else if (value != titleOther)
		{*/
			var req = {};
			var currentNode = $(this);
			req.text = currentNode.val();
			req.oid = currentNode.closest('li').attr('oid');
			req.lang = $('#survey-language-version').val();
			
			$.ajax({ 
			   type: "POST",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + urlBase + "/api/OptionService/updateTextOtehrOption",
			   data: JSON.stringify(req),
			   success: function (data) {
				   console.log(data);
				   if(data != '')
				   {
					   if(value === "") {currentNode.val(titleOther);}
					   currentNode.trigger("gotoOther");
					   currentNode.trigger("setJsonOther");					   
				   }
			   },
			   error: function (xhr, ajaxOptions, thrownError) {
				   console.log(xhr.status);
				   console.log(thrownError);
				   console.log(xhr.responseText);
				   console.log(xhr);
			   }
			});
		//}
	});

	$('.section-pages').on("empty", ".remove-optionsgroupmatrix", function(e){			
		console.log("Empty optionsgroupmatrix");
		currentQuestion = $(this).closest('#panel-question1').attr('qid');
		var item = $(this).closest('li');
		var input = item.find('input');
		var ogid = input.attr('ogid');

		$.ajax({ 
		   url: host + urlBase + "/api/OptionsGroupMatrixService/" + ogid,
		   type: "DELETE",
		   success: function (data) {
			   console.log("data: "+data);
			   if(data == 'true')
			   { 	
				   input.val("");
				   input.attr('ogid', '0');
			   }
		   }
		});
	});

	$('.section-pages').on("empty", ".remove-optionmatrix", function(e){
		console.log("Empty optionsgroupmatrix");
		currentQuestion = $(this).closest('#panel-question1').attr('qid');
		var item = $(this).closest('li');
		var input = item.find('input');
		var oid = input.attr('oid');

		$.ajax({ 
		   url: host + urlBase + "/api/OptionMatrixService/" + oid,
		   type: "DELETE",
		   success: function (data) {
			   console.log("data: "+data);
			   if(data == 'true')
			   { 	
				   input.val("");
				   input.attr('oid', '0');
			   }
		   }
		});
	});
	
	$('.survey-sections').on("focusout", "#optionmatrix-list #optionmatrix-item input", function(e){
		e.stopPropagation();
		console.log("option matrix language: " + $('#survey-language-version').val());
		if($(this).val() != "" || ($(this).val() === "" && $(this).attr('oid')>0))
		{
			console.log("Text: " + $(this).val() + " - qid: " + $(this).attr('index') + " - qid: " + $(this).closest('li[id=panel-question1]').attr('qid') + " - oid: " + $(this).closest('ul').attr('oid'));
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
			   url: host + urlBase + "/api/QCService/insertOptionMatrix",
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
					   
					   currentNode.closest('li').find('.remove-optionmatrix').attr('aria-label', 'Remove option: ' + req.text);
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
		/*else if($(this).attr('oid')>0){
			//$(this).closest('li[id=optionmatrix-item]').find(".remove-optionmatrix").trigger("click");
			$(this).closest('li[id=optionmatrix-item]').find(".remove-optionmatrix").trigger("empty");
		}*/
		
	});
	

	
	$('.survey-sections').on("focusout", "#optionsgroupmatrix-list #optionsgroupmatrix-item input", function(e){
		e.stopPropagation();
		//console.log("language: " + $('#survey-language-version').val());
		console.log("options group matrix lalala valor: " + $(this).val());
		if($(this).val() != "" || ($(this).val() === "" && $(this).attr('ogid')>0))
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
			   url: host + urlBase + "/api/QCService/insertOptionsGroupMatrix",
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
					   
					   currentNode.closest('li').find('.remove-optionsgroupmatrix').attr('aria-label', 'Remove option: ' + req.text);
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
		/*else if($(this).attr('ogid')>0){
			//console.log("empty value, ogid:"+$(this).attr('ogid'));
			//console.log($(this).closest('li[id=optionsgroupmatrix-item]').attr("class"));
			//$(this).closest('li[id=optionsgroupmatrix-item]').find(".remove-optionsgroupmatrix").trigger("click");
			$(this).closest('li[id=optionsgroupmatrix-item]').find(".remove-optionsgroupmatrix").trigger("empty");
		}*/
	});
	
	
	$('.survey-sections').on("click", "#option-list #btn-add-option", function(e){
		//var index = $(this).parent().parent().children("li").size();
		var otype = $(this).closest("ul").attr("otype");
		var index = $(this).closest("ul").find("li.option-item").size();
		if(otype === "checkbox") index = index + 1;
		var ogid = $(this).closest("ul.option-list").attr("ogid");
		var qid = $(this).closest("li.panel-question").attr("qid");
		var importFileHiddenClass = "";
		debugger;
		if(otype === "radio" && $(this).closest('li.panel-question').find('select.type-simple-answer').val() === "select") importFileHiddenClass = "hidden";
		var optionHtml = '<li class="option-item" id="option-item">' +		
								'<div class="circle-info circle-grey fleft">' + index + '</div> ' + 
								'<label for="option' + qid + '-' + ogid + '-' + index + '" class="visuallyhidden">'+accesibilityTextOption+'</label>	'+													
								'<input id="option' + qid + '-' + ogid + '-' + index + '" type="text" class="option-title form-control fleft option" index="' + index + '" oid="0" placeholder="'+textOption+' ' + index + '" autofocus/> ' +
								'<div class="option-icons fleft"> ' +
									'<label for="add-file-option' + qid + '-' + ogid + '-' + index + '" class="visuallyhidden">' + accesibilityTextButton + ' '+ index +'</label>'+
									'<button class="btn btn-transparent fleft  add-file-option addFileOption ' + importFileHiddenClass + '" id="add-file-option' + qid + '-' + ogid + '-' + index + '"  active="false"><i class="fa fa-file-image-o fa-2x" aria-hidden="true"></i></button> ' +
									'<button class="btn btn-transparent fleft red remove-option" id="remove-option' + qid + '-' + ogid + '-' + index + '" aria-label="remove option"><i class="fa fa-trash fa-2x"></i></button> ' +
								'</div> ' +
								'<div class="row margin-top-40 hidden" type="global" id="multimediaFrame"><div id="div_files"><div class="options-files-frame hidden"><label>'+textOptionFile+'</label><ul class="multimedia-list" id="multimediaFilesList"></ul></div></div></div>' +
							'</li>';
		if(otype === "checkbox") $(this).closest("li").before(optionHtml);
		else $(this).closest("li").prev().before(optionHtml);
		
		if(otype != "checkbox") $(this).closest("li").prev().find("div.circle-info").html(index + 1);
		//$(this).closest('ul').find('input[index=' + index + ']').focus();
	});
	
	$('.survey-sections').on("click", "button.btnAddOptionOther", function(e){
		var req = {};
		var currentNode = $(this);
				
		req.qid = currentNode.closest('li.panel-question').attr('qid');
		req.ogid = currentNode.closest('ul').attr('ogid');
		req.pid = currentNode.closest('li.page').attr('pid');
		req.index = currentNode.closest("ul").find("li.option-item").size();
		req.otype = currentNode.closest('ul').attr('otype');
		req.lang = $('#survey-language-version').val();
		if($(this).hasClass("btnAddOptionOther")) req.isOther = "true";
		else req.isOther = "false";
		
		$.ajax({ 
		   type: "POST",
		   dataType: "text",
		   contentType: "text/plain",
		   url: host + urlBase + "/api/OptionService/createOptionOther",
		   data: JSON.stringify(req),
		   success: function (data) {
			   if(data != "")
			   {
				   currentNode.closest("li").prev().removeClass("hidden"); 
				   var index = currentNode.closest("ul").find("li.option-item").size();
				   currentNode.closest("li").prev().find("div.circle-info").html(index);
				   currentNode.prop( "disabled", true );
				   var json = JSON.parse(data);
				   if(json.hasOwnProperty('oid'))
				   {
					   currentNode.closest("li").prev().attr('oid', json.oid);
				   }
				   
				   if(json.hasOwnProperty('ogid'))
				   {
					   currentNode.closest('ul').attr('ogid', json.ogid);
				   }
				   
				   currentNode.closest("li").prev().find("input.otherOptionTitle").trigger("gotoOther");
				   currentNode.closest("li").prev().find("input.otherOptionTitle").trigger("setJsonOther");
			   }
			   /*else if(data == 'true' && req.value == 'false')
			   {
				   var optionElem = currentNode.closest("li.option-item");				   
				   optionElem.addClass("hidden");
				   var titleOther = optionElem.find("legend").html();
				   optionElem.find("input.otherOptionTitle").val(titleOther);
				   optionElem.find("input.survey-question-max-chars").val("");
				   optionElem.find("input.isLimitedChars").prop("checked", false);
				   optionElem.find("input.isLimitedChars").attr('active', false);
				   optionElem.find('#charsId').attr('class','question-response-settings-sub-none');
				   optionElem.find("input.adjust-lines-adjust").prop("checked", true);
				   optionElem.find("input.adjust-lines-set").prop("checked", false);
				   optionElem.find('#lines').attr('class','question-response-settings-sub-none');
				   optionElem.find("input.survey-question-max-lines").val("");
				   optionElem.next().find("button.btnAddOptionOther").prop( "disabled", false );
					
			   }*/
		   },
		   error: function (xhr, ajaxOptions, thrownError) {
			   console.log(xhr.status);
			   console.log(thrownError);
			   console.log(xhr.responseText);
			   console.log(xhr);
		   }
		});		
	});
	
	$('.survey-sections').on("click", "#optionmatrix-list #btn-add-optionmatrix", function(e){
		var index = $(this).parent().parent().children("li").size();
		var qid = $(this).closest("li.panel-question").attr("qid");
		var optionHtml = '<li class="option-item" id="optionmatrix-item">' +
								//'<button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> ' +	
								'<div class="circle-info circle-grey fleft">' + index + '</div> ' + 
								'<label for="inputCol' + qid + '-' + index + '" class="visuallyhidden">'+accesibilityTextColumn+' '+index+'</label>' +
								'<input type="text" id="inputCol' + qid + '-' + index + '" class="option-title form-control fleft" index="' + index + '" oid="0" placeholder="'+phColumn + ' ' + index + '" autofocus/> ' +
								'<div class="option-icons fleft"> ' +
									//'<button class="btn btn-transparent fleft"><i class="fa fa-file-image-o fa-2x"></i></button> ' +
									//'<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> ' +
									'<button class="btn btn-transparent fleft red remove-optionmatrix" id="remove-optionmatrix" aria-label="remove option"><i class="fa fa-trash fa-2x"></i></button> ' +
								'</div> ' +
							'</li>';
		$(this).parent().before(optionHtml);
		//$(this).closest('ul').find('input[index=' + index + ']').focus();
	});
	
	$('.survey-sections').on("click", "#optionsgroupmatrix-list #btn-add-optionsgroupmatrix", function(e){
		var index = $(this).parent().parent().children("li").size();
		var qid = $(this).closest("li.panel-question").attr("qid");
		var optionHtml = '<li class="option-item" id="optionsgroupmatrix-item">' +
								//'<button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> ' +		
								'<div class="circle-info circle-grey fleft">' + index + '</div> ' + 
								'<label for="inputRow' + qid + '-' + index + '" class="visuallyhidden">'+accesibilityTextItem+' '+index+'</label>' +
								'<input type="text" id="inputRow' + qid + '-' + index + '" class="option-title form-control fleft" index="' + index + '" ogid="0" placeholder="'+phItem + ' ' + index + '" autofocus/> ' +
								'<div class="option-icons fleft"> ' +
									//'<button class="btn btn-transparent fleft"><i class="fa fa-file-image-o fa-2x"></i></button> ' +
									//'<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> ' +
									'<button class="btn btn-transparent fleft red remove-optionsgroupmatrix" id="remove-optionsgroupmatrix" aria-label="remove option"><i class="fa fa-trash fa-2x"></i></button> ' +
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
         
        
        //check option file or question file
        
         //alert($('#optionsFile').hasClass('hidden'));
         if($('#optionsFile').hasClass('hidden') == true){
        	 formData.append("qid", currentQuestion);
        	 formData.append("oid", currentOption);
        	 formData.append("action", "file");
         } else {
        	 formData.append("rid", $('#rid').val());
        	 formData.append("oid", currentOption);
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
        	 var index = res.indexOf("<html");
        	 if(index >= 0) {window.location.replace(host + urlBase + "/SurveysServlet");}
        	 else {
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
        	 }
         });
	});
	
	
	$('#importFile').on("focusout", "#resourceTitle", function(e){
		e.stopPropagation();		
		
		console.log("focusout req.resourceTitle-"+$('#resourceTitle').val());
		
		if($('#resourceTitle').val() === ""){
			showFieldError($('#resourceTitle'));
		}else{
			hideFieldError($('#resourceTitle'));
		}
		
	});
	
	
	$('#importFile').on("focusout", "#resourceAltText", function(e){
		e.stopPropagation();		
		
		console.log("focusout req.resourceAltText-"+$("#resourceAltText").val());
		
		if($("#resourceAltText").val() === ""){
			showFieldError($('#resourceAltText'));
		}else{
			hideFieldError($('#resourceAltText'));
		}
		
	});
	
	
	$('#importFile').on("focusout", "#resourceDescText", function(e){
		e.stopPropagation();		
		
		console.log("focusout req.resourceDescText-"+$("#resourceDescText").val());
		
		if($("#resourceDescText").val() === ""){
			showFieldError($('#resourceDescText'));
		}else{
			hideFieldError($('#resourceDescText'));
		}
		
	});
	
	
	$('#importFile').on("focusout", "#resourceUrl", function(e){
		e.stopPropagation();		
		
		console.log("focusout req.resourceUrl-"+$("#resourceUrl").val());
		
		if(!($("#resourceUrl").val() === "") && (($("#resourceUrl").val().startsWith("http://")) || ($("#resourceUrl").val().startsWith("https://")))){
			console.log("($(#resourceUrl).val() === empty) "+($("#resourceUrl").val() === ""));
			console.log("(!$(#resourceUrl).val().startsWith(http://)) "+(!$("#resourceUrl").val().startsWith("http://")));
			console.log("(!$(#resourceUrl).val().startsWith(https://)) "+(!$("#resourceUrl").val().startsWith("https://")));
			
			hideFieldError($('#resourceUrl'));
		}else{
			showFieldError($('#resourceUrl'));
		}
		
	});
	
	
	$('#importFile').on("focusin", "#resourceTitle", function(e){
		e.stopPropagation();
		console.log("focusin resourceTitle");
		hideFieldError($('#resourceTitle'));
		
	});
	
	
	$('#importFile').on("focusin", "#resourceAltText", function(e){
		e.stopPropagation();
		console.log("focusin resourceAltText");	
		
		hideFieldError($('#resourceAltText'));
		
	});
	
	
	$('#importFile').on("focusin", "#resourceDescText", function(e){
		e.stopPropagation();
		console.log("focusin resourceDescText");	
		
		hideFieldError($('#resourceDescText'));
		
	});
	
	
	$('#importFile').on("focusin", "#resourceUrl", function(e){
		e.stopPropagation();
		console.log("focusin resourceUrl");
		
		hideFieldError($('#resourceUrl'));
		
	});
	
	
	$('#importFile').on('hidden.bs.modal', function () {
	    //alert("close");
	})
	
	$('#importFileForm').on("click", "#btnImportFile", function(e){
		$('#importFileForm').on("submit", function(e){
			console.log("en on(click, #btnImportFile)");
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
			req.qid = currentQuestion;
			req.oid = currentOption;
			req.ogid = currentOptionGroup;
			console.log("currentQuestion "+currentQuestion);
			console.log("currentOption "+currentOption);
			if((currentQuestion<0) && (currentOption==0)){
				req.index = currentElement.attr('index');
				req.lang = $('#survey-language-version').val();
				req.otype = currentElement.closest('ul').attr('otype');
				console.log("req.index"+req.index);
				console.log("req.lang"+req.lang);
				console.log("req.otype"+req.otype);
			}
			
			if(type === "image")
			{				
				req.action = "options";
				req.resourceUrl = $('#previewFileUploaded').attr("urlValue");
				console.log(req.resourceUrl);
				req.resourceAltText = $('#resourceAltText').val();				
				req.rid = $('#rid').val();
			}
			else if(type === "video")
			{
				req.action = type;
				req.resourceDescText = $('#resourceDescText').val();
				req.resourceUrl = $('#resourceUrl').val();
			}
			
			console.log("req.resourceTitle-"+req.resourceTitle);
			console.log("req.resourceAltText-"+req.resourceAltText);
			console.log("req.resourceDescText-"+req.resourceDescText);
			console.log("req.resourceUrl-"+req.resourceUrl);
			console.log("type-"+type);
			
			if(((type === "image") && ((req.resourceTitle==="") || (req.resourceAltText===""))) || ((type === "video") && ((req.resourceTitle==="") || (req.resourceDescText==="") || ((req.resourceUrl === "") || ((!req.resourceUrl.startsWith("http://")) && (!req.resourceUrl.startsWith("https://"))))))){
				console.log("Show errors");
				if(type === "image"){
					hideFieldError($('#resourceDescText'));
					hideFieldError($('#resourceUrl'));
					
					if (req.resourceTitle==="")
						showFieldError($('#resourceTitle'));
					else
						hideFieldError($('#resourceTitle'));
					
					if (req.resourceAltText==="")
						showFieldError($('#resourceAltText'));
					else
						hideFieldError($('#resourceAltText'));					
				} 
				
				if(type === "video"){
					hideFieldError($('#resourceAltText'));
					
					if (req.resourceTitle==="")
						showFieldError($('#resourceTitle'));
					else
						hideFieldError($('#resourceTitle'));
					
					if (req.resourceDescText==="")
						showFieldError($('#resourceDescText'));
					else
						hideFieldError($('#resourceDescText'));
					
					if ((req.resourceUrl==="") || ((!req.resourceUrl.startsWith("http://")) && (!req.resourceUrl.startsWith("https://"))))
						showFieldError($('#resourceUrl'));
					else
						hideFieldError($('#resourceUrl'));
				}
				
				pending = false;
			}
			else{
				$.post('ImportFileServlet', req, function(res) {
		        	 $('#importFileForm')[0].reset();
		             $("#importFile").modal("hide");
		            if(currentQuestion>=0){
		            	console.log("En currentquestion");
			  			  var multimediaFrame = $("li[qid=" + currentQuestion + "]").find("div[id=multimediaFrameQuestion]");
			              multimediaFrame.removeClass("hidden");
			              multimediaFrame.find("div.question-files-frame").removeClass("hidden");
			              multimediaFrame.find("ul[id=multimediaFilesList]").append(res);
			        }else if(currentOption>0){
			        	console.log("En currentoption");
			        	  //var multimediaFrame = $("li[oid=" + currentOption + "]").find("div[id=multimediaFrame]");
			        	  var multimediaFrame = $("input[oid=" + currentOption + "]").parent("li").find("div[id=multimediaFrame]");
			              multimediaFrame.removeClass("hidden");
			              multimediaFrame.find("div.options-files-frame").removeClass("hidden");
			              multimediaFrame.find("ul[id=multimediaFilesList]").empty();
			              multimediaFrame.find("ul[id=multimediaFilesList]").append(res);	

			              $("input[oid=" + currentOption + "]").closest("li.option-item").find('button.add-file-option').focus();
			              
			        }
			        else{
			        	var oid = $(res).attr('oid');
			        	var ogid = $(res).attr('ogid');
			        	currentElement.attr('oid', oid);
			        	currentElement.closest('ul').attr('ogid', ogid);
			        	
						
			        	  //var multimediaFrame = $("li[oid=" + currentOption + "]").find("div[id=multimediaFrame]");
			        	  var multimediaFrame = $("input[oid=" + oid + "]").parent("li").find("div[id=multimediaFrame]");
			              multimediaFrame.removeClass("hidden");
			              multimediaFrame.find("div.options-files-frame").removeClass("hidden");
			              multimediaFrame.find("ul[id=multimediaFilesList]").empty();
			              multimediaFrame.find("ul[id=multimediaFilesList]").append(res);
			              multimediaFrame.find("ul[id=multimediaFilesList]").find("li[oid]").removeAttr('oid');
			              multimediaFrame.find("ul[id=multimediaFilesList]").find("li[ogid]").removeAttr('ogid');
      
			              if(currentElement.val() === "")
			              {
			            	  currentElement.trigger("goto");
			            	  currentElement.trigger("setJson");
			              }
			              			      		
			        }
		            $('#optionsFile').empty();
		              $('#optionsFile').addClass('hidden');
		              $('#selectFile').addClass("hidden");
		              $('#optionsVideoFile').addClass("hidden");
		              pending = false;
		  		});
			}
		});
	});
	
	$('#selectFiteType').on("change", "#fileType", function(e){
		var type = $(this).val();
		if(type === "video")
		{
			$('#selectFile').addClass("hidden");
			$('#optionsFile').empty();
			$('#optionsFile').addClass("hidden");
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
		
		if(($('#rTitle').val()!="") && ((currentElement.rType === "image" && $('#rAltText').val() != "") || (currentElement.rType === "video" && $('#rDescText').val() != ""))){
			hideFieldError($('#rTitle'));
			hideFieldError($('#rAltText'));
			hideFieldError($('#rDescText'));
		
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
				
				var serviceUrl = host + urlBase + "/api/ResourceService/updateContent";
				
				console.log("Resource update content: " + JSON.stringify(req));
				
				updateContent(req, serviceUrl);
				
				var multimediaElem = $('li[rid=' + currentElement.rId + ']').find('#editFile');
				multimediaElem.attr('data-image', JSON.stringify(currentElement));
				
				if(changedTitle != '')
				{
					var fileName = multimediaElem.html().split('-')[1];
					multimediaElem.html(changedTitle + " - " + fileName);
					
					currentAddNode.trigger("goto");
					currentAddNode.trigger("setJson");
				}
				
				
			}		
			
			$("#previewVideo").addClass("hidden");
			$("#resDescText").addClass("hidden");
			$("#previewImage").addClass("hidden");
			$("#resourceAltText").addClass("hidden");					
			$("#updateFile").modal("hide");
		}
		else{
			if($('#rTitle').val() === "")
				showFieldError($('#rTitle'));
			else
				hideFieldError($('#rTitle'));			
			
			if(currentElement.rType === "image" && $('#rAltText').val() === "")
				showFieldError($('#rAltText'));
			else
				hideFieldError($('#rAltText'));
			
			if(currentElement.rType === "video" && $('#rDescText').val() === "")
				showFieldError($('#rDescText'));
			else
				hideFieldError($('#rDescText'));
		}
	});
	
	$('#updateFilesSection').on("click", "#btnCancelUpdateFile", function(e){
		$("#previewVideo").addClass("hidden");
		$("#resDescText").addClass("hidden");
		$("#previewImage").addClass("hidden");
		$("#resourceAltText").addClass("hidden");					
		$("#updateFile").modal("hide");		
	});
	
	$('#updateFilesSection').on("focusout", "#rTitle", function(e){
		e.stopPropagation();		
		
		console.log("focusout req.rTitle-"+$('#rTitle').val());
		
		if($('#rTitle').val() === ""){
			showFieldError($('#rTitle'));
		}else{
			hideFieldError($('#rTitle'));
		}
		
	});
	
	
	$('#updateFilesSection').on("focusout", "#rAltText", function(e){
		e.stopPropagation();		
		
		console.log("focusout req.rAltText-"+$("#rAltText").val());
		
		if($("#rAltText").val() === ""){
			showFieldError($('#rAltText'));
		}else{
			hideFieldError($('#rAltText'));
		}
		
	});
	
	
	$('#updateFilesSection').on("focusout", "#rDescText", function(e){
		e.stopPropagation();		
		
		console.log("focusout req.rDescText-"+$("#rDescText").val());
		
		if($("#rDescText").val() === ""){
			showFieldError($('#rDescText'));
		}else{
			hideFieldError($('#rDescText'));
		}
		
	});
	
	
	$('#updateFilesSection').on("focusin", "#rTitle", function(e){
		e.stopPropagation();
		console.log("focusin rTitle");
		hideFieldError($('#rTitle'));
		
	});
	
	
	$('#updateFilesSection').on("focusin", "#rAltText", function(e){
		e.stopPropagation();
		console.log("focusin rAltText");	
		
		hideFieldError($('#rAltText'));
		
	});
	
	
	$('#updateFilesSection').on("focusin", "#rDescText", function(e){
		e.stopPropagation();
		console.log("focusin rDescText");	
		
		hideFieldError($('#rDescText'));
		
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
	
	$('.survey-sections').on("click", "#btn-question-import-file", function(e){
		currentOption = -1;
		currentOptionGroup = -1;
		currentQuestion = $(this).closest('li[id=panel-question1]').attr('qid');
		currentLanguage = $('#survey-language-version').val();
		console.log("current question: " + currentQuestion + " - language: " + currentLanguage);
	});
	
	
	$('.survey-sections').on("click", ".add-file-option", function(e){
		
		currentQuestion = -1*$(this).closest('li[id=panel-question1]').attr('qid');
		currentOption = $(this).closest('li').find('input.option').attr('oid');
		currentOptionGroup = $(this).closest('ul').attr('ogid');
		currentElement = $(this).closest('li').find('input.option');
		
		/*if(currentOption>0){*/
			currentLanguage = $('#survey-language-version').val();
			console.log("current question: " + currentQuestion + " - language: " + currentLanguage);
			$("#divType").val('Option');
			$('#importFile').modal();
		/*}else{
			alert("First, complete the option");
		}*/
			
		modalFocus = $(this);
	});
			
	$('.survey-sections').on("change", "select.type-matrix", function(e){
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
			   url: host + urlBase + "/api/QuestionService/updateParameters",
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
	
	$('.survey-sections').on("change", "select.type-simple-answer", function(e){
		e.stopPropagation();
		var node = $(this);
		var change = true;
		var qtype = $(this).val();
				
		if(qtype === "select")
		{		
			node.closest("div.panel-question-content").find("ul.option-list").find("div#multimediaFrame").each(function(indice, element){
				change = change && $(element).hasClass("hidden");
			});
		}
		
		if(change)
		{
			var req = {};
			req.qid = node.closest('li.panel-question').attr('qid');
			req.pid = node.closest('li.page').attr('pid');
			req.text = qtype;
			$.ajax({ 
				   type: "PUT",
				   dataType: "text",
				   contentType: "text/plain",
				   url: host + urlBase + "/api/QuestionService/updateSimpleTypeAnswer",
				   data: JSON.stringify(req),
				   success: function (data) {
					   console.log(data);
					   var json = JSON.parse(data);
					   node.closest("div.question-options").find("p.question-type-aux").html(json.questionTypeText);
					   if(json.questionType === "simpleRadio")
					   {
						   node.closest("div.panel-question-content").find("button.addFileOption").each(function(indice, element){$(element).removeClass("hidden");});
						   node.closest("div.panel-question-content").find("label.addFileOption").each(function(indice, element){$(element).removeClass("hidden");});
						   
						   node.closest("div.panel-question-content").find("ul.option-list").find("ul#multimediaFilesList").each(function(indice, element){
							   if($(element).find("li").length > 0)
							   {
								   $(element).closest("div#multimediaFrame").removeClass("hidden");
							   }
						   });
					   }
					   else if(json.questionType === "simpleCombo")
					   {
						   node.closest("div.panel-question-content").find("button.addFileOption").each(function(indice, element){$(element).addClass("hidden");});
						   node.closest("div.panel-question-content").find("label.addFileOption").each(function(indice, element){$(element).addClass("hidden");});
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
		else
		{
			console.log("Ahh po no!");
			currentElement = node;
			$('#questioTypeSimple').modal("show");
		}
	});

	$('.survey-sections').on("click", ".remove-page-break", function(){
		var item = $(this).closest('li.page');
		currentElement = item;
		currentQuestion = item.attr('pid');
		$("#elementToRemoveText").html('"Page-break: ' + item.find('.page-head h4 span').html() + '"');
		$("#removeElemId").val(currentQuestion + '/' + $('#survey-info').attr('sid'));
		$("#removeElemService").val('PageService');
		$("#removeElement").modal("show");
		modalFocus = $(this);
	});
	
	$('.survey-sections').on("click", ".remove-section", function(e){
		var item = $(this).closest('li.panel-section');
		currentQuestion = item.attr('scid');
		currentElement = item;
		$("#confirmRemoveSection").removeClass("hidden");
		$("#elementToRemoveText").html('"Section: ' + item.find('input.survey-section-title').val() + '"');
		$("#removeElemId").val(item.attr('scid') + '/' + $('#survey-info').attr('sid'));
		$("#removeElemService").val('SectionService');
		$("#removeElement").modal("show");
		modalFocus = $(this);
	});
	
	$('.survey-sections').on("click", "#removeQuestion", function(e){
		var item = $(this).closest('#panel-question1');
		var pageId = $(this).closest('li.page').attr('pid');
		currentQuestion = item.attr('qid');
		var qDependence = [];
		var valor = pageId + "-" + currentQuestion;
		$('.dependence-question').each(function(index, selectDepend){
			//console.log("Select index: " + index + " - val: " + $(selectDepend).val());
			if($(selectDepend).val() == valor)
			{
				console.log("Dependence question " + index + ": " + $(selectDepend).closest("li.panel-question").find("span.num-question").html());
				qDependence.push($(selectDepend).closest("li.panel-question").find("span.num-question").html());
			}
		});
		qDependence = $.unique(qDependence);
		
		var qLogic = [];
		$('.logic-option-goto').each(function(index, selectLogic){
			if($(selectLogic).val() == currentQuestion)
			{
				qLogic.push($(selectLogic).closest("li.panel-question").find("span.num-question").html());
			}
		});
		qlogic = $.unique(qLogic);
		
		if(qDependence.length > 0)
		{
			$("#confirmRemoveDep").removeClass("hidden");
			var depCad = "";
			for(var i = 0; i < qDependence.length; i++)
			{
				if((i + 1) == qDependence.length && i > 0) depCad = depCad + " y ";
				else if(i > 0) depCad = depCad + ", ";
				depCad = depCad + qDependence[i];
			}
			$("#confirmRemoveQuestionDepList").html(depCad);
			if(qDependence.length == 1)
			{
				$("#questionDSingular").removeClass("hidden");
				$("#questionDPlural").addClass("hidden");
				$("#questionDHas").removeClass("hidden");
				$("#questionDHave").addClass("hidden");
			}
			else
			{
				$("#questionDSingular").addClass("hidden");
				$("#questionDPlural").removeClass("hidden");
				$("#questionDHas").addClass("hidden");
				$("#questionDHave").removeClass("hidden");
			}
			$("#confirmRemoveRule").removeClass("hidden");
		}
		else
		{
			$("#confirmRemoveDep").addClass("hidden");
		}

		if(qLogic.length > 0)
		{
			$("#confirmRemovelogic").removeClass("hidden");
			var logCad = "";
			
			for(var i = 0; i < qLogic.length; i++)
			{
				if((i + 1) == qLogic.length && i > 0) logCad = logCad + " y ";
				else if(i > 0) logCad = logCad + ", ";
				logCad = logCad + qLogic[i];
			}
			$("#confirmRemoveQuestionLogicList").html(logCad);
			if(qLogic.length == 1)
			{
				$("#questionLSingular").removeClass("hidden");
				$("#questionLPlural").addClass("hidden");
				$("#questionLHas").removeClass("hidden");
				$("#questionLHave").addClass("hidden");
			}
			else
			{
				$("#questionLSingular").addClass("hidden");
				$("#questionLPlural").removeClass("hidden");
				$("#questionLHas").addClass("hidden");
				$("#questionLHave").removeClass("hidden");
			}
			$("#confirmRemoveRule").removeClass("hidden");
		}
		else
		{
			$("#confirmRemovelogic").addClass("hidden");
		}
		
		if(qDependence.length == 0 && qLogic.length == 0) $("#confirmRemoveRule").addClass("hidden");
		
		$("#elementToRemoveText").html('"Question: ' + item.find('#survey-question-title').val() + '"');
		$("#removeElemId").val(item.attr('qid') + '/' + item.closest('li[id=page]').attr('pid'));
		$("#removeElemService").val('QuestionService');
		$("#removeElement").modal("show");
		modalFocus = $(this);		
	});
	
	$('.section-pages').on("click", "#removeMultimediaFile", function(e){
		var item = $(this).closest('li.multimedia-item');
		if($(this).closest('li.option-item').find(".option").val()==""){
			$(this).closest('li.option-item').find(".remove-option").trigger("click");
		}
		else{		
			$("#elementToRemoveText").html('"' + item.find('a').text() + '"');
			$("#removeElemId").val(item.attr('rid'));
			$("#removeElemService").val('ResourceService');
			$("#removeElement").modal("show");
		}
		modalFocus = $(this);
	});
	
	$('.section-pages').on("click", "#removeMultimediaFileQuestion", function(e){
		var item = $(this).closest('li.multimedia-item');
		$("#elementToRemoveText").html('"' + item.find('a').text() + '"');
		$("#removeElemId").val(item.attr('rid'));
		$("#removeElemService").val('ResourceServiceQuestion');
		$("#removeElement").modal("show");
		modalFocus = $(this);
	});
	
	$('.section-pages').on("click", ".remove-option", function(e){
		console.log("Remove option");
		currentQuestion = $(this).closest('#panel-question1').attr('qid');		
		var item = $(this).closest('li');
		var ogid = item.closest('ul').attr('ogid');
		var input = item.find('input');
		var oid = input.attr('oid');
		var result = currentQuestion + "/" + ogid + "/" + oid;
		console.log("result: " + result);
		
		$("#elementToRemoveText").html('"'+phOption+': ' + input.val() + '"');
		$("#removeElemId").val(result);
		$("#removeElemService").val('OptionService');
		$("#removeQuestionId").val(currentQuestion);
		$("#removeElemIndex").val(input.attr('index'));
		$("#removeElement").modal("show");	
		modalFocus = $(this);	
	});
	
	$('.section-pages').on("click", "button.removeOptionOther", function(e){
		var req = {};
		var currentNode = $(this);
	    
		req.qid = currentNode.closest('li.panel-question').attr('qid');
		req.ogid = currentNode.closest('ul').attr('ogid');
		req.oid = currentNode.closest('li.option-item').attr('oid'); 
		
		$.ajax({ 
		   type: "DELETE",
		   dataType: "text",
		   contentType: "text/plain",
		   url: host + urlBase + "/api/OptionService/removeOptionOther",
		   data: JSON.stringify(req),
		   success: function (data) {
			   if(data == "true")
			   {
				   currentNode.closest('li.option-item').find("input.otherOptionTitle").trigger("rmvOptOtherJson")
				   var optionElem = currentNode.closest("li.option-item");				   
				   optionElem.addClass("hidden");
				   var titleOther = optionElem.find("legend").html();
				   optionElem.find("input.otherOptionTitle").val(titleOther);
				   optionElem.find("input.survey-question-max-chars").val("");
				   optionElem.find("input.isLimitedChars").prop("checked", false);
				   optionElem.find("input.isLimitedChars").attr('active', false);
				   optionElem.find('#charsId').attr('class','question-response-settings-sub-none');
				   optionElem.find("input.adjust-lines-adjust").prop("checked", true);
				   optionElem.find("input.adjust-lines-set").prop("checked", false);
				   optionElem.find('#lines').attr('class','question-response-settings-sub-none');
				   optionElem.find("input.survey-question-max-lines").val("");
				   optionElem.next().find("button.btnAddOptionOther").prop( "disabled", false );
				   
				   var logicOptionElement = $('#logic-option-' + req.oid);
				   removeLogicElement(logicOptionElement);
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
	
	$('.section-pages').on("click", ".remove-optionmatrix", function(e){
		console.log("Remove option matrix");
		currentQuestion = $(this).closest('#panel-question1').attr('qid');
		var item = $(this).closest('li');
		var input = item.find('input');
		$("#elementToRemoveText").html('"'+phColumn+': ' + input.val() + '"');
		$("#removeElemId").val(input.attr('oid'));
		$("#removeElemIndex").val(input.attr('index'));
		$("#removeQuestionId").val(currentQuestion);
		$("#removeElemService").val('OptionMatrixService');
		$("#removeElement").modal("show");
		modalFocus = $(this);
	});
	
	$('.section-pages').on("click", ".remove-optionsgroupmatrix", function(e){
		console.log("Remove optionsgroup matrix");
		currentQuestion = $(this).closest('#panel-question1').attr('qid');
		var item = $(this).closest('li');
		var input = item.find('input');
		$("#elementToRemoveText").html('"'+phItem+': ' + input.val() + '"');
		$("#removeElemId").val(input.attr('ogid'));
		$("#removeElemIndex").val(input.attr('index'));
		$("#removeQuestionId").val(currentQuestion);
		$("#removeElemService").val('OptionsGroupMatrixService');
		$("#removeElement").modal("show");
		modalFocus = $(this);
	});
	
	
	$('.section-pages').on("click", "#accesibilityHelp", function(e){
		console.log("Show shortcuts for editor");
		$("#accesibilityHelpWin").modal("show");
		modalFocus = $(this);
	});
	
	
	deleteQuote();
	
	$('#removeElement').on("click", "#acceptRemoveElement", function(e){
		
		var elementId = $('#removeElemId').val(); 
		var questionId = $('#removeQuestionId').val(); 
		var elementIndex = $('#removeElemIndex').val(); 
		var service = $("#removeElemService").val();
		console.log("Resource ID: " + elementId+", service: "+service);
		console.log(host + urlBase + "/api/" + service + "/" + elementId);
		//console.log("number items: " + $('li[rid=' + resourceId + ']').closest("ul").find("li").size());
		var removeFileQuestion = false;
		if (service == "ResourceServiceQuestion"){
			removeFileQuestion = true;
			service = "ResourceService";
		}
		console.log("Resource ID: " + elementId+", service: "+service);	
		$.ajax({ 
		   url: host + urlBase + "/api/" + service + "/" + elementId,
		   type: "DELETE",
		   success: function (data) {
			   console.log("data: "+data+", service: "+service);
			   if(data == 'true')
			   {
				   $("#removeElement").modal("hide");
				   if(service == "ResourceService")
				   {
					   console.log("ResourceService");
					   if(removeFileQuestion){
						   var addResourceButton = $('li[rid=' + elementId + ']').closest("div.div_files").find('button.btn-question-import-file');
						   console.log("It is a question");
						   console.log("Number of elements: "+$('li[rid=' + elementId + ']').closest('div.question-files-frame').find('#multimediaFilesList li').length);
						   if($('li[rid=' + elementId + ']').closest('div.question-files-frame').find('#multimediaFilesList li').length<2){
							   console.log("hidden a class");
							   $('li[rid=' + elementId + ']').closest('#multimediaFrame').addClass('hidden');
							   $('li[rid=' + elementId + ']').closest('div.question-files-frame').addClass('hidden');
						   }
						   console.log("Number of elements: "+$('li[rid=' + elementId + ']').closest('div.question-files-frame').find('#multimediaFilesList li').length);
						   
						   $('li[rid=' + elementId + ']').remove();
						   
						   modalFocus = addResourceButton;
					   } else{
						   console.log("It is an option");
						   var addResourceButton = $('li[rid=' + elementId + ']').closest("li.option-item").find('button.add-file-option');
						   $('li[rid=' + elementId + ']').closest('#multimediaFrame').addClass('hidden');
						   $('li[rid=' + elementId + ']').closest('div.options-files-frame').addClass('hidden');
						   
						   $('li[rid=' + elementId + ']').remove();
						   
						   modalFocus = addResourceButton;
					   }
				   }
				   else if(service == "QuestionService")
				   {
					   $('li[qid="' + currentQuestion + '"]').trigger('rmvQuestionJson');
					   var questionList = $('li[qid="' + currentQuestion + '"]').closest('ul');
					   var nextQuestion = null;
					   if(!$('li[qid="' + currentQuestion + '"]').is(':last-child')) nextQuestion = $('li[qid="' + currentQuestion + '"]').next();
					   
					   $('li[qid="' + currentQuestion + '"]').remove();
					   questionList.find('li[qid]').each(function(i, elem)
					   {
						   console.log("Option Index li: " + i + " - elem: " + $(elem).find('input').val());
						   var index = i + 1;
						   $(elem).attr('index', index);
					   });
					   
					   if(nextQuestion != null) modalFocus = nextQuestion.find("h5"); 
					   else modalFocus = questionList.closest("div.page-body").find('button.btn-add');
					   
						var numQuestion = 1;
						$('#survey-sections').find("li.panel-question").each(function(i, element){
							if(!$(element).hasClass("bcontent"))
							{
								$(element).find('span.num-question').html(numQuestion);
								numQuestion++;
							}
						});					   
				   }
				   else if(service == "OptionService")
				   {   
					   var ids = elementId.split('/');
					   var oid = ids[2];
					   var input = $('li[qid=' + currentQuestion + '] ul[id=option-list] li[id=option-item]').find('input[oid=' + oid + '][index='+elementIndex+']');
					   //var input = $('input[oid=' + oid + '][index='+elementIndex+']'); 					   
					   var numItems = input.closest("ul").find("li.option-item").size();
					   var list = input.closest("ul.option-list");
					   
					   input.trigger("rmvOptJson");
						
					   console.log("Items: " + numItems);
					   if(numItems > 2)
					   {
						   input.closest("li").remove();
						   $('li[qid=' + currentQuestion + '] ul[id=option-list] li[id=option-item]').each(function(i, elem)
						   {
							   console.log("li: " + i + " - elem: " + $(elem).find('input').val());
							   var index = i + 1;
							   $(elem).find('input').attr('index', index);
							   $(elem).find('input').attr('placeholder', phOption+" " + index)
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
					   
					   var logicOptionElement = $('#logic-option-' + oid);
					   removeLogicElement(logicOptionElement);	
					   
					   console.log("Delete option: ")
					   modalFocus = list.first().find("input[index=1]");
				   }
				   else if(service == "OptionMatrixService")
				   {
					   //var input = $('input[oid=' + elementId + '][index='+elementIndex+']'); 					   
					   var input = $('li[qid=' + currentQuestion + '] ul[id=optionmatrix-list] li[id=optionmatrix-item]').find('input[oid=' + elementId + '][index='+elementIndex+']');
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
							   $(elem).find('input').attr('placeholder', phColumn+ " " + index)
							   $(elem).find('.circle-grey').text(index);
						   });
					   }
					   else
					   {
						   input.val("");
						   input.attr('oid', '0');
					   }
				   }
				   else if(service == "OptionsGroupMatrixService")
				   {
					   //var input = $('input[ogid=' + elementId + '][index='+elementIndex+']'); 					   
					   var input = $('li[qid=' + currentQuestion + '] ul[id=optionsgroupmatrix-list] li[id=optionsgroupmatrix-item]').find('input[ogid=' + elementId + '][index='+elementIndex+']');
					   var numItems = input.closest("ul").find("li").size();
					   console.log("Items: " + numItems);
					   if(numItems > 3)
					   {
						   input.closest("li").remove();
						   $('li[qid=' + currentQuestion + '] ul[id=optionsgroupmatrix-list] li[id=optionsgroupmatrix-item]').each(function(i, elem)
						   {
							   //console.log("li: " + i + " - elem: " + $(elem).find('input').val());
							   var index = i + 1;
							   $(elem).find('input').attr('index', index);
							   $(elem).find('input').attr('placeholder', phItem + " " + index)
							   $(elem).find('.circle-grey').text(index);
						   });
					   }
					   else
					   {
						   console.log("input"+input.attr('ogid'));
						   input.val("");
						   input.attr('ogid', '0');
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
					   $('li[scid=' + ids[0] + ']').find('input[id=survey-section-title-' + ids[0] + ']').val('Section 1');
				   }
				   else if(service == "PageService")
				   {
					   //var pagesList = currentElement.closest('ul.section-pages');
					   var currentPageId = parseInt(currentElement.attr("pid"));
					   removeLastPage(currentElement, currentPageId);
					   var currentNumPage = parseInt(currentElement.attr("index"));
					   var sectionList = currentElement.closest('ul.survey-sections');
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
					   /*var i = parseInt(pagesList.find('li.page').first().attr('index'));
					   pagesList.find('li.page').each(function(indice, elemento) {
							var cads = $(elemento).find('h4').html().split(' ');
							$(elemento).attr('index', i + indice);
							$(elemento).find('h4').html(cads[0] + " " + (i + indice));
						});*/
					   
					   var i = 1;
					   sectionList.find("li.panel-section").each(function(indexSection, section){
						   $(section).find('li.page').each(function(indexPage, page){
							   var cads = $(page).find('h4 > span').html().split(' ');
								$(page).attr('index', i);
								$(page).find('h4 > span').html(cads[0] + " " + i);
								i++;
						   });
					   });
					   
					   modalFocus = prevElement.find("h4");
					   
					   removePageJson(currentNumPage, currentPageId);
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
						  var nextDep = null;
						  if(!item.is(':last-child')) nextDep = item.next();
						   
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
								  (item.closest("ul").find("li:nth-child(4)")).find("select.dependence-condition").val((item.closest("ul").find("li:nth-child(3)")).find("select.dependence-condition").val());
								  (item.closest("ul").find("li:nth-child(4)")).find("#fieldset-dependence").removeClass("fieldset-next-dependences");	
								  (item.closest("ul").find("li:nth-child(4)")).find("#fieldset-dependence").attr("condition", "hide");
								  //(item.closest("ul").find("li:nth-child(4)")).find("#fieldset-dependence").addClass("fieldset-second-dependence");
							  }
							  else if(item.closest("ul").find("li:nth-child(3)").attr("index") == elementId.split("/")[1]){
								  console.log("Tercer hijo");
								  (item.closest("ul").find("li:nth-child(4)")).find("select.dependence-condition").removeClass("hidden");
								  (item.closest("ul").find("li:nth-child(4)")).find("select.dependence-condition").val((item.closest("ul").find("li:nth-child(3)")).find("select.dependence-condition").val());
								  (item.closest("ul").find("li:nth-child(4)")).find("#fieldset-dependence").removeClass("fieldset-next-dependences");	
								  (item.closest("ul").find("li:nth-child(4)")).find("#fieldset-dependence").attr("condition", "hide");
								  //(item.closest("ul").find("li:nth-child(4)")).find("#fieldset-dependence").addClass("fieldset-second-dependence");
							  }
							  item.remove();
						  }
						  

						   if(nextDep != null) modalFocus = nextDep.find("legend"); 
						   else modalFocus = question.find("div.dependences-settings").find('button.btn-primary');
						   
						   var rules = question.find("div.rules-frame");
						   setDepLabelCounter(rules);
					   }
				   }
				   else if(service == "QuotaService")
				   {
					   
					   $('#survey-quota-'+quotaid).remove();
				   }
			   }
			   else
			   {
				   $("#removeElement").modal("hide");
				   if(service == "SectionService")
				   {
					   var ids = elementId.split('/');

					   removePageCompleteJson(currentElement);
					  
					   $('li[scid=' + ids[0] + ']').find('li.panel-question').each(function(index, element){
						   console.log("QuestionId: " + $(element).attr("qid"));
						   $(element).trigger("rmvQuestionGoto");
						   $(element).trigger("rmvQuestionDepend");
					   });
					   					   
					   var i = 1;
					   var sectionList = $('li[scid=' + ids[0] + ']').closest("ul.survey-sections");
					   $('li[scid=' + ids[0] + ']').remove();
					   sectionList.find("li.panel-section").each(function(indexSection, section){
						   /*var cads = $(page).find('input.survey-section-title').val().split(' ');
						   $(section).find('input.survey-section-title').val(cads[0] + " " + i);*/
						   $(section).find('li.page').each(function(indexPage, page){
							   var cads = $(page).find('h4 > span').html().split(' ');
								$(page).attr('index', i);
								$(page).find('h4 > span').html(cads[0] + " " + i);
								i++;
						   });
					   });
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
		var serviceUrl = host + urlBase + "/api/SurveyService/updateContent";
		
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
		var serviceUrl = host + urlBase + "/api/SurveyService/updateContent";
		
		updateContent(req, serviceUrl);
	});
	
	$('#survey-info').on("focusout", "#surveyProject", function(e){
		e.stopPropagation();		
		var req = {};		
		req.project = $(this).val();
		req.sid = $(this).closest('#survey-info').attr('sid');		
		var serviceUrl = host + urlBase + "/api/SurveyService/updateProject";
		
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

	$('#survey-info').on("change", "#ipFilter", function(e){
		e.stopPropagation();		
		var req = {};		
		req.ipFilter = $(this).is(':checked');
		req.sid = $(this).closest('#survey-info').attr('sid');		
		var serviceUrl = host + urlBase + "/api/SurveyService/updateIpFilter";
		console.log("Entra en IP filger: " + $(this).is(':checked'));
		updateContent(req, serviceUrl);		
	});
	
	$('.survey-sections').on("focusout", "input.survey-section-title", function(e){
		e.stopPropagation();	
		var req = {};		
		req.text = $(this).val();
		req.contentType = "title";
		req.lan = $("#survey-language-version").val();
		req.scid = $(this).closest('#panel-section1').attr('scid');		
		var serviceUrl = host + urlBase + "/api/SectionService/updateContent";
		
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
	
	$('.survey-sections').on("click", "#helpTextButton", function(e){
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
			   url: host + urlBase + "/api/QuestionService/updateContent",
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
	
	$('.survey-sections').on("keyup", 'div.mandatory-toggle-div', function(e){
		if(e.keyCode == 13)
	    {
			var checkbox = $(this).siblings("input.mandatoryToggle");
			var checked = checkbox.is(":checked");
			if(checked)
			{
				checkbox.prop("checked", false);
			}
			else
			{
				checkbox.prop("checked", true);
			}
			checkbox.trigger("change");
	    }
	});
	
	$('.survey-sections').on("change", 'input.mandatoryToggle', function(e){
		e.stopPropagation();
		
		var checked = $(this).is(":checked");
		if(checked) $(this).closest("div").find("div.toggle").removeClass("off");
		else $(this).closest("div").find("div.toggle").addClass("off");		
	
		var node = $(this); 
		var req = {};
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + urlBase + "/api/QuestionService/updateMandatory",
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
	
	$('.survey-sections').on("click", "#askAlwaysButton", function(e){
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
			   url: host + urlBase + "/api/QuestionService/updateOptionalAnswer",
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
	
	$('.survey-sections').on("focusout", "input.survey-question-max-chars", function(e){
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
			   url: host + urlBase + "/api/QuestionService/updateTextLength",
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
	
	$('.survey-sections').on("focusout", "input.survey-question-decimals", function(e){
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
			   url: host + urlBase + "/api/QuestionService/updateDecimals",
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
	
	$('.survey-sections').on("change", "input.isLimitedChars", function(e){
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
		node.closest('#genericOptions').find('input.survey-question-max-chars').val('');
		
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + urlBase + "/api/QuestionService/updateTextLength",
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

	$('.survey-sections').on("focus", "select.likertType", function(e){
		currentValue = $(this).val();
	});
	
	$('.survey-sections').on("change", "select.likertType", function(e){
		e.stopPropagation();
		console.log("likertType");
		
		var hasLabel = false;
		
		$(this).closest("div.question-frame").find("input.point-label").each(function(i, elem){
			hasLabel = hasLabel || $(elem).val() != "";
		});
		
		if(hasLabel)
		{
			currentElement = $(this);			
			$("#scaleTypeConfirm").modal("show");
		}
		else
		{
			setScaleType($(this));
		}
	});
	

	$('.body-content').on("click", "button.btn-accept-change-qScaleType", function(){
		setScaleType(currentElement);
		$('#scaleTypeConfirm').modal('hide');
	});

	$('.body-content').on("click", "button.btn-cancel-change-qScaleType", function(){
		currentElement.val(currentValue);
		$('#scaleTypeConfirm').modal('hide');
	});
	
	
	$('.survey-sections').on("change", "input.range", function(e){
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
			node.closest('div.question-response-settings').find('#rangeId').find('input#survey-minValue'+node.closest('li[id=panel-question1]').attr('qid')).attr('active','');
			node.closest('div.question-response-settings').find('#rangeId').find('input#survey-minValue'+node.closest('li[id=panel-question1]').attr('qid')).value('');
			node.closest('div.question-response-settings').find('#rangeId').find('input#survey-maxValue'+node.closest('li[id=panel-question1]').attr('qid')).attr('active','');
			node.closest('div.question-response-settings').find('#rangeId').find('input#survey-maxValue'+node.closest('li[id=panel-question1]').attr('qid')).value('');
		}
		node.closest('#rangeOptions').find('#survey-minValue').val('');
		node.closest('#rangeOptions').find('#survey-maxValue').val('');
		
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + urlBase + "/api/QuestionService/updateMinValue",
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
			   url: host + urlBase + "/api/QuestionService/updateMaxValue",
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
	
	$('.survey-sections').on("change", "input.allowDecimals", function(e){
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
			node.closest('div.question-response-settings').find('#decimalsDiv').find('input#survey-question-decimals'+node.closest('li[id=panel-question1]').attr('qid')).attr('active','');
			node.closest('div.question-response-settings').find('#decimalsDiv').find('input#survey-question-decimals'+node.closest('li[id=panel-question1]').attr('qid')).value('');
		}
		node.closest('#decimalsOptions').find('#survey-question-decimals').val('');
		
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + urlBase + "/api/QuestionService/updateDecimals",
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
	
	$('.survey-sections').on("change", ".input-type", function(e){
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
			node.closest('div.row').find('input.survey-question-decimals').val("");
			node.closest('div.row').find('input.allowDecimals').attr("checked", false);
			node.closest('div.row').find('#decimalsDiv').addClass('hidden');
			node.closest('div.row').find('input.survey-minValue').val("");
			node.closest('div.row').find('input.survey-maxValue').val("");
			node.closest('div.row').find('input.range').attr("checked", false);
			node.closest('div.row').find('#rangeId').addClass('hidden');
		}else{
			console.log("isNotNumber");
			node.closest('div.row').find('#rangeOptions').addClass('hidden');
			node.closest('div.row').find('#decimalsOptions').addClass('hidden');
			node.closest('div.row').find('#genericOptions').removeClass('hidden');
			node.closest('div.row').find('input.survey-question-max-chars').val("");
			node.closest('div.row').find('input.isLimitedChars').attr("checked", false);
			node.closest('div.row').find('#charsId').addClass('hidden');
		}
		
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + urlBase + "/api/QuestionService/updateInputTypeMode",
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
	
	$('.survey-sections').on("focusout", "input.survey-question-max-lines", function(e){
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
			   url: host + urlBase + "/api/QuestionService/updateTextLines",
			   data: JSON.stringify(req),
			   success: function (data) {
				   console.log("data="+data);
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
	
	$('.survey-sections').on("change", "input.adjust-lines-adjust", function(e){
		e.stopPropagation();
		var node = $(this); 
		var req = {};
		console.log("OnClick on adjust-lines-adjust");
		node.closest('div.row').find('#lines').attr('class', 'question-response-settings-sub-none');
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = "";

		node.closest('div.row').find('input.survey-question-max-lines').val('');
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + urlBase + "/api/QuestionService/updateTextLines",
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
	
	$('.survey-sections').on("change", "input.adjust-lines-set", function(e){
		e.stopPropagation();
		var node = $(this); 
		var req = {};
		console.log("OnClick on adjust-lines-set");
		node.closest('div.question-response-settings').find('#lines').attr('class','question-response-settings-sub-inherit');
		req.qid = node.closest('li[id=panel-question1]').attr('qid');
		req.pid = node.closest('li[id=page]').attr('pid');
		req.text = "";

		node.closest('div.question-response-settings').find('input.survey-question-max-lines').val('');
		
		$.ajax({ 
			   type: "PUT",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + urlBase + "/api/QuestionService/updateTextLines",
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
	
	$('.survey-sections').on("focusout", "input.survey-minValue", function(e){
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
			   url: host + urlBase + "/api/QuestionService/updateMinValue",
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
	
	$('.survey-sections').on("focusout", "input.survey-maxValue", function(e){
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
			   url: host + urlBase + "/api/QuestionService/updateMaxValue",
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
		var serviceUrl = host + urlBase + "/api/QuestionService/updateContent";
		
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

	$('.survey-sections').on("focusout", "input.point-label", function(e){
		
		console.log("Question update content entra");
		e.stopPropagation();
		var req = {};		
		req.text = $(this).val();
		req.contentType = "label";
		req.lan = $("#survey-language-version").val();
		req.qid = $(this).closest('#panel-question1').attr('qid');	
		req.index = $(this).attr("index");
		var serviceUrl = host + urlBase + "/api/QuestionService/updatePointLabel";
		
		updateContent(req, serviceUrl);		
	});

	$('.survey-sections').on("focusout", "#survey-question-description-text", function(e){
		e.stopPropagation();
		var req = {};		
		req.text = $(this).val();
		req.contentType = "description";
		req.lan = $("#survey-language-version").val();
		req.qid = $(this).closest('#panel-question1').attr('qid');		
		var serviceUrl = host + urlBase + "/api/QuestionService/updateContent";
		
		updateContent(req, serviceUrl);
	});	

	$('.survey-sections').on("focusout", ".editor", function(e){
		e.stopPropagation();
		var req = {};		
		req.text = $(this).html();
		if(($(this).html()===placeholderBContent)||($(this).html()==="")){
			console.log("Empty field");
			$(this).html(placeholderBContent);
		}else{
			console.log("valor del editor:"+$(this).html());
			req.contentType = "description";
			req.lan = $("#survey-language-version").val();
			req.qid = $(this).closest('#panel-question1').attr('qid');		
			var serviceUrl = host + urlBase + "/api/QuestionService/updateContent";
			
			updateContent(req, serviceUrl);
		}
	});	
	
	$('.survey-sections').on("focusin", ".editor", function(e){
		e.stopPropagation();
		console.log($(this).html()+" --> and placeholder: "+placeholderBContent);
		if(($(this).html()===placeholderBContent)||($(this).html()==="")){
			$(this).html("");
		}
	});	
	
	$('.survey-sections').on("focusout", "#survey-question-other-text", function(e){
		e.stopPropagation();
		var req = {};		
		req.text = $(this).val();
		req.contentType = "other";
		req.lan = "en";
		req.qid = $(this).closest('#panel-question1').attr('qid');		
		var serviceUrl = host + urlBase + "/api/QuestionService/updateContent";
		
		updateContent(req, serviceUrl);
	});	
	
	$(".survey-sections").on('keydown', 'div.editor', function(e) {
		var keyCode = e.keyCode || e.which; 
		
		keyBuffer[keyCode] = true;

		if (keyBuffer[27] && keyBuffer[16]) { 
			e.preventDefault(); 
			console.log("Shift + Esc pressed");
			// call custom function here
			
			var helpButton = $(this).closest("div.rowPlus").find("a.accesibilityHelp");
			helpButton.focus();
			
			keyBuffer = {};
			
			//$(this).next().focus();
		} 
	});

	$(".survey-sections").on('keyup', 'div.editor', function(e) {
		console.log("Key pressed: " + JSON.stringify(keyBuffer));  
		var keyCode = e.keyCode || e.which; 

		if (keyBuffer[27] && !keyBuffer[16]) { 
			e.preventDefault(); 
			console.log("Only Esc key pressed");
			// call custom function here
			
			var filesDiv = $(this).closest("div.question-frame").find("div.question-files-frame");
			if(filesDiv.hasClass('hidden')) $(this).closest("div.question-frame").find("button.btn-question-import-file").focus();
			else filesDiv.find("ul").first().find('a').focus();

			keyBuffer = {};
			
			//$(this).next().focus();
		}
		
		delete keyBuffer[keyCode];
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
	
	$('.main-sidebar').on("click", "#quotas-menu", function(e){
		
		$('.content-statistics').addClass('hidden');
		
		var generalInfoDiv = $(this).closest("#statistics").find('div.content-wrapper').find('#quotas-info');
		generalInfoDiv.removeClass('hidden');
		
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
				   url: host + urlBase + "/api/QuestionService/updateIndex",
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
	$('.body-content').on("click", "button.btn-accept-move-element", function(){
		console.log("Acepta mover ");
		var question = currentElement;
		var currentPage = question.closest("li.page");
				
		var moveType = $("#moveType").val();
		if(moveType === "up")
		{
			var simpleQuestions = getSimpleQuestions(previousPage.attr("index"));
			
			question.find("select.dependence-question").each(function(index, element){
				var values = $(element).val().split("-");
				if($.inArray(parseInt(values[1]), simpleQuestions) != -1)
				{
					var trashButton = $(element).closest("fieldset").find("button#remove-dependence");
					trashButton.trigger("rmvNoConfirm");
				}
			});
			
			var questionId = question.attr("qid");
			previousPage.find("select.logic-option-goto").each(function(index, element){
				var value = $(element).val();
				if(value == questionId)
				{
					$(element).val("none");
					$(element).trigger("change");
				}
			});
			
			moveQuestionPrevPage(previousPage, currentPage, question);
			question.find("div.rules-frame").trigger("displayLogic");
			question.find("fieldset.dependences-frame").trigger("displayDependences");
			$("#moveQuestion").modal('hide');
			question.find("button.moveup-question-arrow").focus();
		}
		else if(moveType === "down")
		{
			var idQuestions = getAllQuestions(nextPage.attr("index"));
			var questionId = question.attr("qid");
			
			nextPage.find("select.dependence-question").each(function(index, element){
				var values = $(element).val().split("-");
				if(values[1] === questionId)
				{
					var trashButton = $(element).closest("fieldset").find("button#remove-dependence");
					trashButton.trigger("rmvNoConfirm");
				}
			});
			
			question.find("select.logic-option-goto").each(function(index, element){
				var value = $(element).val();
				if($.inArray(parseInt(value), idQuestions) != -1)
				{
					$(element).val("none");
					$(element).trigger("change");
				}
			});
			
			moveQuestionNextPage(previousPage, currentPage, question);
			question.find("div.rules-frame").trigger("displayLogic");
			question.find("fieldset.dependences-frame").trigger("displayDependences");
			question.find("fieldset.logic-frame").trigger("setLogicMoved");
			$("#moveQuestion").modal('hide');
			question.find("button.movedown-question-arrow").focus();
		}
	});
	
	$('.body-content').on("click", "button.btn-accept-change-sqType", function(){
		currentElement.closest("div.panel-question-content").find("ul.option-list").find("div#multimediaFrame").each(function(indice, element){
			$(element).addClass("hidden");
		});
		$('#questioTypeSimple').modal('hide');
		currentElement.trigger("change");
	});

	$('.body-content').on("click", "button.btn-cancel-change-sqType", function(){
		currentElement.val("radio");
		$('#questioTypeSimple').modal('hide');
	});
	
	$('.survey-sections').on("click", "button.moveup-question-arrow", function(){
		var question = $(this).closest("li.panel-question");
		currentElement = question;
		console.log("question moving up");
		var currentPage = question.closest("li.page");
		var questionJson = surveyTree[parseInt(currentPage.attr("index")) - 1].questions[parseInt(question.attr("index")) - 1];
		//question.trigger('rmvQuestionJsonNoDepLog');
		var isModal = false;
		if(question.is(':first-child'))
		{
			
			if(!currentPage.is(':first-child'))
			{
				previousPage = currentPage.prev();
				isModal = executeBtnMoveup(previousPage, currentPage, question);				
			}
			else
			{
				var currentSection = currentPage.closest("li.panel-section");
				if(!currentSection.is(':first-child'))
				{
					previousPage = currentSection.prev().find('li.page').last();
					//moveQuestionPrevPage(previousPage, currentPage, question);
					isModal = executeBtnMoveup(previousPage, currentPage, question);
				}
				
			}
		}
		else
		{
			question.trigger('rmvQuestionJsonNoDepLog');
			var prevQuestion = question.prev();
			prevQuestion.insertAfter(question);
			var index = parseInt(question.attr("index"));
			question.attr("index", index - 1);
			questionJson.index = index - 2;
			var prevIndex = parseInt(prevQuestion.attr("index"));
			prevQuestion.attr("index", prevIndex + 1);
			
			if(!prevQuestion.hasClass("bcontent"))
			{
				var numQuestion = prevQuestion.find("span.num-question").html();
				question.find("span.num-question").html(numQuestion);
				prevQuestion.find("span.num-question").html(parseInt(numQuestion)+1);
			}
			
			var prevQid = 0;
			if(!question.is(':first-child')) prevQid = question.prev().attr("qid");
				
			updateQuestionIndex(question.attr("qid"), prevQid, question.closest("li.page").attr("pid"), false, "up", host);
			question.trigger('insertQuestionJson', [questionJson]);
		}

		if(!isModal)
		{
			console.log("question moved: " + JSON.stringify(questionJson));
			//question.trigger('rmvQuestionJsonNoDepLog');
			//question.trigger('insertQuestionJson', [questionJson]);
			question.find("div.rules-frame").trigger("displayLogic");
			question.find("fieldset.dependences-frame").trigger("displayDependences");
			$(this).focus();
		}		
		else isModal = false;
	});
	
	$('.survey-sections').on("click", "button.movedown-question-arrow", function(){
		var question = $(this).closest("li.panel-question");
		var currentPage = question.closest("li.page");
		var questionJson = surveyTree[parseInt(currentPage.attr("index")) - 1].questions[parseInt(question.attr("index")) - 1];
		console.log("question to move: " + JSON.stringify(questionJson));
		//question.trigger('rmvQuestionJsonNoDepLog');
		var isModal = false;
		if(question.is(':last-child'))
		{
			if(!currentPage.is(':last-child'))
			{
				console.log("no last question");
				nextPage = currentPage.next();

				isModal = executeBtnMovedown(nextPage, currentPage, question);
			}
			else
			{
				var currentSection = currentPage.closest("li.panel-section");
				if(!currentSection.is(':last-child'))
				{
					nextPage = currentSection.next().find('li.page').first();

					isModal = executeBtnMovedown(nextPage, currentPage, question);
				}
			}
			//questionJson.index = 0;
		}
		else
		{
			question.trigger('rmvQuestionJsonNoDepLog');
			var nextQuestion = question.next();
			nextQuestion.insertBefore(question);
			var index = parseInt(question.attr("index"));
			question.attr("index", index + 1);
			questionJson.index = index;
			var prevIndex = parseInt(nextQuestion.attr("index"));
			nextQuestion.attr("index", prevIndex - 1);
			
			if(!nextQuestion.hasClass("bcontent"))
			{
				var numQuestion = question.find("span.num-question").html();
				nextQuestion.find("span.num-question").html(numQuestion);
				question.find("span.num-question").html(parseInt(numQuestion)+1);
			}

			updateQuestionIndex(question.attr("qid"), nextQuestion.attr("qid"), question.closest("li.page").attr("pid"), false, "down", host);
			question.trigger('insertQuestionJson', [questionJson]);
		}

		if(!isModal)
		{
			console.log("question moved: " + JSON.stringify(questionJson));
			//question.trigger('insertQuestionJson', [questionJson]);
			question.find("div.rules-frame").trigger("displayLogic");
			question.find("fieldset.dependences-frame").trigger("displayDependences");
			question.find("fieldset.logic-frame").trigger("setLogicMoved");
			$(this).focus();
		}	
		else isModal = false;
	});
	
	$('#listcompletequotas').on("click", "#removeQuota", function(e){
		//var item = $(this).parents(".survey-info");
		var item = $(this).closest("li.quota-item");
		quotaid = item.attr('quota');
		currentQuestion = item.find(".widthTitleSurveyCollapsed");
		sid = currentQuestion.attr('sid');
		qid = currentQuestion.attr('qid');
		$("#elementToRemoveText").html('"Quota for Question: ' + item.find('.selquestionforfees option:selected').text() + '"');
		$("#removeElemId").val(sid + '/' +qid +"/111");
		$("#removeElemService").val('QuotaService');
		$("#removeElement").modal("show");
	});
	
	$('#removeElement').on('hidden.bs.modal', function () {
		console.log("close remove element");
		$("#confirmRemoveDep").addClass("hidden");
		$("#confirmRemovelogic").addClass("hidden");
		$("#confirmRemoveRule").addClass("hidden");
		$("#confirmRemoveSection").addClass("hidden");
		modalFocus.focus();
	});
	
	/*$('#moveQuestion').on('hidden.bs.modal', function () {		
		modalFocus.focus();
	});*/
	
	$('#importFile').on('hidden.bs.modal', function () {
		console.log("close import file");
		modalFocus.focus();
	});
	
	$('#updateFile').on('hidden.bs.modal', function () {
		console.log("close update file");
		modalFocus.focus();
	});
	
	$('#accesibilityHelpWin').on('hidden.bs.modal', function () {
		console.log("close accesibilityHelpWin");
		modalFocus.focus();
	});
	
	$('#newQuestionModal').on('hidden.bs.modal', function () {
		console.log("close new question: " + modalFocus.prop("tagName"));
		modalFocus.focus();
		$('html,body').animate({scrollTop: modalFocus.offset().top - 25},'slow');
		/*if(modalFocus.prop("tagName") === "BUTTON")*/ modalFocus.closest("add-menu").show(); 
	});
});

function setScaleType(node)
{
	var req = {};
	req.qid = node.closest('li[id=panel-question1]').attr('qid');
	req.pid = node.closest('li[id=page]').attr('pid');
	req.scaleType = node.val();
	
	$.ajax({ 
		   type: "PUT",
		   dataType: "text",
		   contentType: "text/plain",
		   url: host + urlBase + "/api/QuestionService/scaleType",
		   data: JSON.stringify(req),
		   success: function (data) {
			   var numPoints = parseInt(node.val());
			   var listNode = node.closest("div.question-frame").find("ul.option-list");
			   //var numLiElements = listNode.find("li").size();
			   var tempElement = listNode.find("li").first().clone();
			   listNode.find("li").remove();
			   
			   var label = tempElement.find("label").html();
			   label = label.substring(0, label.length - 1);
			   var labelFor = tempElement.find("label").attr("for").split("-")[0] + "-";
			   
			   var initial = 1;
			   var total = numPoints;
			   if(numPoints == 11) {
				   initial = 0;
				   total = 10;
			   }
			   
			   for(i = initial; i <= total; i++) {
				   var elem = tempElement.clone();
				   elem.find("div.circle-info").html(i);
				   elem.find("label").attr("for", labelFor + i);
				   elem.find("label").html(label + i);
				   elem.find("input").attr("id", labelFor + i);
				   elem.find("input").attr("placeholder", label + i);
				   elem.find("input").attr("index", i);
				   elem.find("input").val("");
				   
				   listNode.append(elem);
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

function executeBtnMovedown(nextPage, currentPage, question)
{
	var isModal = false;
	var existLogDep = false;
	
	//var idQuestions = getAllQuestions(nextPage.attr("index"));
	var questionId = question.attr("qid");
	nextPage.find("select.dependence-question").each(function(index, element){
		var values = $(element).val().split("-");
		existLogDep = existLogDep || values[1] == questionId;
		//existLogDep = existLogDep || $.inArray(parseInt(values[1]), simpleQuestions) != -1;
	});
	
	var idQuestions = getAllQuestions(nextPage.attr("index"));
	question.find("select.logic-option-goto").each(function(index, element){
		console.log("Select index: " + index + " - val: " + $(element).val());
		var value = $(element).val();
		existLogDep = existLogDep || $.inArray(parseInt(value), idQuestions) != -1;
	});
	
	if(existLogDep)
	{
		isModal = true;
		$("#moveType").val("down");
		$("#moveQuestion").modal('show');
		modalFocus = $(this);
	}
	else
	{
		moveQuestionNextPage(nextPage, currentPage, question)
	}
		
	return isModal;
}

function moveQuestionNextPage(nPage, cPage, question)
{
	var questionJson = surveyTree[parseInt(cPage.attr("index")) - 1].questions[parseInt(question.attr("index")) - 1];
	question.trigger('rmvQuestionJsonNoDepLog');
	var questions = nPage.find("ul.page-items");

	insertQuestionNextPage(question, questions);				
	
	updateQuestionIndex(question.attr("qid"), 0, cPage.attr("pid"), true, "down", host);
	
	questionJson.index = 0;
	question.trigger('insertQuestionJson', [questionJson]);
}

function executeBtnMoveup(previousPage, currentPage, question)
{
	var isModal = false;
	var existLogDep = false;
	if(question.find("li.dependence-item").size() > 1)
	{
		var simpleQuestions = getSimpleQuestions(previousPage.attr("index"));
		question.find("select.dependence-question").each(function(index, element){
			var values = $(element).val().split("-");
			existLogDep = existLogDep || $.inArray(parseInt(values[1]), simpleQuestions) != -1;
		});
		
		if(existLogDep)
		{
			isModal = true;
			$("#moveType").val("up");
			$("#moveQuestion").modal('show');
			modalFocus = $(this);
		}
		else
		{
			moveQuestionPrevPage(previousPage, currentPage, question);
		}
	}
	else
	{ 
		//moveQuestionPrevPage(previousPage, currentPage, question);
		var questionId = question.attr("qid");
		previousPage.find("select.logic-option-goto").each(function(index, element){
			console.log("Select index: " + index + " - val: " + $(element).val());
			var value = $(element).val();
			existLogDep = existLogDep || value == questionId;
		});
		
		if(existLogDep)
		{
			isModal = true;
			$("#moveType").val("up");
			$("#moveQuestion").modal('show');
			modalFocus = $(this);
		}
		else
		{
			moveQuestionPrevPage(previousPage, currentPage, question);
		}
	}
	
	return isModal;
}

function moveQuestionPrevPage(pPage, cPage, question)
{
	var questionJson = surveyTree[parseInt(cPage.attr("index")) - 1].questions[parseInt(question.attr("index")) - 1];
	question.trigger('rmvQuestionJsonNoDepLog');
	var questions = pPage.find('ul.page-items');
	questions.append(question);
	var numQuestions = questions.find("li.panel-question").size();
	questionJson.index = numQuestions;
	question.attr("index", numQuestions);
	
	cPage.find("ul.page-items").find("li.panel-question").each(function(index, element){
		$(element).attr("index", index + 1);
	});

	updateQuestionIndex(question.attr("qid"), 0, cPage.attr("pid"), true, "up", host);
	question.trigger('insertQuestionJson', [questionJson]);
}

function insertQuestionNextPage(question, questions)
{

	var numQuestions = questions.find("li.panel-question").size();
	
	if(numQuestions == 0)
	{
		question.attr("index", 1);
		questions.append(question);
	}
	else
	{
		question.insertBefore(questions.find("li.panel-question").first());
		//console.log("first question: " + questions.find("li.panel-question").first().html());

		questions.find("li.panel-question").each(function(index, element){
			$(element).attr("index", index + 1);
		});
	}	
}

function updateQuestionIndex(qid, prevQid, pid, changePage, action, host)
{
	var req = {};		
	req.qid = qid;
	req.prevId = prevQid;
	req.pid = pid;
	req.changePage = changePage;
	req.action = action;
	console.log("Dragged: " + JSON.stringify(req));
	$.ajax({ 
		   type: "PUT",
		   dataType: "text",
		   contentType: "text/plain",
		   url: host + urlBase + "/api/QuestionService/updateIndex",
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

function loadquotas(json){
	jsonquotas = json;
}

function loadvaluequestion(id){
	$("#selquestionforfees"+id).change(function(){
		var currentNode = $(this);
		currentNode.closest('.widthTitleSurveyCollapsed').attr('qid',$('#selquestionforfees'+id).val());
	});
	
	$('#selquestionforfees'+id).trigger("change");
	$('#selquestionforfees'+id).prop("disabled", true);
	
	
	
	insertValueQuota();
	deleteQuote();
	
}



function changeoptionsfees(id){
	var valuesel = $("#selquestionforfees"+id).val();
	$('#optionsquota'+id).empty();
	
	//if(jsonquotas === "undefined"){
		var json = jQuery.parseJSON(jsonquotas);

		for (var k=0;k<json.length;++k)
		{
			for (var i=0;i<json[k].questions.length;++i)
		    {
				if(json[k].questions[i].questionId == valuesel){
					$('#optionsquota'+id).attr("ogid", json[k].questions[i].optionsGroup[0].optionsGroupId);
					for (var j=0;j< json[k].questions[i].optionsGroup[0].options.length;++j){
						var placeholdermax="none";
						var placeholdermin="none";
						var max=json[k].questions[i].optionsGroup[0].options[j].max;
						var min=json[k].questions[i].optionsGroup[0].options[j].min;
						
						if(max>0){
							max = "value='"+json[k].questions[i].optionsGroup[0].options[j].max+"' ";
						}
						
						if(min>0){
							min = "value='"+json[k].questions[i].optionsGroup[0].options[j].min+"' ";
						}
						
						//$('#optionsquota'+id).append("<div class='form-group' style='margin:0px;display: inline-flex;' id='optionquota'><div class='form-group col-md-4'><label class='control-label profileLabel' for='language'>"+json[0].questions[i].optionsGroup[0].options[j].title+"</label></div><div class='form-group col-md-4'><label class='col-md-4 control-label profileLabel' for='language'>Min</label><input id='min"+json[0].questions[i].optionsGroup[0].options[j].optionId+"' name='min' type='number' placeholder='none' class='form-control-small col-md-8' "+min+" index='"+j+"' oid='"+json[0].questions[i].optionsGroup[0].options[j].optionId+"' style='width: 60%;' min='1'></div><div class='form-group col-md-4'><label class='col-md-4 control-label profileLabel' for='language'>Max</label> <input id='max"+json[0].questions[i].optionsGroup[0].options[j].optionId+"' name='max' type='number' placeholder='none' class='form-control-small col-md-8' "+max+" index='"+j+"' oid='"+json[0].questions[i].optionsGroup[0].options[j].optionId+"' style='width: 60%;' min='1'></div></div>");
						$('#optionsquota'+id).prepend("<div class='form-group quoteoption' id='optionquota'><fieldset class='form-group col-md-4' style='width:100%'><legend class='col-md-4' style='border:0px;font-size:16px;'>"+json[k].questions[i].optionsGroup[0].options[j].title+"</legend><div class='form-group col-md-4'><label class='col-md-4 control-label profileLabel'>Min</label><input id='min"+json[k].questions[i].optionsGroup[0].options[j].optionId+"' name='min' type='number' placeholder='none' class='form-control-small col-md-8' "+min+" index='"+j+"' oid='"+json[k].questions[i].optionsGroup[0].options[j].optionId+"' style='width: 60%;' min='1'></div><div class='form-group col-md-4'><label class='col-md-4 control-label profileLabel'>Max</label> <input id='max"+json[k].questions[i].optionsGroup[0].options[j].optionId+"' name='max' type='number' placeholder='none' class='form-control-small col-md-8' "+max+" index='"+j+"' oid='"+json[k].questions[i].optionsGroup[0].options[j].optionId+"' style='width: 60%;' min='1'></fieldset></div>");
					}
					
				}
				
		    }
		}
	//}
		$('#questionquotaname'+id).html($("#selquestionforfees"+id +" option:selected").text());
	
}

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

		   if(node != null)
		   {
			   node.trigger("setQuestionJson");
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

function insertValueQuota(){
	$('.widthTitleSurveyCollapsed').on("focusout change", "#optionquota input", function(e){
		e.stopPropagation();
		//if($(this).val() != ""){
			
			var req = {};
			var currentNode = $(this);
			req.text = currentNode.val();
			req.oid = currentNode.attr('oid');
			req.index = currentNode.attr('index');
			req.qid = currentNode.closest('.widthTitleSurveyCollapsed').attr('qid');
			req.sid = currentNode.closest('.widthTitleSurveyCollapsed').attr('sid');
			req.ogid = currentNode.closest('.optionsquota').attr('ogid');
			
			var max = Number($("#max"+req.oid).val());
			var min = Number($("#min"+req.oid).val());
			
			if($("#max"+req.oid).val()!="" && min>max){
				max = $("#min"+req.oid).val();
				$("#max"+req.oid).val(max);
			}
			
			
			if(max=="")max=0;
			if(min=="")min=0;
			
			req.max = max;
			req.min = min;
			
			console.log("TExt: " + $(this).val() + " - qid: " + $(this).attr('index') + " - : " + req.qid + " - ogid: " + req.oid);
			//alert("TExt: " + $(this).val() + " - qid: " + $(this).attr('index') + " - qid: " + req.qid + " - ogid: " + req.oid);
			var host = "http://" + window.location.host;
			
			$.ajax({ 
			   type: "POST",
			   dataType: "text",
			   contentType: "text/plain",
			   url: host + urlBase + "/api/QuotaService/insertQuota",
			   data: JSON.stringify(req),
			   success: function (data) {
				   console.log(data);
				   if(data != '')
				   {
					   var jsonresponse = JSON.parse(data);
					   if(jsonresponse.hasOwnProperty('oid'))
					   {
						   console.log("hello oid: " + jsonresponse.oid);
						   //update jsonquotas
						   var json = jQuery.parseJSON(jsonquotas);
						   for (var k=0;k<json.length;k++)
						   {
							   for (var i=0;i<json[k].questions.length;i++)
							    {
								   		if(json[k].questions[i].optionsGroup.length==0 || json[k].questions[i].optionsGroup[0]==='undefined' || json[k].questions[i].optionsGroup[0].options==='undefined'){
								   		}else{
											for (var j=0; j < json[k].questions[i].optionsGroup[0].options.length;j++){
												if(json[k].questions[i].optionsGroup[0].options[j].optionId == jsonresponse.oid){
													json[k].questions[i].optionsGroup[0].options[j].max = jsonresponse.max;
													json[k].questions[i].optionsGroup[0].options[j].min = jsonresponse.min;
													jsonquotas = JSON.stringify(json);
												}
											}
								   		}
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
		//}
	});
	

}

$.getScript('http://mindmup.github.io/bootstrap-wysiwyg/external/jquery.hotkeys.js',function(){
	$.getScript('http://mindmup.github.io/bootstrap-wysiwyg/bootstrap-wysiwyg.js',function(){

	  $('#editor').wysiwyg();
	  $('#editor').cleanHtml();

	});
	});

function deleteQuote(){
	
	
}

function alertNotQuota(){
    bootbox.dialog({
		message: textQuotaAlert,
		title: textQuotaTitleAlert,
		buttons: {
			success: {
			label: textQuotaAlertBtn,
			className: "btn-success",
				callback: function() {
				   
				
				}
			}
		}
    });
}
