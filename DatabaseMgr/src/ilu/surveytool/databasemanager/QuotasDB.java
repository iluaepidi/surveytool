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

import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Project;
import ilu.surveytool.databasemanager.DataObject.Questionnaire;
import ilu.surveytool.databasemanager.DataObject.Quota;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class QuotasDB {

	public QuotasDB() {
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
	
	public boolean getQuotasByQuestionnarie(int idQuestionnaire, int idQuestion, int idOptionGroup, int value)
	{
		List<Questionnaire> response = new ArrayList<Questionnaire>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_CHECK_EXISTS_QUOTAS);			
		    pstm.setInt(1, idQuestionnaire); 
			pstm.setInt(2, idQuestion); 
			pstm.setInt(3, idOptionGroup); 
			pstm.setInt(4, value); 
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			return true;
	   		}
	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return false;
	}
	
	public List<Quota> getListQuotasByQuestionnarie(int idQuestionnaire)
	{
		List<Quota> response = new ArrayList<Quota>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
			//idQuestion, idOptionsGroup, value, maxResponses, minResponses
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUOTAS_BY_IDQUESTIONNARIE);			
		    pstm.setInt(1, idQuestionnaire);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			Quota quota = new Quota();
	   			quota.setIdQuestion(rs.getInt(DBFieldNames.s_QUESTION_ID));
	   			quota.setIdOptionsGroup(rs.getInt(DBFieldNames.s_OPTIONSGROUPID));
	   			quota.setIdOption(rs.getInt(DBFieldNames.s_VALUE));
	   			quota.setMaxResponses(rs.getInt(DBFieldNames.s_MAX_RESPONSES));
	   			quota.setMinResponses(rs.getInt(DBFieldNames.s_MIN_RESPONSES));
	   			response.add(quota);
	   		}	   	
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}
	
	public int getQuotasMax(int value)
	{
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUOTA_MAX);			
		    pstm.setInt(1, value); 
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			return rs.getInt(1);
	   		}
	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return 0;
	}
	
	public int getQuotasMin(int value)
	{
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUOTA_MIN);			
		    pstm.setInt(1, value); 
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			return rs.getInt(1);
	   		}
	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return 0;
	}
	
	
	public String getNameOptionForIDOption(String lang, int idoption)
	{
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_OPTION_NAME_IDOPTION);			
		    pstm.setString(1, lang); 
		    pstm.setInt(2, idoption); 
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			return rs.getString(1);
	   		}
	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return null;
	}
	
	public String getNameQuestionForIDQuestion(String lang, int idquestion)
	{
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_NAME_IDOPTION);			
		    pstm.setString(1, lang); 
		    pstm.setInt(2, idquestion); 
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			return rs.getString(1);
	   		}
	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return null;
	}
	
	public int getCountResponses(int idQuestion, int idOptionsGroup, int idOption)
	{
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_NUMBER_RESPONSES_QUOTA);			
		    pstm.setInt(1, idQuestion); 
		    pstm.setInt(2, idOptionsGroup); 
		    pstm.setInt(3, idOption); 
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			return rs.getInt(1);
	   		}
	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return 0;
	}
	
	public int getCountSurveyCompletesAnonymous(int idSurvey)
	{
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_NUMBER_RESPONSES_SURVEY);			
		    pstm.setInt(1, idSurvey); 
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			return rs.getInt(1);
	   		}
	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return 0;
	}
	
	public boolean isCompleteGeneralQuota(int surveyId)
	{
		boolean response = false;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_IS_COMPLETE_GENERAL_QUOTA);		
		   	pstm.setInt(1, surveyId);
		   	pstm.setInt(2, surveyId);
		   	pstm.setInt(3, surveyId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			Object objResp = rs.getBoolean(DBFieldNames.s_IS_COMPLETE_GENERAL_QUOTA);
	   			if(objResp != null) response =((Boolean) objResp).booleanValue();
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
	
	public int insertQuota(int idQuestionnaire, int idQuestion, int idOptionGroup, int value, int maxResponses, int minResponses) {
		//System.out.println("inserUser");
		int quotaId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_QUOTA, Statement.RETURN_GENERATED_KEYS);
		   pstm.setInt(1, idQuestionnaire); 
		   pstm.setInt(2, idQuestion); 
		   pstm.setInt(3, idOptionGroup); 
		   pstm.setInt(4, value); 
		   pstm.setInt(5, maxResponses); 
		   pstm.setInt(6, minResponses); 
		   
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   quotaId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return quotaId;
	}
	
	
	/*
	 * update
	 */
	
	public boolean updateQuota(int idQuestionnaire, int idQuestion, int idOptionGroup, int value, int maxResponses, int minResponses) {
		//System.out.println("updateState");
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_QUOTAS); 
			pstm.setInt(1, maxResponses); 
			pstm.setInt(2, minResponses);
		    pstm.setInt(3, idQuestionnaire); 
			pstm.setInt(4, idQuestion); 
			pstm.setInt(5, idOptionGroup); 
			pstm.setInt(6, value); 
		   		
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
	
	
	public boolean updateObjetive(int idQuestionnaire, String objetive) {
		//System.out.println("updateState");
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_OBJETIVE_SURVEY); 
		   	if(objetive==null || objetive.equals("")){
		   		pstm.setNull(1, Types.INTEGER);
		   	}else{
		   		pstm.setInt(1, Integer.parseInt(objetive)); 
		   	}
			
			pstm.setInt(2, idQuestionnaire);
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
	
	//idQuestionnaire= ? AND idQuestion=? AND idOptionsGroup=? AND value=?
	public void removeQuota(int idQuestionnaire, int idQuestion, int idOptionGroup, int value) {
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_QUOTA_OPTION);
		   	pstm.setInt(1, idQuestionnaire);
		   	pstm.setInt(2, idQuestion);
		   	pstm.setInt(3, idOptionGroup);
		   	pstm.setInt(4, value);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}
	
	public void removeQuota(int idQuestionnaire, int idQuestion) {
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_QUOTA);
		   	pstm.setInt(1, idQuestionnaire);
		   	pstm.setInt(2, idQuestion);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

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
