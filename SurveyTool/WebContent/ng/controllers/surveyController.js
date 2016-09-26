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
		console.log("Entra");
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

	$scope.getDecimals = function(decimals) {
		if(decimals == null || decimals.value == null || decimals.value == "")
		{
			return "0.01";
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

}]);

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