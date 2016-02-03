package ilu.surveytool.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.orchestrator.QuestionOrch;
import ilu.surveytool.orchestrator.SurveysOrch;
import ilu.surveytool.properties.SurveyToolProperties;

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
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		if(userSessionInfo != null && userSessionInfo.isValid())
		{
			String mainVersion = request.getParameter(Parameter.s_MAIN_VERSION);
			Question question = new Question();
			question.setQuestionType(request.getParameter(Parameter.s_QTYPE));
			question.setCategory("generic");
			question.setTag("generic");
			question.setHelpText(Boolean.parseBoolean(request.getParameter(Parameter.s_HELP_TEXT)));
			question.setMainVersion(request.getParameter(Parameter.s_MAIN_VERSION));
			question.setMandatory(Boolean.parseBoolean(request.getParameter(Parameter.s_MANDATORY)));
			question.getContents().put(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, new Content(0, mainVersion, DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, request.getParameter(Parameter.s_QSTATEMENT)));
			
			int pageId = Integer.parseInt(request.getParameter(Parameter.s_PAGE_ID));
			
			QuestionOrch questionOrch = new QuestionOrch();
			int questionId = questionOrch.createQuestion(question, pageId);
			question.setQuestionId(questionId);
			String templateFile = questionOrch.getQuestionTypeTemplateFile(question.getQuestionType());
			request.setAttribute(Attribute.s_TEMPLATE_FILE, templateFile);
			request.setAttribute(Attribute.s_QUESTION, question);			
			
			CommonCode.redirect(request, response, Address.s_EDIT_QUESTION_MASTER);
		}
		else
		{
			userSessionInfo = new LoginResponse();
			userSessionInfo.setErrorMsg("Session is expired or not exist.");
			request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_LOGIN));
			request.setAttribute(Attribute.s_PAGE_TITLE, "Home");
			request.setAttribute(Attribute.s_LOGIN_RESPONSE, userSessionInfo);
			
			CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
		}
		
		//CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
	}

}
