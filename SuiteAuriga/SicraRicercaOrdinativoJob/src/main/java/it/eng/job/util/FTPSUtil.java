/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.ConnectException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

public class FTPSUtil {

	private static final Logger logger = Logger.getLogger(FTPSUtil.class.getName());
	
	public static synchronized boolean uploadFiles(String subDir, List<File> files, List<String> fileNames, String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, String ftpsKeyStoreFilename, String ftpsKeyStorePassword, int numMaxTentativi) throws Exception {
		FTPClient client = null;
		try {
			client = connectFTP(ftpHost, ftpPort, ftpDir, userName, pwd, ftpsKeyStoreFilename, ftpsKeyStorePassword, numMaxTentativi);
			traceDebug("Aperto il canale ftp");
			if(subDir != null && !"".equals(subDir)) {				
				try {
					client.createDirectory(subDir);
					traceDebug("Creata la directory " + subDir);
				} catch (Exception e) {
					traceDebug("La directory " + subDir + "esiste già");
				}
				client.changeDirectory(subDir);
			}
			for (int i = 0; i < files.size(); i++) {
				uploadFile(client, files.get(i), fileNames.get(i));
			}
			return true;
		} catch (Exception e) {
			logger.error("Errore nella connessione/invio: " + e.getMessage(), e);	
			return false;			
		} finally {
			if (client != null) {				
				client.disconnect(true);
				traceDebug("Chiuso il canale ftp");
			}
		}
	}	

	public static File getFile(String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, String ftpsKeyStoreFilename, String ftpsKeyStorePassword, int numMaxTentativi, String fileRemoto, String fileLocale) throws Exception {
		FTPClient client = null;
		try {
			client = connectFTP(ftpHost, ftpPort, ftpDir, userName, pwd, ftpsKeyStoreFilename, ftpsKeyStorePassword, numMaxTentativi);
			traceDebug("Aperto il canale ftp");
			getFile(client, fileRemoto, fileLocale);
			return new File(fileLocale);
		} finally {
			if (client != null) {
				client.logout();
				client.disconnect(true);
				traceDebug("Chiuso il canale ftp");
			}
		}
	}

	public static File[] getAllFile(String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, String ftpsKeyStoreFilename, String ftpsKeyStorePassword, int numMaxTentativi, String pathTemp, String path) throws Exception {
		FTPClient client = null;
		try {
			client = connectFTP(ftpHost, ftpPort, ftpDir, userName, pwd, ftpsKeyStoreFilename, ftpsKeyStorePassword, numMaxTentativi);
			traceDebug("Aperto il canale ftp");
			return getFiles(client, pathTemp, path);
		} finally {
			if (client != null) {
				client.logout();
				client.disconnect(true);
				traceDebug("Chiuso il canale ftp");
			}
		}
	}

	public static void deleteFile(String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, String ftpsKeyStoreFilename, String ftpsKeyStorePassword, int numMaxTentativi, String fileRemoto, String path) throws Exception {
		FTPClient client = null;
		try {
			client = connectFTP(ftpHost, ftpPort, ftpDir, userName, pwd, ftpsKeyStoreFilename, ftpsKeyStorePassword, numMaxTentativi);
			traceDebug("Aperto il canale ftp");
			deleteFile(client, fileRemoto,path);
		} finally {
			if (client != null) {
				client.logout();
				client.disconnect(true);
				traceDebug("Chiuso il canale ftp");
			}
		}
	}
	
	private static FTPClient connectFTP(String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, String ftpsKeyStoreFilename, String ftpsKeyStorePassword, int numMaxTentativi) throws Exception {
		traceDebug("Inizio metodo connectFTP; host dove collegarsi: [" + ftpHost + "] [" + ftpPort + "]; username: [" + userName + "] password: [" + pwd + "]");
		if (!StringUtils.hasLength(ftpHost)) {
			throw new Exception("Passato un host nullo. Impossibile connettersi");
		}
		if (!StringUtils.hasLength(userName) || !StringUtils.hasLength(pwd)) {
			throw new Exception("Passato una username o una password nulla. Impossibile connettersi al server ftp");
		}
		TrustManager[] trustManager = new TrustManager[] { 
			new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) {
					
				}
				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) {
					
				}
			} 
		};		
		InputStream fin = null;
		traceDebug("Carico il keystore da " + ftpsKeyStoreFilename + " (" + ftpsKeyStorePassword + ")");
		try {
			fin = new FileInputStream(ftpsKeyStoreFilename); 
		} catch(Exception e1) {
			traceDebug("Non trovo il file! Carico il keystore dal classpath");
			fin = FTPClient.class.getClassLoader().getResourceAsStream("keystore.jks");			
		}		
		KeyStore keyStore = KeyStore.getInstance("jks"); 
		if(fin != null) {
			keyStore.load(fin, ftpsKeyStorePassword.toCharArray());
			fin.close();
		}
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("SSL");
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyManagerFactory.init(keyStore, ftpsKeyStorePassword.toCharArray());				
			sslContext.init(keyManagerFactory.getKeyManagers(), trustManager, new SecureRandom());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);			
		}
		SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
		FTPClient client = new FTPClient();					
		boolean connect = false;
		int qtaTentativi = 0;
		traceDebug("numMaxTentativi " + numMaxTentativi);
		while (!connect && qtaTentativi < numMaxTentativi) {
			try {
				traceDebug("Tentativo connessione " + (qtaTentativi + 1) + "...");
				client.setSSLSocketFactory(sslSocketFactory);
				client.setSecurity(FTPClient.SECURITY_FTPES);		
				if(ftpPort != null && !"".equals(ftpPort)) {
					traceDebug("Porta " + ftpPort);
					client.connect(ftpHost, Integer.parseInt(ftpPort));
				} else {
					client.connect(ftpHost);
				}
				traceDebug("Connesso!");
				
				connect = true;
			} catch (ConnectException ce) {
				logger.error(ce.getMessage(), ce);
				traceDebug("Tentativo connessione " + (qtaTentativi + 1) + " fallito");
				connect = false;
			}
			qtaTentativi++;
		}
		if (!connect) {
			traceDebug("Connessione al server ftp non effettuata");
		} else {
			boolean logged = false;
			traceDebug("Connessione al server ftp inviata");
			traceDebug("Mi autentico al server...");
			try {
				client.login(userName, pwd);
				logged = true;
			} catch (ConnectException ce) {
				logger.error(ce.getMessage(), ce);
				traceDebug("Autenticazione fallita");
			}
			if (logged) {
				traceDebug("Autenticazione avvenuta");
			} else {
				logger.error("Autenticazione sul server ftp fallita. Impossibile proseguire con il trasferimento del file");
				throw new Exception("Autenticazione sul server ftp fallita. Impossibile proseguire con il trasferimento del file");
			}
		}		
		if(ftpDir != null && !"".equals(ftpDir)) {
			client.changeDirectory(ftpDir);			
		}
		return client;
	}

	private static synchronized void uploadFile(FTPClient client, File file, String fileName) throws Exception {
		traceDebug("Trasferimento file " + file.getPath());
		client.upload(file);
		traceDebug("File " + file.getName() + " trasferito");
		if(fileName != null && !"".equals(fileName)) {
			try {
				client.deleteFile(fileName);
				traceDebug("File " + fileName + " cancellato");
			} catch(Exception e) {
//				logger.error(e.getMessage(), e);
			}
			client.rename(file.getName(), fileName);
			traceDebug("File " + file.getName() + " rinominato in " + fileName);			
		}		
	}
	
	private static File getFile(FTPClient client, String fileRemoto, String pathFileLocale) throws Exception {
		traceDebug("Inizio metodo getFile; fileRemoto: [" + fileRemoto + "] fileLocale: [" + pathFileLocale + "]");
		if (client == null) {
			throw new Exception("Passato un canale nullo. Impossibile connettersi");
		}
		if (!StringUtils.hasLength(fileRemoto)) {
			throw new Exception("Passato un fileRemoto nullo");
		}
		if (!StringUtils.hasLength(pathFileLocale)) {
			throw new Exception("Passato un fileLocale nullo");
		}
		File fileLocale = new File(pathFileLocale);
		client.download(fileRemoto, fileLocale);
		traceDebug("File recuperato");
		return fileLocale;
	}

	private static File[] getFiles(FTPClient client, String pathTemp, String path) throws Exception {
		traceDebug("Inizio metodo getFiles");
		List<File> listaFile = new ArrayList<File>();
		if (client == null) {
			throw new Exception("Passato un canale chiuso. Impossibile connettersi");
		}
		FTPFile[] listRemoteFiles = client.list(path);
		for (int i = 0; i < listRemoteFiles.length; i++) {
			FTPFile ftpFile = listRemoteFiles[i];
			String filename = ftpFile.getName();
			if (ftpFile.getType() == FTPFile.TYPE_FILE && !"..".equals(filename) && !".".equals(filename)) {
				getFile(client, filename, pathTemp + filename);
				listaFile.add(new File(pathTemp + filename));
			}
		}
		traceDebug("File recuperati: " + listaFile.size());
		return listaFile.toArray(new File[listaFile.size()]);
	}

	private static void deleteFile(FTPClient client, String fileRemoto, String path) throws Exception {
		traceDebug("Inizio metodo deleteFile; fileRemoto: [" + fileRemoto + "]");
		if (client == null) {
			throw new Exception("Passato un canale nullo. Impossibile connettersi");
		}
		if (!StringUtils.hasLength(fileRemoto)) {
			throw new Exception("Passato un fileRemoto nullo");
		}
		client.deleteFile(path + fileRemoto);
		traceDebug("File " + (path + fileRemoto) + " cancellato");
	}

	private static void traceDebug(String message) {
		if (logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}
	
}