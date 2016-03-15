package ilu.surveytool.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ilu.surveytool.databasemanager.DataObject.Page;
import ilu.surveytool.databasemanager.DataObject.Section;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class PageDB {

	public PageDB() {
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
	
	public List<Page> getPagesBySectionId(int sectionId, String lang)
	{
		List<Page> pages = new ArrayList<Page>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_PAGES_BY_SECTIONID);			
	   		pstm.setInt(1, sectionId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			Page page = new Page();
	   			page.setPageId(rs.getInt(DBFieldNames.s_PAGE_ID));
	   			page.setNumPage(rs.getInt(DBFieldNames.s_NUM_PAGE));
	   			
	   			QuestionDB questionDB = new QuestionDB();
	   			page.setQuestions(questionDB.getQuestionsByPageId(page.getPageId(), lang));
	   			
	   			pages.add(page);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return pages;
	}
	
	public List<Integer> getPagesIdBySectionId(int sectionId)
	{
		List<Integer> pages = new ArrayList<Integer>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_PAGES_ID_BY_SECTIONID);			
	   		pstm.setInt(1, sectionId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			int pageId = rs.getInt(DBFieldNames.s_PAGE_ID);   			
	   			
	   			pages.add(pageId);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return pages;
	}
	
	public int getPageId(int surveyId)
	{
		int response = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_PAGE_ID_BY_QUESTIONNAIRE_ID);		
		   	pstm.setInt(1, surveyId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			response = rs.getInt(DBFieldNames.s_PAGE_ID);
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

	public int insertPage(int sectionId, int numPage) {
		//System.out.println("inserUser");
		int pageId = 0;
		//int contentId = this.insertContentIndex();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_PAGE, Statement.RETURN_GENERATED_KEYS);
		   pstm.setInt(1, sectionId);
		   pstm.setInt(2, numPage);
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   pageId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return pageId;
	}

	public int getNumPagesBySectionId(int sectionId)
	{
		int numPages = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_NUM_PAGE_BY_SECTIONID);			
	   		pstm.setInt(1, sectionId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			numPages = rs.getInt(DBFieldNames.s_NUM_ELEMENTS);	   			
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
	 * Update
	 */
	
	/*
	 * Remove
	 */

	public void removePage(int pageId) {
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_PAGE);
		   	pstm.setInt(1, pageId);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}

	public void removePageFromQuestionByPage(int pageId) {
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_QUESTION_BY_PAGE_WHERE_PAGEID);
		   	pstm.setInt(1, pageId);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}

}
