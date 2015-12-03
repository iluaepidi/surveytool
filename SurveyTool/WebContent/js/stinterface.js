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
	
	$('#panel-section1 .panel-heading h3 input').click(function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "panel-section1";
		$('#panel-section1 .panel-heading h3 input').prop("class", "survey-section-title-selected");
		$('#panel-section1 .panel-heading h3 input').focus();
	});
	
	$('#add-menu-frame1 div a').click(function(e){
		e.stopPropagation();
		bodyClick();
		currentFrameActivate = "add-menu-frame1";
		$('#add-menu-frame1 .col-sm-12').prop("class", "col-sm-1");
		$('#add-menu-frame1 .add-menu').css("display", "inherit");
	});
	
	/*$('#add-menu-frame1 div div #btn-question').click(function(e){
		e.stopPropagation();
	});*/
	
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
			$('#' + currentFrameActivate + ' .panel-heading h3 input').prop("class", "survey-section-title-unselected");
			$('#add-menu-frame1 .add-menu').css("display", "none");
			$('#add-menu-frame1 .col-sm-1').prop("class", "col-sm-12");
		}
		
		currentFrameActivate = "";
	}
}
