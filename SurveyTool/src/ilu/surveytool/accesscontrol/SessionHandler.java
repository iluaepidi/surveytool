package ilu.surveytool.accesscontrol;

import javax.servlet.http.HttpServletRequest;

import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.properties.SurveyToolProperties;

public class SessionHandler {

	public SessionHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public void sessionClosed(HttpServletRequest request , SurveyToolProperties properties)
	{
		LoginResponse userSessionInfo = new LoginResponse();
		userSessionInfo.setErrorMsg("Session is expired or not exist.");
		request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_LOGIN));
		request.setAttribute(Attribute.s_PAGE_TITLE, "Home");
		request.setAttribute(Attribute.s_LOGIN_RESPONSE, userSessionInfo);
	}

}
