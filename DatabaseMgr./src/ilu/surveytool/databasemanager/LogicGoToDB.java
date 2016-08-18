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
import ilu.surveytool.databasemanager.DataObject.LogicGoTo;
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

public class LogicGoToDB {

	public LogicGoToDB() {
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
	
	public List<LogicGoTo> getLogicGoToByQuestionId(int questionId, String lang)
	{
		List<LogicGoTo> logicGoTo = new ArrayList<LogicGoTo>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{			
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_LOGICGOTTO_BY_IDQUESTION);			
	   		pstm.setInt(1, questionId);
	   		pstm.setString(2, lang);
	   		pstm.setString(3, lang);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			logicGoTo.add(new LogicGoTo(questionId,rs.getInt(DBFieldNames.s_QDESTID),rs.getString(DBFieldNames.s_QTEXT),rs.getInt(DBFieldNames.s_OPTIONSGROUPID),Integer.parseInt(rs.getString(DBFieldNames.s_OPTIONVALUE)),rs.getString(DBFieldNames.s_OTEXT)));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return logicGoTo;
	}
	
	public boolean existLogicGoToByQuestionId_OgId_OId(int questionId, int ogid, int oid)
	{
		boolean exist = false;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{			
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_EXISTLOGICGOTTO_BY_IDQUESTION_OGID_OID);			
	   		pstm.setInt(1, questionId);
	   		pstm.setInt(2, ogid);
	   		pstm.setString(3, Integer.toString(oid));
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			exist = true;
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return exist;
	}

	/**
	 * Inserts 
	 */
	public boolean insertLogicGoTo(LogicGoTo logicGoTo) 
	{		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		boolean inserted = false;
	    try {
	    	System.out.println("Inserting goto");
	    	pstm = con.prepareStatement(DBSQLQueries.s_INSERT_LOGICGOTTO);
	    	pstm.setInt(1, logicGoTo.getQid()); 
	    	pstm.setInt(2, logicGoTo.getOgid()); 
	    	pstm.setString(3, Integer.toString(logicGoTo.getOid()));
		   	pstm.setInt(4, logicGoTo.getQDestId());
		   	pstm.setInt(5, logicGoTo.getQid());
		   
		   inserted = pstm.execute();
		   
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return inserted;
	}
	
	/**
	 * Update
	 */
	
	public boolean updateLogicGoTo(LogicGoTo logicGoTo) {
		System.out.println("updating goto");
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
			pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_LOGICGOTTO);
			pstm.setInt(1, logicGoTo.getQDestId());
			pstm.setInt(2, logicGoTo.getQid()); 
		    pstm.setInt(3, logicGoTo.getOgid()); 
		    pstm.setString(4, Integer.toString(logicGoTo.getOid()));
			pstm.setInt(5, logicGoTo.getQid());
		   		
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
	public boolean removeLogicGoTo(LogicGoTo logicGoTo) {
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		boolean response = false;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_LOGICGOTTO);
		   	pstm.setInt(1, logicGoTo.getQid()); 
		    pstm.setInt(2, logicGoTo.getOgid()); 
		    pstm.setString(3, Integer.toString(logicGoTo.getOid()));
			pstm.setInt(4, logicGoTo.getQid());
	   		
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

	public boolean removeAllLogicGoTo(LogicGoTo logicGoTo) {
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		boolean response = false;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_ALLLOGICGOTTO);
		   	pstm.setInt(1, logicGoTo.getQid());
		   	pstm.setInt(2, logicGoTo.getQid());
	   		
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

}
