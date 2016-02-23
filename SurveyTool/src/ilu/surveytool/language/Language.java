package ilu.surveytool.language;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

public class Language {

	String rootPath = "";
	InputStream input = null;
	Properties prop = null;

	public Language(String rootPath) {
		this.rootPath = rootPath;
	}
	
	public Properties loadLanguage(String lang) {

		this.prop = new Properties();

		try {
			
			this.input = new FileInputStream(this.rootPath + "/_locales/lang_" + lang + ".properties");

			// load a properties file
			prop.load(this.input);

		} catch (IOException ex) {
			ex.printStackTrace();
		} 
		
		return prop;
	}
	
	public void close()
	{
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getContent(String content)
	{
		return prop.getProperty(content);
	}
}
