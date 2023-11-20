/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author Cristiano Daniele
 *
 */

@XmlRootElement
public class InvioMailXmlBean extends AbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3633366930042455183L;

	/**
	 * Dimensione in bytes della mail
	 */
	@XmlVariabile(nome = "Dimensione", tipo = TipoVariabile.SEMPLICE)
	private String dimensione;

	/**
	 * URI (con notazione di StorageUtil) a cui reperire l'eml (busta di trasporto intera in caso di e-mail ricevuta)
	 */
	@XmlVariabile(nome = "URI", tipo = TipoVariabile.SEMPLICE)
	private String uri;

	/**
	 * valori 1/0 Se 1 indica che la mail è firmata (l'intera busta SMIME)
	 */
	@XmlVariabile(nome = "FlgEmailFirmata", tipo = TipoVariabile.SEMPLICE)
	private Integer flgEmailFirmata;

	/**
	 * Indirizzo mittente
	 */
	@XmlVariabile(nome = "AccountMittente", tipo = TipoVariabile.SEMPLICE)
	private String accountMittente;

	/**
	 * Subject/oggetto della mail
	 */
	@XmlVariabile(nome = "Subject", tipo = TipoVariabile.SEMPLICE)
	private String subject;

	/**
	 * Corpo della mail (quello in formato text/plain)
	 */
	@XmlVariabile(nome = "Body", tipo = TipoVariabile.SEMPLICE)
	private String body;

	/**
	 * Lista dei destinatari principali della mail.
	 */
	@XmlVariabile(nome = "@Destinatari", tipo = TipoVariabile.LISTA)
	private List<DestinatariInvioMailXmlBean> listaDestinatari;

	/**
	 * Indica il tipo di relazione con la mail predecessore: {@link RispostaInoltro#INOLTRO} = Inoltro, {@link RispostaInoltro#RISPOSTA} = Risposta
	 */
	@XmlVariabile(nome = "EmailPredecessore.TipoRelazione", tipo = TipoVariabile.SEMPLICE)
	private String emailPredTipoRel;

	/**
	 * ID_EMAIL della mail predecessore da cui quella corrente ha avuto luogo
	 */
	@XmlVariabile(nome = "EmailPredecessore.IdEmail", tipo = TipoVariabile.SEMPLICE)
	private String emailPredIdEmail;

	/**
	 * Lista di ID_EMAIL delle mail di cui la bozza costituisce un inoltro massivo
	 */
	@XmlVariabile(nome = "@IdEmailInoltrate", tipo = TipoVariabile.LISTA)
	private List<IdMailInoltrataMailXmlBean> listaIdEmailInoltrate;

	/**
	 * Allegati(file) della mail. Ogni allegato è una riga e contiene le seguenti colonne:
	 */
	@XmlVariabile(nome = "@Allegati", tipo = TipoVariabile.LISTA)
	private List<AllegatiInvioMailXmlBean> listaAllegati;

	/**
	 * Lista degli item di lavorazione - commenti, tag, file - associati alla mail
	 */
	@XmlVariabile(nome = "@ItemLavorazione", tipo = TipoVariabile.LISTA)
	private List<ItemLavorazioneMailXmlBean> listaItemLavorazione;

	/**
	 * Indica se è presente una richiesta di conferma di lettura
	 */

	@XmlVariabile(nome = "FlgRichConfermaLettura", tipo = TipoVariabile.SEMPLICE)
	private Integer flgRichConfermaLettura;

	/**
	 * Indica se siano da aggiornare solo i destinatari della bozza o l'intera bozza
	 */

	@XmlVariabile(nome = "FlgAggSoloDestinatari", tipo = TipoVariabile.SEMPLICE)
	private Integer flgAggSoloDestinatari;

	private List<SenderAttachmentsBean> lSenderAttachmentsBean;
	
	/**
	 * 
	 * Invia mail separate a ciascun destinatario.
	 * Il fatto di fare invii separati significa che creiamo tante mail quanti i destinatari, 
	 * mettendo in ciascuna un solo destinatario (come se il limite max di destinatari fosse 1). 
	 * Di default FALSE.
	 */
	private Boolean flgInvioSeparato = false;

	/**
	 * @return the dimensione
	 */
	public String getDimensione() {
		return dimensione;
	}

	/**
	 * @param dimensione
	 *            the dimensione to set
	 */
	public void setDimensione(String dimensione) {
		this.dimensione = dimensione;
	}

	/**
	 * @return the uRI
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uRI
	 *            the uRI to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the flgEmailFirmata
	 */
	public Integer getFlgEmailFirmata() {
		return flgEmailFirmata;
	}

	/**
	 * @param flgEmailFirmata
	 *            the flgEmailFirmata to set
	 */
	public void setFlgEmailFirmata(Integer flgEmailFirmata) {
		this.flgEmailFirmata = flgEmailFirmata;
	}

	/**
	 * @return the accountMittente
	 */
	public String getAccountMittente() {
		return accountMittente;
	}

	/**
	 * @param accountMittente
	 *            the accountMittente to set
	 */
	public void setAccountMittente(String accountMittente) {
		this.accountMittente = accountMittente;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the listaDestinatari
	 */
	public List<DestinatariInvioMailXmlBean> getListaDestinatari() {
		return listaDestinatari;
	}

	/**
	 * @param listaDestinatari
	 *            the listaDestinatari to set
	 */
	public void setListaDestinatari(List<DestinatariInvioMailXmlBean> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}

	/**
	 * @return the emailPredTipoRel
	 */
	public String getEmailPredTipoRel() {
		return emailPredTipoRel;
	}

	/**
	 * @param emailPredTipoRel
	 *            the emailPredTipoRel to set
	 */
	public void setEmailPredTipoRel(String emailPredTipoRel) {
		this.emailPredTipoRel = emailPredTipoRel;
	}

	/**
	 * @return the emailPredIdEmail
	 */
	public String getEmailPredIdEmail() {
		return emailPredIdEmail;
	}

	/**
	 * @param emailPredIdEmail
	 *            the emailPredIdEmail to set
	 */
	public void setEmailPredIdEmail(String emailPredIdEmail) {
		this.emailPredIdEmail = emailPredIdEmail;
	}

	/**
	 * @return the listaAllegati
	 */
	public List<AllegatiInvioMailXmlBean> getListaAllegati() {
		return listaAllegati;
	}

	/**
	 * @param listaAllegati
	 *            the listaAllegati to set
	 */
	public void setListaAllegati(List<AllegatiInvioMailXmlBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}

	/**
	 * @return the listaItemLavorazione
	 */
	public List<ItemLavorazioneMailXmlBean> getListaItemLavorazione() {
		return listaItemLavorazione;
	}

	/**
	 * @param listaItemLavorazione
	 *            the listaItemLavorazione to set
	 */
	public void setListaItemLavorazione(List<ItemLavorazioneMailXmlBean> listaItemLavorazione) {
		this.listaItemLavorazione = listaItemLavorazione;
	}

	/**
	 * @return the lSenderAttachmentsBean
	 */
	public List<SenderAttachmentsBean> getlSenderAttachmentsBean() {
		return lSenderAttachmentsBean;
	}

	/**
	 * @param lSenderAttachmentsBean
	 *            the lSenderAttachmentsBean to set
	 */
	public void setlSenderAttachmentsBean(List<SenderAttachmentsBean> lSenderAttachmentsBean) {
		this.lSenderAttachmentsBean = lSenderAttachmentsBean;
	}

	public List<IdMailInoltrataMailXmlBean> getListaIdEmailInoltrate() {
		return listaIdEmailInoltrate;
	}

	public void setListaIdEmailInoltrate(List<IdMailInoltrataMailXmlBean> listaIdEmailInoltrate) {
		this.listaIdEmailInoltrate = listaIdEmailInoltrate;
	}

	public Integer getFlgRichConfermaLettura() {
		return flgRichConfermaLettura;
	}

	public void setFlgRichConfermaLettura(Integer flgRichConfermaLettura) {
		this.flgRichConfermaLettura = flgRichConfermaLettura;
	}

	public Integer getFlgAggSoloDestinatari() {
		return flgAggSoloDestinatari;
	}

	public void setFlgAggSoloDestinatari(Integer flgAggSoloDestinatari) {
		this.flgAggSoloDestinatari = flgAggSoloDestinatari;
	}
	
	public Boolean getFlgInvioSeparato() {
		return flgInvioSeparato;
	}

	public void setFlgInvioSeparato(Boolean flgInvioSeparato) {
		this.flgInvioSeparato = flgInvioSeparato;
	}

}
