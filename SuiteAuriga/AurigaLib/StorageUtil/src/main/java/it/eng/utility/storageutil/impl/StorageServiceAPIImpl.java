/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.InputStream;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.Storage;
import it.eng.utility.storageutil.StorageConfig;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.performance.StoragePerformanceLogger;
import it.eng.utility.storageutil.util.StorageUtil;

/**
 * Classe che implementa tutti i servizi dello storage da utilizzare all'esterno
 * per il salvataggio, la cancellazione e il recupero del file
 * 
 * @author Mattia Zanin
 * 
 */
public class StorageServiceAPIImpl implements StorageService {

	private static Logger mLogger = Logger.getLogger(StorageService.class);

	/**
	 * ID relativo allo storage da utilizzare in caso di salvataggio di un file
	 * per le altre operazioni devo usare lo storage ricavato dall'URI
	 * 
	 */
	private String idStorage = null;

	private String idUtilizzatore = null;

	/**
	 * Metodo costruttore della classe
	 * 
	 * @param info
	 */
	public StorageServiceAPIImpl(GenericStorageInfo info) {
		try {
			idUtilizzatore = info.getUtilizzatoreStorageId();
			this.idStorage = StorageConfig.getStorageFromUtilizzatore(idUtilizzatore);
		} catch (Exception e) {
			mLogger.error(e);
			this.idStorage = null;
		}
	}

	/**
	 * Metodo di salvataggio del file nello storage che ritorna l'id
	 * 
	 * @param file
	 * @throws StorageException
	 */
	public String store(File file, String... params) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(
				getUserInfoForPerformanceLogger(file), "store");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		Storage storage = StorageUtil.getConfiguredStorageInDB(this.idStorage);
		storage.setUtilizzatoreStorage(idUtilizzatore);
		String result = "[" + this.idStorage + "]" + storage.insert(file, params);
		// Termino il performance logger
		lStoragePerformanceLogger.end();
		// Restituisco il risultato
		return result;
	}

	/**
	 * Metodo di cancellazione del file dallo storage
	 * 
	 * @param uri
	 * @throws StorageException
	 */
	public void delete(String uri) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(
				getUserInfoForPerformanceLogger(uri), "delete");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		String[] sTmp = StorageUtil.resolveStorageUri(uri);
		Storage storage = StorageUtil.getConfiguredStorageInDB(sTmp[0]);
		storage.delete(sTmp[1]);
		// Termino il performance logger
		lStoragePerformanceLogger.end();
	}

	/**
	 * Metodo di recupero dello stream del file dallo storage
	 * 
	 * @param uri
	 * @throws StorageException
	 */
	public InputStream extract(String uri) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(
				getUserInfoForPerformanceLogger(uri), "extract");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		String[] sTmp = StorageUtil.resolveStorageUri(uri);
		Storage storage = StorageUtil.getConfiguredStorageInDB(sTmp[0]);
		InputStream result = storage.retrieve(sTmp[1]);
		// Termino il performance logger
		lStoragePerformanceLogger.end();
		// Restituisco il risultato
		return result;
	}

	@Override
	public String storeStream(InputStream inputStream, String... params) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(
				getUserInfoForPerformanceLogger(inputStream), "storeStream");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		Storage storage = StorageUtil.getConfiguredStorageInDB(this.idStorage);
		mLogger.debug("STORAGE VALE " + this.idStorage);
		String result = "[" + this.idStorage + "]" + storage.insertInput(inputStream, params);
		// Termino il performance logger
		lStoragePerformanceLogger.end();
		// Restituisco il risultato
		return result;
	}

	@Override
	public String writeTo(Message lMessage) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(
				getUserInfoForPerformanceLogger(lMessage), "writeTo");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		Storage storage = StorageUtil.getConfiguredStorageInDB(this.idStorage);
		mLogger.debug("STORAGE VALE " + this.idStorage);
		String result = "[" + this.idStorage + "]" + storage.printMessage(lMessage);
		// Termino il performance logger
		lStoragePerformanceLogger.end();
		// Restituisco il risultato
		return result;
	}

	@Override
	public String storeFileItem(FileItem fileItem) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(
				getUserInfoForPerformanceLogger(fileItem), "storeFileItem");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		mLogger.debug("cerco lo storage nel db per scrivere: " + fileItem.getName());
		Storage storage = StorageUtil.getConfiguredStorageInDB(this.idStorage);
		mLogger.debug("storage configurato: " + storage);
		mLogger.debug("storage vale: " + this.idStorage);
		String result = "[" + this.idStorage + "]" + storage.writeFileItem(fileItem);
		// Termino il performance logger
		lStoragePerformanceLogger.end();
		// Restituisco il risultato
		return result;
	}

	@Override
	public File extractFile(String uri) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(
				getUserInfoForPerformanceLogger(uri), "extractFile");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		String[] sTmp = StorageUtil.resolveStorageUri(uri);
		Storage storage = StorageUtil.getConfiguredStorageInDB(sTmp[0]);
		File result = storage.retrieveFile(sTmp[1]);
		// Termino il performance logger
		lStoragePerformanceLogger.end();
		// Restituisco il risultato
		return result;
	}

	@Override
	public File getRealFile(String uri) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(
				getUserInfoForPerformanceLogger(uri), "getRealFile");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		String[] sTmp = StorageUtil.resolveStorageUri(uri);
		Storage storage = StorageUtil.getConfiguredStorageInDB(sTmp[0]);
		File result = storage.retrievRealFile(sTmp[1]);
		// Termino il performance logger
		lStoragePerformanceLogger.end();
		// Restituisco il risultato
		return result;
	}

	@Override
	public String getTipoStorage() throws StorageException {
		// Avvio il performance logger
		// StoragePerformanceLogger lStoragePerformanceLogger = new
		// StoragePerformanceLogger(getUserInfoForPerformanceLogger(""),
		// "getTipoStorage");
		// lStoragePerformanceLogger.start();
		// Corpo del metodo
		String result = StorageUtil.getStorageType(this.idStorage);
		// Termino il performance logger
		// lStoragePerformanceLogger.end();
		// Restituisco il risultato
		return result;
	}

	@Override
	public String getConfigurazioniStorage() throws StorageException {
		// Avvio il performance logger
		// StoragePerformanceLogger lStoragePerformanceLogger = new
		// StoragePerformanceLogger(getUserInfoForPerformanceLogger(""),
		// "getConfigurazioniStorage");
		// lStoragePerformanceLogger.start();
		// Corpo del metodo
		String result = StorageUtil.getStorageConfig(this.idStorage);
		// Termino il performance logger
		// lStoragePerformanceLogger.end();
		// Restituisco il risultato
		return result;
	}

	/**
	 * Restituisce la stringa contenente le informazioni utente da riportare nel
	 * performance log
	 */
	private static String getUserInfoForPerformanceLogger(String uri) {
		String result = "";
		if (StringUtils.isNotBlank(uri)) {
			result = "[";
			result += " uri: " + uri;
			result += "]";
		}
		return result;
	}

	/**
	 * Restituisce la stringa contenente le informazioni utente da riportare nel
	 * performance log
	 */
	private static String getUserInfoForPerformanceLogger(File file) {
		String result = "";
		if (file != null) {
			result = "[";
			String path = file.getPath();
			String name = file.getName();
			if (StringUtils.isNotBlank(path)) {
				result += " path: " + path;
			}
			if (StringUtils.isNotBlank(name)) {
				result += " name: " + name;
			}
			result += "]";
		}
		return result;
	}

	/**
	 * Restituisce la stringa contenente le informazioni utente da riportare nel
	 * performance log
	 */
	private static String getUserInfoForPerformanceLogger(FileItem fileItem) {
		String result = "";
		if (fileItem != null) {
			result = "[";
			String name = fileItem.getName();
			if (StringUtils.isNotBlank(name)) {
				result += " name: " + name;
			}
			result += "]";
		}
		return result;
	}

	/**
	 * Restituisce la stringa contenente le informazioni utente da riportare nel
	 * performance log
	 */
	private static String getUserInfoForPerformanceLogger(InputStream inputStream) {
		String result = "";
		if (inputStream != null) {
			result = "[";
			result += "]";
		}
		return result;
	}

	/**
	 * Restituisce la stringa contenente le informazioni utente da riportare nel
	 * performance log
	 */
	private static String getUserInfoForPerformanceLogger(Message message) {
		String result = "";
		try {
			if (message != null) {
				result = "[";
				String subject = message.getSubject();
				if (StringUtils.isNotBlank(subject)) {
					result += " subject: " + subject;
				}
				result += "]";
			}
		} catch (Exception e) {
			result = "";
		}
		return result;
	}

}
