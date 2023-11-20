/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.storageutil.Storage;
import it.eng.utility.storageutil.TemporaryRepository;
import it.eng.utility.storageutil.annotations.TipoStorage;
import it.eng.utility.storageutil.enums.StorageType;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.impl.TemporaryRepositoryImpl;
import it.eng.utility.storageutil.impl.filesystem.DateFileSystemStoragePolicy;
import it.eng.utility.storageutil.impl.filesystem.FileSystemStoragePolicy;
import it.eng.utility.storageutil.impl.ftp.pool.FTPWrap;
import it.eng.utility.storageutil.impl.ftp.pool.FTPWrapFactory;
import it.eng.utility.storageutil.impl.ftp.pool.FTPWrapPool;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.storageutil.util.XMLUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;

@TipoStorage(tipo = StorageType.FTP)
public class FTPStorage implements Storage {

	protected final static int NRO_MAX_FILES = 100;
	protected final static int POOL_MAX_IDLE = 5;
	protected final static int POOL_MIN_IDLE = 2;
	protected final static int POOL_MAX_TOTAL = 5;

	public final static String FILE_SEPARATOR = "/";
	private static Logger logger = Logger.getLogger(FTPStorage.class);

	protected TemporaryRepository temporaryRepository;
	private String idStorage;
	private String utilizzatoreStorage;

	private String ftpHost = null;
	private int ftpPort = 21;
	private String ftpUsername = null;
	private String ftpPassword = null;
	private String ftpWorkingDir = null;

	private String protocol = null;

	private int numRetrieve = 0;
	private int maxNumRetrieve;
	protected int nroMaxFiles;

	private int poolMaxIdle;
	private int poolMaxTotal;
	private int poolMinIdle;
	private int ftpTimeout;

	private boolean overwite;
	private boolean passiveMode;
	private boolean isDebugEnabled;

	private FTPWrapPool pool;

	protected FileSystemStoragePolicy storagePolicy;

	@Override
	public void configure(String xmlConfig) throws StorageException {
		try {
			FTPStorageConfig config = (FTPStorageConfig) XMLUtil.newInstance().deserialize(xmlConfig, FTPStorageConfig.class);

			if (config != null) {
				this.temporaryRepository = new TemporaryRepositoryImpl(config.getTempRepositoryBasePath());
				this.ftpHost = _checkConfigParam("ftp Host", config.getHost(), true);
				this.ftpPort = config.getPort();
				this.ftpPassword = _checkConfigParam("ftp Password", config.getPassword(), true);
				this.ftpWorkingDir = _checkConfigParam("ftp Working Dir", config.getWorkingDir(), true);
				this.ftpUsername = _checkConfigParam("ftp Username", config.getUsername(), true);
				this.maxNumRetrieve = config.getMaxNumRetrieve();
				this.storagePolicy = new DateFileSystemStoragePolicy();
				this.nroMaxFiles = config.getNroMaxFiles() > 0 ? config.getNroMaxFiles() : NRO_MAX_FILES;

				this.poolMaxIdle = config.getMaxIdle() > 0 ? config.getMaxIdle() : POOL_MAX_IDLE;
				this.poolMinIdle = config.getMinIdle() > 0 ? config.getMinIdle() : POOL_MIN_IDLE;
				this.poolMaxTotal = config.getMaxTotal() > 0 ? config.getMaxTotal() : POOL_MAX_TOTAL;
				this.ftpTimeout = config.getTimeout();
				this.protocol = config.getProtocol();

				this.overwite = Boolean.parseBoolean(config.getOverwrite());
				this.passiveMode = Boolean.parseBoolean(config.getPassiveMode());

				this.isDebugEnabled = config.getDebugEnabled() != null && !config.getDebugEnabled().trim().equals("") ? Boolean.parseBoolean(config
						.getDebugEnabled()) : false;

				this.init();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new StorageException(e);
		}

	}

	protected String _checkConfigParam(String configParam, String configValue, boolean returnError) throws Exception {
		String message = null;
		if (configValue == null || configValue.trim().equals("")) {
			message = "FTP/FTPS Storage: Configurazione errata: manca proprieta [" + configParam + "]";
			if (returnError) {
				logger.error(message);
				throw new Exception(message);
			} else {
				logger.warn(message);

			}
		} else {
			message = "FTP/FTPS Storage: Configurazione [" + configParam + "]->[" + configValue + "]";
			logger.info(message);
		}
		return configValue;
	}

	private void init() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(this.poolMaxIdle);
		config.setMaxTotal(this.poolMaxTotal);
		config.setMinIdle(this.poolMinIdle);

		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);

		FTPWrapFactory ftpWrapFactory = new FTPWrapFactory(this.ftpUsername, this.ftpPassword, this.ftpHost, this.ftpPort, this.ftpTimeout, this.protocol,
				this.overwite, this.passiveMode, this.ftpWorkingDir, this.isDebugEnabled);
		pool = new FTPWrapPool(ftpWrapFactory, config);
	}

	private FTPWrap getWrap() throws Exception {
		return pool.borrowObject();
	}

	private void returnWrap(FTPWrap wrap) {
		pool.returnObject(wrap);
	}

	private void close(FTPWrap wrap) {
		if (wrap != null)
			wrap.close();
	}

	protected PrimaryDocumentStored _createPrimaryDocumentStored(String relativePath) throws StorageException {
		String primaryDocumentStoredFolder = null;
		File primaryDocumentStoredFile = null;
		PrimaryDocumentStored stored = new PrimaryDocumentStored();
		try {
			File storageFolder = null;

			if (relativePath.indexOf("/", 2) < 0) {
				storageFolder = storagePolicy.getStorageFolder(this.ftpWorkingDir, nroMaxFiles);
			} else {
				storageFolder = new File(this.ftpWorkingDir);
			}

			logger.debug("andremo a scrivere sul file: " + relativePath + " con storageFolder: " + storageFolder.getAbsolutePath());
			primaryDocumentStoredFile = new File(storageFolder.getAbsolutePath() + FILE_SEPARATOR + relativePath);
			logger.debug("ricavato primaryDocumentStoredFile :" + primaryDocumentStoredFile.getAbsolutePath());
			primaryDocumentStoredFolder = primaryDocumentStoredFile.getParentFile().getAbsolutePath();
			logger.debug("ricavato primaryDocumentStoredFolder :" + primaryDocumentStoredFolder);
			// if (file != null){
			// this.temporaryRepository.storeInputStreamToRelativeFile(new FileInputStream(file), relativePath);
			// }replace("\\", "/");
			primaryDocumentStoredFolder = primaryDocumentStoredFolder.replace("\\", "/");
			int beginIndex = primaryDocumentStoredFolder.indexOf(":");
			if (beginIndex >= 0) {
				primaryDocumentStoredFolder = primaryDocumentStoredFolder.substring(beginIndex + 1);
			}
		} catch (Throwable thr) {
			throw new StorageException(thr.getMessage());
		}
		stored.fileName = primaryDocumentStoredFile.getName();
		stored.file = primaryDocumentStoredFile;

		if (!primaryDocumentStoredFolder.endsWith("/"))
			stored.folder = primaryDocumentStoredFolder + "/";
		else
			stored.folder = primaryDocumentStoredFolder;
		return stored;
	}

	@Override
	public String insert(File file, String... params) throws StorageException {
		FTPWrap ftpWrap = null;
		String relativePath = null;
		PrimaryDocumentStored stored = null;
		try {
			ftpWrap = getWrap();
			relativePath = FILE_SEPARATOR + StorageUtil.getUniqueFileName();
			stored = _createPrimaryDocumentStored(relativePath);

			FileInputStream is = new FileInputStream(file);
			ftpWrap.store(is, stored.folder, stored.fileName);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new StorageException(e.getMessage());
		} finally {
			this.returnWrap(ftpWrap);
		}

		logger.info("File transfered successfully to host.");

		numRetrieve = 0;
		return relativePath;
	}

	@Override
	public String insertInput(InputStream inputStream, String... params) throws StorageException {

		FTPWrap ftpWrap = null;
		String relativePath = null;
		PrimaryDocumentStored stored = null;
		try {
			ftpWrap = getWrap();

			// relativePath = FILE_SEPARATOR + StorageUtil.getUniqueFileName();
			relativePath = buildRelativePath(params);
			stored = _createPrimaryDocumentStored(relativePath);

			ftpWrap.store(inputStream, stored.folder, stored.fileName);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new StorageException(e.getMessage());
		} finally {
			this.returnWrap(ftpWrap);
		}

		logger.info("File transfered successfully to host.");

		numRetrieve = 0;
		String _path = stored.folder + "/" + stored.fileName;
		_path = _path.replaceFirst(ftpWorkingDir, "");
		_path = _path.replaceAll("//", "/");

		return _path;

	}

	protected String buildRelativePath(String... params) throws Exception {
		String ret = null;
		if (params != null && params.length > 0) {
			if (params[0] == "REPLICA") {
				ret = params[1];
			}
		} else {
			ret = FILE_SEPARATOR + StorageUtil.getUniqueFileName();
		}
		return ret;
	}

	@Override
	public String printMessage(Message lMessage) throws StorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) throws StorageException {
		// TODO Auto-generated method stub

	}

	@Override
	public InputStream retrieve(String id) throws StorageException {
		FTPWrap ftpWrap = null;
		InputStream is = null;
		try {
			ftpWrap = getWrap();
			is = ftpWrap.extract(id);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new StorageException(e.getMessage());
		} finally {
			this.returnWrap(ftpWrap);
		}

		logger.info("File transfered successfully to host.");
		return is;
	}

	@Override
	public File retrieveFile(String id) throws StorageException {
		FTPWrap ftpWrap = null;
		InputStream is = null;
		String relativePath = null;
		try {
			ftpWrap = getWrap();
			is = ftpWrap.extract(id);
			this.getTemporaryRepository().storeInputStreamToRelativeFile(is, relativePath);
			logger.info("File transfered successfully to host.");
			return (relativePath != null) ? this.getTemporaryRepository().retrieveFileFromRelativePath(id) : null;
		} catch (Throwable e) {
			e.printStackTrace();
			throw new StorageException(e.getMessage());
		} finally {
			this.returnWrap(ftpWrap);
		}

	}

	@Override
	public File retrievRealFile(String id) throws StorageException {
		return retrieveFile(id);
	}

	@Override
	public String writeFileItem(FileItem fileItem) throws StorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIdStorage(String idStorage) {
		this.idStorage = idStorage;
	}

	@Override
	public void setUtilizzatoreStorage(String idUtilizzatore) {
		// TODO Auto-generated method stub

	}

	public TemporaryRepository getTemporaryRepository() {
		return temporaryRepository;
	}

	public void setTemporaryRepository(TemporaryRepository temporaryRepository) {
		this.temporaryRepository = temporaryRepository;
	}

	class PrimaryDocumentStored {

		String fileName;
		File file;
		String folder;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (pool != null) {
			pool.clear();
			pool.close();
			logger.info("Closed pool FTPStorage");
		}

	}

}
