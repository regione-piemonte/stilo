/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.proxy.AbstractEncryptedProxyGrantingTicketStorageImpl;
import org.jasig.cas.client.proxy.CleanUpTimerTask;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorageImpl;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.ReflectUtils;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.ProxyList;
import org.jasig.cas.client.validation.ProxyListEditor;
import org.jasig.cas.client.validation.TicketValidator;

public class CASTicketValidator extends CASAbstractTicketValidation {

    private static final String[] RESERVED_INIT_PARAMS = new String[] {"proxyReceptorUrl", "acceptAnyProxy", "allowedProxyChains", "casServerUrlPrefix", "proxyCallbackUrl", "renew", "exceptionOnValidationFailure", "redirectAfterValidation", "useSession", "serverName", "service", "artifactParameterName", "serviceParameterName", "encodeServiceUrl", "millisBetweenCleanUps"};

    private static final int DEFAULT_MILLIS_BETWEEN_CLEANUPS = 60 * 1000;

    /**
     * The URL to send to the CAS server as the URL that will process proxying requests on the CAS client. 
     */
    private String proxyReceptorUrl;

    private Timer timer;

    private TimerTask timerTask;

    private int millisBetweenCleanUps;
    
    /**
     * Storage location of ProxyGrantingTickets and Proxy Ticket IOUs.
     */
    private ProxyGrantingTicketStorage proxyGrantingTicketStorage = new ProxyGrantingTicketStorageImpl(new Long(1800000));

    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
        setProxyReceptorUrl(getPropertyFromInitParams(filterConfig, "proxyReceptorUrl", null));

        final String proxyGrantingTicketStorageClass = getPropertyFromInitParams(filterConfig, "proxyGrantingTicketStorageClass", null);

        if (proxyGrantingTicketStorageClass != null) {
            this.proxyGrantingTicketStorage = ReflectUtils.newInstance(proxyGrantingTicketStorageClass);

            if (this.proxyGrantingTicketStorage instanceof AbstractEncryptedProxyGrantingTicketStorageImpl) {
                final AbstractEncryptedProxyGrantingTicketStorageImpl p = (AbstractEncryptedProxyGrantingTicketStorageImpl) this.proxyGrantingTicketStorage;
                final String cipherAlgorithm = getPropertyFromInitParams(filterConfig, "cipherAlgorithm", AbstractEncryptedProxyGrantingTicketStorageImpl.DEFAULT_ENCRYPTION_ALGORITHM);
                final String secretKey = getPropertyFromInitParams(filterConfig, "secretKey", null);

                p.setCipherAlgorithm(cipherAlgorithm);

                try {
                    if (secretKey != null) {
                        p.setSecretKey(secretKey);
                    }
                } catch (final Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        log.trace("Setting proxyReceptorUrl parameter: " + this.proxyReceptorUrl);
        this.millisBetweenCleanUps = Integer.parseInt(getPropertyFromInitParams(filterConfig, "millisBetweenCleanUps", Integer.toString(DEFAULT_MILLIS_BETWEEN_CLEANUPS)));
        super.initInternal(filterConfig);
    }

    public void init() {
        super.init();
        CommonUtils.assertNotNull(this.proxyGrantingTicketStorage, "proxyGrantingTicketStorage cannot be null.");

        if (this.timer == null) {
            this.timer = new Timer(true);
        }

        if (this.timerTask == null) {
            this.timerTask = new CleanUpTimerTask(this.proxyGrantingTicketStorage);
        }
        this.timer.schedule(this.timerTask, this.millisBetweenCleanUps, this.millisBetweenCleanUps);
    }

    /**
     * Constructs a Cas20ServiceTicketValidator or a Cas20ProxyTicketValidator based on supplied parameters.
     *
     * @param filterConfig the Filter Configuration object.
     * @return a fully constructed TicketValidator.
     */
    public final TicketValidator getTicketValidator(final FilterConfig filterConfig) {
        final String allowAnyProxy = getPropertyFromInitParams(filterConfig, "acceptAnyProxy", null);
        final String allowedProxyChains = getPropertyFromInitParams(filterConfig, "allowedProxyChains", null);
        final String casServerUrlPrefix = CASAuthenticationFilter.casServerUrlPrefix;//getPropertyFromInitParams(filterConfig, "casServerUrlPrefix", null);
        final Cas20ServiceTicketValidator validator;

        if (CommonUtils.isNotBlank(allowAnyProxy) || CommonUtils.isNotBlank(allowedProxyChains)) {
            final Cas20ProxyTicketValidator v = new Cas20ProxyTicketValidator(casServerUrlPrefix);
            v.setAcceptAnyProxy(parseBoolean(allowAnyProxy));
            v.setAllowedProxyChains(createProxyList(allowedProxyChains));
            validator = v;
        } else {
            validator = new Cas20ServiceTicketValidator(casServerUrlPrefix);
        }
        validator.setProxyCallbackUrl(getPropertyFromInitParams(filterConfig, "proxyCallbackUrl", null));
        validator.setProxyGrantingTicketStorage(proxyGrantingTicketStorage);
//        validator.setProxyRetriever(new Cas20ProxyRetriever(casServerUrlPrefix));
        validator.setRenew(parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));

        final Map additionalParameters = new HashMap();
        final List params = Arrays.asList(RESERVED_INIT_PARAMS);

        for (final Enumeration e = filterConfig.getInitParameterNames(); e.hasMoreElements();) {
            final String s = (String) e.nextElement();

            if (!params.contains(s)) {
                additionalParameters.put(s, filterConfig.getInitParameter(s));
            }
        }

        validator.setCustomParameters(additionalParameters);

        return validator;
    }

    protected final ProxyList createProxyList(final String proxies) {
        if (CommonUtils.isBlank(proxies)) {
            return new ProxyList();
        }

        final ProxyListEditor editor = new ProxyListEditor();
        editor.setAsText(proxies);
        return (ProxyList) editor.getValue();
     }

    public void destroy() {
        super.destroy();
        this.timer.cancel();
    }

    /**
     * This processes the ProxyReceptor request before the ticket validation code executes.
     */
    protected final boolean preFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String requestUri = request.getRequestURI();

        if (CommonUtils.isEmpty(this.proxyReceptorUrl) || !requestUri.endsWith(this.proxyReceptorUrl)) {
            return true;
        }
       
        CommonUtils.readAndRespondToProxyReceptorRequest(request, response, proxyGrantingTicketStorage);

        return false;
    }

    public final void setProxyReceptorUrl(final String proxyReceptorUrl) {
        this.proxyReceptorUrl = proxyReceptorUrl;
    }

    public void setProxyGrantingTicketStorage(final ProxyGrantingTicketStorage storage) {
        proxyGrantingTicketStorage = storage;
    }

    public void setTimer(final Timer timer) {
        this.timer = timer;
    }

    public void setTimerTask(final TimerTask timerTask) {
        this.timerTask = timerTask;
    }

    public void setMillisBetweenCleanUps(final int millisBetweenCleanUps) {
        this.millisBetweenCleanUps = millisBetweenCleanUps;
    }
    
    private static String getClientIp(HttpServletRequest request, String nomeFile) throws IOException {
    	
    	InputStream is = null;
    	try {
    	
	    	Properties prop = new Properties();
	    	String webappBase = request.getRealPath(File.separator + "WEB-INF" + File.separator + nomeFile.replaceAll("file:", ""));
	    	is = new FileInputStream(webappBase);
	    	
	    	prop.load(is);
	    	String remoteAddr = prop.getProperty(request.getServerName());        	
	   
	        return remoteAddr;
    	} finally {
			if(is != null) {
				try {
					is.close();
				} catch (Exception e) {}
			}
		}
    }
}