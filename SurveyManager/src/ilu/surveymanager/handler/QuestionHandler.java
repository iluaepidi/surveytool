package ilu.surveymanager.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.PageDB;
import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.QuestionParameterDB;
import ilu.surveytool.databasemanager.QuotasDB;
import ilu.surveytool.databasemanager.ResourceDB;
import ilu.surveytool.databasemanager.SectionDB;
import ilu.surveytool.databasemanager.SurveyDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.OptionsByGroup;
import ilu.surveytool.databasemanager.DataObject.Page;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Resource;
import ilu.surveytool.databasemanager.DataObject.Section;
import ilu.surveytool.databasemanager.constants.DBConstants;

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
			question.setIndex(index);
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

	public boolean updateContent(int questionId, Content content, int index)
	{
		boolean updated = false;
				
		QuestionDB questionDB = new QuestionDB();
		int contentId = questionDB.getQuestionContentIdByQuestionId(questionId);
		ContentDB contentDB = new ContentDB();
		if(!content.getText().isEmpty())
		{
			contentDB.updateContentText(contentId, content.getLanguage(), content.getContentType(), content.getText(), index);
		}
		else
		{
			contentDB.removeContentByTypeLangIndex(contentId, content.getLanguage(), content.getContentType(), index);
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
		QuestionParameterDB questionParameterDB = new QuestionParameterDB();		
		questionParameterDB.updateQuestionParameters(questionId, pageId, parameters);	
		return parameters;
	}	
	
	public boolean updateIndex(int questionId, int prevQuestionId, int pageId, boolean changePage, String action)
	{
		boolean updated = false;
		QuestionDB questionDB = new QuestionDB();
		
		int currrentIndex = questionDB.getQuestionByPageIndex(questionId, pageId);
		
		if(!changePage)
		{
			int prevIndex = questionDB.getQuestionByPageIndex(prevQuestionId, pageId);
			List<Question> questions = questionDB.getQuestionsByPageId(pageId, currrentIndex, prevIndex);	
			
			if(prevIndex != 0 || (!questions.isEmpty() && questions.get(0).getQuestionId() != questionId))
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
		}
		else if(action.equals("up"))
		{
			PageDB pageDB = new PageDB();
			Page page = pageDB.getPageByPageId(pageId);
			Page previousPage = pageDB.getPageByNumPageSectionId(page.getSectionId(), page.getNumPage() - 1);
			if(previousPage != null)
			{
				int numQuestions = pageDB.getNumQuestionByPage(previousPage.getPageId());
				questionDB.updateQuestionIndexPageId(questionId, pageId, previousPage.getPageId(), numQuestions + 1);
				
				List<Question> questions = questionDB.getQuestionsIdIndexByPageId(pageId);
				for(int i = 0; i < questions.size(); i++)
				{
					questionDB.updateQuestionIndex(questions.get(i).getQuestionId(), pageId, i + 1);
				}
			}
			else
			{
				SectionDB sectionDB = new SectionDB();
				Section currentSection = sectionDB.getSectionBySectionId(page.getSectionId());
				if(currentSection.getIndex() > 1)
				{
					Section previousSection = sectionDB.getSectionByFormaIdIndex(currentSection.getFormaId(), currentSection.getIndex() - 1);
					previousPage = pageDB.getPageByNumPageSectionId(previousSection.getSectionId(), page.getNumPage() - 1);
					
					int numQuestions = pageDB.getNumQuestionByPage(previousPage.getPageId());
					questionDB.updateQuestionIndexPageId(questionId, pageId, previousPage.getPageId(), numQuestions + 1);
					
					List<Question> questions = questionDB.getQuestionsIdIndexByPageId(pageId);
					for(int i = 0; i < questions.size(); i++)
					{
						questionDB.updateQuestionIndex(questions.get(i).getQuestionId(), pageId, i + 1);
					}
				}
			}
		}
		else
		{
			PageDB pageDB = new PageDB();
			int numQuestions = pageDB.getNumQuestionByPage(pageId);
			if(currrentIndex == numQuestions)
			{
				Page page = pageDB.getPageByPageId(pageId);
				Page nextPage = pageDB.getPageByNumPageSectionId(page.getSectionId(), page.getNumPage() + 1);
				if(nextPage != null)
				{
					List<Question> questions = questionDB.getQuestionsIdIndexByPageId(nextPage.getPageId());
					for(int i = 0; i < questions.size(); i++)
					{
						questionDB.updateQuestionIndex(questions.get(i).getQuestionId(), nextPage.getPageId(), i + 2);
					}
					questionDB.updateQuestionIndexPageId(questionId, pageId, nextPage.getPageId(), 1);
				}
				else
				{
					SectionDB sectionDB = new SectionDB();
					Section currentSection = sectionDB.getSectionBySectionId(page.getSectionId());
					Section nextSection = sectionDB.getSectionByFormaIdIndex(currentSection.getFormaId(), currentSection.getIndex() + 1);
					if(nextSection != null)
					{
						nextPage = pageDB.getPageByNumPageSectionId(nextSection.getSectionId(), page.getNumPage() + 1);
						
						List<Question> questions = questionDB.getQuestionsIdIndexByPageId(nextPage.getPageId());
						for(int i = 0; i < questions.size(); i++)
						{
							questionDB.updateQuestionIndex(questions.get(i).getQuestionId(), nextPage.getPageId(), i + 2);
						}
						questionDB.updateQuestionIndexPageId(questionId, pageId, nextPage.getPageId(), 1);
					}
				}
			}
		}
		
		return updated;
	}

	public boolean updateIndexRemove(int pageId)
	{
		boolean updated = false;
		QuestionDB questionDB = new QuestionDB();
				
		List<Question> questions = questionDB.getQuestionsIdIndexByPageId(pageId);	
		int index = 1;
		
		for(Question question : questions)
		{
			questionDB.updateQuestionIndex(question.getQuestionId(), pageId, index);
			index++;
		}
		
		return updated;
	}
	
	public boolean updateMatrixOptionsGroupType(int questionId, String optionType){
		boolean updated = false;
		
		QuestionDB questionDB = new QuestionDB();
		if (optionType.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_MULTIPLE)){
			updated = questionDB.updateOptionsGroupType( questionId, DBConstants.s_VALUE_OPTIONSGROUP_TYPE_CHECKBOX);
		}
		else if (optionType.equals(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_SIMPLE))
			updated = questionDB.updateOptionsGroupType( questionId, DBConstants.s_VALUE_OPTIONSGROUP_TYPE_RADIO);
		
		return updated;
	}	
	
	public String updateQuestionType(int questionId, String optionType)
	{
		String questionType = "";
		
		QuestionDB questionDB = new QuestionDB();
		boolean updated = questionDB.updateOptionsGroupType(questionId, optionType);
		if(updated && optionType.equals(DBConstants.s_VALUE_OPTIONSGROUP_TYPE_RADIO))
		{
			updated = updated && questionDB.updateQuestionType(questionId, DBConstants.s_VALUE_QUESTIONTYPE_SIMPLE_RADIO);
			questionType = DBConstants.s_VALUE_QUESTIONTYPE_SIMPLE_RADIO;
		}
		else if(updated && optionType.equals(DBConstants.s_VALUE_OPTIONSGROUP_TYPE_SELECT))
		{
			updated = updated && questionDB.updateQuestionType(questionId, DBConstants.s_VALUE_QUESTIONTYPE_SIMPLE_COMBO);
			questionType = DBConstants.s_VALUE_QUESTIONTYPE_SIMPLE_COMBO;
		} 
				
		return questionType;
	}
	
	public boolean removeQuestionByPage(int questionId, int pageId)
	{
		boolean removed = false;
		QuestionDB questionDB = new QuestionDB();
		int numQuestions = questionDB.getNumQuestionByPage(pageId);
		int prevQuestionId = questionDB.getQuestionByPageIdIndex(numQuestions, pageId);
		questionDB.removeQuestionByPage(questionId, pageId);
		this.updateIndexRemove(pageId);
		SurveyDB surveyDB = new SurveyDB();
		int surveyId = surveyDB.getQuestionnaireIdByPageId(pageId);
		QuotasDB quotasDB = new QuotasDB();
		quotasDB.removeQuota(surveyId, questionId);
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
	
	public boolean removeContent(int questionId, String contentType)
	{
		boolean removed = false;		
		ContentDB contentDB = new ContentDB();
		QuestionDB questionDB = new QuestionDB();
		
		int contentId =  questionDB.getQuestionContentIdByQuestionId(questionId);
		contentDB.removeContentByType(contentId, contentType);
		
		removed = true;
		return removed;
	}
}
