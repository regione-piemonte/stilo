/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailGetidutentemgoemailBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.ui.module.layout.shared.bean.ParametriDBBean;
import it.eng.auriga.ui.module.layout.shared.bean.SchemaSelector;
import it.eng.auriga.ui.module.layout.shared.util.SharedConstants;
import it.eng.client.DmpkIntMgoEmailGetidutentemgoemail;
import it.eng.client.DmpkLoginLogin;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.XmlUtility;
import it.eng.utility.authentication.Authentication;
import it.eng.utility.filter.FilterBase;
import it.eng.utility.ui.module.layout.server.common.ServiceRestUserUtil;
import it.eng.utility.ui.module.layout.shared.bean.ApplicationConfigBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

public class ShibbolethLoginFilter extends FilterBase {

	private static Logger mLogger = Logger.getLogger(ShibbolethLoginFilter.class);

	public static final String FMT_STD_DATA = "dd/MM/yyyy";
	
	private FilterConfig filterConfig;
	
	private String usernameHeaderName;
	
	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
	
	public String getUsernameHeaderName() {
		return usernameHeaderName;
	}
	
	public void setUsernameHeaderName(String usernameHeaderName) {
		this.usernameHeaderName = usernameHeaderName;
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest pServletRequest, ServletResponse pServletResponse, FilterChain pFilterChain) throws IOException, ServletException {

		// Trasformo la request
		HttpServletRequest req = (HttpServletRequest) pServletRequest;
		// Trasformo la response
		HttpServletResponse resp = (HttpServletResponse) pServletResponse;
		// Loggo tutti gli header
//		mLogger.error("********** HEADER DELLA REQUEST **********");
//		Enumeration<String> requestHeaders = req.getHeaderNames();
//		while (requestHeaders.hasMoreElements()) {
//			String headerName = requestHeaders.nextElement();
//			String headerContent = req.getHeader(headerName);
//			mLogger.error(headerName + ": " + headerContent);
//		}
//		mLogger.error("********** HEADER DELLA RESPONSE **********");
//		Collection<String> responseHeaders = resp.getHeaderNames();
//		Iterator<String> responseHeadersIterator = responseHeaders.iterator();
//		while (responseHeadersIterator.hasNext()) {
//			responseHeadersIterator.next();
//			String headerName = (String) responseHeadersIterator.next();
//			String headerContent = resp.getHeader(headerName);
//			mLogger.error(headerName + ": " + headerContent);
//		}
		// Recupero la session
		HttpSession session = req.getSession();
		mLogger.debug("path : " + req.getRequestURI());
		mLogger.debug("Session id : " + session.getId());
		if (req.getRequestURI().contains("portlet.jsp")) {
			Date lDate = (Date) session.getAttribute("cleaned");
			if (lDate != null && (new Date().getTime() - lDate.getTime()) < 10000) {
				mLogger.error("Sessione pulita da poco");
			} else {
				mLogger.error("Pulisco la session");
				try {
					Enumeration<String> lEnumeration = session.getAttributeNames();
					while (lEnumeration.hasMoreElements()) {
						String lStrSessionAttr = lEnumeration.nextElement();
						session.removeAttribute(lStrSessionAttr);
					}
					session.setAttribute("cleaned", new Date());
				} catch (Throwable t) {
					mLogger.error("Errore " + t.getMessage(), t);
				}
			}
		}

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
		String shibbolethUsername = getShibUsername(req);

		if (req.getRequestURI().contains("applets") || req.getRequestURI().contains("download") || req.getRequestURI().contains("commons-logging.properties")
				|| req.getRequestURI().contains("UploadMultiSignerApplet") || req.getRequestURI().contains("theme")
				|| req.getRequestURI().contains("log4j.xml") || req.getRequestURI().contains("PatternLayoutBeanInfo")
				|| req.getRequestURI().contains("LayoutBeanInfo") || req.getRequestURI().contains(".class") || req.getRequestURI().contains(".properties")) {
			boolean first = true;
			String forward = "";
			for (String lString : req.getParameterMap().keySet()) {
				if (first) {
					first = false;
				} else {
					forward += "&";
				}
				forward += lString + "=" + req.getParameter(lString);
			}
			req.getRequestDispatcher(req.getRequestURI().substring(req.getContextPath().length()) + "?" + forward).forward(req, resp);
			return;
		} else {
			mLogger.debug("Applet ricado qui");
		}

		if (req.getRequestURI().contains("rest")) {
//			if (iamUsername == null) {
//				session.invalidate();
//				pServletResponse.setContentType("text/html");
//				PrintWriter out = pServletResponse.getWriter(); 				
//				out.print(IOUtils.toString(ShibbolethLoginFilter.class.getResourceAsStream("sessionExpired.jsp")));
//				return;
//			}

			if ((StringUtils.isNotBlank(shibbolethUsername) && loginBean == null) || (StringUtils.isNotBlank(shibbolethUsername) && !utenteLoggedIsSame(shibbolethUsername, loginBean))) {
				try {
					// Loggo tutti gli header
					mLogger.debug("********** HEADER DELLA REQUEST **********");
					Enumeration<String> requestHeaders = req.getHeaderNames();
					while (requestHeaders.hasMoreElements()) {
						String headerName = requestHeaders.nextElement();
						String headerContent = req.getHeader(headerName);
						mLogger.debug(headerName + ": " + headerContent);
					}
				} catch (Exception e){
					mLogger.error("Errore nella lettura degli header nella request", e);
				}

				SchemaSelector lSchemaSelector = (SchemaSelector) SpringAppContext.getContext().getBean("SchemaConfigurator");

				Locale locale = new Locale("it", "IT");
				loginBean = new AurigaLoginBean();
				// Inserisco la lingua di default
				loginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
				loginBean.setSchema(lSchemaSelector.getSchemi().get(0).getName());

				ApplicationConfigBean applicationConfigBean = (ApplicationConfigBean) SpringAppContext.getContext().getBean("ApplicationConfigurator");
				String idApplicazione = applicationConfigBean.getIdApplicazione();

				try {
					DmpkLoginLoginBean lLoginInput = new DmpkLoginLoginBean();
					lLoginInput.setUsernamein(shibbolethUsername);
					lLoginInput.setFlgnoctrlpasswordin(1);
					lLoginInput.setFlgautocommitin(1);
					lLoginInput.setFlgrollbckfullin(0);

					DmpkLoginLogin lLogin = new DmpkLoginLogin();

					StoreResultBean<DmpkLoginLoginBean> lLoginOutput = lLogin.execute(locale, loginBean, lLoginInput);

					if (lLoginOutput.isInError()) {

						throw new Exception(lLoginOutput.getDefaultMessage());
					}

					DmpkLoginLoginBean lLoginBean = lLoginOutput.getResultBean();
					loginBean.setIdApplicazione(idApplicazione);
					loginBean.setUserid(shibbolethUsername);
					String desUser = lLoginBean.getDesuserout();
					String codFisUser = null;
					if(StringUtils.isNotBlank(desUser) && desUser.contains(";CF:")) {
						int pos = desUser.indexOf(";CF:");					
						codFisUser = desUser.substring(pos + 4);		
						desUser = desUser.substring(0, pos);
					}
					String denominazione = StringUtils.isNotBlank(desUser) ? desUser : shibbolethUsername;
					if (StringUtils.isNotBlank(lLoginBean.getDesdominioout())) {
						denominazione += "@" + lLoginBean.getDesdominioout();
					}
					loginBean.setDenominazione(denominazione);
					loginBean.setToken(lLoginBean.getCodidconnectiontokenout());
					loginBean.setIdUserLavoro(null);
					loginBean.setSchema(loginBean.getSchema());					
					loginBean.setDominio(lLoginBean.getFlgtpdominioautio() + ":" + lLoginBean.getIddominioautio());
					loginBean.setIdUser(lLoginBean.getIduserout());
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
						lDmpkIntMgoEmailGetidutentemgoemailBean.setIduserin(loginBean.getIdUser());
						SchemaBean lSchemaBean = new SchemaBean();
						lSchemaBean.setSchema(loginBean.getSchema());
						lDmpkIntMgoEmailGetidutentemgoemailBean = lDmpkIntMgoEmailGetidutentemgoemail.execute(locale, lSchemaBean,
								lDmpkIntMgoEmailGetidutentemgoemailBean).getResultBean();
						spec.setIdUtenteModPec(lDmpkIntMgoEmailGetidutentemgoemailBean.getIdutentepecout());
					} catch (Exception e) {
					}
					loginBean.setSpecializzazioneBean(spec);
					loginBean.setUseridForPrefs(shibbolethUsername);
					
					try {
						StringReader sr = new StringReader(loginBean.getSpecializzazioneBean().getParametriConfigOut());
						Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
						ParametriDBBean parametriDBBean = new ParametriDBBean();
						HashMap<String, String> parametriDB = new HashMap<String, String>();
						if (lista != null) {
							for (int i = 0; i < lista.getRiga().size(); i++) {
								Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
								if(v.get(0).equalsIgnoreCase("CF_UTENTE_LOGIN")) {
									loginBean.setCodFiscale(v.get(1));		
								} else {
									parametriDB.put(v.get(0), v.get(1));
								}
							}
						}
						Authentication auth = (Authentication) SpringAppContext.getContext().getBean("authentication");
						if (auth != null && auth.getAuthType() != null) {
							parametriDB.put("AUTHENTICATION_TYPE", auth.getAuthType().getValue());
						}
						parametriDBBean.setParametriDB(parametriDB);
						ParametriDBUtil.setParametriDB(session, parametriDBBean);
					} catch (Exception e) {
					}

					AurigaUserUtil.setLoginInfo(session, loginBean);

					// salvo nella loginInfo in sessione i privilegi
					String[] privilegi = null;
					try {
						privilegi = ServiceRestUserUtil.getPrivilegi().getPrivilegi(session);
					} catch (Exception e) {
						mLogger.error(e.getMessage(), e);
					}

				} catch (Exception e) {
					mLogger.error(e.getMessage(), e);
				}
			}
		}
		pFilterChain.doFilter(pServletRequest, pServletResponse);
		return;

	}

	private boolean utenteLoggedIsSame(String shibUserName, AurigaLoginBean loginBean) {
		return loginBean.getUserid().equalsIgnoreCase(shibUserName);
	}

	public String getShibUsername(HttpServletRequest request) {
		return StringUtils.isNotBlank(request.getHeader(getUsernameHeaderName())) ? request.getHeader(getUsernameHeaderName()).toUpperCase() : request.getHeader(getUsernameHeaderName());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		this.filterConfig = filterConfig;
	}

	@Override
	protected Logger getLogger() {
		return mLogger;
	}

}
