/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.Flag;

public class InvioUDMailOutDestinatariBean {

	@NumeroColonna(numero = "1")
	private Flag destPrimario;
	@NumeroColonna(numero = "2")
	private Flag destCC;
	@NumeroColonna(numero = "3")
	private String tipoDestinatario;
	@NumeroColonna(numero = "4")
	private String idRubricaAuriga;
	@NumeroColonna(numero = "5")
	private String intestazione;
	@NumeroColonna(numero = "6")
	private String codiciIpa;
	@NumeroColonna(numero = "7")
	private String cipaPec;
	@NumeroColonna(numero = "8")
	private String email;
	@NumeroColonna(numero = "9")
	private String idMailInviata;
	@NumeroColonna(numero = "10")
	private String uriMail;
	@NumeroColonna(numero = "11")
	private String dataOraMail;
	@NumeroColonna(numero = "12")
	private String statoMail;
	@NumeroColonna(numero = "13")
	private String idInRubricaEmail;
	public Flag getDestPrimario() {
		return destPrimario;
	}
	public void setDestPrimario(Flag destPrimario) {
		this.destPrimario = destPrimario;
	}
	public Flag getDestCC() {
		return destCC;
	}
	public void setDestCC(Flag destCC) {
		this.destCC = destCC;
	}
	public String getTipoDestinatario() {
		return tipoDestinatario;
	}
	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
	public String getIdRubricaAuriga() {
		return idRubricaAuriga;
	}
	public void setIdRubricaAuriga(String idRubricaAuriga) {
		this.idRubricaAuriga = idRubricaAuriga;
	}
	public String getIntestazione() {
		return intestazione;
	}
	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}
	public String getCodiciIpa() {
		return codiciIpa;
	}
	public void setCodiciIpa(String codiciIpa) {
		this.codiciIpa = codiciIpa;
	}
	public String getCipaPec() {
		return cipaPec;
	}
	public void setCipaPec(String cipaPec) {
		this.cipaPec = cipaPec;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdMailInviata() {
		return idMailInviata;
	}
	public void setIdMailInviata(String idMailInviata) {
		this.idMailInviata = idMailInviata;
	}
	public String getUriMail() {
		return uriMail;
	}
	public void setUriMail(String uriMail) {
		this.uriMail = uriMail;
	}
	public String getDataOraMail() {
		return dataOraMail;
	}
	public void setDataOraMail(String dataOraMail) {
		this.dataOraMail = dataOraMail;
	}
	public String getStatoMail() {
		return statoMail;
	}
	public void setStatoMail(String statoMail) {
		this.statoMail = statoMail;
	}
	public String getIdInRubricaEmail() {
		return idInRubricaEmail;
	}
	public void setIdInRubricaEmail(String idInRubricaEmail) {
		this.idInRubricaEmail = idInRubricaEmail;
	}
	
}
