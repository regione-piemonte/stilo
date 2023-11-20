/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.sshtools.j2ssh.SftpClient;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.authentication.PublicKeyAuthenticationClient;
import com.sshtools.j2ssh.sftp.FileAttributes;
import com.sshtools.j2ssh.sftp.SftpFile;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;
import com.sshtools.j2ssh.transport.publickey.SshPrivateKey;
import com.sshtools.j2ssh.transport.publickey.SshPrivateKeyFile;

public class SFTPUtil {

	private static final Logger logger = Logger.getLogger(SFTPUtil.class.getName());

	public static synchronized boolean uploadFiles(String subDir, List<File> files, List<String> fileNames, String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, int numMaxTentativi) throws Exception {
		SshClient ssh = null;
		try {
			ssh = connectSFTP(ftpHost, ftpPort, ftpDir, userName, pwd, numMaxTentativi);
			SftpClient sftpClient = openSftpClient(ssh, ftpDir);			
			traceDebug("Aperto il canale sftp");
			if(subDir != null && !"".equals(subDir)) {		
				try {
					sftpClient.mkdir(subDir);
					traceDebug("Creata la directory " + subDir);
				} catch (Exception e) {
					traceDebug("La directory " + subDir + "esiste già");
				}				
				sftpClient.cd(subDir);				
			}
			for (int i = 0; i < files.size(); i++) {
				uploadFile(sftpClient, files.get(i), fileNames.get(i));
			}
			return true;
		} catch (Exception e) {
			logger.error("Errore nella connessione/invio: " + e.getMessage(), e);	
			return false;	
		} finally {
			if (ssh != null) {
				ssh.disconnect();
				traceDebug("Chiuso il canale sftp");
			}
		}
	}

	public static File getFile(String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, int numMaxTentativi, String fileRemoto, String fileLocale) throws Exception {
		SshClient ssh = null;
		try {
			ssh = connectSFTP(ftpHost, ftpPort, ftpDir, userName, pwd, numMaxTentativi);
			SftpClient sftpClient = openSftpClient(ssh, ftpDir);
			traceDebug("Aperto il canale sftp");
			getFile(sftpClient, fileRemoto, fileLocale);
			return new File(fileLocale);
		} finally {
			if (ssh != null) {
				ssh.disconnect();
				traceDebug("Chiuso il canale sftp");
			}
		}
	}

	public static File[] getAllFile(String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, int numMaxTentativi, String pathTemp, String path) throws Exception {
		SshClient ssh = null;
		try {
			ssh = connectSFTP(ftpHost, ftpPort, ftpDir, userName, pwd, numMaxTentativi);
			SftpClient sftpClient = openSftpClient(ssh, ftpDir);
			traceDebug("Aperto il canale sftp");
			return getFiles(sftpClient, pathTemp, path);
		} finally {
			if (ssh != null) {
				ssh.disconnect();
				traceDebug("Chiuso il canale sftp");
			}
		}
	}

	public static void deleteFile(String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, int numMaxTentativi, String fileRemoto, String path) throws Exception {
		SshClient ssh = null;
		try {
			ssh = connectSFTP(ftpHost, ftpPort, ftpDir, userName, pwd, numMaxTentativi);
			SftpClient sftpClient = openSftpClient(ssh, ftpDir);
			traceDebug("Aperto il canale sftp");
			deleteFile(sftpClient, fileRemoto, path);
		} finally {
			if (ssh != null) {
				ssh.disconnect();
				traceDebug("Chiuso il canale sftp");
			}
		}
	}

	private static SshClient connectSFTP(String ftpHost, String ftpPort, String ftpDir, String userName, String pwd, int numMaxTentativi) throws Exception {
		traceDebug("Inizio metodo connectSFTP; host dove collegarsi: [" + ftpHost + "] [" + ftpPort + "]; username: [" + userName + "] password: [" + pwd + "]");
		if (!StringUtils.hasLength(ftpHost)) {
			throw new Exception("Passato un host nullo. Impossibile connettersi");
		}
		if (!StringUtils.hasLength(userName) || !StringUtils.hasLength(pwd)) {
			throw new Exception("Passato una username o una password nulla. Impossibile connettersi al server sftp");
		}
		SshClient ssh = new SshClient();
		boolean connect = false;
		int qtaTentativi = 0;
		while (!connect && qtaTentativi < numMaxTentativi) {
			try {
				traceDebug("Tentativo connessione " + (qtaTentativi + 1) + "...");
				if(ftpPort != null && !"".equals(ftpPort)) {					
					ssh.connect(ftpHost, Integer.parseInt(ftpPort), new IgnoreHostKeyVerification());
				} else {
					ssh.connect(ftpHost, new IgnoreHostKeyVerification());
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
			traceDebug("Connessione al server sftp non effettuata");
		} else {
			traceDebug("Connessione al server sftp inviata");
			traceDebug("Mi autentico al server...");
			int autenticationResult = -1;

			PasswordAuthenticationClient pwdClient = new PasswordAuthenticationClient();
			pwdClient.setUsername(userName);
			pwdClient.setPassword(pwd);
			autenticationResult = ssh.authenticate(pwdClient);

			if (autenticationResult == AuthenticationProtocolState.COMPLETE) {
				traceDebug("Autenticazione avvenuta");
			} else if (autenticationResult == AuthenticationProtocolState.FAILED) {
				logger.error("Autenticazione sul server sftp fallita. Impossibile proseguire con il trasferimento del file");
				throw new Exception("Autenticazione sul server sftp fallita. Impossibile proseguire con il trasferimento del file");
			} else if ((autenticationResult == AuthenticationProtocolState.CANCELLED) || (autenticationResult == AuthenticationProtocolState.PARTIAL)) {
				logger.error("Impossibile proseguire con l'upload del file. L'autenticazione è parziale o cancellata");
				throw new Exception("Impossibile proseguire con l'upload del file. L'autenticazione è parziale o cancellata");
			}
		}
		return ssh;
	}
	
	private static SftpClient openSftpClient(SshClient ssh, String ftpDir) throws Exception {
		SftpClient client = ssh.openSftpClient();
		if(ftpDir != null && !"".equals(ftpDir)) {
			client.cd(ftpDir);
		}
		return client;
	}
	
	private static synchronized void uploadFile(SftpClient sftpClient, File file, String fileName) throws Exception {
		traceDebug("Trasferimento file " + file.getPath());
		sftpClient.put(file.getPath());
		traceDebug("File " + file.getName() + " trasferito");
		if(fileName != null && !"".equals(fileName)) {
			try {
				sftpClient.rm(fileName);
				traceDebug("File " + fileName + " cancellato");
			}  catch(Exception e) {
//				logger.error(e.getMessage(), e);			
			}
			sftpClient.rename(file.getName(), fileName);
			traceDebug("File " + file.getName() + " rinominato in " + fileName);
		}
	}

	private static FileAttributes getFile(SftpClient sftp, String fileRemoto, String fileLocale) throws Exception {
		traceDebug("Inizio metodo getFile; fileRemoto: [" + fileRemoto + "] fileLocale: [" + fileLocale + "]");
		if (sftp == null) {
			throw new Exception("Passato un canale nullo. Impossibile connettersi");
		}
		if (!StringUtils.hasLength(fileRemoto)) {
			throw new Exception("Passato un fileRemoto nullo");
		}
		if (!StringUtils.hasLength(fileLocale)) {
			throw new Exception("Passato un fileLocale nullo");
		}
		FileAttributes fileAttributes = sftp.get(fileRemoto, fileLocale);
		traceDebug("File recuperato");
		return fileAttributes;
	}

	@SuppressWarnings("unchecked")
	private static File[] getFiles(SftpClient sftp, String pathTemp, String path) throws Exception {
		traceDebug("Inizio metodo getFiles");
		List<File> listaFile = new ArrayList<File>();
		if (sftp == null) {
			throw new Exception("Passato un canale chiuso. Impossibile connettersi");
		}
		List<SftpFile> listRemoteFiles = sftp.ls(path);
		for (Iterator<SftpFile> iterator = listRemoteFiles.iterator(); iterator.hasNext();) {
			SftpFile sftpFile = iterator.next();
			String filename = sftpFile.getFilename();
			if (sftpFile.isFile() && !"..".equals(filename) && !".".equals(filename)) {
				getFile(sftp, sftpFile.getAbsolutePath(), pathTemp + sftpFile.getFilename());
				listaFile.add(new File(pathTemp + sftpFile.getFilename()));
			}
		}
		traceDebug("File recuperati: " + listaFile.size());
		return listaFile.toArray(new File[listaFile.size()]);
	}

	public static void deleteFile(SftpClient sftp, String fileRemoto, String path) throws Exception {
		traceDebug("Inizio metodo deleteFile; fileRemoto: [" + fileRemoto + "]");
		if (sftp == null) {
			throw new Exception("Passato un canale nullo. Impossibile connettersi");
		}
		if (!StringUtils.hasLength(fileRemoto)) {
			throw new Exception("Passato un fileRemoto nullo");
		}
		sftp.rm(path + fileRemoto);
		traceDebug("File " + (path + fileRemoto) + " cancellato");
	}

	public synchronized void trasferisciFileSftp(String ftpHost, String ftpDir, String userName, String pwd, String pathRemoto, String pathDestinazione, String filtro, String locationCertificatoSsh, String fileZipDaTrasferireName, boolean cancellaFileZipLocaleObsoleto, boolean usaCertificatoSsh) throws Exception {
		traceDebug("Inizio metodo trasferisciFileSftp; host dove collegarsi: [" + ftpHost + "]; username: [" + userName + "] password: [" + pwd + "] pathRemoto: [" + pathRemoto + "] pathDestinazione: [" + pathDestinazione + "]");
		if (!StringUtils.hasLength(ftpHost)) {
			throw new Exception("Passato un host nullo. Impossibile connettersi");
		}
		if (!StringUtils.hasLength(userName) || !StringUtils.hasLength(pwd)) {
			throw new Exception("Passato una username o una password nulla. Impossibile connettersi al server FTP");
		}
		if (usaCertificatoSsh && !StringUtils.hasLength(locationCertificatoSsh)) {
			throw new Exception("Non è stato specificata la direcotry del certificato SSH");
		}
		if (!StringUtils.hasLength(fileZipDaTrasferireName)) {
			throw new Exception("Impossibile proseguire. Non è stato specificato il file zip da trasferire");
		}
		String[] hosts = null;
		if (ftpHost.indexOf(" ") > -1) {
			hosts = ftpHost.split(" ");
		}
		SshClient ssh = null;
		SftpClient sftp = null;
		try {
			if (hosts == null) {
				ssh = new SshClient();
				ssh.connect(ftpHost, new IgnoreHostKeyVerification());
				traceDebug("Connessione al server sftp inviata");
				traceDebug("Mi autentico al server...");
				int autenticationResult = -1;
				if (usaCertificatoSsh) {
					PublicKeyAuthenticationClient pwdClient = new PublicKeyAuthenticationClient();
					pwdClient.setUsername(userName);
					traceDebug("Costruisco l'sshPrivateKeyFile partendo dal certificato nella directory: " + locationCertificatoSsh);
					SshPrivateKeyFile file = SshPrivateKeyFile.parse(new File(locationCertificatoSsh));
					String frasePass = null;
					if (file.isPassphraseProtected()) {
						traceDebug("Il file è protetto da password");
						frasePass = pwd;
					}
					SshPrivateKey chiave = file.toPrivateKey(frasePass);
					pwdClient.setKey(chiave);
					traceDebug("Ho settato al pwdClient questa chiave: " + chiave + ". Tento l'autenticazione sul server");
					autenticationResult = ssh.authenticate(pwdClient);
					traceDebug("Risultato autenticazione: " + autenticationResult);
				} else {
					PasswordAuthenticationClient pwdClient = new PasswordAuthenticationClient();
					pwdClient.setUsername(userName);
					pwdClient.setPassword(pwd);
					autenticationResult = ssh.authenticate(pwdClient);
				}
				if (autenticationResult == AuthenticationProtocolState.COMPLETE) {
					traceDebug("Autenticazione avvenuta. Apro il canale sftp e trasferisco il file");
					sftp = openSftpClient(ssh, ftpDir);
					traceDebug("Canale sftp aperto. Trasferisco il file");
					File fileZipDaTrasferire = new File(fileZipDaTrasferireName);
					trasferisciFile(fileZipDaTrasferireName, pathDestinazione, fileZipDaTrasferire.getPath(), sftp, cancellaFileZipLocaleObsoleto);
					traceDebug("File trasferito....");
				} else if (autenticationResult == AuthenticationProtocolState.FAILED) {
					logger.error("Autenticazione sul server sftp fallita. Impossibile proseguire con il trasferimento del file");
					throw new Exception("Autenticazione sul server sftp fallita. Impossibile proseguire con il trasferimento del file");
				} else if ((autenticationResult == AuthenticationProtocolState.CANCELLED) || (autenticationResult == AuthenticationProtocolState.PARTIAL)) {
					logger.error("Impossibile proseguire con l'upload del file. L'autenticazione è parziale o cancellata");
				}
			} else {
				traceDebug("Ho un array di host a cui collegarmi. Inizio il trasferimento...");
				for (int i = 0; i < hosts.length; i++) {
					String newHost = hosts[i];
					ssh = new SshClient();
					ssh.connect(newHost, new IgnoreHostKeyVerification());
					traceDebug("Connessione al server sftp inviata");
					traceDebug("Mi autentico al server...");
					int autenticationResult = -1;
					if (usaCertificatoSsh) {
						PublicKeyAuthenticationClient pwdClient = new PublicKeyAuthenticationClient();
						pwdClient.setUsername(userName);
						traceDebug("Costruisco l'sshPrivateKeyFile partendo dal certificato nella directory: " + locationCertificatoSsh);
						SshPrivateKeyFile file = SshPrivateKeyFile.parse(new File(locationCertificatoSsh));
						String frasePass = null;
						if (file.isPassphraseProtected()) {
							traceDebug("Il file è protetto da password");
							frasePass = pwd;
						}
						SshPrivateKey chiave = file.toPrivateKey(frasePass);
						pwdClient.setKey(chiave);
						traceDebug("Ho settato al pwdClient questa chiave: " + chiave + ". Tento l'autenticazione sul server");
						autenticationResult = ssh.authenticate(pwdClient);
						traceDebug("Risultato autenticazione: " + autenticationResult);
					} else {
						PasswordAuthenticationClient pwdClient = new PasswordAuthenticationClient();
						pwdClient.setUsername(userName);
						pwdClient.setPassword(pwd);
						autenticationResult = ssh.authenticate(pwdClient);
					}
					if (autenticationResult == AuthenticationProtocolState.COMPLETE) {
						traceDebug("Autenticazione avvenuta. Apro il canale sftp e trasferisco il file");
						sftp = openSftpClient(ssh, ftpDir);
						traceDebug("Canale sftp aperto. Trasferisco il file");
						File fileZipDaTrasferire = new File(fileZipDaTrasferireName);
						if (i < (hosts.length - 1)) {
							trasferisciFile(fileZipDaTrasferireName, pathDestinazione, fileZipDaTrasferire.getPath(), sftp, false);
						} else {
							trasferisciFile(fileZipDaTrasferireName, pathDestinazione, fileZipDaTrasferire.getPath(), sftp, cancellaFileZipLocaleObsoleto);
						}
					} else if (autenticationResult == AuthenticationProtocolState.FAILED) {
						logger.error("Autenticazione sul server FTP fallita. Impossibile proseguire con il trasferimento del file");
						throw new Exception("Autenticazione sul server FTP fallita. Impossibile proseguire con il trasferimento del file");
					} else if ((autenticationResult == AuthenticationProtocolState.CANCELLED) || (autenticationResult == AuthenticationProtocolState.PARTIAL)) {
						logger.error("Impossibile proseguire con l'upload del file. L'autenticazione è parziale o cancellata");
					}
				}
				traceDebug("File trasferiti");
			}
		} catch (Exception e) {
			logger.error("Errore nel metodo trasferisciFileSftp: " + e.getMessage(), e);
			throw new Exception(e);
		} finally {
			if (ssh != null) {
				ssh.disconnect();
			}
		}
	}

	private void trasferisciFile(String fileZipName, String destinazioneRemota, String directoryLocale, SftpClient sftp, boolean cancellaFileLocaleOsoleto) throws Exception {
		traceDebug("Inizio metodo trasferisciFile; inizio il trasferimento del file zip: " + fileZipName + ". Il file verrà salvato nella directory: " + destinazioneRemota
				+ ". Il client sftp da utilizzare è: " + sftp);
		try {
			traceDebug("Cambio la directory di lavoro nella directory: " + directoryLocale);
			File f = new File(directoryLocale);
			File fileZip = new File(fileZipName);
			if (!f.isDirectory()) {
				sftp.lcd(f.getParent());
			} else {
				sftp.lcd(directoryLocale);
			}
			traceDebug("Cambio la directory di lavoro remota in: " + destinazioneRemota);
			sftp.cd(destinazioneRemota);			
			String nomeFileRemoto = fileZip.getName();
			int indice = nomeFileRemoto.indexOf(".zip");
			if (indice > -1) {
				nomeFileRemoto = nomeFileRemoto.substring(0, indice) + ".tmp";
			}
			traceDebug("Effetuo il trasferimento del file zip con estensione temporanea. Nome del file remoto: " + nomeFileRemoto + ". Sposto il file " + fileZip + ". Il file in questione esiste? " + (new File(fileZipName)).exists());
			sftp.put(fileZipName, nomeFileRemoto);
			traceDebug("Rinomino il file temporaneo " + nomeFileRemoto + " in " + fileZip.getName() + "");
			sftp.rename(nomeFileRemoto, fileZip.getName());
			traceDebug("File rinominato");
			if (cancellaFileLocaleOsoleto) {
				File fileZipObsoleto = new File(fileZipName);
				if (fileZipObsoleto.delete()) {
					traceDebug("File zip obsoleto cancellato con successo");
				} else {
					throw new Exception("Impossibile cancellare il file zip obsoleto");
				}
			}
		} catch (Exception e) {
			logger.error("Errore nel metodo trasferisciFile: " + e.getMessage(), e);
			throw new Exception(e);
		}
		traceDebug("Fine metodo trasferisciFile. Trasferimento completato");
	}

	private static void traceDebug(String message) {
		if (logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}

}