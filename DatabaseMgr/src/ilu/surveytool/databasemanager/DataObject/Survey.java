package ilu.surveytool.databasemanager.DataObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Survey {
	
	int surveyId;
	//List<Content> contents;
	HashMap<String, Content> contents;
	String project = "";
	int author;
	List<Section> sections = null;
	String publicId = "";
	

	public Survey() {
		this.contents = new HashMap<String, Content>();
	}


	public Survey(int surveyId, HashMap<String, Content> contents, String project, int author) {
		super();
		this.surveyId = surveyId;
		this.contents = contents;
		this.project = project;
		this.author = author;
	}
	

	public Survey(int surveyId, HashMap<String, Content> contents, String project, int author,
			List<Section> sections, String publicId) {
		super();
		this.surveyId = surveyId;
		this.contents = contents;
		this.project = project;
		this.author = author;
		this.sections = sections;
		this.publicId = publicId;
	}
	
	
	public int getSurveyId() {
		return surveyId;
	}
	
	
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	
	
	public HashMap<String, Content> getContents() {
		return contents;
	}
	
	
	public void setContents(HashMap<String, Content> contents) {
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


	public List<Section> getSections() {
		return sections;
	}


	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
	
	
	public String getPublicId() {
		return publicId;
	}


	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}


	@Override
	public String toString() {
		return "Survey [surveyId=" + surveyId + ", contents=" + contents + ", project=" + project + ", author=" + author
				+ ", sections=" + sections + ", publicId=" + publicId + "]";
	}
	
}
