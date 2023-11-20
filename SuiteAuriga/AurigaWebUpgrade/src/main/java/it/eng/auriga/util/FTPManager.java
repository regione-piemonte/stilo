/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class FTPManager {
	
	private String protocollo;
	private String ftpHost;
	private String ftpPort;
	private String ftpDir;
	private String ftpUser;
	private String ftpPassword;	
	private String ftpsKeyStoreFileName;
	private String ftpsKeyStorePassword;
	private int numMaxTentativi;
	
	private static Logger logger = Logger.getLogger(FTPManager.class);

	public FTPManager(String propertiesFileName) throws Exception {
		Properties properties =  new PropertiesManager().getProperties(propertiesFileName);
		this.protocollo = (String) properties.get("protocollo");
		this.ftpHost = (String) properties.get("ftpHost");
		this.ftpPort = (String) properties.get("ftpPort");
		this.ftpDir = (String) properties.get("ftpDir");
		this.ftpUser = (String) properties.get("ftpUser");
		this.ftpPassword = (String) properties.get("ftpPassword");
		this.ftpsKeyStoreFileName = (String) properties.get("ftpsKeyStoreFileName");
		this.ftpsKeyStorePassword = (String) properties.get("ftpsKeyStorePassword");
		this.numMaxTentativi = Integer.parseInt((String) properties.get("numMaxTentativi"));
	}
	
	public FTPManager(String protocollo, String ftpHost, String ftpPort, String ftpDir, String ftpUser, String ftpPassword, String ftpsKeyStoreFileName, String ftpsKeyStorePassword, int numMaxTentativi) {
		this.protocollo = protocollo;
		this.ftpHost = ftpHost;
		this.ftpPort = ftpPort;
		this.ftpDir = ftpDir;
		this.ftpUser = ftpUser;
		this.ftpPassword = ftpPassword;
		this.ftpsKeyStoreFileName = ftpsKeyStoreFileName;
		this.ftpsKeyStorePassword = ftpsKeyStorePassword;
		this.numMaxTentativi = numMaxTentativi;
	}
	
	public boolean upload(List<File> files, List<String> fileNames) throws Exception{	
		return upload(null, files, fileNames);
	}
	
	public boolean upload(String subDir, List<File> files, List<String> fileNames) throws Exception{	
		logger.debug("Upload file su ftp con protocollo " + protocollo);		
		if ("SFTP".equalsIgnoreCase(protocollo)){
			return SFTPUtil.uploadFiles(subDir, files, fileNames, ftpHost, ftpPort, ftpDir, ftpUser, ftpPassword, numMaxTentativi);
		} else if ("FTP".equalsIgnoreCase(protocollo)) {			
			return FTPUtil.uploadFiles(subDir, files, fileNames, ftpHost, ftpPort, ftpDir, ftpUser, ftpPassword, numMaxTentativi);
		} else if ("FTPS".equalsIgnoreCase(protocollo)) {			
			return FTPSUtil.uploadFiles(subDir, files, fileNames, ftpHost, ftpPort, ftpDir, ftpUser, ftpPassword, ftpsKeyStoreFileName, ftpsKeyStorePassword, numMaxTentativi);
		} else {
			throw new Exception("Protocollo non valido");
		}
	}

	public File[] getAllFile(String pathTemp, String path) throws Exception{	
		logger.debug("Get all file su ftp con protocollo " + protocollo);	
		File[] ret = null;
		if ("SFTP".equalsIgnoreCase(protocollo)){
			ret = SFTPUtil.getAllFile(ftpHost, ftpPort, ftpDir, ftpUser, ftpPassword, numMaxTentativi, pathTemp, path);
		} else if ("FTP".equalsIgnoreCase(protocollo)) {			
			ret = FTPUtil.getAllFile(ftpHost, ftpPort, ftpDir, ftpUser, ftpPassword, numMaxTentativi, pathTemp, path);
		} else if ("FTPS".equalsIgnoreCase(protocollo)) {			
			ret = FTPSUtil.getAllFile(ftpHost, ftpPort, ftpDir, ftpUser, ftpPassword, ftpsKeyStoreFileName, ftpsKeyStorePassword, numMaxTentativi, pathTemp, path);
		} else {
			throw new Exception("Protocollo non valido");
		}
		if(ret != null) {
			for(File file : ret) {
				logger.debug(file.getPath());
			}
		}
		return ret;
	}
	
	public static void main(String args[]) throws Exception {
		testGetAll();
//		testUpload();
	}

	public static void testGetAll() throws Exception {
		FTPManager lFTPManager = new FTPManager("FTP", "217.172.5.6", "21", "/Atti", "ECMTeamcol", "3cmDGc0l1", null, null, 1);	
		try {
			lFTPManager.getAllFile("C:\\Users\\matzanin\\Engineering\\temp\\TestSuiteSFTP\\", "Provvedimento-DD-31-2015");
		}catch(Exception e) {
			logger.warn(e);
		}
	}

	public static void testUpload() throws Exception {
		FTPManager lFTPManager = new FTPManager("FTP", "217.172.5.6", "21", "/Atti", "ECMTeamcol", "3cmDGc0l1", null, null, 1);	
		try {			
			File fileXml = new File("C:/Users/matzanin/Engineering/temp/pubblatto.xml");		
			if(lFTPManager.upload(Arrays.asList(new File[] {fileXml}), Arrays.asList(new String[] {"XMLDelibere_Provvedimento-DG-8-2015_2015-11-07.xml"}))) {
				List<File> files = new ArrayList<File>();	
				files.add(new File("C:/Users/matzanin/Engineering/temp/Copertina_atto_con_firme.pdf.p7m"));
				files.add(new File("C:/Users/matzanin/Engineering/temp/Dispositivo_Deliberazione_Direttore_Generale.pdf.p7m"));
				List<String> fileNames = new ArrayList<String>();
				fileNames.add("Copertina_atto_con_firme.pdf.p7m");		
				fileNames.add("Dispositivo_Deliberazione_Direttore_Generale.pdf.p7m");
				lFTPManager.upload("Provvedimento-DG-8-2015", files, fileNames);
			}
		}catch(Exception e) {
			logger.warn(e);
		}
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getFtpHost() {
		return ftpHost;
	}

	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}

	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpDir() {
		return ftpDir;
	}

	public void setFtpDir(String ftpDir) {
		this.ftpDir = ftpDir;
	}

	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getFtpsKeyStoreFileName() {
		return ftpsKeyStoreFileName;
	}

	public void setFtpsKeyStoreFileName(String ftpsKeyStoreFileName) {
		this.ftpsKeyStoreFileName = ftpsKeyStoreFileName;
	}

	public String getFtpsKeyStorePassword() {
		return ftpsKeyStorePassword;
	}

	public void setFtpsKeyStorePassword(String ftpsKeyStorePassword) {
		this.ftpsKeyStorePassword = ftpsKeyStorePassword;
	}

	public int getNumMaxTentativi() {
		return numMaxTentativi;
	}

	public void setNumMaxTentativi(int numMaxTentativi) {
		this.numMaxTentativi = numMaxTentativi;
	}
	
}