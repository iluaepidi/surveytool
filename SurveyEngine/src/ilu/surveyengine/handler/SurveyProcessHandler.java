package ilu.surveyengine.handler;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

//import ilu.surveyengine.emailSender.EmailsToSend;
import ilu.surveytool.databasemanager.AnonimousDB;
import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.SurveyDB;
import ilu.surveytool.databasemanager.DataObject.AnonimousUser;
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
	
	public AnonimousUser existAnonimousUser(AnonimousUser anonimousUser)
	{		
		AnonimousDB anonimousDB = new AnonimousDB();
		AnonimousUser anonimousUser2 = anonimousDB.getAnonimousUserByIpAddress(anonimousUser.getIpAddress());
		if(anonimousUser2 != null)
		{
			anonimousUser = anonimousUser2;
		}
		
		return anonimousUser;
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

	public boolean anonimousResponseProcess(AnonimousUser anonimousUser, JSONObject responses)
	{
		boolean stored = true;
		
		//List<Response> responseList = this._responsesJsonToList(responsesJson);
		
		AnonimousDB anonimousDB = new AnonimousDB();
		ResponsesDB responsesDB = new ResponsesDB();
		
		try
		{
			JSONObject page = responses.getJSONObject("page");
			int anonymousUserId = anonimousUser.getId();
			if(anonymousUserId == 0) anonymousUserId = anonimousDB.insertAnonimousUser(responses.getInt("surveyId"), anonimousUser.getIpAddress(), page.getInt("numPage"));
			if(anonymousUserId != 0)
			{
				JSONArray questions = page.getJSONArray("questions");
				for(int q = 0; q < questions.length(); q++)
				{
					JSONObject question = questions.getJSONObject(q);
					int questionId = question.getInt("questionId");
					if(question.has("response"))
					{
						stored = stored && this._storeAnonymousResponse(new Response(questionId, 0, question.getString("response"), 0), anonymousUserId);
					}
					else
					{					
						if(!question.isNull("optionsGroups") && question.getJSONArray("optionsGroups").length() > 0)
						{
							JSONArray optionsGroups = question.getJSONArray("optionsGroups");
							for(int og = 0; og < optionsGroups.length(); og++)
							{
								JSONObject optionsGroup = optionsGroups.getJSONObject(og);
								int optionsGroupId = optionsGroup.getInt("optionGroupId");
								if(optionsGroup.has("response"))
								{
									stored = stored && this._storeAnonymousResponse(new Response(questionId, optionsGroupId, optionsGroup.getString("response"), 0), anonymousUserId);
								}
								else
								{
									JSONArray options = optionsGroup.getJSONArray("options");
									for(int o = 0; o < options.length(); o++)
									{
										JSONObject option = options.getJSONObject(o);
										int optionId = option.getInt("optionId");
										if(option.has("response"))
										{
											stored = stored && this._storeAnonymousResponse(new Response(questionId, optionsGroupId, option.getString("response"), 0), anonymousUserId);
										}
									}
								}
							}
						}
					}
				}
			
			
				if(stored)
				{
					/*EmailsToSend emailToSend = new EmailsToSend("en");
					emailToSend.sendUserResponse(surveyId, anonymousUserId, responses);*/
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stored;
	}
	
	public boolean updateAnonimousUserCurrentPage(int anonimousUserId, int currentPage)
	{
		AnonimousDB anonymousDB = new AnonimousDB();
		return anonymousDB.updateAnonimousUserCurrentPage(anonimousUserId, currentPage);
	}
	
	private boolean _storeAnonymousResponse(Response response, int anonymousUserId)
	{
		boolean stored = false;
		ResponsesDB responsesDB = new ResponsesDB();
		AnonimousDB anonymousDB = new AnonimousDB();
		
		int responseId = responsesDB.insertResponse(response);
		stored = anonymousDB.insertAnonimousResponse(anonymousUserId, responseId);
		
		return stored;
	}

}