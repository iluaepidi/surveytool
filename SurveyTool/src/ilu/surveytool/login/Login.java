package ilu.surveytool.login;

import ilu.surveytool.databasemanager.LoginDB;
import ilu.surveytool.databasemanager.DataObject.Credentials;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;

public class Login {

	public Login() {
		super();
	}
	
	public LoginResponse login(Credentials credentials)
	{
		LoginDB loginDB = new LoginDB();
		return loginDB.login(credentials);		
	}

}