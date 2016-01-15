package ilu.surveytool.databasemanager.DataObject;

public class AnonimousResponse {
	
	int questionId;
	int optionsGroupId;
	String value = "";

	public AnonimousResponse() {
		// TODO Auto-generated constructor stub
	}

	public AnonimousResponse(int questionId, int optionsGroupId, String value) {
		super();
		this.questionId = questionId;
		this.optionsGroupId = optionsGroupId;
		this.value = value;
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
		return "AnonymousResponse [questionId=" + questionId
				+ ", optionsGroupId=" + optionsGroupId + ", value=" + value + "]";
	}

}
