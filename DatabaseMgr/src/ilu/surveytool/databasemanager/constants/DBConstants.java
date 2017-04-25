package ilu.surveytool.databasemanager.constants;

public class DBConstants {
	
	//DB Values
			public final static String s_VALUE_TOKEN = "-#-";
		//ContentType
			public final static String s_VALUE_CONTENTTYPE_NAME_TITLE = "title";
			public final static String s_VALUE_CONTENTTYPE_NAME_DESCRIPTION = "description";
			public final static String s_VALUE_CONTENTTYPE_NAME_OTHER = "other";
			public final static String s_VALUE_CONTENTTYPE_NAME_ALT_TEXT = "altText";
			public final static String s_VALUE_CONTENTTYPE_NAME_HELP_TEXT = "helpText";
			public final static String s_VALUE_CONTENTTYPE_NAME_ACKNOWLEDGMENT_TEXT = "ackText";
			public final static String s_VALUE_CONTENTTYPE_NAME_CALL_TEXT = "callText";
			public final static String s_VALUE_CONTENTTYPE_NAME_LABEL = "label";
			public final static String s_VALUE_CONTENTTYPE_NAME_OTHER_LABEL = "otherLabel";
		//ContentLength
			public final static int s_MAX_CONTENT_LENGTH = 1000;
		//OptionType	
			public final static String s_VALUE_OPTIONTYPE_CHECKBOX = "checkbox";
			public final static String s_VALUE_OPTIONTYPE_RADIO = "radio";		
		//OptionsGroup type
			public final static String s_VALUE_OPTIONSGROUP_TYPE_RADIO = "radio";
			public final static String s_VALUE_OPTIONSGROUP_TYPE_CHECKBOX = "checkbox";
			public final static String s_VALUE_OPTIONSGROUP_TYPE_SELECT = "select";
		//Question parameters
			public final static String s_VALUE_QUESTIONTYPE_FORMFIELD = "formfield";
			public final static String s_VALUE_QUESTIONTYPE_PARAGRAPH = "paragraph";
			public final static String s_VALUE_QUESTIONTYPE_GRADING = "grading";
			public final static String s_VALUE_QUESTIONTYPE_ORDERING = "ordering";
			public final static String s_VALUE_QUESTIONTYPE_MATRIX = "matrix";
			public final static String s_VALUE_QUESTIONTYPE_SCALE = "scale";
			public final static String s_VALUE_QUESTIONTYPE_CODE = "code";
			public final static String s_VALUE_QUESTIONTYPE_MULTIPLE = "multiple";
			public final static String s_VALUE_QUESTIONTYPE_SIMPLE_RADIO = "simpleRadio";
			public final static String s_VALUE_QUESTIONTYPE_SIMPLE_COMBO = "simpleCombo";
			public final static String s_VALUE_QUESTIONTYPE_LONGTEXT = "longText";
			public final static String s_VALUE_QUESTIONTYPE_SHORTTEXT = "shortText";			
			public final static String s_VALUE_QUESTIONTYPE_BCONTENT = "bcontent";
			
			public final static String s_VALUE_QUESTIONPARAMETER_MATRIXTYPE = "matrixType";
			public final static String s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_SIMPLE = "Simple";
			public final static String s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_MULTIPLE = "Multiple";
			public final static String s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_PULLDOWN = "Pull-down";
			public final static String s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_FREE = "Free";
			
			public final static String s_VALUE_QUESTIONPARAMETER_OTHERLENGTH = "otherLength";
			public final static String s_VALUE_QUESTIONPARAMETER_MAXVALUE = "maxValue";
			public final static String s_VALUE_QUESTIONPARAMETER_MINVALUE = "minValue";
			
			public final static String s_VALUE_QUESTIONPARAMETER_TEXTLENGTH = "textLength";	
			public final static String s_VALUE_QUESTIONPARAMETER_TEXTLINES = "textLines";
			public final static String s_VALUE_QUESTIONPARAMETER_DECIMALS = "decimals";	//The value is the number of numbers after comma
			
			public final static String s_VALUE_QUESTIONPARAMETER_FORMFIELD_INPUT_MODE = "formFieldInputMode";
			public final static String s_VALUE_QUESTIONPARAMETER_FORMFIELD_INPUT_MODE_FREE = "formFieldInputModeFree";
			public final static String s_VALUE_QUESTIONPARAMETER_FORMFIELD_INPUT_MODE_PULLDOWN = "formFieldInputModePullDown";
			public final static String s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE = "formFieldType";
			public final static String s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_RANGE = "formFieldRange";
			public final static String s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_CUSTOM = "formFieldTypeCustom";
			public final static String s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_GENERAL = "formFieldTypeGeneral";
			public final static String s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_TEXT = "formFieldTypeText";
			public final static String s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_NUMBER = "formFieldTypeNumber";
			public final static String s_VALUE_QUESTIONPARAMETER_SIMPLE_TYPE_ANSWER = "typeAnswer";
			public final static String s_VALUE_QUESTIONPARAMETER_SCALE_TYPE = "scaleType";
		//language
			public final static String s_VALUE_LANGUAGE_ISONAME_ENGLISH = "en";
			public final static String s_VALUE_LANGUAGE_ISONAME_SPANISH = "es";
		//UserQuestionnaire
			public final static String s_VALUE_STATE_FINISHED = "finished";
		//UserState
			public final static int i_VALUE_USER_STATE_ID_ACTIVE = 1;
			public final static int i_VALUE_USER_STATE_ID_EMAIL = 2;
			public final static int i_VALUE_USER_STATE_ID_ADMIN = 3;
		//Resource
			public final static String s_VALUE_RESOURCE_TYPE_IMAGE = "image";
			public final static String s_VALUE_RESOURCE_TYPE_VIDEO = "video";
		//Rol
			public final static String s_VALUE_ROLNAME_ADMIN = "admin";
			public final static String s_VALUE_ROLNAME_INTERVIEWER = "interviewer";
			public final static String s_VALUE_ROLNAME_USER = "user";	
		//state
			public final static String s_VALUE_SURVEY_STATE_PAUSED = "paused";
			public final static String s_VALUE_SURVEY_STATE_ACTIVE = "active";
			public final static String s_VALUE_SURVEY_STATE_FINISHED = "finished";
		//user state
			public final static int i_VALUE_USER_STATE_ACTIVE = 1;
			public final static int i_VALUE_USER_STATE_EMAIL_CONFIRM = 2;
			
		//Dependence
			public final static String s_VALUE_DEPENDENCETYPE_AND = "And";
			public final static String s_VALUE_DEPENDENCETYPE_OR = "Or";
}
