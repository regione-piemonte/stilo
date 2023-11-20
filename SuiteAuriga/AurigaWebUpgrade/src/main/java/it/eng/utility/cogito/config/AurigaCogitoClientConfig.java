/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.core.service.client.FactoryBusiness.BusinessType;
//import it.eng.sue.client.config.ProxyConfig;

/**
 * Configurazione per la chiamata al ws REST di 
 * COGITO
 * 
 * @author DCoticone
 *
 */
public class AurigaCogitoClientConfig {

	private String url;
	private String user;
	private String password;
	private String maxNroTitolazioni;
	private String maxMbSize;
	
	//private ProxyConfig proxyConfig;
	
	private BusinessType businesstype = BusinessType.REST;
	private static AurigaCogitoClientConfig instance;
	
	public static synchronized AurigaCogitoClientConfig getInstance() {
		if(instance==null){
			instance = new AurigaCogitoClientConfig();
			
		}
		return instance;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	
	public BusinessType getBusinesstype() {
		return businesstype;
	}

	public void setBusinesstype(BusinessType businesstype) {
		this.businesstype = businesstype;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMaxNroTitolazioni() {
		return maxNroTitolazioni;
	}

	public void setMaxNroTitolazioni(String maxNroTitolazioni) {
		this.maxNroTitolazioni = maxNroTitolazioni;
	}

	public String getMaxMbSize() {
		return maxMbSize;
	}

	public void setMaxMbSize(String maxMbSize) {
		this.maxMbSize = maxMbSize;
	}

	/*
	public ProxyConfig getProxyConfig() {
		return proxyConfig;
	}

	public void setProxyConfig(ProxyConfig proxyConfig) {
		this.proxyConfig = proxyConfig;
	}
	*/
}