package ilu.surveytool.databasemanager.constants;

public class DBFieldNames {
	
	//anonymousUser
	 	public final static String s_ANONYMOUS_USER_ID = "idAnonimousUser";
		public final static String s_ANONYMOUS_USER_DATE = "createDate";
		public final static String s_RESPONSE_TIMESTAMP = "timestamp";
		public final static String s_CURRENT_PAGE = "currentPage";
	 
	//generico
		public final static String s_GENERICO_TITLE = "title";
		public final static String s_GENERICO_ALL_USERS = "allUsers";
		public final static String s_GENERICO_USERS_FINISHED = "usersFinished";
		public final static String s_CREATION_DATE = "creationDate";
		public final static String s_RESPONSES_NUMBER = "numResp";
		public final static String s_INDEX = "index";
		public final static String s_NUM_ELEMENTS = "numElements";
		
	//category
		public final static String s_CATEGORY_NAME = "categoryName";
	
	//content
		public final static String s_CONTENTID = "idContent";
		public final static String s_CONTENT_TEXT = "text";
		public final static String s_CONTENT_QUESTION = "question";
		public final static String s_CONTENT_OG = "optionsGroup";
		public final static String s_CONTENT_OPTIONS = "options";
		public final static String s_CONTENT_RESOURCE = "idResoruces";
	
	//contenttype
		public final static String s_CONTENT_TYPE_NAME = "contentTypeName";
		
	//forma
		public final static String s_FORMAID = "idForma";
		
	//language
		public final static String s_LANGUAGE_ISONAME = "isoName";
		public final static String s_LANGUAGE_NAME = "name";
		
	//option
		public final static String s_OPTIONID = "idOption";
		public final static String s_VALUE = "value";
		
	//optionsgroup
		public final static String s_OPTIONSGROUPID = "idOptionsGroup";
		public final static String s_OPTIONSGROUP_OPTIONTYPE_NAME = "optionTypeName";
		public final static String s_OPTIONSGROUP_RANDOM = "random";
		
	//page
		public final static String s_PAGE_ID = "idPage";
		public final static String s_NUM_PAGE = "numPage";
		
	//poll
		public final static String s_POLL_ID = "idPoll";
		public final static String s_POLL_CALL_URL = "callUrl";
		
	//project
		public final static String s_PROJECTID = "idProject";
		public final static String s_PROJECT_NAME = "projectName";
		
	//Question
		public final static String s_QUESTION_ID = "idQuestion";
		public final static String s_QUESTION_TAG = "tag";
		public final static String s_QUESTION_MANDATORY = "mandatory";
		public final static String s_QUESTION_OPTIONALANSWER = "optionalAnswer";
		public final static String s_QUESTION_MAIN_VERSION = "mainVersion";
		public final static String s_NUM_QUESTION = "numQuestions";
		public final static String s_PARAMETER_NAME = "parameterName";
		public final static String s_IDQUESTIONTYPE = "idQuestionType";
	
	//questiontype
		public final static String s_QUESTIONTYPE_NAME = "questionTypeName";
		public final static String s_QUESTIONTYPE_TEMPLATE_FILE = "templateFile";
		public final static String s_QUESTIONTYPE_FORM_FILE = "formFile";
		public final static String s_QUESTIONTYPE_STATISTICRESULTS_FILE = "statisticResultsFile";
		
	//questionnaire
		public final static String s_QUESTIONNAIREID = "idQuestionnaire";
		public final static String s_STATE = "state";
		public final static String s_START_DATE = "startDate";
		public final static String s_DEADLINE_DATE = "deadLineDate";
		public final static String s_PUBLIC_ID = "publicId";
		public final static String s_AUTHOR = "author";
		public final static String s_DEFAULT_LANGUAGE = "defaultLanguage";
		public final static String s_OBJETIVE = "objetive";
		
	//resources
		public final static String s_RESOURCEID = "idResoruces";
		public final static String s_RESOURCE_TYPE_NAME = "resourceTypeName";
		public final static String s_RESOURCE_URL_PATH = "urlPath";
		
	//resourcetype
		public final static String s_RESOURCE_TYPE_ID = "idResourceType";
		
	//rol
		public final static String s_ROLID = "idRol";
		public final static String s_ROLNAME = "rolName";
		
	//section
		public final static String s_SECTIONID = "idSection";
		
	//user
		public final static String s_USERID = "idUser";
		public final static String s_USERNAME = "username";
		public final static String s_USER_EMAIL = "email";
		public final static String s_USER_ISO_LANGUAGE = "isoName";
		
	//QDependences
		public final static String s_IDQDEPENDENCE = "idQDependences";
		public final static String s_SHOW = "show";
		public final static String s_DEPENDENCETYPE ="depType";
		public final static String s_DEPENDENCEITEM = "idDependenceItem";
		public final static String s_DEPENDENCEOPTIONID = "optionValue";
		public final static String s_QTEXT = "qText";
		public final static String s_OTEXT = "oText";
		public final static String s_COUNT = "count";
		
	//QDependences
		public final static String s_QDESTID = "idQuestionDest";
		public final static String s_OPTIONVALUE = "optionValue";

	//quota
		public final static String s_MAX_RESPONSES = "maxResponses";
		public final static String s_MIN_RESPONSES = "minResponses";


}
