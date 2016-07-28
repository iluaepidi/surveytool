package ilu.surveymanager.handler;

import java.util.List;

import ilu.surveytool.databasemanager.PageDB;
import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.DataObject.Page;

public class PageHandler {

	public PageHandler() {
		super();
	}
	
	public Page createPage(int currentNumPage, int sectionId, int surveyId)
	{
		Page response = new Page();
		
		PageDB pageDB = new PageDB();
		//int numPageMax = pageDB.getMaxNumPagesBySectionId(sectionId);
		List<Page> pages = pageDB.getNumPagesBigerThanBySurveyId(surveyId, currentNumPage);
		
		if(!pages.isEmpty())
		{
			for(Page page : pages)
			{
				pageDB.updateNumPage(page.getPageId(), page.getNumPage() + 1);
			}
		}
		
		int pageId = pageDB.insertPage(sectionId, currentNumPage + 1);
		
		response.setPageId(pageId);
		response.setNumPage(currentNumPage + 1);
		
		return response;
	}

	public boolean removePage(int pageId, int surveyId)
	{
		boolean response = false;
				
		PageDB pageDB = new PageDB();
		Page currentPage = pageDB.getPageByPageId(pageId);
		Page prevPage = pageDB.getPageByNumPageSectionId(currentPage.getSectionId(), currentPage.getNumPage() - 1);
		//int numPageMax = pageDB.getMaxNumPagesBySectionId(sectionId);
		
		if(currentPage.getQuestions().size() > 0)
		{
			int index = prevPage.getQuestions().size() + 1;
			int currentSize = currentPage.getQuestions().size();
			QuestionDB questionDB = new QuestionDB();
			for(int i = 0; i < currentSize; i++)
			{
				questionDB.updateQuestionIndexPageId(currentPage.getQuestions().get(i).getQuestionId(), currentPage.getPageId(), prevPage.getPageId(), index);
				index++;
			}
		}
		
		pageDB.removePage(pageId);
		
		List<Page> pages = pageDB.getNumPagesBigerThanBySurveyId(surveyId, currentPage.getNumPage());
		
		if(!pages.isEmpty())
		{
			for(Page page : pages)
			{
				pageDB.updateNumPage(page.getPageId(), page.getNumPage() - 1);
			}
		}
		response = true;
		
		return response;
	}

}
