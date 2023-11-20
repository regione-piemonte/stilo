/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;

public class FiltriTrovaCaselle implements Serializable {

	@XmlVariabile(nome = "Account", tipo = TipoVariabile.SEMPLICE)
	private String account;
	
	@XmlVariabile(nome = "OperAccount", tipo = TipoVariabile.SEMPLICE)
	private String operAccount;
	
	@XmlVariabile(nome = "DesOwner", tipo = TipoVariabile.SEMPLICE)
	private String desOwner;
	
	@XmlVariabile(nome = "OperDesOwner", tipo = TipoVariabile.SEMPLICE)
	private String operDesOwner;
	
	@XmlVariabile(nome = "TipoAccount", tipo = TipoVariabile.SEMPLICE)
	private String tipoAccount;
	
	@XmlVariabile(nome = "GiorniUltimoCambioPwdDa", tipo = TipoVariabile.SEMPLICE)
	private String giorniUltimoCambioPwdDa;
	
	@XmlVariabile(nome = "GiorniUltimoCambioPwdA", tipo = TipoVariabile.SEMPLICE)
	private String giorniUltimoCambioPwdA;
	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOperAccount() {
		return operAccount;
	}

	public void setOperAccount(String operAccount) {
		this.operAccount = operAccount;
	}

	public String getDesOwner() {
		return desOwner;
	}

	public void setDesOwner(String desOwner) {
		this.desOwner = desOwner;
	}

	public String getOperDesOwner() {
		return operDesOwner;
	}

	public void setOperDesOwner(String operDesOwner) {
		this.operDesOwner = operDesOwner;
	}

	public String getTipoAccount() {
		return tipoAccount;
	}

	public void setTipoAccount(String tipoAccount) {
		this.tipoAccount = tipoAccount;
	}

	public String getGiorniUltimoCambioPwdDa() {
		return giorniUltimoCambioPwdDa;
	}

	public void setGiorniUltimoCambioPwdDa(String giorniUltimoCambioPwdDa) {
		this.giorniUltimoCambioPwdDa = giorniUltimoCambioPwdDa;
	}

	public String getGiorniUltimoCambioPwdA() {
		return giorniUltimoCambioPwdA;
	}

	public void setGiorniUltimoCambioPwdA(String giorniUltimoCambioPwdA) {
		this.giorniUltimoCambioPwdA = giorniUltimoCambioPwdA;
	}

}