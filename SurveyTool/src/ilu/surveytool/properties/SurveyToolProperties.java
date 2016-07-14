package ilu.surveytool.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author JAgutierrez
 *
 *	This class contain the methods to obtain the path of the different property files.
 */

public class SurveyToolProperties {
	
	String rootPath = "";

	public SurveyToolProperties(String rootPath) {
		this.rootPath = rootPath;
	}
	
	/**
	 * Get path to the property body pages file.
	 * @param bodyName
	 * @return
	 */
	public String getBudyPagePath(String bodyName) {

		String response = "";
		Properties prop = new Properties();
		InputStream input = null;

		try {
			File folder = new File("");
			//System.out.println(folder.getAbsolutePath());
			
			input = new FileInputStream(this.rootPath + "/properties/bodypages.properties");

			// load a properties file
			prop.load(input);
			response = prop.getProperty(bodyName);
			// get the property value and print it out
			//System.out.println(response);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	 }
	
	/**
	 * Get path to the property js file.
	 * @param bodyName
	 * @return
	 */
	public String getJsFilePath(String jsFileName) {

		String response = "";
		Properties prop = new Properties();
		InputStream input = null;

		try {
			File folder = new File("");
			//System.out.println(folder.getAbsolutePath());
			
			input = new FileInputStream(this.rootPath + "/properties/jsfiles.properties");

			// load a properties file
			prop.load(input);
			response = prop.getProperty(jsFileName);
			// get the property value and print it out
			//System.out.println(response);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	 }

	/**
	 * Get path to the property js file.
	 * @param bodyName
	 * @return
	 */
	public String getCssFilePath(String cssFileName) {

		String response = "";
		Properties prop = new Properties();
		InputStream input = null;

		try {
			File folder = new File("");
			//System.out.println(folder.getAbsolutePath());
			
			input = new FileInputStream(this.rootPath + "/properties/cssfiles.properties");

			// load a properties file
			prop.load(input);
			response = prop.getProperty(cssFileName);
			// get the property value and print it out
			//System.out.println(response);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	 }
}
