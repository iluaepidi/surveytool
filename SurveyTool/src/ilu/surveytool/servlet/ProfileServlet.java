package ilu.surveytool.servlet;

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
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Credentials;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.RegisterResponse;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.userpanel.accesscontrol.Login;
import ilu.userpanel.accesscontrol.Profile;
import ilu.userpanel.accesscontrol.Register;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 100L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		
		
		String savesubmit = request.getParameter("savesubmitvalue");
		
			Profile profileHandler = new Profile();
			RegisterResponse registerReponse = new RegisterResponse();
			registerReponse.setUserName(userSessionInfo.getUserName());
			registerReponse.setPassword(request.getParameter(Parameter.s_PASSWORD));
			registerReponse.setRepassword(request.getParameter(Parameter.s_REPASSWORD));
			registerReponse.setEmail(request.getParameter(Parameter.s_EMAIL));
			registerReponse.setUserId(userSessionInfo.getUserId());
			registerReponse.setIsoLanguage(request.getParameter(Parameter.s_LANGUAGE));
		if(savesubmit!=null){	
			boolean updateuser = profileHandler.updateUser(registerReponse);
			if(updateuser){
				HttpSession session = request.getSession();
				Login loginHandler = new Login();
				Credentials credentials = new Credentials();
				credentials.setUsername(request.getParameter(Parameter.s_USERNAME));
				credentials.setPassword(request.getParameter(Parameter.s_PASSWORD));
				LoginResponse loginResp = loginHandler.login(credentials);
				session.setAttribute(Attribute.s_USER_SESSION_INFO, loginResp);
				
				//ConformationUpdateProfile
				request.setAttribute("ConformationUpdateProfile", "yes");
			}else{
				
			}
		}
		
		
		
		SurveyToolProperties bodyPages = new SurveyToolProperties(getServletContext().getRealPath("/"));
		request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_PROFILE));
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
		
	}

}
