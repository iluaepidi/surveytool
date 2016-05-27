package ilu.surveymanager.handler;


import java.util.ArrayList;
import java.util.List;

import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.PageDB;
import ilu.surveytool.databasemanager.SectionDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.Section;

public class SectionHandler {

	public SectionHandler() {
		super();
	}

	public List<Section> getSectionsBySurveyId(int surveyId, String lang)
	{
		List<Section> sections = new ArrayList<Section>();
		
		SectionDB sectionDB = new SectionDB();
		sections = sectionDB.getSectionsBySurveyId(surveyId, lang,null);
		
		return sections;
	}
	
	public boolean updateContent(int sectionId, Content content)
	{
		boolean updated = false;
		
		SectionDB sectionDB = new SectionDB();
		int contentId = sectionDB.getSectionContentIdBySectionId(sectionId);
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
	
	public boolean removeSection(int sectionId, int surveyId)
	{
		boolean isUnique = false;
		SectionDB sectionDB = new SectionDB();
		
		int numSections = sectionDB.getNumSectionsBySurveyId(surveyId);
		
		if(numSections > 1)
		{
			sectionDB.removeSection(sectionId);
		}
		else
		{
			isUnique = true;
			PageDB pageDB = new PageDB();
			List<Integer> pages = pageDB.getPagesIdBySectionId(sectionId);
			int lastPage = pages.size() - 1;
			for(int i = 0; i < lastPage; i++)
			{
				pageDB.removePage(pages.get(i));
			}
			pageDB.removePageFromQuestionByPage(pages.get(lastPage));
		}
		
		return isUnique;
	}
	

}
