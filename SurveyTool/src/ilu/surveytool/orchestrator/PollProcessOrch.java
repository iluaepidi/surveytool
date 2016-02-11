package ilu.surveytool.orchestrator;

import java.util.List;

import ilu.surveytool.databasemanager.AnonimousDB;
import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.DataObject.Response;
import ilu.surveytool.emailSender.EmailsToSend;

public class PollProcessOrch {

	public PollProcessOrch() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean storePollResponse(Response response)
	{
		boolean stored = true;
		
		if(response != null)
		{
			AnonimousDB anonimousDB = new AnonimousDB();
			ResponsesDB responsesDB = new ResponsesDB();
			int anonymousUserId = anonimousDB.insertAnonimousUser(0);
			if(anonymousUserId != 0)
			{
				int responseId = responsesDB.insertResponse(response);
				stored = stored && anonimousDB.insertAnonimousResponse(anonymousUserId, responseId);	
				
				
				if(stored)
				{
					/*EmailsToSend emailToSend = new EmailsToSend("en");
					emailToSend.sendUserResponse(surveyId, anonymousUserId, responses);*/
				}
			}
		}
		else
		{
			stored = false;
		}
		
		return stored;
	}

}
