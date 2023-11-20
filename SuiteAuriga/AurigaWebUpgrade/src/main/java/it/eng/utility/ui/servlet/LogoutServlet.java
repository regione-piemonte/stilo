/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/logout")
public class LogoutServlet {
	@RequestMapping(value="", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getInfoStorage(ModelMap model, HttpSession httpSession, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception{
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		httpSession.invalidate();
		return new ResponseEntity<String>("", responseHeaders, HttpStatus.OK);
		
	}
}
