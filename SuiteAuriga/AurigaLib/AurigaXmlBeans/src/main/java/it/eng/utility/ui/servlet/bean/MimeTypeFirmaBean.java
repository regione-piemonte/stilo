/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class MimeTypeFirmaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean firmato;
	private String tipoFirma;
	private String infoFirma;
	private boolean convertibile;
	private String mimetype;
	private String mimetypesContenuto;
	private long bytes;
	private boolean firmaValida;
	private String correctFileName;
	private String[] firmatari;
	private Firmatari[] buste;
	private String impronta;
	private String improntaPdf;
	private String improntaFilePreFirma;
	private String improntaSbustato;
	private boolean daScansione;
	private boolean uploadError = false;
	private String[] openFolders;
	private String algoritmo;
	private String encoding;
	private String idFormato;
	private InfoFirmaMarca infoFirmaMarca;
	private boolean pdfProtetto;
	private boolean pdfEditabile;
	private boolean pdfConCommenti;
	private Integer numPaginePdf;

	public boolean isFirmato() {
		return firmato;
	}

	public void setFirmato(boolean firmato) {
		this.firmato = firmato;
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public String getInfoFirma() {
		return infoFirma;
	}

	public void setInfoFirma(String infoFirma) {
		this.infoFirma = infoFirma;
	}

	public boolean isConvertibile() {
		return convertibile;
	}

	public void setConvertibile(boolean convertibile) {
		this.convertibile = convertibile;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getMimetypesContenuto() {
		return mimetypesContenuto;
	}

	public void setMimetypesContenuto(String mimetypesContenuto) {
		this.mimetypesContenuto = mimetypesContenuto;
	}

	public long getBytes() {
		return bytes;
	}

	public void setBytes(long bytes) {
		this.bytes = bytes;
	}

	public boolean isFirmaValida() {
		return firmaValida;
	}

	public void setFirmaValida(boolean firmaValida) {
		this.firmaValida = firmaValida;
	}

	public String getCorrectFileName() {
		return correctFileName;
	}

	public void setCorrectFileName(String correctFileName) {
		this.correctFileName = correctFileName;
	}

	public String[] getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(String[] firmatari) {
		this.firmatari = firmatari;
	}

	public Firmatari[] getBuste() {
		return buste;
	}

	public void setBuste(Firmatari[] buste) {
		this.buste = buste;
	}

	public String getImpronta() {
		return impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	public String getImprontaPdf() {
		return improntaPdf;
	}

	public void setImprontaPdf(String improntaPdf) {
		this.improntaPdf = improntaPdf;
	}
	
	public String getImprontaFilePreFirma() {
		return improntaFilePreFirma;
	}
	
	public void setImprontaFilePreFirma(String improntaFilePreFirma) {
		this.improntaFilePreFirma = improntaFilePreFirma;
	}

	public String getImprontaSbustato() {
		return improntaSbustato;
	}

	public void setImprontaSbustato(String improntaSbustato) {
		this.improntaSbustato = improntaSbustato;
	}

	public boolean isDaScansione() {
		return daScansione;
	}

	public void setDaScansione(boolean daScansione) {
		this.daScansione = daScansione;
	}

	public boolean isUploadError() {
		return uploadError;
	}

	public void setUploadError(boolean uploadError) {
		this.uploadError = uploadError;
	}

	public String[] getOpenFolders() {
		return openFolders;
	}

	public void setOpenFolders(String[] openFolders) {
		this.openFolders = openFolders;
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

	public String getIdFormato() {
		return idFormato;
	}

	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}

	public InfoFirmaMarca getInfoFirmaMarca() {
		return infoFirmaMarca;
	}

	public void setInfoFirmaMarca(InfoFirmaMarca infoFirmaMarca) {
		this.infoFirmaMarca = infoFirmaMarca;
	}
	
	public boolean isPdfProtetto() {
		return pdfProtetto;
	}

	public void setPdfProtetto(boolean pdfProtetto) {
		this.pdfProtetto = pdfProtetto;
	}

	public boolean isPdfEditabile() {
		return pdfEditabile;
	}

	public boolean isPdfConCommenti() {
		return pdfConCommenti;
	}

	public void setPdfEditabile(boolean pdfEditabile) {
		this.pdfEditabile = pdfEditabile;
	}

	public void setPdfConCommenti(boolean pdfConCommenti) {
		this.pdfConCommenti = pdfConCommenti;
	}

	public Integer getNumPaginePdf() {
		return numPaginePdf;
	}

	public void setNumPaginePdf(Integer numPaginePdf) {
		this.numPaginePdf = numPaginePdf;
	}

}
