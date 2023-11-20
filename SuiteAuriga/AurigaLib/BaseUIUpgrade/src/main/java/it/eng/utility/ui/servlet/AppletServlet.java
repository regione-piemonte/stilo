/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/AppletServlet")
public class AppletServlet {

	@RequestMapping(value="/applet.jsp", method=RequestMethod.GET)
	@ResponseBody	
	public ResponseEntity<String> all(HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception {
		String appletId = servletrequest.getParameter("appletId");
		String appletTitle = servletrequest.getParameter("appletTitle");
		String lStringAppletParameters = (String)session.getAttribute(appletId + "parameters");
		lStringAppletParameters = lStringAppletParameters.replaceAll("\\.jnlp", ".jnlp?noCache=" + new Date().getTime());
		String lStringAppletAttributes = (String)session.getAttribute(appletId+ "attributes");
		lStringAppletAttributes = "{id: 'appletFirma', width: '100%' , height:'95%'}";
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.setCacheControl("private, no-store, no-cache, must-revalidate");
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		responseHeaders.add("Cache-Control", "private, no-store, no-cache, must-revalidate");
		StringBuffer lStringBuffer = new StringBuffer();
		lStringBuffer.append("<html>");
		lStringBuffer.append("<head>");
		lStringBuffer.append("<head>");
		lStringBuffer.append("<title>" + appletTitle + "</title>");
		lStringBuffer.append("<body>");
		lStringBuffer.append("<div>");
		lStringBuffer.append("<script src=\"../../script/deployJava.js?noCache=" + new Date().getTime() + "\"></script>");
		lStringBuffer.append("<script>");
		lStringBuffer.append("window.onbeforeunload = function(event) {");
//		lStringBuffer.append("event = event || window.event;");
		lStringBuffer.append("appletFirma.uploadFile();");
		lStringBuffer.append("var confirmClose = dialogArguments.restituisci();");
//		    // For IE and Firefox prior to version 4
//		lStringBuffer.append(" if (event) {");
//		lStringBuffer.append(" event.returnValue = confirmClose;");
//		lStringBuffer.append("}");
//		    // For Safari
		lStringBuffer.append("  return;");
		lStringBuffer.append("};");
//		lStringBuffer.append("function test() {return 'aaa';};");
//		lStringBuffer.append("window.onbeforeunload = function(event) {event = event || window.event; if (event){ event.returnValue = dialogArguments.restituisci();} return dialogArguments.restituisci();};");
		lStringBuffer.append("var parameters = " + lStringAppletParameters  + ";");
		lStringBuffer.append("var attributes = " + lStringAppletAttributes + ";");
		lStringBuffer.append("var errore;");
		lStringBuffer.append("try {deployJava.runApplet(attributes, parameters); }");
		lStringBuffer.append("catch(err) { errore = err; alert(err['message'])}");
//		lStringBuffer.append("deployJava.runApplet(attributes, parameters);");
		lStringBuffer.append("parent.appletFirma = appletFirma;");
		lStringBuffer.append("</script>");
		
		lStringBuffer.append("</div>");
		lStringBuffer.append("</body>");
		lStringBuffer.append("</html>");
		return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.CREATED);
	}
}
