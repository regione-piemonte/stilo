/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

/**
 * file arricchito delle informazioni fornite dai servizi di fileoperation
 * 
 * @author jravagnan
 *
 */
public class EnhancedFile {

	private File file;

	private String originalFileName; // nome originale del file

	private String mimeType;

	private String newName; // eventuale nuovo nome del file

	private Boolean isFirmato;
	
	private Boolean isFirmaValida;

	private Long dimensione;

	/**
	 * Impronta
	 */
	private String impronta;

	/**
	 * Algoritmo calcolo impronta
	 */
	private String algoritmo;

	/**
	 * Encoding impronta (hex o base64)
	 */
	private String encoding;
	
	private Boolean isPdfEditabile;	

	private Boolean isPdfConCommenti;
	
	

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public Boolean getIsFirmato() {
		return isFirmato;
	}

	public void setIsFirmato(Boolean isFirmato) {
		this.isFirmato = isFirmato;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	/**
	 * @return the impronta
	 */
	public String getImpronta() {
		return impronta;
	}

	/**
	 * @param impronta
	 *            the impronta to set
	 */
	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	/**
	 * @return the algoritmo
	 */
	public String getAlgoritmo() {
		return algoritmo;
	}

	/**
	 * @param algoritmo
	 *            the algoritmo to set
	 */
	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding
	 *            the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Long getDimensione() {
		return dimensione;
	}

	public void setDimensione(Long dimensione) {
		this.dimensione = dimensione;
	}

	public Boolean getIsFirmaValida() {
		return isFirmaValida;
	}

	public void setIsFirmaValida(Boolean isFirmaValida) {
		this.isFirmaValida = isFirmaValida;
	}
	
	public Boolean getIsPdfEditabile() {
		return isPdfEditabile;
	}

	public void setIsPdfEditabile(Boolean isPdfEditabile) {
		this.isPdfEditabile = isPdfEditabile;
	}
	
	public Boolean getIsPdfConCommenti() {
		return isPdfConCommenti;
	}
	
	public void setIsPdfConCommenti(Boolean isPdfConCommenti) {
		this.isPdfConCommenti = isPdfConCommenti;
	}

}
