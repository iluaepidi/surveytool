package ilu.surveyengine.handler;

import java.util.ArrayList;
import java.util.List;

//import java.util.List;

//import ilu.surveyengine.emailSender.EmailsToSend;
import ilu.surveytool.databasemanager.AnonimousDB;
import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.PollDB;
import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.DataObject.Poll;
import ilu.surveytool.databasemanager.DataObject.PollResultResume;
import ilu.surveytool.databasemanager.DataObject.Response;
import ilu.surveytool.databasemanager.constants.DBConstants;

public class PollProcessHandler {

	public PollProcessHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public int storePollResponse(Response response, String ipAddr)
	{
		int anonymousUserId = 0;
		
		if(response != null)
		{
			AnonimousDB anonimousDB = new AnonimousDB();
			ResponsesDB responsesDB = new ResponsesDB();
			anonymousUserId = anonimousDB.insertAnonimousUser(0, ipAddr, 1, false);
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
		title = contentDB.getContentByIdAndLanguage(contentId, language,null).get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
		
		return title;
	}
	
	public Poll getPollDetail(int pollId, String language)
	{
		PollDB pollDB = new PollDB();
		return pollDB.getPollById(pollId, language);
	}

	public List<PollResultResume> getPollResultsResume(int pollId, String lang)
	{
		List<PollResultResume> results = new ArrayList<PollResultResume>();
		int totalResponses = 0;
		
		PollDB pollDB = new PollDB();
		results = pollDB.getPollResponsesResume(pollId, lang);
		
		for(PollResultResume result : results)
		{
			totalResponses += result.getNumResposnes();
		}
		
		for(int i = 0; i < results.size(); i++)
		{
			float numResp = Float.parseFloat(Integer.toString(results.get(i).getNumResposnes()));
			float percentage = Float.parseFloat(Integer.toString(Math.round(((numResp * 100) / totalResponses) * 100))) / 100;
			results.get(i).setPercentage(percentage);
			//results.get(i).setPercentage( (float) (Math.round(((results.get(i).getNumResposnes() * 100) / totalResponses) * 100) / 100));
		}
		
		return results;
	}

	public List<PollResultResume> getPollPreviewResultsResume(int pollId, String lang)
	{
		List<PollResultResume> results = new ArrayList<PollResultResume>();
		
		PollDB pollDB = new PollDB();
		results = pollDB.getPollResponsesResume(pollId, lang);
		
		int numOptions = results.size();
		float percentage = 100 / numOptions;
				
		for(int i = 0; i < results.size(); i++)
		{
			results.get(i).setPercentage(percentage);
		}
		
		return results;
	}

}
