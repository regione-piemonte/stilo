/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.passwordScaduta.datasource.AurigaCambioPasswordDataSource;
import it.eng.auriga.ui.module.layout.server.passwordScaduta.datasource.bean.ResetPasswordBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UtenteBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

@Controller
@RequestMapping("/servletResetPassword")
public class ResetPasswordServlet {
	
	private static Logger mLogger = Logger.getLogger(ResetPasswordServlet.class);

	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<String> verifica(@RequestParam("j_username") String username,HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse, ModelMap model) throws Exception {
		AurigaCambioPasswordDataSource lAurigaCambioPasswordDataSource = new AurigaCambioPasswordDataSource();
		
		AurigaLoginBean lLoginBean = AurigaUserUtil.getLoginInfo(session);	
		
		UtenteBean lUtenteBean = new UtenteBean();
		lUtenteBean.setUsername(username);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		Gson lGson = new Gson();
		
		String response ="";
		ResetPasswordBean lResetPasswordBean = new ResetPasswordBean();
		try {
			lResetPasswordBean = lAurigaCambioPasswordDataSource.resetPasswordLogin(lUtenteBean, lLoginBean, session);
		} catch (Exception e) {
			mLogger.warn(e);
			lResetPasswordBean.setChangeOk(false);
			lResetPasswordBean.setErrorMessages(e.getMessage());
		}
		
		response = lGson.toJson(lResetPasswordBean);
		return new ResponseEntity<String>(response, responseHeaders, HttpStatus.OK);
	}
}
