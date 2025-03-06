package it.eng.hsm.client.config;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class MarcaConfig {

	@XmlVariabile(nome = "serviceUrl", tipo = TipoVariabile.SEMPLICE)
	private String serviceUrl;
	@XmlVariabile(nome = "serviceUser", tipo = TipoVariabile.SEMPLICE)
	private String serviceUser;
	@XmlVariabile(nome = "servicePassword", tipo = TipoVariabile.SEMPLICE)
	private String servicePassword;
	@XmlVariabile(nome = "useAuth", tipo = TipoVariabile.SEMPLICE)
	private boolean useAuth;

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getServiceUser() {
		return serviceUser;
	}

	public void setServiceUser(String serviceUser) {
		this.serviceUser = serviceUser;
	}

	public String getServicePassword() {
		return servicePassword;
	}

	public void setServicePassword(String servicePassword) {
		this.servicePassword = servicePassword;
	}

	public boolean isUseAuth() {
		return useAuth;
	}

	public void setUseAuth(boolean useAuth) {
		this.useAuth = useAuth;
	}

}
