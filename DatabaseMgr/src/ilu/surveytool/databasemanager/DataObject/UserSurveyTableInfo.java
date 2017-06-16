package ilu.surveytool.databasemanager.DataObject;

public class UserSurveyTableInfo {
	
	UserSurvey userSurvey;
	SurveyTableInfo surveyTableInfo;
	String surveyPublicId;

	public UserSurveyTableInfo() {
		// TODO Auto-generated constructor stub
		surveyTableInfo = new SurveyTableInfo();
	}

	public UserSurveyTableInfo(UserSurvey userSurvey, SurveyTableInfo surveyTableInfo, String surveyPublicId) {
		super();
		this.userSurvey = userSurvey;
		this.surveyTableInfo = surveyTableInfo;
		this.surveyPublicId = surveyPublicId;
	}

	public UserSurvey getUserSurvey() {
		return userSurvey;
	}

	public void setUserSurvey(UserSurvey userSurvey) {
		this.userSurvey = userSurvey;
	}

	public SurveyTableInfo getSurveyTableInfo() {
		return surveyTableInfo;
	}

	public void setSurveyTableInfo(SurveyTableInfo surveyTableInfo) {
		this.surveyTableInfo = surveyTableInfo;
	}

	public String getSurveyPublicId() {
		return surveyPublicId;
	}

	public void setSurveyPublicId(String surveyPublicId) {
		this.surveyPublicId = surveyPublicId;
	}

	@Override
	public String toString() {
		return "UserSurveyTableInfo [userSurvey=" + userSurvey + ", surveyTableInfo=" + surveyTableInfo
				+ ", surveyPublicId=" + surveyPublicId + "]";
	}

}
