package it.eng.core.config;

import java.io.File;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import it.eng.core.annotation.Module;
import it.eng.core.service.bean.MetadataRegistry;
import it.eng.core.service.serialization.SerializerRepository;

/**
 * Classe di utilità che permette di inizializzare tutti i moduli installati
 * 
 * @author Russo
 *
 */
public class ConfigUtil {

	static Logger log = Logger.getLogger(ConfigUtil.class);

	// private static final ResourceBundle config = ResourceBundle.getBundle("config");
	private static PropertiesConfiguration config;
	private static String servicePackage = null;// default service package
	private static boolean isAuthenticationEnabled = false;
	private static String authenticationServiceName = "";
	private static String authenticationBeanName = "";
	private static Configuration mappaApplEnte;

	public static PropertiesConfiguration getConfig() {
		if (config == null) {
			initConfig();
		}
		return config;
	}

	public static String getServicePackage() {
		if (servicePackage == null) {
			initServicePackage();
		}
		return servicePackage;
	}

	/**
	 * lettura file di conf
	 * 
	 * @throws Exception
	 */
	public static void initConfig() {
		String dir = System.getProperty("iriscore.confdir");
		initConfig(dir);
	}

	public static void initConfig(String dir) {
		try {
			log.info("reading configuration ");

			if (dir == null)
				dir = System.getProperty("iriscore.confdir");

			if (dir != null && !dir.trim().equals("") && new File(dir).exists()) {
				try {
					String file = dir + File.separator + "config.properties";

					log.info("FILE DI CONFIGURAZIONE ESTERNO: " + file);

					config = new PropertiesConfiguration(file);
				} catch (Exception e) {
					config = new PropertiesConfiguration("config.properties");
				}
			} else {
				config = new PropertiesConfiguration("config.properties");
			}

		} catch (ConfigurationException e1) {
			log.fatal("fatal init congiguration..", e1);
			// throw new ServiceException(CoreConfig.modulename, "ERR_READING_CONF");
			throw new RuntimeException("ERR_READING_ CONFIG FILE");
		}
	}

	public static void initLocale() {
		Locale toSet = Locale.getDefault();
		String language = config.getString(ConfigKey.LANGUAGE_CODE, null);
		String country = config.getString(ConfigKey.LANGUAGE_COUNTRY, null);
		String variant = config.getString(ConfigKey.LANGUAGE_VARIANT, null);
		if (language != null) {
			toSet = new Locale(language, country, variant);
			log.info("change locale from " + Locale.getDefault() + " to " + toSet);
			Locale.setDefault(toSet);
		}
	}

	private static void initServicePackage() {
		log.info("reading service package");
		servicePackage = ConfigUtil.getConfig().getString(ConfigKey.SERVICE_PACKAGE, "it.eng");
	}

	private static void initAuth() {
		authenticationServiceName = ConfigUtil.getConfig().getString(ConfigKey.INTERNAL_SECURITY_SERVICE, "");
		authenticationBeanName = ConfigUtil.getConfig().getString(ConfigKey.INTERNAL_SECURITY_BEAN, "");
		isAuthenticationEnabled = new Boolean(ConfigUtil.getConfig().getString(ConfigKey.INTERNAL_AUTH_ENABLED, "false"));
	}

	private static void initMappaApplEnte() {
		mappaApplEnte = ConfigUtil.getConfig().subset(ConfigKey.APPLICAZIONE_ENTE_PREFIX);
	}

	public static boolean isAuthenticationEnabled() {
		return isAuthenticationEnabled;
	}

	public static String getAuthenticationServiceName() {
		return authenticationServiceName;
	}

	public static String getAuthenticationBeanName() {
		return authenticationBeanName;
	}

	public static Configuration getMappaApplEnte() {
		return mappaApplEnte;
	}

	public static void initialize() throws Exception {
		initialize(null);
	}

	public static String obtainConfigPath(ServletContext servletContext) {

		if (servletContext == null)
			return null;

		String pathToCheck = servletContext.getInitParameter("CONFIG_PATH");
		String envVarToCheck = servletContext.getInitParameter("CONFIG_ENV_VAR");
		String subDir = servletContext.getInitParameter("CONFIG_SUBDIR");

		log.info("CONFIG_PATH = " + pathToCheck);
		log.info("CONFIG_ENV_VAR = " + envVarToCheck);
		log.info("CONFIG_SUBDIR = " + subDir);

		String rootConfDir = null;
		if (envVarToCheck != null) {
			rootConfDir = System.getProperty(envVarToCheck);
			log.info("rootConfDir come System Property = " + rootConfDir);

			if (rootConfDir == null) {
				rootConfDir = System.getenv(envVarToCheck);
				log.info("rootConfDir come Env var = " + rootConfDir);
			}
		} else if (pathToCheck != null) {
			rootConfDir = pathToCheck;
			log.info("rootConfDir come path = " + rootConfDir);
		} else
			return null;

		if (rootConfDir == null)
			rootConfDir = "";
		if (!"".equals(rootConfDir) && !rootConfDir.endsWith(File.separator))
			rootConfDir += File.separator;
		if (subDir != null)
			rootConfDir += subDir;

		return rootConfDir;
	}

	/**
	 * Inizializza i moduli di configurazione installati
	 */
	public static void initialize(ServletContext servletContext) throws Exception {
		log.info("*******************************");
		log.info("    start Business Runtime ");

		String dir = obtainConfigPath(servletContext);

		initConfig(dir);
		initLocale();
		// legge i moduli e i servizi nel package indicato in configurazione
		initServicePackage();
		// inizializzo tutti i serializer
		SerializerRepository.initSerializerServer();

		initAuth();
		initMappaApplEnte();

		log.info("finding module in package " + getServicePackage());
		// Scanner scanner = new TypeAnnotationsScanner().filterResultsBy(new Predicate<String>() {
		//
		// @Override
		// public boolean apply(String input) {
		// if(input.equals(Module.class.getName())){
		// return true;
		// }else{
		// return false;
		// }
		//
		// }
		// });
		//
		// Reflections reflection = new Reflections(servicePackage,scanner);
		//
		// Set<Class<?>> irismodules = reflection.getTypesAnnotatedWith(Module.class);
		// Iterator<Class<?>> irismodulesIterator = irismodules.iterator();
		MetadataRegistry.getInstance();
		Iterator<Class<?>> irismodulesIterator = MetadataRegistry.getInstance().getModuli().iterator();

		while (irismodulesIterator.hasNext()) {

			Class<?> configclass = irismodulesIterator.next();
			Object obj;
			try {
				Module moduleAnn = configclass.getAnnotation(Module.class);
				log.info("init module:" + moduleAnn.name());
				obj = configclass.newInstance();
				if (obj instanceof IModuleConfigurator) {
					((IModuleConfigurator) obj).init();
				} else {
					log.warn("module " + moduleAnn.name() + " does not implement Interface ImoduleConfiguration");
				}
				log.info("inizializzazione " + moduleAnn.name() + " completata ");
			} catch (Exception e) {
				log.fatal("fatal initializing module " + configclass, e);
				// log.warn("Warning istanziazione configuratore!",e);
				// throw new RuntimeException("init module failed", e);
			}
		}
		log.info(" business runtime ready");
		log.info("*******************************");
	}
}