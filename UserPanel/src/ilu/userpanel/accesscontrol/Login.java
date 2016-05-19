package ilu.userpanel.accesscontrol;

import ilu.surveytool.databasemanager.LoginDB;
import ilu.surveytool.databasemanager.DataObject.Credentials;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;

/**
 * 
 * @author JAgutierrez
 *
 *	This class handles the login process
 */
public class Login {

	public Login() {
		super();
	}
	
	/**
	 * This method executes the login process.
	 * @param Credentials (user, password)
	 * @return LoginResponse (isValid, userId or error message)
	 */
	public LoginResponse login(Credentials credentials)
	{
		LoginDB loginDB = new LoginDB();
		return loginDB.login(credentials);		
	}
	
	

}