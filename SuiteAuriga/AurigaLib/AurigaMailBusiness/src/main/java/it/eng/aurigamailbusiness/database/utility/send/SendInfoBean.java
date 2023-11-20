/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.SenderBean;

import java.util.List;

import javax.mail.internet.MimeMessage;

public class SendInfoBean {
	
	private MimeMessage message;
	
	private List<SenderBean> emails;
	
	private String idAccount;

	public MimeMessage getMessage() {
		return message;
	}

	public void setMessage(MimeMessage message) {
		this.message = message;
	}

	public List<SenderBean> getEmails() {
		return emails;
	}

	public void setEmails(List<SenderBean> emails) {
		this.emails = emails;
	}

	public String getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}

}
