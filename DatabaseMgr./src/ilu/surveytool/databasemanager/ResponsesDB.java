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
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Option;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;
import ilu.surveytool.databasemanager.DataObject.Project;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Questionnaire;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class ResponsesDB {

	public ResponsesDB() {
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
	
	public HashMap<Integer, HashMap<Integer, HashMap<Integer, List<String>>>> getAnonimousResponseBySurveyId(int surveyId)
	{
		HashMap<Integer, HashMap<Integer, HashMap<Integer, List<String>>>> responses = new HashMap<Integer, HashMap<Integer, HashMap<Integer, List<String>>>>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_ANONYMOUS_RESPONSE_BY_SURVEY_ID);			
	   		pstm.setInt(1, surveyId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{	   			
	   			int anonymousUserId = rs.getInt(DBFieldNames.s_ANONYMOUS_USER_ID);
	   			int questionId = rs.getInt(DBFieldNames.s_QUESTION_ID);
	   			int optionsGroupId = rs.getInt(DBFieldNames.s_OPTIONSGROUPID);
	   			
	   			if(!responses.containsKey(anonymousUserId))
	   			{
	   				responses.put(anonymousUserId, new HashMap<Integer, HashMap<Integer, List<String>>>());
	   			}
	   			
	   			if(!responses.get(anonymousUserId).containsKey(questionId))
	   			{
	   				responses.get(anonymousUserId).put(questionId, new HashMap<Integer, List<String>>());
	   			}
	   			
	   			if(!responses.get(anonymousUserId).get(questionId).containsKey(optionsGroupId))
	   			{
	   				responses.get(anonymousUserId).get(questionId).put(optionsGroupId, new ArrayList<String>());
	   			}
	   			
	   			responses.get(anonymousUserId).get(questionId).get(optionsGroupId).add(rs.getString(DBFieldNames.s_VALUE));
	   			
	   		}
	   			   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return responses;
	}
		

	public HashMap<Integer, HashMap<Integer, String>> getAnonimousResponseByPollId(int pollId)
	{
		HashMap<Integer, HashMap<Integer, String>> responses = new HashMap<Integer, HashMap<Integer, String>>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_ANONYMOUS_RESPONSE_BY_POLL_ID);			
	   		pstm.setInt(1, pollId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{	   			
	   			int anonymousUserId = rs.getInt(DBFieldNames.s_ANONYMOUS_USER_ID);
	   			
	   			if(!responses.containsKey(anonymousUserId))
	   			{
	   				responses.put(anonymousUserId, new HashMap<Integer, String>());
	   			}
	   			
	   			responses.get(anonymousUserId).put(rs.getInt(DBFieldNames.s_QUESTION_ID), 
   						rs.getString(DBFieldNames.s_VALUE));
	   			
	   		}
	   			   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return responses;
	}
		
	/**
	 * Inserts 
	 */
	
	public int insertResponse(Response response) 
	{		
		int responseId = 0;

		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_RESPONSE, Statement.RETURN_GENERATED_KEYS);
		   pstm.setInt(1, response.getQuestionId());
		   int optionsGroupId = response.getOptionsGroupId();
		   if(optionsGroupId != 0)
		   {
			   pstm.setInt(2, optionsGroupId);
		   }else {
			   pstm.setNull(2, Types.INTEGER);
		   }
		   pstm.setString(3, response.getValue());
		   int pollId = response.getPollId();
		   if(pollId != 0)
		   {
			   pstm.setInt(4, pollId);
		   }else {
			   pstm.setNull(4, Types.INTEGER);
		   }
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   responseId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return responseId;
	}
	
}
