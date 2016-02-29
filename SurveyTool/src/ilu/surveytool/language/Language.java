package ilu.surveytool.language;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import ilu.surveytool.constants.Attribute;

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
	
	public static String getLanguageRequest(HttpServletRequest request)
	{
		String language = "en";
		Object langobj = request.getSession().getAttribute(Attribute.s_CURRENT_LANGUAGE);
		if(langobj == null)
		{
			String langReq = request.getHeader("Accept-Language").split(";")[0].split(",")[0];
			System.out.println("Language: " + langReq);
			
			if(langReq.contains("es")){ language = "es";}
			else if(langReq.contains("en")){ language = "en";}	
			/*else if(langReq.contains("ca")){ language = "ca";}	
			else if(langReq.contains("fr")){ language = "fr";}	
			else if(langReq.contains("it")){ language = "it";}	
			else if(langReq.contains("el")){ language = "el";}	
			else if(langReq.contains("de")){ language = "de";}
			else if(langReq.contains("eu")){ language = "eu";}	
			else if(langReq.contains("gl")){ language = "gl";}	
			else if(langReq.contains("ar")){ language = "ar";}	
			else if(langReq.contains("zh")){ language = "zh";}	
			else if(langReq.contains("nl")){ language = "nl";}	
			else if(langReq.contains("pt")){ language = "pt";}
			else if(langReq.contains("sv")){ language = "sv";}	
			else if(langReq.contains("ro")){ language = "ro";}	
			else if(langReq.contains("pl")){ language = "pl";}*/
			
			request.getSession().setAttribute(Attribute.s_CURRENT_LANGUAGE, language);
		}
		else
		{
			language = (String)langobj;
		}
		
		return language;
	}
}
