package ilu.userpanel.handler;

import ilu.surveytool.databasemanager.UserDB;
import ilu.surveytool.databasemanager.DataObject.User;
import ilu.surveytool.databasemanager.constants.DBConstants;

public class UserHandler {

	public UserHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean updateBasicProfile(User user)
	{
		boolean updated = false;
		
		UserDB userDB = new UserDB();
		updated = userDB.updateUserBasicProfile(user);
		
		return updated;
	}

}
