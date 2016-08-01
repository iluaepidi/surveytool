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
import ilu.surveytool.databasemanager.DataObject.Resource;
import ilu.surveytool.databasemanager.DataObject.ResourceType;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class ResourceDB {

	public ResourceDB() {
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
	
	public List<Resource> getResourcesByQuestionId(int questionId, String lang)
	{
		List<Resource> resources = new ArrayList<Resource>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_RESOURCES_BY_QUESTIONID);			
	   		pstm.setInt(1, questionId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();
	   			HashMap<String, Content> contents = contentDB.getContentByIdAndLanguage(contentId, lang,null);
	   				   			
	   			Resource resource = new Resource(rs.getInt(DBFieldNames.s_RESOURCEID), 
	   					rs.getString(DBFieldNames.s_RESOURCE_TYPE_NAME), 
	   					rs.getString(DBFieldNames.s_RESOURCE_URL_PATH), 
	   					contents);
	   			resources.add(resource);
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return resources;
	}
	
	public Resource getResourceById(int resourceId)
	{
		Resource resource = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_RESOURCE_BY_ID);			
	   		pstm.setInt(1, resourceId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			
	   			resource = new Resource(resourceId, 
	   					rs.getString(DBFieldNames.s_RESOURCE_TYPE_NAME), 
	   					rs.getString(DBFieldNames.s_RESOURCE_URL_PATH), 
	   					rs.getInt(DBFieldNames.s_CONTENTID));
	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return resource;
	}

	public Resource getResourceById(int resourceId, String lang,String langdefault)
	{
		Resource resource = null;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		if(lang==null)lang = langdefault;
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_RESOURCE_BY_ID);			
	   		pstm.setInt(1, resourceId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			
	   			/*resource = new Resource(resourceId, 
	   					rs.getString(DBFieldNames.s_RESOURCE_TYPE_NAME), 
	   					rs.getString(DBFieldNames.s_RESOURCE_URL_PATH), 
	   					rs.getInt(DBFieldNames.s_CONTENTID));*/
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();
	   			HashMap<String, Content> contents = contentDB.getContentByIdAndLanguage(contentId, lang,null);
	   				   			
	   			resource = new Resource(rs.getInt(DBFieldNames.s_RESOURCEID), 
	   					rs.getString(DBFieldNames.s_RESOURCE_TYPE_NAME), 
	   					rs.getString(DBFieldNames.s_RESOURCE_URL_PATH), 
	   					contents);
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return resource;
	}
	
	public List<ResourceType> getResourceTypes()
	{
		List<ResourceType> resourceTypes = new ArrayList<ResourceType>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_RESOURCE_TYPES);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			ResourceType resourceType = new ResourceType(rs.getInt(DBFieldNames.s_RESOURCE_TYPE_ID), 
	   					rs.getString(DBFieldNames.s_RESOURCE_TYPE_NAME));
	   			resourceTypes.add(resourceType);
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return resourceTypes;
	}
	
	/**
	 * Inserts 
	 */
	
	public int insertResource(Resource resource, int contentId) {
		//System.out.println("inserUser");
		int resourceId = 0;
		//int contentId = this.insertContentIndex();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_RESOURCE, Statement.RETURN_GENERATED_KEYS);
		   pstm.setString(1, resource.getType());
		   pstm.setString(2, resource.getPathFile()); 
		   pstm.setInt(3, contentId);
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   resourceId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return resourceId;
	}

	public boolean insertQuestionResource(int questionId, int resourceId) {
		//System.out.println("inserUser");
		boolean inserted = false;
		//int contentId = this.insertContentIndex();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_QUESTION_RESOURCE);
		   pstm.setInt(1, questionId);
		   pstm.setInt(2, resourceId);
		   
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
	
	/**
	 * Update
	 */
	
	public boolean updateOptionWithIdResource(int idOption,int resourceId) {
		//System.out.println("updateState");
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_OPTION_IDRESOURCE);
			pstm.setInt(1, resourceId);
			pstm.setInt(2, idOption);
		   		
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
	
	
	public boolean updateResourceUrlPath(int resourceId, String urlPath) {
		//System.out.println("updateState");
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_RESOURCE_URLPATH);
			pstm.setString(1, urlPath);
			pstm.setInt(2, resourceId);
		   		
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
	
	/*
	 * Remove
	 */
	
	public void removeResource(int resourceId) {
		//System.out.println("removeUserOptionValues");
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_RESOURCE);
		   	pstm.setInt(1, resourceId);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}
	

}
