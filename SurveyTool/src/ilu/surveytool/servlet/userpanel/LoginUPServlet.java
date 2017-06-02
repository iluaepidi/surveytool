package ilu.surveytool.servlet.userpanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
		//SurveyToolProperties bodyPages = new SurveyToolProperties(getServletContext().getRealPath("/"));
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		boolean isRedirect = false;
		
		//System.out.println("Path: " + bodyPages.getBudyPagePath());
		/**
		 * cargar correspondiente body page
		 */
		
		Language lang = new Language(getServletContext().getRealPath("/")); 
		lang.loadLanguage(Language.getLanguageRequest(request));
		
		HttpSession session = request.getSession();
		LoginResponse loginResp = (LoginResponse) session.getAttribute(Attribute.s_USER_SESSION_INFO);
		List<String> paramNames = Collections.list(request.getParameterNames());
		
		if(paramNames.contains(Parameter.s_USERNAME) && paramNames.contains(Parameter.s_PASSWORD))
		{
			Login loginHandler = new Login();
			Credentials credentials = new Credentials();
			credentials.setUsername(request.getParameter(Parameter.s_USERNAME));
			credentials.setPassword(request.getParameter(Parameter.s_PASSWORD));
			//System.out.println("Parameters: " + credentials.getUsername() + " - " + credentials.getPassword());
			loginResp = loginHandler.login(credentials);
			//System.out.println(loginResp.toString());
		}	
			
		if(loginResp != null && loginResp.isValid() && loginResp.getRol().equals(DBConstants.s_VALUE_ROLNAME_USER) && loginResp.getUserState() == DBConstants.i_VALUE_USER_STATE_ID_ACTIVE)
		{
			/*request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_USER_PANEL_HOME));
			
			session.setAttribute(Attribute.s_USER_SESSION_INFO, loginResp);
			request.setAttribute(Attribute.s_PAGE_TITLE, "Home");*/
			session.setAttribute(Attribute.s_USER_SESSION_INFO, loginResp);
			isRedirect = true;
			try {
				response.sendRedirect(Address.s_SERVLET_USER_PANEL_UPHOME);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(loginResp != null && loginResp.isValid() && loginResp.getRol().equals(DBConstants.s_VALUE_ROLNAME_USER) && loginResp.getUserState() == DBConstants.i_VALUE_USER_STATE_ID_BASIC_PROFILE)
		{
			request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_USER_PANEL_BASIC_PROFILE));
			
			boolean logged = loginResp.getUserState() == DBConstants.i_VALUE_USER_STATE_ID_ACTIVE;
			request.setAttribute(Attribute.s_LOGGED, Boolean.toString(logged));
			session.setAttribute(Attribute.s_USER_SESSION_INFO, loginResp);
			request.setAttribute(Attribute.s_PAGE_TITLE, "Basic profile");
		}
		else
		{
			request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_LOGIN_USER_PANEL));
			request.setAttribute(Attribute.s_LOGIN_RESPONSE, loginResp);
			request.setAttribute(Attribute.s_PAGE_TITLE, "Home");
		}
			
		if(!isRedirect) CommonCode.redirect(request, response, Address.s_MASTER_PAGE_USER_PANEL);
	}

}
