/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

public class PreviewEmailBean {

	private Date date;
	private String destinatari;
	private String destinataricc;
	private String oggetto;
	private List<PreviewAttachmentEmailBean> allegati;
	private String messaggio;
	private String body;
	private String mittente;
	
	private String nomeFileEml;
	private String uriFileEml;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public List<PreviewAttachmentEmailBean> getAllegati() {
		return allegati;
	}
	public void setAllegati(List<PreviewAttachmentEmailBean> allegati) {
		this.allegati = allegati;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
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
	public String getDestinataricc() {
		return destinataricc;
	}
	public void setDestinataricc(String destinataricc) {
		this.destinataricc = destinataricc;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getNomeFileEml() {
		return nomeFileEml;
	}
	public void setNomeFileEml(String nomeFileEml) {
		this.nomeFileEml = nomeFileEml;
	}
	public String getUriFileEml() {
		return uriFileEml;
	}
	public void setUriFileEml(String uriFileEml) {
		this.uriFileEml = uriFileEml;
	}	
	
}
