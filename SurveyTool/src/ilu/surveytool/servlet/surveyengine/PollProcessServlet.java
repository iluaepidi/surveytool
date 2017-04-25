package ilu.surveytool.servlet.surveyengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ilu.surveyengine.handler.PollProcessHandler;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.AnonimousDB;
import ilu.surveytool.databasemanager.DataObject.PollResultResume;
import ilu.surveytool.databasemanager.DataObject.Response;
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
		String ipAddress = request.getRemoteAddr();
		
		boolean preview = request.getParameter("preview") != null;
				
		PollProcessHandler ppHandler = new PollProcessHandler();
		
		AnonimousDB anonimousDB = new AnonimousDB();
		//if(!anonimousDB.existAnonimousUserByIpAddressPollPublicId(pollId, ipAddress) && !preview)
		if(!preview)
		{
		
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
		
			int anonymousUserId = ppHandler.storePollResponse(pollRes, ipAddress);
		}
		
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		List<String> jsFiles = new ArrayList<>();
		jsFiles.add(properties.getJsFilePath(Address.s_JS_CHART_GRAPHICS));
		request.setAttribute(Attribute.s_JS_FILES, jsFiles);
		
		request.setAttribute(Attribute.s_POLL_TITLE, ppHandler.getPollTitle(pollId, language));
		
		request.setAttribute(Attribute.s_POLL_INFO, ppHandler.getPollDetail(pollId, language));
		
		if(preview) request.setAttribute(Attribute.s_RESPONSES_INFO, ppHandler.getPollPreviewResultsResume(pollId, language));
		else request.setAttribute(Attribute.s_RESPONSES_INFO, ppHandler.getPollResultsResume(pollId, language));
		
		request.setAttribute(Attribute.s_BODY_PAGE, properties.getBudyPagePath(Address.s_BODY_POLL_RESULT));
		request.setAttribute(Attribute.s_PAGE_TITLE, "Final page");
		
		CommonCode.redirect(request, response, Address.s_MASTER_POLL);
	}

}
