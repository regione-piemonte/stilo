/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;

import java.io.File;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LuceneHandler {

	// proprieta' del logger
	private String loggerPrefix = "versionhandler";

	private static final String loggerRoot = "lucenehandler";

	private String defaultRepositoryRoot = null;

	private String destination = null;

	private String defaultContextRoot = null;

	// properies come lette dal file di configurazione
	private java.util.Properties handlerProperties = null;

	// costanti per i nomi delle properties
	private static final String _OVERFLOW_LIMIT = "OVERFLOW_LIMIT";

	// valore di default dell'overflow limit
	private static final int _DEFAULT_OVERFLOW_LIMIT = 3000;

	// overflow limit
	private int overflowLimit = _DEFAULT_OVERFLOW_LIMIT;

	private static Logger log = Logger.getLogger(LuceneHandler.class);

	/**
	 * Costruttore di default
	 * 
	 * @throws ObjectHandlerException
	 */
	public LuceneHandler() throws LuceneHandlerException {

		// // ricavo il nome della classe
		// String nomeClasse = this.getClass().getName();
		// Logger aLogger = getLogger(null);
		//
		// try {
		//
		// // ingresso nel metodo di configurazione
		// aLogger.info("Inizio configurazione " + nomeClasse);
		//
		// // ricavo il nome del file di properties
		// nomeClasse = nomeClasse.replaceAll("\\.", "/") + ".properties";
		//
		// Properties props = new Properties();
		// try {
		// // Cerchiamo un file chiamato <classname>.properties
		// InputStream fis =
		// this.getClass().getClassLoader().getResourceAsStream(nomeClasse);
		// if (fis != null)
		// {
		// // carico da file .properties
		// props.load( fis );
		//
		// // attribuisco le properties. Qui sarebbe meglio clonarle... TODO
		// handlerProperties = props;
		//
		// String overflowLimitStr =
		// handlerProperties.getProperty(_OVERFLOW_LIMIT, (new
		// Integer(_DEFAULT_OVERFLOW_LIMIT)).toString());
		// overflowLimit = (new Integer(overflowLimitStr)).intValue();
		//
		// // configurazione OK
		// aLogger.debug("Configurazione " + nomeClasse + " effettuata.");
		// }
		// else
		// {
		// // configurazione KO
		// aLogger.error("File " + nomeClasse + " non trovato nel classpath!");
		// }
		// } catch (Exception ex)
		// {
		// // configurazione KO
		// handlerProperties = null;
		// aLogger.error("Errore durante la lettura del file di properties("+
		// nomeClasse + ") : " + ex.getMessage());
		// throw new LuceneHandlerException
		// ("Errore in fase di check della configurazione:" + ex.getMessage());
		// }
		// }
		// finally{
		// // uscita dal metodo di configurazione
		// aLogger.info("Fine configurazione " + nomeClasse);
		// }
	}

	public void setHandlerProperties(java.util.Properties handlerProperties) {
		this.handlerProperties = handlerProperties;
	}

	public Properties getHandlerProperties() {
		return handlerProperties;
	}

	// ****************************************************************************************
	// //
	// metodi pubblici
	// ****************************************************************************************
	// //

	/**
	 * Restituisce il risultato della ricerca effettuata su Lucene (in formato LISTA_STD) E' solo un wrapper per il version handler della
	 * chiamata all'api del LuceneHelper
	 * 
	 * @param idDominio
	 * @param fieldName
	 * @param searchValue
	 * @param typeSearch
	 * @param filterObjects
	 * @return
	 * @throws LuceneException
	 * @throws LuceneHandlerException
	 */
	public String searchFullTextRepository(String idDominio, String dbName, String objectType, String[] fieldName, String searchValue,
			SearchType typeSearch, String filterObjects, String filterObjType, String privacyField, LuceneParameterFilter parFilter,
			AurigaLoginBean login) throws LuceneException, LuceneHandlerException {

		String path = getPathFromDominio(objectType, idDominio, dbName);

		String overflowLimitStr = handlerProperties.getProperty(_OVERFLOW_LIMIT, (new Integer(_DEFAULT_OVERFLOW_LIMIT)).toString());
		overflowLimit = (new Integer(overflowLimitStr)).intValue();

		return LuceneHelper.searchFullText(SearchArea.SEARCH_TYPE_REPOSITORY_DOC_FOLDER, path, fieldName, searchValue, typeSearch,
				overflowLimit, filterObjects, filterObjType, privacyField, parFilter, login, null);

	}

	/**
	 * Restituisce il risultato della ricerca effettuata su Lucene (in formato LISTA_STD) E' solo un wrapper per il version handler della
	 * chiamata all'api del LuceneHelper
	 * 
	 * @param idDominio
	 * @param fieldName
	 * @param searchValue
	 * @param typeSearch
	 * @param filterObjects
	 * @param customFilter
	 * @return
	 * @throws LuceneException
	 * @throws LuceneHandlerException
	 */
	public String searchFullTextRepository(String idDominio, String dbName, String objectType, String[] fieldName, String searchValue,
			SearchType typeSearch, String filterObjects, String[] filterObjType, HashMap customFilter, String privacyField,
			LuceneParameterFilter parFilter, AurigaLoginBean login) throws LuceneException, LuceneHandlerException {

		String path = getPathFromDominio(objectType, idDominio, dbName);

		String overflowLimitStr = handlerProperties.getProperty(_OVERFLOW_LIMIT, (new Integer(_DEFAULT_OVERFLOW_LIMIT)).toString());
		overflowLimit = (new Integer(overflowLimitStr)).intValue();

		return LuceneHelper.searchFullText(SearchArea.SEARCH_TYPE_REPOSITORY_DOC_FOLDER, path, fieldName, searchValue, typeSearch,
				overflowLimit, filterObjects, filterObjType, customFilter, privacyField, parFilter, login, null);

	}

	/**
	 * Restituisce il risultato della ricerca effettuata su Lucene (in formato LISTA_STD) E' solo un wrapper per il version handler della
	 * chiamata all'api del LuceneHelper
	 * 
	 * @param idDominio
	 * @param fieldName
	 * @param searchValue
	 * @param typeSearch
	 * @param filterObjects
	 * @return
	 * @throws LuceneException
	 * @throws LuceneHandlerException
	 */
	public String searchFullTextUnitaOrg(String idDominio, String dbName, Connection conn, String objectType, String[] fieldName,
			String searchValue, SearchType typeSearch, String filterObjects, String filterObjType, String privacyField,
			LuceneParameterFilter parFilter, AurigaLoginBean login) throws LuceneException, LuceneHandlerException {

		String path = getPathFromDominio(objectType, idDominio, dbName);

		String overflowLimitStr = handlerProperties.getProperty(_OVERFLOW_LIMIT, (new Integer(_DEFAULT_OVERFLOW_LIMIT)).toString());
		overflowLimit = (new Integer(overflowLimitStr)).intValue();

		return LuceneHelper.searchFullText(SearchArea.SEARCH_TYPE_UO, path, fieldName, searchValue, typeSearch, overflowLimit,
				filterObjects, filterObjType, privacyField, parFilter, login, conn);

	}

	/**
	 * Restituisce il risultato della ricerca effettuata su Lucene (in formato LISTA_STD) E' solo un wrapper per il version handler della
	 * chiamata all'api del LuceneHelper
	 * 
	 * @param idDominio
	 * @param fieldName
	 * @param searchValue
	 * @param typeSearch
	 * @param filterObjects
	 * @param customFilter
	 * @return
	 * @throws LuceneException
	 * @throws LuceneHandlerException
	 */
	public String searchFullTextUnitaOrg(String idDominio, String dbName, Connection conn, String objectType, String[] fieldName,
			String searchValue, SearchType typeSearch, String filterObjects, String[] filterObjType, HashMap customFilter,
			String privacyField, LuceneParameterFilter parFilter, AurigaLoginBean login) throws LuceneException, LuceneHandlerException {

		String path = getPathFromDominio(objectType, idDominio, dbName);

		String overflowLimitStr = handlerProperties.getProperty(_OVERFLOW_LIMIT, (new Integer(_DEFAULT_OVERFLOW_LIMIT)).toString());
		overflowLimit = (new Integer(overflowLimitStr)).intValue();

		return LuceneHelper.searchFullText(SearchArea.SEARCH_TYPE_UO, path, fieldName, searchValue, typeSearch, overflowLimit,
				filterObjects, filterObjType, customFilter, privacyField, parFilter, login, conn);

	}

	/**
	 * Restituisce il risultato della ricerca effettuata su Lucene (in formato LISTA_STD) E' solo un wrapper per il version handler della
	 * chiamata all'api del LuceneHelper
	 * 
	 * @param idDominio
	 * @param fieldName
	 * @param searchValue
	 * @param typeSearch
	 * @param filterObjects
	 * @return
	 * @throws LuceneException
	 * @throws LuceneHandlerException
	 */
	public String searchFullTextTitolario(String idDominio, String dbName, Connection conn, String objectType, String[] fieldName,
			String searchValue, SearchType typeSearch, String filterObjects, String filterObjType, String privacyField,
			LuceneParameterFilter parFilter, AurigaLoginBean login) throws LuceneException, LuceneHandlerException {

		String path = getPathFromDominio(objectType, idDominio, dbName);

		String overflowLimitStr = handlerProperties.getProperty(_OVERFLOW_LIMIT, (new Integer(_DEFAULT_OVERFLOW_LIMIT)).toString());
		overflowLimit = (new Integer(overflowLimitStr)).intValue();

		return LuceneHelper.searchFullText(SearchArea.SEARCH_TYPE_TITOLARIO, path, fieldName, searchValue, typeSearch, overflowLimit,
				filterObjects, filterObjType, privacyField, parFilter, login, conn);

	}

	/**
	 * Restituisce il risultato della ricerca effettuata su Lucene (in formato LISTA_STD) E' solo un wrapper per il version handler della
	 * chiamata all'api del LuceneHelper
	 * 
	 * @param idDominio
	 * @param fieldName
	 * @param searchValue
	 * @param typeSearch
	 * @param filterObjects
	 * @param customFilter
	 * @return
	 * @throws LuceneException
	 * @throws LuceneHandlerException
	 */
	public String searchFullTextTitolario(String idDominio, String dbName, Connection conn, String objectType, String[] fieldName,
			String searchValue, SearchType typeSearch, String filterObjects, String[] filterObjType, HashMap customFilter,
			String privacyField, LuceneParameterFilter parFilter, AurigaLoginBean login) throws LuceneException, LuceneHandlerException {

		String path = getPathFromDominio(objectType, idDominio, dbName);

		String overflowLimitStr = handlerProperties.getProperty(_OVERFLOW_LIMIT, (new Integer(_DEFAULT_OVERFLOW_LIMIT)).toString());
		overflowLimit = (new Integer(overflowLimitStr)).intValue();

		return LuceneHelper.searchFullText(SearchArea.SEARCH_TYPE_TITOLARIO, path, fieldName, searchValue, typeSearch, overflowLimit,
				filterObjects, filterObjType, customFilter, privacyField, parFilter, login, conn);

	}

	/**
	 * Restituisce il risultato della ricerca effettuata su Lucene (in formato LISTA_STD) E' solo un wrapper per il version handler della
	 * chiamata all'api del LuceneHelper
	 * 
	 * @param idDominio
	 * @param fieldName
	 * @param searchValue
	 * @param typeSearch
	 * @param filterObjects
	 * @return
	 * @throws LuceneException
	 * @throws LuceneHandlerException
	 */
	public String searchFullTextProcess(String idDominio, String dbName, String objectType, String[] fieldName, String searchValue,
			SearchType typeSearch, String filterObjects, String filterObjType, String privacyField, LuceneParameterFilter parFilter,
			AurigaLoginBean login) throws LuceneException, LuceneHandlerException {

		String path = getPathFromDominio(objectType, idDominio, dbName);

		String overflowLimitStr = handlerProperties.getProperty(_OVERFLOW_LIMIT, (new Integer(_DEFAULT_OVERFLOW_LIMIT)).toString());
		overflowLimit = (new Integer(overflowLimitStr)).intValue();

		return LuceneHelper.searchFullText(SearchArea.SEARCH_TYPE_PROCESS, path, fieldName, searchValue, typeSearch, overflowLimit,
				filterObjects, filterObjType, privacyField, parFilter, login, null);

	}

	/**
	 * Restituisce il risultato della ricerca effettuata su Lucene (in formato LISTA_STD) E' solo un wrapper per il version handler della
	 * chiamata all'api del LuceneHelper
	 * 
	 * @param idDominio
	 * @param fieldName
	 * @param searchValue
	 * @param typeSearch
	 * @param filterObjects
	 * @param customFilter
	 * @return
	 * @throws LuceneException
	 * @throws LuceneHandlerException
	 */
	public String searchFullTextProcess(String idDominio, String dbName, String objectType, String[] fieldName, String searchValue,
			SearchType typeSearch, String filterObjects, String[] filterObjType, HashMap customFilter, String privacyField,
			LuceneParameterFilter parFilter, AurigaLoginBean login) throws LuceneException, LuceneHandlerException {

		String path = getPathFromDominio(objectType, idDominio, dbName);

		String overflowLimitStr = handlerProperties.getProperty(_OVERFLOW_LIMIT, (new Integer(_DEFAULT_OVERFLOW_LIMIT)).toString());
		overflowLimit = (new Integer(overflowLimitStr)).intValue();

		return LuceneHelper.searchFullText(SearchArea.SEARCH_TYPE_PROCESS, path, fieldName, searchValue, typeSearch, overflowLimit,
				filterObjects, filterObjType, customFilter, privacyField, parFilter, login, null);

	}

	/**
	 * Restituisce il risultato della ricerca effettuata su Lucene (in formato LISTA_STD) E' solo un wrapper per il version handler della
	 * chiamata all'api del LuceneHelper
	 * 
	 * @param idDominio
	 * @param fieldName
	 * @param searchValue
	 * @param typeSearch
	 * @param filterObjects
	 * @return
	 * @throws LuceneException
	 * @throws LuceneHandlerException
	 */
	public String searchFullTextRubrica(String idDominio, String dbName, String objectType, String[] fieldName, String searchValue,
			SearchType typeSearch, String filterObjects, String filterObjType, String privacyField, LuceneParameterFilter parFilter,
			AurigaLoginBean login) throws LuceneException, LuceneHandlerException {

		String path = getPathFromDominio(objectType, idDominio, dbName);

		String overflowLimitStr = handlerProperties.getProperty(_OVERFLOW_LIMIT, (new Integer(_DEFAULT_OVERFLOW_LIMIT)).toString());
		overflowLimit = (new Integer(overflowLimitStr)).intValue();

		return LuceneHelper.searchFullText(SearchArea.SEARCH_TYPE_RUBRICA, path, fieldName, searchValue, typeSearch, overflowLimit,
				filterObjects, filterObjType, privacyField, parFilter, login, null);

	}

	/**
	 * Restituisce il risultato della ricerca effettuata su Lucene (in formato LISTA_STD) E' solo un wrapper per il version handler della
	 * chiamata all'api del LuceneHelper
	 * 
	 * @param idDominio
	 * @param fieldName
	 * @param searchValue
	 * @param typeSearch
	 * @param filterObjects
	 * @param customFilter
	 * @return
	 * @throws LuceneException
	 * @throws LuceneHandlerException
	 */
	public String searchFullTextRubrica(String idDominio, String dbName, String objectType, String[] fieldName, String searchValue,
			SearchType typeSearch, String filterObjects, String[] filterObjType, HashMap customFilter, String privacyField,
			LuceneParameterFilter parFilter, AurigaLoginBean login) throws LuceneException, LuceneHandlerException {

		String path = getPathFromDominio(objectType, idDominio, dbName);

		String overflowLimitStr = handlerProperties.getProperty(_OVERFLOW_LIMIT, (new Integer(_DEFAULT_OVERFLOW_LIMIT)).toString());
		overflowLimit = (new Integer(overflowLimitStr)).intValue();

		return LuceneHelper.searchFullText(SearchArea.SEARCH_TYPE_RUBRICA, path, fieldName, searchValue, typeSearch, overflowLimit,
				filterObjects, filterObjType, customFilter, privacyField, parFilter, login, null);

	}

	/**
	 * restituisce i campi indicizzati per quel dominio e quell'objectype
	 * 
	 * @param idDominio
	 * @param dbName
	 * @param objectType
	 * @return
	 * @throws LuceneException
	 */
	public Collection<String> retrieveIndexedFields(String idDominio, String dbName, String objectType) throws LuceneException {
		String path = getPathFromDominio(objectType, idDominio, dbName);
		log.debug("carico gli attributi da: "+path);
		Collection<String> attributi = LuceneHelper.retrieveIndexedFields(path);
		log.debug("numero attributi su cui cercare: "+attributi.size());
		return attributi;
	}

	/**
	 * restituisce i campi non indicizzati per quel dominio e quell'objectype
	 * 
	 * @param idDominio
	 * @param dbName
	 * @param objectType
	 * @return
	 * @throws LuceneException
	 */
	public Collection<String> retrieveUnindexedFields(String idDominio, String dbName, String objectType) throws LuceneException {
		String path = getPathFromDominio(objectType, idDominio, dbName);
		return LuceneHelper.retrieveUnindexedFields(path);
	}

	/**
     * 
     */
	public int getOverFlowLimit() {
		return overflowLimit;
	}

	/**
	 * 
	 * @param idDominioAut
	 * @return
	 * @throws LuceneHandlerException
	 */
	// public String getPathFromDominio(String idDominioAut, String dbName)
	// throws LuceneNoDataFoundException {
	//
	// String path =
	// handlerProperties.getProperty(_DOMINIO_PATH+"."+idDominioAut+"."+dbName);
	// if (path == null) {
	//
	// }
	//
	// if (path == null)
	// throw new
	// LuceneNoDataFoundException("La ricerca non ha avuto alcun risultato");//Exception("Errore in getSearcher: "
	// + e.getMessage());
	// //HandlerException("Path non configurato per ID = " +
	// idDominioAut+"."+dbName);
	//
	// return path;
	// }

	/**
	 * 
	 * @param type
	 * @param idDominioAut
	 * @param dbName
	 * @return
	 * @throws LuceneHandlerException
	 */
	public String getPathFromDominio(String type, String idDominioAut, String dbName) throws LuceneException {
		log.debug("destination ricerca: "+destination);
		String path = null;

		// per ciascun type c'è un percorso specifico
		if (handlerProperties != null) {
			path = handlerProperties.getProperty(type);
		}
		// se è nullo prendo il default
		if (path == null)
			path = defaultRepositoryRoot;
		if (destination.equalsIgnoreCase("file") || destination == null) {
			if ("REP_DOC".equals(type)) {
				if (!path.endsWith(File.separator))
					path += File.separator;
				path += dbName + "_" + idDominioAut;
			}
			File pathFile = new File(path);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			if (!pathFile.canRead() || !pathFile.canWrite()) {
				throw new LuceneNoDataFoundException(
						"La ricerca non ha avuto alcun risultato (controllare i permessi sulla directory contenente gli indici: " + path
								+ ")");
			}
		} else {
			if (destination.equalsIgnoreCase("db")) {
				if ("REP_DOC".equals(type)) {
					path += "_";
					path += idDominioAut;
				}

			} else {
				throw new LuceneException(
						"La configurazione della destinazione degli indici(LuceneHandler) presenta un valore non conosciuto: "
								+ destination);
			}
		}
		log.debug("vado a leggere gli indici su :"+path);
		return path;
	}

	/**
	 * 
	 * @param idDominio
	 * @param ciObj
	 * @return
	 * @throws LuceneHandlerException
	 * @throws LuceneException
	 */
	// public boolean isDocumentInLucene(String idDominio, String dbName, String
	// ciObj) throws LuceneHandlerException, LuceneException {
	//
	// String path = getPathFromDominio(idDominio, dbName);
	//
	// return LuceneHelper.isDocumentInLucene(path, ciObj);
	// }

	/**
	 * 
	 * @param type
	 * @param idDominio
	 * @param ciObj
	 * @return
	 * @throws LuceneHandlerException
	 * @throws LuceneException
	 */
	public boolean isDocumentInLucene(String type, String idDominio, String dbName, String ciObj) throws LuceneHandlerException,
			LuceneException {

		String path = getPathFromDominio(type, idDominio, dbName);

		return LuceneHelper.isDocumentInLucene(path, ciObj);
	}

	/**
	 * 
	 * @param idDominio
	 * @param ciObj
	 * @param attributes
	 * @throws LuceneHandlerException
	 * @throws LuceneException
	 */
	// public void addLuceneDocument(String idDominio, String dbName, String
	// ciObj, Properties attributes) throws LuceneHandlerException,
	// LuceneException {
	//
	// String path = getPathFromDominio(idDominio, dbName);
	// LuceneHelper.addLuceneDocument(path, ciObj, attributes);
	// }

	/**
	 * 
	 * @param type
	 * @param idDominio
	 * @param ciObj
	 * @param attributes
	 * @throws LuceneHandlerException
	 * @throws LuceneException
	 */
	public void addLuceneDocument(String type, String idDominio, String dbName, String ciObj, Properties attributes)
			throws LuceneHandlerException, LuceneException {

		String path = getPathFromDominio(type, idDominio, dbName);
		LuceneHelper.addLuceneDocument(path, ciObj, attributes);
	}

	/**
	 * 
	 * @param idDominio
	 * @param ciObj
	 * @param attributes
	 * @throws LuceneHandlerException
	 * @throws LuceneException
	 */
	// public void modifyLuceneDocument(String idDominio, String dbName, String
	// ciObj, Properties attributes) throws LuceneHandlerException,
	// LuceneException {
	//
	// String path = getPathFromDominio(idDominio, dbName);
	// LuceneHelper.modifyLuceneDocument(path, ciObj, attributes);
	// }

	/**
	 * 
	 * @param type
	 * @param idDominio
	 * @param ciObj
	 * @param attributes
	 * @throws LuceneHandlerException
	 * @throws LuceneException
	 */
	public void modifyLuceneDocument(String type, String idDominio, String dbName, String ciObj, Properties attributes)
			throws LuceneHandlerException, LuceneException {

		String path = getPathFromDominio(type, idDominio, dbName);
		LuceneHelper.modifyLuceneDocument(path, ciObj, attributes);
	}

	/**
	 * 
	 * @param idDominio
	 * @param ciObj
	 * @throws LuceneHandlerException
	 * @throws LuceneException
	 */
	// public void deleteLuceneDocument(String idDominio, String dbName, String
	// ciObj) throws LuceneHandlerException, LuceneException {
	//
	// String path = getPathFromDominio(idDominio, dbName);
	// LuceneHelper.deleteLuceneDocument(path, ciObj);
	// }

	/**
	 * 
	 * @param idDominio
	 * @param type
	 * @param ciObj
	 * @throws LuceneHandlerException
	 * @throws LuceneException
	 */
	public void deleteLuceneDocument(String type, String idDominio, String dbName, String ciObj) throws LuceneHandlerException,
			LuceneException {

		String path = getPathFromDominio(type, idDominio, dbName);
		LuceneHelper.deleteLuceneDocument(path, ciObj);
	}

	/**
	 * 
	 * @param idDominio
	 * @param dbName
	 * @throws LuceneHandlerException
	 * @throws LuceneException
	 */
	// public void optimizeLucene(String idDominio, String dbName) throws
	// LuceneHandlerException, LuceneException {
	//
	// String path = getPathFromDominio(idDominio, dbName);
	// LuceneHelper.optimizeLucene(path);
	// }

	// ****************************************************************************************
	// //
	// metodi per la gestione del logger
	// ****************************************************************************************
	// //

	public void setLoggerPrefix(String nomeLogger) {
		loggerPrefix = nomeLogger;
	}

	public String getLoggerPrefix() {
		return loggerPrefix;
	}

	private Logger getLogger(String aliasName) {
		StringBuffer sb = new StringBuffer();
		// aggiungo prefix (se: versionhandler)
		if (loggerPrefix != null) {
			sb.append(loggerPrefix);
			sb.append(".");
		}
		// aggiungo root (es: objecthandler)
		sb.append(loggerRoot);
		return Logger.getLogger(sb.toString());
	}

	public String getDefaultContextRoot() {
		return defaultContextRoot;
	}

	public void setDefaultContextRoot(String defaultContextRoot) {
		this.defaultContextRoot = defaultContextRoot;
	}

	public String getDefaultRepositoryRoot() {
		return defaultRepositoryRoot;
	}

	public void setDefaultRepositoryRoot(String defaultRepositoryRoot) {
		this.defaultRepositoryRoot = defaultRepositoryRoot;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

}
