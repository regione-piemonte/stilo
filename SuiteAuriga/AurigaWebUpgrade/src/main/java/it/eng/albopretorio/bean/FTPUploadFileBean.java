/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FTPUploadFileBean {
	
	String serverFTP = "";
	String portFTP = "";
	String userFTP = "";
	String passwordFTP = "";
	String fileRequest = "";
    String nomeFileRemoto = "";
    
	public String getServerFTP() {
		return serverFTP;
	}
	public void setServerFTP(String serverFTP) {
		this.serverFTP = serverFTP;
	}
	public String getPortFTP() {
		return portFTP;
	}
	public void setPortFTP(String portFTP) {
		this.portFTP = portFTP;
	}
	public String getUserFTP() {
		return userFTP;
	}
	public void setUserFTP(String userFTP) {
		this.userFTP = userFTP;
	}
	public String getPasswordFTP() {
		return passwordFTP;
	}
	public void setPasswordFTP(String passwordFTP) {
		this.passwordFTP = passwordFTP;
	}
	public String getFileRequest() {
		return fileRequest;
	}
	public void setFileRequest(String fileRequest) {
		this.fileRequest = fileRequest;
	}
	public String getNomeFileRemoto() {
		return nomeFileRemoto;
	}
	public void setNomeFileRemoto(String nomeFileRemoto) {
		this.nomeFileRemoto = nomeFileRemoto;
	}
    
}
