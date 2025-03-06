package it.eng.utility.cryptosigner.storage.impl.filesystem;

import java.security.cert.X509Certificate;
import java.util.Date;

public class CACertificate {

	private X509Certificate certificate;
	private String country;
	private String serviceProviderName;
	private String serviceName;
	private String serviceStatus;
	private String serviceType;
	private Date dataAttivazioneCertificato;
	private Date dataScadenzaCertificato;
	private Date dataRevocaCertificato;
	
	public X509Certificate getCertificate() {
		return certificate;
	}
	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
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
	public String getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
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
	@Override
	public String toString() {
		return "CACertificate [certificate=" + certificate + ", country=" + country + ", serviceProviderName="
				+ serviceProviderName + ", serviceName=" + serviceName + ", serviceStatus=" + serviceStatus
				+ ", serviceType=" + serviceType + ", dataAttivazioneCertificato=" + dataAttivazioneCertificato
				+ ", dataScadenzaCertificato=" + dataScadenzaCertificato + ", dataRevocaCertificato="
				+ dataRevocaCertificato + ", getCertificate()=" + ", getCountry()=" + getCountry()
				+ ", getServiceProviderName()=" + getServiceProviderName() + ", getServiceName()=" + getServiceName()
				+ ", getServiceStatus()=" + getServiceStatus() + ", getServiceType()=" + getServiceType()
				+ ", getDataAttivazioneCertificato()=" + getDataAttivazioneCertificato()
				+ ", getDataScadenzaCertificato()=" + getDataScadenzaCertificato() + ", getDataRevocaCertificato()="
				+ getDataRevocaCertificato() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
	
}
