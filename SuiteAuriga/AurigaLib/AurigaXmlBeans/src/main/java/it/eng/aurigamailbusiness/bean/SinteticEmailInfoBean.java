/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SinteticEmailInfoBean implements Serializable {

	private static final long serialVersionUID = 4200845329398287675L;
	private Date date;
	private List<String> destinatario;
	private List<String> destinataricc;
	private String oggetto;
	private List<SinteticMailAttachmentsBean> allegati;
	private String messaggio;
	private String mittente;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<String> getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(List<String> destinatario) {
		this.destinatario = destinatario;
	}

	public List<String> getDestinataricc() {
		return destinataricc;
	}

	public void setDestinataricc(List<String> destinataricc) {
		this.destinataricc = destinataricc;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public List<SinteticMailAttachmentsBean> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<SinteticMailAttachmentsBean> allegati) {
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

	@Override
	public String toString() {
		return "SinteticEmailInfoBean [date=" + date + ", destinatario=" + destinatario + ", destinataricc=" + destinataricc + ", oggetto=" + oggetto
				+ ", allegati=" + allegati + ", messaggio=" + messaggio + ", mittente=" + mittente + "]";
	}
}
