var app = angular.module('survey', ['surveyService', 'youtube-embed', 'ngSanitize', 'monospaced.mousewheel']);

var scrollIndex = 0;
var scrolling = false;

app.controller('surveyController', ['$scope', '$location', '$http', '$window', '$filter', '$anchorScroll', '$timeout', 'survey', function($scope, $location, $http, $window, $filter, $anchorScroll, $timeout, survey) {
	
	/** Initial load **/
	$scope.currentSurvey = survey;
	$scope.currentSurvey.questionNumber = 0;
	$scope.warning = false;
	$scope.mandatoryError = false;
	$scope.error = true;
	$scope.surveyError = false;
	$scope.text = "hello world";
	$scope.decimalRegex = '^[0-9]+(.([0-9]{1,2}))?$';
	$scope.questionIndex = 1;
	$scope.otherOptionValue = -1;
	var mandatoryErrorQuestions = [];
	var errorSurvey = false;

	/*if(!$scope.user.name)
	{		
		$scope.user.initialLoad();
	}*/	
	//console.log("Json survey info: " + JSON.stringify($scope.currentSurvey));
	$scope.currentSurvey.initialLoad();
	//console.log("Json survey info: " + JSON.stringify($scope.currentSurvey));
	
	/*$('#question-list').on('click', 'div.form-question', function(){
		$scope.setIndexQuestion(parseInt($(this).closest('li.question').attr('index')));
		console.log("Click: " + $scope.questionIndex);		
	});
	
	$('#question-list').on('focus', ':focusable', function(){		
		$scope.questionIndex = $(this).closest('li.question').attr('index');
		$scope.setIndexQuestion(console.log("Focus: " + $scope.questionIndex));			
	});*/

	/** Initial load **/
	

	$scope.getProgressPercent = function() {
		//console.log("Progress: " + ($scope.currentSurvey.info.numPages) + " / " + ($scope.currentSurvey.info.section.page.numPage));
		//console.log("Survey Json: " + JSON.stringify($scope.currentSurvey.info));
		if($scope.currentSurvey.info.section.page && $scope.currentSurvey.info.section.page.numPage == 1)
		{
			return 0;
		}
		else if($scope.currentSurvey.info.section.page && $scope.currentSurvey.info.section.page.numPage && $scope.currentSurvey.info.hasFinishPage)
		{
			return  ((($scope.currentSurvey.info.section.page.numPage - 1) / ($scope.currentSurvey.info.numPages - 1)) * 100).toFixed(2);
		}
		else  if($scope.currentSurvey.info.section.page && $scope.currentSurvey.info.section.page.numPage)
		{
			return  ((($scope.currentSurvey.info.section.page.numPage - 1) / ($scope.currentSurvey.info.numPages)) * 100).toFixed(2);
		}
		else
		{
			return 100;
		}
		
	};
	
	$scope.getJsonArrayElement = function(array, attribute, value) {
		if ("undefined" != typeof array) {
			for(var i = 0; i < array.length; i++)
			{
				if( array[i][attribute] == value) return array[i];
			}
		}
		return {};
	};

	$scope.getContentElementByAttrAndIndex = function(array, attribute, value, index) {
		for(var i = 0; i < array.length; i++)
		{
			if( array[i][attribute] == value && array[i]['index'] == index) return array[i];
		}
		return {};
	};

	$scope.getLabelPointDisp = function(numPoints) {
		var disp = 0;
		if(numPoints == 7) disp = 1;
		if(numPoints == 5) disp = 2;
		return disp;
	};

	$scope.getLines = function(numLines, maxLength) {
		if(numLines == null || numLines.value == null || numLines.value == "")
		{
			var div = parseFloat($scope.getMaxLength(maxLength))/40.0;
			if(div>10.0)
				return "10";
			else
				return ""+Math.ceil(div);
		}
		else
		{
			return numLines.value;
		}
	};
	
	$scope.getMaxLength = function(maxLength) {
		if(maxLength == null || maxLength.value == null || maxLength.value == "")
		{
			return "9999";
		}
		else
		{
			return maxLength.value;
		}
	};

	$scope.getMinValue = function(minValue) {
		if(minValue == null || minValue.value == null || minValue.value == "")
		{
			return "0";
		}
		else
		{
			return minValue.value;
		}
	};

	$scope.getMaxValue = function(maxValue) {
		if(maxValue == null || maxValue.value == null || maxValue.value == "")
		{
			return "0";
		}
		else
		{
			return maxValue.value;
		}
	};

	$scope.getDecimals = function(decimals) {
		if(decimals == null || decimals.value == null || decimals.value == "")
		{
			return "0";
		}
		else
		{
			return decimals.value;
		}
	};
	
	$scope.isOutOfRange = function(num, min, max) {
		if(!angular.isUndefined(num) && min != max)
		{
			var numParts = num.toString().split(",");
			var finalNum = num;
			if(numParts.length > 1) finalNum = numParts[0] + "." + numParts[1]; 
			console.log("isNumber: " + finalNum);
			console.log("isNumber min: " + min + " - max: " + max);
			if(!isNaN(parseFloat(finalNum)) && isFinite(finalNum))
			{
				var f = parseFloat(finalNum);
				if(f < min || f > max)
				{
					errorSurvey = true;
					return true;
				}
			}
		}
		errorSurvey = false;
		return false;
	};

	$scope.isNumber = function(num) {
		console.log("isNumber: " + num);
		
		if(isNaN(parseFloat(finalNum)))
		{
			errorSurvey = true;
			return false;
		}
		errorSurvey = false;
		return true;
	};
	
	$scope.getQuestionPath = function(question) {	
		console.log("Question: " + JSON.stringify(question));
		var minMaxCad = "";
		if(question.questionType == "shortText")
		{
			var min = $scope.getJsonArrayElement(question.parameters, "name", "minValue");
			if(!min.value) {
				if(minMaxCad == "") minMaxCad = "?";
				minMaxCad = minMaxCad + "min=0";
			}
			
			var max = $scope.getJsonArrayElement(question.parameters, "name", "maxValue");
			if(!max.value) {
				if(minMaxCad == "") { minMaxCad = "?" } else { minMaxCad = minMaxCad + "&" };
				minMaxCad = minMaxCad + "max=0";
			}
		}
		console.log("QuestionPath: " + question.questionJspPath + minMaxCad);
		return question.questionJspPath + minMaxCad;
	}
	
	$scope.decimalRegex = function(decimals) {
		//'^[0-9]+(.([0-9]{1,2}))?$'
		//console.log("decimals: " + JSON.stringify(decimals));
		var regex = '^[0-9]+';
		if(decimals.name && decimals.value)
		{
			regex = regex + '(.([0-9]{1,' + decimals.value + '}))?';
		}
		/*else if(decimals.name && !decimals.value)
		{
			regex = regex + '(.([0-9]+))?';
		}*/		
		regex = regex + '$';
		
		return regex;
	};

	$scope.getMatrixOptionType = function(matrixType) {
		if(matrixType != null && matrixType.value != null && matrixType.value == "Multiple")
		{
			return "checkbox";
		}
		else
		{
			return "radio";
		}
	};
		
	$scope.nextPage = function(action) {
		//console.log("Next page error: " + $scope.survey.$valid);
		mandatoryErrorQuestions = [];
		if(action === 'next')
		{
			$scope.currentSurvey.info.section.page.questions.forEach(function(q){
				if(q.mandatory && q.questionType != 'bcontent')
				{
					if(!q.response)
					{
						if(q.optionsGroups && q.optionsGroups.length > 0)
						{
							q.optionsGroups.forEach(function(og){
								console.log("response option: " + og.response + "- type: " + q.questionType);
								if(!og.response)
								{
									console.log("response option 2: " + og.response + " - other: " + og.responseOther);
									if(og.options && og.options.length > 0 && q.questionType == 'multiple')
									{
										console.log("response option 3: " + og.response);
										var hasResponse = false;
										og.options.forEach(function(o){
											if(o.response)
											{
												hasResponse = true;
											}
										});
										
										if(og.responseOther) hasResponse = true;
										
										if(!hasResponse) mandatoryErrorQuestions.push(q.questionId);
									}
									else
									{
										mandatoryErrorQuestions.push(q.questionId);
									}
								}
									
							});
						}
						else
						{
							mandatoryErrorQuestions.push(q.questionId);
						}
					}
				}
			});
			

			errorSurvey = errorSurvey || !$scope.survey.$valid;
		}
		
		if(action != 'next' || (!errorSurvey && mandatoryErrorQuestions.length == 0))
		{
			$scope.surveyError = false;
			$scope.mandatoryError = false;
			$scope.currentSurvey.saveResponseAndGetNextPage(action, function(err, res){
				if(res != false)
				{
					var element = $window.document.getElementById('main-title');
			        if(element) $(element).focus();	        

			        $scope.questionIndex = 1;
				}
				else
				{
					console.log("Entra en no is user");
					//$location.path("/SurveyTool");
					//$window.location.href = "/SurveyTool";
					$window.location.href = "/";
				}
			});	
			
		}
		
		if(mandatoryErrorQuestions.length != 0)
		{
			$scope.mandatoryError = true;
			$(".error-summary").focus();
		}
		else $scope.mandatoryError = false; 
			
		if(errorSurvey)
		{
			$scope.surveyError = true;
			$(".error-summary").focus();
		}
		else $scope.surveyError = false;
	};
	
	/*$scope.getNextQuestionNumber = function(question) {
		$scope.currentSurvey.increaseNumQuestion(question, function(err, res){
			return res;
		});
	};*/

	$scope.showMandatoryErrorMsg = function(question) {
		var show = false;
		if(mandatoryErrorQuestions.length > 0)
		{
			mandatoryErrorQuestions.forEach(function(id){
				if(id == question.questionId) show = true;
			});
		}
		return show;
	};
	
	$scope.showStartButton = function() {
		if($scope.currentSurvey.info.section.page && $scope.currentSurvey.info.section.page.numPage && $scope.currentSurvey.info.section.page.numPage == 1)
		{
			var show = true;
			$scope.currentSurvey.info.section.page.questions.forEach(function(q){
				show = show && (q.questionType === 'bcontent');
			});
			console.log("ShowStartButton: " + show);
			return show;
		}
		else
		{
			return false;
		}
	};

	$scope.showButtonLastPage = function() {
		if($scope.currentSurvey.info.section && $scope.currentSurvey.info.section.page && $scope.currentSurvey.info.section.page.numPage == $scope.currentSurvey.info.numPages)
		{
			var show = true;
			$scope.currentSurvey.info.section.page.questions.forEach(function(q){
				show = show && (q.questionType === 'bcontent');
			});
			//console.log("ShowEndButton: " + show + " - numPage: " + $scope.currentSurvey.info.section.page.numPage + " - numPages: " + $scope.currentSurvey.info.numPages);
			return !show;
		}
		else
		{
			return true;
		}
	};

	//Navigation focus

	$scope.setIndexQuestion = function(qIndex) {
		$scope.questionIndex = qIndex;
		console.log("index question: " + $scope.questionIndex);
	    //$location.hash('anchor-' + $scope.questionIndex);
	    //$anchorScroll();
	};

	$scope.setOtherSimpleFocus = function(optionGroup, optionId) {
		if(optionGroup.response != optionId) optionGroup.response = optionId;
	};

	$scope.hasOtherOption = function(options) {
		var hasOther = false;
		for(var i = 0; i < options.length; i++){
			if(options[i].otherOption) hasOther = true; 
		}		
		return hasOther;
	};

	$scope.getOtherOptionId = function(options) {
		var otherId = 0;
		for(var i = 0; i < options.length; i++){
			if(options[i].otherOption) otherId = options[i].optionId; 
		}		
		return otherId;
	};

	$scope.setOtherMultipleFocus = function(option) {
		if(option.response == false) option.response = true;
	};
	
	//mousewheel
    $scope.setWheelIndexQuestion =  function(event, delta, deltaX, deltaY){
        //console.log('event: ' + event + ', delta: ' + delta + ', deltaX: ' + deltaX + ', deltaY: ' + deltaY);    	
    	//console.log("---- target 1: " + $(event.target).html());
    	var index = $scope.questionIndex - delta;
    	if(index > $scope.currentSurvey.info.section.page.questions.length) index = $scope.questionIndex;
    	else if(index <= 0) index = 1;
    	var elem = $('li[index=' + index + ']');
    	console.log("Index: " + index);
    	//var elem = $('li[index=' + $scope.questionIndex + ']');
    		
    	scrolling = isScrolledIntoView(elem, delta);
    	
    	console.log("Scrolling: " + scrolling);
    	
    	if(scrolling)
    	{
			if(delta == -1){
	        	if($scope.questionIndex < $scope.currentSurvey.info.section.page.questions.length) {
	        		$scope.questionIndex = $scope.questionIndex + 1;
	        		scrollIndex = $scope.questionIndex;
	        		//$location.hash('anchor-' + $scope.questionIndex);
	        	    //$anchorScroll();
	        	}
	        	else
	        	{
	        		scrollIndex = $scope.currentSurvey.info.section.page.questions.length + 1;
	        	}
	        }
	        else if(delta == 1){
	        	if($scope.questionIndex > 1){
	        		$scope.questionIndex = $scope.questionIndex - 1;
	        		scrollIndex = $scope.questionIndex;
	        		//$location.hash('anchor-' + $scope.questionIndex);
	        	    //$anchorScroll();
	        	}
	        	else
	        	{
	        		scrollIndex = 0;
	        	}
	        }		
    	}  
	 };
	 
	 $scope.hasResource = function(options) {
		var hasResource =  false;
		var i = 0;
		while(!hasResource && i < options.length){
			if(options[i].resource) hasResource = true;
			//console.log("hasResource " + i + ": " + hasResource);
			i++;
		}
		return hasResource;
	 };
	 
	 $scope.getNumber = function(textNum) {
		 return parseInt(textNum);
	 }
	 
	 /*$scope.focusTextArea = function(optionGroup, textAreaId) {
		 console.log("OptionGroup: " + JSON.stringify(optionGroup) + " - textAreaId: " + textAreaId);
		 if(optionGroup.response == $scope.getOtherOptionId(optionGroup.options)) $('#' + textAreaId).focus();
	 }*/
}]);

app.directive('focusOn',function($timeout) {
    return {
        restrict : 'A',
        link : function($scope,$element,$attr) {
            $scope.$watch($attr.focusOn,function(_focusVal) {
                $timeout(function() {
                	if(_focusVal) $(".error-summary").focus();
                });
            });
        }
    }
});

app.directive('preventScrollBody', function() {
    return{
        link: link,
        restrict: 'A'
    };

    function link(scope, element, attrs) {
        element.bind( 'mousewheel DOMMouseScroll', function ( e ) {   
        	//var elem = $('li[index=' + scope.questionIndex + ']');    		
        	
            if(scrolling && scrollIndex > 0 && scrollIndex < scope.currentSurvey.info.section.page.questions.length + 1)
            {
            	var e0 = e.originalEvent;
                	delta = e0.wheelDelta || -e0.detail;
            	var deltaScroll = (delta < 0 ? 1 : -1);
            	
		        this.scrollTop += deltaScroll * 30;
	            e.preventDefault();
            }
            
        });
    }
});

function isScrolledIntoView(elem, delta)
{
    var docViewTop = $(window).scrollTop();
    var elemTop = $(elem).offset().top;
    var chagePoint = $(window).height() / 2;
    
       
    if(delta == 1)
    {
    	console.log("Windows top: " + docViewTop + " + Change Point: " + chagePoint + " <= elem top: " + elemTop);
    	if(docViewTop == 0) return true;
    	else return elemTop >= docViewTop + chagePoint - ((2 * chagePoint) / 2);
    }
    else
    {
    	console.log("Windows top: " + docViewTop + " + Change Point: " + chagePoint + " = total " + (docViewTop + chagePoint - (chagePoint / 5)) + " >= elem top: " + elemTop);
    	var docViewBottom = docViewTop + $(window).height();   
    	console.log("Windows botton: " + docViewBottom + " < body height: " + $('body').height());
        /*var elemBottom = elemTop + $(elem).height();
        console.log("Windows bottom: " + docViewBottom + " >= elem bottom: " + elemBottom + " - body heigth: " + $('body').height());*/
        if(docViewBottom < $('body').height())
        {
        	//return elemBottom <= docViewBottom - 550;
        	return elemTop <= docViewTop + chagePoint - (chagePoint / 5);
        }
        else
        {
        	return true;
        }
    }
    
    //return ((elemBottom <= docViewBottom) && (elemTop >= docViewTop));
}

app.filter('range', function() {
	return function(input, total) {
		total = parseInt(total);
		var init = 1;
		if(total == 11)
		{
			init = 0;
			total = 10;
		}

		for (var i=init; i<=total; i++) {
			input.push(i);
		}

		return input;
	};
});

/*app.directive('decimalLimit',function(){
    return {
        link:function(scope,ele,attrs){
            ele.bind('keypress',function(e){
            	console.log('Entra en keypress val: ' + $(this).val())
                var newChar = (e.charCode!==0?String.fromCharCode(e.charCode):'');
            	var currentVal = $(this).attr('currentVal');
                var newVal=$(this).val();
                
                if(currentVal[currentVal.length-1] === '.') newVal = newVal + '.' +newChar;
                else newVal = newVal+newChar;
                console.log('Entra en keypress newVal: ' + newVal);
                console.log('Entra en keypress newVal.substring: ' + newVal.substring(0, newVal.length-1));
                if(currentVal >= newVal) currentVal = newVal.substring(0, newVal.length-1);
                
                
            	console.log('Entra en keypress newChar: ' + newChar);
            	console.log('Entra en keypress currentVal before: ' + currentVal + " - " + currentVal.includes("."));
                
                if(($(this).val().search(/(.*)\.[0-9][0-9]/)===0)
                		|| (currentVal.includes(".") && newChar === '.')
                		|| newChar === 'e'){
                	
                    e.preventDefault();
                }
                else
                {
                	$(this).attr('currentVal', newVal);
                }
                console.log('Entra en keypress currentVal after: ' + $(this).attr('currentVal'));
            });
        }
    };
});*/

/*
var compareTo = function() {
    return {
      require: 'ngModel',
      link: function (scope, elem, attrs, ctrl) {
        var firstPassword = '#' + attrs.compareTo;
        elem.add(firstPassword).on('keyup', function () {
          scope.$apply(function () {
          	//console.log('ERROR Values: ' + elem.val() + " - " + $(firstPassword).val());
            var v = elem.val()===$(firstPassword).val();
            ctrl.$setValidity('compareTo', v);
          });
        });
      }
    }
};

app.directive("compareTo", compareTo);
*/

