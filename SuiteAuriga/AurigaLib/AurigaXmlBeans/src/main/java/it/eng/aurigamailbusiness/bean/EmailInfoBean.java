/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * informazioni salienti della email
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class EmailInfoBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -6938539831907003794L;
	
	private ModalitaRicevuta modalitaRicevuta;

	private ClassificazioneRicevuta ricevuta;

	private Boolean isPec;

	private String messageid;

	private String riferimentomessageid;
	
	private String destinatarioRicevuta;

	private Date date;
	private List<String> destinatario;
	private List<String> destinataricc;
	private String oggetto;
	private List<MailAttachmentsBean> allegati;
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

	public List<MailAttachmentsBean> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<MailAttachmentsBean> allegati) {
		this.allegati = allegati;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	
	public ModalitaRicevuta getModalitaRicevuta() {
		return modalitaRicevuta;
	}

	public void setModalitaRicevuta(ModalitaRicevuta modalitaRicevuta) {
		this.modalitaRicevuta = modalitaRicevuta;
	}

	public ClassificazioneRicevuta getRicevuta() {
		return ricevuta;
	}

	public void setRicevuta(ClassificazioneRicevuta ricevuta) {
		this.ricevuta = ricevuta;
	}

	public Boolean getIsPec() {
		return isPec;
	}

	public void setIsPec(Boolean isPec) {
		this.isPec = isPec;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getRiferimentomessageid() {
		return riferimentomessageid;
	}

	public void setRiferimentomessageid(String riferimentomessageid) {
		this.riferimentomessageid = riferimentomessageid;
	}

	public String getDestinatarioRicevuta() {
		return destinatarioRicevuta;
	}

	public void setDestinatarioRicevuta(String destinatarioRicevuta) {
		this.destinatarioRicevuta = destinatarioRicevuta;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	
}
