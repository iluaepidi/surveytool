/**
 * 
 */

var surveyInfoOpen = false;
var currentFrameActivate = "";
var currentElement;
var hideText = "";
var displayText = "";
var sectionText = "";
var pageText = "";
var questionText = "";
var surveyInfoText = "";

var currentUrl = window.location.href;
var urlBase = "";
//if(currentUrl.includes("SurveyTool")) urlBase = "/SurveyTool";
if(currentUrl.indexOf("SurveyTool") != -1) urlBase = "/SurveyTool";

$(function() {
	console.log("CurrentUrl Protoccol: " + window.location.protocol);
	
	$('table').removeAttr("role");

	$('body').click(function() {
		bodyClick();
		currentFrameActivate = "";		
    });
	
	//survey-info
	$('#survey-info').click(function(e){
		e.stopPropagation();
		if(!surveyInfoOpen){
			/*bodyClick();
			currentFrameActivate = "survey-info";
			surveyInfoOpen = true;
			$('.survey-info-title').focus();
			$('.survey-info-title').css("border-bottom", "1px solid #555");
			$('.survey-info-title').focus();
			$('.survey-info-project').css("display", "inherit");
			$('.survey-info-description').css("display", "inline-block");
			$('.survey-info-url').css("display", "inline-block");
			$('.sect-arrow i').prop("class", "fa fa-caret-down fa-2x");
			$('.sect-arrow').css("padding-top", "15px");*/
			displaySurveyInfo();
		}
	});
	
	$('#display-survey-settings').click(function(e) {
		e.stopPropagation();
		if($(this).attr('display') == "false")
		{
			displaySurveyInfo();
			$(this).attr("aria-label", hideText + ' ' + surveyInfoText);
		}
		else
		{
			hideSurveyInfo();
			$(this).attr("aria-label", displayText + ' ' + surveyInfoText);
		}		
    });
	
	$('#panel-section1 .panel-heading h3 #survey-section-title').click(function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "panel-section1";
		$('#panel-section1 .panel-heading h3 #survey-section-title').prop("class", "survey-section-title-selected");
		$('#panel-section1 .panel-heading h3 #survey-section-title').focus();
	});
	
	$('.survey-sections').on("click", "#add-menu-frame div button", function(e){
		console.log("add-menu-frame");
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "add-menu-frame";
		var root = $(this).closest('div[id=add-menu-frame]')
		//$('#add-menu-frame .add-menu').css("display", "inherit");
		root.find(".add-menu").css("display", "inherit");
		$(this).attr("aria-expanded", "true");
		root.find('button.btn-question').focus();
	});
	
	/*$('#add-menu-frame a').click(function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "add-menu-frame2";
		$('#add-menu-frame2 a').prop("class", "btn-add col-sm-1");
		$('#add-menu-frame2 .add-menu').css("display", "inherit");
	});*/
	
	/*$('#add-menu-frame1 div div #btn-question').click(function(e){
		e.stopPropagation();
	});*/
	
	$('#panel-section1 div div #display-section-panel').click(function(e){
		e.stopPropagation();
		var cl = $('#panel-section1 div div #display-section-panel i').prop("class");
		if (cl == "fa fa-caret-down fa-2x")
		{
			$('#panel-section1 .page-items').css("display", "none");
			$('#panel-section1 div div #display-section-panel i').prop("class", "fa fa-caret-right fa-2x");
		}
		else if(cl == "fa fa-caret-right fa-2x")
		{
			$('#panel-section1 .page-items').css("display", "inherit");
			$('#panel-section1 div div #display-section-panel i').prop("class", "fa fa-caret-down fa-2x");
		}
	});
	
	//$('#panel-question1 .panel-heading h3 #survey-question-title').click(function(e){
	$('#page-items').on("click", "#survey-question-title", function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "panel-question1";
		//$('#panel-question1 .panel-heading h3 #survey-question-title').prop("class", "survey-section-title-selected");
		$(this).prop("class", "survey-section-title-selected");
		//$('#panel-question1 .panel-heading h3 #survey-question-title').focus();
		$(this).focus();
	});
	
	//abrir cerrar section panel
	$('#survey-sections').on('click', '#panel-heading-display', function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "panel-heading-display";
		var section = $(this).attr("aria-label").split(":")[1].trim();
		var cl = $(this).children('i').prop("class");
		if (cl == "fa fa-caret-down fa-2x")
		{
			$(this).closest('#panel-section1').children('#section-pages').css("display", "none");
			$(this).children('i').prop("class", "fa fa-caret-right fa-2x");
			$(this).attr("aria-label", displayText + ' ' + sectionText + ": " + section);
		}
		else if(cl == "fa fa-caret-right fa-2x")
		{
			$(this).closest('#panel-section1').children('#section-pages').css("display", "inherit");
			$(this).children('i').prop("class", "fa fa-caret-down fa-2x");
			$(this).attr("aria-label", hideText + ' ' + sectionText + ": " + section);
		}
	});

	//abrir cerrar page panel
	$('#survey-sections').on('click', '.display-page-arrow', function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "panel-heading-display";
		var numPage = $(this).closest("li.page").attr("index");
		var cl = $(this).children('i').prop("class");
		if (cl == "fa fa-caret-down fa-2x")
		{
			$(this).closest('li.page').children('.page-body').css("display", "none");
			$(this).children('i').prop("class", "fa fa-caret-right fa-2x");
			$(this).attr("aria-label", displayText + ' ' + pageText + ' ' + numPage);
		}
		else if(cl == "fa fa-caret-right fa-2x")
		{
			$(this).closest('li.page').children('.page-body').css("display", "inherit");
			$(this).children('i').prop("class", "fa fa-caret-down fa-2x");
			$(this).attr("aria-label", hideText + ' ' + pageText + ' ' + numPage);
		}
	});
	
	//abrir cerrar question panel
	$('.survey-sections').on('click', '.display-question-arrow', function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "panel-heading-display";
		var cl = $(this).children('i').prop("class");
		var question = $(this).attr("aria-label").split(":")[1].trim();
		if (cl == "fa fa-caret-down fa-2x")
		{
			$(this).closest('li.panel-question').find('.panel-body').css("display", "none");
			$(this).children('i').prop("class", "fa fa-caret-right fa-2x");
			$(this).attr("aria-label", displayText + ' ' + questionText + ": " + question);
		}
		else if(cl == "fa fa-caret-right fa-2x")
		{
			$(this).closest('li.panel-question').find('.panel-body').css("display", "inherit");
			$(this).children('i').prop("class", "fa fa-caret-down fa-2x");
			$(this).attr("aria-label", hideText + ' ' + questionText + ": " + question);
		}
	});
	
	$('#tab-display-surveys').click('click', function(e){
		$('#nav-tabs-li-surveys').addClass('active');
		$('#nav-tabs-li-polls').removeClass('active');
		$('#polls-list').addClass('hidden');
		$('#surveys-list').removeClass('hidden');
	});

	$('#tab-display-polls').click('click', function(e){
		$('#nav-tabs-li-surveys').removeClass('active');
		$('#nav-tabs-li-polls').addClass('active');
		$('#polls-list').removeClass('hidden');
		$('#surveys-list').addClass('hidden');
	});
	
	$('#option-list').on("click", "#btn-add-option-poll", function(e){
		var index = $(this).parent().parent().children("li").size();
		var optionHtml = '<li class="option-item" id="option-item">' +
							//'<button class="btn btn-transparent fleft"><i class="fa fa-sort fa-2x"></i></button> ' +		
							'<div class="circle-info circle-grey fleft">' + index + '</div> ' + 
							
							//'<input type="text" class="option-title form-control fleft" index="' + index + '" placeholder="Option ' + index + '" autofocus/> ' +
							'<div class="col-sm-8"><div class="form-group" style="margin:0px;"><input type="text" id="option' + index + '" class="form-control fleft" index="' + index + '" placeholder="'+textOption+' ' + index + '" style="width: 100%; !important;"/><span  id="option' + index + '-feedback" class="glyphicon glyphicon-remove form-control-feedback hidden" aria-hidden="true" style="color:#A94442;right: 20px;"></span><span  id="option' + index + '-error" class="error hidden" style="top: 0px;right: -180px;">'+textErrorPollOption+'</span></div></div>'+
							
							'<div class="option-icons fleft"> ' +
								//'<button class="btn btn-transparent fleft"><i class="fa fa-file-image-o fa-2x"></i></button> ' +
								//'<button class="btn btn-transparent fleft"><i class="fa fa-question-circle fa-2x"></i></button> ' +
								'<button class="btn btn-transparent fleft red" id="remove-option-poll" aria-label="remove option"><i class="fa fa-trash fa-2x"></i></button> ' +
							'</div> ' +
						'</li>';
		$(this).parent().before(optionHtml);
		//$(this).closest('ul').find('input[index=' + index + ']').focus();
	});
	
	$('#option-list').on("click", "#remove-option-poll", function(e){
		e.stopPropagation();
		var currentli = $(this).closest('li'); 
		var numItems = currentli.closest("ul").find("li").size();
		if(numItems > 3)
		{
			currentli.remove();			
			$('li[id=option-item]').each(function (i, elem)
			{
			   var index = i + 1;
			   $(elem).find('input').attr('index', index);
			   $(elem).find('input').attr('placeholder', "Option " + index)
			   $(elem).find('.circle-grey').text(index);
		   });
		}
		else
		{
			var input = currentli.find('input');
			input.val('');
		}
	});
	
	$('#newPollForm').on("submit", function(e){
		e.preventDefault();
		console.log('Entra 2: ' + e.target.id);
	});
	
	$('#btnCreateNewPoll').click(function(){
		console.log("Table lenght: " + $('table.table-polls tr').length);
		var valid = true;
		if($('#pollTitle').val() == ""){
			valid = false;
			showFieldError($('#pollTitle'));
		}else{
			hideFieldError($('#pollTitle'));
		}
		
		if($('#pollProject').val() == ""){
			valid = false;
			showFieldError($('#pollProject'));
		}else{
			hideFieldError($('#pollProject'));
		}
		
		if($('#qstatement').val() == ""){
			valid = false;
			showFieldError($('#qstatement'));
		}else{
			hideFieldError($('#qstatement'));
		}
		
		
		var option=1;
		while($('#option'+option)!="undefined" && $('#option'+option).length>0){
			
			if($('#option'+option).val() == ""){
				valid = false;
				showFieldError($('#option'+option));
			}else{
				hideFieldError($('#option'+option));
			}
			
			option++;
		}
		
		
		
		if(valid){
		
			var pollOptions = [];
			var existPolls = 'false';
			if($('table.table-polls tr').length)
			{
				existPolls = 'true';
				console.log("Exist polls: " + existPolls);
			}
			
			$('li[id=option-item]').each(function (i, elem)
			{
				var option = {};
				var input = $(elem).find('input');
				option.title = input.val();
				option.index = input.attr('index');
				pollOptions.push(option);
			});
			
			$.post('CreatePollServlet', {
	        	title : $('#pollTitle').val(),
	        	project: $('#pollProject').val(),
	        	qstatement: $('#qstatement').val(),
	  			options: JSON.stringify(pollOptions),
	  			ackText: $('#ackText').val(),
	  			callText: $('#pollCallText').val(),
	  			linkLabel: $('#pollLinkLabel').val(),
	  			linkUrl: $('#pollLinkUrl').val(),
	  			existPolls: existPolls
	  		}, function(res) {
	  			console.log('create poll response: ' + res);
	  			$('#newPollForm')[0].reset();
	  			$("#newPollModal").modal("hide");
	  			if(existPolls === 'false')
	  			{
	  				$('#no-polls-msg').after(res);
	  				$('#no-polls-msg').remove();
	  			}
	  			else
	  			{
	  				$('table.table-polls').find('tbody').append(res);
	  			}
	  		});

		}else{
			//$('#newPollForm').validator();
		}
	});
	
	$('.surveys-table').on("click", "a.survey-pause", function(e){
		var surveyId = parseInt($(this).closest("tr").attr("sid"));
		setSurveyState(surveyId, "paused", $(this), "a.survey-play");
	});
	
	$('.surveys-table').on("click", "a.survey-play", function(e){
		var surveyId = parseInt($(this).closest("tr").attr("sid"));
		setSurveyState(surveyId, "active", $(this), "a.survey-pause");
	});
	
	$('.surveys-table').on("click", "a.survey-finish", function(e){
		/*var surveyId = parseInt($(this).closest("tr").attr("sid"));
		setSurveyState(surveyId, "finished", $(this), "");*/
		currentElement =  $(this);
		$("#finishQuestionarieConfirm").modal("show");
	});
	
	$('.body-content').on("click", "button.btn-accept-finish-survey", function(){
		var surveyId = parseInt(currentElement.closest("tr").attr("sid"));
		setSurveyState(surveyId, "finished", currentElement, "");
		$('#finishQuestionarieConfirm').modal('hide');
	});
});

function setSurveyState(surveyId, state, element, elemToShow)
{
	var req = {};
	req.sid = surveyId;
	req.state = state;
	$.ajax({ 
	   type: "PUT",
	   dataType: "text",
	   contentType: "text/plain",
	   url: window.location.protocol + "//" + window.location.host + urlBase + "/api/SurveyService/updateState",
	   data: JSON.stringify(req),
	   success: function (data) {
		   //console.log("set state response: " + data);
		   var json = JSON.parse(data);
		   if(json.updated)
		   {
			   //element.closest("tr").find("td.state").html(json.stateLabel);
			   $('div.surveys-table').find('table').DataTable().cell($('#cellState' + surveyId)).data(json.stateLabel).draw()
			   
			   element.addClass("hidden");
			   if(elemToShow != "")
			   {
				   element.closest("li").find(elemToShow).removeClass("hidden");
			   }
			   else
			   {
				   element.closest("ul").find("a.survey-pause").addClass("hidden");
				   element.closest("ul").find("a.survey-play").addClass("hidden");
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
}

function displaySurveyInfo(node)
{
	bodyClick();
	currentFrameActivate = "survey-info";
	surveyInfoOpen = true;
	$('.survey-info-title').focus();
	$('.survey-info-title').css("border-bottom", "1px solid #555");
	$('.survey-info-title').focus();
	$('.survey-info-project').css("display", "inline-block");
	$('.survey-info-description').css("display", "inline-block");
	$('.survey-info-url').css("display", "inline-block");
	$('.survey-info-ipfilter').css("display", "inline-block");
	$('.display-default-arrow i').prop("class", "fa fa-caret-down fa-2x");
	$('.display-default-arrow').css("padding-top", "15px");
	$('#display-survey-settings').attr('display', 'true');
	//$('#survey-div-title').css('width', '42%');
	$('#survey-div-title').addClass('widthTitleSurveyExtended');
	$('#survey-div-title').removeClass('widthTitleSurveyCollapsed');
	//$('#survey-info').attr('id', 'survey-info-displayed');
}

function hideSurveyInfo(node)
{
	if($('#surveyProject-error').attr("class")!="error"){
		$('.survey-info-title').css("border-bottom", "none");
		$('.survey-info-project').css("display", "none");
		$('.survey-info-description').css("display", "none");
		$('.survey-info-url').css("display", "none");
		$('.survey-info-ipfilter').css("display", "none");
		$('.display-default-arrow i').prop("class", "fa fa-caret-right fa-2x");
		$('.display-default-arrow').css("padding-top", "0px");
		currentFrameActivate = "";
		$('#display-survey-settings').attr('display', 'false');
		//$('#survey-div-title').css('width', '85%');
		$('#survey-div-title').removeClass('widthTitleSurveyExtended');
		$('#survey-div-title').addClass('widthTitleSurveyCollapsed');
		surveyInfoOpen = false;
	}
	//$('#survey-info-displayed').attr('id', 'survey-info');
}


function bodyClick()
{
	if(currentFrameActivate != "")
	{
		if(currentFrameActivate == "survey-info")
		{
			/*surveyInfoOpen = false;
			$('.survey-info-title').css("border-bottom", "none");
			$('.survey-info-project').css("display", "none");
			$('.survey-info-description').css("display", "none");
			$('.survey-info-url').css("display", "none");
			$('.sect-arrow i').prop("class", "fa fa-caret-right fa-2x");
			$('.sect-arrow').css("padding-top", "0px");*/
			hideSurveyInfo();
		}
		else
		{
			if($('#' + currentFrameActivate + ' .panel-heading h3 #survey-section-title-error').is(":hidden")){
				$('#' + currentFrameActivate + ' .panel-heading h3 #survey-section-title').prop("class", "survey-section-title-unselected");
			}
			//if($('#' + currentFrameActivate + ' .panel-heading h3 .error').closest('#survey-question-title').is(":hidden")){
				//$('#' + currentFrameActivate + ' .panel-heading h3 #survey-question-title').prop("class", "survey-section-title-unselected");
			//}
			//alert($('#' + currentFrameActivate + ' .panel-heading h3 #survey-question-title').closest('#panel-question1').attr('qid'));
			
			$('#' + currentFrameActivate + ' .add-menu').css("display", "none");
			$('#' + currentFrameActivate + ' .btn-add').attr("aria-expanded", "false");
		}
		
		currentFrameActivate = "";
	}
}


function showHideRegister(register){
	if(register==true){
		$('#registerDivForm').show();
		$('#loginDivForm').hide();
	}else{
		$('#registerDivForm').hide();
		$('#loginDivForm').show();
	}
	
	
	
}


