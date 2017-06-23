package ilu.surveytool.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import ilu.surveytool.databasemanager.DataObject.SurveyUser;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class SurveyUserDB {

	public SurveyUserDB() {
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

	public SurveyUser getSurveyUserByUserId(int surveyId, int userId)
	{
		SurveyUser surveyUser = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_SURVEY_USER_BY_USERID_SURVEYID);			
	   		pstm.setInt(1, userId);
	   		pstm.setInt(2, surveyId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			surveyUser = new SurveyUser(rs.getInt(DBFieldNames.s_USER_QUESTIONNAIRE_ID), 
	   					rs.getInt(DBFieldNames.s_QUESTIONNAIREID), 
	   					userId,
	   					"", 
	   					rs.getInt(DBFieldNames.s_CURRENT_PAGE),
	   					false);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return surveyUser;
	}

	/**
	 * Inserts 
	 */
	
	public int insertSurveyUser(int surveyId, int userId, int numPage) 
	{		
		int surveyUserId = 0;

		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_USER_QUESTIONNAIRE, Statement.RETURN_GENERATED_KEYS);
		   
		   pstm.setInt(1, userId);
		   pstm.setInt(2, surveyId);
		   pstm.setInt(3, numPage);
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   surveyUserId = rs.getInt(1); 
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return surveyUserId;
	}

	public boolean insertUserResponse(int surveyUserId, int responseId) 
	{		
		boolean inserted = true;

		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
	    	pstm = con.prepareStatement(DBSQLQueries.s_INSERT_USER_RESPONSE);
			pstm.setInt(1, surveyUserId);
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

	/**
	 * Update
	 */
	
	public boolean updateSurveyUserCurrentPage(int surveyUserId, int currentPage) {
		//System.out.println("updateState");
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_USERQUESTIONNAIRE_CURRET_PAGE);
			pstm.setInt(1, currentPage);
			pstm.setInt(2, surveyUserId);
		   		
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

	public boolean updateUserSurveyFinished(int surveyUserId, boolean finished) {
		//System.out.println("updateState");
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_USERQUESTIONNAIRE_FINISHED);
			pstm.setBoolean(1, finished);
			pstm.setInt(2, surveyUserId);
		   		
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
	
	
}
