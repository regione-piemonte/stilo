/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.utility.storageutil.annotations.TipoStorage;
import it.eng.utility.storageutil.enums.StorageType;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.impl.TemporaryRepositoryImpl;
import it.eng.utility.storageutil.impl.filesystem.DateFileSystemStoragePolicy;
import it.eng.utility.storageutil.impl.filesystem.FileSystemStorage;
import it.eng.utility.storageutil.impl.filesystem.FileSystemStorageConfig;
import it.eng.utility.storageutil.impl.filesystem.util.EnvironmentVariableConfigManager;
import it.eng.utility.storageutil.util.StorageDeleter;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.storageutil.util.StorageUtil7Z;
import it.eng.utility.storageutil.util.XMLUtil;

@TipoStorage(tipo = StorageType.FS7Z)
public class SevenZFileSystemStorage extends FileSystemStorage {

	private static Logger logger = Logger.getLogger(SevenZFileSystemStorage.class);

	private String archiveExt = null;

	public SevenZFileSystemStorage() {

	}

	@Override
	public void configure(String xmlConfig) throws StorageException {
		try {
			FileSystemStorageConfig config = (FileSystemStorageConfig) XMLUtil.newInstance().deserialize(xmlConfig,
					FileSystemStorageConfig.class);
			if (config != null) {
				EnvironmentVariableConfigManager.verifyPathsForVariable(config);
				this.baseFolder = new File(config.getBaseFolderPath());
				this.repositoryLocations = config.getRepositoryLocations();
				this.temporaryRepository = new TemporaryRepositoryImpl(config.getTempRepositoryBasePath());
				this.storagePolicy = new DateFileSystemStoragePolicy();
				this.nroMaxFiles = config.getNroMaxFiles() > 0 ? config.getNroMaxFiles() : NRO_MAX_FILES;
				this.archiveExt = config.getArchiveExt();
			}
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	//TODO Inserire la compressione del file 
	protected String persistTemporaryFile(String relativePath, File storageFolder) throws IOException {
		// stream dei dati del file temporaneo
//		InputStream is = temporaryRepository.retrieveInputStreamFromRelativePath(relativePath);
//
//		// file definitivo
//		// File primaryDocumentStoredFile = new
//		// File(storageFolder.getAbsolutePath() + File.separator +
//		// relativePath);
//
//		// se non esiste creo la directory in cui salvare il file
//		// File primaryDocumentStoredFolder =
//		// primaryDocumentStoredFile.getParentFile();
//		File primaryDocumentStoredFolder = new File(storageFolder.getAbsolutePath() + File.separator);
//		if (!primaryDocumentStoredFolder.exists())
//			primaryDocumentStoredFolder.mkdirs();
//
//		File zipFile = new File(primaryDocumentStoredFolder.getAbsolutePath() + relativePath + "." + this.archiveExt);
//		try {
//			StorageUtil.compressToZipFile(is, zipFile.getAbsolutePath(), 16384);
//		} catch (Exception e) {
//			logger.warn("Eccezione persistTemporaryFile", e);
//		}
//
//		temporaryRepository.deleteRelativeFile(relativePath);
//		if (zipFile.exists())
//			return zipFile.getAbsolutePath();
//		else
			return "";
	}

	private File getRealId(String id) {
		for (int i = 0; i < repositoryLocations.length; i++) {
			if (id.startsWith(File.separator + File.separator)) {
				id = StringUtils.substring(id, 1);
			}
			File file = new File(repositoryLocations[i] + id);
			if (file.exists()) {
				return file;
			}
		}
		return null;
	}

	@Override
	public String insert(File file, String... params) throws StorageException {
		try {
			// genero il nome per il file da salvare
			String relativePath = File.separator + StorageUtil.getUniqueFileName();

			// salvo il file temporaneo
			this.getTemporaryRepository().storeInputStreamToRelativeFile(new FileInputStream(file), relativePath);

			// creo la directory in cui salvare
			File storageFolder = storagePolicy.getStorageFolder(this.baseFolder.getAbsolutePath(), nroMaxFiles);
			logger.debug("storageFolder " + storageFolder.getAbsolutePath());

			String persistentFilePath = persistTemporaryFile(relativePath, storageFolder);
			if (persistentFilePath.length() > this.baseFolder.getAbsolutePath().length())
				return persistentFilePath.substring(this.baseFolder.getAbsolutePath().length());
			else
				return "";
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public String insertInput(InputStream inputStream, String... params) throws StorageException {
		try {
			// genero il nome per il file da salvare
			String relativePath = File.separator + StorageUtil.getUniqueFileName();

			// salvo il file temporaneo
			this.getTemporaryRepository().storeInputStreamToRelativeFile(inputStream, relativePath);

			File storageFolder = storagePolicy.getStorageFolder(this.baseFolder.getAbsolutePath(), nroMaxFiles);
			logger.debug("storageFolder " + storageFolder.getAbsolutePath());

			String persistentFilePath = persistTemporaryFile(relativePath, storageFolder);
			if (persistentFilePath.length() > this.baseFolder.getAbsolutePath().length())
				return persistentFilePath.substring(this.baseFolder.getAbsolutePath().length());
			else
				return "";
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public String writeFileItem(FileItem fileItem) throws StorageException {
		try {
			// genero il nome per il file da salvare
			String relativePath = File.separator + StorageUtil.getUniqueFileName();

			// salvo il file temporaneo
			this.getTemporaryRepository().writeFileItemToRelativeFile(fileItem, relativePath);

			File storageFolder = storagePolicy.getStorageFolder(this.baseFolder.getAbsolutePath(), nroMaxFiles);
			logger.debug("storageFolder " + storageFolder.getAbsolutePath());

			String persistentFilePath = persistTemporaryFile(relativePath, storageFolder);
			if (persistentFilePath.length() > this.baseFolder.getAbsolutePath().length())
				return persistentFilePath.substring(this.baseFolder.getAbsolutePath().length());
			else
				return "";
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public String printMessage(Message lMessage) throws StorageException {
		try {
			// genero il nome per il file da salvare
			String relativePath = File.separator + StorageUtil.getUniqueFileName();

			// salvo il file temporaneo
			this.getTemporaryRepository().printMessageToRelativeFile(lMessage, relativePath);

			File storageFolder = storagePolicy.getStorageFolder(this.baseFolder.getAbsolutePath(), nroMaxFiles);
			logger.debug("storageFolder " + storageFolder.getAbsolutePath());

			String persistentFilePath = persistTemporaryFile(relativePath, storageFolder);
			if (persistentFilePath.length() > this.baseFolder.getAbsolutePath().length())
				return persistentFilePath.substring(this.baseFolder.getAbsolutePath().length());
			else
				return "";
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public void delete(String id) throws StorageException {
		try {
			File file = getRealId(id);
			FileUtils.deleteQuietly(file);
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public InputStream retrieve(String id) throws StorageException {
		try {
			File file = retrieveFile(id);
			return new FileInputStream(file);
		} catch (IOException e) {
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
			//Sposto l'archivio 7z nel repository temporaneo
			this.getTemporaryRepository().storeInputStreamToRelativeFile(new FileInputStream(file), id);
			
			File file7z = this.getTemporaryRepository().retrieveFileFromRelativePath(id);
			String filename = FilenameUtils.getName(file7z.getPath()); //ottengo nomeFile.7z
			String baseName = FilenameUtils.getBaseName(filename); //ottengo nomeFile
			
			//Creo il file di ritorno che deve avere path del precedente + file separator
			String outputPath = file7z.getParentFile().getAbsoluteFile() + File.separator;
			
			//Decomprimo il file 7z salvando quanto ottenuto nello stesso percorso  
			StorageUtil7Z.decompress7Zip(file7z, outputPath);

			if(file7z.exists()){
				//Eseguo la cancellazione dell'archivio che era stato copiato e adesso scompattato
				StorageDeleter.deleteFileResolved(file7z);
			}
			
			
			return new File(new StringBuilder().append(outputPath).append(baseName).toString());
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public String getArchiveExt() {
		return archiveExt;
	}

	public void setArchiveExt(String archiveExt) {
		this.archiveExt = archiveExt;
	}

}
