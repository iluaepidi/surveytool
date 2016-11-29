package ilu.surveytool.servlet.surveymanager;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ilu.surveymanager.handler.PageHandler;
import ilu.surveymanager.handler.SectionHandler;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Page;
import ilu.surveytool.databasemanager.DataObject.Section;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.surveytool.sessioncontrol.SessionHandler;

/**
 * Servlet implementation class CreateSectionServlet
 */
@WebServlet("/CreateSectionServlet")
public class CreateSectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateSectionServlet() {
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
		System.out.println("processRequest en CreateSectionServlet");
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));		

		if(userSessionInfo != null && userSessionInfo.isValid())
		{
			int formaId = Integer.parseInt(request.getParameter(Parameter.s_FID));
			int surveyId = Integer.parseInt(request.getParameter(Parameter.s_SID));

			SectionHandler sectionHandler = new SectionHandler();
			Section section = sectionHandler.createSection(surveyId, formaId, userSessionInfo.getIsoLanguage());
			
			request.setAttribute(Attribute.s_INDEX, section.getIndex());
			request.setAttribute(Attribute.s_SECTION, section);
			request.setAttribute(Attribute.s_ADD_QUESTIONS, true);
			
			CommonCode.redirect(request, response, Address.s_SECTION);
		}
		else
		{
			SessionHandler sessionHandler = new SessionHandler();
			sessionHandler.sessionClosed(request, properties);
			
			CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
		}		
	}

}
