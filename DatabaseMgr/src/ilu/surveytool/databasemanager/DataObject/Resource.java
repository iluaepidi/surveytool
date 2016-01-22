package ilu.surveytool.databasemanager.DataObject;

import java.util.HashMap;

public class Resource {
	
	String type = "";
	String pathFile = "";
	HashMap<String, Content> contents;
	
	public Resource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Resource(String type, String pathFile, HashMap<String, Content> contents) {
		super();
		this.type = type;
		this.pathFile = pathFile;
		this.contents = contents;
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

	@Override
	public String toString() {
		return "Resource [type=" + type + ", pathFile=" + pathFile + ", contents=" + contents + "]";
	}
	
}
