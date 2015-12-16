package ilu.surveytool.orchestrator;

import ilu.surveytool.databasemanager.ContentDB;
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
			for(Content content : survey.getContents())
			{
				contentDB.insertContent(contentId, content.getLanguage(), content.getContentType(), content.getText());
			}
		}
		
		return surveyId;
	}

}
