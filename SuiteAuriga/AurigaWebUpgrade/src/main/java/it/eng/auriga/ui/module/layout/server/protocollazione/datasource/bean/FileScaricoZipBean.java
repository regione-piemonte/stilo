/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

public class FileScaricoZipBean {

	private String uriFile;
	private String nomeFile;
	private boolean firmato;
	private boolean canBeTimbrato;
	private BigDecimal idUd;
	private BigDecimal idDoc;
	private String mimeType;
	
	public String getUriFile() {
		return uriFile;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public boolean isFirmato() {
		return firmato;
	}
	public boolean isCanBeTimbrato() {
		return canBeTimbrato;
	}
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public void setFirmato(boolean firmato) {
		this.firmato = firmato;
	}
	public void setCanBeTimbrato(boolean canBeTimbrato) {
		this.canBeTimbrato = canBeTimbrato;
	}
	public BigDecimal getIdUd() {
		return idUd;
	}
	public void setIdUd(BigDecimal bigDecimal) {
		this.idUd = bigDecimal;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public BigDecimal getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}	
	
}
