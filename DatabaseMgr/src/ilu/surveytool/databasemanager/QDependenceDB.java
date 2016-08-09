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
	
	public QDependence getQDependenceByQuestionId(int questionId)
	{
		QDependence qDependence = null;
		List<QDependenceValue> qDependenceValue = new ArrayList<QDependenceValue>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QDEPENDENCES_BY_QUESTIONID);			
	   		pstm.setInt(1, questionId);
	   		
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
	   			
	   			qDependenceValue.add(new QDependenceValue(
	   					rs.getInt(DBFieldNames.s_DEPENDENCEITEM),
	   					rs.getInt(DBFieldNames.s_QUESTION_ID),
	   					rs.getInt(DBFieldNames.s_OPTIONSGROUPID),
	   					rs.getInt(DBFieldNames.s_OPTIONID)));
	   		}
	   		
	   		qDependence.setqdepval(qDependenceValue);
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return qDependence;
	}
	

	public QDependence getQDependenceByQuestionsIdOption(int questionId, int condquestionID, int ogid, int oid)
	{
		QDependence qDependence = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QDEPENDENCES_BY_QUESTION_CONDQUESTION_AND_OPTION);			
	   		pstm.setInt(1, questionId);
	   		pstm.setInt(2, condquestionID);
	   		pstm.setInt(3, ogid);
	   		pstm.setInt(4, oid);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			qDependence = new QDependence(rs.getInt(DBFieldNames.s_IDQDEPENDENCE),
	   					rs.getInt(DBFieldNames.s_QUESTION_ID),
	   					1,
	   					rs.getString(DBFieldNames.s_DEPENDENCETYPE),
	   					new ArrayList<QDependenceValue>());
	   			
	   			List<QDependenceValue> qDependenceValue = new ArrayList<QDependenceValue>();
	   			qDependenceValue.add(new QDependenceValue(rs.getInt(DBFieldNames.s_DEPENDENCEITEM),
	   					rs.getInt(DBFieldNames.s_QUESTION_ID),
	   					rs.getInt(DBFieldNames.s_OPTIONSGROUPID),
	   					rs.getInt(DBFieldNames.s_OPTIONID)));
	   			qDependence.setqdepval(qDependenceValue);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return qDependence;
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
	
	public boolean updateQDependence(int idQDependences, int idQuestion, int idOptionsGroup, int idOption, String type) {
		boolean result = updateQDependenceType(idQDependences, type);
		if(result)
			result = updateQDependenceQuestion(idQDependences, idQuestion, idOptionsGroup, idOption);
		
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
	
	public boolean updateQDependenceQuestion(int idQDependences, int idQuestion, int idOptionsGroup, int idOption) {
		//System.out.println("updateState");
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_QDEPENDENCEVALUE_QUESTION);
			pstm.setInt(1, idQuestion);
			pstm.setInt(2, idOptionsGroup);
			pstm.setInt(2, idOption);
			pstm.setInt(4, idQDependences);
		   		
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
	public void removeQDependence(int idQDependences) {
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_QDEPENDENCE);
		   	pstm.setInt(1, idQDependences);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}

	public void removeQDependenceValue(int idQDependences) {
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_OPTION);
		   	pstm.setInt(1, idQDependences);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}
	

}
