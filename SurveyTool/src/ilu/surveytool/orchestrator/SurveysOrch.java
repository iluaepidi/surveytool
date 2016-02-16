package ilu.surveytool.orchestrator;

import java.util.Iterator;
import java.util.List;

import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.SurveyDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.Project;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.constants.DBConstants;

public class SurveysOrch {

	public SurveysOrch() {
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
		
		surveyId = surveyDB.insertSurvey(survey.getAuthor(), project.getProjectId(), contentId);
		
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
	
	public int createFormaAndPage(int surveyId)
	{
		int pageId = 0;
		
		SurveyDB surveyDB = new SurveyDB();
		int formaId = surveyDB.insertForma(surveyId);
		pageId = surveyDB.insertPage(formaId, 0);
		
		return pageId;
	}
	
	public Survey getSurveyDetail(int surveyId, String lang)
	{
		SurveyDB surveyDB = new SurveyDB();
		Survey survey = surveyDB.getQuestionnairesById(surveyId);
		QuestionDB questionDB = new QuestionDB();
		survey.setQuestions(questionDB.getQuestionsBySurveyId(surveyId, lang));
		return survey;
	}
	
	public Survey getSurveyDetailByPublicId(String publicId, String lang)
	{
		SurveyDB surveyDB = new SurveyDB();
		if(lang == null || lang.isEmpty()) lang = "en";
		Survey survey = surveyDB.getQuestionnairesByPublicId(publicId, lang);
		return survey;
	}
	
	public int getPageIdBySurveyId(int surveyId)
	{
		SurveyDB surveyDB = new SurveyDB();
		return surveyDB.getPageId(surveyId);
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
		Project project = surveyDB.getProjectByName(projectName);
		if(project != null)
		{
			updated = surveyDB.updateSurveyProject(project.getProjectId(), surveyId);
		}
		else if(surveysId.size() > 1)
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

}
