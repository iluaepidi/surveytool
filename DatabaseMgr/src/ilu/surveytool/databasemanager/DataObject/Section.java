package ilu.surveytool.databasemanager.DataObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Section {

	int sectionId;
	int formaId;
	List<Page> pages;
	HashMap<String, Content> contents;
	
	public Section() {
		super();
		pages = new ArrayList<Page>();
		contents = new HashMap<String, Content>();
	}
	
	public Section(int sectionId, int formaId, List<Page> pages, HashMap<String, Content> contents) {
		super();
		this.sectionId = sectionId;
		this.formaId = formaId;
		this.pages = pages;
		this.contents = contents;
	}

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}

	public int getFormaId() {
		return formaId;
	}

	public void setFormaId(int formaId) {
		this.formaId = formaId;
	}
	
	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	public HashMap<String, Content> getContents() {
		return contents;
	}

	public void setContents(HashMap<String, Content> contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "Section [sectionId=" + sectionId + ", formaId=" + formaId + ", pages=" + pages + ", contents="
				+ contents + "]";
	}
	
}
