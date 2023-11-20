/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.annotation.Attachment;
import it.eng.core.business.beans.AbstractBean;

/**
 * Bean indicante gli attachment da inviare
 * 
 * @author jravagnan
 */
@XmlRootElement
public class SenderAttachmentsBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 4405006310471098422L;

	/**
	 * Nome del file da visualizzare
	 */

	private String filename;
	
	private byte[] content;

	@Attachment
	private File file;

	private String mimetype;

	private int nrAllegato;

	/**
	 * Nome originale del file
	 */

	private String originalName;

	private boolean firmato;
	
	private boolean pdfEditabile;	
	
	private boolean pdfConCommenti;

	/**
	 * Dimensione in bytes
	 */

	private BigDecimal dimensione;

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
	
	private boolean firmaValida;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public boolean isFirmato() {
		return firmato;
	}

	public void setFirmato(boolean firmato) {
		this.firmato = firmato;
	}

	public BigDecimal getDimensione() {
		return dimensione;
	}

	public void setDimensione(BigDecimal dimensione) {
		this.dimensione = dimensione;
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

	public boolean isFirmaValida() {
		return firmaValida;
	}

	public void setFirmaValida(boolean firmaValida) {
		this.firmaValida = firmaValida;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getNrAllegato() {
		return nrAllegato;
	}

	public void setNrAllegato(int nrAllegato) {
		this.nrAllegato = nrAllegato;
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

		
}