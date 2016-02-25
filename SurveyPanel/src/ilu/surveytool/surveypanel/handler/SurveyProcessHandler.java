package ilu.surveytool.surveypanel.handler;

import java.util.List;

import ilu.surveytool.databasemanager.AnonimousDB;
import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.DataObject.Response;
import ilu.surveytool.surveypanel.emailSender.EmailsToSend;

public class SurveyProcessHandler {

	public SurveyProcessHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean storeAnonimousResponse(int surveyId, List<Response> responses)
	{
		boolean stored = true;
		
		AnonimousDB anonimousDB = new AnonimousDB();
		ResponsesDB responsesDB = new ResponsesDB();
		int anonymousUserId = anonimousDB.insertAnonimousUser(surveyId);
		if(anonymousUserId != 0)
		{
			for(Response response : responses)
			{
				int responseId = responsesDB.insertResponse(response);
				stored = stored && anonimousDB.insertAnonimousResponse(anonymousUserId, responseId);	
			}
			
			if(stored)
			{
				/*EmailsToSend emailToSend = new EmailsToSend("en");
				emailToSend.sendUserResponse(surveyId, anonymousUserId, responses);*/
			}
		}
		
		return stored;
	}

}
