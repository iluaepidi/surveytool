/**
 * 
 */

var surveyTree;
var req = {};

$(function() {

	var host = "http://" + window.location.host;
	console.log("surveyTree: " + JSON.stringify(surveyTree));
	
	$(".survey-sections").on("click", "button.btn-dependences", function(){
		var root = $(this).closest("div.rules-frame") 
		root.find("fieldset.dependences-frame").removeClass("hidden");
		root.find("fieldset.logic-frame").addClass("hidden");
		$(this).addClass("active");
		root.find("button.btn-logic").removeClass("active");
	});
	$(".survey-sections").on("click", "button.btn-logic", function(){
		var root = $(this).closest("div.rules-frame") 
		root.find("fieldset.logic-frame").removeClass("hidden");
		root.find("fieldset.dependences-frame").addClass("hidden");
		$(this).addClass("active");
		root.find("button.btn-dependences").removeClass("active");
	});
	
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
			var pageInserted = false;
			var pageJson = surveyTree[i];
			console.log("Page number: "+i+", page="+pageJson+", surveyTree="+surveyTree);
			
			if(pageJson.pageId == pageId){
				currentValue = pageJson.pageId+"-"+qId;
			}			
			
			for(var j = 0; j < pageJson.questions.length; j++)
			{
				var questionJson = pageJson.questions[j];
				var type = questionJson.type;
				if(type === "simple")
				{
					if(!pageInserted)
					{
						var numPageText = $('li[pid=' + pageJson.pageId + ']').find('h4').html();
						var optionPageHtml = '<option class="option-page-qdependences" disabled="disabled">' + numPageText + '</option>';
						$(this).append(optionPageHtml);
						pageInserted = true;
					}
					var numQuestion = $('li[qid=' + questionJson.questionId + ']').find('span.num-question').html();
					var optionHtml = '<option class="option-qdependences" id="question-dependence-' + pageJson.pageId + '-' + questionJson.questionId + '" value="' + pageJson.pageId + '-' + questionJson.questionId + '"> - ' + numQuestion + ' ' + questionJson.title + '</option>';
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

			console.log("surveyTree focusin question: " + JSON.stringify(question));
			var questionParam = question.split("-");
			
			question = $.grep(surveyTree[parseInt(questionParam[0]) - 1].questions, function( n, i ) {
				return n.questionId===parseInt(questionParam[1]);
			})[0];
			console.log("surveyTree focusin option depend: " + JSON.stringify(surveyTree));
			if(question.optionsGroup != null && question.optionsGroup.length > 0)
			{
				for(var i = 0; i < question.optionsGroup.length; i++)
				{
					var optionsGroup = question.optionsGroup[i];
					for(var j = 0; j < optionsGroup.options.length; j++)
					{
						var option = optionsGroup.options[j];
						var optionHtml = '<option id="option-dependence-' + optionsGroup.optionsGroupId + '-' + option.optionId + '" value="' + optionsGroup.optionsGroupId + '-' + option.optionId + '">' + option.title + '</option>';
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
		//var totalNumPages = $(this).closest(".section-pages").find("li.page").size();
		var totalNumPages = surveyTree.length;
		console.log("Num pages: " + totalNumPages);
		var defaultOption = $(this).find('option.default-option');
		$(this).empty();
		
		$(this).append(defaultOption);
		for(var i = numPage; i < parseFloat(totalNumPages); i++)
		{
			var pageInserted = false;
			var pageJson = surveyTree[i];
			
			for(var j = 0; j < pageJson.questions.length; j++)
			{
				if(!pageInserted)
				{
					var numPageText = $('li[pid=' + pageJson.pageId + ']').find('h4').html();
					var optionPageHtml = '<option class="option-page-qdependences" disabled="disabled">' + numPageText + '</option>';
					$(this).append(optionPageHtml);
					pageInserted = true;
				}
				var questionJson = pageJson.questions[j];
				var type = questionJson.type;
				var numQuestion = " ";
				if(type != "bcontent") numQuestion = $('li[qid=' + questionJson.questionId + ']').find('span.num-question').html();
				var optionHtml = '<option id="option-goto-' + questionJson.questionId + '" value="' + questionJson.questionId + '"> - ' + numQuestion + ' ' + questionJson.title + '</option>';
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
			console.log("Value not none");
			
			if($(this).closest("li.dependence-item").attr("index")>=0){
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
			
			var questionParam = value.split("-");	
			
			var page = $.grep(surveyTree, function( n, i ) {
				return n.pageId === parseInt(questionParam[0]);
			})[0];
			
			var question = $.grep(page.questions, function( n, i ) {
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
			console.log("Value = none");
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
		var select = $(this);
		console.log("in on change");		
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
						   if(index>0)
							   root.closest("li.dependence-item").attr("index", index*(-1));
						   
						   setDepLabelCounter(select.closest("div.rules-frame"));
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
					   if(root.closest('li').is(':last-child'))
					   {
						   root.removeClass("disable");
						   root.closest(".dependences-settings").find("div.dependences-add-button").find("button").prop("disabled", false);
					   }
					   	
					   setDepLabelCounter(select.closest("div.rules-frame"));
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
				   
				   setDepLabelCounter(select.closest("div.rules-frame"));
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
					   //console.log("Dentro del function: "+data);
					   root.closest("ul").find("li > fieldset[condition]").each(function(indice, element){
						   $(element).attr("condition", req.deptype);
					   });					   
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
		var value = $(this).val();
		var select = $(this);
		console.log("logic value: " + value);
			
		$.ajax({ 
			type: "POST",
			dataType: "text",
			contentType: "text/plain",
			url: host + "/SurveyTool/api/QCService/insertLogic",
			data: JSON.stringify(req),
			success: function (data) {
				console.log("Dentro del function: "+data);
				var labelCounter = select.closest("div.rules-frame ").find("#logicCounter")
				var counter = 0;
				select.closest("ul.logic-option-list").find("select.logic-option-goto").each(function(index, element){
					if($(element).val() != "none")
					{
						counter++;
					}
				});
				console.log("Logic counter: " + counter);
				if(counter > 0)
				{
					labelCounter.html(counter);
					labelCounter.removeClass("hidden");
				}
				else
				{
					labelCounter.html(counter);
					labelCounter.addClass("hidden");
				}				
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
		var condition=  $(this).closest("div.dependences-settings").find("select.dependence-condition:not(.hidden)").val();
		//console.log("condition selected: " + condition);
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
			//$(this).closest("div.dependences-frame").find("button.btn-close-dependences").removeClass("hidden");
			depTemp.find("label.dependence-question-label").addClass("hidden");
			depTemp.find("select.dependence-condition").addClass("hidden");
			depTemp.find("label.next-dependence-question-label").removeClass("hidden");
			text = depTemp.find("label.next-dependence-question-label").attr("for") + numElem;
			depTemp.find("label.next-dependence-question-label").attr("for", text);
			console.log($("ul.dependences-list li:nth-child(3)").find("select.dependence-condition").val());
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
			//depTemp.find("#fieldset-dependence").addClass("fieldset-second-dependence");
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
			depTemp.find("span.static-condition-and").removeClass("hidden");
			depTemp.find("span.static-condition-or").removeClass("hidden");
			depTemp.find("fieldSet").attr("condition", condition);
			//depTemp.find("#fieldset-dependence").addClass("fieldset-next-dependences");
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
		
		$(this).prop( "disabled", true );		 
		
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
		modalFocus = $(this);
	});
	
	$('.section-pages').on("rmvNoConfirm", "#remove-dependence", function(e){
		console.log("Remove dependence item");
		var root = $(this).closest("fieldset");
		var itemindex = root.closest("li.dependence-item").attr("index");
		var question = (root.find("select.dependence-question")).children(':selected').text();
		var option = (root.find("select.dependence-option")).children(':selected').text();
		var questionIndex = $(this).closest('li.panel-question').attr("qid");
		$("#elementToRemoveText").html('"Dependence: question-' + question + ', option-'+option+'"');
		$("#removeElemId").val(questionIndex + '/' + itemindex);
		$("#removeElemService").val('QCService');
		
		$('#acceptRemoveElement').trigger("click")
	});
	
	$('.survey-sections').on("click", ".logic-button > button", function(){
		$(this).parent().addClass('hidden');
		$(this).closest('div.logic-frame').find('div.logic-settings').removeClass('hidden');
		$(this).closest('div.logic-frame').find('button.btn-close-logic').removeClass('hidden');
	});

	$('.survey-sections').on("click", "button.btn-close-logic", function(){
		$(this).closest('fieldset.logic-frame').addClass('hidden');
		var root = $(this).closest("div.rules-frame").find("button.btn-logic").removeClass("active");
	});

	$('.survey-sections').on("click", ".dependences-button > button", function(){
		$(this).parent().addClass('hidden');
		$(this).closest('div.dependences-frame').find('div.dependences-settings').removeClass('hidden');
		//$(this).closest('div.dependences-frame').find('button.btn-close-dependences').removeClass('hidden');
	});

	$('.survey-sections').on("click", "button.btn-close-dependences", function(){
		$(this).closest('fieldset.dependences-frame').addClass('hidden');
		var root = $(this).closest("div.rules-frame").find("button.btn-dependences").removeClass("active");
		//$(this).closest('div.dependences-frame').find('div.dependences-settings').addClass('hidden');
		//$(this).addClass('hidden');
	});
	
	$('.survey-sections').on("goto", "#option-list #option-item input", function(e){
		var optionId = $(this).attr('oid');
		var optionGroupId = $(this).closest('ul').attr('ogid');
		console.log("Option goto: " + $(this).val() + " - oid: " + optionId);
		var logicElem = $('#logic-option-' + optionId);

		var text = $(this).val();
		if(text === "") 
		{
			text = $(this).closest('li.option-item').find('li.multimedia-item a').text().split(' - ')[0];
		}
		if(logicElem.length)
		{
			logicElem.html(text);
		}
		else
		{
			var logicList = $(this).closest("div.panel-body").find("ul.logic-option-list");
			if(logicList.hasClass('hidden'))
			{
				logicList.removeClass("hidden");
				var elem = logicList.find('span');
				elem.attr("id", "logic-option-" + optionId);
				elem.html(text);
				var logicOption = logicList.find(".logic-option");
				logicOption.find("select.logic-option-goto").val('none');
				logicOption.attr("oid", optionId);
				logicOption.attr("ogid", optionGroupId);
				logicList.closest(".logic-settings").find("p").addClass("hidden");
			}
			else
			{
				var ulLogic = $(this).closest("div.question-frame").next().find("ul.logic-option-list");
				var clonElem = ulLogic.find("li").first().clone();
				clonElem.attr("oid", optionId);
				clonElem.attr("ogid", optionGroupId);
				var clonSpan = clonElem.find("span");
				clonSpan.attr("id", "logic-option-" + optionId);
				clonSpan.html(text);
				clonElem.find("select.logic-option-goto").val('none');
				ulLogic.append(clonElem);
			}
		}
		
	});

	$('.survey-sections').on("gotoOther", "input.otherOptionTitle", function(e){
		var optionId = $(this).closest("li.option-item").attr('oid');
		var optionGroupId = $(this).closest('ul').attr('ogid');
		console.log("Option other goto: " + $(this).val() + " - oid: " + optionId);
		var logicElem = $('#logic-option-' + optionId);

		var text = $(this).val();
		
		if(logicElem.length)
		{
			logicElem.html(text);
		}
		else
		{
			var logicList = $(this).closest("div.panel-body").find("ul.logic-option-list");
			if(logicList.hasClass('hidden'))
			{
				logicList.removeClass("hidden");
				var elem = logicList.find('span');
				elem.attr("id", "logic-option-" + optionId);
				elem.html(text);
				var logicOption = logicList.find(".logic-option");
				logicOption.find("select.logic-option-goto").val('none');
				logicOption.attr("oid", optionId);
				logicOption.attr("ogid", optionGroupId);
				logicList.closest(".logic-settings").find("p").addClass("hidden");
			}
			else
			{
				var ulLogic = $(this).closest("div.question-frame").next().find("ul.logic-option-list");
				var clonElem = ulLogic.find("li").first().clone();
				clonElem.attr("oid", optionId);
				clonElem.attr("ogid", optionGroupId);
				var clonSpan = clonElem.find("span");
				clonSpan.attr("id", "logic-option-" + optionId);
				clonSpan.html(text);
				clonElem.find("select.logic-option-goto").val('none');
				ulLogic.append(clonElem);
			}
		}
		
	});

	$('.survey-sections').on("setJson", "#option-list #option-item input", function(e){
		var optionId = $(this).attr('oid');
		var pageId = $(this).closest("li.page").attr("pid");
		var questionId = $(this).closest("li.panel-question").attr("qid");
		var optionGroupId = $(this).closest("ul.option-list").attr("ogid");
		var index = $(this).attr("index");
		var text = $(this).val();
		if(text === "") 
		{
			text = $(this).closest('li.option-item').find('li.multimedia-item a').text().split(' - ')[0];
		}
		
		setJson(optionId, pageId, questionId, optionGroupId, index, text);	
				
		$(this).trigger("setOptionDepend");
	
		//console.log("surveyTree changed: " + JSON.stringify(surveyTree));
	});
	
	$('.survey-sections').on("setJsonOther", "input.otherOptionTitle", function(e){
		var optionId = $(this).closest("li.option-item").attr('oid');
		var pageId = $(this).closest("li.page").attr("pid");
		var questionId = $(this).closest("li.panel-question").attr("qid");
		var optionGroupId = $(this).closest("ul.option-list").attr("ogid");
		var index = 999;
		var text = $(this).val();
		
		setJson(optionId, pageId, questionId, optionGroupId, index, text);	
				
		$(this).trigger("setOptionOtherDepend");
	
		//console.log("surveyTree changed: " + JSON.stringify(surveyTree));
	});
	
	$('.survey-sections').on("rmvOptJson", "#option-list #option-item input", function(e){		
		var optionId = $(this).attr('oid');
		var pageId = $(this).closest("li.page").attr("pid");
		var questionId = $(this).closest("li.panel-question").attr("qid");
		var optionGroupId = $(this).closest("ul.option-list").attr("ogid");
		
		//console.log("Entra en rmvOptJson - oId: " + optionId + " - ogid: " + optionGroupId + " - qid: " + questionId + " - pid: " + pageId);
		
		$(surveyTree).each(function(index, pageElem){
		    if(pageElem.pageId == pageId)
		    {
		    	$(pageElem.questions).each(function(index, questionElem){
		    		if(questionElem.questionId == questionId)
		    		{
		    			$(questionElem.optionsGroup).each(function(index, ogElem){
				    		if(ogElem.optionsGroupId == optionGroupId)
				    		{
				    			ogElem.options = $.grep(ogElem.options, function(option) {
				    				return option.optionId != optionId;  
				    			});						    					    		
				    		}
				    	});
		    		}
		    	});
		    }		    
		});
		
		$(this).trigger('rmvOptionDepend');
		//console.log("surveyTree removed: " + JSON.stringify(surveyTree));
	});

	$('.survey-sections').on("rmvOptOtherJson", "input.otherOptionTitle", function(e){
		var optionId = $(this).closest("li.option-item").attr('oid');
		var pageId = $(this).closest("li.page").attr("pid");
		var questionId = $(this).closest("li.panel-question").attr("qid");
		var optionGroupId = $(this).closest("ul.option-list").attr("ogid");
		
		//console.log("Entra en rmvOptJson - oId: " + optionId + " - ogid: " + optionGroupId + " - qid: " + questionId + " - pid: " + pageId);
		
		$(surveyTree).each(function(index, pageElem){
		    if(pageElem.pageId == pageId)
		    {
		    	$(pageElem.questions).each(function(index, questionElem){
		    		if(questionElem.questionId == questionId)
		    		{
		    			$(questionElem.optionsGroup).each(function(index, ogElem){
				    		if(ogElem.optionsGroupId == optionGroupId)
				    		{
				    			ogElem.options = $.grep(ogElem.options, function(option) {
				    				return option.optionId != optionId;  
				    			});						    					    		
				    		}
				    	});
		    		}
		    	});
		    }		    
		});
		
		$(this).trigger('rmvOptionOtherDepend');
		//console.log("surveyTree removed: " + JSON.stringify(surveyTree));
	});
	
	$('.survey-sections').on("createQuestionJson", "input.survey-question-title", function(e, qType, pageId, text, qid, qIndex){
		
		$(surveyTree).each(function(index, pageElem){
		    if(pageElem.pageId == pageId)
		    {
				var questionJson = {"questionId":parseInt(qid),"index":parseInt(qIndex),"type":qType,"title":text,"optionsGroup":[]};
		    	pageElem.questions.push(questionJson);
				//pageElem.questions.splice(qIndex - 1, 0, questionJson);
		    }		    
		});
		
		var question = $(this).closest("li.panel-question");
		var page = $(this).closest("li.page");
		var pageIndex = parseInt(page.attr("index"));
		var prevPage = $("li.page[index=" + (pageIndex - 1) + "]");
		var simpleQuestions = getSimpleQuestions(prevPage.attr("index"));
		prevPage.find("li.panel-question").each(function(index, qElement){
			var qElementId = parseInt($(qElement).attr("qid")); 
			if($.inArray(qElementId, simpleQuestions) != -1)
			{
				$(qElement).find("div.rules-frame").removeClass("hidden");
				$(qElement).find("button.btn-logic").removeClass("hidden");
			}
		});
					
		//console.log("surveyTree created: " + JSON.stringify(surveyTree));
	});

	$('.survey-sections').on("insertQuestionJson", "li.panel-question", function(e, questionJson){
		var pageId = $(this).closest("li.page").attr("pid");
		
		$(surveyTree).each(function(index, pageElem){
		    if(pageElem.pageId == pageId)
		    {
				//var questionJson = {"questionId":parseInt(qid),"index":parseInt(qIndex),"type":qType,"title":text,"optionsGroup":[]};
				pageElem.questions.splice(questionJson.index, 0, questionJson);
				$(pageElem.questions).each(function(index, questionElem){
		    		questionElem.index = index + 1;
		    	});
		    }		    
		});
		
		//console.log("surveyTree inserted: " + JSON.stringify(surveyTree));
	});

	$('.survey-sections').on("setQuestionJson", "input#survey-question-title", function(e){
		var pageId = $(this).closest("li.page").attr("pid");
		var questionId = $(this).closest('li.panel-question').attr('qid');
		var text = $(this).val();
				
		$(surveyTree).each(function(index, pageElem){
		    if(pageElem.pageId == pageId)
		    {
		    	$(pageElem.questions).each(function(index, questionElem){
		    		if(questionElem.questionId == questionId)
		    		{
		    			questionElem.title = text;
		    		}
		    	});
		    }		    
		});
		
		$(this).trigger("setQuestionGoto");
		$(this).trigger("setQuestionDepend");
		//console.log("surveyTree question changed: " + JSON.stringify(surveyTree));
	});
	
	$('.survey-sections').on("rmvQuestionJson", "li.panel-question", function(e){
		var pageId = $(this).closest('li.page').attr('pid');
		var questionId = $(this).attr('qid');
		
		//console.log("question remove - pageId: " + pageId + " - questionId: " + questionId);
		
		$(surveyTree).each(function(index, pageElem){
		    if(pageElem.pageId == pageId)
		    {
		    	pageElem.questions = $.grep(pageElem.questions, function(question) {
    				return question.questionId != questionId;  
    			});
		    	
		    	$(pageElem.questions).each(function(index, questionElem){
		    		questionElem.index = index + 1;
		    	});
		    }		    
		});

		$(this).trigger("rmvQuestionGoto");
		$(this).trigger("rmvQuestionDepend");
		

		var question = $(this).closest("li.panel-question");
		var numQuestions = question.closest("ul.page-items").find("li.panel-question").length;
		console.log("Num questions: " + numQuestions);
		if(numQuestions < 2)
		{
			var page = $(this).closest("li.page");
			var pageIndex = parseInt(page.attr("index"));
			var prevPage = $("li.page[index=" + (pageIndex - 1) + "]");
			var simpleQuestions = getSimpleQuestions(prevPage.attr("index"));
			prevPage.find("li.panel-question").each(function(index, qElement){
				var qElementId = parseInt($(qElement).attr("qid")); 
				if($.inArray(qElementId, simpleQuestions) != -1)
				{
					$(qElement).find("button.btn-logic").addClass("hidden");
				}
			});
		}
		//console.log("surveyTree question removed: " + JSON.stringify(surveyTree));
	});

	$('.survey-sections').on("rmvQuestionJsonNoDepLog", "li.panel-question", function(e){
		var pageId = $(this).closest('li.page').attr('pid');
		var questionId = $(this).attr('qid');
		
		//console.log("question remove - pageId: " + pageId + " - questionId: " + questionId);
		
		$(surveyTree).each(function(index, pageElem){
		    if(pageElem.pageId == pageId)
		    {
		    	pageElem.questions = $.grep(pageElem.questions, function(question) {
    				return question.questionId != questionId;  
    			});
		    	
		    	$(pageElem.questions).each(function(index, questionElem){
		    		questionElem.index = index + 1;
		    	});
		    }		    
		});
	});
	
	$('.survey-sections').on("setQuestionGoto", "input#survey-question-title", function(e){
		var root = $(this).closest('li.panel-question');
		var questionId = root.attr('qid');
		var text = root.find("span.num-question").html() + " " + $(this).val();
		
		$('.logic-option-goto').each(function(index, selectGoto){
			//console.log("Select index: " + index + " - val: " + $(selectGoto).val());
			if($(selectGoto).val() == questionId)
			{				
				$(selectGoto).find('#option-goto-' + questionId).html(text);
			}
		});
	});

	$('.survey-sections').on("rmvQuestionGoto", "li.panel-question", function(e){
		var questionId = $(this).attr('qid');
		
		$('.logic-option-goto').each(function(index, selectGoto){
			//console.log("Select index: " + index + " - val: " + $(selectGoto).val());
			if($(selectGoto).val() == questionId)
			{
				$(selectGoto).val('none');
				$(selectGoto).trigger("change");
			}
		});
	});

	$('.survey-sections').on("setQuestionDepend", "input#survey-question-title", function(e){
		var pageId = $(this).closest('li.page').attr('pid');
		var root = $(this).closest('li.panel-question');
		var questionId = root.attr('qid');
		var text = root.find("span.num-question").html() + " " + $(this).val();

		var valor = pageId + "-" + questionId;
				
		$('.dependence-question').each(function(index, selectDepend){
			//console.log("Select index: " + index + " - val: " + $(selectDepend).val());
			//console.log("Select index: " + index + " - valor: " + valor);
			if($(selectDepend).val() == valor)
			{	
				console.log("setQuestionDepend entra if");
				$(selectDepend).find('#question-dependence-' + valor).html(text);
			}
		});
	});

	$('.survey-sections').on("rmvQuestionDepend", "li.panel-question", function(e){
		var pageId = $(this).closest('li.page').attr('pid');
		var questionId = $(this).attr('qid');
		
		var valor = pageId + "-" + questionId;
		
		console.log("rmvQuestionDepend value: " + valor);
		
		$('.dependence-question').each(function(index, selectDepend){
			//console.log("Select index: " + index + " - val: " + $(selectDepend).val());
			if($(selectDepend).val() == valor)
			{
				console.log("entra");
				var trashButton = $(selectDepend).closest("fieldset").find("button#remove-dependence");
				trashButton.trigger("rmvNoConfirm");
			}
		});
	});
	
	$('.survey-sections').on("setOptionDepend", "#option-list #option-item input", function(e){
		var optionId = $(this).attr('oid');
		var optionGroupId = $(this).closest("ul.option-list").attr("ogid");
		var text = $(this).val();
		if(text === "") 
		{
			text = $(this).closest('li.option-item').find('li.multimedia-item a').text().split(' - ')[0];
		}
		
		var valor = optionGroupId + "-" + optionId;
		
		console.log("rmvOptionDepend value: " + valor);
		
		$('.dependence-option').each(function(index, selectDepend){
			//console.log("Select index: " + index + " - val: " + $(selectDepend).val());
			if($(selectDepend).val() == valor)
			{					
				$(selectDepend).find('#option-dependence-' + valor).html(text);
			}
		});
	});

	$('.survey-sections').on("setOptionOtherDepend", "input.otherOptionTitle", function(e){
		var optionId = $(this).closest("li.option-item").attr('oid');
		var optionGroupId = $(this).closest("ul.option-list").attr("ogid");
		var text = $(this).val();		
		
		var valor = optionGroupId + "-" + optionId;
		
		console.log("rmvOptionDepend value: " + valor);
		
		$('.dependence-option').each(function(index, selectDepend){
			//console.log("Select index: " + index + " - val: " + $(selectDepend).val());
			if($(selectDepend).val() == valor)
			{					
				$(selectDepend).find('#option-dependence-' + valor).html(text);
			}
		});
	});

	$('.survey-sections').on("rmvOptionDepend", "#option-list #option-item input", function(e){
		var optionId = $(this).attr('oid');
		var optionGroupId = $(this).closest("ul.option-list").attr("ogid");
		
		var valor = optionGroupId + "-" + optionId;
		
		console.log("rmvOptionDepend value: " + valor);
		
		$('.dependence-option').each(function(index, selectDepend){
			//console.log("Select index: " + index + " - val: " + $(selectDepend).val());
			if($(selectDepend).val() == valor)
			{					
				var trashButton = $(selectDepend).closest("fieldset").find("button.trash");
				trashButton.trigger("rmvNoConfirm");
			}
		});
	});

	$('.survey-sections').on("rmvOptionOtherDepend", "input.otherOptionTitle", function(e){
		var optionId = $(this).closest("li.option-item").attr('oid');
		var optionGroupId = $(this).closest("ul.option-list").attr("ogid");
		
		var valor = optionGroupId + "-" + optionId;
		
		//console.log("rmvOptionDepend value: " + valor);
		
		$('.dependence-option').each(function(index, selectDepend){
			//console.log("Select index: " + index + " - val: " + $(selectDepend).val());
			if($(selectDepend).val() == valor)
			{					
				var trashButton = $(selectDepend).closest("fieldset").find("button.removeDependence");
				trashButton.trigger("rmvNoConfirm");
			}
		});
	});
	
	$('.survey-sections').on("displayLogic", "div.rules-frame", function(e){
		var pageIndex = $(this).closest('li.page').attr('index');
		var logicButton = $(this).find("button.btn-logic");
		if(pageIndex == surveyTree.length)
		{
			if($(this).find("fieldset.logic-frame").length)
			{
				console.log("Pregunta simple o con lógica");
				if(!$(this).hasClass("hidden"))
				{
					$(this).find("select.logic-option-goto").each(function(index, element){
						if($(element).val() != "none")
						{
							$(element).val("none");
							$(element).trigger("change");
						}
					});
					$(this).addClass("hidden");
				}
				
				if(!logicButton.hasClass("hidden"))
				{
					logicButton.addClass("hidden");
					logicButton.removeClass("active");
				}
			}
			
			var numQuestions = $(this).closest("ul.page-items").find("li.panel-question").length;
			console.log("numero de preguntas: " + numQuestions);
			if(numQuestions < 2)
			{
				var prevPage = $("li.page[index=" + (pageIndex - 1) + "]");
				var simpleQuestions = getSimpleQuestions(prevPage.attr("index"));
				prevPage.find("li.panel-question").each(function(index, qElement){
					var qElementId = parseInt($(qElement).attr("qid")); 
					if($.inArray(qElementId, simpleQuestions) != -1)
					{
						$(qElement).find("div.rules-frame").removeClass("hidden");
						$(qElement).find("button.btn-logic").removeClass("hidden");
					}
				});
			}
		}
		else
		{
			var nextPageIndex = parseInt(pageIndex) + 1;
			var nextPage = $("li.page[index=" + nextPageIndex + "]");
			var nextPageId = parseInt(nextPage.attr("pid"));
			var lastPageId = surveyTree[surveyTree.length - 1].pageId;
			var numQuestions = nextPage.find("li.panel-question").length;
			console.log("PageIndex: " + nextPageIndex + " - nextId: " + nextPageId + " - lastId: " + lastPageId + " - numQuestions: " + numQuestions);
			if(nextPageId != lastPageId || numQuestions > 0)
			{
				if($(this).find("fieldset.logic-frame").length) logicButton.removeClass("hidden");
			}
			else
			{
				var simpleQuestions = getSimpleQuestions(pageIndex);
				$(this).closest('li.page').find("li.panel-question").each(function(index, qElement){
					var qElementId = parseInt($(qElement).attr("qid")); 
					if($.inArray(qElementId, simpleQuestions) != -1)
					{
						$(qElement).find("button.btn-logic").addClass("hidden");
					}
				});
				logicButton.addClass("hidden");
			}
		}
	});

	$('.survey-sections').on("setLogicMoved", "fieldset.logic-frame", function(e){	
		var pageIndex = $(this).closest('li.page').attr('index');
	
		$(this).find("select.logic-option-goto").each(function(index, element){
			var val = $(element).val();
			var exist = false;
			if(val != "none")
			{
				var i  = pageIndex;
				while(i < surveyTree.length && !exist)
				{
					var page = surveyTree[i];
					for(var j = 0; j < page.questions.length; j++)
					{
						if(page.questions[j].questionId == parseInt(val)) exist = true;
					}
					i++;
				}
				if(!exist)
				{
					$(element).val("none");
					$(element).trigger("change");
				}
			}
		});
	});

	$('.survey-sections').on("displayDependences", "fieldset.dependences-frame", function(e){
		var pageIndex = $(this).closest('li.page').attr('index');
		var hasSimpleQuestions = false;
		var depButton = $(this).closest("div.rules-frame").find("button.btn-dependences");
		
		for(var i = 0; i < pageIndex - 1; i++)
		{
			var page = surveyTree[i];
			for(var j = 0; j < page.questions.length; j++)
			{
				if(page.questions[j].type === "simple") hasSimpleQuestions = true;
			}
		}
		
		if(!hasSimpleQuestions)
		{
			if(!$(this).hasClass("hidden"))
			{
				/*$(this).find("button#remove-dependence").each(function(index, element){
					$(element).trigger("click");
				});*/
				$(this).addClass("hidden");
				depButton.removeClass("hidden");
			}
			
			if(!depButton.hasClass("hidden"))
			{
				depButton.addClass("hidden");
				depButton.removeClass("active");
			}
			
			if(!$(this).closest("div.question-frame").find("div.logic-frame").length || $(this).closest("div.question-frame").find("div.logic-frame").hasClass("hidden"))
			{
				$(this).closest("div.question-frame").addClass("hidden");
			}
		}
		else
		{
			if($(this).hasClass("hidden"))
			{
				//$(this).removeClass("hidden");
				$(this).closest("div.question-frame").removeClass("hidden");
				depButton.removeClass("hidden");
			}
		}
	});
	
});

function setJson(optionId, pageId, questionId, optionGroupId, index, text)
{
	console.log("Option setJson pageId: " + pageId + " - oid: " + optionId + " - valor: " + text);
	var existOg = false;
	var existOpt = false;
	console.log("index: " + index);
	$(surveyTree).each(function(ind, pageElem){
	    if(pageElem.pageId == pageId)
	    {
	    	$(pageElem.questions).each(function(ind2, questionElem){
	    		if(questionElem.questionId == questionId)
	    		{
	    			$(questionElem.optionsGroup).each(function(ind3, ogElem){
			    		if(ogElem.optionsGroupId == optionGroupId)
			    		{
			    			existOg = true;
			    			$(ogElem.options).each(function(ind4, optionElem){
					    		if(optionElem.optionId == optionId)
					    		{
					    			existOpt = true;
					    			optionElem.title = text;
					    		}
					    	});
			    			
			    			if(!existOpt) ogElem.options.push({"optionId":parseInt(optionId),"index":parseInt(index),"title": text});
			    		}
			    	});
	    			
	    			if(!existOg) questionElem.optionsGroup.push({"optionsGroupId":parseInt(optionGroupId),"options":[{"optionId":parseInt(optionId),"index":parseInt(index),"title": text}]});
	    		}
	    	});
	    }		    
	});
	
}

function getSimpleQuestions(numPage)
{
	var questions = [];
	var position = numPage - 1;
	for(var j = 0; j < surveyTree[position].questions.length; j++)
	{
		if(surveyTree[position].questions[j].type === "simple") questions.push(surveyTree[position].questions[j].questionId);
	}
	return questions;
}

function getAllQuestions(numPage)
{
	var questions = [];
	var position = numPage - 1;
	for(var j = 0; j < surveyTree[position].questions.length; j++)
	{
		questions.push(surveyTree[position].questions[j].questionId);
	}
	return questions;
}

function setDepLabelCounter(rules)
{
	var labelCounter = rules.find("#depCounter");
	var counter = -1;
	rules.find("ul.dependences-list").find("select.dependence-option").each(function(index, element){
		counter++;
	});	
	console.log("Dependence counter: " + counter);
	if(counter > 0)
	{
		labelCounter.html(counter);
		labelCounter.removeClass("hidden");
	}
	else
	{
		labelCounter.html(counter);
		labelCounter.addClass("hidden");
	}	
}

function insertPageJson(numPage, pageId)
{
	var newPage = {};
	newPage.numPage = parseInt(numPage);
	newPage.pageId = parseInt(pageId);
	newPage.questions = [];
	var position = newPage.numPage - 1;
	surveyTree.splice(position, 0, newPage);
	updateNumberPage();
	console.log("SurveyTree inserPage: " + JSON.stringify(surveyTree));
}

function removePageJson(numPage, pageId)
{
	var position = numPage - 1;
	var positionPrev = numPage - 2;
	surveyTree[positionPrev].questions = surveyTree[positionPrev].questions.concat(surveyTree[position].questions);
	surveyTree = jQuery.grep(surveyTree, function(page) {
		return page.pageId != pageId;
	});	
	updateNumberPage();
	console.log("SurveyTree removePage: " + JSON.stringify(surveyTree));
}

function removePageCompleteJson(section)
{
	section.find("li.page").each(function(indexPage, page){
		var pageId = parseInt($(page).attr("pid"));
		surveyTree = jQuery.grep(surveyTree, function(page) {
			return page.pageId != pageId;
		});	
	});
	updateNumberPage();
	console.log("SurveyTree removePageComplete: " + JSON.stringify(surveyTree));
}

function removeLastPage(page, pageId)
{
	//var lastPageId = parseInt(page.closest("ul.section-pages").find("li.page").last().attr("pid"));
	var lastPageId = surveyTree[surveyTree.length - 1].pageId;
	console.log("CurrentPageId: " + pageId + " - lasPageId: " + lastPageId);
	var prevPage = page.prev();
	if(pageId === lastPageId)
	{
		var simpleQuestions = getSimpleQuestions(prevPage.attr("index"));
		prevPage.find("li.panel-question").each(function(index, qElement){
			var qElementId = parseInt($(qElement).attr("qid")); 
			if($.inArray(qElementId, simpleQuestions) != -1)
			{
				$(qElement).find("button.btn-logic").addClass("hidden");
			}
		});		
	}

	var prevPageId = parseInt(prevPage.attr("pid"));
	var firstPageId = surveyTree[0].pageId;
	if(prevPageId === firstPageId)
	{
		page.find("li.panel-question").each(function(index, qElement){
			$(qElement).find("button.btn-dependences").addClass("hidden");
		});
	}
}

function updateNumberPage()
{
	for(var i = 0; i < surveyTree.length; i++)
	{
		surveyTree[i].numPage = i + 1;
	}
}

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
	
	//if(root.attr("class")==="fieldset-second-dependence")
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

function removeLogicElement(elem)
{
	var root = elem.closest("ul");
	
	var numItems = root.find("li").size();
	if(numItems == 1)
	{
		root.addClass('hidden');
		root.closest(".logic-settings").find("p").removeClass("hidden");
	}
	else
	{
		elem.closest("li").remove();
	}
}
