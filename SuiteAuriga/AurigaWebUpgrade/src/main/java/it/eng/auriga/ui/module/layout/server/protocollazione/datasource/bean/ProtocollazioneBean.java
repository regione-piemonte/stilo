/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.core.business.beans.AbstractBean;
import it.eng.document.function.bean.DestInvioCCProcessoBean;
import it.eng.document.function.bean.FileCompletiXAttiBean;
import it.eng.document.function.bean.NominativiFirmaOlografaBean;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class ProtocollazioneBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * UUID Univoco per evitare nuova doppia protocollazione
	 */
	private String idOperRegistrazione;
	
	private String flgTipoProv;
	private String flgVersoBozza;
	// ************* NUMERAZIONE PRINCIPALE *************
	private BigDecimal idUd;	
	private BigDecimal idUdNuovoComeCopia;
	private String prefKeyModello;
	private String prefNameModello;
	private String useridModello;
	private String idUoModello;	
	private String flgTipoDocDiverso;
	private String idTipoDocDaCopiare;		
	private String codStatoDett;
	private String codStato;
	private String stato;
	private String idProcess;
	private String estremiProcess;
	private String ruoloSmistamentoAtto;
	private String tipoProtocollo;
	private String registroProtocollo;
	private String codCategoriaProtocollo;
	private String siglaProtocollo;
	private String annoProtocollo;
	private BigDecimal nroProtocollo;
	private String subProtocollo;
	private Date dataProtocollo;
	private String societa;
	private String desUserProtocollo;
	private String desUOProtocollo;
	private String idUdAttoAutAnnProtocollo;
	private Boolean annullata;
	private String datiAnnullamento;
	private Boolean richAnnullamento;
	private String datiRichAnnullamento;
	private String motiviRichAnnullamento;
	private String presenzaDocCollegati;
	private List<DocCollegatoBean> listaDocumentiDaCollegare;
	private Boolean derivaDaRdA;
	// **************************************************
	private Boolean flgMulta;
	private String statoTrasferimentoBloomfleet;
	private String dettaglioTrasferimentoBloomfleet;
	private Boolean flgDocContratti;
	private String codContratti;
	private String flgFirmeCompilateContratti;
	private Boolean flgDocContrattiBarcode;
	// **************************************************
	private String estremiRepertorioPerStruttura;
	private String idUdLiquidazione;
	private String estremiAttoLiquidazione;
	private String rifOrigineProtRicevuto;
	private String nroProtRicevuto;
	private String annoProtRicevuto;
	private Date dataProtRicevuto;
	private Date dataEOraArrivo;
	private Date dataEOraSpedizione;
	private String codRapidoOggetto;
	private String oggetto;
	private BigDecimal livelloRiservatezza;
	private String descrizioneRiservatezza;
	private Date dataRiservatezza;
	private String prioritaRiservatezza;
	private String descrizionePrioritaRiservatezza;
	private Boolean flgRispostaUrgente;
	private String siglaDocRiferimento;
	private String annoDocRiferimento;
	private BigDecimal nroDocRiferimento;
	private Boolean flgRichAccCivSemplice;
	private Boolean flgRichAccCivGeneralizzato;
	// ************* FILE PRIMARIO *************
	private BigDecimal idDocPrimario;
	private File filePrimario;
	private String percorsoFilePrimari;
	private String nomeFilePrimario;
	private String uriFilePrimario;
	private Date tsInsLastVerFilePrimario;
	private Boolean remoteUriFilePrimario;
	private String idAttachEmailPrimario;
	private MimeTypeFirmaBean infoFile;
	private Boolean isDocPrimarioChanged;
	private String mimetypeVerPreFirma;
	private String uriFileVerPreFirma;
	private String nomeFileVerPreFirma;
	private String flgConvertibilePdfVerPreFirma;
	private String improntaVerPreFirma;
	private MimeTypeFirmaBean infoFileVerPreFirma;
	private Boolean flgNoPubblPrimario;
	private Boolean flgDatiSensibili;
	private String idUDScansione;
	// Dettaglio doc. contratti con barcode
	private String tipoBarcodePrimario;
	private String barcodePrimario;
	private String energiaGasPrimario;
	private String tipoSezioneContrattoPrimario;
	private String codContrattoPrimario;
	private String flgPresentiFirmeContrattoPrimario;
	private String flgFirmeCompleteContrattoPrimario;
	private String nroFirmePrevisteContrattoPrimario;
	private String nroFirmeCompilateContrattoPrimario;
	private String flgIncertezzaRilevazioneFirmePrimario;
	private List<NominativiFirmaOlografaBean> listaNominativiFirmaOlografa;
	private Boolean flgSostituisciVerPrec;
	private String nroLastVer;
	private Boolean flgOriginaleCartaceo;
	private Boolean flgCopiaSostitutiva;
	private Boolean flgTimbratoFilePostReg;
	private Boolean flgTimbraFilePostReg;
	private OpzioniTimbroDocBean opzioniTimbro;
	private String tipoModFilePrimario;
	private List<AllegatoProtocolloBean> listaFilePrimarioVerPubbl;
	private String esitoInvioACTASerieAttiIntegrali;
	private String esitoInvioACTASeriePubbl;
	// ************* VERS. CON OMISSIS *************
	private DocumentBean filePrimarioOmissis;
//	private BigDecimal idDocOmissis;
//	private String nomeFileOmissis;
//	private String uriFileOmissis;
//	private Boolean remoteUriOmissis;
//	private MimeTypeFirmaBean infoFileOmissis;
//	private Boolean isChangedOmissis;
//	private String mimetypeVerPreFirmaOmissis;
//	private String uriFileVerPreFirmaOmissis;
//	private String nomeFileVerPreFirmaOmissis;
//	private String flgConvertibilePdfVerPreFirmaOmissis;
//	private String improntaVerPreFirmaOmissis;
//	private MimeTypeFirmaBean infoFileVerPreFirmaOmissis;
//	private Boolean flgSostituisciVerPrecOmissis;
//	private String nroUltimaVersioneOmissis;
//	private Boolean flgTimbratoFilePostRegOmissis;
//	private Boolean flgTimbraFilePostRegOmissis;
//	private OpzioniTimbroDocBean opzioniTimbroOmissis;
	// *********************************************
	private String docPressoCentroPosta;
	private List<AllegatoProtocolloBean> listaAllegati;
	private String percorsoFileAllegati;	
	private List<AllegatoProtocolloBean> listaDocProcFolder;
	private List<FileCompletiXAttiBean> listaFileCompletiXAtti;
	private String idToponimo;
	private String descrizioneCollocazioneFisica;
	private Date dataDocumento;
	private Date dataSpedizioneCartaceo;
	private Date dataArrivoProtocollo;
	private Boolean dataArrivoProtocolloEditabile;
	private String tipoDocumentoSalvato;
	private String tipoDocumento;
	private String nomeTipoDocumento;
	private String flgRichiestaFirmaDigitale;
	private String codCategoriaAltraNumerazione;
	private String siglaAltraNumerazione;
	private String altreNumerazioniUD;
	private String rowidDoc;
	private String note;
	private String noteMancanzaFile;	
	private String puntoProtAssegnatario;
	private Boolean flgPresentiAssPreselMitt;
	private List<AssPreselMittBean> listaAssPreselMitt;
	private List<DestinatarioProtBean> listaDestinatari;
	private List<AssegnazioneBean> listaAssegnazioni;
	private List<AssegnazioneBean> listaAssegnazioniSalvate;
	private List<AssegnazioneBean> listaDestinatariUoProtocollazione;
	private List<DestInvioCCBean> listaDestInvioCC;
	private List<DestInvioCCBean> listaDestInvioCCSalvati;
	private List<DestInvioCCBean> listaUoCoinvolte;
	private List<ClassificazioneFascicoloBean> listaClassFasc;
	private List<FolderCustomBean> listaFolderCustom;
	private List<MittenteProtBean> listaMittenti;
	private List<TipoNumerazioneBean> listaTipoNumerazione;
	private String rifRegEmergenza;
	private BigDecimal nroRegEmergenza;
	private Date dataRegEmergenza;
	private String idUserRegEmergenza;
	private String idUoRegEmergenza;
	private String mezzoTrasmissione;
	private String nroRaccomandata;
	private Date dataRaccomandata;
	private String nroListaRaccomandata;
	private String nomeCollocazioneFisica;
	private String codRapidoCollocazioneFisica;	
	// ************* NUMERAZIONE SECONDARIA *************
	private String siglaNumerazioneSecondaria;
	private String annoNumerazioneSecondaria;
	private String tipoNumerazioneSecondaria;
	private BigDecimal numeroNumerazioneSecondaria;
	private String subNumerazioneSecondaria;
	private Date dataRegistrazioneNumerazioneSecondaria;
	private String idUdAttoAutAnnRegSecondaria;
	private Date dtEsecutivita;
	private Boolean flgImmediatamenteEseg;
	// ************* ABILITAZIONI *************
	private Boolean abilRevocaAtto;
	private Boolean abilSmistaPropAtto;
	private Boolean abilPresaInCaricoPropAtto;
	private Boolean abilSottoscrizioneCons;
	private Boolean abilRimuoviSottoscrizioneCons;
	private Boolean abilPresentazionePropAtto;
	private Boolean abilRitiroPropAtto;
	private Boolean abilAvviaEmendamento;
	private Boolean abilAnnullaPropostaAtto;
	private Boolean abilRilascioVisto;
	private Boolean abilRifiutoVisto;
	private Boolean abilProtocollazioneEntrata;
	private Boolean abilProtocollazioneUscita;
	private Boolean abilProtocollazioneInterna;
	private Boolean abilAssegnazioneSmistamento;
	private Boolean abilModAssInviiCC;
	private Boolean abilSetVisionato;
	private Boolean abilSmista;
	private Boolean abilSmistaCC;
	private Boolean abilCondivisione;
	private Boolean abilClassificazioneFascicolazione;
	private Boolean abilOrganizza;
	private Boolean abilModificaDatiRegistrazione;
	private Boolean abilModificaDati;
	private Boolean abilAvvioIterWF;
	private Boolean abilAggiuntaFile;
	private Boolean abilRegAccessoCivico;
	private Boolean abilInvioPEC;
	private Boolean flgInvioPECMulti;
	private Boolean abilInvioPEO;
	private Boolean abilRichAnnullamentoReg;
	private Boolean abilModificaRichAnnullamentoReg;
	private Boolean abilEliminaRichAnnullamentoReg;
	private Boolean abilAnnullamentoReg;
	private Boolean abilPresaInCarico;
	private Boolean abilRestituzione;
	private Boolean abilInvioConferma;
	private Boolean abilInvioAggiornamento;
	private Boolean abilInvioAnnullamento;
	private Boolean abilArchiviazione;
	private Boolean abilOsservazioniNotifiche;
	private Boolean abilFirma;
	private Boolean abilFirmaProtocolla;
	private Boolean abilVistoElettronico;
	private Boolean abilStampaCopertina;
	private Boolean abilStampaRicevuta;
	private Boolean abilRispondi;
	private Boolean abilRispondiUscita;
	private Boolean abilStampaEtichetta;
	private Boolean abilNuovoComeCopia;
	private Boolean abilModificaTipologia;	
	private Boolean abilModificaCodAttoCMTO;
	private Boolean abilInviaALibroFirmaVistoRegCont;
	private Boolean abilImpostaStatoAlVistoRegCont;
	private Boolean abilTogliaDaLibroFirmaVistoRegCont;
	private Boolean abilInvioEmailRicevuta;
	private Boolean abilProrogaPubblicazione;
	private Boolean abilAnnullamentoPubblicazione;
	private Boolean abilRettificaPubblicazione;
	private Boolean abilModificaDatiExtraIter;
	private Boolean abilModificaOpereAtto;
	private Boolean abilModificaDatiPubblAtto;
	private Boolean abilModificaSostDirRegTecnicaPropAtto;
	private Boolean abilModificaTermineSottoscrizionePropAtto;
	private Boolean abilPubblicazioneTraspAmm;
	private Boolean abilGestioneCollegamentiUD;
	
	
	// ****************************************
	private Boolean conDatiAnnullati;
	private String listaDatiAnnullati;
	private String indirizzo;
	private Boolean flgPEC;
	private Boolean flgCasellaIstituzionale;
	private Boolean flgDichIPA;
	private String gestorePEC;
	private String uoProtocollante;
	private String motivoVarDatiReg;
	private String idEmailArrivo;
	private String casellaEmailDestinatario;
	private Boolean casellaIsPEC;
	private Boolean emailArrivoInteroperabile;
	private Boolean emailInviataFlgPEC;
	private Boolean emailInviataFlgPEO;		
	private Boolean inviataMailInteroperabile;
	// ************* CONFERMA ASSEGNAZIONE *************
	private String idUserConfermaAssegnazione;
	private String desUserConfermaAssegnazione;
	private String usernameConfermaAssegnazione;
	// *************************************************
	private Boolean ricEccezioniInterop;
	private Boolean ricAggiornamentiInterop;
	private Boolean ricAnnullamentiInterop;
	private String segnatura;
	// ************* ALTRE NUMERAZIONI *************
	private Boolean isRepertorio;
	private Boolean isRegistroFatture;
	private Boolean flgAncheRepertorio;	
	private String repertorio;
	private Boolean annoPassato;
	private Boolean protocolloGenerale;
	private String registroFatture;
	private String regNumSecondariaDesGruppo;
	// ************* COMPILAZIONE MODULO *************
	private Boolean flgCompilazioneModulo;
	// ************* ITER ATTI *************
	private Boolean flgPropostaAtto;
	private String idUoProponente;
	private String desDirProponente;
	private Date dataAvvioIterAtto;
	private String funzionarioIstruttoreAtto;
	private String responsabileAtto;
	// private String direttoreFirmatarioAtto;
	private Date dataFirmaAtto;
	private String funzionarioFirmaAtto;
	private Date dataPubblicazioneAtto;
	private String logIterAtto;
	private String modificatoDispositivoAtto;
	// ************* RESP PROCEDIMENTO *************
	private String idUserRespProc;
	private String desUserRespProc;
	// ************* PROPOSTA ATTO_2 MILANO *************
	private String sceltaIter;
	private Date dataAvvioAtto;
	private Boolean flgRichParereRevisori;
	private String siglaAttoRifSubImpegno;
	private String numeroAttoRifSubImpegno;
	private String annoAttoRifSubImpegno;
	private Date dataAttoRifSubImpegno;	
	// ************* PROPOSTA REVISIONE ORGANIGRAMMA *************	
	private String idUoRevisioneOrganigramma;
	private String idUoPadreRevisioneOrganigramma;
	private String codRapidoUoRevisioneOrganigramma;
	private String nomeUoRevisioneOrganigramma;
	private String tipoUoRevisioneOrganigramma;
	private String livelloUoRevisioneOrganigramma;
	// ************* PROTOCOLLO MILANO X SOSTITUZIONE PG@WEB *************
	private Boolean flgSkipControlliCartaceo;	
	private String supportoOriginale;	
	private List<SoggEsternoProtBean> listaEsibenti;
	private List<SoggEsternoProtBean> listaInteressati;	
	private List<SoggEsternoProtBean> listaDelegato;
	private List<SoggEsternoProtBean> listaFirmatari;
	private List<AltraViaProtBean> listaAltreVie;
	private List<DocCollegatoBean> listaDocumentiCollegati;
	private List<AltroRifBean> listaAltriRiferimenti;
	private String siglaProtocolloPregresso;
	private String nroProtocolloPregresso;
	private String annoProtocolloPregresso;
	private Date dataProtocolloPregresso;
	private String subProtocolloPregresso;
	// private String siglaIdentificativoWF;
	private String nroIdentificativoWF;
	private String annoIdentificativoWF;
	private Boolean flgCaricamentoPGPregressoDaGUI;
	private Boolean flgTipoDocConVie;
	private Boolean flgOggettoNonObblig;
	private String flgTipoProtModulo;
	private String codSupportoOrig;
	// ************* INSTANZE CED/AUTOTUTELA *************
	private List<ContribuenteBean> listaContribuenti;
	private Date dataEOraRicezione;
	// ***************************************************
	private Map<String, Object> valori;
	private Map<String, String> tipiValori;
	// ***************************************************
	private Boolean attivaTimbroTipologia;
	private Boolean isFilePrimarioPubblicato;
	private String defaultAzioneStampaEtichetta;
	// ************* RICHIESTA ACCESSO ATTI *************
	private Boolean flgRichiestaAccessoAtti;
	private String tipoRichiedente;
	private String codStatoRichAccessoAtti;
	private String desStatoRichAccessoAtti;
	private String siglaPraticaSuSistUfficioRichiedente;
	private String numeroPraticaSuSistUfficioRichiedente;
	private String annoPraticaSuSistUfficioRichiedente;
	private List<MittenteProtBean> listaRichiedentiInterni;
	private Boolean flgRichAttiFabbricaVisuraSue;
	private Boolean flgRichModificheVisuraSue;
	private List<AttiRichiestiBean> listaAttiRichiesti;
	private Boolean flgAltriAttiDaRicercareVisuraSue;
	private List<SoggEsternoProtBean> listaRichiedentiDelegati;
	private String motivoRichiestaAccessoAtti;
	private String dettagliRichiestaAccessoAtti;	
	private String idIncaricatoPrelievoPerUffRichiedente;
	private String usernameIncaricatoPrelievoPerUffRichiedente;
	private String codRapidoIncaricatoPrelievoPerUffRichiedente;
	private String cognomeIncaricatoPrelievoPerUffRichiedente;
	private String nomeIncaricatoPrelievoPerUffRichiedente;
	private String telefonoIncaricatoPrelievoPerUffRichiedente;	
	private String cognomeIncaricatoPrelievoPerRichEsterno;
	private String nomeIncaricatoPrelievoPerRichEsterno;
	private String codiceFiscaleIncaricatoPrelievoPerRichEsterno;
	private String emailIncaricatoPrelievoPerRichEsterno;
	private String telefonoIncaricatoPrelievoPerRichEsterno;
	private String dataAppuntamento;
	private String orarioAppuntamento;
	private String provenienzaAppuntamento;
	private Date dataPrelievo;
	private String dataRestituzionePrelievo;
	private String restituzionePrelievoAttestataDa;
	private String idNodoDefaultRicercaAtti;
	private String idEmailProv;
	private Boolean flgNonArchiviareEmail;
	private Boolean abilVisualizzaDettStdProt;	
	private Boolean abilInvioInApprovazione;
	private Boolean abilApprovazione;
	private Boolean abilInvioEsitoVerificaArchivio;
	private Boolean abilAbilitaAppuntamentoDaAgenda;
	private Boolean abilSetAppuntamento;
	private Boolean abilAnnullamentoAppuntamento;
	private Boolean abilRegistraPrelievo;
	private Boolean abilRegistraRestituzione;
	private Boolean abilAnnullamento;
	private Boolean abilStampaFoglioPrelievo;
	private Boolean abilRichAccessoAttiChiusura;
	private Boolean abilRichAccessoAttiRiapertura;
	private Boolean abilRichAccessoAttiRipristino;		
	private String idRicercatoreVisuraSUE;
	// ************* DECOMPRESSIONE ARCHIVI *************
	private List<String> erroriArchivi;	
	// ************* ESTREMI UD TRASMESSO IN USCITA CON *************
	private String idUDTrasmessaInUscitaCon;
	private String estremiUDTrasmessoInUscitaCon;
	// **************************************************************
	private List<TipoNumerazioneBean> listaTipiNumerazioneDaDare;
	// ************* GESTIONE ESTENSIONE ALLEGATI *************
	private List<String> erroriEstensioniFile;
	// ************* ERRORI NEL SALVATAGGIO DEI FILE *************	
	private Map<String, String> fileInErrors;		
	// ************* RISPOSTA PROTO/REPERTORIO *************
	private String codCategoriaRegPrimariaRisposta;
	private String siglaRegistroRegPrimariaRisposta;
	private String desRegistroRegPrimariaRisposta;
	private String categoriaRepertorioRegPrimariaRisposta;
	private String codCategoriaRegSecondariaRisposta;
	private String siglaRegistroRegSecondariaRisposta;
	private String desRegistroRegSecondariaRisposta;
	private String categoriaRepertorioRegSecondariaRisposta;
	// ************* SMISTAMENTO ATTI *************
	private String idGruppoLiquidatori;
	private String nomeGruppoLiquidatori;
	private String codGruppoLiquidatori;
	private String idAssegnatarioProcesso;
	private String desAssegnatarioProcesso;
	private String nriLivelliAssegnatarioProcesso;
	private String idGruppoRagioneria;
	private String nomeGruppoRagioneria;
	private String codGruppoRagioneria;
	// ************* CONDIVISIONE ATTI *************
	private List<DestInvioCCProcessoBean> destinatariInvioCCProcesso;	
	// ************* PERIZIE ADSP *************
	private List<PeriziaBean> listaPerizie;
	// ************* AVVIO ITER WF RISPOSTA **************
	private String idProcessTypeIterWFRisposta;
	private String nomeProcessTypeIterWFRisposta;
	private String nomeTipoFlussoIterWFRisposta;
	private String idDocTypeIterWFRisposta;
	private String nomeDocTypeIterWFRisposta;
	private String codCategoriaNumIniIterWFRisposta;
	private String siglaNumIniIterWFRisposta;
	// ******* POSTEL ****	
	private String gruppoProtocollantePostelMittente;
	private String tipoToponimoMittentePostel;
	private String toponimoMittentePostel;
	private String civicoMittentePostel;
	private String comuneMittentePostel;
	private String provinciaMittentePostel;
	private String capMittentePostel;
	private String modalitaInvio;
	// ************* PUBBLICAZIONE E RI-PUBBLICAZIONE *************
	private Boolean flgPresenzaPubblicazioni;
	private String idPubblicazione;
	private String nroPubblicazione;
	private Date dataPubblicazione;
	private Date dataDalPubblicazione;
	private Date dataAlPubblicazione;
	private String richiestaDaPubblicazione;
	private String statoPubblicazione;
	private String formaPubblicazione;
	private String rettificataDaPubblicazione;
	private String motivoRettificaPubblicazione;
	private String motivoAnnullamentoPubblicazione;	
	private String proroghePubblicazione;
	private String idRipubblicazione;
	private String nroRipubblicazione;
	private Date dataRipubblicazione;
	private Date dataDalRipubblicazione;
	private Date dataAlRipubblicazione;
	private String richiestaDaRipubblicazione;
	private String statoRipubblicazione;
	private String formaRipubblicazione;
	private String rettificataDaRipubblicazione;	
	private String motivoRettificaRipubblicazione;
	private String motivoAnnullamentoRipubblicazione;	
	private String prorogheRipubblicazione;
	// **************R********************
	private String warningMsgDoppiaReg;
	// **************REGISTRO ACCESSO CIVICO*********************
	private String flgPresentiControinteressati;
	private List<ControinteressatoBean> listaControinteressati;
	// **************INFO ALLEGATO PARTE INTEGRANTE****************
	private Boolean infoXAllegFlgPrimarioAtto; 
	private Boolean infoXAllegShowParteIntegrante; 
	private Boolean infoXAllegDefaultParteIntegrante;
	private Boolean infoXAllegShowEscludiPubblicazione;
	private Boolean infoXAllegDefaultEscludiPubblicazione;
	private Boolean infoXAllegShowSeparato;
	private Boolean infoXAllegDefaultSeparato;
	private Boolean infoXAllegShowVersOmissis;
	// **************IMPORTAZIONE ALLEGATI SELETTIVA****************
	private Boolean flgAttivaCtrlAllegatiXImportUnitaDoc;
	// **************ERRORI SUI FILE****************
	private HashMap<String, String> erroriFile;
	
	// **************Controllo per evitare salvataggi con dati/file non aggiornati (correttiva)****************
	private String timestampGetData;
	
	public String getIdOperRegistrazione() {
		return idOperRegistrazione;
	}
	public void setIdOperRegistrazione(String idOperRegistrazione) {
		this.idOperRegistrazione = idOperRegistrazione;
	}
	
	public String getFlgTipoProv() {
		return flgTipoProv;
	}
	public void setFlgTipoProv(String flgTipoProv) {
		this.flgTipoProv = flgTipoProv;
	}
	public String getFlgVersoBozza() {
		return flgVersoBozza;
	}
	public void setFlgVersoBozza(String flgVersoBozza) {
		this.flgVersoBozza = flgVersoBozza;
	}
	public BigDecimal getIdUd() {
		return idUd;
	}
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	public BigDecimal getIdUdNuovoComeCopia() {
		return idUdNuovoComeCopia;
	}
	public void setIdUdNuovoComeCopia(BigDecimal idUdNuovoComeCopia) {
		this.idUdNuovoComeCopia = idUdNuovoComeCopia;
	}
	public String getPrefKeyModello() {
		return prefKeyModello;
	}
	public void setPrefKeyModello(String prefKeyModello) {
		this.prefKeyModello = prefKeyModello;
	}
	public String getPrefNameModello() {
		return prefNameModello;
	}
	public void setPrefNameModello(String prefNameModello) {
		this.prefNameModello = prefNameModello;
	}
	public String getUseridModello() {
		return useridModello;
	}
	public void setUseridModello(String useridModello) {
		this.useridModello = useridModello;
	}
	public String getIdUoModello() {
		return idUoModello;
	}
	public void setIdUoModello(String idUoModello) {
		this.idUoModello = idUoModello;
	}
	public String getFlgTipoDocDiverso() {
		return flgTipoDocDiverso;
	}
	public void setFlgTipoDocDiverso(String flgTipoDocDiverso) {
		this.flgTipoDocDiverso = flgTipoDocDiverso;
	}
	public String getIdTipoDocDaCopiare() {
		return idTipoDocDaCopiare;
	}
	public void setIdTipoDocDaCopiare(String idTipoDocDaCopiare) {
		this.idTipoDocDaCopiare = idTipoDocDaCopiare;
	}
	public String getCodStatoDett() {
		return codStatoDett;
	}
	public void setCodStatoDett(String codStatoDett) {
		this.codStatoDett = codStatoDett;
	}
	public String getCodStato() {
		return codStato;
	}
	public void setCodStato(String codStato) {
		this.codStato = codStato;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getIdProcess() {
		return idProcess;
	}
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	public String getEstremiProcess() {
		return estremiProcess;
	}
	public void setEstremiProcess(String estremiProcess) {
		this.estremiProcess = estremiProcess;
	}
	public String getRuoloSmistamentoAtto() {
		return ruoloSmistamentoAtto;
	}
	public void setRuoloSmistamentoAtto(String ruoloSmistamentoAtto) {
		this.ruoloSmistamentoAtto = ruoloSmistamentoAtto;
	}
	public String getTipoProtocollo() {
		return tipoProtocollo;
	}
	public void setTipoProtocollo(String tipoProtocollo) {
		this.tipoProtocollo = tipoProtocollo;
	}
	public String getRegistroProtocollo() {
		return registroProtocollo;
	}
	public void setRegistroProtocollo(String registroProtocollo) {
		this.registroProtocollo = registroProtocollo;
	}
	public String getCodCategoriaProtocollo() {
		return codCategoriaProtocollo;
	}
	public void setCodCategoriaProtocollo(String codCategoriaProtocollo) {
		this.codCategoriaProtocollo = codCategoriaProtocollo;
	}
	public String getSiglaProtocollo() {
		return siglaProtocollo;
	}
	public void setSiglaProtocollo(String siglaProtocollo) {
		this.siglaProtocollo = siglaProtocollo;
	}
	public String getAnnoProtocollo() {
		return annoProtocollo;
	}
	public void setAnnoProtocollo(String annoProtocollo) {
		this.annoProtocollo = annoProtocollo;
	}
	public BigDecimal getNroProtocollo() {
		return nroProtocollo;
	}
	public void setNroProtocollo(BigDecimal nroProtocollo) {
		this.nroProtocollo = nroProtocollo;
	}
	public String getSubProtocollo() {
		return subProtocollo;
	}
	public void setSubProtocollo(String subProtocollo) {
		this.subProtocollo = subProtocollo;
	}
	public Date getDataProtocollo() {
		return dataProtocollo;
	}
	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	public String getSocieta() {
		return societa;
	}
	public void setSocieta(String societa) {
		this.societa = societa;
	}
	public String getDesUserProtocollo() {
		return desUserProtocollo;
	}
	public void setDesUserProtocollo(String desUserProtocollo) {
		this.desUserProtocollo = desUserProtocollo;
	}
	public String getDesUOProtocollo() {
		return desUOProtocollo;
	}
	public void setDesUOProtocollo(String desUOProtocollo) {
		this.desUOProtocollo = desUOProtocollo;
	}
	public String getIdUdAttoAutAnnProtocollo() {
		return idUdAttoAutAnnProtocollo;
	}
	public void setIdUdAttoAutAnnProtocollo(String idUdAttoAutAnnProtocollo) {
		this.idUdAttoAutAnnProtocollo = idUdAttoAutAnnProtocollo;
	}
	public Boolean getAnnullata() {
		return annullata;
	}
	public void setAnnullata(Boolean annullata) {
		this.annullata = annullata;
	}
	public String getDatiAnnullamento() {
		return datiAnnullamento;
	}
	public void setDatiAnnullamento(String datiAnnullamento) {
		this.datiAnnullamento = datiAnnullamento;
	}
	public Boolean getRichAnnullamento() {
		return richAnnullamento;
	}
	public void setRichAnnullamento(Boolean richAnnullamento) {
		this.richAnnullamento = richAnnullamento;
	}
	public String getDatiRichAnnullamento() {
		return datiRichAnnullamento;
	}
	public void setDatiRichAnnullamento(String datiRichAnnullamento) {
		this.datiRichAnnullamento = datiRichAnnullamento;
	}
	public String getMotiviRichAnnullamento() {
		return motiviRichAnnullamento;
	}
	public void setMotiviRichAnnullamento(String motiviRichAnnullamento) {
		this.motiviRichAnnullamento = motiviRichAnnullamento;
	}
	public String getPresenzaDocCollegati() {
		return presenzaDocCollegati;
	}
	public void setPresenzaDocCollegati(String presenzaDocCollegati) {
		this.presenzaDocCollegati = presenzaDocCollegati;
	}
	public List<DocCollegatoBean> getListaDocumentiDaCollegare() {
		return listaDocumentiDaCollegare;
	}
	public void setListaDocumentiDaCollegare(List<DocCollegatoBean> listaDocumentiDaCollegare) {
		this.listaDocumentiDaCollegare = listaDocumentiDaCollegare;
	}
	public Boolean getFlgMulta() {
		return flgMulta;
	}
	public void setFlgMulta(Boolean flgMulta) {
		this.flgMulta = flgMulta;
	}
	public String getStatoTrasferimentoBloomfleet() {
		return statoTrasferimentoBloomfleet;
	}
	public void setStatoTrasferimentoBloomfleet(String statoTrasferimentoBloomfleet) {
		this.statoTrasferimentoBloomfleet = statoTrasferimentoBloomfleet;
	}
	public String getDettaglioTrasferimentoBloomfleet() {
		return dettaglioTrasferimentoBloomfleet;
	}
	public void setDettaglioTrasferimentoBloomfleet(String dettaglioTrasferimentoBloomfleet) {
		this.dettaglioTrasferimentoBloomfleet = dettaglioTrasferimentoBloomfleet;
	}
	public Boolean getFlgDocContratti() {
		return flgDocContratti;
	}
	public void setFlgDocContratti(Boolean flgDocContratti) {
		this.flgDocContratti = flgDocContratti;
	}
	public String getCodContratti() {
		return codContratti;
	}
	public void setCodContratti(String codContratti) {
		this.codContratti = codContratti;
	}
	public String getFlgFirmeCompilateContratti() {
		return flgFirmeCompilateContratti;
	}
	public void setFlgFirmeCompilateContratti(String flgFirmeCompilateContratti) {
		this.flgFirmeCompilateContratti = flgFirmeCompilateContratti;
	}
	public Boolean getFlgDocContrattiBarcode() {
		return flgDocContrattiBarcode;
	}
	public void setFlgDocContrattiBarcode(Boolean flgDocContrattiBarcode) {
		this.flgDocContrattiBarcode = flgDocContrattiBarcode;
	}
	public String getEstremiRepertorioPerStruttura() {
		return estremiRepertorioPerStruttura;
	}
	public void setEstremiRepertorioPerStruttura(String estremiRepertorioPerStruttura) {
		this.estremiRepertorioPerStruttura = estremiRepertorioPerStruttura;
	}
	public String getIdUdLiquidazione() {
		return idUdLiquidazione;
	}
	public void setIdUdLiquidazione(String idUdLiquidazione) {
		this.idUdLiquidazione = idUdLiquidazione;
	}
	public String getEstremiAttoLiquidazione() {
		return estremiAttoLiquidazione;
	}
	public void setEstremiAttoLiquidazione(String estremiAttoLiquidazione) {
		this.estremiAttoLiquidazione = estremiAttoLiquidazione;
	}
	public String getRifOrigineProtRicevuto() {
		return rifOrigineProtRicevuto;
	}
	public void setRifOrigineProtRicevuto(String rifOrigineProtRicevuto) {
		this.rifOrigineProtRicevuto = rifOrigineProtRicevuto;
	}
	public String getNroProtRicevuto() {
		return nroProtRicevuto;
	}
	public void setNroProtRicevuto(String nroProtRicevuto) {
		this.nroProtRicevuto = nroProtRicevuto;
	}
	public String getAnnoProtRicevuto() {
		return annoProtRicevuto;
	}
	public void setAnnoProtRicevuto(String annoProtRicevuto) {
		this.annoProtRicevuto = annoProtRicevuto;
	}
	public Date getDataProtRicevuto() {
		return dataProtRicevuto;
	}
	public void setDataProtRicevuto(Date dataProtRicevuto) {
		this.dataProtRicevuto = dataProtRicevuto;
	}
	public Date getDataEOraArrivo() {
		return dataEOraArrivo;
	}
	public void setDataEOraArrivo(Date dataEOraArrivo) {
		this.dataEOraArrivo = dataEOraArrivo;
	}
	public Date getDataEOraSpedizione() {
		return dataEOraSpedizione;
	}
	public void setDataEOraSpedizione(Date dataEOraSpedizione) {
		this.dataEOraSpedizione = dataEOraSpedizione;
	}
	public String getCodRapidoOggetto() {
		return codRapidoOggetto;
	}
	public void setCodRapidoOggetto(String codRapidoOggetto) {
		this.codRapidoOggetto = codRapidoOggetto;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public BigDecimal getLivelloRiservatezza() {
		return livelloRiservatezza;
	}
	public void setLivelloRiservatezza(BigDecimal livelloRiservatezza) {
		this.livelloRiservatezza = livelloRiservatezza;
	}
	public String getDescrizioneRiservatezza() {
		return descrizioneRiservatezza;
	}
	public void setDescrizioneRiservatezza(String descrizioneRiservatezza) {
		this.descrizioneRiservatezza = descrizioneRiservatezza;
	}
	public Date getDataRiservatezza() {
		return dataRiservatezza;
	}
	public void setDataRiservatezza(Date dataRiservatezza) {
		this.dataRiservatezza = dataRiservatezza;
	}
	public String getPrioritaRiservatezza() {
		return prioritaRiservatezza;
	}
	public void setPrioritaRiservatezza(String prioritaRiservatezza) {
		this.prioritaRiservatezza = prioritaRiservatezza;
	}
	public String getDescrizionePrioritaRiservatezza() {
		return descrizionePrioritaRiservatezza;
	}
	public void setDescrizionePrioritaRiservatezza(String descrizionePrioritaRiservatezza) {
		this.descrizionePrioritaRiservatezza = descrizionePrioritaRiservatezza;
	}
	public Boolean getFlgRispostaUrgente() {
		return flgRispostaUrgente;
	}
	public void setFlgRispostaUrgente(Boolean flgRispostaUrgente) {
		this.flgRispostaUrgente = flgRispostaUrgente;
	}
	public String getSiglaDocRiferimento() {
		return siglaDocRiferimento;
	}
	public void setSiglaDocRiferimento(String siglaDocRiferimento) {
		this.siglaDocRiferimento = siglaDocRiferimento;
	}
	public String getAnnoDocRiferimento() {
		return annoDocRiferimento;
	}
	public void setAnnoDocRiferimento(String annoDocRiferimento) {
		this.annoDocRiferimento = annoDocRiferimento;
	}
	public BigDecimal getNroDocRiferimento() {
		return nroDocRiferimento;
	}
	public void setNroDocRiferimento(BigDecimal nroDocRiferimento) {
		this.nroDocRiferimento = nroDocRiferimento;
	}
	public Boolean getFlgRichAccCivSemplice() {
		return flgRichAccCivSemplice;
	}
	public void setFlgRichAccCivSemplice(Boolean flgRichAccCivSemplice) {
		this.flgRichAccCivSemplice = flgRichAccCivSemplice;
	}
	public Boolean getFlgRichAccCivGeneralizzato() {
		return flgRichAccCivGeneralizzato;
	}
	public void setFlgRichAccCivGeneralizzato(Boolean flgRichAccCivGeneralizzato) {
		this.flgRichAccCivGeneralizzato = flgRichAccCivGeneralizzato;
	}
	public BigDecimal getIdDocPrimario() {
		return idDocPrimario;
	}
	public void setIdDocPrimario(BigDecimal idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}
	public File getFilePrimario() {
		return filePrimario;
	}
	public void setFilePrimario(File filePrimario) {
		this.filePrimario = filePrimario;
	}
	public String getPercorsoFilePrimari() {
		return percorsoFilePrimari;
	}
	public void setPercorsoFilePrimari(String percorsoFilePrimari) {
		this.percorsoFilePrimari = percorsoFilePrimari;
	}
	public String getNomeFilePrimario() {
		return nomeFilePrimario;
	}
	public void setNomeFilePrimario(String nomeFilePrimario) {
		this.nomeFilePrimario = nomeFilePrimario;
	}
	public String getUriFilePrimario() {
		return uriFilePrimario;
	}
	public void setUriFilePrimario(String uriFilePrimario) {
		this.uriFilePrimario = uriFilePrimario;
	}
	public Date getTsInsLastVerFilePrimario() {
		return tsInsLastVerFilePrimario;
	}
	public void setTsInsLastVerFilePrimario(Date tsInsLastVerFilePrimario) {
		this.tsInsLastVerFilePrimario = tsInsLastVerFilePrimario;
	}
	public Boolean getRemoteUriFilePrimario() {
		return remoteUriFilePrimario;
	}
	public void setRemoteUriFilePrimario(Boolean remoteUriFilePrimario) {
		this.remoteUriFilePrimario = remoteUriFilePrimario;
	}
	public String getIdAttachEmailPrimario() {
		return idAttachEmailPrimario;
	}
	public void setIdAttachEmailPrimario(String idAttachEmailPrimario) {
		this.idAttachEmailPrimario = idAttachEmailPrimario;
	}
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	public Boolean getIsDocPrimarioChanged() {
		return isDocPrimarioChanged;
	}
	public void setIsDocPrimarioChanged(Boolean isDocPrimarioChanged) {
		this.isDocPrimarioChanged = isDocPrimarioChanged;
	}
	public String getMimetypeVerPreFirma() {
		return mimetypeVerPreFirma;
	}
	public void setMimetypeVerPreFirma(String mimetypeVerPreFirma) {
		this.mimetypeVerPreFirma = mimetypeVerPreFirma;
	}
	public String getUriFileVerPreFirma() {
		return uriFileVerPreFirma;
	}
	public void setUriFileVerPreFirma(String uriFileVerPreFirma) {
		this.uriFileVerPreFirma = uriFileVerPreFirma;
	}
	public String getNomeFileVerPreFirma() {
		return nomeFileVerPreFirma;
	}
	public void setNomeFileVerPreFirma(String nomeFileVerPreFirma) {
		this.nomeFileVerPreFirma = nomeFileVerPreFirma;
	}
	public String getFlgConvertibilePdfVerPreFirma() {
		return flgConvertibilePdfVerPreFirma;
	}
	public void setFlgConvertibilePdfVerPreFirma(String flgConvertibilePdfVerPreFirma) {
		this.flgConvertibilePdfVerPreFirma = flgConvertibilePdfVerPreFirma;
	}
	public String getImprontaVerPreFirma() {
		return improntaVerPreFirma;
	}
	public void setImprontaVerPreFirma(String improntaVerPreFirma) {
		this.improntaVerPreFirma = improntaVerPreFirma;
	}
	public MimeTypeFirmaBean getInfoFileVerPreFirma() {
		return infoFileVerPreFirma;
	}
	public void setInfoFileVerPreFirma(MimeTypeFirmaBean infoFileVerPreFirma) {
		this.infoFileVerPreFirma = infoFileVerPreFirma;
	}
	public Boolean getFlgNoPubblPrimario() {
		return flgNoPubblPrimario;
	}
	public void setFlgNoPubblPrimario(Boolean flgNoPubblPrimario) {
		this.flgNoPubblPrimario = flgNoPubblPrimario;
	}
	public Boolean getFlgDatiSensibili() {
		return flgDatiSensibili;
	}
	public void setFlgDatiSensibili(Boolean flgDatiSensibili) {
		this.flgDatiSensibili = flgDatiSensibili;
	}
	public String getTipoSezioneContrattoPrimario() {
		return tipoSezioneContrattoPrimario;
	}
	public void setTipoSezioneContrattoPrimario(String tipoSezioneContrattoPrimario) {
		this.tipoSezioneContrattoPrimario = tipoSezioneContrattoPrimario;
	}
	public String getCodContrattoPrimario() {
		return codContrattoPrimario;
	}
	public void setCodContrattoPrimario(String codContrattoPrimario) {
		this.codContrattoPrimario = codContrattoPrimario;
	}
	public String getFlgPresentiFirmeContrattoPrimario() {
		return flgPresentiFirmeContrattoPrimario;
	}
	public void setFlgPresentiFirmeContrattoPrimario(String flgPresentiFirmeContrattoPrimario) {
		this.flgPresentiFirmeContrattoPrimario = flgPresentiFirmeContrattoPrimario;
	}
	public String getFlgFirmeCompleteContrattoPrimario() {
		return flgFirmeCompleteContrattoPrimario;
	}
	public void setFlgFirmeCompleteContrattoPrimario(String flgFirmeCompleteContrattoPrimario) {
		this.flgFirmeCompleteContrattoPrimario = flgFirmeCompleteContrattoPrimario;
	}
	public String getNroFirmePrevisteContrattoPrimario() {
		return nroFirmePrevisteContrattoPrimario;
	}
	public void setNroFirmePrevisteContrattoPrimario(String nroFirmePrevisteContrattoPrimario) {
		this.nroFirmePrevisteContrattoPrimario = nroFirmePrevisteContrattoPrimario;
	}
	public String getNroFirmeCompilateContrattoPrimario() {
		return nroFirmeCompilateContrattoPrimario;
	}
	public void setNroFirmeCompilateContrattoPrimario(String nroFirmeCompilateContrattoPrimario) {
		this.nroFirmeCompilateContrattoPrimario = nroFirmeCompilateContrattoPrimario;
	}
	public List<NominativiFirmaOlografaBean> getListaNominativiFirmaOlografa() {
		return listaNominativiFirmaOlografa;
	}
	public void setListaNominativiFirmaOlografa(List<NominativiFirmaOlografaBean> listaNominativiFirmaOlografa) {
		this.listaNominativiFirmaOlografa = listaNominativiFirmaOlografa;
	}
	public Boolean getFlgSostituisciVerPrec() {
		return flgSostituisciVerPrec;
	}
	public void setFlgSostituisciVerPrec(Boolean flgSostituisciVerPrec) {
		this.flgSostituisciVerPrec = flgSostituisciVerPrec;
	}
	public String getNroLastVer() {
		return nroLastVer;
	}
	public void setNroLastVer(String nroLastVer) {
		this.nroLastVer = nroLastVer;
	}
	public Boolean getFlgOriginaleCartaceo() {
		return flgOriginaleCartaceo;
	}
	public void setFlgOriginaleCartaceo(Boolean flgOriginaleCartaceo) {
		this.flgOriginaleCartaceo = flgOriginaleCartaceo;
	}
	public Boolean getFlgCopiaSostitutiva() {
		return flgCopiaSostitutiva;
	}
	public void setFlgCopiaSostitutiva(Boolean flgCopiaSostitutiva) {
		this.flgCopiaSostitutiva = flgCopiaSostitutiva;
	}
	public Boolean getFlgTimbratoFilePostReg() {
		return flgTimbratoFilePostReg;
	}
	public void setFlgTimbratoFilePostReg(Boolean flgTimbratoFilePostReg) {
		this.flgTimbratoFilePostReg = flgTimbratoFilePostReg;
	}
	public Boolean getFlgTimbraFilePostReg() {
		return flgTimbraFilePostReg;
	}
	public void setFlgTimbraFilePostReg(Boolean flgTimbraFilePostReg) {
		this.flgTimbraFilePostReg = flgTimbraFilePostReg;
	}
	public OpzioniTimbroDocBean getOpzioniTimbro() {
		return opzioniTimbro;
	}
	public void setOpzioniTimbro(OpzioniTimbroDocBean opzioniTimbro) {
		this.opzioniTimbro = opzioniTimbro;
	}
	public String getTipoModFilePrimario() {
		return tipoModFilePrimario;
	}
	public void setTipoModFilePrimario(String tipoModFilePrimario) {
		this.tipoModFilePrimario = tipoModFilePrimario;
	}
	public List<AllegatoProtocolloBean> getListaFilePrimarioVerPubbl() {
		return listaFilePrimarioVerPubbl;
	}
	public void setListaFilePrimarioVerPubbl(List<AllegatoProtocolloBean> listaFilePrimarioVerPubbl) {
		this.listaFilePrimarioVerPubbl = listaFilePrimarioVerPubbl;
	}	
	public String getEsitoInvioACTASerieAttiIntegrali() {
		return esitoInvioACTASerieAttiIntegrali;
	}
	public void setEsitoInvioACTASerieAttiIntegrali(String esitoInvioACTASerieAttiIntegrali) {
		this.esitoInvioACTASerieAttiIntegrali = esitoInvioACTASerieAttiIntegrali;
	}
	public String getEsitoInvioACTASeriePubbl() {
		return esitoInvioACTASeriePubbl;
	}
	public void setEsitoInvioACTASeriePubbl(String esitoInvioACTASeriePubbl) {
		this.esitoInvioACTASeriePubbl = esitoInvioACTASeriePubbl;
	}
	public DocumentBean getFilePrimarioOmissis() {
		return filePrimarioOmissis;
	}
	public void setFilePrimarioOmissis(DocumentBean filePrimarioOmissis) {
		this.filePrimarioOmissis = filePrimarioOmissis;
	}
	/*
	public BigDecimal getIdDocOmissis() {
		return idDocOmissis;
	}
	public void setIdDocOmissis(BigDecimal idDocOmissis) {
		this.idDocOmissis = idDocOmissis;
	}
	public String getNomeFileOmissis() {
		return nomeFileOmissis;
	}
	public void setNomeFileOmissis(String nomeFileOmissis) {
		this.nomeFileOmissis = nomeFileOmissis;
	}
	public String getUriFileOmissis() {
		return uriFileOmissis;
	}
	public void setUriFileOmissis(String uriFileOmissis) {
		this.uriFileOmissis = uriFileOmissis;
	}
	public Boolean getRemoteUriOmissis() {
		return remoteUriOmissis;
	}
	public void setRemoteUriOmissis(Boolean remoteUriOmissis) {
		this.remoteUriOmissis = remoteUriOmissis;
	}
	public MimeTypeFirmaBean getInfoFileOmissis() {
		return infoFileOmissis;
	}
	public void setInfoFileOmissis(MimeTypeFirmaBean infoFileOmissis) {
		this.infoFileOmissis = infoFileOmissis;
	}
	public Boolean getIsChangedOmissis() {
		return isChangedOmissis;
	}
	public void setIsChangedOmissis(Boolean isChangedOmissis) {
		this.isChangedOmissis = isChangedOmissis;
	}
	public String getMimetypeVerPreFirmaOmissis() {
		return mimetypeVerPreFirmaOmissis;
	}
	public void setMimetypeVerPreFirmaOmissis(String mimetypeVerPreFirmaOmissis) {
		this.mimetypeVerPreFirmaOmissis = mimetypeVerPreFirmaOmissis;
	}
	public String getUriFileVerPreFirmaOmissis() {
		return uriFileVerPreFirmaOmissis;
	}
	public void setUriFileVerPreFirmaOmissis(String uriFileVerPreFirmaOmissis) {
		this.uriFileVerPreFirmaOmissis = uriFileVerPreFirmaOmissis;
	}
	public String getNomeFileVerPreFirmaOmissis() {
		return nomeFileVerPreFirmaOmissis;
	}
	public void setNomeFileVerPreFirmaOmissis(String nomeFileVerPreFirmaOmissis) {
		this.nomeFileVerPreFirmaOmissis = nomeFileVerPreFirmaOmissis;
	}
	public String getFlgConvertibilePdfVerPreFirmaOmissis() {
		return flgConvertibilePdfVerPreFirmaOmissis;
	}
	public void setFlgConvertibilePdfVerPreFirmaOmissis(String flgConvertibilePdfVerPreFirmaOmissis) {
		this.flgConvertibilePdfVerPreFirmaOmissis = flgConvertibilePdfVerPreFirmaOmissis;
	}
	public String getImprontaVerPreFirmaOmissis() {
		return improntaVerPreFirmaOmissis;
	}
	public void setImprontaVerPreFirmaOmissis(String improntaVerPreFirmaOmissis) {
		this.improntaVerPreFirmaOmissis = improntaVerPreFirmaOmissis;
	}
	public MimeTypeFirmaBean getInfoFileVerPreFirmaOmissis() {
		return infoFileVerPreFirmaOmissis;
	}
	public void setInfoFileVerPreFirmaOmissis(MimeTypeFirmaBean infoFileVerPreFirmaOmissis) {
		this.infoFileVerPreFirmaOmissis = infoFileVerPreFirmaOmissis;
	}
	public Boolean getFlgSostituisciVerPrecOmissis() {
		return flgSostituisciVerPrecOmissis;
	}
	public void setFlgSostituisciVerPrecOmissis(Boolean flgSostituisciVerPrecOmissis) {
		this.flgSostituisciVerPrecOmissis = flgSostituisciVerPrecOmissis;
	}
	public String getNroUltimaVersioneOmissis() {
		return nroUltimaVersioneOmissis;
	}
	public void setNroUltimaVersioneOmissis(String nroUltimaVersioneOmissis) {
		this.nroUltimaVersioneOmissis = nroUltimaVersioneOmissis;
	}
	public Boolean getFlgTimbratoFilePostRegOmissis() {
		return flgTimbratoFilePostRegOmissis;
	}
	public void setFlgTimbratoFilePostRegOmissis(Boolean flgTimbratoFilePostRegOmissis) {
		this.flgTimbratoFilePostRegOmissis = flgTimbratoFilePostRegOmissis;
	}
	public Boolean getFlgTimbraFilePostRegOmissis() {
		return flgTimbraFilePostRegOmissis;
	}
	public void setFlgTimbraFilePostRegOmissis(Boolean flgTimbraFilePostRegOmissis) {
		this.flgTimbraFilePostRegOmissis = flgTimbraFilePostRegOmissis;
	}
	public OpzioniTimbroDocBean getOpzioniTimbroOmissis() {
		return opzioniTimbroOmissis;
	}
	public void setOpzioniTimbroOmissis(OpzioniTimbroDocBean opzioniTimbroOmissis) {
		this.opzioniTimbroOmissis = opzioniTimbroOmissis;
	}
	*/
	public String getDocPressoCentroPosta() {
		return docPressoCentroPosta;
	}
	public void setDocPressoCentroPosta(String docPressoCentroPosta) {
		this.docPressoCentroPosta = docPressoCentroPosta;
	}
	public List<AllegatoProtocolloBean> getListaAllegati() {
		return listaAllegati;
	}
	public void setListaAllegati(List<AllegatoProtocolloBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}
	public String getPercorsoFileAllegati() {
		return percorsoFileAllegati;
	}
	public void setPercorsoFileAllegati(String percorsoFileAllegati) {
		this.percorsoFileAllegati = percorsoFileAllegati;
	}
	public List<AllegatoProtocolloBean> getListaDocProcFolder() {
		return listaDocProcFolder;
	}
	public void setListaDocProcFolder(List<AllegatoProtocolloBean> listaDocProcFolder) {
		this.listaDocProcFolder = listaDocProcFolder;
	}	
	public List<FileCompletiXAttiBean> getListaFileCompletiXAtti() {
		return listaFileCompletiXAtti;
	}
	public void setListaFileCompletiXAtti(List<FileCompletiXAttiBean> listaFileCompletiXAtti) {
		this.listaFileCompletiXAtti = listaFileCompletiXAtti;
	}
	public String getIdToponimo() {
		return idToponimo;
	}
	public void setIdToponimo(String idToponimo) {
		this.idToponimo = idToponimo;
	}
	public String getDescrizioneCollocazioneFisica() {
		return descrizioneCollocazioneFisica;
	}
	public void setDescrizioneCollocazioneFisica(String descrizioneCollocazioneFisica) {
		this.descrizioneCollocazioneFisica = descrizioneCollocazioneFisica;
	}
	public Date getDataDocumento() {
		return dataDocumento;
	}
	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}
	public Date getDataSpedizioneCartaceo() {
		return dataSpedizioneCartaceo;
	}
	public void setDataSpedizioneCartaceo(Date dataSpedizioneCartaceo) {
		this.dataSpedizioneCartaceo = dataSpedizioneCartaceo;
	}
	public Date getDataArrivoProtocollo() {
		return dataArrivoProtocollo;
	}
	public void setDataArrivoProtocollo(Date dataArrivoProtocollo) {
		this.dataArrivoProtocollo = dataArrivoProtocollo;
	}
	public Boolean getDataArrivoProtocolloEditabile() {
		return dataArrivoProtocolloEditabile;
	}
	public void setDataArrivoProtocolloEditabile(Boolean dataArrivoProtocolloEditabile) {
		this.dataArrivoProtocolloEditabile = dataArrivoProtocolloEditabile;
	}
	public String getTipoDocumentoSalvato() {
		return tipoDocumentoSalvato;
	}
	public void setTipoDocumentoSalvato(String tipoDocumentoSalvato) {
		this.tipoDocumentoSalvato = tipoDocumentoSalvato;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNomeTipoDocumento() {
		return nomeTipoDocumento;
	}
	public void setNomeTipoDocumento(String nomeTipoDocumento) {
		this.nomeTipoDocumento = nomeTipoDocumento;
	}
	public String getFlgRichiestaFirmaDigitale() {
		return flgRichiestaFirmaDigitale;
	}
	public void setFlgRichiestaFirmaDigitale(String flgRichiestaFirmaDigitale) {
		this.flgRichiestaFirmaDigitale = flgRichiestaFirmaDigitale;
	}
	public String getCodCategoriaAltraNumerazione() {
		return codCategoriaAltraNumerazione;
	}
	public void setCodCategoriaAltraNumerazione(String codCategoriaAltraNumerazione) {
		this.codCategoriaAltraNumerazione = codCategoriaAltraNumerazione;
	}
	public String getSiglaAltraNumerazione() {
		return siglaAltraNumerazione;
	}
	public void setSiglaAltraNumerazione(String siglaAltraNumerazione) {
		this.siglaAltraNumerazione = siglaAltraNumerazione;
	}
	public String getAltreNumerazioniUD() {
		return altreNumerazioniUD;
	}
	public void setAltreNumerazioniUD(String altreNumerazioniUD) {
		this.altreNumerazioniUD = altreNumerazioniUD;
	}
	public String getRowidDoc() {
		return rowidDoc;
	}
	public void setRowidDoc(String rowidDoc) {
		this.rowidDoc = rowidDoc;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNoteMancanzaFile() {
		return noteMancanzaFile;
	}
	public void setNoteMancanzaFile(String noteMancanzaFile) {
		this.noteMancanzaFile = noteMancanzaFile;
	}
	public String getPuntoProtAssegnatario() {
		return puntoProtAssegnatario;
	}
	public void setPuntoProtAssegnatario(String puntoProtAssegnatario) {
		this.puntoProtAssegnatario = puntoProtAssegnatario;
	}
	public Boolean getFlgPresentiAssPreselMitt() {
		return flgPresentiAssPreselMitt;
	}
	public void setFlgPresentiAssPreselMitt(Boolean flgPresentiAssPreselMitt) {
		this.flgPresentiAssPreselMitt = flgPresentiAssPreselMitt;
	}
	public List<AssPreselMittBean> getListaAssPreselMitt() {
		return listaAssPreselMitt;
	}
	public void setListaAssPreselMitt(List<AssPreselMittBean> listaAssPreselMitt) {
		this.listaAssPreselMitt = listaAssPreselMitt;
	}
	public List<DestinatarioProtBean> getListaDestinatari() {
		return listaDestinatari;
	}
	public void setListaDestinatari(List<DestinatarioProtBean> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}
	public List<AssegnazioneBean> getListaAssegnazioni() {
		return listaAssegnazioni;
	}
	public void setListaAssegnazioni(List<AssegnazioneBean> listaAssegnazioni) {
		this.listaAssegnazioni = listaAssegnazioni;
	}
	public List<AssegnazioneBean> getListaAssegnazioniSalvate() {
		return listaAssegnazioniSalvate;
	}
	public void setListaAssegnazioniSalvate(List<AssegnazioneBean> listaAssegnazioniSalvate) {
		this.listaAssegnazioniSalvate = listaAssegnazioniSalvate;
	}
	public List<AssegnazioneBean> getListaDestinatariUoProtocollazione() {
		return listaDestinatariUoProtocollazione;
	}
	public void setListaDestinatariUoProtocollazione(List<AssegnazioneBean> listaDestinatariUoProtocollazione) {
		this.listaDestinatariUoProtocollazione = listaDestinatariUoProtocollazione;
	}
	public List<DestInvioCCBean> getListaDestInvioCC() {
		return listaDestInvioCC;
	}
	public void setListaDestInvioCC(List<DestInvioCCBean> listaDestInvioCC) {
		this.listaDestInvioCC = listaDestInvioCC;
	}
	public List<DestInvioCCBean> getListaDestInvioCCSalvati() {
		return listaDestInvioCCSalvati;
	}
	public void setListaDestInvioCCSalvati(List<DestInvioCCBean> listaDestInvioCCSalvati) {
		this.listaDestInvioCCSalvati = listaDestInvioCCSalvati;
	}
	public List<DestInvioCCBean> getListaUoCoinvolte() {
		return listaUoCoinvolte;
	}
	public void setListaUoCoinvolte(List<DestInvioCCBean> listaUoCoinvolte) {
		this.listaUoCoinvolte = listaUoCoinvolte;
	}
	public List<ClassificazioneFascicoloBean> getListaClassFasc() {
		return listaClassFasc;
	}
	public void setListaClassFasc(List<ClassificazioneFascicoloBean> listaClassFasc) {
		this.listaClassFasc = listaClassFasc;
	}
	public List<FolderCustomBean> getListaFolderCustom() {
		return listaFolderCustom;
	}
	public void setListaFolderCustom(List<FolderCustomBean> listaFolderCustom) {
		this.listaFolderCustom = listaFolderCustom;
	}
	public List<MittenteProtBean> getListaMittenti() {
		return listaMittenti;
	}
	public void setListaMittenti(List<MittenteProtBean> listaMittenti) {
		this.listaMittenti = listaMittenti;
	}
	public List<TipoNumerazioneBean> getListaTipoNumerazione() {
		return listaTipoNumerazione;
	}
	public void setListaTipoNumerazione(List<TipoNumerazioneBean> listaTipoNumerazione) {
		this.listaTipoNumerazione = listaTipoNumerazione;
	}
	public String getRifRegEmergenza() {
		return rifRegEmergenza;
	}
	public void setRifRegEmergenza(String rifRegEmergenza) {
		this.rifRegEmergenza = rifRegEmergenza;
	}
	public BigDecimal getNroRegEmergenza() {
		return nroRegEmergenza;
	}
	public void setNroRegEmergenza(BigDecimal nroRegEmergenza) {
		this.nroRegEmergenza = nroRegEmergenza;
	}
	public Date getDataRegEmergenza() {
		return dataRegEmergenza;
	}
	public void setDataRegEmergenza(Date dataRegEmergenza) {
		this.dataRegEmergenza = dataRegEmergenza;
	}
	public String getIdUserRegEmergenza() {
		return idUserRegEmergenza;
	}
	public void setIdUserRegEmergenza(String idUserRegEmergenza) {
		this.idUserRegEmergenza = idUserRegEmergenza;
	}
	public String getIdUoRegEmergenza() {
		return idUoRegEmergenza;
	}
	public void setIdUoRegEmergenza(String idUoRegEmergenza) {
		this.idUoRegEmergenza = idUoRegEmergenza;
	}
	public String getMezzoTrasmissione() {
		return mezzoTrasmissione;
	}
	public void setMezzoTrasmissione(String mezzoTrasmissione) {
		this.mezzoTrasmissione = mezzoTrasmissione;
	}
	public String getNroRaccomandata() {
		return nroRaccomandata;
	}
	public void setNroRaccomandata(String nroRaccomandata) {
		this.nroRaccomandata = nroRaccomandata;
	}
	public Date getDataRaccomandata() {
		return dataRaccomandata;
	}
	public void setDataRaccomandata(Date dataRaccomandata) {
		this.dataRaccomandata = dataRaccomandata;
	}
	public String getNroListaRaccomandata() {
		return nroListaRaccomandata;
	}
	public void setNroListaRaccomandata(String nroListaRaccomandata) {
		this.nroListaRaccomandata = nroListaRaccomandata;
	}
	public String getNomeCollocazioneFisica() {
		return nomeCollocazioneFisica;
	}
	public void setNomeCollocazioneFisica(String nomeCollocazioneFisica) {
		this.nomeCollocazioneFisica = nomeCollocazioneFisica;
	}
	public String getCodRapidoCollocazioneFisica() {
		return codRapidoCollocazioneFisica;
	}
	public void setCodRapidoCollocazioneFisica(String codRapidoCollocazioneFisica) {
		this.codRapidoCollocazioneFisica = codRapidoCollocazioneFisica;
	}
	public String getSiglaNumerazioneSecondaria() {
		return siglaNumerazioneSecondaria;
	}
	public void setSiglaNumerazioneSecondaria(String siglaNumerazioneSecondaria) {
		this.siglaNumerazioneSecondaria = siglaNumerazioneSecondaria;
	}
	public String getAnnoNumerazioneSecondaria() {
		return annoNumerazioneSecondaria;
	}
	public void setAnnoNumerazioneSecondaria(String annoNumerazioneSecondaria) {
		this.annoNumerazioneSecondaria = annoNumerazioneSecondaria;
	}
	public String getTipoNumerazioneSecondaria() {
		return tipoNumerazioneSecondaria;
	}
	public void setTipoNumerazioneSecondaria(String tipoNumerazioneSecondaria) {
		this.tipoNumerazioneSecondaria = tipoNumerazioneSecondaria;
	}
	public BigDecimal getNumeroNumerazioneSecondaria() {
		return numeroNumerazioneSecondaria;
	}
	public void setNumeroNumerazioneSecondaria(BigDecimal numeroNumerazioneSecondaria) {
		this.numeroNumerazioneSecondaria = numeroNumerazioneSecondaria;
	}
	public String getSubNumerazioneSecondaria() {
		return subNumerazioneSecondaria;
	}
	public void setSubNumerazioneSecondaria(String subNumerazioneSecondaria) {
		this.subNumerazioneSecondaria = subNumerazioneSecondaria;
	}
	public Date getDataRegistrazioneNumerazioneSecondaria() {
		return dataRegistrazioneNumerazioneSecondaria;
	}
	public void setDataRegistrazioneNumerazioneSecondaria(Date dataRegistrazioneNumerazioneSecondaria) {
		this.dataRegistrazioneNumerazioneSecondaria = dataRegistrazioneNumerazioneSecondaria;
	}
	public String getIdUdAttoAutAnnRegSecondaria() {
		return idUdAttoAutAnnRegSecondaria;
	}
	public void setIdUdAttoAutAnnRegSecondaria(String idUdAttoAutAnnRegSecondaria) {
		this.idUdAttoAutAnnRegSecondaria = idUdAttoAutAnnRegSecondaria;
	}
	public Boolean getAbilRevocaAtto() {
		return abilRevocaAtto;
	}
	public void setAbilRevocaAtto(Boolean abilRevocaAtto) {
		this.abilRevocaAtto = abilRevocaAtto;
	}
	public Boolean getAbilSmistaPropAtto() {
		return abilSmistaPropAtto;
	}
	public void setAbilSmistaPropAtto(Boolean abilSmistaPropAtto) {
		this.abilSmistaPropAtto = abilSmistaPropAtto;
	}
	public Boolean getAbilPresaInCaricoPropAtto() {
		return abilPresaInCaricoPropAtto;
	}
	public void setAbilPresaInCaricoPropAtto(Boolean abilPresaInCaricoPropAtto) {
		this.abilPresaInCaricoPropAtto = abilPresaInCaricoPropAtto;
	}
	public Boolean getAbilSottoscrizioneCons() {
		return abilSottoscrizioneCons;
	}
	public void setAbilSottoscrizioneCons(Boolean abilSottoscrizioneCons) {
		this.abilSottoscrizioneCons = abilSottoscrizioneCons;
	}
	public Boolean getAbilRimuoviSottoscrizioneCons() {
		return abilRimuoviSottoscrizioneCons;
	}
	public void setAbilRimuoviSottoscrizioneCons(Boolean abilRimuoviSottoscrizioneCons) {
		this.abilRimuoviSottoscrizioneCons = abilRimuoviSottoscrizioneCons;
	}
	public Boolean getAbilPresentazionePropAtto() {
		return abilPresentazionePropAtto;
	}
	public void setAbilPresentazionePropAtto(Boolean abilPresentazionePropAtto) {
		this.abilPresentazionePropAtto = abilPresentazionePropAtto;
	}
	public Boolean getAbilRitiroPropAtto() {
		return abilRitiroPropAtto;
	}
	public void setAbilRitiroPropAtto(Boolean abilRitiroPropAtto) {
		this.abilRitiroPropAtto = abilRitiroPropAtto;
	}
	public Boolean getAbilAvviaEmendamento() {
		return abilAvviaEmendamento;
	}
	public void setAbilAvviaEmendamento(Boolean abilAvviaEmendamento) {
		this.abilAvviaEmendamento = abilAvviaEmendamento;
	}
	public Boolean getAbilAnnullaPropostaAtto() {
		return abilAnnullaPropostaAtto;
	}
	public void setAbilAnnullaPropostaAtto(Boolean abilAnnullaPropostaAtto) {
		this.abilAnnullaPropostaAtto = abilAnnullaPropostaAtto;
	}
	public Boolean getAbilRilascioVisto() {
		return abilRilascioVisto;
	}
	public void setAbilRilascioVisto(Boolean abilRilascioVisto) {
		this.abilRilascioVisto = abilRilascioVisto;
	}
	public Boolean getAbilRifiutoVisto() {
		return abilRifiutoVisto;
	}
	public void setAbilRifiutoVisto(Boolean abilRifiutoVisto) {
		this.abilRifiutoVisto = abilRifiutoVisto;
	}
	public Boolean getAbilProtocollazioneEntrata() {
		return abilProtocollazioneEntrata;
	}
	public void setAbilProtocollazioneEntrata(Boolean abilProtocollazioneEntrata) {
		this.abilProtocollazioneEntrata = abilProtocollazioneEntrata;
	}
	public Boolean getAbilProtocollazioneUscita() {
		return abilProtocollazioneUscita;
	}
	public void setAbilProtocollazioneUscita(Boolean abilProtocollazioneUscita) {
		this.abilProtocollazioneUscita = abilProtocollazioneUscita;
	}
	public Boolean getAbilProtocollazioneInterna() {
		return abilProtocollazioneInterna;
	}
	public void setAbilProtocollazioneInterna(Boolean abilProtocollazioneInterna) {
		this.abilProtocollazioneInterna = abilProtocollazioneInterna;
	}
	public Boolean getAbilAssegnazioneSmistamento() {
		return abilAssegnazioneSmistamento;
	}
	public void setAbilAssegnazioneSmistamento(Boolean abilAssegnazioneSmistamento) {
		this.abilAssegnazioneSmistamento = abilAssegnazioneSmistamento;
	}
	public Boolean getAbilModAssInviiCC() {
		return abilModAssInviiCC;
	}
	public void setAbilModAssInviiCC(Boolean abilModAssInviiCC) {
		this.abilModAssInviiCC = abilModAssInviiCC;
	}
	public Boolean getAbilSetVisionato() {
		return abilSetVisionato;
	}
	public void setAbilSetVisionato(Boolean abilSetVisionato) {
		this.abilSetVisionato = abilSetVisionato;
	}	
	public Boolean getAbilSmista() {
		return abilSmista;
	}
	public void setAbilSmista(Boolean abilSmista) {
		this.abilSmista = abilSmista;
	}
	public Boolean getAbilSmistaCC() {
		return abilSmistaCC;
	}
	public void setAbilSmistaCC(Boolean abilSmistaCC) {
		this.abilSmistaCC = abilSmistaCC;
	}
	public Boolean getAbilCondivisione() {
		return abilCondivisione;
	}
	public void setAbilCondivisione(Boolean abilCondivisione) {
		this.abilCondivisione = abilCondivisione;
	}
	public Boolean getAbilClassificazioneFascicolazione() {
		return abilClassificazioneFascicolazione;
	}
	public void setAbilClassificazioneFascicolazione(Boolean abilClassificazioneFascicolazione) {
		this.abilClassificazioneFascicolazione = abilClassificazioneFascicolazione;
	}
	public Boolean getAbilOrganizza() {
		return abilOrganizza;
	}
	public void setAbilOrganizza(Boolean abilOrganizza) {
		this.abilOrganizza = abilOrganizza;
	}
	public Boolean getAbilModificaDatiRegistrazione() {
		return abilModificaDatiRegistrazione;
	}
	public void setAbilModificaDatiRegistrazione(Boolean abilModificaDatiRegistrazione) {
		this.abilModificaDatiRegistrazione = abilModificaDatiRegistrazione;
	}
	public Boolean getAbilModificaDati() {
		return abilModificaDati;
	}
	public void setAbilModificaDati(Boolean abilModificaDati) {
		this.abilModificaDati = abilModificaDati;
	}
	public Boolean getAbilAvvioIterWF() {
		return abilAvvioIterWF;
	}
	public void setAbilAvvioIterWF(Boolean abilAvvioIterWF) {
		this.abilAvvioIterWF = abilAvvioIterWF;
	}
	public Boolean getAbilAggiuntaFile() {
		return abilAggiuntaFile;
	}
	public void setAbilAggiuntaFile(Boolean abilAggiuntaFile) {
		this.abilAggiuntaFile = abilAggiuntaFile;
	}
	public Boolean getAbilRegAccessoCivico() {
		return abilRegAccessoCivico;
	}
	public void setAbilRegAccessoCivico(Boolean abilRegAccessoCivico) {
		this.abilRegAccessoCivico = abilRegAccessoCivico;
	}
	public Boolean getAbilInvioPEC() {
		return abilInvioPEC;
	}
	public void setAbilInvioPEC(Boolean abilInvioPEC) {
		this.abilInvioPEC = abilInvioPEC;
	}
	public Boolean getFlgInvioPECMulti() {
		return flgInvioPECMulti;
	}
	public void setFlgInvioPECMulti(Boolean flgInvioPECMulti) {
		this.flgInvioPECMulti = flgInvioPECMulti;
	}
	public Boolean getAbilInvioPEO() {
		return abilInvioPEO;
	}
	public void setAbilInvioPEO(Boolean abilInvioPEO) {
		this.abilInvioPEO = abilInvioPEO;
	}
	public Boolean getAbilRichAnnullamentoReg() {
		return abilRichAnnullamentoReg;
	}
	public void setAbilRichAnnullamentoReg(Boolean abilRichAnnullamentoReg) {
		this.abilRichAnnullamentoReg = abilRichAnnullamentoReg;
	}
	public Boolean getAbilModificaRichAnnullamentoReg() {
		return abilModificaRichAnnullamentoReg;
	}
	public void setAbilModificaRichAnnullamentoReg(Boolean abilModificaRichAnnullamentoReg) {
		this.abilModificaRichAnnullamentoReg = abilModificaRichAnnullamentoReg;
	}
	public Boolean getAbilEliminaRichAnnullamentoReg() {
		return abilEliminaRichAnnullamentoReg;
	}
	public void setAbilEliminaRichAnnullamentoReg(Boolean abilEliminaRichAnnullamentoReg) {
		this.abilEliminaRichAnnullamentoReg = abilEliminaRichAnnullamentoReg;
	}
	public Boolean getAbilAnnullamentoReg() {
		return abilAnnullamentoReg;
	}
	public void setAbilAnnullamentoReg(Boolean abilAnnullamentoReg) {
		this.abilAnnullamentoReg = abilAnnullamentoReg;
	}
	public Boolean getAbilPresaInCarico() {
		return abilPresaInCarico;
	}
	public void setAbilPresaInCarico(Boolean abilPresaInCarico) {
		this.abilPresaInCarico = abilPresaInCarico;
	}
	public Boolean getAbilRestituzione() {
		return abilRestituzione;
	}
	public void setAbilRestituzione(Boolean abilRestituzione) {
		this.abilRestituzione = abilRestituzione;
	}
	public Boolean getAbilInvioConferma() {
		return abilInvioConferma;
	}
	public void setAbilInvioConferma(Boolean abilInvioConferma) {
		this.abilInvioConferma = abilInvioConferma;
	}
	public Boolean getAbilInvioAggiornamento() {
		return abilInvioAggiornamento;
	}
	public void setAbilInvioAggiornamento(Boolean abilInvioAggiornamento) {
		this.abilInvioAggiornamento = abilInvioAggiornamento;
	}
	public Boolean getAbilInvioAnnullamento() {
		return abilInvioAnnullamento;
	}
	public void setAbilInvioAnnullamento(Boolean abilInvioAnnullamento) {
		this.abilInvioAnnullamento = abilInvioAnnullamento;
	}
	public Boolean getAbilArchiviazione() {
		return abilArchiviazione;
	}
	public void setAbilArchiviazione(Boolean abilArchiviazione) {
		this.abilArchiviazione = abilArchiviazione;
	}
	public Boolean getAbilOsservazioniNotifiche() {
		return abilOsservazioniNotifiche;
	}
	public void setAbilOsservazioniNotifiche(Boolean abilOsservazioniNotifiche) {
		this.abilOsservazioniNotifiche = abilOsservazioniNotifiche;
	}
	public Boolean getAbilFirma() {
		return abilFirma;
	}
	public void setAbilFirma(Boolean abilFirma) {
		this.abilFirma = abilFirma;
	}
	public Boolean getAbilFirmaProtocolla() {
		return abilFirmaProtocolla;
	}
	public void setAbilFirmaProtocolla(Boolean abilFirmaProtocolla) {
		this.abilFirmaProtocolla = abilFirmaProtocolla;
	}
	public Boolean getAbilVistoElettronico() {
		return abilVistoElettronico;
	}
	public void setAbilVistoElettronico(Boolean abilVistoElettronico) {
		this.abilVistoElettronico = abilVistoElettronico;
	}
	public Boolean getAbilStampaCopertina() {
		return abilStampaCopertina;
	}
	public void setAbilStampaCopertina(Boolean abilStampaCopertina) {
		this.abilStampaCopertina = abilStampaCopertina;
	}
	public Boolean getAbilStampaRicevuta() {
		return abilStampaRicevuta;
	}
	public void setAbilStampaRicevuta(Boolean abilStampaRicevuta) {
		this.abilStampaRicevuta = abilStampaRicevuta;
	}
	public Boolean getAbilRispondi() {
		return abilRispondi;
	}
	public void setAbilRispondi(Boolean abilRispondi) {
		this.abilRispondi = abilRispondi;
	}
	public Boolean getAbilRispondiUscita() {
		return abilRispondiUscita;
	}
	public void setAbilRispondiUscita(Boolean abilRispondiUscita) {
		this.abilRispondiUscita = abilRispondiUscita;
	}	
	public Boolean getAbilStampaEtichetta() {
		return abilStampaEtichetta;
	}
	public void setAbilStampaEtichetta(Boolean abilStampaEtichetta) {
		this.abilStampaEtichetta = abilStampaEtichetta;
	}
	public Boolean getAbilNuovoComeCopia() {
		return abilNuovoComeCopia;
	}
	public void setAbilNuovoComeCopia(Boolean abilNuovoComeCopia) {
		this.abilNuovoComeCopia = abilNuovoComeCopia;
	}
	public Boolean getAbilModificaTipologia() {
		return abilModificaTipologia;
	}
	public void setAbilModificaTipologia(Boolean abilModificaTipologia) {
		this.abilModificaTipologia = abilModificaTipologia;
	}		
	public Boolean getAbilModificaCodAttoCMTO() {
		return abilModificaCodAttoCMTO;
	}
	public void setAbilModificaCodAttoCMTO(Boolean abilModificaCodAttoCMTO) {
		this.abilModificaCodAttoCMTO = abilModificaCodAttoCMTO;
	}
	public Boolean getAbilInviaALibroFirmaVistoRegCont() {
		return abilInviaALibroFirmaVistoRegCont;
	}
	public void setAbilInviaALibroFirmaVistoRegCont(Boolean abilInviaALibroFirmaVistoRegCont) {
		this.abilInviaALibroFirmaVistoRegCont = abilInviaALibroFirmaVistoRegCont;
	}
	public Boolean getAbilImpostaStatoAlVistoRegCont() {
		return abilImpostaStatoAlVistoRegCont;
	}
	public void setAbilImpostaStatoAlVistoRegCont(Boolean abilImpostaStatoAlVistoRegCont) {
		this.abilImpostaStatoAlVistoRegCont = abilImpostaStatoAlVistoRegCont;
	}
	public Boolean getAbilTogliaDaLibroFirmaVistoRegCont() {
		return abilTogliaDaLibroFirmaVistoRegCont;
	}
	public void setAbilTogliaDaLibroFirmaVistoRegCont(Boolean abilTogliaDaLibroFirmaVistoRegCont) {
		this.abilTogliaDaLibroFirmaVistoRegCont = abilTogliaDaLibroFirmaVistoRegCont;
	}
	public Boolean getConDatiAnnullati() {
		return conDatiAnnullati;
	}
	public void setConDatiAnnullati(Boolean conDatiAnnullati) {
		this.conDatiAnnullati = conDatiAnnullati;
	}
	public String getListaDatiAnnullati() {
		return listaDatiAnnullati;
	}
	public void setListaDatiAnnullati(String listaDatiAnnullati) {
		this.listaDatiAnnullati = listaDatiAnnullati;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public Boolean getFlgPEC() {
		return flgPEC;
	}
	public void setFlgPEC(Boolean flgPEC) {
		this.flgPEC = flgPEC;
	}
	public Boolean getFlgCasellaIstituzionale() {
		return flgCasellaIstituzionale;
	}
	public void setFlgCasellaIstituzionale(Boolean flgCasellaIstituzionale) {
		this.flgCasellaIstituzionale = flgCasellaIstituzionale;
	}
	public Boolean getFlgDichIPA() {
		return flgDichIPA;
	}
	public void setFlgDichIPA(Boolean flgDichIPA) {
		this.flgDichIPA = flgDichIPA;
	}
	public String getGestorePEC() {
		return gestorePEC;
	}
	public void setGestorePEC(String gestorePEC) {
		this.gestorePEC = gestorePEC;
	}
	public String getUoProtocollante() {
		return uoProtocollante;
	}
	public void setUoProtocollante(String uoProtocollante) {
		this.uoProtocollante = uoProtocollante;
	}
	public String getMotivoVarDatiReg() {
		return motivoVarDatiReg;
	}
	public void setMotivoVarDatiReg(String motivoVarDatiReg) {
		this.motivoVarDatiReg = motivoVarDatiReg;
	}
	public String getIdEmailArrivo() {
		return idEmailArrivo;
	}
	public void setIdEmailArrivo(String idEmailArrivo) {
		this.idEmailArrivo = idEmailArrivo;
	}
	public String getCasellaEmailDestinatario() {
		return casellaEmailDestinatario;
	}
	public void setCasellaEmailDestinatario(String casellaEmailDestinatario) {
		this.casellaEmailDestinatario = casellaEmailDestinatario;
	}
	public Boolean getCasellaIsPEC() {
		return casellaIsPEC;
	}
	public void setCasellaIsPEC(Boolean casellaIsPEC) {
		this.casellaIsPEC = casellaIsPEC;
	}
	public Boolean getEmailArrivoInteroperabile() {
		return emailArrivoInteroperabile;
	}
	public void setEmailArrivoInteroperabile(Boolean emailArrivoInteroperabile) {
		this.emailArrivoInteroperabile = emailArrivoInteroperabile;
	}
	public Boolean getEmailInviataFlgPEC() {
		return emailInviataFlgPEC;
	}
	public void setEmailInviataFlgPEC(Boolean emailInviataFlgPEC) {
		this.emailInviataFlgPEC = emailInviataFlgPEC;
	}
	public Boolean getEmailInviataFlgPEO() {
		return emailInviataFlgPEO;
	}
	public void setEmailInviataFlgPEO(Boolean emailInviataFlgPEO) {
		this.emailInviataFlgPEO = emailInviataFlgPEO;
	}
	public Boolean getInviataMailInteroperabile() {
		return inviataMailInteroperabile;
	}
	public void setInviataMailInteroperabile(Boolean inviataMailInteroperabile) {
		this.inviataMailInteroperabile = inviataMailInteroperabile;
	}
	public String getIdUserConfermaAssegnazione() {
		return idUserConfermaAssegnazione;
	}
	public void setIdUserConfermaAssegnazione(String idUserConfermaAssegnazione) {
		this.idUserConfermaAssegnazione = idUserConfermaAssegnazione;
	}
	public String getDesUserConfermaAssegnazione() {
		return desUserConfermaAssegnazione;
	}
	public void setDesUserConfermaAssegnazione(String desUserConfermaAssegnazione) {
		this.desUserConfermaAssegnazione = desUserConfermaAssegnazione;
	}
	public String getUsernameConfermaAssegnazione() {
		return usernameConfermaAssegnazione;
	}
	public void setUsernameConfermaAssegnazione(String usernameConfermaAssegnazione) {
		this.usernameConfermaAssegnazione = usernameConfermaAssegnazione;
	}
	public Boolean getRicEccezioniInterop() {
		return ricEccezioniInterop;
	}
	public void setRicEccezioniInterop(Boolean ricEccezioniInterop) {
		this.ricEccezioniInterop = ricEccezioniInterop;
	}
	public Boolean getRicAggiornamentiInterop() {
		return ricAggiornamentiInterop;
	}
	public void setRicAggiornamentiInterop(Boolean ricAggiornamentiInterop) {
		this.ricAggiornamentiInterop = ricAggiornamentiInterop;
	}
	public Boolean getRicAnnullamentiInterop() {
		return ricAnnullamentiInterop;
	}
	public void setRicAnnullamentiInterop(Boolean ricAnnullamentiInterop) {
		this.ricAnnullamentiInterop = ricAnnullamentiInterop;
	}
	public String getSegnatura() {
		return segnatura;
	}
	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}
	public Boolean getIsRepertorio() {
		return isRepertorio;
	}
	public void setIsRepertorio(Boolean isRepertorio) {
		this.isRepertorio = isRepertorio;
	}
	public Boolean getIsRegistroFatture() {
		return isRegistroFatture;
	}
	public void setIsRegistroFatture(Boolean isRegistroFatture) {
		this.isRegistroFatture = isRegistroFatture;
	}
	public Boolean getFlgAncheRepertorio() {
		return flgAncheRepertorio;
	}
	public void setFlgAncheRepertorio(Boolean flgAncheRepertorio) {
		this.flgAncheRepertorio = flgAncheRepertorio;
	}
	public String getRepertorio() {
		return repertorio;
	}
	public void setRepertorio(String repertorio) {
		this.repertorio = repertorio;
	}
	public Boolean getAnnoPassato() {
		return annoPassato;
	}
	public void setAnnoPassato(Boolean annoPassato) {
		this.annoPassato = annoPassato;
	}
	public Boolean getProtocolloGenerale() {
		return protocolloGenerale;
	}
	public void setProtocolloGenerale(Boolean protocolloGenerale) {
		this.protocolloGenerale = protocolloGenerale;
	}
	public String getRegistroFatture() {
		return registroFatture;
	}
	public void setRegistroFatture(String registroFatture) {
		this.registroFatture = registroFatture;
	}
	public String getRegNumSecondariaDesGruppo() {
		return regNumSecondariaDesGruppo;
	}
	public void setRegNumSecondariaDesGruppo(String regNumSecondariaDesGruppo) {
		this.regNumSecondariaDesGruppo = regNumSecondariaDesGruppo;
	}	
	public Boolean getFlgCompilazioneModulo() {
		return flgCompilazioneModulo;
	}
	public void setFlgCompilazioneModulo(Boolean flgCompilazioneModulo) {
		this.flgCompilazioneModulo = flgCompilazioneModulo;
	}
	public Boolean getFlgPropostaAtto() {
		return flgPropostaAtto;
	}
	public void setFlgPropostaAtto(Boolean flgPropostaAtto) {
		this.flgPropostaAtto = flgPropostaAtto;
	}
	public String getIdUoProponente() {
		return idUoProponente;
	}
	public void setIdUoProponente(String idUoProponente) {
		this.idUoProponente = idUoProponente;
	}	
	public String getDesDirProponente() {
		return desDirProponente;
	}
	public void setDesDirProponente(String desDirProponente) {
		this.desDirProponente = desDirProponente;
	}	
	public Date getDataAvvioIterAtto() {
		return dataAvvioIterAtto;
	}
	public void setDataAvvioIterAtto(Date dataAvvioIterAtto) {
		this.dataAvvioIterAtto = dataAvvioIterAtto;
	}
	public String getFunzionarioIstruttoreAtto() {
		return funzionarioIstruttoreAtto;
	}
	public void setFunzionarioIstruttoreAtto(String funzionarioIstruttoreAtto) {
		this.funzionarioIstruttoreAtto = funzionarioIstruttoreAtto;
	}
	public String getResponsabileAtto() {
		return responsabileAtto;
	}
	public void setResponsabileAtto(String responsabileAtto) {
		this.responsabileAtto = responsabileAtto;
	}
	public Date getDataFirmaAtto() {
		return dataFirmaAtto;
	}
	public void setDataFirmaAtto(Date dataFirmaAtto) {
		this.dataFirmaAtto = dataFirmaAtto;
	}
	public String getFunzionarioFirmaAtto() {
		return funzionarioFirmaAtto;
	}
	public void setFunzionarioFirmaAtto(String funzionarioFirmaAtto) {
		this.funzionarioFirmaAtto = funzionarioFirmaAtto;
	}
	public Date getDataPubblicazioneAtto() {
		return dataPubblicazioneAtto;
	}
	public void setDataPubblicazioneAtto(Date dataPubblicazioneAtto) {
		this.dataPubblicazioneAtto = dataPubblicazioneAtto;
	}
	public String getLogIterAtto() {
		return logIterAtto;
	}
	public void setLogIterAtto(String logIterAtto) {
		this.logIterAtto = logIterAtto;
	}
	public String getModificatoDispositivoAtto() {
		return modificatoDispositivoAtto;
	}
	public void setModificatoDispositivoAtto(String modificatoDispositivoAtto) {
		this.modificatoDispositivoAtto = modificatoDispositivoAtto;
	}
	public String getIdUserRespProc() {
		return idUserRespProc;
	}
	public void setIdUserRespProc(String idUserRespProc) {
		this.idUserRespProc = idUserRespProc;
	}
	public String getDesUserRespProc() {
		return desUserRespProc;
	}
	public void setDesUserRespProc(String desUserRespProc) {
		this.desUserRespProc = desUserRespProc;
	}
	public String getSceltaIter() {
		return sceltaIter;
	}
	public void setSceltaIter(String sceltaIter) {
		this.sceltaIter = sceltaIter;
	}
	public Date getDataAvvioAtto() {
		return dataAvvioAtto;
	}
	public void setDataAvvioAtto(Date dataAvvioAtto) {
		this.dataAvvioAtto = dataAvvioAtto;
	}
	public Boolean getFlgRichParereRevisori() {
		return flgRichParereRevisori;
	}
	public void setFlgRichParereRevisori(Boolean flgRichParereRevisori) {
		this.flgRichParereRevisori = flgRichParereRevisori;
	}
	public String getSiglaAttoRifSubImpegno() {
		return siglaAttoRifSubImpegno;
	}
	public void setSiglaAttoRifSubImpegno(String siglaAttoRifSubImpegno) {
		this.siglaAttoRifSubImpegno = siglaAttoRifSubImpegno;
	}
	public String getNumeroAttoRifSubImpegno() {
		return numeroAttoRifSubImpegno;
	}
	public void setNumeroAttoRifSubImpegno(String numeroAttoRifSubImpegno) {
		this.numeroAttoRifSubImpegno = numeroAttoRifSubImpegno;
	}
	public String getAnnoAttoRifSubImpegno() {
		return annoAttoRifSubImpegno;
	}
	public void setAnnoAttoRifSubImpegno(String annoAttoRifSubImpegno) {
		this.annoAttoRifSubImpegno = annoAttoRifSubImpegno;
	}
	public Date getDataAttoRifSubImpegno() {
		return dataAttoRifSubImpegno;
	}
	public void setDataAttoRifSubImpegno(Date dataAttoRifSubImpegno) {
		this.dataAttoRifSubImpegno = dataAttoRifSubImpegno;
	}
	public String getIdUoRevisioneOrganigramma() {
		return idUoRevisioneOrganigramma;
	}
	public void setIdUoRevisioneOrganigramma(String idUoRevisioneOrganigramma) {
		this.idUoRevisioneOrganigramma = idUoRevisioneOrganigramma;
	}
	public String getIdUoPadreRevisioneOrganigramma() {
		return idUoPadreRevisioneOrganigramma;
	}
	public void setIdUoPadreRevisioneOrganigramma(String idUoPadreRevisioneOrganigramma) {
		this.idUoPadreRevisioneOrganigramma = idUoPadreRevisioneOrganigramma;
	}
	public String getCodRapidoUoRevisioneOrganigramma() {
		return codRapidoUoRevisioneOrganigramma;
	}
	public void setCodRapidoUoRevisioneOrganigramma(String codRapidoUoRevisioneOrganigramma) {
		this.codRapidoUoRevisioneOrganigramma = codRapidoUoRevisioneOrganigramma;
	}
	public String getNomeUoRevisioneOrganigramma() {
		return nomeUoRevisioneOrganigramma;
	}
	public void setNomeUoRevisioneOrganigramma(String nomeUoRevisioneOrganigramma) {
		this.nomeUoRevisioneOrganigramma = nomeUoRevisioneOrganigramma;
	}
	public String getTipoUoRevisioneOrganigramma() {
		return tipoUoRevisioneOrganigramma;
	}
	public void setTipoUoRevisioneOrganigramma(String tipoUoRevisioneOrganigramma) {
		this.tipoUoRevisioneOrganigramma = tipoUoRevisioneOrganigramma;
	}
	public String getLivelloUoRevisioneOrganigramma() {
		return livelloUoRevisioneOrganigramma;
	}
	public void setLivelloUoRevisioneOrganigramma(String livelloUoRevisioneOrganigramma) {
		this.livelloUoRevisioneOrganigramma = livelloUoRevisioneOrganigramma;
	}
	public Boolean getFlgSkipControlliCartaceo() {
		return flgSkipControlliCartaceo;
	}
	public void setFlgSkipControlliCartaceo(Boolean flgSkipControlliCartaceo) {
		this.flgSkipControlliCartaceo = flgSkipControlliCartaceo;
	}
	public String getSupportoOriginale() {
		return supportoOriginale;
	}
	public void setSupportoOriginale(String supportoOriginale) {
		this.supportoOriginale = supportoOriginale;
	}
	public List<SoggEsternoProtBean> getListaEsibenti() {
		return listaEsibenti;
	}
	public void setListaEsibenti(List<SoggEsternoProtBean> listaEsibenti) {
		this.listaEsibenti = listaEsibenti;
	}
	public List<SoggEsternoProtBean> getListaInteressati() {
		return listaInteressati;
	}
	public void setListaInteressati(List<SoggEsternoProtBean> listaInteressati) {
		this.listaInteressati = listaInteressati;
	}	
	public List<SoggEsternoProtBean> getListaDelegato() {
		return listaDelegato;
	}
	public void setListaDelegato(List<SoggEsternoProtBean> listaDelegato) {
		this.listaDelegato = listaDelegato;
	}
	public List<SoggEsternoProtBean> getListaFirmatari() {
		return listaFirmatari;
	}
	public void setListaFirmatari(List<SoggEsternoProtBean> listaFirmatari) {
		this.listaFirmatari = listaFirmatari;
	}
	public List<AltraViaProtBean> getListaAltreVie() {
		return listaAltreVie;
	}
	public void setListaAltreVie(List<AltraViaProtBean> listaAltreVie) {
		this.listaAltreVie = listaAltreVie;
	}
	public List<DocCollegatoBean> getListaDocumentiCollegati() {
		return listaDocumentiCollegati;
	}
	public void setListaDocumentiCollegati(List<DocCollegatoBean> listaDocumentiCollegati) {
		this.listaDocumentiCollegati = listaDocumentiCollegati;
	}
	public List<AltroRifBean> getListaAltriRiferimenti() {
		return listaAltriRiferimenti;
	}
	public void setListaAltriRiferimenti(List<AltroRifBean> listaAltriRiferimenti) {
		this.listaAltriRiferimenti = listaAltriRiferimenti;
	}
	public String getSiglaProtocolloPregresso() {
		return siglaProtocolloPregresso;
	}
	public void setSiglaProtocolloPregresso(String siglaProtocolloPregresso) {
		this.siglaProtocolloPregresso = siglaProtocolloPregresso;
	}
	public String getNroProtocolloPregresso() {
		return nroProtocolloPregresso;
	}
	public void setNroProtocolloPregresso(String nroProtocolloPregresso) {
		this.nroProtocolloPregresso = nroProtocolloPregresso;
	}
	public String getAnnoProtocolloPregresso() {
		return annoProtocolloPregresso;
	}
	public void setAnnoProtocolloPregresso(String annoProtocolloPregresso) {
		this.annoProtocolloPregresso = annoProtocolloPregresso;
	}
	public Date getDataProtocolloPregresso() {
		return dataProtocolloPregresso;
	}
	public void setDataProtocolloPregresso(Date dataProtocolloPregresso) {
		this.dataProtocolloPregresso = dataProtocolloPregresso;
	}
	public String getSubProtocolloPregresso() {
		return subProtocolloPregresso;
	}
	public void setSubProtocolloPregresso(String subProtocolloPregresso) {
		this.subProtocolloPregresso = subProtocolloPregresso;
	}
	public String getNroIdentificativoWF() {
		return nroIdentificativoWF;
	}
	public void setNroIdentificativoWF(String nroIdentificativoWF) {
		this.nroIdentificativoWF = nroIdentificativoWF;
	}
	public String getAnnoIdentificativoWF() {
		return annoIdentificativoWF;
	}
	public void setAnnoIdentificativoWF(String annoIdentificativoWF) {
		this.annoIdentificativoWF = annoIdentificativoWF;
	}
	public Boolean getFlgCaricamentoPGPregressoDaGUI() {
		return flgCaricamentoPGPregressoDaGUI;
	}
	public void setFlgCaricamentoPGPregressoDaGUI(Boolean flgCaricamentoPGPregressoDaGUI) {
		this.flgCaricamentoPGPregressoDaGUI = flgCaricamentoPGPregressoDaGUI;
	}
	public Boolean getFlgTipoDocConVie() {
		return flgTipoDocConVie;
	}
	public void setFlgTipoDocConVie(Boolean flgTipoDocConVie) {
		this.flgTipoDocConVie = flgTipoDocConVie;
	}
	public Boolean getFlgOggettoNonObblig() {
		return flgOggettoNonObblig;
	}
	public void setFlgOggettoNonObblig(Boolean flgOggettoNonObblig) {
		this.flgOggettoNonObblig = flgOggettoNonObblig;
	}
	public String getFlgTipoProtModulo() {
		return flgTipoProtModulo;
	}
	public void setFlgTipoProtModulo(String flgTipoProtModulo) {
		this.flgTipoProtModulo = flgTipoProtModulo;
	}
	public String getCodSupportoOrig() {
		return codSupportoOrig;
	}
	public void setCodSupportoOrig(String codSupportoOrig) {
		this.codSupportoOrig = codSupportoOrig;
	}
	public List<ContribuenteBean> getListaContribuenti() {
		return listaContribuenti;
	}
	public void setListaContribuenti(List<ContribuenteBean> listaContribuenti) {
		this.listaContribuenti = listaContribuenti;
	}
	public Date getDataEOraRicezione() {
		return dataEOraRicezione;
	}
	public void setDataEOraRicezione(Date dataEOraRicezione) {
		this.dataEOraRicezione = dataEOraRicezione;
	}
	public Map<String, Object> getValori() {
		return valori;
	}
	public void setValori(Map<String, Object> valori) {
		this.valori = valori;
	}
	public Map<String, String> getTipiValori() {
		return tipiValori;
	}
	public void setTipiValori(Map<String, String> tipiValori) {
		this.tipiValori = tipiValori;
	}
	public Boolean getAttivaTimbroTipologia() {
		return attivaTimbroTipologia;
	}
	public void setAttivaTimbroTipologia(Boolean attivaTimbroTipologia) {
		this.attivaTimbroTipologia = attivaTimbroTipologia;
	}
	public Boolean getIsFilePrimarioPubblicato() {
		return isFilePrimarioPubblicato;
	}
	public void setIsFilePrimarioPubblicato(Boolean isFilePrimarioPubblicato) {
		this.isFilePrimarioPubblicato = isFilePrimarioPubblicato;
	}
	public String getDefaultAzioneStampaEtichetta() {
		return defaultAzioneStampaEtichetta;
	}
	public void setDefaultAzioneStampaEtichetta(String defaultAzioneStampaEtichetta) {
		this.defaultAzioneStampaEtichetta = defaultAzioneStampaEtichetta;
	}
	public Boolean getFlgRichiestaAccessoAtti() {
		return flgRichiestaAccessoAtti;
	}
	public void setFlgRichiestaAccessoAtti(Boolean flgRichiestaAccessoAtti) {
		this.flgRichiestaAccessoAtti = flgRichiestaAccessoAtti;
	}
	public String getTipoRichiedente() {
		return tipoRichiedente;
	}
	public void setTipoRichiedente(String tipoRichiedente) {
		this.tipoRichiedente = tipoRichiedente;
	}
	public String getCodStatoRichAccessoAtti() {
		return codStatoRichAccessoAtti;
	}
	public void setCodStatoRichAccessoAtti(String codStatoRichAccessoAtti) {
		this.codStatoRichAccessoAtti = codStatoRichAccessoAtti;
	}
	public String getDesStatoRichAccessoAtti() {
		return desStatoRichAccessoAtti;
	}
	public void setDesStatoRichAccessoAtti(String desStatoRichAccessoAtti) {
		this.desStatoRichAccessoAtti = desStatoRichAccessoAtti;
	}
	public String getSiglaPraticaSuSistUfficioRichiedente() {
		return siglaPraticaSuSistUfficioRichiedente;
	}
	public void setSiglaPraticaSuSistUfficioRichiedente(String siglaPraticaSuSistUfficioRichiedente) {
		this.siglaPraticaSuSistUfficioRichiedente = siglaPraticaSuSistUfficioRichiedente;
	}
	public String getNumeroPraticaSuSistUfficioRichiedente() {
		return numeroPraticaSuSistUfficioRichiedente;
	}
	public void setNumeroPraticaSuSistUfficioRichiedente(String numeroPraticaSuSistUfficioRichiedente) {
		this.numeroPraticaSuSistUfficioRichiedente = numeroPraticaSuSistUfficioRichiedente;
	}
	public String getAnnoPraticaSuSistUfficioRichiedente() {
		return annoPraticaSuSistUfficioRichiedente;
	}
	public void setAnnoPraticaSuSistUfficioRichiedente(String annoPraticaSuSistUfficioRichiedente) {
		this.annoPraticaSuSistUfficioRichiedente = annoPraticaSuSistUfficioRichiedente;
	}
	public List<MittenteProtBean> getListaRichiedentiInterni() {
		return listaRichiedentiInterni;
	}
	public void setListaRichiedentiInterni(List<MittenteProtBean> listaRichiedentiInterni) {
		this.listaRichiedentiInterni = listaRichiedentiInterni;
	}
	public Boolean getFlgRichAttiFabbricaVisuraSue() {
		return flgRichAttiFabbricaVisuraSue;
	}
	public void setFlgRichAttiFabbricaVisuraSue(Boolean flgRichAttiFabbricaVisuraSue) {
		this.flgRichAttiFabbricaVisuraSue = flgRichAttiFabbricaVisuraSue;
	}
	public Boolean getFlgRichModificheVisuraSue() {
		return flgRichModificheVisuraSue;
	}
	public void setFlgRichModificheVisuraSue(Boolean flgRichModificheVisuraSue) {
		this.flgRichModificheVisuraSue = flgRichModificheVisuraSue;
	}
	public List<AttiRichiestiBean> getListaAttiRichiesti() {
		return listaAttiRichiesti;
	}
	public void setListaAttiRichiesti(List<AttiRichiestiBean> listaAttiRichiesti) {
		this.listaAttiRichiesti = listaAttiRichiesti;
	}
	public Boolean getFlgAltriAttiDaRicercareVisuraSue() {
		return flgAltriAttiDaRicercareVisuraSue;
	}
	public void setFlgAltriAttiDaRicercareVisuraSue(Boolean flgAltriAttiDaRicercareVisuraSue) {
		this.flgAltriAttiDaRicercareVisuraSue = flgAltriAttiDaRicercareVisuraSue;
	}
	public List<SoggEsternoProtBean> getListaRichiedentiDelegati() {
		return listaRichiedentiDelegati;
	}
	public void setListaRichiedentiDelegati(List<SoggEsternoProtBean> listaRichiedentiDelegati) {
		this.listaRichiedentiDelegati = listaRichiedentiDelegati;
	}
	public String getMotivoRichiestaAccessoAtti() {
		return motivoRichiestaAccessoAtti;
	}
	public void setMotivoRichiestaAccessoAtti(String motivoRichiestaAccessoAtti) {
		this.motivoRichiestaAccessoAtti = motivoRichiestaAccessoAtti;
	}
	public String getDettagliRichiestaAccessoAtti() {
		return dettagliRichiestaAccessoAtti;
	}
	public void setDettagliRichiestaAccessoAtti(String dettagliRichiestaAccessoAtti) {
		this.dettagliRichiestaAccessoAtti = dettagliRichiestaAccessoAtti;
	}
	public String getIdIncaricatoPrelievoPerUffRichiedente() {
		return idIncaricatoPrelievoPerUffRichiedente;
	}
	public void setIdIncaricatoPrelievoPerUffRichiedente(String idIncaricatoPrelievoPerUffRichiedente) {
		this.idIncaricatoPrelievoPerUffRichiedente = idIncaricatoPrelievoPerUffRichiedente;
	}
	public String getUsernameIncaricatoPrelievoPerUffRichiedente() {
		return usernameIncaricatoPrelievoPerUffRichiedente;
	}
	public void setUsernameIncaricatoPrelievoPerUffRichiedente(String usernameIncaricatoPrelievoPerUffRichiedente) {
		this.usernameIncaricatoPrelievoPerUffRichiedente = usernameIncaricatoPrelievoPerUffRichiedente;
	}
	public String getCodRapidoIncaricatoPrelievoPerUffRichiedente() {
		return codRapidoIncaricatoPrelievoPerUffRichiedente;
	}
	public void setCodRapidoIncaricatoPrelievoPerUffRichiedente(String codRapidoIncaricatoPrelievoPerUffRichiedente) {
		this.codRapidoIncaricatoPrelievoPerUffRichiedente = codRapidoIncaricatoPrelievoPerUffRichiedente;
	}
	public String getCognomeIncaricatoPrelievoPerUffRichiedente() {
		return cognomeIncaricatoPrelievoPerUffRichiedente;
	}
	public void setCognomeIncaricatoPrelievoPerUffRichiedente(String cognomeIncaricatoPrelievoPerUffRichiedente) {
		this.cognomeIncaricatoPrelievoPerUffRichiedente = cognomeIncaricatoPrelievoPerUffRichiedente;
	}
	public String getNomeIncaricatoPrelievoPerUffRichiedente() {
		return nomeIncaricatoPrelievoPerUffRichiedente;
	}
	public void setNomeIncaricatoPrelievoPerUffRichiedente(String nomeIncaricatoPrelievoPerUffRichiedente) {
		this.nomeIncaricatoPrelievoPerUffRichiedente = nomeIncaricatoPrelievoPerUffRichiedente;
	}
	public String getTelefonoIncaricatoPrelievoPerUffRichiedente() {
		return telefonoIncaricatoPrelievoPerUffRichiedente;
	}
	public void setTelefonoIncaricatoPrelievoPerUffRichiedente(String telefonoIncaricatoPrelievoPerUffRichiedente) {
		this.telefonoIncaricatoPrelievoPerUffRichiedente = telefonoIncaricatoPrelievoPerUffRichiedente;
	}
	public String getCognomeIncaricatoPrelievoPerRichEsterno() {
		return cognomeIncaricatoPrelievoPerRichEsterno;
	}
	public void setCognomeIncaricatoPrelievoPerRichEsterno(String cognomeIncaricatoPrelievoPerRichEsterno) {
		this.cognomeIncaricatoPrelievoPerRichEsterno = cognomeIncaricatoPrelievoPerRichEsterno;
	}
	public String getNomeIncaricatoPrelievoPerRichEsterno() {
		return nomeIncaricatoPrelievoPerRichEsterno;
	}
	public void setNomeIncaricatoPrelievoPerRichEsterno(String nomeIncaricatoPrelievoPerRichEsterno) {
		this.nomeIncaricatoPrelievoPerRichEsterno = nomeIncaricatoPrelievoPerRichEsterno;
	}
	public String getCodiceFiscaleIncaricatoPrelievoPerRichEsterno() {
		return codiceFiscaleIncaricatoPrelievoPerRichEsterno;
	}
	public void setCodiceFiscaleIncaricatoPrelievoPerRichEsterno(String codiceFiscaleIncaricatoPrelievoPerRichEsterno) {
		this.codiceFiscaleIncaricatoPrelievoPerRichEsterno = codiceFiscaleIncaricatoPrelievoPerRichEsterno;
	}
	public String getEmailIncaricatoPrelievoPerRichEsterno() {
		return emailIncaricatoPrelievoPerRichEsterno;
	}
	public void setEmailIncaricatoPrelievoPerRichEsterno(String emailIncaricatoPrelievoPerRichEsterno) {
		this.emailIncaricatoPrelievoPerRichEsterno = emailIncaricatoPrelievoPerRichEsterno;
	}
	public String getTelefonoIncaricatoPrelievoPerRichEsterno() {
		return telefonoIncaricatoPrelievoPerRichEsterno;
	}
	public void setTelefonoIncaricatoPrelievoPerRichEsterno(String telefonoIncaricatoPrelievoPerRichEsterno) {
		this.telefonoIncaricatoPrelievoPerRichEsterno = telefonoIncaricatoPrelievoPerRichEsterno;
	}
	public String getDataAppuntamento() {
		return dataAppuntamento;
	}
	public void setDataAppuntamento(String dataAppuntamento) {
		this.dataAppuntamento = dataAppuntamento;
	}
	public String getOrarioAppuntamento() {
		return orarioAppuntamento;
	}
	public void setOrarioAppuntamento(String orarioAppuntamento) {
		this.orarioAppuntamento = orarioAppuntamento;
	}
	public String getProvenienzaAppuntamento() {
		return provenienzaAppuntamento;
	}
	public void setProvenienzaAppuntamento(String provenienzaAppuntamento) {
		this.provenienzaAppuntamento = provenienzaAppuntamento;
	}
	public Date getDataPrelievo() {
		return dataPrelievo;
	}
	public void setDataPrelievo(Date dataPrelievo) {
		this.dataPrelievo = dataPrelievo;
	}
	public String getDataRestituzionePrelievo() {
		return dataRestituzionePrelievo;
	}
	public void setDataRestituzionePrelievo(String dataRestituzionePrelievo) {
		this.dataRestituzionePrelievo = dataRestituzionePrelievo;
	}
	public String getRestituzionePrelievoAttestataDa() {
		return restituzionePrelievoAttestataDa;
	}
	public void setRestituzionePrelievoAttestataDa(String restituzionePrelievoAttestataDa) {
		this.restituzionePrelievoAttestataDa = restituzionePrelievoAttestataDa;
	}
	public String getIdNodoDefaultRicercaAtti() {
		return idNodoDefaultRicercaAtti;
	}
	public void setIdNodoDefaultRicercaAtti(String idNodoDefaultRicercaAtti) {
		this.idNodoDefaultRicercaAtti = idNodoDefaultRicercaAtti;
	}
	public String getIdEmailProv() {
		return idEmailProv;
	}
	public void setIdEmailProv(String idEmailProv) {
		this.idEmailProv = idEmailProv;
	}
	public Boolean getFlgNonArchiviareEmail() {
		return flgNonArchiviareEmail;
	}
	public void setFlgNonArchiviareEmail(Boolean flgNonArchiviareEmail) {
		this.flgNonArchiviareEmail = flgNonArchiviareEmail;
	}
	public Boolean getAbilVisualizzaDettStdProt() {
		return abilVisualizzaDettStdProt;
	}
	public void setAbilVisualizzaDettStdProt(Boolean abilVisualizzaDettStdProt) {
		this.abilVisualizzaDettStdProt = abilVisualizzaDettStdProt;
	}
	public Boolean getAbilInvioInApprovazione() {
		return abilInvioInApprovazione;
	}
	public void setAbilInvioInApprovazione(Boolean abilInvioInApprovazione) {
		this.abilInvioInApprovazione = abilInvioInApprovazione;
	}
	public Boolean getAbilApprovazione() {
		return abilApprovazione;
	}
	public void setAbilApprovazione(Boolean abilApprovazione) {
		this.abilApprovazione = abilApprovazione;
	}
	public Boolean getAbilInvioEsitoVerificaArchivio() {
		return abilInvioEsitoVerificaArchivio;
	}
	public void setAbilInvioEsitoVerificaArchivio(Boolean abilInvioEsitoVerificaArchivio) {
		this.abilInvioEsitoVerificaArchivio = abilInvioEsitoVerificaArchivio;
	}
	public Boolean getAbilAbilitaAppuntamentoDaAgenda() {
		return abilAbilitaAppuntamentoDaAgenda;
	}
	public void setAbilAbilitaAppuntamentoDaAgenda(Boolean abilAbilitaAppuntamentoDaAgenda) {
		this.abilAbilitaAppuntamentoDaAgenda = abilAbilitaAppuntamentoDaAgenda;
	}
	public Boolean getAbilSetAppuntamento() {
		return abilSetAppuntamento;
	}
	public void setAbilSetAppuntamento(Boolean abilSetAppuntamento) {
		this.abilSetAppuntamento = abilSetAppuntamento;
	}
	public Boolean getAbilAnnullamentoAppuntamento() {
		return abilAnnullamentoAppuntamento;
	}
	public void setAbilAnnullamentoAppuntamento(Boolean abilAnnullamentoAppuntamento) {
		this.abilAnnullamentoAppuntamento = abilAnnullamentoAppuntamento;
	}
	public Boolean getAbilRegistraPrelievo() {
		return abilRegistraPrelievo;
	}
	public void setAbilRegistraPrelievo(Boolean abilRegistraPrelievo) {
		this.abilRegistraPrelievo = abilRegistraPrelievo;
	}
	public Boolean getAbilRegistraRestituzione() {
		return abilRegistraRestituzione;
	}
	public void setAbilRegistraRestituzione(Boolean abilRegistraRestituzione) {
		this.abilRegistraRestituzione = abilRegistraRestituzione;
	}
	public Boolean getAbilAnnullamento() {
		return abilAnnullamento;
	}
	public void setAbilAnnullamento(Boolean abilAnnullamento) {
		this.abilAnnullamento = abilAnnullamento;
	}
	public Boolean getAbilStampaFoglioPrelievo() {
		return abilStampaFoglioPrelievo;
	}
	public void setAbilStampaFoglioPrelievo(Boolean abilStampaFoglioPrelievo) {
		this.abilStampaFoglioPrelievo = abilStampaFoglioPrelievo;
	}
	public Boolean getAbilRichAccessoAttiChiusura() {
		return abilRichAccessoAttiChiusura;
	}
	public void setAbilRichAccessoAttiChiusura(Boolean abilRichAccessoAttiChiusura) {
		this.abilRichAccessoAttiChiusura = abilRichAccessoAttiChiusura;
	}
	public Boolean getAbilRichAccessoAttiRiapertura() {
		return abilRichAccessoAttiRiapertura;
	}
	public void setAbilRichAccessoAttiRiapertura(Boolean abilRichAccessoAttiRiapertura) {
		this.abilRichAccessoAttiRiapertura = abilRichAccessoAttiRiapertura;
	}
	public Boolean getAbilRichAccessoAttiRipristino() {
		return abilRichAccessoAttiRipristino;
	}
	public void setAbilRichAccessoAttiRipristino(Boolean abilRichAccessoAttiRipristino) {
		this.abilRichAccessoAttiRipristino = abilRichAccessoAttiRipristino;
	}		
	public String getIdRicercatoreVisuraSUE() {
		return idRicercatoreVisuraSUE;
	}
	public void setIdRicercatoreVisuraSUE(String idRicercatoreVisuraSUE) {
		this.idRicercatoreVisuraSUE = idRicercatoreVisuraSUE;
	}
	public List<String> getErroriArchivi() {
		return erroriArchivi;
	}
	public void setErroriArchivi(List<String> erroriArchivi) {
		this.erroriArchivi = erroriArchivi;
	}
	public String getIdUDTrasmessaInUscitaCon() {
		return idUDTrasmessaInUscitaCon;
	}
	public void setIdUDTrasmessaInUscitaCon(String idUDTrasmessaInUscitaCon) {
		this.idUDTrasmessaInUscitaCon = idUDTrasmessaInUscitaCon;
	}
	public String getEstremiUDTrasmessoInUscitaCon() {
		return estremiUDTrasmessoInUscitaCon;
	}
	public void setEstremiUDTrasmessoInUscitaCon(String estremiUDTrasmessoInUscitaCon) {
		this.estremiUDTrasmessoInUscitaCon = estremiUDTrasmessoInUscitaCon;
	}
	public List<TipoNumerazioneBean> getListaTipiNumerazioneDaDare() {
		return listaTipiNumerazioneDaDare;
	}
	public void setListaTipiNumerazioneDaDare(List<TipoNumerazioneBean> listaTipiNumerazioneDaDare) {
		this.listaTipiNumerazioneDaDare = listaTipiNumerazioneDaDare;
	}
	public List<String> getErroriEstensioniFile() {
		return erroriEstensioniFile;
	}
	public void setErroriEstensioniFile(List<String> erroriEstensioniFile) {
		this.erroriEstensioniFile = erroriEstensioniFile;
	}
	public Map<String, String> getFileInErrors() {
		return fileInErrors;
	}
	public void setFileInErrors(Map<String, String> fileInErrors) {
		this.fileInErrors = fileInErrors;
	}
	public String getCodCategoriaRegPrimariaRisposta() {
		return codCategoriaRegPrimariaRisposta;
	}
	public void setCodCategoriaRegPrimariaRisposta(String codCategoriaRegPrimariaRisposta) {
		this.codCategoriaRegPrimariaRisposta = codCategoriaRegPrimariaRisposta;
	}
	public String getSiglaRegistroRegPrimariaRisposta() {
		return siglaRegistroRegPrimariaRisposta;
	}
	public void setSiglaRegistroRegPrimariaRisposta(String siglaRegistroRegPrimariaRisposta) {
		this.siglaRegistroRegPrimariaRisposta = siglaRegistroRegPrimariaRisposta;
	}
	public String getDesRegistroRegPrimariaRisposta() {
		return desRegistroRegPrimariaRisposta;
	}
	public void setDesRegistroRegPrimariaRisposta(String desRegistroRegPrimariaRisposta) {
		this.desRegistroRegPrimariaRisposta = desRegistroRegPrimariaRisposta;
	}
	public String getCategoriaRepertorioRegPrimariaRisposta() {
		return categoriaRepertorioRegPrimariaRisposta;
	}
	public void setCategoriaRepertorioRegPrimariaRisposta(String categoriaRepertorioRegPrimariaRisposta) {
		this.categoriaRepertorioRegPrimariaRisposta = categoriaRepertorioRegPrimariaRisposta;
	}
	public String getCodCategoriaRegSecondariaRisposta() {
		return codCategoriaRegSecondariaRisposta;
	}
	public void setCodCategoriaRegSecondariaRisposta(String codCategoriaRegSecondariaRisposta) {
		this.codCategoriaRegSecondariaRisposta = codCategoriaRegSecondariaRisposta;
	}
	public String getSiglaRegistroRegSecondariaRisposta() {
		return siglaRegistroRegSecondariaRisposta;
	}
	public void setSiglaRegistroRegSecondariaRisposta(String siglaRegistroRegSecondariaRisposta) {
		this.siglaRegistroRegSecondariaRisposta = siglaRegistroRegSecondariaRisposta;
	}
	public String getDesRegistroRegSecondariaRisposta() {
		return desRegistroRegSecondariaRisposta;
	}
	public void setDesRegistroRegSecondariaRisposta(String desRegistroRegSecondariaRisposta) {
		this.desRegistroRegSecondariaRisposta = desRegistroRegSecondariaRisposta;
	}
	public String getCategoriaRepertorioRegSecondariaRisposta() {
		return categoriaRepertorioRegSecondariaRisposta;
	}
	public void setCategoriaRepertorioRegSecondariaRisposta(String categoriaRepertorioRegSecondariaRisposta) {
		this.categoriaRepertorioRegSecondariaRisposta = categoriaRepertorioRegSecondariaRisposta;
	}
	public Date getDtEsecutivita() {
		return dtEsecutivita;
	}
	public void setDtEsecutivita(Date dtEsecutivita) {
		this.dtEsecutivita = dtEsecutivita;
	}
	public Boolean getFlgImmediatamenteEseg() {
		return flgImmediatamenteEseg;
	}
	public void setFlgImmediatamenteEseg(Boolean flgImmediatamenteEseg) {
		this.flgImmediatamenteEseg = flgImmediatamenteEseg;
	}
	public String getIdGruppoLiquidatori() {
		return idGruppoLiquidatori;
	}
	public void setIdGruppoLiquidatori(String idGruppoLiquidatori) {
		this.idGruppoLiquidatori = idGruppoLiquidatori;
	}
	public String getNomeGruppoLiquidatori() {
		return nomeGruppoLiquidatori;
	}
	public void setNomeGruppoLiquidatori(String nomeGruppoLiquidatori) {
		this.nomeGruppoLiquidatori = nomeGruppoLiquidatori;
	}
	public String getCodGruppoLiquidatori() {
		return codGruppoLiquidatori;
	}
	public void setCodGruppoLiquidatori(String codGruppoLiquidatori) {
		this.codGruppoLiquidatori = codGruppoLiquidatori;
	}
	public String getIdAssegnatarioProcesso() {
		return idAssegnatarioProcesso;
	}
	public void setIdAssegnatarioProcesso(String idAssegnatarioProcesso) {
		this.idAssegnatarioProcesso = idAssegnatarioProcesso;
	}
	public String getDesAssegnatarioProcesso() {
		return desAssegnatarioProcesso;
	}
	public void setDesAssegnatarioProcesso(String desAssegnatarioProcesso) {
		this.desAssegnatarioProcesso = desAssegnatarioProcesso;
	}
	public String getNriLivelliAssegnatarioProcesso() {
		return nriLivelliAssegnatarioProcesso;
	}
	public void setNriLivelliAssegnatarioProcesso(String nriLivelliAssegnatarioProcesso) {
		this.nriLivelliAssegnatarioProcesso = nriLivelliAssegnatarioProcesso;
	}	
	public String getIdGruppoRagioneria() {
		return idGruppoRagioneria;
	}
	public void setIdGruppoRagioneria(String idGruppoRagioneria) {
		this.idGruppoRagioneria = idGruppoRagioneria;
	}
	public String getNomeGruppoRagioneria() {
		return nomeGruppoRagioneria;
	}
	public void setNomeGruppoRagioneria(String nomeGruppoRagioneria) {
		this.nomeGruppoRagioneria = nomeGruppoRagioneria;
	}
	public String getCodGruppoRagioneria() {
		return codGruppoRagioneria;
	}
	public void setCodGruppoRagioneria(String codGruppoRagioneria) {
		this.codGruppoRagioneria = codGruppoRagioneria;
	}
	public List<DestInvioCCProcessoBean> getDestinatariInvioCCProcesso() {
		return destinatariInvioCCProcesso;
	}
	public void setDestinatariInvioCCProcesso(List<DestInvioCCProcessoBean> destinatariInvioCCProcesso) {
		this.destinatariInvioCCProcesso = destinatariInvioCCProcesso;
	}
	public List<PeriziaBean> getListaPerizie() {
		return listaPerizie;
	}
	public void setListaPerizie(List<PeriziaBean> listaPerizie) {
		this.listaPerizie = listaPerizie;
	}
	public Boolean getAbilInvioEmailRicevuta() {
		return abilInvioEmailRicevuta;
	}
	public void setAbilInvioEmailRicevuta(Boolean abilInvioEmailRicevuta) {
		this.abilInvioEmailRicevuta = abilInvioEmailRicevuta;
	}
	public Boolean getAbilProrogaPubblicazione() {
		return abilProrogaPubblicazione;
	}
	public void setAbilProrogaPubblicazione(Boolean abilProrogaPubblicazione) {
		this.abilProrogaPubblicazione = abilProrogaPubblicazione;
	}
	public Boolean getAbilAnnullamentoPubblicazione() {
		return abilAnnullamentoPubblicazione;
	}
	public void setAbilAnnullamentoPubblicazione(Boolean abilAnnullamentoPubblicazione) {
		this.abilAnnullamentoPubblicazione = abilAnnullamentoPubblicazione;
	}
	public Boolean getAbilRettificaPubblicazione() {
		return abilRettificaPubblicazione;
	}
	public void setAbilRettificaPubblicazione(Boolean abilRettificaPubblicazione) {
		this.abilRettificaPubblicazione = abilRettificaPubblicazione;
	}
	public String getIdProcessTypeIterWFRisposta() {
		return idProcessTypeIterWFRisposta;
	}
	public void setIdProcessTypeIterWFRisposta(String idProcessTypeIterWFRisposta) {
		this.idProcessTypeIterWFRisposta = idProcessTypeIterWFRisposta;
	}
	public String getNomeProcessTypeIterWFRisposta() {
		return nomeProcessTypeIterWFRisposta;
	}
	public void setNomeProcessTypeIterWFRisposta(String nomeProcessTypeIterWFRisposta) {
		this.nomeProcessTypeIterWFRisposta = nomeProcessTypeIterWFRisposta;
	}
	public String getNomeTipoFlussoIterWFRisposta() {
		return nomeTipoFlussoIterWFRisposta;
	}
	public void setNomeTipoFlussoIterWFRisposta(String nomeTipoFlussoIterWFRisposta) {
		this.nomeTipoFlussoIterWFRisposta = nomeTipoFlussoIterWFRisposta;
	}
	public String getIdDocTypeIterWFRisposta() {
		return idDocTypeIterWFRisposta;
	}
	public void setIdDocTypeIterWFRisposta(String idDocTypeIterWFRisposta) {
		this.idDocTypeIterWFRisposta = idDocTypeIterWFRisposta;
	}
	public String getNomeDocTypeIterWFRisposta() {
		return nomeDocTypeIterWFRisposta;
	}
	public void setNomeDocTypeIterWFRisposta(String nomeDocTypeIterWFRisposta) {
		this.nomeDocTypeIterWFRisposta = nomeDocTypeIterWFRisposta;
	}
	public String getCodCategoriaNumIniIterWFRisposta() {
		return codCategoriaNumIniIterWFRisposta;
	}
	public void setCodCategoriaNumIniIterWFRisposta(String codCategoriaNumIniIterWFRisposta) {
		this.codCategoriaNumIniIterWFRisposta = codCategoriaNumIniIterWFRisposta;
	}
	public String getSiglaNumIniIterWFRisposta() {
		return siglaNumIniIterWFRisposta;
	}
	public void setSiglaNumIniIterWFRisposta(String siglaNumIniIterWFRisposta) {
		this.siglaNumIniIterWFRisposta = siglaNumIniIterWFRisposta;
	}
	public String getGruppoProtocollantePostelMittente() {
		return gruppoProtocollantePostelMittente;
	}
	public void setGruppoProtocollantePostelMittente(String gruppoProtocollantePostelMittente) {
		this.gruppoProtocollantePostelMittente = gruppoProtocollantePostelMittente;
	}
	public String getTipoToponimoMittentePostel() {
		return tipoToponimoMittentePostel;
	}
	public void setTipoToponimoMittentePostel(String tipoToponimoMittentePostel) {
		this.tipoToponimoMittentePostel = tipoToponimoMittentePostel;
	}
	public String getToponimoMittentePostel() {
		return toponimoMittentePostel;
	}
	public void setToponimoMittentePostel(String toponimoMittentePostel) {
		this.toponimoMittentePostel = toponimoMittentePostel;
	}
	public String getCivicoMittentePostel() {
		return civicoMittentePostel;
	}
	public void setCivicoMittentePostel(String civicoMittentePostel) {
		this.civicoMittentePostel = civicoMittentePostel;
	}
	public String getComuneMittentePostel() {
		return comuneMittentePostel;
	}
	public void setComuneMittentePostel(String comuneMittentePostel) {
		this.comuneMittentePostel = comuneMittentePostel;
	}
	public String getProvinciaMittentePostel() {
		return provinciaMittentePostel;
	}
	public void setProvinciaMittentePostel(String provinciaMittentePostel) {
		this.provinciaMittentePostel = provinciaMittentePostel;
	}
	public String getCapMittentePostel() {
		return capMittentePostel;
	}
	public void setCapMittentePostel(String capMittentePostel) {
		this.capMittentePostel = capMittentePostel;
	}
	public String getModalitaInvio() {
		return modalitaInvio;
	}
	public void setModalitaInvio(String modalitaInvio) {
		this.modalitaInvio = modalitaInvio;
	}
	public Boolean getFlgPresenzaPubblicazioni() {
		return flgPresenzaPubblicazioni;
	}
	public void setFlgPresenzaPubblicazioni(Boolean flgPresenzaPubblicazioni) {
		this.flgPresenzaPubblicazioni = flgPresenzaPubblicazioni;
	}
	public String getIdPubblicazione() {
		return idPubblicazione;
	}
	public void setIdPubblicazione(String idPubblicazione) {
		this.idPubblicazione = idPubblicazione;
	}
	public String getNroPubblicazione() {
		return nroPubblicazione;
	}
	public void setNroPubblicazione(String nroPubblicazione) {
		this.nroPubblicazione = nroPubblicazione;
	}
	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}
	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}
	public Date getDataDalPubblicazione() {
		return dataDalPubblicazione;
	}
	public void setDataDalPubblicazione(Date dataDalPubblicazione) {
		this.dataDalPubblicazione = dataDalPubblicazione;
	}
	public Date getDataAlPubblicazione() {
		return dataAlPubblicazione;
	}
	public void setDataAlPubblicazione(Date dataAlPubblicazione) {
		this.dataAlPubblicazione = dataAlPubblicazione;
	}
	public String getRichiestaDaPubblicazione() {
		return richiestaDaPubblicazione;
	}
	public void setRichiestaDaPubblicazione(String richiestaDaPubblicazione) {
		this.richiestaDaPubblicazione = richiestaDaPubblicazione;
	}
	public String getStatoPubblicazione() {
		return statoPubblicazione;
	}
	public void setStatoPubblicazione(String statoPubblicazione) {
		this.statoPubblicazione = statoPubblicazione;
	}
	public String getFormaPubblicazione() {
		return formaPubblicazione;
	}
	public void setFormaPubblicazione(String formaPubblicazione) {
		this.formaPubblicazione = formaPubblicazione;
	}
	public String getRettificataDaPubblicazione() {
		return rettificataDaPubblicazione;
	}
	public void setRettificataDaPubblicazione(String rettificataDaPubblicazione) {
		this.rettificataDaPubblicazione = rettificataDaPubblicazione;
	}
	public String getMotivoRettificaPubblicazione() {
		return motivoRettificaPubblicazione;
	}
	public void setMotivoRettificaPubblicazione(String motivoRettificaPubblicazione) {
		this.motivoRettificaPubblicazione = motivoRettificaPubblicazione;
	}
	public String getMotivoAnnullamentoPubblicazione() {
		return motivoAnnullamentoPubblicazione;
	}
	public void setMotivoAnnullamentoPubblicazione(String motivoAnnullamentoPubblicazione) {
		this.motivoAnnullamentoPubblicazione = motivoAnnullamentoPubblicazione;
	}
	public String getProroghePubblicazione() {
		return proroghePubblicazione;
	}
	public void setProroghePubblicazione(String proroghePubblicazione) {
		this.proroghePubblicazione = proroghePubblicazione;
	}
	public String getIdRipubblicazione() {
		return idRipubblicazione;
	}
	public void setIdRipubblicazione(String idRipubblicazione) {
		this.idRipubblicazione = idRipubblicazione;
	}
	public String getNroRipubblicazione() {
		return nroRipubblicazione;
	}
	public void setNroRipubblicazione(String nroRipubblicazione) {
		this.nroRipubblicazione = nroRipubblicazione;
	}
	public Date getDataRipubblicazione() {
		return dataRipubblicazione;
	}
	public void setDataRipubblicazione(Date dataRipubblicazione) {
		this.dataRipubblicazione = dataRipubblicazione;
	}
	public Date getDataDalRipubblicazione() {
		return dataDalRipubblicazione;
	}
	public void setDataDalRipubblicazione(Date dataDalRipubblicazione) {
		this.dataDalRipubblicazione = dataDalRipubblicazione;
	}
	public Date getDataAlRipubblicazione() {
		return dataAlRipubblicazione;
	}
	public void setDataAlRipubblicazione(Date dataAlRipubblicazione) {
		this.dataAlRipubblicazione = dataAlRipubblicazione;
	}
	public String getRichiestaDaRipubblicazione() {
		return richiestaDaRipubblicazione;
	}
	public void setRichiestaDaRipubblicazione(String richiestaDaRipubblicazione) {
		this.richiestaDaRipubblicazione = richiestaDaRipubblicazione;
	}
	public String getStatoRipubblicazione() {
		return statoRipubblicazione;
	}
	public void setStatoRipubblicazione(String statoRipubblicazione) {
		this.statoRipubblicazione = statoRipubblicazione;
	}
	public String getFormaRipubblicazione() {
		return formaRipubblicazione;
	}
	public void setFormaRipubblicazione(String formaRipubblicazione) {
		this.formaRipubblicazione = formaRipubblicazione;
	}
	public String getRettificataDaRipubblicazione() {
		return rettificataDaRipubblicazione;
	}
	public void setRettificataDaRipubblicazione(String rettificataDaRipubblicazione) {
		this.rettificataDaRipubblicazione = rettificataDaRipubblicazione;
	}
	public String getMotivoRettificaRipubblicazione() {
		return motivoRettificaRipubblicazione;
	}
	public void setMotivoRettificaRipubblicazione(String motivoRettificaRipubblicazione) {
		this.motivoRettificaRipubblicazione = motivoRettificaRipubblicazione;
	}
	public String getMotivoAnnullamentoRipubblicazione() {
		return motivoAnnullamentoRipubblicazione;
	}
	public void setMotivoAnnullamentoRipubblicazione(String motivoAnnullamentoRipubblicazione) {
		this.motivoAnnullamentoRipubblicazione = motivoAnnullamentoRipubblicazione;
	}
	public String getProrogheRipubblicazione() {
		return prorogheRipubblicazione;
	}
	public void setProrogheRipubblicazione(String prorogheRipubblicazione) {
		this.prorogheRipubblicazione = prorogheRipubblicazione;
	}
	public Boolean getAbilModificaDatiExtraIter() {
		return abilModificaDatiExtraIter;
	}
	public void setAbilModificaDatiExtraIter(Boolean abilModificaDatiExtraIter) {
		this.abilModificaDatiExtraIter = abilModificaDatiExtraIter;
	}
	public Boolean getAbilModificaOpereAtto() {
		return abilModificaOpereAtto;
	}
	public void setAbilModificaOpereAtto(Boolean abilModificaOpereAtto) {
		this.abilModificaOpereAtto = abilModificaOpereAtto;
	}
	public Boolean getAbilModificaDatiPubblAtto() {
		return abilModificaDatiPubblAtto;
	}
	public void setAbilModificaDatiPubblAtto(Boolean abilModificaDatiPubblAtto) {
		this.abilModificaDatiPubblAtto = abilModificaDatiPubblAtto;
	}
	public Boolean getAbilModificaSostDirRegTecnicaPropAtto() {
		return abilModificaSostDirRegTecnicaPropAtto;
	}
	public void setAbilModificaSostDirRegTecnicaPropAtto(Boolean abilModificaSostDirRegTecnicaPropAtto) {
		this.abilModificaSostDirRegTecnicaPropAtto = abilModificaSostDirRegTecnicaPropAtto;
	}
	public Boolean getAbilModificaTermineSottoscrizionePropAtto() {
		return abilModificaTermineSottoscrizionePropAtto;
	}
	public void setAbilModificaTermineSottoscrizionePropAtto(Boolean abilModificaTermineSottoscrizionePropAtto) {
		this.abilModificaTermineSottoscrizionePropAtto = abilModificaTermineSottoscrizionePropAtto;
	}
	public Boolean getAbilPubblicazioneTraspAmm() {
		return abilPubblicazioneTraspAmm;
	}
	public void setAbilPubblicazioneTraspAmm(Boolean abilPubblicazioneTraspAmm) {
		this.abilPubblicazioneTraspAmm = abilPubblicazioneTraspAmm;
	}
	public String getIdUDScansione() {
		return idUDScansione;
	}
	public void setIdUDScansione(String idUDScansione) {
		this.idUDScansione = idUDScansione;
	}
	public String getTipoBarcodePrimario() {
		return tipoBarcodePrimario;
	}
	public String getBarcodePrimario() {
		return barcodePrimario;
	}
	public String getEnergiaGasPrimario() {
		return energiaGasPrimario;
	}
	public void setTipoBarcodePrimario(String tipoBarcodePrimario) {
		this.tipoBarcodePrimario = tipoBarcodePrimario;
	}
	public void setBarcodePrimario(String barcodePrimario) {
		this.barcodePrimario = barcodePrimario;
	}
	public void setEnergiaGasPrimario(String energiaGasPrimario) {
		this.energiaGasPrimario = energiaGasPrimario;
	}
	public Boolean getAbilGestioneCollegamentiUD() {
		return abilGestioneCollegamentiUD;
	}
	public void setAbilGestioneCollegamentiUD(Boolean abilGestioneCollegamentiUD) {
		this.abilGestioneCollegamentiUD = abilGestioneCollegamentiUD;
	}
	public String getFlgIncertezzaRilevazioneFirmePrimario() {
		return flgIncertezzaRilevazioneFirmePrimario;
	}
	public void setFlgIncertezzaRilevazioneFirmePrimario(String flgIncertezzaRilevazioneFirmePrimario) {
		this.flgIncertezzaRilevazioneFirmePrimario = flgIncertezzaRilevazioneFirmePrimario;
	}
	public String getWarningMsgDoppiaReg() {
		return warningMsgDoppiaReg;
	}
	public void setWarningMsgDoppiaReg(String warningMsgDoppiaReg) {
		this.warningMsgDoppiaReg = warningMsgDoppiaReg;
	}
	public String getFlgPresentiControinteressati() {
		return flgPresentiControinteressati;
	}
	public void setFlgPresentiControinteressati(String flgPresentiControinteressati) {
		this.flgPresentiControinteressati = flgPresentiControinteressati;
	}
	public List<ControinteressatoBean> getListaControinteressati() {
		return listaControinteressati;
	}
	public void setListaControinteressati(List<ControinteressatoBean> listaControinteressati) {
		this.listaControinteressati = listaControinteressati;
	}
	public Boolean getInfoXAllegFlgPrimarioAtto() {
		return infoXAllegFlgPrimarioAtto;
	}
	public Boolean getInfoXAllegShowParteIntegrante() {
		return infoXAllegShowParteIntegrante;
	}
	public Boolean getInfoXAllegDefaultParteIntegrante() {
		return infoXAllegDefaultParteIntegrante;
	}
	public Boolean getInfoXAllegShowEscludiPubblicazione() {
		return infoXAllegShowEscludiPubblicazione;
	}
	public Boolean getInfoXAllegDefaultEscludiPubblicazione() {
		return infoXAllegDefaultEscludiPubblicazione;
	}
	public Boolean getInfoXAllegShowSeparato() {
		return infoXAllegShowSeparato;
	}
	public Boolean getInfoXAllegDefaultSeparato() {
		return infoXAllegDefaultSeparato;
	}
	public Boolean getInfoXAllegShowVersOmissis() {
		return infoXAllegShowVersOmissis;
	}
	public void setInfoXAllegFlgPrimarioAtto(Boolean infoXAllegFlgPrimarioAtto) {
		this.infoXAllegFlgPrimarioAtto = infoXAllegFlgPrimarioAtto;
	}
	public void setInfoXAllegShowParteIntegrante(Boolean infoXAllegShowParteIntegrante) {
		this.infoXAllegShowParteIntegrante = infoXAllegShowParteIntegrante;
	}
	public void setInfoXAllegDefaultParteIntegrante(Boolean infoXAllegDefaultParteIntegrante) {
		this.infoXAllegDefaultParteIntegrante = infoXAllegDefaultParteIntegrante;
	}
	public void setInfoXAllegShowEscludiPubblicazione(Boolean infoXAllegShowEscludiPubblicazione) {
		this.infoXAllegShowEscludiPubblicazione = infoXAllegShowEscludiPubblicazione;
	}
	public void setInfoXAllegDefaultEscludiPubblicazione(Boolean infoXAllegDefaultEscludiPubblicazione) {
		this.infoXAllegDefaultEscludiPubblicazione = infoXAllegDefaultEscludiPubblicazione;
	}
	public void setInfoXAllegShowSeparato(Boolean infoXAllegShowSeparato) {
		this.infoXAllegShowSeparato = infoXAllegShowSeparato;
	}
	public void setInfoXAllegDefaultSeparato(Boolean infoXAllegDefaultSeparato) {
		this.infoXAllegDefaultSeparato = infoXAllegDefaultSeparato;
	}
	public void setInfoXAllegShowVersOmissis(Boolean infoXAllegShowVersOmissis) {
		this.infoXAllegShowVersOmissis = infoXAllegShowVersOmissis;
	}
	public Boolean getFlgAttivaCtrlAllegatiXImportUnitaDoc() {
		return flgAttivaCtrlAllegatiXImportUnitaDoc;
	}
	public void setFlgAttivaCtrlAllegatiXImportUnitaDoc(Boolean flgAttivaCtrlAllegatiXImportUnitaDoc) {
		this.flgAttivaCtrlAllegatiXImportUnitaDoc = flgAttivaCtrlAllegatiXImportUnitaDoc;
	}
	public Boolean getDerivaDaRdA() {
		return derivaDaRdA;
	}
	public void setDerivaDaRdA(Boolean derivaDaRdA) {
		this.derivaDaRdA = derivaDaRdA;
	}
	public HashMap<String, String> getErroriFile() {
		return erroriFile;
	}
	public void setErroriFile(HashMap<String, String> erroriFile) {
		this.erroriFile = erroriFile;
	}
	public String getTimestampGetData() {
		return timestampGetData;
	}
	public void setTimestampGetData(String timestampGetData) {
		this.timestampGetData = timestampGetData;
	}	
}