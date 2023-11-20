/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MailboxBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -181091683302162313L;
	
	private String idMailbox;

	private String idAccount;

	private String description;

	private String name;

	private String processFlow;

	private String status;

	private String folder;

	public String getIdMailbox() {
		return idMailbox;
	}

	public void setIdMailbox(String idMailbox) {
		this.idMailbox = idMailbox;
	}

	public String getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProcessFlow() {
		return processFlow;
	}

	public void setProcessFlow(String processFlow) {
		this.processFlow = processFlow;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}



}
