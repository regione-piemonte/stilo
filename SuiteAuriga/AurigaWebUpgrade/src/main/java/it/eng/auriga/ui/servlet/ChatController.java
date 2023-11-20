/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.util.ArrayList;
import java.util.List;

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

import com.google.gson.Gson;

@Controller
@RequestMapping("/chat")
public class ChatController {

	private static List<String> messages = new ArrayList<String>();

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<String> login(HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception {

		AurigaLoginBean loginInfo = AurigaUserUtil.getLoginInfo(session);
		ChatControllerBean bean = new ChatControllerBean();
		bean.setLoginInfo(loginInfo);
		bean.setUsername(loginInfo.getUserid());
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		String response = new Gson().toJson(bean);
		return new ResponseEntity<String>(response, responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/getHistory", method = RequestMethod.GET)
	public ResponseEntity<String> getHistory(HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception {

		ChatControllerBean bean = new ChatControllerBean();
		bean.setHistory(messages.toArray(new String[0]));
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		String response = new Gson().toJson(bean);
		return new ResponseEntity<String>(response, responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	public synchronized ResponseEntity<String> sendMessage(ChatControllerBean bean, HttpSession session, HttpServletRequest servletrequest,
			HttpServletResponse servletresponse) throws Exception {

		if (bean.getMessaggio() != null && !"".equals(bean.getMessaggio())) {
			messages.add(bean.getUsername() + ": " + bean.getMessaggio());
		}
		bean.setHistory(messages.toArray(new String[0]));
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		String response = new Gson().toJson(bean);
		return new ResponseEntity<String>(response, responseHeaders, HttpStatus.OK);
	}
}
