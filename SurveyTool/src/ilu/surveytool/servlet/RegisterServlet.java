package ilu.surveytool.servlet;

import java.io.File;
import java.io.IOException;

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
import ilu.surveytool.databasemanager.DataObject.RegisterResponse;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.language.Language;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.userpanel.accesscontrol.Login;
import ilu.userpanel.accesscontrol.Register;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
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
		
		String langReq = Language.getLanguageRequest(request);
		
		Register registerHandler = new Register();
		RegisterResponse registerReponse = new RegisterResponse();
		registerReponse.setUserName(request.getParameter(Parameter.s_USERNAME));
		registerReponse.setPassword(request.getParameter(Parameter.s_PASSWORD));
		registerReponse.setRepassword(request.getParameter(Parameter.s_REPASSWORD));
		registerReponse.setEmail(request.getParameter(Parameter.s_EMAIL));
		registerReponse.setIsoLanguage(langReq);
		
		
		
		System.out.println("Parameters: " + registerReponse.getUserName() + " - " + registerReponse.getPassword());
		RegisterResponse regResp = registerHandler.register(registerReponse);
		System.out.println(registerReponse.toString());
		
		if(regResp.isValid())
		{	
			//cargar usuario y entrar en el sistema
			Login loginHandler = new Login();
			Credentials credentials = new Credentials();
			credentials.setUsername(request.getParameter(Parameter.s_USERNAME));
			credentials.setPassword(request.getParameter(Parameter.s_PASSWORD));
			System.out.println("Parameters: " + credentials.getUsername() + " - " + credentials.getPassword());
			LoginResponse loginResp = loginHandler.login(credentials);
			
			request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_USER_PANEL_HOME));
			HttpSession session = request.getSession();
			session.setAttribute(Attribute.s_USER_SESSION_INFO, loginResp);
			request.setAttribute(Attribute.s_PAGE_TITLE, "User Panel");
		}
		else
		{
			request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_LOGIN));
			request.setAttribute(Attribute.s_REGISTER_RESPONSE, regResp);
			request.setAttribute(Attribute.s_PAGE_TITLE, "Home");
		}
		
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
		
	}

}
