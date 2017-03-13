package ilu.surveytool.databasemanager.DataObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Option {
	
	int id;
	HashMap<String, Content> contents;
	int index;
	List<Resource> resources;
	boolean isOther = false;

	public Option() {
		contents = new HashMap<String, Content>();
		resources = new ArrayList<Resource>();
	}
	
	public Option(int id, HashMap<String, Content> contents, int index) {
		super();
		this.id = id;
		this.contents = contents;
		this.index = index;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HashMap<String, Content> getContents() {
		return contents;
	}

	public void setContents(HashMap<String, Content> contents) {
		this.contents = contents;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public boolean isOther() {
		return isOther;
	}

	public void setOther(boolean isOther) {
		this.isOther = isOther;
	}

	@Override
	public String toString() {
		return "Option [id=" + id + ", contents=" + contents + ", index=" + index + "]";
	}
	
}
