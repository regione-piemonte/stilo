/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.utility.FileUtil;
import it.eng.utility.storageutil.Storage;
import it.eng.utility.storageutil.StorageConfig;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.manager.dao.DaoStorages;
import it.eng.utility.storageutil.manager.entity.Storages;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * Classe che espone alcuni metodi di utilit� da utilizzare nello storage
 * 
 * @author Mattia Zanin
 *
 */
public class StorageUtil {

	private static final Logger log = Logger.getLogger(StorageUtil.class);

	/**
	 * Metodo che ritorna un filename univoco, contenente il timestamp e un numero casuale
	 * 
	 */
	public static final String getUniqueFileName() {
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
		String suffix = "" + (Math.random() * Integer.MAX_VALUE);
		suffix = suffix.replaceAll("[\\.E]", "");
		return timestamp + suffix;
	}

	/**
	 * Metodo che ritorna lo storage configurato in DB, corrispondente all'id passato in ingresso
	 * 
	 * @param idStorage
	 * @throws StorageException
	 */
	public static Storage getConfiguredStorageInDB(String idStorage) throws StorageException {
		log.debug("richiesto storage per " + idStorage);
		return StorageConfig.getStorage(idStorage);
	}

	public static String getStorageType(String idStorage) throws StorageException {
		try {
			log.debug("idStorage: " + idStorage);
			Storages storages = (idStorage != null) ? DaoStorages.newInstance().get(idStorage) : null;
			log.debug("tipo storage: " + storages.getTipoStorage());
			return storages != null ? storages.getTipoStorage() : null;
		} catch (Exception e) {
			throw new StorageException("Errore nel recupero del tipo relativo allo storage con id. " + idStorage);
		}
	}

	public static String getStorageConfig(String idStorage) throws StorageException {
		try {
			log.debug("idStorage: " + idStorage);
			Storages storages = (idStorage != null) ? DaoStorages.newInstance().get(idStorage) : null;
			log.debug("tipo storage: " + storages.getTipoStorage());
			return storages != null ? storages.getXmlConfig() : null;
		} catch (Exception e) {
			throw new StorageException("Errore nel recupero delle configurazioni relative allo storage con id. " + idStorage);
		}
	}

	/**
	 * Metodo che risolve l'uri passato in ingresso e ritorna un array di stringhe contenente: [0] id dello storage [1] id con cui recuperare il file dallo
	 * storage
	 * 
	 * Se l'uri � malformato torna l'array con entrambi gli elementi a null
	 * 
	 * @param uri
	 * @throws StorageException
	 */
	public static String[] resolveStorageUri(String uri) {
		int pos = 0;
		String[] res = new String[2];
		// LogicaName nullo
		if ((uri == null) || (uri.trim().equals(""))) {
			return res;
		}

		// verifica che absoluteLogicalName inizi per "["
		if (uri.startsWith("[")) {
			// prelevo posizione di "]"
			pos = uri.indexOf("]");
			// se c'e' la "]" allora divido per logical name e handler
			if (pos >= 1) {
				res[0] = uri.substring(1, pos).trim();
				res[1] = uri.substring(pos + 1).trim();
				return res;
			}
			// c'e' l'apertura ma non non c'e' la chiusura "]", quindi e' concettalmente un errore.
			else {
				return res;
			}
		} else {
			res[0] = null;
			res[1] = uri;
		}
		return res;
	}

	public static String compressToZipFile(InputStream from, String to, int bufferSize) throws ZipException, Exception {

		String tmpSha1Base64 = null;
		boolean isSha1Valid = false;

		File tmp = null;

		try {
			tmp = File.createTempFile("file", null);
			tmpSha1Base64 = copiaFile(from, tmp, bufferSize);

			ZipFile zipFile = new ZipFile(to);
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // set the compression level
			parameters.setEncryptFiles(false);
			parameters.setFileNameInZip(FilenameUtils.getBaseName(to));
			parameters.setSourceExternalStream(true);
			zipFile.addFile(tmp, parameters);

			// ho calcolato tutto lo SHA1
			isSha1Valid = true;

		}
		// qualcosa e' andato male...
		catch (ZipException ex) {
			// chiudo tutto e poi cancello l'eventuale file
			FileUtil.deleteFile(new File(to));
			throw ex;
		} catch (Exception e) {
			FileUtil.deleteFile(new File(to));
			throw e;
		} finally {
			FileUtil.deleteFile(tmp);
		}

		// restituisco lo sha1 solo se sono arrivato alla fine della copia.
		if (isSha1Valid) {
			return tmpSha1Base64;
		} else {
			return null;
		}
	}

	/**
	 * Aggiunge il file specificato allo zip fornito
	 * 
	 * @param fileToBeAdded
	 * @param nameFileToBeAdded
	 * @param destinationZip
	 * @return lo sha1 dello zip, oppure null se qualcosa è andato storto
	 * @throws Exception
	 */
	public static String addFileToZip(File fileToBeAdded, String nameFileToBeAdded, String destinationZip) throws Exception {

		String tmpSha1Base64 = null;
		boolean isSha1Valid = false;

		File tmp = null;

		try {
			tmp = File.createTempFile("file", null);
			tmpSha1Base64 = copiaFile(new FileInputStream(fileToBeAdded), tmp, 16383);

			ZipFile zipFile = new ZipFile(destinationZip);
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // set the compression level
			// il nome del file nello zip deve essere necessariamente impostato
			parameters.setFileNameInZip(nameFileToBeAdded);
			parameters.setEncryptFiles(false);
			parameters.setSourceExternalStream(true);
			zipFile.addFile(tmp, parameters);

			// ho calcolato tutto lo SHA1
			isSha1Valid = true;

		} finally {
			FileUtil.deleteFile(tmp);
		}

		// restituisco lo sha1 solo se sono arrivato alla fine della copia.
		if (isSha1Valid) {
			return tmpSha1Base64;
		} else {
			return null;
		}
	}

	/**
	 * Aggiunge il file specificato allo zip fornito
	 * 
	 * @param fileToBeAdded
	 * @param destinationZip
	 * @return lo sha1 dello zip, oppure null se qualcosa è andato storto
	 * @throws Exception
	 */
	public static String addFileToZip(File fileToBeAdded, String destinationZip) throws Exception {

		String tmpSha1Base64 = null;
		boolean isSha1Valid = false;

		File tmp = null;

		try {
			tmp = File.createTempFile("file", null);
			tmpSha1Base64 = copiaFile(new FileInputStream(fileToBeAdded), tmp, 16383);

			ZipFile zipFile = new ZipFile(destinationZip);
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // set the compression level
			// il nome del file nello zip deve essere necessariamente impostato
			parameters.setFileNameInZip(fileToBeAdded.getName());
			parameters.setEncryptFiles(false);
			parameters.setSourceExternalStream(true);
			zipFile.addFile(tmp, parameters);

			// ho calcolato tutto lo SHA1
			isSha1Valid = true;

		} finally {
			FileUtil.deleteFile(tmp);
		}

		// restituisco lo sha1 solo se sono arrivato alla fine della copia.
		if (isSha1Valid) {
			return tmpSha1Base64;
		} else {
			return null;
		}

	}

	public static String copiaFile(InputStream from, File to, int bufferSize) throws IOException, Exception {
		String tmpSha1Base64 = null;
		boolean isSha1Valid = false;

		// apro l'output stream
		FileOutputStream fos = new FileOutputStream(to);
		FileHelper fileHelper = new FileHelper(from);

		try {
			byte[] buffer = new byte[(bufferSize > 0) ? bufferSize : 16384];
			int byte_count = 0;
			while ((byte_count = fileHelper.read(buffer)) > 0) {
				fos.write(buffer, 0, byte_count);
			}
			// ho calcolato tutto lo SHA1
			isSha1Valid = true;
		}
		// qualcosa e' andato male...
		catch (IOException ex) {
			// chiudo tutto e poi cancello l'eventuale file
			try {
				fos.close();
			} catch (Exception ex1) {
			}
			FileUtil.deleteFile(to);
			throw ex;
		} finally {
			try {
				fos.close();
			} catch (Exception ex) {
			}
			tmpSha1Base64 = fileHelper.getSha1AndClose();
		}

		// restituisco lo sha1 solo se sono arrivato alla fine della copia.
		if (isSha1Valid) {
			return tmpSha1Base64;
		} else {
			return null;
		}
	}

	public static String compressToCryptedZipFile(InputStream from, String to, int bufferSize) throws Exception {

		String tmpSha1Base64 = null;
		boolean isSha1Valid = false;

		File tmp = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			tmp = File.createTempFile("file", null);
			tmpSha1Base64 = copiaFile(from, tmp, bufferSize);

			String password = UUID.randomUUID().toString();
			log.debug("Password: " + password);
			log.debug("Password length: " + password.length());

			ZipFile zipFile = new ZipFile(to);
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to store compression
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // set the compression level
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // set the encryption method to Standard Zip Encryption
			parameters.setPassword(password);
			parameters.setFileNameInZip(FilenameUtils.getBaseName(to));
			parameters.setSourceExternalStream(true);
			zipFile.addFile(tmp, parameters);

			FileUtil.deleteFile(tmp);

			// scrivo la password all'interno del file, esattamente a partire dal centro (calcolato come meta' della lunghezza del file originale approssimando
			// per difetto)
			fis = new FileInputStream(to);

			tmp = new File(tmp.getPath() + ".zip");
			fos = new FileOutputStream(tmp);

			byte[] buffer = new byte[(bufferSize > 0) ? bufferSize : 16384];

			int middle = new Long(zipFile.getFile().length()).intValue() / 2;

			int byteParzialiLetti = 0;
			int byteTotaliScritti = 0;

			while ((byteParzialiLetti = fis.read(buffer)) > 0) {
				if ((byteTotaliScritti <= middle) && (middle < (byteTotaliScritti + byteParzialiLetti))) {
					if ((middle - byteTotaliScritti) > 0) {
						fos.write(buffer, 0, (middle - byteTotaliScritti));
					}
					fos.write(password.getBytes());
					fos.write(buffer, (middle - byteTotaliScritti), (byteParzialiLetti + byteTotaliScritti - middle));
				} else {
					fos.write(buffer, 0, byteParzialiLetti);
				}
				byteTotaliScritti += byteParzialiLetti;
			}

			copiaFile(FileUtils.openInputStream(tmp), new File(to), bufferSize);

			// ho calcolato tutto lo SHA1
			isSha1Valid = true;
		}
		// qualcosa e' andato male...
		catch (ZipException ex) {
			// chiudo tutto e poi cancello l'eventuale file
			FileUtil.deleteFile(new File(to));
			throw ex;
		} catch (Exception e) {
			FileUtil.deleteFile(new File(to));
			throw e;
		} finally {
			FileUtil.deleteFile(tmp);
			try {
				fis.close();
			} catch (Exception ex) {
			}
			try {
				fos.close();
			} catch (Exception ex) {
			}
		}

		// restituisco lo sha1 solo se sono arrivato alla fine della copia.
		if (isSha1Valid) {
			return tmpSha1Base64;
		} else {
			return null;
		}
	}
	
	/**
	 * Metodo che controlla se il file indicato esiste o meno.
	 * Dapprima risolve l'indirizzo e poi cerca di recuperarlo.
	 * Se � presente ritorna il valore true, altrimenti ritorna il valore false.
	 * @param file file da controllare se presente all'interno del file system
	 * @return true se il file � presente, false altrimenti
	 * @throws Exception se il file da analizzare non � valido
	 */
	public static Boolean fileExist(File file) throws Exception{
		if(file != null && file.getPath() != null && !file.getPath().isEmpty()){

			//Risolvo l'URI ottenendo quello fisico
			String[] lStrResult = StorageUtil.resolveStorageUri(file.getPath());
			if (lStrResult == null || lStrResult.length != 2 || StringUtils.isEmpty(lStrResult[0])){
				//Non � stato possibile recuperare il file
				return new Boolean(false);
			}
			
			Storage lStorage = null;
			try {
				lStorage = StorageUtil.getConfiguredStorageInDB(lStrResult[0]);
				//Cerco di ottenere il file
				lStorage.retrieveFile(lStrResult[1]);
				
				//Se arriva qui vuol dire che si � riusciti ad ottenere tale file
				return new Boolean(true);
			} catch (StorageException e) {
				return new Boolean(false);
			}
			
		}else{
			/*
			 * Lancio un eccezione poich� vuol dire che il file fornito in 
			 * input non � considerato valido
			 */
			throw new Exception("Il file da analizzare non � valido");
		}
	}
	
	/**
	 * Overload del metodo fileExist(File file)
	 * @param pathFile path del percorso del file da analizzare
	 * @return true se il file � presente, false altrimenti
	 * @throws Exception se il file da analizzare non � valido
	 */
	public static Boolean fileExist(String pathFile) throws Exception{
		return fileExist(new File(pathFile));
	}
}
