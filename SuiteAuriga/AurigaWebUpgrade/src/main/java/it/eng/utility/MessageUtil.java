/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.ui.module.layout.client.i18n.defaults.Messages;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;

public class MessageUtil {

	static Properties mProperties = loadProperties();
	static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();
	static GenericConfigBean genericConfig = null;

	private static Logger mLogger = Logger.getLogger(MessageUtil.class);

	@Deprecated
	/**
	 * Utilizzare String getValue(String userLanguage, String key, String[]
	 * attributes)
	 */
	public static String getValue(String key, String[] attributes) {
		if (mProperties.getProperty(key) != null) {
			return MessageFormat.format(mProperties.getProperty(key), attributes);
		}
		return key;
	}

	@Deprecated
	/**
	 * Utilizzare String getValue(String userLanguage, String key)
	 */
	public static String getValue(String key) {
		// mLogger.debug("Chiamata a getValue deprecato, con key " + key);
		if (mProperties.getProperty(key) != null) {
			return MessageFormat.format(mProperties.getProperty(key), new String[] {});
		}
		return key;
	}

	private static Properties loadProperties() {
		// mLogger.debug("Chiamata a loadProperties vecchia versione");
		String language = UserUtil.getLocale().getLanguage();
		Properties lProperties = new Properties();
		try {
			if (StringUtils.isNotBlank(language) && !"it".equals(language)) {
				lProperties.load(Messages.class.getResourceAsStream("Messages_" + language + ".properties"));
				// mLogger.debug("loadProperties vecchia versione, carico file
				// property Messages_" + language + ".properties");
			} else
				lProperties.load(Messages.class.getResourceAsStream("Messages.properties"));
			// mLogger.debug("loadProperties vecchia versione, carico file
			// property Messages.properties");
		} catch (IOException e) {

		}
		return lProperties;
	}

	/*******************************************************************
	 ******************** NUOVA GESTIONE GETVALUE **********************
	 *******************************************************************/

	@Deprecated
	/**
	 * Utilizzare String getValue(String userLanguage, HttpSession session,
	 * String key, String[] attributes)
	 */
	public static String getValue(String userLanguage, String key, String[] attributes) {
		mLogger.debug("Richiesto geValue senza sessione per key " + key + " e userLanguage " + userLanguage);
		return getValue(userLanguage, null, key, attributes);
	}

	@Deprecated
	/**
	 * Utilizzare String getValue(String userLanguage, HttpSession session,
	 * String key)
	 */
	public static String getValue(String userLanguage, String key) {
		mLogger.debug("Richiesto geValue senza sessione per key " + key + " e userLanguage " + userLanguage);
		return getValue(userLanguage, null, key);
	}

	public static String getValue(String userLanguage, HttpSession session, String key, String[] attributes) {
		mLogger.debug("Richiesto geValue per key " + key + " e userLanguage " + userLanguage);
		String propertyValue = getValueCommon(userLanguage, session, key);

		try {
			return MessageFormat.format(propertyValue, attributes);
		} catch (Exception e) {
			return key;
		}
	}

	public static String getValue(String userLanguage, HttpSession session, String key) {

		mLogger.debug("Richiesto geValue per key " + key + " e userLanguage " + userLanguage);

		try {
			String propertyValue = getValueCommon(userLanguage, session, key);
			return MessageFormat.format(propertyValue, new String[] {});
		} catch (Exception e) {
			return key;
		}
	}

	private static String getValueCommon(String userLanguage, HttpSession session, String key) {
		// Estraggo la stringa di specializzazione dell'interfaccia grafica
		String dominioSpecGUI = "";
		// IMPORTANTE: bisogna recuperare l'applicationName direttamente dalle
		// configurazioni, solo se qui non è settato provo a recuperarmelo dalla
		// sessione
		String applicationName = getGenericConfigBean().getApplicationName();
		mLogger.debug("applicationName letta da SpringAppContext: " + applicationName);
		if (session != null) {
			mLogger.debug("sessione è diverso da null");
			try {
				if (StringUtils.isBlank(applicationName)) {
					applicationName = session != null ? (String) session.getAttribute("APPLICATION_NAME") : null;
					mLogger.debug("applicationName letta da sessione: " + applicationName);
				}
				dominioSpecGUI = ParametriDBUtil.getParametroDB(session, "SPEC_LABEL_GUI");
			} catch (NullPointerException e) {
				mLogger.debug("Errore durante la lettura del parametro SPEC_LABEL_GUI, restituisco stringa vuota");
				dominioSpecGUI = "";
			}
		}
		// Forzo applicationName e dominioSpecGUI a "" nel caso fossero a null
		applicationName = applicationName == null ? "" : applicationName;
		dominioSpecGUI = dominioSpecGUI == null ? "" : dominioSpecGUI;
		mLogger.debug(
				"Chiamo getUserLocale con applicationName: " + applicationName + " e dominioSpecGui:" + dominioSpecGUI);
		// Ottengo la locale completa, che comprende lingua utente e dominio di
		// specializzione
		String userLocale = UserUtil.getUserLocaleFromUserLanguageAndDominioSpecGUI(userLanguage, dominioSpecGUI);
		// Cerco la property
		mLogger.debug("Cerco la property " + key + " con userLocale " + userLocale);
		String propertyValue = getProperty(userLocale, key, applicationName);
		mLogger.debug("La property con key " + key + " ha valore " + propertyValue);
		return propertyValue;
	}

	private static String getProperty(String userLocale, String key, String applicationName) {
		String partialUserLocal = userLocale;
		// Con questo ciclo cerco nelle property in modo gerarchico fino a
		// quando non trovo il valore,
		// ad esempio
		// Iterazione 1: File Property A_B_C
		// Iterazione 2: File Property A_B
		// Iterazione 3: File Property A
		// Iterazione 4: File property di default ("")
		boolean continuaCiclo = true;
		// Ad ogni iterazione tolgo la sottostringa dopo l'ultimo "_", così
		// risalgo la gerarchia delle property
		while (continuaCiclo) {
			// Se la stringa è "" sono all'ultimo giro
			mLogger.debug("Nuova iterazione di searchProperty con partialUserLocal " + partialUserLocal);
			if (partialUserLocal.equalsIgnoreCase("")) {
				mLogger.debug("Questa è l'ultima iterazione per trovare la property");
				continuaCiclo = false;
			}
			// Ricerco la property
			mLogger.debug("Invoco searchProperty con partialUserLocal " + partialUserLocal + ", key " + key
					+ " e applicationName " + applicationName);
			String propertyValue = searchProperty(partialUserLocal, key, applicationName);
			if ((propertyValue != null) && (!"".equalsIgnoreCase(propertyValue.trim()))) {
				// Se la property non è vuota la restituisco e termino
				mLogger.debug("getProperty termina e resituisce il valore " + propertyValue);
				return propertyValue;
			}
			if (!continuaCiclo) {
				// Se ero all'ultimo giro termino, non ho trovato la property
				mLogger.debug("Non no trovato la key: " + key + " in nessun file di propery analizzato");
				break;
			} else {
				// Continuo a risalire la gerarchia delle property
				int pos = partialUserLocal.lastIndexOf("_");
				if (pos != -1) {
					// Ho un livello supertiore (ad esempio A_B_C -> A_B)
					partialUserLocal = partialUserLocal.substring(0, pos);
				} else {
					// Non ho sovralivelli, cerco nel default
					partialUserLocal = "";
				}
			}
		}
		// La property non esiste in nessun file di property, restituisco la
		// chiave
		mLogger.debug("searchProperty non ha trovato nulla, restituisco " + key);
		return key;
	}

	private static String searchProperty(String userLocale, String key, String applicationName) {
		// Verifico se il file di property è già caricato nella mappa
		String propName = null;
		if (applicationName != null && !"".equals(applicationName)) {
			propName = applicationName + "_" + userLocale;
		} else {
			propName = userLocale;
		}
		mLogger.debug("propName vale " + propName);
		// Durante il caricamento inziale dei filtri l'applicationName non è
		// valorizzato, e vengono caricate le properties del solo modulo Auriga.
		// Aggiungo quindi l'applicationName nel nome della chiave nella mappa,
		// in modo da differenziarle da quelle caricate prima.
		// Ad esempio in questo modo le properties it_IT caricate senza
		// applicationName non vanno ad influire il corretto caricamento delle
		// properties
		// it_IT una volta che l'applicationName è valorizzato
		if (!propertiesMap.containsKey(propName)) {
			// Carico la property nella mappa
			mLogger.debug("La mappa non contiene la property " + propName + ", la carico");
			Properties p = loadProperties(userLocale, applicationName);
			propertiesMap.put(propName, p);
		} else {
			mLogger.debug(
					"La mappa contiene la property " + userLocale + " con size " + propertiesMap.get(propName).size());
		}
		// Sono sicuro di avere caricato il file di property nella mappa
		mLogger.debug("Chiamo propertiesMap.get con userLocale " + userLocale + " e key " + key);
		String propertyValue = propertiesMap.get(propName).getProperty(key.trim());
		mLogger.debug("propertiesMap.get restituisce " + propertyValue);
		return propertyValue;
	}

	private static Properties loadProperties(String userLanguage, String applicationName) {
		Properties lProperties = new Properties();

		// Devo caricare i Messages esatti in base al modulo in cui mi trovo
		// AurigaWeb -> Carico solo Messages di AurigaWeb
		List<Class> messagesClassList;
		// Ottengo la lista delle classi Message da caricare
		mLogger.debug("Ottengo la lista delle classi Message da caricare. userLanguage: " + userLanguage
				+ " applicationName" + applicationName);
		messagesClassList = getMessagesClass(applicationName);
		// Carico le properties associate all'userLanguage di tutte le classi
		// Messages
		Properties properties = new Properties();
		for (Class messagesClass : messagesClassList) {
			Properties tmpProperties = new Properties();
			String nomeFile = "";
			// Se userLanguage è uguale a it, allora uso la properties di default
			if (StringUtils.isNotBlank(userLanguage) && !"it".equals(userLanguage)) {
				nomeFile = "Messages_" + userLanguage + ".properties";
			} else {
				nomeFile = "Messages.properties";
			}
			InputStream stream = messagesClass.getResourceAsStream(nomeFile);
			if (stream != null) {
				try {
					// Leggo il file fdi properties
					tmpProperties.load(stream);
					mLogger.debug("Ho caricato in lProperties lo stream del file " + nomeFile);
				} catch (IOException e) {
					mLogger.debug("Errore nella lettura del file di property " + nomeFile, e);
				}
			}
			// Faccio il marge tra i contenuti dei file di properties con lo
			// stesso nome ma
			// collegati a classsi diverse
			properties.putAll(tmpProperties);
		}
		return properties;
	}

	private static List<Class> getMessagesClass(String applicationName) {
		// In base all'application name ricavo il path della classe Messages
		// corretta
		List<Class> messagesClassList = new ArrayList<Class>();
		mLogger.debug("Selezioni le classi Message da caricare, l'applicationName è " + applicationName);		
		// Carico le classi, la classe Messages di AurigaWeb viene sempre
		// caricata
		String classPathAurigaMessage = "it.eng.auriga.ui.module.layout.client.i18n.defaults.Messages";
		try {
			messagesClassList.add(Class.forName(classPathAurigaMessage));
		} catch (ClassNotFoundException e) {
			mLogger.error("Errore nel caricamento della classe con path:" + classPathAurigaMessage, e);
			messagesClassList.add(Messages.class);
		}
		// Restituisco la lista delle classi Messages
		return messagesClassList;
	}
	
	private static GenericConfigBean getGenericConfigBean() {
		if (genericConfig != null) {
			return genericConfig;
		} else {
			genericConfig = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
			return genericConfig;
		}
	}

}
