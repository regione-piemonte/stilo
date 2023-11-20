/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.task.bean.AttributiAddDocTabBean;
import it.eng.auriga.ui.module.layout.server.task.bean.ControlloXEsitoTaskDocBean;
import it.eng.auriga.ui.module.layout.server.task.bean.EsitoTaskOkBean;
import it.eng.auriga.ui.module.layout.server.task.bean.EventoXEsitoTaskBean;
import it.eng.auriga.ui.module.layout.server.task.bean.FileDaUnireBean;
import it.eng.auriga.ui.module.layout.server.task.bean.InfoFirmaGraficaBean;
import it.eng.auriga.ui.module.layout.server.task.bean.ParametriTipoAttoBean;
import it.eng.auriga.ui.module.layout.server.task.bean.StatoDocBean;
import it.eng.auriga.ui.module.layout.server.task.bean.ValoriEsitoOutBean;
import it.eng.auriga.ui.module.layout.server.task.bean.WarningMsgXEsitoTaskBean;
import it.eng.document.NumeroColonna;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class AttProcBean {

	@NumeroColonna(numero = "1")
	private String instanceId;
	// 3) Internal name che identifica l'attività nel disegno del flusso. Se ol recxord è una fase coincide con colonna 4
	// Nel caso di Activiti è TASK_DEF_KEY_ dell'attività (attribute id del BPMN)
	@NumeroColonna(numero = "3")
	private String activityName;
	// 4) Nome con cui mostrare l'attività o gruppo di attività
	// Nel caso di Activiti è il NAME_ dell'attività (attribute name del BPMN)
	@NumeroColonna(numero = "4")
	private String nome;
	// 5) (valori 1/0) Se 1 significa che l'attività è eseguibile dall'utente di lavoro
	@NumeroColonna(numero = "5")
	private String flgEseguibile;
	// 6) Motivo per cui l'attività NON è eseguibile dall'utente di lavoro
	@NumeroColonna(numero = "6")
	private String motiviNonEseg;
	// 7) (valori 1/0) Se 1 significa che l'utente di lavoro può accedere alla consultazione dei dati gestiti nella maschera dell'attività
	@NumeroColonna(numero = "7")
	private String flgDatiVisibili;
	// 8) (valori 1/0/-1) Se 1 significa che l'attività è già stata svolta in precedenza, se -1 che non è stata svolta ed è da svolgere, se 0 che non è stata
	// svolta ma non è ancora da svolgere
	@NumeroColonna(numero = "8")
	private String flgFatta;
	// 11) rowid del record della DMT_PROCESS_HISTORY da cui recuperare gli attributi custom dell'attività
	@NumeroColonna(numero = "11")
	private String rowId;
	// 12) Id. evento (PK della DMT_PROCESS_HISTORY) dell'ultimo evento salvato corrispondente alla data attività
	@NumeroColonna(numero = "12")
	private String idEvento;
	// 13) (valori G/AS/AC) Se G il record rappresenta un gruppo di attività, se AC un'attività con sotto-attività, se AS una singola attività senza
	// sotto-attività
	// Un esempio di attività complessa è "Descrizione progetto" (slide 29 del mock-up).
	// Quando si clicca su un'attività complessa nel menu' di attività pensavo di usare sempre la stored dmpk_processes.GetListaAttProcFO per caricare la pagina
	// da mostrare al centro,
	// ovviamente passando come IdTipoEventoAppIn il valore in colonna 14 della voce di menu' premuta
	@NumeroColonna(numero = "13")
	private String tipoAttivita;
	// 14) Id. del tipo evento corrispondente all'attività (se colonna 13 = AS o AC) o cod. identificativo del gruppo di attività (se colonna 13=G)
	@NumeroColonna(numero = "14")
	private String idTipoEvento;
	// 15) Nome dell'eventuale gruppo di attività di appartenenza (gruppo = voce 1° livello del menu' attività)
	@NumeroColonna(numero = "15")
	private String nomeGruppoApp;
	// 16) Nome dell'eventuale attività complessa di appartenenza (gruppo = voce n° livello (con n>1) del menu' attività)
	@NumeroColonna(numero = "16")
	private String nomeAttivitaApp;
	// 17) Entry-point corrispondente alla GUI specifica del task. Se non valorizzato e se colonna 14 = AS significa che è un task con maschera dinamica
	@NumeroColonna(numero = "17")
	private String idGUIEvento;
	// 18) Spiegazione del significato dell'attività / gruppo di attività: questo serve per le frasi di spiegazione in grigio nel mock-up (vedi slide 29 del
	// mock-up)
	@NumeroColonna(numero = "18")
	private String dettagli;
	// 19) Id. del tipo documento associato al task
	@NumeroColonna(numero = "19")
	private String idDocTipDocTask;
	// 20) Nome del tipo documento associato al task => variabile #NomeDocType dell’xml TaskInfoXMLOut restituito dalla dmpk_wf.Call_ExecAtt
	@NumeroColonna(numero = "20")
	private String nomeTipDocTask;
	// 21) URI dell’unico modello associato al tipo di documento corrispondente al task
	@NumeroColonna(numero = "21")
	private String uriModAssDocTask;
	// 31) tipo del compilatore da usare per il modello associato al task
	@NumeroColonna(numero = "31")
	private String tipoModAssDocTask;
	// 22) Mimetype del modello (se uno solo) associato al tipo di documento corrispondente al task => variabile #MimetypeModelloDoc dell’xml TaskInfoXMLOut
	// restituito dalla dmpk_wf.Call_ExecAtt
	@NumeroColonna(numero = "22")
	private String mimetypeModAssDocTask;
	// 23) ID_DOC del documento della tipologia documentale associata al task
	@NumeroColonna(numero = "23")
	private String idDocAssTask;
	// 24) URI dell'ultima versione del documento della tipologia documentale associata al task
	@NumeroColonna(numero = "24")
	private String uriUltimaVersDocTask;
	// 25) Mimetype dell'ultima versione del documento della tipologia documentale associata al task
	@NumeroColonna(numero = "25")
	private String mimetypeDocTipAssTask;
	// 26 Nome file dell'ultima versione del documento della tipologia documentale associata al task
	@NumeroColonna(numero = "26")
	private String nomeFileDocTipAssTask;
	// 27 Flag (1|0) che indica se devo chiamare la CallExecAtt
	@NumeroColonna(numero = "27")
	private String flgCallExecAtt;
	// 28 Nome file contenente l'icona del task
	@NumeroColonna(numero = "28")
	private String icona;
	// 29 ID_UD dell'unità documentaria associata al task
	@NumeroColonna(numero = "29")
	private String idUd;
	// 31) (valori OK/W/KO) Se OK significa che l'attività è stata svolta con esito positivo, se W con esito positivo ma con degli avvertimenti, se KO con esito
	// negativo
	@NumeroColonna(numero = "31")
	private String flgEsito;
	// 32 Flag (1|0) che indica se quell'attività e da fare in generale, anche se quell'utente non la può eseguire
	@NumeroColonna(numero = "32")
	private String flgToDo;
	// 33 Flag (1|0) che indica se quell'attività non deve essere presa in considerazione quando si apre automatico il primo task eseguibile
	@NumeroColonna(numero = "33")
	private String flgAttivitaDaNonAprireInAutomatico;


	private String idUnitaLocale;
	private String nomeUnitaLocale;
	private String idUbicazioneAreaIntervento;
	private String idUdIstanza;
	private String uriRicAvvio;
	private String emailProponente;
	private String desUoProponente;
	private String nomeResponsabileUoProponente;
	private String rifAttoInOrganigramma;
	private String listaNextTask;
	private Boolean abilitaCallApplTitoliEdilizi;
	private String annoProtocolloIstanza;
	private String nroProtocolloIstanza;
	private String idDocIstanza;		
	
	// proprieta' della Call_ExecAtt
	private String idProcess;
	private String idFolder;
	private String idDoc;
	private String idTipoDoc;
	private String uriFile;
	private String nomeFile;
	private MimeTypeFirmaBean infoFile;
	private String uriFileOmissis;
	private String nomeFileOmissis;
	private MimeTypeFirmaBean infoFileOmissis;	
	private String estremiUd;
	private String nomeTastoSalvaProvvisorio;
	private String nomeTastoSalvaDefinitivo;
	private String nomeTastoSalvaProvvisorio_2;
	private String nomeTastoSalvaDefinitivo_2;
	private String alertConfermaSalvaDefinitivo;
	
	private List<EsitoTaskOkBean> esitiTaskAzioni;
	private String idDocAzioni;
	private Boolean flgFirmaFile;
	private Boolean flgShowAnteprimaFileDaFirmare;	
	private Boolean flgTimbraFile;
	private Boolean flgProtocolla;
	private Boolean flgUnioneFile;
	private String unioneFileNomeFile;
	private String unioneFileNomeFileOmissis;
	private String unioneFileIdTipoDoc;
	private String unioneFileNomeTipoDoc;
	private String unioneFileDescrizione;
	private Boolean flgShowAnteprimaDefinitiva;
	private Boolean flgConversionePdf;
	private Boolean flgInvioPEC;
	private String invioPECSubject;
	private String invioPECBody;
	private String invioPECDestinatari;
	private String invioPECDestinatariCC;
	private String invioPECIdCasellaMittente;
	private String invioPECIndirizzoMittente;
	private String invioPECAttach;
	private String eventoSUETipo;
	private String eventoSUEIdPratica;
	private String eventoSUECodFiscale;
	private String eventoSUEGiorniSospensione;
	private Boolean eventoSUEFlgPubblico;
	private List<DestinatarioSUEBean> eventoSUEDestinatari;
	private List<FileDaPubblicareSUEBean> eventoSUEFileDaPubblicare;
	private Boolean flgInvioNotEmail;
	private String invioNotEmailSubject;
	private String invioNotEmailBody;
	private List<SimpleValueBean> invioNotEmailDestinatari;
	private List<SimpleValueBean> invioNotEmailDestinatariCC;
	private List<SimpleValueBean> invioNotEmailDestinatariCCN;
	private String invioNotEmailIdCasellaMittente;
	private String invioNotEmailIndirizzoMittente;
	private String invioNotEmailAliasIndirizzoMittente;
	private Boolean invioNotEmailFlgPEC;
	private List<AttachmentInvioNotEmailBean> invioNotEmailAttachment;	
	private Boolean invioNotEmailFlgInvioMailXComplTask;
	private Boolean invioNotEmailFlgConfermaInvio;
	private Boolean invioNotEmailFlgCallXDettagliMail;	
	private Boolean flgNotificaMail;
	private String notificaMailSubject;
	private String notificaMailBody;
	private String notificaMailDestinatari;
	private String notificaMailDestinatariCC;
	private String notificaMailIdCasellaMittente;
	private String notificaMailIndirizzoMittente;	
	private Boolean flgInvioFtpAlbo;	
	private Boolean flgPubblica;
	private Boolean flgCompilaDatiPubblicazione;
	private String dataInizioPubblicazione;
	private String giorniPubblicazione;
	
	private String idDefProcFlow;
	private String idInstProcFlow;
	private String warningMsg;
	private List<FileDaUnireBean> fileDaUnire;
	private String nomeFileUnione;
	private List<FileDaUnireBean> fileDaUnireVersIntegrale;
	private String nomeFileUnioneVersIntegrale;
	private String tipoDocFileUnioneVersIntegrale;
	private String siglaRegistroAtto;
	private String siglaRegistroAtto2;
	private String editor;
	private String nomeAttrCustomEsitoTask;
	private Boolean flgReadOnly;
	private String idModAssDocTask;
	private String nomeModAssDocTask;
	private String displayFilenameModAssDocTask;		
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
	private String idModFoglioFirme;
	private String nomeModFoglioFirme;
	private String displayFilenameModFoglioFirme;
	private String idModFoglioFirme2;
	private String nomeModFoglioFirme2;
	private String displayFilenameModFoglioFirme2;
	private String idModSchedaTrasp;
	private String nomeModSchedaTrasp;
	private String displayFilenameModSchedaTrasp;
	
	private List<AttributiAddDocTabBean> attributiAddDocTabs;
	private List<EsitoTaskOkBean> esitiTaskOk;
	private List<EsitoTaskOkBean> esitiTaskKo;
	private Boolean fileUnioneDaFirmare;
	private Boolean fileUnioneDaTimbrare;

	private String posizioneTimbro;
	private String tipoPagina;
	private String paginaDa;
	private String paginaA;
	private String rotazioneTimbro;

	private String codTabDefault;
	private String idTipoTaskDoc;
	private String nomeTipoTaskDoc;
	private Boolean flgParereTaskDoc;
	private Boolean flgParteDispositivoTaskDoc;
	private Boolean flgNoPubblTaskDoc;
	private Boolean flgPubblicaSeparatoTaskDoc;
	
	private List<ControlloXEsitoTaskDocBean> controlliXEsitiTaskDoc;
	private List<ValoriEsitoOutBean> valoriEsito;
	
	private String attivaModificaEsclusionePubblicazione;
	private String attivaEliminazioneUOCoinvolte;
	
	private Boolean taskArchivio;
	private Boolean flgDatiSpesaEditabili;
	private Boolean flgCIGEditabile;
	private String tipoEventoSIB;
	private List<EsitoTaskOkBean> esitiTaskEventoSIB;	
	private List<EventoXEsitoTaskBean> tipiEventoSIBXEsitoTask;	
	private String idUoDirAdottanteSIB;
	private String codUoDirAdottanteSIB;
	private String desUoDirAdottanteSIB;
	
	private List<EventoXEsitoTaskBean> tipiEventoContabiliaXEsitoTask;	
	
	private List<EventoXEsitoTaskBean> tipiEventoSICRAXEsitoTask;
	
	private List<EventoXEsitoTaskBean> tipiEventoCWOLXEsitoTask;
	private Boolean flgRecuperaMovimentiContabDefinitivi;
	
	private Boolean flgAttivaSmistamento;
	
	private String nomeAttrListaDatiContabili;
	private String nomeAttrListaDatiContabiliRichiesti;
	
	private Boolean flgAttivaRequestMovimentiDaAMC;
	private Boolean flgAttivaSalvataggioMovimentiDaAMC;
	private Boolean flgEscludiFiltroCdCVsAMC;
	
	private String nomeVersConfronto;	
	private String nroVersConfronto;	
	private String nroVersLavoro;	
	private String azione;	
	private String idDocOrganigramma;  
	private String idDocArchiviazionePdf;
	private String livelloRevisione;
	private String livelloMaxRevisione;
	private String tipiUORevisione;
	
	private Boolean flgDelibera;
	private String showDirigentiProponenti;
	private String showAssessori;
	private String showConsiglieri;
	
	private Boolean flgPubblicazioneAllegatiUguale;			
	private ParametriTipoAttoBean parametriTipoAtto;
	private Boolean flgSoloPreparazioneVersPubblicazione;
	private Boolean flgCtrlMimeTypeAllegPI;
	private Boolean flgProtocollazioneProsa;
	private Boolean flgPresentiEmendamenti;
	private Boolean flgFirmaVersPubblAggiornata;
	
	private Boolean abilAggiornaStatoAttoPostDiscussione;
	private List<StatoDocBean> statiXAggAttoPostDiscussione;
	
	private String exportAttoInDocFmt;
	
	private Boolean flgAvvioLiquidazioneContabilia;
	private Boolean flgNumPropostaDiffXStruttura;
	
	private ImpostazioniUnioneFileBean impostazioniUnioneFile;
	
	private List<WarningMsgXEsitoTaskBean> warningMsgXEsitoTask;
	private String esitoTaskDaPreimpostare;	
	private String msgTaskDaPreimpostare;
	private String msgTaskProvvisorio;
	
	private Boolean flgAbilToSelProponentiEstesi;
	
	private Boolean flgAttivaUploadPdfAtto;
	private Boolean flgAttivaUploadPdfAttoOmissis;
	
	private Boolean flgSoloOmissisModProprietaFile;
	
	private Boolean flgDocActionsFirmaAutomatica;
	private String docActionsFirmaAutomaticaProvider;
	private String docActionsFirmaAutomaticaUseridFirmatario;
	private String docActionsFirmaAutomaticaFirmaInDelega;
	private String docActionsFirmaAutomaticaPassword;
	
	private List<InfoFirmaGraficaBean> infoFirmaGrafica; 
	
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFlgEseguibile() {
		return flgEseguibile;
	}
	public void setFlgEseguibile(String flgEseguibile) {
		this.flgEseguibile = flgEseguibile;
	}
	public String getMotiviNonEseg() {
		return motiviNonEseg;
	}
	public void setMotiviNonEseg(String motiviNonEseg) {
		this.motiviNonEseg = motiviNonEseg;
	}
	public String getFlgDatiVisibili() {
		return flgDatiVisibili;
	}
	public void setFlgDatiVisibili(String flgDatiVisibili) {
		this.flgDatiVisibili = flgDatiVisibili;
	}
	public String getFlgFatta() {
		return flgFatta;
	}
	public void setFlgFatta(String flgFatta) {
		this.flgFatta = flgFatta;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}
	public String getTipoAttivita() {
		return tipoAttivita;
	}
	public void setTipoAttivita(String tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}
	public String getIdTipoEvento() {
		return idTipoEvento;
	}
	public void setIdTipoEvento(String idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}
	public String getNomeGruppoApp() {
		return nomeGruppoApp;
	}
	public void setNomeGruppoApp(String nomeGruppoApp) {
		this.nomeGruppoApp = nomeGruppoApp;
	}
	public String getNomeAttivitaApp() {
		return nomeAttivitaApp;
	}
	public void setNomeAttivitaApp(String nomeAttivitaApp) {
		this.nomeAttivitaApp = nomeAttivitaApp;
	}
	public String getIdGUIEvento() {
		return idGUIEvento;
	}
	public void setIdGUIEvento(String idGUIEvento) {
		this.idGUIEvento = idGUIEvento;
	}
	public String getDettagli() {
		return dettagli;
	}
	public void setDettagli(String dettagli) {
		this.dettagli = dettagli;
	}
	public String getIdDocTipDocTask() {
		return idDocTipDocTask;
	}
	public void setIdDocTipDocTask(String idDocTipDocTask) {
		this.idDocTipDocTask = idDocTipDocTask;
	}
	public String getNomeTipDocTask() {
		return nomeTipDocTask;
	}
	public void setNomeTipDocTask(String nomeTipDocTask) {
		this.nomeTipDocTask = nomeTipDocTask;
	}
	public String getUriModAssDocTask() {
		return uriModAssDocTask;
	}
	public void setUriModAssDocTask(String uriModAssDocTask) {
		this.uriModAssDocTask = uriModAssDocTask;
	}
	public String getTipoModAssDocTask() {
		return tipoModAssDocTask;
	}
	public void setTipoModAssDocTask(String tipoModAssDocTask) {
		this.tipoModAssDocTask = tipoModAssDocTask;
	}
	public String getMimetypeModAssDocTask() {
		return mimetypeModAssDocTask;
	}
	public void setMimetypeModAssDocTask(String mimetypeModAssDocTask) {
		this.mimetypeModAssDocTask = mimetypeModAssDocTask;
	}
	public String getIdDocAssTask() {
		return idDocAssTask;
	}
	public void setIdDocAssTask(String idDocAssTask) {
		this.idDocAssTask = idDocAssTask;
	}
	public String getUriUltimaVersDocTask() {
		return uriUltimaVersDocTask;
	}
	public void setUriUltimaVersDocTask(String uriUltimaVersDocTask) {
		this.uriUltimaVersDocTask = uriUltimaVersDocTask;
	}
	public String getMimetypeDocTipAssTask() {
		return mimetypeDocTipAssTask;
	}
	public void setMimetypeDocTipAssTask(String mimetypeDocTipAssTask) {
		this.mimetypeDocTipAssTask = mimetypeDocTipAssTask;
	}
	public String getNomeFileDocTipAssTask() {
		return nomeFileDocTipAssTask;
	}
	public void setNomeFileDocTipAssTask(String nomeFileDocTipAssTask) {
		this.nomeFileDocTipAssTask = nomeFileDocTipAssTask;
	}
	public String getFlgCallExecAtt() {
		return flgCallExecAtt;
	}
	public void setFlgCallExecAtt(String flgCallExecAtt) {
		this.flgCallExecAtt = flgCallExecAtt;
	}
	public String getIcona() {
		return icona;
	}
	public void setIcona(String icona) {
		this.icona = icona;
	}
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getFlgEsito() {
		return flgEsito;
	}
	public void setFlgEsito(String flgEsito) {
		this.flgEsito = flgEsito;
	}
	public String getFlgToDo() {
		return flgToDo;
	}
	public void setFlgToDo(String flgToDo) {
		this.flgToDo = flgToDo;
	}
	public String getIdUnitaLocale() {
		return idUnitaLocale;
	}
	public void setIdUnitaLocale(String idUnitaLocale) {
		this.idUnitaLocale = idUnitaLocale;
	}
	public String getNomeUnitaLocale() {
		return nomeUnitaLocale;
	}
	public void setNomeUnitaLocale(String nomeUnitaLocale) {
		this.nomeUnitaLocale = nomeUnitaLocale;
	}
	public String getIdUbicazioneAreaIntervento() {
		return idUbicazioneAreaIntervento;
	}
	public void setIdUbicazioneAreaIntervento(String idUbicazioneAreaIntervento) {
		this.idUbicazioneAreaIntervento = idUbicazioneAreaIntervento;
	}
	public String getIdUdIstanza() {
		return idUdIstanza;
	}
	public void setIdUdIstanza(String idUdIstanza) {
		this.idUdIstanza = idUdIstanza;
	}
	public String getUriRicAvvio() {
		return uriRicAvvio;
	}
	public void setUriRicAvvio(String uriRicAvvio) {
		this.uriRicAvvio = uriRicAvvio;
	}
	public String getEmailProponente() {
		return emailProponente;
	}
	public void setEmailProponente(String emailProponente) {
		this.emailProponente = emailProponente;
	}
	public String getDesUoProponente() {
		return desUoProponente;
	}
	public void setDesUoProponente(String desUoProponente) {
		this.desUoProponente = desUoProponente;
	}
	public String getNomeResponsabileUoProponente() {
		return nomeResponsabileUoProponente;
	}
	public void setNomeResponsabileUoProponente(String nomeResponsabileUoProponente) {
		this.nomeResponsabileUoProponente = nomeResponsabileUoProponente;
	}
	public String getRifAttoInOrganigramma() {
		return rifAttoInOrganigramma;
	}
	public void setRifAttoInOrganigramma(String rifAttoInOrganigramma) {
		this.rifAttoInOrganigramma = rifAttoInOrganigramma;
	}
	public String getListaNextTask() {
		return listaNextTask;
	}
	public void setListaNextTask(String listaNextTask) {
		this.listaNextTask = listaNextTask;
	}	
	public Boolean getAbilitaCallApplTitoliEdilizi() {
		return abilitaCallApplTitoliEdilizi;
	}
	public void setAbilitaCallApplTitoliEdilizi(Boolean abilitaCallApplTitoliEdilizi) {
		this.abilitaCallApplTitoliEdilizi = abilitaCallApplTitoliEdilizi;
	}
	public String getAnnoProtocolloIstanza() {
		return annoProtocolloIstanza;
	}
	public void setAnnoProtocolloIstanza(String annoProtocolloIstanza) {
		this.annoProtocolloIstanza = annoProtocolloIstanza;
	}
	public String getNroProtocolloIstanza() {
		return nroProtocolloIstanza;
	}
	public void setNroProtocolloIstanza(String nroProtocolloIstanza) {
		this.nroProtocolloIstanza = nroProtocolloIstanza;
	}
	public String getIdDocIstanza() {
		return idDocIstanza;
	}
	public void setIdDocIstanza(String idDocIstanza) {
		this.idDocIstanza = idDocIstanza;
	}
	public String getIdProcess() {
		return idProcess;
	}
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	public String getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public String getIdTipoDoc() {
		return idTipoDoc;
	}
	public void setIdTipoDoc(String idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}
	public String getUriFile() {
		return uriFile;
	}
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}	
	public String getUriFileOmissis() {
		return uriFileOmissis;
	}
	public void setUriFileOmissis(String uriFileOmissis) {
		this.uriFileOmissis = uriFileOmissis;
	}
	public String getNomeFileOmissis() {
		return nomeFileOmissis;
	}
	public void setNomeFileOmissis(String nomeFileOmissis) {
		this.nomeFileOmissis = nomeFileOmissis;
	}
	public MimeTypeFirmaBean getInfoFileOmissis() {
		return infoFileOmissis;
	}
	public void setInfoFileOmissis(MimeTypeFirmaBean infoFileOmissis) {
		this.infoFileOmissis = infoFileOmissis;
	}
	public String getEstremiUd() {
		return estremiUd;
	}
	public void setEstremiUd(String estremiUd) {
		this.estremiUd = estremiUd;
	}
	public String getNomeTastoSalvaProvvisorio() {
		return nomeTastoSalvaProvvisorio;
	}
	public void setNomeTastoSalvaProvvisorio(String nomeTastoSalvaProvvisorio) {
		this.nomeTastoSalvaProvvisorio = nomeTastoSalvaProvvisorio;
	}
	public String getNomeTastoSalvaDefinitivo() {
		return nomeTastoSalvaDefinitivo;
	}
	public void setNomeTastoSalvaDefinitivo(String nomeTastoSalvaDefinitivo) {
		this.nomeTastoSalvaDefinitivo = nomeTastoSalvaDefinitivo;
	}
	public String getNomeTastoSalvaProvvisorio_2() {
		return nomeTastoSalvaProvvisorio_2;
	}
	public void setNomeTastoSalvaProvvisorio_2(String nomeTastoSalvaProvvisorio_2) {
		this.nomeTastoSalvaProvvisorio_2 = nomeTastoSalvaProvvisorio_2;
	}
	public String getNomeTastoSalvaDefinitivo_2() {
		return nomeTastoSalvaDefinitivo_2;
	}
	public void setNomeTastoSalvaDefinitivo_2(String nomeTastoSalvaDefinitivo_2) {
		this.nomeTastoSalvaDefinitivo_2 = nomeTastoSalvaDefinitivo_2;
	}
	public String getAlertConfermaSalvaDefinitivo() {
		return alertConfermaSalvaDefinitivo;
	}
	public void setAlertConfermaSalvaDefinitivo(String alertConfermaSalvaDefinitivo) {
		this.alertConfermaSalvaDefinitivo = alertConfermaSalvaDefinitivo;
	}
	public List<EsitoTaskOkBean> getEsitiTaskAzioni() {
		return esitiTaskAzioni;
	}
	public void setEsitiTaskAzioni(List<EsitoTaskOkBean> esitiTaskAzioni) {
		this.esitiTaskAzioni = esitiTaskAzioni;
	}
	public String getIdDocAzioni() {
		return idDocAzioni;
	}
	public void setIdDocAzioni(String idDocAzioni) {
		this.idDocAzioni = idDocAzioni;
	}
	public Boolean getFlgFirmaFile() {
		return flgFirmaFile;
	}
	public void setFlgFirmaFile(Boolean flgFirmaFile) {
		this.flgFirmaFile = flgFirmaFile;
	}
	public Boolean getFlgShowAnteprimaFileDaFirmare() {
		return flgShowAnteprimaFileDaFirmare;
	}
	public void setFlgShowAnteprimaFileDaFirmare(Boolean flgShowAnteprimaFileDaFirmare) {
		this.flgShowAnteprimaFileDaFirmare = flgShowAnteprimaFileDaFirmare;
	}
	public Boolean getFlgTimbraFile() {
		return flgTimbraFile;
	}
	public void setFlgTimbraFile(Boolean flgTimbraFile) {
		this.flgTimbraFile = flgTimbraFile;
	}
	public Boolean getFlgProtocolla() {
		return flgProtocolla;
	}
	public void setFlgProtocolla(Boolean flgProtocolla) {
		this.flgProtocolla = flgProtocolla;
	}
	public Boolean getFlgUnioneFile() {
		return flgUnioneFile;
	}
	public void setFlgUnioneFile(Boolean flgUnioneFile) {
		this.flgUnioneFile = flgUnioneFile;
	}
	public String getUnioneFileNomeFile() {
		return unioneFileNomeFile;
	}
	public void setUnioneFileNomeFile(String unioneFileNomeFile) {
		this.unioneFileNomeFile = unioneFileNomeFile;
	}
	public String getUnioneFileNomeFileOmissis() {
		return unioneFileNomeFileOmissis;
	}
	public void setUnioneFileNomeFileOmissis(String unioneFileNomeFileOmissis) {
		this.unioneFileNomeFileOmissis = unioneFileNomeFileOmissis;
	}
	public String getUnioneFileIdTipoDoc() {
		return unioneFileIdTipoDoc;
	}
	public void setUnioneFileIdTipoDoc(String unioneFileIdTipoDoc) {
		this.unioneFileIdTipoDoc = unioneFileIdTipoDoc;
	}
	public String getUnioneFileNomeTipoDoc() {
		return unioneFileNomeTipoDoc;
	}
	public void setUnioneFileNomeTipoDoc(String unioneFileNomeTipoDoc) {
		this.unioneFileNomeTipoDoc = unioneFileNomeTipoDoc;
	}
	public String getUnioneFileDescrizione() {
		return unioneFileDescrizione;
	}
	public void setUnioneFileDescrizione(String unioneFileDescrizione) {
		this.unioneFileDescrizione = unioneFileDescrizione;
	}
	public Boolean getFlgShowAnteprimaDefinitiva() {
		return flgShowAnteprimaDefinitiva;
	}
	public void setFlgShowAnteprimaDefinitiva(Boolean flgShowAnteprimaDefinitiva) {
		this.flgShowAnteprimaDefinitiva = flgShowAnteprimaDefinitiva;
	}
	public Boolean getFlgConversionePdf() {
		return flgConversionePdf;
	}
	public void setFlgConversionePdf(Boolean flgConversionePdf) {
		this.flgConversionePdf = flgConversionePdf;
	}
	public Boolean getFlgInvioPEC() {
		return flgInvioPEC;
	}
	public void setFlgInvioPEC(Boolean flgInvioPEC) {
		this.flgInvioPEC = flgInvioPEC;
	}
	public String getInvioPECSubject() {
		return invioPECSubject;
	}
	public void setInvioPECSubject(String invioPECSubject) {
		this.invioPECSubject = invioPECSubject;
	}
	public String getInvioPECBody() {
		return invioPECBody;
	}
	public void setInvioPECBody(String invioPECBody) {
		this.invioPECBody = invioPECBody;
	}
	public String getInvioPECDestinatari() {
		return invioPECDestinatari;
	}
	public void setInvioPECDestinatari(String invioPECDestinatari) {
		this.invioPECDestinatari = invioPECDestinatari;
	}
	public String getInvioPECDestinatariCC() {
		return invioPECDestinatariCC;
	}
	public void setInvioPECDestinatariCC(String invioPECDestinatariCC) {
		this.invioPECDestinatariCC = invioPECDestinatariCC;
	}
	public String getInvioPECIdCasellaMittente() {
		return invioPECIdCasellaMittente;
	}
	public void setInvioPECIdCasellaMittente(String invioPECIdCasellaMittente) {
		this.invioPECIdCasellaMittente = invioPECIdCasellaMittente;
	}
	public String getInvioPECIndirizzoMittente() {
		return invioPECIndirizzoMittente;
	}
	public void setInvioPECIndirizzoMittente(String invioPECIndirizzoMittente) {
		this.invioPECIndirizzoMittente = invioPECIndirizzoMittente;
	}
	public String getInvioPECAttach() {
		return invioPECAttach;
	}
	public void setInvioPECAttach(String invioPECAttach) {
		this.invioPECAttach = invioPECAttach;
	}
	public String getEventoSUETipo() {
		return eventoSUETipo;
	}
	public void setEventoSUETipo(String eventoSUETipo) {
		this.eventoSUETipo = eventoSUETipo;
	}
	public String getEventoSUEIdPratica() {
		return eventoSUEIdPratica;
	}
	public void setEventoSUEIdPratica(String eventoSUEIdPratica) {
		this.eventoSUEIdPratica = eventoSUEIdPratica;
	}
	public String getEventoSUECodFiscale() {
		return eventoSUECodFiscale;
	}
	public void setEventoSUECodFiscale(String eventoSUECodFiscale) {
		this.eventoSUECodFiscale = eventoSUECodFiscale;
	}
	public String getEventoSUEGiorniSospensione() {
		return eventoSUEGiorniSospensione;
	}
	public void setEventoSUEGiorniSospensione(String eventoSUEGiorniSospensione) {
		this.eventoSUEGiorniSospensione = eventoSUEGiorniSospensione;
	}
	public Boolean getEventoSUEFlgPubblico() {
		return eventoSUEFlgPubblico;
	}
	public void setEventoSUEFlgPubblico(Boolean eventoSUEFlgPubblico) {
		this.eventoSUEFlgPubblico = eventoSUEFlgPubblico;
	}
	public List<DestinatarioSUEBean> getEventoSUEDestinatari() {
		return eventoSUEDestinatari;
	}
	public void setEventoSUEDestinatari(List<DestinatarioSUEBean> eventoSUEDestinatari) {
		this.eventoSUEDestinatari = eventoSUEDestinatari;
	}
	public List<FileDaPubblicareSUEBean> getEventoSUEFileDaPubblicare() {
		return eventoSUEFileDaPubblicare;
	}
	public void setEventoSUEFileDaPubblicare(List<FileDaPubblicareSUEBean> eventoSUEFileDaPubblicare) {
		this.eventoSUEFileDaPubblicare = eventoSUEFileDaPubblicare;
	}
	public Boolean getFlgInvioNotEmail() {
		return flgInvioNotEmail;
	}
	public void setFlgInvioNotEmail(Boolean flgInvioNotEmail) {
		this.flgInvioNotEmail = flgInvioNotEmail;
	}
	public String getInvioNotEmailSubject() {
		return invioNotEmailSubject;
	}
	public void setInvioNotEmailSubject(String invioNotEmailSubject) {
		this.invioNotEmailSubject = invioNotEmailSubject;
	}
	public String getInvioNotEmailBody() {
		return invioNotEmailBody;
	}
	public void setInvioNotEmailBody(String invioNotEmailBody) {
		this.invioNotEmailBody = invioNotEmailBody;
	}
	public List<SimpleValueBean> getInvioNotEmailDestinatari() {
		return invioNotEmailDestinatari;
	}
	public void setInvioNotEmailDestinatari(List<SimpleValueBean> invioNotEmailDestinatari) {
		this.invioNotEmailDestinatari = invioNotEmailDestinatari;
	}
	public List<SimpleValueBean> getInvioNotEmailDestinatariCC() {
		return invioNotEmailDestinatariCC;
	}
	public void setInvioNotEmailDestinatariCC(List<SimpleValueBean> invioNotEmailDestinatariCC) {
		this.invioNotEmailDestinatariCC = invioNotEmailDestinatariCC;
	}
	public List<SimpleValueBean> getInvioNotEmailDestinatariCCN() {
		return invioNotEmailDestinatariCCN;
	}
	public void setInvioNotEmailDestinatariCCN(List<SimpleValueBean> invioNotEmailDestinatariCCN) {
		this.invioNotEmailDestinatariCCN = invioNotEmailDestinatariCCN;
	}
	public String getInvioNotEmailIdCasellaMittente() {
		return invioNotEmailIdCasellaMittente;
	}
	public void setInvioNotEmailIdCasellaMittente(String invioNotEmailIdCasellaMittente) {
		this.invioNotEmailIdCasellaMittente = invioNotEmailIdCasellaMittente;
	}
	public String getInvioNotEmailIndirizzoMittente() {
		return invioNotEmailIndirizzoMittente;
	}
	public void setInvioNotEmailIndirizzoMittente(String invioNotEmailIndirizzoMittente) {
		this.invioNotEmailIndirizzoMittente = invioNotEmailIndirizzoMittente;
	}
	public String getInvioNotEmailAliasIndirizzoMittente() {
		return invioNotEmailAliasIndirizzoMittente;
	}
	public void setInvioNotEmailAliasIndirizzoMittente(String invioNotEmailAliasIndirizzoMittente) {
		this.invioNotEmailAliasIndirizzoMittente = invioNotEmailAliasIndirizzoMittente;
	}
	public Boolean getInvioNotEmailFlgPEC() {
		return invioNotEmailFlgPEC;
	}
	public void setInvioNotEmailFlgPEC(Boolean invioNotEmailFlgPEC) {
		this.invioNotEmailFlgPEC = invioNotEmailFlgPEC;
	}
	public List<AttachmentInvioNotEmailBean> getInvioNotEmailAttachment() {
		return invioNotEmailAttachment;
	}
	public void setInvioNotEmailAttachment(List<AttachmentInvioNotEmailBean> invioNotEmailAttachment) {
		this.invioNotEmailAttachment = invioNotEmailAttachment;
	}
	public Boolean getInvioNotEmailFlgInvioMailXComplTask() {
		return invioNotEmailFlgInvioMailXComplTask;
	}
	public void setInvioNotEmailFlgInvioMailXComplTask(Boolean invioNotEmailFlgInvioMailXComplTask) {
		this.invioNotEmailFlgInvioMailXComplTask = invioNotEmailFlgInvioMailXComplTask;
	}
	public Boolean getInvioNotEmailFlgConfermaInvio() {
		return invioNotEmailFlgConfermaInvio;
	}
	public void setInvioNotEmailFlgConfermaInvio(Boolean invioNotEmailFlgConfermaInvio) {
		this.invioNotEmailFlgConfermaInvio = invioNotEmailFlgConfermaInvio;
	}
	public Boolean getInvioNotEmailFlgCallXDettagliMail() {
		return invioNotEmailFlgCallXDettagliMail;
	}
	public void setInvioNotEmailFlgCallXDettagliMail(Boolean invioNotEmailFlgCallXDettagliMail) {
		this.invioNotEmailFlgCallXDettagliMail = invioNotEmailFlgCallXDettagliMail;
	}
	public Boolean getFlgNotificaMail() {
		return flgNotificaMail;
	}
	public void setFlgNotificaMail(Boolean flgNotificaMail) {
		this.flgNotificaMail = flgNotificaMail;
	}
	public String getNotificaMailSubject() {
		return notificaMailSubject;
	}
	public void setNotificaMailSubject(String notificaMailSubject) {
		this.notificaMailSubject = notificaMailSubject;
	}
	public String getNotificaMailBody() {
		return notificaMailBody;
	}
	public void setNotificaMailBody(String notificaMailBody) {
		this.notificaMailBody = notificaMailBody;
	}
	public String getNotificaMailDestinatari() {
		return notificaMailDestinatari;
	}
	public void setNotificaMailDestinatari(String notificaMailDestinatari) {
		this.notificaMailDestinatari = notificaMailDestinatari;
	}
	public String getNotificaMailDestinatariCC() {
		return notificaMailDestinatariCC;
	}
	public void setNotificaMailDestinatariCC(String notificaMailDestinatariCC) {
		this.notificaMailDestinatariCC = notificaMailDestinatariCC;
	}
	public String getNotificaMailIdCasellaMittente() {
		return notificaMailIdCasellaMittente;
	}
	public void setNotificaMailIdCasellaMittente(String notificaMailIdCasellaMittente) {
		this.notificaMailIdCasellaMittente = notificaMailIdCasellaMittente;
	}
	public String getNotificaMailIndirizzoMittente() {
		return notificaMailIndirizzoMittente;
	}
	public void setNotificaMailIndirizzoMittente(String notificaMailIndirizzoMittente) {
		this.notificaMailIndirizzoMittente = notificaMailIndirizzoMittente;
	}
	public Boolean getFlgInvioFtpAlbo() {
		return flgInvioFtpAlbo;
	}
	public void setFlgInvioFtpAlbo(Boolean flgInvioFtpAlbo) {
		this.flgInvioFtpAlbo = flgInvioFtpAlbo;
	}
	public Boolean getFlgPubblica() {
		return flgPubblica;
	}
	public void setFlgPubblica(Boolean flgPubblica) {
		this.flgPubblica = flgPubblica;
	}
	public Boolean getFlgCompilaDatiPubblicazione() {
		return flgCompilaDatiPubblicazione;
	}
	public void setFlgCompilaDatiPubblicazione(Boolean flgCompilaDatiPubblicazione) {
		this.flgCompilaDatiPubblicazione = flgCompilaDatiPubblicazione;
	}
	public String getDataInizioPubblicazione() {
		return dataInizioPubblicazione;
	}
	public void setDataInizioPubblicazione(String dataInizioPubblicazione) {
		this.dataInizioPubblicazione = dataInizioPubblicazione;
	}
	public String getGiorniPubblicazione() {
		return giorniPubblicazione;
	}
	public void setGiorniPubblicazione(String giorniPubblicazione) {
		this.giorniPubblicazione = giorniPubblicazione;
	}
	public String getIdDefProcFlow() {
		return idDefProcFlow;
	}
	public void setIdDefProcFlow(String idDefProcFlow) {
		this.idDefProcFlow = idDefProcFlow;
	}
	public String getIdInstProcFlow() {
		return idInstProcFlow;
	}
	public void setIdInstProcFlow(String idInstProcFlow) {
		this.idInstProcFlow = idInstProcFlow;
	}
	public String getWarningMsg() {
		return warningMsg;
	}
	public void setWarningMsg(String warningMsg) {
		this.warningMsg = warningMsg;
	}
	public List<FileDaUnireBean> getFileDaUnire() {
		return fileDaUnire;
	}
	public void setFileDaUnire(List<FileDaUnireBean> fileDaUnire) {
		this.fileDaUnire = fileDaUnire;
	}
	public String getNomeFileUnione() {
		return nomeFileUnione;
	}
	public void setNomeFileUnione(String nomeFileUnione) {
		this.nomeFileUnione = nomeFileUnione;
	}
	public List<FileDaUnireBean> getFileDaUnireVersIntegrale() {
		return fileDaUnireVersIntegrale;
	}
	public void setFileDaUnireVersIntegrale(List<FileDaUnireBean> fileDaUnireVersIntegrale) {
		this.fileDaUnireVersIntegrale = fileDaUnireVersIntegrale;
	}
	public String getNomeFileUnioneVersIntegrale() {
		return nomeFileUnioneVersIntegrale;
	}
	public void setNomeFileUnioneVersIntegrale(String nomeFileUnioneVersIntegrale) {
		this.nomeFileUnioneVersIntegrale = nomeFileUnioneVersIntegrale;
	}
	public String getTipoDocFileUnioneVersIntegrale() {
		return tipoDocFileUnioneVersIntegrale;
	}
	public void setTipoDocFileUnioneVersIntegrale(String tipoDocFileUnioneVersIntegrale) {
		this.tipoDocFileUnioneVersIntegrale = tipoDocFileUnioneVersIntegrale;
	}
	public String getSiglaRegistroAtto() {
		return siglaRegistroAtto;
	}
	public void setSiglaRegistroAtto(String siglaRegistroAtto) {
		this.siglaRegistroAtto = siglaRegistroAtto;
	}
	public String getSiglaRegistroAtto2() {
		return siglaRegistroAtto2;
	}
	public void setSiglaRegistroAtto2(String siglaRegistroAtto2) {
		this.siglaRegistroAtto2 = siglaRegistroAtto2;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getNomeAttrCustomEsitoTask() {
		return nomeAttrCustomEsitoTask;
	}
	public void setNomeAttrCustomEsitoTask(String nomeAttrCustomEsitoTask) {
		this.nomeAttrCustomEsitoTask = nomeAttrCustomEsitoTask;
	}
	public Boolean getFlgReadOnly() {
		return flgReadOnly;
	}
	public void setFlgReadOnly(Boolean flgReadOnly) {
		this.flgReadOnly = flgReadOnly;
	}
	public String getIdModAssDocTask() {
		return idModAssDocTask;
	}
	public void setIdModAssDocTask(String idModAssDocTask) {
		this.idModAssDocTask = idModAssDocTask;
	}
	public String getNomeModAssDocTask() {
		return nomeModAssDocTask;
	}
	public void setNomeModAssDocTask(String nomeModAssDocTask) {
		this.nomeModAssDocTask = nomeModAssDocTask;
	}
	public String getDisplayFilenameModAssDocTask() {
		return displayFilenameModAssDocTask;
	}
	public void setDisplayFilenameModAssDocTask(String displayFilenameModAssDocTask) {
		this.displayFilenameModAssDocTask = displayFilenameModAssDocTask;
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
	public String getIdModFoglioFirme() {
		return idModFoglioFirme;
	}
	public void setIdModFoglioFirme(String idModFoglioFirme) {
		this.idModFoglioFirme = idModFoglioFirme;
	}
	public String getNomeModFoglioFirme() {
		return nomeModFoglioFirme;
	}
	public void setNomeModFoglioFirme(String nomeModFoglioFirme) {
		this.nomeModFoglioFirme = nomeModFoglioFirme;
	}
	public String getDisplayFilenameModFoglioFirme() {
		return displayFilenameModFoglioFirme;
	}
	public void setDisplayFilenameModFoglioFirme(String displayFilenameModFoglioFirme) {
		this.displayFilenameModFoglioFirme = displayFilenameModFoglioFirme;
	}	
	public String getIdModFoglioFirme2() {
		return idModFoglioFirme2;
	}
	public void setIdModFoglioFirme2(String idModFoglioFirme2) {
		this.idModFoglioFirme2 = idModFoglioFirme2;
	}
	public String getNomeModFoglioFirme2() {
		return nomeModFoglioFirme2;
	}
	public void setNomeModFoglioFirme2(String nomeModFoglioFirme2) {
		this.nomeModFoglioFirme2 = nomeModFoglioFirme2;
	}
	public String getDisplayFilenameModFoglioFirme2() {
		return displayFilenameModFoglioFirme2;
	}
	public void setDisplayFilenameModFoglioFirme2(String displayFilenameModFoglioFirme2) {
		this.displayFilenameModFoglioFirme2 = displayFilenameModFoglioFirme2;
	}
	public String getIdModSchedaTrasp() {
		return idModSchedaTrasp;
	}
	public void setIdModSchedaTrasp(String idModSchedaTrasp) {
		this.idModSchedaTrasp = idModSchedaTrasp;
	}
	public String getNomeModSchedaTrasp() {
		return nomeModSchedaTrasp;
	}
	public void setNomeModSchedaTrasp(String nomeModSchedaTrasp) {
		this.nomeModSchedaTrasp = nomeModSchedaTrasp;
	}
	public String getDisplayFilenameModSchedaTrasp() {
		return displayFilenameModSchedaTrasp;
	}
	public void setDisplayFilenameModSchedaTrasp(String displayFilenameModSchedaTrasp) {
		this.displayFilenameModSchedaTrasp = displayFilenameModSchedaTrasp;
	}
	public List<AttributiAddDocTabBean> getAttributiAddDocTabs() {
		return attributiAddDocTabs;
	}
	public void setAttributiAddDocTabs(List<AttributiAddDocTabBean> attributiAddDocTabs) {
		this.attributiAddDocTabs = attributiAddDocTabs;
	}
	public List<EsitoTaskOkBean> getEsitiTaskOk() {
		return esitiTaskOk;
	}
	public void setEsitiTaskOk(List<EsitoTaskOkBean> esitiTaskOk) {
		this.esitiTaskOk = esitiTaskOk;
	}
	public List<EsitoTaskOkBean> getEsitiTaskKo() {
		return esitiTaskKo;
	}
	public void setEsitiTaskKo(List<EsitoTaskOkBean> esitiTaskKo) {
		this.esitiTaskKo = esitiTaskKo;
	}
	public Boolean getFileUnioneDaFirmare() {
		return fileUnioneDaFirmare;
	}
	public void setFileUnioneDaFirmare(Boolean fileUnioneDaFirmare) {
		this.fileUnioneDaFirmare = fileUnioneDaFirmare;
	}
	public Boolean getFileUnioneDaTimbrare() {
		return fileUnioneDaTimbrare;
	}
	public void setFileUnioneDaTimbrare(Boolean fileUnioneDaTimbrare) {
		this.fileUnioneDaTimbrare = fileUnioneDaTimbrare;
	}
	public String getPosizioneTimbro() {
		return posizioneTimbro;
	}
	public void setPosizioneTimbro(String posizioneTimbro) {
		this.posizioneTimbro = posizioneTimbro;
	}
	public String getTipoPagina() {
		return tipoPagina;
	}
	public void setTipoPagina(String tipoPagina) {
		this.tipoPagina = tipoPagina;
	}
	public String getPaginaDa() {
		return paginaDa;
	}
	public void setPaginaDa(String paginaDa) {
		this.paginaDa = paginaDa;
	}
	public String getPaginaA() {
		return paginaA;
	}
	public void setPaginaA(String paginaA) {
		this.paginaA = paginaA;
	}
	public String getRotazioneTimbro() {
		return rotazioneTimbro;
	}
	public void setRotazioneTimbro(String rotazioneTimbro) {
		this.rotazioneTimbro = rotazioneTimbro;
	}
	public String getCodTabDefault() {
		return codTabDefault;
	}
	public void setCodTabDefault(String codTabDefault) {
		this.codTabDefault = codTabDefault;
	}
	public String getIdTipoTaskDoc() {
		return idTipoTaskDoc;
	}
	public void setIdTipoTaskDoc(String idTipoTaskDoc) {
		this.idTipoTaskDoc = idTipoTaskDoc;
	}
	public String getNomeTipoTaskDoc() {
		return nomeTipoTaskDoc;
	}
	public void setNomeTipoTaskDoc(String nomeTipoTaskDoc) {
		this.nomeTipoTaskDoc = nomeTipoTaskDoc;
	}
	public Boolean getFlgParereTaskDoc() {
		return flgParereTaskDoc;
	}
	public void setFlgParereTaskDoc(Boolean flgParereTaskDoc) {
		this.flgParereTaskDoc = flgParereTaskDoc;
	}
	public Boolean getFlgParteDispositivoTaskDoc() {
		return flgParteDispositivoTaskDoc;
	}
	public void setFlgParteDispositivoTaskDoc(Boolean flgParteDispositivoTaskDoc) {
		this.flgParteDispositivoTaskDoc = flgParteDispositivoTaskDoc;
	}
	public Boolean getFlgNoPubblTaskDoc() {
		return flgNoPubblTaskDoc;
	}
	public void setFlgNoPubblTaskDoc(Boolean flgNoPubblTaskDoc) {
		this.flgNoPubblTaskDoc = flgNoPubblTaskDoc;
	}
	public Boolean getFlgPubblicaSeparatoTaskDoc() {
		return flgPubblicaSeparatoTaskDoc;
	}
	public void setFlgPubblicaSeparatoTaskDoc(Boolean flgPubblicaSeparatoTaskDoc) {
		this.flgPubblicaSeparatoTaskDoc = flgPubblicaSeparatoTaskDoc;
	}
	public List<ControlloXEsitoTaskDocBean> getControlliXEsitiTaskDoc() {
		return controlliXEsitiTaskDoc;
	}
	public void setControlliXEsitiTaskDoc(List<ControlloXEsitoTaskDocBean> controlliXEsitiTaskDoc) {
		this.controlliXEsitiTaskDoc = controlliXEsitiTaskDoc;
	}
	public List<ValoriEsitoOutBean> getValoriEsito() {
		return valoriEsito;
	}
	public void setValoriEsito(List<ValoriEsitoOutBean> valoriEsito) {
		this.valoriEsito = valoriEsito;
	}
	public String getAttivaModificaEsclusionePubblicazione() {
		return attivaModificaEsclusionePubblicazione;
	}
	public void setAttivaModificaEsclusionePubblicazione(String attivaModificaEsclusionePubblicazione) {
		this.attivaModificaEsclusionePubblicazione = attivaModificaEsclusionePubblicazione;
	}
	public String getAttivaEliminazioneUOCoinvolte() {
		return attivaEliminazioneUOCoinvolte;
	}
	public void setAttivaEliminazioneUOCoinvolte(String attivaEliminazioneUOCoinvolte) {
		this.attivaEliminazioneUOCoinvolte = attivaEliminazioneUOCoinvolte;
	}
	public Boolean getTaskArchivio() {
		return taskArchivio;
	}
	public void setTaskArchivio(Boolean taskArchivio) {
		this.taskArchivio = taskArchivio;
	}
	public Boolean getFlgDatiSpesaEditabili() {
		return flgDatiSpesaEditabili;
	}
	public void setFlgDatiSpesaEditabili(Boolean flgDatiSpesaEditabili) {
		this.flgDatiSpesaEditabili = flgDatiSpesaEditabili;
	}
	public Boolean getFlgCIGEditabile() {
		return flgCIGEditabile;
	}
	public void setFlgCIGEditabile(Boolean flgCIGEditabile) {
		this.flgCIGEditabile = flgCIGEditabile;
	}
	public String getTipoEventoSIB() {
		return tipoEventoSIB;
	}
	public void setTipoEventoSIB(String tipoEventoSIB) {
		this.tipoEventoSIB = tipoEventoSIB;
	}
	public List<EsitoTaskOkBean> getEsitiTaskEventoSIB() {
		return esitiTaskEventoSIB;
	}
	public void setEsitiTaskEventoSIB(List<EsitoTaskOkBean> esitiTaskEventoSIB) {
		this.esitiTaskEventoSIB = esitiTaskEventoSIB;
	}
	public List<EventoXEsitoTaskBean> getTipiEventoSIBXEsitoTask() {
		return tipiEventoSIBXEsitoTask;
	}
	public void setTipiEventoSIBXEsitoTask(List<EventoXEsitoTaskBean> tipiEventoSIBXEsitoTask) {
		this.tipiEventoSIBXEsitoTask = tipiEventoSIBXEsitoTask;
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
	public List<EventoXEsitoTaskBean> getTipiEventoContabiliaXEsitoTask() {
		return tipiEventoContabiliaXEsitoTask;
	}
	public void setTipiEventoContabiliaXEsitoTask(List<EventoXEsitoTaskBean> tipiEventoContabiliaXEsitoTask) {
		this.tipiEventoContabiliaXEsitoTask = tipiEventoContabiliaXEsitoTask;
	}
	public List<EventoXEsitoTaskBean> getTipiEventoSICRAXEsitoTask() {
		return tipiEventoSICRAXEsitoTask;
	}
	public void setTipiEventoSICRAXEsitoTask(List<EventoXEsitoTaskBean> tipiEventoSICRAXEsitoTask) {
		this.tipiEventoSICRAXEsitoTask = tipiEventoSICRAXEsitoTask;
	}
	public List<EventoXEsitoTaskBean> getTipiEventoCWOLXEsitoTask() {
		return tipiEventoCWOLXEsitoTask;
	}
	public void setTipiEventoCWOLXEsitoTask(List<EventoXEsitoTaskBean> tipiEventoCWOLXEsitoTask) {
		this.tipiEventoCWOLXEsitoTask = tipiEventoCWOLXEsitoTask;
	}
	public Boolean getFlgAttivaSmistamento() {
		return flgAttivaSmistamento;
	}
	public void setFlgAttivaSmistamento(Boolean flgAttivaSmistamento) {
		this.flgAttivaSmistamento = flgAttivaSmistamento;
	}
	public String getNomeAttrListaDatiContabili() {
		return nomeAttrListaDatiContabili;
	}	
	public Boolean getFlgRecuperaMovimentiContabDefinitivi() {
		return flgRecuperaMovimentiContabDefinitivi;
	}
	public void setFlgRecuperaMovimentiContabDefinitivi(Boolean flgRecuperaMovimentiContabDefinitivi) {
		this.flgRecuperaMovimentiContabDefinitivi = flgRecuperaMovimentiContabDefinitivi;
	}
	public void setNomeAttrListaDatiContabili(String nomeAttrListaDatiContabili) {
		this.nomeAttrListaDatiContabili = nomeAttrListaDatiContabili;
	}
	public String getNomeAttrListaDatiContabiliRichiesti() {
		return nomeAttrListaDatiContabiliRichiesti;
	}
	public void setNomeAttrListaDatiContabiliRichiesti(String nomeAttrListaDatiContabiliRichiesti) {
		this.nomeAttrListaDatiContabiliRichiesti = nomeAttrListaDatiContabiliRichiesti;
	}
	public Boolean getFlgAttivaRequestMovimentiDaAMC() {
		return flgAttivaRequestMovimentiDaAMC;
	}
	public void setFlgAttivaRequestMovimentiDaAMC(Boolean flgAttivaRequestMovimentiDaAMC) {
		this.flgAttivaRequestMovimentiDaAMC = flgAttivaRequestMovimentiDaAMC;
	}
	public Boolean getFlgAttivaSalvataggioMovimentiDaAMC() {
		return flgAttivaSalvataggioMovimentiDaAMC;
	}
	public void setFlgAttivaSalvataggioMovimentiDaAMC(Boolean flgAttivaSalvataggioMovimentiDaAMC) {
		this.flgAttivaSalvataggioMovimentiDaAMC = flgAttivaSalvataggioMovimentiDaAMC;
	}
	public Boolean getFlgEscludiFiltroCdCVsAMC() {
		return flgEscludiFiltroCdCVsAMC;
	}
	public void setFlgEscludiFiltroCdCVsAMC(Boolean flgEscludiFiltroCdCVsAMC) {
		this.flgEscludiFiltroCdCVsAMC = flgEscludiFiltroCdCVsAMC;
	}
	public String getNomeVersConfronto() {
		return nomeVersConfronto;
	}
	public void setNomeVersConfronto(String nomeVersConfronto) {
		this.nomeVersConfronto = nomeVersConfronto;
	}
	public String getNroVersConfronto() {
		return nroVersConfronto;
	}
	public void setNroVersConfronto(String nroVersConfronto) {
		this.nroVersConfronto = nroVersConfronto;
	}
	public String getNroVersLavoro() {
		return nroVersLavoro;
	}
	public void setNroVersLavoro(String nroVersLavoro) {
		this.nroVersLavoro = nroVersLavoro;
	}
	public String getAzione() {
		return azione;
	}
	public void setAzione(String azione) {
		this.azione = azione;
	}
	public String getIdDocOrganigramma() {
		return idDocOrganigramma;
	}
	public void setIdDocOrganigramma(String idDocOrganigramma) {
		this.idDocOrganigramma = idDocOrganigramma;
	}
	public String getIdDocArchiviazionePdf() {
		return idDocArchiviazionePdf;
	}
	public void setIdDocArchiviazionePdf(String idDocArchiviazionePdf) {
		this.idDocArchiviazionePdf = idDocArchiviazionePdf;
	}
	public String getLivelloRevisione() {
		return livelloRevisione;
	}
	public void setLivelloRevisione(String livelloRevisione) {
		this.livelloRevisione = livelloRevisione;
	}
	public String getLivelloMaxRevisione() {
		return livelloMaxRevisione;
	}
	public void setLivelloMaxRevisione(String livelloMaxRevisione) {
		this.livelloMaxRevisione = livelloMaxRevisione;
	}
	public String getTipiUORevisione() {
		return tipiUORevisione;
	}
	public void setTipiUORevisione(String tipiUORevisione) {
		this.tipiUORevisione = tipiUORevisione;
	}
	public Boolean getFlgDelibera() {
		return flgDelibera;
	}
	public void setFlgDelibera(Boolean flgDelibera) {
		this.flgDelibera = flgDelibera;
	}
	public String getShowDirigentiProponenti() {
		return showDirigentiProponenti;
	}
	public void setShowDirigentiProponenti(String showDirigentiProponenti) {
		this.showDirigentiProponenti = showDirigentiProponenti;
	}
	public String getShowAssessori() {
		return showAssessori;
	}
	public void setShowAssessori(String showAssessori) {
		this.showAssessori = showAssessori;
	}
	public String getShowConsiglieri() {
		return showConsiglieri;
	}
	public void setShowConsiglieri(String showConsiglieri) {
		this.showConsiglieri = showConsiglieri;
	}
	public Boolean getFlgPubblicazioneAllegatiUguale() {
		return flgPubblicazioneAllegatiUguale;
	}
	public void setFlgPubblicazioneAllegatiUguale(Boolean flgPubblicazioneAllegatiUguale) {
		this.flgPubblicazioneAllegatiUguale = flgPubblicazioneAllegatiUguale;
	}
	public ParametriTipoAttoBean getParametriTipoAtto() {
		return parametriTipoAtto;
	}
	public void setParametriTipoAtto(ParametriTipoAttoBean parametriTipoAtto) {
		this.parametriTipoAtto = parametriTipoAtto;
	}
	public Boolean getFlgSoloPreparazioneVersPubblicazione() {
		return flgSoloPreparazioneVersPubblicazione;
	}
	public void setFlgSoloPreparazioneVersPubblicazione(Boolean flgSoloPreparazioneVersPubblicazione) {
		this.flgSoloPreparazioneVersPubblicazione = flgSoloPreparazioneVersPubblicazione;
	}
	public Boolean getFlgCtrlMimeTypeAllegPI() {
		return flgCtrlMimeTypeAllegPI;
	}
	public void setFlgCtrlMimeTypeAllegPI(Boolean flgCtrlMimeTypeAllegPI) {
		this.flgCtrlMimeTypeAllegPI = flgCtrlMimeTypeAllegPI;
	}
	public Boolean getFlgProtocollazioneProsa() {
		return flgProtocollazioneProsa;
	}
	public void setFlgProtocollazioneProsa(Boolean flgProtocollazioneProsa) {
		this.flgProtocollazioneProsa = flgProtocollazioneProsa;
	}
	public Boolean getFlgPresentiEmendamenti() {
		return flgPresentiEmendamenti;
	}
	public void setFlgPresentiEmendamenti(Boolean flgPresentiEmendamenti) {
		this.flgPresentiEmendamenti = flgPresentiEmendamenti;
	}
	public Boolean getFlgFirmaVersPubblAggiornata() {
		return flgFirmaVersPubblAggiornata;
	}
	public void setFlgFirmaVersPubblAggiornata(Boolean flgFirmaVersPubblAggiornata) {
		this.flgFirmaVersPubblAggiornata = flgFirmaVersPubblAggiornata;
	}
	public Boolean getAbilAggiornaStatoAttoPostDiscussione() {
		return abilAggiornaStatoAttoPostDiscussione;
	}
	public void setAbilAggiornaStatoAttoPostDiscussione(Boolean abilAggiornaStatoAttoPostDiscussione) {
		this.abilAggiornaStatoAttoPostDiscussione = abilAggiornaStatoAttoPostDiscussione;
	}
	public List<StatoDocBean> getStatiXAggAttoPostDiscussione() {
		return statiXAggAttoPostDiscussione;
	}
	public void setStatiXAggAttoPostDiscussione(List<StatoDocBean> statiXAggAttoPostDiscussione) {
		this.statiXAggAttoPostDiscussione = statiXAggAttoPostDiscussione;
	}
	public String getExportAttoInDocFmt() {
		return exportAttoInDocFmt;
	}
	public void setExportAttoInDocFmt(String exportAttoInDocFmt) {
		this.exportAttoInDocFmt = exportAttoInDocFmt;
	}
	public Boolean getFlgAvvioLiquidazioneContabilia() {
		return flgAvvioLiquidazioneContabilia;
	}
	public void setFlgAvvioLiquidazioneContabilia(Boolean flgAvvioLiquidazioneContabilia) {
		this.flgAvvioLiquidazioneContabilia = flgAvvioLiquidazioneContabilia;
	}
	public Boolean getFlgNumPropostaDiffXStruttura() {
		return flgNumPropostaDiffXStruttura;
	}
	public void setFlgNumPropostaDiffXStruttura(Boolean flgNumPropostaDiffXStruttura) {
		this.flgNumPropostaDiffXStruttura = flgNumPropostaDiffXStruttura;
	}
	public ImpostazioniUnioneFileBean getImpostazioniUnioneFile() {
		return impostazioniUnioneFile;
	}
	public void setImpostazioniUnioneFile(ImpostazioniUnioneFileBean impostazioniUnioneFile) {
		this.impostazioniUnioneFile = impostazioniUnioneFile;
	}
	public List<WarningMsgXEsitoTaskBean> getWarningMsgXEsitoTask() {
		return warningMsgXEsitoTask;
	}
	public void setWarningMsgXEsitoTask(List<WarningMsgXEsitoTaskBean> warningMsgXEsitoTask) {
		this.warningMsgXEsitoTask = warningMsgXEsitoTask;
	}
	public String getEsitoTaskDaPreimpostare() {
		return esitoTaskDaPreimpostare;
	}
	public void setEsitoTaskDaPreimpostare(String esitoTaskDaPreimpostare) {
		this.esitoTaskDaPreimpostare = esitoTaskDaPreimpostare;
	}
	public String getMsgTaskDaPreimpostare() {
		return msgTaskDaPreimpostare;
	}
	public void setMsgTaskDaPreimpostare(String msgTaskDaPreimpostare) {
		this.msgTaskDaPreimpostare = msgTaskDaPreimpostare;
	}
	public String getMsgTaskProvvisorio() {
		return msgTaskProvvisorio;
	}
	public void setMsgTaskProvvisorio(String msgTaskProvvisorio) {
		this.msgTaskProvvisorio = msgTaskProvvisorio;
	}
	public Boolean getFlgAbilToSelProponentiEstesi() {
		return flgAbilToSelProponentiEstesi;
	}
	public void setFlgAbilToSelProponentiEstesi(Boolean flgAbilToSelProponentiEstesi) {
		this.flgAbilToSelProponentiEstesi = flgAbilToSelProponentiEstesi;
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
	public Boolean getFlgSoloOmissisModProprietaFile() {
		return flgSoloOmissisModProprietaFile;
	}
	public void setFlgSoloOmissisModProprietaFile(Boolean flgSoloOmissisModProprietaFile) {
		this.flgSoloOmissisModProprietaFile = flgSoloOmissisModProprietaFile;
	}
	public Boolean getFlgDocActionsFirmaAutomatica() {
		return flgDocActionsFirmaAutomatica;
	}
	public void setFlgDocActionsFirmaAutomatica(Boolean flgDocActionsFirmaAutomatica) {
		this.flgDocActionsFirmaAutomatica = flgDocActionsFirmaAutomatica;
	}
	public String getDocActionsFirmaAutomaticaProvider() {
		return docActionsFirmaAutomaticaProvider;
	}
	public void setDocActionsFirmaAutomaticaProvider(String docActionsFirmaAutomaticaProvider) {
		this.docActionsFirmaAutomaticaProvider = docActionsFirmaAutomaticaProvider;
	}
	public String getDocActionsFirmaAutomaticaUseridFirmatario() {
		return docActionsFirmaAutomaticaUseridFirmatario;
	}
	public void setDocActionsFirmaAutomaticaUseridFirmatario(String docActionsFirmaAutomaticaUseridFirmatario) {
		this.docActionsFirmaAutomaticaUseridFirmatario = docActionsFirmaAutomaticaUseridFirmatario;
	}
	public String getDocActionsFirmaAutomaticaFirmaInDelega() {
		return docActionsFirmaAutomaticaFirmaInDelega;
	}
	public void setDocActionsFirmaAutomaticaFirmaInDelega(String docActionsFirmaAutomaticaFirmaInDelega) {
		this.docActionsFirmaAutomaticaFirmaInDelega = docActionsFirmaAutomaticaFirmaInDelega;
	}
	public String getDocActionsFirmaAutomaticaPassword() {
		return docActionsFirmaAutomaticaPassword;
	}
	public void setDocActionsFirmaAutomaticaPassword(String docActionsFirmaAutomaticaPassword) {
		this.docActionsFirmaAutomaticaPassword = docActionsFirmaAutomaticaPassword;
	}
	public List<InfoFirmaGraficaBean> getInfoFirmaGrafica() {
		return infoFirmaGrafica;
	}
	public void setInfoFirmaGrafica(List<InfoFirmaGraficaBean> infoFirmaGrafica) {
		this.infoFirmaGrafica = infoFirmaGrafica;
	}
	public String getFlgAttivitaDaNonAprireInAutomatico() {
		return flgAttivitaDaNonAprireInAutomatico;
	}
	public void setFlgAttivitaDaNonAprireInAutomatico(String flgAttivitaDaNonAprireInAutomatico) {
		this.flgAttivitaDaNonAprireInAutomatico = flgAttivitaDaNonAprireInAutomatico;
	}	
}