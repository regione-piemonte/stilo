/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailGetidutentemgoemailBean;

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginGetusernamemailBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginapplicazioneBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.ui.module.layout.shared.bean.ParametriDBBean;
import it.eng.client.DmpkIntMgoEmailGetidutentemgoemail;
import it.eng.client.DmpkLoginGetusernamemail;
import it.eng.client.DmpkLoginLogin;
import it.eng.client.DmpkLoginLoginapplicazione;
import it.eng.gdpr.ClientDataHttpSupport;
import it.eng.gdpr.ParametriClientExtractor;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.LogLoginTryCallHelper;
import it.eng.utility.XmlUtility;
import it.eng.utility.authentication.Authentication;
import it.eng.utility.authentication.module.LoginModuleImpl;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.AbstractRootService;
import it.eng.utility.ui.module.layout.server.common.ServiceRestUserUtil;
import it.eng.utility.ui.module.layout.shared.bean.ApplicationConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "AurigaLoginDataSource")
public class LoginDataSource extends AbstractServiceDataSource<AurigaLoginBean, AurigaLoginBean> {

	public static final String NAME_ATTRIBUTE_CSRF_TOKEN = "Csrf-Token";
	public static final String NAME_COOKIE_CSRF_TOKEN = "Csrf-Token";
	private static Logger mLogger = Logger.getLogger(LoginModuleImpl.class);
	private final SecureRandom secureRandom = new SecureRandom();
	private LogLoginTryCallHelper logLoginTryHelperCall = new LogLoginTryCallHelper();

	@Override
	public AurigaLoginBean call(AurigaLoginBean bean) throws Exception {

		AurigaLoginBean loginInfo = new AurigaLoginBean();

		loginInfo.setLinguaApplicazione(getLocale().getLanguage());
		loginInfo.setSpecLabelGui("");

		String userid = bean.getUserid() != null && !"".equals(bean.getUserid()) ? bean.getUserid() : getRequest().getRemoteUser();
		userid = userid != null ? userid.toUpperCase().replaceAll(" ", "") : null;

		try {
			// se arrivo da un applicazione esterna
			boolean fromExtAppl = userid != null && userid.startsWith("USERID_APPL#");
			if (fromExtAppl) {
				String useridappl = userid.substring(12);

				DmpkLoginLoginapplicazioneBean input = new DmpkLoginLoginapplicazioneBean();
				input.setUseridapplicazionein(useridappl);
				input.setFlgnoctrlpasswordin(1);

				DmpkLoginLoginapplicazione loginApplicazione = new DmpkLoginLoginapplicazione();
				StoreResultBean<DmpkLoginLoginapplicazioneBean> output = loginApplicazione.execute(getLocale(), bean, input);

				DmpkLoginLoginapplicazioneBean lLoginBean = output.getResultBean();
				if (islogLoginTryEnabled()) {
					logLoginTry(bean, "PORTLET_AURIGAWEB", useridappl, output.isInError(), lLoginBean.getFlgtpdominioautout(), lLoginBean.getIddominioautout(),
							lLoginBean.getCodidconnectiontokenout());
				}

				if (output.isInError()) {
					AurigaLoginBean lAurigaLoginBean = new AurigaLoginBean();
					// Inserisco la lingua di default
					// lAurigaLoginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
					lAurigaLoginBean.setLinguaApplicazione(getLocale().getLanguage());
					lAurigaLoginBean.setLoginReject(output.getDefaultMessage());
					// addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
					return lAurigaLoginBean;
				}

				ApplicationConfigBean applicationConfigBean = (ApplicationConfigBean) SpringAppContext.getContext().getBean("ApplicationConfigurator");
				String idApplicazione = applicationConfigBean.getIdApplicazione();
				loginInfo.setIdApplicazione(idApplicazione); // qui andrebbe messo l'id corrispondente all'applicazione esterna
				loginInfo.setPassword(bean.getPassword());
				loginInfo.setUserid(useridappl);
				String desUser = lLoginBean.getDesuserout();
				String codFisUser = null;
				if(StringUtils.isNotBlank(desUser) && desUser.contains(";CF:")) {
					int pos = desUser.indexOf(";CF:");					
					codFisUser = desUser.substring(pos + 4);		
					desUser = desUser.substring(0, pos);
				}
				String denominazione = StringUtils.isNotBlank(desUser) ? desUser : userid;
				if (StringUtils.isNotBlank(lLoginBean.getDesdominioout())) {
					denominazione += "@" + lLoginBean.getDesdominioout();
				}
				loginInfo.setDenominazione(denominazione);
				loginInfo.setToken(lLoginBean.getCodidconnectiontokenout());
				loginInfo.setIdUserLavoro(null);
				loginInfo.setSchema(bean.getSchema());
				String dominio = (lLoginBean.getFlgtpdominioautout() != null) ? "" + lLoginBean.getFlgtpdominioautout() : "";
				if (lLoginBean.getIddominioautout() != null) {
					dominio += ":" + lLoginBean.getIddominioautout();
				}
				loginInfo.setDominio(dominio);
				loginInfo.setIdUser(lLoginBean.getIduserout());
				SpecializzazioneBean spec = new SpecializzazioneBean();
				spec.setCodIdConnectionToken(lLoginBean.getCodidconnectiontokenout());
				spec.setDesDominioOut(lLoginBean.getDesdominioout());
				spec.setDesUserOut(desUser);
				spec.setCodFiscaleUserOut(codFisUser);
				spec.setIdDominio(lLoginBean.getIddominioautout());
				spec.setParametriConfigOut(lLoginBean.getParametriconfigout());
				spec.setTipoDominio(lLoginBean.getFlgtpdominioautout());
				try {
					DmpkIntMgoEmailGetidutentemgoemail lDmpkIntMgoEmailGetidutentemgoemail = new DmpkIntMgoEmailGetidutentemgoemail();
					DmpkIntMgoEmailGetidutentemgoemailBean lDmpkIntMgoEmailGetidutentemgoemailBean = new DmpkIntMgoEmailGetidutentemgoemailBean();
					lDmpkIntMgoEmailGetidutentemgoemailBean.setIduserin(loginInfo.getIdUser());
					SchemaBean lSchemaBean = new SchemaBean();
					lSchemaBean.setSchema(loginInfo.getSchema());
					lDmpkIntMgoEmailGetidutentemgoemailBean = lDmpkIntMgoEmailGetidutentemgoemail
							.execute(getLocale(), lSchemaBean, lDmpkIntMgoEmailGetidutentemgoemailBean).getResultBean();
					spec.setIdUtenteModPec(lDmpkIntMgoEmailGetidutentemgoemailBean.getIdutentepecout());
				} catch (Exception e) {
				}
				loginInfo.setSpecializzazioneBean(spec);
				loginInfo.setUseridForPrefs(userid);

			} else {

				ApplicationConfigBean applicationConfigBean = (ApplicationConfigBean) SpringAppContext.getContext().getBean("ApplicationConfigurator");
				String idApplicazione = applicationConfigBean.getIdApplicazione();
				DmpkLoginLoginBean input = new DmpkLoginLoginBean();
				input.setUsernamein(userid);
				if(StringUtils.isNotBlank(applicationConfigBean.getCodApplicazioneInLoginDB())) {
					// per il contesto delle ordinanze di Milano in CodApplicazioneEstIn devo passare #ORDINANZE per fare in modo che mi torni il parametro DB GESTIONE_ATTI_COMPLETA forzato a true
					input.setCodapplicazioneestin(applicationConfigBean.getCodApplicazioneInLoginDB());
				}
				input.setFlgnoctrlpasswordin(1);
				String dominio = bean.getDominio();
				Integer tipoDominio = Integer.valueOf(dominio.split(":")[0]);
				if (dominio.split(":").length == 2) {
					BigDecimal idDominio = new BigDecimal(dominio.split(":")[1]);
					input.setIddominioautio(idDominio);
				}
				input.setFlgtpdominioautio(tipoDominio);
				input.setFlgautocommitin(1);
				input.setFlgrollbckfullin(0);
				DmpkLoginLogin login = new DmpkLoginLogin();
				StoreResultBean<DmpkLoginLoginBean> output = login.execute(getLocale(), bean, input);

				DmpkLoginLoginBean lLoginBean = output.getResultBean();
				if (islogLoginTryEnabled()) {
					logLoginTry(bean, null, userid, output.isInError(), lLoginBean.getFlgtpdominioautio(), lLoginBean.getIddominioautio(),
							lLoginBean.getCodidconnectiontokenout());
				}

				if (output.isInError()) {
					if (output.getErrorCode() != 1008) {
						AurigaLoginBean lAurigaLoginBean = new AurigaLoginBean();
						// Inserisco la lingua di default
						// lAurigaLoginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
						lAurigaLoginBean.setLinguaApplicazione(getLocale().getLanguage());
						lAurigaLoginBean.setLoginReject(output.getDefaultMessage());
						// addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
						return lAurigaLoginBean;
					} else
						loginInfo.setPasswordScaduta(true);
				}

				loginInfo.setIdApplicazione(idApplicazione);
				loginInfo.setPassword(bean.getPassword());
				loginInfo.setUserid(userid);
				String desUser = lLoginBean.getDesuserout();
				String codFisUser = null;
				if(StringUtils.isNotBlank(desUser) && desUser.contains(";CF:")) {
					int pos = desUser.indexOf(";CF:");					
					codFisUser = desUser.substring(pos + 4);		
					desUser = desUser.substring(0, pos);
				}						
				String denominazione = StringUtils.isNotBlank(desUser) ? desUser : userid;
				if (StringUtils.isNotBlank(lLoginBean.getDesdominioout())) {
					denominazione += "@" + lLoginBean.getDesdominioout();
				}
				loginInfo.setDenominazione(denominazione);
				loginInfo.setToken(lLoginBean.getCodidconnectiontokenout());
				loginInfo.setIdUserLavoro(null);
				loginInfo.setSchema(bean.getSchema());
				// loginInfo.setDominio(lLoginBean.getIddominioautio());
				loginInfo.setDominio(bean.getDominio());
				loginInfo.setIdUser(lLoginBean.getIduserout());
				SpecializzazioneBean spec = new SpecializzazioneBean();
				spec.setCodIdConnectionToken(lLoginBean.getCodidconnectiontokenout());
				spec.setDesDominioOut(lLoginBean.getDesdominioout());
				spec.setDesUserOut(desUser);
				spec.setCodFiscaleUserOut(codFisUser);
				spec.setIdDominio(lLoginBean.getIddominioautio());
				spec.setParametriConfigOut(lLoginBean.getParametriconfigout());
				spec.setTipoDominio(lLoginBean.getFlgtpdominioautio());
				try {
					DmpkIntMgoEmailGetidutentemgoemail lDmpkIntMgoEmailGetidutentemgoemail = new DmpkIntMgoEmailGetidutentemgoemail();
					DmpkIntMgoEmailGetidutentemgoemailBean lDmpkIntMgoEmailGetidutentemgoemailBean = new DmpkIntMgoEmailGetidutentemgoemailBean();
					lDmpkIntMgoEmailGetidutentemgoemailBean.setIduserin(loginInfo.getIdUser());
					SchemaBean lSchemaBean = new SchemaBean();
					lSchemaBean.setSchema(loginInfo.getSchema());
					lDmpkIntMgoEmailGetidutentemgoemailBean = lDmpkIntMgoEmailGetidutentemgoemail
							.execute(getLocale(), lSchemaBean, lDmpkIntMgoEmailGetidutentemgoemailBean).getResultBean();
					spec.setIdUtenteModPec(lDmpkIntMgoEmailGetidutentemgoemailBean.getIdutentepecout());
				} catch (Exception e) {
				}
				loginInfo.setSpecializzazioneBean(spec);
				loginInfo.setUseridForPrefs(userid);
				// loginInfo.setLinguaApplicazione("en");

			}

			StringReader sr = new StringReader(loginInfo.getSpecializzazioneBean().getParametriConfigOut());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			ParametriDBBean result = new ParametriDBBean();
			HashMap<String, String> parametriDB = new HashMap<String, String>();
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					if (v.get(0).equalsIgnoreCase("CF_UTENTE_LOGIN")) {
						loginInfo.setCodFiscale(v.get(1));
					} else {
						parametriDB.put(v.get(0), v.get(1));
					}
				}
			}
			Authentication auth = (Authentication) SpringAppContext.getContext().getBean("authentication");
			if (auth != null && auth.getAuthType() != null) {
				parametriDB.put("AUTHENTICATION_TYPE", auth.getAuthType().getValue());
			}
			result.setParametriDB(parametriDB);
			ParametriDBUtil.setParametriDB(getSession(), result);

			// Leggo il nome del logo
			if (parametriDB.get("LOGO_UTENTE") != null) {
				String logo = parametriDB.get("LOGO_UTENTE").toString();
				loginInfo.setLogoUtente(logo);
			}

			// Leggo la lingua associata all'utente
			if (parametriDB.get("LINGUA_UTENTE") != null) {
				String lingua = parametriDB.get("LINGUA_UTENTE").toString();
				loginInfo.setLinguaApplicazione(lingua);
			}

			// Leggo il dominio per la specializzazione della GUI
			if (parametriDB.get("SPEC_LABEL_GUI") != null) {
				String specLabelGUI = parametriDB.get("SPEC_LABEL_GUI").toString();
				loginInfo.setSpecLabelGui(specLabelGUI);
			}

			// Leggo il flag per stabilire se attivare la cifratura
			if (getSession() != null) {
				final String NAME_FLAG_CIFRATURA = AbstractRootService.NAME_FLAG_CIFRATURA;
				final boolean flagCifratura = ParametriDBUtil.getParametroDBAsBoolean(getSession(), NAME_FLAG_CIFRATURA);
				getSession().setAttribute(NAME_FLAG_CIFRATURA, Boolean.valueOf(flagCifratura));
				mLogger.info("Messo in sessione " + getSession().getId() + ": " + NAME_FLAG_CIFRATURA + " con valore " + flagCifratura);

				if (getRequest() != null && getResponse() != null) {
					final String token = generateToken();
					getSession().setAttribute(NAME_ATTRIBUTE_CSRF_TOKEN, token);
					mLogger.info("Messo in sessione " + getSession().getId() + ": " + NAME_ATTRIBUTE_CSRF_TOKEN + " con valore " + token);
					sendCookieAndSetResponseHeader(getRequest(), getResponse(), token);
				}
			}

			AurigaUserUtil.setLoginInfo(getRequest().getSession(), loginInfo);

			// salvo nella loginInfo in sessione i privilegi
			String[] privilegi = null;
			try {
				privilegi = ServiceRestUserUtil.getPrivilegi().getPrivilegi(getSession());
			} catch (Exception e) {
			}

			// Cacco Federico 02-12-2015
			// Verifico le abilitazioni per il multilingua
			GenericConfigBean genericConfigBean = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
			loginInfo.setAttivaMultilingua(genericConfigBean.getAttivaMultilingua() != null ? genericConfigBean.getAttivaMultilingua() : false);

		} catch (Exception e) {
			throw e;
		}

		return loginInfo;
	}

	private void logLoginTry(AurigaLoginBean bean, String codApplicazione, String userid, boolean flagKO, Integer tipoDominioAut, BigDecimal idDominioAut,
			String codIdConnectionToken) {
		String parametriClient = null;
		try {
			parametriClient = ParametriClientExtractor.getJsonParametriClient(ClientDataHttpSupport.getData(getRequest()));
		} catch (Exception e) {
			final String msg = "Il recupero dei parametri del client è fallito.";
			mLogger.error(msg, e);
			parametriClient = "{\"errore\":\"" + msg + "\"}";
		}
		try {
			logLoginTryHelperCall.call(bean, codApplicazione, userid, flagKO, tipoDominioAut, idDominioAut, codIdConnectionToken, parametriClient);
		} catch (Exception e) {
			final String msg = "La chiamata della function '" + LogLoginTryCallHelper.FUNCTION_NAME + "' è fallita.";
			mLogger.error(msg, e);
		}
	}// logLoginTry

	private boolean islogLoginTryEnabled() {
		if (getRequest() == null) {
			return false;
		}
		final Object clientDataObj = getRequest().getAttribute(ClientDataHttpSupport.REQUEST_ATTRIBUTE_NAME_CLIENT_DATA);
		if (!(clientDataObj instanceof Map)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		final Map<String, String> clientData = (Map<String, String>) clientDataObj;
		return !clientData.isEmpty();
	}

	private String generateToken() {
		byte[] buffer = new byte[50];
		this.secureRandom.nextBytes(buffer);
		return DatatypeConverter.printHexBinary(buffer);
	}

	private void sendCookieAndSetResponseHeader(HttpServletRequest httpReq, HttpServletResponse httpResp, String token) {
		// Add cookie manually because the current Cookie class implementation do not support the "SameSite" attribute
		// We let the adding of the "Secure" cookie attribute to the reverse proxy rewriting...
		// Here we lock the cookie from JS access (HttpOnly;) and we use the SameSite new attribute protection
		final String name = determineCookieName(httpReq);
		final String value = token;
		final String path = "/";

		final Object[] args = { name, value, path };
		final String format = "%s=%s; Path=%s; SameSite=Strict";
		final String values = String.format(format, args);
		mLogger.debug("values: " + values);
		httpResp.addHeader("Set-Cookie", values);

		// Add cookie header to give access to the token to the JS code
		httpResp.setHeader("X-" + NAME_COOKIE_CSRF_TOKEN, value);
	}

	private String determineCookieName(HttpServletRequest httpRequest) {
		final boolean flagOnlyName = false;
		String backendServiceName = flagOnlyName ? "" : "-" + httpRequest.getContextPath().replaceAll("/", "-");
		return NAME_COOKIE_CSRF_TOKEN + backendServiceName;
	}

	public String getUsernameMail(String usernameIn) throws Exception {
		
		String ret=null;

		// Inizializzo l'INPUT
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(loginBean.getSchema());
		DmpkLoginGetusernamemailBean  input = new DmpkLoginGetusernamemailBean();
		input.setUsernameio(usernameIn);

		// Eseguo il servizio
		DmpkLoginGetusernamemail service = new DmpkLoginGetusernamemail();
		StoreResultBean<DmpkLoginGetusernamemailBean> output = service.execute(getLocale(), lSchemaBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			mLogger.error("Errore nella getUsernameMail : " + output.getDefaultMessage());
			throw new StoreException(output);
		}
		ret = output.getResultBean().getEmailio();
		return ret;
	}
}