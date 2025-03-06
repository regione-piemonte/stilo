package it.eng.hsm.client.config;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class LambdaServiceConfig extends ClientConfig {

	@XmlVariabile(nome = "restConfig", tipo = TipoVariabile.NESTED)
	private RestConfig restConfig;
	@XmlVariabile(nome = "key", tipo = TipoVariabile.SEMPLICE)
	private String key;
	@XmlVariabile(nome = "secret", tipo = TipoVariabile.SEMPLICE)
	private String secret;
	@XmlVariabile(nome = "cfg", tipo = TipoVariabile.SEMPLICE)
	private String cfg;
	@XmlVariabile(nome = "alias", tipo = TipoVariabile.SEMPLICE)
	private String alias;
	@XmlVariabile(nome = "urlAud", tipo = TipoVariabile.SEMPLICE)
	private String urlAud;
	@XmlVariabile(nome = "appCode", tipo = TipoVariabile.SEMPLICE)
	private String appCode;
	
	private PadesConfig padesConfig;
	
	
	public PadesConfig getPadesConfig() {
		return padesConfig;
	}
	public void setPadesConfig(PadesConfig padesConfig) {
		this.padesConfig = padesConfig;
	}
	public RestConfig getRestConfig() {
		return restConfig;
	}
	public void setRestConfig(RestConfig restConfig) {
		this.restConfig = restConfig;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getCfg() {
		return cfg;
	}
	public void setCfg(String cfg) {
		this.cfg = cfg;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getUrlAud() {
		return urlAud;
	}
	public void setUrlAud(String urlAud) {
		this.urlAud = urlAud;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	
}
