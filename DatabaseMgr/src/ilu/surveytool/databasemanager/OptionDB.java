package ilu.surveytool.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveytool.databasemanager.DataObject.AnonimousUser;
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

	public List<OptionsGroup> getOptionsGroupByQuestionId(int questionId, String lang, String langdefault)
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
	   			//optionsGroup.setOtherOption(rs.getBoolean(DBFieldNames.s_OPTIONSGROUP_OTHER_OPTION));
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();

	   			optionsGroup.setContents(contentDB.getContentByIdAndLanguage(contentId, lang, langdefault));
	   			
	   			optionsGroup.setOptions(this.getOptionsByOptionsGroupId(optionsGroup.getId(), lang, langdefault));
	   			
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

	public JSONArray getOptionsGroupJSONByQuestionId(int questionId, String lang, String langdefault, Object anonimousUser)
	{
		JSONArray optionsGroups = new JSONArray();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_OPTIONSGROUP_BY_QUESTION_ID);			
	   		pstm.setInt(1, questionId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			JSONObject optionsGroup = new JSONObject();
	   			int optionsGroupId = rs.getInt(DBFieldNames.s_OPTIONSGROUPID);
	   			optionsGroup.put("optionGroupId", optionsGroupId);
	   			String optionType = rs.getString(DBFieldNames.s_OPTIONSGROUP_OPTIONTYPE_NAME);
	   			optionsGroup.put("optionType", optionType);
	   			optionsGroup.put("ramdom", rs.getBoolean(DBFieldNames.s_OPTIONSGROUP_RANDOM));
	   			optionsGroup.put("index", rs.getInt(DBFieldNames.s_INDEX));
	   			//optionsGroup.put("otherOption", rs.getInt(DBFieldNames.s_OPTIONSGROUP_OTHER_OPTION));
	   			if(optionType.equals(DBConstants.s_VALUE_OPTIONSGROUP_TYPE_RADIO)) 
	   			{
	   				ResponsesDB responsesDB = new ResponsesDB();
	   				if(anonimousUser instanceof AnonimousUser)
	   				{
	   					AnonimousUser anonumousUser2 = ((AnonimousUser) anonimousUser);
	   					String response = responsesDB.getAnonymousResponseValue(anonumousUser2.getId(), anonumousUser2.getSurveyId(), questionId, optionsGroupId);
	   					String responseOtherText = "";
	   					if(response.indexOf(DBConstants.s_VALUE_TOKEN) > -1)
	   					{
	   						String[] respParts = response.split(DBConstants.s_VALUE_TOKEN);
	   						response = respParts[0];
	   						if(respParts.length > 1) responseOtherText = respParts[1];
	   					}
	   					optionsGroup.put("response", response); 
	   					optionsGroup.put("responseOtherText", responseOtherText);
	   				}
	   				else
	   				{
	   					optionsGroup.put("response", "");	   					
	   				}
	   			}
	   			/*else if(optionType.equals(DBConstants.s_VALUE_OPTIONSGROUP_TYPE_CHECKBOX))
	   			{
	   				ResponsesDB responsesDB = new ResponsesDB();
	   				if(anonimousUser instanceof AnonimousUser)
	   				{
	   					AnonimousUser anonumousUser2 = ((AnonimousUser) anonimousUser);
	   					String response = responsesDB.getAnonymousOtherResponseValue(anonumousUser2.getId(), anonumousUser2.getSurveyId(), questionId);
	   					String responseOtherText = "";
	   					boolean responseOther = false;
	   					if(response.indexOf(DBConstants.s_VALUE_TOKEN) > -1)
	   					{
	   						String[] respParts = response.split(DBConstants.s_VALUE_TOKEN);
	   						responseOther = true;
	   						if(respParts.length > 1) responseOtherText = respParts[1];
	   					} 
	   					optionsGroup.put("responseOther", responseOther);
	   					optionsGroup.put("responseOtherText", responseOtherText);
	   				}
	   				else
	   				{
   						optionsGroup.put("responseOther", false); 
	   				}
	   			}*/
	   			
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();
	   			optionsGroup.put("contents", contentDB.getContentJsonByIdAndLanguage(contentId, lang, langdefault));
	   			
	   			optionsGroup.put("options", this.getOptionsJSONByOptionsGroupId(questionId, optionsGroupId, lang, langdefault, optionType, anonimousUser));
	   			
	   			optionsGroups.put(optionsGroup);
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

	public List<Option> getOptionsByOptionsGroupId(int optionsGroupId, String lang, String langdefault)
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
	   			
	   			int resource = rs.getInt(DBFieldNames.s_CONTENT_RESOURCE);
	   			if(resource>0){
	   				ResourceDB resourceDB = new ResourceDB();
	   				option.getResources().add(resourceDB.getResourceById(resource,lang,langdefault));
	   			}
	   			option.setOther(rs.getBoolean(DBFieldNames.s_OPTION_OTHER_OPTION));
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();
	   			option.setContents(contentDB.getContentByIdAndLanguage(contentId, lang,langdefault));
	   			
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

	
	public JSONArray getOptionsJSONByOptionsGroupId(int questionId, int optionsGroupId, String lang, String langdefault, String optionType, Object anonimousUser)
	{
		JSONArray options = new JSONArray();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_OPTION_BY_OPTIONSGROUPID);			
	   		pstm.setInt(1, optionsGroupId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			JSONObject option = new JSONObject();
	   			int optionId = rs.getInt(DBFieldNames.s_OPTIONID);
	   			option.put("optionId", optionId);
	   			option.put("index", rs.getInt(DBFieldNames.s_INDEX));
	   			boolean isOtherOption = rs.getBoolean(DBFieldNames.s_OPTION_OTHER_OPTION);
	   			option.put("otherOption", isOtherOption);
	   			if(optionType.equals(DBConstants.s_VALUE_OPTIONSGROUP_TYPE_CHECKBOX)) 
	   			{
	   				ResponsesDB responsesDB = new ResponsesDB();
	   				if(anonimousUser instanceof AnonimousUser)
	   				{
	   					AnonimousUser anonumousUser2 = ((AnonimousUser) anonimousUser);
	   					if(isOtherOption)
	   					{
		   					String response = responsesDB.getAnonymousOtherResponseValue(anonumousUser2.getId(), anonumousUser2.getSurveyId(), questionId);
		   					String responseOtherText = "";
		   					if(response.indexOf(DBConstants.s_VALUE_TOKEN) > -1)
		   					{
		   						String[] respParts = response.split(DBConstants.s_VALUE_TOKEN);
		   						response = respParts[0];
		   						if(respParts.length > 1) responseOtherText = respParts[1];
		   					}
		   					option.put("response", (!response.isEmpty() && response.equals(String.valueOf(optionId)))); 
		   					option.put("responseOtherText", responseOtherText);
	   					}
	   					else
	   					{
	   						option.put("response", responsesDB.existAnonymousResponseValue(anonumousUser2.getId(), anonumousUser2.getSurveyId(), questionId, optionsGroupId, Integer.toString(optionId)));
	   					}
	   				}
	   				else
	   				{
	   					option.put("response", false);
	   				}
	   			}
	   			
	   			int resource = rs.getInt(DBFieldNames.s_CONTENT_RESOURCE);
	   			if(resource>0){
	   				ResourceDB resourceDB = new ResourceDB();
	   				option.put("resource", resourceDB.getResourceJSONById(resource,lang,langdefault));
	   			}
	   			
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();
	   			option.put("contents", contentDB.getContentJsonByIdAndLanguage(contentId, lang, langdefault));
	   			
	   			options.put(option);
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

	public int getResourceIdByOptionId(int optionId)
	{
		Integer resourceId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_RESOURCEID_BY_OPTIONID);			
	   		pstm.setInt(1, optionId);
	   		
	   		rs = pstm.executeQuery();
	   		
	   		if(rs.next())
	   		{
	   			resourceId = rs.getInt(DBFieldNames.s_RESOURCEID);
	   			if(resourceId == null) resourceId = 0;
	   		}
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return resourceId;
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
			else{
				
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
		   if(contentId<0)
			   pstm.setString(1, null); 
		   else
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
	
	public boolean updateOptionsGroup(int optionsGroupId, int contentId, String type) {
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_OPTIONSGROUP);
			pstm.setString(1, type);
			pstm.setInt(2, contentId);
			pstm.setInt(3, optionsGroupId);
		   		
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

	public boolean updateOptionOther(int optionId, boolean value) {
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_OPTION_OTHER);
			pstm.setBoolean(1, value);
		   	pstm.setInt(2, optionId);
		   		
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
	
	public void removeOptionsGroupContent(int optionsGroupId) {
		//System.out.println("updateState");
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
			System.out.println("optionsGroupId="+optionsGroupId);
		   	pstm = con.prepareStatement(DBSQLQueries.s_REMOVE_OPTIONSGROUP_CONTENT);
		   	pstm.setInt(1, optionsGroupId);
		   		
			pstm.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		   
	}

}
