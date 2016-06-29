package ilu.surveytool.servlet.surveymanager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ilu.surveymanager.handler.QuestionHandler;
import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.surveytool.sessioncontrol.SessionHandler;

/**
 * Servlet implementation class CreateQuestionServlet
 */
@WebServlet("/CreateQuestionServlet")
public class CreateQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String language = "en";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestionServlet() {
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
		System.out.println("processRequest en CreateQuestionServlet");
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		if(userSessionInfo != null && userSessionInfo.isValid())
		{
			Question question = new Question();
			question.setQuestionType(request.getParameter(Parameter.s_QTYPE));
			System.out.println(request.getParameter(Parameter.s_QTYPE));
			if (request.getParameter(Parameter.s_QTYPE).equals("matrix")){
				HashMap<String, String> matrixType = new HashMap<String, String>();
				matrixType.put(DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE, DBConstants.s_VALUE_QUESTIONPARAMETER_MATRIXTYPE_VALUE_SIMPLE);
				question.setParameters(matrixType);
			}
			
			if (request.getParameter(Parameter.s_QTYPE).equals("longText")){
				HashMap<String, String> longtext = new HashMap<String, String>();
				longtext.put(DBConstants.s_VALUE_QUESTIONPARAMETER_TEXTLINES, "");
				question.setParameters(longtext);
			}
			
			if (request.getParameter(Parameter.s_QTYPE).equals("shortText")){
				HashMap<String, String> shorttext = new HashMap<String, String>();
				shorttext.put(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_INPUT_MODE, DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_INPUT_MODE_FREE);
				shorttext.put(DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE, DBConstants.s_VALUE_QUESTIONPARAMETER_FORMFIELD_TYPE_GENERAL);
				question.setParameters(shorttext);
			}
			
			question.setCategory("generic");
			question.setTag("generic");
			question.setHelpText(Boolean.parseBoolean(request.getParameter(Parameter.s_HELP_TEXT)));
			question.setMandatory(Boolean.parseBoolean(request.getParameter(Parameter.s_MANDATORY)));
			question.setOptionalAnswer(Boolean.parseBoolean(request.getParameter(Parameter.s_OPTIONALANSWER)));
			question.getContents().put(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, new Content(0, language, DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, request.getParameter(Parameter.s_QSTATEMENT)));
			
			int pageId = Integer.parseInt(request.getParameter(Parameter.s_PAGE_ID));
			
			QuestionHandler questionHandler = new QuestionHandler();
			int questionId = questionHandler.createQuestion(question, pageId);
			question.setQuestionId(questionId);
			String templateFile = questionHandler.getQuestionTypeTemplateFile(question.getQuestionType());
			request.setAttribute(Attribute.s_TEMPLATE_FILE, templateFile);
			request.setAttribute(Attribute.s_QUESTION, question);	
			CommonCode.redirect(request, response, Address.s_EDIT_QUESTION_MASTER);
		}
		else
		{
			SessionHandler sessionHandler = new SessionHandler();
			sessionHandler.sessionClosed(request, properties);
			
			CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
		}
		
		//CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
	}

}
