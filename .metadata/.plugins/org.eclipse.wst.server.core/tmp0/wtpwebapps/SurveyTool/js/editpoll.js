/**
 * 
 */
var width = 445;
var height = 232;

$(function() {
	
	$('#widthPoll').keyup(function(){
		setWidthPoll($(this));
	});
	
	$('#widthPoll').change(function(){
		setWidthPoll($(this));
	});
	
	$('#heightPoll').keyup(function(){
		setHeightPoll($(this));
	});
	
	$('#heightPoll').change(function(){
		setHeightPoll($(this));
	});
	
});

function setWidthPoll(elem)
{
	width = elem.val();
	var code = $('#pollCode').val();
	$('#pollCode').val(code.replace(/style=".*"/, 'style="width: ' + width + 'px; height: ' + height + 'px;"'));
	if(width < 200)
	{
		elem.val(200);
		width = 200;
	}
	
	$('#iframe').css('width', width);
}

function setHeightPoll(elem)
{
	height = elem.val();
	var code = $('#pollCode').val();
	$('#pollCode').val(code.replace(/style=".*"/, 'style="width: ' + width + 'px; height: ' + height + 'px;"'));
	if(height < 150)
	{
		elem.val(150);
		height = 150;
	}
	
	$('#iframe').css('height', height);
}

/*function updateContent(req, serviceUrl)
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
}*/

