/**
 * 
 */

var surveyTree;

$(function() {

	//console.log("surveyTree: " + JSON.stringify(surveyTree));
	
	//Create the question list of the select when focus in select control
	$(".survey-sections").on("focusin", "select.dependence-question", function(e){
		var numPage = $(this).closest("li.page").attr("index");
		var currentValue = $(this).val(); 
		console.log("numPage: " + numPage + " - value: " + currentValue);
		var defaultOption = $(this).find('option.default-option');
		$(this).empty();
		$(this).append(defaultOption);
		
		for(var i = 0; i < numPage - 1; i++)
		{
			var pageJson = surveyTree[i];
			for(var j = 0; j < pageJson.questions.length; j++)
			{
				var questionJson = pageJson.questions[j];
				var type = questionJson.type;
				if(type === "simple")
				{
					var optionHtml = '<option value="' + pageJson.numPage + '-' + questionJson.questionId + '">' + questionJson.title + '</option>';
					$(this).append(optionHtml);
				}
			}
		}
		
		$(this).val(currentValue);
	});
	
	
	$(".survey-sections").on("change", "select.dependence-question", function(e){
		var value = $(this).val();
		var optionSelect = $(this).closest("fieldset").find("select.dependence-option");
		var defaultOption = optionSelect.find('option.default-option');
		optionSelect.empty();
		optionSelect.append(defaultOption);
		
		if(value != 'none')
		{	
			var questionParam = value.split("-");			
			var question = $.grep(surveyTree[parseInt(questionParam[0]) - 1].questions, function( n, i ) {
				return n.questionId===parseInt(questionParam[1]);
			})[0];
			
			if(question.optionsGroup != null && question.optionsGroup.length > 0)
			{
				for(var i = 0; i < question.optionsGroup.length; i++)
				{
					var optionsGroup = question.optionsGroup[i];
					for(var j = 0; j < optionsGroup.options.length; j++)
					{
						var option = optionsGroup.options[j];
						var optionHtml = '<option value="' + optionsGroup.optionsGroupId + '-' + option.optionId + '">' + option.title + '</option>';
						optionSelect.append(optionHtml);
					}
				}
			}			
		}		
	});
	
	$(".survey-sections").on("change", "select.dependence-option", function(e){
		var value = $(this).val();
				
		if(value != 'none')
		{
			var root = $(this).closest("fieldset");
			setDependence(root);
			/*var questionparams = $(this).closest("fieldset").find("select.dependence-question").val().split("-");
			var optionparams = value.split("-");
			var req = {};
			req.pid = questionparams[0];
			req.qid = questionparams[1];
			req.ogid = optionparams[0];
			req.oid = optionparams[1];
			req.action = $(this).closest("fieldset").find("select.dependence-action").val();
			console.log("Dependence request: " + JSON.stringify(req));*/
		}
	});
	
	$(".survey-sections").on("change", "select.dependence-action", function(e){
		var root = $(this).closest("fieldset");
		var questionvalue = root.find("select.dependence-question").val();
		var optionvalue = root.find("select.dependence-option").val();
		if(questionvalue != "none" && optionvalue != "none")
		{
			setDependence(root);
		}
	});
	
	$(".survey-sections").on("click", ".dependences-add-button > button", function(){
		var dependencesList = $(this).closest("div.dependences-settings").find('ul');
		var depTemp = dependencesList.find('li:first-child').clone();
		var numElem = dependencesList.find("li").size();
		var title = $(this).closest("li.panel-question").find("input.survey-question-title").val();
		
		depTemp.removeClass("hidden");
		
		var text = depTemp.find("fieldset > legend").html();
		depTemp.find("fieldset > legend").html(numElem + text + title);
		
		text = depTemp.find("label.dependence-action-label").attr("for") + numElem;
		depTemp.find("label.dependence-action-label").attr("for", text);
		depTemp.find("select.dependence-action").attr("id", text);

		text = depTemp.find("label.dependence-question-label").attr("for") + numElem;
		depTemp.find("label.dependence-question-label").attr("for", text);
		depTemp.find("select.dependence-question").attr("id", text);

		text = depTemp.find("label.dependence-option-label").attr("for") + numElem;
		depTemp.find("label.dependence-option-label").attr("for", text);
		depTemp.find("select.dependence-option").attr("id", text);
		
		dependencesList.append(depTemp);
		
		console.log("Title: " + title);
	});
	
});

function setDependence(root)
{
	var questionparams = root.find("select.dependence-question").val().split("-");
	var optionparams = root.find("select.dependence-option").val().split("-");
	var req = {};
	req.pid = questionparams[0];
	req.qid = questionparams[1];
	req.ogid = optionparams[0];
	req.oid = optionparams[1];
	req.action = root.find("select.dependence-action").val();
	console.log("Dependence request: " + JSON.stringify(req));
}
