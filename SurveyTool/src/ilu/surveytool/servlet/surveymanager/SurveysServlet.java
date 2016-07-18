package ilu.surveytool.servlet.surveymanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Page;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Section;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.surveytool.sessioncontrol.SessionHandler;

/**
 * Servlet implementation class SurveysServelt
 */
@WebServlet("/SurveysServlet")
public class SurveysServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//String language = "en";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SurveysServlet() {
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
			
			//completeTextDefault(survey,surveyDefaultLanguage);
			
			request.setAttribute(Attribute.s_SURVEY_INFO, survey);
			request.setAttribute(Attribute.s_SURVEY_INFO_DEFAULT_LANGUAGE, surveyDefaultLanguage);
			
			List<String> jsFiles = new ArrayList<>();
			jsFiles.add(properties.getJsFilePath(Address.s_JS_EDIT_SURVEY));
			request.setAttribute(Attribute.s_JS_FILES, jsFiles);
			
			int pageId = surveysHandler.getPageIdBySurveyId(surveyId);
			request.setAttribute(Attribute.s_PAGE_ID, pageId);
			request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_EDIT_SURVEY));
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
	}
	
	/*public void completeTextDefault(Survey survey, Survey surveyDefault){
		for(Section sec:survey.getSections()){
			for(Page p:sec.getPages()){
				for(Question q:p.getQuestions()){
					Iterator it = q.getContents().entrySet().iterator();
				    while (it.hasNext()) {
				        Map.Entry pair = (Map.Entry)it.next();
				        System.out.println(pair.getKey() + " = " + pair.getValue());
				        if(pair.getValue()==null || pair.getValue().equals("")){
				        	pair.setValue(this.completeByDefaultLanguage(surveyDefault, pair.getKey()));
				        }
				    }
					
				}
			}
		}
		
	}
	
	public Object completeByDefaultLanguage(Survey surveyDefault,Object key){
		for(Section sec:surveyDefault.getSections()){
			for(Page p:sec.getPages()){
				for(Question q:p.getQuestions()){
					return q.getContents().get(key); 
					
				}
			}
		}
		return null;
	}*/

}
