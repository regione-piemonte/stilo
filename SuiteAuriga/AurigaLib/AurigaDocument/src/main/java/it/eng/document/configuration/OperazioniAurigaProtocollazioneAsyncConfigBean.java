/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.filemanager.FileManagerConfig;

public class OperazioniAurigaProtocollazioneAsyncConfigBean {
	
	private String defaultSchema;
	private FileManagerConfig fileManagerConfig;
	private String codApplicazioneSUE;
	private String istApplicazioneSUE;
	private String usernameSUE;
	private String passwordSUE;
	private String codApplicazioneServDigitale;
	private String flgAttivaDefaultProtSUE; //true,false
	private String pathFtp;
	
	public String getDefaultSchema() {
		return defaultSchema;
	}
	public FileManagerConfig getFileManagerConfig() {
		return fileManagerConfig;
	}
	public String getCodApplicazioneSUE() {
		return codApplicazioneSUE;
	}
	public String getIstApplicazioneSUE() {
		return istApplicazioneSUE;
	}
	public String getCodApplicazioneServDigitale() {
		return codApplicazioneServDigitale;
	}
	public String getPathFtp() {
		return pathFtp;
	}
	public void setDefaultSchema(String defaultSchema) {
		this.defaultSchema = defaultSchema;
	}
	public void setFileManagerConfig(FileManagerConfig fileManagerConfig) {
		this.fileManagerConfig = fileManagerConfig;
	}
	public void setCodApplicazioneSUE(String codApplicazioneSUE) {
		this.codApplicazioneSUE = codApplicazioneSUE;
	}
	public void setIstApplicazioneSUE(String istApplicazioneSUE) {
		this.istApplicazioneSUE = istApplicazioneSUE;
	}
	public void setCodApplicazioneServDigitale(String codApplicazioneServDigitale) {
		this.codApplicazioneServDigitale = codApplicazioneServDigitale;
	}
	public void setPathFtp(String pathFtp) {
		this.pathFtp = pathFtp;
	}
	public String getUsernameSUE() {
		return usernameSUE;
	}
	public String getPasswordSUE() {
		return passwordSUE;
	}
	public void setUsernameSUE(String usernameSUE) {
		this.usernameSUE = usernameSUE;
	}
	public void setPasswordSUE(String passwordSUE) {
		this.passwordSUE = passwordSUE;
	}
	public String getFlgAttivaDefaultProtSUE() {
		return flgAttivaDefaultProtSUE;
	}
	public void setFlgAttivaDefaultProtSUE(String flgAttivaDefaultProtSUE) {
		this.flgAttivaDefaultProtSUE = flgAttivaDefaultProtSUE;
	}
	
}
