/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
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
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.storageutil.util.XMLUtil;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

@TipoStorage(tipo = StorageType.FSCZIP)
public class CZipFileSystemStorage extends FileSystemStorage {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(CZipFileSystemStorage.class);

	private String archiveExt = null;

	public CZipFileSystemStorage() {

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

	protected String persistTemporaryFile(String relativePath, File storageFolder) throws IOException {
		// stream dei dati del file temporaneo
		InputStream is = temporaryRepository.retrieveInputStreamFromRelativePath(relativePath);

		// file definitivo
		// File primaryDocumentStoredFile = new
		// File(storageFolder.getAbsolutePath() + File.separator +
		// relativePath);

		// File primaryDocumentStoredFolder =
		// primaryDocumentStoredFile.getParentFile();
		File primaryDocumentStoredFolder = new File(storageFolder.getAbsolutePath() + File.separator);
		if (!primaryDocumentStoredFolder.exists())
			primaryDocumentStoredFolder.mkdirs();

		File zipFile = new File(primaryDocumentStoredFolder.getAbsolutePath() + relativePath + "." + this.archiveExt);
		try {
			StorageUtil.compressToCryptedZipFile(is, zipFile.getAbsolutePath(), 16384);
		} catch (ZipException e) {
			logger.warn("ZipException persistTemporaryFile", e);
		} catch (NoSuchAlgorithmException e) {
			logger.warn("NoSuchAlgorithmException persistTemporaryFile", e);
		} catch (Exception e) {
			logger.warn("Eccezione persistTemporaryFile", e);
		}

		temporaryRepository.deleteRelativeFile(relativePath);
		if (zipFile.exists())
			return zipFile.getAbsolutePath();
		else
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
			File file = getRealId(id);
			if (file == null || !file.exists())
				throw new StorageException(id + ": id non valido");
			this.getTemporaryRepository().storeInputStreamToRelativeFile(new FileInputStream(file), id);

			File zip = this.getTemporaryRepository().retrieveFileFromRelativePath(id);
			String filename = FilenameUtils.getName(zip.getPath());

			FileInputStream fis = new FileInputStream(zip);

			File tmp = File.createTempFile("file", null);
			FileOutputStream fos = new FileOutputStream(tmp);

			byte[] passwordBytes = null;

			byte[] buffer = new byte[16384];

			int middle = (new Long(zip.length()).intValue() - 36) / 2;

			int byte_count = 0;
			int writed_byte_count = 0;

			while ((byte_count = fis.read(buffer)) > 0) {
				if ((writed_byte_count <= middle) && (middle < (writed_byte_count + byte_count))) {
					if ((middle - writed_byte_count) > 0) {
						fos.write(buffer, 0, (middle - writed_byte_count));
					}
					if ((middle - writed_byte_count + 36) < byte_count) {
						passwordBytes = (byte[]) ArrayUtils.subarray(buffer, (middle - writed_byte_count),
								(middle - writed_byte_count + 36));
						fos.write(buffer, (middle - writed_byte_count + 36),
								(byte_count + writed_byte_count - middle - 36));
					} else {
						passwordBytes = (byte[]) ArrayUtils.subarray(buffer, (middle - writed_byte_count), byte_count);
					}
				} else if ((writed_byte_count < (middle + 36)) && ((middle + 36) < (writed_byte_count + byte_count))) {
					passwordBytes = ArrayUtils.addAll(passwordBytes,
							(byte[]) ArrayUtils.subarray(buffer, 0, (middle + 36 - writed_byte_count)));
					fos.write(buffer, (middle + 36 - writed_byte_count),
							(byte_count + writed_byte_count - middle - 36));
				} else {
					fos.write(buffer, 0, byte_count);
				}
				writed_byte_count += byte_count;
			}
			fis.close();
			fos.close();

			String password = new String(passwordBytes);
			logger.debug("password:" + password);

			ZipFile zipFile = new ZipFile(tmp);
			logger.debug("File valido" + zipFile.isValidZipFile());

			if (zipFile.isEncrypted()) {
				logger.debug("File cifrato" + zipFile.isEncrypted());
				zipFile.setPassword(password);
			}
			FileHeader fileHeader = zipFile.getFileHeader(FilenameUtils.getBaseName(filename));
			return zipFile.getInputStream(fileHeader);

		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public File retrieveFile(String id) throws StorageException {
		try {
			File file = getRealId(id);
			if (file == null || !file.exists())
				throw new StorageException(id + ": id non valido");
			this.getTemporaryRepository().storeInputStreamToRelativeFile(new FileInputStream(file), id);

			File zip = this.getTemporaryRepository().retrieveFileFromRelativePath(id);
			String filename = FilenameUtils.getName(zip.getPath());

			FileInputStream fis = new FileInputStream(zip);

			File tmp = File.createTempFile("file", null);
			FileOutputStream fos = new FileOutputStream(tmp);

			byte[] passwordBytes = new byte[36];

			byte[] buffer = new byte[16384];

			int middle = (new Long(zip.length()).intValue() - 36) / 2;

			int byte_count = 0;
			int writed_byte_count = 0;

			while ((byte_count = fis.read(buffer)) > 0) {
				if ((writed_byte_count <= middle) && (middle < (writed_byte_count + byte_count))) {
					if ((middle - writed_byte_count) > 0) {
						fos.write(buffer, 0, (middle - writed_byte_count));
					}
					if ((middle - writed_byte_count + 36) < byte_count) {
						passwordBytes = (byte[]) ArrayUtils.subarray(buffer, (middle - writed_byte_count),
								(middle - writed_byte_count + 36));
						fos.write(buffer, (middle - writed_byte_count + 36),
								(byte_count + writed_byte_count - middle - 36));
					} else {
						passwordBytes = (byte[]) ArrayUtils.subarray(buffer, (middle - writed_byte_count), byte_count);
					}
				} else if ((writed_byte_count < (middle + 36)) && ((middle + 36) < (writed_byte_count + byte_count))) {
					passwordBytes = (byte[]) ArrayUtils.subarray(buffer, 0, (middle + 36 - writed_byte_count));
					fos.write(buffer, (middle + 36 - writed_byte_count),
							(byte_count + writed_byte_count - middle - 36));
				} else {
					fos.write(buffer, 0, byte_count);
				}
				writed_byte_count += byte_count;
			}
			try {
				fis.close();
			} catch (Exception e) {
			}
			try {
				fos.close();
			} catch (Exception e) {
			}

			String password = new String(passwordBytes);
			logger.debug("password:" + password);

			ZipFile zipFile = new ZipFile(tmp);
			logger.debug("File cifrato" + zipFile.isValidZipFile());

			if (zipFile.isEncrypted()) {
				logger.debug("File cifrato" + zipFile.isValidZipFile());
				zipFile.setPassword(password);
			}
			String baseName = FilenameUtils.getBaseName(filename);
			FileHeader fileHeader = zipFile.getFileHeader(baseName);

			File fileToReturn = new File(zip.getParentFile().getAbsoluteFile() + File.separator + baseName);
			OutputStream outputStream = new FileOutputStream(fileToReturn);
			IOUtils.copy(zipFile.getInputStream(fileHeader), outputStream);
			try {
				outputStream.close();
			} catch (Exception e) {
			}
			return fileToReturn;

		} catch (ZipException e) {
			logger.error("Errore nel file zip", e);
			throw new StorageException(e);
		} catch (Exception e) {
			logger.error("Errore di lettura/scrittura file", e);
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
