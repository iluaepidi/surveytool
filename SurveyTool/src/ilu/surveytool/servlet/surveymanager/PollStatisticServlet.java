package ilu.surveytool.servlet.surveymanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;

import ilu.surveymanager.handler.PollHandler;
import ilu.surveymanager.handler.QuotaHandler;
import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveymanager.statistics.StatisticsQuestion;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Poll;
import ilu.surveytool.databasemanager.DataObject.Quota;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.surveytool.sessioncontrol.SessionHandler;

/**
 * Servlet implementation class PollStatisticServlet
 */
@WebServlet("/PollStatisticServlet")
public class PollStatisticServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String language = "en";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PollStatisticServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		System.out.println("processRequest en SurveyStatisticServlet");
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		if(userSessionInfo != null && userSessionInfo.isValid())
		{			
			int poid = Integer.parseInt(request.getParameter(Parameter.s_POLL_ID));
			PollHandler pollHandler = new PollHandler();
			Poll poll = pollHandler.getPollDetailById(poid, this.language);	
			request.setAttribute(Attribute.s_POLL_INFO, poll);
			
			StatisticsQuestion sQ = pollHandler.createStatistics(poll.getPollId(), poll.getQuestion(), this.language, this.language);
			request.setAttribute(Attribute.s_SURVEY_STATISTIC, sQ);
			
			List<String> jsFiles = new ArrayList<>();
			jsFiles.add(properties.getJsFilePath(Address.s_JS_EDIT_POLL));
			jsFiles.add(properties.getJsFilePath(Address.s_JS_CHART_GRAPHICS));
			request.setAttribute(Attribute.s_JS_FILES, jsFiles);
			
			List<String> cssFiles = new ArrayList<>();
			request.setAttribute(Attribute.s_CSS_FILES, cssFiles);
			
			request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_POLL_STATISTICS));
			
			request.setAttribute(Attribute.s_PAGE_TITLE, "survey.statistic.title");
			
		}
		else
		{
			SessionHandler sessionHandler = new SessionHandler();
			sessionHandler.sessionClosed(request, properties);
		}
		
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
		//System.out.println(response);
	}

}
