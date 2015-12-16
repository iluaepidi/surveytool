package ilu.surveytool.orchestrator;

import ilu.surveytool.databasemanager.Login;
import ilu.surveytool.databasemanager.DataObject.Credentials;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;

public class LoginOrch {

	public LoginOrch() {
		super();
	}
	
	public LoginResponse login(Credentials credentials)
	{
		Login loginDB = new Login();
		return loginDB.login(credentials);		
	}

}
