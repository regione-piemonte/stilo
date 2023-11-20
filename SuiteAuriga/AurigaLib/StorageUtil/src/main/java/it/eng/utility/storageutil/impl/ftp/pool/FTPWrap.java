/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.log4j.Logger;

public class FTPWrap {

	public final static String FTP = "FTP";
	public final static String SSL = "SSL";
	public final static String TLS = "TLS";

	private Logger logger = Logger.getLogger(FTPWrap.class);

	private String ftpUsername = null;
	private String ftpPassword = null;
	private String ftpHost = null;
	private int ftpPort = 21;
	private int timeout = 60000;
	private String protocol;
	private boolean isDebugEnabled;

	private boolean overwrite;
	private boolean passiveMode;

	private String workingDir;

	private FTPClient ftpClient = null;

	boolean isFTPS = false;

	private boolean isInitialized = false;

	public FTPWrap(String ftpUsername, String ftpPassword, String ftpHost, int ftpPort, int timeout, String protocol, boolean overwrite, boolean passiveMode,
			String workingDir, boolean isDebugEnabled) throws Exception {
		this.ftpUsername = ftpUsername;
		this.ftpPassword = ftpPassword;
		this.ftpHost = ftpHost;
		this.ftpPort = ftpPort;
		this.timeout = timeout;
		this.protocol = protocol;
		this.overwrite = overwrite;
		this.passiveMode = passiveMode;
		this.workingDir = workingDir;
		this.isDebugEnabled = isDebugEnabled;
	}

	protected void init() throws Exception {

		if (this.protocol.equalsIgnoreCase(FTP)) {
			ftpClient = new FTPClient();
		} else if (this.protocol.equalsIgnoreCase(SSL) || this.protocol.equalsIgnoreCase(TLS)) {
			ftpClient = new FTPSClient(this.protocol, true);
			isFTPS = true;
			// ((FTPSClient) ftpClient).setEnabledCipherSuites(new String[] { "SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA" });
			((FTPSClient) ftpClient).setEndpointCheckingEnabled(false);
			if (isDebugEnabled)
				((FTPSClient) ftpClient).addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		} else {
			throw new Exception("Protocollo non idenificato [" + this.protocol + "]");
		}

		if (this.passiveMode)
			ftpClient.enterLocalPassiveMode();
		else
			ftpClient.enterLocalActiveMode();

		// try to connect
		ftpClient.connect(ftpHost, ftpPort);
		// login to server
		if (!ftpClient.login(ftpUsername, ftpPassword)) {
			ftpClient.logout();
			throw new Exception("Login non valida");
		}

		int reply = ftpClient.getReplyCode();

		// FTPReply stores a set of constants for FTP reply codes.
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
			isInitialized = false;
			throw new Exception("Risposta FTP Server [" + reply + "]");
		} else {
			isInitialized = true;
		}
	}

	protected boolean check() throws Exception {
		return ftpClient.isConnected() && ftpClient.isAvailable() && isInitialized;
	}

	public void extract(OutputStream output, String remotePath, String fileName) throws Exception {
		try {
			this.ftpClient.changeWorkingDirectory(this.workingDir);

			if (this.protocol.equalsIgnoreCase(SSL) || this.protocol.equalsIgnoreCase(TLS)) {
				((FTPSClient) ftpClient).execPBSZ((2 ^ 32) - 1);
				((FTPSClient) ftpClient).execPROT("P");
			}

			// verifica se file esiste
			FTPFile[] files = this.ftpClient.listFiles(remotePath + fileName);

			if (files == null || files.length == 0) {
				throw new Exception("File non trovato [" + remotePath + fileName + "]");
			}

			this.ftpClient.retrieveFile(remotePath + fileName, output);

		} catch (Throwable e) {
			throw new Exception("Errore estrazione [" + e.getMessage() + "]");
		}
	}

	public void extract(OutputStream output, String item) throws Exception {
		try {
			this.ftpClient.changeWorkingDirectory(this.workingDir);

			// verifica se file esiste
			FTPFile[] files = this.ftpClient.listFiles(item);

			if (files == null || files.length == 0) {
				throw new Exception("File non trovato [" + item + "]");
			}

			this.ftpClient.retrieveFile(item, output);

		} catch (Throwable e) {
			throw new Exception("Errore estrazione [" + e.getMessage() + "]");
		}
	}

	public InputStream extract(String remotePath, String fileName) throws Exception {
		InputStream is = null;
		try {
			this.ftpClient.changeWorkingDirectory(this.workingDir);

			// verifica se file esiste
			FTPFile[] files = this.ftpClient.listFiles(this.workingDir + remotePath + fileName);

			if (files == null || files.length == 0) {
				throw new Exception("File non trovato [" + remotePath + fileName + "]");
			}

			is = this.ftpClient.retrieveFileStream(remotePath + fileName);
			return is;
		} catch (Throwable e) {
			throw new Exception("Errore estrazione [" + e.getMessage() + "]");
		}
	}

	public InputStream extract(String item) throws Exception {
		InputStream is = null;
		try {
			// this.ftpClient.changeWorkingDirectory(this.workingDir);
			if (this.protocol.equalsIgnoreCase(SSL) || this.protocol.equalsIgnoreCase(TLS)) {
				((FTPSClient) ftpClient).execPBSZ((2 ^ 32) - 1);
				((FTPSClient) ftpClient).execPROT("P");
			}

			// verifica se file esiste
			FTPFile[] files = this.ftpClient.listFiles(this.workingDir + item);

			if (files == null || files.length == 0) {
				throw new Exception("File non trovato [" + item + "]");
			}

			is = this.ftpClient.retrieveFileStream(this.workingDir + item);
			return is;
		} catch (Throwable e) {
			throw new Exception("Errore estrazione [" + e.getMessage() + "]");
		}
	}

	public boolean store(InputStream is, String remotePath, String fileName) throws Exception {
		boolean ret = false;

		try {

			if (this.protocol.equalsIgnoreCase(SSL) || this.protocol.equalsIgnoreCase(TLS)) {
				((FTPSClient) ftpClient).execPBSZ((2 ^ 32) - 1);
				((FTPSClient) ftpClient).execPROT("P");
			}

			// verifica se file esiste
			FTPFile[] files = this.ftpClient.listFiles(remotePath + fileName);

			if (files != null && files.length == 1) {
				if (overwrite)
					this.ftpClient.deleteFile(remotePath + fileName);
				else
					return ret;
			}

			boolean directoryExists = this.ftpClient.changeWorkingDirectory(remotePath);

			if (!directoryExists) {
				this.ftpClient.makeDirectory(remotePath);
				this.ftpClient.changeWorkingDirectory(remotePath);
			}

			this.ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);// binary files

			// if (!isFTPS)
			this.ftpClient.storeFile(remotePath + fileName, is);
			// else
			// this.ftpClient.storeFile(fileName, is);

			// verifica se scrittura ии stata eseguita

			// if (!isFTPS)
			files = this.ftpClient.listFiles(remotePath + fileName);
			// else
			// files = this.ftpClient.listFiles(fileName);
			if (files != null && files.length == 1) {
				ret = true;
			} else {
				throw new Exception("File non scritto [" + remotePath + fileName + "]");
			}

			return ret;

		} catch (Throwable e) {
			throw new Exception("Errore scrittura [" + e.getMessage() + "]");
		}
	}

	public void close() {
		try {
			ftpClient.logout();
			ftpClient.disconnect();
			isInitialized = false;
		} catch (Throwable e) {
			logger.warn("Erroe chiusura connessione [" + this.protocol + "] [" + e.getMessage() + "]");
		}

	}

	public boolean removeSingleFile(String fileToDelete) throws Exception {
		try {
			boolean deleted = ftpClient.deleteFile(fileToDelete);
			if (deleted) {
				logger.debug("The file was deleted successfully.");
			} else {
				logger.error("Could not delete the file.");
			}
			return deleted;
		} catch (Throwable ex) {
			throw new Exception("Errore eliminazione file " + ex.getMessage());
		}
	}

	public void removeDirectory(String parentDir, String currentDir) throws Exception {
		String dirToList = parentDir;
		if (!currentDir.equals("")) {
			dirToList += "/" + currentDir;
		}

		FTPFile[] subFiles = ftpClient.listFiles(dirToList);

		if (subFiles != null && subFiles.length > 0) {
			for (FTPFile aFile : subFiles) {
				String currentFileName = aFile.getName();
				if (currentFileName.equals(".") || currentFileName.equals("..")) {
					// skip parent directory and the directory itself
					continue;
				}
				String filePath = parentDir + "/" + currentDir + "/" + currentFileName;
				if (currentDir.equals("")) {
					filePath = parentDir + "/" + currentFileName;
				}

				if (aFile.isDirectory()) {
					// remove the sub directory
					removeDirectory(dirToList, currentFileName);
				} else {
					// delete the file
					boolean deleted = ftpClient.deleteFile(filePath);
					if (deleted) {
						logger.debug("DELETED the file: " + filePath);
					} else {
						logger.error("CANNOT delete the file: " + filePath);
					}
				}
			}

			// finally, remove the directory itself
			boolean removed = ftpClient.removeDirectory(dirToList);
			if (removed) {
				logger.debug("REMOVED the directory: " + dirToList);
			} else {
				logger.error("CANNOT remove the directory: " + dirToList);
			}
		}
	}

	public void downloadDirectory(String parentDir, String currentDir, String saveDir) throws Exception {
		String dirToList = parentDir;
		if (!currentDir.equals("")) {
			dirToList += "/" + currentDir;
		}

		FTPFile[] subFiles = ftpClient.listFiles(dirToList);

		if (subFiles != null && subFiles.length > 0) {
			for (FTPFile aFile : subFiles) {
				String currentFileName = aFile.getName();
				if (currentFileName.equals(".") || currentFileName.equals("..")) {
					// skip parent directory and the directory itself
					continue;
				}
				String filePath = parentDir + "/" + currentDir + "/" + currentFileName;
				if (currentDir.equals("")) {
					filePath = parentDir + "/" + currentFileName;
				}

				String newDirPath = saveDir + parentDir + File.separator + currentDir + File.separator + currentFileName;
				if (currentDir.equals("")) {
					newDirPath = saveDir + parentDir + File.separator + currentFileName;
				}

				if (aFile.isDirectory()) {
					// create the directory in saveDir
					File newDir = new File(newDirPath);
					boolean created = newDir.mkdirs();
					if (created) {
						logger.debug("CREATED the directory: " + newDirPath);
					} else {
						logger.error("COULD NOT create the directory: " + newDirPath);
					}

					// download the sub directory
					downloadDirectory(dirToList, currentFileName, saveDir);
				} else {
					// download the file
					boolean success = downloadSingleFile(filePath, newDirPath);
					if (success) {
						logger.debug("DOWNLOADED the file: " + filePath);
					} else {
						logger.error("COULD NOT download the file: " + filePath);
					}
				}
			}
		}
	}

	public boolean downloadSingleFile(String remoteFilePath, String savePath) throws IOException {
		File downloadFile = new File(savePath);

		File parentDir = downloadFile.getParentFile();
		if (!parentDir.exists()) {
			parentDir.mkdir();
		}

		OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
		try {
			ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
			return ftpClient.retrieveFile(remoteFilePath, outputStream);
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	public String getFtpUsername() {
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getFtpHost() {
		return ftpHost;
	}

	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public boolean isOverwrite() {
		return overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}

	public boolean isPassiveMode() {
		return passiveMode;
	}

	public void setPassiveMode(boolean passiveMode) {
		this.passiveMode = passiveMode;
	}

	public FTPClient getFtpClient() {
		return ftpClient;
	}

}
