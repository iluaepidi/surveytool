package ilu.surveytool.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveytool.databasemanager.DataObject.AnonimousUser;
import ilu.surveytool.databasemanager.DataObject.Page;
import ilu.surveytool.databasemanager.DataObject.Section;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;
import jdk.nashorn.api.scripting.JSObject;

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
	
	public List<Page> getPagesBySectionId(int sectionId, String lang, String langdefault)
	{
		List<Page> pages = new ArrayList<Page>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		if(lang==null)lang = langdefault;
		
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
	   			page.setQuestions(questionDB.getQuestionsByPageId(page.getPageId(), lang,langdefault));
	   			
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

	public JSONObject getPageJsonBySectionId(int sectionId, Object anonimousUser, String lang, String langdefault)
	{
		JSONObject page = new JSONObject();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		if(lang==null)lang = langdefault;
		
		int numPage = 1;
		if(anonimousUser instanceof AnonimousUser) numPage = ((AnonimousUser) anonimousUser).getCurrentPage();
		
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_PAGE_BY_NUMPAGE_SECTIONID);			
	   		pstm.setInt(1, sectionId);
	   		pstm.setInt(2, numPage);
	   		//System.out.println("[PAgeDB-getPageJsonBySectionId] "+DBSQLQueries.s_SELECT_PAGE_BY_NUMPAGE_SECTIONID+", "+sectionId+" - "+numPage);
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			int pageId = rs.getInt(DBFieldNames.s_PAGE_ID);
	   			page.put("pageId", pageId);
	   			page.put("numPage", rs.getInt(DBFieldNames.s_NUM_PAGE));
	   			
	   			QuestionDB questionDB = new QuestionDB();
	   			page.put("questions", questionDB.getQuestionsJsonByPageId(pageId, lang, langdefault, anonimousUser));
	   			
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return page;
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
	
	public int getPageIdBySectionIdNumPage(int sectionId, int numPage)
	{
		int page = -1;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
			pstm = con.prepareStatement(DBSQLQueries.s_SELECT_PAGE_BY_NUMPAGE_SECTIONID);			
	   		pstm.setInt(1, sectionId);
	   		pstm.setInt(2, numPage);

	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			page = rs.getInt(DBFieldNames.s_PAGE_ID);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return page;
	}
	
	public int getNextPageIdBySectionIdOPageId(int sectionId, int currentNumPage)
	{
		int page = -1;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_NEXT_PAGE_ID_BY_SECTIONID);			
	   		pstm.setInt(1, sectionId);
	   		pstm.setInt(1, currentNumPage);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			page = rs.getInt(DBFieldNames.s_PAGE_ID);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return page;
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
	
	public List<Integer> getPageNum(int surveyId)
	{
		List<Integer> response = new ArrayList<Integer>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_PAGE_NUM_BY_QUESTIONNAIRE_ID);		
		   	pstm.setInt(1, surveyId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			response.add(rs.getInt(DBFieldNames.s_NUM_PAGE));
	   		}	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}

	public Page getPageByPageId(int pageId)
	{
		Page response = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_PAGE_BY_PAGEID);		
		   	pstm.setInt(1, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			response = new Page();
	   			response.setPageId(rs.getInt(DBFieldNames.s_PAGE_ID));
	   			response.setNumPage(rs.getInt(DBFieldNames.s_NUM_PAGE));
	   			response.setSectionId(rs.getInt(DBFieldNames.s_SECTIONID));
	   			QuestionDB questionDB = new QuestionDB();
	   			response.setQuestions(questionDB.getQuestionsIdIndexByPageId(pageId));
	   		}	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}

	public Page getPageByNumPageSectionId(int sectionId, int numPage)
	{
		Page response = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_PAGE_BY_NUMPAGE_SECTIONID);		
		   	pstm.setInt(1, sectionId);
		   	pstm.setInt(2, numPage);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			response = new Page();
	   			response.setPageId(rs.getInt(DBFieldNames.s_PAGE_ID));
	   			response.setNumPage(rs.getInt(DBFieldNames.s_NUM_PAGE));
	   			response.setSectionId(rs.getInt(DBFieldNames.s_SECTIONID));
	   			QuestionDB questionDB = new QuestionDB();
	   			response.setQuestions(questionDB.getQuestionsIdIndexByPageId(response.getPageId()));
	   		}	   		
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}

	public int getMaxNumPagesBySectionId(int sectionId)
	{
		int numPage = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_MAX_PAGE_BY_SECTIONID);			
	   		pstm.setInt(1, sectionId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			numPage = rs.getInt(DBFieldNames.s_NUM_PAGE);   	
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return numPage;
	}

	public List<Page> getNumPagesBigerThanBySurveyId(int surveyId, int numPageIndex)
	{
		List<Page> pages = new ArrayList<Page>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_PAGES_BIGER_THAN_BY_SURVEYID);			
	   		pstm.setInt(1, surveyId);
	   		pstm.setInt(2, numPageIndex);	   		
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			Page page = new Page();
	   			page.setPageId(rs.getInt(DBFieldNames.s_PAGE_ID));
	   			page.setNumPage(rs.getInt(DBFieldNames.s_NUM_PAGE));
	   			
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

	public int getNumPagesBySurveyId(int surveyId)
	{
		int numPages = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_NUM_PAGES_BY_SURVEYID);			
	   		pstm.setInt(1, surveyId);
	   		
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

	/**
	 * Update
	 */

	public void updateNumPage(int pageId, int numPage) {
		System.out.println("updateQuestionMandatory");
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_PAGE_NUM_PAGE);
			pstm.setInt(1, numPage);
			pstm.setInt(2, pageId);
		   		
			int numUpdated = pstm.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		   
	}
	
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
