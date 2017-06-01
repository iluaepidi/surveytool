package ilu.surveytool.sessioncontrol;

import javax.servlet.http.HttpServletRequest;

import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.properties.SurveyToolProperties;

/**
 * 
 * @author JAgutierrez
 *
 *	This class handles the different session options 
 */

public class SessionHandler {

	public SessionHandler() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This method carries out all needed when the server receives a request but the session is closed.
	 * @param request
	 * @param properties
	 */
	public void sessionClosed(HttpServletRequest request , SurveyToolProperties properties)
	{
		LoginResponse userSessionInfo = new LoginResponse();
		userSessionInfo.setErrorMsg("msg.error.session.expired");
		request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_LOGIN));
		request.setAttribute(Attribute.s_PAGE_TITLE, "Home");
		request.setAttribute(Attribute.s_LOGIN_RESPONSE, userSessionInfo);
	}

	public void upSessionClosed(HttpServletRequest request , SurveyToolProperties properties)
	{
		LoginResponse userSessionInfo = new LoginResponse();
		userSessionInfo.setErrorMsg("msg.error.session.expired");
		request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_LOGIN_USER_PANEL));
		request.setAttribute(Attribute.s_PAGE_TITLE, "Home");
		request.setAttribute(Attribute.s_LOGIN_RESPONSE, userSessionInfo);
	}
}
