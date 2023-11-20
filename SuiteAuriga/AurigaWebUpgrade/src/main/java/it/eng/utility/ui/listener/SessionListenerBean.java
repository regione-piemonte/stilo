/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

public class SessionListenerBean {
	
	private String idSession;
	private String denominazione;
	private String userid;
	private Date creationTime;
	private Date sessionLastAccessedTime;
	Integer sessionMaxInactiveInterval;
	
	public String getIdSession() {
		return idSession;
	}
	
	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}
	
	public Date getCreationTime() {
		return creationTime;
	}
	
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getSessionLastAccessedTime() {
		return sessionLastAccessedTime;
	}
	
	public void setSessionLastAccessedTime(Date sessionLastAccessedTime) {
		this.sessionLastAccessedTime = sessionLastAccessedTime;
	}

	public Integer getSessionMaxInactiveInterval() {
		return sessionMaxInactiveInterval;
	}

	public void setSessionMaxInactiveInterval(Integer sessionMaxInactiveInterval) {
		this.sessionMaxInactiveInterval = sessionMaxInactiveInterval;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}