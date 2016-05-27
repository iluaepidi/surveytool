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

import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Project;
import ilu.surveytool.databasemanager.DataObject.Questionnaire;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class SurveyDB {

	public SurveyDB() {
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
	
	public List<Questionnaire> getQuestionnairesByAuthor(int author)
	{
		List<Questionnaire> response = new ArrayList<Questionnaire>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONNAIRE);			
	   		pstm.setInt(1, author);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			Questionnaire questionnaire = new Questionnaire();
	   			questionnaire.setIdQuestionnaire(rs.getInt(DBFieldNames.s_QUESTIONNAIREID));
	   			questionnaire.setState(rs.getString(DBFieldNames.s_STATE));
	   			questionnaire.setStartDate(rs.getTimestamp(DBFieldNames.s_START_DATE));
	   			questionnaire.setDeadLineDate(rs.getTimestamp(DBFieldNames.s_DEADLINE_DATE));
	   			questionnaire.setIdContent(rs.getInt(DBFieldNames.s_CONTENTID));
	   			questionnaire.setCreationDate(rs.getTimestamp(DBFieldNames.s_CREATION_DATE));
	   			questionnaire.setProject(rs.getString(DBFieldNames.s_PROJECT_NAME));
	   			questionnaire.setPublicId(rs.getString(DBFieldNames.s_PUBLIC_ID));
	   			questionnaire.setAuthorId(rs.getInt(DBFieldNames.s_AUTHOR));
	   			response.add(questionnaire);
	   		}
	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}
	
	public Survey getQuestionnairesById(int surveyId,String lang)
	{
		Survey response = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm=null,pstm2 = null;
		ResultSet rs=null,rs2 = null;
		   
		try{
			
			if(lang==null){
				pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONNAIRE_BY_ID);			
				pstm.setInt(1, surveyId);
			}else{
				pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONNAIRE_BY_ID_AND_LANG);			
				pstm.setInt(1, surveyId);
				pstm.setString(2, lang);
			}
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next()){
	   			response = new Survey();
	   			response.setProject(rs.getString(DBFieldNames.s_PROJECT_NAME));
	   			response.setSurveyId(surveyId);
	   			response.setPublicId(rs.getString(DBFieldNames.s_PUBLIC_ID));
	   			response.setAuthor(rs.getInt(DBFieldNames.s_AUTHOR));
	   			response.setDefaultLanguage(String.valueOf(rs.getInt(DBFieldNames.s_DEFAULT_LANGUAGE)));
	   			
	   			String contenttypeName = rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME);
	   			String isoname = rs.getString(DBFieldNames.s_LANGUAGE_ISONAME);
	   			String text = rs.getString(DBFieldNames.s_CONTENT_TEXT);
	   			response.getContents().put(contenttypeName, new Content(0, isoname, contenttypeName, text));
	   			
		   		while(rs.next())
		   		{
		   			contenttypeName = rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME);
		   			isoname = rs.getString(DBFieldNames.s_LANGUAGE_ISONAME);
		   			text = rs.getString(DBFieldNames.s_CONTENT_TEXT);
		   			response.getContents().put(contenttypeName, new Content(0, isoname, contenttypeName, text));		   			
		   		}
	   		}else{
	   			pstm2 = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONNAIRE_BY_ID);			
	   			pstm2.setInt(1, surveyId);
	   			rs2 = pstm2.executeQuery();
				if(rs2.next()){
		   			response = new Survey();
		   			response.setProject("");
		   			response.setSurveyId(surveyId);
		   			response.setPublicId(rs2.getString(DBFieldNames.s_PUBLIC_ID));
		   			response.setAuthor(rs2.getInt(DBFieldNames.s_AUTHOR));
		   			response.setDefaultLanguage(String.valueOf(rs2.getInt(DBFieldNames.s_DEFAULT_LANGUAGE)));
		   			
		   			String contenttypeName = rs2.getString(DBFieldNames.s_CONTENT_TYPE_NAME);
		   			String isoname = rs2.getString(DBFieldNames.s_LANGUAGE_ISONAME);
		   			String text = "";
		   			response.getContents().put(contenttypeName, new Content(0, isoname, contenttypeName, text));
		   			
			   		while(rs2.next())
			   		{
			   			contenttypeName = rs2.getString(DBFieldNames.s_CONTENT_TYPE_NAME);
			   			isoname = rs2.getString(DBFieldNames.s_LANGUAGE_ISONAME);
			   			text = rs2.getString(DBFieldNames.s_CONTENT_TEXT);
			   			response.getContents().put(contenttypeName, new Content(0, isoname, contenttypeName, text));		   			
			   		}
				
				}
			}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
			this._closeConnections(con, pstm2, rs2);
		}
		
		
		if(response!=null){
			response.setDefaultLanguage(this.getIsoLanguage(Integer.parseInt(response.getDefaultLanguage())));
		}
		return response;
	}
	

	public String getQuestionnairesPublicId(int surveyId)
	{
		String response = "";
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONNAIRE_PUBLICID);			
	   		pstm.setInt(1, surveyId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			response = rs.getString(DBFieldNames.s_PUBLIC_ID);
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}
	
	
	public Survey getQuestionnairesByPublicId(String publicId, String lang)
	{
		Survey response = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONNAIRE_BY_PUBLIC_ID);			
	   		pstm.setString(1, publicId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			response = new Survey();
	   			response.setProject(rs.getString(DBFieldNames.s_PROJECT_NAME));
	   			response.setSurveyId(rs.getInt(DBFieldNames.s_QUESTIONNAIREID));
	   			
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			
	   			ContentDB contentDB = new ContentDB();
		   		response.setContents(contentDB.getContentByIdAndLanguage(contentId, lang,null));
		   		
		   		SectionDB sectionDB = new SectionDB();
				response.setSections(sectionDB.getSectionsBySurveyId(response.getSurveyId(), lang,null));
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}
	
	public List<SurveyTableInfo> getSurveysTableInfoByAuthor(int author, String language)
	{
		List<SurveyTableInfo> response = new ArrayList<SurveyTableInfo>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONNAIRE_TABLE_INFO);		
		   	pstm.setString(1, DBConstants.s_VALUE_STATE_FINISHED);
	   		pstm.setInt(2, author);
	   		//pstm.setString(3, language);
	   		pstm.setString(3, DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			SurveyTableInfo survey = new SurveyTableInfo();
	   			survey.setSurveyId(rs.getInt(DBFieldNames.s_QUESTIONNAIREID));
	   			survey.setDeadLineDate(rs.getTimestamp(DBFieldNames.s_DEADLINE_DATE));
	   			survey.setTitle(rs.getString(DBFieldNames.s_GENERICO_TITLE));
	   			survey.setNumUsers(rs.getInt(DBFieldNames.s_GENERICO_ALL_USERS));
	   			survey.setNumUsersFinished(rs.getInt(DBFieldNames.s_GENERICO_USERS_FINISHED));
	   			response.add(survey);
	   		}	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}

	public List<SurveyTableInfo> getSurveysTableInfoAnonimousByAuthor(int author, String language)
	{
		List<SurveyTableInfo> response = new ArrayList<SurveyTableInfo>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONNAIRE_TABLE_INFO_ANONIMOUS);	
	   		pstm.setInt(1, author);
	   		pstm.setString(2, language);
	   		pstm.setString(3, DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			SurveyTableInfo survey = new SurveyTableInfo();
	   			survey.setSurveyId(rs.getInt(DBFieldNames.s_QUESTIONNAIREID));
	   			survey.setDeadLineDate(rs.getTimestamp(DBFieldNames.s_DEADLINE_DATE));
	   			survey.setTitle(rs.getString(DBFieldNames.s_GENERICO_TITLE));
	   			survey.setNumUsers(rs.getInt(DBFieldNames.s_GENERICO_ALL_USERS));
	   			response.add(survey);
	   		}	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}

	public int getQuestionnairesContentId(int surveyId)
	{
		int contentId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONNAIRE_CONTENTID);			
	   		pstm.setInt(1, surveyId);
	   		
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
	
	public int getQuestionnairesProjectId(int surveyId)
	{
		int projectId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONNAIRE_PROJECTID);			
	   		pstm.setInt(1, surveyId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			projectId = rs.getInt(DBFieldNames.s_PROJECTID);
	   		}
	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return projectId;
	}
	
	public List<Integer> getQuestionnairesIdByProjectId(int projectId)
	{
		List<Integer> projectsId = new ArrayList<Integer>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONNAIRE_PROJECTID);			
	   		pstm.setInt(1, projectId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			projectsId.add(rs.getInt(DBFieldNames.s_PROJECTID));
	   		}
	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return projectsId;
	}
	
	public Project getProjectByName(String name)
	{
		Project response = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_PROJECT_BY_NAME);		
		   	pstm.setString(1, name);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			response = new Project();
	   			response.setProjectId(rs.getInt(DBFieldNames.s_PROJECTID));
	   			response.setName(rs.getString(DBFieldNames.s_PROJECT_NAME));
	   			response.setCreateDate(rs.getTimestamp(DBFieldNames.s_CREATION_DATE));
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
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONNAIRE_BY_PUBLIC_ID);		
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
	
	public int insertProject(String title) {
		//System.out.println("inserUser");
		int projectId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_PROJECT, Statement.RETURN_GENERATED_KEYS);
		   pstm.setString(1, title); 
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   projectId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return projectId;
	}
	
	public int insertSurvey(int author, int project, int contentId, String isoNameDefaultLanguage) {
		//System.out.println("inserUser");
		int surveyId = 0;
		int intLanguageDefault = this.getIdLanguage(isoNameDefaultLanguage);
		
		
		//int contentId = this.insertContentIndex();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_QUESTIONNAIRE, Statement.RETURN_GENERATED_KEYS);
		   pstm.setString(1, DBConstants.s_VALUE_SURVEY_STATE_STOP);
		   pstm.setInt(2, contentId); 
		   pstm.setInt(3, project); 
		   pstm.setString(4, this._generatePublicId()); 
		   pstm.setInt(5, author);  
		   pstm.setInt(6, intLanguageDefault);  
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   surveyId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return surveyId;
	}

	public int insertForma(int surveyId) {
		//System.out.println("inserUser");
		int formaId = 0;
		//int contentId = this.insertContentIndex();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_FORMA, Statement.RETURN_GENERATED_KEYS);
		   pstm.setInt(1, surveyId);  
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   formaId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return formaId;
	}
	
	/*
	 * update
	 */
	
	public boolean updateSurveyProject(int projectId, int surveyId) {
		//System.out.println("updateState");
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_QUESTIONNAIRE_PROJECT);
			pstm.setInt(1, projectId);
			pstm.setInt(2, surveyId);
		   		
			int numUpdated = pstm.executeUpdate();
			
			if(numUpdated > 0) updated = true;
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		 
		return updated;
	}

	public boolean updateProjectName(int projectId, String projectName) {
		boolean updated = false;
		//System.out.println("updateState");
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_PROJECT_NAME);
			pstm.setString(1, projectName);
			pstm.setInt(2, projectId);
		   		
			int numUpdated = pstm.executeUpdate();
			
			if(numUpdated > 0) updated = true;
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		   
		return updated;
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
	
	public int getIdLanguage(String isoLanguage)
	{
		int idLanguage = 1;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_GET_IDLANGUEGE_FROM_ISONAME);			
	   		pstm.setString(1, isoLanguage);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			idLanguage = rs.getInt("idLanguage");
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return idLanguage;
	}
	
	public String getIsoLanguage(int idLanguage)
	{
		String isoName = "";
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_GET_ISOLANGUEGE_FROM_IDLANGUAGE);			
	   		pstm.setInt(1, idLanguage);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			isoName = rs.getString("isoName");
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return isoName;
	}
	
	
	

}
