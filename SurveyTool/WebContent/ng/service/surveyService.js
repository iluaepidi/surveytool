var sInfo = {};

var app = angular.module('surveyService',[]);

app.factory('survey', ['$http', '$window', function($http, $window) {
  var survey = {};

  survey.info = {};

  survey.initialLoad = function(){
	  
	  survey.info = sInfo;
	  
  	/*$http.get('/usercontrol/getUserInfo').success(function(response) {
      if(response.valid)
      {
        user.info = response.user;
        user.backup.name = response.user.name;
        user.backup.email = response.user.email;
        user.backup.password = response.user.password;
        user.backup.cpassword = response.user.password;
      }   
    });*/
  };

  survey.saveChanges = function(callback){
  	//console.log("Llega a saveChanges: " + JSON.stringify(user.backup));
  	//var userInfo = {};
  	
	/*if(user.backup.name != null && user.backup.email != null && user.backup.email.indexOf('@') > -1)
	{
	  	if(user.backup.name != user.info.name) userInfo.name = user.backup.name;
	  	if(user.backup.email != user.info.email) userInfo.email = user.backup.email;
	  	//if(user.backup.password != user.info.password) userInfo.password = user.backup.password;

	  	if(JSON.stringify(userInfo) != '{}')
	  	{
			console.log("Hay cambios: " + JSON.stringify(userInfo) + " - " + userInfo.length);
	  		$http.post('/usercontrol/updateProfile', userInfo)
			  	.success( function(response) {
			  		if(response.updated)
			  		{
			  			user.info.name = user.backup.name;
				        user.info.email = user.backup.email;
				        //user.info.password = user.backup.password;
			  			console.log("update profile final: " + JSON.stringify(user.info));
			  			callback(false, true);
			  		}	       
				})
		        .error(function(error) {
		            console.log(error);
		            callback(true, false);
		        });
	  	}
	  	else
	  	{
	  		callback(false, false);
	  	}
  	}
  	else{
  		callback(true, false);
  	}*/
  }

  return survey;
}]);