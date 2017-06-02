package ilu.surveytool.servlet.userpanel;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.UserSurveyResume;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.language.Language;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.userpanel.handler.SurveyHandler;

/**
 * Servlet implementation class UPHomeServlet
 */
@WebServlet("/UPHomeServlet")
public class UPHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UPHomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	{
		//SurveyToolProperties bodyPages = new SurveyToolProperties(getServletContext().getRealPath("/"));
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		//System.out.println("Path: " + bodyPages.getBudyPagePath());
		/**
		 * cargar correspondiente body page
		 */
		
		Language lang = new Language(getServletContext().getRealPath("/")); 
		lang.loadLanguage(Language.getLanguageRequest(request));
		
		HttpSession session = request.getSession();
		LoginResponse loginResp = (LoginResponse) session.getAttribute(Attribute.s_USER_SESSION_INFO);
		
		if(loginResp != null && loginResp.isValid() && loginResp.getRol().equals(DBConstants.s_VALUE_ROLNAME_USER) && loginResp.getUserState() == DBConstants.i_VALUE_USER_STATE_ID_ACTIVE)
		{	
			SurveyHandler surveyHandler = new SurveyHandler();
			UserSurveyResume userSurveyResume = surveyHandler.getUserSurveyResume(loginResp.getUserId());
			request.setAttribute(Attribute.s_USER_SURVEY_RESUME, userSurveyResume);
			
			request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_USER_PANEL_HOME));			
			request.setAttribute(Attribute.s_PAGE_TITLE, "Home");
		}
		
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE_USER_PANEL);
	}

}
