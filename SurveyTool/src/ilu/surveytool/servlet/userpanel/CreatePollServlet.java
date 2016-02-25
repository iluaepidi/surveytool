package ilu.surveytool.servlet.userpanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Option;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;
import ilu.surveytool.databasemanager.DataObject.Poll;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.surveytool.userpanel.handler.PollHandler;

/**
 * Servlet implementation class CreatePollServlet
 */
@WebServlet("/CreatePollServlet")
public class CreatePollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String language = "en";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePollServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	{
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		if(userSessionInfo != null && userSessionInfo.isValid())
		{
			try {
				Poll poll = new Poll();
				poll.setAuthor(userSessionInfo.getUserId());
				poll.setSurveyId(0);
				poll.setProject(request.getParameter(Parameter.s_PROJECT));
				poll.setCallUrl(request.getParameter(Parameter.s_LINK_URL));
				poll.getContents().put(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, 
						new Content(0, language, DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, request.getParameter(Parameter.s_TITLE)));
				String ackText = request.getParameter(Parameter.s_ACKNOWLEDGMENT_TEXT);
				if(!ackText.isEmpty()) poll.getContents().put(DBConstants.s_VALUE_CONTENTTYPE_NAME_ACKNOWLEDGMENT_TEXT, 
						new Content(0, language, DBConstants.s_VALUE_CONTENTTYPE_NAME_ACKNOWLEDGMENT_TEXT, ackText));
				String callText = request.getParameter(Parameter.s_CALL_TEXT);
				if(!callText.isEmpty()) poll.getContents().put(DBConstants.s_VALUE_CONTENTTYPE_NAME_CALL_TEXT, 
						new Content(0, language, DBConstants.s_VALUE_CONTENTTYPE_NAME_CALL_TEXT, callText));
				String label = request.getParameter(Parameter.s_LINK_LABEL);
				if(!label.isEmpty()) poll.getContents().put(DBConstants.s_VALUE_CONTENTTYPE_NAME_LABEL, 
						new Content(0, language, DBConstants.s_VALUE_CONTENTTYPE_NAME_LABEL, label));				
				
				Question question = new Question();
				question.setCategory("generic");
				question.setTag("generic");
				question.setQuestionType("simple");
				question.getContents().put(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, new Content(0, language, 
						DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, request.getParameter(Parameter.s_QSTATEMENT)));
				
				OptionsGroup optionsGroup = new OptionsGroup();
				optionsGroup.setOptionType("radio");
				
				String optionsStr = request.getParameter(Parameter.s_OPTIONS);
				//List<String> options = Arrays.asList(optionsStr.substring(1, optionsStr.length()-1).split(","));
				JSONArray jsonArray = new JSONArray(optionsStr);
				
				for(int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject json = jsonArray.getJSONObject(i);
					Option option = new Option();
					option.setIndex(Integer.parseInt(json.getString(Parameter.s_INDEX)));
					option.getContents().put(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, new Content(0, language, 
							DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, json.getString(Parameter.s_TITLE)));
					
					optionsGroup.getOptions().add(option);
				}
				
				question.getOptionsGroups().add(optionsGroup);
				poll.setQuestion(question);
				
				PollHandler pollHandler = new PollHandler();
				pollHandler.createPoll(poll);
				
				System.out.println("options: " + poll.toString());
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
