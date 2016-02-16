package ilu.surveytool.orchestrator;

import java.util.List;

import ilu.surveytool.databasemanager.AnonimousDB;
import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.PollDB;
import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.DataObject.Poll;
import ilu.surveytool.databasemanager.DataObject.Response;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.emailSender.EmailsToSend;

public class PollProcessOrch {

	public PollProcessOrch() {
		// TODO Auto-generated constructor stub
	}
	
	public int storePollResponse(Response response)
	{
		int anonymousUserId = 0;
		
		if(response != null)
		{
			AnonimousDB anonimousDB = new AnonimousDB();
			ResponsesDB responsesDB = new ResponsesDB();
			anonymousUserId = anonimousDB.insertAnonimousUser(0);
			if(anonymousUserId != 0)
			{
				int responseId = responsesDB.insertResponse(response);
				boolean stored = anonimousDB.insertAnonimousResponse(anonymousUserId, responseId);	
				
				
				if(stored)
				{
					/*EmailsToSend emailToSend = new EmailsToSend("en");
					emailToSend.sendUserResponse(surveyId, anonymousUserId, responses);*/
				}
			}
		}
				
		return anonymousUserId;
	}
	
	public String getPollTitle(int pollId, String language)
	{
		String title = "";
		
		PollDB pollDB = new PollDB();
		int contentId = pollDB.getPollContentId(pollId);
		
		ContentDB contentDB = new ContentDB();
		title = contentDB.getContentByIdAndLanguage(contentId, language).get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
		
		return title;
	}
	
	public Poll getPollDetail(int pollId, String language)
	{
		PollDB pollDB = new PollDB();
		return pollDB.getPollById(pollId, language);
	}

}
