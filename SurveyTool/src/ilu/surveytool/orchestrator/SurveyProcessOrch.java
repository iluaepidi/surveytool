package ilu.surveytool.orchestrator;

import java.util.List;

import ilu.surveytool.databasemanager.AnonimousDB;
import ilu.surveytool.databasemanager.DataObject.AnonimousResponse;
import ilu.surveytool.emailSender.EmailsToSend;

public class SurveyProcessOrch {

	public SurveyProcessOrch() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean storeAnonimousResponse(int surveyId, List<AnonimousResponse> anonymousResponses)
	{
		boolean stored = false;
		
		AnonimousDB anonimousDB = new AnonimousDB();
		int anonymousUserId = anonimousDB.insertAnonimousUser(surveyId);
		if(anonymousUserId != 0)
		{
			stored = anonimousDB.insertAnonimousResponses(anonymousUserId, anonymousResponses);
			
			if(stored)
			{
				EmailsToSend emailToSend = new EmailsToSend("en");
				emailToSend.sendUserResponse(surveyId, anonymousUserId, anonymousResponses);
			}
		}
		
		return stored;
	}

}
