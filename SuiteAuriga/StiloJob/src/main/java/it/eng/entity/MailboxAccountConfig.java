/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MAILBOX_ACCOUNT_CONFIG")
public class MailboxAccountConfig implements Serializable {
	
	private static final long serialVersionUID = -1015814565785625198L;
	
	private String configKey;

	private String configValue;
	
	private String idAccount;
	
	public MailboxAccountConfig() {
		
	}
	
	public MailboxAccountConfig(String configKey, String configValue, String idAccount) {
		this.configKey = configKey;
		this.configValue = configValue;
		this.idAccount = idAccount;
	}
	
	@Id
	@Column(name = "CONFIG_KEY", unique = true, nullable = false, length = 64)
	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	
	@Id
	@Column(name = "ID_ACCOUNT", unique = true, nullable = false, length = 64)
	public String getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}
	
	@Column(name = "CONFIG_VALUE", nullable = false, length = 64)
	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	
}
