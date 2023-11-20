/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MailboxFolderInfoBean {

	@Override
	public String toString() {
		return "MailboxFolderInfoBean [name=" + name + ", countMessage=" + countMessage + "]";
	}

	private static final long serialVersionUID = -7577214025941704126L;

	private String name;
	private Integer countMessage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCountMessage() {
		return countMessage;
	}

	public void setCountMessage(Integer countMessage) {
		this.countMessage = countMessage;
	}

}
