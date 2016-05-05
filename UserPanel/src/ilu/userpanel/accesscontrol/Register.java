package ilu.userpanel.accesscontrol;

import ilu.surveytool.databasemanager.RegisterDB;
import ilu.surveytool.databasemanager.DataObject.RegisterResponse;

/**
 * 
 * @author JAgutierrez
 *
 *	This class handles the login process
 */
public class Register {

	public Register() {
		super();
	}
	
	/**
	 * This method executes the login process.
	 * @param Credentials (user, password)
	 * @return LoginResponse (isValid, userId or error message)
	 */
	public RegisterResponse register(RegisterResponse registerResponse)
	{
		RegisterDB registerDB = new RegisterDB();
		return registerDB.register(registerResponse);
		
	}

}