/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;


public class InvioMailBean implements Serializable {

	private String mittente;
	private String destinatari;
	private String bodyHtml;
	private String oggetto;
	private String textHtml;	
	private String bodyText;
	private String destinatariCC;
	private Boolean salvaInviati;
	private Boolean confermaLettura;	
	private String idEmail;	
	private Boolean interoperabile;
	private String aliasAccountMittente;
	public String getMittente() {
		return mittente;
	}
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	public String getDestinatari() {
		return destinatari;
	}
	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}
	public String getBodyHtml() {
		return bodyHtml;
	}
	public void setBodyHtml(String bodyHtml) {
		this.bodyHtml = bodyHtml;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getTextHtml() {
		return textHtml;
	}
	public void setTextHtml(String textHtml) {
		this.textHtml = textHtml;
	}
	public String getBodyText() {
		return bodyText;
	}
	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}
	public String getDestinatariCC() {
		return destinatariCC;
	}
	public void setDestinatariCC(String destinatariCC) {
		this.destinatariCC = destinatariCC;
	}
	public Boolean getSalvaInviati() {
		return salvaInviati;
	}
	public void setSalvaInviati(Boolean salvaInviati) {
		this.salvaInviati = salvaInviati;
	}
	public Boolean getConfermaLettura() {
		return confermaLettura;
	}
	public void setConfermaLettura(Boolean confermaLettura) {
		this.confermaLettura = confermaLettura;
	}
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	public Boolean getInteroperabile() {
		return interoperabile;
	}
	public void setInteroperabile(Boolean interoperabile) {
		this.interoperabile = interoperabile;
	}
	public String getAliasAccountMittente() {
		return aliasAccountMittente;
	}
	public void setAliasAccountMittente(String aliasAccountMittente) {
		this.aliasAccountMittente = aliasAccountMittente;
	}
	
		
}
