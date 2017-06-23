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
import ilu.surveytool.databasemanager.SurveyUserDB;
import ilu.surveytool.databasemanager.DataObject.SurveyUser;
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

	public JSONObject getCurrentPageJson(String publicId, Object surveyUser, String lang)
	{
		SurveyDB surveyDB = new SurveyDB();
		if(lang == null || lang.isEmpty()) lang = "en";		
		
		SectionDB sectionDB = new SectionDB();
		Section section = sectionDB.getSectionBySurveyIdNumPage(((SurveyUser) surveyUser).getSurveyId(), ((SurveyUser) surveyUser).getCurrentPage());
		int numSection = 0;
		if(section != null) numSection = section.getIndex();
			
		JSONObject survey = surveyDB.getQuestionnaireJson(publicId, numSection, surveyUser, lang);
		try {
			survey.put("hasFinishPage", this.isFinishPage(survey.getInt("surveyId"), survey.getInt("numPages")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return survey;
	}
		
	public int getPageNumber(int surveyId, int numPage, String action, JSONArray questions, int surveyUserId, String lang, boolean isPreview, boolean isAnonymous)
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
		if(!isPreview) responsesDB.insertNewPage(numPage, surveyUserId, section.getSectionId(), isAnonymous);
		
		if (action.equals("next")){
			//Select the goTos related to the questions/responses in the current page
			try{
				int questionId = 0;
				int ogid = 0;
				int oid = 0;
				
				
				
				for (int i = 0; i < questions.length(); i++) {
					//System.out.println(i+","+((questions.getJSONObject(i)).getString("questionType")));
					if (((((questions.getJSONObject(i)).getString("questionType")).equals(DBConstants.s_VALUE_QUESTIONTYPE_SIMPLE_RADIO)) || (((questions.getJSONObject(i)).getString("questionType")).equals(DBConstants.s_VALUE_QUESTIONTYPE_SIMPLE_COMBO))) 
							&& ((questions.getJSONObject(i)).getJSONArray("optionsGroups").length() > 0) 
							&& ((questions.getJSONObject(i)).getJSONArray("optionsGroups").getJSONObject(0).has("response"))){
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
			List<Integer> previousPages = responsesDB.getVisitedPages(surveyUserId, isAnonymous);
			int index = 1;
			while((previousPages.get(previousPages.size()-index) >= numPage))
				index++;
			page = previousPages.get(previousPages.size()-index);
		}
		
		return page;
	}
		
	public SurveyUser existAnonimousUser(SurveyUser surveyUser, boolean isPreview)
	{		
		AnonimousDB anonimousDB = new AnonimousDB();
		SurveyUser anonimousUser2 = anonimousDB.getAnonimousUserByIpAddress(surveyUser.getSurveyId(), surveyUser.getIpAddress(), isPreview);
		if(anonimousUser2 != null)
		{
			surveyUser = anonimousUser2;
		}
		else surveyUser.setAnonymousUser(true);
		
		return surveyUser;
	}
	
	public SurveyUser existSurveyUser(SurveyUser surveyUser, int userId)
	{		
		SurveyUserDB surveyUserDB = new SurveyUserDB();
		SurveyUser surveyUser2 = surveyUserDB.getSurveyUserByUserId(surveyUser.getSurveyId(), userId);
		if(surveyUser2 != null)
		{
			surveyUser = surveyUser2;
		}
		else
		{
			surveyUser.setAnonymousUser(false);
			surveyUser.setUserId(userId);
		}
		
		return surveyUser;
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

	public boolean surveyResponseProcess(SurveyUser surveyUser, JSONObject responses, boolean isPreview)
	{
		boolean stored = true;
		
		//List<Response> responseList = this._responsesJsonToList(responsesJson);
		
		AnonimousDB anonimousDB = new AnonimousDB();
		ResponsesDB responsesDB = new ResponsesDB();
		SurveyUserDB surveyUserDB = new SurveyUserDB();
		
		try
		{
			int surveyId = responses.getInt("surveyId");
			JSONObject page = responses.getJSONObject("page");
			int userSurveyId = surveyUser.getId();
			if(userSurveyId == 0)
			{
//IF IS ANONYMOUS (DONE)
				if(surveyUser.isAnonymousUser())
				{
					userSurveyId = anonimousDB.insertAnonimousUser(responses.getInt("surveyId"), surveyUser.getIpAddress(), page.getInt("numPage"), isPreview);
				}
				else
				{
					userSurveyId = surveyUserDB.insertSurveyUser(surveyId, surveyUser.getUserId(), surveyUser.getCurrentPage());					
				}
				surveyUser.setId(userSurveyId);
			}
			
			if(userSurveyId != 0)
			{
				JSONArray questions = page.getJSONArray("questions");
				for(int q = 0; q < questions.length(); q++)
				{
					JSONObject question = questions.getJSONObject(q);
					int questionId = question.getInt("questionId");
					if(question.has("response"))
					{
//IF IS ANONYMOUS (DONE)
						responsesDB.removeResponse(userSurveyId, surveyId, questionId, null, surveyUser.isAnonymousUser());
//IF IS ANONYMOUS (DONE)
						stored = stored && this._storeUserResponse(new Response(questionId, 0, question.getString("response"), 0), userSurveyId, surveyId, surveyUser.isAnonymousUser());
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
//IF IS ANONYMOUS (DONE)				
									responsesDB.removeResponse(userSurveyId, surveyId, questionId, optionsGroupId, surveyUser.isAnonymousUser());
									String value = optionsGroup.getString("response");
									boolean selectedOther = optionsGroup.getBoolean("selectedOther");
									if(selectedOther && optionsGroup.has("responseOtherText")) value = value + DBConstants.s_VALUE_TOKEN + optionsGroup.getString("responseOtherText");
//IF IS ANONYMOUS (DONE)
									stored = stored && this._storeUserResponse(new Response(questionId, optionsGroupId, value, 0), userSurveyId, surveyId, surveyUser.isAnonymousUser());
								}
								else
								{	
//IF IS ANONYMOUS (DONE)								
									responsesDB.removeResponse(userSurveyId, surveyId, questionId, optionsGroupId, surveyUser.isAnonymousUser());
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
//IF IS ANONYMOUS (DONE)
											stored = stored && this._storeUserResponse(new Response(questionId, optionsGroupId, value, 0), userSurveyId, surveyId, surveyUser.isAnonymousUser());
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
	
	public boolean updateSurveyUserCurrentPage(int surveyUserId, int currentPage, boolean isAnonymous)
	{
		if(isAnonymous)
		{
			AnonimousDB anonymousDB = new AnonimousDB();
			return anonymousDB.updateAnonimousUserCurrentPage(surveyUserId, currentPage);
		}
		else
		{
			SurveyUserDB surveyUserDB = new SurveyUserDB();
			return surveyUserDB.updateSurveyUserCurrentPage(surveyUserId, currentPage);
		}
	}

	public boolean updateSurveyUserFinished(int surveyUserId, boolean finished, boolean isAnonymousUser)
	{
		if(isAnonymousUser)
		{
			AnonimousDB anonymousDB = new AnonimousDB();
			return anonymousDB.updateAnonimousUserFinished(surveyUserId, finished);
		}
		else
		{
			SurveyUserDB surveyUserDB = new SurveyUserDB();
			return surveyUserDB.updateUserSurveyFinished(surveyUserId, finished);
		}
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

	private boolean _storeUserResponse(Response response, int surveyUserId, int surveyId, boolean isAnonymous)
	{
		boolean stored = false;
		ResponsesDB responsesDB = new ResponsesDB();
				
		int responseId = responsesDB.insertResponse(response);
		//IF IS ANONYMOUS
		if(isAnonymous)
		{
			AnonimousDB anonymousDB = new AnonimousDB();
			stored = anonymousDB.insertAnonimousResponse(surveyUserId, responseId);
		}
		else
		{
			SurveyUserDB surveyUserDB = new SurveyUserDB();
			stored = surveyUserDB.insertUserResponse(surveyUserId, responseId);
		}
		
		return stored;
	}

}