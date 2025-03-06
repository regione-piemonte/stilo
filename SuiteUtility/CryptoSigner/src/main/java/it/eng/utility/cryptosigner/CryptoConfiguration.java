package it.eng.utility.cryptosigner;

import it.eng.utility.cryptosigner.utils.CipherUtil;

import java.io.Serializable;

/**
 * Classe che setta i parametri di configurazione del sistema
 * @author Rigo Michele
 * @version 0.1
 */
public class CryptoConfiguration implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Schedulazione per il controllo della revoca dei certificati
	 */
	private String scheduleCARevoke = null;
	
	/**
	 * Schedulazione per l'aggiornamento dei certificati
	 */
	private String scheduleCAUpdate = null;

	/**
	 * URL di recupero dei certificati accreditati
	 */
	private String qualifiedCertificatesURL = null;
	
	/**
	 * Utente del proxy
	 */
	private String proxyUser = null;
	
	/**
	 * Password del proxy
	 */
	private String proxyPassword = null;
	
	private String dominio = null;
	private String NTHost = null;
	
	
	private boolean passwordCifrata = false;
	
	private Boolean NTauth = false;
	
	/**
	 * Host del proxy
	 */
	private String proxyHost = null;
	
	/**
	 * Porta del proxy
	 */
	private Integer proxyPort = null;
	
	/**
	 * indica se si utilizza la schedulazione per scaricare le CA e le CRL
	 */
	private boolean useSchedule;
	
	/**
	 * indica se si deve inizializzare lo storage delle CA
	 */
	private boolean initCAStorage = false;
	
	/**
	 *  numero giorni di attesa per scaricare nuovamente la lista delle CA
	 */
	private Integer dayDownloadCAList =1;
	
	private long lastTimeDownload=-1;
	/**
	 * Restituisce il campo di autenticazione del proxy criptato
	 * @return string
	 */
	public String getProxyAuth() {
		String auth = "";
		if (proxyUser != null && proxyPassword != null) {
            String authString = proxyUser + ":" + proxyPassword;
            auth = "Basic " +  new String(new org.apache.commons.codec.binary.Base64().encode(authString.getBytes()));
        }
		return auth;
	}
	
	/**
	 * Indica se il proxy e configurato o meno
	 * @return boolean
	 */
	public boolean isProxy() {
		boolean ret = false;
		if (proxyHost!=null && proxyPort!=null) {
			ret = true;
		}
		return ret;
	}
	

	/**
	 * Definisce il proxy d'accesso web
	 * @param proxyUser
	 */
	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	/**
	 * Definisce la password d'accesso al proxy
	 * @param proxyPassword
	 */
	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	/**
	 * Restituisce l'host del proxy 
	 * @return string
	 */
	public String getProxyHost() {
		return proxyHost;
	}

	/**
	 * Definisce l'host del proxy 
	 * @param proxyHost
	 */
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	/**
	 * Recupera la porta del proxy
	 * @return int
	 */
	public Integer getProxyPort() {
		return proxyPort;
	}

	/**
	 * Definisce la porta del proxy
	 * @param proxyPort
	 */
	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}
	
	/**
	 * Recupera la schedulazione per il controllo della revoca dei certificati
	 * @return string
	 */
	public String getScheduleCARevoke() {
		return scheduleCARevoke;
	}

	/**
	 * Definisce la schedulazione per il controllo della revoca dei certificati 
	 * @param scheduleCARevoke
	 */
	public void setScheduleCARevoke(String scheduleCARevoke) {
		this.scheduleCARevoke = scheduleCARevoke;
	}

	
	/**
	 * Recupera ls schedulazione per l'aggiornamento dei certificati
	 * @return string
	 */
	public String getScheduleCAUpdate() {
		return scheduleCAUpdate;
	}

	/**
	 * Definisce la schedulazione per l'aggiornamento dei certificati
	 * @param scheduleCAUpdate
	 */
	public void setScheduleCAUpdate(String scheduleCAUpdate) {
		this.scheduleCAUpdate = scheduleCAUpdate;
	}

	/**
	 * Recupera l'URL per reperire i certificati accreditati
	 * @return string
	 */
	public String getQualifiedCertificatesURL() {
		return qualifiedCertificatesURL;
	}

	/**
	 * Definisce l'URL per reperire i certificati accreditati
	 * @param qualifiedCertificatesURL
	 */
	public void setQualifiedCertificatesURL(String qualifiedCertificatesURL) {
		this.qualifiedCertificatesURL = qualifiedCertificatesURL;
	}

	/**
	 * Definisce l'utente per l'accesso al proxy
	 * @return string
	 */
	public String getProxyUser() {
		return proxyUser;
	}

	/**
	 * Recupera l'utente per l'accesso al proxy
	 * @return string
	 */
	public String getProxyPassword() {
		if( isPasswordCifrata()) 
			return CipherUtil.decrypt( proxyPassword );
		else 
			return proxyPassword;
	}

	public boolean isUseSchedule() {
		return useSchedule;
	}

	public void setUseSchedule(boolean useSchedule) {
		this.useSchedule = useSchedule;
	}

	public Integer getDayDownloadCAList() {
		return dayDownloadCAList;
	}

	public void setDayDownloadCAList(Integer dayDownloadCAList) {
		this.dayDownloadCAList = dayDownloadCAList;
	}

	public long getLastTimeDownload() {
		return lastTimeDownload;
	}

	public void setLastTimeDownload(long lastTimeDownload) {
		this.lastTimeDownload = lastTimeDownload;
	}

	public boolean isPasswordCifrata() {
		return passwordCifrata;
	}

	public void setPasswordCifrata(boolean passwordCifrata) {
		this.passwordCifrata = passwordCifrata;
	}
	
	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public Boolean getNTauth() {
		return NTauth;
	}

	public void setNTauth(Boolean nTauth) {
		NTauth = nTauth;
	}

	public String getNTHost() {
		return NTHost;
	}

	public void setNTHost(String nTHost) {
		NTHost = nTHost;
	}
	
	public boolean isInitCAStorage() {
		return initCAStorage;
	}

	public void setInitCAStorage(boolean initCAStorage) {
		this.initCAStorage = initCAStorage;
	}
 
}