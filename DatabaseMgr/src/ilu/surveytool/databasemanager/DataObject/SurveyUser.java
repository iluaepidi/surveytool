package ilu.surveytool.databasemanager.DataObject;

public class SurveyUser {

	int id;
	int surveyId;
	int userId;
	String ipAddress = "";
	int currentPage;
	boolean isAnonymousUser = false;
	
	private String token = "#";
	
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

	public SurveyUser(String cookieValue) {
		super();
		String[] cads = cookieValue.split(token);
		this.id = Integer.parseInt(cads[0]);
		this.surveyId = Integer.parseInt(cads[1]);
		this.userId = Integer.parseInt(cads[2]);
		this.currentPage = Integer.parseInt(cads[3]);
		this.isAnonymousUser = Boolean.parseBoolean(cads[4]);
		this.ipAddress = cads[5];
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

	public String toCoockie() {
		return id + this.token + surveyId + this.token + userId + this.token + currentPage + this.token + isAnonymousUser + this.token + ipAddress;
	}
}
