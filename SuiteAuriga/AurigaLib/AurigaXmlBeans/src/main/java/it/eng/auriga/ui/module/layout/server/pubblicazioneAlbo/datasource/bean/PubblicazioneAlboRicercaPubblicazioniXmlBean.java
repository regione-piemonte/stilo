/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class PubblicazioneAlboRicercaPubblicazioniXmlBean {

	@NumeroColonna(numero = "1")
	private String flgUdFolder;

	@NumeroColonna(numero = "2")
	private String idUdFolder;

	@NumeroColonna(numero = "4")
	private String segnatura;

	@NumeroColonna(numero = "6")
	private String segnaturaXOrd;

	@NumeroColonna(numero = "14")
	private String nroPubblicazione;

	@NumeroColonna(numero = "15")
	@TipoData(tipo = Tipo.DATA)
	private Date tsPubblicazione;

	@NumeroColonna(numero = "18")
	private String oggetto;

	@NumeroColonna(numero = "32")
	private String tipo;

	@NumeroColonna(numero = "33")
	private String idDocPrimario;

	@NumeroColonna(numero = "35")
	@TipoData(tipo = Tipo.DATA)
	private Date tsInvio;

	@NumeroColonna(numero = "36")
	@TipoData(tipo = Tipo.DATA)
	private Date tsEliminazione;

	@NumeroColonna(numero = "37")
	private String eliminatoDa;

	@NumeroColonna(numero = "38")
	private String destinatariInvio;

	@NumeroColonna(numero = "39")
	private Boolean flgSelXFinalita;

	@NumeroColonna(numero = "42")
	private String assegnatari;

	@NumeroColonna(numero = "60")
	@TipoData(tipo = Tipo.DATA)
	private Date tsRicezione;

	@NumeroColonna(numero = "61")
	private BigDecimal prioritaInvioNotifiche;

	@NumeroColonna(numero = "62")
	private String estremiInvioNotifiche;
	
	@NumeroColonna(numero = "72")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtEsecutivita;

	@NumeroColonna(numero = "89")
	private BigDecimal score;

	@NumeroColonna(numero = "91")
	private String richiedentePubblicazione;

	@NumeroColonna(numero = "93")
	private String nroDocConFile;

	@NumeroColonna(numero = "94")
	private String nroDocConFileFirmati;

	@NumeroColonna(numero = "95")
	private String nroDocConFileDaScanner;

	@NumeroColonna(numero = "96")
	private String flgRicevutaViaEmail;

	@NumeroColonna(numero = "97")
	private String flgInviataViaEmail;

	@NumeroColonna(numero = "101")
	private Integer flgImmediatamenteEseg;

	@NumeroColonna(numero = "201")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAtto;

	@NumeroColonna(numero = "206")
	private String fascicoliApp;

	@NumeroColonna(numero = "207")
	private String livelloRiservatezza;

	@NumeroColonna(numero = "209")
	private String flgNotificatoAMe;

	@NumeroColonna(numero = "214")
	private String utenteProtocollante;

	@NumeroColonna(numero = "215")
	private String uoProtocollante;

	@NumeroColonna(numero = "226")
	private String uoProponente;

	@NumeroColonna(numero = "227")
	private String statoTrasmissioneEmail;
	
	@NumeroColonna(numero = "261")
	private String attoAutAnnullamento;
	
	@NumeroColonna(numero = "263")
	@TipoData(tipo = Tipo.DATA)
	private Date tsPresaInCarico;
	
	@NumeroColonna(numero = "270")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInizioPubblicazione;

	@NumeroColonna(numero = "271")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFinePubblicazione;

	@NumeroColonna(numero = "273")
	private String formaPubblicazione;

	@NumeroColonna(numero = "274")
	private String statoPubblicazione;
	
	@NumeroColonna(numero = "276")
	private String motivoAnnullamento;
	
	@NumeroColonna(numero = "281")
	private String centroDiCosto;	
	
	@NumeroColonna(numero = "282")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataScadenza;
	
	@NumeroColonna(numero = "283")
	private String idRichPubbl;

	public String getFlgUdFolder() {
		return flgUdFolder;
	}

	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
	}

	public String getIdUdFolder() {
		return idUdFolder;
	}

	public void setIdUdFolder(String idUdFolder) {
		this.idUdFolder = idUdFolder;
	}

	public String getSegnatura() {
		return segnatura;
	}

	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}

	public String getSegnaturaXOrd() {
		return segnaturaXOrd;
	}

	public void setSegnaturaXOrd(String segnaturaXOrd) {
		this.segnaturaXOrd = segnaturaXOrd;
	}

	public String getNroPubblicazione() {
		return nroPubblicazione;
	}

	public void setNroPubblicazione(String nroPubblicazione) {
		this.nroPubblicazione = nroPubblicazione;
	}

	public Date getTsPubblicazione() {
		return tsPubblicazione;
	}

	public void setTsPubblicazione(Date tsPubblicazione) {
		this.tsPubblicazione = tsPubblicazione;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIdDocPrimario() {
		return idDocPrimario;
	}

	public void setIdDocPrimario(String idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}

	public Date getTsInvio() {
		return tsInvio;
	}

	public void setTsInvio(Date tsInvio) {
		this.tsInvio = tsInvio;
	}

	public Date getTsEliminazione() {
		return tsEliminazione;
	}

	public void setTsEliminazione(Date tsEliminazione) {
		this.tsEliminazione = tsEliminazione;
	}

	public String getEliminatoDa() {
		return eliminatoDa;
	}

	public void setEliminatoDa(String eliminatoDa) {
		this.eliminatoDa = eliminatoDa;
	}

	public String getDestinatariInvio() {
		return destinatariInvio;
	}

	public void setDestinatariInvio(String destinatariInvio) {
		this.destinatariInvio = destinatariInvio;
	}

	public Boolean getFlgSelXFinalita() {
		return flgSelXFinalita;
	}

	public void setFlgSelXFinalita(Boolean flgSelXFinalita) {
		this.flgSelXFinalita = flgSelXFinalita;
	}

	public String getAssegnatari() {
		return assegnatari;
	}

	public void setAssegnatari(String assegnatari) {
		this.assegnatari = assegnatari;
	}

	public Date getTsRicezione() {
		return tsRicezione;
	}

	public void setTsRicezione(Date tsRicezione) {
		this.tsRicezione = tsRicezione;
	}

	public BigDecimal getPrioritaInvioNotifiche() {
		return prioritaInvioNotifiche;
	}

	public void setPrioritaInvioNotifiche(BigDecimal prioritaInvioNotifiche) {
		this.prioritaInvioNotifiche = prioritaInvioNotifiche;
	}

	public String getEstremiInvioNotifiche() {
		return estremiInvioNotifiche;
	}

	public void setEstremiInvioNotifiche(String estremiInvioNotifiche) {
		this.estremiInvioNotifiche = estremiInvioNotifiche;
	}

	public Date getDtEsecutivita() {
		return dtEsecutivita;
	}

	public void setDtEsecutivita(Date dtEsecutivita) {
		this.dtEsecutivita = dtEsecutivita;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getRichiedentePubblicazione() {
		return richiedentePubblicazione;
	}

	public void setRichiedentePubblicazione(String richiedentePubblicazione) {
		this.richiedentePubblicazione = richiedentePubblicazione;
	}

	public String getNroDocConFile() {
		return nroDocConFile;
	}

	public void setNroDocConFile(String nroDocConFile) {
		this.nroDocConFile = nroDocConFile;
	}

	public String getNroDocConFileFirmati() {
		return nroDocConFileFirmati;
	}

	public void setNroDocConFileFirmati(String nroDocConFileFirmati) {
		this.nroDocConFileFirmati = nroDocConFileFirmati;
	}

	public String getNroDocConFileDaScanner() {
		return nroDocConFileDaScanner;
	}

	public void setNroDocConFileDaScanner(String nroDocConFileDaScanner) {
		this.nroDocConFileDaScanner = nroDocConFileDaScanner;
	}

	public String getFlgRicevutaViaEmail() {
		return flgRicevutaViaEmail;
	}

	public void setFlgRicevutaViaEmail(String flgRicevutaViaEmail) {
		this.flgRicevutaViaEmail = flgRicevutaViaEmail;
	}

	public String getFlgInviataViaEmail() {
		return flgInviataViaEmail;
	}

	public void setFlgInviataViaEmail(String flgInviataViaEmail) {
		this.flgInviataViaEmail = flgInviataViaEmail;
	}

	public Integer getFlgImmediatamenteEseg() {
		return flgImmediatamenteEseg;
	}

	public void setFlgImmediatamenteEseg(Integer flgImmediatamenteEseg) {
		this.flgImmediatamenteEseg = flgImmediatamenteEseg;
	}

	public Date getDataAtto() {
		return dataAtto;
	}

	public void setDataAtto(Date dataAtto) {
		this.dataAtto = dataAtto;
	}

	public String getFascicoliApp() {
		return fascicoliApp;
	}

	public void setFascicoliApp(String fascicoliApp) {
		this.fascicoliApp = fascicoliApp;
	}

	public String getLivelloRiservatezza() {
		return livelloRiservatezza;
	}

	public void setLivelloRiservatezza(String livelloRiservatezza) {
		this.livelloRiservatezza = livelloRiservatezza;
	}

	public String getFlgNotificatoAMe() {
		return flgNotificatoAMe;
	}

	public void setFlgNotificatoAMe(String flgNotificatoAMe) {
		this.flgNotificatoAMe = flgNotificatoAMe;
	}

	public String getUtenteProtocollante() {
		return utenteProtocollante;
	}

	public void setUtenteProtocollante(String utenteProtocollante) {
		this.utenteProtocollante = utenteProtocollante;
	}

	public String getUoProtocollante() {
		return uoProtocollante;
	}

	public void setUoProtocollante(String uoProtocollante) {
		this.uoProtocollante = uoProtocollante;
	}

	public String getUoProponente() {
		return uoProponente;
	}

	public void setUoProponente(String uoProponente) {
		this.uoProponente = uoProponente;
	}

	public String getStatoTrasmissioneEmail() {
		return statoTrasmissioneEmail;
	}

	public void setStatoTrasmissioneEmail(String statoTrasmissioneEmail) {
		this.statoTrasmissioneEmail = statoTrasmissioneEmail;
	}

	public String getAttoAutAnnullamento() {
		return attoAutAnnullamento;
	}

	public void setAttoAutAnnullamento(String attoAutAnnullamento) {
		this.attoAutAnnullamento = attoAutAnnullamento;
	}

	public Date getTsPresaInCarico() {
		return tsPresaInCarico;
	}

	public void setTsPresaInCarico(Date tsPresaInCarico) {
		this.tsPresaInCarico = tsPresaInCarico;
	}

	public Date getDataInizioPubblicazione() {
		return dataInizioPubblicazione;
	}

	public void setDataInizioPubblicazione(Date dataInizioPubblicazione) {
		this.dataInizioPubblicazione = dataInizioPubblicazione;
	}

	public Date getDataFinePubblicazione() {
		return dataFinePubblicazione;
	}

	public void setDataFinePubblicazione(Date dataFinePubblicazione) {
		this.dataFinePubblicazione = dataFinePubblicazione;
	}

	public String getFormaPubblicazione() {
		return formaPubblicazione;
	}

	public void setFormaPubblicazione(String formaPubblicazione) {
		this.formaPubblicazione = formaPubblicazione;
	}

	public String getStatoPubblicazione() {
		return statoPubblicazione;
	}

	public void setStatoPubblicazione(String statoPubblicazione) {
		this.statoPubblicazione = statoPubblicazione;
	}

	public String getMotivoAnnullamento() {
		return motivoAnnullamento;
	}

	public void setMotivoAnnullamento(String motivoAnnullamento) {
		this.motivoAnnullamento = motivoAnnullamento;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getIdRichPubbl() {
		return idRichPubbl;
	}

	public void setIdRichPubbl(String idRichPubbl) {
		this.idRichPubbl = idRichPubbl;
	}	
		
}