package ilu.surveytool.databasemanager.DataObject;

public class UserSurvey {
	
	int idUserSurvey = 0;
	int idUser = 0;
	int idSurvey = 0;
	int idForma = 0;
	int progress = 0;
	int currentPage = 0;
	boolean finished = false;

	public UserSurvey() {
		// TODO Auto-generated constructor stub
	}

	public UserSurvey(int idUserSurvey, int idUser, int idSurvey, int idForma, int progress, int currentPage,
			boolean finished) {
		super();
		this.idUserSurvey = idUserSurvey;
		this.idUser = idUser;
		this.idSurvey = idSurvey;
		this.idForma = idForma;
		this.progress = progress;
		this.currentPage = currentPage;
		this.finished = finished;
	}

	public int getIdUserSurvey() {
		return idUserSurvey;
	}

	public void setIdUserSurvey(int idUserSurvey) {
		this.idUserSurvey = idUserSurvey;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdSurvey() {
		return idSurvey;
	}

	public void setIdSurvey(int idSurvey) {
		this.idSurvey = idSurvey;
	}

	public int getIdForma() {
		return idForma;
	}

	public void setIdForma(int idForma) {
		this.idForma = idForma;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	@Override
	public String toString() {
		return "UserSurvey [idUserSurvey=" + idUserSurvey + ", idUser=" + idUser + ", idSurvey=" + idSurvey
				+ ", idForma=" + idForma + ", progress=" + progress + ", currentPage=" + currentPage + ", finished="
				+ finished + "]";
	}

}
