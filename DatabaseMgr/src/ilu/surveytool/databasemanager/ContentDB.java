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
import ilu.surveytool.databasemanager.DataObject.Questionnaire;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class ContentDB {

	public ContentDB() {
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
	 */
	
	public HashMap<String, Content> getContentByIdAndLanguage(int contentId, String language)
	{
		HashMap<String, Content> contents = new HashMap<String, Content>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_CONTENT_BY_ID_LANGUAGE);			
	   		pstm.setInt(1, contentId);
	   		pstm.setString(2, language);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			String contentType = rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME);
	   			contents.put(contentType, new Content(contentId, language, 
	   					rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME), 
	   					rs.getString(DBFieldNames.s_CONTENT_TEXT)));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return contents;
	}
	

	
	/*
	 * selects
	 */
	
	public HashMap<String, Content> getContentByIdLanguageContentType(int contentId, String language, String contentType)
	{
		HashMap<String, Content> contents = new HashMap<String, Content>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
			pstm = con.prepareStatement(DBSQLQueries.s_SELECT_CONTENT_BY_ID_LANGUAGE_CONTENTTYPE);
	    	pstm.setInt(1, contentId);
			pstm.setString(2, language); 
			pstm.setString(3, contentType);
			   
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			contents.put(contentType, new Content(contentId, language, 
	   					rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME), 
	   					rs.getString(DBFieldNames.s_CONTENT_TEXT)));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return contents;
	}
	
	/*
	 * exists 
	 */
	
	public boolean existContent(int contentId, String language, String contentType)
	{
		boolean exist = false;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_CONTENT_BY_ID_LANGUAGE_CONTENTTYPE);			
	   		pstm.setInt(1, contentId);
	   		pstm.setString(2, language);
	   		pstm.setString(3, contentType);
	   		
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
	
	/*
	 * Inserts
	 */
	
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
	
	public boolean insertContent(int contentId, String language, String contentType, String text) {
		System.out.println("insertContent: [contentId: "+ contentId+", language: "+language+", contentType: "+contentType+", text: "+text+"]");
		boolean inserted = false;
		boolean notInserted = false;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
	    if(getContentByIdLanguageContentType(contentId, language, contentType).isEmpty()){
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_CONTENT);
		   pstm.setInt(1, contentId);
		   pstm.setString(2, language); 
		   pstm.setString(3, contentType); 
		   pstm.setString(4, text);
		   
		   notInserted = pstm.execute();
	    }
	    else
	    	updateContentText(contentId, language, contentType, text);
	    
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
	
	public void updateContentText(int contentId, String language, String contentType, String text) {
		//System.out.println("updateState");
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_CONTENT_TEXT);
			pstm.setString(1, text);
			pstm.setInt(2, contentId);
			pstm.setString(3, language);
			pstm.setString(4, contentType);
		   		
			int numUpdated = pstm.executeUpdate();
					
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

	public void removeContent(int contentId) {
		//System.out.println("removeUserOptionValues");
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_CONTENT);
		   	pstm.setInt(1, contentId);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}
	
	public void removeContentByTypeLang(int contentId, String language, String contentType) {
		//System.out.println("removeUserOptionValues");
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_CONTENT_BY_ID_TYPE_LANG);
		   	pstm.setInt(1, contentId);
			pstm.setString(2, language);
			pstm.setString(3, contentType);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}
	

}
