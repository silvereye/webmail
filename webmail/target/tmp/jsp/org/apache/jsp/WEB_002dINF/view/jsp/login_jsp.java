package org.apache.jsp.WEB_002dINF.view.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

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
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("  <head>\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("\t<title>Login Page</title>\r\n");
      out.write("\t\r\n");
      out.write("\t<!-- Bootstrap -->\r\n");
      out.write("\t<link href=\"css/bootstrap.css\" rel=\"stylesheet\" media=\"screen\">\r\n");
      out.write("\t<link href=\"css/custom.css\" rel=\"stylesheet\" media=\"screen\">\r\n");
      out.write("\r\n");
      out.write("\t<!-- Animo css-->\r\n");
      out.write("\t<link href=\"css/animate+animo.css\" rel=\"stylesheet\" media=\"screen\">\r\n");
      out.write("\t\r\n");
      out.write("\t<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->\r\n");
      out.write("\t<!--[if lt IE 9]>\r\n");
      out.write("\t  <script src=\"assets/js/html5shiv.js\"></script>\r\n");
      out.write("\t  <script src=\"assets/js/respond.min.js\"></script>\r\n");
      out.write("\t<![endif]-->\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t<!-- Load jQuery -->\r\n");
      out.write("\t<script src=\"js/jquery.v2.0.3.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("  </head>\r\n");
      out.write("  <body>\r\n");
      out.write("\t<!-- 100% Width & Height container  -->\r\n");
      out.write("\t<div class=\"login-fullwidith\">\r\n");
      out.write("\t\t<form name=\"f\" action=\"j_spring_security_check\" method=\"POST\">\r\n");
      out.write("\t\t");
      out.write("\r\n");
      out.write("\t\t<!-- Login Wrap  -->\r\n");
      out.write("\t\t<div class=\"login-wrap\">\r\n");
      out.write("            <div class=\"logo_new\">\r\n");
      out.write("                <img src=\"images/logo_avi.png\" />\r\n");
      out.write("            </div>\r\n");
      out.write("\t\t\t<div class=\"login-c1\">\r\n");
      out.write("\t\t\t\t<div class=\"cpadding50\">\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div class=\"username\"></div>\r\n");
      out.write("\t\t\t\t\t<input name=\"j_username\" id=\"j_username\" type=\"text\" class=\"form-control logpadding username\" placeholder=\"Username\"  >\r\n");
      out.write("\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t<br/>\r\n");
      out.write("\t\t\t\t\t<div class=\"password\"></div>\r\n");
      out.write("\t\t\t\t\t<input name=\"j_password\" id=\"j_password\" type=\"text\" class=\"form-control logpadding password\" placeholder=\"Password\" >\r\n");
      out.write("\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"login-c2\">\r\n");
      out.write("\t\t\t\t<div class=\"logmargfix\">\r\n");
      out.write("\t\t\t\t\t<div class=\"chpadding50\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"alignbottom\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"submit\" value=\"Sign In\" class=\"sign_top\" name=\"submit\"/>\r\n");
      out.write("\t\t\t\t\t\t\t\t<!-- <button class=\"btn-search4\"  type=\"submit\" onclick=\"errorMessage()\">Submit</button>\t -->\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"alignbottom2\">\r\n");
      out.write("\t\t\t\t\t\t\t  <div class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t  <input type=\"checkbox\">Remember\r\n");
      out.write("\t\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t\t  </div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"login-c3\">\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div class=\"right\"><a href=\"forgot.html\" class=\"whitelink\">Lost password?</a></div>\r\n");
      out.write("\t\t\t</div>\t\t\t\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<!-- End of Login Wrap  -->\r\n");
      out.write("\t</form>\r\n");
      out.write("\t");
      out.write("\r\n");
      out.write("\t</div>\t\r\n");
      out.write("\t<!-- End of Container  -->\r\n");
      out.write("\r\n");
      out.write("\t<!-- Javascript  -->\r\n");
      out.write("\t<!--<script src=\"js/initialize-loginpage.js\"></script>-->\r\n");
      out.write("\t<script src=\"js/jquery.easing.js\"></script>\r\n");
      out.write("\t<!-- Load Animo -->\r\n");
      out.write("\t<script src=\"js/animo.js\"></script>\r\n");
      out.write("\t<script>\r\n");
      out.write("\tfunction errorMessage(){\r\n");
      out.write("\t\t$('.login-wrap').animo( { animation: 'tada' } );\r\n");
      out.write("\t}\r\n");
      out.write("\t</script>\r\n");
      out.write("\t\r\n");
      out.write("  </body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
