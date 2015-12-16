package ilu.surveytool.databasemanager.DataObject;

public class LoginResponse {
	
	int userId;
	String userName = "";
	String rol = "";
	boolean isValid = false;
	String errorMsg = "";

	public LoginResponse() {
		super();
	}

	public LoginResponse(int userId, String userName, String rol, boolean isValid, String errorMsg) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.rol = rol;
		this.isValid = isValid;
		this.errorMsg = errorMsg;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "LoginResponse [userId=" + userId + ", userName=" + userName + ", isValid=" + isValid + ", errorMsg="
				+ errorMsg + "]";
	}

}
