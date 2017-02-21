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
import ilu.surveytool.databasemanager.DataObject.Option;
import ilu.surveytool.databasemanager.DataObject.OptionsByGroup;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;
import ilu.surveytool.databasemanager.DataObject.Project;
import ilu.surveytool.databasemanager.DataObject.QDependence;
import ilu.surveytool.databasemanager.DataObject.QDependenceValue;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Questionnaire;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class QDependenceDB {

	public QDependenceDB() {
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
	
	public QDependence getQDependenceByQuestionId(int questionId, String lang)
	{
		QDependence qDependence = null;
		List<QDependenceValue> qDependenceValue = new ArrayList<QDependenceValue>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QDEPENDENCES_BY_QUESTIONID_LANG);			
	   		pstm.setInt(1, questionId);
	   		pstm.setString(2, lang);
	   		pstm.setString(3, lang);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			if(qDependence == null){
	   				qDependence = new QDependence(
	   						rs.getInt(DBFieldNames.s_IDQDEPENDENCE),
		   					rs.getInt(DBFieldNames.s_QUESTION_ID),
		   					1,
		   					rs.getString(DBFieldNames.s_DEPENDENCETYPE),
		   					new ArrayList<QDependenceValue>()
		   					);
	   				
	   			}
	   			
	   			QDependenceValue qDepVal = new QDependenceValue(
	   					rs.getInt(DBFieldNames.s_DEPENDENCEITEM),
	   					rs.getInt(DBFieldNames.s_QUESTION_ID),
	   					rs.getInt(DBFieldNames.s_PAGE_ID),
	   					rs.getString(DBFieldNames.s_QTEXT),
	   					rs.getInt(DBFieldNames.s_OPTIONSGROUPID),
	   					rs.getInt(DBFieldNames.s_DEPENDENCEOPTIONID),
	   					rs.getString(DBFieldNames.s_OTEXT));
	   			
	   			if(qDepVal.getOName().isEmpty())
	   			{
	   				OptionDB optionDB = new OptionDB();
	   				ResourceDB resourceDB = new ResourceDB();
	   				int resourceId = optionDB.getResourceIdByOptionId(qDepVal.getOid());
	   				int contentId = resourceDB.getContentIdByResourceId(resourceId);
	   				ContentDB contentDB = new ContentDB();
	   				String text = contentDB.getContentByIdLanguageContentType(contentId, lang, DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
	   				qDepVal.setOName(text);
	   			}
	   			
	   			qDependenceValue.add(qDepVal);
	   		}
	   		
	   		if(qDependence != null)
	   			qDependence.setqdepval(qDependenceValue);
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return qDependence;
	}
	
	public int getQDependenceIdByQuestionId(int questionId)
	{
		int qDependenceId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QDEPENDENCEID_BY_QUESTIONID_LANG);			
	   		pstm.setInt(1, questionId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			qDependenceId = rs.getInt(DBFieldNames.s_IDQDEPENDENCE);
	   				
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return qDependenceId;
	}
	
	public QDependence getQDependenceByQuestionIdWithoutTexts(int questionId)
	{
		QDependence qDependence = null;
		List<QDependenceValue> qDependenceValue = new ArrayList<QDependenceValue>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QDEPENDENCES_BY_QUESTIONID_NOTEXT);			
	   		pstm.setInt(1, questionId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			if(qDependence == null){
	   				qDependence = new QDependence(
	   						rs.getInt(DBFieldNames.s_IDQDEPENDENCE),
		   					0,
		   					0,
		   					rs.getString(DBFieldNames.s_DEPENDENCETYPE),
		   					new ArrayList<QDependenceValue>()
		   					);
	   				
	   			}
	   			
	   			qDependenceValue.add(new QDependenceValue(
	   					0,
	   					rs.getInt(DBFieldNames.s_QUESTION_ID),
	   					0,
	   					"",
	   					rs.getInt(DBFieldNames.s_OPTIONSGROUPID),
	   					rs.getInt(DBFieldNames.s_DEPENDENCEOPTIONID),
	   					""));
	   		}
	   		
	   		if(qDependence != null)
	   			qDependence.setqdepval(qDependenceValue);
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return qDependence;
	}
	
	public int getCountQDependenceValue(int qDependenceId)
	{
		int count = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_COUNT_QDEPENDENCESVALUE);			
	   		pstm.setInt(1, qDependenceId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			count = rs.getInt(DBFieldNames.s_COUNT);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
			
		return count;
	}
	
	public int getIDQDependence(int qDependenceValueItem)
	{
		int id = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_IDQDEPENDENCE_FROM_QDEPENDENCESVALUE);			
			pstm.setInt(1, qDependenceValueItem);
	   		
			rs = pstm.executeQuery();
			if(rs.next())
			{
				id = rs.getInt(DBFieldNames.s_IDQDEPENDENCE);
			}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
			
		return id;
	}

	/**
	 * Inserts 
	 */
	public int insertQDependence(int idQuestion, String idDependenceType) 
	{		
		int qdependenceId = 0;

		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_QDEPENDENCE, Statement.RETURN_GENERATED_KEYS);
		   pstm.setInt(1, idQuestion); 
		   pstm.setInt(2, idQuestion); 
		   if((idDependenceType==null) || (idDependenceType.equals("")))
			   pstm.setString(3, "And");
		   else
			   pstm.setString(3, idDependenceType);
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   qdependenceId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return qdependenceId;
	}
	
	public int insertQDependenceValue(int idQDependences, int idQuestion, int idOptionsGroup, String optionValue) 
	{		
		int qdependenceItemId = 0;

		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_QDEPENDENCEVALUE, Statement.RETURN_GENERATED_KEYS);
		   pstm.setInt(1, idQDependences); 
		   pstm.setInt(2, idQuestion); 
		   pstm.setInt(3, idOptionsGroup);
		   pstm.setString(4, optionValue);
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   qdependenceItemId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return qdependenceItemId;
	}
	
	/**
	 * Update
	 */
	
	public boolean updateQDependence(int idQDependences, int idDependenceItem, int idQuestion, int idOptionsGroup, int idOption, String type) {
		boolean result = true;
		
		if(!type.equals(""))
			result = updateQDependenceType(idQDependences, type);
		
		if(result)
			result = updateQDependenceQuestion(idDependenceItem, idQuestion, idOptionsGroup, idOption);
		
		return result;
	}
	
	public boolean updateQDependenceType(int idQDependences, String type) {
		//System.out.println("updateState");
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_QDEPENDENCE_DEPENDENCETYPE);
			pstm.setString(1, type);
			pstm.setInt(2, idQDependences);
		   		
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
	
	public boolean updateQDependenceQuestion(int idDependenceItem, int idQuestion, int idOptionsGroup, int idOption) {
		System.out.println("updateState: idDependenceItem"+idDependenceItem+", idQuestion"+idQuestion+", idOptionsGroup"+idOptionsGroup+", idOption"+idOption);
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_QDEPENDENCEVALUE_QUESTION);
			pstm.setInt(1, idQuestion);
			pstm.setInt(2, idOptionsGroup);
			pstm.setInt(3, idOption);
			pstm.setInt(4, idDependenceItem);
		   		
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
	 * Remove
	 */
	public boolean removeQDependence(int idQDependences) {
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		boolean response = false;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_QDEPENDENCE);
		   	pstm.setInt(1, idQDependences);
	   		
		   	pstm.execute();
		   	response = true;
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

		return response;
	}

	public boolean removeQDependenceValue(int idQDependencesValue) {
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		boolean response = false;
		   
		try{
			int idQDependences = getIDQDependence(idQDependencesValue);
			
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_QDEPENDENCEVALUE);
		   	pstm.setInt(1, idQDependencesValue);
	   		
		   	pstm.execute();
		   	response = true;
		   	System.out.println(DBSQLQueries.s_DELETE_QDEPENDENCEVALUE+", 1="+idQDependencesValue +"-->"+response);
		   			   	
		   	if(getCountQDependenceValue(idQDependences) == 0)
		   		removeQDependence(idQDependences);
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

		return response;

	}


	public boolean removeAllQDependenceValue(int idQDependences) {
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		boolean response = false;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_ALLQDEPENDENCEVALUE);
		   	pstm.setInt(1, idQDependences);
	   		
		   	pstm.execute();
		   	response = true;
		   	System.out.println(DBSQLQueries.s_DELETE_ALLQDEPENDENCEVALUE+", 1="+idQDependences +"-->"+response);
		   	removeQDependence(idQDependences);
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

		return response;

	}

}
