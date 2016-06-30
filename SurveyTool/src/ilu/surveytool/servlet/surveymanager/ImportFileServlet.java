package ilu.surveytool.servlet.surveymanager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ilu.surveymanager.handler.ResourceHandler;
import ilu.surveytool.commoncode.CommonCode;
import ilu.surveytool.constants.Address;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.LoginResponse;
import ilu.surveytool.databasemanager.DataObject.Resource;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.properties.SurveyToolProperties;
import ilu.surveytool.sessioncontrol.SessionHandler;

/**
 * Servlet implementation class ImportFileServlet
 */
@WebServlet("/ImportFileServlet")
@MultipartConfig
public class ImportFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String language = "en";
       
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
				String action = request.getParameter(Parameter.s_ACTION);
				
				if(action.equals("file") || action.equals("fileUpdate"))
				{
				    Part filePart;
					filePart = request.getPart("uploadedFile");
					String fileName = filePart.getSubmittedFileName();
					fileName = this._checkFilename(fileName);	
					
				    fileName = this._importFile(filePart, fileName, rootPath, 0);
	
					Resource resource = new Resource();
					resource.setPathFile(Address.s_FOLDER_RESOURCES + fileName);
					
					resource.setType("image");
				    System.out.print("Resource: " + resource);

			    	ResourceHandler resourceHandler = new ResourceHandler();
				    
				    if(action.equals("file"))
				    {
					    int questionId = Integer.parseInt(request.getParameter(Parameter.s_QID));
					    resourceHandler.insertResource(resource, questionId);
				    }
				    else if(action.equals("fileUpdate"))
				    {
				    	int resourceId = Integer.parseInt(request.getParameter(Parameter.s_RID));
				    	resourceHandler.updateResourceUrlPath(resourceId, resource.getPathFile());
				    }
				    
				    request.setAttribute(Attribute.s_RESOURCE, resource);
				    request.setAttribute(Attribute.s_ACTION, action);
				    
				    CommonCode.redirect(request, response, Address.s_IMPORT_IMAGE_OPTION);
				}
				else if(action.equals("options"))
				{
					int resourceId = Integer.parseInt(request.getParameter(Parameter.s_RID));
					this.language = request.getParameter(Parameter.s_MAIN_VERSION);
					HashMap<String, Content> contents = new HashMap<String, Content>();
					contents.put(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE,
							new Content(0, this.language, DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, request.getParameter(Parameter.s_RESOURCE_TITLE)));
					contents.put(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT,
							new Content(0, this.language, DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT, request.getParameter(Parameter.s_RESOURCE_ALTERNTIVE_TEXT)));
					
					ResourceHandler resourceHandler = new ResourceHandler();
					Resource resource = resourceHandler.insertImageContent(resourceId, contents);
					
					request.setAttribute(Attribute.s_RESOURCE, resource);
				    
				    CommonCode.redirect(request, response, Address.s_MULTIMEDIA_ITEM);
				}				
			    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Retrieves <input type="file" name="file">
		    
		}
		else
		{
			SessionHandler sessionHandler = new SessionHandler();
			sessionHandler.sessionClosed(request, properties);
			
			CommonCode.redirect(request, response, Address.s_MASTER_PAGE);
		}
		    
	
	}
	
	private String _importFile(Part filePart, String fileName, String rootPath, int index)
	{
		String fileNameFinal = fileName;
		try{
			if(index > 0)
			{
				String[] cads = fileName.split(Pattern.quote("."));
				fileNameFinal = cads[0] + index + "." + cads[1];
			}
			System.out.println("File Name: " + fileNameFinal);
			
			InputStream fileContent = filePart.getInputStream();
		    System.out.println("RootPath: " + rootPath);
		    Path fpath = Paths.get(rootPath + "\\resources", fileNameFinal);
		    Files.copy(fileContent, fpath);
		} catch (FileAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			index++;
			//fileNameFinal = this._importFile(filePart, fileName, rootPath, index);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileNameFinal;
	}
	
	private String _checkFilename(String fname)
	{
		String response = fname;
		
		if(fname.contains(":\\"))
		{
			Path path = Paths.get(fname);
			response = path.getFileName().toString();
		}
		return response;
	}

}
