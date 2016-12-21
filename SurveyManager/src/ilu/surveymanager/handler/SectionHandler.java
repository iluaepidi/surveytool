package ilu.surveymanager.handler;


import java.util.ArrayList;
import java.util.List;

import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.PageDB;
import ilu.surveytool.databasemanager.SectionDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.Page;
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
	
	public Section createSection(int surveyId, int formaId, String language)
	{
		Section section = new Section();
		SectionDB sectionDB = new SectionDB();
		
		/*Section currentSection = sectionDB.getSectionBySectionId(sectionId);
		List<Section> sectionsFollowed = sectionDB.getSectionsWithIndexBiggerThan(surveyId, currentSection.getIndex());
		
		for(Section section : sectionsFollowed)
		{
			sectionDB.updateIndex(section.getSectionId(), section.getIndex() + 1);
		}*/
		
		ContentDB contentDB = new ContentDB();
		int contentId = contentDB.insertContentIndex();
		
		//section.setFormaId(currentSection.getFormaId());
		int numSections = sectionDB.getNumSectionsBySurveyId(surveyId);
		section.setIndex(numSections + 1);
		section.setSectionId(sectionDB.insertSection(formaId, section.getIndex(), contentId));
		
		PageDB pageDB = new PageDB();
		//List<Page> pages = pageDB.getPagesBySectionIdWithNumPageBigger(sectionId, language, language, numPage);
		int numPages = pageDB.getNumPagesBySurveyId(surveyId);
		Page page = new Page();
		page.setNumPage(numPages + 1);
		page.setSectionId(section.getSectionId());
		page.setPageId(pageDB.insertPage(section.getSectionId(), page.getNumPage()));		
		section.getPages().add(page);
		/*for(Page page : pages)
		{
			pageDB.updateSectionId(page.getPageId(), result.getSectionId());
		}*/
		
		return section;
	}
	
	public boolean removeSection(int sectionId, int surveyId)
	{
		boolean isUnique = false;
		SectionDB sectionDB = new SectionDB();
		
		int numSections = sectionDB.getNumSectionsBySurveyId(surveyId);
		int indexSection = sectionDB.getSectionIndexBySectionId(sectionId);
		
		if(numSections > 1)
		{
			sectionDB.removeSection(sectionId);
			PageDB pageDB = new PageDB();
			List<Page> pages = pageDB.getPagesIdNumPageBySurveyId(surveyId);
			int i = 1;
			for(Page page : pages)
			{
				pageDB.updateNumPage(page.getPageId(), i);
				i++;
			}
			
			List<Section> sections = sectionDB.getSectionsWithIndexBiggerThan(surveyId, indexSection);
			for(Section section : sections)
			{
				sectionDB.updateIndex(section.getSectionId(), section.getIndex() - 1);
			}
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
