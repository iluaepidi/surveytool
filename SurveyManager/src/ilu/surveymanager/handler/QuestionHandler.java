package ilu.surveymanager.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.QuestionParameterDB;
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

			int index = questionDB.getNumQuestionByPage(pageId) + 1;
			
			questionDB.insertQuestionByPage(questionId, pageId, question.isMandatory(), question.isOptionalAnswer(), index, question.getParameters());
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
			if (!content.getText().equals(""))
				contentDB.insertContent(contentId, content.getLanguage(), content.getContentType(), content.getText());
		}
		
		updated = true;
		
		return updated;
	}
	
	public boolean updateMandatory(int questionId, int pageId)
	{
		System.out.println("Update mandatory");
		QuestionDB questionDB = new QuestionDB();
		boolean mandatory = !questionDB.getQuestionByPageMandatory(questionId, pageId);
		questionDB.updateQuestionMandatory(questionId, pageId, mandatory);
		
		return mandatory;
	}
	
	public boolean updateOptionalAnswer(int questionId, int pageId)
	{
		System.out.println("Update opt answer");
		QuestionDB questionDB = new QuestionDB();
		boolean optionalAnswer = !questionDB.getQuestionByPageOptionalAnswer(questionId, pageId);
		questionDB.updateQuestionOptionalAnswer(questionId, pageId, optionalAnswer);
		
		return optionalAnswer;
	}
	
	public HashMap<String,String> updateParameters(int questionId, int pageId, HashMap<String,String> parameters)
	{
		System.out.println("Update parameters");
		QuestionDB questionDB = new QuestionDB();		
		questionDB.updateQuestionParameters(questionId, pageId, parameters);	
		return parameters;
	}	
	
	public boolean updateIndex(int questionId, int prevQuestionId, int pageId)
	{
		boolean updated = false;
		QuestionDB questionDB = new QuestionDB();
		
		int currrentIndex = questionDB.getQuestionByPageIndex(questionId, pageId);
		int prevIndex = questionDB.getQuestionByPageIndex(prevQuestionId, pageId);
		List<Question> questions = questionDB.getQuestionsByPageId(pageId, currrentIndex, prevIndex);	
		
		if(prevIndex != 0 || questions.get(0).getQuestionId() != questionId)
		{		
			for(int i = 0; i < questions.size(); i++)
			{
				int index = questions.get(i).getIndex(); 
				if(index < currrentIndex)
				{
					questionDB.updateQuestionIndex(questions.get(i).getQuestionId(), pageId, index + 1);
				}
				else if(index > currrentIndex)
				{
					questionDB.updateQuestionIndex(questions.get(i).getQuestionId(), pageId, index - 1);
				}
				else
				{
					if(prevIndex < currrentIndex)
					{
						questionDB.updateQuestionIndex(questionId, pageId, prevIndex + 1);
					}
					else
					{
						questionDB.updateQuestionIndex(questionId, pageId, prevIndex);
					}
				}
			}
		}
		
		return updated;
	}
	
	public boolean removeQuestionByPage(int questionId, int pageId)
	{
		boolean removed = false;
		QuestionDB questionDB = new QuestionDB();
		int numQuestions = questionDB.getNumQuestionByPage(pageId);
		int prevQuestionId = questionDB.getQuestionByPageIdIndex(numQuestions, pageId);
		this.updateIndex(questionId, prevQuestionId, pageId);
		questionDB.removeQuestionByPage(questionId, pageId);
		removed = true;
		return removed;
	}
	
	public boolean removeParameterByQuestionByPage(int questionId, int pageId, String parameter)
	{
		boolean removed = false;
		QuestionParameterDB questionParameterDB = new QuestionParameterDB();
		questionParameterDB.removeQuestionParameter(questionId, pageId, parameter);
		removed = true;
		return removed;
	}
	
	public boolean removeParametersByQuestionByPage(int questionId, int pageId)
	{
		boolean removed = false;
		QuestionParameterDB questionParameterDB = new QuestionParameterDB();
		questionParameterDB.removeQuestionParameters(questionId, pageId);
		removed = true;
		return removed;
	}
}
