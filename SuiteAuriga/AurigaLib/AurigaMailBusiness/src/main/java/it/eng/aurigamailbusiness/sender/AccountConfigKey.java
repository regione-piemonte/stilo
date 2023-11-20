/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum AccountConfigKey {
	
	
	// JavaMail
	MAIL_DEBUG("mail.debug"), // abilitazione del debug JavaMail per la mailbox, i log sono visibili in console e quindi in catalina.out

	// Account
	ACCOUNT_USERNAME("mail.username"), ACCOUNT_PASSWORD("mail.password"), ACCOUNT_IS_PEC("mail.account.ispec"), ACCOUNT_PASSWORD_ENCRYPTION(
			"mail.password.encryption"),

	// SMTP
	SMTP_HOST("mail.smtp.host"), SMTP_PORT("mail.smtp.port"), SMTP_AUTH("mail.smtp.auth"), SMTP_MAX_ADDRESS(
			"mail.mailbox.maxaddresstosend"), SMTP_AUTH_LOGIN_DISABLE("mail.smtp.auth.login.disable"), SMTP_SOCKETFACTORY_CLASS(
					"mail.smtp.socketFactory.class"), SMTP_SOCKETFACTORY_FALLBACK("mail.smtp.socketFactory.fallback"), SMTP_SSL("mail.smtp.ssl.enable"),

	// IMAP
	IMAP_HOST("mail.imap.host"), IMAP_PORT("mail.imap.port"), IMAP_AUTH("mail.imap.auth"), IMAP_USERNAME("mail.imap.user"), // in alcuni casi occorre utilizzare
																															// username specifica per imap, in
																															// alcuni casi contiene anche
																															// l'alias per utilizzare shared
																															// account in exchange
	IMAP_AUTH_LOGIN_DISABLE("mail.imap.auth.login.disable"), IMAP_SOCKETFACTORY_CLASS("mail.imap.socketFactory.class"), IMAP_SOCKETFACTORY_FALLBACK(
			"mail.imap.socketFactory.fallback"), IMAP_SSL("mail.imap.ssl.enable"), IMAP_STORE_PROTOCOL("mail.imap.store.protocol"),

	IMAP_DISABLE_AUTH_PLAIN("mail.imap.auth.plain.disable"), // con server exchange a volte è necessario disabilitare l'autenticazione PLAIN
	IMAP_DISABLE_AUTH_NTLM("mail.imap.auth.ntlm.disable"), // con server exchange a volte è necessario disabilitare l'autenticazione NTLM
	IMAP_DISABLE_AUTH_GSSAPI("mail.imap.auth.gssapi.disable"), // con server exchange a volte è necessario disabilitare l'autenticazione GSSAPI

	// se si utilizza il protocollo IMAPS o SMTPS le proprietà da utilizzare sono imaps o smtps

	IMAPS_DISABLE_AUTH_PLAIN("mail.imaps.auth.plain.disable"), IMAPS_DISABLE_AUTH_NTLM("mail.imaps.auth.ntlm.disable"), IMAPS_DISABLE_AUTH_GSSAPI(
			"mail.imaps.auth.gssapi.disable"),

	// fetch parziale del messaggio disabilitato

	IMAP_PARTIAL_FETCH("mail.imap.partialfetch"), IMAPS_PARTIAL_FETCH("mail.imaps.partialfetch"),

	// fetchsize della mail, crea dei chunck della dimensione impostata

	IMAP_CONNECTIONPOOL_SIZE("mail.imap.connectionpoolsize"), IMAPS_CONNECTIONPOOL_SIZE("mail.imaps.connectionpoolsize"),

	IMAP_FETCH_SIZE("mail.imap.fetchsize"), IMAPS_FETCH_SIZE("mail.imaps.fetchsize"),

	// modalità peek per impedire che i messaggi siano segnati come letti sul server

	IMAP_PEEK("mail.imap.peek"), IMAPS_PEEK("mail.imaps.peek"),

	// timeout

	IMAP_CONNECTION_TIMEOUT("mail.imap.connectiontimeout"), IMAP_TIMEOUT("mail.imap.timeout"), IMAP_WRITE_TIMEOUT("mail.imap.writetimeout"),

	IMAPS_CONNECTION_TIMEOUT("mail.imaps.connectiontimeout"), IMAPS_TIMEOUT("mail.imaps.timeout"), IMAPS_WRITE_TIMEOUT("mail.imaps.writetimeout"),
	//Aggiunta gestione con autenticazione XOAUTH2 Office 365 / GMAIL
    IMAPS_SASL_ENABLE("mail.imaps.sasl.enable"),
    IMAPS_SASL_MECHANISM("mail.imaps.sasl.mechanisms"),
    IMAPS_SASL_MECHANISM_OAUTH2_OAUTHTOKEN("mail.imaps.sasl.mechanisms.oauth2.oauthToken"),
    OAUTH2_AUTHORITY("oauth2.authority"),
    OAUTH2_CLIENT_ID("oauth2.clientId"),
    OAUTH2_CLIENT_SECRET("oauth2.clientSecret"),
    OAUTH2_SCOPE("oauth2.scope"),
    OAUTH2_MAIL("oauth2.mail"),
	//Fine gestione con autenticazione XOAUTH2 Office 365 / GMAIL
    POP3S_CONNECTION_TIMEOUT("mail.pop3s.connectiontimeout"), POP3S_TIMEOUT("mail.pop3s.timeout"), POP3S_WRITE_TIMEOUT("mail.pop3s.writetimeout"),

	// POP3
		POP3_HOST("mail.pop3.host"), POP3_PORT("mail.pop3.port"), POP3_AUTH("mail.pop3.auth"), // in alcuni casi occorre utilizzare
																																// username specifica per pop3, in
																																// alcuni casi contiene anche
																																// l'alias per utilizzare shared
																																// account in exchange
		POP3_AUTH_LOGIN_DISABLE("mail.pop3.auth.login.disable"), POP3_SOCKETFACTORY_CLASS("mail.pop3.socketFactory.class"), POP3_SOCKETFACTORY_FALLBACK(
				"mail.pop3.socketFactory.fallback"), POP3_SSL("mail.pop3.ssl.enable"), POP3_STORE_PROTOCOL("mail.pop3.store.protocol"),

		POP3_DISABLE_AUTH_PLAIN("mail.pop3.auth.plain.disable"), // con server exchange a volte è necessario disabilitare l'autenticazione PLAIN
		POP3_DISABLE_AUTH_NTLM("mail.pop3.auth.ntlm.disable"), // con server exchange a volte è necessario disabilitare l'autenticazione NTLM
		POP3_DISABLE_AUTH_GSSAPI("mail.pop3.auth.gssapi.disable"), // con server exchange a volte è necessario disabilitare l'autenticazione GSSAPI

		// se si utilizza il protocollo POP3S o SMTPS le proprietà da utilizzare sono imaps o smtps

		// fetch parziale del messaggio disabilitato

		POP3_PARTIAL_FETCH("mail.pop3.partialfetch"),

		// fetchsize della mail, crea dei chunck della dimensione impostata

		POP3_CONNECTIONPOOL_SIZE("mail.pop3.connectionpoolsize"),

		POP3_FETCH_SIZE("mail.pop3.fetchsize"),

		// modalità peek per impedire che i messaggi siano segnati come letti sul server

		POP3_PEEK("mail.pop3.peek"), POP3S_PEEK("mail.pop3s.peek"),
		POP3_CONNECTION_TIMEOUT("mail.pop3.connectiontimeout"), POP3_TIMEOUT("mail.pop3.timeout"), POP3_WRITE_TIMEOUT("mail.pop3.writetimeout"),
	
	SMTP_CONNECTION_TIMEOUT("mail.smtp.connectiontimeout"), SMTP_TIMEOUT("mail.smtp.timeout"), SMTP_WRITE_TIMEOUT("mail.smtp.writetimeout"),

	SMTPS_CONNECTION_TIMEOUT("mail.smtps.connectiontimeout"), SMTPS_TIMEOUT("mail.smtps.timeout"), SMTPS_WRITE_TIMEOUT("mail.smtps.writetimeout"),

	// mime encoding

	MAIL_MIME_IGNORE_UNKNOWN_ENCODING("mail.mime.ignoreunknownencoding"),

	MAIL_MIME_BASE64_IGNORE_ERRORS("mail.mime.base64.ignoreerrors"),

	MAIL_MIME_UUDECODE_IGNORE_MISSING_BEGIN_END("mail.mime.uudecode.ignoremissingbeginend"),

	MAIL_MIME_UUDECODE_IGNORE_ERRORS("mail.mime.uudecode.ignoreerrors"),

	MAIL_MIME_MULTIPART_IGNORE_MISSING_BOUNDARY_PARAMETER("mail.mime.multipart.ignoremissingboundaryparameter"),

	MAIL_MIME_MULTIPART_IGNORE_EXISTING_BOUNDARY_PARAMETER("mail.mime.multipart.ignoreexistingboundaryparameter"),
	
	MAIL_MIME_ENCODEPARAMETERS("mail.mime.encodeparameters")

	;

	private String name;

	private AccountConfigKey(String name) {
		this.name = name;
	}

	public String keyname() {
		return this.name;
	}

	public static AccountConfigKey getForValue(String name) {
		for (AccountConfigKey mess : AccountConfigKey.values()) {
			if (mess.name.equals(name)) {
				return mess;
			}
		}
		return null;
	}
}