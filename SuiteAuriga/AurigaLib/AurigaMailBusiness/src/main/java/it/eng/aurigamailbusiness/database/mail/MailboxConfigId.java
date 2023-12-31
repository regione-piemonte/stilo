/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 2-feb-2017 16.02.31 by Hibernate Tools 3.5.0.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MailboxConfigId generated by hbm2java
 */
@Embeddable
public class MailboxConfigId implements java.io.Serializable {

	private static final long serialVersionUID = -8202540221758817677L;
	private String configKey;
	private String idMailbox;

	public MailboxConfigId() {
	}

	public MailboxConfigId(String configKey, String idMailbox) {
		this.configKey = configKey;
		this.idMailbox = idMailbox;
	}

	@Column(name = "CONFIG_KEY", nullable = false, length = 100)
	public String getConfigKey() {
		return this.configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
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
		if (!(other instanceof MailboxConfigId))
			return false;
		MailboxConfigId castOther = (MailboxConfigId) other;

		return ((this.getConfigKey() == castOther.getConfigKey())
				|| (this.getConfigKey() != null && castOther.getConfigKey() != null && this.getConfigKey().equals(castOther.getConfigKey())))
				&& ((this.getIdMailbox() == castOther.getIdMailbox())
						|| (this.getIdMailbox() != null && castOther.getIdMailbox() != null && this.getIdMailbox().equals(castOther.getIdMailbox())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getConfigKey() == null ? 0 : this.getConfigKey().hashCode());
		result = 37 * result + (getIdMailbox() == null ? 0 : this.getIdMailbox().hashCode());
		return result;
	}

}
