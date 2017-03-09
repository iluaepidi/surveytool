var sInfo = {};

var app = angular.module('surveyService',[]);

app.factory('survey', ['$http', '$window', function($http, $window) {
  var survey = {};

  survey.info = {};
  survey.questionNumber = 0;

  survey.initialLoad = function(){
	  
	  survey.info = sInfo;
	  
  };

  survey.saveResponseAndGetNextPage = function(action, callback){
	  console.log("Next page: " + JSON.stringify(survey));
	  var responses = getResponseJson(survey, action, true);
	  console.log("Json Response: " + JSON.stringify(responses));
	  $http.post('/SurveyTool/api/SurveyProcessService/responseProcess', responses)
	  	.success( function(response) {
	  		//console.log("Rest response: " + JSON.stringify(response));
	  		//var resJson = JSON.parse(response);
			if(response.stored)
			{
				/*if(action != 'next') {
					response.page.section.page.questions.forEach(function(q){
						if(q.questionType != "bcontent") survey.questionNumber--;						
					});
					console.log("Num Questions sin bcontent: " + survey.questionNumber);
				}*/
				survey.info = response.page;
			}
	  		callback(false, response.stored);	  			       
		})
        .error(function(error) {
            console.log(error);
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
		if(q.response)
		{
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
						//console.log("###### antes otro: " + optionsGroup.responseOtherText);
						if(optionsGroup.response == -1)
						{
							//console.log("###### Es otro: " + og.responseOtherText);
							optionsGroup.responseOtherText = og.responseOtherText;
						}
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
									optionsGroup.options.push(option);
								}
							});
						}
						
						if(og.otherOption)
						{
							if(og.responseOther) optionsGroup.responseOther = og.responseOther;
							if(og.responseOtherText) optionsGroup.responseOtherText = og.responseOtherText;
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
