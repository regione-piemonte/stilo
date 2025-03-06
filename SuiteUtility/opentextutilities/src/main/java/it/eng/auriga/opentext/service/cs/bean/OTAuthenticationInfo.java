package it.eng.auriga.opentext.service.cs.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * oggetto restituito a fronte dell'autenticazione sul Content Server
 * @author tbarbaro
 *
 */
public class OTAuthenticationInfo  implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 8688293749940418620L;
	private String token;
	private Date expiringTime;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiringTime() {
		return expiringTime;
	}

	public void setExpiringTime(Date expiringTime) {
		this.expiringTime = expiringTime;
	}

	public OTAuthenticationInfo(String token, Date expiringTime) {
		this.token = token;
		this.expiringTime = expiringTime;
	}
	
	
}
