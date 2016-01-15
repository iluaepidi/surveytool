package ilu.surveytool.orchestrator;

import java.util.List;

import ilu.surveytool.databasemanager.AnonimousDB;
import ilu.surveytool.databasemanager.DataObject.AnonimousResponse;

public class SurveyProcessOrch {

	public SurveyProcessOrch() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean storeAnonimousResponse(int surveyId, List<AnonimousResponse> anonimousResponses)
	{
		boolean stored = false;
		
		AnonimousDB anonimousDB = new AnonimousDB();
		int anonimousUserId = anonimousDB.insertAnonimousUser(surveyId);
		if(anonimousUserId != 0)
		{
			stored = anonimousDB.insertAnonimousResponses(anonimousUserId, anonimousResponses);
		}
		
		return stored;
	}

}
