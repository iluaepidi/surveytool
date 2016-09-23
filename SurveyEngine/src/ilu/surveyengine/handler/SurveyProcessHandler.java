package ilu.surveyengine.handler;

import java.util.List;

import org.codehaus.jettison.json.JSONObject;

//import ilu.surveyengine.emailSender.EmailsToSend;
import ilu.surveytool.databasemanager.AnonimousDB;
import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.SurveyDB;
import ilu.surveytool.databasemanager.DataObject.Response;
import ilu.surveytool.databasemanager.DataObject.Survey;

public class SurveyProcessHandler {

	public SurveyProcessHandler() {
		// TODO Auto-generated constructor stub
	}

	public Survey getSurveyDetailByPublicId(String publicId, String lang)
	{
		SurveyDB surveyDB = new SurveyDB();
		if(lang == null || lang.isEmpty()) lang = "en";
		Survey survey = surveyDB.getQuestionnairesByPublicId(publicId, lang);
		return survey;
	}

	public JSONObject getCurrentPageJson(String publicId, int numSection, int numPage, String lang)
	{
		SurveyDB surveyDB = new SurveyDB();
		if(lang == null || lang.isEmpty()) lang = "en";
		JSONObject survey = surveyDB.getQuestionnaireJson(publicId, numSection, numPage, lang);
		return survey;
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
