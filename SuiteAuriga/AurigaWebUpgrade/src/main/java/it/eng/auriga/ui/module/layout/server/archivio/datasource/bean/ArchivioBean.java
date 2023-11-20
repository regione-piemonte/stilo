/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.IstruttoreProcBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ACLBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AltraViaProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioCCBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DocPraticaPregressaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskBean;
import it.eng.document.function.bean.FlagSottoFasc;
import it.eng.document.function.bean.ValueBean;

public class ArchivioBean extends ArchivioXmlBean {

	private String desUserApertura;
	private String desUserChiusura;
	private String codice;
	private String idUdCapofila;
	private String capofila;	
	private Boolean flgFascTitolario;
	private BigDecimal annoProt;
	private BigDecimal nroProt;
	private String tipoProt;
	private String statoFasc;
	private String nomeFascicolo;
	private String nomeFolderType;
	private Integer annoFascicolo;
	private String idClassifica;
	private String indiceClassifica;
	private String nroFascicolo;
	private String nroSottofascicolo;
	private String nroInserto;
	private String folderType;
	private String idFolderType;
	private String descFolderType;
	private Date dtTermineRiservatezza;
	private String idTopografico;
	private String codRapidoTopografico;
	private String nomeTopografico;
	private String noteTopografico;
	private String descrizioneTopografico;
	private String noteFascicolo;
	private String descClassifica;
	private Boolean propagaRiservatezzaContenuti;
//	private String idUOScrivResponsabile;
//	private String typeNodo;
	private FlagSottoFasc flgSottoFascInserto;
	private String faseCorrenteProc;
	private String idDefProcFlow;
	private String idInstProcFlow;
	private String idProcess;
	private String estremiProcess;
	private String rowidFolder;		
	private List<AssegnazioneBean> listaAssegnazioni;
	private List<DestInvioCCBean> listaDestInvioCC;
	private List<ACLBean> listaACL;
	private List<TaskBean> listaTask;
	private String  flgArchivio;
	
	// Abilitazioni
	private Boolean abilModAssInviiCC;
	private Boolean abilSetVisionato;
	private Boolean abilSmista;
	private Boolean abilSmistaCC; 
	private Boolean abilAssegnazioneSmistamento;
	private Boolean abilEliminazione;
	private Boolean abilCondivisione;
	private Boolean abilModificaDati;
	private Boolean abilAvvioIterWF;
	private Boolean abilPresaInCarico;
	private Boolean abilRestituzione;
	private Boolean abilArchiviazione;
	private Boolean abilChiudiFascicolo;	
	private Boolean abilRiapriFascicolo;
	private Boolean abilVersaInArchivioStoricoFascicolo;
	private Boolean abilOsservazioniNotifiche;
	private Boolean abilRegistrazionePrelievo; 	
	private Boolean abilModificaPrelievo; 
	private Boolean abilEliminazionePrelievo; 
	private Boolean abilRestituzionePrelievo; 
	private Boolean abilStampaCopertina; 
	private Boolean abilStampaSegnatura; 
	private Boolean abilStampaListaContenuti; 
	private Boolean abilStampaEtichetta;
	private Boolean abilModificaTipologia;
	private Boolean abilModificaDatiExtraIter;
	private Boolean abilModificaOpereAtto;
	private Boolean abilModificaDatiPubblAtto;
	private Boolean abilGestioneCollegamentiFolder;
	
	
	// Istanze Autotutela / CED
//	private String tipoIstruttoreProc;
//	private String idUtenteIstruttoreProc;
//	private String nomeUtenteIstruttoreProc;
//	private String idGruppoIstruttoreProc;
//	private String codRapidoGruppoIstruttoreProc;
//	private String nomeGruppoIstruttoreProc;	
	private List<IstruttoreProcBean> listaIstruttoriProc;	
	private List<AllegatoProtocolloBean> listaDocumentiIniziali;
	private List<AllegatoProtocolloBean> listaDocumentiIstruttoria;
	private List<DocPraticaPregressaBean> listaDocumentiPraticaPregressa;
	
	private Date dataPresentazioneInstanza;
	
	// Caricamento pregresso
	private Boolean flgCaricamentoPregressoDaGUI;
	private Boolean flgDocumentazioneCompleta;
	private Boolean flgTipoFolderConVie;
	private String dictionaryEntrySezione;
	private String templateNomeFolder;
	private String numProtocolloGenerale;
	private String annoProtocolloGenerale;
	private String siglaProtocolloSettore;
	private String numProtocolloSettore;
	private String subProtocolloSettore;
	private String annoProtocolloSettore;
	private Date dtRichPraticaUrbanistica;
	private String codClassFascEdilizio;	
	private String numeroDeposito;
	private String annoDeposito;
	private String numeroEP;
	private String annoEP;
	private String numeroLicenza;
	private String annoLicenza;
	private String numProtocolloGeneraleRichPratica;
	private String annoProtocolloGeneraleRichPratica;
	private String numProtocolloGeneraleRichPraticaWF;
	private String annoProtocolloGeneraleRichPraticaWF;
	private List<ValueBean> listaEsibentiPraticaPregressa;	
	private Boolean flgUdDaDataEntryScansioni;
	private Boolean flgNextDocAllegato;
	private Boolean attivaTimbroTipologia;
	private List<AltraViaProtBean> listaAltreVie;
	
	// Pratiche pregresse - Dati archivio e prelievo
	private String datiPrelievoDaArchivioCodUO;
	private String datiPrelievoDaArchivioIdUO;
	private String datiPrelievoDaArchivioDesUO;
	private String datiPrelievoDaArchivioOrganigrammaUO;
	private String datiPrelievoDaArchivioDataPrelievo;
	private Date datiPrelievoDaArchivioDataRestituzionePrelievo;
	private String datiPrelievoDaArchivioIdUserResponsabile;
	private String datiPrelievoDaArchivioUsernameResponsabile;
	private String datiPrelievoDaArchivioCognomeResponsabile;
	private String datiPrelievoDaArchivioNomeResponsabile;
	private String datiPrelievoDaArchivioNoteRichiedente;
	private String datiPrelievoDaArchivioNoteArchivio;
	private String competenzaPratica;
	private String reperibilitaCartaceoPratica;
	private String archivioPresCartaceoPratica;
	private String classifPregressa;
	private String udc;
	private String presenzaInArchivioDeposito;
	
	// Validazione protocollo pregresso
	private String tipoProtocolloPregresso;	
	private String numProtocolloPregresso;
	private String annoProtocolloPregresso;
	private String siglaProtocolloPregresso;
	
	// Attributi dinamici
	private Map<String, Object> valori;
	private Map<String, String> tipiValori;
	
	// Descrizioni priorita e riservatezza
	private String desLivelloRiservatezza;
	private String desPriorita;
	
	private String tipoMittIntUDOut;
	private BigDecimal idMittUDOut;
	private String tipoMittUltimoInvioUDOut;
	private BigDecimal idMittUltimoInvioUDOut;
	
	// Campi per protocollaione allegati ced autotutele
	private String datiProtDocIstrMittenteId;	
	private String datiProtDocIstrDestinatarioFlgPersonaFisica;	
	private String datiProtDocIstrDestinatarioDenomCognome;
	private String datiProtDocIstrDestinatarioNome;
	private String datiProtDocIstrCodFiscale;
	private String datiProtDocIstrUOProtocollante;
	private String idUdDaProtocollare;
	private String desAllegatoDaProtocollare;		
	
	// Campi per procedimenti autotutele/ced
	private List<ProcedimentiCollegatiBean> listaProcCollegati;
	
	// Fascicoli da collegare/collegati
	private List<FascCollegatoBean> listaFascicoliDaCollegare;
	private String presenzaFascCollegati;
	
	/*Controllo per evitare salvataggi con dati/file non aggiornati*/
	private String timestampGetData;
	
	// Errori sui file
	private HashMap<String, String> erroriFile;

	public String getDesUserApertura() {
		return desUserApertura;
	}

	public void setDesUserApertura(String desUserApertura) {
		this.desUserApertura = desUserApertura;
	}

	public String getDesUserChiusura() {
		return desUserChiusura;
	}

	public void setDesUserChiusura(String desUserChiusura) {
		this.desUserChiusura = desUserChiusura;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getIdUdCapofila() {
		return idUdCapofila;
	}

	public void setIdUdCapofila(String idUdCapofila) {
		this.idUdCapofila = idUdCapofila;
	}

	public String getCapofila() {
		return capofila;
	}

	public void setCapofila(String capofila) {
		this.capofila = capofila;
	}

	public Boolean getFlgFascTitolario() {
		return flgFascTitolario;
	}

	public void setFlgFascTitolario(Boolean flgFascTitolario) {
		this.flgFascTitolario = flgFascTitolario;
	}

	public BigDecimal getAnnoProt() {
		return annoProt;
	}

	public void setAnnoProt(BigDecimal annoProt) {
		this.annoProt = annoProt;
	}

	public BigDecimal getNroProt() {
		return nroProt;
	}

	public void setNroProt(BigDecimal nroProt) {
		this.nroProt = nroProt;
	}

	public String getTipoProt() {
		return tipoProt;
	}

	public void setTipoProt(String tipoProt) {
		this.tipoProt = tipoProt;
	}

	public String getStatoFasc() {
		return statoFasc;
	}

	public void setStatoFasc(String statoFasc) {
		this.statoFasc = statoFasc;
	}

	public String getNomeFascicolo() {
		return nomeFascicolo;
	}

	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}

	public String getNomeFolderType() {
		return nomeFolderType;
	}

	public void setNomeFolderType(String nomeFolderType) {
		this.nomeFolderType = nomeFolderType;
	}

	public Integer getAnnoFascicolo() {
		return annoFascicolo;
	}

	public void setAnnoFascicolo(Integer annoFascicolo) {
		this.annoFascicolo = annoFascicolo;
	}

	public String getIdClassifica() {
		return idClassifica;
	}

	public void setIdClassifica(String idClassifica) {
		this.idClassifica = idClassifica;
	}

	public String getIndiceClassifica() {
		return indiceClassifica;
	}

	public void setIndiceClassifica(String indiceClassifica) {
		this.indiceClassifica = indiceClassifica;
	}

	public String getNroFascicolo() {
		return nroFascicolo;
	}

	public void setNroFascicolo(String nroFascicolo) {
		this.nroFascicolo = nroFascicolo;
	}

	public String getNroSottofascicolo() {
		return nroSottofascicolo;
	}

	public void setNroSottofascicolo(String nroSottofascicolo) {
		this.nroSottofascicolo = nroSottofascicolo;
	}

	public String getNroInserto() {
		return nroInserto;
	}

	public void setNroInserto(String nroInserto) {
		this.nroInserto = nroInserto;
	}

	public String getFolderType() {
		return folderType;
	}

	public void setFolderType(String folderType) {
		this.folderType = folderType;
	}

	public String getIdFolderType() {
		return idFolderType;
	}

	public void setIdFolderType(String idFolderType) {
		this.idFolderType = idFolderType;
	}

	public String getDescFolderType() {
		return descFolderType;
	}

	public void setDescFolderType(String descFolderType) {
		this.descFolderType = descFolderType;
	}

	public Date getDtTermineRiservatezza() {
		return dtTermineRiservatezza;
	}

	public void setDtTermineRiservatezza(Date dtTermineRiservatezza) {
		this.dtTermineRiservatezza = dtTermineRiservatezza;
	}

	public String getIdTopografico() {
		return idTopografico;
	}

	public void setIdTopografico(String idTopografico) {
		this.idTopografico = idTopografico;
	}

	public String getCodRapidoTopografico() {
		return codRapidoTopografico;
	}

	public void setCodRapidoTopografico(String codRapidoTopografico) {
		this.codRapidoTopografico = codRapidoTopografico;
	}

	public String getNomeTopografico() {
		return nomeTopografico;
	}

	public void setNomeTopografico(String nomeTopografico) {
		this.nomeTopografico = nomeTopografico;
	}

	public String getNoteTopografico() {
		return noteTopografico;
	}

	public void setNoteTopografico(String noteTopografico) {
		this.noteTopografico = noteTopografico;
	}

	public String getDescrizioneTopografico() {
		return descrizioneTopografico;
	}

	public void setDescrizioneTopografico(String descrizioneTopografico) {
		this.descrizioneTopografico = descrizioneTopografico;
	}

	public String getNoteFascicolo() {
		return noteFascicolo;
	}

	public void setNoteFascicolo(String noteFascicolo) {
		this.noteFascicolo = noteFascicolo;
	}

	public String getDescClassifica() {
		return descClassifica;
	}

	public void setDescClassifica(String descClassifica) {
		this.descClassifica = descClassifica;
	}

	public Boolean getPropagaRiservatezzaContenuti() {
		return propagaRiservatezzaContenuti;
	}

	public void setPropagaRiservatezzaContenuti(Boolean propagaRiservatezzaContenuti) {
		this.propagaRiservatezzaContenuti = propagaRiservatezzaContenuti;
	}

	public FlagSottoFasc getFlgSottoFascInserto() {
		return flgSottoFascInserto;
	}

	public void setFlgSottoFascInserto(FlagSottoFasc flgSottoFascInserto) {
		this.flgSottoFascInserto = flgSottoFascInserto;
	}

	public String getFaseCorrenteProc() {
		return faseCorrenteProc;
	}

	public void setFaseCorrenteProc(String faseCorrenteProc) {
		this.faseCorrenteProc = faseCorrenteProc;
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

	public String getRowidFolder() {
		return rowidFolder;
	}

	public void setRowidFolder(String rowidFolder) {
		this.rowidFolder = rowidFolder;
	}

	public List<AssegnazioneBean> getListaAssegnazioni() {
		return listaAssegnazioni;
	}

	public void setListaAssegnazioni(List<AssegnazioneBean> listaAssegnazioni) {
		this.listaAssegnazioni = listaAssegnazioni;
	}

	public List<DestInvioCCBean> getListaDestInvioCC() {
		return listaDestInvioCC;
	}

	public void setListaDestInvioCC(List<DestInvioCCBean> listaDestInvioCC) {
		this.listaDestInvioCC = listaDestInvioCC;
	}

	public List<ACLBean> getListaACL() {
		return listaACL;
	}

	public void setListaACL(List<ACLBean> listaACL) {
		this.listaACL = listaACL;
	}

	public List<TaskBean> getListaTask() {
		return listaTask;
	}

	public void setListaTask(List<TaskBean> listaTask) {
		this.listaTask = listaTask;
	}

	public String getFlgArchivio() {
		return flgArchivio;
	}

	public void setFlgArchivio(String flgArchivio) {
		this.flgArchivio = flgArchivio;
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

	public Boolean getAbilAssegnazioneSmistamento() {
		return abilAssegnazioneSmistamento;
	}

	public void setAbilAssegnazioneSmistamento(Boolean abilAssegnazioneSmistamento) {
		this.abilAssegnazioneSmistamento = abilAssegnazioneSmistamento;
	}

	public Boolean getAbilEliminazione() {
		return abilEliminazione;
	}

	public void setAbilEliminazione(Boolean abilEliminazione) {
		this.abilEliminazione = abilEliminazione;
	}

	public Boolean getAbilCondivisione() {
		return abilCondivisione;
	}

	public void setAbilCondivisione(Boolean abilCondivisione) {
		this.abilCondivisione = abilCondivisione;
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

	public Boolean getAbilArchiviazione() {
		return abilArchiviazione;
	}

	public void setAbilArchiviazione(Boolean abilArchiviazione) {
		this.abilArchiviazione = abilArchiviazione;
	}

	public Boolean getAbilChiudiFascicolo() {
		return abilChiudiFascicolo;
	}

	public void setAbilChiudiFascicolo(Boolean abilChiudiFascicolo) {
		this.abilChiudiFascicolo = abilChiudiFascicolo;
	}

	public Boolean getAbilRiapriFascicolo() {
		return abilRiapriFascicolo;
	}

	public void setAbilRiapriFascicolo(Boolean abilRiapriFascicolo) {
		this.abilRiapriFascicolo = abilRiapriFascicolo;
	}

	public Boolean getAbilVersaInArchivioStoricoFascicolo() {
		return abilVersaInArchivioStoricoFascicolo;
	}

	public void setAbilVersaInArchivioStoricoFascicolo(Boolean abilVersaInArchivioStoricoFascicolo) {
		this.abilVersaInArchivioStoricoFascicolo = abilVersaInArchivioStoricoFascicolo;
	}

	public Boolean getAbilOsservazioniNotifiche() {
		return abilOsservazioniNotifiche;
	}

	public void setAbilOsservazioniNotifiche(Boolean abilOsservazioniNotifiche) {
		this.abilOsservazioniNotifiche = abilOsservazioniNotifiche;
	}

	public Boolean getAbilRegistrazionePrelievo() {
		return abilRegistrazionePrelievo;
	}

	public void setAbilRegistrazionePrelievo(Boolean abilRegistrazionePrelievo) {
		this.abilRegistrazionePrelievo = abilRegistrazionePrelievo;
	}

	public Boolean getAbilModificaPrelievo() {
		return abilModificaPrelievo;
	}

	public void setAbilModificaPrelievo(Boolean abilModificaPrelievo) {
		this.abilModificaPrelievo = abilModificaPrelievo;
	}

	public Boolean getAbilEliminazionePrelievo() {
		return abilEliminazionePrelievo;
	}

	public void setAbilEliminazionePrelievo(Boolean abilEliminazionePrelievo) {
		this.abilEliminazionePrelievo = abilEliminazionePrelievo;
	}

	public Boolean getAbilRestituzionePrelievo() {
		return abilRestituzionePrelievo;
	}

	public void setAbilRestituzionePrelievo(Boolean abilRestituzionePrelievo) {
		this.abilRestituzionePrelievo = abilRestituzionePrelievo;
	}

	public Boolean getAbilStampaCopertina() {
		return abilStampaCopertina;
	}

	public void setAbilStampaCopertina(Boolean abilStampaCopertina) {
		this.abilStampaCopertina = abilStampaCopertina;
	}

	public Boolean getAbilStampaSegnatura() {
		return abilStampaSegnatura;
	}

	public void setAbilStampaSegnatura(Boolean abilStampaSegnatura) {
		this.abilStampaSegnatura = abilStampaSegnatura;
	}

	public Boolean getAbilStampaListaContenuti() {
		return abilStampaListaContenuti;
	}

	public void setAbilStampaListaContenuti(Boolean abilStampaListaContenuti) {
		this.abilStampaListaContenuti = abilStampaListaContenuti;
	}

	public Boolean getAbilStampaEtichetta() {
		return abilStampaEtichetta;
	}

	public void setAbilStampaEtichetta(Boolean abilStampaEtichetta) {
		this.abilStampaEtichetta = abilStampaEtichetta;
	}

	public Boolean getAbilModificaTipologia() {
		return abilModificaTipologia;
	}

	public void setAbilModificaTipologia(Boolean abilModificaTipologia) {
		this.abilModificaTipologia = abilModificaTipologia;
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

	public Boolean getAbilGestioneCollegamentiFolder() {
		return abilGestioneCollegamentiFolder;
	}

	public void setAbilGestioneCollegamentiFolder(Boolean abilGestioneCollegamentiFolder) {
		this.abilGestioneCollegamentiFolder = abilGestioneCollegamentiFolder;
	}

	public List<IstruttoreProcBean> getListaIstruttoriProc() {
		return listaIstruttoriProc;
	}

	public void setListaIstruttoriProc(List<IstruttoreProcBean> listaIstruttoriProc) {
		this.listaIstruttoriProc = listaIstruttoriProc;
	}

	public List<AllegatoProtocolloBean> getListaDocumentiIniziali() {
		return listaDocumentiIniziali;
	}

	public void setListaDocumentiIniziali(List<AllegatoProtocolloBean> listaDocumentiIniziali) {
		this.listaDocumentiIniziali = listaDocumentiIniziali;
	}

	public List<AllegatoProtocolloBean> getListaDocumentiIstruttoria() {
		return listaDocumentiIstruttoria;
	}

	public void setListaDocumentiIstruttoria(List<AllegatoProtocolloBean> listaDocumentiIstruttoria) {
		this.listaDocumentiIstruttoria = listaDocumentiIstruttoria;
	}

	public List<DocPraticaPregressaBean> getListaDocumentiPraticaPregressa() {
		return listaDocumentiPraticaPregressa;
	}

	public void setListaDocumentiPraticaPregressa(List<DocPraticaPregressaBean> listaDocumentiPraticaPregressa) {
		this.listaDocumentiPraticaPregressa = listaDocumentiPraticaPregressa;
	}

	public Date getDataPresentazioneInstanza() {
		return dataPresentazioneInstanza;
	}

	public void setDataPresentazioneInstanza(Date dataPresentazioneInstanza) {
		this.dataPresentazioneInstanza = dataPresentazioneInstanza;
	}

	public Boolean getFlgCaricamentoPregressoDaGUI() {
		return flgCaricamentoPregressoDaGUI;
	}

	public void setFlgCaricamentoPregressoDaGUI(Boolean flgCaricamentoPregressoDaGUI) {
		this.flgCaricamentoPregressoDaGUI = flgCaricamentoPregressoDaGUI;
	}

	public Boolean getFlgDocumentazioneCompleta() {
		return flgDocumentazioneCompleta;
	}

	public void setFlgDocumentazioneCompleta(Boolean flgDocumentazioneCompleta) {
		this.flgDocumentazioneCompleta = flgDocumentazioneCompleta;
	}

	public Boolean getFlgTipoFolderConVie() {
		return flgTipoFolderConVie;
	}

	public void setFlgTipoFolderConVie(Boolean flgTipoFolderConVie) {
		this.flgTipoFolderConVie = flgTipoFolderConVie;
	}

	public String getDictionaryEntrySezione() {
		return dictionaryEntrySezione;
	}

	public void setDictionaryEntrySezione(String dictionaryEntrySezione) {
		this.dictionaryEntrySezione = dictionaryEntrySezione;
	}

	public String getTemplateNomeFolder() {
		return templateNomeFolder;
	}

	public void setTemplateNomeFolder(String templateNomeFolder) {
		this.templateNomeFolder = templateNomeFolder;
	}

	public String getNumProtocolloGenerale() {
		return numProtocolloGenerale;
	}

	public void setNumProtocolloGenerale(String numProtocolloGenerale) {
		this.numProtocolloGenerale = numProtocolloGenerale;
	}

	public String getAnnoProtocolloGenerale() {
		return annoProtocolloGenerale;
	}

	public void setAnnoProtocolloGenerale(String annoProtocolloGenerale) {
		this.annoProtocolloGenerale = annoProtocolloGenerale;
	}

	public String getSiglaProtocolloSettore() {
		return siglaProtocolloSettore;
	}

	public void setSiglaProtocolloSettore(String siglaProtocolloSettore) {
		this.siglaProtocolloSettore = siglaProtocolloSettore;
	}

	public String getNumProtocolloSettore() {
		return numProtocolloSettore;
	}

	public void setNumProtocolloSettore(String numProtocolloSettore) {
		this.numProtocolloSettore = numProtocolloSettore;
	}

	public String getSubProtocolloSettore() {
		return subProtocolloSettore;
	}

	public void setSubProtocolloSettore(String subProtocolloSettore) {
		this.subProtocolloSettore = subProtocolloSettore;
	}

	public String getAnnoProtocolloSettore() {
		return annoProtocolloSettore;
	}

	public void setAnnoProtocolloSettore(String annoProtocolloSettore) {
		this.annoProtocolloSettore = annoProtocolloSettore;
	}

	public Date getDtRichPraticaUrbanistica() {
		return dtRichPraticaUrbanistica;
	}

	public void setDtRichPraticaUrbanistica(Date dtRichPraticaUrbanistica) {
		this.dtRichPraticaUrbanistica = dtRichPraticaUrbanistica;
	}

	public String getCodClassFascEdilizio() {
		return codClassFascEdilizio;
	}

	public void setCodClassFascEdilizio(String codClassFascEdilizio) {
		this.codClassFascEdilizio = codClassFascEdilizio;
	}

	public String getNumeroDeposito() {
		return numeroDeposito;
	}

	public void setNumeroDeposito(String numeroDeposito) {
		this.numeroDeposito = numeroDeposito;
	}

	public String getAnnoDeposito() {
		return annoDeposito;
	}

	public void setAnnoDeposito(String annoDeposito) {
		this.annoDeposito = annoDeposito;
	}

	public String getNumeroEP() {
		return numeroEP;
	}

	public void setNumeroEP(String numeroEP) {
		this.numeroEP = numeroEP;
	}

	public String getAnnoEP() {
		return annoEP;
	}

	public void setAnnoEP(String annoEP) {
		this.annoEP = annoEP;
	}

	public String getNumeroLicenza() {
		return numeroLicenza;
	}

	public void setNumeroLicenza(String numeroLicenza) {
		this.numeroLicenza = numeroLicenza;
	}

	public String getAnnoLicenza() {
		return annoLicenza;
	}

	public void setAnnoLicenza(String annoLicenza) {
		this.annoLicenza = annoLicenza;
	}

	public String getNumProtocolloGeneraleRichPratica() {
		return numProtocolloGeneraleRichPratica;
	}

	public void setNumProtocolloGeneraleRichPratica(String numProtocolloGeneraleRichPratica) {
		this.numProtocolloGeneraleRichPratica = numProtocolloGeneraleRichPratica;
	}

	public String getAnnoProtocolloGeneraleRichPratica() {
		return annoProtocolloGeneraleRichPratica;
	}

	public void setAnnoProtocolloGeneraleRichPratica(String annoProtocolloGeneraleRichPratica) {
		this.annoProtocolloGeneraleRichPratica = annoProtocolloGeneraleRichPratica;
	}

	public String getNumProtocolloGeneraleRichPraticaWF() {
		return numProtocolloGeneraleRichPraticaWF;
	}

	public void setNumProtocolloGeneraleRichPraticaWF(String numProtocolloGeneraleRichPraticaWF) {
		this.numProtocolloGeneraleRichPraticaWF = numProtocolloGeneraleRichPraticaWF;
	}

	public String getAnnoProtocolloGeneraleRichPraticaWF() {
		return annoProtocolloGeneraleRichPraticaWF;
	}

	public void setAnnoProtocolloGeneraleRichPraticaWF(String annoProtocolloGeneraleRichPraticaWF) {
		this.annoProtocolloGeneraleRichPraticaWF = annoProtocolloGeneraleRichPraticaWF;
	}

	public List<ValueBean> getListaEsibentiPraticaPregressa() {
		return listaEsibentiPraticaPregressa;
	}

	public void setListaEsibentiPraticaPregressa(List<ValueBean> listaEsibentiPraticaPregressa) {
		this.listaEsibentiPraticaPregressa = listaEsibentiPraticaPregressa;
	}

	public Boolean getFlgUdDaDataEntryScansioni() {
		return flgUdDaDataEntryScansioni;
	}

	public void setFlgUdDaDataEntryScansioni(Boolean flgUdDaDataEntryScansioni) {
		this.flgUdDaDataEntryScansioni = flgUdDaDataEntryScansioni;
	}

	public Boolean getFlgNextDocAllegato() {
		return flgNextDocAllegato;
	}

	public void setFlgNextDocAllegato(Boolean flgNextDocAllegato) {
		this.flgNextDocAllegato = flgNextDocAllegato;
	}

	public Boolean getAttivaTimbroTipologia() {
		return attivaTimbroTipologia;
	}

	public void setAttivaTimbroTipologia(Boolean attivaTimbroTipologia) {
		this.attivaTimbroTipologia = attivaTimbroTipologia;
	}

	public List<AltraViaProtBean> getListaAltreVie() {
		return listaAltreVie;
	}

	public void setListaAltreVie(List<AltraViaProtBean> listaAltreVie) {
		this.listaAltreVie = listaAltreVie;
	}

	public String getDatiPrelievoDaArchivioCodUO() {
		return datiPrelievoDaArchivioCodUO;
	}

	public void setDatiPrelievoDaArchivioCodUO(String datiPrelievoDaArchivioCodUO) {
		this.datiPrelievoDaArchivioCodUO = datiPrelievoDaArchivioCodUO;
	}

	public String getDatiPrelievoDaArchivioIdUO() {
		return datiPrelievoDaArchivioIdUO;
	}

	public void setDatiPrelievoDaArchivioIdUO(String datiPrelievoDaArchivioIdUO) {
		this.datiPrelievoDaArchivioIdUO = datiPrelievoDaArchivioIdUO;
	}

	public String getDatiPrelievoDaArchivioDesUO() {
		return datiPrelievoDaArchivioDesUO;
	}

	public void setDatiPrelievoDaArchivioDesUO(String datiPrelievoDaArchivioDesUO) {
		this.datiPrelievoDaArchivioDesUO = datiPrelievoDaArchivioDesUO;
	}

	public String getDatiPrelievoDaArchivioOrganigrammaUO() {
		return datiPrelievoDaArchivioOrganigrammaUO;
	}

	public void setDatiPrelievoDaArchivioOrganigrammaUO(String datiPrelievoDaArchivioOrganigrammaUO) {
		this.datiPrelievoDaArchivioOrganigrammaUO = datiPrelievoDaArchivioOrganigrammaUO;
	}

	public String getDatiPrelievoDaArchivioDataPrelievo() {
		return datiPrelievoDaArchivioDataPrelievo;
	}

	public void setDatiPrelievoDaArchivioDataPrelievo(String datiPrelievoDaArchivioDataPrelievo) {
		this.datiPrelievoDaArchivioDataPrelievo = datiPrelievoDaArchivioDataPrelievo;
	}

	public Date getDatiPrelievoDaArchivioDataRestituzionePrelievo() {
		return datiPrelievoDaArchivioDataRestituzionePrelievo;
	}

	public void setDatiPrelievoDaArchivioDataRestituzionePrelievo(Date datiPrelievoDaArchivioDataRestituzionePrelievo) {
		this.datiPrelievoDaArchivioDataRestituzionePrelievo = datiPrelievoDaArchivioDataRestituzionePrelievo;
	}

	public String getDatiPrelievoDaArchivioIdUserResponsabile() {
		return datiPrelievoDaArchivioIdUserResponsabile;
	}

	public void setDatiPrelievoDaArchivioIdUserResponsabile(String datiPrelievoDaArchivioIdUserResponsabile) {
		this.datiPrelievoDaArchivioIdUserResponsabile = datiPrelievoDaArchivioIdUserResponsabile;
	}

	public String getDatiPrelievoDaArchivioUsernameResponsabile() {
		return datiPrelievoDaArchivioUsernameResponsabile;
	}

	public void setDatiPrelievoDaArchivioUsernameResponsabile(String datiPrelievoDaArchivioUsernameResponsabile) {
		this.datiPrelievoDaArchivioUsernameResponsabile = datiPrelievoDaArchivioUsernameResponsabile;
	}

	public String getDatiPrelievoDaArchivioCognomeResponsabile() {
		return datiPrelievoDaArchivioCognomeResponsabile;
	}

	public void setDatiPrelievoDaArchivioCognomeResponsabile(String datiPrelievoDaArchivioCognomeResponsabile) {
		this.datiPrelievoDaArchivioCognomeResponsabile = datiPrelievoDaArchivioCognomeResponsabile;
	}

	public String getDatiPrelievoDaArchivioNomeResponsabile() {
		return datiPrelievoDaArchivioNomeResponsabile;
	}

	public void setDatiPrelievoDaArchivioNomeResponsabile(String datiPrelievoDaArchivioNomeResponsabile) {
		this.datiPrelievoDaArchivioNomeResponsabile = datiPrelievoDaArchivioNomeResponsabile;
	}

	public String getDatiPrelievoDaArchivioNoteRichiedente() {
		return datiPrelievoDaArchivioNoteRichiedente;
	}

	public void setDatiPrelievoDaArchivioNoteRichiedente(String datiPrelievoDaArchivioNoteRichiedente) {
		this.datiPrelievoDaArchivioNoteRichiedente = datiPrelievoDaArchivioNoteRichiedente;
	}

	public String getDatiPrelievoDaArchivioNoteArchivio() {
		return datiPrelievoDaArchivioNoteArchivio;
	}

	public void setDatiPrelievoDaArchivioNoteArchivio(String datiPrelievoDaArchivioNoteArchivio) {
		this.datiPrelievoDaArchivioNoteArchivio = datiPrelievoDaArchivioNoteArchivio;
	}

	public String getCompetenzaPratica() {
		return competenzaPratica;
	}

	public void setCompetenzaPratica(String competenzaPratica) {
		this.competenzaPratica = competenzaPratica;
	}

	public String getReperibilitaCartaceoPratica() {
		return reperibilitaCartaceoPratica;
	}

	public void setReperibilitaCartaceoPratica(String reperibilitaCartaceoPratica) {
		this.reperibilitaCartaceoPratica = reperibilitaCartaceoPratica;
	}

	public String getArchivioPresCartaceoPratica() {
		return archivioPresCartaceoPratica;
	}

	public void setArchivioPresCartaceoPratica(String archivioPresCartaceoPratica) {
		this.archivioPresCartaceoPratica = archivioPresCartaceoPratica;
	}

	public String getClassifPregressa() {
		return classifPregressa;
	}

	public void setClassifPregressa(String classifPregressa) {
		this.classifPregressa = classifPregressa;
	}

	public String getUdc() {
		return udc;
	}

	public void setUdc(String udc) {
		this.udc = udc;
	}

	public String getPresenzaInArchivioDeposito() {
		return presenzaInArchivioDeposito;
	}

	public void setPresenzaInArchivioDeposito(String presenzaInArchivioDeposito) {
		this.presenzaInArchivioDeposito = presenzaInArchivioDeposito;
	}

	public String getTipoProtocolloPregresso() {
		return tipoProtocolloPregresso;
	}

	public void setTipoProtocolloPregresso(String tipoProtocolloPregresso) {
		this.tipoProtocolloPregresso = tipoProtocolloPregresso;
	}

	public String getNumProtocolloPregresso() {
		return numProtocolloPregresso;
	}

	public void setNumProtocolloPregresso(String numProtocolloPregresso) {
		this.numProtocolloPregresso = numProtocolloPregresso;
	}

	public String getAnnoProtocolloPregresso() {
		return annoProtocolloPregresso;
	}

	public void setAnnoProtocolloPregresso(String annoProtocolloPregresso) {
		this.annoProtocolloPregresso = annoProtocolloPregresso;
	}

	public String getSiglaProtocolloPregresso() {
		return siglaProtocolloPregresso;
	}

	public void setSiglaProtocolloPregresso(String siglaProtocolloPregresso) {
		this.siglaProtocolloPregresso = siglaProtocolloPregresso;
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

	public String getDesLivelloRiservatezza() {
		return desLivelloRiservatezza;
	}

	public void setDesLivelloRiservatezza(String desLivelloRiservatezza) {
		this.desLivelloRiservatezza = desLivelloRiservatezza;
	}

	public String getDesPriorita() {
		return desPriorita;
	}

	public void setDesPriorita(String desPriorita) {
		this.desPriorita = desPriorita;
	}

	public String getTipoMittIntUDOut() {
		return tipoMittIntUDOut;
	}

	public void setTipoMittIntUDOut(String tipoMittIntUDOut) {
		this.tipoMittIntUDOut = tipoMittIntUDOut;
	}

	public BigDecimal getIdMittUDOut() {
		return idMittUDOut;
	}

	public void setIdMittUDOut(BigDecimal idMittUDOut) {
		this.idMittUDOut = idMittUDOut;
	}

	public String getTipoMittUltimoInvioUDOut() {
		return tipoMittUltimoInvioUDOut;
	}

	public void setTipoMittUltimoInvioUDOut(String tipoMittUltimoInvioUDOut) {
		this.tipoMittUltimoInvioUDOut = tipoMittUltimoInvioUDOut;
	}

	public BigDecimal getIdMittUltimoInvioUDOut() {
		return idMittUltimoInvioUDOut;
	}

	public void setIdMittUltimoInvioUDOut(BigDecimal idMittUltimoInvioUDOut) {
		this.idMittUltimoInvioUDOut = idMittUltimoInvioUDOut;
	}

	public String getDatiProtDocIstrMittenteId() {
		return datiProtDocIstrMittenteId;
	}

	public void setDatiProtDocIstrMittenteId(String datiProtDocIstrMittenteId) {
		this.datiProtDocIstrMittenteId = datiProtDocIstrMittenteId;
	}

	public String getDatiProtDocIstrDestinatarioFlgPersonaFisica() {
		return datiProtDocIstrDestinatarioFlgPersonaFisica;
	}

	public void setDatiProtDocIstrDestinatarioFlgPersonaFisica(String datiProtDocIstrDestinatarioFlgPersonaFisica) {
		this.datiProtDocIstrDestinatarioFlgPersonaFisica = datiProtDocIstrDestinatarioFlgPersonaFisica;
	}

	public String getDatiProtDocIstrDestinatarioDenomCognome() {
		return datiProtDocIstrDestinatarioDenomCognome;
	}

	public void setDatiProtDocIstrDestinatarioDenomCognome(String datiProtDocIstrDestinatarioDenomCognome) {
		this.datiProtDocIstrDestinatarioDenomCognome = datiProtDocIstrDestinatarioDenomCognome;
	}

	public String getDatiProtDocIstrDestinatarioNome() {
		return datiProtDocIstrDestinatarioNome;
	}

	public void setDatiProtDocIstrDestinatarioNome(String datiProtDocIstrDestinatarioNome) {
		this.datiProtDocIstrDestinatarioNome = datiProtDocIstrDestinatarioNome;
	}

	public String getDatiProtDocIstrCodFiscale() {
		return datiProtDocIstrCodFiscale;
	}

	public void setDatiProtDocIstrCodFiscale(String datiProtDocIstrCodFiscale) {
		this.datiProtDocIstrCodFiscale = datiProtDocIstrCodFiscale;
	}

	public String getDatiProtDocIstrUOProtocollante() {
		return datiProtDocIstrUOProtocollante;
	}

	public void setDatiProtDocIstrUOProtocollante(String datiProtDocIstrUOProtocollante) {
		this.datiProtDocIstrUOProtocollante = datiProtDocIstrUOProtocollante;
	}

	public String getIdUdDaProtocollare() {
		return idUdDaProtocollare;
	}

	public void setIdUdDaProtocollare(String idUdDaProtocollare) {
		this.idUdDaProtocollare = idUdDaProtocollare;
	}

	public String getDesAllegatoDaProtocollare() {
		return desAllegatoDaProtocollare;
	}

	public void setDesAllegatoDaProtocollare(String desAllegatoDaProtocollare) {
		this.desAllegatoDaProtocollare = desAllegatoDaProtocollare;
	}

	public List<ProcedimentiCollegatiBean> getListaProcCollegati() {
		return listaProcCollegati;
	}

	public void setListaProcCollegati(List<ProcedimentiCollegatiBean> listaProcCollegati) {
		this.listaProcCollegati = listaProcCollegati;
	}

	public List<FascCollegatoBean> getListaFascicoliDaCollegare() {
		return listaFascicoliDaCollegare;
	}

	public void setListaFascicoliDaCollegare(List<FascCollegatoBean> listaFascicoliDaCollegare) {
		this.listaFascicoliDaCollegare = listaFascicoliDaCollegare;
	}

	public String getPresenzaFascCollegati() {
		return presenzaFascCollegati;
	}

	public void setPresenzaFascCollegati(String presenzaFascCollegati) {
		this.presenzaFascCollegati = presenzaFascCollegati;
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