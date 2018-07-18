package ilu.surveytool.databasemanager.encrypt;

/*import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ilu.surveytool.databasemanager.DataObject.User;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

import ilu.securitymodule.SecurityModule;
import ilu.securitymodule.exception.SecurityModuleException;;*/

public class EncryptDB {
	
	/*SecurityModule sm;

	public EncryptDB() {
		// TODO Auto-generated constructor stub
		super();
		sm = new SecurityModule();
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
	
	public void displayValue(int idResponse)
	{
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement("SELECT idResponse, value FROM surveytool.responses where idResponse = ?");
		   	pstm.setInt(1, idResponse);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			byte[] valueCypher = rs.getBytes("value");
	   			System.out.println("Valor descifrado: " + this.sm.decrypt(valueCypher));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
	}

	public void cypherDb()
	{
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement("SELECT idResponse, value FROM surveytool.responses");
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			int idResponse = rs.getInt("idResponse");
	   			String value = rs.getString("value");
	   			this.updateCypherValue(idResponse, sm.encrypt(value));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		//return email;
	}

	
	public boolean updateCypherValue(int idResponse, byte[] valueEncrypted) {
		boolean updated = false;
			
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement("UPDATE `surveytool`.`responses` SET `valueCypher`= ? WHERE `idResponse`= ?");
			pstm.setBytes(1, valueEncrypted);
			pstm.setInt(2, idResponse);
		   		
			int numUpdated = pstm.executeUpdate();
			
			if(numUpdated > 0) updated = true;
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}		
		 
		return updated;
	}*/
}
