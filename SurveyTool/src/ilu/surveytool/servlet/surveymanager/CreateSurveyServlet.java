package ilu.surveytool.servlet.surveymanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ilu.surveymanager.handler.SectionHandler;
import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.surveytool.sessioncontrol.SessionHandler;

/**
 * Servlet implementation class CreateSurveyServlet
 */
@WebServlet("/CreateSurveyServlet")
public class CreateSurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String language = "en";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateSurveyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("processRequest in CreateSurveyServlet");
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		if(userSessionInfo != null && userSessionInfo.isValid())
		{
			Survey survey = new Survey();
			this.language = request.getParameter(Parameter.s_DEFAULT_LANGUAGE);
			survey.setDefaultLanguage(this.language);
			survey.getContents().put(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, new Content(0, this.language, DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, request.getParameter(Parameter.s_TITLE)));
			survey.getContents().put(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION, new Content(0, this.language, DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION, request.getParameter(Parameter.s_DESCRIPTION)));
			survey.setProject(request.getParameter(Parameter.s_PROJECT));
			survey.setAuthor(userSessionInfo.getUserId());
			
			System.out.println("Survey: " + survey.toString());
			
			SurveysHandler surveysHandler = new SurveysHandler();
			int surveyId = surveysHandler.createSurvey(survey);
			
			survey.setPublicId(surveysHandler.getSurveyPublicId(surveyId));
			
			survey.setSurveyId(surveyId);
			
			if(surveyId > 0)
			{	

				//request.setAttribute(Attribute.s_SURVEY_INFO, survey);
				
				int pageId = surveysHandler.createFormaSectionAndPage(surveyId);
				request.setAttribute(Attribute.s_PAGE_ID, pageId);
				
				/*SectionHandler sectionHandler = new SectionHandler();
				survey.setSections(sectionHandler.getSectionsBySurveyId(surveyId, language));
				
				List<String> jsFiles = new ArrayList<>();
				jsFiles.add(properties.getJsFilePath(Address.s_JS_EDIT_SURVEY));
				request.setAttribute(Attribute.s_JS_FILES, jsFiles);
				request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_EDIT_SURVEY));
				request.setAttribute(Attribute.s_PAGE_TITLE, "Edit survey");*/
				
				String editSurveyUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/SurveyTool/SurveysServlet?surveyid=" + surveyId;
				
				try {
					response.sendRedirect(editSurveyUrl);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else
			{

				CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
			}
			
		}
		else
		{
			SessionHandler sessionHandler = new SessionHandler();
			sessionHandler.sessionClosed(request, properties);
			
			CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
		}
		
	}

}
