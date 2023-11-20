/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriAvanzati;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class CriteriAvanzatiRichAccessoAtti extends CriteriAvanzati implements Serializable {
	
	@XmlVariabile(nome="CodStatoRichAccessoAtti", tipo=TipoVariabile.SEMPLICE)
	private String codStatoRichAccessoAtti;
	
	@XmlVariabile(nome="UDC", tipo=TipoVariabile.SEMPLICE)
	private String UDC;
	
	@XmlVariabile(nome="VerificaArchivioCompletata", tipo=TipoVariabile.SEMPLICE)
	private String verificaArchivioCompletata;
	
	@XmlVariabile(nome="RichPrenotabiliDaAgendaDigitale", tipo=TipoVariabile.SEMPLICE)
	private String richPrenotabiliDaAgendaDigitale;
	

	public String getCodStatoRichAccessoAtti() {
		return codStatoRichAccessoAtti;
	}

	public void setCodStatoRichAccessoAtti(String codStatoRichAccessoAtti) {
		this.codStatoRichAccessoAtti = codStatoRichAccessoAtti;
	}

	public String getUDC() {
		return UDC;
	}

	public void setUDC(String uDC) {
		UDC = uDC;
	}

	public String getVerificaArchivioCompletata() {
		return verificaArchivioCompletata;
	}

	public void setVerificaArchivioCompletata(String verificaArchivioCompletata) {
		this.verificaArchivioCompletata = verificaArchivioCompletata;
	}
	
	public String getRichPrenotabiliDaAgendaDigitale() {
		return richPrenotabiliDaAgendaDigitale;
	}
	
	public void setRichPrenotabiliDaAgendaDigitale(String richPrenotabiliDaAgendaDigitale) {
		this.richPrenotabiliDaAgendaDigitale = richPrenotabiliDaAgendaDigitale;
	}

}