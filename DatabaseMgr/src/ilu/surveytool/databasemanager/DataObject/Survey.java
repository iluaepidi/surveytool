package ilu.surveytool.databasemanager.DataObject;

import java.util.ArrayList;
import java.util.List;

public class Survey {
	
	int surveyId;
	List<Content> contents;
	String project = "";
	int author;
	

	public Survey() {
		this.contents = new ArrayList<Content>();
	}


	public Survey(int surveyId, List<Content> contents, String project, int author) {
		super();
		this.surveyId = surveyId;
		this.contents = contents;
		this.project = project;
		this.author = author;
	}
	
	
	public int getSurveyId() {
		return surveyId;
	}
	
	
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	
	
	public List<Content> getContents() {
		return contents;
	}
	
	
	public void setContents(List<Content> contents) {
		this.contents = contents;
	}
	
	
	public String getProject() {
		return project;
	}
	
	
	public void setProject(String project) {
		this.project = project;
	}


	public int getAuthor() {
		return author;
	}


	public void setAuthor(int author) {
		this.author = author;
	}


	@Override
	public String toString() {
		return "Survey [surveyId=" + surveyId + ", contents=" + contents + ", project=" + project + ", author=" + author + "]";
	}
	
}
