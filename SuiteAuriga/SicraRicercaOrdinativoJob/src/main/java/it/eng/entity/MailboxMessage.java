/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 6-feb-2015 16.51.43 by Hibernate Tools 3.4.0.CR1

import java.sql.Blob;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * MailboxMessage generated by hbm2java
 */
@Entity
@Table(name = "MAILBOX_MESSAGE")
public class MailboxMessage implements java.io.Serializable {

	private static final long serialVersionUID = 4641925078693092111L;

	private MailboxMessageId id;
	private Mailbox mailbox;
	private String status;
	private String statusMessage;
	private String urlMime;
	private Blob datamail;
	private Date dateDischarged;

	public MailboxMessage() {
	}

	public MailboxMessage(MailboxMessageId id, Mailbox mailbox, String status, Date dateDischarged) {
		this.id = id;
		this.mailbox = mailbox;
		this.status = status;
		this.dateDischarged = dateDischarged;
	}

	public MailboxMessage(MailboxMessageId id, Mailbox mailbox, String status, String statusMessage, String urlMime,
			Blob datamail, Date dateDischarged) {
		this.id = id;
		this.mailbox = mailbox;
		this.status = status;
		this.statusMessage = statusMessage;
		this.urlMime = urlMime;
		this.datamail = datamail;
		this.dateDischarged = dateDischarged;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "messageId", column = @Column(name = "MESSAGE_ID", nullable = false, length = 400)),
			@AttributeOverride(name = "idMailbox", column = @Column(name = "ID_MAILBOX", nullable = false, length = 40)) })
	public MailboxMessageId getId() {
		return this.id;
	}

	public void setId(MailboxMessageId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_MAILBOX", nullable = false, insertable = false, updatable = false)
	public Mailbox getMailbox() {
		return this.mailbox;
	}

	public void setMailbox(Mailbox mailbox) {
		this.mailbox = mailbox;
	}

	@Column(name = "STATUS", nullable = false, length = 30)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "STATUS_MESSAGE", length = 1000)
	public String getStatusMessage() {
		return this.statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@Column(name = "URL_MIME", length = 300)
	public String getUrlMime() {
		return this.urlMime;
	}

	public void setUrlMime(String urlMime) {
		this.urlMime = urlMime;
	}

	@Column(name = "DATAMAIL")
	public Blob getDatamail() {
		return this.datamail;
	}

	public void setDatamail(Blob datamail) {
		this.datamail = datamail;
	}

	@Column(name = "DATE_DISCHARGED", nullable = false, length = 7)
	public Date getDateDischarged() {
		return this.dateDischarged;
	}

	public void setDateDischarged(Date dateDischarged) {
		this.dateDischarged = dateDischarged;
	}

}
