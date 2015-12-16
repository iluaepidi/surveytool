package ilu.surveytool.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BodyPages {
	
	String rootPath = "";

	public BodyPages(String rootPath) {
		this.rootPath = rootPath;
	}
	
	public String getBudyPagePath(String bodyName) {

		String response = "";
		Properties prop = new Properties();
		InputStream input = null;

		try {
			File folder = new File("");
			System.out.println(folder.getAbsolutePath());
			
			input = new FileInputStream(this.rootPath + "/properties/bodypages.properties");

			// load a properties file
			prop.load(input);
			response = prop.getProperty(bodyName);
			// get the property value and print it out
			System.out.println(response);

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
