/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.job.exception.AurigaJobException;
import it.eng.utility.storageutil.Storage;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.util.StorageUtil;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * gestisce lo storage dei file
 * 
 * @author jravagnan
 * 
 */
public class StorageRetriever {

	private final static Logger log = Logger.getLogger(StorageRetriever.class);

	/**
	 * Esegue il retrieve del file dallo storage utilizzato.
	 * Se non è stato possibile ottenere questo file dallo storage allora viene stampata e successivamente
	 * lanciata un'eccezione a riguardo
	 * @param uri uri del file da prelevare
	 * @return il file desiderato nel caso sia andato tutto a buon fine
	 * @throws AurigaJobException se non è stato possibile risolvere lo storage oppure se non è stato possibile ottenere tale file
	 */
	public static File retrieveFile(String uri) throws AurigaJobException {
		File file = null;
		String[] lStrResult = StorageUtil.resolveStorageUri(uri);
		if (lStrResult == null || lStrResult.length != 2 || StringUtils.isEmpty(lStrResult[0])){
			throw new AurigaJobException("Impossibile recuperare il file");
		}
		
		Storage lStorage = null;
		try {
			lStorage = StorageUtil.getConfiguredStorageInDB(lStrResult[0]);
			file = lStorage.retrieveFile(lStrResult[1]);
		} catch (StorageException e) {
			log.error("Impossibile ricavare il file dallo storage: ", e);
			throw new AurigaJobException("Impossibile ricavare il file dallo storage: ", e);
		}
		return file;
	}
	
	/**
	 * Esegue il retrieve del file ma, al contrario del metodo precedente, in modo silenzioso.
	 * Se viene generato un errore perchè il file non viene trovato all'interno dello storage 
	 * allora viene ritornato il valore null invece di stampare un eccezione
	 * @param uri uri del file da prelevare
	 * @return il file desiderato nel caso sia andato tutto a buon fine o null in caso contrario
	 * @throws AurigaJobException se non è stato possibile risolvere lo storage
	 */
	public static File retrieveFileQuietly(String uri) throws AurigaJobException {
		File file = null;
		String[] lStrResult = StorageUtil.resolveStorageUri(uri);
		if (lStrResult == null || lStrResult.length != 2 || StringUtils.isEmpty(lStrResult[0])){
			throw new AurigaJobException("Impossibile recuperare il file");
		}
		Storage lStorage = null;
		try {
			lStorage = StorageUtil.getConfiguredStorageInDB(lStrResult[0]);
			file = lStorage.retrieveFile(lStrResult[1]);
		} catch (StorageException e) {
			return null;
		}
		return file;
	}

	public static InputStream retrieveInput(String uri) throws AurigaJobException {
		InputStream out = null;
		String[] lStrResult = StorageUtil.resolveStorageUri(uri);
		if (lStrResult == null || lStrResult.length != 2 || StringUtils.isEmpty(lStrResult[0])){
			throw new AurigaJobException("Impossibile recuperare il file");
		}
		Storage lStorage = null;
		try {
			lStorage = StorageUtil.getConfiguredStorageInDB(lStrResult[0]);
			out = lStorage.retrieve(lStrResult[1]);
		} catch (StorageException e) {
			log.error("Impossibile ricavare il file dallo storage: ", e);
			throw new AurigaJobException("Impossibile ricavare il file dallo storage: ", e);
		}
		return out;
	}

	public static void deleteFile(String uri) throws AurigaJobException {
		String[] lStrResult = StorageUtil.resolveStorageUri(uri);
		if (lStrResult == null || lStrResult.length != 2 || StringUtils.isEmpty(lStrResult[0])){
			throw new AurigaJobException("Impossibile recuperare il file");
		}
		Storage lStorage = null;
		try {
			lStorage = StorageUtil.getConfiguredStorageInDB(lStrResult[0]);
			lStorage.delete(lStrResult[1]);
		} catch (StorageException e) {
			log.error("Impossibile ricavare il file dallo storage: ", e);
			throw new AurigaJobException("Impossibile cancellare il file dallo storage: ", e);
		}
	}

}
