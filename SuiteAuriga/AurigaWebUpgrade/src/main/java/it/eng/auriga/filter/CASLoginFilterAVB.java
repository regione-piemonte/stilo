/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
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
import it.eng.utility.ui.module.layout.server.common.ServiceRestUserUtil;
import it.eng.utility.ui.module.layout.shared.bean.ApplicationConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.GestioneDominioBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

public class CASLoginFilterAVB implements Filter {

	private static Logger mLogger = Logger.getLogger(CASLoginFilterAVB.class);

	public static final String FMT_STD_DATA = "dd/MM/yyyy";

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest pServletRequest, ServletResponse pServletResponse, FilterChain pFilterChain) throws IOException, ServletException {

		// Trasformo la request
		HttpServletRequest req = (HttpServletRequest) pServletRequest;
		// Trasformo la response
		HttpServletResponse resp = (HttpServletResponse) pServletResponse;
		// Recupero la session
		HttpSession session = req.getSession();
		mLogger.debug("Path : " + req.getRequestURI());
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
		GestioneDominioBean gestioneDominioBean = null;
		// Bean utilizzato per la gestione delle omonimie su Area Vasta Bari. Decommentare per AVB
		if(!(SpringAppContext.getContext().getBean("GestioneDominioBean")==null)) {
			gestioneDominioBean = (GestioneDominioBean) SpringAppContext.getContext().getBean("GestioneDominioBean");
		}
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
		String usernameSSO = getUsernameSSO(session);
		String domain = "";
		// Parte inerente la gestione delle omonimie per Area Vasta Bari. Decommentare per AVB
		if (gestioneDominioBean!=null) {
			if(gestioneDominioBean.isAttivaGestioneMultidominio()) {
				domain = gestioneDominioBean.getDominio();
			} else {
				domain = CASAuthenticationFilter.casServerUrlPrefix;//getDomainName(session);//req.getServerName();
			}
		}
		if (domain!=null && domain.contains("rupar.puglia.it")) {
			usernameSSO = checkUsernameSSO(usernameSSO, domain);
		}

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
			mLogger.error("Chiamata a getRequestDispatcher");
			req.getRequestDispatcher(req.getRequestURI().substring(req.getContextPath().length()) + "?" + forward).forward(req, resp);
			return;
		}
		
		if (req.getRequestURI().contains("rest")) {
			mLogger.error("Faccio la verifica della chiamata rest");
			if (usernameSSO == null) {
				mLogger.error("usernameSSO è null");
				pServletResponse.setContentType("text/html");
				PrintWriter out = pServletResponse.getWriter();
				out.print(IOUtils.toString(CASLoginFilterAVB.class.getResourceAsStream("sessionExpired.jsp")));
				return;
			}

			if ((usernameSSO != null && loginBean == null) || (usernameSSO != null && !utenteLoggedIsSame(usernameSSO, loginBean))) {
				mLogger.error("Devo fare la login");
				SchemaSelector lSchemaSelector = (SchemaSelector) SpringAppContext.getContext().getBean("SchemaConfigurator");

				Locale locale = new Locale("it", "IT");
				loginBean = new AurigaLoginBean();
				// Inserisco la lingua di default
				loginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
				loginBean.setSchema(lSchemaSelector.getSchemi().get(0).getName());

				ApplicationConfigBean applicationConfigBean = (ApplicationConfigBean) SpringAppContext.getContext().getBean("ApplicationConfigurator");
				String idApplicazione = applicationConfigBean.getIdApplicazione();

				try {
					mLogger.error("Chiamo la DmpkLoginLogin");
					DmpkLoginLoginBean lLoginInput = new DmpkLoginLoginBean();
					lLoginInput.setUsernamein(usernameSSO);
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
					loginBean.setUserid(usernameSSO);
					String desUser = lLoginBean.getDesuserout();
					String codFisUser = null;
					if(StringUtils.isNotBlank(desUser) && desUser.contains(";CF:")) {
						int pos = desUser.indexOf(";CF:");					
						codFisUser = desUser.substring(pos + 4);		
						desUser = desUser.substring(0, pos);
					}
					String denominazione = StringUtils.isNotBlank(desUser) ? desUser : usernameSSO;
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
						mLogger.error("Chiamo la DmpkIntMgoEmailGetidutentemgoemail");
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
					loginBean.setUseridForPrefs(usernameSSO);
					
					try {
						mLogger.error("Setto parametri db");
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

					mLogger.error("Setto loginBean");
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
		mLogger.error("Termino il doFilter");
		pFilterChain.doFilter(pServletRequest, pServletResponse);
		return;

	}

	private boolean utenteLoggedIsSame(String usernameSSO, AurigaLoginBean loginBean) {
		return loginBean.getUserid().equals(usernameSSO);
	}

	public String getUsernameSSO(HttpSession session) {
		return (String) session.getAttribute("USERNAME_SSO");
	}
	
	public String getDomainName(HttpSession session) {
		return (String) session.getAttribute("DOMAIN_NAME");
	}
	
	public boolean isClienteAVB(HttpSession session) {
		return ParametriDBUtil.getParametroDB(session, "CLIENTE") != null && 
			   ParametriDBUtil.getParametroDB(session, "CLIENTE").equalsIgnoreCase("AVB");
	}
	
	public String checkUsernameSSO(String usernameSSO, String domain) {
		mLogger.debug("DOMINIO DI PROVENIENZA: " + domain);
		
		if (!(usernameSSO==null) && domain.contains("cruscotto.bari.avmtb.rsr.rupar.puglia.it")) {
			return usernameSSO;//.concat("_A662");
		} else if (!(usernameSSO==null) && domain.contains("cruscotto.ruvodipuglia.avmtb.rsr.rupar.puglia.it")) {
			return usernameSSO = usernameSSO.concat("_H645");
		}  else if (!(usernameSSO==null) && domain.contains("cruscotto.moladibari.avmtb.rsr.rupar.puglia.it")) {
			return usernameSSO = usernameSSO.concat("_F280");
		}  else if (!(usernameSSO==null) && domain.contains("cruscotto.casamassima.avmtb.rsr.rupar.puglia.it")) {
			return usernameSSO = usernameSSO.concat("_B923");
		}  else if (!(usernameSSO==null) && domain.contains("cruscotto.adelfia.avmtb.rsr.rupar.puglia.it")) {
			return usernameSSO = usernameSSO.concat("_A055");
		}  else if (!(usernameSSO==null) && domain.contains("cruscotto.turi.avmtb.rsr.rupar.puglia.it")) {
			return usernameSSO = usernameSSO.concat("_L472");
		}  else if (!(usernameSSO==null) && domain.contains("cruscotto.noicattaro.avmtb.rsr.rupar.puglia.it")) {
			return usernameSSO = usernameSSO.concat("_F923");
		}  else if (!(usernameSSO==null) && domain.contains("cruscotto.giovinazzo.avmtb.rsr.rupar.puglia.it")) {
			return usernameSSO = usernameSSO.concat("_E047");
		}  else if (!(usernameSSO==null) && domain.contains("cruscotto.cassanodellemurge.avmtb.rsr.rupar.puglia.it")) {
			return usernameSSO = usernameSSO.concat("_B998");
		} else if (!(usernameSSO==null) && domain.contains("cruscotto.cellamare.avmtb.rsr.rupar.puglia.it")) {
			return usernameSSO = usernameSSO.concat("_C436");
		} else {
				return usernameSSO;
		}
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
