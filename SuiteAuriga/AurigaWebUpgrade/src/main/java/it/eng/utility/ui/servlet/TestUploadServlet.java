/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/TestUploadServlet")
public class TestUploadServlet {
	
	public final static Logger logger = Logger.getLogger(TestUploadServlet.class);

	@RequestMapping(value="/", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> post(@RequestParam("messageTest") String message,
			final HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception {
		
		Cookie ck[] = servletrequest.getCookies();  
		if (ck != null){
			for(int i = 0; i < ck.length; i++){  
				logger.debug("Trovato cookie: " + ck[i].getName() + " " + ck[i].getValue());  
			}  
		} else {
			logger.debug("Nessuno cookies presente");
		}
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		String response = new String(message + " ricevuto alle " + new Date().toString());
		return new ResponseEntity<String>(response, responseHeaders, HttpStatus.OK);

	}
}
