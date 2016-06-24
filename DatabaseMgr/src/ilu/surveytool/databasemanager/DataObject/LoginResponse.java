package ilu.surveytool.databasemanager.DataObject;

import java.util.HashMap;

public class LoginResponse {
	
	int userId;
	String userName = "";
	String rol = "";
	boolean isValid = false;
	String errorMsg = "";
	private String password;
	private String email;
	private String isoLanguage;
	private HashMap<String, String> listLanguage;
	

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsoLanguage() {
		return isoLanguage;
	}

	public void setIsoLanguage(String isoLanguage) {
		this.isoLanguage = isoLanguage;
	}

	public HashMap<String, String> getListLanguage() {
		return listLanguage;
	}

	public void setListLanguage(HashMap<String, String> listLanguage) {
		this.listLanguage = listLanguage;
	}

	

}
