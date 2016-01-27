package ilu.surveytool.orchestrator;

import java.util.HashMap;
import java.util.Iterator;

import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.ResourceDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.Resource;

public class ResourceOrch {

	public ResourceOrch() {
		super();
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

}
