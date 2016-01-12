package ilu.surveytool.databasemanager.DataObject;

import java.sql.Timestamp;
import java.util.HashMap;

public class Question {

	int questionId;
	String tag = "";
	Timestamp creationDate;
	String questionType = "";
	HashMap<String, Content> contents;
	String category = "";
	boolean mandatory = false;
	String mainVersion = "";
	boolean helpText = false;
	String templatePage = "";
	String formPage = "";
	
	public Question() {
		super();
		contents = new HashMap<String, Content>();
	}

	public Question(int questionId, String tag, Timestamp creationDate, String questionType,
			HashMap<String, Content> contents, String category, boolean mandatory, String mainVersion,
			boolean helpText) {
		super();
		this.questionId = questionId;
		this.tag = tag;
		this.creationDate = creationDate;
		this.questionType = questionType;
		this.contents = contents;
		this.category = category;
		this.mandatory = mandatory;
		this.mainVersion = mainVersion;
		this.helpText = helpText;
	}
	
	

	public Question(int questionId, String tag, Timestamp creationDate, String questionType,
			HashMap<String, Content> contents, String category, boolean mandatory, String mainVersion, boolean helpText,
			String templatePage, String formPage) {
		super();
		this.questionId = questionId;
		this.tag = tag;
		this.creationDate = creationDate;
		this.questionType = questionType;
		this.contents = contents;
		this.category = category;
		this.mandatory = mandatory;
		this.mainVersion = mainVersion;
		this.helpText = helpText;
		this.templatePage = templatePage;
		this.formPage = formPage;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public HashMap<String, Content> getContents() {
		return contents;
	}

	public void setContents(HashMap<String, Content> contents) {
		this.contents = contents;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getMainVersion() {
		return mainVersion;
	}

	public void setMainVersion(String mainVersion) {
		this.mainVersion = mainVersion;
	}

	public boolean isHelpText() {
		return helpText;
	}

	public void setHelpText(boolean helpText) {
		this.helpText = helpText;
	}

	public String getTemplatePage() {
		return templatePage;
	}

	public void setTemplatePage(String templatePage) {
		this.templatePage = templatePage;
	}

	public String getFormPage() {
		return formPage;
	}

	public void setFormPage(String formPage) {
		this.formPage = formPage;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", tag=" + tag + ", creationDate=" + creationDate
				+ ", questionType=" + questionType + ", contents=" + contents + ", category=" + category
				+ ", mandatory=" + mandatory + ", mainVersion=" + mainVersion + ", helpText=" + helpText + "]";
	}
		
}
