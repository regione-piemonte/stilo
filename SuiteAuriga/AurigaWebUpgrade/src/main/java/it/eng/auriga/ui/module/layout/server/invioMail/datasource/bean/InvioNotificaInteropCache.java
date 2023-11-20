/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.Flag;

public class InvioNotificaInteropCache {

	@XmlVariabile(nome="Mittente", tipo=TipoVariabile.SEMPLICE)
	private String mittente;
	@XmlVariabile(nome="Destinatari", tipo=TipoVariabile.SEMPLICE)
	private String destinatari;
	@XmlVariabile(nome="Subject", tipo=TipoVariabile.SEMPLICE)
	private String oggetto;
	@XmlVariabile(nome="Body", tipo=TipoVariabile.SEMPLICE)
	private String body;
	@XmlVariabile(nome="DestinatariCC", tipo=TipoVariabile.SEMPLICE)
	private String destinatariCC;
	@XmlVariabile(nome="FlgSalvaInInviati", tipo=TipoVariabile.SEMPLICE)
	private Flag flagSalvaInviati;	
	@XmlVariabile(nome="BodyFormat", tipo=TipoVariabile.SEMPLICE)
	private String bodyFormat;
	
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
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDestinatariCC() {
		return destinatariCC;
	}
	public void setDestinatariCC(String destinatariCC) {
		this.destinatariCC = destinatariCC;
	}
	public void setFlagSalvaInviati(Flag flagSalvaInviati) {
		this.flagSalvaInviati = flagSalvaInviati;
	}
	public Flag getFlagSalvaInviati() {
		return flagSalvaInviati;
	}
	public String getBodyFormat() {
		return bodyFormat;
	}
	public void setBodyFormat(String bodyFormat) {
		this.bodyFormat = bodyFormat;
	}
}
