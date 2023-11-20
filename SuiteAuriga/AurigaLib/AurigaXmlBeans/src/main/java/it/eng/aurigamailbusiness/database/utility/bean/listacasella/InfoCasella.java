/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean che contiene informazioni sulla casella
 * 
 * @author Jacopo
 *
 */
@XmlRootElement
public class InfoCasella implements Serializable{

	private static final long serialVersionUID = -4028847205561266615L;
	
	private String idAccount;
	private String account;
	private Boolean isPec;

	public String getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Boolean getIsPec() {
		return isPec;
	}

	public void setIsPec(Boolean isPec) {
		this.isPec = isPec;
	}
	
}