package ilu.userpanel.handler;

import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.SurveyDB;
import ilu.surveytool.databasemanager.DataObject.UserSurveyResume;
import ilu.surveytool.databasemanager.constants.DBConstants;

public class SurveyHandler {

	public SurveyHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public UserSurveyResume getUserSurveyResume(int userId)
	{
		UserSurveyResume userSurveyResume = new UserSurveyResume();
		
		SurveyDB surveyDB = new SurveyDB();
		userSurveyResume.setAvailableSurveys(surveyDB.getNumQuestionnairesByStatus(DBConstants.s_VALUE_SURVEY_STATE_ACTIVE));
		
		ResponsesDB responsesDB = new ResponsesDB();
		userSurveyResume.setCompletedSurveys(responsesDB.getNumQuestionnairesFinished(userId, true));
		
		userSurveyResume.setLastSurveyTitel(surveyDB.getLastQuestionnaireTitleNotCompletedByUser(userId));
		
		return userSurveyResume;
	}

}
