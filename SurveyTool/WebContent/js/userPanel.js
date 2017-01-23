/**
 * 
 */

$(function() {
	
	$("#registrateConditions").click(function(){
		var checked = $("#acceptConditions").prop('checked');
		if(checked)
		{
			$("#conditionsForm").submit();			
		}
		else
		{
			$('#notAcceptConditions').modal('show');
		}		
	});
	
});

