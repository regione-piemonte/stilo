/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * login per le store procedures
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class MailLoginBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -6938539831907003794L;
	
	private String token;

	private String userId;
	
	private String schema;
	
	private String idUtente;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	
}
