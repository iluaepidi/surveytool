package ilu.surveytool.orchestrator;

import java.util.Iterator;

import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.PollDB;
import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.SurveyDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;
import ilu.surveytool.databasemanager.DataObject.Poll;
import ilu.surveytool.databasemanager.DataObject.Project;
import ilu.surveytool.databasemanager.DataObject.Survey;

public class PollOrch {

	public PollOrch() {
		super();
	}
	
	public int createPoll(Poll poll)
	{
		int pollId = 0;
		
		PollDB pollDB = new PollDB();
		ContentDB contentDB = new ContentDB();
		SurveyDB surveyDB = new SurveyDB();
				
		int contentId = contentDB.insertContentIndex();
		Project project = surveyDB.getProjectByName(poll.getProject());
		if(project == null)
		{	
			project = new Project(surveyDB.insertProject(poll.getProject()), "", null);
		}
				
		pollId = pollDB.insertPoll(poll, contentId, project.getProjectId());
		
		if(pollId > 0)
		{
			Iterator<String> iter = poll.getContents().keySet().iterator();
			while(iter.hasNext())
			{
				String key = iter.next();
				Content content = poll.getContents().get(key);
				contentDB.insertContent(contentId, content.getLanguage(), content.getContentType(), content.getText());
			}
			
			QuestionOrch questionOrch = new QuestionOrch();
			int questionId = questionOrch.createQuestion(poll.getQuestion());
			
			OptionOrch optionOrch = new OptionOrch();
			for(OptionsGroup optionsGroup : poll.getQuestion().getOptionsGroups())
			{
				optionOrch.createOptionsGroup(optionsGroup, questionId);
			}
			
			pollDB.insertQuestionByPoll(questionId, pollId, 1);
		}
		
		return pollId;
	}
	
	public Poll getPollDetailByPublicId(String publicId, String lang)
	{
		PollDB pollDB = new PollDB();
		if(lang == null || lang.isEmpty()) lang = "en";
		Poll poll = pollDB.getPollByPublicId(publicId, lang);
		return poll;
	}

}
