package ilu.surveytool.databasemanager.DataObject;

import java.sql.Timestamp;
import java.util.HashMap;

public class Poll {
	
	int pollId;
	String publicUrl = "";
	Timestamp deadLineDate;
	int author;
	int surveyId;
	HashMap<String, Content> contents;
	String project = "";
	Question question;
	String callUrl = "";

	public Poll() {
		super();
		contents = new HashMap<String, Content>();
	}

	public Poll(int pollId, String publicUrl, Timestamp deadLineDate, int author, int surveyId,
			HashMap<String, Content> contents, String project, Question question, String callUrl) {
		super();
		this.pollId = pollId;
		this.publicUrl = publicUrl;
		this.deadLineDate = deadLineDate;
		this.author = author;
		this.surveyId = surveyId;
		this.contents = contents;
		this.project = project;
		this.question = question;
		this.callUrl = callUrl;
	}

	public int getPollId() {
		return pollId;
	}

	public void setPollId(int pollId) {
		this.pollId = pollId;
	}

	public String getPublicUrl() {
		return publicUrl;
	}

	public void setPublicUrl(String publicUrl) {
		this.publicUrl = publicUrl;
	}

	public Timestamp getDeadLineDate() {
		return deadLineDate;
	}

	public void setDeadLineDate(Timestamp deadLineDate) {
		this.deadLineDate = deadLineDate;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
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

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getCallUrl() {
		return callUrl;
	}

	public void setCallUrl(String callUrl) {
		this.callUrl = callUrl;
	}

	@Override
	public String toString() {
		return "Poll [pollId=" + pollId + ", publicUrl=" + publicUrl + ", deadLineDate=" + deadLineDate + ", author="
				+ author + ", surveyId=" + surveyId + ", contents=" + contents + ", project=" + project + ", question="
				+ question + ", callUrl=" + callUrl + "]";
	}
	
}
