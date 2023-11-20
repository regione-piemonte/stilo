/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;

import java.io.File;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

public class FTPUtil {

	private static final Logger logger = Logger.getLogger(FTPUtil.class.getName());
	
	public static synchronized boolean uploadFiles(String subDir, List<File> files, List<String> fileNames, String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, int numMaxTentativi) throws Exception {
		FTPClient client = null;
		try {
			client = connectFTP(ftpHost, ftpPort, ftpDir, userName, pwd, numMaxTentativi);
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

	public static File getFile(String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, int numMaxTentativi, String fileRemoto, String fileLocale) throws Exception {
		FTPClient client = null;
		try {
			client = connectFTP(ftpHost, ftpPort, ftpDir, userName, pwd, numMaxTentativi);
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

	public static File[] getAllFile(String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, int numMaxTentativi, String pathTemp, String path) throws Exception {
		FTPClient client = null;
		try {
			client = connectFTP(ftpHost, ftpPort, ftpDir, userName, pwd, numMaxTentativi);
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

	public static void deleteFile(String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, int numMaxTentativi, String fileRemoto, String path) throws Exception {
		FTPClient client = null;
		try {
			client = connectFTP(ftpHost, ftpPort, ftpDir, userName, pwd, numMaxTentativi);
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

	private static FTPClient connectFTP(String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, int numMaxTentativi) throws Exception {
		traceDebug("Inizio metodo connectFTP; host dove collegarsi: [" + ftpHost + "] [" + ftpPort + "]; username: [" + userName + "] password: [" + pwd + "]");
		if (!StringUtils.hasLength(ftpHost)) {
			throw new Exception("Passato un host nullo. Impossibile connettersi");
		}
		if (!StringUtils.hasLength(userName) || !StringUtils.hasLength(pwd)) {
			throw new Exception("Passato una username o una password nulla. Impossibile connettersi al server ftp");
		}
		FTPClient client = new FTPClient();
		boolean connect = false;
		int qtaTentativi = 0;
		while (!connect && qtaTentativi < numMaxTentativi) {
			try {
				traceDebug("Tentativo connessione " + (qtaTentativi + 1) + "...");
				if(ftpPort != null && !"".equals(ftpPort)) {
					client.connect(ftpHost, Integer.parseInt(ftpPort));
				} else {
					client.connect(ftpHost);
				}
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
				traceDebug("Autenticazione sul server ftp fallita. Impossibile proseguire con il trasferimento del file");
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