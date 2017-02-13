package ilu.surveytool.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ilu.surveyengine.emailSender.EmailSender;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.RegisterResponse;
import ilu.surveytool.language.Language;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.userpanel.accesscontrol.Register;

/**
 * Servlet implementation class VerifyEmail
 */
@WebServlet("/VerifyEmail")
public class VerifyEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyEmail() {
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
		System.out.println("Llega a verify email.");
		Language lang = new Language(getServletContext().getRealPath("/")); 
		lang.loadLanguage(Language.getLanguageRequest(request));
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		String tempId = request.getParameter(Parameter.s_TOKEN);
		Register register = new Register();		
		
		if(tempId != null && !tempId.isEmpty() && register.verifyEmail(tempId))
		{
			request.setAttribute(Attribute.s_EMAIL_VERIFIED, true);			
		}
		else
		{
			request.setAttribute(Attribute.s_EMAIL_VERIFIED, false);
		}

		String title = lang.getContent("userpanel.registration.title") + " - " + lang.getContent("userpanel.registration.confirmation.title");
		request.setAttribute(Attribute.s_PAGE_TITLE, title);
		request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_LOGIN_REGISTRATION_CONFIRMATION));
		
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE_USER_PANEL);
	}

}
