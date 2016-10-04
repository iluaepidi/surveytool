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

import ilu.surveytool.databasemanager.DataObject.Section;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class SectionDB {

	public SectionDB() {
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
	
	public List<Section> getSectionsBySurveyId(int surveyId, String lang, String langdefault)
	{
		List<Section> sections = new ArrayList<Section>();
		
		//if(lang==null)lang = langdefault;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_SECTIONS_BY_SURVEYID);			
	   		pstm.setInt(1, surveyId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			Section section = new Section();
	   			section.setSectionId(rs.getInt(DBFieldNames.s_SECTIONID));
	   			section.setFormaId(rs.getInt(DBFieldNames.s_FORMAID));	   			
	   			
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();
	   			section.setContents(contentDB.getContentByIdAndLanguage(contentId, lang,langdefault));
	   			
	   			PageDB pageDB = new PageDB();
	   			section.setPages(pageDB.getPagesBySectionId(section.getSectionId(), lang,langdefault));
	   			
	   			sections.add(section);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return sections;
	}

	public JSONObject getSectionJsonBySurveyId(int surveyId, int numSection, Object anonimousUser, String lang, String langdefault)
	{
		JSONObject section = new JSONObject();
		
		//if(lang==null)lang = langdefault;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_SECTION_BY_SURVEYID_INDEX);			
	   		pstm.setInt(1, surveyId);
	   		pstm.setInt(2, numSection);
	   		//System.out.println("[SectionDB-getSectionJsonBySurveyId] "+DBSQLQueries.s_SELECT_SECTION_BY_SURVEYID_INDEX+", "+surveyId+" - "+numSection);
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			int sectionId = rs.getInt(DBFieldNames.s_SECTIONID);
	   			section.put("sectionId", sectionId);
	   			section.put("formaId", rs.getInt(DBFieldNames.s_FORMAID));	   			
	   			
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();
		   		section.put("contents", contentDB.getContentJsonByIdAndLanguage(contentId, lang, null));
	   			
	   			PageDB pageDB = new PageDB();
	   			section.put("page", pageDB.getPageJsonBySectionId(sectionId, anonimousUser, lang, langdefault));
	   			
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
		
		return section;
	}

	public List<Section> getSectionByIndexNumPage(int surveyId, String lang, String langdefault, int numSection, int numPage)
	{
		List<Section> sections = new ArrayList<Section>();
		
		//if(lang==null)lang = langdefault;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_SECTIONS_BY_SURVEYID);			
	   		pstm.setInt(1, surveyId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			Section section = new Section();
	   			section.setSectionId(rs.getInt(DBFieldNames.s_SECTIONID));
	   			section.setFormaId(rs.getInt(DBFieldNames.s_FORMAID));	   			
	   			
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();
	   			section.setContents(contentDB.getContentByIdAndLanguage(contentId, lang,langdefault));
	   			
	   			PageDB pageDB = new PageDB();
	   			section.setPages(pageDB.getPagesBySectionId(section.getSectionId(), lang,langdefault));
	   			
	   			sections.add(section);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return sections;
	}
	
	public int getSectionContentIdBySectionId(int sectionId)
	{
		int contentId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_SECTION_COTENTID_BY_ID);			
	   		pstm.setInt(1, sectionId);
	   		
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
	
	public int getNumSectionsBySurveyId(int surveyId)
	{
		int numSections = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_SECTION_NUM_ROWS_BY_SURVEYID);			
	   		pstm.setInt(1, surveyId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			numSections = rs.getInt(DBFieldNames.s_NUM_ELEMENTS);	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return numSections;
	}

	/**
	 * Inserts 
	 */

	public int insertSection(int formaId, int index, int contentId) {
		int sectionId = 0;

		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_SECTION, Statement.RETURN_GENERATED_KEYS);
		   pstm.setInt(1, index);
		   pstm.setInt(2, formaId);
		   pstm.setInt(3, contentId);
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   sectionId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return sectionId;
	}

	/**
	 * Update
	 */
	
	/*
	 * Remove
	 */

	public void removeSection(int sectionId) {
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_SECTION);
		   	pstm.setInt(1, sectionId);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}

}
