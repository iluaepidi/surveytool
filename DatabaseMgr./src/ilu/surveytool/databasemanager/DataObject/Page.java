package ilu.surveytool.databasemanager.DataObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Page {

	int pageId;
	int sectionId;
	List<Question> questions;
	int numPage;
	
	public Page() {
		super();
		questions = new ArrayList<Question>();
	}

	public Page(int pageId, List<Question> questions, int numPage) {
		super();
		this.pageId = pageId;
		this.questions = questions;
		this.numPage = numPage;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public int getNumPage() {
		return numPage;
	}

	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}

	@Override
	public String toString() {
		return "Page [pageId=" + pageId + ", questions=" + questions + ", numPage=" + numPage + "]";
	}
	
}
