/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;
import it.eng.core.annotation.Attachment;
import it.eng.core.business.beans.AbstractBean;

/**
 * Bean per l'invio della mail
 * 
 * @author jravagnan
 */
@XmlRootElement
public class SenderBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7025594314059680937L;

	private String idUtenteModPec;

	/**
	 * Oggetto della mail
	 */
	private String subject;

	/**
	 * id dell'email se già salvata su db e da reinviare
	 */
	private String idEmail;

	/**
	 * Body della mail
	 */
	private String body;

	/**
	 * Indica se il body è un html (default false)
	 */
	private Boolean isHtml;

	/**
	 * Lista degli attachments della mail
	 */
	private List<SenderAttachmentsBean> attachments;

	/**
	 * Lista delgi indirizzi a cui spedire
	 */
	private List<String> addressTo;

	/**
	 * Liusta degli indirizzi in copia conoscenza
	 */
	private List<String> addressCc;

	/**
	 * Lista degli inidirizzi in copia conoscenza nascosta
	 */
	private List<String> addressBcc;

	/**
	 * Indirizzo mittente
	 */
	private String addressFrom;

	/**
	 * Alias mittente
	 */
	private String aliasAddressFrom;

	/**
	 * Account da cui prendere le configurazioni
	 */
	private String account;

	/**
	 * mi dice se la email è stata spedita
	 */
	private Boolean isSent;

	/**
	 * fa sapere se la mail è PEC o meno
	 */
	private Boolean isPec;

	/**
	 * messageId relativo al messaggo spedito
	 */
	private String messageId;

	/**
	 * mi dice se è una risposta o un inoltro
	 * 
	 */
	private RispostaInoltroBean rispInol;

	/**
	 * richiedi un avviso di lettura
	 */
	private Boolean returnReceipt;

	/**
	 * richiesta conferma interoperabile
	 */
	private Boolean richiestaConfermaInteroperabile;

	/**
	 * motivoEccezione motivo di invio per una email di notifica interoperabile di eccezione
	 */
	private String motivoEccezione;

	private String uriSavedMimeMessage; // riferimento al file salvato nello Storage

	/**
	 * Lista degli item di lavorazione - commenti, tag, file - associati alla mail
	 */
	private List<ItemLavorazioneMailXmlBean> listaItemInLavorazioneInvioMail;
	
	/**
	 * Lista dei file associati agli item di lavorazione
	 * Attenzione. inserire l'annotazione attachment in modo da serializzare correttamente i file, altrimenti verrebbero mandati i path 
	 */

	@Attachment
	@ApiModelProperty(hidden=true)
	private List<File> listaFileItemLavorazione;
	
	/**
	 * 
	 * Invia mail separate a ciascun destinatario.
	 * Il fatto di fare invii separati significa che creiamo tante mail quanti i destinatari, 
	 * mettendo in ciascuna un solo destinatario (come se il limite max di destinatari fosse 1). 
	 * Di default FALSE.
	 */
	private Boolean flgInvioSeparato = false;

	
	/**
	 * Reply-To
	 */
	private String replyTo;
	
	public void addAddressTo(String address) {
		if (addressTo == null) {
			addressTo = new ArrayList<String>();
		}
		addressTo.add(address);
	}

	public void addAddressCc(String address) {
		if (addressCc == null) {
			addressCc = new ArrayList<String>();
		}
		addressCc.add(address);
	}

	public void addAddressBcc(String address) {
		if (addressBcc == null) {
			addressBcc = new ArrayList<String>();
		}
		addressBcc.add(address);
	}

	public List<String> getAddressTo() {
		return addressTo;
	}

	public String getAddressFrom() {
		return addressFrom;
	}

	public String getAliasAddressFrom() {
		return aliasAddressFrom;
	}

	public List<String> getAddressCc() {
		return addressCc;
	}

	public void setAddressCc(List<String> addressCc) {
		this.addressCc = addressCc;
	}

	public List<String> getAddressBcc() {
		return addressBcc;
	}

	public void setAddressBcc(List<String> addressBcc) {
		this.addressBcc = addressBcc;
	}

	public void setAddressTo(List<String> addressTo) {
		this.addressTo = addressTo;
	}

	public void setAddressFrom(String addressFrom) {
		this.addressFrom = addressFrom;
	}

	public void setAliasAddressFrom(String aliasAddressFrom) {
		this.aliasAddressFrom = aliasAddressFrom;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Boolean getIsHtml() {
		return isHtml;
	}

	public void setIsHtml(Boolean isHtml) {
		this.isHtml = isHtml;
	}

	public List<SenderAttachmentsBean> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<SenderAttachmentsBean> attachments) {
		this.attachments = attachments;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public Boolean getIsSent() {
		return isSent;
	}

	public void setIsSent(Boolean isSent) {
		this.isSent = isSent;
	}

	public Boolean getIsPec() {
		return isPec;
	}

	public void setIsPec(Boolean isPec) {
		this.isPec = isPec;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public RispostaInoltroBean getRispInol() {
		return rispInol;
	}

	public void setRispInol(RispostaInoltroBean rispInol) {
		this.rispInol = rispInol;
	}

	public String getIdUtenteModPec() {
		return idUtenteModPec;
	}

	public void setIdUtenteModPec(String idUtenteModPec) {
		this.idUtenteModPec = idUtenteModPec;
	}

	public Boolean getReturnReceipt() {
		return returnReceipt;
	}

	public void setReturnReceipt(Boolean returnReceipt) {
		this.returnReceipt = returnReceipt;
	}

	public Boolean getRichiestaConfermaInteroperabile() {
		return richiestaConfermaInteroperabile;
	}

	public void setRichiestaConfermaInteroperabile(Boolean richiestaConfermaInteroperabile) {
		this.richiestaConfermaInteroperabile = richiestaConfermaInteroperabile;
	}

	public String getMotivoEccezione() {
		return motivoEccezione;
	}

	public void setMotivoEccezione(String motivoEccezione) {
		this.motivoEccezione = motivoEccezione;
	}

	public String getUriSavedMimeMessage() {
		return uriSavedMimeMessage;
	}

	public void setUriSavedMimeMessage(String uriSavedMimeMessage) {
		this.uriSavedMimeMessage = uriSavedMimeMessage;
	}

	public List<ItemLavorazioneMailXmlBean> getListaItemInLavorazioneInvioMail() {
		return listaItemInLavorazioneInvioMail;
	}

	public void setListaItemInLavorazioneInvioMail(List<ItemLavorazioneMailXmlBean> listaItemInLavorazioneInvioMail) {
		this.listaItemInLavorazioneInvioMail = listaItemInLavorazioneInvioMail;
	}

	public List<File> getListaFileItemLavorazione() {
		return listaFileItemLavorazione;
	}

	public void setListaFileItemLavorazione(List<File> listaFileItemLavorazione) {
		this.listaFileItemLavorazione = listaFileItemLavorazione;
	}

	public Boolean getFlgInvioSeparato() {
		return flgInvioSeparato;
	}

	public void setFlgInvioSeparato(Boolean flgInvioSeparato) {
		this.flgInvioSeparato = flgInvioSeparato;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

}