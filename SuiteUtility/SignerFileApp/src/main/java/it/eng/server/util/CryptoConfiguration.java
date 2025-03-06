package it.eng.server.util;

import java.io.Serializable;

/**
 * Classe che setta i parametri di configurazione del sistema
 * @author Rigo Michele
 * @version 0.1
 */
public class CryptoConfiguration implements Serializable{

	/**
	 * Utente del proxy
	 */
	private String proxyUser = null;
	
	/**
	 * Password del proxy
	 */
	private String proxyPassword = null;
	
	/**
	 * Host del proxy
	 */
	private String proxyHost = null;
	
	/**
	 * Porta del proxy
	 */
	private Integer proxyPort = null;

	
//	/**
//	 * Restituisce il campo di autenticazione del proxy criptato
//	 * @return
//	 */
	public String getProxyAuth(){
		String auth = "";
		if (proxyUser != null && proxyPassword != null) {
            String authString = proxyUser + ":" + proxyPassword;
            auth = "Basic " +  new String(new org.apache.commons.codec.binary.Base64().encode(authString.getBytes()));
        }
		return auth;
	}
	
	/**
	 * Indica se il proxy e' configurato o meno
	 * @return
	 */
	public boolean isProxy(){
		boolean ret = false;
		if(proxyHost!=null && proxyPort!=null){
			ret = true;
		}
		return ret;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public Integer getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}
}