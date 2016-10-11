/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.27
 * Generated at: 2016-10-06 11:40:56 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsp.surveyManager.components;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import ilu.surveytool.language.Language;
import ilu.surveytool.databasemanager.DataObject.Content;
import java.util.HashMap;
import ilu.surveytool.databasemanager.constants.DBConstants;
import java.util.List;
import ilu.surveytool.databasemanager.DataObject.Resource;
import ilu.surveytool.constants.Attribute;

public final class cMultimediaItem_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("ilu.surveytool.language.Language");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.constants.DBConstants");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.DataObject.Content");
    _jspx_imports_classes.add("java.util.HashMap");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.DataObject.Resource");
    _jspx_imports_classes.add("ilu.surveytool.constants.Attribute");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    \t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t");

							  					Language lang = new Language(getServletContext().getRealPath("/")); 
							  					lang.loadLanguage(Language.getLanguageRequest(request));
							  					
							  					Resource resource = (Resource) request.getAttribute(Attribute.s_RESOURCE);
							  					HashMap<String, Content> contents = resource.getContents();
							  					String title = "";

							  					if(!contents.isEmpty() && contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE) != null)
							  					{
							  						title = contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
							  					}
							  					String path = resource.getPathFile();
							  					
							  					if(resource.getType().equals("image"))
							  					{
								  					String altText = "";
								  					if(!contents.isEmpty() && contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT) != null)
								  					{
								  						altText = contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_ALT_TEXT).getText();
								  					}
							  					
							  					
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t<li class=\"multimedia-item\" rid=\"");
      out.print( resource.getResourceId() );
      out.write("\">\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t\t<i class=\"fa fa-file-image-o\" aria-hidden=\"true\"></i>\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t\t<a class=\"active\" active=\"false\" id=\"editFile\" data-image='{\"rId\":\"");
      out.print( resource.getResourceId());
      out.write("\",\"tittle\":\"");
      out.print(title );
      out.write("\",\"altText\":\"");
      out.print(altText );
      out.write("\",\"path\":\"");
      out.print(path );
      out.write("\", \"rType\":\"");
      out.print( resource.getType() );
      out.write("\"}' href=\"#\">");
      out.print( title + " - " + path );
      out.write("</a>\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t\t");
 if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t\t<button id=\"removeMultimediaFile\" class=\"btn btn-transparent red\" aria-label=\"");
      out.print( lang.getContent("button.remove_file") );
      out.write(':');
      out.write(' ');
      out.print( title );
      out.write("\"><i class=\"fa fa-trash\"></i></button>\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");

							  					}
							  					else if(resource.getType().equals("video"))
							  					{
							  						String descText = "";
								  					if(!contents.isEmpty() && contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION) != null)
								  					{
								  						descText = contents.get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText();
								  					}	
								  					
							  					
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t<li class=\"multimedia-item\" rid=\"");
      out.print( resource.getResourceId() );
      out.write("\">\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t\t<i class=\"fa fa-file-video-o\" aria-hidden=\"true\"></i>\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t\t<a class=\"active\" active=\"false\" id=\"editFile\" data-image='{\"rId\":\"");
      out.print( resource.getResourceId());
      out.write("\",\"tittle\":\"");
      out.print(title );
      out.write("\",\"descText\":\"");
      out.print(descText );
      out.write("\",\"path\":\"");
      out.print(path );
      out.write("\", \"rType\":\"");
      out.print( resource.getType() );
      out.write("\"}' href=\"#\">");
      out.print( title + " - " + path );
      out.write("</a>\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t\t");
 if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t\t<button id=\"removeMultimediaFile\" class=\"btn btn-transparent fright red\" aria-label=\"");
      out.print( lang.getContent("button.remove_video") );
      out.write(':');
      out.write(' ');
      out.print( title );
      out.write("\"><i class=\"fa fa-trash\"></i></button>\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");

							  					}
							  					
												lang.close();
												
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t  \t\t\t\t\t");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
