package ilu.surveytool.servlet.surveymanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;

import ilu.surveymanager.handler.QuotaHandler;
import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Quota;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.surveytool.sessioncontrol.SessionHandler;

/**
 * Servlet implementation class SurveysFees
 */
@WebServlet("/SurveysFees")
public class SurveysFees extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SurveysFees() {
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
   		System.out.println("processRequest en SurveysServlet");
   		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
   		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
   		
   		if(userSessionInfo != null && userSessionInfo.isValid())
   		{
   			//cargar lenguaje seleccionado en el combo
   			int surveyId = Integer.parseInt(request.getParameter(Parameter.s_SURVEY_ID));		
   			SurveysHandler surveysHandler = new SurveysHandler();
   			String language = request.getParameter(Parameter.s_LANGUAGE_SURVEY);
   			
   			Survey survey = surveysHandler.getSurveyDetail(surveyId, language);
   			Survey surveyDefaultLanguage = surveysHandler.getSurveyDetail(surveyId, survey.getDefaultLanguage());
   			
   			JSONArray pages = surveysHandler.getQuestionsJson(survey);
            request.setAttribute(Attribute.s_JSON_QUOTAS, pages);
            
            QuotaHandler qHandler = new QuotaHandler();
            List<Quota> listQuotas = qHandler.getListQuotas(survey.getSurveyId());
            request.setAttribute(Attribute.s_LIST_QUOTAS, listQuotas);
   			//completeTextDefault(survey,surveyDefaultLanguage);
   			
   			request.setAttribute(Attribute.s_SURVEY_INFO, survey);
   			request.setAttribute(Attribute.s_SURVEY_INFO_DEFAULT_LANGUAGE, surveyDefaultLanguage);
   			
   			List<String> jsFiles = new ArrayList<>();
   			jsFiles.add(properties.getJsFilePath(Address.s_JS_EDIT_SURVEY));
   			jsFiles.add(properties.getJsFilePath(Address.s_JS_CONTROLLES_ACCESIBLES_YOUTUBE));
   			jsFiles.add(properties.getJsFilePath(Address.s_JS_YOUTUBE_IFRAME_API));
   			request.setAttribute(Attribute.s_JS_FILES, jsFiles);
   			
   			List<String> cssFiles = new ArrayList<>();
   			cssFiles.add(properties.getCssFilePath(Address.s_CSS_CONTROLLES_ACCESIBLES_YOUTUBE));
   			request.setAttribute(Attribute.s_CSS_FILES, cssFiles);
   			
   			int pageId = surveysHandler.getPageIdBySurveyId(surveyId);
   			request.setAttribute(Attribute.s_PAGE_ID, pageId);
   			request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_EDIT_FEES));
   			request.setAttribute(Attribute.s_PAGE_TITLE, "Edit survey");
   			
   			if(language==null || language.equals(survey.getDefaultLanguage())){
   				request.setAttribute(Attribute.s_ADD_QUESTIONS, true);
   			}else{
   				request.setAttribute(Attribute.s_ADD_QUESTIONS, false);
   			}
   		}
   		else
   		{
   			SessionHandler sessionHandler = new SessionHandler();
   			sessionHandler.sessionClosed(request, properties);
   		}
   		
   		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
   		System.out.println(response);
   	}

}
