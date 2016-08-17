/**
 * 
 */

var surveyTree;
var req = {};

$(function() {

	var host = "http://" + window.location.host;
	console.log("surveyTree: " + JSON.stringify(surveyTree));
	
	
	//Create the question list of the select when focus in select control
	$(".survey-sections").on("focusin", "select.dependence-question", function(e){
		var numPage = $(this).closest("li.page").attr("index");
		var currentValue = $(this).val(); 
		console.log("numPage: " + numPage + " - value: " + currentValue);
		var defaultOption = $(this).find('option.default-option');
		$(this).empty();
		$(this).append(defaultOption);
		var pageId = currentValue.split("-")[0];
		var qId = currentValue.split("-")[1];
		
		for(var i = 0; i < numPage - 1; i++)
		{
			var pageJson = surveyTree[i];
			console.log("Page number: "+i+", page="+pageJson+", surveyTree="+surveyTree);
			
			if(pageJson.pageId == pageId){
				currentValue = pageJson.numPage+"-"+qId;
			}
			
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
	
	
	$(".survey-sections").on("focusin", "select.dependence-option", function(e){
		var currentValue = $(this).val(); 
		var value = $(this).val();
		var defaultOption = $(this).find('option.default-option');
		$(this).empty();
		$(this).append(defaultOption);
		
		
		
		var question = $(this).closest("fieldset").find("select.dependence-question").val(); 
		console.log(question);
			
		if(question != 'none')
		{	
			var questionParamAux = question.split("-");
			var questionParamCont;
			
			var numPage = $(this).closest("li.page").attr("index");
			for(var i = 0; i < numPage - 1; i++)
			{
				var pageJson = surveyTree[i];
				if(pageJson.pageId == questionParamAux[0]){
					question = pageJson.numPage+"-"+questionParamAux[1];
					i=numPage;
				}
			}
			
			var questionParam = question.split("-");
			
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
						$(this).append(optionHtml);
					}
				}
			}			
		}
		
		$(this).val(currentValue);
	});
	
	//Create the question list of the select when focus in select control in logic section
	$(".survey-sections").on("focusin", "select.logic-option-goto", function(e){
		var numPage = $(this).closest("li.page").attr("index");
		var currentValue = $(this).val();	
		//Total number of pages
		var totalNumPages = $(this).closest(".section-pages").find("li.page").size();
		var defaultOption = $(this).find('option.default-option');
		$(this).empty();
		
		$(this).append(defaultOption);
		for(var i = numPage; i < parseFloat(totalNumPages); i++)
		{
			var pageJson = surveyTree[i];
			
			for(var j = 0; j < pageJson.questions.length; j++)
			{
				var questionJson = pageJson.questions[j];
				var type = questionJson.type;
				var optionHtml = '<option value="' + questionJson.questionId + '">' + questionJson.title + '</option>';
				$(this).append(optionHtml);
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
		console.log(value);
		
		if(value != 'none')
		{
			console.log("Tranatndo de cambiar el options");
			var questionParam = value.split("-");			
			var question = $.grep(surveyTree[parseInt(questionParam[0]) - 1].questions, function( n, i ) {
				return n.questionId===parseInt(questionParam[1]);
			})[0];
			
			if(question.optionsGroup != null && question.optionsGroup.length > 0)
			{
				console.log("Hay optionGroups");
				for(var i = 0; i < question.optionsGroup.length; i++)
				{
					var optionsGroup = question.optionsGroup[i];
					for(var j = 0; j < optionsGroup.options.length; j++)
					{
						console.log("Hay options");
						var option = optionsGroup.options[j];
						var optionHtml = '<option value="' + optionsGroup.optionsGroupId + '-' + option.optionId + '">' + option.title + '</option>';
						optionSelect.append(optionHtml);
					}
				}
			}			
		}
		else
		{
			var root = $(this).closest("fieldset");
			setDependence(root);
		
			$.ajax({ 
				type: "POST",
				dataType: "text",
				contentType: "text/plain",
				url: host + "/SurveyTool/api/QCService/removeDependenceValue",
				data: JSON.stringify(req),
				success: function (data) {
					console.log("Dentro del function: "+data);
					var index = root.closest("li.dependence-item").attr("index");
					root.closest("li.dependence-item").attr("index", index*(-1));
				},
				error: function (xhr, ajaxOptions, thrownError) {
					console.log(xhr.status);
					console.log(thrownError);
					console.log(xhr.responseText);
					console.log(xhr);
				}
			});
		}
	});
	
	$(".survey-sections").on("change", "select.dependence-option", function(e){
		var value = $(this).val();
				
		if(value != 'none')
		{
			
			var myIndex = $(this).closest("li").attr("index");
			var list = $(this).closest("ul");
			var valid = true;
			var value = $(this).children(':selected').val();
			
			for (var i=1;i<=list.find("li").length;i++){
				if(myIndex != list.find("li:nth-child("+i+")").attr("index")){
				console.log("mio:"+value+", otro:"+list.find("li:nth-child("+i+")").find("select.dependence-option").children(':selected').val());
				if(value === list.find("li:nth-child("+i+")").find("select.dependence-option").children(':selected').val()){
					valid=false;
					i=list.find("li").length+1;
				}
				}
			}
			
			if(!valid){	
				console.log("en show error");
				$(this).closest("div").addClass("has-error has-feedback");
				$(this).attr('aria-describedby', '#dependence-option-error');
				$(this).closest("div").find('#dependence-option-error').removeClass('hidden');
				$(this).closest("div").find('#dependence-option-feedback').removeClass('hidden');
				
				var root = $(this).closest("fieldset");
				setDependence(root);
				
				$.ajax({ 
					   type: "POST",
					   dataType: "text",
					   contentType: "text/plain",
					   url: host + "/SurveyTool/api/QCService/removeDependenceValue",
					   data: JSON.stringify(req),
					   success: function (data) {
						   console.log("Dentro del function: "+data);
						   var index = root.closest("li.dependence-item").attr("index");
						   root.closest("li.dependence-item").attr("index", index*(-1));
					   },
					   error: function (xhr, ajaxOptions, thrownError) {
						   console.log(xhr.status);
						   console.log(thrownError);
						   console.log(xhr.responseText);
						   console.log(xhr);
					   }
					});
				
			}else{
				$(this).closest("div").removeClass("has-error has-feedback");
				$(this).removeAttr('aria-describedby');
				$(this).closest("div").find('#dependence-option-error').addClass('hidden');
				$(this).closest("div").find('#dependence-option-feedback').addClass('hidden');
				
				var root = $(this).closest("fieldset");
				setDependence(root);
				
				$.ajax({ 
					   type: "POST",
					   dataType: "text",
					   contentType: "text/plain",
					   url: host + "/SurveyTool/api/QCService/insertDependence",
					   data: JSON.stringify(req),
					   success: function (data) {
						   console.log("Dentro del function: "+data);
						   if(data != '')
						   {
							   var json = JSON.parse(data);
								
							   if(json.hasOwnProperty('qdependenceId'))
							   {
								   console.log("qdependenceId: "+json.qdependenceId+", previous id="+root.closest("ul.dependences-list").attr("index"));
								   root.closest("ul.dependences-list").attr("index", json.qdependenceId);						   
							   }
							   
							   if(json.hasOwnProperty('qDependeceItemId'))
							   {
								   console.log("qDependeceItemId: "+json.qDependeceItemId);
								   root.closest("li.dependence-item").attr("index", json.qDependeceItemId);						   
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
		}
		else
			{
			var root = $(this).closest("fieldset");
			setDependence(root);
			
			$.ajax({ 
				   type: "POST",
				   dataType: "text",
				   contentType: "text/plain",
				   url: host + "/SurveyTool/api/QCService/removeDependenceValue",
				   data: JSON.stringify(req),
				   success: function (data) {
					   console.log("Dentro del function: "+data);
					   var index = root.closest("li.dependence-item").attr("index");
					   root.closest("li.dependence-item").attr("index", index*(-1));
				   },
				   error: function (xhr, ajaxOptions, thrownError) {
					   console.log(xhr.status);
					   console.log(thrownError);
					   console.log(xhr.responseText);
					   console.log(xhr);
				   }
				});
			}
	});
	
	
	
	$(".survey-sections").on("change", "select.dependence-condition", function(e){
		var value = $(this).val();
				
		var root = $(this).closest("fieldset");
		setDependence(root);
			
			$.ajax({ 
				   type: "POST",
				   dataType: "text",
				   contentType: "text/plain",
				   url: host + "/SurveyTool/api/QCService/updateDependenceType",
				   data: JSON.stringify(req),
				   success: function (data) {
					   console.log("Dentro del function: "+data);
				   },
				   error: function (xhr, ajaxOptions, thrownError) {
					   console.log(xhr.status);
					   console.log(thrownError);
					   console.log(xhr.responseText);
					   console.log(xhr);
				   }
				});		
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
	
	$(".survey-sections").on("change", "select.dependence-condition", function(e){
		var root = $(this).closest("fieldset");
		var questionvalue = root.find("select.dependence-question").val();
		var optionvalue = root.find("select.dependence-option").val();
		var index = $(this).val();
		console.log("index of conditional:"+index);
		if(questionvalue != "none" && optionvalue != "none")
		{
			setDependence(root);
		}
	});
	
	
	$(".survey-sections").on("change", "select.logic-option-goto", function(e){
		var root = $(this).closest("li.logic-option");
		setLogic(root);
			
		$.ajax({ 
			type: "POST",
			dataType: "text",
			contentType: "text/plain",
			url: host + "/SurveyTool/api/QCService/insertLogic",
			data: JSON.stringify(req),
			success: function (data) {
				console.log("Dentro del function: "+data);
			},
			error: function (xhr, ajaxOptions, thrownError) {
				console.log(xhr.status);
				console.log(thrownError);
				console.log(xhr.responseText);
				console.log(xhr);
			}
		});	
		
	});
	
	
	$(".survey-sections").on("click", ".dependences-add-button > button", function(){
		console.log("Add button")
		var dependencesList = $(this).closest("div.dependences-settings").find("ul");
		
		var depTemp = dependencesList.find("li:first-child").clone();
		var numElem = dependencesList.find("li").size();
		
		console.log(dependencesList.attr("index"));
		
		var title = $(this).closest("li.panel-question").find("input.survey-question-title").val();
		
		depTemp.removeClass("hidden");
		depTemp.attr("index", numElem*(-1));
		
		var text = depTemp.find("fieldset > legend").html();
		depTemp.find("fieldset > legend").html(numElem + text + title);
		
		text = depTemp.find("label.dependence-action-label").attr("for") + numElem;
		depTemp.find("label.dependence-action-label").attr("for", text);
		depTemp.find("select.dependence-action").attr("id", text);

		console.log(numElem);
		if(numElem===1){
			$(this).closest("div.dependences-frame").find("button.btn-close-dependences").removeClass("hidden");
		}
		if(numElem===2){
			//Only one dependence, the new one needs to have the condition selector
			console.log("numElem: "+numElem);
			depTemp.find("label.dependence-question-label").addClass("hidden");
			depTemp.find("select.dependence-condition").removeClass("hidden");
			text = depTemp.find("label.dependence-condition-label").attr("for") + numElem;
			depTemp.find("label.dependence-condition-label").attr("for", text);
			depTemp.find("select.dependence-condition").attr("id", text);			
			depTemp.find("label.next-dependence-question-label").removeClass("hidden");
			text = depTemp.find("label.next-dependence-question-label").attr("for") + numElem;
			depTemp.find("label.next-dependence-question-label").attr("for", text);
			depTemp.find("#fieldset-dependence").addClass("fieldset-second-dependence");
		} else if(numElem>=3){
			//There are two dependences. The new one needs to change the sentence and delete the condition selector
			$("ul.dependences-list li:nth-child(3)").find("select.dependence-condition").val()
			console.log("numElem: "+numElem);
			depTemp.find("label.dependence-question-label").addClass("hidden");
			depTemp.find("select.dependence-condition").addClass("hidden");
			depTemp.find("label.next-dependence-question-label").removeClass("hidden");
			text = depTemp.find("label.next-dependence-question-label").attr("for") + numElem;
			depTemp.find("label.next-dependence-question-label").attr("for", text);
			console.log($("ul.dependences-list li:nth-child(3)").find("select.dependence-condition").val());
			depTemp.find("#fieldset-dependence").addClass("fieldset-next-dependences");
		}
		else{
			text = depTemp.find("label.dependence-question-label").attr("for") + numElem;
			depTemp.find("label.dependence-question-label").attr("for", text);
		}
		
		depTemp.find("select.dependence-question").attr("id", text);
		
		text = depTemp.find("label.dependence-option-label").attr("for") + numElem;
		depTemp.find("label.dependence-option-label").attr("for", text);
		depTemp.find("select.dependence-option").attr("id", text);
		
		dependencesList.append(depTemp);
		
		console.log("Title: " + title);
	});
	
	$('.section-pages').on("click", "#remove-dependence", function(e){
		console.log("Remove dependence item");
		var root = $(this).closest("fieldset");
		var itemindex = root.closest("li.dependence-item").attr("index");
		var question = (root.find("select.dependence-question")).children(':selected').text();
		var option = (root.find("select.dependence-option")).children(':selected').text();
		var questionIndex = $(this).closest('li.panel-question').attr("qid");
		$("#elementToRemoveText").html('"Dependence: question-' + question + ', option-'+option+'"');
		$("#removeElemId").val(questionIndex + '/' + itemindex);
		$("#removeElemService").val('QCService');
		$("#removeElement").modal("show");
	});
	
});


function setDependence(root)
{	
	var questionparams = root.find("select.dependence-question").val().split("-");
	var optionparams = root.find("select.dependence-option").val().split("-");
	req = {};
	req.pid = questionparams[0];
	req.qid = questionparams[1];
	req.ogid = optionparams[0];
	req.oid = optionparams[1];	
	req.action = root.find("select.dependence-action").val();
	
	req.index = root.closest("ul.dependences-list").attr("index");
	req.itemindex = root.closest("li.dependence-item").attr("index");
	req.questionid = root.closest("li.panel-question").attr("qid");
	
	if(root.attr("class")==="fieldset-second-dependence")
		req.deptype = root.find("select.dependence-condition").val();
	
	console.log("Dependence request: " + JSON.stringify(req));
}

function setLogic(root)
{	
	req = {};
	req.qid = root.closest("li.panel-question").attr("qid");
	req.ogid = root.attr("ogid");
	req.oid = root.attr("oid");	
	
	if (root.find("select.logic-option-goto").val() != 'none'){
		console.log("The value for questionId is:"+root.find("select.logic-option-goto").val()+" and it is detected as different to none");
		req.questionid = root.find("select.logic-option-goto").val();
	}
	else
		console.log("The value for questionId is:"+root.find("select.logic-option-goto").val()+" and it is detected as none");
	
	console.log("Logic request: " + JSON.stringify(req));
}
