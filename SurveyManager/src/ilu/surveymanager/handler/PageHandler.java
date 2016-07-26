package ilu.surveymanager.handler;

import java.util.List;

import ilu.surveytool.databasemanager.PageDB;
import ilu.surveytool.databasemanager.DataObject.Page;

public class PageHandler {

	public PageHandler() {
		super();
	}
	
	public Page createPage(int sectionId, int surveyId)
	{
		Page response = new Page();
		
		PageDB pageDB = new PageDB();
		int numPageMax = pageDB.getMaxNumPagesBySectionId(sectionId);
		List<Page> pages = pageDB.getNumPagesBigerThanBySurveyId(surveyId, numPageMax);
		
		if(!pages.isEmpty())
		{
			for(Page page : pages)
			{
				pageDB.updateNumPage(page.getPageId(), page.getNumPage() + 1);
			}
		}
		
		int pageId = pageDB.insertPage(sectionId, numPageMax + 1);
		
		response.setPageId(pageId);
		response.setNumPage(numPageMax + 1);
		
		return response;
	}

}
