package ilu.surveytool.userpanel.handler;

import java.util.Iterator;

import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.ResourceDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Resource;

public class QuestionHandler {

	public QuestionHandler() {
		super();
	}

	public int createQuestion(Question question, int pageId)
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
			
			questionDB.insertQuestionByPage(questionId, pageId, question.isMandatory(), 1);
		}
		
		return questionId;
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
	
	public String getQuestionTypeTemplateFile(String questionType)
	{
		QuestionDB questionDB = new QuestionDB();
		return questionDB.getQuestionTypeTemplateFileByName(questionType);
	}

	public boolean updateContent(int questionId, Content content)
	{
		boolean updated = false;
		
		QuestionDB questionDB = new QuestionDB();
		int contentId = questionDB.getQuestionContentIdByQuestionId(questionId);
		ContentDB contentDB = new ContentDB();
		if(contentDB.existContent(contentId, content.getLanguage(), content.getContentType()))
		{
			contentDB.updateContentText(contentId, content.getLanguage(), content.getContentType(), content.getText());
		}
		else
		{
			contentDB.insertContent(contentId, content.getLanguage(), content.getContentType(), content.getText());
		}
		
		updated = true;
		
		return updated;
	}
	
	public boolean updateMandatory(int questionId, int pageId)
	{
		QuestionDB questionDB = new QuestionDB();
		boolean mandatory = !questionDB.getQuestionByPageMandatory(questionId, pageId);
		questionDB.updateQuestionMandatory(questionId, pageId, mandatory);
		
		return mandatory;
	}
	
	public boolean removeQuestionByPage(int questionId, int pageId)
	{
		boolean removed = false;
		QuestionDB questionDB = new QuestionDB();
		questionDB.removeQuestionByPage(questionId, pageId);
		removed = true;
		return removed;
	}
}
