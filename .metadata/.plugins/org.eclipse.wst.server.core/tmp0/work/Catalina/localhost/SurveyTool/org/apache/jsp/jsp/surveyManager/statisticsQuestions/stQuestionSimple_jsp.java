/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.27
 * Generated at: 2016-10-08 13:07:08 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsp.surveyManager.statisticsQuestions;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import ilu.surveytool.databasemanager.DataObject.Resource;
import java.util.List;
import java.util.ArrayList;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.constants.Attribute;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Option;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;
import ilu.surveytool.databasemanager.DataObject.OptionsByGroup;
import ilu.surveytool.language.Language;
import ilu.surveymanager.statistics.Statistics;
import ilu.surveymanager.statistics.StatisticsQuestion;
import ilu.surveymanager.statistics.Statistics;
import ilu.surveymanager.statistics.StatisticsQuestion;
import ilu.surveymanager.handler.SurveysHandler;
import ilu.surveytool.databasemanager.DataObject.Survey;

public final class stQuestionSimple_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("ilu.surveymanager.statistics.StatisticsQuestion");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.DataObject.Option");
    _jspx_imports_classes.add("ilu.surveymanager.statistics.Statistics");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.DataObject.Question");
    _jspx_imports_classes.add("java.util.ArrayList");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.DataObject.OptionsByGroup");
    _jspx_imports_classes.add("ilu.surveymanager.handler.SurveysHandler");
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("ilu.surveytool.language.Language");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.DataObject.Survey");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.constants.DBConstants");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.DataObject.OptionsGroup");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.DataObject.Resource");
    _jspx_imports_classes.add("ilu.surveytool.databasemanager.DataObject.Content");
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
      out.write(" \r\n");
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    \r\n");

String[] colors = {"cian", "yellow", "red", "gray", "brown", "green", "blue", "purple", "black"};
String[] grafColors = {"#576C99", "#FFB506", "#F7464A", "#c5c5c5", "#B28B6E", "#ADC6A6", "#57A4DA", "#E3DBEC", "#151C25"};
String[] highlight = {"#4A5C82", "#D99A05", "#D23C3F", "#A7A7A7", "#97765E", "#93A88D", "#4A8BB9", "#C1BAC9", "#12181F"};

Question question = (Question) request.getAttribute(Attribute.s_QUESTION);
String title = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
if(title==null)
	title = "";
String description = "";
if(question.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION))
{
	description = question.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_DESCRIPTION).getText(); 
} else
	description="";

int index = Integer.parseInt(request.getParameter("index"));

Language lang = new Language(getServletContext().getRealPath("/")); 
lang.loadLanguage(Language.getLanguageRequest(request));
//System.out.println(lang);

StatisticsQuestion sQ = (StatisticsQuestion) request.getAttribute(Attribute.s_SURVEY_STATISTIC);
List<OptionsByGroup> obg = sQ.getOptionsByGroup();
List<Option> o = sQ.getOptions();

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<h3>");
      out.print( title);
      out.write("</h2>\r\n");
      out.write("<h4>");
      out.print( description);
      out.write("</h3>\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"row single-questions-row\">\r\n");
      out.write("\t        <div class=\"small-box bg-aqua\">\r\n");
      out.write("\t            <div class=\"inner\">\r\n");
      out.write("\t              <h3 aria-hidden=\"true\">");
      out.print( sQ.getNumResponses());
      out.write("</h3>\r\n");
      out.write("\t              <p>");
      out.print( lang.getContent("statistics.boxes.numAnswers"));
      out.write("</p><span class=\"visuallyhidden\">: ");
      out.print( sQ.getNumResponses());
      out.write("</span>\r\n");
      out.write("\t            </div>\r\n");
      out.write("\t          </div>\r\n");
      out.write("\t      </div>\r\n");
      out.write("\t      <div class=\"row single-questions-row\">\r\n");
      out.write("\t\t      <div class=\"canvasPie text\">\r\n");
      out.write("\t\t      <p class=\"graph-title no-block\"> ");
      out.print( lang.getContent("statistics.boxes.numAnswersByOption"));
      out.write("</p>\r\n");
      out.write("\t            \t<span class=\"visuallyhidden\">\r\n");
      out.write("\t\t\t\t\t");
 
					for(int i = 0; i<o.size();i++){
						int idoption = ((Option)(o.get(i))).getId();
						for(int j=0;j<obg.size();j++){
			    			if ((((OptionsByGroup)(obg.get(j))).getOptionId()) == idoption){
      out.write("\r\n");
      out.write("\t\t\t    \t\t\t\t");
      out.print( ((Content)(((Option)(o.get(i))).getContents().get("text"))).getText());
      out.write(',');
      out.write(' ');
      out.print( (int)Math.round((((((OptionsByGroup)(obg.get(j))).getNumResponses()*1.0)/(sQ.getNumResponses()*1.0))*100.0)));
      out.write("\r\n");
      out.write("\t\t\t    \t\t\t\t");

			    				j=obg.size();
			    			}
			    		}
					}
					
      out.write("\r\n");
      out.write("\t\t\t\t\t</span>\r\n");
      out.write("\t\t      \t<div class=\"no-block\">\r\n");
      out.write("\t\t      \t<div class=\"tab-content in-block\">\r\n");
      out.write("\t\t\t  \t\t<div id=\"canvas-holder\" class=\"chart tab-pane active\">\r\n");
      out.write("\t\t\t\t\t\t<canvas id=\"chart-area-");
      out.print( question.getQuestionId() );
      out.write("\" width=\"250\" height=\"250\" style=\"width: 250px; height: 250px;\"/>\t\t                \r\n");
      out.write("\t\t\t\t  \t</div>\r\n");
      out.write("\t\t\t\t  \t<script>\r\n");
      out.write("\t\t\t\t  \t");

					List<String> labels = new ArrayList<String>();
					List<Integer> values = new ArrayList<Integer>();
					
					//System.out.println("Size of options:"+o.size());
					for(int i = 0; i<o.size();i++){
						int idoption = ((Option)(o.get(i))).getId();
						labels.add(((Content)(((Option)(o.get(i))).getContents().get("text"))).getText());       		    		
			    		for(int j=0;j<obg.size();j++){
			    			if ((((OptionsByGroup)(obg.get(j))).getOptionId()) == idoption){
			    				values.add((int)Math.round((((((OptionsByGroup)(obg.get(j))).getNumResponses()*1.0)/(sQ.getNumResponses()*1.0))*100.0)));
			    				j=obg.size();
			    			}
			    		}
					}
					
					String graf = "";
					for(int i=0;i<labels.size();i++)
					{
						graf += "{value: " + values.get(i) + ", " +
								"color: \"" + grafColors[i%9] + "\", " +
								"highlight: \"" +highlight[i%9] + "\", " +
								"label: \"" + labels.get(i) + "\"}";
						if(i < (labels.size()-1))graf += ",";
					}
					
					
      out.write("\r\n");
      out.write("\t        var pieData");
      out.print( question.getQuestionId() );
      out.write(" = [\r\n");
      out.write("\t\t\t\t\t     ");
      out.print( graf );
      out.write("   \t\r\n");
      out.write("\t\t\t\t\t];\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\t\t\t\tvar ctx = document.getElementById(\"chart-area-");
      out.print( question.getQuestionId() );
      out.write("\").getContext(\"2d\");\r\n");
      out.write("\t\t\t\t\t\twindow.myPie");
      out.print( question.getQuestionId() );
      out.write(" = new Chart(ctx).Pie(pieData");
      out.print( question.getQuestionId() );
      out.write(",{percentageInnerCutout : 40});\r\n");
      out.write("\t\t\t\t\t</script>\r\n");
      out.write("\t\t\t  \t</div>\r\n");
      out.write("\t\t\t\t<div class=\"legendSurvey in-block\">\r\n");
      out.write("\t\t\t\t  \t<ul>\r\n");
      out.write("\t\t\t\t  \t");
for(int i=0;i<labels.size();i++)
					{
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t  \t\t\t<i class=\"fa fa-square ");
      out.print( colors[i%9] );
      out.write("\"></i> ");
      out.print( labels.get(i) );
      out.write("\r\n");
      out.write("\t\t\t\t\t\t  \t\t</li>\r\n");
      out.write("\t\t\t\t\t\t");

					}
					
					
      out.write("\r\n");
      out.write("\t\t\t\t  \t</ul>\r\n");
      out.write("\t\t\t\t </div>\r\n");
      out.write("\t\t\t\t </div>\r\n");
      out.write("\t\t\t  </div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t        \r\n");
      out.write("\t\t\t");
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
