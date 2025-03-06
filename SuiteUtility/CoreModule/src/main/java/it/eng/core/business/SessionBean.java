package it.eng.core.business;

import java.util.Date;

import org.hibernate.Session;

/**
 * Bean che mappa la Session di hibernate
 * @author michele
 *
 */
public class SessionBean {

	private String uuid;	
	private Session session;
	private Date lastUse;
	private Boolean inUse;	
	
	public Session getSession() {
		return session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getLastUse() {
		return lastUse;
	}

	public void setLastUse(Date lastUse) {
		this.lastUse = lastUse;
	}

	public Boolean getInUse() {
		return inUse;
	}

	public void setInUse(Boolean inUse) {
		this.inUse = inUse;
	}
}