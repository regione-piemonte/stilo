/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FileToExtractBean {

	private String idDocumento;
	private String versione;
	private String displayFilename;
	private String uri;
	private Boolean sbustato;
	private Boolean remoteUri;
	private String correctFilename;
	private String impronta;
	
	public String getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getVersione() {
		return versione;
	}
	public void setVersione(String versione) {
		this.versione = versione;
	}
	public String getDisplayFilename() {
		return displayFilename;
	}
	public void setDisplayFileName(String displayFileName) {
		this.displayFilename = displayFileName;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getUri() {
		return uri;
	}
	public void setSbustato(boolean sbustato) {
		this.sbustato = sbustato;
	}
	public boolean isSbustato() {
		return sbustato;
	}
	
	public void setCorrectFilename(String correctFilename) {
		this.correctFilename = correctFilename;
	}
	public String getCorrectFilename() {
		return correctFilename;
	}
	public void setRemoteUri(Boolean remoteUri) {
		this.remoteUri = remoteUri;
	}
	public Boolean getRemoteUri() {
		return remoteUri;
	}
	public String getImpronta() {
		return impronta;
	}
	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}
	
	
}
