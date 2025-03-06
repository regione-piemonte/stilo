package it.eng.utility.cryptosigner.controller.bean;

import it.eng.utility.cryptosigner.data.AbstractSigner;

import java.io.File;
import java.io.InputStream;
import java.util.Date;



/**
 * Bean contenente tutte le proprieta di input del documento firmato da analizzare.
 * Le informazioni contenute sono le seguenti:
 * <ul>
 * 	<li>documentAndTimeStampInfo: informazioni riguardanti il timestamp associato al
 * documento firmato</li>
 * <li>referenceDate: data di riferimento temporale rispetto alla quale validare i certificati di firma</li>
 * <li>signer: istanza della classe per l'estrazione e verifica delle firme contenute</li>
 * <li>envelope: file contenente la busta del documento firmato</li>
 * <li>detachedFile: eventuale riferimento al contenuto sbustato</li>
 * <li>crl: crl in input</li>
 * <li>checks: flag dei controlli da effettuare</li> 
 * </ul>
 * @author Rigo Michele
 *
 */
public class InputSignerBean extends InputBean{

	DocumentAndTimeStampInfoBean documentAndTimeStampInfo;
	Date				referenceDate;
	AbstractSigner  	signer;	
	InputStream			envelopeStream;
	File				envelope;
	File 				detachedFile;
	
	/**
	 * Recupera le informazioni riguardanti il timestamp associato al
	 * documento firmato
	 * @return informazioni riguardanti il timestamp associato al
	 * documento firmato
	 */
	public DocumentAndTimeStampInfoBean getDocumentAndTimeStampInfo() {
		return documentAndTimeStampInfo;
	}
	
	/**
	 * Definisce le informazioni riguardanti il timestamp associato al
	 * documento firmato
	 * @param documentAndTimeStampInfo
	 */
	public void setDocumentAndTimeStampInfo(
			DocumentAndTimeStampInfoBean documentAndTimeStampInfo) {
		this.documentAndTimeStampInfo = documentAndTimeStampInfo;
	}
	
	/**
	 * Recupera il file contenente la busta del documento firmato
	 * @return file
	 */
	public InputStream getEnvelopeStream() {
		return envelopeStream;
	}
	
	/**
	 * Definisce il file contenente la busta del documento firmato
	 * @param envelope
	 */
	public void setEnvelopeStream(InputStream envelope) {
		this.envelopeStream = envelope;
	}
	
	public File getEnvelope() {
		return envelope;
	}

	public void setEnvelope(File envelope) {
		this.envelope = envelope;
	}

	/**
	 * Recupera il riferimento al contenuto sbustato (detached)
	 * @return file
	 */
	public File getDetachedFile() {
		return detachedFile;
	}
	
	/**
	 *  Definisce il riferimento al contenuto sbustato (detached)
	 * @param detachedFile
	 */
	public void setDetachedFile(File detachedFile) {
		this.detachedFile = detachedFile;
	}
	
	/**
	 * Recupera l'istanza della classe per l'estrazione e verifica delle firme contenute
	 * @return istanza
	 */
	public AbstractSigner getSigner() {
		return signer;
	}
	
	/**
	 * Definisce l'istanza della classe per l'estrazione e verifica delle firme contenute
	 * @param signer
	 */
	public void setSigner(AbstractSigner signer) {
		this.signer = signer;
	}
	
	/**
	 * @return the referenceDate
	 */
	public Date getReferenceDate() {
		return referenceDate;
	}

	/**
	 * @param referenceDate the referenceDate to set
	 */
	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}

	 
	
}
