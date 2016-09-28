package ilu.surveytool.databasemanager.DataObject;

public class AnonimousUser {

	int id;
	int surveyId;
	String ipAddress = "";
	int currentPage;
	
	public AnonimousUser() {
		super();
		this.id = 0;
		this.surveyId = 0;
		this.ipAddress = "";
		this.currentPage = 0;
	}

	public AnonimousUser(int id, int surveyId, String ipAddress, int currentPage) {
		super();
		this.id = id;
		this.surveyId = surveyId;
		this.ipAddress = ipAddress;
		this.currentPage = currentPage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	@Override
	public String toString() {
		return "AnonimousUser [id=" + id + ", surveyId=" + surveyId + ", ipAddress=" + ipAddress + ", currentPage="
				+ currentPage + "]";
	}

}
