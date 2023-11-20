/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DatiFileDettContenutoSezXmlBean {
	
	@NumeroColonna(numero="8")
	private String uriFile;
	
	@NumeroColonna(numero="9")
	private String displayFile;
	
	@NumeroColonna(numero="10")
	private Boolean flgFileFirmato;
	
	@NumeroColonna(numero="11")
	private Boolean flgFileFirmatoCades;
	
	@NumeroColonna(numero="12")
	private Boolean flgFilePdf;
	
	@NumeroColonna(numero="13")
	private String mimeTypeFile;
	
	@NumeroColonna(numero="14")
	private String improntaFile;
	
	@NumeroColonna(numero="15")
	private String algoritmoImpronta;
	
	@NumeroColonna(numero="16")
	private String encodingImpronta;
	
	@NumeroColonna(numero="17")
	private String idDocFile;
	
	@NumeroColonna(numero="18")
	private String nroVersioneFile;

	@NumeroColonna(numero="19")
	private String idUd;
	
	@NumeroColonna(numero="21")
	private String nroAllegato;

	public String getUriFile() {
		return uriFile;
	}

	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

	public String getDisplayFile() {
		return displayFile;
	}

	public void setDisplayFile(String displayFile) {
		this.displayFile = displayFile;
	}

	public Boolean getFlgFileFirmato() {
		return flgFileFirmato;
	}

	public void setFlgFileFirmato(Boolean flgFileFirmato) {
		this.flgFileFirmato = flgFileFirmato;
	}

	public Boolean getFlgFileFirmatoCades() {
		return flgFileFirmatoCades;
	}

	public void setFlgFileFirmatoCades(Boolean flgFileFirmatoCades) {
		this.flgFileFirmatoCades = flgFileFirmatoCades;
	}

	public Boolean getFlgFilePdf() {
		return flgFilePdf;
	}

	public void setFlgFilePdf(Boolean flgFilePdf) {
		this.flgFilePdf = flgFilePdf;
	}

	public String getMimeTypeFile() {
		return mimeTypeFile;
	}

	public void setMimeTypeFile(String mimeTypeFile) {
		this.mimeTypeFile = mimeTypeFile;
	}

	public String getImprontaFile() {
		return improntaFile;
	}

	public void setImprontaFile(String improntaFile) {
		this.improntaFile = improntaFile;
	}

	public String getAlgoritmoImpronta() {
		return algoritmoImpronta;
	}

	public void setAlgoritmoImpronta(String algoritmoImpronta) {
		this.algoritmoImpronta = algoritmoImpronta;
	}

	public String getEncodingImpronta() {
		return encodingImpronta;
	}

	public void setEncodingImpronta(String encodingImpronta) {
		this.encodingImpronta = encodingImpronta;
	}

	public String getIdDocFile() {
		return idDocFile;
	}

	public void setIdDocFile(String idDocFile) {
		this.idDocFile = idDocFile;
	}

	public String getNroVersioneFile() {
		return nroVersioneFile;
	}

	public void setNroVersioneFile(String nroVersioneFile) {
		this.nroVersioneFile = nroVersioneFile;
	}

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getNroAllegato() {
		return nroAllegato;
	}

	public void setNroAllegato(String nroAllegato) {
		this.nroAllegato = nroAllegato;
	}
			
}
