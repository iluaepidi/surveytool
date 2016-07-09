package ilu.surveytool.databasemanager.DataObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Question {

	int questionId;
	String tag = "";
	Timestamp creationDate;
	String questionType = "";
	HashMap<String, Content> contents;
	HashMap<String, String> parameters;
	String category = "";
	boolean mandatory = false;
	boolean optionalAnswer = false;
	boolean helpText = false;
	String templatePage = "";
	String formPage = "";
	List<OptionsGroup> optionsGroups;
	List<Resource> resources;
	int index = 0;
	
	public Question() {
		super();
		contents = new HashMap<String, Content>();
		parameters = new HashMap<String, String>();
		optionsGroups = new ArrayList<OptionsGroup>();
		resources = new ArrayList<Resource>();
	}

	public Question(int questionId, String tag, Timestamp creationDate, String questionType,
			HashMap<String, Content> contents, String category, boolean mandatory, boolean optionalAnswer, boolean helpText, HashMap<String, String> parameters) {
		super();
		this.questionId = questionId;
		this.tag = tag;
		this.creationDate = creationDate;
		this.questionType = questionType;
		this.contents = contents;
		this.category = category;
		this.mandatory = mandatory;
		this.optionalAnswer = optionalAnswer;
		this.helpText = helpText;
		this.parameters = parameters;
	}
	
	public Question(int questionId, String tag, Timestamp creationDate, String questionType,
			HashMap<String, Content> contents, String category, boolean mandatory, boolean optionalAnswer, boolean helpText, HashMap<String, String> parameters,
			String templatePage, String formPage) {
		super();
		this.questionId = questionId;
		this.tag = tag;
		this.creationDate = creationDate;
		this.questionType = questionType;
		this.contents = contents;
		this.category = category;
		this.mandatory = mandatory;
		this.optionalAnswer = optionalAnswer;
		this.helpText = helpText;
		this.parameters = parameters;
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
	
	public boolean isOptionalAnswer() {
		return optionalAnswer;
	}

	public void setOptionalAnswer(boolean optionalAnswer) {
		this.optionalAnswer = optionalAnswer;
	}

	public boolean isHelpText() {
		return helpText;
	}

	public void setHelpText(boolean helpText) {
		this.helpText = helpText;
	}

	public HashMap<String, String> getParameters() {
		return parameters;
	}
	
	public String getParameterValue(String parameterName) {
		String value = "";
		
		if(!parameters.isEmpty()){
			value = parameters.get(parameterName);
			if(value==null)
				value = "";
		}
		
		return value;
	}
	
	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
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
	
	public List<OptionsGroup> getOptionsGroups() {
		return optionsGroups;
	}

	public void setOptionsGroups(List<OptionsGroup> optionsGroups) {
		this.optionsGroups = optionsGroups;
	}
	
	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", tag=" + tag + ", creationDate=" + creationDate
				+ ", questionType=" + questionType + ", contents=" + contents + ", category=" + category
				+ ", mandatory=" + mandatory + ", optionalAnswer=" + optionalAnswer
				+ ", helpText=" + helpText + ", templatePage=" + templatePage
				+ ", formPage=" + formPage + ", optionsGroups=" + optionsGroups + ", resources=" + resources
				+ ", index=" + index + "]";
	}
	
}
