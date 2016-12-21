package ilu.surveytool.servlet.surveymanager;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ilu.surveymanager.handler.PollHandler;
import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.surveytool.sessioncontrol.SessionHandler;

/**
 * Servlet implementation class UserPanelHomeServlet
 */
@WebServlet("/UserPanelHomeServlet")
public class UserPanelHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String language = "en";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPanelHomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProcessRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProcessRequest(request, response);
	}
	
	protected void ProcessRequest(HttpServletRequest request, HttpServletResponse response)
	{
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		SurveyToolProperties bodyPages = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		if(userSessionInfo != null && userSessionInfo.isValid())
		{
			String bodyPage = request.getParameter(Parameter.s_UPOPTION);
			
			//pre-select tab
			String tab = request.getParameter(Parameter.s_TAB);
			if(tab == null) tab = "survey";
			
			if(bodyPage != null && !bodyPage.isEmpty())
			{
				if(bodyPage.equals(Address.s_BODY_SURVEYS))
				{
					SurveysHandler surveysHandler = new SurveysHandler();
					request.setAttribute(Attribute.s_SURVEYS, surveysHandler.getSurveysTableInfoByAuthor(userSessionInfo.getUserId(), this.language));
					PollHandler pollHandler = new PollHandler();
					request.setAttribute(Attribute.s_POLLS, pollHandler.getPollsTableInfoByAuthor(userSessionInfo.getUserId(), this.language));
				}
				request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(bodyPage));
				request.setAttribute(Attribute.s_PAGE_TITLE, "Survey Manager");
				request.setAttribute(Attribute.s_TAB, tab);
			}			
		}
		else
		{
			SessionHandler sessionHandler = new SessionHandler();
			sessionHandler.sessionClosed(request, bodyPages);
		}
		
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
	}

}
