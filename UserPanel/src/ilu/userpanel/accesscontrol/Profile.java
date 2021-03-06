package ilu.userpanel.accesscontrol;

import ilu.surveytool.databasemanager.UserDB;
import ilu.surveytool.databasemanager.DataObject.RegisterResponse;

/**
 * 
 * @author JAgutierrez
 *
 *	This class handles the login process
 */
public class Profile {

	public Profile() {
		super();
	}
	
	/**
	 * This method executes the login process.
	 * @param Credentials (user, password)
	 * @return LoginResponse (isValid, userId or error message)
	 */
	public boolean updateUser(RegisterResponse registerResponse)
	{

		UserDB profileDB = new UserDB();

		return profileDB.updatePassword(registerResponse.getUserId(),registerResponse.getUserName(),registerResponse.getPassword(),registerResponse.getEmail(),registerResponse.getIsoLanguage());
		
	}

}