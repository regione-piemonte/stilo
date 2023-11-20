/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class RigaAllegatoEmail {

	/**
	 * Nome file allegato originale
	 */
	@NumeroColonna(numero = "1")
	private String nomeFile;
	@NumeroColonna(numero = "2")
	private String mimetype;
	@NumeroColonna(numero = "3")
	private String flgConvertibileInPdf;
	@NumeroColonna(numero = "4")
	private String flgFirmato;
	@NumeroColonna(numero = "5")
	private String bytes;
	@NumeroColonna(numero = "6")
	private String impronta;
	@NumeroColonna(numero = "7")
	private String algoritmo;
	@NumeroColonna(numero = "8")
	private String encoding;
	@NumeroColonna(numero = "9")
	private String idUD;
	@NumeroColonna(numero = "10")
	private String estremiProt;
	@NumeroColonna(numero = "11")
	/**
	 * Nome file allegato da visualizzare
	 */
	private String displayFileName;
	@NumeroColonna(numero = "12")
	private String numeroProgrAllegato;

	private String uri;
	private MimeTypeFirmaBean infoFile;

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getFlgConvertibileInPdf() {
		return flgConvertibileInPdf;
	}

	public void setFlgConvertibileInPdf(String flgConvertibileInPdf) {
		this.flgConvertibileInPdf = flgConvertibileInPdf;
	}

	public String getFlgFirmato() {
		return flgFirmato;
	}

	public void setFlgFirmato(String flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	public String getBytes() {
		return bytes;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
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

	public String getIdUD() {
		return idUD;
	}

	public void setIdUD(String idUD) {
		this.idUD = idUD;
	}

	public String getEstremiProt() {
		return estremiProt;
	}

	public void setEstremiProt(String estremiProt) {
		this.estremiProt = estremiProt;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}

	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}

	/**
	 * @return the displayFileName
	 */
	public String getDisplayFileName() {
		return displayFileName;
	}

	/**
	 * @param displayFileName
	 *            the displayFileName to set
	 */
	public void setDisplayFileName(String displayFileName) {
		this.displayFileName = displayFileName;
	}

	public String getNumeroProgrAllegato() {
		return numeroProgrAllegato;
	}

	public void setNumeroProgrAllegato(String numeroProgrAllegato) {
		this.numeroProgrAllegato = numeroProgrAllegato;
	}

}