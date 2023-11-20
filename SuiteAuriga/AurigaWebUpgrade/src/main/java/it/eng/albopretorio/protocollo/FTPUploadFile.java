/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.albopretorio.bean.FTPUploadFileBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
 
/**
 * A program that demonstrates how to upload files from local computer
 * to a remote FTP server using Apache Commons Net API.
 * @author www.codejava.net
 */
public class FTPUploadFile {

	static Logger logger = Logger.getLogger(FTPUploadFile.class);
	
	
	public boolean uploadFTP(FTPUploadFileBean impostazioniUploadBean) throws Exception {
	
		String serverFTP = "";
		String portFTP = "";
		String userFTP = "";
		String passwordFTP = "";
		String fileRequest = "";
	    String nomeFileRemoto = "";
		
		if (impostazioniUploadBean != null) {
			serverFTP = impostazioniUploadBean.getServerFTP();
			portFTP = impostazioniUploadBean.getPortFTP();
			userFTP = impostazioniUploadBean.getUserFTP();
			passwordFTP = impostazioniUploadBean.getPasswordFTP();
			fileRequest = impostazioniUploadBean.getFileRequest();
		    nomeFileRemoto = impostazioniUploadBean.getNomeFileRemoto();
		}
		
		try {
			if (serverFTP == null || "".equalsIgnoreCase(serverFTP))  {
				ReadProperties rp=new ReadProperties();
				serverFTP = rp.getProperty("SERVER_FTP");
				logger.debug("ServerFTP(da file properties): " +serverFTP);
			}
			if (portFTP == null || "".equalsIgnoreCase(portFTP))  {
				ReadProperties rp=new ReadProperties();
				portFTP = rp.getProperty("PORT_FTP");
				logger.debug("PortFTP(da file properties): " +portFTP);
			}
			if (userFTP == null || "".equalsIgnoreCase(userFTP))  {
				ReadProperties rp=new ReadProperties();
				userFTP = rp.getProperty("USER_FTP");
				logger.debug("UserFTP(da file properties): " +userFTP);
			}
			if (passwordFTP == null || "".equalsIgnoreCase(passwordFTP))  {
				ReadProperties rp=new ReadProperties();
				passwordFTP = rp.getProperty("PASSWORD_FTP");
				logger.debug("PasswordFTP(da file properties): " +passwordFTP);
			}
			if (fileRequest == null || "".equalsIgnoreCase(fileRequest))  {
				ReadProperties rp=new ReadProperties();
				fileRequest = rp.getProperty("FILE_REQUEST");
				logger.debug("FileRequest(da file properties): " +fileRequest);
			}
			if (nomeFileRemoto == null || "".equalsIgnoreCase(nomeFileRemoto))  {
				ReadProperties rp=new ReadProperties();
				nomeFileRemoto = rp.getProperty("NOME_FILE_REMOTO");
				logger.debug("FileRequest(da file properties): " +nomeFileRemoto);
			}
		} catch (Exception e) {
			logger.error("Errore nel reperimento delle variabili da file di properties: " + e.getMessage());
		}
	
		boolean result = false;
		
		FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(serverFTP, Integer.parseInt(portFTP));
            ftpClient.login(userFTP, passwordFTP);
            ftpClient.enterLocalPassiveMode();
 
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File(fileRequest);
 
            InputStream inputStream = new FileInputStream(firstLocalFile);
 
            logger.debug("Start uploading first file");
            boolean done = ftpClient.storeFile(nomeFileRemoto, inputStream);
            inputStream.close();
            if (done) {
            	result = true;
            	logger.debug("Il file e' stato caricato correttamente");
            }
 
        } catch (IOException e) {
        	throw new Exception("Il file non è stato caricato correttamente con il seguente errore: " + e.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                    logger.debug("Chiudo le connessioni e mi disconnetto dal client FTP");
                }
            } catch (IOException e) {
            	logger.error("Errore IO nella chiusura della connessione FTP: " + e.getMessage());
            }
        }
		
        return result;
	}
	
    public static void main(String[] args) {
        try {
			boolean result = new FTPUploadFile().uploadFTP(null);
			if (result) {
				logger.debug("Uplodato il file con successo in FTP");
			}
		} catch (Exception e) {
			logger.error("Errore nell'upload del file in FTP: " + e.getMessage());
		}
    }
 
}