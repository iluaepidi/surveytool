package ilu.surveytool.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ilu.surveytool.constants.Attribute;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Credentials;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.language.Language;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.userpanel.accesscontrol.Login;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		ProcessRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProcessRequest(request, response);
	}
	
	protected void ProcessRequest(HttpServletRequest request, HttpServletResponse response)
	{
		SurveyToolProperties bodyPages = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
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
			
			
			System.out.println(loginResp.toString());
		}
		
		if(loginResp != null && loginResp.isValid() && loginResp.getRol().equals(DBConstants.s_VALUE_ROLNAME_INTERVIEWER) && loginResp.getUserState() == DBConstants.i_VALUE_USER_STATE_ID_ACTIVE)
		{
			request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_SURVEY_MANAGER_HOME));
			session = request.getSession();
			session.setAttribute(Attribute.s_USER_SESSION_INFO, loginResp);
			request.setAttribute(Attribute.s_PAGE_TITLE, "User Panel");
		}
		else if(loginResp != null && loginResp.isValid() && loginResp.getUserState() == DBConstants.i_VALUE_USER_STATE_ID_ADMIN)
		{
			request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_LOGIN));
			loginResp.setValid(false);
			loginResp.setErrorMsg("login.invalid.state.admin");
			request.setAttribute(Attribute.s_LOGIN_RESPONSE, loginResp);
			request.setAttribute(Attribute.s_PAGE_TITLE, "Home");
		}
		else
		{
			request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_LOGIN));
			request.setAttribute(Attribute.s_LOGIN_RESPONSE, loginResp);
			request.setAttribute(Attribute.s_PAGE_TITLE, "Home");
		}
		
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
		
	}

}
