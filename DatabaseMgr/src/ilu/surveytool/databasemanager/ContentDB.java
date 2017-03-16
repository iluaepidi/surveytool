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
	
	public HashMap<String, Content> getContentByIdAndLanguage(int contentId, String lang, String langdefault)
	{
		HashMap<String, Content> contents = new HashMap<String, Content>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null, pstm2 = null;
		ResultSet rs = null,rs2 = null;
		
		if(lang==null)lang = langdefault;
		
		String text = "";
		String typename = "";
		String contenttext = "";
		String contentType = "";
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_CONTENT_BY_ID_LANGUAGE);			
	   		pstm.setInt(1, contentId);
	   		pstm.setString(2, lang);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			contentType = rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME);
	   			text = rs.getString(DBFieldNames.s_CONTENT_TEXT);
	   			typename = rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME);
	   			contenttext = rs.getString(DBFieldNames.s_CONTENT_TEXT);
	   			if(text!=null && !text.equals("")){
		   			contents.put(contentType, new Content(contentId,lang,typename,contenttext));
	   			}
	   		}
	   		
	   		
	   		if((text==null || text.equals(""))&&langdefault!=null){
	   			pstm2 = con.prepareStatement(DBSQLQueries.s_SELECT_CONTENT_BY_ID_LANGUAGE);			
		   		pstm2.setInt(1, contentId);
		   		pstm2.setString(2, langdefault);
		   		
		   		rs2 = pstm2.executeQuery();
		   		while(rs2.next())
		   		{
		   			contentType = rs2.getString(DBFieldNames.s_CONTENT_TYPE_NAME);
		   			text = rs2.getString(DBFieldNames.s_CONTENT_TEXT);
		   				contents.put(contentType, new Content(contentId, lang, 
			   					rs2.getString(DBFieldNames.s_CONTENT_TYPE_NAME), 
			   					rs2.getString(DBFieldNames.s_CONTENT_TEXT)));
		   			
		   		}
	   		}else{
	   			contents.put(contentType, new Content(contentId, lang, 
	   					typename, 
	   					contenttext));
	   		}
	   		
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return contents;
	}
	
	
	public int getCountContentByIdAndType(int contentId, String contentType)
	{
		int contents = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;		
		
		   
		try{
			pstm = con.prepareStatement(DBSQLQueries.s_SELECT_COUNT_CONTENT_BY_ID_CONTENTTYPE);			
	   		pstm.setInt(1, contentId);
	   		pstm.setString(2, contentType);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			contents = rs.getInt(DBFieldNames.s_COUNT_CONTENT);
	   		}
	   		
	   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return contents;
	}


	public JSONArray getContentJsonByIdAndLanguage(int contentId, String lang, String langdefault)
	{
		JSONArray contents = new JSONArray();

		Connection con = this._openConnection();
		PreparedStatement pstm = null, pstm2 = null;
		ResultSet rs = null,rs2 = null;
		
		if(lang==null)lang = langdefault;
				   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_CONTENT_BY_ID_LANGUAGE);			
	   		pstm.setInt(1, contentId);
	   		pstm.setString(2, lang);

	   		//System.out.println("[ContentDB-getContentJsonByIdAndLanguage] "+DBSQLQueries.s_SELECT_CONTENT_BY_ID_LANGUAGE+", "+contentId+" - "+lang);
	   		String text = "";
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			text = rs.getString(DBFieldNames.s_CONTENT_TEXT);
	   			
	   			if(text!=null && !text.equals("")){	
	   				JSONObject content = new JSONObject();
		   			content.put("contentId", contentId);
		   			content.put("lang", lang);
		   			content.put("contentType", rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME));
		   			content.put("text", text);
		   			contents.put(content);
	   			}
	   		}
	   		
	   		
	   		if((text==null || text.equals(""))&&langdefault!=null){
	   			pstm2 = con.prepareStatement(DBSQLQueries.s_SELECT_CONTENT_BY_ID_LANGUAGE);			
		   		pstm2.setInt(1, contentId);
		   		pstm2.setString(2, langdefault);
		   		//System.out.println("[ContentDB-getContentJsonByIdAndLanguage] default - "+DBSQLQueries.s_SELECT_CONTENT_BY_ID_LANGUAGE+", "+contentId+" - "+langdefault);
		   		rs2 = pstm2.executeQuery();
		   		while(rs2.next())
		   		{
		   			JSONObject content = new JSONObject();
		   			content.put("contentId", contentId);
		   			content.put("lang", lang);
		   			content.put("contentType", rs2.getString(DBFieldNames.s_CONTENT_TYPE_NAME));
		   			content.put("text", rs2.getString(DBFieldNames.s_CONTENT_TEXT));
		   			contents.put(content);
		   		}
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
		
		return contents;
	}
	public JSONArray getLongContentJsonByIdAndLanguage(int contentId, String lang, String langdefault)
	{
		JSONArray contentsJson = new JSONArray();
		
		try
		{
			HashMap<String, Content> contents = this.getLongContentByIdAndLanguage(contentId, lang, langdefault);
			for(String contentType : contents.keySet())
			{
				JSONObject contentJson = new JSONObject();
				Content content = contents.get(contentType);
				contentJson.put("contentId", content.getContentId());
				contentJson.put("lang", content.getLanguage());
				contentJson.put("contentType", content.getContentType());
				contentJson.put("text", content.getText());
				contentsJson.put(contentJson);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		return contentsJson;
	}
	
	public HashMap<String, Content> getLongContentByIdAndLanguage(int contentId, String lang, String langdefault)
	{
		HashMap<String, Content> contents = new HashMap<String, Content>();
		//System.out.println("new question");
		Connection con = this._openConnection();
		PreparedStatement pstm = null, pstm2 = null;
		ResultSet rs = null,rs2 = null;
		
		if(lang==null)lang = langdefault;
		
		String text = "";
		String typename = "";
		String contenttext = "";
		String contentType = "";
		int index = 1;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_LONG_CONTENT_BY_ID_LANGUAGE);			
	   		pstm.setInt(1, contentId);
	   		pstm.setString(2, lang);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			//System.out.println("Recorriendo los contents (contentID:"+contentId+"): contentName-"+rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME)+", index-"+rs.getInt(DBFieldNames.s_INDEX)+", text-" + rs.getString(DBFieldNames.s_CONTENT_TEXT));
	   			//System.out.println("Previous: typename-"+typename+", contentType-"+contentType+", text-"+text);
	   			if(typename.equals(rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME))){
	   				//System.out.println("In if(typename.equals(rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME)))");
	   				if(text!=null && !text.equals("")){
	   					//System.out.println("In if(text!=null && !text.equals(empty))");
	   					contenttext = contenttext + rs.getString(DBFieldNames.s_CONTENT_TEXT);
	   					text = text + rs.getString(DBFieldNames.s_CONTENT_TEXT);
		   			}
	   				index = rs.getInt(DBFieldNames.s_INDEX);
	   			}
	   			else{
	   				//System.out.println("In else");
	   				if(!contentType.equals("")){
	   					//System.out.println("In if(!contentType.equals(empty))");
	   					if(text!=null && !text.equals("")){
	   						//System.out.println("In if(text!=null && !text.equals(empty))");
		   					contents.put(contentType, new Content(contentId, lang, 
				   					typename, 
				   					contenttext));
	   					}
	   					contentType = "";
	   				}
	   				
	   					//System.out.println("In second else");
	   					contentType = rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME);
			   			text = rs.getString(DBFieldNames.s_CONTENT_TEXT);
			   			typename = rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME);
			   			contenttext = rs.getString(DBFieldNames.s_CONTENT_TEXT);
			   			index = rs.getInt(DBFieldNames.s_INDEX);
	   				
	   			}
	   		}

	   		if((text==null || text.equals(""))&&langdefault!=null){
	   			//System.out.println("In language by default");
	   			pstm2 = con.prepareStatement(DBSQLQueries.s_SELECT_LONG_CONTENT_BY_ID_LANGUAGE);			
		   		pstm2.setInt(1, contentId);
		   		pstm2.setString(2, langdefault);
		   		
		   		contentType = "";
		   		text="";
		   		
		   		rs2 = pstm2.executeQuery();
		   		while(rs2.next())
		   		{

		   			//System.out.println("In second while");
		   			if(contentType.equals(rs2.getString(DBFieldNames.s_CONTENT_TYPE_NAME))){
		   				//System.out.println("In if(contentType.equals(rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME)))");
		   				text = text+rs2.getString(DBFieldNames.s_CONTENT_TEXT);
		   				index = rs2.getInt(DBFieldNames.s_INDEX);
		   			}
		   			else{
		   				//System.out.println("In else");
		   				if(!contentType.equals("")){
		   					//System.out.println("In if(!contentType.equals(empty))");
			   				contents.put(contentType, new Content(contentId, lang, 
			   						contentType, 
				   					text));
			   				contentType = "";
		   				}
		   					//System.out.println("In second else");
				   			contentType = rs2.getString(DBFieldNames.s_CONTENT_TYPE_NAME);
				   			index = rs2.getInt(DBFieldNames.s_INDEX);
				   			text = rs2.getString(DBFieldNames.s_CONTENT_TEXT);
		   				
		   			}
		   		}
		   		if(!contentType.equals("")){
		   			//System.out.println("In if(!contentType.equals(empty))");
		   			contents.put(contentType, new Content(contentId, lang, 
	   						contentType, 
		   					text));
		   		}
	   		}else{
	   			//System.out.println("In final else: contents.put(contentType-"+contentType+", new Content(contentId-"+contentId+", lang-"+lang+", typename-"+typename+",	contenttext-"+contenttext+")");
	   			//System.out.println("contents length before:"+contents.size());
	   			contents.put(contentType, new Content(contentId, lang, 
	   					typename, 
	   					contenttext));
	   			//System.out.println("contents length after:"+contents.size());
	   		}
	   		
	   		//System.out.println("Out of all at the end of the method");
	   		/*for(int i=0;i<contents.size();i++)
	   			System.out.println(contents.get(i));
	   		*/
	   		
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
	
	public HashMap<String, Content> getLongContentByIdLanguageContentType(int contentId, String language, String contentType)
	{
		HashMap<String, Content> contents = new HashMap<String, Content>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
			pstm = con.prepareStatement(DBSQLQueries.s_SELECT_LONG_CONTENT_BY_ID_LANGUAGE_CONTENTTYPE);
	    	pstm.setInt(1, contentId);
			pstm.setString(2, language); 
			pstm.setString(3, contentType);
			String text = ""; 
			String contentTypeName = "";
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			text=text+rs.getString(DBFieldNames.s_CONTENT_TEXT);
	   			contentTypeName = rs.getString(DBFieldNames.s_CONTENT_TYPE_NAME);
	   		}
	   		contents.put(contentType, new Content(contentId, language, 
	   				contentTypeName, 
	   				text));
	   		
	   		
	   		
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
		List<String> textChain = new ArrayList<String>();
		if(text.length()>DBConstants.s_MAX_CONTENT_LENGTH){
			int lowerBound = 0;
			int upperBound = DBConstants.s_MAX_CONTENT_LENGTH;
			while (upperBound<=text.length()){
				textChain.add(text.substring(lowerBound, upperBound));
				lowerBound = upperBound+1;
				upperBound = upperBound + DBConstants.s_MAX_CONTENT_LENGTH;
				if((lowerBound<=text.length()) && (upperBound>text.length())){
					upperBound = text.length();
				}
			}
		}
		else{
			textChain.add(text);
		}
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
	    if(getLongContentByIdLanguageContentType(contentId, language, contentType).isEmpty()){
	    	int index = 0;
	    	while(index<textChain.size()){
			   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_CONTENT);
			   pstm.setInt(1, contentId);
			   pstm.setString(2, language); 
			   pstm.setString(3, contentType); 
			   pstm.setInt(4, index+1);
			   pstm.setString(5, textChain.get(index));
			   
			   notInserted = pstm.execute();
			   index++;
	    	}
	    }
	    else{
	    	removeContentByTypeLang(contentId, language, contentType);
	    	int index = 0;
	    	while(index<textChain.size()){
			   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_CONTENT);
			   pstm.setInt(1, contentId);
			   pstm.setString(2, language); 
			   pstm.setString(3, contentType); 
			   pstm.setInt(4, index+1);
			   pstm.setString(5, textChain.get(index));
			   
			   notInserted = pstm.execute();
			   index++;
	    	}
	    }
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
	
	/*public void updateContentText(int contentId, String language, String contentType, String text) {
		//System.out.println("updateState");
		Connection con = this._openConnection();
		PreparedStatement pstm = null,pstm2 = null;;
		
		
		
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_CONTENT_TEXT);
			pstm.setString(1, text);
			pstm.setInt(2, contentId);
			pstm.setString(3, language);
			pstm.setString(4, contentType);
		   		
			int numUpdated = pstm.executeUpdate();
			
			if(numUpdated<1){
				//es necesario insertarlo
				this.insertContent(contentId, language, contentType, text);
				
			}
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		   
	}*/
	
	
	public void updateContentText(int contentId, String language, String contentType, String text) {
		List<String> textChain = new ArrayList<String>();
		if(text.length()>DBConstants.s_MAX_CONTENT_LENGTH){
			int lowerBound = 0;
			int upperBound = DBConstants.s_MAX_CONTENT_LENGTH;
			while (upperBound<=text.length()){
				textChain.add(text.substring(lowerBound, upperBound));
				lowerBound = upperBound+1;
				upperBound = upperBound + DBConstants.s_MAX_CONTENT_LENGTH;
				if((lowerBound<=text.length()) && (upperBound>text.length())){
					upperBound = text.length();
				}
			}
		}
		else{
			textChain.add(text);
		}
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		    if(!getLongContentByIdLanguageContentType(contentId, language, contentType).isEmpty())
		    	removeContentByTypeLang(contentId, language, contentType);		    
		    
		    int index = 0;
	    	while(index<textChain.size()){
			   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_CONTENT);
			   pstm.setInt(1, contentId);
			   pstm.setString(2, language); 
			   pstm.setString(3, contentType); 
			   pstm.setInt(4, index+1);
			   pstm.setString(5, textChain.get(index));
			   
			   pstm.execute();
			   index++;
	    	}
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
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

	public void removeContentByType(int contentId, String contentType) {
		//System.out.println("removeUserOptionValues");
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_CONTENT_BY_ID_TYPE);
		   	pstm.setInt(1, contentId);
			pstm.setString(2, contentType);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}
	

}
