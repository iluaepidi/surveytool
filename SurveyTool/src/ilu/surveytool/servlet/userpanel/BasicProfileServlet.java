package ilu.surveytool.servlet.userpanel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

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
import ilu.surveytool.databasemanager.UserDB;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.User;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.language.Language;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.surveytool.sessioncontrol.SessionHandler;
import ilu.userpanel.handler.UserHandler;

/**
 * Servlet implementation class BasicProfileServlet
 */
@WebServlet("/BasicProfileServlet")
public class BasicProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BasicProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		this.getRequest(request, response);
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
		System.out.println("Basic profile servlet.");
		/**
		 * cargar correspondiente body page
		 */
		
		Language lang = new Language(getServletContext().getRealPath("/")); 
		lang.loadLanguage(Language.getLanguageRequest(request));
		
		HttpSession session = request.getSession();
		LoginResponse loginResp = (LoginResponse) session.getAttribute(Attribute.s_USER_SESSION_INFO);
		
		//Check if the session is active
		if(loginResp != null && loginResp.isValid())
		{
			//Get the parameter into the user object
			User user = this._getUserParams(request);
			user.setUserId(loginResp.getUserId());
			
			UserHandler userHandler = new UserHandler();
			if(userHandler.updateBasicProfile(user))
			{
				UserDB userDB = new UserDB();
				userDB.updateUserState(user.getUserId(), DBConstants.i_VALUE_USER_STATE_ID_ACTIVE);
				loginResp.setUserState(DBConstants.i_VALUE_USER_STATE_ID_ACTIVE);
				session.setAttribute(Attribute.s_USER_SESSION_INFO, loginResp);
				
				boolean logged = loginResp.getUserState() == DBConstants.i_VALUE_USER_STATE_ID_ACTIVE;
				session.setAttribute(Attribute.s_LOGGED, Boolean.toString(logged));
				session.setAttribute(Attribute.s_USER_SESSION_INFO, loginResp);
				isRedirect = true;
				try {
					response.sendRedirect(Address.s_SERVLET_USER_PANEL_UPHOME);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_USER_PANEL_HOME));
				
				
				//request.setAttribute(Attribute.s_PAGE_TITLE, "Home");				
			}
			else
			{
				SessionHandler sessionHandler = new SessionHandler();
				sessionHandler.upSessionClosed(request, properties);
			}
		}
		else
		{
			SessionHandler sessionHandler = new SessionHandler();
			sessionHandler.upSessionClosed(request, properties);
		}		
		
		if(!isRedirect) CommonCode.redirect(request, response, Address.s_MASTER_PAGE_USER_PANEL);
	}
	
	protected void getRequest(HttpServletRequest request, HttpServletResponse response)
	{
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		System.out.println("Basic profile servlet.");
		/**
		 * cargar correspondiente body page
		 */
		
		Language lang = new Language(getServletContext().getRealPath("/")); 
		lang.loadLanguage(Language.getLanguageRequest(request));
		
		HttpSession session = request.getSession();
		LoginResponse loginResp = (LoginResponse) session.getAttribute(Attribute.s_USER_SESSION_INFO);
		
		//Check if the session is active
		if(loginResp != null && loginResp.isValid())
		{
			List<String> paramNames = Collections.list(request.getParameterNames());
			if(paramNames.contains(Parameter.s_SKIP))
			{
				UserDB userDB = new UserDB();
				userDB.updateUserState(loginResp.getUserId(), DBConstants.i_VALUE_USER_STATE_ID_ACTIVE);
				loginResp.setUserState(DBConstants.i_VALUE_USER_STATE_ID_ACTIVE);
				
				//request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_USER_PANEL_HOME));
				
				session.setAttribute(Attribute.s_USER_SESSION_INFO, loginResp);
				//request.setAttribute(Attribute.s_PAGE_TITLE, "Home");
				try {
					response.sendRedirect(Address.s_SERVLET_LOGIN_USER_PANEL);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				SessionHandler sessionHandler = new SessionHandler();
				sessionHandler.upSessionClosed(request, properties);
				CommonCode.redirect(request, response, Address.s_MASTER_PAGE_USER_PANEL);
			}
		}
		else
		{
			SessionHandler sessionHandler = new SessionHandler();
			sessionHandler.upSessionClosed(request, properties);
			CommonCode.redirect(request, response, Address.s_MASTER_PAGE_USER_PANEL);
		}
				
	}
	
	private User _getUserParams(HttpServletRequest request)
	{
		User user = new User();
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> paramNames = Collections.list(request.getParameterNames());
		if(paramNames.contains(Parameter.s_FIRST_NAME)) user.setFirstName(request.getParameter(Parameter.s_FIRST_NAME));
		if(paramNames.contains(Parameter.s_LAST_NAME)) user.setLastName(request.getParameter(Parameter.s_LAST_NAME));
		String day = request.getParameter(Parameter.s_DAY);
		String month = request.getParameter(Parameter.s_MONTH);
		String year = request.getParameter(Parameter.s_YEAR);
		if(!day.equals("none") && !month.equals("none") && !year.equals("none"))
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
			cal.set(Calendar.MONTH, Integer.parseInt(month));
			cal.set(Calendar.YEAR, Integer.parseInt(year));
			user.setBirthdDate(new Timestamp(cal.getTimeInMillis()));
		}
		if(paramNames.contains(Parameter.s_GENDER)) user.setGender(request.getParameter(Parameter.s_GENDER));
		
		return user;
	}

}
