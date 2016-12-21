package ilu.surveytool.servlet.surveymanager;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveymanager.data.Option;
import ilu.surveymanager.data.QDependence;
import ilu.surveymanager.data.QDependenceValue;
import ilu.surveymanager.handler.OptionHandler;
import ilu.surveymanager.handler.QDependenceHandler;
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
		int oId=0;
		
		/*Enumeration params = request.getParameterNames(); 
		while(params.hasMoreElements()){
		 String paramName = (String)params.nextElement();
		 System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
		}*/
		
		LoginResponse userSessionInfo = (LoginResponse) request.getSession().getAttribute(Attribute.s_USER_SESSION_INFO);
		String rootPath = getServletContext().getRealPath("/");
		SurveyToolProperties properties = new SurveyToolProperties(rootPath);
		
		
		request.setAttribute(Attribute.s_ADD_QUESTIONS, true);
		
		
		if(userSessionInfo != null && userSessionInfo.isValid())
		{						
			try {
				String action = request.getParameter(Parameter.s_ACTION);
				//System.out.println("action: " + request.getParameter(Parameter.s_ACTION));;
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
				    //System.out.print("Resource: " + resource);

			    	ResourceHandler resourceHandler = new ResourceHandler();
				    
				    if(action.equals("file"))
				    {
				    	resource.setPathFile(Address.s_FOLDER_RESOURCES + fileName);
					    /*int questionId = Integer.parseInt(request.getParameter(Parameter.s_QID));
					    int optionId = Integer.parseInt(request.getParameter(Parameter.s_OID));
					    if(questionId>=0){
					    	resourceHandler.insertResource(resource, questionId,-1);
					    }else if(optionId>=0){
					    	resourceHandler.insertResource(resource, -1, optionId);
					    }*/
				    }
				    else if(action.equals("fileUpdate"))
				    {
				    	int resourceId = Integer.parseInt(request.getParameter(Parameter.s_RID));
				    	resourceHandler.updateResourceUrlPath(resourceId, resource.getPathFile());
				    }
				    
				    request.setAttribute(Attribute.s_RESOURCE, resource);
				    request.setAttribute(Attribute.s_ACTION, action);
				    /*Enumeration params2 = request.getParameterNames(); 
					while(params2.hasMoreElements()){
					 String paramName = (String)params2.nextElement();
					 System.out.println("Parameter Name 2 - "+paramName+", Value - "+request.getParameter(paramName));
					}*/
				    CommonCode.redirect(request, response, Address.s_IMPORT_IMAGE_OPTION);
				}
				else if(action.equals("options"))
				{
					Resource resource = new Resource();
					resource.setPathFile(request.getParameter(Parameter.s_RESOURCE_URL));
					resource.setType("image");

					//System.out.println(request.getParameter(Parameter.s_RESOURCE_URL));
					//System.out.println(resource.getPathFile());
					
			    	ResourceHandler resourceHandler = new ResourceHandler();
				    //System.out.println("Request: "+request.getParameter(Parameter.s_QID));
				    int questionId = Integer.parseInt(request.getParameter(Parameter.s_QID));
					int optionId = Integer.parseInt(request.getParameter(Parameter.s_OID));
					oId=optionId;
					
					if((optionId==0) && (questionId<0)){
						Option option = new Option("", 
								Integer.parseInt(request.getParameter(Parameter.s_INDEX)), 
								Integer.parseInt(request.getParameter(Parameter.s_QID))*(-1), 
								Integer.parseInt(request.getParameter(Parameter.s_OGID)),
								Integer.parseInt(request.getParameter(Parameter.s_OID)),
								request.getParameter(Parameter.s_OTYPE),
								request.getParameter(Parameter.s_LANG));
						
						//System.out.println("Opción: " + option.toString());
						OptionHandler optionHandler = new OptionHandler();
						String resp = optionHandler.saveOptionWithoutContent(option);
						
						//System.out.println(resp);
						JSONObject json = null;
				    	
				    	try {
							json = new JSONObject(resp);
							
							if(json.has(Parameter.s_OID)){
							   request.setAttribute(Attribute.s_OID, json.getInt(Parameter.s_OID));
							   oId=Integer.parseInt((String)json.get(Parameter.s_OID));
							   //System.out.println("Parameter Name oid - "+json.get(Parameter.s_OID));
							} else{
								request.setAttribute(Attribute.s_OID, request.getParameter(Parameter.s_OID));
							}
							if(json.has(Parameter.s_OGID)){
							   request.setAttribute(Attribute.s_OGID, json.getInt(Parameter.s_OGID));
							   //System.out.println("Parameter Name ogid - "+json.get(Parameter.s_OGID));
							} else{
								request.setAttribute(Attribute.s_OGID, request.getParameter(Parameter.s_OGID));
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if(questionId>=0){
					    resourceHandler.insertResource(resource, questionId,-1);
					    //System.out.println("Resource inserted for question");
					}else if(optionId>=0){
					    resourceHandler.insertResource(resource, -1, oId);
					    request.setAttribute(Attribute.s_OPTION, true);
					    //System.out.println("Resource inserted for option");
					}
					
					//System.out.println(resource.toString());
					
				    request.setAttribute(Attribute.s_RESOURCE, resource);
				    request.setAttribute(Attribute.s_ACTION, action);
				    
					
					resource = this._insertFileContent(request, resource.getResourceId(), request.getParameter(Parameter.s_RESOURCE_TYPE));
					
					request.setAttribute(Attribute.s_RESOURCE, resource);
					/*Enumeration params2 = request.getAttributeNames(); 
					while(params2.hasMoreElements()){
					 String paramName = (String)params2.nextElement();
					 System.out.println("Parameter Name 2 - "+paramName+", Value - "+request.getAttribute(paramName));
					}*/
				    CommonCode.redirect(request, response, Address.s_MULTIMEDIA_ITEM);
				}
				else if(action.equals("video"))
				{
					Resource resource = new Resource();
					
					String idVideo = this._getIdVideoFromURL(request.getParameter(Parameter.s_RESOURCE_URL));
					resource.setPathFile(idVideo);					
					resource.setType(request.getParameter(Parameter.s_RESOURCE_TYPE));
					
					
					
					ResourceHandler resourceHandler = new ResourceHandler();
				    //System.out.println("Request: "+request.getParameter(Parameter.s_QID));
				    int questionId = Integer.parseInt(request.getParameter(Parameter.s_QID));
					int optionId = Integer.parseInt(request.getParameter(Parameter.s_OID));
					oId=optionId;
					
					if((optionId==0) && (questionId<0)){
						System.out.println("Option group:"+request.getParameter(Parameter.s_OGID));
						Option option = new Option("", 
								Integer.parseInt(request.getParameter(Parameter.s_INDEX)), 
								Integer.parseInt(request.getParameter(Parameter.s_QID))*(-1), 
								Integer.parseInt(request.getParameter(Parameter.s_OGID)),
								Integer.parseInt(request.getParameter(Parameter.s_OID)),
								request.getParameter(Parameter.s_OTYPE),
								request.getParameter(Parameter.s_LANG));
						
						//System.out.println("Opción: " + option.toString());
						OptionHandler optionHandler = new OptionHandler();
						String resp = optionHandler.saveOptionWithoutContent(option);
						
						//System.out.println(resp);
						JSONObject json = null;
				    	
				    	try {
							json = new JSONObject(resp);
							
							if(json.has(Parameter.s_OID)){
							   request.setAttribute(Attribute.s_OID, json.getInt(Parameter.s_OID));
							   oId=Integer.parseInt((String)json.get(Parameter.s_OID));
							   //System.out.println("Parameter Name oid - "+json.get(Parameter.s_OID));
							} else{
								request.setAttribute(Attribute.s_OID, request.getParameter(Parameter.s_OID));
							}
							if(json.has(Parameter.s_OGID)){
							   request.setAttribute(Attribute.s_OGID, json.getInt(Parameter.s_OGID));
							   //System.out.println("Parameter Name ogid - "+json.get(Parameter.s_OGID));
							} else{
								request.setAttribute(Attribute.s_OGID, request.getParameter(Parameter.s_OGID));
							}
							
							
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if(questionId>=0){
						resource = resourceHandler.insertResource(resource, questionId,-1);
					   // System.out.println("Resource inserted for question");
					}else if(optionId>=0){
						resource = resourceHandler.insertResource(resource, -1, oId);
						request.setAttribute(Attribute.s_OPTION, true);
					    // System.out.println("Resource inserted for option");
					}					
					
					resource = this._insertFileContent(request, resource.getResourceId(), resource.getType());
					
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
			File output = null;
			Path fpath = Paths.get(rootPath + "\\resources", fileNameFinal);
			do
			{
				if(index > 0)
				{
					String[] cads = fileName.split(Pattern.quote("."));
					fileNameFinal = cads[0] + index + "." + cads[1];
				    fpath = Paths.get(rootPath + "\\resources", fileNameFinal);
				}
				
			    output = new File(fpath.toString());
				System.out.println("File Name: " + fileNameFinal);
				index++;
			} while (output.exists());
			
			InputStream fileContent = filePart.getInputStream();
			
		  //CODIGO PRUEBA
			BufferedImage src = ImageIO.read(fileContent);
		    
		    //Scale
		    int width = src.getWidth();
		    int height = src.getHeight();
		    double scale = 0;
		    double indexFormat = (double)width / height;
		    if (indexFormat > 1.3325)
		    {
		    	scale = (double)533 / width;
		    }
		    else
		    {
		    	scale = (double)400 /height;
		    }
		    
		    BufferedImage dest = new BufferedImage(((Double)(width * scale)).intValue(), ((Double)(height * scale)).intValue(), BufferedImage.TYPE_INT_RGB);	
		    System.out.println("With scaled int: " + ((Double)(400 * scale)).intValue() + " - double: " + (400 * scale));
		    //Fin scale
		    
		    Graphics2D g = dest.createGraphics();
		    //AffineTransform at = AffineTransform.getScaleInstance((double)70 / src.getWidth(), (double)50 / src.getHeight());
		    AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
		    g.drawRenderedImage(src, at);
		  //FIN CODIGO PRUEBA
		    
		    ImageIO.write(dest, "JPG", output);
		    
		    //Files.copy(fileContent, fpath);		    
		    System.out.println("Image path: " + fpath.toString());
		    
		} catch (FileAlreadyExistsException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			index++;
			fileNameFinal = this._importFile(filePart, fileName, rootPath, index);
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

	private Resource _insertFileContent(HttpServletRequest request, int resourceId, String type)
	{
		
		this.language = request.getParameter(Parameter.s_MAIN_VERSION);
		
		HashMap<String, Content> contents = new HashMap<String, Content>();
		contents.put(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE,
				new Content(0, this.language, DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, request.getParameter(Parameter.s_RESOURCE_TITLE)));
		
		if(type.equals("image"))
		{
			contents.put(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT,
					new Content(0, this.language, DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT, request.getParameter(Parameter.s_RESOURCE_ALTERNTIVE_TEXT)));
		}
		else if(type.equals("video"))
		{
			contents.put(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION,
					new Content(0, this.language, DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION, request.getParameter(Parameter.s_RESOURCE_DESCRIPTION_TEXT)));
		}
		
		ResourceHandler resourceHandler = new ResourceHandler();
		return resourceHandler.insertContent(resourceId, contents);
	}
	
	private String _getIdVideoFromURL(String url)
	{
		String idVideo = "";
		boolean indexOfSol = false;
		
		String[] cads = url.split("v=");
		if(cads.length > 1)
		{
			indexOfSol = true;
		}
		else
		{
			cads = url.split("youtu.be/");
			if(cads.length > 1)
			{
				indexOfSol = true;
			}
			else
			{
				idVideo = url;
			}
		}
		
		if(indexOfSol)
		{
			int endId = cads[1].indexOf("&");
			if(endId > -1)
			{
				idVideo = cads[1].substring(endId);
			}
			else
			{
				idVideo = cads[1];
			}	
		}
		
		return idVideo;
	}
	
}
