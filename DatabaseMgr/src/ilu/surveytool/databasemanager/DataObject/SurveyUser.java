package ilu.surveytool.databasemanager.DataObject;

public class SurveyUser {

	int id;
	int surveyId;
	int userId;
	String ipAddress = "";
	int currentPage;
	boolean isAnonymousUser = false;
	
	public SurveyUser() {
		super();
		this.id = 0;
		this.surveyId = 0;
		this.userId = 0;
		this.ipAddress = "";
		this.currentPage = 0;
	}

	public SurveyUser(int id, int surveyId, String ipAddress, int currentPage, boolean isAnonymousUser) {
		super();
		this.id = id;
		this.surveyId = surveyId;
		this.ipAddress = ipAddress;
		this.currentPage = currentPage;
		this.isAnonymousUser = isAnonymousUser;
	}

	public SurveyUser(int id, int surveyId, int userId, String ipAddress, int currentPage, boolean isAnonymousUser) {
		super();
		this.id = id;
		this.surveyId = surveyId;
		this.userId = userId;
		this.ipAddress = ipAddress;
		this.currentPage = currentPage;
		this.isAnonymousUser = isAnonymousUser;
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

	public boolean isAnonymousUser() {
		return isAnonymousUser;
	}

	public void setAnonymousUser(boolean isAnonymousUser) {
		this.isAnonymousUser = isAnonymousUser;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "SurveyUser [id=" + id + ", surveyId=" + surveyId + ", userId=" + userId + ", ipAddress=" + ipAddress
				+ ", currentPage=" + currentPage + ", isAnonymousUser=" + isAnonymousUser + "]";
	}

}
