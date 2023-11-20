/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class DettaglioDatiFileBean {
	
	@NumeroColonna(numero = "8")
	private String uri;
	
	@NumeroColonna(numero = "9")
	private String nomeFile;
	
	@NumeroColonna(numero = "10")
	private String flgFileFirmato;
	
	@NumeroColonna(numero = "11")
	private String flgFirmatoCades;
	
	@NumeroColonna(numero = "12")
	private String flgPdf;
	
	@NumeroColonna(numero = "13")
	private String mimetype;
	
	@NumeroColonna(numero = "14")
	private String impronta;
	
	@NumeroColonna(numero = "15")
	private String algoritmo;
	
	@NumeroColonna(numero = "16")
	private String encoding;
	
	@NumeroColonna(numero = "17")
	private String idDoc;
	
	@NumeroColonna(numero = "18")
	private String nroVersione;

	@NumeroColonna(numero = "19")
	private String idUd;
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getFlgFileFirmato() {
		return flgFileFirmato;
	}

	public void setFlgFileFirmato(String flgFileFirmato) {
		this.flgFileFirmato = flgFileFirmato;
	}

	public String getFlgFirmatoCades() {
		return flgFirmatoCades;
	}

	public void setFlgFirmatoCades(String flgFirmatoCades) {
		this.flgFirmatoCades = flgFirmatoCades;
	}

	public String getFlgPdf() {
		return flgPdf;
	}

	public void setFlgPdf(String flgPdf) {
		this.flgPdf = flgPdf;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getImpronta() {
		return impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public String getNroVersione() {
		return nroVersione;
	}

	public void setNroVersione(String nroVersione) {
		this.nroVersione = nroVersione;
	}

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	
}
