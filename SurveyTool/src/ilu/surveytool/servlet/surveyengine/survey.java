package ilu.surveytool.servlet.surveyengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ilu.surveyengine.handler.SurveyProcessHandler;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.language.Language;
import ilu.surveytool.properties.SurveyToolProperties;

/**
 * Servlet implementation class survey
 */
@WebServlet("/survey")
public class survey extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public survey() {
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
		String sid = request.getParameter(Parameter.s_SID);
		String language = request.getParameter(Parameter.s_LANGUAGE_SURVEY);
		
		System.out.println("SID: " + sid + " - Language: " + language);
		Language lang = new Language(getServletContext().getRealPath("/")); 
		lang.loadLanguage(language); 
		request.setAttribute(Attribute.s_SURVEY_LANGUAGE, lang);
		
		SurveyProcessHandler surveyProcessHandler = new SurveyProcessHandler();
		Survey survey = surveyProcessHandler.getSurveyDetailByPublicId(sid, language);
		request.setAttribute(Attribute.s_SURVEY_INFO, survey);
		String surveyTitle = "";
		if(survey.getContents() != null && !survey.getContents().isEmpty() && survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE) != null) surveyTitle = survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
		request.setAttribute(Attribute.s_PAGE_TITLE, surveyTitle);

		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		List<String> jsFiles = new ArrayList<>();
		jsFiles.add(properties.getJsFilePath(Address.s_JS_CONTROLLES_ACCESIBLES_YOUTUBE));
		jsFiles.add(properties.getJsFilePath(Address.s_JS_YOUTUBE_IFRAME_API));
		request.setAttribute(Attribute.s_JS_FILES, jsFiles);

		List<String> cssFiles = new ArrayList<>();
		cssFiles.add(properties.getCssFilePath(Address.s_CSS_CONTROLLES_ACCESIBLES_YOUTUBE));
		request.setAttribute(Attribute.s_CSS_FILES, cssFiles);
		
		request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_SURVEY_PAGE));
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
	}

}
