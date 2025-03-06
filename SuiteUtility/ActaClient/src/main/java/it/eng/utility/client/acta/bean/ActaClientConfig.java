package it.eng.utility.client.acta.bean;

import java.util.Properties;

public class ActaClientConfig {

	public static final Integer DEFAULT_TIMEOUT = 60000;
	public static final Integer DEFAULT_CONNECT_TIMEOUT = 60000;

	private static final String KEY_ENDPOINT_REPOSITORY = "acta.repository.endpoint";
	private static final String KEY_ENDPOINT_BACKOFFICE = "acta.backoffice.endpoint";
	private static final String KEY_ENDPOINT_OBJECT = "acta.object.endpoint";
	private static final String KEY_REPONAME = "acta.reponame";
	private static final String KEY_DBKEY_TITOLARIO = "acta.dbKeyTitolario";
	private static final String KEY_APPKEY = "acta.appkey";
	private static final String KEY_FISCALCODE = "acta.fiscalcode";
	private static final String KEY_TIMEOUT_REPOSITORY = "acta.repository.timeout";
	private static final String KEY_TIMEOUT_BACKOFFICE = "acta.backoffice.timeout";
	private static final String KEY_TIMEOUT_OBJECT = "acta.object.timeout";

	private String endpointRepository;
	private String endpointBackOffice;
	private String endpointObject;
	private String appKey;
	private String fiscalCodeUtente;
	private String repoName;
	private String dbKeyTitolario;
	private Integer timeoutRepository;
	private Integer timeoutBackoffice;
	private Integer timeoutObject;
	private Integer connectTimeout;

	public void init(Properties properties) {
		endpointRepository = properties.getProperty(KEY_ENDPOINT_REPOSITORY);
		endpointBackOffice = properties.getProperty(KEY_ENDPOINT_BACKOFFICE);
		endpointObject = properties.getProperty(KEY_ENDPOINT_OBJECT);
		appKey = properties.getProperty(KEY_APPKEY);
		fiscalCodeUtente = properties.getProperty(KEY_FISCALCODE);
		repoName = properties.getProperty(KEY_REPONAME);
		dbKeyTitolario = properties.getProperty(KEY_DBKEY_TITOLARIO);
		timeoutRepository = getTimeout(properties.getProperty(KEY_TIMEOUT_REPOSITORY), DEFAULT_TIMEOUT);
		timeoutBackoffice = getTimeout(properties.getProperty(KEY_TIMEOUT_BACKOFFICE), DEFAULT_TIMEOUT);
		timeoutObject = getTimeout(properties.getProperty(KEY_TIMEOUT_OBJECT), DEFAULT_TIMEOUT);
		connectTimeout = DEFAULT_CONNECT_TIMEOUT;
	}

	public String getEndpointRepository() {
		return endpointRepository;
	}

	public void setEndpointRepository(String endpointRepository) {
		this.endpointRepository = endpointRepository;
	}

	public String getEndpointBackOffice() {
		return endpointBackOffice;
	}

	public void setEndpointBackOffice(String endpointBackOffice) {
		this.endpointBackOffice = endpointBackOffice;
	}

	public String getEndpointObject() {
		return endpointObject;
	}

	public void setEndpointObject(String endpointObject) {
		this.endpointObject = endpointObject;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getFiscalCodeUtente() {
		return fiscalCodeUtente;
	}

	public void setFiscalCodeUtente(String fiscalCodeUtente) {
		this.fiscalCodeUtente = fiscalCodeUtente;
	}

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	public Integer getTimeoutRepository() {
		return timeoutRepository;
	}

	public void setTimeoutRepository(Integer timeoutRepository) {
		this.timeoutRepository = timeoutRepository;
	}

	public Integer getTimeoutBackoffice() {
		return timeoutBackoffice;
	}

	public void setTimeoutBackoffice(Integer timeoutBackoffice) {
		this.timeoutBackoffice = timeoutBackoffice;
	}

	public Integer getTimeoutObject() {
		return timeoutObject;
	}

	public void setTimeoutObject(Integer timeoutObject) {
		this.timeoutObject = timeoutObject;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	private Integer getTimeout(String value, Integer defaultValue) {
		Integer timeout = defaultValue;
		try {
			timeout = Integer.valueOf(value);
		} catch (RuntimeException e) {
		}
		return timeout;
	}

	public String getDbKeyTitolario() {
		return dbKeyTitolario;
	}

	public void setDbKeyTitolario(String dbKeyTitolario) {
		this.dbKeyTitolario = dbKeyTitolario;
	}
	
	

}
