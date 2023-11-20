/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.utility.storageutil.Storage;
import it.eng.utility.storageutil.TemporaryRepository;
import it.eng.utility.storageutil.annotations.TipoStorage;
import it.eng.utility.storageutil.enums.StorageType;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.impl.TemporaryRepositoryImpl;
import it.eng.utility.storageutil.impl.filesystem.util.EnvironmentVariableConfigManager;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.storageutil.util.XMLUtil;

@TipoStorage(tipo = StorageType.FS)
public class FileSystemStorage implements Storage {

	private static Logger logger = Logger.getLogger(FileSystemStorage.class);

	protected final static int NRO_MAX_FILES = 100;

	// Base directory
	protected File baseFolder;

	protected String[] repositoryLocations;

	protected FileSystemStoragePolicy storagePolicy;

	// Repository temporaneo da cui recuperare i file
	protected TemporaryRepository temporaryRepository;

	protected int nroMaxFiles;
	private String idStorage;
	private String utilizzatoreStorage;

	public FileSystemStorage() {

	}

	@Override
	public void configure(String xmlConfig) throws StorageException {
		try {
			FileSystemStorageConfig config = (FileSystemStorageConfig) XMLUtil.newInstance().deserialize(xmlConfig, FileSystemStorageConfig.class);
			if (config != null) {
				EnvironmentVariableConfigManager.verifyPathsForVariable(config);
				this.baseFolder = new File(config.getBaseFolderPath());
				this.repositoryLocations = config.getRepositoryLocations();
				this.temporaryRepository = new TemporaryRepositoryImpl(config.getTempRepositoryBasePath());
				this.storagePolicy = new DateFileSystemStoragePolicy();
				this.nroMaxFiles = config.getNroMaxFiles() > 0 ? config.getNroMaxFiles() : NRO_MAX_FILES;
			}
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	protected String persistTemporaryFile(String relativePath, File storageFolder) throws IOException, StorageException {

		InputStream is = null;
		OutputStream out = null;
		try {
			is = temporaryRepository.retrieveInputStreamFromRelativePath(relativePath);
			logger.debug("Andremo a scrivere sul file: " + relativePath + " con storageFolder: " + storageFolder.getAbsolutePath());
			File primaryDocumentStoredFile = new File(storageFolder.getAbsolutePath() + File.separator + relativePath);
			logger.debug("Ricavato primaryDocumentStoredFile :" + primaryDocumentStoredFile.getAbsolutePath());
			File primaryDocumentStoredFolder = primaryDocumentStoredFile.getParentFile();
			logger.debug("Ricavato primaryDocumentStoredFolder :" + primaryDocumentStoredFolder.getAbsolutePath());
			if (!primaryDocumentStoredFolder.exists()) {
				logger.debug("Creo la directory perché non esiste");
				primaryDocumentStoredFolder.mkdirs();
			}
			out = new FileOutputStream(primaryDocumentStoredFile);
			logger.debug("Copio l'inputstream");
			int read = 0;
			byte[] bytes = new byte[4096];
			while ((read = is.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			logger.debug("Copiato");
			logger.debug("File definitivo: " + primaryDocumentStoredFile.getAbsolutePath());
			String id = primaryDocumentStoredFile.getAbsolutePath();
			return id.replace(File.separator, "/");
		} catch (Exception e) {
			throw new StorageException(e);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(out);
		}
	}

	private File getRealId(String id) {

		for (int i = 0; i < repositoryLocations.length; i++) {
			if (id.startsWith(File.separator + File.separator)) {
				id = StringUtils.substring(id, 1);
			}
			logger.debug("Cerco il file in " + repositoryLocations[i] + id);
			File file = new File(repositoryLocations[i] + id);
			if (file.exists())
				return file;
		}
		// Non ho trovato nessun file, stampo tutti i punti in cui lo ho cercato
		String errorMessage = "Il file " + id + " è stato cercato ma non trovato in:";
		for (int i = 0; i < repositoryLocations.length; i++) {
			if (id.startsWith(File.separator + File.separator)) {
				id = StringUtils.substring(id, 1);
			}
			errorMessage += "\n\t" + repositoryLocations[i] + id;
		}
		logger.error(errorMessage);
		return null;
	}

	@Override
	public String insert(File file, String... params) throws StorageException {

		FileInputStream lFileInputStream = null;
		OutputStream out = null;
		String relativePath = null;
		try {
			relativePath = File.separator + StorageUtil.getUniqueFileName();
			File storageFolder = storagePolicy.getStorageFolder(this.baseFolder.getAbsolutePath(), nroMaxFiles);
			logger.debug("Andremo a scrivere sul file: " + relativePath + " con storageFolder: " + storageFolder.getAbsolutePath());
			File primaryDocumentStoredFile = new File(storageFolder.getAbsolutePath() + File.separator + relativePath);
			logger.debug("Ricavato primaryDocumentStoredFile :" + primaryDocumentStoredFile.getAbsolutePath());
			File primaryDocumentStoredFolder = primaryDocumentStoredFile.getParentFile();
			logger.debug("Ricavato primaryDocumentStoredFolder :" + primaryDocumentStoredFolder.getAbsolutePath());
			if (!primaryDocumentStoredFolder.exists()) {
				logger.debug("Creo la directory perchè non esiste");
				primaryDocumentStoredFolder.mkdirs();
			}
			out = new FileOutputStream(primaryDocumentStoredFile);
			lFileInputStream = new FileInputStream(file);
			logger.debug("Copio l'inputstream");
			int read = 0;
			byte[] bytes = new byte[4096 * 1024];
			while ((read = lFileInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			logger.debug("Copiato");
			logger.debug("File definitivo: " + primaryDocumentStoredFile.getAbsolutePath());
			String id = primaryDocumentStoredFile.getAbsolutePath();
			id = id.replace(File.separator, "/");
			return id.substring(this.baseFolder.getAbsolutePath().length());
		} catch (Exception e) {
			throw new StorageException(e);
		} finally {
			IOUtils.closeQuietly(lFileInputStream);
			IOUtils.closeQuietly(out);
		}

	}

	@Override
	public void delete(String id) throws StorageException {
		try {
			File file = getRealId(id);
			if (file != null)
				FileUtils.deleteQuietly(file);
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public InputStream retrieve(String id) throws StorageException {
		try {
			File file = getRealId(id);
			if (file == null || !file.exists()) {
				throw new StorageException(id + ": id non valido" + (file != null ? "path: " + file.getPath() + ")" : ""));
			}
			return new FileInputStream(file);
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public TemporaryRepository getTemporaryRepository() {
		return this.temporaryRepository;
	}

	public void setTemporaryRepository(TemporaryRepository temporaryRepository) {
		this.temporaryRepository = temporaryRepository;
	}

	public File getBaseFolder() {
		return baseFolder;
	}

	public void setBaseFolder(File baseFolder) throws StorageException {
		if (baseFolder.isDirectory())
			this.baseFolder = baseFolder;
		else
			throw new StorageException(baseFolder + " is not a valid folder");
	}

	public String[] getRepositoryLocations() {
		return repositoryLocations;
	}

	public void setRepositoryLocations(String[] repositoryLocations) {
		this.repositoryLocations = repositoryLocations;
	}

	public FileSystemStoragePolicy getStoragePolicy() {
		return storagePolicy;
	}

	public void setStoragePolicy(FileSystemStoragePolicy storagePolicy) {
		this.storagePolicy = storagePolicy;
	}

	@Override
	public String insertInput(InputStream inputStream, String... params) throws StorageException {
		OutputStream out = null;
		String relativePath = null;
		try {
			relativePath = File.separator + StorageUtil.getUniqueFileName();
			File storageFolder = storagePolicy.getStorageFolder(this.baseFolder.getAbsolutePath(), nroMaxFiles);
			logger.debug("Andremo a scrivere sul file: " + relativePath + " con storageFolder: " + storageFolder.getAbsolutePath());
			File primaryDocumentStoredFile = new File(storageFolder.getAbsolutePath() + File.separator + relativePath);
			logger.debug("Ricavato PrimaryDocumentStoredFile :" + primaryDocumentStoredFile.getAbsolutePath());
			File primaryDocumentStoredFolder = primaryDocumentStoredFile.getParentFile();
			logger.debug("Ricavato PrimaryDocumentStoredFolder :" + primaryDocumentStoredFolder.getAbsolutePath());
			if (!primaryDocumentStoredFolder.exists()) {
				logger.debug("Creo la directory perchè non esiste");
				primaryDocumentStoredFolder.mkdirs();
			}
			out = new FileOutputStream(primaryDocumentStoredFile);
			logger.debug("Copio l'inputstream");
			int read = 0;
			byte[] bytes = new byte[4096 * 1024];
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			logger.debug("Copiato");
			logger.debug("file definitivo: " + primaryDocumentStoredFile.getAbsolutePath());
			String id = primaryDocumentStoredFile.getAbsolutePath();
			id = id.replace(File.separator, "/");
			return id.substring(this.baseFolder.getAbsolutePath().length());
		} catch (Exception e) {
			throw new StorageException(e);
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(out);
		}
	}

	@Override
	public String printMessage(Message lMessage) throws StorageException {
		try {
			String relativePath = File.separator + StorageUtil.getUniqueFileName();
			File storageFolder = storagePolicy.getStorageFolder(this.baseFolder.getAbsolutePath(), nroMaxFiles);
			logger.debug("andremo a scrivere sul file: " + relativePath + " con storageFolder: " + storageFolder.getAbsolutePath());
			File primaryDocumentStoredFile = new File(storageFolder.getAbsolutePath() + File.separator + relativePath);
			logger.debug("ricavato primaryDocumentStoredFile :" + primaryDocumentStoredFile.getAbsolutePath());
			File primaryDocumentStoredFolder = primaryDocumentStoredFile.getParentFile();
			logger.debug("ricavato primaryDocumentStoredFolder :" + primaryDocumentStoredFolder.getAbsolutePath());
			if (!primaryDocumentStoredFolder.exists()) {
				logger.debug("creo la directory perchè non esiste");
				primaryDocumentStoredFolder.mkdirs();
			}
			OutputStream out = new FileOutputStream(primaryDocumentStoredFile);
			BufferedOutputStream lBufferedOutputStream = new BufferedOutputStream(out, 4096 * 1024);
			lMessage.writeTo(lBufferedOutputStream);
			String id = primaryDocumentStoredFile.getAbsolutePath();
			id = id.replace(File.separator, "/");

			return id.substring(this.baseFolder.getAbsolutePath().length());
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public String writeFileItem(FileItem fileItem) throws StorageException {
		try {
			String relativePath = File.separator + StorageUtil.getUniqueFileName();
			logger.debug("relative path: " + relativePath);
			this.getTemporaryRepository().writeFileItemToRelativeFile(fileItem, relativePath);
			logger.debug("Ho scritto il file temporaneo");
			File storageFolder = storagePolicy.getStorageFolder(this.baseFolder.getAbsolutePath(), nroMaxFiles);
			logger.debug("ecco la storageFolder: " + storageFolder.getAbsolutePath());
			return persistTemporaryFile(relativePath, storageFolder).substring(this.baseFolder.getAbsolutePath().length());
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public File retrieveFile(String id) throws StorageException {
		try {
			File file = getRealId(id);
			if (file == null || !file.exists()) {
				throw new StorageException(id + ": id non valido" + (file != null ? "path: " + file.getPath() + ")" : ""));
			}
			return file;
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public File retrievRealFile(String id) throws StorageException {
		try {
			File file = getRealId(id);
 			if (file == null || !file.exists()) {
				throw new StorageException(id + ": id non valido" + (file != null ? "path: " + file.getPath() + ")" : ""));
			}
			return file;
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public void setIdStorage(String idStorage) {
		this.idStorage = idStorage;
	}

	public void setUtilizzatoreStorage(String idUtilizzatore) {
		this.utilizzatoreStorage = idUtilizzatore;
	}
}
