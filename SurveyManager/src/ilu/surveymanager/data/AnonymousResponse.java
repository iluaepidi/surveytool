package ilu.surveymanager.data;

public class AnonymousResponse {
	
	int surveyPublicId;
	int questionId;
	int optionsGroupId;
	String value = "";

	public AnonymousResponse() {
		// TODO Auto-generated constructor stub
	}

	public AnonymousResponse(int surveyPublicId, int questionId, int optionsGroupId, String value) {
		super();
		this.surveyPublicId = surveyPublicId;
		this.questionId = questionId;
		this.optionsGroupId = optionsGroupId;
		this.value = value;
	}

	public int getSurveyPublicId() {
		return surveyPublicId;
	}

	public void setSurveyPublicId(int surveyPublicId) {
		this.surveyPublicId = surveyPublicId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getOptionsGroupId() {
		return optionsGroupId;
	}

	public void setOptionsGroupId(int optionsGroupId) {
		this.optionsGroupId = optionsGroupId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AnonymousResponse [surveyPublicId=" + surveyPublicId + ", questionId=" + questionId
				+ ", optionsGroupId=" + optionsGroupId + ", value=" + value + "]";
	}
	
}
