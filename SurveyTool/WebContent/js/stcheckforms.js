/**
 * 
 */

var error = false;

$(function() {
	
	$('#loginSubmit').click(function(e){
		e.preventDefault();
		var valid = true;
		if($('#username').val() == ""){
			valid = false;
			showFieldError($('#username'));}		
		if($('#password').val() == ""){
			valid = false;
			showFieldError($('#password'));}		
		if(valid){
			error = false;
			$('#loginForm').submit();}
	});
	
	$('.form-group').on('keyup', 'input', function(){
		if(error)
		{
			var value = $(this).val();
			
			if(value != ''){hideFieldError($(this));}
			else{showFieldError($(this));}
		}
	});
	
});

function showFieldError(element)
{	
	var errorId = element.attr('id') + '-error';
	element.closest("div").addClass("has-error has-feedback");
	element.attr('aria-describedby', errorId);
	$('#' + errorId).removeClass('hidden');
	$('#' + element.attr('id') + "-feedback").removeClass('hidden');
	
	error = true;
}

function hideFieldError(element)
{
	var errorId = element.attr('id') + '-error';
	element.closest("div").removeClass("has-error has-feedback");
	element.removeAttr('aria-describedby');
	$('#' + errorId).addClass('hidden');
	$('#' + element.attr('id') + "-feedback").addClass('hidden');
}
