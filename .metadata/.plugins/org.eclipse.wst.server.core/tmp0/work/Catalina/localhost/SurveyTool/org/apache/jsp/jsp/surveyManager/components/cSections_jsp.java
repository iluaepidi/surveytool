/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.27
 * Generated at: 2016-10-06 11:27:50 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsp.surveyManager.components;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import ilu.surveytool.databasemanager.DataObject.Page;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.constants.Address;
import ilu.surveytool.databasemanager.DataObject.Section;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.constants.Attribute;
import java.util.List;
import ilu.surveytool.language.Language;

public final class cSections_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("ilu.surveytool.constants.Address");
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.DataObject.Survey");
    _jspx_imports_classes.add("ilu.surveytool.language.Language");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.constants.DBConstants");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.DataObject.Page");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.DataObject.Question");
    _jspx_imports_classes.add("ilu.surveytool.constants.Attribute");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.DataObject.Section");
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
      out.write(" \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write(" \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));

//int pageId = (int) request.getAttribute(Attribute.s_PAGE_ID);
Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO);

      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<ul class=\"survey-sections\" id=\"survey-sections\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t  \t\t\t\t\t<!-- <div class=\"add-frame\">\r\n");
      out.write("\t\t  \t\t\t\t\t\t\t<a href=\"#\" class=\"btn-add\" title=\"create new section\"><i class=\"fa fa-plus-circle fa-2x\"></i></a>  \t\t\t\t\t\t\t\r\n");
      out.write("\t\t  \t\t\t\t\t\t</div> -->\r\n");

							List<Section> sections = survey.getSections();
							int i = 1;
							for(Section section : sections)
							{					
								String title = lang.getContent("survey.edit.section.list") + " " + i;
								if(section.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE))
								{
									title = section.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
								}

      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t    \t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t<li class=\"panel-section\" id=\"panel-section1\" scid=\"");
      out.print( section.getSectionId() );
      out.write("\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"panel-heading\">\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<button id=\"panel-heading-display\" class=\"section-head btn-transparent panel-heading-display-arrow\" aria-label=\"");
      out.print( lang.getContent("button.hide_section") );
      out.write(':');
      out.write(' ');
      out.print( title );
      out.write("\"><i class=\"fa fa-caret-down fa-2x\"></i></button>\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<h3 class=\"panel-title\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<div class=\"col-sm-12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t <div class=\"form-group\" style=\"margin:0px;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<span  class=\"noEditingTitle\">");
      out.print(lang.getContent("survey.edit.section.listQuestions"));
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<!-- <input type=\"text\" class=\"survey-section-title-unselected\" id=\"survey-section-title\" value=\"");
      out.print( title );
      out.write("\" aria-label=\"");
      out.print( lang.getContent("survey.edit.section.title") );
      out.write("\"/>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<span  id='survey-section-title-feedback' class='glyphicon glyphicon-remove form-control-feedback hidden' aria-hidden='true' style=\"color: #a94442;right: 20px\"></span>\r\n");
      out.write("\t\t\t\t  \t\t\t\t\t\t\t\t<span id='survey-section-title-error' class='error hidden' style='top: 0px'>");
      out.print( lang.getContent("msg.error.section.title") );
      out.write("</span> -->\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</h3>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<div class=\"panel-section-buttons right\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
 if((boolean)request.getAttribute(Attribute.s_ADD_QUESTIONS)){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn-transparent\" id=\"removeSection\" aria-label=\"");
      out.print( lang.getContent("button.remove_section") );
      out.write(':');
      out.write(' ');
      out.print( title );
      out.write("\"><i class=\"fa fa-trash fa-2x\"></i></button>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<!-- <h3 class=\"panel-title\">");
      out.print( lang.getContent("survey.edit.section.title") );
      out.write("</h3> -->\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
 request.setAttribute(Attribute.s_SECTION, section); 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "cPages.jsp", out, false);
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
								
								i++;
							}

      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t</ul>\t\t\t\t\t\t\r\n");
							
lang.close();

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
