/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.ItemLavorazioneMailBean;
import it.eng.aurigamailbusiness.bean.IdMailInoltrataMailXmlBean;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public class XmlDatiPostaElettronicaBean {

	@XmlVariabile(nome = "ProgrMsg", tipo = TipoVariabile.SEMPLICE)
	private String id;
	@XmlVariabile(nome = "FlgIO", tipo = TipoVariabile.SEMPLICE)
	private String flgIO;
	@XmlVariabile(nome = "IconaMicroCategoria", tipo = TipoVariabile.SEMPLICE)
	private String iconaMicroCategoria;
	@XmlVariabile(nome = "TitoloGUIDettaglioEmail", tipo = TipoVariabile.SEMPLICE)
	private String titoloGUIDettaglioEmail;
	@XmlVariabile(nome = "FlgRicNotSenzaPredecessore", tipo = TipoVariabile.SEMPLICE)
	private String flgRicNotSenzaPredecessore;
	@XmlVariabile(nome = "MessageId", tipo = TipoVariabile.SEMPLICE)
	private String messageId;
	@XmlVariabile(nome = "Casella.Id", tipo = TipoVariabile.SEMPLICE)
	private String casellaId;
	@XmlVariabile(nome = "Casella.Account", tipo = TipoVariabile.SEMPLICE)
	private String casellaAccount;
	@XmlVariabile(nome = "Casella.IsPEC", tipo = TipoVariabile.SEMPLICE)
	private String casellaIsPEC;
	@XmlVariabile(nome = "Categoria", tipo = TipoVariabile.SEMPLICE)
	private String categoria;
	@XmlVariabile(nome = "Dimensione", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal dimensione;
	@XmlVariabile(nome = "URI", tipo = TipoVariabile.SEMPLICE)
	private String uri;
	@XmlVariabile(nome = "FlgSpam", tipo = TipoVariabile.SEMPLICE)
	private String flgSpam;
	@XmlVariabile(nome = "StatoLavorazione", tipo = TipoVariabile.SEMPLICE)
	private String statoLavorazione;
	@XmlVariabile(nome = "DataAggStatoLavorazione", tipo = TipoVariabile.SEMPLICE)
	private String dataAggStatoLavorazione;
	@XmlVariabile(nome = "OrarioAggStatoLavorazione", tipo = TipoVariabile.SEMPLICE)
	private String orarioAggStatoLavorazione;
	@XmlVariabile(nome = "Tipo", tipo = TipoVariabile.SEMPLICE)
	private String tipo;
	@XmlVariabile(nome = "Sottotipo", tipo = TipoVariabile.SEMPLICE)
	private String sottotipo;
	@XmlVariabile(nome = "FlgPEC", tipo = TipoVariabile.SEMPLICE)
	private String flgPEC;
	@XmlVariabile(nome = "FlgInteroperabile", tipo = TipoVariabile.SEMPLICE)
	private String flgInteroperabile;
	@XmlVariabile(nome = "NroAllegati", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal nroAllegati;
	@XmlVariabile(nome = "NroAllegatiFirmati", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal nroAllegatiFirmati;
	@XmlVariabile(nome = "FlgEmailFirmata", tipo = TipoVariabile.SEMPLICE)
	private String flgEmailFirmata;
	@XmlVariabile(nome = "FlgNoAssociazioneAutomatica", tipo = TipoVariabile.SEMPLICE)
	private String flgNoAssociazioneAutomatica;
	@XmlVariabile(nome = "AccountMittente", tipo = TipoVariabile.SEMPLICE)
	private String accountMittente;
	@XmlVariabile(nome = "Subject", tipo = TipoVariabile.SEMPLICE)
	private String subject;
	@XmlVariabile(nome = "Body", tipo = TipoVariabile.SEMPLICE)
	private String body;
	@XmlVariabile(nome = "SiglaRegistroRegMitt", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegistroRegMitt;
	@XmlVariabile(nome = "NumRegMitt", tipo = TipoVariabile.SEMPLICE)
	private String numRegMitt;
	@XmlVariabile(nome = "EnteRegMitt", tipo = TipoVariabile.SEMPLICE)
	private String enteRegMitt;
	@XmlVariabile(nome = "AnnoRegMitt", tipo = TipoVariabile.SEMPLICE)
	private String annoRegMitt;
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome = "DtRegMitt", tipo = TipoVariabile.SEMPLICE)
	private Date dtRegMitt;
	@XmlVariabile(nome = "OggettoRegMitt", tipo = TipoVariabile.SEMPLICE)
	private String oggettoRegMitt;
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "TsLock", tipo = TipoVariabile.SEMPLICE)
	private Date tsLock;
	@XmlVariabile(nome = "DesUtenteLock", tipo = TipoVariabile.SEMPLICE)
	private String desUtenteLock;
	@XmlVariabile(nome = "DesOperLock", tipo = TipoVariabile.SEMPLICE)
	private String desOperLock;
	@XmlVariabile(nome = "FlgRichConferma", tipo = TipoVariabile.SEMPLICE)
	private String flgRichConferma;
	@XmlVariabile(nome = "FlgRichConfermaLettura", tipo = TipoVariabile.SEMPLICE)
	private String flgRichConfermaLettura;
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "TsUltimaAssegnazione", tipo = TipoVariabile.SEMPLICE)
	private Date tsUltimaAssegnazione;
	@XmlVariabile(nome = "Avvertimenti", tipo = TipoVariabile.SEMPLICE)
	private String avvertimenti;

	@XmlVariabile(nome = "DesUOAssegnataria", tipo = TipoVariabile.SEMPLICE)
	private String desUOAssegnataria;
	@XmlVariabile(nome = "DataUltimaAssegnazione", tipo = TipoVariabile.SEMPLICE)
	private String dataUltimaAssegnazione;
	@XmlVariabile(nome = "OrarioUltimaAssegnazione", tipo = TipoVariabile.SEMPLICE)
	private String orarioUltimaAssegnazione;
	@XmlVariabile(nome = "DesUtenteAssegnatario", tipo = TipoVariabile.SEMPLICE)
	private String desUtenteAssegnatario;
	@XmlVariabile(nome = "DataLock", tipo = TipoVariabile.SEMPLICE)
	private String dataUtenteAssegnatario;
	@XmlVariabile(nome = "OrarioLock", tipo = TipoVariabile.SEMPLICE)
	private String orarioUtenteAssegnatario;

	@XmlVariabile(nome = "MsgUltimaAssegnazione", tipo = TipoVariabile.SEMPLICE)
	private String msgUltimaAssegnazione;
	@XmlVariabile(nome = "FlgInviataRisposta", tipo = TipoVariabile.SEMPLICE)
	private String flgInviataRisposta;
	@XmlVariabile(nome = "FlgInoltrata", tipo = TipoVariabile.SEMPLICE)
	private String flgInoltrata;
	@XmlVariabile(nome = "StatoProtocollazione", tipo = TipoVariabile.SEMPLICE)
	private String statoProtocollazione;
	@XmlVariabile(nome = "IdRecDizContrassegno", tipo = TipoVariabile.SEMPLICE)
	private String idRecDizContrassegno;
	@XmlVariabile(nome = "Contrassegno", tipo = TipoVariabile.SEMPLICE)
	private String contrassegno;
	@XmlVariabile(nome = "FlgRicevutaEccezioneInterop", tipo = TipoVariabile.SEMPLICE)
	private String flgRicevutaEccezioneInterop;
	@XmlVariabile(nome = "FlgRicevutaConfermaInterop", tipo = TipoVariabile.SEMPLICE)
	private String flgRicevutaConfermaInterop;
	@XmlVariabile(nome = "FlgRicevutoAggiornamentoInterop", tipo = TipoVariabile.SEMPLICE)
	private String flgRicevutoAggiornamentoInterop;
	@XmlVariabile(nome = "FlgRicevutoAnnRegInterop", tipo = TipoVariabile.SEMPLICE)
	private String flgRicevutoAnnRegInterop;
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "TsRicezione", tipo = TipoVariabile.SEMPLICE)
	private Date tsRicezione;
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "TsInvioCertificato", tipo = TipoVariabile.SEMPLICE)
	private Date tsInvioCertificato;
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "TsInvio", tipo = TipoVariabile.SEMPLICE)
	private Date tsInvio;
	@XmlVariabile(nome = "LivPriorita", tipo = TipoVariabile.SEMPLICE)
	private String livPriorita;
	@XmlVariabile(nome = "FlgRicevutaCBS", tipo = TipoVariabile.SEMPLICE)
	private String flgRicevutaCBS;
	@XmlVariabile(nome = "DestinatariPrincipali", tipo = TipoVariabile.SEMPLICE)
	private String destinatariPrincipali;
	@XmlVariabile(nome = "@DestinatariPrincipali", tipo = TipoVariabile.LISTA)
	private List<DettaglioPostaElettronicaDestinatario> listaDestinatariPrincipali;
	@XmlVariabile(nome = "DestinatariCC", tipo = TipoVariabile.SEMPLICE)
	private String destinatariCC;
	@XmlVariabile(nome = "@DestinatariCC", tipo = TipoVariabile.LISTA)
	private List<DettaglioPostaElettronicaDestinatario> listaDestinatariCC;
	@XmlVariabile(nome = "@DestinatariCCN", tipo = TipoVariabile.LISTA)
	private List<DettaglioPostaElettronicaDestinatario> listaDestinatariCCN;
	@XmlVariabile(nome = "EmailPredecessore.ProgrMsg", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreId;
	@XmlVariabile(nome = "EmailPredecessore.ProgrMsgXDownloadStampa", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreProgMsgDStampa;
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "EmailPredecessore.TsIns ", tipo = TipoVariabile.SEMPLICE)
	private Date emailPredecessoreTsIns;
	@XmlVariabile(nome = "EmailPredecessore.IdEmail", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreIdEmail;
	@XmlVariabile(nome = "EmailPredecessore.MessageId", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreMessageId;
	@XmlVariabile(nome = "EmailPredecessore.FlgIO", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreFlgIO;
	@XmlVariabile(nome = "EmailPredecessore.IconaMicroCategoria", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreIconaMicroCategoria;
	@XmlVariabile(nome = "EmailPredecessore.Categoria", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreCategoria;
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "EmailPredecessore.TsInvio", tipo = TipoVariabile.SEMPLICE)
	private Date emailPredecessoreTsInvio;
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "EmailPredecessore.TsRicezione", tipo = TipoVariabile.SEMPLICE)
	private Date emailPredecessoreTsRicezione;
	@XmlVariabile(nome = "EmailPredecessore.Casella.Account", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreCasellaAccount;
	@XmlVariabile(nome = "EmailPredecessore.Subject", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreSubject;
	@XmlVariabile(nome = "EmailPredecessore.AccountMittente", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreAccountMittente;
	@XmlVariabile(nome = "EmailPredecessore.Tipo", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreTipo;
	@XmlVariabile(nome = "EmailPredecessore.Sottotipo", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreSottotipo;
	@XmlVariabile(nome = "EmailPredecessore.FlgPEC", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreFlgPEC;
	@XmlVariabile(nome = "EmailPredecessore.FlgInteroperabile", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreFlgInteroperabile;
	@XmlVariabile(nome = "EmailPredecessore.DestinatariPrincipali", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreDestinatariPrincipali;
	@XmlVariabile(nome = "EmailPredecessore.DestinatariCC", tipo = TipoVariabile.SEMPLICE)
	private String emailPredecessoreDestinatariCC;

	@XmlVariabile(nome = "@EmailPredecessore.EstremiDocTrasmessi", tipo = TipoVariabile.LISTA)
	private List<DettaglioPostaElettronicaEstremiDoc> listaEmailPredecessoreEstremiDocTrasmessi;
	@XmlVariabile(nome = "@EstremiDocTrasmessi", tipo = TipoVariabile.LISTA)
	private List<DettaglioPostaElettronicaEstremiDoc> listaEstremiDocTrasmessi;
	@XmlVariabile(nome = "@EstremiDocDerivati", tipo = TipoVariabile.LISTA)
	private List<DettaglioPostaElettronicaEstremiDoc> listaEstremiDocDerivati;
	@XmlVariabile(nome = "@Allegati", tipo = TipoVariabile.LISTA)
	private List<DettaglioPostaElettronicaAllegato> listaAllegati;
	@XmlVariabile(nome = "AbilitaStampaFile", tipo = TipoVariabile.SEMPLICE)
	private String abilitaStampaFile;

	@XmlVariabile(nome = "@ItemLavorazione", tipo = TipoVariabile.LISTA)
	private List<ItemLavorazioneMailBean> listaItemLavorazione;
	@XmlVariabile(nome = "@EmailSuccessiveCollegate", tipo = TipoVariabile.LISTA)
	private List<DettaglioPostaElettronicaEmailCollegata> listaEmailSuccessiveCollegate;
	@XmlVariabile(nome = "AbilitaRispondi", tipo = TipoVariabile.SEMPLICE)
	private String abilitaRispondi;
	@XmlVariabile(nome = "AbilitaRispondiATutti", tipo = TipoVariabile.SEMPLICE)
	private String abilitaRispondiATutti;
	@XmlVariabile(nome = "AbilitaInoltraEmail", tipo = TipoVariabile.SEMPLICE)
	private String abilitaInoltraEmail;
	@XmlVariabile(nome = "AbilitaInoltraContenuti", tipo = TipoVariabile.SEMPLICE)
	private String abilitaInoltraContenuti;
	@XmlVariabile(nome = "AbilitaAssegna", tipo = TipoVariabile.SEMPLICE)
	private String abilitaAssegna;
	@XmlVariabile(nome = "AbilitaAssociaProtocollo", tipo = TipoVariabile.SEMPLICE)
	private String abilitaAssociaProtocollo;
	@XmlVariabile(nome = "AbilitaArchivia", tipo = TipoVariabile.SEMPLICE)
	private String abilitaArchivia;
	@XmlVariabile(nome = "AbilitaProtocolla", tipo = TipoVariabile.SEMPLICE)
	private String abilitaProtocolla;
	@XmlVariabile(nome = "AbilitaScaricaEmail", tipo = TipoVariabile.SEMPLICE)
	private String abilitaScaricaEmail;
	@XmlVariabile(nome = "AbilitaScaricaEmailSenzaBustaTrasporto", tipo = TipoVariabile.SEMPLICE)
	private String abilitaScaricaEmailSenzaBustaTrasporto;
	@XmlVariabile(nome = "AbilitaInoltraEmailSbustata", tipo = TipoVariabile.SEMPLICE)
	private String abilitaInoltraEmailSbustata;
	@XmlVariabile(nome = "AbilitaNotifInteropConferma", tipo = TipoVariabile.SEMPLICE)
	private String abilitaNotifInteropConferma;
	@XmlVariabile(nome = "AbilitaNotifInteropEccezione", tipo = TipoVariabile.SEMPLICE)
	private String abilitaNotifInteropEccezione;
	@XmlVariabile(nome = "AbilitaNotifInteropAggiornamento", tipo = TipoVariabile.SEMPLICE)
	private String abilitaNotifInteropAggiornamento;
	@XmlVariabile(nome = "AbilitaNotifInteropAnnullamento", tipo = TipoVariabile.SEMPLICE)
	private String abilitaNotifInteropAnnullamento;
	@XmlVariabile(nome = "AbilitaRegIstanzaAutotutela", tipo = TipoVariabile.SEMPLICE)
	private String abilitaRegIstanzaAutotutela;
	@XmlVariabile(nome = "AbilitaRegIstanzaCED", tipo = TipoVariabile.SEMPLICE)
	private String abilitaRegIstanzaCED;
	@XmlVariabile(nome = "AbilitaProtocollaAccessoAttiSUE", tipo = TipoVariabile.SEMPLICE)
	private String abilitaProtocollaAccessoAttiSUE;
	@XmlVariabile(nome = "IconaStatoConsolidamento", tipo = TipoVariabile.SEMPLICE)
	private String iconaStatoConsolidamento;
	@XmlVariabile(nome = "AbilitaInvio", tipo = TipoVariabile.SEMPLICE)
	private String abilitaInvia;
	@XmlVariabile(nome = "AbilitaInvioCopia", tipo = TipoVariabile.SEMPLICE)
	private String abilitaInvioCopia;
	@XmlVariabile(nome = "AbilitaSetAzioneDaFare", tipo = TipoVariabile.SEMPLICE)
	private String abilitaAzioneDaFare;
	@XmlVariabile(nome = "AbilitaAssociaAInvio", tipo = TipoVariabile.SEMPLICE)
	private String abilitaAssociaAInvio;
	@XmlVariabile(nome = "AzioneDaFare", tipo = TipoVariabile.SEMPLICE)
	private String azioneDaFare;
	@XmlVariabile(nome = "DettAzioneDaFare", tipo = TipoVariabile.SEMPLICE)
	private String dettaglioAzioneDaFare;
	@XmlVariabile(nome = "CodAzioneDaFare", tipo = TipoVariabile.SEMPLICE)
	private String codAzioneDaFare;
	@XmlVariabile(nome = "AbilitaRiapri", tipo = TipoVariabile.SEMPLICE)
	private String abilitaRiapri;
	@XmlVariabile(nome = "ProgrMsgXDownloadStampa", tipo = TipoVariabile.SEMPLICE)
	private String progMsgDownloadStampa;
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "TsIns", tipo = TipoVariabile.SEMPLICE)
	private Date tsInsRegistrazione;

	@XmlVariabile(nome = "@EmailPrecedentiCollegate", tipo = TipoVariabile.LISTA)
	private List<DettaglioPostaElettronicaEmailPrecedente> listaEmailPrecedentiCollegate;

	@XmlVariabile(nome = "IconaStatoInvio", tipo = TipoVariabile.SEMPLICE)
	private String iconaStatoInvio;
	@XmlVariabile(nome = "IconaStatoAccettazione", tipo = TipoVariabile.SEMPLICE)
	private String iconaStatoAccettazione;
	@XmlVariabile(nome = "IconaStatoConsegna", tipo = TipoVariabile.SEMPLICE)
	private String iconaStatoConsegna;
	@XmlVariabile(nome = "StatoInvio", tipo = TipoVariabile.SEMPLICE)
	private String statoInvio;
	@XmlVariabile(nome = "StatoAccettazione", tipo = TipoVariabile.SEMPLICE)
	private String statoAccettazione;
	@XmlVariabile(nome = "StatoConsegna", tipo = TipoVariabile.SEMPLICE)
	private String statoConsegna;

	@XmlVariabile(nome = "AbilitaPresaIncarico", tipo = TipoVariabile.SEMPLICE)
	private String abilitaPresaInCarico;
	@XmlVariabile(nome = "AbilitaMessaInCarico", tipo = TipoVariabile.SEMPLICE)
	private String abilitaMessaInCarico;
	@XmlVariabile(nome = "AbilitaRilascio", tipo = TipoVariabile.SEMPLICE)
	private String abilitaRilascio;
	@XmlVariabile(nome = "AbilitaAnnullamentoInvio", tipo = TipoVariabile.SEMPLICE)
	private String abilitaAnnullamentoInvio;
	@XmlVariabile(nome = "AbilitaRepertoria", tipo = TipoVariabile.SEMPLICE)
	private String abilitaRepertoria;

	@XmlVariabile(nome = "CategoriaRegUD", tipo = TipoVariabile.SEMPLICE)
	private String categoriaRegUD;

	@XmlVariabile(nome = "SiglaRegUD", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegUD;

	@XmlVariabile(nome = "AnnoRegUD", tipo = TipoVariabile.SEMPLICE)
	private String annoRegUD;

	@XmlVariabile(nome = "NumRegUD", tipo = TipoVariabile.SEMPLICE)
	private String numRegUD;

	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "TsRegUD", tipo = TipoVariabile.SEMPLICE)
	private Date tsRegUD;

	@XmlVariabile(nome = "MotivoEccezione", tipo = TipoVariabile.SEMPLICE)
	private String motivoEccezione;

	@XmlVariabile(nome = "AliasAccountMittente", tipo = TipoVariabile.SEMPLICE)
	private String aliasAccountMittente;

	@XmlVariabile(nome = "ProgrBozza", tipo = TipoVariabile.SEMPLICE)
	private String progrBozza;

	@XmlVariabile(nome = "AbilitaSalvaBozza", tipo = TipoVariabile.SEMPLICE)
	private String abilitaSalvaBozza;
	@XmlVariabile(nome = "AbilitaSalvaItemLav", tipo = TipoVariabile.SEMPLICE)
	private String abilitaSalvaItemLav;

	@XmlVariabile(nome = "@IdEmailInoltrate", tipo = TipoVariabile.LISTA)
	private List<IdMailInoltrataMailXmlBean> idEmailInoltrate;
	
	@XmlVariabile(nome = "FlgMailPredecessoreStatoLavAperta", tipo = TipoVariabile.SEMPLICE)
	private String flgMailPredecessoreStatoLavAperta;
	@XmlVariabile(nome = "FlgMailPredecessoreConAzioneDaFare", tipo = TipoVariabile.SEMPLICE)
	private String flgMailPredecessoreConAzioneDaFare;
	@XmlVariabile(nome = "RuoloVsPredecessore", tipo = TipoVariabile.SEMPLICE)
	private String ruoloVsPredecessore; 
	@XmlVariabile(nome = "NroMailPredecessore", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal nroMailPredecessore; 
	@XmlVariabile(nome = "MailPredecessoriUnicaAzioneDaFare", tipo = TipoVariabile.SEMPLICE)
	private String mailPredecessoriUnicaAzioneDaFare; 
	
	@XmlVariabile(nome = "MsgErrMancataAccettazione", tipo = TipoVariabile.SEMPLICE)
	private String msgErrMancataAccettazione;
	@XmlVariabile(nome = "MsgErrMancataConsegna", tipo = TipoVariabile.SEMPLICE)
	private String msgErrMancataConsegna;
	@XmlVariabile(nome = "MsgErrRicevutaPEC", tipo = TipoVariabile.SEMPLICE)
	private String msgErrRicevutaPEC;
	
	/**
	 * Flg 1/0: Modalità gestione ricevute pec 
	 * 1 attiva 
	 * 0 non attiva 
	 */
	@XmlVariabile(nome = "FlgURIRicevuta", tipo = TipoVariabile.SEMPLICE)
	private String flgURIRicevuta; 
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<DettaglioPostaElettronicaEstremiDoc> getListaEstremiDocTrasmessi() {
		return listaEstremiDocTrasmessi;
	}

	public void setListaEstremiDocTrasmessi(List<DettaglioPostaElettronicaEstremiDoc> listaEstremiDocTrasmessi) {
		this.listaEstremiDocTrasmessi = listaEstremiDocTrasmessi;
	}

	public List<DettaglioPostaElettronicaEstremiDoc> getListaEstremiDocDerivati() {
		return listaEstremiDocDerivati;
	}

	public void setListaEstremiDocDerivati(List<DettaglioPostaElettronicaEstremiDoc> listaEstremiDocDerivati) {
		this.listaEstremiDocDerivati = listaEstremiDocDerivati;
	}

	public List<DettaglioPostaElettronicaAllegato> getListaAllegati() {
		return listaAllegati;
	}

	public void setListaAllegati(List<DettaglioPostaElettronicaAllegato> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}

	public List<ItemLavorazioneMailBean> getListaItemLavorazione() {
		return listaItemLavorazione;
	}

	public void setListaItemLavorazione(List<ItemLavorazioneMailBean> listaItemLavorazione) {
		this.listaItemLavorazione = listaItemLavorazione;
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

	public String getEnteRegMitt() {
		return enteRegMitt;
	}

	public void setEnteRegMitt(String enteRegMitt) {
		this.enteRegMitt = enteRegMitt;
	}

	public String getSottotipo() {
		return sottotipo;
	}

	public void setSottotipo(String sottotipo) {
		this.sottotipo = sottotipo;
	}

	public String getEmailPredecessoreSottotipo() {
		return emailPredecessoreSottotipo;
	}

	public void setEmailPredecessoreSottotipo(String emailPredecessoreSottotipo) {
		this.emailPredecessoreSottotipo = emailPredecessoreSottotipo;
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

	public String getAbilitaInvia() {
		return abilitaInvia;
	}

	public void setAbilitaInvia(String abilitaInvia) {
		this.abilitaInvia = abilitaInvia;
	}

	public String getAbilitaInvioCopia() {
		return abilitaInvioCopia;
	}

	public void setAbilitaInvioCopia(String abilitaInvioCopia) {
		this.abilitaInvioCopia = abilitaInvioCopia;
	}

	public String getAbilitaAzioneDaFare() {
		return abilitaAzioneDaFare;
	}

	public void setAbilitaAzioneDaFare(String abilitaAzioneDaFare) {
		this.abilitaAzioneDaFare = abilitaAzioneDaFare;
	}

	public String getAzioneDaFare() {
		return azioneDaFare;
	}

	public void setAzioneDaFare(String azioneDaFare) {
		this.azioneDaFare = azioneDaFare;
	}

	public String getDettaglioAzioneDaFare() {
		return dettaglioAzioneDaFare;
	}

	public void setDettaglioAzioneDaFare(String dettaglioAzioneDaFare) {
		this.dettaglioAzioneDaFare = dettaglioAzioneDaFare;
	}

	public String getCodAzioneDaFare() {
		return codAzioneDaFare;
	}

	public void setCodAzioneDaFare(String codAzioneDaFare) {
		this.codAzioneDaFare = codAzioneDaFare;
	}

	public String getAbilitaRiapri() {
		return abilitaRiapri;
	}

	public void setAbilitaRiapri(String abilitaRiapri) {
		this.abilitaRiapri = abilitaRiapri;
	}

	public String getProgMsgDownloadStampa() {
		return progMsgDownloadStampa;
	}

	public void setProgMsgDownloadStampa(String progMsgDownloadStampa) {
		this.progMsgDownloadStampa = progMsgDownloadStampa;
	}

	public Date getTsInsRegistrazione() {
		return tsInsRegistrazione;
	}

	public void setTsInsRegistrazione(Date tsInsRegistrazione) {
		this.tsInsRegistrazione = tsInsRegistrazione;
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

	public List<DettaglioPostaElettronicaEmailPrecedente> getListaEmailPrecedentiCollegate() {
		return listaEmailPrecedentiCollegate;
	}

	public void setListaEmailPrecedentiCollegate(List<DettaglioPostaElettronicaEmailPrecedente> listaEmailPrecedentiCollegate) {
		this.listaEmailPrecedentiCollegate = listaEmailPrecedentiCollegate;
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

	public String getDataUtenteAssegnatario() {
		return dataUtenteAssegnatario;
	}

	public void setDataUtenteAssegnatario(String dataUtenteAssegnatario) {
		this.dataUtenteAssegnatario = dataUtenteAssegnatario;
	}

	public String getOrarioUtenteAssegnatario() {
		return orarioUtenteAssegnatario;
	}

	public void setOrarioUtenteAssegnatario(String orarioUtenteAssegnatario) {
		this.orarioUtenteAssegnatario = orarioUtenteAssegnatario;
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

	public String getAbilitaAssociaAInvio() {
		return abilitaAssociaAInvio;
	}

	public void setAbilitaAssociaAInvio(String abilitaAssociaAInvio) {
		this.abilitaAssociaAInvio = abilitaAssociaAInvio;
	}

	public String getCategoriaRegUD() {
		return categoriaRegUD;
	}

	public void setCategoriaRegUD(String categoriaRegUD) {
		this.categoriaRegUD = categoriaRegUD;
	}

	public String getSiglaRegUD() {
		return siglaRegUD;
	}

	public void setSiglaRegUD(String siglaRegUD) {
		this.siglaRegUD = siglaRegUD;
	}

	public String getAnnoRegUD() {
		return annoRegUD;
	}

	public void setAnnoRegUD(String annoRegUD) {
		this.annoRegUD = annoRegUD;
	}

	public String getNumRegUD() {
		return numRegUD;
	}

	public void setNumRegUD(String numRegUD) {
		this.numRegUD = numRegUD;
	}

	public Date getTsRegUD() {
		return tsRegUD;
	}

	public void setTsRegUD(Date tsRegUD) {
		this.tsRegUD = tsRegUD;
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

	/**
	 * @return the flgAbilitaAnnullamentoInvio
	 */
	public String getAbilitaAnnullamentoInvio() {
		return abilitaAnnullamentoInvio;
	}

	/**
	 * @param flgAbilitaAnnullamentoInvio
	 *            the flgAbilitaAnnullamentoInvio to set
	 */
	public void setAbilitaAnnullamentoInvio(String abilitaAnnullamentoInvio) {
		this.abilitaAnnullamentoInvio = abilitaAnnullamentoInvio;
	}

	/**
	 * @return the avvertimenti
	 */
	public String getAvvertimenti() {
		return avvertimenti;
	}

	/**
	 * @param avvertimenti
	 *            the avvertimenti to set
	 */
	public void setAvvertimenti(String avvertimenti) {
		this.avvertimenti = avvertimenti;
	}

	/**
	 * @return the progrBozza
	 */
	public String getProgrBozza() {
		return progrBozza;
	}

	/**
	 * @param progrBozza
	 *            the progrBozza to set
	 */
	public void setProgrBozza(String progrBozza) {
		this.progrBozza = progrBozza;
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

	/**
	 * @return the flgRichConfermaLettura
	 */
	public String getFlgRichConfermaLettura() {
		return flgRichConfermaLettura;
	}

	/**
	 * @param flgRichConfermaLettura
	 *            the flgRichConfermaLettura to set
	 */
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

	public List<IdMailInoltrataMailXmlBean> getIdEmailInoltrate() {
		return idEmailInoltrate;
	}

	public void setIdEmailInoltrate(List<IdMailInoltrataMailXmlBean> idEmailInoltrate) {
		this.idEmailInoltrate = idEmailInoltrate;
	}

	public String getAbilitaStampaFile() {
		return abilitaStampaFile;
	}

	public void setAbilitaStampaFile(String abilitaStampaFile) {
		this.abilitaStampaFile = abilitaStampaFile;
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
	
}