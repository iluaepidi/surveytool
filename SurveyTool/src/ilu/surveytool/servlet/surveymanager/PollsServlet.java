package ilu.surveytool.servlet.surveymanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;

import ilu.surveymanager.handler.PollHandler;
import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Poll;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.surveytool.sessioncontrol.SessionHandler;

/**
 * Servlet implementation class PollsServlet
 */
@WebServlet("/PollsServlet")
public class PollsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PollsServlet() {
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
		System.out.println("processRequest en SurveysServlet");
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		if(userSessionInfo != null && userSessionInfo.isValid())
		{
			//cargar lenguaje seleccionado en el combo
			
			int pollId = Integer.parseInt(request.getParameter(Parameter.s_POLL_ID));			
			String language = request.getParameter(Parameter.s_LANGUAGE_SURVEY);
			
			System.out.println("Edit Poll - id: " + pollId);
			
			PollHandler pollHandler = new PollHandler();
			Poll poll = pollHandler.getPollDetailById(pollId, language);
			request.setAttribute(Attribute.s_POLL_INFO, poll);
			request.setAttribute(Attribute.s_ADD_QUESTIONS, true);
			request.setAttribute(Attribute.s_IS_EDIT_POLL, true);
			
			List<String> jsFiles = new ArrayList<>();
			jsFiles.add(properties.getJsFilePath(Address.s_JS_EDIT_POLL));
			request.setAttribute(Attribute.s_JS_FILES, jsFiles);
			
			request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_EDIT_POLL));
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
