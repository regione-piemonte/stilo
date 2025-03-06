package it.eng.utility.cryptosigner.storage.impl.filesystem;

import java.io.Serializable;
import java.util.Date;
/**
 * Bean di appoggio allo storage del FileSystemCAStorage che wrappa gli
 * attributi di una Certification Authority da salvare
 * @author Rigo Michele
 * @version 0.1
 */
public class CABean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subjectDN;
	private Boolean active = true;
	private String filePath;
	private String serviceStatus;
	private String country;
	private String serviceProviderName;
	private String serviceName;
	private String serviceType;
	private Date dataAttivazioneCertificato;
	private Date dataScadenzaCertificato;
	private Date dataRevocaCertificato;
	
	
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
	 * Recupera il nome dell'entità associata al certificato così
	 * come  riportato nel Distinguished Name (RFC2459)
	 * @return
	 */
	public String getSubjectDN() {
		return subjectDN;
	}
	
	/**
	 * Definisce il nome dell'entità associata al certificato così
	 * come  riportato nel Distinguished Name (RFC2459)
	 * @return
	 */
	public void setSubjectDN(String subjectDN) {
		this.subjectDN = subjectDN;
	}
	
	/**
	 * Restituisce true se il certificato è attivo
	 * @return
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Definisce se il certificato è attivo
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Date getDataAttivazioneCertificato() {
		return dataAttivazioneCertificato;
	}

	public void setDataAttivazioneCertificato(Date dataAttivazioneCertificato) {
		this.dataAttivazioneCertificato = dataAttivazioneCertificato;
	}

	public Date getDataScadenzaCertificato() {
		return dataScadenzaCertificato;
	}

	public void setDataScadenzaCertificato(Date dataScadenzaCertificato) {
		this.dataScadenzaCertificato = dataScadenzaCertificato;
	}

	public Date getDataRevocaCertificato() {
		return dataRevocaCertificato;
	}

	public void setDataRevocaCertificato(Date dataRevocaCertificato) {
		this.dataRevocaCertificato = dataRevocaCertificato;
	}
	
	
}
