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
	
	$('#registerSubmit').click(function(e){
		e.preventDefault();
		var valid = true;
		if($('#usernameregister').val() == ""){
			valid = false;
			showFieldError($('#usernameregister'));
		}else if($('#usernameregister').val().length < 4 || $('#usernameregister').val().length > 16){
			valid = false;
			showFieldErrorRegisterUsername($('#usernameregister'));
		}
		
		if($('#email').val() == "" || $('#email').val().length < 6){
			valid = false;
			showFieldError($('#email'));
		}else if(!($('#email').val().indexOf("@")!=-1) || !($('#email').val().indexOf(".")!=-1)){
			valid = false;
			showFieldError($('#email'));
		}
		
		if($('#passwordregister').val() == "" || $('#passwordregister').val().length < 8){
			valid = false;
			showFieldError($('#passwordregister')); 
		}
		
		if($('#repasswordregister').val() != $('#passwordregister').val()){
			valid = false;
			showFieldError($('#repasswordregister'));
		}
		
		
		
		if(valid){
			error = false;
			$('#registerForm').submit();
		}
	});

	$('#accountSubmit').click(function(e){
		e.preventDefault();
		var valid = true;
		
		if($('#email').val() == "" || $('#email').val().length < 6){
			valid = false;
			showFieldError($('#email'));
		}else if(!($('#email').val().indexOf("@")!=-1) || !($('#email').val().indexOf(".")!=-1)){
			valid = false;
			showFieldError($('#email'));
		}
		
		if($('#reemail').val() != $('#email').val()){
			valid = false;
			showFieldError($('#reemail'));
		}
		
		if($('#passwordregister').val() == "" || $('#passwordregister').val().length < 8){
			valid = false;
			showFieldError($('#passwordregister')); 
		}
		
		if($('#repasswordregister').val() != $('#passwordregister').val()){
			valid = false;
			showFieldError($('#repasswordregister'));
		}
		
		if(valid){
			error = false;
			$('#registerForm').submit();
		}
	});
	
	$('#email').keyup(function(){
		if(error)
		{
			if($('#email').val() != "" && $('#email').val().length >= 6 && $('#email').val().indexOf("@")!=-1 && $('#email').val().indexOf(".")!=-1){				
				hideFieldError($('#email'));
			}
			else
			{
				showFieldError($('#email'));
			}
			
			if($('#reemail').val() == $('#email').val()){				
				hideFieldError($('#reemail'));
			}
			else
			{
				showFieldError($('#reemail'));
			}
		}
	});
	
	$('#reemail').keyup(function(){
		if(error)
		{
			if($('#reemail').val() == $('#email').val()){				
				hideFieldError($('#reemail'));
			}
			else
			{
				showFieldError($('#reemail'));
			}
		}
	});

	$('#passwordregister').keyup(function(){
		if(error)
		{
			if($('#passwordregister').val() != "" && $('#passwordregister').val().length >= 8){				
				hideFieldError($('#passwordregister'));
			}
			else
			{
				showFieldError($('#passwordregister'));
			}
			
			if($('#repasswordregister').val() == $('#passwordregister').val()){				
				hideFieldError($('#repasswordregister'));
			}
			else
			{
				showFieldError($('#repasswordregister'));
			}
		}
	});

	$('#repasswordregister').keyup(function(){
		if(error)
		{
			if($('#repasswordregister').val() == $('#passwordregister').val()){				
				hideFieldError($('#repasswordregister'));
			}
			else
			{
				showFieldError($('#repasswordregister'));
			}
		}
	});

	$('#savesubmit').click(function(e){
		e.preventDefault();
		var valid = true;
		
		if($('#email').val() == "" || $('#email').val().length < 6){
			valid = false;
			showFieldError($('#email'));
		}else if(!($('#email').val().indexOf("@")!=-1) || !($('#email').val().indexOf(".")!=-1)){
			valid = false;
			showFieldError($('#email'));
		}
		
		if($('#password').val() == "" || $('#password').val().length < 8){
			valid = false;
			showFieldError($('#password')); 
		}
		
		if($('#repassword').val() != $('#password').val()){
			valid = false;
			showFieldError($('#repassword'));
		}
		
		
		
		if(valid){
			error = false;
			$('#profileForm').submit();
		}
	});
	
	
	$('#createnewsurvey').click(function(e){
		e.preventDefault();
		var valid = true;
		if($('#surveyTitle').val() == ""){
			valid = false;
			showFieldError($('#surveyTitle'));
		}
		
		if($('#surveyProject').val() == ""){
			valid = false;
			showFieldError($('#surveyProject'));
		}
		
		if(valid){
			error = false;
			$('#formcreatenewsurvey').submit();
		}
		
	});
	
	$('#cancelprofile').click(function(e){
		window.location.href = "InitialServlet";
		//window.location.href = 'http://example.com';
	});
	
	$('.form-group').on('keyup', 'input', function(){
		if(error)
		{
			var value = $(this).val();
			
			if(value != ''){hideFieldError($(this));}
			else{showFieldError($(this));}
		}
	});
	
	$('.form-group').on('keyup', 'textarea', function(){
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

function showFieldErrorRegisterUsername(element)
{	
	var errorId = element.attr('id') + 'length-error';
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
