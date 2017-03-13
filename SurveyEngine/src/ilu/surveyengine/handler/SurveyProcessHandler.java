package ilu.surveyengine.handler;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

//import ilu.surveyengine.emailSender.EmailsToSend;
import ilu.surveytool.databasemanager.AnonimousDB;
import ilu.surveytool.databasemanager.LogicGoToDB;
import ilu.surveytool.databasemanager.PageDB;
import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.SectionDB;
import ilu.surveytool.databasemanager.SurveyDB;
import ilu.surveytool.databasemanager.DataObject.AnonimousUser;
import ilu.surveytool.databasemanager.DataObject.Page;
import ilu.surveytool.databasemanager.DataObject.Response;
import ilu.surveytool.databasemanager.DataObject.Section;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;

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

	public JSONObject getCurrentPageJson(String publicId, Object anonimousUser, String lang)
	{
		SurveyDB surveyDB = new SurveyDB();
		if(lang == null || lang.isEmpty()) lang = "en";		
		
		SectionDB sectionDB = new SectionDB();
		Section section = sectionDB.getSectionBySurveyIdNumPage(((AnonimousUser) anonimousUser).getSurveyId(), ((AnonimousUser) anonimousUser).getCurrentPage());
		int numSection = 0;
		if(section != null) numSection = section.getIndex();
			
		JSONObject survey = surveyDB.getQuestionnaireJson(publicId, numSection, anonimousUser, lang);
		try {
			survey.put("hasFinishPage", this.isFinishPage(survey.getInt("surveyId"), survey.getInt("numPages")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return survey;
	}
		
	public int getPageNumber(int surveyId, int numPage, String action, JSONArray questions, int anonimousUser, String lang, boolean isPreview)
	{
		//System.out.println("In getPageNumber ("+numPage+"): "+json);
		int page = -1;
		
		SectionDB sectionDB = new SectionDB();
		/*int numSection = 0;
		List<Section> sections = sectionDB.getSectionsBySurveyId(surveyId, lang, null);
		if (sections!=null && !sections.isEmpty())
			numSection = sections.get(0).getSectionId();*/
		Section section = sectionDB.getSectionBySurveyIdNumPage(surveyId, numPage);
		
		ResponsesDB responsesDB = new ResponsesDB();
		if(!isPreview) responsesDB.insertNewPage(numPage, anonimousUser, section.getSectionId());
		
		if (action.equals("next")){
			//Select the goTos related to the questions/responses in the current page
			try{
				int questionId = 0;
				int ogid = 0;
				int oid = 0;
				
				
				
				for (int i = 0; i < questions.length(); i++) {
					//System.out.println(i+","+((questions.getJSONObject(i)).getString("questionType")));
					if ((((questions.getJSONObject(i)).getString("questionType")).equals(DBConstants.s_VALUE_QUESTIONTYPE_SIMPLE)) && ((questions.getJSONObject(i)).getJSONArray("optionsGroups").length() > 0) && ((questions.getJSONObject(i)).getJSONArray("optionsGroups").getJSONObject(0).has("response"))){
						questionId = (questions.getJSONObject(i)).getInt("questionId");
						JSONArray optionsGroup = (questions.getJSONObject(i)).getJSONArray("optionsGroups");
						ogid = optionsGroup.getJSONObject(0).getInt("optionGroupId");
						oid = Integer.parseInt(((questions.getJSONObject(i)).getJSONArray("optionsGroups").getJSONObject(0).getString("response")));
						LogicGoToDB logicGoToDB = new LogicGoToDB();
						int pageAux = logicGoToDB.getLogicGoToByQuestionId_OgId_OId(questionId, ogid, oid); 
						if((page<0) || (page>=0 && pageAux>=0 && pageAux<page))
							page = pageAux;
					}
				 }
				
				if(page==-1){
					//System.out.println("In page = -1");
					page = numPage+1;
				}
				
				return page;
			}
			catch(Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
				page = -1;
			}
		}
		else{
			List<Integer> previousPages = responsesDB.getVisitedPages(anonimousUser);
			int index = 1;
			while((previousPages.get(previousPages.size()-index) >= numPage))
				index++;
			page = previousPages.get(previousPages.size()-index);
		}
		
		return page;
	}
	
	
	public AnonimousUser existAnonimousUser(AnonimousUser anonimousUser, boolean isPreview)
	{		
		AnonimousDB anonimousDB = new AnonimousDB();
		AnonimousUser anonimousUser2 = anonimousDB.getAnonimousUserByIpAddress(anonimousUser.getSurveyId(), anonimousUser.getIpAddress(), isPreview);
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

	public boolean anonimousResponseProcess(AnonimousUser anonimousUser, JSONObject responses, boolean isPreview)
	{
		boolean stored = true;
		
		//List<Response> responseList = this._responsesJsonToList(responsesJson);
		
		AnonimousDB anonimousDB = new AnonimousDB();
		ResponsesDB responsesDB = new ResponsesDB();
		
		try
		{
			int surveyId = responses.getInt("surveyId");
			JSONObject page = responses.getJSONObject("page");
			int anonymousUserId = anonimousUser.getId();
			if(anonymousUserId == 0) anonymousUserId = anonimousDB.insertAnonimousUser(responses.getInt("surveyId"), anonimousUser.getIpAddress(), page.getInt("numPage"), isPreview);
			if(anonymousUserId != 0)
			{
				JSONArray questions = page.getJSONArray("questions");
				for(int q = 0; q < questions.length(); q++)
				{
					JSONObject question = questions.getJSONObject(q);
					int questionId = question.getInt("questionId");
					if(question.has("response"))
					{
						responsesDB.removeAnonymousResponse(anonymousUserId, surveyId, questionId, null);
						stored = stored && this._storeAnonymousResponse(new Response(questionId, 0, question.getString("response"), 0), anonymousUserId, surveyId);
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
									responsesDB.removeAnonymousResponse(anonymousUserId, surveyId, questionId, optionsGroupId);
									String value = optionsGroup.getString("response");
									boolean selectedOther = optionsGroup.getBoolean("selectedOther");
									if(selectedOther && optionsGroup.has("responseOtherText")) value = value + DBConstants.s_VALUE_TOKEN + optionsGroup.getString("responseOtherText");
									stored = stored && this._storeAnonymousResponse(new Response(questionId, optionsGroupId, value, 0), anonymousUserId, surveyId);
								}
								else
								{									
									responsesDB.removeAnonymousResponse(anonymousUserId, surveyId, questionId, optionsGroupId);
									JSONArray options = optionsGroup.getJSONArray("options");
									for(int o = 0; o < options.length(); o++)
									{
										JSONObject option = options.getJSONObject(o);
										int optionId = option.getInt("optionId");
										String value = Integer.toString(optionId);
										if(option.has("response"))
										{
											if(option.getBoolean("otherOption") && option.has("responseOtherText"))
											{
												value += DBConstants.s_VALUE_TOKEN + option.getString("responseOtherText");
											}
											stored = stored && this._storeAnonymousResponse(new Response(questionId, optionsGroupId, value, 0), anonymousUserId, surveyId);
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

	public boolean updateAnonimousUserFinished(int anonimousUserId, boolean finished)
	{
		AnonimousDB anonymousDB = new AnonimousDB();
		return anonymousDB.updateAnonimousUserFinished(anonimousUserId, finished);
	}
	
	public boolean isOnlyTextPage(JSONArray questions)
	{
		boolean isOnlyTextPage = true;
		
		for(int i = 0; i < questions.length(); i++)
		{
			try {
				String questionType = questions.getJSONObject(i).getString("questionType");
				isOnlyTextPage = isOnlyTextPage && (questionType.equals(DBConstants.s_VALUE_QUESTIONTYPE_BCONTENT));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return isOnlyTextPage;
	}
	

	public boolean isFinishPage(int surveyId, int numPage)
	{
		boolean isOnlyTextPage = true;
		
		QuestionDB questionDB = new QuestionDB();
		List<String> questionTypes = questionDB.getQuestionSTypeBySurveyPublicIdAndPageId(surveyId, numPage);
		
		for(String questionType : questionTypes)
		{
			isOnlyTextPage = isOnlyTextPage && (questionType.equals(DBConstants.s_VALUE_QUESTIONTYPE_BCONTENT));			
		}
		
		return isOnlyTextPage;
	}
	
	
	private boolean _storeAnonymousResponse(Response response, int anonymousUserId, int surveyId)
	{
		boolean stored = false;
		ResponsesDB responsesDB = new ResponsesDB();
		AnonimousDB anonymousDB = new AnonimousDB();
				
		int responseId = responsesDB.insertResponse(response);
		stored = anonymousDB.insertAnonimousResponse(anonymousUserId, responseId);
		
		return stored;
	}

}