package ilu.surveytool.constants;


/**
 * 
 * @author JAgutierrez
 *
 *	This class contains the constants about the Servlet and JSPs location, 
 *	and keys to JSP properties file. This properties file contain the location of the 
 *	JSP with the body of the pages. 
 */

public class Address {
	//BodypPagesNames
		public final static String s_BODY_LOGIN = "login";
		public final static String s_BODY_POLL_EXAMPLE_CODE = "pollExampleCode";
		public final static String s_BODY_POLL_QUESTION = "pollQuestion";
		public final static String s_BODY_POLL_RESULT = "pollResult";
		public final static String s_BODY_USER_PANEL_HOME = "userPanelHome";
		public final static String s_BODY_SURVEY_PAGE = "surveyPage";
		public final static String s_BODY_SURVEY_FINISH_PAGE = "surveyFinishPage";
		public final static String s_BODY_SURVEYS = "surveys";
		public final static String s_BODY_EDIT_SURVEY = "editSurvey";
		public final static String s_BODY_EDIT_STATISTICS = "surveyStatistics";
		public final static String s_BODY_EDIT_FEES = "editFees";
		public final static String s_BODY_PROFILE = "profile";
	
	//cssFiles
		public final static String s_CSS_CONTROLLES_ACCESIBLES_YOUTUBE = "controlesReproYoutubeAcc";
		
	//jsFiles
		public final static String s_JS_EDIT_SURVEY = "editSurvey";
		public final static String s_JS_EDIT_SURVEY_DEPENDENCES = "editSurveyDependences";
		public final static String s_JS_EDIT_POLL = "editPoll";
		public final static String s_JS_CHART_GRAPHICS = "chart";
		public final static String s_JS_ZERO_CLIPBOARD = "zeroClipboard";
		public final static String s_JS_CONTROLLES_ACCESIBLES_YOUTUBE = "controlesAccesiblesYoutubeIframes";
		public final static String s_JS_YOUTUBE_IFRAME_API = "youtubeIframe";
		
	//Servielt
		public final static String s_SERVLET_CREATE_QUESTION = "CreateQuestionServlet";
		public final static String s_SERVLET_CREATE_SURVEY_SERVLET = "CreateSurveyServlet";
		public final static String s_SERVLET_LOGIN = "LoginServlet";
		public final static String s_SERVLET_SURVEYS_SERVLET = "SurveysServlet";
		public final static String s_SERVLET_SURVEY_PROCESS = "SurveyProcessServlet";
		public final static String s_SERVLET_USER_PANEL_HOME = "UserPanelHomeServlet";
		
	//JSP MasterPage
		public final static String s_INIT_PAGE = "inicio.jsp";
		public final static String s_MASTER_PAGE = "master.jsp";
		public final static String s_MASTER_POLL = "masterPoll.jsp";
		public final static String s_EDIT_QUESTION_MASTER= "EditQuestionMaster.jsp";
		public final static String s_PAGE= "jsp/surveyManager/components/cPage.jsp";
		public final static String s_MULTIMEDIA_ITEM= "jsp/surveyManager/components/cMultimediaItem.jsp";
		public final static String s_IMPORT_IMAGE_OPTION= "jsp/surveyManager/components/cImportImageOptions.jsp";
		public final static String s_POLL_ROW= "jsp/surveyManager/components/cPollRow.jsp";
		public final static String s_POLLS_TABLE_LIST= "jsp/surveyManager/components/cPollsTableList.jsp";
		
	//Folder paths
		public final static String s_FOLDER_RESOURCES= "resources/";
}
