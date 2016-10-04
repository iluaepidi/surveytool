package ilu.surveytool.databasemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveytool.databasemanager.DataObject.AnonimousUser;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LogicGoTo;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Option;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;
import ilu.surveytool.databasemanager.DataObject.Project;
import ilu.surveytool.databasemanager.DataObject.QDependence;
import ilu.surveytool.databasemanager.DataObject.QDependenceValue;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Questionnaire;
import ilu.surveytool.databasemanager.DataObject.Resource;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.databasemanager.constants.DBSQLQueries;
import ilu.surveytool.databasemanager.factory.ConnectionFactoryJDBC;

public class QuestionDB {

	public QuestionDB() {
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
	

	public List<Question> getQuestionsBySurveyId(int surveyId, String lang, String langdefault)
	{
		List<Question> questions = new ArrayList<Question>();
		//System.out.println("Get questions by survey Id="+surveyId);
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_BY_SURVEYID);			
	   		pstm.setInt(1, surveyId);
	   		
	   		rs = pstm.executeQuery();

	   		questions = this._getQuestionList(rs, lang, langdefault);
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questions;
	}

	public List<Question> getQuestionsBySectionId(int sectionId, String lang, String langdefault)
	{
		List<Question> questions = new ArrayList<Question>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_BY_SECTIONID);			
	   		pstm.setInt(1, sectionId);
	   		
	   		rs = pstm.executeQuery();

	   		questions = this._getQuestionList(rs, lang, langdefault);
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questions;
	}

	public List<Question> getQuestionsByPageId(int pageId, String lang,String langdefault)
	{
		List<Question> questions = new ArrayList<Question>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_BY_PAGEID);			
	   		pstm.setInt(1, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		questions = this._getQuestionList(rs, lang, langdefault);
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questions;
	}

	public JSONArray getQuestionsJsonByPageId(int pageId, String lang, String langdefault, Object anonimousUser)
	{
		JSONArray questions = new JSONArray();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_BY_PAGEID);			
	   		pstm.setInt(1, pageId);
	   		System.out.println("[QuestionDB-getQuestionsJsonByPageId] "+DBSQLQueries.s_SELECT_QUESTION_BY_PAGEID+", pageId:"+pageId);
	   		rs = pstm.executeQuery();
	   		questions = this._getQuestionJsonArray(rs, lang, langdefault, anonimousUser);
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questions;
	}

	public List<Question> getQuestionsIdIndexByPageId(int pageId)
	{
		List<Question> questions = new ArrayList<Question>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_BY_PAGEID);			
	   		pstm.setInt(1, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		
	   		while(rs.next())
	   		{
	   			Question question = new Question();
	   			
	   			question.setQuestionId(rs.getInt(DBFieldNames.s_QUESTION_ID));
	   			question.setIndex(rs.getInt(DBFieldNames.s_INDEX));
	   			
	   			questions.add(question);
	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questions;
	}

	public List<Question> getQuestionsByPollId(int pollId, String lang)
	{
		List<Question> questions = new ArrayList<Question>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_BY_POLLID);			
	   		pstm.setInt(1, pollId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();

	   			HashMap<String, Content> contents = contentDB.getLongContentByIdAndLanguage(contentId, lang,null);	   			
	   			QuestionParameterDB questionParameterDB = new QuestionParameterDB();
	   			HashMap<String, String> parameters = questionParameterDB.getQuestionParameterPollByPollIDQuestionID(pollId, rs.getInt(DBFieldNames.s_QUESTION_ID));
	   			QDependenceDB qdependenceDB = new QDependenceDB();
	   			QDependence qdependence = qdependenceDB.getQDependenceByQuestionId(rs.getInt(DBFieldNames.s_QUESTION_ID), lang);
	   			LogicGoToDB logicGoToDB = new LogicGoToDB();
	   			List<LogicGoTo> logicGoTo = logicGoToDB.getLogicGoToByQuestionId(rs.getInt(DBFieldNames.s_QUESTION_ID), lang);
	   			
	   			Question question = new Question(rs.getInt(DBFieldNames.s_QUESTION_ID), 
	   					rs.getString(DBFieldNames.s_QUESTION_TAG), 
	   					null, 
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_NAME), 
	   					contents, 
	   					rs.getString(DBFieldNames.s_CATEGORY_NAME), 
	   					true,  
	   					false,
	   					false,
	   					parameters,
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_TEMPLATE_FILE),
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_FORM_FILE),
	   					//rs.getString(DBFieldNames.s_QUESTIONTYPE_STATISTICRESULTS_FILE),
	   					null,
	   					qdependence,
	   					logicGoTo);
	   			
	   			OptionDB optionDB = new OptionDB();
	   			question.setOptionsGroups(optionDB.getOptionsGroupByQuestionId(question.getQuestionId(), lang,null));
	   			
	   			ResourceDB resourceDB = new ResourceDB();
	   			question.setResources(resourceDB.getResourcesByQuestionId(question.getQuestionId(), lang));
	   			
	   			questions.add(question);
	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questions;
	}

	public List<Question> getQuestionsByPageId(int pageId, int currentIndex, int prevIndex)
	{
		List<Question> questions = new ArrayList<Question>();
		
		String maxMin = this._maxMinIndex(currentIndex, prevIndex);
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String query = DBSQLQueries.s_SELECT_QUESTIONBYPAGE_BY_PAGEID_MAX_MIN.replaceAll("##", maxMin);
		   
		try{
		   	pstm = con.prepareStatement(query);			
	   		pstm.setInt(1, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			Question question = new Question();
	   			
	   			question.setQuestionId(rs.getInt(DBFieldNames.s_QUESTION_ID));
	   			question.setIndex(rs.getInt(DBFieldNames.s_INDEX));
	   			
	   			questions.add(question);
	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return questions;
	}
	
	public String getQuestionTypeTemplateFileByName(String questionType)
	{
		String response = "";
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONTYPE_TEMPLATE_FILE_BY_ID);			
	   		pstm.setString(1, questionType);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			response = rs.getString(DBFieldNames.s_QUESTIONTYPE_TEMPLATE_FILE);
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return response;
	}
	
	public HashMap<String, String> getQuestionContentByQuestionId(int questionId, String lang)
	{
		HashMap<String, String> contents = new HashMap<String, String>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_CONTENT_BY_QUESTIONID);			
	   		pstm.setInt(1, questionId);
	   		pstm.setString(2, lang);
	   		
	   		rs = pstm.executeQuery();
	   		while(rs.next())
	   		{
	   			contents.put(rs.getString(DBFieldNames.s_QUESTIONTYPE_NAME), rs.getString(DBFieldNames.s_CONTENT_TEXT));	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return contents;
	}
	
	
	public int getQuestionTypeByQuestionID (int questionId){
		int type = -1;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_TYPE_QUESTIONID);			
	   		pstm.setInt(1, questionId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			type = rs.getInt(DBFieldNames.s_IDQUESTIONTYPE);
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return type;
	}
	
	public HashMap<Integer,OptionsGroup> getQuestionContentsByQuestionIdLang(int questionId, String defaultLanguage)
	{
		HashMap<Integer,OptionsGroup> contents = new HashMap<Integer,OptionsGroup>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		
		   
		try{
			
			pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_CONTENTS_QUESTIONID_LANGUAGE);			
		   		pstm.setInt(1, questionId);
		   		pstm.setString(2, defaultLanguage);
		   		rs = pstm.executeQuery();
		   		int ogID = -1;
		   		
		   		while(rs.next())
		   		{
		   			if((contents.isEmpty()) || (!contents.containsKey(rs.getInt(DBFieldNames.s_OPTIONSGROUPID)))){
		   				HashMap<String, Content> contentsOG = new HashMap<String, Content>();
		   				Content c = new Content();
		   				c.setText(rs.getString(DBFieldNames.s_CONTENT_OG));
		   				contentsOG.put("text", c);
		   				
		   				List<Option> options = new ArrayList<Option>();
		   				HashMap<String, Content> contentsO = new HashMap<String, Content>();
		   				c = new Content();
		   				c.setText(rs.getString(DBFieldNames.s_CONTENT_OPTIONS));
		   				contentsO.put("text", c);
		   				options.add(new Option(rs.getInt(DBFieldNames.s_OPTIONID), contentsO, 0));
		   				
		   				OptionsGroup oG = new OptionsGroup(rs.getInt(DBFieldNames.s_OPTIONSGROUPID), contentsOG, "", false, 0, 0, new ArrayList<Option>());
		   				
		   				contents.put(rs.getInt(DBFieldNames.s_OPTIONSGROUPID),oG);
		   			}
		   			else{
		   				HashMap<String, Content> contentsO = new HashMap<String, Content>();
		   				Content c = new Content();
		   				c.setText(rs.getString(DBFieldNames.s_CONTENT_OPTIONS));
		   				contentsO.put("text", c);
		   				contents.get(rs.getInt(DBFieldNames.s_OPTIONSGROUPID)).getOptions().add(new Option(rs.getInt(DBFieldNames.s_OPTIONID), contentsO, 0));
		   			}
		   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return contents;
	}
	
	public HashMap<Integer,HashMap<Integer,OptionsGroup>> getSurveyQuestionsContentsLang(int questionnaireId, String defaultLanguage)
	{
		HashMap<Integer,HashMap<Integer,OptionsGroup>> contents = new HashMap<Integer,HashMap<Integer,OptionsGroup>>();
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
			
			pstm = con.prepareStatement(DBSQLQueries.s_SELECT_SURVEY_QUESTION_CONTENTS_SURVEYID_LANGUAGE);			
	   		pstm.setInt(1, questionnaireId);
	   		pstm.setString(2, defaultLanguage);
	   		rs = pstm.executeQuery();
	   		int ogID = -1;
	   		
	   		while(rs.next())
	   		{
	   			if((contents.isEmpty()) || (!contents.containsKey(rs.getInt(DBFieldNames.s_QUESTION_ID)))){
	   				HashMap<String, Content> contentsOG = new HashMap<String, Content>();
	   				Content c = new Content();
	   				c.setText(rs.getString(DBFieldNames.s_CONTENT_OG));
	   				contentsOG.put("text", c);
	   				
	   				List<Option> options = new ArrayList<Option>();
	   				HashMap<String, Content> contentsO = new HashMap<String, Content>();
	   				c = new Content();
	  				c.setText(rs.getString(DBFieldNames.s_CONTENT_OPTIONS));
	   				contentsO.put("text", c);
	   				options.add(new Option(rs.getInt(DBFieldNames.s_OPTIONID), contentsO, 0));
		   				
	   				OptionsGroup oG = new OptionsGroup(rs.getInt(DBFieldNames.s_OPTIONSGROUPID), contentsOG, "", false, 0, 0, options);
		   				
	   				HashMap<Integer,OptionsGroup> contentsQuestion = new HashMap<Integer,OptionsGroup>();
	   				contentsQuestion.put(rs.getInt(DBFieldNames.s_OPTIONSGROUPID),oG);
		   				
	   				contents.put(rs.getInt(DBFieldNames.s_QUESTION_ID),contentsQuestion);
	   				//System.out.println("No existía el questionId. He insertado: qId="+rs.getInt(DBFieldNames.s_QUESTION_ID)+", ogId="+rs.getInt(DBFieldNames.s_OPTIONSGROUPID)+", oId="+rs.getInt(DBFieldNames.s_OPTIONID));
	   			}
	   			else{
	   				//System.out.println("Ya existía el questionId");
	   				if((!(contents.get(rs.getInt(DBFieldNames.s_QUESTION_ID))).containsKey(rs.getInt(DBFieldNames.s_OPTIONSGROUPID)))){
		   				HashMap<String, Content> contentsOG = new HashMap<String, Content>();
		   				Content c = new Content();
		   				c.setText(rs.getString(DBFieldNames.s_CONTENT_OG));
		   				contentsOG.put("text", c);
		   				
		   				List<Option> options = new ArrayList<Option>();
		   				HashMap<String, Content> contentsO = new HashMap<String, Content>();
		   				c = new Content();
		   				c.setText(rs.getString(DBFieldNames.s_CONTENT_OPTIONS));
		   				contentsO.put("text", c);
		   				options.add(new Option(rs.getInt(DBFieldNames.s_OPTIONID), contentsO, 0));
			   				
		   				OptionsGroup oG = new OptionsGroup(rs.getInt(DBFieldNames.s_OPTIONSGROUPID), contentsOG, "", false, 0, 0, options);
			   				
		   				contents.get(rs.getInt(DBFieldNames.s_QUESTION_ID)).put(rs.getInt(DBFieldNames.s_OPTIONSGROUPID),oG);
		   				//System.out.println("No existía el optionsgroup. He insertado: qId="+rs.getInt(DBFieldNames.s_QUESTION_ID)+", ogId="+rs.getInt(DBFieldNames.s_OPTIONSGROUPID)+", oId="+rs.getInt(DBFieldNames.s_OPTIONID));
		   			}
		   			else{
		   				HashMap<String, Content> contentsO = new HashMap<String, Content>();
		   				Content c = new Content();
		   				c.setText(rs.getString(DBFieldNames.s_CONTENT_OPTIONS));
		   				contentsO.put("text", c);
		   				List<Option> options = contents.get(rs.getInt(DBFieldNames.s_QUESTION_ID)).get(rs.getInt(DBFieldNames.s_OPTIONSGROUPID)).getOptions();
		   				//System.out.println(options.size());
		   				options.add(new Option(rs.getInt(DBFieldNames.s_OPTIONID), contentsO, 0));
		   				//System.out.println(options.size());
		   				
		   				HashMap<String, Content> contentsOG = new HashMap<String, Content>();
		   				c = new Content();
		   				c.setText(rs.getString(DBFieldNames.s_CONTENT_OG));
		   				contentsOG.put("text", c);
		   				
		   				OptionsGroup oG = new OptionsGroup(rs.getInt(DBFieldNames.s_OPTIONSGROUPID), contentsOG, "", false, 0, 0, options);
		   				//System.out.println(oG.getOptions().size());
		   				
		   				//System.out.println(contents.get(rs.getInt(DBFieldNames.s_QUESTION_ID)).get(rs.getInt(DBFieldNames.s_OPTIONSGROUPID)).getOptions().size());
		   				contents.get(rs.getInt(DBFieldNames.s_QUESTION_ID)).put(rs.getInt(DBFieldNames.s_OPTIONSGROUPID),oG);
		   				
		   				//System.out.println("Ya existía el optionsgroup. He insertado("+contents.get(rs.getInt(DBFieldNames.s_QUESTION_ID)).get(rs.getInt(DBFieldNames.s_OPTIONSGROUPID)).getOptions().size()+"): qId="+rs.getInt(DBFieldNames.s_QUESTION_ID)+", ogId="+rs.getInt(DBFieldNames.s_OPTIONSGROUPID)+", oId="+rs.getInt(DBFieldNames.s_OPTIONID));
		   			}
	   			}
	   		}
	   		
	   		/*Iterator it = contents.entrySet().iterator();
			while (it.hasNext()) {
			    Map.Entry pair = (Map.Entry)it.next();
			    HashMap<Integer,OptionsGroup> contents2 = (HashMap<Integer,OptionsGroup>)pair.getValue();
			    
			    Iterator it2 = contents2.entrySet().iterator();
				while (it2.hasNext()) {
				    Map.Entry pair2 = (Map.Entry)it2.next();
				    OptionsGroup contents3 = (OptionsGroup)pair2.getValue();
				    List<Option> options = contents3.getOptions();
				    for (int i=0;i<options.size();i++)
				    	System.out.println("qID:"+((Integer)pair.getKey()).intValue()+", optionsGroup:"+contents3.getId()+", option:"+options.get(i).getId());
				}
			}*/
			
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return contents;
	}
	
	
	
	public int getQuestionContentIdByQuestionId(int questionId)
	{
		int contentId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTION_CONTENTID_BY_QUESTIONID);			
	   		pstm.setInt(1, questionId);
	   		
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
	
	public boolean getQuestionByPageMandatory(int questionId, int pageId)
	{
		boolean mandatory = false;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONBYPAGE_MANDATORY);			
	   		pstm.setInt(1, questionId);
	   		pstm.setInt(2, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			mandatory = rs.getBoolean(DBFieldNames.s_QUESTION_MANDATORY);	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return mandatory;
	}	
	
	
	public boolean getQuestionByPageOptionalAnswer(int questionId, int pageId)
	{
		boolean optionalAnswer = false;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONBYPAGE_OPTIONALANSWER);			
	   		pstm.setInt(1, questionId);
	   		pstm.setInt(2, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			optionalAnswer = rs.getBoolean(DBFieldNames.s_QUESTION_OPTIONALANSWER);	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return optionalAnswer;
	}
	
	public int getQuestionByPageIndex(int questionId, int pageId)
	{
		int index = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
			
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONBYPAGE_INDEX);			
	   		pstm.setInt(1, questionId);
	   		pstm.setInt(2, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			index = rs.getInt(DBFieldNames.s_INDEX);	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return index;
	}

	public int getQuestionByPageIdIndex(int index, int pageId)
	{
		int questionId = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
			
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_QUESTIONBYPAGE_QUESTIONID_BY_PAGEID_INDEX);			
	   		pstm.setInt(1, pageId);
	   		pstm.setInt(2, index);
	   		
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

	public int getNumQuestionByPage(int pageId)
	{
		int numQuestions = 0;
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		   
		try{
			
		   	pstm = con.prepareStatement(DBSQLQueries.s_SELECT_NUMQUESTION_BY_PAGE);			
	   		pstm.setInt(1, pageId);
	   		
	   		rs = pstm.executeQuery();
	   		if(rs.next())
	   		{
	   			numQuestions = rs.getInt(DBFieldNames.s_NUM_QUESTION);	   			
	   		}
	   		
	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, rs);
		}
		
		return numQuestions;
	}
	
	
	/**
	 * Inserts 
	 */
	
	public int insertQuestion(Question question, int contentId) {
		System.out.println("inserQuestion");
		int questionId = 0;
		//int contentId = this.insertContentIndex();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_QUESTION, Statement.RETURN_GENERATED_KEYS);
		   pstm.setString(1, question.getTag());
		   pstm.setString(2, question.getQuestionType()); 
		   pstm.setInt(3, contentId); 
		   pstm.setString(4, question.getCategory()); 
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   ResultSet rs = pstm.getGeneratedKeys();
			   if(rs.next())
				   questionId = rs.getInt(1);
		   }
		  		  	   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    finally {
			this._closeConnections(con, pstm, null);
		}
		
		return questionId;
	}

	public boolean insertQuestionByPage(int questionId, int pageId, boolean mandatory, boolean optionalAnswer, int numQuestion, HashMap<String, String> parameters) {
		System.out.println("inserQuestionByPage");
		boolean inserted = false;
		//int contentId = this.insertContentIndex();
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
	    try {
		   pstm = con.prepareStatement(DBSQLQueries.s_INSERT_QUESTION_BY_PAGE, Statement.RETURN_GENERATED_KEYS);
		   pstm.setInt(1, pageId);
		   pstm.setInt(2, questionId);
		   pstm.setInt(3, numQuestion);
		   pstm.setBoolean(4, mandatory);
		   pstm.setBoolean(5, optionalAnswer);
		   
		   boolean notInserted = pstm.execute();
		   
		   if(!notInserted)
		   {
			   inserted = true;
		   }
		   
		   if (parameters !=null){
			   QuestionParameterDB qpDB = new QuestionParameterDB();
			   Iterator<String> iter = parameters.keySet().iterator();
			   while(iter.hasNext())
			   {
				   String parameterName = iter.next();
				   String value = parameters.get(parameterName);
				   qpDB.insertQuestionParameter(pageId, questionId, parameterName, value);
			   }
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
	
	public void updateQuestionMandatory(int questionId, int pageId, boolean mandatory) {
		System.out.println("updateQuestionMandatory");
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_QUESTIONBYPAGE_MANDATORY);
			pstm.setBoolean(1, mandatory);
			pstm.setInt(2, pageId);
			pstm.setInt(3, questionId);
		   		
			int numUpdated = pstm.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		   
	}
	
	public void updateQuestionOptionalAnswer(int questionId, int pageId, boolean optionalAnswer) {
		System.out.println("updateQuestionOptionalAnswer");
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_QUESTIONBYPAGE_OPTIONALANSWER);
			pstm.setBoolean(1, optionalAnswer);
			pstm.setInt(2, pageId);
			pstm.setInt(3, questionId);
		   		
			int numUpdated = pstm.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		   
	}

	public void updateQuestionIndex(int questionId, int pageId, int index) {
		//System.out.println("updateState");
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_QUESTIONBYPAGE_INDEX);
			pstm.setInt(1, index);
			pstm.setInt(2, pageId);
			pstm.setInt(3, questionId);
		   		
			int numUpdated = pstm.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		   
	}

	public void updateQuestionIndexPageId(int questionId, int pageId, int newPageId, int index) {
		//System.out.println("updateState");
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_QUESTIONBYPAGE_INDEX_PAGEID);
			pstm.setInt(1, index);
			pstm.setInt(2, newPageId);
			pstm.setInt(3, pageId);
			pstm.setInt(4, questionId);
		   		
			int numUpdated = pstm.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}
		   
	}
	
	public boolean updateOptionsGroupType(int questionId, String type) {
		System.out.println("updateOptionsGroupType: "+type);
		boolean updated = false;
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_UPDATE_OPTIONSGROUP_TYPE);
			pstm.setString(1, type);
			pstm.setInt(2, questionId);
		   		
			//System.out.println(DBSQLQueries.s_UPDATE_OPTIONSGROUP_TYPE +": qID: " +questionId+", type: "+type);
			int numUpdated = pstm.executeUpdate();
			
			if(numUpdated > 0)
			{
				updated = true;
				//System.out.println("numUpdated: "+numUpdated);
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
	
	public void removeQuestionByPage(int questionId, int pageId) {
		//System.out.println("removeUserOptionValues");
		
		Connection con = this._openConnection();
		PreparedStatement pstm = null;
		   
		try{
		   	pstm = con.prepareStatement(DBSQLQueries.s_DELETE_QUESTION_BY_PAGE);
		   	pstm.setInt(1, pageId);
		   	pstm.setInt(2, questionId);
	   		
		   	pstm.execute();
		   	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this._closeConnections(con, pstm, null);
		}

	}
	
	private String _maxMinIndex(int currentIndex, int prevIndex)
	{
		String maxMin = "";
		
		if(prevIndex != 0 && prevIndex < currentIndex)
		{
			maxMin = "and `index` > " + prevIndex + " and `index` <= " + currentIndex;
		}
		else if(prevIndex != 0 && currentIndex < prevIndex)
		{
			maxMin = "and `index` >= " + currentIndex + " and `index` <= " + prevIndex;
		}
		else if(prevIndex == 0)
		{
			maxMin = "and `index` > " + prevIndex;
		}
		
		return maxMin;
	}
	
	private List<Question> _getQuestionList(ResultSet rs, String lang, String langdefault)
	{
		List<Question> questions = new ArrayList<Question>();
		
		try
		{
			while(rs.next())
	   		{
	   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
	   			ContentDB contentDB = new ContentDB();	   			

				HashMap<String, Content> contents = contentDB.getLongContentByIdAndLanguage(contentId, lang, langdefault);
	   			
	   			QuestionParameterDB questionParameterDB = new QuestionParameterDB();
	   			HashMap<String, String> parameters = questionParameterDB.getQuestionParameterByPageIDQuestionID(rs.getInt(DBFieldNames.s_PAGE_ID), rs.getInt(DBFieldNames.s_QUESTION_ID));
	   			QDependenceDB qdependenceDB = new QDependenceDB();
	   			QDependence qdependence = qdependenceDB.getQDependenceByQuestionId(rs.getInt(DBFieldNames.s_QUESTION_ID), lang);
	   			LogicGoToDB logicGoToDB = new LogicGoToDB();
	   			List<LogicGoTo> logicGoTo = logicGoToDB.getLogicGoToByQuestionId(rs.getInt(DBFieldNames.s_QUESTION_ID), lang);
	   			
	   			Question question = new Question(rs.getInt(DBFieldNames.s_QUESTION_ID), 
	   					rs.getString(DBFieldNames.s_QUESTION_TAG), 
	   					null, 
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_NAME), 
	   					contents, 
	   					rs.getString(DBFieldNames.s_CATEGORY_NAME), 
	   					rs.getBoolean(DBFieldNames.s_QUESTION_MANDATORY), 
	   					rs.getBoolean(DBFieldNames.s_QUESTION_OPTIONALANSWER),
	   					false,
	   					parameters,
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_TEMPLATE_FILE),
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_FORM_FILE),
	   					rs.getString(DBFieldNames.s_QUESTIONTYPE_STATISTICRESULTS_FILE),
	   					qdependence,
	   					logicGoTo);
	   			
	   			question.setIndex(rs.getInt(DBFieldNames.s_INDEX));
	   			
	   			OptionDB optionDB = new OptionDB();

	   			question.setOptionsGroups(optionDB.getOptionsGroupByQuestionId(question.getQuestionId(), lang, langdefault));
	   			
	   			ResourceDB resourceDB = new ResourceDB();
	   			question.setResources(resourceDB.getResourcesByQuestionId(question.getQuestionId(), lang));
	   			
	   			questions.add(question);
	   			
	   		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return questions;
	}
	

	private JSONArray _getQuestionJsonArray(ResultSet rs, String lang, String langdefault, Object anonimousUser)
	{
		JSONArray questions = new JSONArray();
		
		try
		{
			
			while(rs.next())
	   		{
				
				System.out.println("[QuestionDB-_getQuestionJsonArray] userId:"+((AnonimousUser) anonimousUser).getId()+", lang:"+lang+", langdefault:"+langdefault);
				int questionId = rs.getInt(DBFieldNames.s_QUESTION_ID);
				boolean hidden = false;
				
				if(anonimousUser instanceof AnonimousUser)
   				{
   					AnonimousUser anonymousUser2 = ((AnonimousUser) anonimousUser);
					QDependenceDB qDependeceDB = new QDependenceDB();
					QDependence qdependence = qDependeceDB.getQDependenceByQuestionIdWithoutTexts(questionId);
					
					if(qdependence!=null && !qdependence.getqdepval().isEmpty()){
						System.out.println("There are dependences realted to this question: "+questionId);
						ResponsesDB responsesDB = new ResponsesDB();
						
						if(qdependence.getDependenceType().equals(DBConstants.s_VALUE_DEPENDENCETYPE_AND)){
							hidden = true;
						}
						else{
							hidden = false;
						}
							
						System.out.println("The dependence type is: "+qdependence.getDependenceType()+", so hidden="+hidden);
						for(QDependenceValue qdepval:qdependence.getqdepval()){
							System.out.println("Check the answer:"+qdepval.getQid()+", "+qdepval.getOgid()+", "+Integer.toString(qdepval.getOid()));
							if(qdependence.getDependenceType().equals(DBConstants.s_VALUE_DEPENDENCETYPE_AND)){
								hidden = hidden && responsesDB.haveExpectedAnswer(anonymousUser2.getId(), qdepval.getQid(), qdepval.getOgid(), Integer.toString(qdepval.getOid()));
							}
							else{
								hidden = hidden || responsesDB.haveExpectedAnswer(anonymousUser2.getId(), qdepval.getQid(), qdepval.getOgid(), Integer.toString(qdepval.getOid()));
							}
						}
						System.out.println("Final value of hidden:"+hidden);
					}
   				}

				if(!hidden){
					
					JSONObject question = new JSONObject();
		   			int contentId = rs.getInt(DBFieldNames.s_CONTENTID);
		   			ContentDB contentDB = new ContentDB();	
		   			String questionType = rs.getString(DBFieldNames.s_QUESTIONTYPE_NAME);
		   			if(questionType.equals("bcontent"))
		   			{
		   				question.put("contents", contentDB.getLongContentJsonByIdAndLanguage(contentId, lang, null));
		   			}
		   			else
		   			{
		   				question.put("contents", contentDB.getContentJsonByIdAndLanguage(contentId, lang, null));
		   			}
					
		   			QuestionParameterDB questionParameterDB = new QuestionParameterDB();
		   			question.put("parameters", questionParameterDB.getQuestionParameterJSONByPageIDQuestionID(rs.getInt(DBFieldNames.s_PAGE_ID), rs.getInt(DBFieldNames.s_QUESTION_ID)));
		   			
		   			//int questionId = rs.getInt(DBFieldNames.s_QUESTION_ID);
		   			question.put("questionId", questionId); 
		   			question.put("tag", rs.getString(DBFieldNames.s_QUESTION_TAG));
		   			question.put("questionType", questionType);
		   			question.put("mandatory", rs.getBoolean(DBFieldNames.s_QUESTION_MANDATORY));
		   			question.put("optionAlAnswer", rs.getBoolean(DBFieldNames.s_QUESTION_OPTIONALANSWER));
		   			question.put("questionJspPath", rs.getString(DBFieldNames.s_QUESTIONTYPE_FORM_FILE));
		   			
		   			question.put("index", rs.getInt(DBFieldNames.s_INDEX));
		   			
		   			OptionDB optionDB = new OptionDB();
		   			
		   			JSONArray optionsGroups = optionDB.getOptionsGroupJSONByQuestionId(questionId, lang, langdefault, anonimousUser);
		   			question.put("optionsGroups", optionsGroups);
		   			
		   			if(optionsGroups.length() == 0)
		   			{
		   				ResponsesDB responsesDB = new ResponsesDB();
		   				if(anonimousUser instanceof AnonimousUser)
		   				{
		   					AnonimousUser anonumousUser2 = ((AnonimousUser) anonimousUser);
		   					String value = responsesDB.getAnonymousResponseValue(anonumousUser2.getId(), anonumousUser2.getSurveyId(), questionId, null);
		   					try
		   					{
		   						Float valFloat = Float.valueOf(value);
		   						question.put("response", valFloat);
		   					}catch(NumberFormatException e){
		   						question.put("response", value);
		   					}
		   					
		   				}
		   				else
		   				{
		   					question.put("response", "");
		   				}
		   			}
		   			
		   			ResourceDB resourceDB = new ResourceDB();
		   			question.put("resources", resourceDB.getResourcesJsonByQuestionId(questionId, lang));
		   			
		   			questions.put(question);
		   			
				}
	   		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return questions;
	}
	
}
