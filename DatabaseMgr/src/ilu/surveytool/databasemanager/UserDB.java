package ilu.surveytool.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.User;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class UserDB {

	public UserDB() {
		super();
	}
	
	private Connection _openConnection()
	{
		ConnectionFactoryJDBC connectionFactory  = new ConnectionFactoryJDBC();
		return connectionFactory.getConnection();
	}
	
	private void _closeConnections(Connection con, PreparedStatement pstm, ResultSet rs)
	{
		 try {
			 if(pstm != null)
				 pstm.close();
			 if(rs != null)
				 rs.close();
			 if (con != null)
				 con.close();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	}
	
	/*
	 * selects
	 */
	
	public String getEmailByUserId(int userId)
	{
		String email = "";
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_USER_EMAIL_BY_USERID);			
	   		pstm.setInt(1, userId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			email = rs.getString(DBFieldNames.s_USER_EMAIL);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return email;
	}

	public int getUserByTempIdAndEmailConfirmStatus(String tempId)
	{
		int userId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_USER_BY_TEMPORALID_AND_USERSTATUS);			
	   		pstm.setString(1, tempId);
	   		pstm.setInt(2, DBConstants.i_VALUE_USER_STATE_ID_EMAIL_CONFIRM);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			userId = rs.getInt(DBFieldNames.s_USERID);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return userId;
	}
	
	/*
	 * update
	 */
	
	public boolean updatePassword(int userid, String username, String password, String email,String isoLanguage) {
		boolean updated = false;
		
		LanguageDB language = new LanguageDB();
		int idLanguage = language.getIdLanguage(isoLanguage);
		
		if(!this.existsEmail(email,username)){
			
			//System.out.println("updateState");
			
			Connection con = this._openConnection();
			PreparedStatement pstm = null;
			   
			try{
			   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_USER_PASSWORD_AND_EMAIL);
				pstm.setString(1, password);
				pstm.setString(2, email);
				pstm.setInt(3, idLanguage);
				pstm.setInt(4, userid);
			   		
				int numUpdated = pstm.executeUpdate();
				
				if(numUpdated > 0) updated = true;
						
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				this._closeConnections(con, pstm, null);
			}
		}
		 
		return updated;
	}

	public boolean updateActivateAccount(int userId) {
		boolean updated = false;
			
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_USER_ACCOUNT);
			pstm.setString(1, null);
			pstm.setInt(2, DBConstants.i_VALUE_USER_STATE_ID_ACTIVE);
			pstm.setInt(3, userId);
		   		
			int numUpdated = pstm.executeUpdate();
			
			if(numUpdated > 0) updated = true;
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}		
		 
		return updated;
	}

	public boolean updateUserState(int userId, int userStateId) {
		boolean updated = false;
			
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_USER_ACCOUNT);
			pstm.setString(1, null);
			pstm.setInt(2, userStateId);
			pstm.setInt(3, userId);
		   		
			int numUpdated = pstm.executeUpdate();
			
			if(numUpdated > 0) updated = true;
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}		
		 
		return updated;
	}

	public boolean updateUserBasicProfile(User user) {
		boolean updated = false;
			
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_USER_BASIC_PROFILE);
			pstm.setString(1, user.getFirstName().isEmpty() ? null : user.getFirstName());
			pstm.setString(2, user.getLastName().isEmpty() ? null : user.getLastName());
			pstm.setTimestamp(3, user.getBirthdDate());
			pstm.setString(4, user.getGender().isEmpty() ? null : user.getGender());
			pstm.setInt(5, user.getUserId());
		   		
			int numUpdated = pstm.executeUpdate();
			
			if(numUpdated > 0) updated = true;
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}		
		 
		return updated;
	}
	
	public boolean existsEmail(String email,String username)
	{
		boolean existemail = false;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_CHECK_EXISTS_USER_BY_EMAIL_PROFILE);			
	   		pstm.setString(1, email);
	   		pstm.setString(2, username);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			existemail = true;
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return existemail;
	}

}
