/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class VerificaFirmaBean {

	public enum TipoFirma {CADES_BES, PDF}
	private boolean isFirmato;
	private TipoFirma firma;
	private boolean isConvertible;
	private String uriConverted;
	
	private String uri;
	private String displayFileName;
	public boolean isFirmato() {
		return isFirmato;
	}
	public void setFirmato(boolean isFirmato) {
		this.isFirmato = isFirmato;
	}
	public TipoFirma getFirma() {
		return firma;
	}
	public void setFirma(TipoFirma firma) {
		this.firma = firma;
	}
	public boolean isConvertible() {
		return isConvertible;
	}
	public void setConvertible(boolean isConvertible) {
		this.isConvertible = isConvertible;
	}
	public String getUriConverted() {
		return uriConverted;
	}
	public void setUriConverted(String uriConverted) {
		this.uriConverted = uriConverted;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getDisplayFileName() {
		return displayFileName;
	}
	public void setDisplayFileName(String displayFileName) {
		this.displayFileName = displayFileName;
	}
	
}
