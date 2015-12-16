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
import ilu.surveytool.constants.FormParameter;
import ilu.surveytool.databasemanager.DataObject.Credentials;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.orchestrator.LoginOrch;
import ilu.surveytool.properties.BodyPages;

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
		BodyPages bodyPages = new BodyPages(getServletContext().getRealPath("/"));
		
		//System.out.println("Path: " + bodyPages.getBudyPagePath());
		/**
		 * cargar correspondiente body page
		 */
		
		LoginOrch loginOrch = new LoginOrch();
		Credentials credentials = new Credentials();
		credentials.setUsername(request.getParameter(FormParameter.s_USERNAME));
		credentials.setPassword(request.getParameter(FormParameter.s_PASSWORD));
		System.out.println("Parameters: " + credentials.getUsername() + " - " + credentials.getPassword());
		LoginResponse loginResp = loginOrch.login(credentials);
		System.out.println(loginResp.toString());
		
		if(loginResp.isValid() && loginResp.getRol().equals(DBConstants.s_VALUE_ROLNAME_ADMIN))
		{
			request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_USER_PANEL_HOME));
			HttpSession session = request.getSession();
			session.setAttribute(Attribute.s_USER_SESSION_INFO, loginResp);
		}
		else
		{
			request.setAttribute(Attribute.s_BODY_PAGE, bodyPages.getBudyPagePath(Address.s_BODY_LOGIN));
			request.setAttribute(Attribute.s_LOGIN_RESPONSE, loginResp);
		}
		
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
		
	}

}
