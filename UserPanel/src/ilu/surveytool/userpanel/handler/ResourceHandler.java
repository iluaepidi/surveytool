package ilu.surveytool.userpanel.handler;

import java.util.HashMap;
import java.util.Iterator;

import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.ResourceDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.Resource;

public class ResourceHandler {

	public ResourceHandler() {
		super();
	}

	public Resource insertResource(Resource resource, int questionId)
	{
		int resourceId = 0;
		
		ResourceDB resourceDB = new ResourceDB();
		ContentDB contentDB = new ContentDB();
		
		int contentId = contentDB.insertContentIndex();
		resourceId = resourceDB.insertResource(resource, contentId);
		
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
			
			resourceDB.insertQuestionResource(questionId, resourceId);
			resource.setResourceId(resourceId);
		}
		
		return resource;
	}

	public Resource insertImageContent(int resourceId, HashMap<String, Content> contents)
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
