/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 6-feb-2015 16.51.43 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MailboxMessageId generated by hbm2java
 */
@Embeddable
public class MailboxMessageId implements java.io.Serializable {

	private static final long serialVersionUID = 1625148959363123936L;

	private String messageId;
	private String idMailbox;

	public MailboxMessageId() {
	}

	public MailboxMessageId(String messageId, String idMailbox) {
		this.messageId = messageId;
		this.idMailbox = idMailbox;
	}

	@Column(name = "MESSAGE_ID", nullable = false, length = 400)
	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@Column(name = "ID_MAILBOX", nullable = false, length = 40)
	public String getIdMailbox() {
		return this.idMailbox;
	}

	public void setIdMailbox(String idMailbox) {
		this.idMailbox = idMailbox;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MailboxMessageId))
			return false;
		MailboxMessageId castOther = (MailboxMessageId) other;

		return ((this.getMessageId() == castOther.getMessageId()) || (this.getMessageId() != null
				&& castOther.getMessageId() != null && this.getMessageId().equals(castOther.getMessageId())))
				&& ((this.getIdMailbox() == castOther.getIdMailbox()) || (this.getIdMailbox() != null
						&& castOther.getIdMailbox() != null && this.getIdMailbox().equals(castOther.getIdMailbox())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getMessageId() == null ? 0 : this.getMessageId().hashCode());
		result = 37 * result + (getIdMailbox() == null ? 0 : this.getIdMailbox().hashCode());
		return result;
	}

}
