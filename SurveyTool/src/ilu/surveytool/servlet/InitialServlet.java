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
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.properties.SurveyToolProperties;

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
		
		String logout = (String)request.getParameter(Parameter.s_LOGOUT);
		String site = request.getParameter("site");
		if(logout!=null){
			//hacer logout
			HttpSession session = request.getSession();
			session.removeAttribute(Attribute.s_USER_SESSION_INFO);
			try {
				//response.sendRedirect("manager");
				response.sendRedirect(site);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
			SurveyToolProperties bodyPages = new SurveyToolProperties(getServletContext().getRealPath("/"));
			
			if(userSessionInfo != null && userSessionInfo.isValid())
			{
				request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_SURVEY_MANAGER_HOME));
				request.setAttribute(Attribute.s_PAGE_TITLE, "user_panel.title");			
			}
			else
			{
				userSessionInfo = new LoginResponse();
				userSessionInfo.setErrorMsg("msg.error.session.expired");
				request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_LOGIN));
				request.setAttribute(Attribute.s_LOGIN_RESPONSE, userSessionInfo);
				request.setAttribute(Attribute.s_PAGE_TITLE, "home.title");
			}
			
			CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
		}
	}

}
