package ilu.surveytool.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.FormParameter;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.orchestrator.SurveysOrch;
import ilu.surveytool.orchestrator.UserPanelHomeOrch;
import ilu.surveytool.properties.BodyPages;

/**
 * Servlet implementation class CreateSurveyServlet
 */
@WebServlet("/CreateSurveyServlet")
public class CreateSurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String language = "en";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateSurveyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	{
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		BodyPages bodyPages = new BodyPages(getServletContext().getRealPath("/"));
		
		if(userSessionInfo != null && userSessionInfo.isValid())
		{
			Survey survey = new Survey();
			survey.getContents().add(new Content(0, this.language, DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, request.getParameter(FormParameter.s_TITLE)));
			survey.getContents().add(new Content(0, this.language, DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION, request.getParameter(FormParameter.s_DESCRIPTION)));
			survey.setProject(request.getParameter(FormParameter.s_PROJECT));
			survey.setAuthor(userSessionInfo.getUserId());
			
			SurveysOrch surveysOrch = new SurveysOrch();
			int surveyId = surveysOrch.createSurvey(survey);
			
			survey.setSurveyId(surveyId);
			
			if(surveyId > 0)
			{
				request.setAttribute(Attribute.s_SURVEY_INFO, survey);
				request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_EDIT_SURVEY));
			}
			
		}
		else
		{
			userSessionInfo = new LoginResponse();
			userSessionInfo.setErrorMsg("Session is expired or not exist.");
			request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_LOGIN));
			request.setAttribute(Attribute.s_LOGIN_RESPONSE, userSessionInfo);
		}
		
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
	}

}
