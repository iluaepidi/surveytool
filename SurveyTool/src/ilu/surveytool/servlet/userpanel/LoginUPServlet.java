package ilu.surveytool.servlet.userpanel;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ilu.surveyengine.emailSender.EmailSender;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Credentials;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.RegisterResponse;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.language.Language;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.userpanel.accesscontrol.Login;
import ilu.userpanel.accesscontrol.Register;

/**
 * Servlet implementation class LoginUPServlet
 */
@WebServlet("/LoginUPServlet")
public class LoginUPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginUPServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//this.processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	{
		SurveyToolProperties bodyPages = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		//System.out.println("Path: " + bodyPages.getBudyPagePath());
		/**
		 * cargar correspondiente body page
		 */
		
		Language lang = new Language(getServletContext().getRealPath("/")); 
		lang.loadLanguage(Language.getLanguageRequest(request));
		
		Login loginHandler = new Login();
		Credentials credentials = new Credentials();
		credentials.setUsername(request.getParameter(Parameter.s_USERNAME));
		credentials.setPassword(request.getParameter(Parameter.s_PASSWORD));
		//System.out.println("Parameters: " + credentials.getUsername() + " - " + credentials.getPassword());
		LoginResponse loginResp = loginHandler.login(credentials);
		
		
		System.out.println(loginResp.toString());
		
		if(loginResp.isValid() && loginResp.getRol().equals(DBConstants.s_VALUE_ROLNAME_USER) && loginResp.getUserState() == DBConstants.i_VALUE_USER_STATE_ID_ACTIVE)
		{
			request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_USER_PANEL_HOME));
			HttpSession session = request.getSession();
			session.setAttribute(Attribute.s_USER_SESSION_INFO, loginResp);
			request.setAttribute(Attribute.s_PAGE_TITLE, "Home");
		}
		else
		{
			request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_LOGIN_USER_PANEL));
			request.setAttribute(Attribute.s_LOGIN_RESPONSE, loginResp);
			request.setAttribute(Attribute.s_PAGE_TITLE, "Home");
		}
			
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE_USER_PANEL);
	}

}
