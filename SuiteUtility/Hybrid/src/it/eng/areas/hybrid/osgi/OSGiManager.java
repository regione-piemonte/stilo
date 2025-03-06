package it.eng.areas.hybrid.osgi;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceReference;

import it.eng.areas.hybrid.module.IClientModuleManager;

public class OSGiManager {

	Logger logger = Logger.getLogger(OSGiManager.class);

	public static final int LOG_ERROR = 1;
	public static final int LOG_WARNING = 2;
	public static final int LOG_INFO = 3;
	public static final int LOG_DEBUG = 4;

	protected static OSGiManager instance;

	protected Felix framework;
	protected IClientModuleManager moduleManager;

	public static OSGiManager start(File workfolder, IClientModuleManager moduleManager) throws Exception {
		instance = new OSGiManager();
		instance.init(workfolder, moduleManager);

		return instance;
	}

	public static OSGiManager getInstance() {
		return instance;
	}

	protected OSGiManager() {

	}

	public IClientModuleManager getClientModuleManager() {
		return this.moduleManager;
	}

	protected void init(File workfolder, IClientModuleManager moduleManager) throws Exception {
		this.moduleManager = moduleManager;
		HashMap<String, Object> mapConfig = new HashMap<String, Object>();

		Properties props = new Properties();
		props.load(OSGiManager.class.getResourceAsStream("framework.properties"));
		for (Object key : props.keySet()) {
			mapConfig.put(key.toString(), (String) props.get(key));
		}
		mapConfig.put("org.osgi.framework.storage", workfolder + "/osgi-cache");

		// map.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP,
		// Arrays.asList(provisionActivator));
		mapConfig.put(FelixConstants.LOG_LOGGER_PROP, new org.apache.felix.framework.Logger() {

			@SuppressWarnings("rawtypes")
			@Override
			protected void doLog(Bundle bundle, ServiceReference sr, int level, String msg, Throwable throwable) {
				OSGiManager.this.log(level, msg, throwable);
			}

		});

		// Registro l'attivatore per il bundle host
		mapConfig.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP, Arrays.asList(new HostActivator(moduleManager)));

		framework = new Felix(mapConfig);
		framework.start();
		framework.getBundleContext().addBundleListener(new BundleListener() {

			@Override
			public void bundleChanged(BundleEvent bundleEvent) {
				String message = bundleEvent.getSource() + " " + OSGiUtils.bundleTypeToString(bundleEvent);
				log(LOG_DEBUG, message, null);
			}
		});

		// Avvio i jar delle dipendenze come bundle per renderlo disponibile a tutti
		logger.debug("Inizio a startare i bundle");
		
		try {
			startBundle("log4j-1.2.17.jar", OSGiManager.class.getResourceAsStream("bundles/log4j-1.2.17.jar"));
	
			startBundle("activation-1.1.jar", OSGiManager.class.getResourceAsStream("bundles/activation-1.1.jar"));
			startBundle("mail-1.4.jar", OSGiManager.class.getResourceAsStream("bundles/mail-1.4.jar"));
	
			startBundle("commons-lang-2.6.jar", OSGiManager.class.getResourceAsStream("bundles/commons-lang-2.6.jar"));
			startBundle("commons-io-2.3.jar", OSGiManager.class.getResourceAsStream("bundles/commons-io-2.3.jar"));
			startBundle("commons-logging-1.2.jar", OSGiManager.class.getResourceAsStream("bundles/commons-logging-1.2.jar"));
			startBundle("commons-configuration-1.9.jar", OSGiManager.class.getResourceAsStream("bundles/commons-configuration-1.9.jar"));
			startBundle("commons-codec-1.6.jar", OSGiManager.class.getResourceAsStream("bundles/commons-codec-1.6.jar"));
			startBundle("commons-beanutils-core-1.8.3.jar", OSGiManager.class.getResourceAsStream("bundles/commons-beanutils-core-1.8.3.jar"));
			startBundle("org.osgi.compendium-4.2.0.jar", OSGiManager.class.getResourceAsStream("bundles/org.osgi.compendium-4.2.0.jar"));
			startBundle("httpcore-osgi-4.3.2.jar", OSGiManager.class.getResourceAsStream("bundles/httpcore-osgi-4.3.2.jar"));
			startBundle("httpclient-osgi-4.3.4.jar", OSGiManager.class.getResourceAsStream("bundles/httpclient-osgi-4.3.4.jar"));
			startBundle("httpmime-4.3.4.jar", OSGiManager.class.getResourceAsStream("bundles/httpmime-4.3.4.jar"));
			startBundle("bcprov-jdk15on-1.53.jar", OSGiManager.class.getResourceAsStream("bundles/bcprov-jdk15on-1.53.jar"));
			startBundle("bcpkix-jdk15on-1.53.jar", OSGiManager.class.getResourceAsStream("bundles/bcpkix-jdk15on-1.53.jar"));
			startBundle("bcmail-jdk15on-1.53.jar", OSGiManager.class.getResourceAsStream("bundles/bcmail-jdk15on-1.53.jar"));
			startBundle("itext_pdf_pdfa-5.4.0.jar", OSGiManager.class.getResourceAsStream("bundles/itext_pdf_pdfa-5.4.0.jar"));
			
			startBundle("xml-apis-1.3.04.jar", OSGiManager.class.getResourceAsStream("bundles/xml-apis-1.3.04.jar"));
			startBundle("batik-all-1.6.jar", OSGiManager.class.getResourceAsStream("bundles/batik-all-1.6.jar"));
			
			startBundle("xmlgraphics-commons-1.3.1.jar", OSGiManager.class.getResourceAsStream("bundles/xmlgraphics-commons-1.3.1.jar"));
			startBundle("avalon-framework-4.2.0b.jar", OSGiManager.class.getResourceAsStream("bundles/avalon-framework-4.2.0b.jar"));
			
			startBundle("servlet-api-2.5.jar", OSGiManager.class.getResourceAsStream("bundles/servlet-api-2.5.jar"));
			
			startBundle("fontbox-2.0.1.jar", OSGiManager.class.getResourceAsStream("bundles/fontbox-2.0.1.jar"));
			startBundle("jempbox-1.8.6.jar", OSGiManager.class.getResourceAsStream("bundles/jempbox-1.8.6.jar"));
			startBundle("pdfbox-2.0.1.jar", OSGiManager.class.getResourceAsStream("bundles/pdfbox-2.0.1.jar"));
	
			startBundle("sunpkcs11.jar", OSGiManager.class.getResourceAsStream("bundles/sunpkcs11.jar"));
			startBundle("proxy-vole-1.0.jar", OSGiManager.class.getResourceAsStream("bundles/proxy-vole-1.0.jar"));
			startBundle("ProxySelector-1.0.4-SNAPSHOT.jar", OSGiManager.class.getResourceAsStream("bundles/ProxySelector-1.0.4-SNAPSHOT.jar"));
			startBundle("clientFileoperationWS-1.0.1-SNAPSHOT.jar", OSGiManager.class.getResourceAsStream("bundles/clientFileoperationWS-1.0.1-SNAPSHOT.jar"));
			startBundle("jai_codec-1.1.3.jar", OSGiManager.class.getResourceAsStream("bundles/jai_codec-1.1.3.jar"));
			startBundle("jai_core-1.1.3.jar", OSGiManager.class.getResourceAsStream("bundles/jai_core-1.1.3.jar"));
			startBundle("http-1.0.jar", OSGiManager.class.getResourceAsStream("bundles/http-1.0.jar"));
	
			startBundle("jcommon-1.0.21.jar", OSGiManager.class.getResourceAsStream("bundles/jcommon-1.0.21.jar"));
			startBundle("jfreechart-1.0.17.jar", OSGiManager.class.getResourceAsStream("bundles/jfreechart-1.0.17.jar"));
			
			startBundle("org.apache.servicemix.bundles.commons-collections-3.2.1_3", OSGiManager.class.getResourceAsStream("bundles/org.apache.servicemix.bundles.commons-collections-3.2.1_3.jar"));
			startBundle("org.apache.servicemix.bundles.commons-beanutils-1.8.3_2", OSGiManager.class.getResourceAsStream("bundles/org.apache.servicemix.bundles.commons-beanutils-1.8.3_2.jar"));
			
		} catch (Throwable t) {
			logger.error("Errore nello start dei bundle", t);
			System.exit(0);
		}
	
		logger.debug("Terminato di startare i bundle");

	}

	public Bundle installBundle(String location, InputStream input) throws Exception {
		Bundle bundle = framework.getBundleContext().installBundle(location, input);
		return bundle;
	}

	public Bundle startBundle(String location, InputStream input) throws Exception {
		Bundle bundle = installBundle(location, input);
		bundle.start();
		return bundle;
	}

	protected void log(int level, String message, Throwable cause) {
		switch (level) {
		case LOG_DEBUG:
			logger.debug(message, cause);
			break;
		case LOG_INFO:
			logger.info(message, cause);
			break;
		case LOG_WARNING:
			logger.warn(message, cause);
			break;
		case LOG_ERROR:
			logger.error(message, cause);
			break;
		default:
			logger.error(message, cause);
			break;
		}
	}
	
	public void stop(){
		try {
			framework.stop();
		} catch (BundleException e) {
			logger.error(e);
		}
	}

}
