package ilu.surveytool.databasemanager.DataObject;

public class RegisterResponse {
	
	int userId;
	String userName = "";
	private String email = "";
	private String reemail = "";
	private String password = "";
	private String repassword = "";
	private String temporalId ="";
	private int rol = 2;
	private int status = 2;
	boolean isValid = false;
	String errorMsg = "";
	private String isoLanguage ="";

	public static int ROL_ADMIN_USER = 1;
	public static int ROL_INTERVIEWER_USER = 4;
	public static int ROL_NORMAL_USER = 2;
	public static int ROL_OBSERVER_USER = 5;
	
	public static int STATUS_ACTIVE_USER = 1;
	public static int STATUS_EMAIL_USER = 2;
	
	public RegisterResponse() {
		super();
	}

	public RegisterResponse(int userId, String userName, String email,String password, boolean isValid, String errorMsg) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.setEmail(email);
		this.setPassword(password);
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

	

	public String getReemail() {
		return reemail;
	}

	public void setReemail(String reemail) {
		this.reemail = reemail;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RegisterResponse [userId=" + userId + ", userName=" + userName + ", email=" + getEmail() + ", password=" + getPassword() + ", isValid=" + isValid + ", errorMsg="
				+ errorMsg + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getIsoLanguage() {
		return isoLanguage;
	}

	public void setIsoLanguage(String isoLanguage) {
		this.isoLanguage = isoLanguage;
	}

	public String getTemporalId() {
		return temporalId;
	}

	public void setTemporalId(String temporalId) {
		this.temporalId = temporalId;
	}

}
