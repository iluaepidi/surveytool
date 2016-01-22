package ilu.surveytool.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Resource;
import ilu.surveytool.databasemanager.constants.DBConstants;
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
		String rootPath = getServletContext().getRealPath("/");
		SurveyToolProperties properties = new SurveyToolProperties(rootPath);
		
		if(userSessionInfo != null && userSessionInfo.isValid())
		{						
			try {
			    Part filePart;
				filePart = request.getPart("uploadedFile");
				String fileName = filePart.getSubmittedFileName();

			    InputStream fileContent = filePart.getInputStream();
			    System.out.println("RootPath: " + rootPath);
			    Path fpath = Paths.get("C:\\resources", fileName);
			    Files.copy(fileContent, fpath);

				Resource resource = new Resource();
				resource.setPathFile(Address.s_FOLDER_RESOURCES + fileName);
				resource.getContents().put(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, request.getParameter(Parameter.s_RESOURCE_TITLE));
				resource.getContents().put(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT, request.getParameter(Parameter.s_RESOURCE_ALTERNTIVE_TEXT));
				resource.setType("image");
			    System.out.print("Resource: " + resource);
			    
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
