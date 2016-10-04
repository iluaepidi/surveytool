package ilu.surveytool.servlet.surveyengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;

import ilu.surveyengine.handler.SurveyProcessHandler;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.SurveyDB;
import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.DataObject.AnonimousUser;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.language.Language;
import ilu.surveytool.properties.SurveyToolProperties;

/**
 * Servlet implementation class surveyajs
 */
@WebServlet("/surveyajs")
public class surveyajs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public surveyajs() {
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
		SurveyDB surveyDB = new SurveyDB();
		String sid = request.getParameter(Parameter.s_SID);
		String language = request.getParameter(Parameter.s_LANGUAGE_SURVEY);
		SurveyProcessHandler surveyProcessHandler = new SurveyProcessHandler();
		
		AnonimousUser anonimousUser = new AnonimousUser();
		anonimousUser.setIpAddress(request.getRemoteAddr());
		anonimousUser.setSurveyId(surveyDB.getQuestionnaireIdByPublicId(sid));
		anonimousUser.setCurrentPage(1);
		anonimousUser = surveyProcessHandler.existAnonimousUser(anonimousUser);
		request.getSession().setAttribute(Attribute.s_ANONIMOUS_USER, anonimousUser);
		
		//System.out.println("SID: " + sid + " - Language: " + language);
		Language lang = new Language(getServletContext().getRealPath("/")); 
		lang.loadLanguage(language); 
		request.getSession().setAttribute(Attribute.s_SURVEY_LANGUAGE, lang);
		
		//int currentPage = (anonimousUser.getId() != 0 ? anonimousUser.getCurrentPage() : 1);
		JSONObject survey = surveyProcessHandler.getCurrentPageJson(sid, 1, anonimousUser, language);
		System.out.println("Json: " + survey.toString());

		request.setAttribute(Attribute.s_SURVEY_INFO, survey);
		String surveyTitle = "";
		//if(survey.getContents() != null && !survey.getContents().isEmpty() && survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE) != null) surveyTitle = survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
		request.setAttribute(Attribute.s_PAGE_TITLE, surveyTitle);

		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		List<String> jsFiles = new ArrayList<>();
		jsFiles.add(properties.getJsFilePath(Address.s_JS_CONTROLLES_ACCESIBLES_YOUTUBE));
		jsFiles.add(properties.getJsFilePath(Address.s_JS_YOUTUBE_IFRAME_API));
		jsFiles.add(properties.getJsFilePath(Address.s_JS_ANGULAR));
		jsFiles.add(properties.getJsFilePath(Address.s_JS_ANGULAR_SANITIZE));
		jsFiles.add(properties.getJsFilePath(Address.s_JS_ANGULAR_ROUTER));
		jsFiles.add(properties.getJsFilePath(Address.s_NG_CONTROLLER_SURVEY));
		jsFiles.add(properties.getJsFilePath(Address.s_NG_SERVICE_SURVEY));
		jsFiles.add(properties.getJsFilePath(Address.s_JS_YOUTUBE_IFRAME_ANGULAR_API));
		jsFiles.add(properties.getJsFilePath(Address.s_JS_YOUTUBE_ANGULAR_EMBED));
		request.setAttribute(Attribute.s_JS_FILES, jsFiles);

		List<String> cssFiles = new ArrayList<>();
		cssFiles.add(properties.getCssFilePath(Address.s_CSS_CONTROLLES_ACCESIBLES_YOUTUBE));
		request.setAttribute(Attribute.s_CSS_FILES, cssFiles);
		
		request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_SURVEY_PAGE_AJS));
		CommonCode.redirect(request, response, Address.s_SURVEY_MASTER_PAGE);
	}

}
