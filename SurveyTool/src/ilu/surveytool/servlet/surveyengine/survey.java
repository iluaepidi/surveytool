package ilu.surveytool.servlet.surveyengine;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.properties.SurveyToolProperties;

/**
 * Servlet implementation class survey
 */
@WebServlet("/survey")
public class survey extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public survey() {
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
		String sid = request.getParameter(Parameter.s_SID);
		String language = request.getParameter(Parameter.s_LANGUAGE_LAN);
		
		System.out.println("SID: " + sid + " - Language: " + language);
		
		SurveysHandler surveyHandler = new SurveysHandler();
		Survey survey = surveyHandler.getSurveyDetailByPublicId(sid, language);
		request.setAttribute(Attribute.s_SURVEY_INFO, survey);
		request.setAttribute(Attribute.s_PAGE_TITLE, survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText());
		
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_SURVEY_PAGE));
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
	}

}
