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

public class RegisterDB {

	public RegisterDB() {
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
	
	public RegisterResponse register(RegisterResponse registerResponse)
	{
		RegisterResponse response = new RegisterResponse();
		
		LanguageDB language = new LanguageDB();
		int idLanguage = language.getIdLanguage(registerResponse.getIsoLanguage());
		
		//comprobar que no existe ese username
		if(!this.existsUsername(registerResponse.getUserName())){
			
			if(!this.existsEmail(registerResponse.getEmail())){
				
				Connection con = this._openConnection();
				PreparedStatement pstm = null;
				ResultSet rs = null;
				//`idUser`,`userName`,`email`,`password`,`anonymous`,`idRol`   
				try{
				   	pstm = con.prepareStatement(DBSQLQueries.s_INSERT_USER, Statement.RETURN_GENERATED_KEYS);			
			   		pstm.setString(1, registerResponse.getUserName());
			   		pstm.setString(2, registerResponse.getEmail());
			   		pstm.setString(3, registerResponse.getPassword());
			   		pstm.setBoolean(4, false);
			   		pstm.setInt(5, registerResponse.ROL_NORMAL_USER);
			   		pstm.setInt(6, idLanguage);
			   		
			   		boolean notInserted = pstm.execute();
					   if(!notInserted){
						   rs = pstm.getGeneratedKeys();
						   if(rs.next()){
							   response.setUserId(rs.getInt(1));
							   response.setValid(true);
						   }
						   System.out.println("userid:"+registerResponse.getUserId());
					   }
					   
			   		else
			   		{
			   			response.setErrorMsg("register.invalid");
			   		}
			   		
			    } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					this._closeConnections(con, pstm, rs);
				}
			}else{
				response.setErrorMsg("register.exists.email");
			}
		}else{
			response.setErrorMsg("register.exists.username");
		}
		
		
		
		
		return response;
	}
	
	public boolean existsUsername(String username)
	{
		boolean existusername = false;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_CHECK_EXISTS_USER_BY_USERNAME);			
	   		pstm.setString(1, username);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			existusername = true;
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return existusername;
	}
	
	public boolean existsEmail(String email)
	{
		boolean existemail = false;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_CHECK_EXISTS_USER_BY_EMAIL);			
	   		pstm.setString(1, email);
	   		
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
