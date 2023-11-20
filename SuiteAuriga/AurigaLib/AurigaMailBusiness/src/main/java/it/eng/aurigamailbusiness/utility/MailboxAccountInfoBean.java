/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MailboxAccountInfoBean {

	private static final long serialVersionUID = -1940013635638934170L;

	private String idMailbox;

	private String idAccount;

	private Long totalMessagesDischarged = 0L;

	private Long totalMessagesProcessed = 0L;

	private Long totalMessagesSent = 0L;

	private String lastMessageIdDischarged;

	private Date dateLastMessageDischarged;

	private String lastMessageIdProcessed;

	private String lastIdEmailProcessed;

	private Date dateLastMessageProcessed;

	private Date dateLastMessageSent;

	private String lastMessageIdSent;

	private String lastIdEmailSent;

	private MailboxIMAPInfoBean infoIMAP;

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

	public Long getTotalMessagesDischarged() {
		return totalMessagesDischarged;
	}

	public void setTotalMessagesDischarged(Long totalMessagesDischarged) {
		this.totalMessagesDischarged = totalMessagesDischarged;
	}

	public Long getTotalMessagesProcessed() {
		return totalMessagesProcessed;
	}

	public void setTotalMessagesProcessed(Long totalMessagesProcessed) {
		this.totalMessagesProcessed = totalMessagesProcessed;
	}

	public Long getTotalMessagesSent() {
		return totalMessagesSent;
	}

	public void setTotalMessagesSent(Long totalMessagesSent) {
		this.totalMessagesSent = totalMessagesSent;
	}

	public String getLastMessageIdDischarged() {
		return lastMessageIdDischarged;
	}

	public void setLastMessageIdDischarged(String lastMessageIdDischarged) {
		this.lastMessageIdDischarged = lastMessageIdDischarged;
	}

	public Date getDateLastMessageDischarged() {
		return dateLastMessageDischarged;
	}

	public void setDateLastMessageDischarged(Date dateLastMessageDischarged) {
		this.dateLastMessageDischarged = dateLastMessageDischarged;
	}

	public String getLastMessageIdProcessed() {
		return lastMessageIdProcessed;
	}

	public void setLastMessageIdProcessed(String lastMessageIdProcessed) {
		this.lastMessageIdProcessed = lastMessageIdProcessed;
	}

	public String getLastIdEmailProcessed() {
		return lastIdEmailProcessed;
	}

	public void setLastIdEmailProcessed(String lastIdEmailProcessed) {
		this.lastIdEmailProcessed = lastIdEmailProcessed;
	}

	public Date getDateLastMessageProcessed() {
		return dateLastMessageProcessed;
	}

	public void setDateLastMessageProcessed(Date dateLastMessageProcessed) {
		this.dateLastMessageProcessed = dateLastMessageProcessed;
	}

	public Date getDateLastMessageSent() {
		return dateLastMessageSent;
	}

	public void setDateLastMessageSent(Date dateLastMessageSent) {
		this.dateLastMessageSent = dateLastMessageSent;
	}

	@Override
	public String toString() {
		return "MailboxInfoBean [idMailbox=" + idMailbox + ", idAccount=" + idAccount + ", totalMessagesDischarged=" + totalMessagesDischarged
				+ ", totalMessagesProcessed=" + totalMessagesProcessed + ", totalMessagesSent=" + totalMessagesSent + ", lastMessageIdDischarged="
				+ lastMessageIdDischarged + ", dateLastMessageDischarged=" + dateLastMessageDischarged + ", lastMessageIdProcessed=" + lastMessageIdProcessed
				+ ", lastIdEmailProcessed=" + lastIdEmailProcessed + ", dateLastMessageProcessed=" + dateLastMessageProcessed + ", dateLastMessageSent="
				+ dateLastMessageSent + ", lastMessageIdSent=" + lastMessageIdSent + ", lastIdEmailSent=" + lastIdEmailSent + ", infoIMAP=" + infoIMAP + "]";
	}

	public String getLastMessageIdSent() {
		return lastMessageIdSent;
	}

	public void setLastMessageIdSent(String lastMessageIdSent) {
		this.lastMessageIdSent = lastMessageIdSent;
	}

	public String getLastIdEmailSent() {
		return lastIdEmailSent;
	}

	public void setLastIdEmailSent(String lastIdEmailSent) {
		this.lastIdEmailSent = lastIdEmailSent;
	}

	public MailboxIMAPInfoBean getInfoIMAP() {
		return infoIMAP;
	}

	public void setInfoIMAP(MailboxIMAPInfoBean infoIMAP) {
		this.infoIMAP = infoIMAP;
	}

}
