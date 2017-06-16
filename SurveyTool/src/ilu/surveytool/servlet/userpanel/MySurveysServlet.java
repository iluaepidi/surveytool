package ilu.surveytool.servlet.userpanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import ilu.surveytool.databasemanager.DataObject.UserSurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.language.Language;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.surveytool.sessioncontrol.SessionHandler;
import ilu.userpanel.handler.SurveyHandler;

/**
 * Servlet implementation class MySurveys
 */
@WebServlet("/MySurveysServlet")
public class MySurveysServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MySurveysServlet() {
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
			
			List<UserSurveyTableInfo> tableInfo = surveyHandler.getUserSurveyTableInfo(loginResp.getUserId(), lang.getCurrentLanguage());
			request.setAttribute(Attribute.s_SURVEYS, tableInfo);
			
			List<String> jsFiles = new ArrayList<>();
			jsFiles.add(properties.getJsFilePath(Address.s_JS_DATATABLE_JQUERY));
			jsFiles.add(properties.getJsFilePath(Address.s_JS_DATATABLE_BOOTSTRAP));
			request.setAttribute(Attribute.s_JS_FILES, jsFiles);
			
			request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_USER_PANEL_MYSURVEYS));			
			request.setAttribute(Attribute.s_PAGE_TITLE, "My surveys");
		}
		else
		{
			SessionHandler sessionHandler = new SessionHandler();
			sessionHandler.upSessionClosed(request, properties);
		}
		
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE_USER_PANEL);
	}

}
