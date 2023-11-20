/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CheckLockOutBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private TEmailMgoBean mail;

	private Boolean isLocked;

	public TEmailMgoBean getMail() {
		return mail;
	}

	public void setMail(TEmailMgoBean mail) {
		this.mail = mail;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

}
