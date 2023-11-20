/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioMailBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.ItemLavorazioneMailBean;
import it.eng.aurigamailbusiness.bean.IdMailInoltrataMailXmlBean;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */

public class DettaglioPostaElettronicaBean {

	private String id;
	private String idEmail;
	private String flgIO;
	private String iconaMicroCategoria;
	private String titoloGUIDettaglioEmail;
	private String flgRicNotSenzaPredecessore;
	private String messageId;
	private String casellaId;
	private String casellaAccount;
	private String casellaIsPEC;
	private String categoria;
	private BigDecimal dimensione;
	private String uri;
	private String flgSpam;
	private String statoConsolidamento;
	private String iconaStatoConsolidamento;
	private String statoLavorazione;
	private String tipo;
	private String sottotipo;
	private String flgPEC;
	private String flgInteroperabile;
	private BigDecimal nroAllegati;
	private BigDecimal nroAllegatiFirmati;
	private String flgEmailFirmata;
	private String flgNoAssociazioneAutomatica;
	private String accountMittente;
	private String subject;
	private String body;
	private String escapedHtmlBody;
	private String siglaRegistroRegMitt;
	private String numRegMitt;
	private String annoRegMitt;
	private Date dtRegMitt;
	private String enteRegMitt;
	private String oggettoRegMitt;
	private Date tsLock;
	private String desUtenteLock;
	private String desOperLock;
	private String flgRichConferma;
	private String flgRichConfermaLettura;
	private Date tsUltimaAssegnazione;
	private String desUOAssegnataria;
	private String desUtenteAssegnatario;
	private String msgUltimaAssegnazione;
	private String flgInviataRisposta;
	private String flgInoltrata;
	private String statoProtocollazione;
	private String idRecDizContrassegno;
	private String contrassegno;
	private String flgRicevutaEccezioneInterop;
	private String flgRicevutaConfermaInterop;
	private String flgRicevutoAggiornamentoInterop;
	private String flgRicevutoAnnRegInterop;
	private Date tsRicezione;
	private Date tsInvioCertificato;
	private Date tsInvio;
	private String livPriorita;
	private String flgRicevutaCBS;
	private String destinatariPrincipali;
	private List<DettaglioPostaElettronicaDestinatario> listaDestinatariPrincipali;
	private String destinatariCC;
	private List<DettaglioPostaElettronicaDestinatario> listaDestinatariCC;
	private List<DettaglioPostaElettronicaDestinatario> listaDestinatariCCN;
	private String emailPredecessoreIdEmail;
	private List<IdMailInoltrataMailXmlBean> idEmailInoltrate;
	private String emailPredecessoreMessageId;
	private String emailPredecessoreFlgIO;
	private String emailPredecessoreIconaMicroCategoria;
	private String emailPredecessoreCategoria;
	private Date emailPredecessoreTsInvio;
	private Date emailPredecessoreTsRicezione;
	private String emailPredecessoreCasellaAccount;
	private String emailPredecessoreSubject;
	private String emailPredecessoreAccountMittente;
	private String emailPredecessoreTipo;
	private String emailPredecessoreSottotipo;
	private String emailPredecessoreFlgPEC;
	private String emailPredecessoreFlgInteroperabile;
	private String emailPredecessoreDestinatariPrincipali;
	private String emailPredecessoreDestinatariCC;
	private List<DettaglioPostaElettronicaEstremiDoc> listaEmailPredecessoreEstremiDocTrasmessi;
	private String estremiDocTrasmessi;
	private HashMap<String, String> mappaEstremiDocTrasmessi;
	private String estremiDocDerivati;
	private HashMap<String, String> mappaEstremiDocDerivati;
	private List<DettaglioPostaElettronicaAllegato> listaAllegati;
	private String abilitaStampaFile;
	private List<DettaglioPostaElettronicaEmailCollegata> listaEmailSuccessiveCollegate;
	private String abilitaRispondi;
	private String abilitaRispondiATutti;
	private String abilitaInoltraEmail;
	private String abilitaInoltraContenuti;
	private String abilitaAssegna;
	private String abilitaAssociaProtocollo;
	private String abilitaArchivia;
	private String abilitaProtocolla;
	private String abilitaScaricaEmail;
	private String abilitaScaricaEmailSenzaBustaTrasporto;
	private String abilitaInoltraEmailSbustata;
	private String abilitaNotifInteropConferma;
	private String abilitaNotifInteropEccezione;
	private String abilitaNotifInteropAggiornamento;
	private String abilitaNotifInteropAnnullamento;
	private String abilitaInvia;
	private String abilitaInvioCopia;
	private String abilitaAzioneDaFare;
	private String abilitaAssociaAInvio;
	private String abilitaRiapri;
	private String abilitaRepertoria;
	private String abilitaAnnullamentoInvio;
	private String abilitaRegIstanzaAutotutela;
	private String abilitaRegIstanzaCED;
	private String abilitaProtocollaAccessoAttiSUE;
	private AzioneDaFareBean azioneDaFareBean;
	private String emailPredecessoreId;
	private String emailPredecessoreProgMsgDStampa;
	private Date emailPredecessoreTsIns;
	private String progrDownloadStampa;
	private Date tsInsRegistrazione;
	private String avvertimenti;
	private String descrizioneAzioneDaFare;
	private String dettaglioAzioneDaFare;
	private String inCaricoA;
	private Date tsInCaricoDal;
	private Date tsUOAssegnazioneDal;

	private String dataAggStatoLavorazione;
	private String orarioAggStatoLavorazione;

	private String dataLock;
	private String orarioLock;

	private String dataUltimaAssegnazione;
	private String orarioUltimaAssegnazione;

	private String abilitaPresaInCarico;
	private String abilitaMessaInCarico;
	private String abilitaRilascio;

	private String idEmailPrecIn;

	private String progrBozza;

	private String abilitaSalvaBozza;
	private String abilitaSalvaItemLav;

	/* Nuova gestione Briciole di pane */
	private List<DettaglioPostaElettronicaEmailPrecedente> listaEmailPrecedenti;

	// Gestione degli item in lavorazione
	private List<ItemLavorazioneMailBean> listaItemInLavorazione;

	private String statoInvio;
	private String statoAccettazione;
	private String statoConsegna;
	private String iconaStatoInvio;
	private String iconaStatoAccettazione;
	private String iconaStatoConsegna;
	
	private String motivoEccezione;
	private String aliasAccountMittente;

	/* ITEM PER LE BOZZE */
	private InvioMailBean invioMailBean;

	/**
	 * mi indica in quale tab devo restare quando faccio il refresh da dettaglio
	 */
	private int indexTab;

	private String idMessaggio;
	
	/**
	 * Dati per gestione chiusura mail risposte o inoltare
	 */
	private String flgMailPredecessoreStatoLavAperta;
	private String flgMailPredecessoreConAzioneDaFare;
	private String ruoloVsPredecessore; 
	private BigDecimal nroMailPredecessore; 
	private String mailPredecessoriUnicaAzioneDaFare;

	// errori sugli stati 
	private String msgErrMancataAccettazione;   
	private String msgErrMancataConsegna;
	private String msgErrRicevutaPEC;
	
	// Gestione modalità pec
	private String flgURIRicevuta; 
	
	// Pregresso
	private String messagePregresso;
	
	private List<ItemLavorazioneMailBean> fileDaAppunti;
	
	// Conteggio destinatari principali, cc e ccn
	private String numeroTotaleDestinatariPrincipali;
	private String numeroTotaleDestinatariCopia;
	private String numeroTotaleDestinatariCCNAttori;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getFlgIO() {
		return flgIO;
	}

	public void setFlgIO(String flgIO) {
		this.flgIO = flgIO;
	}

	public String getIconaMicroCategoria() {
		return iconaMicroCategoria;
	}

	public void setIconaMicroCategoria(String iconaMicroCategoria) {
		this.iconaMicroCategoria = iconaMicroCategoria;
	}

	public String getTitoloGUIDettaglioEmail() {
		return titoloGUIDettaglioEmail;
	}

	public void setTitoloGUIDettaglioEmail(String titoloGUIDettaglioEmail) {
		this.titoloGUIDettaglioEmail = titoloGUIDettaglioEmail;
	}

	public String getFlgRicNotSenzaPredecessore() {
		return flgRicNotSenzaPredecessore;
	}

	public void setFlgRicNotSenzaPredecessore(String flgRicNotSenzaPredecessore) {
		this.flgRicNotSenzaPredecessore = flgRicNotSenzaPredecessore;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getCasellaId() {
		return casellaId;
	}

	public void setCasellaId(String casellaId) {
		this.casellaId = casellaId;
	}

	public String getCasellaAccount() {
		return casellaAccount;
	}

	public void setCasellaAccount(String casellaAccount) {
		this.casellaAccount = casellaAccount;
	}

	public String getCasellaIsPEC() {
		return casellaIsPEC;
	}

	public void setCasellaIsPEC(String casellaIsPEC) {
		this.casellaIsPEC = casellaIsPEC;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getDimensione() {
		return dimensione;
	}

	public void setDimensione(BigDecimal dimensione) {
		this.dimensione = dimensione;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getFlgSpam() {
		return flgSpam;
	}

	public void setFlgSpam(String flgSpam) {
		this.flgSpam = flgSpam;
	}

	public String getStatoConsolidamento() {
		return statoConsolidamento;
	}

	public void setStatoConsolidamento(String statoConsolidamento) {
		this.statoConsolidamento = statoConsolidamento;
	}

	public String getIconaStatoConsolidamento() {
		return iconaStatoConsolidamento;
	}

	public void setIconaStatoConsolidamento(String iconaStatoConsolidamento) {
		this.iconaStatoConsolidamento = iconaStatoConsolidamento;
	}

	public String getStatoLavorazione() {
		return statoLavorazione;
	}

	public void setStatoLavorazione(String statoLavorazione) {
		this.statoLavorazione = statoLavorazione;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFlgPEC() {
		return flgPEC;
	}

	public void setFlgPEC(String flgPEC) {
		this.flgPEC = flgPEC;
	}

	public BigDecimal getNroAllegati() {
		return nroAllegati;
	}

	public void setNroAllegati(BigDecimal nroAllegati) {
		this.nroAllegati = nroAllegati;
	}

	public BigDecimal getNroAllegatiFirmati() {
		return nroAllegatiFirmati;
	}

	public void setNroAllegatiFirmati(BigDecimal nroAllegatiFirmati) {
		this.nroAllegatiFirmati = nroAllegatiFirmati;
	}

	public String getFlgEmailFirmata() {
		return flgEmailFirmata;
	}

	public void setFlgEmailFirmata(String flgEmailFirmata) {
		this.flgEmailFirmata = flgEmailFirmata;
	}

	public String getFlgNoAssociazioneAutomatica() {
		return flgNoAssociazioneAutomatica;
	}

	public void setFlgNoAssociazioneAutomatica(String flgNoAssociazioneAutomatica) {
		this.flgNoAssociazioneAutomatica = flgNoAssociazioneAutomatica;
	}

	public String getAccountMittente() {
		return accountMittente;
	}

	public void setAccountMittente(String accountMittente) {
		this.accountMittente = accountMittente;
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

	public String getSiglaRegistroRegMitt() {
		return siglaRegistroRegMitt;
	}

	public void setSiglaRegistroRegMitt(String siglaRegistroRegMitt) {
		this.siglaRegistroRegMitt = siglaRegistroRegMitt;
	}

	public String getNumRegMitt() {
		return numRegMitt;
	}

	public void setNumRegMitt(String numRegMitt) {
		this.numRegMitt = numRegMitt;
	}

	public String getAnnoRegMitt() {
		return annoRegMitt;
	}

	public void setAnnoRegMitt(String annoRegMitt) {
		this.annoRegMitt = annoRegMitt;
	}

	public Date getDtRegMitt() {
		return dtRegMitt;
	}

	public void setDtRegMitt(Date dtRegMitt) {
		this.dtRegMitt = dtRegMitt;
	}

	public String getOggettoRegMitt() {
		return oggettoRegMitt;
	}

	public void setOggettoRegMitt(String oggettoRegMitt) {
		this.oggettoRegMitt = oggettoRegMitt;
	}

	public Date getTsLock() {
		return tsLock;
	}

	public void setTsLock(Date tsLock) {
		this.tsLock = tsLock;
	}

	public String getDesUtenteLock() {
		return desUtenteLock;
	}

	public void setDesUtenteLock(String desUtenteLock) {
		this.desUtenteLock = desUtenteLock;
	}

	public String getDesOperLock() {
		return desOperLock;
	}

	public void setDesOperLock(String desOperLock) {
		this.desOperLock = desOperLock;
	}

	public String getFlgRichConferma() {
		return flgRichConferma;
	}

	public void setFlgRichConferma(String flgRichConferma) {
		this.flgRichConferma = flgRichConferma;
	}

	public Date getTsUltimaAssegnazione() {
		return tsUltimaAssegnazione;
	}

	public void setTsUltimaAssegnazione(Date tsUltimaAssegnazione) {
		this.tsUltimaAssegnazione = tsUltimaAssegnazione;
	}

	public String getDesUOAssegnataria() {
		return desUOAssegnataria;
	}

	public void setDesUOAssegnataria(String desUOAssegnataria) {
		this.desUOAssegnataria = desUOAssegnataria;
	}

	public String getDesUtenteAssegnatario() {
		return desUtenteAssegnatario;
	}

	public void setDesUtenteAssegnatario(String desUtenteAssegnatario) {
		this.desUtenteAssegnatario = desUtenteAssegnatario;
	}

	public String getFlgInviataRisposta() {
		return flgInviataRisposta;
	}

	public void setFlgInviataRisposta(String flgInviataRisposta) {
		this.flgInviataRisposta = flgInviataRisposta;
	}

	public String getFlgInoltrata() {
		return flgInoltrata;
	}

	public void setFlgInoltrata(String flgInoltrata) {
		this.flgInoltrata = flgInoltrata;
	}

	public String getStatoProtocollazione() {
		return statoProtocollazione;
	}

	public void setStatoProtocollazione(String statoProtocollazione) {
		this.statoProtocollazione = statoProtocollazione;
	}

	public String getContrassegno() {
		return contrassegno;
	}

	public void setContrassegno(String contrassegno) {
		this.contrassegno = contrassegno;
	}

	public String getFlgRicevutaEccezioneInterop() {
		return flgRicevutaEccezioneInterop;
	}

	public void setFlgRicevutaEccezioneInterop(String flgRicevutaEccezioneInterop) {
		this.flgRicevutaEccezioneInterop = flgRicevutaEccezioneInterop;
	}

	public String getFlgRicevutaConfermaInterop() {
		return flgRicevutaConfermaInterop;
	}

	public void setFlgRicevutaConfermaInterop(String flgRicevutaConfermaInterop) {
		this.flgRicevutaConfermaInterop = flgRicevutaConfermaInterop;
	}

	public String getFlgRicevutoAggiornamentoInterop() {
		return flgRicevutoAggiornamentoInterop;
	}

	public void setFlgRicevutoAggiornamentoInterop(String flgRicevutoAggiornamentoInterop) {
		this.flgRicevutoAggiornamentoInterop = flgRicevutoAggiornamentoInterop;
	}

	public String getFlgRicevutoAnnRegInterop() {
		return flgRicevutoAnnRegInterop;
	}

	public void setFlgRicevutoAnnRegInterop(String flgRicevutoAnnRegInterop) {
		this.flgRicevutoAnnRegInterop = flgRicevutoAnnRegInterop;
	}

	public Date getTsRicezione() {
		return tsRicezione;
	}

	public void setTsRicezione(Date tsRicezione) {
		this.tsRicezione = tsRicezione;
	}

	public Date getTsInvioCertificato() {
		return tsInvioCertificato;
	}

	public void setTsInvioCertificato(Date tsInvioCertificato) {
		this.tsInvioCertificato = tsInvioCertificato;
	}

	public Date getTsInvio() {
		return tsInvio;
	}

	public void setTsInvio(Date tsInvio) {
		this.tsInvio = tsInvio;
	}

	public String getLivPriorita() {
		return livPriorita;
	}

	public void setLivPriorita(String livPriorita) {
		this.livPriorita = livPriorita;
	}

	public String getFlgRicevutaCBS() {
		return flgRicevutaCBS;
	}

	public void setFlgRicevutaCBS(String flgRicevutaCBS) {
		this.flgRicevutaCBS = flgRicevutaCBS;
	}

	public String getDestinatariPrincipali() {
		return destinatariPrincipali;
	}

	public void setDestinatariPrincipali(String destinatariPrincipali) {
		this.destinatariPrincipali = destinatariPrincipali;
	}

	public List<DettaglioPostaElettronicaDestinatario> getListaDestinatariPrincipali() {
		return listaDestinatariPrincipali;
	}

	public void setListaDestinatariPrincipali(List<DettaglioPostaElettronicaDestinatario> listaDestinatariPrincipali) {
		this.listaDestinatariPrincipali = listaDestinatariPrincipali;
	}

	public String getDestinatariCC() {
		return destinatariCC;
	}

	public void setDestinatariCC(String destinatariCC) {
		this.destinatariCC = destinatariCC;
	}

	public List<DettaglioPostaElettronicaDestinatario> getListaDestinatariCC() {
		return listaDestinatariCC;
	}

	public void setListaDestinatariCC(List<DettaglioPostaElettronicaDestinatario> listaDestinatariCC) {
		this.listaDestinatariCC = listaDestinatariCC;
	}

	public List<DettaglioPostaElettronicaDestinatario> getListaDestinatariCCN() {
		return listaDestinatariCCN;
	}

	public void setListaDestinatariCCN(List<DettaglioPostaElettronicaDestinatario> listaDestinatariCCN) {
		this.listaDestinatariCCN = listaDestinatariCCN;
	}

	public String getEmailPredecessoreIdEmail() {
		return emailPredecessoreIdEmail;
	}

	public void setEmailPredecessoreIdEmail(String emailPredecessoreIdEmail) {
		this.emailPredecessoreIdEmail = emailPredecessoreIdEmail;
	}

	public List<IdMailInoltrataMailXmlBean> getIdEmailInoltrate() {
		return idEmailInoltrate;
	}

	public void setIdEmailInoltrate(List<IdMailInoltrataMailXmlBean> idEmailInoltrate) {
		this.idEmailInoltrate = idEmailInoltrate;
	}

	public String getEmailPredecessoreMessageId() {
		return emailPredecessoreMessageId;
	}

	public void setEmailPredecessoreMessageId(String emailPredecessoreMessageId) {
		this.emailPredecessoreMessageId = emailPredecessoreMessageId;
	}

	public String getEmailPredecessoreFlgIO() {
		return emailPredecessoreFlgIO;
	}

	public void setEmailPredecessoreFlgIO(String emailPredecessoreFlgIO) {
		this.emailPredecessoreFlgIO = emailPredecessoreFlgIO;
	}

	public String getEmailPredecessoreIconaMicroCategoria() {
		return emailPredecessoreIconaMicroCategoria;
	}

	public void setEmailPredecessoreIconaMicroCategoria(String emailPredecessoreIconaMicroCategoria) {
		this.emailPredecessoreIconaMicroCategoria = emailPredecessoreIconaMicroCategoria;
	}

	public String getEmailPredecessoreCategoria() {
		return emailPredecessoreCategoria;
	}

	public void setEmailPredecessoreCategoria(String emailPredecessoreCategoria) {
		this.emailPredecessoreCategoria = emailPredecessoreCategoria;
	}

	public Date getEmailPredecessoreTsInvio() {
		return emailPredecessoreTsInvio;
	}

	public void setEmailPredecessoreTsInvio(Date emailPredecessoreTsInvio) {
		this.emailPredecessoreTsInvio = emailPredecessoreTsInvio;
	}

	public Date getEmailPredecessoreTsRicezione() {
		return emailPredecessoreTsRicezione;
	}

	public void setEmailPredecessoreTsRicezione(Date emailPredecessoreTsRicezione) {
		this.emailPredecessoreTsRicezione = emailPredecessoreTsRicezione;
	}

	public String getEmailPredecessoreCasellaAccount() {
		return emailPredecessoreCasellaAccount;
	}

	public void setEmailPredecessoreCasellaAccount(String emailPredecessoreCasellaAccount) {
		this.emailPredecessoreCasellaAccount = emailPredecessoreCasellaAccount;
	}

	public String getEmailPredecessoreSubject() {
		return emailPredecessoreSubject;
	}

	public void setEmailPredecessoreSubject(String emailPredecessoreSubject) {
		this.emailPredecessoreSubject = emailPredecessoreSubject;
	}

	public String getEmailPredecessoreAccountMittente() {
		return emailPredecessoreAccountMittente;
	}

	public void setEmailPredecessoreAccountMittente(String emailPredecessoreAccountMittente) {
		this.emailPredecessoreAccountMittente = emailPredecessoreAccountMittente;
	}

	public String getEmailPredecessoreTipo() {
		return emailPredecessoreTipo;
	}

	public void setEmailPredecessoreTipo(String emailPredecessoreTipo) {
		this.emailPredecessoreTipo = emailPredecessoreTipo;
	}

	public String getEmailPredecessoreFlgPEC() {
		return emailPredecessoreFlgPEC;
	}

	public void setEmailPredecessoreFlgPEC(String emailPredecessoreFlgPEC) {
		this.emailPredecessoreFlgPEC = emailPredecessoreFlgPEC;
	}

	public String getEmailPredecessoreDestinatariPrincipali() {
		return emailPredecessoreDestinatariPrincipali;
	}

	public void setEmailPredecessoreDestinatariPrincipali(String emailPredecessoreDestinatariPrincipali) {
		this.emailPredecessoreDestinatariPrincipali = emailPredecessoreDestinatariPrincipali;
	}

	public String getEmailPredecessoreDestinatariCC() {
		return emailPredecessoreDestinatariCC;
	}

	public void setEmailPredecessoreDestinatariCC(String emailPredecessoreDestinatariCC) {
		this.emailPredecessoreDestinatariCC = emailPredecessoreDestinatariCC;
	}

	public List<DettaglioPostaElettronicaEstremiDoc> getListaEmailPredecessoreEstremiDocTrasmessi() {
		return listaEmailPredecessoreEstremiDocTrasmessi;
	}

	public void setListaEmailPredecessoreEstremiDocTrasmessi(List<DettaglioPostaElettronicaEstremiDoc> listaEmailPredecessoreEstremiDocTrasmessi) {
		this.listaEmailPredecessoreEstremiDocTrasmessi = listaEmailPredecessoreEstremiDocTrasmessi;
	}

	public List<DettaglioPostaElettronicaAllegato> getListaAllegati() {
		return listaAllegati;
	}

	public void setListaAllegati(List<DettaglioPostaElettronicaAllegato> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}

	public List<DettaglioPostaElettronicaEmailCollegata> getListaEmailSuccessiveCollegate() {
		return listaEmailSuccessiveCollegate;
	}

	public void setListaEmailSuccessiveCollegate(List<DettaglioPostaElettronicaEmailCollegata> listaEmailSuccessiveCollegate) {
		this.listaEmailSuccessiveCollegate = listaEmailSuccessiveCollegate;
	}

	public String getAbilitaRispondi() {
		return abilitaRispondi;
	}

	public void setAbilitaRispondi(String abilitaRispondi) {
		this.abilitaRispondi = abilitaRispondi;
	}

	public String getAbilitaRispondiATutti() {
		return abilitaRispondiATutti;
	}

	public void setAbilitaRispondiATutti(String abilitaRispondiATutti) {
		this.abilitaRispondiATutti = abilitaRispondiATutti;
	}

	public String getAbilitaInoltraEmail() {
		return abilitaInoltraEmail;
	}

	public void setAbilitaInoltraEmail(String abilitaInoltraEmail) {
		this.abilitaInoltraEmail = abilitaInoltraEmail;
	}

	public String getAbilitaInoltraContenuti() {
		return abilitaInoltraContenuti;
	}

	public void setAbilitaInoltraContenuti(String abilitaInoltraContenuti) {
		this.abilitaInoltraContenuti = abilitaInoltraContenuti;
	}

	public String getAbilitaAssegna() {
		return abilitaAssegna;
	}

	public void setAbilitaAssegna(String abilitaAssegna) {
		this.abilitaAssegna = abilitaAssegna;
	}

	public String getAbilitaAssociaProtocollo() {
		return abilitaAssociaProtocollo;
	}

	public void setAbilitaAssociaProtocollo(String abilitaAssociaProtocollo) {
		this.abilitaAssociaProtocollo = abilitaAssociaProtocollo;
	}

	public String getAbilitaArchivia() {
		return abilitaArchivia;
	}

	public void setAbilitaArchivia(String abilitaArchivia) {
		this.abilitaArchivia = abilitaArchivia;
	}

	public String getAbilitaProtocolla() {
		return abilitaProtocolla;
	}

	public void setAbilitaProtocolla(String abilitaProtocolla) {
		this.abilitaProtocolla = abilitaProtocolla;
	}

	public String getAbilitaScaricaEmail() {
		return abilitaScaricaEmail;
	}

	public void setAbilitaScaricaEmail(String abilitaScaricaEmail) {
		this.abilitaScaricaEmail = abilitaScaricaEmail;
	}

	public String getAbilitaScaricaEmailSenzaBustaTrasporto() {
		return abilitaScaricaEmailSenzaBustaTrasporto;
	}

	public void setAbilitaScaricaEmailSenzaBustaTrasporto(String abilitaScaricaEmailSenzaBustaTrasporto) {
		this.abilitaScaricaEmailSenzaBustaTrasporto = abilitaScaricaEmailSenzaBustaTrasporto;
	}

	public String getAbilitaInoltraEmailSbustata() {
		return abilitaInoltraEmailSbustata;
	}

	public void setAbilitaInoltraEmailSbustata(String abilitaInoltraEmailSbustata) {
		this.abilitaInoltraEmailSbustata = abilitaInoltraEmailSbustata;
	}

	public String getEstremiDocDerivati() {
		return estremiDocDerivati;
	}

	public void setEstremiDocDerivati(String estremiDocDerivati) {
		this.estremiDocDerivati = estremiDocDerivati;
	}

	public HashMap<String, String> getMappaEstremiDocDerivati() {
		return mappaEstremiDocDerivati;
	}

	public void setMappaEstremiDocDerivati(HashMap<String, String> mappaEstremiDocDerivati) {
		this.mappaEstremiDocDerivati = mappaEstremiDocDerivati;
	}

	public String getEstremiDocTrasmessi() {
		return estremiDocTrasmessi;
	}

	public void setEstremiDocTrasmessi(String estremiDocTrasmessi) {
		this.estremiDocTrasmessi = estremiDocTrasmessi;
	}

	public HashMap<String, String> getMappaEstremiDocTrasmessi() {
		return mappaEstremiDocTrasmessi;
	}

	public void setMappaEstremiDocTrasmessi(HashMap<String, String> mappaEstremiDocTrasmessi) {
		this.mappaEstremiDocTrasmessi = mappaEstremiDocTrasmessi;
	}

	public String getEnteRegMitt() {
		return enteRegMitt;
	}

	public void setEnteRegMitt(String enteRegMitt) {
		this.enteRegMitt = enteRegMitt;
	}

	public String getEmailPredecessoreSottotipo() {
		return emailPredecessoreSottotipo;
	}

	public void setEmailPredecessoreSottotipo(String emailPredecessoreSottotipo) {
		this.emailPredecessoreSottotipo = emailPredecessoreSottotipo;
	}

	public String getSottotipo() {
		return sottotipo;
	}

	public void setSottotipo(String sottotipo) {
		this.sottotipo = sottotipo;
	}

	public String getFlgInteroperabile() {
		return flgInteroperabile;
	}

	public void setFlgInteroperabile(String flgInteroperabile) {
		this.flgInteroperabile = flgInteroperabile;
	}

	public String getEmailPredecessoreFlgInteroperabile() {
		return emailPredecessoreFlgInteroperabile;
	}

	public void setEmailPredecessoreFlgInteroperabile(String emailPredecessoreFlgInteroperabile) {
		this.emailPredecessoreFlgInteroperabile = emailPredecessoreFlgInteroperabile;
	}

	public String getAbilitaNotifInteropConferma() {
		return abilitaNotifInteropConferma;
	}

	public void setAbilitaNotifInteropConferma(String abilitaNotifInteropConferma) {
		this.abilitaNotifInteropConferma = abilitaNotifInteropConferma;
	}

	public String getAbilitaNotifInteropEccezione() {
		return abilitaNotifInteropEccezione;
	}

	public void setAbilitaNotifInteropEccezione(String abilitaNotifInteropEccezione) {
		this.abilitaNotifInteropEccezione = abilitaNotifInteropEccezione;
	}

	public String getAbilitaNotifInteropAggiornamento() {
		return abilitaNotifInteropAggiornamento;
	}

	public void setAbilitaNotifInteropAggiornamento(String abilitaNotifInteropAggiornamento) {
		this.abilitaNotifInteropAggiornamento = abilitaNotifInteropAggiornamento;
	}

	public String getAbilitaNotifInteropAnnullamento() {
		return abilitaNotifInteropAnnullamento;
	}

	public void setAbilitaNotifInteropAnnullamento(String abilitaNotifInteropAnnullamento) {
		this.abilitaNotifInteropAnnullamento = abilitaNotifInteropAnnullamento;
	}

	public String getIdRecDizContrassegno() {
		return idRecDizContrassegno;
	}

	public void setIdRecDizContrassegno(String idRecDizContrassegno) {
		this.idRecDizContrassegno = idRecDizContrassegno;
	}

	public String getMsgUltimaAssegnazione() {
		return msgUltimaAssegnazione;
	}

	public void setMsgUltimaAssegnazione(String msgUltimaAssegnazione) {
		this.msgUltimaAssegnazione = msgUltimaAssegnazione;
	}

	public String getEscapedHtmlBody() {
		return escapedHtmlBody;
	}

	public void setEscapedHtmlBody(String escapedHtmlBody) {
		this.escapedHtmlBody = escapedHtmlBody;
	}

	public String getAbilitaInvia() {
		return abilitaInvia;
	}

	public void setAbilitaInvia(String abilitaInvia) {
		this.abilitaInvia = abilitaInvia;
	}

	public String getAbilitaAzioneDaFare() {
		return abilitaAzioneDaFare;
	}

	public void setAbilitaAzioneDaFare(String abilitaAzioneDaFare) {
		this.abilitaAzioneDaFare = abilitaAzioneDaFare;
	}

	public AzioneDaFareBean getAzioneDaFareBean() {
		return azioneDaFareBean;
	}

	public void setAzioneDaFareBean(AzioneDaFareBean azioneDaFareBean) {
		this.azioneDaFareBean = azioneDaFareBean;
	}

	public String getAbilitaRiapri() {
		return abilitaRiapri;
	}

	public void setAbilitaRiapri(String abilitaRiapri) {
		this.abilitaRiapri = abilitaRiapri;
	}

	public String getEmailPredecessoreId() {
		return emailPredecessoreId;
	}

	public void setEmailPredecessoreId(String emailPredecessoreId) {
		this.emailPredecessoreId = emailPredecessoreId;
	}

	public String getEmailPredecessoreProgMsgDStampa() {
		return emailPredecessoreProgMsgDStampa;
	}

	public void setEmailPredecessoreProgMsgDStampa(String emailPredecessoreProgMsgDStampa) {
		this.emailPredecessoreProgMsgDStampa = emailPredecessoreProgMsgDStampa;
	}

	public Date getEmailPredecessoreTsIns() {
		return emailPredecessoreTsIns;
	}

	public void setEmailPredecessoreTsIns(Date emailPredecessoreTsIns) {
		this.emailPredecessoreTsIns = emailPredecessoreTsIns;
	}

	public String getProgrDownloadStampa() {
		return progrDownloadStampa;
	}

	public void setProgrDownloadStampa(String progrDownloadStampa) {
		this.progrDownloadStampa = progrDownloadStampa;
	}

	public Date getTsInsRegistrazione() {
		return tsInsRegistrazione;
	}

	public void setTsInsRegistrazione(Date tsInsRegistrazione) {
		this.tsInsRegistrazione = tsInsRegistrazione;
	}

	public String getDescrizioneAzioneDaFare() {
		return descrizioneAzioneDaFare;
	}

	public void setDescrizioneAzioneDaFare(String descrizioneAzioneDaFare) {
		this.descrizioneAzioneDaFare = descrizioneAzioneDaFare;
	}

	public String getDettaglioAzioneDaFare() {
		return dettaglioAzioneDaFare;
	}

	public void setDettaglioAzioneDaFare(String dettaglioAzioneDaFare) {
		this.dettaglioAzioneDaFare = dettaglioAzioneDaFare;
	}

	public String getInCaricoA() {
		return inCaricoA;
	}

	public void setInCaricoA(String inCaricoA) {
		this.inCaricoA = inCaricoA;
	}

	public Date getTsInCaricoDal() {
		return tsInCaricoDal;
	}

	public void setTsInCaricoDal(Date tsInCaricoDal) {
		this.tsInCaricoDal = tsInCaricoDal;
	}

	public Date getTsUOAssegnazioneDal() {
		return tsUOAssegnazioneDal;
	}

	public void setTsUOAssegnazioneDal(Date tsUOAssegnazioneDal) {
		this.tsUOAssegnazioneDal = tsUOAssegnazioneDal;
	}

	public List<DettaglioPostaElettronicaEmailPrecedente> getListaEmailPrecedenti() {
		return listaEmailPrecedenti;
	}

	public void setListaEmailPrecedenti(List<DettaglioPostaElettronicaEmailPrecedente> listaEmailPrecedenti) {
		this.listaEmailPrecedenti = listaEmailPrecedenti;
	}

	public String getStatoInvio() {
		return statoInvio;
	}

	public void setStatoInvio(String statoInvio) {
		this.statoInvio = statoInvio;
	}

	public String getStatoAccettazione() {
		return statoAccettazione;
	}

	public void setStatoAccettazione(String statoAccettazione) {
		this.statoAccettazione = statoAccettazione;
	}

	public String getStatoConsegna() {
		return statoConsegna;
	}

	public void setStatoConsegna(String statoConsegna) {
		this.statoConsegna = statoConsegna;
	}

	public String getDataAggStatoLavorazione() {
		return dataAggStatoLavorazione;
	}

	public void setDataAggStatoLavorazione(String dataAggStatoLavorazione) {
		this.dataAggStatoLavorazione = dataAggStatoLavorazione;
	}

	public String getOrarioAggStatoLavorazione() {
		return orarioAggStatoLavorazione;
	}

	public void setOrarioAggStatoLavorazione(String orarioAggStatoLavorazione) {
		this.orarioAggStatoLavorazione = orarioAggStatoLavorazione;
	}

	public String getIconaStatoInvio() {
		return iconaStatoInvio;
	}

	public void setIconaStatoInvio(String iconaStatoInvio) {
		this.iconaStatoInvio = iconaStatoInvio;
	}

	public String getIconaStatoAccettazione() {
		return iconaStatoAccettazione;
	}

	public void setIconaStatoAccettazione(String iconaStatoAccettazione) {
		this.iconaStatoAccettazione = iconaStatoAccettazione;
	}

	public String getIconaStatoConsegna() {
		return iconaStatoConsegna;
	}

	public void setIconaStatoConsegna(String iconaStatoConsegna) {
		this.iconaStatoConsegna = iconaStatoConsegna;
	}

	public String getDataLock() {
		return dataLock;
	}

	public void setDataLock(String dataLock) {
		this.dataLock = dataLock;
	}

	public String getOrarioLock() {
		return orarioLock;
	}

	public void setOrarioLock(String orarioLock) {
		this.orarioLock = orarioLock;
	}

	public String getDataUltimaAssegnazione() {
		return dataUltimaAssegnazione;
	}

	public void setDataUltimaAssegnazione(String dataUltimaAssegnazione) {
		this.dataUltimaAssegnazione = dataUltimaAssegnazione;
	}

	public String getOrarioUltimaAssegnazione() {
		return orarioUltimaAssegnazione;
	}

	public void setOrarioUltimaAssegnazione(String orarioUltimaAssegnazione) {
		this.orarioUltimaAssegnazione = orarioUltimaAssegnazione;
	}

	public String getAbilitaPresaInCarico() {
		return abilitaPresaInCarico;
	}

	public void setAbilitaPresaInCarico(String abilitaPresaInCarico) {
		this.abilitaPresaInCarico = abilitaPresaInCarico;
	}

	public String getAbilitaMessaInCarico() {
		return abilitaMessaInCarico;
	}

	public void setAbilitaMessaInCarico(String abilitaMessaInCarico) {
		this.abilitaMessaInCarico = abilitaMessaInCarico;
	}

	public String getAbilitaRilascio() {
		return abilitaRilascio;
	}

	public void setAbilitaRilascio(String abilitaRilascio) {
		this.abilitaRilascio = abilitaRilascio;
	}

	public String getAbilitaInvioCopia() {
		return abilitaInvioCopia;
	}

	public void setAbilitaInvioCopia(String abilitaInvioCopia) {
		this.abilitaInvioCopia = abilitaInvioCopia;
	}

	public String getIdEmailPrecIn() {
		return idEmailPrecIn;
	}

	public void setIdEmailPrecIn(String idEmailPrecIn) {
		this.idEmailPrecIn = idEmailPrecIn;
	}

	public String getAbilitaAssociaAInvio() {
		return abilitaAssociaAInvio;
	}

	public void setAbilitaAssociaAInvio(String abilitaAssociaAInvio) {
		this.abilitaAssociaAInvio = abilitaAssociaAInvio;
	}

	public int getIndexTab() {
		return indexTab;
	}

	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}

	public String getMotivoEccezione() {
		return motivoEccezione;
	}

	public void setMotivoEccezione(String motivoEccezione) {
		this.motivoEccezione = motivoEccezione;
	}

	public String getAliasAccountMittente() {
		return aliasAccountMittente;
	}

	public void setAliasAccountMittente(String aliasAccountMittente) {
		this.aliasAccountMittente = aliasAccountMittente;
	}

	public String getAbilitaAnnullamentoInvio() {
		return abilitaAnnullamentoInvio;
	}

	public void setAbilitaAnnullamentoInvio(String abilitaAnnullamentoInvio) {
		this.abilitaAnnullamentoInvio = abilitaAnnullamentoInvio;
	}

	public String getAvvertimenti() {
		return avvertimenti;
	}

	public void setAvvertimenti(String avvertimenti) {
		this.avvertimenti = avvertimenti;
	}

	public String getProgrBozza() {
		return progrBozza;
	}

	public void setProgrBozza(String progrBozza) {
		this.progrBozza = progrBozza;
	}

	public List<ItemLavorazioneMailBean> getListaItemInLavorazione() {
		return listaItemInLavorazione;
	}

	public void setListaItemInLavorazione(List<ItemLavorazioneMailBean> listaItemInLavorazione) {
		this.listaItemInLavorazione = listaItemInLavorazione;
	}

	public String getAbilitaSalvaBozza() {
		return abilitaSalvaBozza;
	}

	public void setAbilitaSalvaBozza(String abilitaSalvaBozza) {
		this.abilitaSalvaBozza = abilitaSalvaBozza;
	}

	public String getAbilitaSalvaItemLav() {
		return abilitaSalvaItemLav;
	}

	public void setAbilitaSalvaItemLav(String abilitaSalvaItemLav) {
		this.abilitaSalvaItemLav = abilitaSalvaItemLav;
	}

	public InvioMailBean getInvioMailBean() {
		return invioMailBean;
	}

	public void setInvioMailBean(InvioMailBean invioMailBean) {
		this.invioMailBean = invioMailBean;
	}

	public String getFlgRichConfermaLettura() {
		return flgRichConfermaLettura;
	}

	public void setFlgRichConfermaLettura(String flgRichConfermaLettura) {
		this.flgRichConfermaLettura = flgRichConfermaLettura;
	}

	/**
	 * @return the abilitaRegIstanzaAutotutela
	 */
	public String getAbilitaRegIstanzaAutotutela() {
		return abilitaRegIstanzaAutotutela;
	}

	/**
	 * @param abilitaRegIstanzaAutotutela
	 *            the abilitaRegIstanzaAutotutela to set
	 */
	public void setAbilitaRegIstanzaAutotutela(String abilitaRegIstanzaAutotutela) {
		this.abilitaRegIstanzaAutotutela = abilitaRegIstanzaAutotutela;
	}

	/**
	 * @return the abilitaRegIstanzaCED
	 */
	public String getAbilitaRegIstanzaCED() {
		return abilitaRegIstanzaCED;
	}

	/**
	 * @param abilitaRegIstanzaCED
	 *            the abilitaRegIstanzaCED to set
	 */
	public void setAbilitaRegIstanzaCED(String abilitaRegIstanzaCED) {
		this.abilitaRegIstanzaCED = abilitaRegIstanzaCED;
	}
	
	public String getAbilitaProtocollaAccessoAttiSUE() {
		return abilitaProtocollaAccessoAttiSUE;
	}

	public void setAbilitaProtocollaAccessoAttiSUE(String abilitaProtocollaAccessoAttiSUE) {
		this.abilitaProtocollaAccessoAttiSUE = abilitaProtocollaAccessoAttiSUE;
	}

	/**
	 * @return the abilitaStampaFile
	 */
	public String getAbilitaStampaFile() {
		return abilitaStampaFile;
	}

	/**
	 * @param abilitaStampaFile
	 *            the abilitaStampaFile to set
	 */
	public void setAbilitaStampaFile(String abilitaStampaFile) {
		this.abilitaStampaFile = abilitaStampaFile;
	}

	public String getIdMessaggio() {
		return idMessaggio;
	}

	public void setIdMessaggio(String idMessaggio) {
		this.idMessaggio = idMessaggio;
	}
	
	public String getFlgMailPredecessoreStatoLavAperta() {
		return flgMailPredecessoreStatoLavAperta;
	}
	
	public void setFlgMailPredecessoreStatoLavAperta(String flgMailPredecessoreStatoLavAperta) {
		this.flgMailPredecessoreStatoLavAperta = flgMailPredecessoreStatoLavAperta;
	}

	public String getFlgMailPredecessoreConAzioneDaFare() {
		return flgMailPredecessoreConAzioneDaFare;
	}

	public void setFlgMailPredecessoreConAzioneDaFare(String flgMailPredecessoreConAzioneDaFare) {
		this.flgMailPredecessoreConAzioneDaFare = flgMailPredecessoreConAzioneDaFare;
	}

	public String getRuoloVsPredecessore() {
		return ruoloVsPredecessore;
	}

	public void setRuoloVsPredecessore(String ruoloVsPredecessore) {
		this.ruoloVsPredecessore = ruoloVsPredecessore;
	}

	public BigDecimal getNroMailPredecessore() {
		return nroMailPredecessore;
	}

	public void setNroMailPredecessore(BigDecimal nroMailPredecessore) {
		this.nroMailPredecessore = nroMailPredecessore;
	}
	
	public String getMailPredecessoriUnicaAzioneDaFare() {
		return mailPredecessoriUnicaAzioneDaFare;
	}

	public void setMailPredecessoriUnicaAzioneDaFare(String mailPredecessoriUnicaAzioneDaFare) {
		this.mailPredecessoriUnicaAzioneDaFare = mailPredecessoriUnicaAzioneDaFare;
	}

	public String getMsgErrMancataAccettazione() {
		return msgErrMancataAccettazione;
	}

	public void setMsgErrMancataAccettazione(String msgErrMancataAccettazione) {
		this.msgErrMancataAccettazione = msgErrMancataAccettazione;
	}

	public String getMsgErrMancataConsegna() {
		return msgErrMancataConsegna;
	}

	public void setMsgErrMancataConsegna(String msgErrMancataConsegna) {
		this.msgErrMancataConsegna = msgErrMancataConsegna;
	}

	public String getMsgErrRicevutaPEC() {
		return msgErrRicevutaPEC;
	}

	public void setMsgErrRicevutaPEC(String msgErrRicevutaPEC) {
		this.msgErrRicevutaPEC = msgErrRicevutaPEC;
	}
	
	public String getFlgURIRicevuta() {
		return flgURIRicevuta;
	}

	public void setFlgURIRicevuta(String flgURIRicevuta) {
		this.flgURIRicevuta = flgURIRicevuta;
	}
	
	public String getAbilitaRepertoria() {
		return abilitaRepertoria;
	}

	public void setAbilitaRepertoria(String abilitaRepertoria) {
		this.abilitaRepertoria = abilitaRepertoria;
	}
	
	public List<ItemLavorazioneMailBean> getFileDaAppunti() {
		return fileDaAppunti;
	}

	public void setFileDaAppunti(List<ItemLavorazioneMailBean> fileDaAppunti) {
		this.fileDaAppunti = fileDaAppunti;
	}
	
	public String getMessagePregresso() {
		return messagePregresso;
	}

	public void setMessagePregresso(String messagePregresso) {
		this.messagePregresso = messagePregresso;
	}

	public String getNumeroTotaleDestinatariPrincipali() {
		return numeroTotaleDestinatariPrincipali;
	}

	public void setNumeroTotaleDestinatariPrincipali(String numeroTotaleDestinatariPrincipali) {
		this.numeroTotaleDestinatariPrincipali = numeroTotaleDestinatariPrincipali;
	}

	public String getNumeroTotaleDestinatariCopia() {
		return numeroTotaleDestinatariCopia;
	}

	public void setNumeroTotaleDestinatariCopia(String numeroTotaleDestinatariCopia) {
		this.numeroTotaleDestinatariCopia = numeroTotaleDestinatariCopia;
	}

	public String getNumeroTotaleDestinatariCCNAttori() {
		return numeroTotaleDestinatariCCNAttori;
	}

	public void setNumeroTotaleDestinatariCCNAttori(String numeroTotaleDestinatariCCNAttori) {
		this.numeroTotaleDestinatariCCNAttori = numeroTotaleDestinatariCCNAttori;
	}
	
}