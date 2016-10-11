/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.27
 * Generated at: 2016-10-10 17:33:06 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsp.surveyEngine.bodyPages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.codehaus.jettison.json.JSONObject;
import ilu.surveytool.databasemanager.DataObject.Page;
import ilu.surveytool.databasemanager.DataObject.Section;
import ilu.surveytool.language.Language;
import ilu.surveytool.constants.Address;
import java.util.List;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.constants.Parameter;
import ilu.surveytool.databasemanager.DataObject.Survey;

public final class bSurveyPageAJS_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("ilu.surveytool.language.Language");
    _jspx_imports_classes.add("ilu.surveytool.constants.Parameter");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.DataObject.Survey");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.constants.DBConstants");
    _jspx_imports_classes.add("org.codehaus.jettison.json.JSONObject");
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\t\t\t\t\t\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t");

				//Survey survey = (Survey) request.getAttribute(Attribute.s_SURVEY_INFO);
				
				Language lang = (Language) request.getSession().getAttribute(Attribute.s_SURVEY_LANGUAGE);
				JSONObject sInfo = (JSONObject) request.getAttribute(Attribute.s_SURVEY_INFO);
				
      out.write("\t\r\n");
      out.write("\t\t\t\t<script>\r\n");
      out.write("\t\t\t\t var playText = \"");
      out.print( lang.getContent("file.player.yt.button.play") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var muteText = \"");
      out.print( lang.getContent("file.player.yt.button.mute") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var volDownText = \"");
      out.print( lang.getContent("file.player.yt.button.volDown") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var volUpText = \"");
      out.print( lang.getContent("file.player.yt.button.volUp") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var rewText = \"");
      out.print( lang.getContent("file.player.yt.button.rew") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var fwdText = \"");
      out.print( lang.getContent("file.player.yt.button.fwd") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var pauseText = \"");
      out.print( lang.getContent("file.player.yt.button.pause") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var positionText = \"");
      out.print( lang.getContent("file.player.yt.dt.position") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var secondsText = \"");
      out.print( lang.getContent("file.player.yt.dd.seconds") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var durationText = \"");
      out.print( lang.getContent("file.player.yt.dt.duration") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var unknownText = \"");
      out.print( lang.getContent("file.player.yt.dd.unknown") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var skipVideoText = \"");
      out.print( lang.getContent("file.player.yt.a.skip_video") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var watchYtText = \"");
      out.print( lang.getContent("file.player.yt.a.watch_yt") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var openNewWindowText = \"");
      out.print( lang.getContent("file.player.yt.a.open_new_window") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var finishedText = \"");
      out.print( lang.getContent("file.player.yt.alert.video_finished") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var playingText = \"");
      out.print( lang.getContent("file.player.yt.alert.playing") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var pausedText = \"");
      out.print( lang.getContent("file.player.yt.alert.paused") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var loadingText = \"");
      out.print( lang.getContent("file.player.yt.alert.loading") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var soundActivatedText = \"");
      out.print( lang.getContent("file.player.yt.alert.sound_activated") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var soundDeactivatedText = \"");
      out.print( lang.getContent("file.player.yt.alert.sound_deactivated") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var volumeMaxText = \"");
      out.print( lang.getContent("file.player.yt.alert.volume_max") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var volumeAdjustText = \"");
      out.print( lang.getContent("file.player.yt.alert.volume_adjust") );
      out.write("\";\r\n");
      out.write("\t\t\t\t var volumeMinText = \"");
      out.print( lang.getContent("file.player.yt.alert.volume_min") );
      out.write("\";\r\n");
      out.write("\t\t\t\t \r\n");
      out.write("\t\t\t\t sInfo = ");
      out.print( sInfo.toString() );
      out.write(";\r\n");
      out.write("\t\t\t\t</script>\r\n");
      out.write("\t\t\t\t<div class=\"container-fluid\" ng-app=\"survey\" ng-controller=\"surveyController\">\r\n");
      out.write("\t  \t\t\t\t<div class=\"title-content col-xs-10 col-xs-push-1 col-md-8 col-md-push-2\">\t  \t\t\t\t\t\r\n");
      out.write("\t  \t\t\t\t\t<h1>{{getJsonArrayElement(currentSurvey.info.contents, \"contentType\", \"title\").text}}</h1>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"progress\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"progress-bar\" role=\"progressbar\" aria-valuenow=\"50\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width:50%\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"sr-only\" ng-show=\"showButtonLastPage()\">");
      out.print( lang.getContent("survey.process.pageInfo1") );
      out.write(" {{currentSurvey.info.section.page.numPage}} ");
      out.print( lang.getContent("survey.process.pageInfo2") );
      out.write(" {{currentSurvey.info.numPages}}</span>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t  \t\t\t\t\t<h2>Section X</h2>\r\n");
      out.write("\t  \t\t\t\t</div>\t  \t\t\t\t\r\n");
      out.write("\t  \t\t\t\t<div class=\"content\">\r\n");
      out.write("\t  \t\t\t\t\t<form name=\"survey\" role=\"form\" ng-show=\"currentSurvey.info.section.page.questions\" class=\"\">\r\n");
      out.write("\t  \t\t\t\t\t\t\r\n");
      out.write("\t  \t\t\t\t\t\t<div class=\"survey-form col-xs-10 col-xs-push-1 col-md-8 col-md-push-2\">\t  \t\t\t\t\t\t\t\r\n");
      out.write("\t  \t\t\t\t\t\t\t<p>{{getJsonArrayElement(currentSurvey.info.contents, \"contentType\", \"description\").text}}</p>\r\n");
      out.write("\t  \t\t\t\t\t\t\t\r\n");
      out.write("\t  \t\t\t\t\t\t\t\r\n");
      out.write("\t  \t\t\t\t\t\t\t<ul>\r\n");
      out.write("\t  \t\t\t\t\t\t\t\t<li ng-repeat=\"question in currentSurvey.info.section.page.questions\" >\r\n");
      out.write("\t  \t\t\t\t\t\t\t\t\t<ng-include src=\"question.questionJspPath\"></ng-include>\r\n");
      out.write("\t  \t\t\t\t\t\t\t\t</li>\r\n");
      out.write("\t  \t\t\t\t\t\t\t</ul>\r\n");
      out.write("\t  \t\t\t\t\t\t\t\r\n");
      out.write("\t  \t\t\t\t\t\t</div>\r\n");
      out.write("\t  \t\t\t\t\t\t<div class=\"btn-submit-page clearfix col-xs-10 col-xs-push-1 col-md-8 col-md-push-2\">\r\n");
      out.write("\t  \t\t\t\t\t\t\t<div class=\"col-xs-6\">\r\n");
      out.write("\t  \t\t\t\t\t\t\t\t<button class=\"btn btn-default btn-submit-page-back\" ng-show=\"currentSurvey.info.section.page.numPage > 1 && showButtonLastPage()\" ng-click='nextPage(\"back\")'>");
      out.print( lang.getContent("button.back") );
      out.write("</button>\r\n");
      out.write("\t  \t\t\t\t\t\t\t</div>\r\n");
      out.write("\t  \t\t\t\t\t\t\t<div class=\"col-xs-6\">\r\n");
      out.write("\t  \t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-primary btn-submit-page-continue\" ng-show=\"!showStartButton() && showButtonLastPage()\" ng-click='nextPage(\"next\")'>");
      out.print( lang.getContent("button.continue") );
      out.write("</button>\r\n");
      out.write("\t  \t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-primary btn-submit-page-continue\" ng-show=\"showStartButton()\" ng-click='nextPage(\"next\")'>");
      out.print( lang.getContent("button.start") );
      out.write("</button>\r\n");
      out.write("\t  \t\t\t\t\t\t\t\t\r\n");
      out.write("\t  \t\t\t\t\t\t\t</div>\r\n");
      out.write("\t  \t\t\t\t\t\t</div>\r\n");
      out.write("\t  \t\t\t\t\t</form>\r\n");
      out.write("\t  \t\t\t\t\t\r\n");
      out.write("\t  \t\t\t\t\t<div ng-show=\"currentSurvey.info.isFinishPage\">\r\n");
      out.write("\t  \t\t\t\t\t\t<ng-include src=\"'jsp/surveyEngine/bodyPages/bSurveyFinishPage.jsp'\"></ng-include>\r\n");
      out.write("\t  \t\t\t\t\t</div>\r\n");
      out.write("\t  \t\t\t\t\t\r\n");
      out.write("\t  \t\t\t\t</div>\r\n");
      out.write("\t  \t\t\t</div>\r\n");
      out.write("\t  \t\t\t\r\n");

lang.close();

      out.write("\r\n");
      out.write("\t  \t\t\t");
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
