package ilu.surveytool.databasemanager;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ilu.surveytool.databasemanager.DataObject.Poll;
import ilu.surveytool.databasemanager.DataObject.PollResultResume;
import ilu.surveytool.databasemanager.DataObject.PollTableInfo;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Survey;
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

	public PollTableInfo getPollsTableInfoById(int pollId, String language)
	{
		PollTableInfo poll = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_POLL_TABLE_INFO_BY_ID);	
	   		pstm.setInt(1, pollId);
	   		pstm.setString(2, language);
	   		pstm.setString(3, DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			poll = new PollTableInfo();
	   			poll.setPollId(rs.getInt(DBFieldNames.s_POLL_ID));
	   			poll.setDeadLineDate(rs.getTimestamp(DBFieldNames.s_DEADLINE_DATE));
	   			poll.setTitle(rs.getString(DBFieldNames.s_GENERICO_TITLE));
	   			poll.setPublicUrl(rs.getString(DBFieldNames.s_PUBLIC_ID));
	   			
	   		}	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return poll;
	}

	public Poll getPollByPublicId(String publicId, String lang)
	{
		Poll response = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_POLL_BY_PUBLIC_ID);			
	   		pstm.setString(1, publicId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			response = new Poll();
	   			response.setProject(rs.getString(DBFieldNames.s_PROJECT_NAME));
	   			response.setPollId(rs.getInt(DBFieldNames.s_POLL_ID));
	   			response.setPublicUrl(publicId);
	   			response.setAuthor(rs.getInt(DBFieldNames.s_AUTHOR));
	   			try{
	   				response.setSurveyId(rs.getInt(DBFieldNames.s_QUESTIONNAIREID));
	   			}catch(NumberFormatException e)
	   			{
	   				response.setSurveyId(0);
	   			}
	   			response.setDeadLineDate(rs.getTimestamp(DBFieldNames.s_DEADLINE_DATE));
	   			response.setCallUrl(rs.getString(DBFieldNames.s_POLL_CALL_URL));
	   			
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			
	   			ContentDB contentDB = new ContentDB();
		   		response.setContents(contentDB.getContentByIdAndLanguage(contentId, lang,null));
		   		
		   		QuestionDB questionDB = new QuestionDB();
		   		List<Question> questions = questionDB.getQuestionsByPollId(response.getPollId(), lang);
		   		if(!questions.isEmpty()) response.setQuestion(questions.get(0));
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}

	public Poll getPollById(int pollId, String lang)
	{
		Poll response = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_POLL_BY_ID);			
	   		pstm.setInt(1, pollId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			response = new Poll();
	   			response.setProject(rs.getString(DBFieldNames.s_PROJECT_NAME));
	   			response.setPollId(pollId);
	   			response.setPublicUrl(rs.getString(DBFieldNames.s_PUBLIC_ID));
	   			response.setAuthor(rs.getInt(DBFieldNames.s_AUTHOR));
	   			try{
	   				response.setSurveyId(rs.getInt(DBFieldNames.s_QUESTIONNAIREID));
	   			}catch(NumberFormatException e)
	   			{
	   				response.setSurveyId(0);
	   			}
	   			response.setDeadLineDate(rs.getTimestamp(DBFieldNames.s_DEADLINE_DATE));
	   			response.setCallUrl(rs.getString(DBFieldNames.s_POLL_CALL_URL));
	   			
	   			if(lang != null && !lang.isEmpty())
	   			{
		   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
		   			
		   			ContentDB contentDB = new ContentDB();
			   		response.setContents(contentDB.getContentByIdAndLanguage(contentId, lang,null));
	   			}
		   		
		   		QuestionDB questionDB = new QuestionDB();
		   		List<Question> questions = questionDB.getQuestionsByPollId(response.getPollId(), lang);
		   		if(!questions.isEmpty()) response.setQuestion(questions.get(0));
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}
	
	public int getPollContentId(int pollId)
	{
		int contentId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_POLL_CONTENTID);		
		   	pstm.setInt(1, pollId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   		}	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return contentId;
	}

	public List<PollResultResume> getPollResponsesResume(int pollId, String language)
	{
		List<PollResultResume> response = new ArrayList<PollResultResume>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_POLL_RESPONSES_RESUME);	
	   		pstm.setInt(1, pollId);
	   		pstm.setString(2, language);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			PollResultResume result = new PollResultResume(rs.getInt(DBFieldNames.s_OPTIONID), 
	   					rs.getString(DBFieldNames.s_CONTENT_TEXT), 
	   					rs.getInt(DBFieldNames.s_RESPONSES_NUMBER));
	   			
	   			response.add(result);
	   		}	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}

	public boolean existPublicId(String publicId)
	{
		boolean result = false;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_POLL_BY_PUBLIC_ID);		
		   	pstm.setString(1, publicId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			result = true;
	   		}	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return result;
	}

	/**
	 * Inserts 
	 */
	
	public int insertPoll(Poll poll, int contentId, int projectId) {
		//System.out.println("inserUser");
		int pollId = 0;
		//int contentId = this.insertContentIndex();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_POLL, Statement.RETURN_GENERATED_KEYS);
		   pstm.setString(1, this._generatePublicId());
		   pstm.setInt(2, poll.getAuthor());
		   if(poll.getSurveyId() > 0)
		   {
			   pstm.setInt(3, poll.getSurveyId());
		   }
		   else
		   {
			   pstm.setNull(3, Types.INTEGER);
		   }
		   pstm.setInt(4, contentId); 
		   pstm.setInt(5, projectId); 
		   pstm.setString(6, poll.getCallUrl());
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   pollId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return pollId;
	}
	
	public boolean insertQuestionByPoll(int questionId, int pollId, int index) {
		//System.out.println("inserUser");
		boolean inserted = false;
		//int contentId = this.insertContentIndex();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_QUESTION_BY_POLL);
		   pstm.setInt(1, pollId);
		   pstm.setInt(2, questionId);
		   pstm.setInt(3, index);
		   
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
	
	private String _generatePublicId()
	{
		String publicId = "";
		
		SecureRandom random = new SecureRandom();
		
		do{
			publicId = new BigInteger(50, random).toString(32);
		}while(this.existPublicId(publicId));
						
		return publicId;
	}


}
