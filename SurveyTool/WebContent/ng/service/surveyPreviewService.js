var sInfo = {};
var hostname = "";

var app = angular.module('surveyService',[]);

app.factory('survey', ['$http', '$window', function($http, $window) {
  var survey = {};

  survey.info = {};
  survey.questionNumber = 0;

  survey.initialLoad = function(){
	  
	  survey.info = sInfo;
	  
  };

  survey.saveResponseAndGetNextPage = function(action, callback){
	  //console.log("Next page: " + JSON.stringify(survey));
	  var responses = getResponseJson(survey, action, true);
	  console.log("Json Response: " + JSON.stringify(responses));
	  console.log("Current URL: " + $window.location.href);
	  var currentUrl = $window.location.href;
	  var urlBase = "";
	  if(currentUrl.indexOf("SurveyTool") !== -1) urlBase = "/SurveyTool"; 
	  //alert("URL next page: " + hostname + urlBase + '/api/SurveyProcessService/responseProcess');
	  //$http.post('/SurveyTool/api/SurveyProcessService/responseProcess', responses)
	  $http.post(urlBase + '/api/SurveyProcessService/responseProcess', responses)
	  	.success( function(response) {
	  		console.log("Rest response: " + JSON.stringify(response));
	  		//var resJson = JSON.parse(response);
			if(response.stored)
			{
				survey.info = response.page;
			}
	  		callback(false, response.stored);	  			       
		})
        .error(function(error) {
            console.log("ERROR: " + error);
            callback(true, false);
        });
	  
	  callback();
  };
  
  /*survey.increaseNumQuestion = function(question, callback){
	  console.log("increaseNumQuestion: " + JSON.stringify(question));
	  if(question.questionType != "bcontent") return survey.questionNumber++;
	  else return 0;
  };*/

  return survey;
}]);


function getResponseJson(currentSurvey, action, preview)
{
	var response = {};
	response.publicId = currentSurvey.info.publicId;
	response.surveyId = currentSurvey.info.surveyId;
	response.lang = currentSurvey.info.lang;
	response.numPages = currentSurvey.info.numPages;
	response.action = action;
	response.isPreview = preview
	response.page = {};
	response.page.numPage = currentSurvey.info.section.page.numPage;
	response.page.questions = [];
	currentSurvey.info.section.page.questions.forEach(function(q){
		var question = {};
		question.questionId = q.questionId;
		question.questionType = q.questionType;
		//console.log("RESPONSE: " + q.response + " - qid: " + q.questionId);
		if(q.response || (q.questionType === 'scale' && q.response == 0))
		{
			//console.log("RESPONSE DENTRO: " + q.response + " - qid: " + q.questionId);
			question.response = q.response;
		}
		else
		{
			if(q.optionsGroups && q.optionsGroups.length > 0)
			{
				question.optionsGroups = [];
				q.optionsGroups.forEach(function(og){
					var optionsGroup = {};
					optionsGroup.optionGroupId = og.optionGroupId;
					optionsGroup.optionType = og.optionType;
					if(og.response)
					{
						optionsGroup.response = og.response;
						var selectedOther = false;
						og.options.forEach(function(o){
							if(o.optionId == optionsGroup.response) selectedOther = o.otherOption;
						});
						optionsGroup.selectedOther = selectedOther;
						if(selectedOther) optionsGroup.responseOtherText =  og.responseOtherText;
					}
					else
					{
						if(og.options)
						{
							optionsGroup.options = [];
							og.options.forEach(function(o){
								if(o.response)
								{
									var option = {};
									option.optionId = o.optionId;
									option.response = o.response;
									option.otherOption = o.otherOption;
									if(option.otherOption) option.responseOtherText = o.responseOtherText
									optionsGroup.options.push(option);
								}
							});
						}
						
					}
					question.optionsGroups.push(optionsGroup);
				});
			}
		}
		response.page.questions.push(question);			
	});
	
	return response;
}
