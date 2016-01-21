package ilu.surveytool.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ilu.surveytool.constants.Attribute;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.properties.SurveyToolProperties;

/**
 * Servlet implementation class ImportFileServlet
 */
@WebServlet("/ImportFileServlet")
@MultipartConfig
public class ImportFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImportFileServlet() {
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
		System.out.println("Llega la petición a ImportFileServlet");
		
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		SurveyToolProperties properties = new SurveyToolProperties(getServletContext().getRealPath("/"));
		
		if(userSessionInfo != null && userSessionInfo.isValid())
		{
			Enumeration<String> names = request.getParameterNames();
			while(names.hasMoreElements())
			{
				System.out.println("Parameter: " + names.nextElement());
			}
						
			try {
				//String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
			    Part filePart;
				filePart = request.getPart("uploadedFile");
				String fileName = filePart.getSubmittedFileName();
			    System.out.print("File name: " + fileName);
			    InputStream fileContent = filePart.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Retrieves <input type="file" name="file">
		    
		}
		    
	
	}

}
