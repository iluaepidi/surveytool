package ilu.surveytool.servlet.surveymanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
 * Servlet implementation class SurveyStatisticServlet
 */
@WebServlet("/SurveyStatisticServlet")
//Nombre a poner en los href para que entre

public class SurveyStatisticServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//String language = "en";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SurveyStatisticServlet() {
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
		System.out.println("processRequest en SurveyStatisticServlet");
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		if(userSessionInfo != null && userSessionInfo.isValid())
		{
			//cargar lenguaje seleccionado en el combo
			
			int surveyId = Integer.parseInt(request.getParameter(Parameter.s_SURVEY_ID));		
			SurveysHandler surveysHandler = new SurveysHandler();
			String language = request.getParameter(Parameter.s_LANGUAGE_SURVEY);
			
			//System.out.println("Lang en processRequest de SurveyServlet: "+language);
			
			Survey survey = surveysHandler.getSurveyDetail(surveyId, language);
			
			Survey surveyDefaultLanguage = surveysHandler.getSurveyDetail(surveyId, survey.getDefaultLanguage());
			
			
			//completeTextDefault(survey,surveyDefaultLanguage);
			
			request.setAttribute(Attribute.s_SURVEY_INFO, survey);
			request.setAttribute(Attribute.s_SURVEY_INFO_DEFAULT_LANGUAGE, surveyDefaultLanguage);
			
			List<String> jsFiles = new ArrayList<>();
			jsFiles.add(properties.getJsFilePath(Address.s_JS_EDIT_SURVEY));
			jsFiles.add(properties.getJsFilePath(Address.s_JS_CHART_GRAPHICS));
			request.setAttribute(Attribute.s_JS_FILES, jsFiles);
			
			List<String> cssFiles = new ArrayList<>();
			request.setAttribute(Attribute.s_CSS_FILES, cssFiles);
			
			request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_EDIT_STATISTICS));
			
			request.setAttribute(Attribute.s_PAGE_TITLE, "Survey Statistics");
			
			//QUotas Result
			JSONArray pages = surveysHandler.getQuestionsFeesJson(survey);
            request.setAttribute(Attribute.s_JSON_QUOTAS, pages);
            
            QuotaHandler qHandler = new QuotaHandler();
            HashMap<Integer,ArrayList<Quota>> listQuotas = qHandler.getListQuotasResults(survey);
            request.setAttribute(Attribute.s_LIST_QUOTAS_RESULTS, listQuotas);
			
			
		}
		else
		{
			SessionHandler sessionHandler = new SessionHandler();
			sessionHandler.sessionClosed(request, properties);
		}
		
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
		//System.out.println(response);
	}

}
