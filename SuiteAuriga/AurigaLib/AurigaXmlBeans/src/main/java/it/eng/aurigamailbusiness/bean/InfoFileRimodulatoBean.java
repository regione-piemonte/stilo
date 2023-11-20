/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import it.eng.document.NumeroColonna;

public class InfoFileRimodulatoBean {
	
//	colonna 1) Nome del file                            Se contiene solo pagine di uno stesso file allegato è il nome del file originale, altrimenti si chiama File_<progressivo da 1 in su).pdf
//	colonna 2) Impronta del file (vedi sopra l’osservazione in giallo)
//	colonna 3) QRCode letti nell'allegato (se più di uno separati da |*|)        (dovremmo averne uno solo…ma sai mai)
//	colonna 4) Datamatrix letti nell'allegato (se più di uno separati da |*|)  (dovremmo averne uno solo…ma sai mai)
//	colonna 5) Presenza firma digitale (1/0)
//	colonna 6) Nro di firme attese (n.ro di qrcode che indicano aree di firma)
//	colonna 7) Nro firme compilate (n.ro di aree firma, alla destra dei qrcode di firma, in cui sono state trovate firme compilate)
//	colonna 8) uri file rimodulato
//	colonna 9) dimensione file in bytes 
//	colonna 10) Nome file di origine

	@NumeroColonna(numero = "1")
	private String nomeFile;
	@NumeroColonna(numero = "2")
	private String improntaFile;
	@NumeroColonna(numero = "3")
	private String qrCode;
	@NumeroColonna(numero = "4")
	private String datamatrix;
	@NumeroColonna(numero = "5")
	private String flgFirmatoDigitalmente;
	@NumeroColonna(numero = "6")
	private String numFirmeAttese;
	@NumeroColonna(numero = "7")
	private String numFirmeCompilate;
	@NumeroColonna(numero = "8")
	private String uriFileRimodulato;
	@NumeroColonna(numero = "9")
	private String dimensioneFile;
	@NumeroColonna(numero = "10")
	private String nomeFileOriginale;
	
	private File fileRimodulato;

	public String getNomeFile() {
		return nomeFile;
	}

	public String getImprontaFile() {
		return improntaFile;
	}

	public String getQrCode() {
		return qrCode;
	}

	public String getDatamatrix() {
		return datamatrix;
	}

	public String getFlgFirmatoDigitalmente() {
		return flgFirmatoDigitalmente;
	}

	public String getNumFirmeAttese() {
		return numFirmeAttese;
	}

	public String getNumFirmeCompilate() {
		return numFirmeCompilate;
	}

	public File getFileRimodulato() {
		return fileRimodulato;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public void setImprontaFile(String improntaFile) {
		this.improntaFile = improntaFile;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public void setDatamatrix(String datamatrix) {
		this.datamatrix = datamatrix;
	}

	public void setFlgFirmatoDigitalmente(String flgFirmatoDigitalmente) {
		this.flgFirmatoDigitalmente = flgFirmatoDigitalmente;
	}

	public void setNumFirmeAttese(String numFirmeAttese) {
		this.numFirmeAttese = numFirmeAttese;
	}

	public void setNumFirmeCompilate(String numFirmeCompilate) {
		this.numFirmeCompilate = numFirmeCompilate;
	}

	public void setFileRimodulato(File fileRimodulato) {
		this.fileRimodulato = fileRimodulato;
	}

	public String getUriFileRimodulato() {
		return uriFileRimodulato;
	}

	public void setUriFileRimodulato(String uriFileRimodulato) {
		this.uriFileRimodulato = uriFileRimodulato;
	}

	public String getDimensioneFile() {
		return dimensioneFile;
	}

	public void setDimensioneFile(String dimensioneFile) {
		this.dimensioneFile = dimensioneFile;
	}

	public String getNomeFileOriginale() {
		return nomeFileOriginale;
	}

	public void setNomeFileOriginale(String nomeFileOriginale) {
		this.nomeFileOriginale = nomeFileOriginale;
	}
	
}
