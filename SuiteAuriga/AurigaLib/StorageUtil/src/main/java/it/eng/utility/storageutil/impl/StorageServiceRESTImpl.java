/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.client.StorageServiceWS;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageConfig;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.performance.StoragePerformanceLogger;

/**
 * Classe che implementa le chiamate ai servizi dello storage, esposti dalla Business,  
 * da utilizzare all'esterno per il salvataggio, la cancellazione e il recupero del file
 * <br /><b>Il suo funzionamento dipende dalla corretta inizializzazione del BusinessClient</ b>  
 * 
 * @author D.Bragato
 * 
 */
public class StorageServiceRESTImpl implements StorageService {

	private static final Locale locale = Locale.ITALY;

	private static Logger mLogger = Logger.getLogger(StorageService.class);

	private String idUtilizzatore = null;
	private StorageServiceWS storageServiceWS;

	/**
	 * Metodo costruttore della classe
	 * 
	 * @param info
	 */
	public StorageServiceRESTImpl(GenericStorageInfo info) {
		this.idUtilizzatore = info.getUtilizzatoreStorageId();
		this.storageServiceWS = new StorageServiceWS();
		mLogger.debug("Inizializzato StorageService per chiamate REST");
	}

	/**
	 * Metodo di salvataggio del file nello storage che ritorna l'id
	 * 
	 * @param file
	 * @throws StorageException
	 */
	public String store(File file, String... params) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(getUserInfoForPerformanceLogger(file), "store");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		try {
			String result = this.storageServiceWS.store(locale, this.idUtilizzatore, file);
			// Termino il performance logger
			lStoragePerformanceLogger.end();
			// Restituisco il risultato
			return result;
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	/**
	 * Metodo di cancellazione del file dallo storage
	 * 
	 * @param uri
	 * @throws StorageException
	 */
	public void delete(String uri) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(getUserInfoForPerformanceLogger(uri), "delete");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		try {
			this.storageServiceWS.delete(locale, this.idUtilizzatore, uri);
			// Termino il performance logger
			lStoragePerformanceLogger.end();
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	/**
	 * Metodo di recupero dello stream del file dallo storage
	 * 
	 * @param uri
	 * @throws StorageException
	 */
	public InputStream extract(String uri) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(getUserInfoForPerformanceLogger(uri), "extract");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		try {
			InputStream result = FileUtils.openInputStream(this.storageServiceWS.extractfile(locale, this.idUtilizzatore, uri));
			// Termino il performance logger
			lStoragePerformanceLogger.end();
			// Restituisco il risultato
			return result;
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public String storeStream(InputStream inputStream, String... params) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(getUserInfoForPerformanceLogger(inputStream), "storeStream");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		File tmpFile = null;
		try {
			tmpFile = File.createTempFile("stream", null, StorageConfig.getTempPathPerStreams());
			FileUtils.copyInputStreamToFile(
					inputStream, 
					tmpFile);
			String result = this.storageServiceWS.store(locale, this.idUtilizzatore, tmpFile);
			// Termino il performance logger
			lStoragePerformanceLogger.end();
			// Restituisco il risultato
			return result;
		} catch (Exception e) {
			throw new StorageException(e);
		} finally {
			if (tmpFile != null && tmpFile.exists()) tmpFile.delete();
		}
	}

	@Override
	public String writeTo(Message lMessage) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(getUserInfoForPerformanceLogger(lMessage), "writeTo");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		File tmpFile = null;
		try {
			tmpFile = File.createTempFile("message", null, StorageConfig.getTempPathPerStreams());
			// Termino il performance logger
			lStoragePerformanceLogger.end();
			// Restituisco il risultato
		} catch (Exception e) {
			throw new StorageException(e);
		}
		
		OutputStream out = null;
		try 
		{
			out = FileUtils.openOutputStream(tmpFile);
			BufferedOutputStream lBufferedOutputStream = new BufferedOutputStream(out, 4096*1024);
			lMessage.writeTo(lBufferedOutputStream);
			String result = this.storageServiceWS.store(locale, this.idUtilizzatore, tmpFile);
			// Termino il performance logger
			lStoragePerformanceLogger.end();
			// Restituisco il risultato
			return result;
		} catch (Exception e) {
			throw new StorageException(e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					throw new StorageException(e);
				}
			}
		}
	}

	@Override
	public String storeFileItem(FileItem fileItem) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(getUserInfoForPerformanceLogger(fileItem), "storeFileItem");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		try {
			File tmpFile = File.createTempFile("fileitem", null, StorageConfig.getTempPathPerStreams());
			fileItem.write(tmpFile);
			String result = this.storageServiceWS.store(locale, this.idUtilizzatore, tmpFile);
			// Termino il performance logger
			lStoragePerformanceLogger.end();
			// Restituisco il risultato
			return result;
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public File extractFile(String uri) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(getUserInfoForPerformanceLogger(uri), "extractFile");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		try {
			File result = this.storageServiceWS.extractfile(locale, this.idUtilizzatore, uri);
			// Termino il performance logger
			lStoragePerformanceLogger.end();
			// Restituisco il risultato
			return result;
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public File getRealFile(String uri) throws StorageException {
		// Avvio il performance logger
		StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(getUserInfoForPerformanceLogger(uri), "getRealFile");
		lStoragePerformanceLogger.start();
		// Corpo del metodo
		try {
			File result = this.storageServiceWS.getrealfile(locale, this.idUtilizzatore, uri);
			// Termino il performance logger
			lStoragePerformanceLogger.end();
			// Restituisco il risultato
			return result;
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public String getTipoStorage() throws StorageException {
		// Avvio il performance logger
		// StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(getUserInfoForPerformanceLogger(""), "getTipoStorage");
		// lStoragePerformanceLogger.start();
		// Corpo del metodo
		try {
			String result =  this.storageServiceWS.gettipostorage(locale, this.idUtilizzatore);
			// Termino il performance logger
			// lStoragePerformanceLogger.end();
			// Restituisco il risultato
			return result;
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public String getConfigurazioniStorage() throws StorageException {
		// Avvio il performance logger
		// StoragePerformanceLogger lStoragePerformanceLogger = new StoragePerformanceLogger(getUserInfoForPerformanceLogger(""), "getConfigurazioniStorage");
		// lStoragePerformanceLogger.start();
		// Corpo del metodo
		try {
			String result = this.storageServiceWS.getconfigurazionistorage(locale, this.idUtilizzatore);
			// Termino il performance logger
			// lStoragePerformanceLogger.end();
			// Restituisco il risultato
			return result;
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}
	
	/**
	 * Restituisce la stringa contenente le informazioni utente da riportare nel performance log
	 */
	private static String getUserInfoForPerformanceLogger(String uri) {
		String result = "";
		if (StringUtils.isNotBlank(uri)){
			result = "[";
			result += " uri: " + uri;
			result += "]";
		}
		return result;
	}
	
	/**
	 * Restituisce la stringa contenente le informazioni utente da riportare nel performance log
	 */
	private static String getUserInfoForPerformanceLogger(File file) {
		String result = "";
		if (file != null){
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
	 * Restituisce la stringa contenente le informazioni utente da riportare nel performance log
	 */
	private static String getUserInfoForPerformanceLogger(FileItem fileItem) {
		String result = "";
		if (fileItem != null){
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
	 * Restituisce la stringa contenente le informazioni utente da riportare nel performance log
	 */
	private static String getUserInfoForPerformanceLogger(InputStream inputStream) {
		String result = "";
		if (inputStream != null){
			result = "[";
			result += "]";
		}
		return result;
	}
	
	/**
	 * Restituisce la stringa contenente le informazioni utente da riportare nel performance log
	 */
	private static String getUserInfoForPerformanceLogger(Message message) {
		String result = "";
		try {
			if (message != null){
				result = "[";
				String subject = message.getSubject();
				if (StringUtils.isNotBlank(subject)) {
					result += " subject: " + subject;
				}
				result += "]";
			}
			}catch (Exception e) {
				result = "";
			}
		return result;
	}

}
