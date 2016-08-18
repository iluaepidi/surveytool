package ilu.surveytool.commoncode;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ilu.surveytool.exception.STException;

/**
 * 
 * @author JAgutierrez
 *
 *	This class contain common methods to all the web application.
 */

public class CommonCode {
	
	/**
	 * This method redirects to other jsp from a servlet.
	 * @param request
	 * @param response
	 * @param dir
	 */
	public static void redirect(HttpServletRequest request, HttpServletResponse response, String dir){
		try {
			//IE compatibility
			response.addHeader("X-UA-Compatible", "IE=edge");
			request.getSession().getServletContext().getRequestDispatcher("/" + dir).forward(request, response);
		} catch (ServletException e) {
			try {
				throw new STException("Fallo en la redirección");
			} catch (STException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
