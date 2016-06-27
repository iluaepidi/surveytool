package ilu.surveymanager.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ilu.surveymanager.exportdata.ExportData;
import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.PollDB;
import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.SurveyDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;
import ilu.surveytool.databasemanager.DataObject.Poll;
import ilu.surveytool.databasemanager.DataObject.PollResultResume;
import ilu.surveytool.databasemanager.DataObject.PollTableInfo;
import ilu.surveytool.databasemanager.DataObject.Project;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Survey;

public class PollHandler {

	public PollHandler() {
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
			
			QuestionHandler questionOrch = new QuestionHandler();
			int questionId = questionOrch.createQuestion(poll.getQuestion());
			
			OptionHandler optionOrch = new OptionHandler();
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

	public List<PollTableInfo> getPollsTableInfoByAuthor(int author, String language)
	{
		List<PollTableInfo> response = new ArrayList<PollTableInfo>();
		
		PollDB pollDB = new PollDB();
		response = pollDB.getPollsTableInfoByAuthor(author, language);
		
		return response;
	}
	
	public PollTableInfo getPollsTableInfoById(int pollId, String language)
	{
		PollTableInfo response = null;
		
		PollDB pollDB = new PollDB();
		response = pollDB.getPollsTableInfoById(pollId, language);
		
		return response;
	}
	

	public File exportResults(int pollId)
	{
		File file = null;
		
		ResponsesDB responsesDB = new ResponsesDB();
		HashMap<Integer, HashMap<Integer, String>> responses = responsesDB.getAnonimousResponseByPollId(pollId);  
		
		QuestionDB questionDB = new QuestionDB();
		List<Question> questions = questionDB.getQuestionsByPollId(pollId, "en");
		
		ExportData exportData = new ExportData();
		file = exportData.exportPollResponses(pollId, questions, responses);
		
		return file;
	}

}
