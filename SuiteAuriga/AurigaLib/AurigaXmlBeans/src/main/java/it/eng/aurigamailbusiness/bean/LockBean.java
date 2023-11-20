/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * classe da utilizzare per i metodi di lock
 * @author jravagnan
 *
 */
@XmlRootElement
public class LockBean extends AbstractBean implements Serializable{

	private static final long serialVersionUID = 2219977861218598219L;

	String mailId;

	String userId;

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
}
