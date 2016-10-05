package ilu.surveymanager.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.ResourceDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.Resource;
import ilu.surveytool.databasemanager.DataObject.ResourceType;

public class ResourceHandler {

	public ResourceHandler() {
		super();
	}

	public List<ResourceType> getResourceTypes()
	{
		ResourceDB resourceDB = new ResourceDB();
		return resourceDB.getResourceTypes();
	}
	
	public Resource insertResource(Resource resource, int questionId, int optionid)
	{
		int resourceId = 0;
		
		ResourceDB resourceDB = new ResourceDB();
		ContentDB contentDB = new ContentDB();
		
		int contentId = contentDB.insertContentIndex();
		resourceId = resourceDB.insertResource(resource, contentId);
		//resource.setContentId(contentId);
		
		if(resourceId > 0)
		{
			/*Iterator<String> iter = resource.getContents().keySet().iterator();
			while(iter.hasNext())
			{
				String key = iter.next();
				Content content = resource.getContents().get(key);
				contentDB.insertContent(contentId, content.getLanguage(), content.getContentType(), content.getText());
				resource.getContents().get(key).setContentId(contentId);
			}*/
			
			
			if(questionId>=0) resourceDB.insertQuestionResource(questionId, resourceId);
			else if (optionid>=0) resourceDB.updateOptionWithIdResource(optionid, resourceId);
			resource.setResourceId(resourceId);
		}
		
		return resource;
	}

	public Resource insertContent(int resourceId, HashMap<String, Content> contents)
	{
		Resource resource = null;
		
		ContentDB contentDB = new ContentDB();
		ResourceDB resourceDB = new ResourceDB();
		resource = resourceDB.getResourceById(resourceId);
		
		if(resource != null)
		{
			Iterator<String> iter = contents.keySet().iterator();
			while(iter.hasNext())
			{
				String key = iter.next();
				Content content = contents.get(key);
				contentDB.insertContent(resource.getContentId(), content.getLanguage(), content.getContentType(), content.getText());
				contents.get(key).setContentId(resource.getContentId());
			}
			resource.setContents(contents);
		}
		
		return resource;
	}
	
	public boolean updateResourceUrlPath(int resourceId, String urlPath)
	{
		ResourceDB resourceDB = new ResourceDB();
		return resourceDB.updateResourceUrlPath(resourceId, urlPath);
	}

	public boolean updateContent(int resourceId, List<Content> contents)
	{
		boolean updated = false;
				
		ResourceDB resourceDB = new ResourceDB();
		Resource resource = resourceDB.getResourceById(resourceId);
		int contentId = resource.getContentId(); 
		ContentDB contentDB = new ContentDB();
		
		for(Content content : contents)
		{
			if(contentDB.existContent(contentId, content.getLanguage(), content.getContentType()))
			{
				contentDB.updateContentText(contentId, content.getLanguage(), content.getContentType(), content.getText());
			}
			else
			{
				contentDB.insertContent(contentId, content.getLanguage(), content.getContentType(), content.getText());
			}
		}
		
		updated = true;
		
		return updated;
	}
	
	public boolean removeResource(int resourceId)
	{
		boolean removed = false;
		
		ResourceDB resourceDB = new ResourceDB();
		ContentDB contentDB = new ContentDB();
		
		Resource resource = resourceDB.getResourceById(resourceId);
		resourceDB.removeResource(resourceId);
		contentDB.removeContent(resource.getContentId());
		removed = true;
		
		return removed;
	}

}
