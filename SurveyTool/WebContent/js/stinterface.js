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
			bodyClick();
			currentFrameActivate = "survey-info";
			surveyInfoOpen = true;
			$('.survey-info-title').focus();
			$('.survey-info-title').css("border-bottom", "1px solid #555");
			$('.survey-info-title').focus();
			$('.survey-info-project').css("display", "inherit");
			$('.survey-info-description').css("display", "inline-block");
			$('.sect-arrow i').prop("class", "fa fa-caret-down fa-2x");
			$('.sect-arrow').css("padding-top", "15px");
		}
	});
	
	$('#panel-section1 .panel-heading h3 #survey-section-title').click(function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "panel-section1";
		$('#panel-section1 .panel-heading h3 #survey-section-title').prop("class", "survey-section-title-selected center");
		$('#panel-section1 .panel-heading h3 #survey-section-title').focus();
	});
	
	$('#add-menu-frame1 a').click(function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "add-menu-frame1";
		//$('#add-menu-frame1 a').prop("class", "btn-add col-sm-1");
		$('#add-menu-frame1 .add-menu').css("display", "inherit");
	});
	
	$('#add-menu-frame2 a').click(function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "add-menu-frame2";
		$('#add-menu-frame2 a').prop("class", "btn-add col-sm-1");
		$('#add-menu-frame2 .add-menu').css("display", "inherit");
	});
	
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
	
	$('#panel-question1 .panel-heading h3 #survey-question-title').click(function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "panel-question1";
		$('#panel-question1 .panel-heading h3 #survey-question-title').prop("class", "survey-section-title-selected");
		$('#panel-question1 .panel-heading h3 #survey-question-title').focus();
	});
	
	$('#panel-question1 div div #display-question-panel').click(function(e){
		e.stopPropagation();
		var cl = $('#panel-question1 div div #display-question-panel i').prop("class");
		if (cl == "fa fa-caret-down fa-2x")
		{
			$('#panel-question1 .panel-body').css("display", "none");
			$('#panel-question1 div div #display-question-panel i').prop("class", "fa fa-caret-right fa-2x");
		}
		else if(cl == "fa fa-caret-right fa-2x")
		{
			$('#panel-question1 .panel-body').css("display", "inherit");
			$('#panel-question1 div div #display-question-panel i').prop("class", "fa fa-caret-down fa-2x");
		}
	});
	
});


function bodyClick()
{
	if(currentFrameActivate != "")
	{
		if(currentFrameActivate == "survey-info")
		{
			surveyInfoOpen = false;
			$('.survey-info-title').css("border-bottom", "none");
			$('.survey-info-project').css("display", "none");
			$('.survey-info-description').css("display", "none");
			$('.sect-arrow i').prop("class", "fa fa-caret-right fa-2x");
			$('.sect-arrow').css("padding-top", "0px");
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
