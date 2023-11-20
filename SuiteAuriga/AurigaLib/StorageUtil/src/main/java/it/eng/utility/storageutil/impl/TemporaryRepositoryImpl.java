/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.eng.utility.storageutil.TemporaryRepository;

/**
 * Classe che implementa i metodi per la gestione dei file temporanei utilizzati nello storage
 * 
 * @author Mattia Zanin
 *
 */
public class TemporaryRepositoryImpl implements TemporaryRepository {

	private static final Log logger = LogFactory.getLog(TemporaryRepositoryImpl.class.getName());

	/**
	 * Percorso in cui vengono salvati i file temporanei
	 * 
	 */
	private String basePath;

	/**
	 * Metodo costruttore della classe
	 * 
	 * @param basePath
	 */
	public TemporaryRepositoryImpl(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * Metodo che ritorna lo stream in lettura del file temporaneo
	 * 
	 * @param relativePath
	 * @throws FileNotFoundException
	 */
	@Override
	public InputStream retrieveInputStreamFromRelativePath(String relativePath) throws FileNotFoundException {
		return new FileInputStream(basePath + relativePath);
	}

	/**
	 * Metodo di salvataggio del file temporaneo
	 * 
	 * @param is
	 * @param relativePath
	 */
	@Override
	public void storeInputStreamToRelativeFile(InputStream is, String relativePath) {
		File file = new File(basePath + relativePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		OutputStream out = null;
		try {

			byte buf[] = new byte[4096 * 1024];
			int len;
			if (!file.exists()) {
				file.createNewFile();
				while ((len = is.read(buf)) > 0) {
					if (out == null) {
						out = new FileOutputStream(file);
					}
					out.write(buf, 0, len);
				}
			}
		} catch (Exception e) {
			logger.error("Eccezione storeInputStreamToRelativeFile", e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e1) {
				logger.warn("Eccezione storeInputStreamToRelativeFile - Errore chiusura stream", e1);
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e2) {
				logger.warn("Eccezione storeInputStreamToRelativeFile - Errore chiusura stream", e2);
			}
		}
	}

	/**
	 * Metodo che apre uno stream in scrittura sul file temporaneo
	 * 
	 * @param relativePath
	 * @throws IOException
	 */
	@Override
	public OutputStream openRelativeFileOutputStream(String relativePath) throws IOException {
		File file = new File(basePath + relativePath);
		File parentFolder = file.getParentFile();
		if (!parentFolder.exists())
			parentFolder.mkdirs();
		file.createNewFile();
		return new FileOutputStream(file);
	}

	/**
	 * Metodo che cancella il file temporaneo
	 * 
	 * @param relativePath
	 * @throws IOException
	 */
	@Override
	public void deleteRelativeFile(String relativePath) throws IOException {
		File file = new File(basePath + relativePath);
		if (file.exists()) {
			if (file.isDirectory()) {
				FileUtils.deleteDirectory(file);
			} else {
				FileUtils.forceDelete(file);
			}
			// cancello anche tutte le cartelle superiori (se vuote) del
			// relativePath
			File baseDir = new File(basePath);
			while (!file.getParentFile().equals(baseDir) && ArrayUtils.isEmpty(file.getParentFile().listFiles())) {
				file = file.getParentFile();
				FileUtils.deleteDirectory(file);
			}
		}
	}

	/**
	 * Metodo che ritorna il basePath
	 * 
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * Metodo che setta il basePath (se non finisce con un separatore lo aggiunge)
	 * 
	 */
	public void setBasePath(String basePath) {
		if (!basePath.endsWith(File.separator))
			this.basePath = basePath + File.separator;
		else
			this.basePath = basePath;
	}

	@Override
	public void printMessageToRelativeFile(Message lMessage, String relativePath) {
		File file = new File(basePath + relativePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		OutputStream out = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
				out = new FileOutputStream(file);
				lMessage.writeTo(out);
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}

	}

	@Override
	public void writeFileItemToRelativeFile(FileItem fileItem, String relativePath) {
		File file = new File(basePath + relativePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			if (!file.exists()) {
				file.createNewFile();
				fileItem.write(file);
			}

		} catch (Exception e) {
			logger.error("Eccezione writeFileItemToRelativeFile", e);
		}

	}

	@Override
	public File retrieveFileFromRelativePath(String relativePath) throws FileNotFoundException {
		return new File(basePath + relativePath);
	}

}
