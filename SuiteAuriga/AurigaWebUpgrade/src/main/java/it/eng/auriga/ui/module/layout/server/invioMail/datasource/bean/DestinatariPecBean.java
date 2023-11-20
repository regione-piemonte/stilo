/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class DestinatariPecBean {

	private String idRubricaAuriga;	
	private Boolean destPrimario;
	private Boolean destCC;
	private String intestazione;
	private String tipoDestinatario;
	private String cipaPec;
	private String codiciIpa;
	private String indirizzoMail;
	private String idMailInviata;
	private String statoMail;
	private String dataOraMail;
	private String uriMail;
	private String idInRubricaEmail;	
	
	public Boolean getDestPrimario() {
		return destPrimario;
	}
	public void setDestPrimario(Boolean destPrimario) {
		this.destPrimario = destPrimario;
	}
	public Boolean getDestCC() {
		return destCC;
	}
	public void setDestCC(Boolean destCC) {
		this.destCC = destCC;
	}
	public String getIntestazione() {
		return intestazione;
	}
	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}
	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
	public String getTipoDestinatario() {
		return tipoDestinatario;
	}
	public String getCipaPec() {
		return cipaPec;
	}
	public void setCipaPec(String cipaPec) {
		this.cipaPec = cipaPec;
	}
	public String getCodiciIpa() {
		return codiciIpa;
	}
	public void setCodiciIpa(String codiciIpa) {
		this.codiciIpa = codiciIpa;
	}
	public String getIndirizzoMail() {
		return indirizzoMail;
	}
	public void setIndirizzoMail(String indirizzoMail) {
		this.indirizzoMail = indirizzoMail;
	}
	public String getIdMailInviata() {
		return idMailInviata;
	}
	public void setIdMailInviata(String idMailInviata) {
		this.idMailInviata = idMailInviata;
	}
	public String getStatoMail() {
		return statoMail;
	}
	public void setStatoMail(String statoMail) {
		this.statoMail = statoMail;
	}
	public String getDataOraMail() {
		return dataOraMail;
	}
	public void setDataOraMail(String dataOraMail) {
		this.dataOraMail = dataOraMail;
	}
	public String getUriMail() {
		return uriMail;
	}
	public void setUriMail(String uriMail) {
		this.uriMail = uriMail;
	}
	public String getIdRubricaAuriga() {
		return idRubricaAuriga;
	}
	public void setIdRubricaAuriga(String idRubricaAuriga) {
		this.idRubricaAuriga = idRubricaAuriga;
	}
	public String getIdInRubricaEmail() {
		return idInRubricaEmail;
	}
	public void setIdInRubricaEmail(String idInRubricaEmail) {
		this.idInRubricaEmail = idInRubricaEmail;
	}
}
