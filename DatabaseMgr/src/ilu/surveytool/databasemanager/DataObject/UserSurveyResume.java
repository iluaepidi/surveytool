package ilu.surveytool.databasemanager.DataObject;

public class UserSurveyResume {
	
	int availableSurveys = 0;
	int completedSurveys = 0;
	String lastSurveyTitel = "";
	
	public UserSurveyResume() {
		// TODO Auto-generated constructor stub
	}

	public UserSurveyResume(int availableSurveys, int completedSurveys, String lastSurveyTitel) {
		super();
		this.availableSurveys = availableSurveys;
		this.completedSurveys = completedSurveys;
		this.lastSurveyTitel = lastSurveyTitel;
	}

	public int getAvailableSurveys() {
		return availableSurveys;
	}

	public void setAvailableSurveys(int availableSurveys) {
		this.availableSurveys = availableSurveys;
	}

	public int getCompletedSurveys() {
		return completedSurveys;
	}

	public void setCompletedSurveys(int completedSurveys) {
		this.completedSurveys = completedSurveys;
	}

	public String getLastSurveyTitel() {
		return lastSurveyTitel;
	}

	public void setLastSurveyTitel(String lastSurveyTitel) {
		this.lastSurveyTitel = lastSurveyTitel;
	}

	@Override
	public String toString() {
		return "UserSurveyResume [availableSurveys=" + availableSurveys + ", completedSurveys=" + completedSurveys
				+ ", lastSurveyTitel=" + lastSurveyTitel + "]";
	}

}
