var app = angular.module('survey', ['surveyService', 'youtube-embed', 'ngSanitize', 'monospaced.mousewheel']);

app.controller('surveyController', ['$scope', '$http', '$window', '$filter', 'survey', function($scope, $http, $window, $filter, survey) {
	
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
		if($scope.currentSurvey.info.section.page.numPage == 1)
		{
			return 0;
		}
		else if($scope.currentSurvey.info.section.page.numPage && $scope.currentSurvey.info.hasFinishPage)
		{
			return  (($scope.currentSurvey.info.section.page.numPage - 1) / ($scope.currentSurvey.info.numPages - 1)) * 100;
		}
		else  if($scope.currentSurvey.info.section.page.numPage)
		{
			return  (($scope.currentSurvey.info.section.page.numPage - 1) / ($scope.currentSurvey.info.numPages)) * 100;
		}
		else
		{
			return 100;
		}
		
	};
	
	$scope.getJsonArrayElement = function(array, attribute, value) {
		for(var i = 0; i < array.length; i++)
		{
			if( array[i][attribute] == value) return array[i];
		}
		return {};
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
			return "9999";
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
		if(!isNaN(parseFloat(num)) && isFinite(num))
		{
			var f = parseFloat(num);
			if(f < min || f > max)
			{
				errorSurvey = true;
				return true;
			}
		}
		errorSurvey = false;
		return false;
	};
	
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
								if(!og.response)
								{
									if(og.options && og.options.length > 0)
									{
										og.options.forEach(function(o){
											if(!o.response)
											{
												mandatoryErrorQuestions.push(q.questionId);
											}
										});
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
				if(res)
				{
					var element = $window.document.getElementById('main-title');
			        if(element) $(element).focus();	        

			        $scope.questionIndex = 1;
				}				
			});	
			
		}
		
		if(mandatoryErrorQuestions.length != 0) $scope.mandatoryError = true;
		else $scope.mandatoryError = false; 
			
		if(errorSurvey) $scope.surveyError = true;
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
		if($scope.currentSurvey.info.section.page.numPage == 1)
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
		if($scope.currentSurvey.info.section.page.numPage == $scope.currentSurvey.info.numPages)
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
	};
	
	//mousewheel
    $scope.setWheelIndexQuestion =  function(event, delta, deltaX, deltaY){
        //console.log('event: ' + event + ', delta: ' + delta + ', deltaX: ' + deltaX + ', deltaY: ' + deltaY);
        if(delta == -1){
        	if($scope.questionIndex < $scope.currentSurvey.info.section.page.questions.length)
        	$scope.questionIndex = $scope.questionIndex + 1;
        }
        if(delta == 1){
        	if($scope.questionIndex > 1){
        		$scope.questionIndex = $scope.questionIndex - 1;
        	}
        }
      };
}]);

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