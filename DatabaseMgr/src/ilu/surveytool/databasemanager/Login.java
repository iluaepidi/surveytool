package ilu.surveytool.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ilu.surveytool.databasemanager.DataObject.Credentials;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class Login {

	public Login() {
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
	
	public LoginResponse login(Credentials credentials)
	{
		LoginResponse response = new LoginResponse();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_LOGIN);			
	   		pstm.setString(1, credentials.getUsername());
	   		pstm.setString(2, credentials.getUsername());
	   		pstm.setString(3, credentials.getPassword());
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			response.setValid(true);
	   			response.setUserId(rs.getInt(DBFieldNames.s_USERID));
	   			response.setUserName(rs.getString(DBFieldNames.s_USERNAME));
	   			response.setRol(rs.getString(DBFieldNames.s_ROLNAME));
	   		}
	   		else
	   		{
	   			response.setErrorMsg("Invalid username or password");
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}

}
