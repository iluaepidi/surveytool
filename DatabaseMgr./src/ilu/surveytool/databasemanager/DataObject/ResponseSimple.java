package ilu.surveytool.databasemanager.DataObject;

public class ResponseSimple {

	int questionId;
	String value = "";
	
	public ResponseSimple() {
		super();
	}

	public ResponseSimple(int questionId, String value) {
		super();
		this.questionId = questionId;
		this.value = value;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ResponseSimple [questionId=" + questionId + ", value=" + value + "]";
	}
	
}