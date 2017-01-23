package ilu.surveytool.servlet.userpanel;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.RegisterResponse;
import ilu.surveytool.language.Language;
import ilu.surveytool.properties.SurveyToolProperties;
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
		// TODO Auto-generated method stub
		Language lang = new Language(getServletContext().getRealPath("/")); 
		lang.loadLanguage(Language.getLanguageRequest(request));
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		if(request.getParameter("registrationUP0") != null)
		{
			request.setAttribute(Attribute.s_PAGE_TITLE, lang.getContent("userpanel.registration.conditions.title"));
			request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_LOGIN_REGISTRATION_CONDITIONS));
		}
		else if(request.getParameter("acceptedConditions") != null)
		{
			String title = lang.getContent("userpanel.registration.title") + " - " + lang.getContent("userpanel.registration.accountCreation.title");
			request.setAttribute(Attribute.s_PAGE_TITLE, title);
			request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_LOGIN_ACCOUNT_CREATION));
		}
		else if(request.getParameter(Parameter.s_EMAIL) != null && request.getParameter(Parameter.s_PASSWORD) != null)
		{
			String langReq = Language.getLanguageRequest(request);
			
			Register registerHandler = new Register();
			RegisterResponse registerReponse = new RegisterResponse();
			registerReponse.setPassword(request.getParameter(Parameter.s_PASSWORD));
			registerReponse.setRepassword(request.getParameter(Parameter.s_REPASSWORD));
			registerReponse.setEmail(request.getParameter(Parameter.s_EMAIL));
			registerReponse.setReemail(request.getParameter(Parameter.s_REEMAIL));
			registerReponse.setIsoLanguage(langReq);			
			
			RegisterResponse regResp = registerHandler.register(registerReponse);
			
			if(regResp.isValid())
			{
				registerReponse.setRol(registerReponse.ROL_NORMAL_USER);
				registerReponse.setStatus(registerReponse.STATUS_EMAIL_USER);
				String title = lang.getContent("userpanel.registration.title") + " - " + lang.getContent("userpanel.registration.confirmation.title");
				request.setAttribute(Attribute.s_PAGE_TITLE, title);
				request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_LOGIN_REGISTRATION_CONFIRMATION));
			}
			else
			{
				registerReponse.setValid(false);
				registerReponse.setErrorMsg("msg.error.login.email.exist");
				request.setAttribute(Attribute.s_REGISTER_RESPONSE, registerReponse);
				String title = lang.getContent("userpanel.registration.title") + " - " + lang.getContent("userpanel.registration.accountCreation.title");
				request.setAttribute(Attribute.s_PAGE_TITLE, title);
				request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_LOGIN_ACCOUNT_CREATION));
			}
		}
		else
		{
			request.setAttribute(Attribute.s_PAGE_TITLE, lang.getContent("userpanel.registration.conditions.title"));
			request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_LOGIN_REGISTRATION_CONDITIONS));
		}
		
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE_USER_PANEL);
	}

}
