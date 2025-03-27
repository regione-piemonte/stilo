/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.firmaXades.bean;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author dbe4235
 *
 */

public class FirmaXadesBean {

	// Se manca vengono usati paramentri e codice del vecchio client infocert
	@XmlVariabile(nome = "provider", tipo = TipoVariabile.SEMPLICE)
	private String provider;

	// Mantenuto per retrocompatibilità con client infocert
	@XmlVariabile(nome = "endpoint", tipo = TipoVariabile.SEMPLICE)
	private String endpoint;

	@XmlVariabile(nome = "userid", tipo = TipoVariabile.SEMPLICE)
	private String userid;

	// Mantenuto per retrocompatibilità con client infocert
	@XmlVariabile(nome = "alias", tipo = TipoVariabile.SEMPLICE)
	private String alias;

	@XmlVariabile(nome = "delegatedUserid", tipo = TipoVariabile.SEMPLICE)
	private String delegatedUserid;

	@XmlVariabile(nome = "password", tipo = TipoVariabile.SEMPLICE)
	private String password;

	@XmlVariabile(nome = "pin", tipo = TipoVariabile.SEMPLICE)
	private String pin;

	// Mantenuto per retrocompatibilità con client infocert
	@XmlVariabile(nome = "otp", tipo = TipoVariabile.SEMPLICE)
	private String otp;
	
	//parametri limitati all'implementazione LambaService
	@XmlVariabile(nome = "secret", tipo = TipoVariabile.SEMPLICE)
	private String secret;

	//parametri limitati all'implementazione LambaService
	@XmlVariabile(nome = "key", tipo = TipoVariabile.SEMPLICE)
	private String key;

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDelegatedUserid() {
		return delegatedUserid;
	}

	public void setDelegatedUserid(String delegatedUserid) {
		this.delegatedUserid = delegatedUserid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	
}
