package ilu.surveytool.databasemanager;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ilu.surveytool.databasemanager.DataObject.PollTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;


public class PollDB {

	public PollDB() {
		// TODO Auto-generated constructor stub
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
	
	/**
	 * Selects
	 */
	
	public List<PollTableInfo> getPollsTableInfoByAuthor(int author, String language)
	{
		List<PollTableInfo> response = new ArrayList<PollTableInfo>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_POLL_TABLE_INFO);	
	   		pstm.setInt(1, author);
	   		pstm.setString(2, language);
	   		pstm.setString(3, DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			PollTableInfo poll = new PollTableInfo();
	   			poll.setPollId(rs.getInt(DBFieldNames.s_POLL_ID));
	   			poll.setDeadLineDate(rs.getTimestamp(DBFieldNames.s_DEADLINE_DATE));
	   			poll.setTitle(rs.getString(DBFieldNames.s_GENERICO_TITLE));
	   			poll.setPublicUrl(rs.getString(DBFieldNames.s_PUBLIC_ID));
	   			response.add(poll);
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
