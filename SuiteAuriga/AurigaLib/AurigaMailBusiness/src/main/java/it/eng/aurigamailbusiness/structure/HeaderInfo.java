/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

/**
 * Info dell'headers della mail
 * 
 * @author michele
 * 
 */
public class HeaderInfo {

	private XTipoRicevuta tiporicevuta;

	private XRicevuta ricevuta;

	private XTrasporto trasporto;

	private XVerificaSicurezza verficasicurezza;

	private String messageid;

	private String riferimentomessageid;

	private Date sendDate;

	private Date retrieveDate;

	private String subject;

	private String priority;

	private Boolean richiestaRicevuta;

	private Boolean richiestaLettura;

	private String messageidInReplyTo;

	/**
	 * Mittente della mail: è già concatenato con il sender, se presente. Il mittente è l'indirizzo dell'autore della mail, mentre il sender è l'indirizzo usato
	 * per contattare il server SMTP
	 */
	private String mittente;

	/**
	 * Destinatari TO
	 */
	private List<String> destinatarito;

	/**
	 * Destinatari CC
	 */
	private List<String> destinataricc;

	/**
	 * Destinatari in BCC (Se ci sono indirizzi qui, la mail pec non è valida)
	 */
	private List<String> destinataribcc;
	
	private String replyTO;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<String> getDestinataribcc() {
		return destinataribcc;
	}

	public void setDestinataribcc(List<String> destinataribcc) {
		this.destinataribcc = destinataribcc;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public List<String> getDestinatarito() {
		return destinatarito;
	}

	public void setDestinatarito(List<String> destinatarito) {
		this.destinatarito = destinatarito;
	}

	public List<String> getDestinataricc() {
		return destinataricc;
	}

	public void setDestinataricc(List<String> destinataricc) {
		this.destinataricc = destinataricc;
	}

	public XTipoRicevuta getTiporicevuta() {
		return tiporicevuta;
	}

	public void setTiporicevuta(XTipoRicevuta tiporicevuta) {
		this.tiporicevuta = tiporicevuta;
	}

	public XRicevuta getRicevuta() {
		return ricevuta;
	}

	public void setRicevuta(XRicevuta ricevuta) {
		this.ricevuta = ricevuta;
	}

	public XTrasporto getTrasporto() {
		return trasporto;
	}

	public void setTrasporto(XTrasporto trasporto) {
		this.trasporto = trasporto;
	}

	public XVerificaSicurezza getVerficasicurezza() {
		return verficasicurezza;
	}

	public void setVerficasicurezza(XVerificaSicurezza verficasicurezza) {
		this.verficasicurezza = verficasicurezza;
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

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getRetrieveDate() {
		return retrieveDate;
	}

	public void setRetrieveDate(Date retrieveDate) {
		this.retrieveDate = retrieveDate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Boolean getRichiestaRicevuta() {
		return richiestaRicevuta;
	}

	public void setRichiestaRicevuta(Boolean richiestaRicevuta) {
		this.richiestaRicevuta = richiestaRicevuta;
	}

	public Boolean getRichiestaLettura() {
		return richiestaLettura;
	}

	public void setRichiestaLettura(Boolean richiestaLettura) {
		this.richiestaLettura = richiestaLettura;
	}

	public String getMessageidInReplyTo() {
		return messageidInReplyTo;
	}

	public void setMessageidInReplyTo(String messageidInReplyTo) {
		this.messageidInReplyTo = messageidInReplyTo;
	}

	public String getReplyTO() {
		return replyTO;
	}

	public void setReplyTO(String replyTO) {
		this.replyTO = replyTO;
	}
	
}