package it.eng.utility.cryptosigner.controller.bean;

import it.eng.utility.cryptosigner.data.AbstractSigner;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

public class InputTimeStampBean extends InputBean{

	/**
	 * Marca temporale embedded 
	 */
	private File timeStampWithContentFile;
	
	/**
	 * File contenente la marca temporale detached
	 */
	private File timeStampFile;
	
	/**
	 * Contenuto marcato
	 */
	private File contentFile;
	
	
	/**
	 * Catena delle estensioni della marca temporale
	 */
	private File[] timeStampExtensionsChain;

	private String fileName;
	private AbstractSigner signer;
	private Boolean isSigned = false;
	
	/**
	 * Data di riferimento per la verifica della 
	 * validita dell'ultima marca temporale
	 */
	private Date referenceDate;
	
	/**
	 * @return the timeStampWithContentFile
	 */
	public File getTimeStampWithContentFile() {
		return timeStampWithContentFile;
	}

	/**
	 * @param timeStampWithContentFile the timeStampWithContentFile to set
	 */
	public void setTimeStampWithContentFile(File timeStampWithContentFile) {
		this.timeStampWithContentFile = timeStampWithContentFile;
	}

	/**
	 * @return the timeStampFile
	 */
	public File getTimeStampFile() {
		return timeStampFile;
	}

	/**
	 * @param timeStampFile the timeStampFile to set
	 */
	public void setTimeStampFile(File timeStampFile) {
		this.timeStampFile = timeStampFile;
	}

	/**
	 * @return the contentFile
	 */
	public File getContentFile() {
		return contentFile;
	}

	/**
	 * @param contentFile the contentFile to set
	 */
	public void setContentFile(File contentFile) {
		this.contentFile = contentFile;
	}

	/**
	 * @return the timeStampExtensionsChain
	 */
	public File[] getTimeStampExtensionsChain() {
		return timeStampExtensionsChain;
	}

	/**
	 * @param timeStampExtensionsChain the timeStampExtensionsChain to set
	 */
	public void setTimeStampExtensionsChain(File[] timeStampExtensionsChain) {
		this.timeStampExtensionsChain = timeStampExtensionsChain;
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
	
	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	

	public AbstractSigner getSigner() {
		return signer;
	}

	public void setSigner(AbstractSigner signer) {
		this.signer = signer;
	}

	public Boolean getIsSigned() {
		return isSigned;
	}

	public void setIsSigned(Boolean isSigned) {
		this.isSigned = isSigned;
	}

	@Override
	public String toString() {
		return "InputTimeStampBean [timeStampWithContentFile="
				+ timeStampWithContentFile + ", timeStampFile=" + timeStampFile
				+ ", contentFile=" + contentFile
				+ ", timeStampExtensionsChain="
				+ Arrays.toString(timeStampExtensionsChain)
				+ ", referenceDate=" + referenceDate + "]";
	}

	
	
}
