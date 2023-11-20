/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchOut implements Serializable{

	private static final long serialVersionUID = 3838293268565241122L;
	
	private List<EmailResultBean> emails;

	public void setEmails(List<EmailResultBean> emails) {
		this.emails = emails;
	}

	public List<EmailResultBean> getEmails() {
		return emails;
	}

}
