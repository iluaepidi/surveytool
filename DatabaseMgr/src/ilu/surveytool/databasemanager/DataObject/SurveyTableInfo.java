package ilu.surveytool.databasemanager.DataObject;

import java.sql.Timestamp;

public class SurveyTableInfo {
	
	int surveyId;
	String title = "";
	String description = "";
	int numUsers;
	int numUsersFinished;
	Timestamp deadLineDate;
	String state = "";

	public SurveyTableInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SurveyTableInfo(int surveyId, String title, int numUsers, int numUsersFinished, Timestamp deadLineDate) {
		super();
		this.surveyId = surveyId;
		this.title = title;
		this.numUsers = numUsers;
		this.numUsersFinished = numUsersFinished;
		this.deadLineDate = deadLineDate;
	}

	public int getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNumUsers() {
		return numUsers;
	}

	public void setNumUsers(int numUsers) {
		this.numUsers = numUsers;
	}

	public int getNumUsersFinished() {
		return numUsersFinished;
	}

	public void setNumUsersFinished(int numUsersFinished) {
		this.numUsersFinished = numUsersFinished;
	}

	public Timestamp getDeadLineDate() {
		return deadLineDate;
	}

	public void setDeadLineDate(Timestamp deadLineDate) {
		this.deadLineDate = deadLineDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "SurveyTableInfo [surveyId=" + surveyId + ", title=" + title + ", description=" + description
				+ ", numUsers=" + numUsers + ", numUsersFinished=" + numUsersFinished + ", deadLineDate=" + deadLineDate
				+ ", state=" + state + "]";
	}
	
}
