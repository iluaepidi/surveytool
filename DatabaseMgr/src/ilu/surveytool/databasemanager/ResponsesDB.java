package ilu.surveytool.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
	
	public HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>> getAnonimousResponseCompleteBySurveyId(int surveyId)
	{
		HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>> responses = new HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>>();
		
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
	   			Timestamp createDate = rs.getTimestamp(DBFieldNames.s_ANONYMOUS_USER_DATE);
	   			Timestamp timestamp = rs.getTimestamp(DBFieldNames.s_RESPONSE_TIMESTAMP);
	   			
	   			if(!responses.containsKey(anonymousUserId))
	   			{
	   				responses.put(anonymousUserId, new HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>());
	   				responses.get(anonymousUserId).put(createDate, new HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>());
	   			}
	   			
	   			if(!responses.get(anonymousUserId).get(createDate).containsKey(questionId))
	   			{
	   				responses.get(anonymousUserId).get(createDate).put(questionId, new HashMap<Integer, HashMap<String,Timestamp>>());
	   			}
	   			
	   			if(!responses.get(anonymousUserId).get(createDate).get(questionId).containsKey(optionsGroupId))
	   			{
	   				responses.get(anonymousUserId).get(createDate).get(questionId).put(optionsGroupId, new HashMap<String,Timestamp>());
	   			}
	   			
	   			responses.get(anonymousUserId).get(createDate).get(questionId).get(optionsGroupId).put(rs.getString(DBFieldNames.s_VALUE), timestamp);
	   			
	   		}
	   			   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return responses;
	}
	
	public HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>> getAnonimousResponseCompleteWithoptionIdBySurveyId(int surveyId)
	{
		HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>> responses = new HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_ANONYMOUS_RESPONSE_WITH_OPTIONID_BY_SURVEY_ID);			
	   		pstm.setInt(1, surveyId);
	   		//System.out.println(DBSQLQueries.s_SELECT_ANONYMOUS_RESPONSE_WITH_OPTIONID_BY_SURVEY_ID+": "+surveyId);
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{	   			
	   			int anonymousUserId = rs.getInt(DBFieldNames.s_ANONYMOUS_USER_ID);
	   			int questionId = rs.getInt(DBFieldNames.s_QUESTION_ID);
	   			int optionsGroupId = rs.getInt(DBFieldNames.s_OPTIONSGROUPID);
	   			Timestamp createDate = rs.getTimestamp(DBFieldNames.s_ANONYMOUS_USER_DATE);
	   			Timestamp timestamp = rs.getTimestamp(DBFieldNames.s_RESPONSE_TIMESTAMP);
	   			String option = rs.getString(DBFieldNames.s_VALUE);
	   			
	   			if(!responses.containsKey(anonymousUserId))
	   			{
	   				responses.put(anonymousUserId, new HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>());
	   				responses.get(anonymousUserId).put(createDate, new HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>());
	   			}
	   			
	   			if(!responses.get(anonymousUserId).get(createDate).containsKey(questionId))
	   			{
	   				responses.get(anonymousUserId).get(createDate).put(questionId, new HashMap<Integer, HashMap<String,Timestamp>>());
	   			}
	   			
	   			if(!responses.get(anonymousUserId).get(createDate).get(questionId).containsKey(optionsGroupId))
	   			{
	   				responses.get(anonymousUserId).get(createDate).get(questionId).put(optionsGroupId, new HashMap<String,Timestamp>());
	   			}
	   			
	   			responses.get(anonymousUserId).get(createDate).get(questionId).get(optionsGroupId).put(rs.getString(DBFieldNames.s_VALUE), timestamp);
	   			//System.out.println("[ResponsesDB/getAnonimousResponseCompleteWithoptionIdBySurveyId] anonymousUserId:"+anonymousUserId+", questionId:"+questionId+", optionsGroupId:"+optionsGroupId+", option:"+option);
	   		}
	   			   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return responses;
	}
	
	public int[] getNumQuestionsQuestionnaires(int surveyId){
		int[] count = new int[2];
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_NUMQUESTIONS_QUESTIONNAIRE_SURVEYID);			
	   		pstm.setInt(1, surveyId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			if(rs.getInt(DBFieldNames.s_QUESTION_MANDATORY)==1)
	   				count[1] = rs.getInt(DBFieldNames.s_COUNT);
	   			else
	   				count[0] = rs.getInt(DBFieldNames.s_COUNT);
	   		}
	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return count;
	}
	
	public HashMap<Integer,Boolean> getQuestionsID_Mandatory_BySurveyId(int surveyId)
	{
		HashMap<Integer,Boolean> questions = new HashMap<Integer,Boolean>();
		//System.out.println("Get questions by survey Id="+surveyId);
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_BY_SURVEYID);			
	   		pstm.setInt(1, surveyId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next()){
	   			questions.put(rs.getInt(DBFieldNames.s_QUESTION_ID), rs.getBoolean(DBFieldNames.s_QUESTION_MANDATORY));
	   			//System.out.println(rs.getInt(DBFieldNames.s_QUESTION_ID)+"-->"+rs.getBoolean(DBFieldNames.s_QUESTION_MANDATORY));
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questions;
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
	
	public List<String> getValuesByQuestionIdLang(int questionId, String lang)
	{
		List<String> contents = new ArrayList<String>();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
			pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_OPTIONS_QUESTIONID_LANGUAGE);			
		   	pstm.setInt(1, questionId);
		   	pstm.setString(2, lang);
		   		
		   	rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			if(contents.isEmpty())
	   				contents.add(rs.getString(DBFieldNames.s_CONTENT_QUESTION));
	   			
	   			contents.add(rs.getString(DBFieldNames.s_VALUE));
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return contents;
	}
		
	public List<Integer> getAnsweredPages(int anonimousUserId)
	{
		List<Integer> numPages = new ArrayList<Integer>();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
			pstm = con.prepareStatement(DBSQLQueries.s_SELECT_ANSWERED_PAGES);			
		   	pstm.setInt(1, anonimousUserId);
		   		
		   	rs = pstm.executeQuery();
		   	int previousNum = -1;
	   		while(rs.next())
	   		{
	   			if (rs.getInt(DBFieldNames.s_NUM_PAGE)!=previousNum)
	   				numPages.add(rs.getInt(DBFieldNames.s_NUM_PAGE));
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return numPages;
	}
	
	public List<Integer> getVisitedPages(int anonimousUserId)
	{
		List<Integer> numPages = new ArrayList<Integer>();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
			pstm = con.prepareStatement(DBSQLQueries.s_SELECT_VISITED_PAGES);			
		   	pstm.setInt(1, anonimousUserId);
		   		
		   	rs = pstm.executeQuery();
		   	int previousNum = -1;
	   		while(rs.next())
	   		{
	   			if (rs.getInt(DBFieldNames.s_NUM_PAGE)!=previousNum)
	   				numPages.add(rs.getInt(DBFieldNames.s_NUM_PAGE));
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return numPages;
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
	
	
	public int insertContentIndex() {
		//System.out.println("inserUser");
		int contentId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_CONTENT_INDEX, Statement.RETURN_GENERATED_KEYS);
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   contentId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return contentId;
	}
	
	public void insertNewPage(int numPage, int idAnonimousUser, int sectionId){
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_RESPONSE_PAGES_ANONIMOUS);
		   PageDB pageDB = new PageDB();
		   int idPage = pageDB.getPageIdBySectionIdNumPage(sectionId, numPage);
		   System.out.println("idPage: "+ idPage +", sectionId: "+ sectionId +", numPage: "+ numPage);
		   pstm.setInt(1, idPage);
		   pstm.setInt(2, idAnonimousUser);
		   
		   
		   pstm.execute();
			  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
	}
}
