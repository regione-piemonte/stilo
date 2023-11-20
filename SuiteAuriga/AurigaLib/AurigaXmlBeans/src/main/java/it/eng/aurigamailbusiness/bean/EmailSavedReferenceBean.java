/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

/**
 * riferimento delle email salvate e non ancora spedite
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class EmailSavedReferenceBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -6938539831907003794L;

	private List<SenderBean> savedEmails;

	private List<String> idEmails;

	public List<String> getIdEmails() {
		return idEmails;
	}

	public void setIdEmails(List<String> idEmails) {
		this.idEmails = idEmails;
	}

	public List<SenderBean> getSavedEmails() {
		return savedEmails;
	}

	public void setSavedEmails(List<SenderBean> savedEmails) {
		this.savedEmails = savedEmails;
	}

}
