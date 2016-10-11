var app = angular.module('survey', ['surveyService', 'youtube-embed']);

app.controller('surveyController', ['$scope', '$http', '$window', '$filter', 'survey', function($scope, $http, $window, $filter, survey) {
	
	/** Initial load **/
	$scope.currentSurvey = survey;
	$scope.warning = false;
	$scope.error = true;	
	$scope.text = "hello world";

	/*if(!$scope.user.name)
	{		
		$scope.user.initialLoad();
	}*/	
	console.log("Json survey info: " + JSON.stringify($scope.currentSurvey));
	$scope.currentSurvey.initialLoad();
	console.log("Json survey info: " + JSON.stringify($scope.currentSurvey.info.contents));

	/** Initial load **/

	$scope.getJsonArrayElement = function(array, attribute, value) {
		for(var i = 0; i < array.length; i++)
		{
			if( array[i][attribute] == value) return array[i];
		}
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

	/*$scope.getDecimals = function(decimals) {
		if(decimals == null || decimals.value == null || decimals.value == "")
		{
			return "1";
		}
		else
		{
			var afterComma = decimals.value;
			var result="0.";
			for(var i=1;i<afterComma;i++)
				result=result+"0";
			result=result+"1";
			
			return result;
		}
	};*/
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
	
	$scope.getPattern = function(decimals) {
		if(decimals == null || decimals.value == null || decimals.value == "")
		{
			return "/(.*)\.[0-9][0-9]/";//[0-9]+([\,|\.][0-9][0-9])";
		}
		else
		{
			var afterComma = decimals.value;
			var result="/(.*)\.";
			for(var i=1;i<afterComma;i++)
				result=result+"[0-9]";
			result=result+"[0-9]/";
			
			return result;
		}
	};
	
	
	
	$scope.nextPage = function(action) {
		$scope.currentSurvey.saveResponseAndGetNextPage(action, function(err, res){});
	};
}]);

var limitDecimal = function(){
    return {
        link:function(scope,ele,attrs){
        	console.log("Entra directiva");
            ele.bind('keypress',function(e){
            	var newVal=$(this).val()+(e.charCode!==0?String.fromCharCode(e.charCode):'');
            	var newChar = (e.charCode!==0?String.fromCharCode(e.charCode):'');
            	console.log("old: "+$(this).val());
            	console.log("new: "+newChar);
            	if((newChar==='') || (newChar==='+') || (newChar==='*') || (newChar==='/')  || ((newChar==='-') && ($(this).val().length>0)) || ((newChar==='.') && ($(this).val().includes('.'))) || ((newChar===',') && ($(this).val().includes(',')))){
            		console.log("Invalid character");
            		e.preventDefault();
            	}else if(newVal.length>$(this).val().length){
            		if(attrs.decimals>0){     
            			console.log("Has decimals");
                    	
    	                var result='(.*)\.';
    	    			for(var i=1;i<decimals;i++)
    	    				result=result+'[0-9]';
    	    			result=result+'[0-9]';
    	    			
    	    			var regex = new RegExp(result);
            			console.log("Result of search ("+result+"): "+($(this).val().search(regex)===0));
                        if($(this).val().search(regex)===0){
                            e.preventDefault();
                        }
                    }
                    else{
                    	console.log("Without decimals");
                    	if((newChar==='.') || (newChar===',')){
                    		console.log("New char can not be . or ,");
                    		e.preventDefault();
                    	}
                    		
                    }            		
                    
            	}
            	
            	
            	console.log("val before keypress: " + $(this).val()); 
                
            	if(attrs.step.includes(".")){                	
                	maxNumbersAfterPoint = attrs.step.split(".")[1].length;
                	maxNumbersBeforePoint = attrs.max.length;
                }
                else{
                	maxNumbersAfterPoint = 0;
                	maxNumbersBeforePoint = attrs.max.length;
                }
            	
            	if(newVal.step.includes(".")){                	
                	if(maxNumbersAfterPoint < newVal.split(".")[1].length)
                		e.preventDefault();
                	else if(maxNumbersBeforePoint < newVal.split(".")[0].length)
                		e.preventDefault();
                }
            });
        }
    };
};

app.directive('limitDecimal', limitDecimal);

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