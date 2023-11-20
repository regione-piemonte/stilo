/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Locale;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginVerificacredenzialiBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.shared.bean.SchemaSelector;
import it.eng.auriga.ui.module.layout.shared.util.SharedConstants;
import it.eng.client.DmpkLoginVerificacredenziali;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.service.SJCLServer;
import it.eng.utility.ui.module.layout.shared.bean.ApplicationConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.user.UserUtil;

@Controller
@RequestMapping("/verificaLogin")
public class VerificaCredenzialiServlet {

	private static Logger mLogger = Logger.getLogger(VerificaCredenzialiServlet.class);
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> get(HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception {
		String resultHtml = "<hml><h1>Hello verifica login</h1><html>";
		mLogger.debug(resultHtml);
		servletresponse.setHeader("Content-Type", "text/html;charset=ISO-8859-1");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		return new ResponseEntity<String>(resultHtml, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<String> verifica(@RequestBody String json,HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse, ModelMap model) throws Exception {
		
		SJCLServer sjcl = null;
		try {
		    sjcl = (SJCLServer) SpringAppContext.getContext().getBean("sjcl");
		    // mLogger.debug("sjcl: "+String.valueOf(sjcl));
		} catch (BeansException e) {
			mLogger.warn("Recupero del bean 'sjcl' per la cifratura non riuscito.", e);
		}
		servletresponse.setHeader("Content-Type", "text/html;charset=ISO-8859-1");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		String action = servletrequest.getParameter("action");
		mLogger.debug("Richiesta azione " + action);
		if (StringUtils.isNotEmpty(action) && action.equals("logout")){
			session.invalidate();	
			return new ResponseEntity<String>("", responseHeaders, HttpStatus.OK);
		} else if (StringUtils.isNotEmpty(action) && action.equals("login")){
			//Recupero l'utente inserito
			String username = servletrequest.getParameter("j_username");
			//Recupero la password inserita
			String password = servletrequest.getParameter("j_password");
			if (sjcl != null) {
				try {
					password = sjcl.decrypt(password, true);
				} catch (ScriptException e) {
					mLogger.error("Decifratura della password non riuscita.", e);					
				}
			}
			String values[] = password.split("#SCHEMA#");
			if(values != null && values.length > 0) {
				password = values[0];
			}
			String dominio = servletrequest.getParameter("j_dominio");
			AurigaLoginBean lAurigaLoginBean = new AurigaLoginBean();
			// Inserisco la lingua di default
			lAurigaLoginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
			lAurigaLoginBean.setUserid(username);
			lAurigaLoginBean.setDominio(dominio);
						
			// Verifico se ho lo schema selezionato in sessione
			SchemaSelector lSchemaSelector = SpringAppContext.getContext().getBean(SchemaSelector.class);
			String schemaName = null;
			if (session != null && session.getAttribute("LOGIN_INFO") != null && StringUtils.isNotBlank(((LoginBean) session.getAttribute("LOGIN_INFO")).getSchema())) {
				schemaName = (((LoginBean) session.getAttribute("LOGIN_INFO")).getSchema());
			} else {
				schemaName = (lSchemaSelector.getSchemi().get(0).getName());
			}
						
			boolean login = makeLogin(schemaName, username, password);
			
			//Faccio la login
			if (login) {
				LoginBean lLoginBean = new LoginBean();
				ApplicationConfigBean applicationConfigBean = (ApplicationConfigBean)SpringAppContext.getContext().getBean("ApplicationConfigurator");
				String idApplicazione = applicationConfigBean.getIdApplicazione();
				lLoginBean.setIdApplicazione(idApplicazione);
				lLoginBean.setUserid(username);
				lLoginBean.setDenominazione(username);	
				
				lLoginBean.setSchema(schemaName);
				lAurigaLoginBean.setSchema(schemaName);
				
				
				UserUtil.setLoginInfo(session, lLoginBean);
				return new ResponseEntity<String>("loginOK",  HttpStatus.OK);
			}
		} 
		session.invalidate(); 
		return new ResponseEntity<String>("",  HttpStatus.OK);
	}

	private boolean makeLogin(String  schemaName, String username, String password) {
		DmpkLoginVerificacredenziali lDmpkLoginVerificacredenziali = new DmpkLoginVerificacredenziali();
		DmpkLoginVerificacredenzialiBean lDmpkLoginVerificacredenzialiBean = new DmpkLoginVerificacredenzialiBean();
		lDmpkLoginVerificacredenzialiBean.setUsernamein(username);
		
		Locale locale = new Locale("it", "IT");
		StoreResultBean<DmpkLoginVerificacredenzialiBean> result;
		
		lDmpkLoginVerificacredenzialiBean.setPasswordin(password);
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(schemaName);
		
		try {
			result = lDmpkLoginVerificacredenziali.execute(locale, lSchemaBean, lDmpkLoginVerificacredenzialiBean);
		} catch (Exception e) {
			mLogger.error(e);
			return false;
		}
		if (result.isInError()) {
			mLogger.error(result.getDefaultMessage(), new Throwable("Messaggio: " + 
					result.getDefaultMessage() + " errorContext: " + result.getErrorContext() 
					+ "errorCode: " + result.getErrorCode()));
			return false;
		}
		else return true;
	}
	
}
