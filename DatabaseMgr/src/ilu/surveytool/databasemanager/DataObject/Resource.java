package ilu.surveytool.databasemanager.DataObject;

import java.util.HashMap;

public class Resource {
	
	int resourceId = 0;
	String type = "";
	String pathFile = "";
	HashMap<String, Content> contents;
	int contentId = 0;
	
	public Resource() {
		super();
		contents = new HashMap<String, Content>();
	}
	
	public Resource(int resourceId, String type, String pathFile, HashMap<String, Content> contents) {
		super();
		this.resourceId = resourceId;
		this.type = type;
		this.pathFile = pathFile;
		this.contents = contents;
	}
	
	public Resource(int resourceId, String type, String pathFile, int contentId) {
		super();
		this.resourceId = resourceId;
		this.type = type;
		this.pathFile = pathFile;
		this.contentId = contentId;
	}
	
	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public HashMap<String, Content> getContents() {
		return contents;
	}

	public void setContents(HashMap<String, Content> contents) {
		this.contents = contents;
	}
	
	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	@Override
	public String toString() {
		return "Resource [type=" + type + ", pathFile=" + pathFile + ", contents=" + contents + "]";
	}
	
}
