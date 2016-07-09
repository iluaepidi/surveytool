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
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Questionnaire;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class OptionDB {

	public OptionDB() {
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
	
	public int getContentIdByOptionId(int optionId)
	{
		int contentId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_OPTION_BY_OPTIONID);			
	   		pstm.setInt(1, optionId);
	   		
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
	
	public int getContentIdByOptionsGroupId(int optionsGroupId)
	{
		int contentId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_OPTIONSGROUP_BY_ID);			
	   		pstm.setInt(1, optionsGroupId);
	   		
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
	
	public List<OptionsGroup> getOptionsGroupByQuestionId(int questionId, String lang)
	{
		List<OptionsGroup> optionsGroups = new ArrayList<OptionsGroup>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_OPTIONSGROUP_BY_QUESTION_ID);			
	   		pstm.setInt(1, questionId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			OptionsGroup optionsGroup = new OptionsGroup();
	   			optionsGroup.setId(rs.getInt(DBFieldNames.s_OPTIONSGROUPID));
	   			optionsGroup.setOptionType(rs.getString(DBFieldNames.s_OPTIONSGROUP_OPTIONTYPE_NAME));
	   			optionsGroup.setRandom(rs.getBoolean(DBFieldNames.s_OPTIONSGROUP_RANDOM));
	   			optionsGroup.setIndex(rs.getInt(DBFieldNames.s_INDEX));
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();
	   			optionsGroup.setContents(contentDB.getContentByIdAndLanguage(contentId, lang));
	   			
	   			optionsGroup.setOptions(this.getOptionsByOptionsGroupId(optionsGroup.getId(), lang));
	   			
	   			optionsGroups.add(optionsGroup);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return optionsGroups;
	}
	
	public List<Integer> getOptionsGroupIdByQuestionId(int questionId)
	{
		List<Integer> optionsGroupsId = new ArrayList<Integer>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_OPTIONSGROUPID_BY_QUESTION_ID);			
	   		pstm.setInt(1, questionId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			optionsGroupsId.add(rs.getInt(DBFieldNames.s_OPTIONSGROUPID));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return optionsGroupsId;
	}
	
	public List<Option> getOptionsByOptionsGroupId(int optionsGroupId, String lang)
	{
		List<Option> options = new ArrayList<Option>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_OPTION_BY_OPTIONSGROUPID);			
	   		pstm.setInt(1, optionsGroupId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			Option option = new Option();
	   			option.setId(rs.getInt(DBFieldNames.s_OPTIONID));
	   			option.setIndex(rs.getInt(DBFieldNames.s_INDEX));
	   			
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();
	   			option.setContents(contentDB.getContentByIdAndLanguage(contentId, lang));
	   			
	   			options.add(option);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return options;
	}
	
	public List<Integer> getOptionByGroupIdByOptionId(int optionId)
	{
		List<Integer> optionGroupId = new ArrayList<Integer>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_OPTIONSBYGROUP_ID_BY_OPTIONID);			
	   		pstm.setInt(1, optionId);
	   		
	   		rs = pstm.executeQuery();
	   		
	   		while(rs.next())
	   		{
	   			optionGroupId.add(rs.getInt(DBFieldNames.s_OPTIONSGROUPID));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return optionGroupId;
	}
	
	public List<Integer> getOptionIdByQuestionId(int questionId)
	{
		List<Integer> optionId = new ArrayList<Integer>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_OPTIONID_BY_QUESTIONID);			
	   		pstm.setInt(1, questionId);
	   		
	   		rs = pstm.executeQuery();
	   		
	   		while(rs.next())
	   		{
	   			optionId.add(rs.getInt(DBFieldNames.s_OPTIONID));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return optionId;
	}
	
	public int getQuestionIdByOptionsGroupId(int optionsGroupId)
	{
		int questionId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_OPTIONSGROUP_BY_ID);			
	   		pstm.setInt(1, optionsGroupId);
	   		
	   		rs = pstm.executeQuery();
	   		
	   		if(rs.next())
	   		{
	   			questionId = rs.getInt(DBFieldNames.s_QUESTION_ID);
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questionId;
	}
	
	public List<OptionsByGroup> getOptionsByGroupById(int optionsGroupId)
	{
		List<OptionsByGroup> optionsByGroup = new ArrayList<OptionsByGroup>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_OPTIONSBYGROUP_BY_ID);			
	   		pstm.setInt(1, optionsGroupId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			optionsByGroup.add(new OptionsByGroup(optionsGroupId, 
	   					rs.getInt(DBFieldNames.s_OPTIONID), 
	   					rs.getInt(DBFieldNames.s_INDEX)));
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return optionsByGroup;
	}
	
	/**
	 * Inserts 
	 */
	
	public int insertOptionsGroup(int questionId, String optionType, int contentId, int index) 
	{		
		int optionsGroupId = 0;

		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_OPTIONS_GROUP, Statement.RETURN_GENERATED_KEYS);
		   pstm.setInt(1, questionId); 
		   pstm.setInt(2, contentId); 
		   pstm.setString(3, optionType);
		   pstm.setInt(4, index);
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   optionsGroupId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return optionsGroupId;
	}
	
	public int insertOption(int contentId) 
	{		
		int optionId = 0;

		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_OPTION, Statement.RETURN_GENERATED_KEYS);
		   pstm.setInt(1, contentId); 
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   optionId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return optionId;
	}
	
	public boolean insertOptionsByGroup(int optionsGroupId, int optionId, int index) 
	{		
		boolean inserted = false;

		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_OPTIONS_BY_GROUP, Statement.RETURN_GENERATED_KEYS);
		   pstm.setInt(1, optionsGroupId); 
		   pstm.setInt(2, optionId); 
		   pstm.setInt(3, index);
		   
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
	
	public boolean updateOptionsByGroupIndex(int optionsGroupId, int optionId, int index) {
		//System.out.println("updateState");
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_OPTIONSBYGROUP_INDEX);
			pstm.setInt(1, index);
			pstm.setInt(2, optionsGroupId);
			pstm.setInt(3, optionId);
		   		
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
	
	public boolean updateOptionsGroupIndex(int optionsGroupId, int index) {
		//System.out.println("updateState");
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_OPTIONSGROUP_INDEX);
			pstm.setInt(1, index);
			pstm.setInt(2, optionsGroupId);
		   		
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
	
	public void removeOption(int optionId) {
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_OPTION);
		   	pstm.setInt(1, optionId);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}
	
	public void removeOptionsGroup(int optionsGroupId) {
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_OPTIONSGROUP);
		   	pstm.setInt(1, optionsGroupId);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}

}
