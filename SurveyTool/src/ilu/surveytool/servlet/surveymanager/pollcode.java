package ilu.surveytool.servlet.surveymanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import ilu.surveytool.sessioncontrol.SessionHandler;

/**
 * Servlet implementation class pollcode
 */
@WebServlet("/pollcode")
public class pollcode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pollcode() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	{
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		if(userSessionInfo != null && userSessionInfo.isValid())
		{
			String pid = request.getParameter(Parameter.s_PID);
			List<String> jsFiles = new ArrayList<>();
			jsFiles.add(properties.getJsFilePath(Address.s_JS_EDIT_POLL));
			request.setAttribute(Attribute.s_JS_FILES, jsFiles);
			request.setAttribute(Attribute.s_POLL_ID, pid);
			request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_POLL_EXAMPLE_CODE));
			request.setAttribute(Attribute.s_PAGE_TITLE, "Poll example and code");
		}
		else
		{
			SessionHandler sessionHandler = new SessionHandler();
			sessionHandler.sessionClosed(request, properties);
		}
		
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
	}
		

}
