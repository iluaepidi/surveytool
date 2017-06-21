package ilu.surveytool.databasemanager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ilu.surveytool.databasemanager.DataObject.Response;
import ilu.surveytool.databasemanager.DataObject.SurveyUser;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Option;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;
import ilu.surveytool.databasemanager.DataObject.Page;
import ilu.surveytool.databasemanager.DataObject.Project;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Questionnaire;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class AnonimousDB {

	public AnonimousDB() {
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
	

	public SurveyUser getAnonimousUserByIpAddress(int surveyId, String ipAddress, boolean isPreview)
	{
		SurveyUser anonimousUser = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_ANONYMOUS_USER_BY_IP_ADDRESS_SURVEYID);			
	   		pstm.setString(1, ipAddress);
	   		pstm.setInt(2, surveyId);
	   		pstm.setBoolean(3, isPreview);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			anonimousUser = new SurveyUser(rs.getInt(DBFieldNames.s_ANONYMOUS_USER_ID), 
	   					rs.getInt(DBFieldNames.s_QUESTIONNAIREID), 
	   					ipAddress, 
	   					rs.getInt(DBFieldNames.s_CURRENT_PAGE),
	   					true);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return anonimousUser;
	}

	public boolean existAnonimousUserByIpAddressPollPublicId(int pollId, String ipAddress)
	{
		boolean exist = false;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_ANONYMOUS_USER_BY_IP_ADDRESS_POLLID);			
	   		pstm.setString(1, ipAddress);
	   		pstm.setInt(2, pollId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			exist = true;
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return exist;
	}

	/**
	 * Update
	 */
	
	public boolean updateAnonimousUserCurrentPage(int anonimousUserId, int currentPage) {
		//System.out.println("updateState");
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_ANONIMOUSUSER_CURRET_PAGE);
			pstm.setInt(1, currentPage);
			pstm.setInt(2, anonimousUserId);
		   		
			int numUpdated = pstm.executeUpdate();
			
			if(numUpdated > 0)
			{
				updated = true;
			}
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		
		return updated;
		   
	}

	public boolean updateAnonimousUserFinished(int anonimousUserId, boolean finished) {
		//System.out.println("updateState");
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_ANONIMOUSUSER_FINISHED);
			pstm.setBoolean(1, finished);
			pstm.setInt(2, anonimousUserId);
		   		
			int numUpdated = pstm.executeUpdate();
			
			if(numUpdated > 0)
			{
				updated = true;
			}
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		
		return updated;
		   
	}
	
	
	/**
	 * Inserts 
	 */
	
	public int insertAnonimousUser(int surveyId) 
	{		
		int anonimousUserId = 0;

		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_ANONIMOUS_USER, Statement.RETURN_GENERATED_KEYS);
		   
		   if(surveyId != 0)
		   {
			   pstm.setInt(1, surveyId);
		   }else {
			   pstm.setNull(1, Types.INTEGER);
		   }
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   anonimousUserId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return anonimousUserId;
	}

	public int insertAnonimousUser(int surveyId, String ipAddress, int numPage, boolean isPreview) 
	{		
		int anonimousUserId = 0;

		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_ANONIMOUS_USER_WITH_IP_NUMPAGE, Statement.RETURN_GENERATED_KEYS);
		   
		   if(surveyId != 0)
		   {
			   pstm.setInt(1, surveyId);
		   }else {
			   pstm.setNull(1, Types.INTEGER);
		   }
		   
		   pstm.setString(2, ipAddress);
		   pstm.setInt(3, numPage);
		   pstm.setBoolean(4, isPreview);
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   anonimousUserId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return anonimousUserId;
	}
	
	public boolean insertAnonimousResponse(int anonimousUserId, int responseId) 
	{		
		boolean inserted = true;

		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
	    	pstm = con.prepareStatement(DBSQLQueries.s_INSERT_ANONIMOUS_RESPONSE);
			pstm.setInt(1, anonimousUserId);
			pstm.setInt(2, responseId);
			   
			boolean notInserted = pstm.execute();
			   
			if(notInserted)
			{
				inserted = false; 
			}
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			inserted = false;
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return inserted;
	}
	
}
