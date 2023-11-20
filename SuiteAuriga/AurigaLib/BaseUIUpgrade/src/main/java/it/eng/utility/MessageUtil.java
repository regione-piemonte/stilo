/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.utility.ui.module.core.client.i18n.defaults.Messages;
import it.eng.utility.ui.user.UserUtil;

public class MessageUtil {

	static Properties mProperties = loadProperties();
	static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();

	@Deprecated
	/**
	 * Utilizzare String getValue(String userLanguage, String key, String[]
	 * attributes)
	 */
	public static String getValue(String key, String[] attributes) {
		if (mProperties.getProperty(key) != null) {
			return MessageFormat.format(mProperties.getProperty(key), attributes);
		}
		return null;
	}

	@Deprecated
	/**
	 * Utilizzare String getValue(String userLanguage, String key)
	 */
	public static String getValue(String key) {
		if (mProperties.getProperty(key) != null) {
			return MessageFormat.format(mProperties.getProperty(key), new String[] {});
		}
		return null;
	}

	private static Properties loadProperties() {
		String language = UserUtil.getLocale().getLanguage();
		Properties lProperties = new Properties();
		try {
			if (StringUtils.isNotBlank(language) && !"it".equals(language)) {
				lProperties.load(Messages.class.getResourceAsStream("Messages_" + language + ".properties"));
			} else
				lProperties.load(Messages.class.getResourceAsStream("Messages.properties"));
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
		return getValue(userLanguage, null, key, attributes);
	}

	@Deprecated
	/**
	 * Utilizzare String getValue(String userLanguage, HttpSession session,
	 * String key)
	 */
	public static String getValue(String userLanguage, String key) {
		return getValue(userLanguage, null, key);
	}

	// session non è usato, ma viene messo nella firma per consentire
	// l'overriding da parte di Auriga
	public static String getValue(String userLanguage, HttpSession session, String key, String[] attributes) {
		// Il dominio potrebbe avere dei sottodomini, quindi devo gestire il
		// caso generale che a_b_c_....
		String userLocale = "";
		if ((userLanguage != null) && (!"".equalsIgnoreCase(userLanguage.trim()))) {
			userLocale = userLanguage + "_" + userLanguage.toUpperCase(Locale.ITALIAN);
		}
		// Cerco la property
		String propertyValue = getProperty(userLocale, key);
		try {
			return MessageFormat.format(propertyValue, attributes);
		} catch (Exception e) {
			return key;
		}
	}

	// session non è usato, ma viene messo nella firma per consentire
	// l'overriding da parte di Auriga
	public static String getValue(String userLanguage, HttpSession session, String key) {
		String userLocale = "";
		if ((userLanguage != null) && (!"".equalsIgnoreCase(userLanguage.trim()))) {
			userLocale = userLanguage + "_" + userLanguage.toUpperCase(Locale.ITALIAN);
		}
		// Cerco la property
		String propertyValue = getProperty(userLocale, key);
		try {
			return MessageFormat.format(propertyValue, new String[] {});
		} catch (Exception e) {
			return key;
		}
	}

	private static String getProperty(String userLocale, String key) {
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
			if (partialUserLocal.equalsIgnoreCase("")) {
				continuaCiclo = false;
			}
			// Ricerco la property
			String propertyValue = searchProperty(partialUserLocal, key);
			if ((propertyValue != null) && (!"".equalsIgnoreCase(propertyValue.trim()))) {
				// Se la property non è vuota la restituisco e termino
				return propertyValue;
			}
			if (!continuaCiclo) {
				// Se ero all'ultimo giro termino, non ho trovato la property
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
		return key;
	}

	private static String searchProperty(String userLocale, String key) {
		// Verifico se il file di property è già caricato nella mappa
		if (!propertiesMap.containsKey(userLocale)) {
			// Carico la property nella mappa
			Properties p = loadProperties(userLocale);
			propertiesMap.put(userLocale, p);
		}
		// Sono sicuro di avere caricato il file di property nella mappa
		String propertyValue = propertiesMap.get(userLocale).getProperty(key);
		return propertyValue;
	}

	private static Properties loadProperties(String userLanguage) {
		Properties lProperties = new Properties();
		try {
			if (StringUtils.isNotBlank(userLanguage) && !"it".equals(userLanguage)) {
				InputStream stream = Messages.class.getResourceAsStream("Messages_" + userLanguage + ".properties");
				if (stream != null) {
					lProperties.load(stream);
				}
			} else {
				lProperties.load(Messages.class.getResourceAsStream("Messages.properties"));
			}
		} catch (IOException e) {
			Logger logger = Logger.getLogger(MessageUtil.class);
			logger.error("Errore nella lettura del file di property", e);
		}
		return lProperties;
	}
}
