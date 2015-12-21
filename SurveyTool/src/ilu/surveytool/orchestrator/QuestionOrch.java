package ilu.surveytool.orchestrator;

import java.util.Iterator;

import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.Question;

public class QuestionOrch {

	public QuestionOrch() {
		super();
	}

	public int createQuestion(Question question)
	{
		int questionId = 0;
		
		QuestionDB questionDB = new QuestionDB();
		ContentDB contentDB = new ContentDB();
				
		int contentId = contentDB.insertContentIndex();
				
		questionId = questionDB.insertQuestion(question, contentId);
		
		if(questionId > 0)
		{
			Iterator<String> iter = question.getContents().keySet().iterator();
			while(iter.hasNext())
			{
				String key = iter.next();
				Content content = question.getContents().get(key);
				contentDB.insertContent(contentId, content.getLanguage(), content.getContentType(), content.getText());
			}
		}
		
		return questionId;
	}
	
}
