/**
 * 
 */

var surveyInfoOpen = false;
var currentFrameActivate = "";

$(function() {

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
		}
		else
		{
			hideSurveyInfo();
		}		
    });
	
	$('#panel-section1 .panel-heading h3 #survey-section-title').click(function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "panel-section1";
		$('#panel-section1 .panel-heading h3 #survey-section-title').prop("class", "survey-section-title-selected");
		$('#panel-section1 .panel-heading h3 #survey-section-title').focus();
	});
	
	$('#page').on("click", "#add-menu-frame div button", function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "add-menu-frame";
		var root = $(this).closest('div[id=add-menu-frame]')
		//$('#add-menu-frame .add-menu').css("display", "inherit");
		root.find(".add-menu").css("display", "inherit");
		root.find('#btn-question').focus();
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
		var cl = $(this).children('i').prop("class");
		if (cl == "fa fa-caret-down fa-2x")
		{
			$(this).closest('#panel-section1').children('#section-pages').css("display", "none");
			$(this).children('i').prop("class", "fa fa-caret-right fa-2x");
		}
		else if(cl == "fa fa-caret-right fa-2x")
		{
			$(this).closest('#panel-section1').children('#section-pages').css("display", "inherit");
			$(this).children('i').prop("class", "fa fa-caret-down fa-2x");
		}
	});
	
	//abrir cerrar question panel
	$('#page-items').on('click', '#panel-heading-display', function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "panel-heading-display";
		var cl = $(this).children('i').prop("class");
		if (cl == "fa fa-caret-down fa-2x")
		{
			$(this).closest('#panel-question1').children('.panel-body').css("display", "none");
			$(this).children('i').prop("class", "fa fa-caret-right fa-2x");
		}
		else if(cl == "fa fa-caret-right fa-2x")
		{
			$(this).closest('#panel-question1').children('.panel-body').css("display", "inherit");
			$(this).children('i').prop("class", "fa fa-caret-down fa-2x");
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
							'<div class="col-sm-8"><div class="form-group" style="margin:0px;"><input type="text" id="option' + index + '" class="form-control fleft" index="' + index + '" placeholder="Option ' + index + '" style="width: 100%; !important;"/><span  id="option' + index + '-feedback" class="glyphicon glyphicon-remove form-control-feedback hidden" aria-hidden="true" style="color:#A94442;right: 20px;"></span><span  id="option' + index + '-error" class="error hidden" style="top: 0px;right: -180px;">'+'textmultilanguage'+'</span></div></div>'+
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
			if($('#poll-table').length)
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
	  				$('#poll-table').find('tbody').append(res);
	  			}
	  		});

		}else{
			//$('#newPollForm').validator();
		}
	});
	
});

function displaySurveyInfo(node)
{
	bodyClick();
	currentFrameActivate = "survey-info";
	surveyInfoOpen = true;
	$('.survey-info-title').focus();
	$('.survey-info-title').css("border-bottom", "1px solid #555");
	$('.survey-info-title').focus();
	$('.survey-info-project').css("display", "inherit");
	$('.survey-info-description').css("display", "inline-block");
	$('.survey-info-url').css("display", "inline-block");
	$('.display-default-arrow i').prop("class", "fa fa-caret-down fa-2x");
	$('.display-default-arrow').css("padding-top", "15px");
	$('#display-survey-settings').attr('display', 'true');
	//$('#survey-info').attr('id', 'survey-info-displayed');
}

function hideSurveyInfo(node)
{
	$('.survey-info-title').css("border-bottom", "none");
	$('.survey-info-project').css("display", "none");
	$('.survey-info-description').css("display", "none");
	$('.survey-info-url').css("display", "none");
	$('.display-default-arrow i').prop("class", "fa fa-caret-right fa-2x");
	$('.display-default-arrow').css("padding-top", "0px");
	currentFrameActivate = "";
	$('#display-survey-settings').attr('display', 'false');
	surveyInfoOpen = false;
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
			$('#' + currentFrameActivate + ' .panel-heading h3 #survey-section-title').prop("class", "survey-section-title-unselected");
			$('#' + currentFrameActivate + ' .panel-heading h3 #survey-question-title').prop("class", "survey-section-title-unselected");
			$('#' + currentFrameActivate + ' .add-menu').css("display", "none");
			//$('#' + currentFrameActivate + ' .btn-add').prop("class", "btn-add col-sm-12");
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


