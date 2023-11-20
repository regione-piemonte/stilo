/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;


public class DettaglioEmailBean {

	// ID DELLA MAIL
	private String id;
	// ID MAIL PREDECESSORE
	private String idEmail;
	private String tipoRel;
	private String flgIo;
	private String accountMittente;
	private String replyTo;
	private String oggetto;
	private String corpo;
	private String corpoHtml;
	private String uriEmail;
	private Date dateRif;
	private String statoConsolidamento;
	private String inviataRispostaPresente;
	private String inoltrataPresente;
	private String notificaEccezionePresente;
	private String notificaConfermaPresente;
	private String notificaAggiornamentoPresente;
	private String notificaAnnullamentoPresente;
	private List<DettaglioEmailDestinatarioBean> destinatariPrincipali;
	private List<DettaglioEmailDestinatarioBean> destinatariCC;
	private List<DettaglioEmailDestinatarioBean> destinatariCCn;
	private List<DettaglioEmailAllegatoBean> allegati;
	private String avvertimenti;
	private Date tsInvio;
	private Date tsRicezione;
	private String allegaEmlSbustato;
	private String progrDownloadStampa;
	private boolean flgRilascia;
	private String firmaEmailHtml;	
	private String aliasAccountMittente;
	private String casellaRicezione;
	
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getCorpo() {
		return corpo;
	}
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	public String getIdEmail() {
		return idEmail;
	}
	public void setUriEmail(String uriEmail) {
		this.uriEmail = uriEmail;
	}
	public String getUriEmail() {
		return uriEmail;
	}
	public void setAccountMittente(String accountMittente) {
		this.accountMittente = accountMittente;
	}
	public String getAccountMittente() {
		return accountMittente;
	}
	public String getReplyTo() {
		return replyTo;
	}
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	public void setStatoConsolidamento(String statoConsolidamento) {
		this.statoConsolidamento = statoConsolidamento;
	}
	public String getStatoConsolidamento() {
		return statoConsolidamento;
	}
	public String getInviataRispostaPresente() {
		return inviataRispostaPresente;
	}
	public void setInviataRispostaPresente(String inviataRispostaPresente) {
		this.inviataRispostaPresente = inviataRispostaPresente;
	}
	public String getInoltrataPresente() {
		return inoltrataPresente;
	}
	public void setInoltrataPresente(String inoltrataPresente) {
		this.inoltrataPresente = inoltrataPresente;
	}
	public String getNotificaEccezionePresente() {
		return notificaEccezionePresente;
	}
	public void setNotificaEccezionePresente(String notificaEccezionePresente) {
		this.notificaEccezionePresente = notificaEccezionePresente;
	}
	public String getNotificaConfermaPresente() {
		return notificaConfermaPresente;
	}
	public void setNotificaConfermaPresente(String notificaConfermaPresente) {
		this.notificaConfermaPresente = notificaConfermaPresente;
	}
	public String getNotificaAggiornamentoPresente() {
		return notificaAggiornamentoPresente;
	}
	public void setNotificaAggiornamentoPresente(
			String notificaAggiornamentoPresente) {
		this.notificaAggiornamentoPresente = notificaAggiornamentoPresente;
	}
	public String getNotificaAnnullamentoPresente() {
		return notificaAnnullamentoPresente;
	}
	public void setNotificaAnnullamentoPresente(String notificaAnnullamentoPresente) {
		this.notificaAnnullamentoPresente = notificaAnnullamentoPresente;
	}
	public void setDestinatariPrincipali(List<DettaglioEmailDestinatarioBean> destinatariPrincipali) {
		this.destinatariPrincipali = destinatariPrincipali;
	}
	public List<DettaglioEmailDestinatarioBean> getDestinatariPrincipali() {
		return destinatariPrincipali;
	}
	public List<DettaglioEmailDestinatarioBean> getDestinatariCC() {
		return destinatariCC;
	}
	public void setDestinatariCC(List<DettaglioEmailDestinatarioBean> destinatariCC) {
		this.destinatariCC = destinatariCC;
	}
	public List<DettaglioEmailDestinatarioBean> getDestinatariCCn() {
		return destinatariCCn;
	}
	public void setDestinatariCCn(
			List<DettaglioEmailDestinatarioBean> destinatariCCn) {
		this.destinatariCCn = destinatariCCn;
	}
	public List<DettaglioEmailAllegatoBean> getAllegati() {
		return allegati;
	}
	public void setAllegati(List<DettaglioEmailAllegatoBean> allegati) {
		this.allegati = allegati;
	}
	public Date getDateRif() {
		return dateRif;
	}
	public void setDateRif(Date dateRif) {
		this.dateRif = dateRif;
	}
	public String getFlgIo() {
		return flgIo;
	}
	public void setFlgIo(String flgIo) {
		this.flgIo = flgIo;
	}
	public String getAvvertimenti() {
		return avvertimenti;
	}
	public void setAvvertimenti(String avvertimenti) {
		this.avvertimenti = avvertimenti;
	}
	public String getTipoRel() {
		return tipoRel;
	}
	public void setTipoRel(String tipoRel) {
		this.tipoRel = tipoRel;
	}
	public Date getTsInvio() {
		return tsInvio;
	}
	public void setTsInvio(Date tsInvio) {
		this.tsInvio = tsInvio;
	}
	public Date getTsRicezione() {
		return tsRicezione;
	}
	public void setTsRicezione(Date tsRicezione) {
		this.tsRicezione = tsRicezione;
	}
	public String getAllegaEmlSbustato() {
		return allegaEmlSbustato;
	}
	public void setAllegaEmlSbustato(String allegaEmlSbustato) {
		this.allegaEmlSbustato = allegaEmlSbustato;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProgrDownloadStampa() {
		return progrDownloadStampa;
	}
	public void setProgrDownloadStampa(String progrDownloadStampa) {
		this.progrDownloadStampa = progrDownloadStampa;
	}
	public String getCorpoHtml() {
		return corpoHtml;
	}
	public void setCorpoHtml(String corpoHtml) {
		this.corpoHtml = corpoHtml;
	}
	public boolean isFlgRilascia() {
		return flgRilascia;
	}
	public void setFlgRilascia(boolean flgRilascia) {
		this.flgRilascia = flgRilascia;
	}
	public String getFirmaEmailHtml() {
		return firmaEmailHtml;
	}
	public void setFirmaEmailHtml(String firmaEmailHtml) {
		this.firmaEmailHtml = firmaEmailHtml;
	}
	public String getAliasAccountMittente() {
		return aliasAccountMittente;
	}
	public void setAliasAccountMittente(String aliasAccountMittente) {
		this.aliasAccountMittente = aliasAccountMittente;
	}
	public String getCasellaRicezione() {
		return casellaRicezione;
	}
	public void setCasellaRicezione(String casellaRicezione) {
		this.casellaRicezione = casellaRicezione;
	}
}