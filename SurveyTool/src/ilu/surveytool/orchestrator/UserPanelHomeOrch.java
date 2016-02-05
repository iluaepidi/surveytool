package ilu.surveytool.orchestrator;

import java.util.ArrayList;
import java.util.List;

import ilu.surveytool.databasemanager.PollDB;
import ilu.surveytool.databasemanager.SurveyDB;
import ilu.surveytool.databasemanager.DataObject.PollTableInfo;
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
	
	public List<PollTableInfo> getPollsTableInfoByAuthor(int author, String language)
	{
		List<PollTableInfo> response = new ArrayList<PollTableInfo>();
		
		PollDB pollDB = new PollDB();
		response = pollDB.getPollsTableInfoByAuthor(author, language);
		
		return response;
	}

}
