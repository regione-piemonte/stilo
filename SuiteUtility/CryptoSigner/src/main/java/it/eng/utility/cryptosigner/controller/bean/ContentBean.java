package it.eng.utility.cryptosigner.controller.bean;

import it.eng.utility.cryptosigner.data.AbstractSigner;

import java.io.File;
import java.io.InputStream;

/**
 * Bean d'appoggio contenente il riferimento ai file sbustati 
 * e informazioni sul fatto che questi possano contenere a loro 
 * volta delle firme
 * @author Stefano Zennaro
 *
 */
public class ContentBean {
	
	private InputStream	contentStream;
	private AbstractSigner signer;
	private boolean possiblySigned = false;
	private File extractedFile;
	
	/**
	 * Recupera il contenuto sbustato
	 * @return il contenuto sbustato
	 */
	public InputStream getContentStream() {
		return contentStream;
	}
	
	/**
	 * Definisce il contenuto sbustato 
	 * @param contentFile 
	 */
	public void setContentStream(InputStream contentStream) {
		this.contentStream = contentStream;
	}
	
	/**
	 * Restituisce true se il contenuto puo essere
	 * ulteriormente firmato
	 * @return booelan
	 */
	public boolean isPossiblySigned() {
		return possiblySigned;
	}
	
	/**
	 * Definisce se il contenuto puo essere ulteriormente firmato
	 * @param possiblySigned
	 */
	public void setPossiblySigned(boolean possiblySigned) {
		this.possiblySigned = possiblySigned;
	}

	public AbstractSigner getSigner() {
		return signer;
	}

	public void setSigner(AbstractSigner signer) {
		this.signer = signer;
	}

	public File getExtractedFile() {
		return extractedFile;
	}

	public void setExtractedFile(File extractedFile) {
		this.extractedFile = extractedFile;
	}
	
	
	
}
