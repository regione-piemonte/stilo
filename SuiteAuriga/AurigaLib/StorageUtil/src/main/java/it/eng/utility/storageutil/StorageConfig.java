/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.reflections.Reflections;

import it.eng.core.service.client.FactoryBusiness.BusinessType;
import it.eng.utility.storageutil.annotations.TipoStorage;
import it.eng.utility.storageutil.enums.StorageType;
import it.eng.utility.storageutil.exception.DAOException;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.exception.StorageRuntimeException;
import it.eng.utility.storageutil.manager.dao.DaoStorages;
import it.eng.utility.storageutil.manager.entity.Storages;
import it.eng.utility.storageutil.manager.entity.UtilizzatoriStorage;

/**
 * Classe che contiene le configurazioni relative al database dello storage 
 * 
 * @author Mattia Zanin
 *
 */
public class StorageConfig {

	private static final String CONN_STR_OPT_FILE_LOCK_SERIALIZED = ";FILE_LOCK=SERIALIZED";

	private static final String CONN_STR_JDBC_H2_FILE = "jdbc:h2:file:";

	private static Logger logger = Logger.getLogger(StorageConfig.class);

	private static BusinessType tipoServizio = BusinessType.API;
	
	private static String databasePath; // Percorso in cui salvare il file del database (es.: C:/data/)
	private static String databaseName; // Nome del database (es.: storage)
	
	private static String databaseInternal; // Nome del file interno al classpath contenente il database
	
	private static String hibernateCfgPath; // Path della configurazione hibernate del database
    
	private static File tempPathPerStreams; // Path per scrivere gli stream su file prima di inviarli tramite WebService

	private static Map<String, String> mappaStoragesUtilizzatori;
	private static Map<String, Storage> mappaStorages;
	
	private StorageConfig() {
		// CLASSE STATICA
	}
	
	/**
	 * Metodo di configurazione del database dello storage (se il file del database non esiste ne verr�
	 * creato uno nuovo e le tabelle verranno automaticamente generate a partire dalle entity di hibernate)
	 * 
	 * @param databasePath	 
	 * @param databaseName	 
	 * @throws Exception 
	 */
	public static void configure(String databasePath, String databaseName, String hibernateCfgPath) {
		StorageConfig.databasePath = databasePath;
		StorageConfig.databaseName = databaseName;
		StorageConfig.hibernateCfgPath = hibernateCfgPath;
		logger.debug("StorageConfig.configure(): databasePath="+databasePath+" databaseName="+databaseName+" hibernateCfgPath="+hibernateCfgPath);
		try {
			init();
		} catch (Exception e) {
			throw new StorageRuntimeException(e);
		}
	}
	
	public static void configureWithInternalDatabase(String databaseFileName, String hibernateCfgPath) {
		String databaseInternal = null;
		try {
			File databaseFile = new File(StorageConfig.class.getClassLoader().getResource(databaseFileName).toURI());
			databaseInternal = FilenameUtils.removeExtension(FilenameUtils.removeExtension(databaseFile.getPath()));			
		} catch (Exception e) {
			logger.error("Errore nel riferimento al file di database", e);
		}
		StorageConfig.databaseInternal = databaseInternal;
		StorageConfig.hibernateCfgPath = hibernateCfgPath;
		logger.debug("StorageConfig.configureWithInternalDatabase(): databaseInternal="+databaseInternal+" hibernateCfgPath="+hibernateCfgPath);
		try {
			init();
		} catch (Exception e) {
			throw new StorageRuntimeException(e);
		}
	}
	
	public static void configureWithOtherDatabase(String hibernateCfgPath) {
		StorageConfig.hibernateCfgPath = hibernateCfgPath;
		logger.debug("StorageConfig.configureWithOtherDatabase(): hibernateCfgPath="+hibernateCfgPath);
		try {
			init();
		} catch (Exception e) {
			throw new StorageRuntimeException(e);
		}
	}
	
	public static void configureWithWebServices(String tempPathPerStreams) {
		logger.debug("StorageConfig.configureWithWebServices(): tempPathPerStreams = " + tempPathPerStreams);
		try {
			StorageConfig.tempPathPerStreams = new File(tempPathPerStreams);
			initWithWS();
		} catch (Exception e) {
			throw new StorageRuntimeException(e);
		}
	}

	private static void initWithWS() throws IOException {
		logger.debug("StorageConfig.initWithWS()");
		tipoServizio = BusinessType.REST;
		if (!StorageConfig.tempPathPerStreams.exists() || !StorageConfig.tempPathPerStreams.isDirectory())
			throw new IOException("La directory temporanea non esiste: " + StorageConfig.tempPathPerStreams);
	}

	private static void init() throws DAOException, IllegalAccessException, InstantiationException, StorageException {
		logger.debug("StorageConfig.init()");
		mappaStoragesUtilizzatori = new ConcurrentHashMap<String, String>();
		mappaStorages = new ConcurrentHashMap<String, Storage>();
		List<Storages> lListStorages = DaoStorages.newInstance().getAll();
		if (lListStorages!=null) {
			logger.debug("trovati "+lListStorages.size()+" storage");
		} else {
			logger.debug("nessun storage trovato");
		}
		Reflections reflections = new Reflections("it.eng.utility.storageutil");
		Set<Class<?>> storageClassSet = reflections.getTypesAnnotatedWith(TipoStorage.class);
		for (Storages lStorage : lListStorages){
			logger.debug("tipo storage: "+ lStorage.getTipoStorage());
			StorageType type = StorageType.valueOf( lStorage.getTipoStorage());
			for (Class<?> storageClass : storageClassSet) {
				if(type.equals(storageClass.getAnnotation(TipoStorage.class).tipo())) {
					logger.debug("istanzio il valore corretto dello storage");
					Storage storage = (Storage) storageClass.newInstance();	
					logger.debug("istanziato lo storage con xmlConfig: "+lStorage.getXmlConfig());
					storage.configure(lStorage.getXmlConfig());	
					logger.debug("configuro lo storage");
					storage.setIdStorage(lStorage.getIdStorage());
					for (UtilizzatoriStorage lUtilizzatoriStorage : lStorage.getUtilizzatoriStorages()){
						mappaStoragesUtilizzatori.put(lUtilizzatoriStorage.getIdUtilizzatore(), lStorage.getIdStorage());
					}
					mappaStorages.put(lStorage.getIdStorage(), storage);
					logger.debug("inserito lo storage " + lStorage.getIdStorage()); 
				}
			}
		}
	}
	
	public static Storage getStorage(String idStorage){
		if (mappaStorages==null) {
			throw new StorageRuntimeException("mappaStorages==null, probabilmente StorageConfig non è stato inizializzato");
		}
		if (mappaStorages.get(idStorage)==null) {
			try {
				init();
			} catch (Exception e) {
				throw new StorageRuntimeException(e);
			}
		}
		return mappaStorages.get(idStorage);		
	}
	
	/**
	 * Metodo che restituisce lo storage associato all'id utilizzatore in input
	 * In mancanza di un primo risultato prova a ricaricare la mappa, visto che potrebbero essere state inserite
	 * delle nuove configurazioni
	 * @param idUtilizzatore
	 * @return
	 */
	public static String getStorageFromUtilizzatore(String idUtilizzatore){
		if (mappaStoragesUtilizzatori==null) {
			throw new StorageRuntimeException("mappaStoragesUtilizzatori==null, probabilmente StorageConfig non è stato inizializzato");
		}
		if (mappaStoragesUtilizzatori.get(idUtilizzatore)==null) {
			try {
				init();
			} catch (Exception e) {
				throw new StorageRuntimeException(e);
			}
		}
		return mappaStoragesUtilizzatori.get(idUtilizzatore);		
	}

	/**
	 * Metodo che recupera la stringa di connessione del database H2 dello storage
	 * 	 
	 */
	public static String getConnectionUrl() {
		String url = null;
		
		if(StringUtils.isNotBlank(databasePath) && StringUtils.isNotBlank(databaseName))  {
			if(!databasePath.endsWith(File.separator)) {
				databasePath += File.separator;
			}
			url = new StringBuilder(CONN_STR_JDBC_H2_FILE).append(databasePath).append(databaseName).append(CONN_STR_OPT_FILE_LOCK_SERIALIZED).toString();
		} else if(StringUtils.isNotBlank(databaseInternal))  {
			url = new StringBuilder(CONN_STR_JDBC_H2_FILE).append(databaseInternal).append(CONN_STR_OPT_FILE_LOCK_SERIALIZED).toString();
		}
		logger.debug("connetto con " + url);
		
		return url;
	}	

	/**
	 * Metodo che recupera il path in cui è salvato il file .cfg.xml di hibernate
	 * 	 
	 */
	public static String getHibernateCfgPath() {
		return hibernateCfgPath;
	}
	
	/**
	 * Ritorna il tipo di servizio usato per comunicare con StorageUtil
	 * @return {@link BusinessType} <br /> 
	 * 			{@code API} se StorageUtil viene usato come dipendenza diretta<br />
	 * 			{@code REST} se StorageUtil viene richiamato passando per la Business
	 */
	public static BusinessType getTipoServizio() {
		return tipoServizio;
	}
	
	/**
	 * Ritorna il riferimento alla directory temporanea in cui scrivere gli stream da salvare via Web Service
	 * @return {@link File} riferimento al file temporaneo
	 */
	public static File getTempPathPerStreams() {
		return tempPathPerStreams;
	}

}
