/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * riferimento delle email spedite
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class EmailSentReferenceBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -6938539831907003794L;
	
	private List<SenderBean> sentMails;

	private List<String> idEmails;

	public List<String> getIdEmails() {
		return idEmails;
	}

	public void setIdEmails(List<String> idEmails) {
		this.idEmails = idEmails;
	}

	public List<SenderBean> getSentMails() {
		return sentMails;
	}

	public void setSentMails(List<SenderBean> sentMails) {
		this.sentMails = sentMails;
	}

}
