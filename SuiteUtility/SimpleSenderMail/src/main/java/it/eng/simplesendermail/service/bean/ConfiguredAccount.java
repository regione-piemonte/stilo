/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

public class ConfiguredAccount {

	private Map<String, SmtpSenderBean> accounts;

	public Map<String, SmtpSenderBean> getAccounts() {
		return accounts;
	}

	public void setAccounts(Map<String, SmtpSenderBean> accounts) {
		this.accounts = accounts;
	}
}
