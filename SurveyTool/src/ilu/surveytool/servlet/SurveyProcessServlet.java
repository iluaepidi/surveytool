package ilu.surveytool.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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
import ilu.surveytool.databasemanager.DataObject.AnonimousResponse;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.orchestrator.SurveyProcessOrch;
import ilu.surveytool.orchestrator.SurveysOrch;
import ilu.surveytool.properties.SurveyToolProperties;

/**
 * Servlet implementation class SurveyProcessServlet
 */
@WebServlet("/SurveyProcessServlet")
public class SurveyProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SurveyProcessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	{
		Enumeration<String> paramNames = request.getParameterNames();
		List<AnonimousResponse> responses = new ArrayList<AnonimousResponse>();
		int surveyId = Integer.parseInt(request.getParameter(Parameter.s_SID));
		
		while(paramNames.hasMoreElements())
		{
			String element = paramNames.nextElement();
			if(!element.equalsIgnoreCase(Parameter.s_SID))
			{
				String[] elemSplit = element.split("-");
				int questionId = Integer.parseInt(elemSplit[0]);
				int optionsGroupId = 0;
				if(elemSplit.length > 1) optionsGroupId = Integer.parseInt(elemSplit[1]);
				AnonimousResponse aresp = new AnonimousResponse(questionId, optionsGroupId, request.getParameter(element)); 
				responses.add(aresp);
				System.out.println("Param: " + aresp.toString());				
			}
		}
		
		SurveyProcessOrch spOrch = new SurveyProcessOrch();
		boolean stored = spOrch.storeAnonimousResponse(surveyId, responses);
		
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_SURVEY_FINISH_PAGE));
		request.setAttribute(Attribute.s_PAGE_TITLE, "Final page");
		
		CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
	}

}
