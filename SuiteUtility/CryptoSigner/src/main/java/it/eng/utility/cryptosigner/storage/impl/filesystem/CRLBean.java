package it.eng.utility.cryptosigner.storage.impl.filesystem;

import java.io.Serializable;

/**
 * Bean di appoggio allo storage FileSystemCRLStorage che wrappa 
 * gli attributi di una CRL da salvare
 * @author Rigo Michele
 * @version 0.1
 */
class CRLBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subjectDN;
	private String filePath;
	private String country;
	private String serviceProviderName;
	
	/**
	 * Recupera il riferimento al file
	 * @return
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * Definisce il riferimento al file
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * Recupera il nome dell'entit� associata al certificato cos�
	 * come  riportato nel Distinguished Name (RFC2459)
	 * @return
	 */
	public String getSubjectDN() {
		return subjectDN;
	}
	
	/**
	 * Definisce il nome dell'entit� associata al certificato
	 * @return
	 */
	public void setSubjectDN(String subjectDN) {
		this.subjectDN = subjectDN;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getServiceProviderName() {
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}
}
