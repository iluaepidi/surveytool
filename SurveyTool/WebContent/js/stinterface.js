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
		$('#panel-section1 .panel-heading h3 #survey-section-title').prop("class", "survey-section-title-selected center");
		$('#panel-section1 .panel-heading h3 #survey-section-title').focus();
	});
	
	$('#panel-body').on("click", "#add-menu-frame a", function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "add-menu-frame";
		//$('#add-menu-frame .add-menu').css("display", "inherit");
		$(this).parent().children(".add-menu").css("display", "inherit");
		$(this).parent().find('#btn-question').focus();
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
			$('#panel-section1 .panel-body').css("display", "none");
			$('#panel-section1 div div #display-section-panel i').prop("class", "fa fa-caret-right fa-2x");
		}
		else if(cl == "fa fa-caret-right fa-2x")
		{
			$('#panel-section1 .panel-body').css("display", "inherit");
			$('#panel-section1 div div #display-section-panel i').prop("class", "fa fa-caret-down fa-2x");
		}
	});
	
	//$('#panel-question1 .panel-heading h3 #survey-question-title').click(function(e){
	$('#panel-body').on("click", "#survey-question-title", function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "panel-question1";
		//$('#panel-question1 .panel-heading h3 #survey-question-title').prop("class", "survey-section-title-selected");
		$(this).prop("class", "survey-section-title-selected");
		//$('#panel-question1 .panel-heading h3 #survey-question-title').focus();
		$(this).focus();
	});
	
	//abrir cerrar question panel
	$('#panel-question').on('click', '#display-question-panel', function(e){
		e.stopPropagation();
		//var cl = $('#panel-question1 div div #display-question-panel i').prop("class");
		var cl = $(this).children('i').prop("class");
		if (cl == "fa fa-caret-down fa-2x")
		{
			//$('#panel-question1 .panel-body').css("display", "none");
			$('#panel-question').children('.panel-body').css("display", "none");
			//$('#panel-question1 div div #display-question-panel i').prop("class", "fa fa-caret-right fa-2x");
			$(this).children('i').prop("class", "fa fa-caret-right fa-2x");
		}
		else if(cl == "fa fa-caret-right fa-2x")
		{
			/*$('#panel-question1 .panel-body').css("display", "inherit");
			$('#panel-question1 div div #display-question-panel i').prop("class", "fa fa-caret-down fa-2x");*/
			$('#panel-question').children('.panel-body').css("display", "inherit");
			$(this).children('i').prop("class", "fa fa-caret-down fa-2x");
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
			$('#' + currentFrameActivate + ' .panel-heading h3 #survey-section-title').prop("class", "survey-section-title-unselected center");
			$('#' + currentFrameActivate + ' .panel-heading h3 #survey-question-title').prop("class", "survey-section-title-unselected");
			$('#' + currentFrameActivate + ' .add-menu').css("display", "none");
			//$('#' + currentFrameActivate + ' .btn-add').prop("class", "btn-add col-sm-12");
		}
		
		currentFrameActivate = "";
	}
}
