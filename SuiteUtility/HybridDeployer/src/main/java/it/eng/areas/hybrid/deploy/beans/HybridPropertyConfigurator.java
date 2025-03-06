package it.eng.areas.hybrid.deploy.beans;

public class HybridPropertyConfigurator {

	private String hybridPort;
	private String hybridPortSSL;
	private String hybridWorkFolder;
	private Boolean hybridForceHttps;

	// Configurazioni per l'utilizzo di Hybrid tramite CAS
	private Boolean enableHybridCasConfig;
	private String accessControlAllowOriginValue;
	private String accessControlAllowMethodsValue;
	private String accessControlAllowHeadersValue;
	private String accessControlMaxAgeValue;
	private String baseUrl;

	public String getHybridPort() {
		return hybridPort;
	}

	public void setHybridPort(String hybridPort) {
		this.hybridPort = hybridPort;
	}

	public String getHybridPortSSL() {
		return hybridPortSSL;
	}

	public void setHybridPortSSL(String hybridPortSSL) {
		this.hybridPortSSL = hybridPortSSL;
	}

	public String getHybridWorkFolder() {
		return hybridWorkFolder;
	}

	public void setHybridWorkFolder(String hybridWorkFolder) {
		this.hybridWorkFolder = hybridWorkFolder;
	}

	public Boolean getHybridForceHttps() {
		return hybridForceHttps;
	}

	public Boolean getEnableHybridCasConfig() {
		return enableHybridCasConfig;
	}

	public void setEnableHybridCasConfig(Boolean enableHybridCasConfig) {
		this.enableHybridCasConfig = enableHybridCasConfig;
	}

	public void setHybridForceHttps(Boolean hybridForceHttps) {
		this.hybridForceHttps = hybridForceHttps;
	}

	public String getAccessControlAllowOriginValue() {
		return accessControlAllowOriginValue;
	}

	public void setAccessControlAllowOriginValue(String accessControlAllowOriginValue) {
		this.accessControlAllowOriginValue = accessControlAllowOriginValue;
	}

	public String getAccessControlAllowMethodsValue() {
		return accessControlAllowMethodsValue;
	}

	public void setAccessControlAllowMethodsValue(String accessControlAllowMethodsValue) {
		this.accessControlAllowMethodsValue = accessControlAllowMethodsValue;
	}

	public String getAccessControlAllowHeadersValue() {
		return accessControlAllowHeadersValue;
	}

	public void setAccessControlAllowHeadersValue(String accessControlAllowHeadersValue) {
		this.accessControlAllowHeadersValue = accessControlAllowHeadersValue;
	}

	public String getAccessControlMaxAgeValue() {
		return accessControlMaxAgeValue;
	}

	public void setAccessControlMaxAgeValue(String accessControlMaxAgeValue) {
		this.accessControlMaxAgeValue = accessControlMaxAgeValue;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}
