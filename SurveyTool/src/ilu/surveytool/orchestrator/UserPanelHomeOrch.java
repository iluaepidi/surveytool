package ilu.surveytool.orchestrator;

import java.util.ArrayList;
import java.util.List;

import ilu.surveytool.databasemanager.SurveyDB;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;

public class UserPanelHomeOrch {

	public UserPanelHomeOrch() {
		// TODO Auto-generated constructor stub
	}
	
	public List<SurveyTableInfo> getSurveysTableInfoByAuthor(int author, String language)
	{
		List<SurveyTableInfo> response = new ArrayList<SurveyTableInfo>();
		
		SurveyDB surveyDB = new SurveyDB();
		response = surveyDB.getSurveysTableInfoByAuthor(author, language);
		
		return response;
	}

}
