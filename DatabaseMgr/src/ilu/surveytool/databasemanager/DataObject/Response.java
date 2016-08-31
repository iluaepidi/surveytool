package ilu.surveytool.databasemanager.DataObject;

import java.sql.Timestamp;

public class Response {
	
	int questionId;
	int optionsGroupId;
	String value = "";
	int pollId;

	public Response() {
		super();
		this.optionsGroupId = 0;
		this.pollId = 0;
	}

	public Response(int questionId, int optionsGroupId, String value, int pollId) {
		super();
		this.questionId = questionId;
		this.optionsGroupId = optionsGroupId;
		this.value = value;
		this.pollId = pollId;
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
	
	public int getPollId() {
		return pollId;
	}

	public void setPollId(int pollId) {
		this.pollId = pollId;
	}

	@Override
	public String toString() {
		return "Response [questionId=" + questionId + ", optionsGroupId=" + optionsGroupId + ", value=" + value
				+ ", pollId=" + pollId + "]";
	}
	
}
