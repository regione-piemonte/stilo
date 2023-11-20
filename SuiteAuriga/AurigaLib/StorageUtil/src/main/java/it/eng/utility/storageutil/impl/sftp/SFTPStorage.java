/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.storageutil.Storage;
import it.eng.utility.storageutil.TemporaryRepository;
import it.eng.utility.storageutil.annotations.TipoStorage;
import it.eng.utility.storageutil.enums.StorageType;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.impl.TemporaryRepositoryImpl;
import it.eng.utility.storageutil.impl.filesystem.DateFileSystemStoragePolicy;
import it.eng.utility.storageutil.impl.filesystem.FileSystemStoragePolicy;
import it.eng.utility.storageutil.impl.sftp.pool.SFTPWrap;
import it.eng.utility.storageutil.impl.sftp.pool.SFTPWrapFactory;
import it.eng.utility.storageutil.impl.sftp.pool.SFTPWrapPool;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.storageutil.util.XMLUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@TipoStorage(tipo = StorageType.SFTP)
public class SFTPStorage implements Storage {

	protected final static int NRO_MAX_FILES = 100;

	protected final static int POOL_MAX_IDLE = 5;
	protected final static int POOL_MIN_IDLE = 2;
	protected final static int POOL_MAX_TOTAL = 5;

	public final static String FILE_SEPARATOR = "/";
	private static Logger logger = Logger.getLogger(SFTPStorage.class);

	protected TemporaryRepository temporaryRepository;

	private String sftpHost = null;
	private int sftpPort = 22;
	private String sftpUsername = null;
	private String sftpPassword = null;
	private String sftpWorkingDir = null;

	private int numRetrieve = 0;
	private int maxNumRetrieve;
	protected int nroMaxFiles;

	private int poolMaxIdle;
	private int poolMaxTotal;
	private int poolMinIdle;
	private int sftpTimeout;

	private SFTPWrapPool pool;

	protected FileSystemStoragePolicy storagePolicy;
	private SFTPStorageConfig config = null;

	@Override
	public void configure(String xmlConfig) throws StorageException {
		try {
			config = (SFTPStorageConfig) XMLUtil.newInstance().deserialize(xmlConfig, SFTPStorageConfig.class);

			if (config != null) {
				this.temporaryRepository = new TemporaryRepositoryImpl(config.getTempRepositoryBasePath());
				this.sftpHost = _checkConfigParam("SFTP Host", config.getHost(), true);
				this.sftpPort = config.getPort();
				this.sftpPassword = _checkConfigParam("SFTP Password", config.getPassword(), true);
				this.sftpWorkingDir = _checkConfigParam("SFTP Working Dir", config.getWorkingDir(), true);
				this.sftpUsername = _checkConfigParam("SFTP Username", config.getUsername(), true);
				this.maxNumRetrieve = config.getMaxNumRetrieve();
				this.storagePolicy = new DateFileSystemStoragePolicy();
				this.nroMaxFiles = config.getNroMaxFiles() > 0 ? config.getNroMaxFiles() : NRO_MAX_FILES;

				this.poolMaxIdle = config.getMaxIdle() > 0 ? config.getMaxIdle() : POOL_MAX_IDLE;
				this.poolMinIdle = config.getMinIdle() > 0 ? config.getMinIdle() : POOL_MIN_IDLE;
				this.poolMaxTotal = config.getMaxTotal() > 0 ? config.getMaxTotal() : POOL_MAX_TOTAL;
				this.sftpTimeout = config.getTimeout();

				this.init();
			}
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	private void init() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(this.poolMaxIdle);
		config.setMaxTotal(this.poolMaxTotal);
		config.setMinIdle(this.poolMinIdle);

		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		SFTPWrapFactory sftpWrapFactory = new SFTPWrapFactory(this.sftpUsername, this.sftpPassword, this.sftpHost,
				this.sftpPort, this.sftpTimeout);
		pool = new SFTPWrapPool(sftpWrapFactory, config);
	}

	private SFTPWrap getWrap() throws Exception {
		return pool.borrowObject();
	}

	private void returnWrap(SFTPWrap wrap) {
		pool.returnObject(wrap);
	}

	private JSch jsch = null;
	private Session session = null;

	@Deprecated
	private ChannelSftp _init() throws Exception {

		Channel channel = null;
		ChannelSftp channelSftp = null;

		if (session == null || !session.isConnected()) {
			this.jsch = new JSch(); // TODO
			session = jsch.getSession(this.sftpUsername, this.sftpHost, this.sftpPort);
			session.setPassword(this.sftpPassword);
			java.util.Properties _config = new java.util.Properties();
			_config.put("StrictHostKeyChecking", "no");
			session.setConfig(_config);
			session.connect();
		}

		channel = session.openChannel("sftp");
		channel.connect(360000);
		logger.info("sftp channel opened and connected.");
		channelSftp = (ChannelSftp) channel;

		return channelSftp;
	}

	private void close(ChannelSftp channelSftp) {
		if (channelSftp != null)
			channelSftp.exit();
		session.disconnect();

	}

	@Override
	public String insert(File file, String... params) throws StorageException {

		String relativePath = null;
		PrimaryDocumentStored stored = null;
		try {
			relativePath = FILE_SEPARATOR + StorageUtil.getUniqueFileName();
			stored = _createPrimaryDocumentStored(relativePath);
		} catch (Throwable e) {
			throw new StorageException(e.getMessage());
		}
		
		try {
			_createDir(stored.folder);	
		} catch (Throwable e) {}
		
		
		_insert(file, stored.fileName, stored.folder);

		logger.info("File transfered successfully to host.");

		numRetrieve = 0;

		String _path = stored.folder + "/" + stored.fileName;
		_path = _path.replaceFirst(sftpWorkingDir, "");
		_path = _path.replaceAll("//", "/");

		return _path;
	}

	private void cleanTemporaryRepository(String relativePath) {
		try {
			temporaryRepository.deleteRelativeFile(relativePath);
		} catch (Throwable e1) {
		}
	}

	@Override
	public String insertInput(InputStream inputStream, String... params) throws StorageException {
		String relativePath = null;
		PrimaryDocumentStored stored;
		InputStream is = null;

		try {
			// relativePath = FILE_SEPARATOR + StorageUtil.getUniqueFileName();
			relativePath = buildRelativePath(params);
			this.temporaryRepository.storeInputStreamToRelativeFile(inputStream, relativePath);
			is = temporaryRepository.retrieveInputStreamFromRelativePath(relativePath);
			stored = _createPrimaryDocumentStored(relativePath);
		} catch (Throwable e) {
			cleanTemporaryRepository(relativePath);
			throw new StorageException(e.getMessage());
		}

		String _path = null;

		try {
			try {
				_createDir(stored.folder);				
			} catch (Throwable e) {}
						
			_insert(is, stored.fileName, stored.folder);

			_path = stored.folder + "/" + stored.fileName;
			_path = _path.replaceFirst(sftpWorkingDir, "");
			_path = _path.replaceAll("//", "/");

		} finally {
			cleanTemporaryRepository(relativePath);
		}

		return _path;
		// return relativePath;
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
		String relativePath = null;
		ByteArrayInputStream is = null;
		String _path = FILE_SEPARATOR; 
		File file = null;
		if (this.config.getTempRepositoryBasePath() != null) {

			if (this.config.getTempRepositoryBasePath().endsWith(FILE_SEPARATOR)) {
				_path = this.config.getTempRepositoryBasePath() + StorageUtil.getUniqueFileName();
			} else {
				_path = this.config.getTempRepositoryBasePath() + FILE_SEPARATOR;
			}
		}
		
		try {
			relativePath = _path + StorageUtil.getUniqueFileName();
			file = new File(relativePath);
			// FileOutputStream out = new FileOutputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			_retrieve(id, out);
			is = new ByteArrayInputStream(out.toByteArray());
		} catch (Throwable e) {
			logger.error("Errore retrieve [" + e.getMessage() + "]");
			throw new StorageException(e.getMessage());
		}

		return is;
	}

	@Override
	public File retrieveFile(String id) throws StorageException {
		File file = null;
		String relativePath = null;
		ByteArrayInputStream is = null;
		try {
			relativePath = FILE_SEPARATOR + StorageUtil.getUniqueFileName();
			// logger.debug("File sara estratto in ["+ relativePath+"]");
			// file = new File( relativePath );
			// FileOutputStream out = new FileOutputStream(file);
			// _retrieve(id, out);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			_retrieve(id, out);
			is = new ByteArrayInputStream(out.toByteArray());
			this.temporaryRepository.storeInputStreamToRelativeFile(is, relativePath);
			return (relativePath != null) ? this.temporaryRepository.retrieveFileFromRelativePath(relativePath) : null;
		} catch (Throwable e) {
			String errMessage = "Errore retrieve [" + e.getMessage() + "][" + id + "][" + relativePath + "]";
			logger.error(errMessage);
			throw new StorageException(errMessage);
		}

		// return file;
	}

	@Override
	public File retrievRealFile(String id) throws StorageException {
		// //TODO Auto-generated method stub
		return this.retrieveFile(id);
	}

	@Override
	public String writeFileItem(FileItem fileItem) throws StorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIdStorage(String idStorage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUtilizzatoreStorage(String idUtilizzatore) {
		// TODO Auto-generated method stub

	}

	/*****************************************************************/

	public void _createDir(String path) {
		SFTPWrap sftpWrap = null;
		try {
			sftpWrap = this.getWrap();
			ChannelSftp channelSftp = sftpWrap.getChannelSftp();

			String[] folders = path.split("/");
			if (folders[0].isEmpty())
				folders[0] = "/";
			String fullPath = folders[0];
			for (int i = 1; i < folders.length; i++) {
				Vector<?> ls = channelSftp.ls(fullPath);
				boolean isExist = false;
				for (Object o : ls) {
					if (o instanceof LsEntry) {
						LsEntry e = (LsEntry) o;
						if (e.getAttrs().isDir() && e.getFilename().equals(folders[i])) {
							isExist = true;
						}
					}
				}
				if (!isExist && !folders[i].isEmpty()) {
					channelSftp.mkdir(fullPath + folders[i]);
				}
				fullPath = fullPath + folders[i] + "/";
			}
		} catch (Throwable e) {
			logger.warn("Errore _createDir [" + e.getMessage() + "]["+path+"]");
		} finally {
			this.returnWrap(sftpWrap);
		}
	}

	protected void _insert(File file, String fileName, String directory) throws StorageException {
		SFTPWrap sftpWrap = null;
		try {
			sftpWrap = this.getWrap();
			ChannelSftp channelSftp = sftpWrap.getChannelSftp();
			channelSftp.cd(directory);
			channelSftp.put(new FileInputStream(file), fileName.substring(1));
			numRetrieve = 0;
		} catch (FileNotFoundException e) {
			throw new StorageException(e);
		} catch (Throwable e) {
			logger.warn("Errore [" + e.getMessage() + "] Numero retry [" + numRetrieve + "]/[" + maxNumRetrieve + "]");
			if (numRetrieve <= maxNumRetrieve) {
				try {
					numRetrieve++;
					Thread.currentThread().sleep(10000);
					this._insert(file, fileName, directory);
				} catch (Throwable e1) {
					logger.error("Errore Retrieve [" + e1.getMessage() + "]");
				}
			} else {
				logger.fatal("Numero massimo di tentativi raggiunto [" + fileName + "]");
			}
			throw new StorageException(e.getMessage());
		} finally {
			if (numRetrieve == 0 || numRetrieve <= maxNumRetrieve) {
				this.returnWrap(sftpWrap);
			}
		}
	}

	// protected void _retrieve(String src, FileOutputStream out) throws
	// StorageException {
	protected void _retrieve(String src, ByteArrayOutputStream out) throws StorageException {

		SFTPWrap sftpWrap = null;
		try {
			sftpWrap = this.getWrap();
			ChannelSftp channelSftp = sftpWrap.getChannelSftp();
			PrimaryDocumentStored pds = _createPrimaryDocumentStored(src);
			logger.debug("pds  folder:  " + pds.folder + ": fileName:" + pds.fileName);
			channelSftp.cd(pds.folder);
			channelSftp.get(pds.fileName, out);
			numRetrieve = 0;
			logger.debug("Retrieved inputStream");
		} catch (FileNotFoundException e) {
			logger.error("Errore [" + e.getMessage() + "]");
			throw new StorageException(e);
		} catch (Throwable e) {
			logger.warn("Errore [" + e.getMessage() + "] Numero retry [" + numRetrieve + "]/[" + maxNumRetrieve + "]");
			if (numRetrieve <= maxNumRetrieve) {
				try {
					numRetrieve++;
					Thread.currentThread().sleep(10000);
					this._retrieve(src, out);
				} catch (Throwable e1) {
					logger.error("Errore Retrieve [" + e1.getMessage() + "]");
				}
			} else {
				logger.fatal("Numero massimo di tentativi raggiunto [" + src + "]");
			}
			throw new StorageException(e.getMessage());
		} finally {
			if (numRetrieve == 0 || numRetrieve <= maxNumRetrieve) {
				this.returnWrap(sftpWrap);
			}
		}
	}

	protected void _retrieve(String src, OutputStream out) throws StorageException {
		SFTPWrap sftpWrap = null;
		try {
			sftpWrap = this.getWrap();
			ChannelSftp channelSftp = sftpWrap.getChannelSftp();

			PrimaryDocumentStored pds = _createPrimaryDocumentStored(src);
			channelSftp.cd(pds.folder + "/");
			channelSftp.get(pds.fileName, out);

			numRetrieve = 0;
		} catch (FileNotFoundException e) {
			logger.error("Errore Retrieve [" + e.getMessage() + "]");
			throw new StorageException(e);
		} catch (Throwable e) {
			logger.warn("Errore [" + e.getMessage() + "] Numero retry [" + numRetrieve + "]/[" + maxNumRetrieve + "]");
			if (numRetrieve <= maxNumRetrieve) {
				try {
					numRetrieve++;
					Thread.currentThread().sleep(10000);
					this._retrieve(src, out);
				} catch (Throwable e1) {
					logger.error("Errore Retrieve [" + e1.getMessage() + "]");
				}
			} else {
				logger.fatal("Numero massimo di tentativi raggiunto [" + src + "]");
			}
			throw new StorageException(e.getMessage());
		} finally {
			if (numRetrieve == 0 || numRetrieve <= maxNumRetrieve) {
				this.returnWrap(sftpWrap);
			}
		}
	}

	protected void _insert(InputStream input, String fileName, String directory) throws StorageException {

		try {
			if (input.available() <= 0) {
				throw new StorageException("Inputstream di dimensione 0.");
			}
		} catch (IOException e2) {
			throw new StorageException(e2);
		}

		SFTPWrap sftpWrap = null;

		try {
			sftpWrap = this.getWrap();

			ChannelSftp channelSftp = sftpWrap.getChannelSftp();

			channelSftp.cd(directory);
			if (fileName.startsWith("/"))
				channelSftp.put(input, fileName.substring(1));
			else
				channelSftp.put(input, fileName);
			numRetrieve = 0;
		} catch (Throwable e) {
			logger.warn("Errore [" + e.getMessage() + "] Numero retry [" + numRetrieve + "]/[" + maxNumRetrieve + "]");
			if (numRetrieve <= maxNumRetrieve) {
				try {
					numRetrieve++;
					Thread.currentThread().sleep(10000);
					this._insert(input, fileName, directory);
				} catch (Throwable e1) {
					logger.error("Errore Retrieve [" + e1.getMessage() + "]");
				}
			} else {
				logger.fatal("Numero massimo di tentativi raggiunto [" + fileName + "]");
			}
			throw new StorageException(e.getMessage());
		} finally {
			if (numRetrieve == 0 || numRetrieve <= maxNumRetrieve) {
				this.returnWrap(sftpWrap);
			}
		}
	}

	protected PrimaryDocumentStored _createPrimaryDocumentStored(String relativePath) throws StorageException {
		String primaryDocumentStoredFolder = null;
		File primaryDocumentStoredFile = null;
		PrimaryDocumentStored stored = new PrimaryDocumentStored();
		try {
			File storageFolder = null;

			if (relativePath.indexOf("/", 2) < 0) {
				storageFolder = storagePolicy.getStorageFolder(this.sftpWorkingDir, nroMaxFiles);
			} else {
				storageFolder = new File(this.sftpWorkingDir);
			}

			logger.debug("identificazione file : " + relativePath + " con storageFolder: "
					+ storageFolder.getAbsolutePath());
			primaryDocumentStoredFile = new File(storageFolder.getAbsolutePath() + FILE_SEPARATOR + relativePath);
			logger.debug("ricavato primaryDocumentStoredFile :" + primaryDocumentStoredFile.getAbsolutePath());
			primaryDocumentStoredFolder = primaryDocumentStoredFile.getParentFile().getAbsolutePath();
			logger.debug("ricavato primaryDocumentStoredFolder :" + primaryDocumentStoredFolder);
			// if (file != null){
			// this.temporaryRepository.storeInputStreamToRelativeFile(new
			// FileInputStream(file), relativePath);
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

	// private void convertNIO(OutputStream os, InputStream is) throws
	// Exception{
	// try {
	// IOUtils.copyLarge(is, os);
	// } catch (Throwable e) {
	// System.out.println("os:"+os +" -- is:"+is);
	// throw new Exception( e.getMessage() );
	// }
	//
	// }
	//
	// private PrimaryDocumentStored extractPrimaryDocumentStored(String uri){
	// PrimaryDocumentStored stored = new PrimaryDocumentStored();
	//
	// int pos = uri.lastIndexOf("/");
	// stored.folder =uri.substring(0, pos);
	// stored.fileName = uri.substring(pos+1);
	// return stored;
	// }

	protected String _checkConfigParam(String configParam, String configValue, boolean returnError) throws Exception {
		String message = null;
		if (configValue == null || configValue.trim().equals("")) {
			message = "SFTP Storage: Configurazione errata: manca proprieta [" + configParam + "]";
			if (returnError) {
				logger.error(message);
				throw new Exception(message);
			} else {
				logger.warn(message);

			}
		} else {
			message = "SFTP Storage: Configurazione [" + configParam + "]->[" + configValue + "]";
			logger.info(message);
		}
		return configValue;
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

	class PrimaryDocumentStored {

		String fileName;
		File file;
		String folder;
	}
}