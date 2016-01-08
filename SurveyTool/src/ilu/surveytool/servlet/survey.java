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
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.orchestrator.SurveysOrch;
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
		String sid = request.getParameter(FormParameter.s_SID);
		String language = request.getParameter(FormParameter.s_LANGUAGE_LAN);
		
		System.out.println("SID: " + sid + " - Language: " + language);
		
		SurveysOrch surveyOrch = new SurveysOrch();
		Survey survey = surveyOrch.getSurveyDetailByPublicId(sid, language);
		request.setAttribute(Attribute.s_SURVEY_INFO, survey);
		
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_SURVEY_PAGE));
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
	}

}
