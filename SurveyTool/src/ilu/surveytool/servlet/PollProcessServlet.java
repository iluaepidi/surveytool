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
import ilu.surveytool.databasemanager.DataObject.Response;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.orchestrator.PollProcessOrch;
import ilu.surveytool.orchestrator.SurveyProcessOrch;
import ilu.surveytool.properties.SurveyToolProperties;

/**
 * Servlet implementation class PollProcessServlet
 */
@WebServlet("/PollProcessServlet")
public class PollProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String language = "en";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PollProcessServlet() {
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
		Response pollRes = null;
		int pollId = Integer.parseInt(request.getParameter(Parameter.s_PID));
		
		while(paramNames.hasMoreElements())
		{
			String element = paramNames.nextElement();
			if(!element.equalsIgnoreCase(Parameter.s_PID) && !element.equalsIgnoreCase(Parameter.s_SEND_POLL_BUTTON))
			{
				String[] elemSplit = element.split("-");
				int questionId = Integer.parseInt(elemSplit[0]);
				int optionsGroupId = 0;
				if(elemSplit.length > 1) optionsGroupId = Integer.parseInt(elemSplit[1]);
				pollRes = new Response(questionId, optionsGroupId, request.getParameter(element), pollId); 
			
				System.out.println("Param: " + pollRes.toString());				
			}
		}
		
		PollProcessOrch ppOrch = new PollProcessOrch();
		int anonymousUserId = ppOrch.storePollResponse(pollRes);

		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		List<String> jsFiles = new ArrayList<>();
		jsFiles.add(properties.getJsFilePath(Address.s_JS_CHART_GRAPHICS));
		request.setAttribute(Attribute.s_JS_FILES, jsFiles);
		
		request.setAttribute(Attribute.s_POLL_TITLE, ppOrch.getPollTitle(pollId, language));
		
		request.setAttribute(Attribute.s_POLL_INFO, ppOrch.getPollDetail(pollId, language));
		
		request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_POLL_RESULT));
		request.setAttribute(Attribute.s_PAGE_TITLE, "Final page");
		
		CommonCode.redirect(request, response, Address.s_MASTER_POLL);
	}

}
