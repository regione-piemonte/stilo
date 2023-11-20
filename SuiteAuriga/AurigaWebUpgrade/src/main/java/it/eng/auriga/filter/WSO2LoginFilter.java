/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
import it.eng.utility.LogLoginTryCallHelper;
import it.eng.utility.XmlUtility;
import it.eng.utility.authentication.Authentication;
import it.eng.utility.ui.module.layout.server.common.ServiceRestUserUtil;
import it.eng.utility.ui.module.layout.shared.bean.ApplicationConfigBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

public class WSO2LoginFilter implements Filter {
	
	private LogLoginTryCallHelper logLoginTryHelperCall = new LogLoginTryCallHelper();
	
    private static Logger mLogger;
    public static final String FMT_STD_DATA = "dd/MM/yyyy";
    
    public void destroy() {
    }
    
    public void doFilter(final ServletRequest pServletRequest, final ServletResponse pServletResponse, final FilterChain pFilterChain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest)pServletRequest;
        final HttpServletResponse resp = (HttpServletResponse)pServletResponse;
        final HttpSession session = req.getSession();
        WSO2LoginFilter.mLogger.debug((Object)("path : " + req.getRequestURI()));
        WSO2LoginFilter.mLogger.debug((Object)("Session id : " + session.getId()));
//        if (req.getRequestURI().contains("acs.jsp")) {
//            final Date lDate = (Date)session.getAttribute("cleaned");
//            if (lDate != null && new Date().getTime() - lDate.getTime() < 10000L) {
//                WSO2LoginFilter.mLogger.error((Object)"Sessione pulita da poco");
//            }
//            else {
//                WSO2LoginFilter.mLogger.error((Object)"Pulisco la session");
//                try {
//                    final Enumeration<String> lEnumeration = (Enumeration<String>)session.getAttributeNames();
//                    while (lEnumeration.hasMoreElements()) {
//                        final String lStrSessionAttr = lEnumeration.nextElement();
//                        session.removeAttribute(lStrSessionAttr);
//                    }
//                    session.setAttribute("cleaned", (Object)new Date());
//                    session.setAttribute("isLogin", (Object)"isLogin");
//                }
//                catch (Throwable t) {
//                    WSO2LoginFilter.mLogger.error((Object)("Errore " + t.getMessage()), t);
//                }
//            }
//        }
        AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
        final String usernameSSO = getUsernameSSO(session);
        if (req.getRequestURI().contains("applets") || req.getRequestURI().contains("download") || req.getRequestURI().contains("commons-logging.properties") || req.getRequestURI().contains("UploadMultiSignerApplet") || req.getRequestURI().contains("theme") || req.getRequestURI().contains("log4j.xml") || req.getRequestURI().contains("PatternLayoutBeanInfo") || req.getRequestURI().contains("LayoutBeanInfo") || req.getRequestURI().contains(".class") || req.getRequestURI().contains(".properties")) {
            final Map<String, String[]> lMap = (Map<String, String[]>)req.getParameterMap();
            boolean first = true;
            String forward = "";
            for (final String lString : lMap.keySet()) {
                if (first) {
                    first = false;
                }
                else {
                    forward += "&";
                }
                forward = forward + lString + "=" + req.getParameter(lString);
            }
            req.getRequestDispatcher(req.getRequestURI().substring(req.getContextPath().length()) + "?" + forward).forward((ServletRequest)req, (ServletResponse)resp);
            return;
        }
        WSO2LoginFilter.mLogger.debug((Object)"Applet ricado qui");
//        if (req.getRequestURI().contains("rest") || (req.getRequestURI().contains("acs.jsp") && WSO2LoginFilter.class.getResourceAsStream("sessionExpired.jsp") != null)) {
       	if (req.getRequestURI().contains("rest")) {
            if (usernameSSO == null) {
            	mLogger.error("usernameSSO è null. JSESSIONID vale " + (session != null ? session.getId() : "null (session null)"));
                pServletResponse.setContentType("text/html");
                final PrintWriter out = pServletResponse.getWriter();
                out.print(IOUtils.toString(WSO2LoginFilter.class.getResourceAsStream("sessionExpired.jsp")));
                return;
            }
            if ((usernameSSO != null && loginBean == null) || (usernameSSO != null && !this.utenteLoggedIsSame(usernameSSO, loginBean))) {
                final SchemaSelector lSchemaSelector = (SchemaSelector)SpringAppContext.getContext().getBean("SchemaConfigurator");
                final Locale locale = new Locale("it", "IT");
                loginBean = new AurigaLoginBean();
                loginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
                loginBean.setSchema(lSchemaSelector.getSchemi().get(0).getName());
                
                final ApplicationConfigBean applicationConfigBean = (ApplicationConfigBean)SpringAppContext.getContext().getBean("ApplicationConfigurator");
                final String idApplicazione = applicationConfigBean.getIdApplicazione();
                try {
                    final DmpkLoginLoginBean lLoginInput = new DmpkLoginLoginBean();
                    lLoginInput.setUsernamein(usernameSSO);
                    lLoginInput.setFlgnoctrlpasswordin(Integer.valueOf(1));
                    lLoginInput.setFlgautocommitin(Integer.valueOf(1));
                    lLoginInput.setFlgrollbckfullin(Integer.valueOf(0));
                    
                    final DmpkLoginLogin lLogin = new DmpkLoginLogin();
                    final StoreResultBean<DmpkLoginLoginBean> lLoginOutput = (StoreResultBean<DmpkLoginLoginBean>)lLogin.execute(locale, loginBean, lLoginInput);
                    if (lLoginOutput.isInError()) {
                        throw new Exception(lLoginOutput.getDefaultMessage());
                    }
                    
                    final DmpkLoginLoginBean lLoginBean = (DmpkLoginLoginBean)lLoginOutput.getResultBean();
                    
                    try {
                    	logLoginTry(loginBean, null, usernameSSO, lLoginOutput.isInError(), lLoginBean.getFlgtpdominioautio(), lLoginBean.getIddominioautio(), lLoginBean.getCodidconnectiontokenout());
                    } catch (Exception e) {
                    	WSO2LoginFilter.mLogger.debug("Errore in logLoginTry", e);
					}
                    
                    loginBean.setIdApplicazione(idApplicazione);
                    loginBean.setUserid(usernameSSO);
                    String denominazione = StringUtils.isNotBlank((CharSequence)lLoginBean.getDesuserout()) ? lLoginBean.getDesuserout() : usernameSSO;
                    if (StringUtils.isNotBlank((CharSequence)lLoginBean.getDesdominioout())) {
                        denominazione = denominazione + "@" + lLoginBean.getDesdominioout();
                    }
                    loginBean.setDenominazione(denominazione);
                    loginBean.setToken(lLoginBean.getCodidconnectiontokenout());
                    loginBean.setIdUserLavoro((String)null);
                    loginBean.setSchema(loginBean.getSchema());
                    loginBean.setDominio(lLoginBean.getFlgtpdominioautio() + ":" + lLoginBean.getIddominioautio());
                    loginBean.setIdUser(lLoginBean.getIduserout());
                    final SpecializzazioneBean spec = new SpecializzazioneBean();
                    spec.setCodIdConnectionToken(lLoginBean.getCodidconnectiontokenout());
                    spec.setDesDominioOut(lLoginBean.getDesdominioout());
                    spec.setDesUserOut(lLoginBean.getDesuserout());
                    spec.setIdDominio(lLoginBean.getIddominioautio());
                    spec.setParametriConfigOut(lLoginBean.getParametriconfigout());
                    spec.setTipoDominio(lLoginBean.getFlgtpdominioautio());
                    try {
                        final DmpkIntMgoEmailGetidutentemgoemail lDmpkIntMgoEmailGetidutentemgoemail = new DmpkIntMgoEmailGetidutentemgoemail();
                        DmpkIntMgoEmailGetidutentemgoemailBean lDmpkIntMgoEmailGetidutentemgoemailBean = new DmpkIntMgoEmailGetidutentemgoemailBean();
                        lDmpkIntMgoEmailGetidutentemgoemailBean.setIduserin(loginBean.getIdUser());
                        final it.eng.auriga.database.store.bean.SchemaBean lSchemaBean = new it.eng.auriga.database.store.bean.SchemaBean();
                        lSchemaBean.setSchema(loginBean.getSchema());
                        lDmpkIntMgoEmailGetidutentemgoemailBean = (DmpkIntMgoEmailGetidutentemgoemailBean)lDmpkIntMgoEmailGetidutentemgoemail.execute(locale, lSchemaBean, lDmpkIntMgoEmailGetidutentemgoemailBean).getResultBean();
                        spec.setIdUtenteModPec(lDmpkIntMgoEmailGetidutentemgoemailBean.getIdutentepecout());
                    }
                    catch (Exception ex) {}
                    loginBean.setSpecializzazioneBean(spec);
                    loginBean.setUseridForPrefs(usernameSSO);
                    try {
                        final StringReader sr = new StringReader(loginBean.getSpecializzazioneBean().getParametriConfigOut());
                        final Lista lista = (Lista)SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal((Reader)sr);
                        final ParametriDBBean parametriDBBean = new ParametriDBBean();
                        final HashMap<String, String> parametriDB = new HashMap<String, String>();
                        if (lista != null) {
                            for (int i = 0; i < lista.getRiga().size(); ++i) {
                                final Vector<String> v = (Vector<String>)new XmlUtility().getValoriRiga((Lista.Riga)lista.getRiga().get(i));
                                if (v.get(0).equalsIgnoreCase("CF_UTENTE_LOGIN")) {
                                    loginBean.setCodFiscale((String)v.get(1));
                                }
                                else {
                                    parametriDB.put(v.get(0), v.get(1));
                                }
                            }
                        }
                        final Authentication auth = (Authentication)SpringAppContext.getContext().getBean("authentication");
                        if (auth != null && auth.getAuthType() != null) {
                            parametriDB.put("AUTHENTICATION_TYPE", auth.getAuthType().getValue());
                        }
                        parametriDBBean.setParametriDB((Map)parametriDB);
                        ParametriDBUtil.setParametriDB(session, parametriDBBean);
                    }
                    catch (Exception ex2) {
                    	mLogger.error(ex2);
                    }
                    AurigaUserUtil.setLoginInfo(session, loginBean);
                    String[] privilegi = null;
                    try {
                        privilegi = ServiceRestUserUtil.getPrivilegi().getPrivilegi(session);
                    }
                    catch (Exception e) {
                        WSO2LoginFilter.mLogger.error((Object)e.getMessage(), (Throwable)e);
                    }
                }
                catch (Exception e2) {
                    WSO2LoginFilter.mLogger.error((Object)e2.getMessage(), (Throwable)e2);
                }
            }
        }
        pFilterChain.doFilter(pServletRequest, pServletResponse);
    }
    
    private boolean utenteLoggedIsSame(final String usernameSSO, final AurigaLoginBean loginBean) {
        return loginBean.getUserid().equals(usernameSSO);
    }
    
    public String getUsernameSSO(final HttpSession session) {
        return (String) session.getAttribute("USERNAME_SSO");
    }
    
    public void init(final FilterConfig arg0) throws ServletException {
    }
    
    static {
        WSO2LoginFilter.mLogger = Logger.getLogger((Class)WSO2LoginFilter.class);
    }
    
    private void logLoginTry(AurigaLoginBean bean, String codApplicazione, String userid, boolean flagKO, Integer tipoDominioAut, BigDecimal idDominioAut,
			String codIdConnectionToken) {
		String parametriClient = "";
		try {
			logLoginTryHelperCall.call(bean, codApplicazione, userid, flagKO, tipoDominioAut, idDominioAut, codIdConnectionToken, parametriClient);
		} catch (Exception e) {
			final String msg = "La chiamata della function '" + LogLoginTryCallHelper.FUNCTION_NAME + "' è fallita.";
			mLogger.error(msg, e);
		}
	}
}