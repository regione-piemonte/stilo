package it.eng.utility.cryptosigner.manager;



import it.eng.utility.cryptosigner.manager.SignatureManager.CONFIGURATION;

import java.io.File;
import java.util.Date;

/**
 * Bean che descrive la configurazione per 
 * eseguire i controlli dei file firmati 
 * @author Stefano Zennaro
 *
 */
public class SignatureManagerConfig {
	
	/**
	 * Tipo di contenuto EMBEDDED/DETACHED
	 */
	public static final int EMBEDDED_CONTENT = 0;
	public static final int DETACHED_CONTENT = 1;
	
	public enum ContentType{
		EMBEDDED_CONTENT, DETACHED_CONTENT
	}
	
	private ContentType contentType;
	
	private File contentFile;
	
	private File signatureFile;
	
	private File timeStampFile;
	
	private File[] timeStampExtensions;
	
	private boolean isTimeStampEmbedded = true;

	private Date referenceDate;
	
	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public File getContentFile() {
		return contentFile;
	}

	/**
	 * Definisce il file corrispondente al contenuto firmato
	 * @param contentFile
	 */
	public void setContentFile(File contentFile) {
		this.contentFile = contentFile;
	}

	public File getSignatureFile() {
		return signatureFile;
	}

	/**
	 * Definisce il file contenete la firma digitale
	 * @param signatureFile
	 */
	public void setSignatureFile(File signatureFile) {
		this.signatureFile = signatureFile;
	}

	/**
	 * 
	 * @return Recupera il file contenente la marca temporale
	 */
	public File getTimeStampFile() {
		return timeStampFile;
	}

	/**
	 * Definisce il file contente la marca temporale
	 * @param timeStampFile
	 */
	public void setTimeStampFile(File timeStampFile) {
		this.timeStampFile = timeStampFile;
	}

	/**
	 * Recupera i file che corrispondono alla catena di estensioni della marca temporale 
	 * @return file
	 */
	public File[] getTimeStampExtensions() {
		return timeStampExtensions;
	}

	public void setTimeStampExtensions(File[] timeStampExtensions) {
		this.timeStampExtensions = timeStampExtensions;
	}

	/**
	 * 
	 * @return Restituisce true se la marca temporale e embedded
	 */
	public boolean isTimeStampEmbedded() {
		return isTimeStampEmbedded;
	}

	/**
	 * Definisce se la marca temporale e embedded nel file 
	 * corrispondente alla firma digitale
	 * @param isTimeStampEmbedded
	 */
	public void setTimeStampEmbedded(boolean isTimeStampEmbedded) {
		this.isTimeStampEmbedded = isTimeStampEmbedded;
	}
	
	CONFIGURATION getConfiguration() {
		switch (contentType) {
			case DETACHED_CONTENT:
				 if (isTimeStampEmbedded)
					 return CONFIGURATION.CONFIG_4_5;
				 else
					 return CONFIGURATION.CONFIG_6;
			default:
				if (isTimeStampEmbedded)
					return CONFIGURATION.CONFIG_1_2;
				else
					return CONFIGURATION.CONFIG_3;
		}
	}
	
	/**
	 * Verifica se la configurazione attualmente settata e valida
	 * @return true se la configurazione attuale e valida
	 */
	public boolean isValid() {
		CONFIGURATION config = getConfiguration();
		switch (config) {
		case CONFIG_1_2:
		case CONFIG_3:
			if (contentFile==null)
				return false;
			break;
		case CONFIG_4_5:
		case CONFIG_6:
			if (contentFile==null || signatureFile == null)
				return false;
			break;
		default:
			return false;
		}
		return true;
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
