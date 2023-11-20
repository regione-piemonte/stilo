/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class XmlFascicoloOut implements Serializable {
	
	@XmlVariabile(nome="#FlgFascTitolario", tipo = TipoVariabile.SEMPLICE)
	private Flag flgFascTitolario = Flag.SETTED;
	
	@XmlVariabile(nome="#FlgSottoFascInserto", tipo = TipoVariabile.SEMPLICE)
	private FlagSottoFasc flgSottoFascInserto;
	
	@XmlVariabile(nome="#IdFolderType", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idFolderType;
	
	@XmlVariabile(nome="#NomeFolderType", tipo = TipoVariabile.SEMPLICE)
	private String nomeFolderType;
	
	@XmlVariabile(nome="#NomeFolder", tipo = TipoVariabile.SEMPLICE)
	private String nomeFolder;
	
	@XmlVariabile(nome="#IdClassificazione", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idClassificazione;
	
	@XmlVariabile(nome="#NroSecondario", tipo = TipoVariabile.SEMPLICE)
	private String nroSecondario; 
	
	@XmlVariabile(nome="#IdUDCapofila", tipo = TipoVariabile.SEMPLICE)
	private String idUdCapofila; 
	
	@XmlVariabile(nome="#IdDocCapofila", tipo = TipoVariabile.SEMPLICE)
	private String idDocCapofila;
	
	@XmlVariabile(nome="#FlgUdCapofilaDaDataEntryScansioni", tipo = TipoVariabile.SEMPLICE)
	private Flag flgUdCapofilaDaDataEntryScansioni;
	
	@XmlVariabile(nome="#EstremiDocCapofila", tipo = TipoVariabile.SEMPLICE)
	private String estremiDocCapofila; 
	
	@XmlVariabile(nome="#DesOgg", tipo = TipoVariabile.SEMPLICE)
	private String desOgg; 
	
	@XmlVariabile(nome="#IdUOResponsabile", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idUOResponsabile; 
	
	@XmlVariabile(nome="#IdScrivResponsabile", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idScrivResponsabile; 
	
	@XmlVariabile(nome="#FlgArchivio", tipo = TipoVariabile.SEMPLICE)
	private FlagArchivio flgArchivio = FlagArchivio.ARCHIVIO;
	
	@XmlVariabile(nome="#LivRiservatezza", tipo = TipoVariabile.SEMPLICE)
	private String livRiservatezza;
	
	@XmlVariabile(nome="#DesLivRiservatezza", tipo = TipoVariabile.SEMPLICE)
	private String desLivRiservatezza;
	
	@XmlVariabile(nome="#DtTermineRiservatezza", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dtTermineRiservatezza; 
	
	@XmlVariabile(nome="#LivEvidenza", tipo = TipoVariabile.SEMPLICE)
	private String livEvidenza; 
	
	@XmlVariabile(nome="#DesLivEvidenza", tipo = TipoVariabile.SEMPLICE)
	private String desLivEvidenza; 
	
	@XmlVariabile(nome="#CollocazioneFisica", tipo = TipoVariabile.NESTED)
	private CollocazioneFisicaFascicoloBean collocazioneFisica; 
	
	@XmlVariabile(nome="#Note", tipo = TipoVariabile.SEMPLICE)
	private String note;

	@XmlVariabile(nome="#Segnatura", tipo=TipoVariabile.NESTED)
	private SegnaturaFascicoloOut segnatura;

	@XmlVariabile(nome="#Abilitazioni", tipo=TipoVariabile.NESTED)
	private AbilitazioniFascicoloOut abilitazioni;

	@XmlVariabile(nome="#TsApertura", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsApertura;

	@XmlVariabile(nome="#TsChiusura", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsChiusura;

	@XmlVariabile(nome="#IdFolderApp", tipo=TipoVariabile.SEMPLICE)
	private BigDecimal idFolderApp;
	
	@XmlVariabile(nome="#PathFolderApp", tipo=TipoVariabile.SEMPLICE)	
	private String pathFolderApp;

	@XmlVariabile(nome="#DesUserApertura", tipo=TipoVariabile.SEMPLICE)
	private String desUserApertura;
	
	@XmlVariabile(nome="#DesUserChiusura", tipo=TipoVariabile.SEMPLICE)
	private String desUserChiusura;
	
	@XmlVariabile(nome="#@Assegnatari", tipo=TipoVariabile.LISTA)
	private List<AssegnatariOutBean> assegnatari;
	
	@XmlVariabile(nome="#@DestInvioCC", tipo=TipoVariabile.LISTA)
	private List<DestInvioCCOutBean> destInvioCC;
	
	@XmlVariabile(nome="#@ACL", tipo = TipoVariabile.LISTA)
	private List<ACLFolderBean> listaACL; 		
	
	@XmlVariabile(nome="#IdLibrary", tipo=TipoVariabile.SEMPLICE)
	private String idLibrary;
	
	@XmlVariabile(nome="#FaseCorrenteProc", tipo=TipoVariabile.SEMPLICE)
	private String faseCorrenteProc;
	
	@XmlVariabile(nome="#@Task", tipo = TipoVariabile.LISTA)
	private List<TaskOutBean> listaTask; 
	
	@XmlVariabile(nome="#IdDefProcFlow", tipo=TipoVariabile.SEMPLICE)
	private String idDefProcFlow;
	
	@XmlVariabile(nome="#IdInstProcFlow", tipo=TipoVariabile.SEMPLICE)
	private String idInstProcFlow;	
	
	@XmlVariabile(nome="#IdProcess", tipo=TipoVariabile.SEMPLICE)
	private String idProcess;		
	
	@XmlVariabile(nome="#EstremiProcess", tipo=TipoVariabile.SEMPLICE)
	private String estremiProcess;
	
	@XmlVariabile(nome="#NriLivClassificazione", tipo = TipoVariabile.SEMPLICE)
	private String nriLivClassificazione;	
	
	@XmlVariabile(nome="#DesClassificazione", tipo = TipoVariabile.SEMPLICE)
	private String desClassificazione;	
	
	@XmlVariabile(nome="#NroFasc", tipo = TipoVariabile.SEMPLICE)
	private String nroFasc;	
		
	@XmlVariabile(nome="#NroSottofasc", tipo = TipoVariabile.SEMPLICE)
	private String nroSottofasc;	
	
	@XmlVariabile(nome="#NroInserto", tipo = TipoVariabile.SEMPLICE)
	private String nroInserto;	
	
	@XmlVariabile(nome="#FlgEreditaPermessi", tipo = TipoVariabile.SEMPLICE)
	private String flgEreditaPermessi;		
	
	@XmlVariabile(nome="RowidFolder", tipo = TipoVariabile.SEMPLICE)
	private String rowidFolder;		
		
	@XmlVariabile(nome="#IstruttoreProc.Tipo", tipo = TipoVariabile.SEMPLICE)
	private String tipoIstruttoreProc;
	
	@XmlVariabile(nome="#IstruttoreProc.Id", tipo = TipoVariabile.SEMPLICE)
	private String idIstruttoreProc;
	
	@XmlVariabile(nome="#IstruttoreProc.CodRapido", tipo = TipoVariabile.SEMPLICE)
	private String codRapidoIstruttoreProc;	
	
	@XmlVariabile(nome="#IstruttoreProc.Nome", tipo = TipoVariabile.SEMPLICE)
	private String nomeIstruttoreProc;
	
	@XmlVariabile(nome="#@DocFasc_Iniziali", tipo = TipoVariabile.LISTA)
	private List<AllegatiOutBean> listaDocumentiIniziali;		
	
	@XmlVariabile(nome="#@DocFasc_Istruttoria", tipo = TipoVariabile.LISTA)
	private List<AllegatiOutBean> listaDocumentiIstruttoria;		
	
	@XmlVariabile(nome="#FlgCaricamentoPregressoDaGUI", tipo = TipoVariabile.SEMPLICE)
	private Flag flgCaricamentoPregressoDaGUI;
	
	@XmlVariabile(nome="#FlgTipoFolderConVie", tipo = TipoVariabile.SEMPLICE)
	private Flag flgTipoFolderConVie;
	
	@XmlVariabile(nome="#DictionaryEntrySezione", tipo = TipoVariabile.SEMPLICE)
	private String dictionaryEntrySezione;
	
	@XmlVariabile(nome="#TemplateNomeFolder", tipo = TipoVariabile.SEMPLICE)
	private String templateNomeFolder;	
	
	@XmlVariabile(nome = "#@AltreUbicazioni", tipo = TipoVariabile.LISTA)
	private List<AltreUbicazioniBean> altreUbicazioni;
	
	@XmlVariabile(nome = "#AttivaTimbroTipologia", tipo = TipoVariabile.SEMPLICE)
	private Flag attivaTimbroTipologia;
	
	@XmlVariabile(nome = "#DatiPrelievoDaArchivio.IdUO", tipo = TipoVariabile.SEMPLICE)
	private String datiPrelievoDaArchivioIdUO;
	
	@XmlVariabile(nome = "#DatiPrelievoDaArchivio.CodUO", tipo = TipoVariabile.SEMPLICE)
	private String datiPrelievoDaArchivioCodUO;
	
	@XmlVariabile(nome = "#DatiPrelievoDaArchivio.DesUO", tipo = TipoVariabile.SEMPLICE)
	private String datiPrelievoDaArchivioDesUO;
	
	@XmlVariabile(nome = "#DatiPrelievoDaArchivio.DataPrelievo", tipo = TipoVariabile.SEMPLICE)
	private String datiPrelievoDaArchivioDataPrelievo;
	
	@XmlVariabile(nome = "#DatiPrelievoDaArchivio.IdUserResponsabile", tipo = TipoVariabile.SEMPLICE)
	private String datiPrelievoDaArchivioIdUserResponsabile;
	
	@XmlVariabile(nome = "#DatiPrelievoDaArchivio.UsernameResponsabile", tipo = TipoVariabile.SEMPLICE)
	private String datiPrelievoDaArchivioUsernameResponsabile;
	
	@XmlVariabile(nome = "#DatiPrelievoDaArchivio.CognomeResponsabile", tipo = TipoVariabile.SEMPLICE)
	private String datiPrelievoDaArchivioCognomeResponsabile;
	
	@XmlVariabile(nome = "#DatiPrelievoDaArchivio.NomeResponsabile", tipo = TipoVariabile.SEMPLICE)
	private String datiPrelievoDaArchivioNomeResponsabile;
	
	@XmlVariabile(nome = "#DatiPrelievoDaArchivio.NoteRichiedente", tipo = TipoVariabile.SEMPLICE)
	private String datiPrelievoDaArchivioNoteRichiedente;
	
	@XmlVariabile(nome = "#DatiPrelievoDaArchivio.NoteArchivio", tipo = TipoVariabile.SEMPLICE)
	private String datiPrelievoDaArchivioNoteArchivio;
	
	@XmlVariabile(nome = "COMPETENZA_PRATICA", tipo = TipoVariabile.SEMPLICE)
	private String competenzaPratica;
	
	@XmlVariabile(nome = "REPERIBILITA_CARTACEO_PRATICA", tipo = TipoVariabile.SEMPLICE)
	private String reperibilitaCartaceoPratica;
	
	@XmlVariabile(nome = "ARCHIVIO_PRES_CARTACEO_PRATICA", tipo = TipoVariabile.SEMPLICE)
	private String archivioPresCartaceoPratica;
	
	@XmlVariabile(nome="FLG_DOCUMENTAZIONE_COMPLETA", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDocumentazioneCompleta;
	
	@XmlVariabile(nome="PG_CAPOFILA_NRO", tipo = TipoVariabile.SEMPLICE)
	private String numProtocolloGenerale;
	
	@XmlVariabile(nome="PG_CAPOFILA_ANNO", tipo = TipoVariabile.SEMPLICE)
	private String annoProtocolloGenerale;
	
	@XmlVariabile(nome="PROT_CAPOFILA_REGISTRO", tipo = TipoVariabile.SEMPLICE)
	private String siglaProtocolloSettore;
	
	@XmlVariabile(nome="PROT_CAPOFILA_NRO", tipo = TipoVariabile.SEMPLICE)
	private String numProtocolloSettore;
	
	@XmlVariabile(nome="PROT_CAPOFILA_SUB_NRO", tipo = TipoVariabile.SEMPLICE)
	private String subProtocolloSettore;
	
	@XmlVariabile(nome="PROT_CAPOFILA_ANNO", tipo = TipoVariabile.SEMPLICE)
	private String annoProtocolloSettore;
	
	@XmlVariabile(nome="DT_RICHIESTA_PRATICA_URBANISTICA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtRichPraticaUrbanistica;
	
	@XmlVariabile(nome="COD_CLASSIF_FASC_EDILIZIO", tipo = TipoVariabile.SEMPLICE)
	private String codClassFascEdilizio;
	
	@XmlVariabile(nome="NRO_DEPOSITO", tipo = TipoVariabile.SEMPLICE)
	private String numeroDeposito;
	
	@XmlVariabile(nome="ANNO_DEPOSITO", tipo = TipoVariabile.SEMPLICE)
	private String annoDeposito;

	@XmlVariabile(nome="VISURA_PROT_NRO", tipo = TipoVariabile.SEMPLICE)
	private String numProtocolloGeneraleRichPratica;
	
	@XmlVariabile(nome="VISURA_PROT_ANNO", tipo = TipoVariabile.SEMPLICE)
	private String annoProtocolloGeneraleRichPratica;
	
	@XmlVariabile(nome="VISURA_PRATICA_NRO", tipo = TipoVariabile.SEMPLICE)
	private String numProtocolloGeneraleRichPraticaWF;
	
	@XmlVariabile(nome="VISURA_PRATICA_ANNO", tipo = TipoVariabile.SEMPLICE)
	private String annoProtocolloGeneraleRichPraticaWF;
	
	@XmlVariabile(nome="NRO_EP_PRAT_URBANISTICA", tipo = TipoVariabile.SEMPLICE)
	private String numeroEP;

	@XmlVariabile(nome="ANNO_EP_PRAT_URBANISTICA", tipo = TipoVariabile.SEMPLICE)
	private String annoEP;
	
	@XmlVariabile(nome="NRO_LICENZA_PRAT_URBANISTICA", tipo = TipoVariabile.SEMPLICE)
	private String numeroLicenza;
	
	@XmlVariabile(nome="ANNO_LICENZA_PRAT_URBANISTICA", tipo = TipoVariabile.SEMPLICE)
	private String annoLicenza;
	
	@XmlVariabile(nome = "DENOM_ESIBENTE_PRATICA_URB", tipo = TipoVariabile.LISTA)
	private List<ValueBean> listaEsibentiPraticaPregressa;

	@XmlVariabile(nome="CLASSIF_PREGRESSA", tipo = TipoVariabile.SEMPLICE)
	private String classifPregressa;
	 
	@XmlVariabile(nome="UDC", tipo = TipoVariabile.SEMPLICE)
	private String udc;
	
	@XmlVariabile(nome="PRESENZA_IN_ARCHIVIO_DEPOSITO", tipo = TipoVariabile.SEMPLICE)
	private String presenzaInArchivioDeposito;
	
	@XmlVariabile(nome="#DataPresentazioneIstanza ", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataPresentazioneIstanza;
	
	@XmlVariabile(nome="#DatiProtocollazioneDocIstruttoria.Mittente.Id", tipo = TipoVariabile.SEMPLICE)
	private String datiProtDocIstrMittenteId;
	
	@XmlVariabile(nome="#DatiProtocollazioneDocIstruttoria.Destinatario.FlgPersonaFisica", tipo = TipoVariabile.SEMPLICE)
	private String datiProtDocIstrDestinatarioFlgPersonaFisica;
	
	@XmlVariabile(nome="#DatiProtocollazioneDocIstruttoria.Destinatario.DenomCognome", tipo = TipoVariabile.SEMPLICE)
	private String datiProtDocIstrDestinatarioDenomCognome;
	
	@XmlVariabile(nome="#DatiProtocollazioneDocIstruttoria.Destinatario.Nome", tipo = TipoVariabile.SEMPLICE)
	private String datiProtDocIstrDestinatarioNome;
	
	@XmlVariabile(nome="#DatiProtocollazioneDocIstruttoria.CodFiscale", tipo = TipoVariabile.SEMPLICE)
	private String datiProtDocIstrCodFiscale;
	
	@XmlVariabile(nome="#@ProcCollegati", tipo=TipoVariabile.LISTA)
	private List<ProcedimentiCollegatiOutBean> procedimentiCollegati;
		
	@XmlVariabile(nome="#PresenzaFascCollegati", tipo = TipoVariabile.SEMPLICE)
	private String presenzaFascCollegati;
	
	@XmlVariabile(nome = "#Stato", tipo = TipoVariabile.SEMPLICE)
	private String stato;
	
	// *********** Controllo per evitare salvataggi con dati/file non aggiornati ****************************//
	@XmlVariabile(nome = "#TimestampGetData", tipo = TipoVariabile.SEMPLICE)
	private String timestampGetData;
	
	public Date getDataPresentazioneIstanza() {
		return dataPresentazioneIstanza;
	}

	public void setDataPresentazioneIstanza(Date dataPresentazioneIstanza) {
		this.dataPresentazioneIstanza = dataPresentazioneIstanza;
	}
	 
	public Flag getFlgFascTitolario() {
		return flgFascTitolario;
	}

	public void setFlgFascTitolario(Flag flgFascTitolario) {
		this.flgFascTitolario = flgFascTitolario;
	}

	public FlagSottoFasc getFlgSottoFascInserto() {
		return flgSottoFascInserto;
	}

	public void setFlgSottoFascInserto(FlagSottoFasc flgSottoFascInserto) {
		this.flgSottoFascInserto = flgSottoFascInserto;
	}

	public BigDecimal getIdFolderType() {
		return idFolderType;
	}

	public void setIdFolderType(BigDecimal idFolderType) {
		this.idFolderType = idFolderType;
	}

	
	public String getNomeFolderType() {
		return nomeFolderType;
	}

	
	public void setNomeFolderType(String nomeFolderType) {
		this.nomeFolderType = nomeFolderType;
	}

	public String getNomeFolder() {
		return nomeFolder;
	}

	public void setNomeFolder(String nomeFolder) {
		this.nomeFolder = nomeFolder;
	}

	public BigDecimal getIdClassificazione() {
		return idClassificazione;
	}

	public void setIdClassificazione(BigDecimal idClassificazione) {
		this.idClassificazione = idClassificazione;
	}

	public String getNroSecondario() {
		return nroSecondario;
	}

	public void setNroSecondario(String nroSecondario) {
		this.nroSecondario = nroSecondario;
	}

	public String getIdUdCapofila() {
		return idUdCapofila;
	}

	public void setIdUdCapofila(String idUdCapofila) {
		this.idUdCapofila = idUdCapofila;
	}
	
	public String getIdDocCapofila() {
		return idDocCapofila;
	}

	public void setIdDocCapofila(String idDocCapofila) {
		this.idDocCapofila = idDocCapofila;
	}

	public Flag getFlgUdCapofilaDaDataEntryScansioni() {
		return flgUdCapofilaDaDataEntryScansioni;
	}

	public void setFlgUdCapofilaDaDataEntryScansioni(Flag flgUdCapofilaDaDataEntryScansioni) {
		this.flgUdCapofilaDaDataEntryScansioni = flgUdCapofilaDaDataEntryScansioni;
	}		

	public String getEstremiDocCapofila() {
		return estremiDocCapofila;
	}

	public void setEstremiDocCapofila(String estremiDocCapofila) {
		this.estremiDocCapofila = estremiDocCapofila;
	}

	public String getDesOgg() {
		return desOgg;
	}

	public void setDesOgg(String desOgg) {
		this.desOgg = desOgg;
	}

	public BigDecimal getIdUOResponsabile() {
		return idUOResponsabile;
	}

	public void setIdUOResponsabile(BigDecimal idUOResponsabile) {
		this.idUOResponsabile = idUOResponsabile;
	}

	public BigDecimal getIdScrivResponsabile() {
		return idScrivResponsabile;
	}

	public void setIdScrivResponsabile(BigDecimal idScrivResponsabile) {
		this.idScrivResponsabile = idScrivResponsabile;
	}

	public FlagArchivio getFlgArchivio() {
		return flgArchivio;
	}

	public void setFlgArchivio(FlagArchivio flgArchivio) {
		this.flgArchivio = flgArchivio;
	}

	public String getLivRiservatezza() {
		return livRiservatezza;
	}

	public void setLivRiservatezza(String livRiservatezza) {
		this.livRiservatezza = livRiservatezza;
	}
	
	public String getDesLivRiservatezza() {
		return desLivRiservatezza;
	}

	public void setDesLivRiservatezza(String desLivRiservatezza) {
		this.desLivRiservatezza = desLivRiservatezza;
	}

	public Date getDtTermineRiservatezza() {
		return dtTermineRiservatezza;
	}

	public void setDtTermineRiservatezza(Date dtTermineRiservatezza) {
		this.dtTermineRiservatezza = dtTermineRiservatezza;
	}

	public String getLivEvidenza() {
		return livEvidenza;
	}

	public void setLivEvidenza(String livEvidenza) {
		this.livEvidenza = livEvidenza;
	}
	
	public String getDesLivEvidenza() {
		return desLivEvidenza;
	}

	public void setDesLivEvidenza(String desLivEvidenza) {
		this.desLivEvidenza = desLivEvidenza;
	}
	
	public CollocazioneFisicaFascicoloBean getCollocazioneFisica() {
		return collocazioneFisica;
	}

	public void setCollocazioneFisica(CollocazioneFisicaFascicoloBean collocazioneFisica) {
		this.collocazioneFisica = collocazioneFisica;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public SegnaturaFascicoloOut getSegnatura() {
		return segnatura;
	}

	public void setSegnatura(SegnaturaFascicoloOut segnatura) {
		this.segnatura = segnatura;
	}

	public Date getTsApertura() {
		return tsApertura;
	}

	public void setTsApertura(Date tsApertura) {
		this.tsApertura = tsApertura;
	}

	public Date getTsChiusura() {
		return tsChiusura;
	}

	public void setTsChiusura(Date tsChiusura) {
		this.tsChiusura = tsChiusura;
	}

	public void setIdFolderApp(BigDecimal idFolderApp) {
		this.idFolderApp = idFolderApp;
	}

	public BigDecimal getIdFolderApp() {
		return idFolderApp;
	}

	public AbilitazioniFascicoloOut getAbilitazioni() {
		return abilitazioni;
	}

	public void setAbilitazioni(AbilitazioniFascicoloOut abilitazioni) {
		this.abilitazioni = abilitazioni;
	}

	public String getDesUserApertura() {
		return desUserApertura;
	}

	public void setDesUserApertura(String desUserApertura) {
		this.desUserApertura = desUserApertura;
	}

	public List<ACLFolderBean> getListaACL() {
		return listaACL;
	}

	public void setListaACL(List<ACLFolderBean> listaACL) {
		this.listaACL = listaACL;
	}

	public List<AssegnatariOutBean> getAssegnatari() {
		return assegnatari;
	}

	public void setAssegnatari(List<AssegnatariOutBean> assegnatari) {
		this.assegnatari = assegnatari;
	}

	public List<DestInvioCCOutBean> getDestInvioCC() {
		return destInvioCC;
	}

	public void setDestInvioCC(List<DestInvioCCOutBean> destInvioCC) {
		this.destInvioCC = destInvioCC;
	}

	public String getIdLibrary() {
		return idLibrary;
	}

	public void setIdLibrary(String idLibrary) {
		this.idLibrary = idLibrary;
	}

	public String getFaseCorrenteProc() {
		return faseCorrenteProc;
	}

	public void setFaseCorrenteProc(String faseCorrenteProc) {
		this.faseCorrenteProc = faseCorrenteProc;
	}

	public String getPathFolderApp() {
		return pathFolderApp;
	}

	public void setPathFolderApp(String pathFolderApp) {
		this.pathFolderApp = pathFolderApp;
	}

	public List<TaskOutBean> getListaTask() {
		return listaTask;
	}

	public void setListaTask(List<TaskOutBean> listaTask) {
		this.listaTask = listaTask;
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

	public String getNriLivClassificazione() {
		return nriLivClassificazione;
	}

	public String getDesClassificazione() {
		return desClassificazione;
	}

	public String getNroFasc() {
		return nroFasc;
	}

	public String getNroSottofasc() {
		return nroSottofasc;
	}

	public String getNroInserto() {
		return nroInserto;
	}

	public void setNriLivClassificazione(String nriLivClassificazione) {
		this.nriLivClassificazione = nriLivClassificazione;
	}

	public void setDesClassificazione(String desClassificazione) {
		this.desClassificazione = desClassificazione;
	}

	public void setNroFasc(String nroFasc) {
		this.nroFasc = nroFasc;
	}

	public void setNroSottofasc(String nroSottofasc) {
		this.nroSottofasc = nroSottofasc;
	}

	public void setNroInserto(String nroInserto) {
		this.nroInserto = nroInserto;
	}

	public String getFlgEreditaPermessi() {
		return flgEreditaPermessi;
	}

	public void setFlgEreditaPermessi(String flgEreditaPermessi) {
		this.flgEreditaPermessi = flgEreditaPermessi;
	}
	
	public String getRowidFolder() {
		return rowidFolder;
	}
	
	public void setRowidFolder(String rowidFolder) {
		this.rowidFolder = rowidFolder;
	}

	public String getDesUserChiusura() {
		return desUserChiusura;
	}

	public void setDesUserChiusura(String desUserChiusura) {
		this.desUserChiusura = desUserChiusura;
	}
	
	public String getIdIstruttoreProc() {
		return idIstruttoreProc;
	}
	
	public void setIdIstruttoreProc(String idIstruttoreProc) {
		this.idIstruttoreProc = idIstruttoreProc;
	}

	public String getTipoIstruttoreProc() {
		return tipoIstruttoreProc;
	}
	
	public void setTipoIstruttoreProc(String tipoIstruttoreProc) {
		this.tipoIstruttoreProc = tipoIstruttoreProc;
	}
	
	public String getCodRapidoIstruttoreProc() {
		return codRapidoIstruttoreProc;
	}
	
	public void setCodRapidoIstruttoreProc(String codRapidoIstruttoreProc) {
		this.codRapidoIstruttoreProc = codRapidoIstruttoreProc;
	}

	public String getNomeIstruttoreProc() {
		return nomeIstruttoreProc;
	}

	public void setNomeIstruttoreProc(String nomeIstruttoreProc) {
		this.nomeIstruttoreProc = nomeIstruttoreProc;
	}

	public List<AllegatiOutBean> getListaDocumentiIniziali() {
		return listaDocumentiIniziali;
	}
	
	public void setListaDocumentiIniziali(List<AllegatiOutBean> listaDocumentiIniziali) {
		this.listaDocumentiIniziali = listaDocumentiIniziali;
	}

	public List<AllegatiOutBean> getListaDocumentiIstruttoria() {
		return listaDocumentiIstruttoria;
	}
	
	public void setListaDocumentiIstruttoria(List<AllegatiOutBean> listaDocumentiIstruttoria) {
		this.listaDocumentiIstruttoria = listaDocumentiIstruttoria;
	}

	public Flag getFlgCaricamentoPregressoDaGUI() {
		return flgCaricamentoPregressoDaGUI;
	}

	public void setFlgCaricamentoPregressoDaGUI(Flag flgCaricamentoPregressoDaGUI) {
		this.flgCaricamentoPregressoDaGUI = flgCaricamentoPregressoDaGUI;
	}

	public Flag getFlgTipoFolderConVie() {
		return flgTipoFolderConVie;
	}

	public void setFlgTipoFolderConVie(Flag flgTipoFolderConVie) {
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

	public List<AltreUbicazioniBean> getAltreUbicazioni() {
		return altreUbicazioni;
	}

	public void setAltreUbicazioni(List<AltreUbicazioniBean> altreUbicazioni) {
		this.altreUbicazioni = altreUbicazioni;
	}

	public Flag getAttivaTimbroTipologia() {
		return attivaTimbroTipologia;
	}

	public void setAttivaTimbroTipologia(Flag attivaTimbroTipologia) {
		this.attivaTimbroTipologia = attivaTimbroTipologia;
	}
		
	public String getDatiPrelievoDaArchivioIdUO() {
		return datiPrelievoDaArchivioIdUO;
	}

	public void setDatiPrelievoDaArchivioIdUO(String datiPrelievoDaArchivioIdUO) {
		this.datiPrelievoDaArchivioIdUO = datiPrelievoDaArchivioIdUO;
	}

	public String getDatiPrelievoDaArchivioCodUO() {
		return datiPrelievoDaArchivioCodUO;
	}

	public void setDatiPrelievoDaArchivioCodUO(String datiPrelievoDaArchivioCodUO) {
		this.datiPrelievoDaArchivioCodUO = datiPrelievoDaArchivioCodUO;
	}

	public String getDatiPrelievoDaArchivioDesUO() {
		return datiPrelievoDaArchivioDesUO;
	}
	
	public void setDatiPrelievoDaArchivioDesUO(String datiPrelievoDaArchivioDesUO) {
		this.datiPrelievoDaArchivioDesUO = datiPrelievoDaArchivioDesUO;
	}
	
	public String getDatiPrelievoDaArchivioDataPrelievo() {
		return datiPrelievoDaArchivioDataPrelievo;
	}
	
	public void setDatiPrelievoDaArchivioDataPrelievo(String datiPrelievoDaArchivioDataPrelievo) {
		this.datiPrelievoDaArchivioDataPrelievo = datiPrelievoDaArchivioDataPrelievo;
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

	public Flag getFlgDocumentazioneCompleta() {
		return flgDocumentazioneCompleta;
	}

	public void setFlgDocumentazioneCompleta(Flag flgDocumentazioneCompleta) {
		this.flgDocumentazioneCompleta = flgDocumentazioneCompleta;
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
	
	public String getCodClassFascEdilizio() {
		return codClassFascEdilizio;
	}

	public void setCodClassFascEdilizio(String codClassFascEdilizio) {
		this.codClassFascEdilizio = codClassFascEdilizio;
	}	
	
	public Date getDtRichPraticaUrbanistica() {
		return dtRichPraticaUrbanistica;
	}

	public void setDtRichPraticaUrbanistica(Date dtRichPraticaUrbanistica) {
		this.dtRichPraticaUrbanistica = dtRichPraticaUrbanistica;
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
	
	public List<ValueBean> getListaEsibentiPraticaPregressa() {
		return listaEsibentiPraticaPregressa;
	}

	public void setListaEsibentiPraticaPregressa(List<ValueBean> listaEsibentiPraticaPregressa) {
		this.listaEsibentiPraticaPregressa = listaEsibentiPraticaPregressa;
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

	public List<ProcedimentiCollegatiOutBean> getProcedimentiCollegati() {
		return procedimentiCollegati;
	}

	public void setProcedimentiCollegati(List<ProcedimentiCollegatiOutBean> procedimentiCollegati) {
		this.procedimentiCollegati = procedimentiCollegati;
	}

	public String getPresenzaFascCollegati() {
		return presenzaFascCollegati;
	}

	public void setPresenzaFascCollegati(String presenzaFascCollegati) {
		this.presenzaFascCollegati = presenzaFascCollegati;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getTimestampGetData() {
		return timestampGetData;
	}

	public void setTimestampGetData(String timestampGetData) {
		this.timestampGetData = timestampGetData;
	}
	
}