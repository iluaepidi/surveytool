package ilu.surveytool.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Project;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Questionnaire;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class QuestionDB {

	public QuestionDB() {
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
	
	public List<Question> getQuestionsBySurveyId(int surveyId, String lang)
	{
		List<Question> questions = new ArrayList<Question>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_BY_SURVEYID);			
	   		pstm.setInt(1, surveyId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			String mainVersion = rs.getString(DBFieldNames.s_LANGUAGE_ISONAME);
	   			if(lang == null || lang.isEmpty()) lang = mainVersion;
	   			ContentDB contentDB = new ContentDB();
	   			HashMap<String, Content> contents = contentDB.getContentByIdAndLanguage(contentId, lang);
	   			questions.add(new Question(rs.getInt(DBFieldNames.s_QUESTION_ID), 
	   					rs.getString(DBFieldNames.s_QUESTION_TAG), 
	   					null, 
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_NAME), 
	   					contents, 
	   					rs.getString(DBFieldNames.s_CATEGORY_NAME), 
	   					rs.getBoolean(DBFieldNames.s_QUESTION_MANDATORY), 
	   					mainVersion, 
	   					false,
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_TEMPLATE_FILE),
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_FORM_FILE)));
	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questions;
	}
	
	public String getQuestionTypeTemplateFileByName(String questionType)
	{
		String response = "";
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONTYPE_TEMPLATE_FILE_BY_ID);			
	   		pstm.setString(1, questionType);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			response = rs.getString(DBFieldNames.s_QUESTIONTYPE_TEMPLATE_FILE);
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}
	
	/**
	 * Inserts 
	 */
	
	public int insertQuestion(Question question, int contentId) {
		//System.out.println("inserUser");
		int questionId = 0;
		//int contentId = this.insertContentIndex();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_QUESTION, Statement.RETURN_GENERATED_KEYS);
		   pstm.setString(1, question.getTag());
		   pstm.setString(2, question.getQuestionType()); 
		   pstm.setInt(3, contentId); 
		   pstm.setString(4, question.getCategory()); 
		   pstm.setString(5, question.getMainVersion());
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   questionId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return questionId;
	}

	public boolean insertQuestionByPage(int questionId, int pageId, boolean mandatory, int numQuestion) {
		//System.out.println("inserUser");
		boolean inserted = false;
		//int contentId = this.insertContentIndex();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_QUESTION_BY_PAGE, Statement.RETURN_GENERATED_KEYS);
		   pstm.setInt(1, pageId);
		   pstm.setInt(2, questionId);
		   pstm.setInt(3, numQuestion);
		   pstm.setBoolean(4, mandatory);
		   
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
	
	

}
