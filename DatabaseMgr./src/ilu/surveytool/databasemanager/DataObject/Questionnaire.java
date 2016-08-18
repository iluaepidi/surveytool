package ilu.surveytool.databasemanager.DataObject;

import java.sql.Timestamp;

public class Questionnaire {

	int idQuestionnaire;
	String state = "";
	Timestamp startDate;
	Timestamp deadLineDate;
	int idContent;
	Timestamp creationDate;
	String project = "";
	String publicId = "";
	int authorId;
	
	public Questionnaire() {
		// TODO Auto-generated constructor stub
	}

	public Questionnaire(int idQuestionnaire, String state, Timestamp startDate, Timestamp deadLineDate, int idContent,
			Timestamp creationDate, String project, String publicId, int authorId) {
		super();
		this.idQuestionnaire = idQuestionnaire;
		this.state = state;
		this.startDate = startDate;
		this.deadLineDate = deadLineDate;
		this.idContent = idContent;
		this.creationDate = creationDate;
		this.project = project;
		this.publicId = publicId;
		this.authorId = authorId;
	}

	public int getIdQuestionnaire() {
		return idQuestionnaire;
	}

	public void setIdQuestionnaire(int idQuestionnaire) {
		this.idQuestionnaire = idQuestionnaire;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getDeadLineDate() {
		return deadLineDate;
	}

	public void setDeadLineDate(Timestamp deadLineDate) {
		this.deadLineDate = deadLineDate;
	}

	public int getIdContent() {
		return idContent;
	}

	public void setIdContent(int idContent) {
		this.idContent = idContent;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	@Override
	public String toString() {
		return "Questionnaire [idQuestionnaire=" + idQuestionnaire + ", state=" + state + ", startDate=" + startDate
				+ ", deadLineDate=" + deadLineDate + ", idContent=" + idContent + ", creationDate=" + creationDate
				+ ", project=" + project + ", publicId=" + publicId + ", authorId=" + authorId + "]";
	}

}
