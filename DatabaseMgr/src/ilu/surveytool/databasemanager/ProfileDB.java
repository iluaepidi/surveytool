package ilu.surveytool.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ilu.surveytool.databasemanager.DataObject.Credentials;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.RegisterResponse;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class ProfileDB {

	public ProfileDB() {
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
	 * update
	 */
	
	public boolean updatePassword(int userid, String username, String password, String email) {
		boolean updated = false;
		
		if(!this.existsEmail(email,username)){
			
			//System.out.println("updateState");
			
			Connection con = this._openConnection();
			PreparedStatement pstm = null;
			   
			try{
			   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_USER_PASSWORD_AND_EMAIL);
				pstm.setString(1, password);
				pstm.setString(2, email);
				pstm.setInt(3, userid);
			   		
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
