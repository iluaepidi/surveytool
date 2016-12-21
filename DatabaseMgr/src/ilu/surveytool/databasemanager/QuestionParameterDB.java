package ilu.surveytool.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Questionnaire;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class QuestionParameterDB {

	public QuestionParameterDB() {
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
	
	/*
	 * selects
	 *  		
	 */
	
	//QuestionByPage
	
	public HashMap<String, String> getQuestionParameterByPageID(int pageId)
	{
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONPARAMETER_BY_PAGEID);			
	   		pstm.setInt(1, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			parameters.put(rs.getString(DBFieldNames.s_PARAMETER_NAME), 
	   					rs.getString(DBFieldNames.s_VALUE));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return parameters;
	}
	
	public HashMap<String, String> getQuestionParameterByQuestionID(int questionId)
	{
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONPARAMETER_BY_QUESTIONID);			
	   		pstm.setInt(1, questionId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			parameters.put(rs.getString(DBFieldNames.s_PARAMETER_NAME), 
	   					rs.getString(DBFieldNames.s_VALUE));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return parameters;
	}
	
	public HashMap<String, String> getQuestionParameterByPageIDQuestionID(int pageId, int questionId)
	{
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONPARAMETER_BY_QUESTIONID_PAGEID);			
	   		pstm.setInt(1, questionId);
	   		pstm.setInt(2, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			parameters.put(rs.getString(DBFieldNames.s_PARAMETER_NAME), 
	   					rs.getString(DBFieldNames.s_VALUE));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return parameters;
	}

	public JSONArray getQuestionParameterJSONByPageIDQuestionID(int pageId, int questionId)
	{
		JSONArray parameters = new JSONArray();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONPARAMETER_BY_QUESTIONID_PAGEID);			
	   		pstm.setInt(1, questionId);
	   		pstm.setInt(2, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			JSONObject parameter = new JSONObject();
	   			parameter.put("name", rs.getString(DBFieldNames.s_PARAMETER_NAME));
	   			parameter.put("value", rs.getString(DBFieldNames.s_VALUE));
	   			parameters.put(parameter);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return parameters;
	}
	
	//QuestionByPoll
	
	public HashMap<String, String> getQuestionParameterByPollID(int pollId)
	{
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONPARAMETERPOLL_BY_POLLID);			
	   		pstm.setInt(1, pollId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			parameters.put(rs.getString(DBFieldNames.s_PARAMETER_NAME), 
	   					rs.getString(DBFieldNames.s_VALUE));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return parameters;
	}
	
	public HashMap<String, String> getQuestionParameterPollByQuestionID(int questionId)
	{
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONPARAMETERPOLL_BY_QUESTIONID);			
	   		pstm.setInt(1, questionId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			parameters.put(rs.getString(DBFieldNames.s_PARAMETER_NAME), 
	   					rs.getString(DBFieldNames.s_VALUE));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return parameters;
	}
	
	public HashMap<String, String> getQuestionParameterPollByPollIDQuestionID(int pollId, int questionId)
	{
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONPARAMETERPOLL_BY_QUESTIONID_POLLID);			
	   		pstm.setInt(1, questionId);
	   		pstm.setInt(2, pollId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			parameters.put(rs.getString(DBFieldNames.s_PARAMETER_NAME), 
	   					rs.getString(DBFieldNames.s_VALUE));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return parameters;
	}
	
	public String getQuestionParameterByPageIDQuestionIDParameterName(int pageId, int questionId, String parameterName)
	{
		String value = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONPARAMETER_BY_QUESTIONID_PAGEID_PARAMETERNAME);			
	   		pstm.setInt(1, questionId);
	   		pstm.setInt(2, pageId);
	   		pstm.setString(3, parameterName);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			value = rs.getString(DBFieldNames.s_VALUE);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return value;
	}
	
	public String getQuestionParameterPollByPollIDQuestionIDParameterName(int pollId, int questionId, String parameterName)
	{
		String value = "";
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONPARAMETERPOLL_BY_QUESTIONID_POLLID_PARAMETERNAME);			
	   		pstm.setInt(1, questionId);
	   		pstm.setInt(2, pollId);
	   		pstm.setString(3, parameterName);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			value = rs.getString(DBFieldNames.s_VALUE);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return value;
	}
	
	/*
	 * Inserts
	 */
	
	//QuestionByPage
	
	public boolean insertQuestionParameter(int pageId, int questionId, String parameterName, String value) {
		//System.out.println("insertParameter");
		boolean inserted = false;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
	    	System.out.println("insertQuestionParameter(pageId: "+pageId+", questionId: "+questionId+", parameterName: "+parameterName+", value: "+value);
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_PARAMETER_FOR_QUESTION);
		   pstm.setInt(1, questionId);
		   pstm.setInt(2, pageId); 
		   pstm.setString(3, parameterName); 
		   pstm.setString(4, value);
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   inserted = true;
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return inserted;
	}
	
	//QuestionByPoll
	
	public boolean insertQuestionParameterPoll(int pollId, int questionId, String parameterName, String value) {
		//System.out.println("insertParameter");
		boolean inserted = false;
			
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_PARAMETER_FOR_QUESTION_POLL);
		   pstm.setInt(1, questionId);
		   pstm.setInt(2, pollId); 
		   pstm.setString(3, parameterName); 
		   pstm.setString(4, value);
			   
		   boolean notInserted = pstm.execute();
			   
		   if(!notInserted)
		   {
			   inserted = true;
		   }
			  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return inserted;
	}
	
	
	/*
	 * Update
	 */
	
	public void updateParameter(int pageId, int questionId, String parameterName, String value) {
		//System.out.println("updateParameter");
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
			pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_PARAMETERFORQUESTION);
			pstm.setString(1, value);
			pstm.setInt(2, pageId);
			pstm.setInt(3, questionId);
			pstm.setString(4, parameterName);
		   		
			int numUpdated = pstm.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		   
	}
	
	public void updateParameterPoll(int pollId, int questionId, String parameterName, String value) {
		//System.out.println("updateParameter");
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
			pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_PARAMETERFORQUESTIONPOLL);
			pstm.setString(1, value);
			pstm.setInt(2, pollId);
			pstm.setInt(3, questionId);
			pstm.setString(4, parameterName);
		   		
			int numUpdated = pstm.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		   
	}
	
	public void updateQuestionParameters(int questionId, int pageId, HashMap<String, String> parameters) {
		//System.out.println("updateState");
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		
		QuestionParameterDB questionParameterDB = new QuestionParameterDB();		
		   
		try{
			
			
			Iterator<String> iter = parameters.keySet().iterator();
			while(iter.hasNext()){
				String parameterName = iter.next();
				String value = parameters.get(parameterName);
				
				if(questionParameterDB.getQuestionParameterByPageIDQuestionIDParameterName(pageId, questionId, parameterName) != null){
					pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_PARAMETERFORQUESTION);
					System.out.println(DBSQLQueries.s_UPDATE_PARAMETERFORQUESTION+", 1:"+value+", 2:"+pageId+", 3:"+questionId+", 4:"+parameterName);
					pstm.setString(1, value);
			   		pstm.setInt(2, pageId);
					pstm.setInt(3, questionId);
					pstm.setString(4, parameterName);
					pstm.executeUpdate();
				}
				else{
					pstm = con.prepareStatement(DBSQLQueries.s_INSERT_PARAMETER_FOR_QUESTION);
					System.out.println(DBSQLQueries.s_INSERT_PARAMETER_FOR_QUESTION+", 1:"+questionId+", 2:"+pageId+", 3:"+parameterName+", 4:"+value);
					pstm.setInt(1, questionId);
			   		pstm.setInt(2, pageId);
					pstm.setString(3, parameterName);
					pstm.setString(4, value);
					pstm.executeUpdate();
				}
				
			}
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		   
	}
	
	/*
	 * Delete
	 */

	public void removeParameter(int pageId, int questionId, String parameterName) {
		//System.out.println("removeParameter");
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
			pstm = con.prepareStatement(DBSQLQueries.s_DELETE_PARAMETERFORQUESTION_BY_PAGE_QUESTION_ID_PARAMETER_NAME);
		   	pstm.setInt(1, pageId);
		   	pstm.setInt(2, questionId);
		   	pstm.setString(3, parameterName);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}
	
	public void removeParameterPoll(int pollId, int questionId, String parameterName) {
		//System.out.println("removeParameter");
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
			pstm = con.prepareStatement(DBSQLQueries.s_DELETE_PARAMETERFORQUESTIONPOLL_BY_POLL_QUESTION_ID_PARAMETER_NAME);
		   	pstm.setInt(1, pollId);
		   	pstm.setInt(2, questionId);
		   	pstm.setString(3, parameterName);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}
	
	public void removeQuestionParameter(int questionId, int pageId, String parameter) {
		//System.out.println("removeUserOptionValues");
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{			
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_PARAMETERFORQUESTION_BY_PAGE_QUESTION_ID_PARAMETER_NAME);
		   	pstm.setInt(1, pageId);
		   	pstm.setInt(2, questionId);
		   	pstm.setString(3, parameter);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}
	
	public void removeQuestionParameters(int questionId, int pageId) {
		//System.out.println("removeUserOptionValues");
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{			
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_PARAMETERFORQUESTION_BY_PAGE_QUESTION_ID);
		   	pstm.setInt(1, pageId);
		   	pstm.setInt(2, questionId);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}
	

}
