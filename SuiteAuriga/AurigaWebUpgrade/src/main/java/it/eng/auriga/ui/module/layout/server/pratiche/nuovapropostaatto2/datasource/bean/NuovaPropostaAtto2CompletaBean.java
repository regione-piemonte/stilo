/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.SelezionaUOBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.ImpostazioniUnioneFileBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AltraViaProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ClassificazioneFascicoloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.PeriziaBean;
import it.eng.auriga.ui.module.layout.server.task.bean.ParametriTipoAttoBean;
import it.eng.document.function.bean.CIGCUPBean;
import it.eng.document.function.bean.CUIBean;
import it.eng.document.function.bean.DatiContabiliADSPXmlBean;
import it.eng.document.function.bean.DatiSpesaAnnualiXDetPersonaleXmlBean;
import it.eng.document.function.bean.DestinatariNotificaMessiXmlBean;
import it.eng.document.function.bean.DestinatariNotificaPECXmlBean;
import it.eng.document.function.bean.MovimentiContabiliaXmlBean;
import it.eng.document.function.bean.MovimentiGSAXmlBean;
import it.eng.document.function.bean.RiepilogoDatiLiquidazioneAVB;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class NuovaPropostaAtto2CompletaBean {
	
	/*******************
	 * TAB DATI SCHEDA *
	 *******************/
	
	/* Hidden */
	private String idUd;
	private String idUdNuovoComeCopia;
	private String derivaDaRdA;
	private String idFolder;
	private String prefKeyModello;
	private String prefNameModello;
	private String useridModello;
	private String idUoModello;	
	private String flgTipoDocDiverso;
	private String siglaRegAttoDaCopiare;
	private String idTipoDocDaCopiare;	
	private String tipoDocumento;
	private String nomeTipoDocumento;
	private String rowidDoc;
	private String idDocPrimario;
	private String nomeFilePrimario;
	private String uriFilePrimario;
	private Boolean remoteUriFilePrimario;
	private MimeTypeFirmaBean infoFilePrimario;
	private Boolean isChangedFilePrimario;
	private String idDocPrimarioOmissis; 
	private String nomeFilePrimarioOmissis;
	private String uriFilePrimarioOmissis;
	private Boolean remoteUriFilePrimarioOmissis;
	private MimeTypeFirmaBean infoFilePrimarioOmissis;		
	private Boolean isChangedFilePrimarioOmissis;
	private Boolean flgAggiornataVersDaPubblicare;
	private String uriDocGeneratoFormatoOdt;
	
	// Forzata modifica atto
	private Boolean flgForzataModificaAtto;
	
	/* Modifica cod. atto/LL.PP./beni e servizi */
	private Boolean abilModificaCodAttoCMTO;
	
	/* Revoca atto */
	private String idTipoProcRevocaAtto;	
	private String nomeTipoProcRevocaAtto;	
	private String idDefFlussoWFRevocaAtto;
	private String idTipoDocPropostaRevocaAtto;	
	private String nomeTipoDocPropostaRevocaAtto;		
	private String siglaPropostaRevocaAtto;	
	
	/* Emendamento */
	private String idTipoProcEmendamento;	
	private String nomeTipoProcEmendamento;	
	private String idDefFlussoWFEmendamento;
	private String idTipoDocPropostaEmendamento;	
	private String nomeTipoDocPropostaEmendamento;		
	private String siglaPropostaEmendamento;	
	private String tipoAttoRifEmendamento;			
	private String siglaAttoRifEmendamento;			
	private String numeroAttoRifEmendamento;			
	private String annoAttoRifEmendamento;		
	private String idEmendamentoRif;
	private String numeroEmendamentoRif;			
	
	/* Dati scheda - Registrazione */
	private String categoriaRegAvvio;
	private String siglaRegAvvio;	
	private String siglaRegistrazione;
	private String numeroRegistrazione;
	private Date dataRegistrazione;
	private String annoRegistrazione;
	private String desUserRegistrazione;
	private String desUORegistrazione;
	private String estremiRepertorioPerStruttura;
	private String siglaRegProvvisoria;
	private String numeroRegProvvisoria;
	private Date dataRegProvvisoria;
	private String annoRegProvvisoria;
	private String desUserRegProvvisoria;
	private String desUORegProvvisoria;
	private String idDocPrimarioLiquidazione;
	private String idUdLiquidazione;
	private String codTipoLiquidazioneXContabilia;
	private String siglaRegLiquidazione;
	private String annoRegLiquidazione;
	private String nroRegLiquidazione;
	private Date dataAdozioneLiquidazione;
	private String estremiAttoLiquidazione;
	private String esitoInvioACTASerieAttiIntegrali;
	private String esitoInvioACTASeriePubbl;
	
	/* Dati scheda - Dati di pubblicazione */
	private Date dataInizioPubblicazione;
	private String giorniPubblicazione;
	
	/* Dati scheda - Emenda */	
	private String tipoAttoEmendamento; 
	private String siglaAttoEmendamento;
	private String numeroAttoEmendamento;
	private String annoAttoEmendamento;
	private String idEmendamento;
	private String numeroEmendamento; 
	private String flgEmendamentoSuFile; 
	private String numeroAllegatoEmendamento; 
	private Boolean flgEmendamentoIntegraleAllegato; 
	private String numeroPaginaEmendamento;
	private String numeroRigaEmendamento;
	private String effettoEmendamento;
    
	/* Dati scheda - Destinatari */
	
	private Boolean flgAttivaDestinatari;
	private List<DestAttoBean> listaDestinatariAtto;
	private List<DestAttoBean> listaDestinatariPCAtto;
	
	/* Dati scheda - Tipo proposta */
	private String iniziativaProposta;
	private Boolean flgAttoMeroIndirizzo;
	private Boolean flgAttoCommissario;
	private Boolean flgModificaRegolamento;
	private Boolean flgModificaStatuto;
	private Boolean flgNomina;
	private Boolean flgRatificaDeliberaUrgenza;
	private Boolean flgAttoUrgente;
	private Boolean flgCommissioniTipoProposta;
	
	/* Dati scheda - Circoscrizioni proponenti delibera */
	private List<SimpleKeyValueBean> listaCircoscrizioni;
	
	/* Dati scheda - Interpellanza */
	private String tipoInterpellanza;
	private String motivazioneInterpellanzaRispScritta;
	
	/* Dati scheda - Ordinanza di mobilità */
	private String tipoOrdMobilita;
	private Date dataInizioVldOrdinanza;
	private Date dataFineVldOrdinanza;
	private String tipoLuogoOrdMobilita;
	private List<AltraViaProtBean> listaIndirizziOrdMobilita;
	private String luogoOrdMobilita;
	private String luogoOrdMobilitaFile;
	
	private List<SimpleKeyValueBean> listaCircoscrizioniOrdMobilita;
	private String descrizioneOrdMobilita;
	private String descrizioneOrdMobilitaFile;
		
	/* Dati scheda - Ruoli */
	private List<ProponentiBean> listaProponenti;
	private String ufficioProponente; // (idUO)
	private String codUfficioProponente;
	private String desUfficioProponente;
	private String flgUfficioProponenteGare;
	private List<SelezionaUOBean> listaUfficioProponente;
	private String tipoProvvedimento;
	private String sottotipoDelibera;
	private List<UfficioGareAcquistiBean> listaUfficioGareAcquisti;	
	private String flgProcExCodAppalti;
	private String ufficioCompetente; // (idUO)
	private String codUfficioCompetente;
	private String desUfficioCompetente;
	private String cdrUOCompetente;
	private OpzUOInDettAttoBean opzUOCompetente;
	private List<SelezionaUOBean> listaUfficioCompetente;
	private List<RUPCompletaBean> listaRUPCodAppalti;
	private List<DirigenteAdottanteBean> listaAdottante;
	private List<RdPCompletaBean> listaRdPCodAppalti;
	private String centroDiCosto;
	private List<DirigenteDiConcertoBean> listaDirigentiConcerto;
	private List<DirigenteRespRegTecnicaBean> listaDirRespRegTecnica;
	private List<AltriDirRespRegTecnicaBean> listaAltriDirRespRegTecnica;
	private List<RdPCompletaBean> listaRdP;
	private String codProcedimento;	
	private String desProcedimento;	
	private List<RUPCompletaBean> listaRUP;
	private List<ScrivaniaDECBean> listaScrivaniaDEC;	
	private List<AssessoreBean> listaAssessori;
	private List<AssessoreBean> listaAltriAssessori;
	private List<ProponenteAttoConsiglioBean> listaProponenteAttoConsiglio;
	private List<ConsigliereBean> listaConsiglieri;
	private List<ConsigliereBean> listaAltriConsiglieri;	
	private Date dataTermFirmeConsiglieri;
	private List<DirigenteProponenteBean> listaDirigentiProponenti;
	private List<DirigenteProponenteBean> listaAltriDirigentiProponenti;
	private List<CoordinatoreCompCircBean> listaCoordinatoriCompCirc;		
	private Boolean flgRichiediVistoDirettore;
	private List<ResponsabileVistiConformitaBean> listaRespVistiConformita;
	private List<ResponsabileUfficioPropBean> listaRespUfficioProp;
	private List<ResponsabileVistiPerfezionamentoBean> listaRespVistiPerfezionamento;
	private String flgVistoBilancio;
	private List<RespVisAltBilancioBean> listaRespVisAltBilancio;
	private String tipoVistoBilancio;
	private Boolean flgVistoSG;
	private Boolean flgVistoPresidente;
	private List<EstensoreBean> listaEstensori;
	private List<EstensoreBean> listaAltriEstensori;
	private List<IstruttoreBean> listaIstruttori;
	private List<IstruttoreBean> listaAltriIstruttori;
	private List<UtenteRifAttoConsiglioBean> listaUtenteRifAttoConsiglio;
	private Boolean flgSenzaValidazionePO;
	
	/* Dati scheda - Visti dir. superiori */
	
	private Boolean flgVistoRespUff;
	private Boolean flgVistoDirSup1;
	private Boolean flgVistoDirSup2;
	
	private String idScrivaniaVistoRespUff;
	private String idScrivaniaVistoDirSup1;
	private String idScrivaniaVistoDirSup2;
	
	/* Dati scheda - Parere della/e circoscrizioni */
	private List<SimpleKeyValueBean> listaParereCircoscrizioni;
	
	/* Dati scheda - Parere della/e commissioni */
	private Boolean flgCommissioni;
	private List<SimpleKeyValueBean> listaParereCommissioni;
	
	/* Dati scheda - Oggetto */
	private String desOgg;
	private String oggetto;
	private String oggettoHtml;
	private String oggettoHtmlFile;
	
	/* Dati scheda - Atto di riferimento */
	
	private List<AttoRiferimentoBean> listaAttiRiferimento;
//	private String flgAttoRifASistema;
//	private String idUdAttoDeterminaAContrarre;
//	private String categoriaRegAttoDeterminaAContrarre;
//	private String siglaAttoDeterminaAContrarre;
//	private String numeroAttoDeterminaAContrarre;
//	private String annoAttoDeterminaAContrarre;
	
	/* Dati scheda - Specificità del provvedimento */
	private String oggLiquidazione;
	private Date dataScadenzaLiquidazione;
	private String urgenzaLiquidazione;
	private Boolean flgLiqXUffCassa;
	private String importoAnticipoCassa;
	private Date dataDecorrenzaContratto;
	private String anniDurataContratto;
	private Boolean flgAffidamento;
	private Boolean flgDeterminaAContrarreTramiteProceduraGara;
	private Boolean flgDeterminaAggiudicaProceduraGara;
	private Boolean flgDeterminaRimodulazioneSpesaGaraAggiudicata;
	private Boolean flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro;
	private Boolean flgDeterminaRiaccertamento;
	private Boolean flgDeterminaAccertRadiaz;
	private Boolean flgDeterminaVariazBil;
	private Boolean flgVantaggiEconomici;
	private Boolean flgDecretoReggio;
	private Boolean flgAvvocatura;
	private Boolean flgDeterminaArchiviazione;
	private Boolean flgContributi; // il check "contributi" non è esclusivo come gli altri sopra
	private String flgSpesa; // valori SI/NO
	private String flgDatiRilevantiGSA; // valori SI/NO
	private List<SimpleKeyValueBean> listaUfficiCompetentiRag;
	private String annoContabileCompetenza;	
	private Boolean flgCorteConti;
	private Boolean flgLiqContestualeImpegno;
	private Boolean flgLiqContestualeAltriAspettiRilCont;
	private Boolean flgDetConLiquidazione;
	private Boolean flgCompQuadroFinRagDec;
	private Boolean flgNuoviImpAcc;
	private Boolean flgImpSuAnnoCorrente;
	private Boolean flgInsMovARagioneria;
	private Boolean flgPresaVisioneContabilita;
	private Boolean flgSpesaCorrente;
	private Boolean flgImpegniCorrenteGiaValidati;
	private Boolean flgSpesaContoCapitale;
	private Boolean flgImpegniContoCapitaleGiaRilasciati;
	private Boolean flgSoloSubImpSubCrono;
	private String tipoAttoInDeliberaPEG;
	private String tipoAffidamento;
	private String normRifAffidamento;
	private String respAffidamento;
	private String materiaTipoAtto;	
	private String desMateriaTipoAtto;	
	private String tipoFinanziamentoPNRR;
	private String flgSottotipoAtto;
	private String flgTipoIter;
	private Boolean flgFondiEuropeiPON;
	private String flgFondiPNRRRadio;
	private Boolean flgFondiPNRR;
	private Boolean flgFondiPNRRRigen;
	private Boolean flgFondiPRU;
	private Boolean flgVistoUtenze;
	private Boolean flgVistoCapitolatiSottoSoglia;
	private Boolean flgVistoCapitolatiSopraSoglia;
	private Boolean flgVistoPar117_2013;
	private Boolean flgNotificaDaMessi;	
	private Boolean flgSenzaImpegniCont;
	private String flgMEPACONSIP;
	private String flgServeDUVRI;
	private String importoOneriSicurezza;	
	private String imponibileComplessivo;	
	private String flgLLPP;	
	private String annoProgettoLLPP;	
	private String numProgettoLLPP;	
	private String flgBeniServizi;
	private String flgProgrammazioneAcquisti;
	private String annoProgettoBeniServizi;	
	private String numProgettoBeniServizi;		
	private String flgPrivacy;
	private Boolean flgDatiProtettiTipo1;
	private Boolean flgDatiProtettiTipo2;
	private Boolean flgDatiProtettiTipo3;
	private Boolean flgDatiProtettiTipo4;
	private String numGara;		
	private String flgControlloLegittimita;
	private String motivazioniEsclControlloLegittimita;
	
	/* Dati scheda - Dest. vantaggio */
	private List<DestVantaggioBean> listaDestVantaggio;
	
	/* Dati scheda - Ruoli e visti per dati contabili */
	private Boolean flgAdottanteUnicoRespPEG;	
	private Boolean flgDirRespRegTecnicaUnicoRespSpesa;	
	private List<ResponsabilePEGBean> listaResponsabiliPEG; // responsabilePEG (idSV), codUoResponsabilePEG e desResponsabilePEG
	private String ufficioDefinizioneSpesa; // (idUO)
	private String codUfficioDefinizioneSpesa;
	private String desUfficioDefinizioneSpesa;
	private List<SelezionaUOBean> listaUfficioDefinizioneSpesa;	
	private String opzAssCompSpesa;
	private Boolean flgRichVerificaDiBilancioCorrente;
	private Boolean flgRichVerificaDiBilancioContoCapitale;
	private Boolean flgRichVerificaDiContabilita;
	private Boolean flgConVerificaContabilita;
	private Boolean flgRichiediParereRevisoriContabili;
	
	/* Dati scheda - CIG */
	private String flgOpCommerciale;
	private Boolean flgEscludiCIG;
	private Boolean flgCIGDaAcquisire;
	private String motivoEsclusioneCIG;
	private String codiceCIGPadre;
	private List<CIGCUPBean> listaCIG;
	
	/* Dati scheda - CUI */
	private List<CUIBean> listaCUI;
	
	/* Dati scheda - Categoria di rischio */
	private String categoriaRischio;
	
	/* Dati scheda - Categoria di rischio */
	private String subfolderRda;
	
	/* Dati scheda - Classificazione e fascicolazione */
	private List<ClassificazioneFascicoloBean> listaClassFasc;
	
	/* Altri dati */
	private String idPropostaAMC;	
	private Boolean flgDettRevocaAtto;
	private Boolean flgDatiSensibili;
	private List<SimpleKeyValueBean> listaVociPEGNoVerifDisp;
	
	/*Controllo per evitare salvataggi con dati/file non aggiornati*/
	private String timestampGetData;
	
	/************************
	 * TAB DATI DISPOSITIVO *
	 ************************/
	
	/* Dati dispositivo - Upload pdf atto */
	private Boolean flgAttivaUploadPdfAtto;
	private Boolean flgAttivaUploadPdfAttoOmissis;	
	private DocumentBean filePdfAtto;
	private DocumentBean filePdfAttoOmissis;
	
	/* Dati dispositivo - Riferimenti normativi */
	private List<SimpleValueBean> listaRiferimentiNormativi; // colonne: riferimentoNormativo
	
	/* Dati dispositivo - Atti presupposti */
//	private List<SimpleValueBean> listaAttiPresupposti; // colonne: attoPresupposto
	private String attiPresupposti;
	private String attiPresuppostiFile;
		
	/* Dati dispositivo - Motivazioni */
	private String motivazioni;
	private String motivazioniFile;
	
	/* Dati dispositivo - Premessa */
	private String premessa;
	private String premessaFile;
	
	/* Dati dispositivo - Riferimenti normativi */
	private String rifNormativiLiberi;
	private String rifNormativiLiberiFile;
	
	/* Dati dispositivo - Dispositivo */
	private String dispositivo;
	private String dispositivoFile;
	private String loghiAggiuntiviDispositivo;
	
	/**************************
	 * TAB DATI DISPOSITIVO 2 *
	 **************************/
	
	/* Dati dispositivo 2 - Premessa 2 */
	private String premessa2;
	private String premessa2File;
	
	/* Dati dispositivo 2 - Dispositivo 2 */
	private String dispositivo2;
	private String dispositivo2File;
	
	/****************
	 * TAB ALLEGATI *
	 ****************/
	
	/* Allegati */
	private Boolean flgPubblicaAllegatiSeparati;	
	private List<AllegatoProtocolloBean> listaAllegati; // colonne: le stesse degli allegati in Protocollo
	
	/***************************
	 * TAB DOCUMENTI FASCICOLO *
	 ***************************/
	
	private List<AllegatoProtocolloBean> listaDocFasc;	
	
	/*******************************
	 * TAB PUBBLICAZIONE/NOTIFICHE *
	 *******************************/
	
	/* Pubblicazione/notifiche - Opzioni testo */	
	protected Boolean flgEscludiPremesseFisseDaTestoAtto;
	protected Boolean flgEscludiOggettoDaTestoAtto;
	
	/* Pubblicazione/notifiche - Opzioni avanzate testo */	
	protected String scrittaTraPremessaEDispositivoAtto;
	protected String numRighePreScrittaTraPremessaEDispositivoAtto;
	protected String numRighePostScrittaTraPremessaEDispositivoAtto;
	
	/* Pubblicazione/notifiche - Opzioni iter */	
	protected Boolean flgAcquisitiVistiTornaVerificaUoProp;
	
	/* Pubblicazione/notifiche - Opzioni visualizzazione */	
	protected Boolean flgVisibilitaPubblPostAdozione;
	
	/* Pubblicazione/notifiche - Pubblicazione all'Albo */	
	private String flgPubblAlbo;
	private String tipoDecorrenzaPubblAlbo;
	private Date dataPubblAlboDal;
	private String numGiorniPubblAlbo;
	private Date dataPubblAlboAl;
	private Boolean flgUrgentePubblAlbo;
	private Date dataPubblAlboEntro;
	private Date dataRipubblAlboDal;
	private String numGiorniRipubblAlbo;
	private Date dataRipubblAlboAl;
	
	/* Pubblicazione/notifiche - Pubblicazione in Amm. Trasparente */
	
	private String flgPubblAmmTrasp;
	private String annoTerminePubblAmmTrasp;
	private String sezionePubblAmmTrasp;
	private String sottoSezionePubblAmmTrasp;
	
	/* Pubblicazione/notifiche - Pubblicazione al B.U. */	
	private String flgPubblBUR;
	private String annoTerminePubblBUR;
	private String tipoDecorrenzaPubblBUR;
	private Date dataPubblBURDal;
	private Boolean flgUrgentePubblBUR;
	private Date dataPubblBUREntro;
		
	/* Pubblicazione/notifiche - Pubblicazione sul notiziario */
	private String flgPubblNotiziario;
	
	/* Pubblicazione/notifiche - Esecutività */	
	private Date dataEsecutivitaDal;
	private Boolean flgImmediatamenteEseguibile;
	private String motiviImmediatamenteEseguibile;
	private String motiviImmediatamenteEseguibileFile;
	
	/* Pubblicazione/notifiche - Notifiche */	
	private String listaDestNotificaAtto;
	
	/* Pubblicazione/notifiche - Notifica a mezzo messi */	
	private Boolean flgMessiNotificatori;
	private List<DestinatariNotificaMessiXmlBean> listaDestinatariNotificaMessi;
	
	/* Pubblicazione/notifiche - Notifica PEC */	
	private Boolean flgNotificaPEC;
	private List<DestinatariNotificaPECXmlBean> listaDestinatariNotificaPEC;
	
	/* Pubblicazione/notifiche - Visibilità pubblica post adozione */
	private Boolean flgVisibPubblicaPostAdozione;
	
	/***************************
	 * TAB DATI SPESA CORRENTE *
	 ***************************/

	private Boolean flgDisattivaAutoRequestDatiContabiliSIBCorrente;
	private String prenotazioneSpesaSIBDiCorrente;
	private String modalitaInvioDatiSpesaARagioneriaCorrente;
		
	/* Dati spesa corrente - Impegni e accertamenti */
	private List<DatiContabiliBean> listaDatiContabiliSIBCorrente; // colonne: vedi DatiContabiliBean
	private String errorMessageDatiContabiliSIBCorrente;
	
	/* Dati spesa corrente - Compilazione griglia */
	private List<DatiContabiliBean> listaInvioDatiSpesaCorrente; // colonne: vedi DatiContabiliBean
	
	/* Dati spesa corrente - Upload xls importabile in SIB */
	private DocumentBean fileXlsCorrente;
	private String nomeFileTracciatoXlsCorrente;
	private String uriFileTracciatoXlsCorrente;
	
	/* Dati spesa corrente - Allegati */
	private List<AllegatoProtocolloBean> listaAllegatiCorrente; // colonne: le stesse degli allegati in Protocollo
	
	/* Dati spesa corrente - Note */
	private String noteCorrente;
	private String noteCorrenteFile;
		
	/*********************************
	 * TAB DATI SPESA CONTO CAPITALE *
	 *********************************/

	private Boolean flgDisattivaAutoRequestDatiContabiliSIBContoCapitale;
	private String modalitaInvioDatiSpesaARagioneriaContoCapitale;
	
	/* Dati spesa conto capitale - Impegni e accertamenti */
	private List<DatiContabiliBean> listaDatiContabiliSIBContoCapitale; // colonne: vedi DatiContabiliBean
	private String errorMessageDatiContabiliSIBContoCapitale;
	
	/* Dati spesa conto capitale - Compilazione griglia */
	private List<DatiContabiliBean> listaInvioDatiSpesaContoCapitale; // colonne: vedi DatiContabiliBean
	
	/* Dati spesa conto capitale - Upload xls importabile in SIB */
	private DocumentBean fileXlsContoCapitale;
	private String nomeFileTracciatoXlsContoCapitale;
	private String uriFileTracciatoXlsContoCapitale;
	
	/* Dati spesa conto capitale - Allegati */
	private List<AllegatoProtocolloBean> listaAllegatiContoCapitale; // colonne: le stesse degli allegati in Protocollo
	
	/* Dati spesa conto capitale - Note */
	private String noteContoCapitale;
	private String noteContoCapitaleFile;
	
	/****************************
	 * TAB DATI SPESA PERSONALE *
	 ****************************/

	/* Dati spesa personale - Spesa anno corrente ed eventuali successivi */
	private List<DatiSpesaAnnualiXDetPersonaleXmlBean> listaDatiSpesaAnnualiXDetPersonale;
		
	/* Dati spesa personale - Spesa annua */
	private String capitoloDatiSpesaAnnuaXDetPers;
	private String articoloDatiSpesaAnnuaXDetPers;
	private String numeroDatiSpesaAnnuaXDetPers;
	private String importoDatiSpesaAnnuaXDetPers;
	
	/********************
	 * INTEGRAZIONE SIB *
	 ********************/
	
	private String eventoSIB;
	private String esitoEventoSIB;
	private Date dataEventoSIB;
	private String errMsgEventoSIB;
	private String idUoDirAdottanteSIB;
	private String codUoDirAdottanteSIB;
	private String desUoDirAdottanteSIB;
	
	/***************************
	 * INTEGRAZIONE CONTABILIA *
	 ***************************/
	
	private String eventoContabilia;
	private String esitoEventoContabilia;
	private Date dataEventoContabilia;
	private String errMsgEventoContabilia;
	
	private String dirigenteResponsabileContabilia;
	private String centroResponsabilitaContabilia;
	private String centroCostoContabilia;
	
	private List<MovimentiContabiliaXmlBean> listaMovimentiContabilia;
	private String errorMessageMovimentiContabilia;
	
	/*********************************
	 * INTEGRAZIONE SICRA (MAGGIOLI) *
	 *********************************/
	
	private String eventoSICRA;
	private String esitoEventoSICRA;
	private Date dataEventoSICRA;
	private String errMsgEventoSICRA;
	
	private List<MovimentiContabiliSICRABean> listaInvioMovimentiContabiliSICRA;	
	private List<MovimentiContabiliSICRABean> listaMovimentiSICRAToDelete;
	private List<MovimentiContabiliSICRABean> listaMovimentiSICRAToInsert;
	
	private String esitoSetMovimentiAttoSICRA;
	private String messaggioWarning;
	private String codXSalvataggioConWarning;
	
	private String nomeFileAttoLiqTecnica;
	private MimeTypeFirmaBean infoFileAttoLiqTecnica;
	private String uriFileAttoLiqTecnica;
	
	private String nomeFileAttoLiqContabile;
	private MimeTypeFirmaBean infoFileAttoLiqContabile;
	private String uriFileAttoLiqContabile;
	
	/************
	 * DATI GSA *
	 ************/
	
	private List<MovimentiGSAXmlBean> listaMovimentiGSA;
	
	/*********************************
	 * INTEGRAZIONE CWOL (ADSP) *
	 *********************************/
	
	private String eventoCWOL;
	private Boolean flgRecuperaMovimentiContabDefinitivi;
	
	/******************************
	 * AGGREGATO/SMISTAMENTO ACTA *
	 ******************************/
	
	private String codAOOXSelNodoACTA;
	private String codStrutturaXSelNodoACTA;
	
	private Boolean flgAggregatoACTA;
	private Boolean flgSmistamentoACTA;
	private Boolean flgIndiceClassificazioneACTA;
	private Boolean flgFascicoloACTA;
	private String codIndiceClassificazioneACTA;
	private Boolean flgPresenzaClassificazioneACTA;
	private String codVoceTitolarioACTA;
	private String codFascicoloACTA;
	private String suffissoCodFascicoloACTA;
	private String codSottofascicoloACTA;
	private String idFascicoloACTA;
	private String idNodoSmistamentoACTA;
	private String desNodoSmistamentoACTA;	
	
	/*********************************
	 * TAB OPERE/DATI CONTABILI ADSP *
	 *********************************/
	
	private List<PeriziaBean> listaOpereADSP;
	private List<DatiContabiliADSPXmlBean> listaDatiContabiliADSP;
	private List<DatiContabiliADSPXmlBean> listaDatiContabiliDefinitiviADSP;
	private List<ErroreRigaExcelBean> listaExcelDatiContabiliADSPInError;
	private Boolean flgSavedAttoSuSistemaContabile;
	private Integer statoAttoContabilita;
	
	private Boolean flgDatiContabiliADSPNonAllineati;
	
	/**************************
	 * TAB DATI CONTABILI AVB *
	 **************************/
	private List<DatiContabiliAVBBean> listaImpegniAVB;
	private List<DatiContabiliAVBBean> listaAccertamentiAVB;
	private List<DatiContabiliAVBBean> listaLiquidazioniAVB;
	
	private List<DatiLiquidazioneAVBBean> listaDatiLiquidazioneAVB;
	private List<RiepilogoDatiLiquidazioneAVB> listaRiepilogoDatiLiquidazioneAVB;
	private List<String> listaXlsToDeleteDatiLiqAVB;
	
	
	/**************************
	 * TAB CONTABILI ATERSIR  *
	 **************************/
	private List<DatiContabiliATERSIRBean> listaDatiContabiliATERSIR;
	
	
	/***********************
	 * TAB TRASPARENZA AVB *
	 ***********************/
	
	private String flgErogVantaggiEconomici;
	private String tipoTrasparenzaVantEcon;
	private Boolean flgBeneficiariObbligXTipoTraspVantEcon;
	private String sottotipoTrasparenzaVantEcon;
	private Boolean flgBeneficiariObbligXSottotipoTraspVantEcon;
	private String flgInterventi;
	private String tipoInterventi;
	private Boolean flgBeneficiariObbligXTipoInterventi;
	private String sottotipoInterventi;
	private Boolean flgBeneficiariObbligXSottotipoInterventi;
	private String flgAltriProvvedimenti;
	private String tipoAltriProvvedimenti;
	private Boolean flgBeneficiariObbligXTipoAltriProvvedimenti;
	private String sottotipoAltriProvvedimenti;
	private Boolean flgBeneficiariObbligXSottotipoAltriProvvedimenti;
	private String normaTrasparenzaVantEcon;
	private String modalitaIndividuazioneBeneficiario;
	private String estremiDocumentiPrincipaliFascicolo;
	private String avvisiPerLaCompilazione;
	private String ufficioCompetenteTabTrasp;
	private String responsabileProcedimentoTabTrasp;
	private Date dataAvvioProcedimento;
	private String oggettoProvvedimento;
	private String durataRapportoGiuridico;
	private String numeroMesiDurataRapporto;
	private Date dalDurataRapporto;
	private Date alDurataRapporto;
	private String listaAllegatiObbligatoriTrasparenza;
	private String listaIdAllegatiObbligatoriTrasparenza;
	private String listaAllegatiNonObbligatoriTrasparenza;
	private String listaIdAllegatiNonObbligatoriTrasparenza;
	private List<DestVantaggioBean> listaBeneficiariTrasparenza;
	
	/******************************
	 * TAB ATTRIBUTI DINAMICI DOC *
	 ******************************/
	
	private Map<String, Object> valori;
	private Map<String, String> tipiValori;
	private Map<String, String> colonneListe;
	
	/******************************
	 * PER GENERAZIONE DA MODELLO *
	 ******************************/
	
	private String idProcess;
	private String idModello;
	private String nomeModello;
	private String displayFilenameModello;	
	private String uriModello;
	private String tipoModello;
	private String idModCopertina;
	private String nomeModCopertina;
	private String uriModCopertina;
	private String tipoModCopertina;
	private String idModCopertinaFinale;
	private String nomeModCopertinaFinale;
	private String uriModCopertinaFinale;
	private String tipoModCopertinaFinale;
	private String idModAllegatiParteIntSeparati;
	private String nomeModAllegatiParteIntSeparati;
	private String uriModAllegatiParteIntSeparati;
	private String tipoModAllegatiParteIntSeparati;
	private String idModAllegatiParteIntSeparatiXPubbl;
	private String nomeModAllegatiParteIntSeparatiXPubbl;
	private String uriModAllegatiParteIntSeparatiXPubbl;
	private String tipoModAllegatiParteIntSeparatiXPubbl;
	private Boolean flgAppendiceDaUnire;
	private String idModAppendice;
	private String nomeModAppendice;
	private String uriModAppendice;
	private String tipoModAppendice;
	private String uriAppendice;
	private String displayFilenameModAppendice;
	private Boolean flgMostraDatiSensibili;
	private Boolean flgMostraOmissisBarrati;
	private Boolean flgAnteprima;
	private Boolean flgVersioneFinale;
	private List<String> elencoCampiConGestioneOmissisDaIgnorare;
	
	/**************************************
	 * IDUO ALBO PRETORIO REGGIO CALABARIA *
	 **************************************/
	private String idUoAlboReggio;
	
	/**************************************
	 * ID TIPO DOC ALBO PRETORIO          *
	 **************************************/
	private String idTipoDocAlbo;
	
	/**************************************
	 * PER PUBBLICAZIONE IN ALBO PRETORIO *
	 **************************************/
	
	private String desDirezioneProponente;
	private AllegatoProtocolloBean allegatoVistoContabile;
	
	/***************
	 * EMENDAMENTI *
	 ***************/

	private List<EmendamentoBean> listaEmendamenti;
	private Boolean listaEmendamentiBloccoRiordinoAut;
	
	/***********************
	 * PARAMETRI TIPO ATTO *
	 ***********************/
	
	private ParametriTipoAttoBean parametriTipoAtto;
	
	/*********************************
	 * IMPOSTAZIONI PER FILE UNIONE *
	 *********************************/
	
	private String unioneFileNomeFile;
	private String unioneFileNomeFileOmissis;
	
	private ImpostazioniUnioneFileBean impostazioniUnioneFile;
	
	/********************************
	 * ALLEGATE PARTE INT. SEPARATI *
	 ********************************/
	
	private Boolean flgAllegatiParteIntUniti;
	private List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparati;
	
	private Boolean flgAllegatiParteIntUnitiXPubbl;
	private List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparatiXPubbl;
	
	/****************************************
	 * ROLLBACK NUMERAZIONE DEFINITIVA ATTI *
	 ****************************************/
	
	private String esitoRollbackNumDefAtti;

	/***********************************************
	 * MESSAGGIO DI COMPLETAMENTO TASK PROVVISORIO *
	 ***********************************************/
	
	private String msgTaskProvvisorio;
	
	/****************************
	 * DATI PER NUOVO COME COPIA *
	 ****************************/
	
	private Boolean flgCopiaAllegatiPareri;
	
	/*******************
	 * ERRORI SUI FILE *
	 *******************/
	private HashMap<String, String> erroriFile;

	/***********************
	 * Getters and Setters *
	 ***********************/
	
	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getIdUdNuovoComeCopia() {
		return idUdNuovoComeCopia;
	}

	public void setIdUdNuovoComeCopia(String idUdNuovoComeCopia) {
		this.idUdNuovoComeCopia = idUdNuovoComeCopia;
	}

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
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

	public String getSiglaRegAttoDaCopiare() {
		return siglaRegAttoDaCopiare;
	}

	public void setSiglaRegAttoDaCopiare(String siglaRegAttoDaCopiare) {
		this.siglaRegAttoDaCopiare = siglaRegAttoDaCopiare;
	}

	public String getIdTipoDocDaCopiare() {
		return idTipoDocDaCopiare;
	}

	public void setIdTipoDocDaCopiare(String idTipoDocDaCopiare) {
		this.idTipoDocDaCopiare = idTipoDocDaCopiare;
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

	public String getRowidDoc() {
		return rowidDoc;
	}

	public void setRowidDoc(String rowidDoc) {
		this.rowidDoc = rowidDoc;
	}

	public String getIdDocPrimario() {
		return idDocPrimario;
	}

	public void setIdDocPrimario(String idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
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

	public Boolean getRemoteUriFilePrimario() {
		return remoteUriFilePrimario;
	}

	public void setRemoteUriFilePrimario(Boolean remoteUriFilePrimario) {
		this.remoteUriFilePrimario = remoteUriFilePrimario;
	}

	public MimeTypeFirmaBean getInfoFilePrimario() {
		return infoFilePrimario;
	}

	public void setInfoFilePrimario(MimeTypeFirmaBean infoFilePrimario) {
		this.infoFilePrimario = infoFilePrimario;
	}

	public Boolean getIsChangedFilePrimario() {
		return isChangedFilePrimario;
	}

	public void setIsChangedFilePrimario(Boolean isChangedFilePrimario) {
		this.isChangedFilePrimario = isChangedFilePrimario;
	}

	public String getIdDocPrimarioOmissis() {
		return idDocPrimarioOmissis;
	}

	public void setIdDocPrimarioOmissis(String idDocPrimarioOmissis) {
		this.idDocPrimarioOmissis = idDocPrimarioOmissis;
	}

	public String getNomeFilePrimarioOmissis() {
		return nomeFilePrimarioOmissis;
	}

	public void setNomeFilePrimarioOmissis(String nomeFilePrimarioOmissis) {
		this.nomeFilePrimarioOmissis = nomeFilePrimarioOmissis;
	}

	public String getUriFilePrimarioOmissis() {
		return uriFilePrimarioOmissis;
	}

	public void setUriFilePrimarioOmissis(String uriFilePrimarioOmissis) {
		this.uriFilePrimarioOmissis = uriFilePrimarioOmissis;
	}

	public Boolean getRemoteUriFilePrimarioOmissis() {
		return remoteUriFilePrimarioOmissis;
	}

	public void setRemoteUriFilePrimarioOmissis(Boolean remoteUriFilePrimarioOmissis) {
		this.remoteUriFilePrimarioOmissis = remoteUriFilePrimarioOmissis;
	}

	public MimeTypeFirmaBean getInfoFilePrimarioOmissis() {
		return infoFilePrimarioOmissis;
	}

	public void setInfoFilePrimarioOmissis(MimeTypeFirmaBean infoFilePrimarioOmissis) {
		this.infoFilePrimarioOmissis = infoFilePrimarioOmissis;
	}

	public Boolean getIsChangedFilePrimarioOmissis() {
		return isChangedFilePrimarioOmissis;
	}

	public void setIsChangedFilePrimarioOmissis(Boolean isChangedFilePrimarioOmissis) {
		this.isChangedFilePrimarioOmissis = isChangedFilePrimarioOmissis;
	}

	public String getDerivaDaRdA() {
		return derivaDaRdA;
	}

	public void setDerivaDaRdA(String derivaDaRdA) {
		this.derivaDaRdA = derivaDaRdA;
	}

	public Boolean getFlgAggiornataVersDaPubblicare() {
		return flgAggiornataVersDaPubblicare;
	}

	public void setFlgAggiornataVersDaPubblicare(Boolean flgAggiornataVersDaPubblicare) {
		this.flgAggiornataVersDaPubblicare = flgAggiornataVersDaPubblicare;
	}

	public String getUriDocGeneratoFormatoOdt() {
		return uriDocGeneratoFormatoOdt;
	}

	public void setUriDocGeneratoFormatoOdt(String uriDocGeneratoFormatoOdt) {
		this.uriDocGeneratoFormatoOdt = uriDocGeneratoFormatoOdt;
	}

	public Boolean getFlgForzataModificaAtto() {
		return flgForzataModificaAtto;
	}

	public void setFlgForzataModificaAtto(Boolean flgForzataModificaAtto) {
		this.flgForzataModificaAtto = flgForzataModificaAtto;
	}

	public Boolean getAbilModificaCodAttoCMTO() {
		return abilModificaCodAttoCMTO;
	}

	public void setAbilModificaCodAttoCMTO(Boolean abilModificaCodAttoCMTO) {
		this.abilModificaCodAttoCMTO = abilModificaCodAttoCMTO;
	}

	public String getIdTipoProcRevocaAtto() {
		return idTipoProcRevocaAtto;
	}

	public void setIdTipoProcRevocaAtto(String idTipoProcRevocaAtto) {
		this.idTipoProcRevocaAtto = idTipoProcRevocaAtto;
	}

	public String getNomeTipoProcRevocaAtto() {
		return nomeTipoProcRevocaAtto;
	}

	public void setNomeTipoProcRevocaAtto(String nomeTipoProcRevocaAtto) {
		this.nomeTipoProcRevocaAtto = nomeTipoProcRevocaAtto;
	}

	public String getIdDefFlussoWFRevocaAtto() {
		return idDefFlussoWFRevocaAtto;
	}

	public void setIdDefFlussoWFRevocaAtto(String idDefFlussoWFRevocaAtto) {
		this.idDefFlussoWFRevocaAtto = idDefFlussoWFRevocaAtto;
	}

	public String getIdTipoDocPropostaRevocaAtto() {
		return idTipoDocPropostaRevocaAtto;
	}

	public void setIdTipoDocPropostaRevocaAtto(String idTipoDocPropostaRevocaAtto) {
		this.idTipoDocPropostaRevocaAtto = idTipoDocPropostaRevocaAtto;
	}

	public String getNomeTipoDocPropostaRevocaAtto() {
		return nomeTipoDocPropostaRevocaAtto;
	}

	public void setNomeTipoDocPropostaRevocaAtto(String nomeTipoDocPropostaRevocaAtto) {
		this.nomeTipoDocPropostaRevocaAtto = nomeTipoDocPropostaRevocaAtto;
	}

	public String getSiglaPropostaRevocaAtto() {
		return siglaPropostaRevocaAtto;
	}

	public void setSiglaPropostaRevocaAtto(String siglaPropostaRevocaAtto) {
		this.siglaPropostaRevocaAtto = siglaPropostaRevocaAtto;
	}

	public String getIdTipoProcEmendamento() {
		return idTipoProcEmendamento;
	}

	public void setIdTipoProcEmendamento(String idTipoProcEmendamento) {
		this.idTipoProcEmendamento = idTipoProcEmendamento;
	}

	public String getNomeTipoProcEmendamento() {
		return nomeTipoProcEmendamento;
	}

	public void setNomeTipoProcEmendamento(String nomeTipoProcEmendamento) {
		this.nomeTipoProcEmendamento = nomeTipoProcEmendamento;
	}

	public String getIdDefFlussoWFEmendamento() {
		return idDefFlussoWFEmendamento;
	}

	public void setIdDefFlussoWFEmendamento(String idDefFlussoWFEmendamento) {
		this.idDefFlussoWFEmendamento = idDefFlussoWFEmendamento;
	}

	public String getIdTipoDocPropostaEmendamento() {
		return idTipoDocPropostaEmendamento;
	}

	public void setIdTipoDocPropostaEmendamento(String idTipoDocPropostaEmendamento) {
		this.idTipoDocPropostaEmendamento = idTipoDocPropostaEmendamento;
	}

	public String getNomeTipoDocPropostaEmendamento() {
		return nomeTipoDocPropostaEmendamento;
	}

	public void setNomeTipoDocPropostaEmendamento(String nomeTipoDocPropostaEmendamento) {
		this.nomeTipoDocPropostaEmendamento = nomeTipoDocPropostaEmendamento;
	}

	public String getSiglaPropostaEmendamento() {
		return siglaPropostaEmendamento;
	}

	public void setSiglaPropostaEmendamento(String siglaPropostaEmendamento) {
		this.siglaPropostaEmendamento = siglaPropostaEmendamento;
	}

	public String getTipoAttoRifEmendamento() {
		return tipoAttoRifEmendamento;
	}

	public void setTipoAttoRifEmendamento(String tipoAttoRifEmendamento) {
		this.tipoAttoRifEmendamento = tipoAttoRifEmendamento;
	}

	public String getSiglaAttoRifEmendamento() {
		return siglaAttoRifEmendamento;
	}

	public void setSiglaAttoRifEmendamento(String siglaAttoRifEmendamento) {
		this.siglaAttoRifEmendamento = siglaAttoRifEmendamento;
	}

	public String getNumeroAttoRifEmendamento() {
		return numeroAttoRifEmendamento;
	}

	public void setNumeroAttoRifEmendamento(String numeroAttoRifEmendamento) {
		this.numeroAttoRifEmendamento = numeroAttoRifEmendamento;
	}

	public String getAnnoAttoRifEmendamento() {
		return annoAttoRifEmendamento;
	}

	public void setAnnoAttoRifEmendamento(String annoAttoRifEmendamento) {
		this.annoAttoRifEmendamento = annoAttoRifEmendamento;
	}

	public String getIdEmendamentoRif() {
		return idEmendamentoRif;
	}

	public void setIdEmendamentoRif(String idEmendamentoRif) {
		this.idEmendamentoRif = idEmendamentoRif;
	}

	public String getNumeroEmendamentoRif() {
		return numeroEmendamentoRif;
	}

	public void setNumeroEmendamentoRif(String numeroEmendamentoRif) {
		this.numeroEmendamentoRif = numeroEmendamentoRif;
	}

	public String getCategoriaRegAvvio() {
		return categoriaRegAvvio;
	}

	public void setCategoriaRegAvvio(String categoriaRegAvvio) {
		this.categoriaRegAvvio = categoriaRegAvvio;
	}

	public String getSiglaRegAvvio() {
		return siglaRegAvvio;
	}

	public void setSiglaRegAvvio(String siglaRegAvvio) {
		this.siglaRegAvvio = siglaRegAvvio;
	}

	public String getSiglaRegistrazione() {
		return siglaRegistrazione;
	}

	public void setSiglaRegistrazione(String siglaRegistrazione) {
		this.siglaRegistrazione = siglaRegistrazione;
	}

	public String getNumeroRegistrazione() {
		return numeroRegistrazione;
	}

	public void setNumeroRegistrazione(String numeroRegistrazione) {
		this.numeroRegistrazione = numeroRegistrazione;
	}

	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public String getAnnoRegistrazione() {
		return annoRegistrazione;
	}

	public void setAnnoRegistrazione(String annoRegistrazione) {
		this.annoRegistrazione = annoRegistrazione;
	}

	public String getDesUserRegistrazione() {
		return desUserRegistrazione;
	}

	public void setDesUserRegistrazione(String desUserRegistrazione) {
		this.desUserRegistrazione = desUserRegistrazione;
	}

	public String getDesUORegistrazione() {
		return desUORegistrazione;
	}

	public void setDesUORegistrazione(String desUORegistrazione) {
		this.desUORegistrazione = desUORegistrazione;
	}

	public String getEstremiRepertorioPerStruttura() {
		return estremiRepertorioPerStruttura;
	}

	public void setEstremiRepertorioPerStruttura(String estremiRepertorioPerStruttura) {
		this.estremiRepertorioPerStruttura = estremiRepertorioPerStruttura;
	}

	public String getSiglaRegProvvisoria() {
		return siglaRegProvvisoria;
	}

	public void setSiglaRegProvvisoria(String siglaRegProvvisoria) {
		this.siglaRegProvvisoria = siglaRegProvvisoria;
	}

	public String getNumeroRegProvvisoria() {
		return numeroRegProvvisoria;
	}

	public void setNumeroRegProvvisoria(String numeroRegProvvisoria) {
		this.numeroRegProvvisoria = numeroRegProvvisoria;
	}

	public Date getDataRegProvvisoria() {
		return dataRegProvvisoria;
	}

	public void setDataRegProvvisoria(Date dataRegProvvisoria) {
		this.dataRegProvvisoria = dataRegProvvisoria;
	}

	public String getAnnoRegProvvisoria() {
		return annoRegProvvisoria;
	}

	public void setAnnoRegProvvisoria(String annoRegProvvisoria) {
		this.annoRegProvvisoria = annoRegProvvisoria;
	}

	public String getDesUserRegProvvisoria() {
		return desUserRegProvvisoria;
	}

	public void setDesUserRegProvvisoria(String desUserRegProvvisoria) {
		this.desUserRegProvvisoria = desUserRegProvvisoria;
	}

	public String getDesUORegProvvisoria() {
		return desUORegProvvisoria;
	}

	public void setDesUORegProvvisoria(String desUORegProvvisoria) {
		this.desUORegProvvisoria = desUORegProvvisoria;
	}

	public String getIdDocPrimarioLiquidazione() {
		return idDocPrimarioLiquidazione;
	}

	public void setIdDocPrimarioLiquidazione(String idDocPrimarioLiquidazione) {
		this.idDocPrimarioLiquidazione = idDocPrimarioLiquidazione;
	}

	public String getIdUdLiquidazione() {
		return idUdLiquidazione;
	}

	public void setIdUdLiquidazione(String idUdLiquidazione) {
		this.idUdLiquidazione = idUdLiquidazione;
	}

	public String getCodTipoLiquidazioneXContabilia() {
		return codTipoLiquidazioneXContabilia;
	}

	public void setCodTipoLiquidazioneXContabilia(String codTipoLiquidazioneXContabilia) {
		this.codTipoLiquidazioneXContabilia = codTipoLiquidazioneXContabilia;
	}

	public String getSiglaRegLiquidazione() {
		return siglaRegLiquidazione;
	}

	public void setSiglaRegLiquidazione(String siglaRegLiquidazione) {
		this.siglaRegLiquidazione = siglaRegLiquidazione;
	}

	public String getAnnoRegLiquidazione() {
		return annoRegLiquidazione;
	}

	public void setAnnoRegLiquidazione(String annoRegLiquidazione) {
		this.annoRegLiquidazione = annoRegLiquidazione;
	}

	public String getNroRegLiquidazione() {
		return nroRegLiquidazione;
	}

	public void setNroRegLiquidazione(String nroRegLiquidazione) {
		this.nroRegLiquidazione = nroRegLiquidazione;
	}

	public Date getDataAdozioneLiquidazione() {
		return dataAdozioneLiquidazione;
	}

	public void setDataAdozioneLiquidazione(Date dataAdozioneLiquidazione) {
		this.dataAdozioneLiquidazione = dataAdozioneLiquidazione;
	}

	public String getEstremiAttoLiquidazione() {
		return estremiAttoLiquidazione;
	}

	public void setEstremiAttoLiquidazione(String estremiAttoLiquidazione) {
		this.estremiAttoLiquidazione = estremiAttoLiquidazione;
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

	public Date getDataInizioPubblicazione() {
		return dataInizioPubblicazione;
	}

	public void setDataInizioPubblicazione(Date dataInizioPubblicazione) {
		this.dataInizioPubblicazione = dataInizioPubblicazione;
	}

	public String getGiorniPubblicazione() {
		return giorniPubblicazione;
	}

	public void setGiorniPubblicazione(String giorniPubblicazione) {
		this.giorniPubblicazione = giorniPubblicazione;
	}

	public String getTipoAttoEmendamento() {
		return tipoAttoEmendamento;
	}

	public void setTipoAttoEmendamento(String tipoAttoEmendamento) {
		this.tipoAttoEmendamento = tipoAttoEmendamento;
	}

	public String getSiglaAttoEmendamento() {
		return siglaAttoEmendamento;
	}

	public void setSiglaAttoEmendamento(String siglaAttoEmendamento) {
		this.siglaAttoEmendamento = siglaAttoEmendamento;
	}

	public String getNumeroAttoEmendamento() {
		return numeroAttoEmendamento;
	}

	public void setNumeroAttoEmendamento(String numeroAttoEmendamento) {
		this.numeroAttoEmendamento = numeroAttoEmendamento;
	}

	public String getAnnoAttoEmendamento() {
		return annoAttoEmendamento;
	}

	public void setAnnoAttoEmendamento(String annoAttoEmendamento) {
		this.annoAttoEmendamento = annoAttoEmendamento;
	}

	public String getIdEmendamento() {
		return idEmendamento;
	}

	public void setIdEmendamento(String idEmendamento) {
		this.idEmendamento = idEmendamento;
	}

	public String getNumeroEmendamento() {
		return numeroEmendamento;
	}

	public void setNumeroEmendamento(String numeroEmendamento) {
		this.numeroEmendamento = numeroEmendamento;
	}

	public String getFlgEmendamentoSuFile() {
		return flgEmendamentoSuFile;
	}

	public void setFlgEmendamentoSuFile(String flgEmendamentoSuFile) {
		this.flgEmendamentoSuFile = flgEmendamentoSuFile;
	}

	public String getNumeroAllegatoEmendamento() {
		return numeroAllegatoEmendamento;
	}

	public void setNumeroAllegatoEmendamento(String numeroAllegatoEmendamento) {
		this.numeroAllegatoEmendamento = numeroAllegatoEmendamento;
	}

	public Boolean getFlgEmendamentoIntegraleAllegato() {
		return flgEmendamentoIntegraleAllegato;
	}

	public void setFlgEmendamentoIntegraleAllegato(Boolean flgEmendamentoIntegraleAllegato) {
		this.flgEmendamentoIntegraleAllegato = flgEmendamentoIntegraleAllegato;
	}

	public String getNumeroPaginaEmendamento() {
		return numeroPaginaEmendamento;
	}

	public void setNumeroPaginaEmendamento(String numeroPaginaEmendamento) {
		this.numeroPaginaEmendamento = numeroPaginaEmendamento;
	}

	public String getNumeroRigaEmendamento() {
		return numeroRigaEmendamento;
	}

	public void setNumeroRigaEmendamento(String numeroRigaEmendamento) {
		this.numeroRigaEmendamento = numeroRigaEmendamento;
	}

	public String getEffettoEmendamento() {
		return effettoEmendamento;
	}

	public void setEffettoEmendamento(String effettoEmendamento) {
		this.effettoEmendamento = effettoEmendamento;
	}
	
	public Boolean getFlgAttivaDestinatari() {
		return flgAttivaDestinatari;
	}

	public void setFlgAttivaDestinatari(Boolean flgAttivaDestinatari) {
		this.flgAttivaDestinatari = flgAttivaDestinatari;
	}

	public List<DestAttoBean> getListaDestinatariAtto() {
		return listaDestinatariAtto;
	}

	public void setListaDestinatariAtto(List<DestAttoBean> listaDestinatariAtto) {
		this.listaDestinatariAtto = listaDestinatariAtto;
	}

	public List<DestAttoBean> getListaDestinatariPCAtto() {
		return listaDestinatariPCAtto;
	}

	public void setListaDestinatariPCAtto(List<DestAttoBean> listaDestinatariPCAtto) {
		this.listaDestinatariPCAtto = listaDestinatariPCAtto;
	}

	public String getIniziativaProposta() {
		return iniziativaProposta;
	}

	public void setIniziativaProposta(String iniziativaProposta) {
		this.iniziativaProposta = iniziativaProposta;
	}

	public Boolean getFlgAttoMeroIndirizzo() {
		return flgAttoMeroIndirizzo;
	}

	public void setFlgAttoMeroIndirizzo(Boolean flgAttoMeroIndirizzo) {
		this.flgAttoMeroIndirizzo = flgAttoMeroIndirizzo;
	}

	public Boolean getFlgAttoCommissario() {
		return flgAttoCommissario;
	}

	public void setFlgAttoCommissario(Boolean flgAttoCommissario) {
		this.flgAttoCommissario = flgAttoCommissario;
	}

	public Boolean getFlgModificaRegolamento() {
		return flgModificaRegolamento;
	}

	public void setFlgModificaRegolamento(Boolean flgModificaRegolamento) {
		this.flgModificaRegolamento = flgModificaRegolamento;
	}

	public Boolean getFlgModificaStatuto() {
		return flgModificaStatuto;
	}

	public void setFlgModificaStatuto(Boolean flgModificaStatuto) {
		this.flgModificaStatuto = flgModificaStatuto;
	}

	public Boolean getFlgNomina() {
		return flgNomina;
	}

	public void setFlgNomina(Boolean flgNomina) {
		this.flgNomina = flgNomina;
	}

	public Boolean getFlgRatificaDeliberaUrgenza() {
		return flgRatificaDeliberaUrgenza;
	}

	public void setFlgRatificaDeliberaUrgenza(Boolean flgRatificaDeliberaUrgenza) {
		this.flgRatificaDeliberaUrgenza = flgRatificaDeliberaUrgenza;
	}

	public Boolean getFlgAttoUrgente() {
		return flgAttoUrgente;
	}

	public void setFlgAttoUrgente(Boolean flgAttoUrgente) {
		this.flgAttoUrgente = flgAttoUrgente;
	}

	public Boolean getFlgCommissioniTipoProposta() {
		return flgCommissioniTipoProposta;
	}

	public void setFlgCommissioniTipoProposta(Boolean flgCommissioniTipoProposta) {
		this.flgCommissioniTipoProposta = flgCommissioniTipoProposta;
	}

	public List<SimpleKeyValueBean> getListaCircoscrizioni() {
		return listaCircoscrizioni;
	}

	public void setListaCircoscrizioni(List<SimpleKeyValueBean> listaCircoscrizioni) {
		this.listaCircoscrizioni = listaCircoscrizioni;
	}

	public String getTipoInterpellanza() {
		return tipoInterpellanza;
	}

	public void setTipoInterpellanza(String tipoInterpellanza) {
		this.tipoInterpellanza = tipoInterpellanza;
	}

	public String getMotivazioneInterpellanzaRispScritta() {
		return motivazioneInterpellanzaRispScritta;
	}

	public void setMotivazioneInterpellanzaRispScritta(String motivazioneInterpellanzaRispScritta) {
		this.motivazioneInterpellanzaRispScritta = motivazioneInterpellanzaRispScritta;
	}

	public String getTipoOrdMobilita() {
		return tipoOrdMobilita;
	}

	public void setTipoOrdMobilita(String tipoOrdMobilita) {
		this.tipoOrdMobilita = tipoOrdMobilita;
	}

	public Date getDataInizioVldOrdinanza() {
		return dataInizioVldOrdinanza;
	}

	public void setDataInizioVldOrdinanza(Date dataInizioVldOrdinanza) {
		this.dataInizioVldOrdinanza = dataInizioVldOrdinanza;
	}

	public Date getDataFineVldOrdinanza() {
		return dataFineVldOrdinanza;
	}

	public void setDataFineVldOrdinanza(Date dataFineVldOrdinanza) {
		this.dataFineVldOrdinanza = dataFineVldOrdinanza;
	}

	public String getTipoLuogoOrdMobilita() {
		return tipoLuogoOrdMobilita;
	}

	public void setTipoLuogoOrdMobilita(String tipoLuogoOrdMobilita) {
		this.tipoLuogoOrdMobilita = tipoLuogoOrdMobilita;
	}

	public List<AltraViaProtBean> getListaIndirizziOrdMobilita() {
		return listaIndirizziOrdMobilita;
	}

	public void setListaIndirizziOrdMobilita(List<AltraViaProtBean> listaIndirizziOrdMobilita) {
		this.listaIndirizziOrdMobilita = listaIndirizziOrdMobilita;
	}

	public String getLuogoOrdMobilita() {
		return luogoOrdMobilita;
	}

	public void setLuogoOrdMobilita(String luogoOrdMobilita) {
		this.luogoOrdMobilita = luogoOrdMobilita;
	}	
	
	public String getLuogoOrdMobilitaFile() {
		return luogoOrdMobilitaFile;
	}

	public void setLuogoOrdMobilitaFile(String luogoOrdMobilitaFile) {
		this.luogoOrdMobilitaFile = luogoOrdMobilitaFile;
	}

	public List<SimpleKeyValueBean> getListaCircoscrizioniOrdMobilita() {
		return listaCircoscrizioniOrdMobilita;
	}

	public void setListaCircoscrizioniOrdMobilita(List<SimpleKeyValueBean> listaCircoscrizioniOrdMobilita) {
		this.listaCircoscrizioniOrdMobilita = listaCircoscrizioniOrdMobilita;
	}

	public String getDescrizioneOrdMobilita() {
		return descrizioneOrdMobilita;
	}

	public void setDescrizioneOrdMobilita(String descrizioneOrdMobilita) {
		this.descrizioneOrdMobilita = descrizioneOrdMobilita;
	}
	
	public String getDescrizioneOrdMobilitaFile() {
		return descrizioneOrdMobilitaFile;
	}

	public void setDescrizioneOrdMobilitaFile(String descrizioneOrdMobilitaFile) {
		this.descrizioneOrdMobilitaFile = descrizioneOrdMobilitaFile;
	}

	public List<ProponentiBean> getListaProponenti() {
		return listaProponenti;
	}

	public void setListaProponenti(List<ProponentiBean> listaProponenti) {
		this.listaProponenti = listaProponenti;
	}

	public String getUfficioProponente() {
		return ufficioProponente;
	}

	public void setUfficioProponente(String ufficioProponente) {
		this.ufficioProponente = ufficioProponente;
	}

	public String getCodUfficioProponente() {
		return codUfficioProponente;
	}

	public void setCodUfficioProponente(String codUfficioProponente) {
		this.codUfficioProponente = codUfficioProponente;
	}

	public String getDesUfficioProponente() {
		return desUfficioProponente;
	}

	public void setDesUfficioProponente(String desUfficioProponente) {
		this.desUfficioProponente = desUfficioProponente;
	}

	public String getFlgUfficioProponenteGare() {
		return flgUfficioProponenteGare;
	}

	public void setFlgUfficioProponenteGare(String flgUfficioProponenteGare) {
		this.flgUfficioProponenteGare = flgUfficioProponenteGare;
	}

	public List<SelezionaUOBean> getListaUfficioProponente() {
		return listaUfficioProponente;
	}

	public void setListaUfficioProponente(List<SelezionaUOBean> listaUfficioProponente) {
		this.listaUfficioProponente = listaUfficioProponente;
	}

	public String getTipoProvvedimento() {
		return tipoProvvedimento;
	}

	public void setTipoProvvedimento(String tipoProvvedimento) {
		this.tipoProvvedimento = tipoProvvedimento;
	}

	public String getSottotipoDelibera() {
		return sottotipoDelibera;
	}

	public void setSottotipoDelibera(String sottotipoDelibera) {
		this.sottotipoDelibera = sottotipoDelibera;
	}

	public List<UfficioGareAcquistiBean> getListaUfficioGareAcquisti() {
		return listaUfficioGareAcquisti;
	}

	public void setListaUfficioGareAcquisti(List<UfficioGareAcquistiBean> listaUfficioGareAcquisti) {
		this.listaUfficioGareAcquisti = listaUfficioGareAcquisti;
	}

	public String getFlgProcExCodAppalti() {
		return flgProcExCodAppalti;
	}

	public void setFlgProcExCodAppalti(String flgProcExCodAppalti) {
		this.flgProcExCodAppalti = flgProcExCodAppalti;
	}

	public String getUfficioCompetente() {
		return ufficioCompetente;
	}

	public void setUfficioCompetente(String ufficioCompetente) {
		this.ufficioCompetente = ufficioCompetente;
	}

	public String getCodUfficioCompetente() {
		return codUfficioCompetente;
	}

	public void setCodUfficioCompetente(String codUfficioCompetente) {
		this.codUfficioCompetente = codUfficioCompetente;
	}

	public String getDesUfficioCompetente() {
		return desUfficioCompetente;
	}

	public void setDesUfficioCompetente(String desUfficioCompetente) {
		this.desUfficioCompetente = desUfficioCompetente;
	}

	public List<SelezionaUOBean> getListaUfficioCompetente() {
		return listaUfficioCompetente;
	}

	public void setListaUfficioCompetente(List<SelezionaUOBean> listaUfficioCompetente) {
		this.listaUfficioCompetente = listaUfficioCompetente;
	}
	
	public List<RUPCompletaBean> getListaRUPCodAppalti() {
		return listaRUPCodAppalti;
	}

	public void setListaRUPCodAppalti(List<RUPCompletaBean> listaRUPCodAppalti) {
		this.listaRUPCodAppalti = listaRUPCodAppalti;
	}

	public List<DirigenteAdottanteBean> getListaAdottante() {
		return listaAdottante;
	}

	public void setListaAdottante(List<DirigenteAdottanteBean> listaAdottante) {
		this.listaAdottante = listaAdottante;
	}
	
	public List<RdPCompletaBean> getListaRdPCodAppalti() {
		return listaRdPCodAppalti;
	}

	public void setListaRdPCodAppalti(List<RdPCompletaBean> listaRdPCodAppalti) {
		this.listaRdPCodAppalti = listaRdPCodAppalti;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public List<DirigenteDiConcertoBean> getListaDirigentiConcerto() {
		return listaDirigentiConcerto;
	}

	public void setListaDirigentiConcerto(List<DirigenteDiConcertoBean> listaDirigentiConcerto) {
		this.listaDirigentiConcerto = listaDirigentiConcerto;
	}

	public List<DirigenteRespRegTecnicaBean> getListaDirRespRegTecnica() {
		return listaDirRespRegTecnica;
	}

	public void setListaDirRespRegTecnica(List<DirigenteRespRegTecnicaBean> listaDirRespRegTecnica) {
		this.listaDirRespRegTecnica = listaDirRespRegTecnica;
	}

	public List<AltriDirRespRegTecnicaBean> getListaAltriDirRespRegTecnica() {
		return listaAltriDirRespRegTecnica;
	}

	public void setListaAltriDirRespRegTecnica(List<AltriDirRespRegTecnicaBean> listaAltriDirRespRegTecnica) {
		this.listaAltriDirRespRegTecnica = listaAltriDirRespRegTecnica;
	}

	public List<RdPCompletaBean> getListaRdP() {
		return listaRdP;
	}

	public void setListaRdP(List<RdPCompletaBean> listaRdP) {
		this.listaRdP = listaRdP;
	}

	public String getCodProcedimento() {
		return codProcedimento;
	}

	public void setCodProcedimento(String codProcedimento) {
		this.codProcedimento = codProcedimento;
	}

	public String getDesProcedimento() {
		return desProcedimento;
	}

	public void setDesProcedimento(String desProcedimento) {
		this.desProcedimento = desProcedimento;
	}

	public List<RUPCompletaBean> getListaRUP() {
		return listaRUP;
	}

	public void setListaRUP(List<RUPCompletaBean> listaRUP) {
		this.listaRUP = listaRUP;
	}
	
	public List<ScrivaniaDECBean> getListaScrivaniaDEC() {
		return listaScrivaniaDEC;
	}

	public void setListaScrivaniaDEC(List<ScrivaniaDECBean> listaScrivaniaDEC) {
		this.listaScrivaniaDEC = listaScrivaniaDEC;
	}

	public List<AssessoreBean> getListaAssessori() {
		return listaAssessori;
	}

	public void setListaAssessori(List<AssessoreBean> listaAssessori) {
		this.listaAssessori = listaAssessori;
	}

	public List<AssessoreBean> getListaAltriAssessori() {
		return listaAltriAssessori;
	}

	public void setListaAltriAssessori(List<AssessoreBean> listaAltriAssessori) {
		this.listaAltriAssessori = listaAltriAssessori;
	}

	public List<ProponenteAttoConsiglioBean> getListaProponenteAttoConsiglio() {
		return listaProponenteAttoConsiglio;
	}

	public void setListaProponenteAttoConsiglio(List<ProponenteAttoConsiglioBean> listaProponenteAttoConsiglio) {
		this.listaProponenteAttoConsiglio = listaProponenteAttoConsiglio;
	}

	public List<ConsigliereBean> getListaConsiglieri() {
		return listaConsiglieri;
	}

	public void setListaConsiglieri(List<ConsigliereBean> listaConsiglieri) {
		this.listaConsiglieri = listaConsiglieri;
	}

	public List<ConsigliereBean> getListaAltriConsiglieri() {
		return listaAltriConsiglieri;
	}

	public void setListaAltriConsiglieri(List<ConsigliereBean> listaAltriConsiglieri) {
		this.listaAltriConsiglieri = listaAltriConsiglieri;
	}

	public Date getDataTermFirmeConsiglieri() {
		return dataTermFirmeConsiglieri;
	}

	public void setDataTermFirmeConsiglieri(Date dataTermFirmeConsiglieri) {
		this.dataTermFirmeConsiglieri = dataTermFirmeConsiglieri;
	}

	public List<DirigenteProponenteBean> getListaDirigentiProponenti() {
		return listaDirigentiProponenti;
	}

	public void setListaDirigentiProponenti(List<DirigenteProponenteBean> listaDirigentiProponenti) {
		this.listaDirigentiProponenti = listaDirigentiProponenti;
	}

	public List<DirigenteProponenteBean> getListaAltriDirigentiProponenti() {
		return listaAltriDirigentiProponenti;
	}

	public void setListaAltriDirigentiProponenti(List<DirigenteProponenteBean> listaAltriDirigentiProponenti) {
		this.listaAltriDirigentiProponenti = listaAltriDirigentiProponenti;
	}

	public List<CoordinatoreCompCircBean> getListaCoordinatoriCompCirc() {
		return listaCoordinatoriCompCirc;
	}

	public void setListaCoordinatoriCompCirc(List<CoordinatoreCompCircBean> listaCoordinatoriCompCirc) {
		this.listaCoordinatoriCompCirc = listaCoordinatoriCompCirc;
	}

	public Boolean getFlgRichiediVistoDirettore() {
		return flgRichiediVistoDirettore;
	}

	public void setFlgRichiediVistoDirettore(Boolean flgRichiediVistoDirettore) {
		this.flgRichiediVistoDirettore = flgRichiediVistoDirettore;
	}

	public List<ResponsabileVistiConformitaBean> getListaRespVistiConformita() {
		return listaRespVistiConformita;
	}

	public void setListaRespVistiConformita(List<ResponsabileVistiConformitaBean> listaRespVistiConformita) {
		this.listaRespVistiConformita = listaRespVistiConformita;
	}
	
	public List<ResponsabileUfficioPropBean> getListaRespUfficioProp() {
		return listaRespUfficioProp;
	}

	public void setListaRespUfficioProp(List<ResponsabileUfficioPropBean> listaRespUfficioProp) {
		this.listaRespUfficioProp = listaRespUfficioProp;
	}

	public List<ResponsabileVistiPerfezionamentoBean> getListaRespVistiPerfezionamento() {
		return listaRespVistiPerfezionamento;
	}

	public void setListaRespVistiPerfezionamento(List<ResponsabileVistiPerfezionamentoBean> listaRespVistiPerfezionamento) {
		this.listaRespVistiPerfezionamento = listaRespVistiPerfezionamento;
	}

	public String getFlgVistoBilancio() {
		return flgVistoBilancio;
	}

	public void setFlgVistoBilancio(String flgVistoBilancio) {
		this.flgVistoBilancio = flgVistoBilancio;
	}

	public List<RespVisAltBilancioBean> getListaRespVisAltBilancio() {
		return listaRespVisAltBilancio;
	}

	public void setListaRespVisAltBilancio(List<RespVisAltBilancioBean> listaRespVisAltBilancio) {
		this.listaRespVisAltBilancio = listaRespVisAltBilancio;
	}

	public String getTipoVistoBilancio() {
		return tipoVistoBilancio;
	}

	public void setTipoVistoBilancio(String tipoVistoBilancio) {
		this.tipoVistoBilancio = tipoVistoBilancio;
	}

	public Boolean getFlgVistoSG() {
		return flgVistoSG;
	}

	public void setFlgVistoSG(Boolean flgVistoSG) {
		this.flgVistoSG = flgVistoSG;
	}

	public Boolean getFlgVistoPresidente() {
		return flgVistoPresidente;
	}

	public void setFlgVistoPresidente(Boolean flgVistoPresidente) {
		this.flgVistoPresidente = flgVistoPresidente;
	}

	public List<EstensoreBean> getListaEstensori() {
		return listaEstensori;
	}

	public void setListaEstensori(List<EstensoreBean> listaEstensori) {
		this.listaEstensori = listaEstensori;
	}

	public List<EstensoreBean> getListaAltriEstensori() {
		return listaAltriEstensori;
	}

	public void setListaAltriEstensori(List<EstensoreBean> listaAltriEstensori) {
		this.listaAltriEstensori = listaAltriEstensori;
	}

	public List<IstruttoreBean> getListaIstruttori() {
		return listaIstruttori;
	}

	public void setListaIstruttori(List<IstruttoreBean> listaIstruttori) {
		this.listaIstruttori = listaIstruttori;
	}

	public List<IstruttoreBean> getListaAltriIstruttori() {
		return listaAltriIstruttori;
	}

	public void setListaAltriIstruttori(List<IstruttoreBean> listaAltriIstruttori) {
		this.listaAltriIstruttori = listaAltriIstruttori;
	}
	
	public List<UtenteRifAttoConsiglioBean> getListaUtenteRifAttoConsiglio() {
		return listaUtenteRifAttoConsiglio;
	}

	public void setListaUtenteRifAttoConsiglio(List<UtenteRifAttoConsiglioBean> listaUtenteRifAttoConsiglio) {
		this.listaUtenteRifAttoConsiglio = listaUtenteRifAttoConsiglio;
	}

	public Boolean getFlgSenzaValidazionePO() {
		return flgSenzaValidazionePO;
	}

	public void setFlgSenzaValidazionePO(Boolean flgSenzaValidazionePO) {
		this.flgSenzaValidazionePO = flgSenzaValidazionePO;
	}

	public Boolean getFlgVistoRespUff() {
		return flgVistoRespUff;
	}

	public void setFlgVistoRespUff(Boolean flgVistoRespUff) {
		this.flgVistoRespUff = flgVistoRespUff;
	}

	public Boolean getFlgVistoDirSup1() {
		return flgVistoDirSup1;
	}

	public void setFlgVistoDirSup1(Boolean flgVistoDirSup1) {
		this.flgVistoDirSup1 = flgVistoDirSup1;
	}

	public Boolean getFlgVistoDirSup2() {
		return flgVistoDirSup2;
	}

	public void setFlgVistoDirSup2(Boolean flgVistoDirSup2) {
		this.flgVistoDirSup2 = flgVistoDirSup2;
	}

	public String getIdScrivaniaVistoRespUff() {
		return idScrivaniaVistoRespUff;
	}

	public void setIdScrivaniaVistoRespUff(String idScrivaniaVistoRespUff) {
		this.idScrivaniaVistoRespUff = idScrivaniaVistoRespUff;
	}

	public String getIdScrivaniaVistoDirSup1() {
		return idScrivaniaVistoDirSup1;
	}

	public void setIdScrivaniaVistoDirSup1(String idScrivaniaVistoDirSup1) {
		this.idScrivaniaVistoDirSup1 = idScrivaniaVistoDirSup1;
	}

	public String getIdScrivaniaVistoDirSup2() {
		return idScrivaniaVistoDirSup2;
	}

	public void setIdScrivaniaVistoDirSup2(String idScrivaniaVistoDirSup2) {
		this.idScrivaniaVistoDirSup2 = idScrivaniaVistoDirSup2;
	}

	public List<SimpleKeyValueBean> getListaParereCircoscrizioni() {
		return listaParereCircoscrizioni;
	}

	public void setListaParereCircoscrizioni(List<SimpleKeyValueBean> listaParereCircoscrizioni) {
		this.listaParereCircoscrizioni = listaParereCircoscrizioni;
	}

	public Boolean getFlgCommissioni() {
		return flgCommissioni;
	}

	public void setFlgCommissioni(Boolean flgCommissioni) {
		this.flgCommissioni = flgCommissioni;
	}

	public List<SimpleKeyValueBean> getListaParereCommissioni() {
		return listaParereCommissioni;
	}

	public void setListaParereCommissioni(List<SimpleKeyValueBean> listaParereCommissioni) {
		this.listaParereCommissioni = listaParereCommissioni;
	}
	
	public String getDesOgg() {
		return desOgg;
	}

	public void setDesOgg(String desOgg) {
		this.desOgg = desOgg;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getOggettoHtml() {
		return oggettoHtml;
	}

	public void setOggettoHtml(String oggettoHtml) {
		this.oggettoHtml = oggettoHtml;
	}
	
	public String getOggettoHtmlFile() {
		return oggettoHtmlFile;
	}

	public void setOggettoHtmlFile(String oggettoHtmlFile) {
		this.oggettoHtmlFile = oggettoHtmlFile;
	}

	public List<AttoRiferimentoBean> getListaAttiRiferimento() {
		return listaAttiRiferimento;
	}

	public void setListaAttiRiferimento(List<AttoRiferimentoBean> listaAttiRiferimento) {
		this.listaAttiRiferimento = listaAttiRiferimento;
	}
	
//	public String getFlgAttoRifASistema() {
//		return flgAttoRifASistema;
//	}
//
//	public void setFlgAttoRifASistema(String flgAttoRifASistema) {
//		this.flgAttoRifASistema = flgAttoRifASistema;
//	}
//
//	public String getIdUdAttoDeterminaAContrarre() {
//		return idUdAttoDeterminaAContrarre;
//	}
//
//	public void setIdUdAttoDeterminaAContrarre(String idUdAttoDeterminaAContrarre) {
//		this.idUdAttoDeterminaAContrarre = idUdAttoDeterminaAContrarre;
//	}
//
//	public String getCategoriaRegAttoDeterminaAContrarre() {
//		return categoriaRegAttoDeterminaAContrarre;
//	}
//
//	public void setCategoriaRegAttoDeterminaAContrarre(String categoriaRegAttoDeterminaAContrarre) {
//		this.categoriaRegAttoDeterminaAContrarre = categoriaRegAttoDeterminaAContrarre;
//	}
//
//	public String getSiglaAttoDeterminaAContrarre() {
//		return siglaAttoDeterminaAContrarre;
//	}
//
//	public void setSiglaAttoDeterminaAContrarre(String siglaAttoDeterminaAContrarre) {
//		this.siglaAttoDeterminaAContrarre = siglaAttoDeterminaAContrarre;
//	}
//
//	public String getNumeroAttoDeterminaAContrarre() {
//		return numeroAttoDeterminaAContrarre;
//	}
//
//	public void setNumeroAttoDeterminaAContrarre(String numeroAttoDeterminaAContrarre) {
//		this.numeroAttoDeterminaAContrarre = numeroAttoDeterminaAContrarre;
//	}
//
//	public String getAnnoAttoDeterminaAContrarre() {
//		return annoAttoDeterminaAContrarre;
//	}
//
//	public void setAnnoAttoDeterminaAContrarre(String annoAttoDeterminaAContrarre) {
//		this.annoAttoDeterminaAContrarre = annoAttoDeterminaAContrarre;
//	}

	public String getOggLiquidazione() {
		return oggLiquidazione;
	}

	public void setOggLiquidazione(String oggLiquidazione) {
		this.oggLiquidazione = oggLiquidazione;
	}

	public Date getDataScadenzaLiquidazione() {
		return dataScadenzaLiquidazione;
	}

	public void setDataScadenzaLiquidazione(Date dataScadenzaLiquidazione) {
		this.dataScadenzaLiquidazione = dataScadenzaLiquidazione;
	}

	public String getUrgenzaLiquidazione() {
		return urgenzaLiquidazione;
	}

	public void setUrgenzaLiquidazione(String urgenzaLiquidazione) {
		this.urgenzaLiquidazione = urgenzaLiquidazione;
	}

	public Boolean getFlgLiqXUffCassa() {
		return flgLiqXUffCassa;
	}

	public void setFlgLiqXUffCassa(Boolean flgLiqXUffCassa) {
		this.flgLiqXUffCassa = flgLiqXUffCassa;
	}

	public String getImportoAnticipoCassa() {
		return importoAnticipoCassa;
	}

	public void setImportoAnticipoCassa(String importoAnticipoCassa) {
		this.importoAnticipoCassa = importoAnticipoCassa;
	}

	public Date getDataDecorrenzaContratto() {
		return dataDecorrenzaContratto;
	}

	public void setDataDecorrenzaContratto(Date dataDecorrenzaContratto) {
		this.dataDecorrenzaContratto = dataDecorrenzaContratto;
	}

	public String getAnniDurataContratto() {
		return anniDurataContratto;
	}

	public void setAnniDurataContratto(String anniDurataContratto) {
		this.anniDurataContratto = anniDurataContratto;
	}

	public Boolean getFlgAffidamento() {
		return flgAffidamento;
	}

	public void setFlgAffidamento(Boolean flgAffidamento) {
		this.flgAffidamento = flgAffidamento;
	}

	public Boolean getFlgDeterminaAContrarreTramiteProceduraGara() {
		return flgDeterminaAContrarreTramiteProceduraGara;
	}

	public void setFlgDeterminaAContrarreTramiteProceduraGara(Boolean flgDeterminaAContrarreTramiteProceduraGara) {
		this.flgDeterminaAContrarreTramiteProceduraGara = flgDeterminaAContrarreTramiteProceduraGara;
	}

	public Boolean getFlgDeterminaAggiudicaProceduraGara() {
		return flgDeterminaAggiudicaProceduraGara;
	}

	public void setFlgDeterminaAggiudicaProceduraGara(Boolean flgDeterminaAggiudicaProceduraGara) {
		this.flgDeterminaAggiudicaProceduraGara = flgDeterminaAggiudicaProceduraGara;
	}

	public Boolean getFlgDeterminaRimodulazioneSpesaGaraAggiudicata() {
		return flgDeterminaRimodulazioneSpesaGaraAggiudicata;
	}

	public void setFlgDeterminaRimodulazioneSpesaGaraAggiudicata(Boolean flgDeterminaRimodulazioneSpesaGaraAggiudicata) {
		this.flgDeterminaRimodulazioneSpesaGaraAggiudicata = flgDeterminaRimodulazioneSpesaGaraAggiudicata;
	}

	public Boolean getFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro() {
		return flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro;
	}

	public void setFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro(
			Boolean flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro) {
		this.flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro = flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro;
	}

	public Boolean getFlgDeterminaRiaccertamento() {
		return flgDeterminaRiaccertamento;
	}

	public void setFlgDeterminaRiaccertamento(Boolean flgDeterminaRiaccertamento) {
		this.flgDeterminaRiaccertamento = flgDeterminaRiaccertamento;
	}
	
	public Boolean getFlgDeterminaAccertRadiaz() {
		return flgDeterminaAccertRadiaz;
	}

	public void setFlgDeterminaAccertRadiaz(Boolean flgDeterminaAccertRadiaz) {
		this.flgDeterminaAccertRadiaz = flgDeterminaAccertRadiaz;
	}

	public Boolean getFlgDeterminaVariazBil() {
		return flgDeterminaVariazBil;
	}

	public void setFlgDeterminaVariazBil(Boolean flgDeterminaVariazBil) {
		this.flgDeterminaVariazBil = flgDeterminaVariazBil;
	}

	public Boolean getFlgVantaggiEconomici() {
		return flgVantaggiEconomici;
	}

	public void setFlgVantaggiEconomici(Boolean flgVantaggiEconomici) {
		this.flgVantaggiEconomici = flgVantaggiEconomici;
	}

	public Boolean getFlgDecretoReggio() {
		return flgDecretoReggio;
	}

	public void setFlgDecretoReggio(Boolean flgDecretoReggio) {
		this.flgDecretoReggio = flgDecretoReggio;
	}

	public Boolean getFlgAvvocatura() {
		return flgAvvocatura;
	}

	public void setFlgAvvocatura(Boolean flgAvvocatura) {
		this.flgAvvocatura = flgAvvocatura;
	}

	public Boolean getFlgDeterminaArchiviazione() {
		return flgDeterminaArchiviazione;
	}

	public void setFlgDeterminaArchiviazione(Boolean flgDeterminaArchiviazione) {
		this.flgDeterminaArchiviazione = flgDeterminaArchiviazione;
	}

	public Boolean getFlgContributi() {
		return flgContributi;
	}

	public void setFlgContributi(Boolean flgContributi) {
		this.flgContributi = flgContributi;
	}

	public String getFlgSpesa() {
		return flgSpesa;
	}

	public void setFlgSpesa(String flgSpesa) {
		this.flgSpesa = flgSpesa;
	}
	
	public String getFlgDatiRilevantiGSA() {
		return flgDatiRilevantiGSA;
	}

	public void setFlgDatiRilevantiGSA(String flgDatiRilevantiGSA) {
		this.flgDatiRilevantiGSA = flgDatiRilevantiGSA;
	}

	public String getAnnoContabileCompetenza() {
		return annoContabileCompetenza;
	}

	public void setAnnoContabileCompetenza(String annoContabileCompetenza) {
		this.annoContabileCompetenza = annoContabileCompetenza;
	}

	public Boolean getFlgCorteConti() {
		return flgCorteConti;
	}

	public void setFlgCorteConti(Boolean flgCorteConti) {
		this.flgCorteConti = flgCorteConti;
	}

	public Boolean getFlgLiqContestualeImpegno() {
		return flgLiqContestualeImpegno;
	}

	public void setFlgLiqContestualeImpegno(Boolean flgLiqContestualeImpegno) {
		this.flgLiqContestualeImpegno = flgLiqContestualeImpegno;
	}

	public Boolean getFlgLiqContestualeAltriAspettiRilCont() {
		return flgLiqContestualeAltriAspettiRilCont;
	}

	public void setFlgLiqContestualeAltriAspettiRilCont(Boolean flgLiqContestualeAltriAspettiRilCont) {
		this.flgLiqContestualeAltriAspettiRilCont = flgLiqContestualeAltriAspettiRilCont;
	}
	
	public Boolean getFlgDetConLiquidazione() {
		return flgDetConLiquidazione;
	}

	public void setFlgDetConLiquidazione(Boolean flgDetConLiquidazione) {
		this.flgDetConLiquidazione = flgDetConLiquidazione;
	}

	public Boolean getFlgCompQuadroFinRagDec() {
		return flgCompQuadroFinRagDec;
	}

	public void setFlgCompQuadroFinRagDec(Boolean flgCompQuadroFinRagDec) {
		this.flgCompQuadroFinRagDec = flgCompQuadroFinRagDec;
	}

	public Boolean getFlgNuoviImpAcc() {
		return flgNuoviImpAcc;
	}

	public void setFlgNuoviImpAcc(Boolean flgNuoviImpAcc) {
		this.flgNuoviImpAcc = flgNuoviImpAcc;
	}

	public Boolean getFlgImpSuAnnoCorrente() {
		return flgImpSuAnnoCorrente;
	}

	public void setFlgImpSuAnnoCorrente(Boolean flgImpSuAnnoCorrente) {
		this.flgImpSuAnnoCorrente = flgImpSuAnnoCorrente;
	}

	public Boolean getFlgInsMovARagioneria() {
		return flgInsMovARagioneria;
	}

	public void setFlgInsMovARagioneria(Boolean flgInsMovARagioneria) {
		this.flgInsMovARagioneria = flgInsMovARagioneria;
	}

	public Boolean getFlgPresaVisioneContabilita() {
		return flgPresaVisioneContabilita;
	}

	public void setFlgPresaVisioneContabilita(Boolean flgPresaVisioneContabilita) {
		this.flgPresaVisioneContabilita = flgPresaVisioneContabilita;
	}

	public Boolean getFlgSpesaCorrente() {
		return flgSpesaCorrente;
	}

	public void setFlgSpesaCorrente(Boolean flgSpesaCorrente) {
		this.flgSpesaCorrente = flgSpesaCorrente;
	}

	public Boolean getFlgImpegniCorrenteGiaValidati() {
		return flgImpegniCorrenteGiaValidati;
	}

	public void setFlgImpegniCorrenteGiaValidati(Boolean flgImpegniCorrenteGiaValidati) {
		this.flgImpegniCorrenteGiaValidati = flgImpegniCorrenteGiaValidati;
	}

	public Boolean getFlgSpesaContoCapitale() {
		return flgSpesaContoCapitale;
	}

	public void setFlgSpesaContoCapitale(Boolean flgSpesaContoCapitale) {
		this.flgSpesaContoCapitale = flgSpesaContoCapitale;
	}

	public Boolean getFlgImpegniContoCapitaleGiaRilasciati() {
		return flgImpegniContoCapitaleGiaRilasciati;
	}

	public void setFlgImpegniContoCapitaleGiaRilasciati(Boolean flgImpegniContoCapitaleGiaRilasciati) {
		this.flgImpegniContoCapitaleGiaRilasciati = flgImpegniContoCapitaleGiaRilasciati;
	}

	public Boolean getFlgSoloSubImpSubCrono() {
		return flgSoloSubImpSubCrono;
	}

	public void setFlgSoloSubImpSubCrono(Boolean flgSoloSubImpSubCrono) {
		this.flgSoloSubImpSubCrono = flgSoloSubImpSubCrono;
	}

	public String getTipoAttoInDeliberaPEG() {
		return tipoAttoInDeliberaPEG;
	}

	public void setTipoAttoInDeliberaPEG(String tipoAttoInDeliberaPEG) {
		this.tipoAttoInDeliberaPEG = tipoAttoInDeliberaPEG;
	}

	public String getTipoAffidamento() {
		return tipoAffidamento;
	}

	public void setTipoAffidamento(String tipoAffidamento) {
		this.tipoAffidamento = tipoAffidamento;
	}

	public String getNormRifAffidamento() {
		return normRifAffidamento;
	}

	public void setNormRifAffidamento(String normRifAffidamento) {
		this.normRifAffidamento = normRifAffidamento;
	}

	public String getRespAffidamento() {
		return respAffidamento;
	}

	public void setRespAffidamento(String respAffidamento) {
		this.respAffidamento = respAffidamento;
	}

	public String getMateriaTipoAtto() {
		return materiaTipoAtto;
	}

	public void setMateriaTipoAtto(String materiaTipoAtto) {
		this.materiaTipoAtto = materiaTipoAtto;
	}

	public String getDesMateriaTipoAtto() {
		return desMateriaTipoAtto;
	}

	public void setDesMateriaTipoAtto(String desMateriaTipoAtto) {
		this.desMateriaTipoAtto = desMateriaTipoAtto;
	}
	
	public String getTipoFinanziamentoPNRR() {
		return tipoFinanziamentoPNRR;
	}

	public void setTipoFinanziamentoPNRR(String tipoFinanziamentoPNRR) {
		this.tipoFinanziamentoPNRR = tipoFinanziamentoPNRR;
	}

	public String getFlgSottotipoAtto() {
		return flgSottotipoAtto;
	}

	public void setFlgSottotipoAtto(String flgSottotipoAtto) {
		this.flgSottotipoAtto = flgSottotipoAtto;
	}

	public String getFlgTipoIter() {
		return flgTipoIter;
	}

	public void setFlgTipoIter(String flgTipoIter) {
		this.flgTipoIter = flgTipoIter;
	}

	public Boolean getFlgFondiEuropeiPON() {
		return flgFondiEuropeiPON;
	}

	public void setFlgFondiEuropeiPON(Boolean flgFondiEuropeiPON) {
		this.flgFondiEuropeiPON = flgFondiEuropeiPON;
	}
	
	public String getFlgFondiPNRRRadio() {
		return flgFondiPNRRRadio;
	}

	public void setFlgFondiPNRRRadio(String flgFondiPNRRRadio) {
		this.flgFondiPNRRRadio = flgFondiPNRRRadio;
	}

	public Boolean getFlgFondiPNRR() {
		return flgFondiPNRR;
	}

	public void setFlgFondiPNRR(Boolean flgFondiPNRR) {
		this.flgFondiPNRR = flgFondiPNRR;
	}

	public Boolean getFlgFondiPNRRRigen() {
		return flgFondiPNRRRigen;
	}

	public void setFlgFondiPNRRRigen(Boolean flgFondiPNRRRigen) {
		this.flgFondiPNRRRigen = flgFondiPNRRRigen;
	}

	public Boolean getFlgFondiPRU() {
		return flgFondiPRU;
	}

	public void setFlgFondiPRU(Boolean flgFondiPRU) {
		this.flgFondiPRU = flgFondiPRU;
	}
	
	public Boolean getFlgVistoUtenze() {
		return flgVistoUtenze;
	}

	public void setFlgVistoUtenze(Boolean flgVistoUtenze) {
		this.flgVistoUtenze = flgVistoUtenze;
	}

	public Boolean getFlgVistoCapitolatiSottoSoglia() {
		return flgVistoCapitolatiSottoSoglia;
	}

	public void setFlgVistoCapitolatiSottoSoglia(Boolean flgVistoCapitolatiSottoSoglia) {
		this.flgVistoCapitolatiSottoSoglia = flgVistoCapitolatiSottoSoglia;
	}

	public Boolean getFlgVistoCapitolatiSopraSoglia() {
		return flgVistoCapitolatiSopraSoglia;
	}

	public void setFlgVistoCapitolatiSopraSoglia(Boolean flgVistoCapitolatiSopraSoglia) {
		this.flgVistoCapitolatiSopraSoglia = flgVistoCapitolatiSopraSoglia;
	}

	public Boolean getFlgVistoPar117_2013() {
		return flgVistoPar117_2013;
	}

	public void setFlgVistoPar117_2013(Boolean flgVistoPar117_2013) {
		this.flgVistoPar117_2013 = flgVistoPar117_2013;
	}

	public Boolean getFlgNotificaDaMessi() {
		return flgNotificaDaMessi;
	}

	public void setFlgNotificaDaMessi(Boolean flgNotificaDaMessi) {
		this.flgNotificaDaMessi = flgNotificaDaMessi;
	}
	
	public Boolean getFlgSenzaImpegniCont() {
		return flgSenzaImpegniCont;
	}

	public void setFlgSenzaImpegniCont(Boolean flgSenzaImpegniCont) {
		this.flgSenzaImpegniCont = flgSenzaImpegniCont;
	}

	public String getFlgMEPACONSIP() {
		return flgMEPACONSIP;
	}

	public void setFlgMEPACONSIP(String flgMEPACONSIP) {
		this.flgMEPACONSIP = flgMEPACONSIP;
	}

	public String getFlgServeDUVRI() {
		return flgServeDUVRI;
	}

	public void setFlgServeDUVRI(String flgServeDUVRI) {
		this.flgServeDUVRI = flgServeDUVRI;
	}

	public String getImportoOneriSicurezza() {
		return importoOneriSicurezza;
	}

	public void setImportoOneriSicurezza(String importoOneriSicurezza) {
		this.importoOneriSicurezza = importoOneriSicurezza;
	}

	public String getFlgLLPP() {
		return flgLLPP;
	}

	public void setFlgLLPP(String flgLLPP) {
		this.flgLLPP = flgLLPP;
	}

	public String getAnnoProgettoLLPP() {
		return annoProgettoLLPP;
	}

	public void setAnnoProgettoLLPP(String annoProgettoLLPP) {
		this.annoProgettoLLPP = annoProgettoLLPP;
	}

	public String getNumProgettoLLPP() {
		return numProgettoLLPP;
	}

	public void setNumProgettoLLPP(String numProgettoLLPP) {
		this.numProgettoLLPP = numProgettoLLPP;
	}

	public String getFlgBeniServizi() {
		return flgBeniServizi;
	}

	public void setFlgBeniServizi(String flgBeniServizi) {
		this.flgBeniServizi = flgBeniServizi;
	}

	public String getFlgProgrammazioneAcquisti() {
		return flgProgrammazioneAcquisti;
	}

	public void setFlgProgrammazioneAcquisti(String flgProgrammazioneAcquisti) {
		this.flgProgrammazioneAcquisti = flgProgrammazioneAcquisti;
	}

	public String getAnnoProgettoBeniServizi() {
		return annoProgettoBeniServizi;
	}

	public void setAnnoProgettoBeniServizi(String annoProgettoBeniServizi) {
		this.annoProgettoBeniServizi = annoProgettoBeniServizi;
	}

	public String getNumProgettoBeniServizi() {
		return numProgettoBeniServizi;
	}

	public void setNumProgettoBeniServizi(String numProgettoBeniServizi) {
		this.numProgettoBeniServizi = numProgettoBeniServizi;
	}
	
	public String getFlgPrivacy() {
		return flgPrivacy;
	}

	public void setFlgPrivacy(String flgPrivacy) {
		this.flgPrivacy = flgPrivacy;
	}

	public Boolean getFlgDatiProtettiTipo1() {
		return flgDatiProtettiTipo1;
	}

	public void setFlgDatiProtettiTipo1(Boolean flgDatiProtettiTipo1) {
		this.flgDatiProtettiTipo1 = flgDatiProtettiTipo1;
	}

	public Boolean getFlgDatiProtettiTipo2() {
		return flgDatiProtettiTipo2;
	}

	public void setFlgDatiProtettiTipo2(Boolean flgDatiProtettiTipo2) {
		this.flgDatiProtettiTipo2 = flgDatiProtettiTipo2;
	}

	public Boolean getFlgDatiProtettiTipo3() {
		return flgDatiProtettiTipo3;
	}

	public void setFlgDatiProtettiTipo3(Boolean flgDatiProtettiTipo3) {
		this.flgDatiProtettiTipo3 = flgDatiProtettiTipo3;
	}

	public Boolean getFlgDatiProtettiTipo4() {
		return flgDatiProtettiTipo4;
	}

	public void setFlgDatiProtettiTipo4(Boolean flgDatiProtettiTipo4) {
		this.flgDatiProtettiTipo4 = flgDatiProtettiTipo4;
	}
	
	public String getNumGara() {
		return numGara;
	}

	public void setNumGara(String numGara) {
		this.numGara = numGara;
	}

	public String getFlgControlloLegittimita() {
		return flgControlloLegittimita;
	}

	public void setFlgControlloLegittimita(String flgControlloLegittimita) {
		this.flgControlloLegittimita = flgControlloLegittimita;
	}

	public String getMotivazioniEsclControlloLegittimita() {
		return motivazioniEsclControlloLegittimita;
	}

	public void setMotivazioniEsclControlloLegittimita(String motivazioniEsclControlloLegittimita) {
		this.motivazioniEsclControlloLegittimita = motivazioniEsclControlloLegittimita;
	}

	public List<DestVantaggioBean> getListaDestVantaggio() {
		return listaDestVantaggio;
	}

	public void setListaDestVantaggio(List<DestVantaggioBean> listaDestVantaggio) {
		this.listaDestVantaggio = listaDestVantaggio;
	}

	public Boolean getFlgAdottanteUnicoRespPEG() {
		return flgAdottanteUnicoRespPEG;
	}

	public void setFlgAdottanteUnicoRespPEG(Boolean flgAdottanteUnicoRespPEG) {
		this.flgAdottanteUnicoRespPEG = flgAdottanteUnicoRespPEG;
	}

	public List<ResponsabilePEGBean> getListaResponsabiliPEG() {
		return listaResponsabiliPEG;
	}

	public void setListaResponsabiliPEG(List<ResponsabilePEGBean> listaResponsabiliPEG) {
		this.listaResponsabiliPEG = listaResponsabiliPEG;
	}

	public String getUfficioDefinizioneSpesa() {
		return ufficioDefinizioneSpesa;
	}

	public void setUfficioDefinizioneSpesa(String ufficioDefinizioneSpesa) {
		this.ufficioDefinizioneSpesa = ufficioDefinizioneSpesa;
	}

	public String getCodUfficioDefinizioneSpesa() {
		return codUfficioDefinizioneSpesa;
	}

	public void setCodUfficioDefinizioneSpesa(String codUfficioDefinizioneSpesa) {
		this.codUfficioDefinizioneSpesa = codUfficioDefinizioneSpesa;
	}

	public String getDesUfficioDefinizioneSpesa() {
		return desUfficioDefinizioneSpesa;
	}

	public void setDesUfficioDefinizioneSpesa(String desUfficioDefinizioneSpesa) {
		this.desUfficioDefinizioneSpesa = desUfficioDefinizioneSpesa;
	}

	public List<SelezionaUOBean> getListaUfficioDefinizioneSpesa() {
		return listaUfficioDefinizioneSpesa;
	}

	public void setListaUfficioDefinizioneSpesa(List<SelezionaUOBean> listaUfficioDefinizioneSpesa) {
		this.listaUfficioDefinizioneSpesa = listaUfficioDefinizioneSpesa;
	}

	public String getOpzAssCompSpesa() {
		return opzAssCompSpesa;
	}

	public void setOpzAssCompSpesa(String opzAssCompSpesa) {
		this.opzAssCompSpesa = opzAssCompSpesa;
	}

	public Boolean getFlgRichVerificaDiBilancioCorrente() {
		return flgRichVerificaDiBilancioCorrente;
	}

	public void setFlgRichVerificaDiBilancioCorrente(Boolean flgRichVerificaDiBilancioCorrente) {
		this.flgRichVerificaDiBilancioCorrente = flgRichVerificaDiBilancioCorrente;
	}

	public Boolean getFlgRichVerificaDiBilancioContoCapitale() {
		return flgRichVerificaDiBilancioContoCapitale;
	}

	public void setFlgRichVerificaDiBilancioContoCapitale(Boolean flgRichVerificaDiBilancioContoCapitale) {
		this.flgRichVerificaDiBilancioContoCapitale = flgRichVerificaDiBilancioContoCapitale;
	}

	public Boolean getFlgRichVerificaDiContabilita() {
		return flgRichVerificaDiContabilita;
	}

	public void setFlgRichVerificaDiContabilita(Boolean flgRichVerificaDiContabilita) {
		this.flgRichVerificaDiContabilita = flgRichVerificaDiContabilita;
	}

	public Boolean getFlgConVerificaContabilita() {
		return flgConVerificaContabilita;
	}

	public void setFlgConVerificaContabilita(Boolean flgConVerificaContabilita) {
		this.flgConVerificaContabilita = flgConVerificaContabilita;
	}

	public Boolean getFlgRichiediParereRevisoriContabili() {
		return flgRichiediParereRevisoriContabili;
	}

	public void setFlgRichiediParereRevisoriContabili(Boolean flgRichiediParereRevisoriContabili) {
		this.flgRichiediParereRevisoriContabili = flgRichiediParereRevisoriContabili;
	}

	public String getFlgOpCommerciale() {
		return flgOpCommerciale;
	}

	public void setFlgOpCommerciale(String flgOpCommerciale) {
		this.flgOpCommerciale = flgOpCommerciale;
	}

	public Boolean getFlgEscludiCIG() {
		return flgEscludiCIG;
	}

	public void setFlgEscludiCIG(Boolean flgEscludiCIG) {
		this.flgEscludiCIG = flgEscludiCIG;
	}

	public Boolean getFlgCIGDaAcquisire() {
		return flgCIGDaAcquisire;
	}

	public void setFlgCIGDaAcquisire(Boolean flgCIGDaAcquisire) {
		this.flgCIGDaAcquisire = flgCIGDaAcquisire;
	}

	public String getMotivoEsclusioneCIG() {
		return motivoEsclusioneCIG;
	}

	public void setMotivoEsclusioneCIG(String motivoEsclusioneCIG) {
		this.motivoEsclusioneCIG = motivoEsclusioneCIG;
	}

	public String getCodiceCIGPadre() {
		return codiceCIGPadre;
	}

	public void setCodiceCIGPadre(String codiceCIGPadre) {
		this.codiceCIGPadre = codiceCIGPadre;
	}

	public List<CIGCUPBean> getListaCIG() {
		return listaCIG;
	}

	public void setListaCIG(List<CIGCUPBean> listaCIG) {
		this.listaCIG = listaCIG;
	}

	public List<CUIBean> getListaCUI() {
		return listaCUI;
	}

	public void setListaCUI(List<CUIBean> listaCUI) {
		this.listaCUI = listaCUI;
	}

	public String getCategoriaRischio() {
		return categoriaRischio;
	}

	public void setCategoriaRischio(String categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}

	public List<ClassificazioneFascicoloBean> getListaClassFasc() {
		return listaClassFasc;
	}

	public void setListaClassFasc(List<ClassificazioneFascicoloBean> listaClassFasc) {
		this.listaClassFasc = listaClassFasc;
	}

	public String getIdPropostaAMC() {
		return idPropostaAMC;
	}

	public void setIdPropostaAMC(String idPropostaAMC) {
		this.idPropostaAMC = idPropostaAMC;
	}

	public Boolean getFlgDettRevocaAtto() {
		return flgDettRevocaAtto;
	}

	public void setFlgDettRevocaAtto(Boolean flgDettRevocaAtto) {
		this.flgDettRevocaAtto = flgDettRevocaAtto;
	}

	public Boolean getFlgDatiSensibili() {
		return flgDatiSensibili;
	}

	public void setFlgDatiSensibili(Boolean flgDatiSensibili) {
		this.flgDatiSensibili = flgDatiSensibili;
	}

	public List<SimpleKeyValueBean> getListaVociPEGNoVerifDisp() {
		return listaVociPEGNoVerifDisp;
	}

	public void setListaVociPEGNoVerifDisp(List<SimpleKeyValueBean> listaVociPEGNoVerifDisp) {
		this.listaVociPEGNoVerifDisp = listaVociPEGNoVerifDisp;
	}

	public Boolean getFlgAttivaUploadPdfAtto() {
		return flgAttivaUploadPdfAtto;
	}

	public void setFlgAttivaUploadPdfAtto(Boolean flgAttivaUploadPdfAtto) {
		this.flgAttivaUploadPdfAtto = flgAttivaUploadPdfAtto;
	}

	public Boolean getFlgAttivaUploadPdfAttoOmissis() {
		return flgAttivaUploadPdfAttoOmissis;
	}

	public void setFlgAttivaUploadPdfAttoOmissis(Boolean flgAttivaUploadPdfAttoOmissis) {
		this.flgAttivaUploadPdfAttoOmissis = flgAttivaUploadPdfAttoOmissis;
	}

	public DocumentBean getFilePdfAtto() {
		return filePdfAtto;
	}

	public void setFilePdfAtto(DocumentBean filePdfAtto) {
		this.filePdfAtto = filePdfAtto;
	}

	public DocumentBean getFilePdfAttoOmissis() {
		return filePdfAttoOmissis;
	}

	public void setFilePdfAttoOmissis(DocumentBean filePdfAttoOmissis) {
		this.filePdfAttoOmissis = filePdfAttoOmissis;
	}

	public List<SimpleValueBean> getListaRiferimentiNormativi() {
		return listaRiferimentiNormativi;
	}

	public void setListaRiferimentiNormativi(List<SimpleValueBean> listaRiferimentiNormativi) {
		this.listaRiferimentiNormativi = listaRiferimentiNormativi;
	}

	public String getAttiPresupposti() {
		return attiPresupposti;
	}

	public void setAttiPresupposti(String attiPresupposti) {
		this.attiPresupposti = attiPresupposti;
	}
	
	public String getAttiPresuppostiFile() {
		return attiPresuppostiFile;
	}
	
	public void setAttiPresuppostiFile(String attiPresuppostiFile) {
		this.attiPresuppostiFile = attiPresuppostiFile;
	}

	public String getMotivazioni() {
		return motivazioni;
	}

	public void setMotivazioni(String motivazioni) {
		this.motivazioni = motivazioni;
	}
	
	public String getMotivazioniFile() {
		return motivazioniFile;
	}

	public void setMotivazioniFile(String motivazioniFile) {
		this.motivazioniFile = motivazioniFile;
	}

	public String getPremessa() {
		return premessa;
	}

	public void setPremessa(String premessa) {
		this.premessa = premessa;
	}
	
	public String getPremessaFile() {
		return premessaFile;
	}

	public void setPremessaFile(String premessaFile) {
		this.premessaFile = premessaFile;
	}

	public String getRifNormativiLiberi() {
		return rifNormativiLiberi;
	}

	public void setRifNormativiLiberi(String rifNormativiLiberi) {
		this.rifNormativiLiberi = rifNormativiLiberi;
	}
	
	public String getRifNormativiLiberiFile() {
		return rifNormativiLiberiFile;
	}

	public void setRifNormativiLiberiFile(String rifNormativiLiberiFile) {
		this.rifNormativiLiberiFile = rifNormativiLiberiFile;
	}

	public String getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(String dispositivo) {
		this.dispositivo = dispositivo;
	}
	
	public String getDispositivoFile() {
		return dispositivoFile;
	}
	
	public void setDispositivoFile(String dispositivoFile) {
		this.dispositivoFile = dispositivoFile;
	}

	public String getLoghiAggiuntiviDispositivo() {
		return loghiAggiuntiviDispositivo;
	}

	public void setLoghiAggiuntiviDispositivo(String loghiAggiuntiviDispositivo) {
		this.loghiAggiuntiviDispositivo = loghiAggiuntiviDispositivo;
	}
	
	public String getPremessa2() {
		return premessa2;
	}

	public void setPremessa2(String premessa2) {
		this.premessa2 = premessa2;
	}
	
	public String getPremessa2File() {
		return premessa2File;
	}
	
	public void setPremessa2File(String premessa2File) {
		this.premessa2File = premessa2File;
	}

	public String getDispositivo2() {
		return dispositivo2;
	}

	public void setDispositivo2(String dispositivo2) {
		this.dispositivo2 = dispositivo2;
	}
	
	public String getDispositivo2File() {
		return dispositivo2File;
	}
	
	public void setDispositivo2File(String dispositivo2File) {
		this.dispositivo2File = dispositivo2File;
	}

	public Boolean getFlgPubblicaAllegatiSeparati() {
		return flgPubblicaAllegatiSeparati;
	}

	public void setFlgPubblicaAllegatiSeparati(Boolean flgPubblicaAllegatiSeparati) {
		this.flgPubblicaAllegatiSeparati = flgPubblicaAllegatiSeparati;
	}

	public List<AllegatoProtocolloBean> getListaAllegati() {
		return listaAllegati;
	}

	public void setListaAllegati(List<AllegatoProtocolloBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}

	public List<AllegatoProtocolloBean> getListaDocFasc() {
		return listaDocFasc;
	}

	public void setListaDocFasc(List<AllegatoProtocolloBean> listaDocFasc) {
		this.listaDocFasc = listaDocFasc;
	}

	public Boolean getFlgEscludiPremesseFisseDaTestoAtto() {
		return flgEscludiPremesseFisseDaTestoAtto;
	}

	public void setFlgEscludiPremesseFisseDaTestoAtto(Boolean flgEscludiPremesseFisseDaTestoAtto) {
		this.flgEscludiPremesseFisseDaTestoAtto = flgEscludiPremesseFisseDaTestoAtto;
	}

	public Boolean getFlgEscludiOggettoDaTestoAtto() {
		return flgEscludiOggettoDaTestoAtto;
	}

	public void setFlgEscludiOggettoDaTestoAtto(Boolean flgEscludiOggettoDaTestoAtto) {
		this.flgEscludiOggettoDaTestoAtto = flgEscludiOggettoDaTestoAtto;
	}

	public String getScrittaTraPremessaEDispositivoAtto() {
		return scrittaTraPremessaEDispositivoAtto;
	}

	public void setScrittaTraPremessaEDispositivoAtto(String scrittaTraPremessaEDispositivoAtto) {
		this.scrittaTraPremessaEDispositivoAtto = scrittaTraPremessaEDispositivoAtto;
	}

	public String getNumRighePreScrittaTraPremessaEDispositivoAtto() {
		return numRighePreScrittaTraPremessaEDispositivoAtto;
	}

	public void setNumRighePreScrittaTraPremessaEDispositivoAtto(String numRighePreScrittaTraPremessaEDispositivoAtto) {
		this.numRighePreScrittaTraPremessaEDispositivoAtto = numRighePreScrittaTraPremessaEDispositivoAtto;
	}

	public String getNumRighePostScrittaTraPremessaEDispositivoAtto() {
		return numRighePostScrittaTraPremessaEDispositivoAtto;
	}

	public void setNumRighePostScrittaTraPremessaEDispositivoAtto(String numRighePostScrittaTraPremessaEDispositivoAtto) {
		this.numRighePostScrittaTraPremessaEDispositivoAtto = numRighePostScrittaTraPremessaEDispositivoAtto;
	}

	public Boolean getFlgAcquisitiVistiTornaVerificaUoProp() {
		return flgAcquisitiVistiTornaVerificaUoProp;
	}

	public void setFlgAcquisitiVistiTornaVerificaUoProp(Boolean flgAcquisitiVistiTornaVerificaUoProp) {
		this.flgAcquisitiVistiTornaVerificaUoProp = flgAcquisitiVistiTornaVerificaUoProp;
	}

	public Boolean getFlgVisibilitaPubblPostAdozione() {
		return flgVisibilitaPubblPostAdozione;
	}

	public void setFlgVisibilitaPubblPostAdozione(Boolean flgVisibilitaPubblPostAdozione) {
		this.flgVisibilitaPubblPostAdozione = flgVisibilitaPubblPostAdozione;
	}

	public String getFlgPubblAlbo() {
		return flgPubblAlbo;
	}

	public void setFlgPubblAlbo(String flgPubblAlbo) {
		this.flgPubblAlbo = flgPubblAlbo;
	}
	
	public String getTipoDecorrenzaPubblAlbo() {
		return tipoDecorrenzaPubblAlbo;
	}

	public void setTipoDecorrenzaPubblAlbo(String tipoDecorrenzaPubblAlbo) {
		this.tipoDecorrenzaPubblAlbo = tipoDecorrenzaPubblAlbo;
	}

	public Date getDataPubblAlboDal() {
		return dataPubblAlboDal;
	}

	public void setDataPubblAlboDal(Date dataPubblAlboDal) {
		this.dataPubblAlboDal = dataPubblAlboDal;
	}
	
	public String getNumGiorniPubblAlbo() {
		return numGiorniPubblAlbo;
	}

	public void setNumGiorniPubblAlbo(String numGiorniPubblAlbo) {
		this.numGiorniPubblAlbo = numGiorniPubblAlbo;
	}

	public Date getDataPubblAlboAl() {
		return dataPubblAlboAl;
	}

	public void setDataPubblAlboAl(Date dataPubblAlboAl) {
		this.dataPubblAlboAl = dataPubblAlboAl;
	}

	public Boolean getFlgUrgentePubblAlbo() {
		return flgUrgentePubblAlbo;
	}

	public void setFlgUrgentePubblAlbo(Boolean flgUrgentePubblAlbo) {
		this.flgUrgentePubblAlbo = flgUrgentePubblAlbo;
	}

	public Date getDataPubblAlboEntro() {
		return dataPubblAlboEntro;
	}

	public void setDataPubblAlboEntro(Date dataPubblAlboEntro) {
		this.dataPubblAlboEntro = dataPubblAlboEntro;
	}

	public Date getDataRipubblAlboDal() {
		return dataRipubblAlboDal;
	}

	public void setDataRipubblAlboDal(Date dataRipubblAlboDal) {
		this.dataRipubblAlboDal = dataRipubblAlboDal;
	}

	public String getNumGiorniRipubblAlbo() {
		return numGiorniRipubblAlbo;
	}

	public void setNumGiorniRipubblAlbo(String numGiorniRipubblAlbo) {
		this.numGiorniRipubblAlbo = numGiorniRipubblAlbo;
	}

	public Date getDataRipubblAlboAl() {
		return dataRipubblAlboAl;
	}

	public void setDataRipubblAlboAl(Date dataRipubblAlboAl) {
		this.dataRipubblAlboAl = dataRipubblAlboAl;
	}

	public String getFlgPubblAmmTrasp() {
		return flgPubblAmmTrasp;
	}

	public void setFlgPubblAmmTrasp(String flgPubblAmmTrasp) {
		this.flgPubblAmmTrasp = flgPubblAmmTrasp;
	}
	
	public String getAnnoTerminePubblAmmTrasp() {
		return annoTerminePubblAmmTrasp;
	}

	public void setAnnoTerminePubblAmmTrasp(String annoTerminePubblAmmTrasp) {
		this.annoTerminePubblAmmTrasp = annoTerminePubblAmmTrasp;
	}

	public String getSezionePubblAmmTrasp() {
		return sezionePubblAmmTrasp;
	}

	public void setSezionePubblAmmTrasp(String sezionePubblAmmTrasp) {
		this.sezionePubblAmmTrasp = sezionePubblAmmTrasp;
	}

	public String getSottoSezionePubblAmmTrasp() {
		return sottoSezionePubblAmmTrasp;
	}

	public void setSottoSezionePubblAmmTrasp(String sottoSezionePubblAmmTrasp) {
		this.sottoSezionePubblAmmTrasp = sottoSezionePubblAmmTrasp;
	}

	public String getFlgPubblBUR() {
		return flgPubblBUR;
	}

	public void setFlgPubblBUR(String flgPubblBUR) {
		this.flgPubblBUR = flgPubblBUR;
	}

	public String getAnnoTerminePubblBUR() {
		return annoTerminePubblBUR;
	}

	public void setAnnoTerminePubblBUR(String annoTerminePubblBUR) {
		this.annoTerminePubblBUR = annoTerminePubblBUR;
	}

	public String getTipoDecorrenzaPubblBUR() {
		return tipoDecorrenzaPubblBUR;
	}

	public void setTipoDecorrenzaPubblBUR(String tipoDecorrenzaPubblBUR) {
		this.tipoDecorrenzaPubblBUR = tipoDecorrenzaPubblBUR;
	}

	public Date getDataPubblBURDal() {
		return dataPubblBURDal;
	}

	public void setDataPubblBURDal(Date dataPubblBURDal) {
		this.dataPubblBURDal = dataPubblBURDal;
	}

	public Boolean getFlgUrgentePubblBUR() {
		return flgUrgentePubblBUR;
	}

	public void setFlgUrgentePubblBUR(Boolean flgUrgentePubblBUR) {
		this.flgUrgentePubblBUR = flgUrgentePubblBUR;
	}

	public Date getDataPubblBUREntro() {
		return dataPubblBUREntro;
	}

	public void setDataPubblBUREntro(Date dataPubblBUREntro) {
		this.dataPubblBUREntro = dataPubblBUREntro;
	}
	
	public String getFlgPubblNotiziario() {
		return flgPubblNotiziario;
	}

	public void setFlgPubblNotiziario(String flgPubblNotiziario) {
		this.flgPubblNotiziario = flgPubblNotiziario;
	}

	public Date getDataEsecutivitaDal() {
		return dataEsecutivitaDal;
	}

	public void setDataEsecutivitaDal(Date dataEsecutivitaDal) {
		this.dataEsecutivitaDal = dataEsecutivitaDal;
	}

	public Boolean getFlgImmediatamenteEseguibile() {
		return flgImmediatamenteEseguibile;
	}

	public void setFlgImmediatamenteEseguibile(Boolean flgImmediatamenteEseguibile) {
		this.flgImmediatamenteEseguibile = flgImmediatamenteEseguibile;
	}

	public String getMotiviImmediatamenteEseguibile() {
		return motiviImmediatamenteEseguibile;
	}

	public void setMotiviImmediatamenteEseguibile(String motiviImmediatamenteEseguibile) {
		this.motiviImmediatamenteEseguibile = motiviImmediatamenteEseguibile;
	}
	
	public String getMotiviImmediatamenteEseguibileFile() {
		return motiviImmediatamenteEseguibileFile;
	}

	public void setMotiviImmediatamenteEseguibileFile(String motiviImmediatamenteEseguibileFile) {
		this.motiviImmediatamenteEseguibileFile = motiviImmediatamenteEseguibileFile;
	}

	public String getListaDestNotificaAtto() {
		return listaDestNotificaAtto;
	}

	public void setListaDestNotificaAtto(String listaDestNotificaAtto) {
		this.listaDestNotificaAtto = listaDestNotificaAtto;
	}
	
	public Boolean getFlgMessiNotificatori() {
		return flgMessiNotificatori;
	}

	public void setFlgMessiNotificatori(Boolean flgMessiNotificatori) {
		this.flgMessiNotificatori = flgMessiNotificatori;
	}

	public List<DestinatariNotificaMessiXmlBean> getListaDestinatariNotificaMessi() {
		return listaDestinatariNotificaMessi;
	}

	public void setListaDestinatariNotificaMessi(List<DestinatariNotificaMessiXmlBean> listaDestinatariNotificaMessi) {
		this.listaDestinatariNotificaMessi = listaDestinatariNotificaMessi;
	}
	
	public Boolean getFlgNotificaPEC() {
		return flgNotificaPEC;
	}

	public void setFlgNotificaPEC(Boolean flgNotificaPEC) {
		this.flgNotificaPEC = flgNotificaPEC;
	}

	public List<DestinatariNotificaPECXmlBean> getListaDestinatariNotificaPEC() {
		return listaDestinatariNotificaPEC;
	}

	public void setListaDestinatariNotificaPEC(List<DestinatariNotificaPECXmlBean> listaDestinatariNotificaPEC) {
		this.listaDestinatariNotificaPEC = listaDestinatariNotificaPEC;
	}

	public Boolean getFlgVisibPubblicaPostAdozione() {
		return flgVisibPubblicaPostAdozione;
	}

	public void setFlgVisibPubblicaPostAdozione(Boolean flgVisibPubblicaPostAdozione) {
		this.flgVisibPubblicaPostAdozione = flgVisibPubblicaPostAdozione;
	}

	public Boolean getFlgDisattivaAutoRequestDatiContabiliSIBCorrente() {
		return flgDisattivaAutoRequestDatiContabiliSIBCorrente;
	}

	public void setFlgDisattivaAutoRequestDatiContabiliSIBCorrente(
			Boolean flgDisattivaAutoRequestDatiContabiliSIBCorrente) {
		this.flgDisattivaAutoRequestDatiContabiliSIBCorrente = flgDisattivaAutoRequestDatiContabiliSIBCorrente;
	}

	public String getPrenotazioneSpesaSIBDiCorrente() {
		return prenotazioneSpesaSIBDiCorrente;
	}

	public void setPrenotazioneSpesaSIBDiCorrente(String prenotazioneSpesaSIBDiCorrente) {
		this.prenotazioneSpesaSIBDiCorrente = prenotazioneSpesaSIBDiCorrente;
	}

	public String getModalitaInvioDatiSpesaARagioneriaCorrente() {
		return modalitaInvioDatiSpesaARagioneriaCorrente;
	}

	public void setModalitaInvioDatiSpesaARagioneriaCorrente(String modalitaInvioDatiSpesaARagioneriaCorrente) {
		this.modalitaInvioDatiSpesaARagioneriaCorrente = modalitaInvioDatiSpesaARagioneriaCorrente;
	}

	public List<DatiContabiliBean> getListaDatiContabiliSIBCorrente() {
		return listaDatiContabiliSIBCorrente;
	}

	public void setListaDatiContabiliSIBCorrente(List<DatiContabiliBean> listaDatiContabiliSIBCorrente) {
		this.listaDatiContabiliSIBCorrente = listaDatiContabiliSIBCorrente;
	}

	public String getErrorMessageDatiContabiliSIBCorrente() {
		return errorMessageDatiContabiliSIBCorrente;
	}

	public void setErrorMessageDatiContabiliSIBCorrente(String errorMessageDatiContabiliSIBCorrente) {
		this.errorMessageDatiContabiliSIBCorrente = errorMessageDatiContabiliSIBCorrente;
	}

	public List<DatiContabiliBean> getListaInvioDatiSpesaCorrente() {
		return listaInvioDatiSpesaCorrente;
	}

	public void setListaInvioDatiSpesaCorrente(List<DatiContabiliBean> listaInvioDatiSpesaCorrente) {
		this.listaInvioDatiSpesaCorrente = listaInvioDatiSpesaCorrente;
	}

	public DocumentBean getFileXlsCorrente() {
		return fileXlsCorrente;
	}

	public void setFileXlsCorrente(DocumentBean fileXlsCorrente) {
		this.fileXlsCorrente = fileXlsCorrente;
	}

	public String getNomeFileTracciatoXlsCorrente() {
		return nomeFileTracciatoXlsCorrente;
	}

	public void setNomeFileTracciatoXlsCorrente(String nomeFileTracciatoXlsCorrente) {
		this.nomeFileTracciatoXlsCorrente = nomeFileTracciatoXlsCorrente;
	}

	public String getUriFileTracciatoXlsCorrente() {
		return uriFileTracciatoXlsCorrente;
	}

	public void setUriFileTracciatoXlsCorrente(String uriFileTracciatoXlsCorrente) {
		this.uriFileTracciatoXlsCorrente = uriFileTracciatoXlsCorrente;
	}

	public List<AllegatoProtocolloBean> getListaAllegatiCorrente() {
		return listaAllegatiCorrente;
	}

	public void setListaAllegatiCorrente(List<AllegatoProtocolloBean> listaAllegatiCorrente) {
		this.listaAllegatiCorrente = listaAllegatiCorrente;
	}

	public String getNoteCorrente() {
		return noteCorrente;
	}

	public void setNoteCorrente(String noteCorrente) {
		this.noteCorrente = noteCorrente;
	}	
	
	public String getNoteCorrenteFile() {
		return noteCorrenteFile;
	}

	public void setNoteCorrenteFile(String noteCorrenteFile) {
		this.noteCorrenteFile = noteCorrenteFile;
	}

	public Boolean getFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale() {
		return flgDisattivaAutoRequestDatiContabiliSIBContoCapitale;
	}

	public void setFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale(
			Boolean flgDisattivaAutoRequestDatiContabiliSIBContoCapitale) {
		this.flgDisattivaAutoRequestDatiContabiliSIBContoCapitale = flgDisattivaAutoRequestDatiContabiliSIBContoCapitale;
	}

	public String getModalitaInvioDatiSpesaARagioneriaContoCapitale() {
		return modalitaInvioDatiSpesaARagioneriaContoCapitale;
	}

	public void setModalitaInvioDatiSpesaARagioneriaContoCapitale(String modalitaInvioDatiSpesaARagioneriaContoCapitale) {
		this.modalitaInvioDatiSpesaARagioneriaContoCapitale = modalitaInvioDatiSpesaARagioneriaContoCapitale;
	}

	public List<DatiContabiliBean> getListaDatiContabiliSIBContoCapitale() {
		return listaDatiContabiliSIBContoCapitale;
	}

	public void setListaDatiContabiliSIBContoCapitale(List<DatiContabiliBean> listaDatiContabiliSIBContoCapitale) {
		this.listaDatiContabiliSIBContoCapitale = listaDatiContabiliSIBContoCapitale;
	}

	public String getErrorMessageDatiContabiliSIBContoCapitale() {
		return errorMessageDatiContabiliSIBContoCapitale;
	}

	public void setErrorMessageDatiContabiliSIBContoCapitale(String errorMessageDatiContabiliSIBContoCapitale) {
		this.errorMessageDatiContabiliSIBContoCapitale = errorMessageDatiContabiliSIBContoCapitale;
	}

	public List<DatiContabiliBean> getListaInvioDatiSpesaContoCapitale() {
		return listaInvioDatiSpesaContoCapitale;
	}

	public void setListaInvioDatiSpesaContoCapitale(List<DatiContabiliBean> listaInvioDatiSpesaContoCapitale) {
		this.listaInvioDatiSpesaContoCapitale = listaInvioDatiSpesaContoCapitale;
	}

	public DocumentBean getFileXlsContoCapitale() {
		return fileXlsContoCapitale;
	}

	public void setFileXlsContoCapitale(DocumentBean fileXlsContoCapitale) {
		this.fileXlsContoCapitale = fileXlsContoCapitale;
	}

	public String getNomeFileTracciatoXlsContoCapitale() {
		return nomeFileTracciatoXlsContoCapitale;
	}

	public void setNomeFileTracciatoXlsContoCapitale(String nomeFileTracciatoXlsContoCapitale) {
		this.nomeFileTracciatoXlsContoCapitale = nomeFileTracciatoXlsContoCapitale;
	}

	public String getUriFileTracciatoXlsContoCapitale() {
		return uriFileTracciatoXlsContoCapitale;
	}

	public void setUriFileTracciatoXlsContoCapitale(String uriFileTracciatoXlsContoCapitale) {
		this.uriFileTracciatoXlsContoCapitale = uriFileTracciatoXlsContoCapitale;
	}

	public List<AllegatoProtocolloBean> getListaAllegatiContoCapitale() {
		return listaAllegatiContoCapitale;
	}

	public void setListaAllegatiContoCapitale(List<AllegatoProtocolloBean> listaAllegatiContoCapitale) {
		this.listaAllegatiContoCapitale = listaAllegatiContoCapitale;
	}

	public String getNoteContoCapitale() {
		return noteContoCapitale;
	}

	public void setNoteContoCapitale(String noteContoCapitale) {
		this.noteContoCapitale = noteContoCapitale;
	}
	
	public String getNoteContoCapitaleFile() {
		return noteContoCapitaleFile;
	}

	public void setNoteContoCapitaleFile(String noteContoCapitaleFile) {
		this.noteContoCapitaleFile = noteContoCapitaleFile;
	}

	public List<DatiSpesaAnnualiXDetPersonaleXmlBean> getListaDatiSpesaAnnualiXDetPersonale() {
		return listaDatiSpesaAnnualiXDetPersonale;
	}

	public void setListaDatiSpesaAnnualiXDetPersonale(
			List<DatiSpesaAnnualiXDetPersonaleXmlBean> listaDatiSpesaAnnualiXDetPersonale) {
		this.listaDatiSpesaAnnualiXDetPersonale = listaDatiSpesaAnnualiXDetPersonale;
	}

	public String getCapitoloDatiSpesaAnnuaXDetPers() {
		return capitoloDatiSpesaAnnuaXDetPers;
	}

	public void setCapitoloDatiSpesaAnnuaXDetPers(String capitoloDatiSpesaAnnuaXDetPers) {
		this.capitoloDatiSpesaAnnuaXDetPers = capitoloDatiSpesaAnnuaXDetPers;
	}

	public String getArticoloDatiSpesaAnnuaXDetPers() {
		return articoloDatiSpesaAnnuaXDetPers;
	}

	public void setArticoloDatiSpesaAnnuaXDetPers(String articoloDatiSpesaAnnuaXDetPers) {
		this.articoloDatiSpesaAnnuaXDetPers = articoloDatiSpesaAnnuaXDetPers;
	}

	public String getNumeroDatiSpesaAnnuaXDetPers() {
		return numeroDatiSpesaAnnuaXDetPers;
	}

	public void setNumeroDatiSpesaAnnuaXDetPers(String numeroDatiSpesaAnnuaXDetPers) {
		this.numeroDatiSpesaAnnuaXDetPers = numeroDatiSpesaAnnuaXDetPers;
	}

	public String getImportoDatiSpesaAnnuaXDetPers() {
		return importoDatiSpesaAnnuaXDetPers;
	}

	public void setImportoDatiSpesaAnnuaXDetPers(String importoDatiSpesaAnnuaXDetPers) {
		this.importoDatiSpesaAnnuaXDetPers = importoDatiSpesaAnnuaXDetPers;
	}

	public String getEventoSIB() {
		return eventoSIB;
	}

	public void setEventoSIB(String eventoSIB) {
		this.eventoSIB = eventoSIB;
	}

	public String getEsitoEventoSIB() {
		return esitoEventoSIB;
	}

	public void setEsitoEventoSIB(String esitoEventoSIB) {
		this.esitoEventoSIB = esitoEventoSIB;
	}

	public Date getDataEventoSIB() {
		return dataEventoSIB;
	}

	public void setDataEventoSIB(Date dataEventoSIB) {
		this.dataEventoSIB = dataEventoSIB;
	}

	public String getErrMsgEventoSIB() {
		return errMsgEventoSIB;
	}

	public void setErrMsgEventoSIB(String errMsgEventoSIB) {
		this.errMsgEventoSIB = errMsgEventoSIB;
	}

	public String getIdUoDirAdottanteSIB() {
		return idUoDirAdottanteSIB;
	}

	public void setIdUoDirAdottanteSIB(String idUoDirAdottanteSIB) {
		this.idUoDirAdottanteSIB = idUoDirAdottanteSIB;
	}

	public String getCodUoDirAdottanteSIB() {
		return codUoDirAdottanteSIB;
	}

	public void setCodUoDirAdottanteSIB(String codUoDirAdottanteSIB) {
		this.codUoDirAdottanteSIB = codUoDirAdottanteSIB;
	}

	public String getDesUoDirAdottanteSIB() {
		return desUoDirAdottanteSIB;
	}

	public void setDesUoDirAdottanteSIB(String desUoDirAdottanteSIB) {
		this.desUoDirAdottanteSIB = desUoDirAdottanteSIB;
	}

	public String getEventoContabilia() {
		return eventoContabilia;
	}

	public void setEventoContabilia(String eventoContabilia) {
		this.eventoContabilia = eventoContabilia;
	}

	public String getEsitoEventoContabilia() {
		return esitoEventoContabilia;
	}

	public void setEsitoEventoContabilia(String esitoEventoContabilia) {
		this.esitoEventoContabilia = esitoEventoContabilia;
	}

	public Date getDataEventoContabilia() {
		return dataEventoContabilia;
	}

	public void setDataEventoContabilia(Date dataEventoContabilia) {
		this.dataEventoContabilia = dataEventoContabilia;
	}

	public String getErrMsgEventoContabilia() {
		return errMsgEventoContabilia;
	}

	public void setErrMsgEventoContabilia(String errMsgEventoContabilia) {
		this.errMsgEventoContabilia = errMsgEventoContabilia;
	}

	public String getDirigenteResponsabileContabilia() {
		return dirigenteResponsabileContabilia;
	}

	public void setDirigenteResponsabileContabilia(String dirigenteResponsabileContabilia) {
		this.dirigenteResponsabileContabilia = dirigenteResponsabileContabilia;
	}

	public String getCentroResponsabilitaContabilia() {
		return centroResponsabilitaContabilia;
	}

	public void setCentroResponsabilitaContabilia(String centroResponsabilitaContabilia) {
		this.centroResponsabilitaContabilia = centroResponsabilitaContabilia;
	}

	public String getCentroCostoContabilia() {
		return centroCostoContabilia;
	}

	public void setCentroCostoContabilia(String centroCostoContabilia) {
		this.centroCostoContabilia = centroCostoContabilia;
	}

	public List<MovimentiContabiliaXmlBean> getListaMovimentiContabilia() {
		return listaMovimentiContabilia;
	}

	public void setListaMovimentiContabilia(List<MovimentiContabiliaXmlBean> listaMovimentiContabilia) {
		this.listaMovimentiContabilia = listaMovimentiContabilia;
	}

	public String getErrorMessageMovimentiContabilia() {
		return errorMessageMovimentiContabilia;
	}

	public void setErrorMessageMovimentiContabilia(String errorMessageMovimentiContabilia) {
		this.errorMessageMovimentiContabilia = errorMessageMovimentiContabilia;
	}

	public String getEventoSICRA() {
		return eventoSICRA;
	}

	public void setEventoSICRA(String eventoSICRA) {
		this.eventoSICRA = eventoSICRA;
	}

	public String getEsitoEventoSICRA() {
		return esitoEventoSICRA;
	}

	public void setEsitoEventoSICRA(String esitoEventoSICRA) {
		this.esitoEventoSICRA = esitoEventoSICRA;
	}

	public Date getDataEventoSICRA() {
		return dataEventoSICRA;
	}

	public void setDataEventoSICRA(Date dataEventoSICRA) {
		this.dataEventoSICRA = dataEventoSICRA;
	}

	public String getErrMsgEventoSICRA() {
		return errMsgEventoSICRA;
	}

	public void setErrMsgEventoSICRA(String errMsgEventoSICRA) {
		this.errMsgEventoSICRA = errMsgEventoSICRA;
	}

	public List<MovimentiContabiliSICRABean> getListaInvioMovimentiContabiliSICRA() {
		return listaInvioMovimentiContabiliSICRA;
	}

	public void setListaInvioMovimentiContabiliSICRA(List<MovimentiContabiliSICRABean> listaInvioMovimentiContabiliSICRA) {
		this.listaInvioMovimentiContabiliSICRA = listaInvioMovimentiContabiliSICRA;
	}

	public List<MovimentiContabiliSICRABean> getListaMovimentiSICRAToDelete() {
		return listaMovimentiSICRAToDelete;
	}

	public void setListaMovimentiSICRAToDelete(List<MovimentiContabiliSICRABean> listaMovimentiSICRAToDelete) {
		this.listaMovimentiSICRAToDelete = listaMovimentiSICRAToDelete;
	}

	public List<MovimentiContabiliSICRABean> getListaMovimentiSICRAToInsert() {
		return listaMovimentiSICRAToInsert;
	}

	public void setListaMovimentiSICRAToInsert(List<MovimentiContabiliSICRABean> listaMovimentiSICRAToInsert) {
		this.listaMovimentiSICRAToInsert = listaMovimentiSICRAToInsert;
	}

	public String getEsitoSetMovimentiAttoSICRA() {
		return esitoSetMovimentiAttoSICRA;
	}

	public void setEsitoSetMovimentiAttoSICRA(String esitoSetMovimentiAttoSICRA) {
		this.esitoSetMovimentiAttoSICRA = esitoSetMovimentiAttoSICRA;
	}

	public String getMessaggioWarning() {
		return messaggioWarning;
	}

	public void setMessaggioWarning(String messaggioWarning) {
		this.messaggioWarning = messaggioWarning;
	}

	public String getCodXSalvataggioConWarning() {
		return codXSalvataggioConWarning;
	}

	public void setCodXSalvataggioConWarning(String codXSalvataggioConWarning) {
		this.codXSalvataggioConWarning = codXSalvataggioConWarning;
	}
	
	public String getNomeFileAttoLiqTecnica() {
		return nomeFileAttoLiqTecnica;
	}

	public void setNomeFileAttoLiqTecnica(String nomeFileAttoLiqTecnica) {
		this.nomeFileAttoLiqTecnica = nomeFileAttoLiqTecnica;
	}

	public MimeTypeFirmaBean getInfoFileAttoLiqTecnica() {
		return infoFileAttoLiqTecnica;
	}

	public void setInfoFileAttoLiqTecnica(MimeTypeFirmaBean infoFileAttoLiqTecnica) {
		this.infoFileAttoLiqTecnica = infoFileAttoLiqTecnica;
	}

	public String getUriFileAttoLiqTecnica() {
		return uriFileAttoLiqTecnica;
	}

	public void setUriFileAttoLiqTecnica(String uriFileAttoLiqTecnica) {
		this.uriFileAttoLiqTecnica = uriFileAttoLiqTecnica;
	}
	
	public String getNomeFileAttoLiqContabile() {
		return nomeFileAttoLiqContabile;
	}

	public void setNomeFileAttoLiqContabile(String nomeFileAttoLiqContabile) {
		this.nomeFileAttoLiqContabile = nomeFileAttoLiqContabile;
	}

	public MimeTypeFirmaBean getInfoFileAttoLiqContabile() {
		return infoFileAttoLiqContabile;
	}

	public void setInfoFileAttoLiqContabile(MimeTypeFirmaBean infoFileAttoLiqContabile) {
		this.infoFileAttoLiqContabile = infoFileAttoLiqContabile;
	}

	public String getUriFileAttoLiqContabile() {
		return uriFileAttoLiqContabile;
	}

	public void setUriFileAttoLiqContabile(String uriFileAttoLiqContabile) {
		this.uriFileAttoLiqContabile = uriFileAttoLiqContabile;
	}
	
	public List<MovimentiGSAXmlBean> getListaMovimentiGSA() {
		return listaMovimentiGSA;
	}

	public void setListaMovimentiGSA(List<MovimentiGSAXmlBean> listaMovimentiGSA) {
		this.listaMovimentiGSA = listaMovimentiGSA;
	}

	public String getEventoCWOL() {
		return eventoCWOL;
	}

	public void setEventoCWOL(String eventoCWOL) {
		this.eventoCWOL = eventoCWOL;
	}

	public Boolean getFlgRecuperaMovimentiContabDefinitivi() {
		return flgRecuperaMovimentiContabDefinitivi;
	}

	public void setFlgRecuperaMovimentiContabDefinitivi(Boolean flgRecuperaMovimentiContabDefinitivi) {
		this.flgRecuperaMovimentiContabDefinitivi = flgRecuperaMovimentiContabDefinitivi;
	}

	public String getCodAOOXSelNodoACTA() {
		return codAOOXSelNodoACTA;
	}

	public void setCodAOOXSelNodoACTA(String codAOOXSelNodoACTA) {
		this.codAOOXSelNodoACTA = codAOOXSelNodoACTA;
	}

	public String getCodStrutturaXSelNodoACTA() {
		return codStrutturaXSelNodoACTA;
	}

	public void setCodStrutturaXSelNodoACTA(String codStrutturaXSelNodoACTA) {
		this.codStrutturaXSelNodoACTA = codStrutturaXSelNodoACTA;
	}

	public Boolean getFlgAggregatoACTA() {
		return flgAggregatoACTA;
	}

	public void setFlgAggregatoACTA(Boolean flgAggregatoACTA) {
		this.flgAggregatoACTA = flgAggregatoACTA;
	}

	public Boolean getFlgSmistamentoACTA() {
		return flgSmistamentoACTA;
	}

	public void setFlgSmistamentoACTA(Boolean flgSmistamentoACTA) {
		this.flgSmistamentoACTA = flgSmistamentoACTA;
	}

	public Boolean getFlgIndiceClassificazioneACTA() {
		return flgIndiceClassificazioneACTA;
	}

	public void setFlgIndiceClassificazioneACTA(Boolean flgIndiceClassificazioneACTA) {
		this.flgIndiceClassificazioneACTA = flgIndiceClassificazioneACTA;
	}

	public Boolean getFlgFascicoloACTA() {
		return flgFascicoloACTA;
	}

	public void setFlgFascicoloACTA(Boolean flgFascicoloACTA) {
		this.flgFascicoloACTA = flgFascicoloACTA;
	}

	public String getCodIndiceClassificazioneACTA() {
		return codIndiceClassificazioneACTA;
	}

	public void setCodIndiceClassificazioneACTA(String codIndiceClassificazioneACTA) {
		this.codIndiceClassificazioneACTA = codIndiceClassificazioneACTA;
	}

	public Boolean getFlgPresenzaClassificazioneACTA() {
		return flgPresenzaClassificazioneACTA;
	}

	public void setFlgPresenzaClassificazioneACTA(Boolean flgPresenzaClassificazioneACTA) {
		this.flgPresenzaClassificazioneACTA = flgPresenzaClassificazioneACTA;
	}

	public String getCodVoceTitolarioACTA() {
		return codVoceTitolarioACTA;
	}

	public void setCodVoceTitolarioACTA(String codVoceTitolarioACTA) {
		this.codVoceTitolarioACTA = codVoceTitolarioACTA;
	}

	public String getCodFascicoloACTA() {
		return codFascicoloACTA;
	}

	public void setCodFascicoloACTA(String codFascicoloACTA) {
		this.codFascicoloACTA = codFascicoloACTA;
	}

	public String getSuffissoCodFascicoloACTA() {
		return suffissoCodFascicoloACTA;
	}

	public void setSuffissoCodFascicoloACTA(String suffissoCodFascicoloACTA) {
		this.suffissoCodFascicoloACTA = suffissoCodFascicoloACTA;
	}

	public String getCodSottofascicoloACTA() {
		return codSottofascicoloACTA;
	}

	public void setCodSottofascicoloACTA(String codSottofascicoloACTA) {
		this.codSottofascicoloACTA = codSottofascicoloACTA;
	}

	public String getIdFascicoloACTA() {
		return idFascicoloACTA;
	}

	public void setIdFascicoloACTA(String idFascicoloACTA) {
		this.idFascicoloACTA = idFascicoloACTA;
	}

	public String getIdNodoSmistamentoACTA() {
		return idNodoSmistamentoACTA;
	}

	public void setIdNodoSmistamentoACTA(String idNodoSmistamentoACTA) {
		this.idNodoSmistamentoACTA = idNodoSmistamentoACTA;
	}

	public String getDesNodoSmistamentoACTA() {
		return desNodoSmistamentoACTA;
	}

	public void setDesNodoSmistamentoACTA(String desNodoSmistamentoACTA) {
		this.desNodoSmistamentoACTA = desNodoSmistamentoACTA;
	}
	
	public List<PeriziaBean> getListaOpereADSP() {
		return listaOpereADSP;
	}

	public void setListaOpereADSP(List<PeriziaBean> listaOpereADSP) {
		this.listaOpereADSP = listaOpereADSP;
	}

	public List<DatiContabiliADSPXmlBean> getListaDatiContabiliADSP() {
		return listaDatiContabiliADSP;
	}

	public List<DatiContabiliADSPXmlBean> getListaDatiContabiliDefinitiviADSP() {
		return listaDatiContabiliDefinitiviADSP;
	}

	public void setListaDatiContabiliDefinitiviADSP(List<DatiContabiliADSPXmlBean> listaDatiContabiliDefinitiviADSP) {
		this.listaDatiContabiliDefinitiviADSP = listaDatiContabiliDefinitiviADSP;
	}

	public void setListaDatiContabiliADSP(List<DatiContabiliADSPXmlBean> listaDatiContabiliADSP) {
		this.listaDatiContabiliADSP = listaDatiContabiliADSP;
	}

	public List<DatiContabiliAVBBean> getListaImpegniAVB() {
		return listaImpegniAVB;
	}

	public void setListaImpegniAVB(List<DatiContabiliAVBBean> listaImpegniAVB) {
		this.listaImpegniAVB = listaImpegniAVB;
	}

	public List<DatiContabiliAVBBean> getListaAccertamentiAVB() {
		return listaAccertamentiAVB;
	}

	public void setListaAccertamentiAVB(List<DatiContabiliAVBBean> listaAccertamentiAVB) {
		this.listaAccertamentiAVB = listaAccertamentiAVB;
	}

	public List<DatiContabiliAVBBean> getListaLiquidazioniAVB() {
		return listaLiquidazioniAVB;
	}

	public void setListaLiquidazioniAVB(List<DatiContabiliAVBBean> listaLiquidazioniAVB) {
		this.listaLiquidazioniAVB = listaLiquidazioniAVB;
	}

	public String getFlgErogVantaggiEconomici() {
		return flgErogVantaggiEconomici;
	}

	public void setFlgErogVantaggiEconomici(String flgErogVantaggiEconomici) {
		this.flgErogVantaggiEconomici = flgErogVantaggiEconomici;
	}

	public String getTipoTrasparenzaVantEcon() {
		return tipoTrasparenzaVantEcon;
	}

	public void setTipoTrasparenzaVantEcon(String tipoTrasparenzaVantEcon) {
		this.tipoTrasparenzaVantEcon = tipoTrasparenzaVantEcon;
	}

	public Boolean getFlgBeneficiariObbligXTipoTraspVantEcon() {
		return flgBeneficiariObbligXTipoTraspVantEcon;
	}

	public void setFlgBeneficiariObbligXTipoTraspVantEcon(Boolean flgBeneficiariObbligXTipoTraspVantEcon) {
		this.flgBeneficiariObbligXTipoTraspVantEcon = flgBeneficiariObbligXTipoTraspVantEcon;
	}

	public String getSottotipoTrasparenzaVantEcon() {
		return sottotipoTrasparenzaVantEcon;
	}

	public void setSottotipoTrasparenzaVantEcon(String sottotipoTrasparenzaVantEcon) {
		this.sottotipoTrasparenzaVantEcon = sottotipoTrasparenzaVantEcon;
	}

	public Boolean getFlgBeneficiariObbligXSottotipoTraspVantEcon() {
		return flgBeneficiariObbligXSottotipoTraspVantEcon;
	}

	public void setFlgBeneficiariObbligXSottotipoTraspVantEcon(Boolean flgBeneficiariObbligXSottotipoTraspVantEcon) {
		this.flgBeneficiariObbligXSottotipoTraspVantEcon = flgBeneficiariObbligXSottotipoTraspVantEcon;
	}

	public String getFlgInterventi() {
		return flgInterventi;
	}

	public void setFlgInterventi(String flgInterventi) {
		this.flgInterventi = flgInterventi;
	}

	public String getTipoInterventi() {
		return tipoInterventi;
	}

	public void setTipoInterventi(String tipoInterventi) {
		this.tipoInterventi = tipoInterventi;
	}

	public Boolean getFlgBeneficiariObbligXTipoInterventi() {
		return flgBeneficiariObbligXTipoInterventi;
	}

	public void setFlgBeneficiariObbligXTipoInterventi(Boolean flgBeneficiariObbligXTipoInterventi) {
		this.flgBeneficiariObbligXTipoInterventi = flgBeneficiariObbligXTipoInterventi;
	}

	public String getSottotipoInterventi() {
		return sottotipoInterventi;
	}

	public void setSottotipoInterventi(String sottotipoInterventi) {
		this.sottotipoInterventi = sottotipoInterventi;
	}

	public Boolean getFlgBeneficiariObbligXSottotipoInterventi() {
		return flgBeneficiariObbligXSottotipoInterventi;
	}

	public void setFlgBeneficiariObbligXSottotipoInterventi(Boolean flgBeneficiariObbligXSottotipoInterventi) {
		this.flgBeneficiariObbligXSottotipoInterventi = flgBeneficiariObbligXSottotipoInterventi;
	}

	public String getFlgAltriProvvedimenti() {
		return flgAltriProvvedimenti;
	}

	public void setFlgAltriProvvedimenti(String flgAltriProvvedimenti) {
		this.flgAltriProvvedimenti = flgAltriProvvedimenti;
	}

	public String getTipoAltriProvvedimenti() {
		return tipoAltriProvvedimenti;
	}

	public void setTipoAltriProvvedimenti(String tipoAltriProvvedimenti) {
		this.tipoAltriProvvedimenti = tipoAltriProvvedimenti;
	}

	public Boolean getFlgBeneficiariObbligXTipoAltriProvvedimenti() {
		return flgBeneficiariObbligXTipoAltriProvvedimenti;
	}

	public void setFlgBeneficiariObbligXTipoAltriProvvedimenti(Boolean flgBeneficiariObbligXTipoAltriProvvedimenti) {
		this.flgBeneficiariObbligXTipoAltriProvvedimenti = flgBeneficiariObbligXTipoAltriProvvedimenti;
	}

	public String getSottotipoAltriProvvedimenti() {
		return sottotipoAltriProvvedimenti;
	}

	public void setSottotipoAltriProvvedimenti(String sottotipoAltriProvvedimenti) {
		this.sottotipoAltriProvvedimenti = sottotipoAltriProvvedimenti;
	}

	public Boolean getFlgBeneficiariObbligXSottotipoAltriProvvedimenti() {
		return flgBeneficiariObbligXSottotipoAltriProvvedimenti;
	}

	public void setFlgBeneficiariObbligXSottotipoAltriProvvedimenti(
			Boolean flgBeneficiariObbligXSottotipoAltriProvvedimenti) {
		this.flgBeneficiariObbligXSottotipoAltriProvvedimenti = flgBeneficiariObbligXSottotipoAltriProvvedimenti;
	}

	public String getNormaTrasparenzaVantEcon() {
		return normaTrasparenzaVantEcon;
	}

	public void setNormaTrasparenzaVantEcon(String normaTrasparenzaVantEcon) {
		this.normaTrasparenzaVantEcon = normaTrasparenzaVantEcon;
	}

	public String getModalitaIndividuazioneBeneficiario() {
		return modalitaIndividuazioneBeneficiario;
	}

	public void setModalitaIndividuazioneBeneficiario(String modalitaIndividuazioneBeneficiario) {
		this.modalitaIndividuazioneBeneficiario = modalitaIndividuazioneBeneficiario;
	}

	public String getEstremiDocumentiPrincipaliFascicolo() {
		return estremiDocumentiPrincipaliFascicolo;
	}

	public void setEstremiDocumentiPrincipaliFascicolo(String estremiDocumentiPrincipaliFascicolo) {
		this.estremiDocumentiPrincipaliFascicolo = estremiDocumentiPrincipaliFascicolo;
	}

	public String getAvvisiPerLaCompilazione() {
		return avvisiPerLaCompilazione;
	}

	public void setAvvisiPerLaCompilazione(String avvisiPerLaCompilazione) {
		this.avvisiPerLaCompilazione = avvisiPerLaCompilazione;
	}

	public String getUfficioCompetenteTabTrasp() {
		return ufficioCompetenteTabTrasp;
	}

	public void setUfficioCompetenteTabTrasp(String ufficioCompetenteTabTrasp) {
		this.ufficioCompetenteTabTrasp = ufficioCompetenteTabTrasp;
	}

	public String getResponsabileProcedimentoTabTrasp() {
		return responsabileProcedimentoTabTrasp;
	}

	public void setResponsabileProcedimentoTabTrasp(String responsabileProcedimentoTabTrasp) {
		this.responsabileProcedimentoTabTrasp = responsabileProcedimentoTabTrasp;
	}

	public Date getDataAvvioProcedimento() {
		return dataAvvioProcedimento;
	}

	public void setDataAvvioProcedimento(Date dataAvvioProcedimento) {
		this.dataAvvioProcedimento = dataAvvioProcedimento;
	}

	public String getOggettoProvvedimento() {
		return oggettoProvvedimento;
	}

	public void setOggettoProvvedimento(String oggettoProvvedimento) {
		this.oggettoProvvedimento = oggettoProvvedimento;
	}

	public String getDurataRapportoGiuridico() {
		return durataRapportoGiuridico;
	}

	public void setDurataRapportoGiuridico(String durataRapportoGiuridico) {
		this.durataRapportoGiuridico = durataRapportoGiuridico;
	}

	public String getNumeroMesiDurataRapporto() {
		return numeroMesiDurataRapporto;
	}

	public void setNumeroMesiDurataRapporto(String numeroMesiDurataRapporto) {
		this.numeroMesiDurataRapporto = numeroMesiDurataRapporto;
	}

	public Date getDalDurataRapporto() {
		return dalDurataRapporto;
	}

	public void setDalDurataRapporto(Date dalDurataRapporto) {
		this.dalDurataRapporto = dalDurataRapporto;
	}

	public Date getAlDurataRapporto() {
		return alDurataRapporto;
	}

	public void setAlDurataRapporto(Date alDurataRapporto) {
		this.alDurataRapporto = alDurataRapporto;
	}

	public String getListaAllegatiObbligatoriTrasparenza() {
		return listaAllegatiObbligatoriTrasparenza;
	}

	public void setListaAllegatiObbligatoriTrasparenza(String listaAllegatiObbligatoriTrasparenza) {
		this.listaAllegatiObbligatoriTrasparenza = listaAllegatiObbligatoriTrasparenza;
	}

	public String getListaIdAllegatiObbligatoriTrasparenza() {
		return listaIdAllegatiObbligatoriTrasparenza;
	}

	public void setListaIdAllegatiObbligatoriTrasparenza(String listaIdAllegatiObbligatoriTrasparenza) {
		this.listaIdAllegatiObbligatoriTrasparenza = listaIdAllegatiObbligatoriTrasparenza;
	}

	public String getListaAllegatiNonObbligatoriTrasparenza() {
		return listaAllegatiNonObbligatoriTrasparenza;
	}

	public void setListaAllegatiNonObbligatoriTrasparenza(String listaAllegatiNonObbligatoriTrasparenza) {
		this.listaAllegatiNonObbligatoriTrasparenza = listaAllegatiNonObbligatoriTrasparenza;
	}

	public String getListaIdAllegatiNonObbligatoriTrasparenza() {
		return listaIdAllegatiNonObbligatoriTrasparenza;
	}

	public void setListaIdAllegatiNonObbligatoriTrasparenza(String listaIdAllegatiNonObbligatoriTrasparenza) {
		this.listaIdAllegatiNonObbligatoriTrasparenza = listaIdAllegatiNonObbligatoriTrasparenza;
	}

	public List<DestVantaggioBean> getListaBeneficiariTrasparenza() {
		return listaBeneficiariTrasparenza;
	}

	public void setListaBeneficiariTrasparenza(List<DestVantaggioBean> listaBeneficiariTrasparenza) {
		this.listaBeneficiariTrasparenza = listaBeneficiariTrasparenza;
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

	public Map<String, String> getColonneListe() {
		return colonneListe;
	}

	public void setColonneListe(Map<String, String> colonneListe) {
		this.colonneListe = colonneListe;
	}

	public String getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}

	public String getIdModello() {
		return idModello;
	}

	public void setIdModello(String idModello) {
		this.idModello = idModello;
	}

	public String getNomeModello() {
		return nomeModello;
	}

	public void setNomeModello(String nomeModello) {
		this.nomeModello = nomeModello;
	}

	public String getDisplayFilenameModello() {
		return displayFilenameModello;
	}

	public void setDisplayFilenameModello(String displayFilenameModello) {
		this.displayFilenameModello = displayFilenameModello;
	}

	public String getUriModello() {
		return uriModello;
	}

	public void setUriModello(String uriModello) {
		this.uriModello = uriModello;
	}

	public String getTipoModello() {
		return tipoModello;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}

	public String getIdModCopertina() {
		return idModCopertina;
	}

	public void setIdModCopertina(String idModCopertina) {
		this.idModCopertina = idModCopertina;
	}

	public String getNomeModCopertina() {
		return nomeModCopertina;
	}

	public void setNomeModCopertina(String nomeModCopertina) {
		this.nomeModCopertina = nomeModCopertina;
	}

	public String getUriModCopertina() {
		return uriModCopertina;
	}

	public void setUriModCopertina(String uriModCopertina) {
		this.uriModCopertina = uriModCopertina;
	}

	public String getTipoModCopertina() {
		return tipoModCopertina;
	}

	public void setTipoModCopertina(String tipoModCopertina) {
		this.tipoModCopertina = tipoModCopertina;
	}

	public String getIdModCopertinaFinale() {
		return idModCopertinaFinale;
	}

	public void setIdModCopertinaFinale(String idModCopertinaFinale) {
		this.idModCopertinaFinale = idModCopertinaFinale;
	}

	public String getNomeModCopertinaFinale() {
		return nomeModCopertinaFinale;
	}

	public void setNomeModCopertinaFinale(String nomeModCopertinaFinale) {
		this.nomeModCopertinaFinale = nomeModCopertinaFinale;
	}

	public String getUriModCopertinaFinale() {
		return uriModCopertinaFinale;
	}

	public void setUriModCopertinaFinale(String uriModCopertinaFinale) {
		this.uriModCopertinaFinale = uriModCopertinaFinale;
	}

	public String getTipoModCopertinaFinale() {
		return tipoModCopertinaFinale;
	}

	public void setTipoModCopertinaFinale(String tipoModCopertinaFinale) {
		this.tipoModCopertinaFinale = tipoModCopertinaFinale;
	}

	public String getIdModAllegatiParteIntSeparati() {
		return idModAllegatiParteIntSeparati;
	}

	public void setIdModAllegatiParteIntSeparati(String idModAllegatiParteIntSeparati) {
		this.idModAllegatiParteIntSeparati = idModAllegatiParteIntSeparati;
	}

	public String getNomeModAllegatiParteIntSeparati() {
		return nomeModAllegatiParteIntSeparati;
	}

	public void setNomeModAllegatiParteIntSeparati(String nomeModAllegatiParteIntSeparati) {
		this.nomeModAllegatiParteIntSeparati = nomeModAllegatiParteIntSeparati;
	}

	public String getUriModAllegatiParteIntSeparati() {
		return uriModAllegatiParteIntSeparati;
	}

	public void setUriModAllegatiParteIntSeparati(String uriModAllegatiParteIntSeparati) {
		this.uriModAllegatiParteIntSeparati = uriModAllegatiParteIntSeparati;
	}

	public String getTipoModAllegatiParteIntSeparati() {
		return tipoModAllegatiParteIntSeparati;
	}

	public void setTipoModAllegatiParteIntSeparati(String tipoModAllegatiParteIntSeparati) {
		this.tipoModAllegatiParteIntSeparati = tipoModAllegatiParteIntSeparati;
	}

	public String getIdModAllegatiParteIntSeparatiXPubbl() {
		return idModAllegatiParteIntSeparatiXPubbl;
	}

	public void setIdModAllegatiParteIntSeparatiXPubbl(String idModAllegatiParteIntSeparatiXPubbl) {
		this.idModAllegatiParteIntSeparatiXPubbl = idModAllegatiParteIntSeparatiXPubbl;
	}

	public String getNomeModAllegatiParteIntSeparatiXPubbl() {
		return nomeModAllegatiParteIntSeparatiXPubbl;
	}

	public void setNomeModAllegatiParteIntSeparatiXPubbl(String nomeModAllegatiParteIntSeparatiXPubbl) {
		this.nomeModAllegatiParteIntSeparatiXPubbl = nomeModAllegatiParteIntSeparatiXPubbl;
	}

	public String getUriModAllegatiParteIntSeparatiXPubbl() {
		return uriModAllegatiParteIntSeparatiXPubbl;
	}

	public void setUriModAllegatiParteIntSeparatiXPubbl(String uriModAllegatiParteIntSeparatiXPubbl) {
		this.uriModAllegatiParteIntSeparatiXPubbl = uriModAllegatiParteIntSeparatiXPubbl;
	}

	public String getTipoModAllegatiParteIntSeparatiXPubbl() {
		return tipoModAllegatiParteIntSeparatiXPubbl;
	}

	public void setTipoModAllegatiParteIntSeparatiXPubbl(String tipoModAllegatiParteIntSeparatiXPubbl) {
		this.tipoModAllegatiParteIntSeparatiXPubbl = tipoModAllegatiParteIntSeparatiXPubbl;
	}

	public Boolean getFlgAppendiceDaUnire() {
		return flgAppendiceDaUnire;
	}

	public void setFlgAppendiceDaUnire(Boolean flgAppendiceDaUnire) {
		this.flgAppendiceDaUnire = flgAppendiceDaUnire;
	}

	public String getIdModAppendice() {
		return idModAppendice;
	}

	public void setIdModAppendice(String idModAppendice) {
		this.idModAppendice = idModAppendice;
	}

	public String getNomeModAppendice() {
		return nomeModAppendice;
	}

	public void setNomeModAppendice(String nomeModAppendice) {
		this.nomeModAppendice = nomeModAppendice;
	}

	public String getUriModAppendice() {
		return uriModAppendice;
	}

	public void setUriModAppendice(String uriModAppendice) {
		this.uriModAppendice = uriModAppendice;
	}

	public String getTipoModAppendice() {
		return tipoModAppendice;
	}

	public void setTipoModAppendice(String tipoModAppendice) {
		this.tipoModAppendice = tipoModAppendice;
	}

	public String getUriAppendice() {
		return uriAppendice;
	}

	public void setUriAppendice(String uriAppendice) {
		this.uriAppendice = uriAppendice;
	}

	public String getDisplayFilenameModAppendice() {
		return displayFilenameModAppendice;
	}

	public void setDisplayFilenameModAppendice(String displayFilenameModAppendice) {
		this.displayFilenameModAppendice = displayFilenameModAppendice;
	}

	public Boolean getFlgMostraDatiSensibili() {
		return flgMostraDatiSensibili;
	}

	public void setFlgMostraDatiSensibili(Boolean flgMostraDatiSensibili) {
		this.flgMostraDatiSensibili = flgMostraDatiSensibili;
	}

	public Boolean getFlgMostraOmissisBarrati() {
		return flgMostraOmissisBarrati;
	}

	public void setFlgMostraOmissisBarrati(Boolean flgMostraOmissisBarrati) {
		this.flgMostraOmissisBarrati = flgMostraOmissisBarrati;
	}

	public Boolean getFlgAnteprima() {
		return flgAnteprima;
	}

	public void setFlgAnteprima(Boolean flgAnteprima) {
		this.flgAnteprima = flgAnteprima;
	}

	public Boolean getFlgVersioneFinale() {
		return flgVersioneFinale;
	}

	public void setFlgVersioneFinale(Boolean flgVersioneFinale) {
		this.flgVersioneFinale = flgVersioneFinale;
	}
	
	public List<String> getElencoCampiConGestioneOmissisDaIgnorare() {
		return elencoCampiConGestioneOmissisDaIgnorare;
	}

	public void setElencoCampiConGestioneOmissisDaIgnorare(List<String> elencoCampiConGestioneOmissisDaIgnorare) {
		this.elencoCampiConGestioneOmissisDaIgnorare = elencoCampiConGestioneOmissisDaIgnorare;
	}

	public String getDesDirezioneProponente() {
		return desDirezioneProponente;
	}

	public void setDesDirezioneProponente(String desDirezioneProponente) {
		this.desDirezioneProponente = desDirezioneProponente;
	}

	public AllegatoProtocolloBean getAllegatoVistoContabile() {
		return allegatoVistoContabile;
	}

	public void setAllegatoVistoContabile(AllegatoProtocolloBean allegatoVistoContabile) {
		this.allegatoVistoContabile = allegatoVistoContabile;
	}

	public List<EmendamentoBean> getListaEmendamenti() {
		return listaEmendamenti;
	}

	public void setListaEmendamenti(List<EmendamentoBean> listaEmendamenti) {
		this.listaEmendamenti = listaEmendamenti;
	}
	
	public Boolean getListaEmendamentiBloccoRiordinoAut() {
		return listaEmendamentiBloccoRiordinoAut;
	}
	
	public void setListaEmendamentiBloccoRiordinoAut(Boolean listaEmendamentiBloccoRiordinoAut) {
		this.listaEmendamentiBloccoRiordinoAut = listaEmendamentiBloccoRiordinoAut;
	}

	public ParametriTipoAttoBean getParametriTipoAtto() {
		return parametriTipoAtto;
	}

	public void setParametriTipoAtto(ParametriTipoAttoBean parametriTipoAtto) {
		this.parametriTipoAtto = parametriTipoAtto;
	}

	public String getIdUoAlboReggio() {
		return idUoAlboReggio;
	}

	public void setIdUoAlboReggio(String idUoAlboReggio) {
		this.idUoAlboReggio = idUoAlboReggio;
	}
	
	public String getIdTipoDocAlbo() {
		return idTipoDocAlbo;
	}

	public void setIdTipoDocAlbo(String idTipoDocAlbo) {
		this.idTipoDocAlbo = idTipoDocAlbo;
	}
	
	public String getUnioneFileNomeFile() {
		return unioneFileNomeFile;
	}

	public String getUnioneFileNomeFileOmissis() {
		return unioneFileNomeFileOmissis;
	}

	public void setUnioneFileNomeFile(String unioneFileNomeFile) {
		this.unioneFileNomeFile = unioneFileNomeFile;
	}

	public void setUnioneFileNomeFileOmissis(String unioneFileNomeFileOmissis) {
		this.unioneFileNomeFileOmissis = unioneFileNomeFileOmissis;
	}

	public ImpostazioniUnioneFileBean getImpostazioniUnioneFile() {
		return impostazioniUnioneFile;
	}

	public void setImpostazioniUnioneFile(ImpostazioniUnioneFileBean impostazioniUnioneFile) {
		this.impostazioniUnioneFile = impostazioniUnioneFile;
	}

	public Boolean getFlgAllegatiParteIntUniti() {
		return flgAllegatiParteIntUniti;
	}

	public void setFlgAllegatiParteIntUniti(Boolean flgAllegatiParteIntUniti) {
		this.flgAllegatiParteIntUniti = flgAllegatiParteIntUniti;
	}

	public List<AllegatoParteIntSeparatoBean> getListaAllegatiParteIntSeparati() {
		return listaAllegatiParteIntSeparati;
	}

	public void setListaAllegatiParteIntSeparati(List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparati) {
		this.listaAllegatiParteIntSeparati = listaAllegatiParteIntSeparati;
	}

	public Boolean getFlgAllegatiParteIntUnitiXPubbl() {
		return flgAllegatiParteIntUnitiXPubbl;
	}

	public void setFlgAllegatiParteIntUnitiXPubbl(Boolean flgAllegatiParteIntUnitiXPubbl) {
		this.flgAllegatiParteIntUnitiXPubbl = flgAllegatiParteIntUnitiXPubbl;
	}

	public List<AllegatoParteIntSeparatoBean> getListaAllegatiParteIntSeparatiXPubbl() {
		return listaAllegatiParteIntSeparatiXPubbl;
	}

	public void setListaAllegatiParteIntSeparatiXPubbl(
			List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparatiXPubbl) {
		this.listaAllegatiParteIntSeparatiXPubbl = listaAllegatiParteIntSeparatiXPubbl;
	}
	
	public Boolean getFlgDirRespRegTecnicaUnicoRespSpesa() {
		return flgDirRespRegTecnicaUnicoRespSpesa;
	}

	public void setFlgDirRespRegTecnicaUnicoRespSpesa(Boolean flgDirRespRegTecnicaUnicoRespSpesa) {
		this.flgDirRespRegTecnicaUnicoRespSpesa = flgDirRespRegTecnicaUnicoRespSpesa;
	}

	public Boolean getFlgSavedAttoSuSistemaContabile() {
		return flgSavedAttoSuSistemaContabile;
	}

	public Integer getStatoAttoContabilita() {
		return statoAttoContabilita;
	}

	public void setStatoAttoContabilita(Integer statoAttoContabilita) {
		this.statoAttoContabilita = statoAttoContabilita;
	}

	public void setFlgSavedAttoSuSistemaContabile(Boolean flgSavedAttoSuSistemaContabile) {
		this.flgSavedAttoSuSistemaContabile = flgSavedAttoSuSistemaContabile;
	}

	public Boolean getFlgDatiContabiliADSPNonAllineati() {
		return flgDatiContabiliADSPNonAllineati;
	}

	public void setFlgDatiContabiliADSPNonAllineati(Boolean flgDatiContabiliADSPNonAllineati) {
		this.flgDatiContabiliADSPNonAllineati = flgDatiContabiliADSPNonAllineati;
	}

	public String getCdrUOCompetente() {
		return cdrUOCompetente;
	}

	public void setCdrUOCompetente(String cdrUOCompetente) {
		this.cdrUOCompetente = cdrUOCompetente;
	}

	public OpzUOInDettAttoBean getOpzUOCompetente() {
		return opzUOCompetente;
	}

	public void setOpzUOCompetente(OpzUOInDettAttoBean opzUOCompetente) {
		this.opzUOCompetente = opzUOCompetente;
	}

	public List<ErroreRigaExcelBean> getListaExcelDatiContabiliADSPInError() {
		return listaExcelDatiContabiliADSPInError;
	}

	public void setListaExcelDatiContabiliADSPInError(List<ErroreRigaExcelBean> listaExcelDatiContabiliADSPInError) {
		this.listaExcelDatiContabiliADSPInError = listaExcelDatiContabiliADSPInError;
	}

	public List<DatiLiquidazioneAVBBean> getListaDatiLiquidazioneAVB() {
		return listaDatiLiquidazioneAVB;
	}

	public void setListaDatiLiquidazioneAVB(List<DatiLiquidazioneAVBBean> listaDatiLiquidazioneAVB) {
		this.listaDatiLiquidazioneAVB = listaDatiLiquidazioneAVB;
	}

	public List<RiepilogoDatiLiquidazioneAVB> getListaRiepilogoDatiLiquidazioneAVB() {
		return listaRiepilogoDatiLiquidazioneAVB;
	}

	public void setListaRiepilogoDatiLiquidazioneAVB(List<RiepilogoDatiLiquidazioneAVB> listaRiepilogoDatiLiquidazioneAVB) {
		this.listaRiepilogoDatiLiquidazioneAVB = listaRiepilogoDatiLiquidazioneAVB;
	}

	public List<String> getListaXlsToDeleteDatiLiqAVB() {
		return listaXlsToDeleteDatiLiqAVB;
	}

	public void setListaXlsToDeleteDatiLiqAVB(List<String> listaXlsToDeleteDatiLiqAVB) {
		this.listaXlsToDeleteDatiLiqAVB = listaXlsToDeleteDatiLiqAVB;
	}

	public String getEsitoRollbackNumDefAtti() {
		return esitoRollbackNumDefAtti;
	}

	public void setEsitoRollbackNumDefAtti(String esitoRollbackNumDefAtti) {
		this.esitoRollbackNumDefAtti = esitoRollbackNumDefAtti;
	}

	public String getMsgTaskProvvisorio() {
		return msgTaskProvvisorio;
	}

	public void setMsgTaskProvvisorio(String msgTaskProvvisorio) {
		this.msgTaskProvvisorio = msgTaskProvvisorio;
	}

	public List<DatiContabiliATERSIRBean> getListaDatiContabiliATERSIR() {
		return listaDatiContabiliATERSIR;
	}

	public void setListaDatiContabiliATERSIR(List<DatiContabiliATERSIRBean> listaDatiContabiliATERSIR) {
		this.listaDatiContabiliATERSIR = listaDatiContabiliATERSIR;
	}
	
	public Boolean getFlgCopiaAllegatiPareri() {
		return flgCopiaAllegatiPareri;
	}
	
	public void setFlgCopiaAllegatiPareri(Boolean flgCopiaAllegatiPareri) {
		this.flgCopiaAllegatiPareri = flgCopiaAllegatiPareri;
	}

	public String getImponibileComplessivo() {
		return imponibileComplessivo;
	}
	public void setImponibileComplessivo(String imponibileComplessivo) {
		this.imponibileComplessivo = imponibileComplessivo;
	}

	public List<SimpleKeyValueBean> getListaUfficiCompetentiRag() {
		return listaUfficiCompetentiRag;
	}

	public void setListaUfficiCompetentiRag(List<SimpleKeyValueBean> listaUfficiCompetentiRag) {
		this.listaUfficiCompetentiRag = listaUfficiCompetentiRag;
	}

	public String getSubfolderRda() {
		return subfolderRda;
	}

	public void setSubfolderRda(String subfolderRda) {
		this.subfolderRda = subfolderRda;
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