package ilu.surveymanager.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

//import ilu.surveymanager.data.Option;
import ilu.surveymanager.exportdata.ExportData;
import ilu.surveymanager.statistics.Statistics;
import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.PageDB;
import ilu.surveytool.databasemanager.PollDB;
import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.QuotasDB;
import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.SectionDB;
import ilu.surveytool.databasemanager.SurveyDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.Option;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;
import ilu.surveytool.databasemanager.DataObject.Page;
import ilu.surveytool.databasemanager.DataObject.Project;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Response;
import ilu.surveytool.databasemanager.DataObject.ResponseSimple;
import ilu.surveytool.databasemanager.DataObject.Section;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;

public class SurveysHandler {

	public SurveysHandler() {
		super();
	}
	
	public int createSurvey(Survey survey)
	{
		int surveyId = 0;
		
		SurveyDB surveyDB = new SurveyDB();
		ContentDB contentDB = new ContentDB();
				
		int contentId = contentDB.insertContentIndex();
		Project project = surveyDB.getProjectByName(survey.getProject());
		if(project == null)
		{	
			project = new Project(surveyDB.insertProject(survey.getProject()), "", null);
		}
		
		surveyId = surveyDB.insertSurvey(survey.getAuthor(), project.getProjectId(), contentId,survey.getDefaultLanguage());
		
		if(surveyId > 0)
		{
			Iterator<String> iter = survey.getContents().keySet().iterator();
			while(iter.hasNext())
			{
				String key = iter.next();
				Content content = survey.getContents().get(key);
				contentDB.insertContent(contentId, content.getLanguage(), content.getContentType(), content.getText());
			}
		}
		
		return surveyId;
	}
	
	public String getSurveyPublicId(int surveyId)
	{
		String publicId = "";
		
		SurveyDB surveyDB = new SurveyDB();
		publicId = surveyDB.getQuestionnairesPublicId(surveyId);
		
		return publicId;
	}
	
	public int createFormaSectionAndPage(int surveyId)
	{
		int pageId = 0;
		ContentDB contentDB = new ContentDB();
				
		SurveyDB surveyDB = new SurveyDB();
		SectionDB sectionDB = new SectionDB();
		PageDB pageDB = new PageDB();
		int formaId = surveyDB.insertForma(surveyId);
		int contentId = contentDB.insertContentIndex();
		int sectionId = sectionDB.insertSection(formaId, 1, contentId);
		pageId = pageDB.insertPage(sectionId, 1);
		
		return pageId;
	}
	
	public Survey getSurveyDetail(int surveyId, String lang)
	{
		SurveyDB surveyDB = new SurveyDB();
		Survey survey = surveyDB.getQuestionnairesById(surveyId,lang);
		SectionDB sectionDB = new SectionDB();
		survey.setSections(sectionDB.getSectionsBySurveyId(surveyId, lang, survey.getDefaultLanguage()));
		survey.setFormaId(survey.getSections().get(0).getFormaId());
		return survey;
	}
	
	public int getPageIdBySurveyId(int surveyId)
	{
		PageDB pageDB = new PageDB();
		return pageDB.getPageId(surveyId);
	}

	public List<SurveyTableInfo> getSurveysTableInfoByAuthor(int author, String language)
	{
		List<SurveyTableInfo> response = new ArrayList<SurveyTableInfo>();
		
		SurveyDB surveyDB = new SurveyDB();
		//response = surveyDB.getSurveysTableInfoByAuthor(author, language);
		response = surveyDB.getSurveysTableInfoAnonimousByAuthor(author, language);
		
		return response;
	}
	
	public boolean updateContent(int surveyId, Content content)
	{
		boolean updated = false;
		
		SurveyDB surveyDB = new SurveyDB();
		int contentId = surveyDB.getQuestionnairesContentId(surveyId);
		ContentDB contentDB = new ContentDB();
		contentDB.updateContentText(contentId, content.getLanguage(), content.getContentType(), content.getText());
		updated = true;
		
		return updated;
	}
	
	public boolean updateProject(int surveyId, String projectName)
	{
		boolean updated = false;
		
		SurveyDB surveyDB = new SurveyDB();
		int projectId = surveyDB.getQuestionnairesProjectId(surveyId);
		List<Integer> surveysId = surveyDB.getQuestionnairesIdByProjectId(projectId);

		PollDB pollDB = new PollDB();
		List<Integer> pollsId = pollDB.getPollsIdByProjectId(projectId);
		Project project = surveyDB.getProjectByName(projectName);
		if(project != null)
		{
			updated = surveyDB.updateSurveyProject(project.getProjectId(), surveyId);
		}
		else if(surveysId.size() > 1 || pollsId.size() > 0)
		{
			projectId = surveyDB.insertProject(projectName);			
			updated = surveyDB.updateSurveyProject(projectId, surveyId);
		}
		else
		{
			updated = surveyDB.updateProjectName(projectId, projectName);
		}
		
		return updated;
	}
	
	public File exportResults(int surveyId, String userLang)
	{
		File file = null;
		
		ResponsesDB responsesDB = new ResponsesDB();
		HashMap<Integer, HashMap<Integer, HashMap<Integer, List<String>>>> responses = responsesDB.getAnonimousResponseBySurveyId(surveyId, userLang);  
		System.out.println("responses excel: " + responses.toString());
		Survey survey = this.getSurveyDetail(surveyId, "");
				
		QuestionDB questionDB = new QuestionDB();
		List<Question> questions = questionDB.getQuestionsBySurveyId(surveyId, userLang, survey.getDefaultLanguage());
		
		ExportData exportData = new ExportData();
		file = exportData.exportSurveyResponses(surveyId, questions, responses);
		
		return file;
	}
	
	public JSONArray getQuestionsJson(Survey survey)
	{
		JSONArray pagesJson = new JSONArray();
		
		try {
			for(Section section : survey.getSections())
			{
				for(Page page : section.getPages())
				{
					JSONObject pageJson = new JSONObject();
					
					pageJson.put("numPage", page.getNumPage());
					
					pageJson.put("pageId", page.getPageId());
					JSONArray questionsJson = new JSONArray();
					for(Question question : page.getQuestions())
					{
						JSONObject questionJson = new JSONObject();
						questionJson.put("questionId", question.getQuestionId());
						questionJson.put("index", question.getIndex());
						questionJson.put("type", question.getQuestionType());
						questionJson.put("title", question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText());
						JSONArray optionsGroupsJson = new JSONArray();
												
						if(question.getOptionsGroups() != null && !question.getOptionsGroups().isEmpty())
						{
							for(OptionsGroup optionsGroup : question.getOptionsGroups())
							{
								JSONObject optionsGroupJson = new JSONObject();
								optionsGroupJson.put("optionsGroupId", optionsGroup.getId());
								JSONArray optionsJson = new JSONArray();
								if(optionsGroup.getOptions() != null && !optionsGroup.getOptions().isEmpty())
								{
									for(Option option : optionsGroup.getOptions())
									{
										JSONObject optionJson = new JSONObject();
										optionJson.put("optionId", option.getId());
										optionJson.put("index", option.getIndex());
										
										if(option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null && !option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText().isEmpty())
										{
											optionJson.put("title", option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText());
										}
										else if(!option.getResources().isEmpty())
										{
											optionJson.put("title", option.getResources().get(0).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText());
										}
										else
										{
											optionJson.put("title", "");
										}
										
										optionsJson.put(optionJson);
									}
								}
								optionsGroupJson.put("options", optionsJson);
								optionsGroupsJson.put(optionsGroupJson);
							}
						}						
						questionJson.put("optionsGroup", optionsGroupsJson);
						
						questionsJson.put(questionJson);
					}
					
					pageJson.put("questions", questionsJson);
					
					pagesJson.put(pageJson);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pagesJson;
	}

	public JSONArray getQuestionsFeesJson(Survey survey)
	{
          JSONArray pagesJson = new JSONArray();
          QuotasDB quotaDB = new QuotasDB(); 
         
          try {
                 for(Section section : survey.getSections())
                 {
                        for(Page page : section.getPages())
                        {
                               JSONObject pageJson = new JSONObject();
                              
                               //pageJson.put("numPage", page.getNumPage());
                              
                               //pageJson.put("pageId", page.getPageId());
                               JSONArray questionsJson = new JSONArray();
                               for(Question question : page.getQuestions())
                               {
                            	   if(question.getQuestionType().equals("multiple")||question.getQuestionType().equals("simple")){
                                     JSONObject questionJson = new JSONObject();
                                     questionJson.put("questionId", question.getQuestionId());
                                     questionJson.put("index", question.getIndex());
                                     questionJson.put("type", question.getQuestionType());
                                     questionJson.put("title", question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText());
                                     JSONArray optionsGroupsJson = new JSONArray();
                                                                             
                                     if(question.getOptionsGroups() != null && !question.getOptionsGroups().isEmpty())
                                     {
                                            for(OptionsGroup optionsGroup : question.getOptionsGroups())
                                            {
                                                   JSONObject optionsGroupJson = new JSONObject();
                                                   optionsGroupJson.put("optionsGroupId", optionsGroup.getId());
                                                   JSONArray optionsJson = new JSONArray();
                                                   if(optionsGroup.getOptions() != null && !optionsGroup.getOptions().isEmpty())
                                                   {
                                                         for(ilu.surveytool.databasemanager.DataObject.Option option : optionsGroup.getOptions())
                                                         {
                                                        	 	//buscar cuota max y min quote
                                                        	 	int max = quotaDB.getQuotasMax(option.getId());
                                                        	 	int min = quotaDB.getQuotasMin(option.getId());
                                                        	 
                                                                JSONObject optionJson = new JSONObject();
                                                                optionJson.put("optionId", option.getId());
                                                                optionJson.put("index", option.getIndex());
                                                                if(option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)!=null && !option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText().isEmpty())
                        										{
                        											optionJson.put("title", option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText());
                        										}
                        										else if(!option.getResources().isEmpty())
                        										{
                        											optionJson.put("title", option.getResources().get(0).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText());
                        										}
                        										else
                        										{
                        											optionJson.put("title", "");
                        										}
                                                                //optionJson.put("title", option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText());
                                                                optionJson.put("max", max);
                                                                optionJson.put("min", min);
                                                               
                                                                optionsJson.put(optionJson);
                                                         }
                                                   }
                                                   optionsGroupJson.put("options", optionsJson);
                                                   optionsGroupsJson.put(optionsGroupJson);
                                            }
                                     }                                      
                                     questionJson.put("optionsGroup", optionsGroupsJson);
                                    
                                     questionsJson.put(questionJson);
                               }
                            }
                              
                               pageJson.put("questions", questionsJson);
                              
                               pagesJson.put(pageJson);
                               
                        }
                 }
          } catch (JSONException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
          }
         
          return pagesJson;
    }


	public Statistics createStatistics(int surveyId, String language, String languageDefault){
		Statistics statistics = new Statistics();
		statistics.loadData(surveyId, language, languageDefault);
		
		return statistics;
	}
}
