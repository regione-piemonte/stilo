package it.eng.simplesendermail.service.bean;

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
