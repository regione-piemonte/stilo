package it.eng.hsm.client.config;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class WSConfig {

	@XmlVariabile(nome = "wsdlEndpoint", tipo = TipoVariabile.SEMPLICE)
	private String wsdlEndpoint;
	@XmlVariabile(nome = "wsdlInternalEndpoint", tipo = TipoVariabile.SEMPLICE)
	private String wsdlInternalEndpoint;
	@XmlVariabile(nome = "serviceNS", tipo = TipoVariabile.SEMPLICE)
	private String serviceNS;
	@XmlVariabile(nome = "serviceName", tipo = TipoVariabile.SEMPLICE)
	private String serviceName;

	public String getWsdlEndpoint() {
		return wsdlEndpoint;
	}

	public void setWsdlEndpoint(String wsdlEndpoint) {
		this.wsdlEndpoint = wsdlEndpoint;
	}

	public String getServiceNS() {
		return serviceNS;
	}

	public void setServiceNS(String serviceNS) {
		this.serviceNS = serviceNS;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getWsdlInternalEndpoint() {
		return wsdlInternalEndpoint;
	}

	public void setWsdlInternalEndpoint(String wsdlInternalEndpoint) {
		this.wsdlInternalEndpoint = wsdlInternalEndpoint;
	}
	
	
}
