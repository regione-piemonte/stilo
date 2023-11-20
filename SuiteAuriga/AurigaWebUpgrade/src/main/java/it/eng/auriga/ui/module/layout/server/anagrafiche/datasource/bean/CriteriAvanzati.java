/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CriteriAvanzati implements Serializable {

	@XmlVariabile(nome = "TipoVoce", tipo = TipoVariabile.SEMPLICE)
	private String tipoVoce;

	@XmlVariabile(nome = "Account", tipo = TipoVariabile.SEMPLICE)
	private String account;

	@XmlVariabile(nome = "OperAccount", tipo = TipoVariabile.SEMPLICE)
	private String operAccount;

	@XmlVariabile(nome = "DesVoce", tipo = TipoVariabile.SEMPLICE)
	private String desVoce;

	@XmlVariabile(nome = "OperDesVoce", tipo = TipoVariabile.SEMPLICE)
	private String operDesVoce;

	@XmlVariabile(nome = "DesToponimo", tipo = TipoVariabile.SEMPLICE)
	private String desToponimo;

	@XmlVariabile(nome = "OperDesToponimo", tipo = TipoVariabile.SEMPLICE)
	private String operDesToponimo;

	@XmlVariabile(nome = "CITiponimo", tipo = TipoVariabile.SEMPLICE)
	private String cITiponimo;

	@XmlVariabile(nome = "TipoAccount", tipo = TipoVariabile.SEMPLICE)
	private String tipoAccount;

	@XmlVariabile(nome = "InseritaDal", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date inseritaDal;

	@XmlVariabile(nome = "InseritaAl", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date inseritaAl;

	@XmlVariabile(nome = "InseritaDaMe", tipo = TipoVariabile.SEMPLICE)
	private String inseritaDaMe;

	@XmlVariabile(nome = "IncludiAnnullati", tipo = TipoVariabile.SEMPLICE)
	private String includiAnnullati;

	@XmlVariabile(nome = "@CodTipiIndirizzi", tipo = TipoVariabile.LISTA)
	private List<CodTipiIndirizziXmlBean> lCodTipiIndirizzi;

	public String getTipoVoce() {
		return tipoVoce;
	}

	public void setTipoVoce(String tipoVoce) {
		this.tipoVoce = tipoVoce;
	}

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

	public String getDesVoce() {
		return desVoce;
	}

	public void setDesVoce(String desVoce) {
		this.desVoce = desVoce;
	}

	public String getOperDesVoce() {
		return operDesVoce;
	}

	public void setOperDesVoce(String operDesVoce) {
		this.operDesVoce = operDesVoce;
	}

	public String getTipoAccount() {
		return tipoAccount;
	}

	public void setTipoAccount(String tipoAccount) {
		this.tipoAccount = tipoAccount;
	}

	public Date getInseritaDal() {
		return inseritaDal;
	}

	public void setInseritaDal(Date inseritaDal) {
		this.inseritaDal = inseritaDal;
	}

	public Date getInseritaAl() {
		return inseritaAl;
	}

	public void setInseritaAl(Date inseritaAl) {
		this.inseritaAl = inseritaAl;
	}

	public String getInseritaDaMe() {
		return inseritaDaMe;
	}

	public void setInseritaDaMe(String inseritaDaMe) {
		this.inseritaDaMe = inseritaDaMe;
	}

	public String getIncludiAnnullati() {
		return includiAnnullati;
	}

	public void setIncludiAnnullati(String includiAnnullati) {
		this.includiAnnullati = includiAnnullati;
	}

	/**
	 * @return the desToponimo
	 */
	public String getDesToponimo() {
		return desToponimo;
	}

	/**
	 * @param desToponimo
	 *            the desToponimo to set
	 */
	public void setDesToponimo(String desToponimo) {
		this.desToponimo = desToponimo;
	}

	/**
	 * @return the operDesToponimo
	 */
	public String getOperDesToponimo() {
		return operDesToponimo;
	}

	/**
	 * @param operDesToponimo
	 *            the operDesToponimo to set
	 */
	public void setOperDesToponimo(String operDesToponimo) {
		this.operDesToponimo = operDesToponimo;
	}

	/**
	 * @return the cITiponimo
	 */
	public String getcITiponimo() {
		return cITiponimo;
	}

	/**
	 * @param cITiponimo
	 *            the cITiponimo to set
	 */
	public void setcITiponimo(String cITiponimo) {
		this.cITiponimo = cITiponimo;
	}

	/**
	 * @return the lCodTipiIndirizzi
	 */
	public List<CodTipiIndirizziXmlBean> getlCodTipiIndirizzi() {
		return lCodTipiIndirizzi;
	}

	/**
	 * @param lCodTipiIndirizzi
	 *            the lCodTipiIndirizzi to set
	 */
	public void setlCodTipiIndirizzi(List<CodTipiIndirizziXmlBean> lCodTipiIndirizzi) {
		this.lCodTipiIndirizzi = lCodTipiIndirizzi;
	}

}