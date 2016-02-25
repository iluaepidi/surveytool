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
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.surveytool.userpanel.handler.UserPanelHomeHandler;

/**
 * Servlet implementation class InitialServlet
 */
@WebServlet("/InitialServlet")
public class InitialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitialServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProcessRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProcessRequest(request, response);
	}
	
	protected void ProcessRequest(HttpServletRequest request, HttpServletResponse response)
	{
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		SurveyToolProperties bodyPages = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		if(userSessionInfo != null && userSessionInfo.isValid())
		{
			request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_USER_PANEL_HOME));
			request.setAttribute(Attribute.s_PAGE_TITLE, "Survey Manager");			
		}
		else
		{
			userSessionInfo = new LoginResponse();
			userSessionInfo.setErrorMsg("Session is expired or not exist.");
			request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_LOGIN));
			request.setAttribute(Attribute.s_LOGIN_RESPONSE, userSessionInfo);
			request.setAttribute(Attribute.s_PAGE_TITLE, "Home");
		}
		
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
	}

}
