package ilu.userpanel.accesscontrol;

import ilu.surveytool.databasemanager.RegisterDB;
import ilu.surveytool.databasemanager.UserDB;
import ilu.surveytool.databasemanager.DataObject.RegisterResponse;
import ilu.surveytool.databasemanager.constants.DBConstants;

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
	
	public boolean verifyEmail(String tempId)
	{
		boolean verified = false;
		
		UserDB userDB = new UserDB();
		int userId = userDB.getUserByTempIdAndEmailConfirmStatus(tempId);
		if(userId != 0)
		{
			//verified = userDB.updateActivateAccount(userId);
			verified = userDB.updateUserState(userId, DBConstants.i_VALUE_USER_STATE_ID_BASIC_PROFILE);
		}
		
		return verified;
	}

}