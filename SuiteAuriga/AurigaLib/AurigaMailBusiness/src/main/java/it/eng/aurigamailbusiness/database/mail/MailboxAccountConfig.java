/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 2-feb-2017 16.02.31 by Hibernate Tools 3.5.0.Final

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
 * MailboxAccountConfig generated by hbm2java
 */
@Entity
@Table(name = "MAILBOX_ACCOUNT_CONFIG")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class MailboxAccountConfig implements java.io.Serializable {

	private static final long serialVersionUID = 6080910081615342865L;
	private MailboxAccountConfigId id;
	private MailboxAccount mailboxAccount;
	private String configValue;

	public MailboxAccountConfig() {
	}

	public MailboxAccountConfig(MailboxAccountConfigId id, MailboxAccount mailboxAccount, String configValue) {
		this.id = id;
		this.mailboxAccount = mailboxAccount;
		this.configValue = configValue;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "idAccount", column = @Column(name = "ID_ACCOUNT", nullable = false, length = 40)),
			@AttributeOverride(name = "configKey", column = @Column(name = "CONFIG_KEY", nullable = false, length = 100)) })
	public MailboxAccountConfigId getId() {
		return this.id;
	}

	public void setId(MailboxAccountConfigId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ACCOUNT", nullable = false, insertable = false, updatable = false)
	public MailboxAccount getMailboxAccount() {
		return this.mailboxAccount;
	}

	public void setMailboxAccount(MailboxAccount mailboxAccount) {
		this.mailboxAccount = mailboxAccount;
	}

	@Column(name = "CONFIG_VALUE", nullable = false, length = 400)
	public String getConfigValue() {
		return this.configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

}
