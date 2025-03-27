/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.EmendamentoXmlBean;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlAttributiCustom;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.jaxb.variabili.SezioneCache;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreaModDocumentoInBean implements Serializable {

	private static final long serialVersionUID = 8827729163041008691L;
	
	private List<String> campiDaAggiornare;
	
	@XmlVariabile(nome = "CopiaExIdUD", tipo = TipoVariabile.SEMPLICE)
	private String copiaExIdUD;
	
	@XmlVariabile(nome = "#FlgDocTypeDiverso", tipo = TipoVariabile.SEMPLICE)
	private Integer flgTipoDocDiverso;

	@XmlVariabile(nome = "#IdDocTypeDaCopiare", tipo = TipoVariabile.SEMPLICE)
	private String idTipoDocDaCopiare;

	@XmlVariabile(nome = "ChiaveModelloSelezionato", tipo = TipoVariabile.SEMPLICE)
	private String chiaveModelloSelezionato;
	
	@XmlVariabile(nome = "NomeModelloSelezionato", tipo = TipoVariabile.SEMPLICE)
	private String nomeModelloSelezionato;
	
	@XmlVariabile(nome = "UseridModelloSelezionato", tipo = TipoVariabile.SEMPLICE)
	private String useridModelloSelezionato;
	
	@XmlVariabile(nome = "IdUOModelloSelezionato", tipo = TipoVariabile.SEMPLICE)
	private String idUOModelloSelezionato;	
	
	@XmlVariabile(nome = "#FlgTipoProv", tipo = TipoVariabile.SEMPLICE)
	private TipoProvenienza flgTipoProv;

	@XmlVariabile(nome = "#DesOgg", tipo = TipoVariabile.SEMPLICE)
	private String oggetto;

	@XmlVariabile(nome = "#LivRiservatezza", tipo = TipoVariabile.SEMPLICE)
	private String livelloRiservatezza;

	@XmlVariabile(nome = "#DtTermineRiservatezza", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date termineRiservatezza;

	@XmlVariabile(nome = "#LivEvidenza", tipo = TipoVariabile.SEMPLICE)
	private String priorita;
	
	@XmlVariabile(nome = "FLG_RISPOSTA_URGENTE_Ud", tipo = TipoVariabile.SEMPLICE)
	private Integer flgRispostaUrgente;
	
	@XmlVariabile(nome = "FLG_ACCESSO_CIVICO_SEMPLICE_Ud", tipo = TipoVariabile.SEMPLICE)
	private Integer flgAccessoCivicoSemplice;
	
	@XmlVariabile(nome = "FLG_ACCESSO_CIVICO_GENERALIZZATO_Ud", tipo = TipoVariabile.SEMPLICE)
	private Integer flgAccessoCivicoGeneralizzato;
	
	@XmlVariabile(nome = "", tipo = TipoVariabile.NESTED)
	private ProtocolloRicevuto protocolloRicevuto;

	@XmlVariabile(nome = "#TsArrivo", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataArrivo;
	
	@XmlVariabile(nome = "#TsSpedizione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataEOraSpedizione;	

	@XmlVariabile(nome = "#CodMezzoTrasm", tipo = TipoVariabile.SEMPLICE)
	private String messoTrasmissione;

	@XmlVariabile(nome = "#DtRaccomandata", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtRaccomandata;

	@XmlVariabile(nome = "#NroRaccomandata", tipo = TipoVariabile.SEMPLICE)
	private String nroRaccomandata;
	
	@XmlVariabile(nome = "#NroListaRaccomandata", tipo = TipoVariabile.SEMPLICE)
	private String nroListaRaccomandata;
	
	@XmlVariabile(nome = "#SupportoOriginale", tipo = TipoVariabile.SEMPLICE)
	private String supportoOriginale;

	@XmlVariabile(nome = "#@RelazioniVsUD", tipo = TipoVariabile.LISTA)
	private List<DocumentoCollegato> docCollegato;

	@XmlVariabile(nome = "#Append_#@RelazioniVsUD", tipo = TipoVariabile.SEMPLICE)
	private String appendRelazioniVsUD;

	@XmlVariabile(nome = "#CollocazioneFisica.IdTopografico", tipo = TipoVariabile.SEMPLICE)
	private String idTopografico;

	@XmlVariabile(nome = "#CollocazioneFisica.Descrizione", tipo = TipoVariabile.SEMPLICE)
	private String descrizioneCollocazione;

	@XmlElementWrapper(nillable = true)
	@XmlVariabile(nome = "#@RegistrazioniDate", tipo = TipoVariabile.LISTA)
	private List<RegistroEmergenza> registroEmergenza;

	@XmlVariabile(nome = "#NoteUD", tipo = TipoVariabile.SEMPLICE)
	private String note;

	@XmlVariabile(nome = "#IdDocType", tipo = TipoVariabile.SEMPLICE)
	private String tipoDocumento;

	@XmlVariabile(nome = "#DtStesura", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataStesura;
	
	@XmlVariabile(nome = "#DtSpedizione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataSpedizioneCartaceo;
	
	@XmlVariabile(nome = "DATA_ARRIVO_PROTOCOLLO_Ud", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataArrivoProtocollo;

	@XmlVariabile(nome = "#@NumerazioniDaDare", tipo = TipoVariabile.LISTA)
	private List<TipoNumerazioneBean> tipoNumerazioni;

	@XmlVariabile(nome = "FLG_MULTA_Ud", tipo = TipoVariabile.SEMPLICE)
	private Integer flgMulta;
	
	@XmlVariabile(nome = "FLG_DOC_CONTRATTI_Ud", tipo = TipoVariabile.SEMPLICE)
	private Integer flgDocContratti;
	
	@XmlVariabile(nome = "COD_CONTRATTO_Ud", tipo = TipoVariabile.SEMPLICE)
	private String codContratti;
	
	@XmlVariabile(nome = "FLG_FIRME_COMPILATE_Ud", tipo = TipoVariabile.SEMPLICE)
	private String flgFirmeCompilateContratti;
	
	@XmlVariabile(nome = "FLG_DOC_CONTRATTI_CON_BARCODE_Ud", tipo = TipoVariabile.SEMPLICE)
	private Integer flgDocContrattiConBarcode;
	
	@XmlVariabile(nome = "#@MittentiEsterni", tipo = TipoVariabile.LISTA)
	private List<MittentiDocumentoBean> mittenti;

	@XmlVariabile(nome = "#@DestinatariEsterni", tipo = TipoVariabile.LISTA)
	private List<DestinatariBean> destinatari;
	
	@XmlVariabile(nome = "FLG_SEGNA_INVIO_MAIL_EXTRA_SISTEMA_Ud", tipo = TipoVariabile.SEMPLICE)
	private Integer flgSegnaInvioMailExtraSistema;
	
	@XmlVariabile(nome = "FLG_INVIATA_MAIL_EXTRA_SISTEMA_Ud", tipo = TipoVariabile.SEMPLICE)
	private Integer flgInviataMailExtraSistema;
	
	@XmlVariabile(nome = "#@FogliXlsDestinatari", tipo = TipoVariabile.LISTA)
	private List<FogliXlsDestinatari> fogliXlsDestinatari;

	@XmlVariabile(nome = "#@GruppiDestinatari", tipo = TipoVariabile.LISTA)
	private List<DistribuzioneBean> gruppi;

	@XmlVariabile(nome = "#@Assegnatari", tipo = TipoVariabile.LISTA)
	private List<AssegnatariBean> assegnatari;

	@XmlVariabile(nome = "#@InvioDestCC", tipo = TipoVariabile.LISTA)
	private List<AssegnatariBean> invioDestCC;

	@XmlVariabile(nome = "ID_UO_COINVOLTA_Doc", tipo = TipoVariabile.LISTA)
	private List<AltreUoCoinvolteBean> altreUoCoinvolte;

	@XmlVariabile(nome = "#@ClassFascTitolario", tipo = TipoVariabile.LISTA)
	private List<ClassificheFascicoliDocumentoBean> classifichefascicoli;

	@XmlVariabile(nome = "#@FolderCustom", tipo = TipoVariabile.LISTA)
	private List<FolderCustom> folderCustom;
	
	@XmlVariabile(nome = "RelVsPraticheApplEsterne", tipo = TipoVariabile.LISTA)
	private List<RelVsPraticheApplEsterneXmlBean> relVsPraticheApplEsterne;

	@XmlVariabile(nome = "#EmailProv", tipo = TipoVariabile.NESTED)
	private EmailProvBean emailProv;

	@XmlVariabile(nome = "#MotiviModificaDatiRegistrazione", tipo = TipoVariabile.SEMPLICE)
	private String motiviModificaDatiReg;

	@XmlVariabile(nome = "#ConfermaAssegnazione.IdUser", tipo = TipoVariabile.SEMPLICE)
	private String idUserConfermaAssegnazione;

	@XmlVariabile(nome = "#@RelazioniVsUD", tipo = TipoVariabile.LISTA)
	private List<RelazioneVsUdBean> relazioniVsUd;

	@XmlVariabile(nome = "#@URIVersPrecSuSharePoint_Ver", tipo = TipoVariabile.LISTA)
	private List<UrlVersione> uriVerPrecSuSharePoint_Ver;

	@XmlVariabile(nome = "#CodStatoDett", tipo = TipoVariabile.SEMPLICE)
	private String codStatoDett;

	@XmlVariabile(nome = "#CodStato", tipo = TipoVariabile.SEMPLICE)
	private String codStato;

	@XmlVariabile(nome = "#NomeDocType", tipo = TipoVariabile.SEMPLICE)
	private String nomeDocType;

	@XmlVariabile(nome = "OSSERVAZIONI_Doc", tipo = TipoVariabile.SEMPLICE)
	private String osservazioniDoc;

	@XmlVariabile(nome = "#FlgInvioInConserv", tipo = TipoVariabile.SEMPLICE)
	private Integer flgInvioInConserv;

	@XmlVariabile(nome = "#TsInvioInConservazione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsInvioInConservazione;

	@XmlVariabile(nome = "#IdInConservazione", tipo = TipoVariabile.SEMPLICE)
	private String idInConservazione;

	@XmlVariabile(nome = "#ErrMsgInvioInConservazione", tipo = TipoVariabile.SEMPLICE)
	private String errMsgInvioInConservazione;

	@XmlVariabile(nome = "#StatoConservazione", tipo = TipoVariabile.SEMPLICE)
	private String statoConservazione;

	@XmlVariabile(nome = "#@AltriSoggettiEsterni", tipo = TipoVariabile.LISTA)
	private List<SoggettoEsternoBean> altriSoggettiEsterni;

	@XmlVariabile(nome = "#@AltriSoggettiInt", tipo = TipoVariabile.LISTA)
	private List<SoggettoInternoBean> altriSoggettiInterni;

	@XmlVariabile(nome = "MODIFICATO_DISPOSITIVO_ATTO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String modificatoDispositivoAtto;

	@XmlVariabile(nome = "STATO_INVIO_MAIL_Doc", tipo = TipoVariabile.SEMPLICE)
	private String statoInvioMailDoc;

	@XmlVariabile(nome = "DATA_INVIO_MAIL_Doc", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataInvioMailDoc;

	@XmlVariabile(nome = "#ProvCIDocType", tipo = TipoVariabile.SEMPLICE)
	private String provCIDocType;

	@XmlVariabile(nome = "#ProvCIUD", tipo = TipoVariabile.SEMPLICE)
	private String provCIUD;

	@XmlVariabile(nome = "#NroPagineCartaceo", tipo = TipoVariabile.SEMPLICE)
	private Integer nroPagineCartaceo;

	@XmlVariabile(nome = "FLG_NO_PUBBL_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgNoPubblPrimario;

	@XmlVariabile(nome = "FLG_DATI_SENSIBILI_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgDatiSensibiliPrimario;
	
	@XmlVariabile(nome = "NOMINATIVO_FIRMA_OLOGRAFA_Ud", tipo = TipoVariabile.LISTA)
	private List<NominativiFirmaOlografaBean> listaNominativiFirmaOlografa;
	
	@XmlVariabile(nome = "ALTRO_RIF_UD_Ud", tipo = TipoVariabile.LISTA)
	private List<AltriRiferimentiBean> altriRiferimenti;

	@XmlVariabile(nome = "#CanaleInvioDest", tipo = TipoVariabile.SEMPLICE)
	private String canaleInvioDest;
	
	@XmlVariabile(nome = "#@AttiRichiesti", tipo = TipoVariabile.LISTA)
	private List<AttiRichiestiXMLBean> attiRichiesti;
	
	@XmlVariabile(nome = "#FlgSkipControlliCartaceo", tipo = TipoVariabile.SEMPLICE)
	private String flgSkipControlliCartaceo;
	
	@XmlVariabile(nome="#@ACL", tipo=TipoVariabile.LISTA)
	private List<ACLUdXmlBean> permessi;
	
	@XmlVariabile(nome = "#Append_#@RegistrazioniDate", tipo = TipoVariabile.SEMPLICE)
	private String appendRegistroEmergenza;
	
	@XmlVariabile(nome = "VEICOLO_TARGA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String targaVeicolo;
	
	@XmlVariabile(nome = "VEICOLO_TELAIO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String telaioVeicolo;
	
	@XmlVariabile(nome = "#@IdDocAllegatiOrdinati", tipo = TipoVariabile.LISTA)
	private List<ValueBean> idDocAllegatiOrdinati;
	
	@XmlVariabile(nome = "#@IdUDEmendamentiOrdinati", tipo = TipoVariabile.LISTA)
	private List<EmendamentoXmlBean> idUDEmendamentiOrdinati;
	
	@XmlVariabile(nome = "PERIZIA_ADSP_Ud", tipo = TipoVariabile.LISTA)
	private List<PeriziaXmlBean> listaPerizie;
	
	@XmlVariabile(nome = "COD_CONCESSIONE_Ud", tipo = TipoVariabile.LISTA)
	private List<ConcessioneXmlInBean> listaConcessioni;
	
	@XmlAttributiCustom
	private SezioneCache sezioneCacheAttributiDinamici;
	
	@XmlVariabile(nome = "#DataEsecutivita", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataEsecutivita;
	
	@XmlVariabile(nome = "#RichPubblicazione.DtInizioPubbl", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtInizioPubblAlboBK;
	
	@XmlVariabile(nome = "#RichPubblicazione.NroGGPubbl", tipo = TipoVariabile.SEMPLICE)
	private String nroGgPubblAlboBK;
	
	@XmlVariabile(nome = "#RichPubblicazione.FormaPubblicazione", tipo = TipoVariabile.SEMPLICE)
	private String formaPubblAlboBK;
	
	@XmlVariabile(nome = "#RichPubblicazione.DaEliminare", tipo = TipoVariabile.SEMPLICE)
	private Integer daEliminare;
	
	@XmlVariabile(nome = "#FlgSoloSetDatiRichPubbl", tipo = TipoVariabile.SEMPLICE)
	private Integer flgSoloSetDatiRichPubbl;
	
	@XmlVariabile(nome = "#FirstCallPreCompleteTask", tipo = TipoVariabile.SEMPLICE)
	private Integer flgFirstCallPreCompleteTask;
	
	@XmlVariabile(nome = "#@IdDocTypeAllegati", tipo = TipoVariabile.LISTA)
	private List<ValueBean> idDocTypeAllegati;

	@XmlVariabile(nome = "#ActivityName", tipo = TipoVariabile.SEMPLICE)
	private String activityName;
	
	@XmlVariabile(nome = "#EsitoTask", tipo = TipoVariabile.SEMPLICE)
	private String esitoTask;
	
	@XmlVariabile(nome = "#MsgEsitoTask", tipo = TipoVariabile.SEMPLICE)
	private String msgEsitoTask;
	
	@XmlVariabile(nome = "DES_OGG_PUBBLICATO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String oggettoOmissis;
	
	@XmlVariabile(nome = "#IdRicercatoreVisuraSUE", tipo = TipoVariabile.SEMPLICE)
	private String idRicercatoreVisuraSUE;
	
	@XmlVariabile(nome = "#RichPubblicazione.Id", tipo = TipoVariabile.SEMPLICE)
	private String idRichPubblicazione;
	
	@XmlVariabile(nome = "#AnnPubblicazione.Id", tipo = TipoVariabile.SEMPLICE)
	private String idAnnPubblicazione;
	
	@XmlVariabile(nome = "#AnnPubblicazione.Motivo", tipo = TipoVariabile.SEMPLICE)
	private String motivoAnnPubblicazione;
	
	@XmlVariabile(nome = "#ProrogaPubblicazione.Id", tipo = TipoVariabile.SEMPLICE)
	private String idProrogaPubblicazione;
	
	@XmlVariabile(nome = "#ProrogaPubblicazione.DataA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAProrogaPubblicazione;
	
	@XmlVariabile(nome = "#ProrogaPubblicazione.GiorniDurata", tipo = TipoVariabile.SEMPLICE)
	private String giorniDurataProrogaPubblicazione;
	
	@XmlVariabile(nome = "#ProrogaPubblicazione.Motivo", tipo = TipoVariabile.SEMPLICE)
	private String motivoProrogaPubblicazione;
	   
	@XmlVariabile(nome = "#RettificaPubblicazione.Id", tipo = TipoVariabile.SEMPLICE)
	private String idRettificaPubblicazione;
	
	@XmlVariabile(nome = "#RettificaPubblicazione.Motivo", tipo = TipoVariabile.SEMPLICE)
	private String motivoRettificaPubblicazione;
	
	@XmlVariabile(nome = "#DataAdozione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAdozione;
	
	@XmlVariabile(nome = "#RichPubblicazione.Note", tipo = TipoVariabile.SEMPLICE)
	private String notePubblicazione;
	
	@XmlVariabile(nome = "INDIRIZZO_MITT_EMAIL_ID_Ud", tipo = TipoVariabile.SEMPLICE)
	private String idCasellaMittente;
	
	@XmlVariabile(nome = "OPZ_PROT_AUTO_IN_ITER_FIRMA_Ud", tipo = TipoVariabile.SEMPLICE)
	private String opzProtAutoInIterFirma;
	
	@XmlVariabile(nome = "OPZ_REG_AUTO_IN_ITER_FIRMA_Ud", tipo = TipoVariabile.SEMPLICE)
	private String opzRegAutoInIterFirma;
	
	@XmlVariabile(nome = "DES_REGISTRO_REG_AUTO_IN_ITER_FIRMA_Ud", tipo = TipoVariabile.SEMPLICE)
	private String desRegistroRegAutoInIterFirma;
	
	@XmlVariabile(nome = "COD_CATEGORIA_REG_AUTO_IN_ITER_FIRMA_Ud", tipo = TipoVariabile.SEMPLICE)
	private String codCategoriaRegAutoInIterFirma;
	
	@XmlVariabile(nome = "ID_UO_REG_POST_ITER_FIRMA_Ud", tipo = TipoVariabile.SEMPLICE)
	private String idUoRegPostIterFirma;
	
	@XmlVariabile(nome = "FLG_EMAIL_AUTO_TERMINE_ITER_FIRMA_REG_Ud", tipo = TipoVariabile.SEMPLICE)
	private Integer flgEmailAutoTermineIterFirmaReg;
	
	@XmlVariabile(nome = "#IdOperRegistrazione", tipo = TipoVariabile.SEMPLICE)
	private String idOperRegistrazione;
	
	@XmlVariabile(nome = "#TimestampGetData", tipo = TipoVariabile.SEMPLICE)
	private String timestampGetData;

	// jira auriga-559
	@XmlVariabile(nome = "#IdFoglioRegMassiva", tipo = TipoVariabile.SEMPLICE)
	private String idFoglioRegMassiva;
	
	@XmlVariabile(nome = "#NroRigaFoglioRegMassiva", tipo = TipoVariabile.SEMPLICE)
	private String nroRigaFoglioRegMassiva;
	// fine auriga-559
	
	@XmlVariabile(nome = "BODY_EMAIL_Ud", tipo = TipoVariabile.SEMPLICE)
	private String bodyEmail;
	
	// Istanze concessione ADSP
	
	@XmlVariabile(nome = "PROC_CONC_ADSP_TIPO_AVVIO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String tipoAvvioProcConcADSP;
	
	@XmlVariabile(nome = "PROC_CONC_ADSP_ID_DOC_AVVIO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String idDocAvvioProcConcADSP;
	
	@XmlVariabile(nome = "PROC_CONC_ADSP_GG_TERMINE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String sceltaGiorniTermineProcConcADSP;
	
	@XmlVariabile(nome = "PROC_CONC_ADSP_FLG_TERMINI_MODIFICATI_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgTerminiModificatiProcConcADSP;
	
	@XmlVariabile(nome = "#AzioneScollegaDaIstanzaSUAPadre", tipo = TipoVariabile.SEMPLICE)
	private String azioneScollegaDaIstanzaSUAPadre;	

	@XmlVariabile(nome = "@Allegati", tipo = TipoVariabile.LISTA)
	private List<AttachWSBean> listaAllegati;

	/*********************
	 * GETTER AND SETTER *
	 *********************/
	
	public List<AttachWSBean> getListaAllegati() {
		return listaAllegati;
	}

	public void setListaAllegati(List<AttachWSBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}
	
	public List<String> getCampiDaAggiornare() {
		return campiDaAggiornare;
	}

	public void setCampiDaAggiornare(List<String> campiDaAggiornare) {
		this.campiDaAggiornare = campiDaAggiornare;
	}

	public String getCopiaExIdUD() {
		return copiaExIdUD;
	}

	public void setCopiaExIdUD(String copiaExIdUD) {
		this.copiaExIdUD = copiaExIdUD;
	}
	
	public Integer getFlgTipoDocDiverso() {
		return flgTipoDocDiverso;
	}

	public void setFlgTipoDocDiverso(Integer flgTipoDocDiverso) {
		this.flgTipoDocDiverso = flgTipoDocDiverso;
	}

	public String getIdTipoDocDaCopiare() {
		return idTipoDocDaCopiare;
	}

	public void setIdTipoDocDaCopiare(String idTipoDocDaCopiare) {
		this.idTipoDocDaCopiare = idTipoDocDaCopiare;
	}

	public String getChiaveModelloSelezionato() {
		return chiaveModelloSelezionato;
	}

	public void setChiaveModelloSelezionato(String chiaveModelloSelezionato) {
		this.chiaveModelloSelezionato = chiaveModelloSelezionato;
	}

	public String getNomeModelloSelezionato() {
		return nomeModelloSelezionato;
	}

	public void setNomeModelloSelezionato(String nomeModelloSelezionato) {
		this.nomeModelloSelezionato = nomeModelloSelezionato;
	}

	public String getUseridModelloSelezionato() {
		return useridModelloSelezionato;
	}

	public void setUseridModelloSelezionato(String useridModelloSelezionato) {
		this.useridModelloSelezionato = useridModelloSelezionato;
	}

	public String getIdUOModelloSelezionato() {
		return idUOModelloSelezionato;
	}

	public void setIdUOModelloSelezionato(String idUOModelloSelezionato) {
		this.idUOModelloSelezionato = idUOModelloSelezionato;
	}

	public TipoProvenienza getFlgTipoProv() {
		return flgTipoProv;
	}

	public void setFlgTipoProv(TipoProvenienza flgTipoProv) {
		this.flgTipoProv = flgTipoProv;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getLivelloRiservatezza() {
		return livelloRiservatezza;
	}

	public void setLivelloRiservatezza(String livelloRiservatezza) {
		this.livelloRiservatezza = livelloRiservatezza;
	}

	public Date getTermineRiservatezza() {
		return termineRiservatezza;
	}

	public void setTermineRiservatezza(Date termineRiservatezza) {
		this.termineRiservatezza = termineRiservatezza;
	}

	public String getPriorita() {
		return priorita;
	}

	public void setPriorita(String priorita) {
		this.priorita = priorita;
	}
	
	public Integer getFlgRispostaUrgente() {
		return flgRispostaUrgente;
	}

	public void setFlgRispostaUrgente(Integer flgRispostaUrgente) {
		this.flgRispostaUrgente = flgRispostaUrgente;
	}

	public Integer getFlgAccessoCivicoSemplice() {
		return flgAccessoCivicoSemplice;
	}

	public void setFlgAccessoCivicoSemplice(Integer flgAccessoCivicoSemplice) {
		this.flgAccessoCivicoSemplice = flgAccessoCivicoSemplice;
	}

	public Integer getFlgAccessoCivicoGeneralizzato() {
		return flgAccessoCivicoGeneralizzato;
	}

	public void setFlgAccessoCivicoGeneralizzato(Integer flgAccessoCivicoGeneralizzato) {
		this.flgAccessoCivicoGeneralizzato = flgAccessoCivicoGeneralizzato;
	}
	
	public ProtocolloRicevuto getProtocolloRicevuto() {
		return protocolloRicevuto;
	}

	public void setProtocolloRicevuto(ProtocolloRicevuto protocolloRicevuto) {
		this.protocolloRicevuto = protocolloRicevuto;
	}

	public Date getDataArrivo() {
		return dataArrivo;
	}

	public void setDataArrivo(Date dataArrivo) {
		this.dataArrivo = dataArrivo;
	}

	public Date getDataEOraSpedizione() {
		return dataEOraSpedizione;
	}

	public void setDataEOraSpedizione(Date dataEOraSpedizione) {
		this.dataEOraSpedizione = dataEOraSpedizione;
	}

	public String getMessoTrasmissione() {
		return messoTrasmissione;
	}

	public void setMessoTrasmissione(String messoTrasmissione) {
		this.messoTrasmissione = messoTrasmissione;
	}

	public Date getDtRaccomandata() {
		return dtRaccomandata;
	}

	public void setDtRaccomandata(Date dtRaccomandata) {
		this.dtRaccomandata = dtRaccomandata;
	}

	public String getNroRaccomandata() {
		return nroRaccomandata;
	}

	public void setNroRaccomandata(String nroRaccomandata) {
		this.nroRaccomandata = nroRaccomandata;
	}

	public String getNroListaRaccomandata() {
		return nroListaRaccomandata;
	}

	public void setNroListaRaccomandata(String nroListaRaccomandata) {
		this.nroListaRaccomandata = nroListaRaccomandata;
	}

	public String getSupportoOriginale() {
		return supportoOriginale;
	}

	public void setSupportoOriginale(String supportoOriginale) {
		this.supportoOriginale = supportoOriginale;
	}

	public List<DocumentoCollegato> getDocCollegato() {
		return docCollegato;
	}

	public void setDocCollegato(List<DocumentoCollegato> docCollegato) {
		this.docCollegato = docCollegato;
	}

	public String getAppendRelazioniVsUD() {
		return appendRelazioniVsUD;
	}

	public void setAppendRelazioniVsUD(String appendRelazioniVsUD) {
		this.appendRelazioniVsUD = appendRelazioniVsUD;
	}

	public String getIdTopografico() {
		return idTopografico;
	}

	public void setIdTopografico(String idTopografico) {
		this.idTopografico = idTopografico;
	}

	public String getDescrizioneCollocazione() {
		return descrizioneCollocazione;
	}

	public void setDescrizioneCollocazione(String descrizioneCollocazione) {
		this.descrizioneCollocazione = descrizioneCollocazione;
	}

	public List<RegistroEmergenza> getRegistroEmergenza() {
		return registroEmergenza;
	}

	public void setRegistroEmergenza(List<RegistroEmergenza> registroEmergenza) {
		this.registroEmergenza = registroEmergenza;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Date getDataStesura() {
		return dataStesura;
	}

	public void setDataStesura(Date dataStesura) {
		this.dataStesura = dataStesura;
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

	public List<TipoNumerazioneBean> getTipoNumerazioni() {
		return tipoNumerazioni;
	}

	public void setTipoNumerazioni(List<TipoNumerazioneBean> tipoNumerazioni) {
		this.tipoNumerazioni = tipoNumerazioni;
	}

	public Integer getFlgMulta() {
		return flgMulta;
	}

	public void setFlgMulta(Integer flgMulta) {
		this.flgMulta = flgMulta;
	}

	public Integer getFlgDocContratti() {
		return flgDocContratti;
	}

	public void setFlgDocContratti(Integer flgDocContratti) {
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

	public Integer getFlgDocContrattiConBarcode() {
		return flgDocContrattiConBarcode;
	}

	public void setFlgDocContrattiConBarcode(Integer flgDocContrattiConBarcode) {
		this.flgDocContrattiConBarcode = flgDocContrattiConBarcode;
	}

	public List<MittentiDocumentoBean> getMittenti() {
		return mittenti;
	}

	public void setMittenti(List<MittentiDocumentoBean> mittenti) {
		this.mittenti = mittenti;
	}

	public List<DestinatariBean> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<DestinatariBean> destinatari) {
		this.destinatari = destinatari;
	}

	public Integer getFlgSegnaInvioMailExtraSistema() {
		return flgSegnaInvioMailExtraSistema;
	}

	public void setFlgSegnaInvioMailExtraSistema(Integer flgSegnaInvioMailExtraSistema) {
		this.flgSegnaInvioMailExtraSistema = flgSegnaInvioMailExtraSistema;
	}

	public List<DistribuzioneBean> getGruppi() {
		return gruppi;
	}

	public void setGruppi(List<DistribuzioneBean> gruppi) {
		this.gruppi = gruppi;
	}

	public List<AssegnatariBean> getAssegnatari() {
		return assegnatari;
	}

	public void setAssegnatari(List<AssegnatariBean> assegnatari) {
		this.assegnatari = assegnatari;
	}

	public List<AssegnatariBean> getInvioDestCC() {
		return invioDestCC;
	}

	public void setInvioDestCC(List<AssegnatariBean> invioDestCC) {
		this.invioDestCC = invioDestCC;
	}

	public List<AltreUoCoinvolteBean> getAltreUoCoinvolte() {
		return altreUoCoinvolte;
	}

	public void setAltreUoCoinvolte(List<AltreUoCoinvolteBean> altreUoCoinvolte) {
		this.altreUoCoinvolte = altreUoCoinvolte;
	}

	public List<ClassificheFascicoliDocumentoBean> getClassifichefascicoli() {
		return classifichefascicoli;
	}

	public void setClassifichefascicoli(List<ClassificheFascicoliDocumentoBean> classifichefascicoli) {
		this.classifichefascicoli = classifichefascicoli;
	}

	public List<FolderCustom> getFolderCustom() {
		return folderCustom;
	}

	public void setFolderCustom(List<FolderCustom> folderCustom) {
		this.folderCustom = folderCustom;
	}

	public List<RelVsPraticheApplEsterneXmlBean> getRelVsPraticheApplEsterne() {
		return relVsPraticheApplEsterne;
	}

	public void setRelVsPraticheApplEsterne(List<RelVsPraticheApplEsterneXmlBean> relVsPraticheApplEsterne) {
		this.relVsPraticheApplEsterne = relVsPraticheApplEsterne;
	}

	public EmailProvBean getEmailProv() {
		return emailProv;
	}

	public void setEmailProv(EmailProvBean emailProv) {
		this.emailProv = emailProv;
	}

	public String getMotiviModificaDatiReg() {
		return motiviModificaDatiReg;
	}

	public void setMotiviModificaDatiReg(String motiviModificaDatiReg) {
		this.motiviModificaDatiReg = motiviModificaDatiReg;
	}

	public String getIdUserConfermaAssegnazione() {
		return idUserConfermaAssegnazione;
	}

	public void setIdUserConfermaAssegnazione(String idUserConfermaAssegnazione) {
		this.idUserConfermaAssegnazione = idUserConfermaAssegnazione;
	}

	public List<RelazioneVsUdBean> getRelazioniVsUd() {
		return relazioniVsUd;
	}

	public void setRelazioniVsUd(List<RelazioneVsUdBean> relazioniVsUd) {
		this.relazioniVsUd = relazioniVsUd;
	}

	public List<UrlVersione> getUriVerPrecSuSharePoint_Ver() {
		return uriVerPrecSuSharePoint_Ver;
	}

	public void setUriVerPrecSuSharePoint_Ver(List<UrlVersione> uriVerPrecSuSharePoint_Ver) {
		this.uriVerPrecSuSharePoint_Ver = uriVerPrecSuSharePoint_Ver;
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

	public String getNomeDocType() {
		return nomeDocType;
	}

	public void setNomeDocType(String nomeDocType) {
		this.nomeDocType = nomeDocType;
	}

	public String getOsservazioniDoc() {
		return osservazioniDoc;
	}

	public void setOsservazioniDoc(String osservazioniDoc) {
		this.osservazioniDoc = osservazioniDoc;
	}

	public Integer getFlgInvioInConserv() {
		return flgInvioInConserv;
	}

	public void setFlgInvioInConserv(Integer flgInvioInConserv) {
		this.flgInvioInConserv = flgInvioInConserv;
	}

	public Date getTsInvioInConservazione() {
		return tsInvioInConservazione;
	}

	public void setTsInvioInConservazione(Date tsInvioInConservazione) {
		this.tsInvioInConservazione = tsInvioInConservazione;
	}

	public String getIdInConservazione() {
		return idInConservazione;
	}

	public void setIdInConservazione(String idInConservazione) {
		this.idInConservazione = idInConservazione;
	}

	public String getErrMsgInvioInConservazione() {
		return errMsgInvioInConservazione;
	}

	public void setErrMsgInvioInConservazione(String errMsgInvioInConservazione) {
		this.errMsgInvioInConservazione = errMsgInvioInConservazione;
	}

	public String getStatoConservazione() {
		return statoConservazione;
	}

	public void setStatoConservazione(String statoConservazione) {
		this.statoConservazione = statoConservazione;
	}

	public List<SoggettoInternoBean> getAltriSoggettiInterni() {
		return altriSoggettiInterni;
	}

	public void setAltriSoggettiInterni(List<SoggettoInternoBean> altriSoggettiInterni) {
		this.altriSoggettiInterni = altriSoggettiInterni;
	}

	public List<SoggettoEsternoBean> getAltriSoggettiEsterni() {
		return altriSoggettiEsterni;
	}

	public void setAltriSoggettiEsterni(List<SoggettoEsternoBean> altriSoggettiEsterni) {
		this.altriSoggettiEsterni = altriSoggettiEsterni;
	}

	public String getModificatoDispositivoAtto() {
		return modificatoDispositivoAtto;
	}

	public void setModificatoDispositivoAtto(String modificatoDispositivoAtto) {
		this.modificatoDispositivoAtto = modificatoDispositivoAtto;
	}

	public String getStatoInvioMailDoc() {
		return statoInvioMailDoc;
	}

	public void setStatoInvioMailDoc(String statoInvioMailDoc) {
		this.statoInvioMailDoc = statoInvioMailDoc;
	}

	public Date getDataInvioMailDoc() {
		return dataInvioMailDoc;
	}

	public void setDataInvioMailDoc(Date dataInvioMailDoc) {
		this.dataInvioMailDoc = dataInvioMailDoc;
	}

	public String getProvCIDocType() {
		return provCIDocType;
	}

	public void setProvCIDocType(String provCIDocType) {
		this.provCIDocType = provCIDocType;
	}

	public String getProvCIUD() {
		return provCIUD;
	}

	public void setProvCIUD(String provCIUD) {
		this.provCIUD = provCIUD;
	}

	public Integer getNroPagineCartaceo() {
		return nroPagineCartaceo;
	}

	public void setNroPagineCartaceo(Integer nroPagineCartaceo) {
		this.nroPagineCartaceo = nroPagineCartaceo;
	}

	public String getFlgNoPubblPrimario() {
		return flgNoPubblPrimario;
	}

	public void setFlgNoPubblPrimario(String flgNoPubblPrimario) {
		this.flgNoPubblPrimario = flgNoPubblPrimario;
	}

	public String getFlgDatiSensibiliPrimario() {
		return flgDatiSensibiliPrimario;
	}

	public void setFlgDatiSensibiliPrimario(String flgDatiSensibiliPrimario) {
		this.flgDatiSensibiliPrimario = flgDatiSensibiliPrimario;
	}

	public List<NominativiFirmaOlografaBean> getListaNominativiFirmaOlografa() {
		return listaNominativiFirmaOlografa;
	}

	public void setListaNominativiFirmaOlografa(List<NominativiFirmaOlografaBean> listaNominativiFirmaOlografa) {
		this.listaNominativiFirmaOlografa = listaNominativiFirmaOlografa;
	}

	public List<AltriRiferimentiBean> getAltriRiferimenti() {
		return altriRiferimenti;
	}

	public void setAltriRiferimenti(List<AltriRiferimentiBean> altriRiferimenti) {
		this.altriRiferimenti = altriRiferimenti;
	}

	public String getCanaleInvioDest() {
		return canaleInvioDest;
	}

	public void setCanaleInvioDest(String canaleInvioDest) {
		this.canaleInvioDest = canaleInvioDest;
	}

	public List<AttiRichiestiXMLBean> getAttiRichiesti() {
		return attiRichiesti;
	}

	public void setAttiRichiesti(List<AttiRichiestiXMLBean> attiRichiesti) {
		this.attiRichiesti = attiRichiesti;
	}

	public String getFlgSkipControlliCartaceo() {
		return flgSkipControlliCartaceo;
	}

	public void setFlgSkipControlliCartaceo(String flgSkipControlliCartaceo) {
		this.flgSkipControlliCartaceo = flgSkipControlliCartaceo;
	}

	public List<ACLUdXmlBean> getPermessi() {
		return permessi;
	}

	public void setPermessi(List<ACLUdXmlBean> permessi) {
		this.permessi = permessi;
	}

	public String getAppendRegistroEmergenza() {
		return appendRegistroEmergenza;
	}

	public void setAppendRegistroEmergenza(String appendRegistroEmergenza) {
		this.appendRegistroEmergenza = appendRegistroEmergenza;
	}

	public String getTargaVeicolo() {
		return targaVeicolo;
	}

	public void setTargaVeicolo(String targaVeicolo) {
		this.targaVeicolo = targaVeicolo;
	}

	public String getTelaioVeicolo() {
		return telaioVeicolo;
	}

	public void setTelaioVeicolo(String telaioVeicolo) {
		this.telaioVeicolo = telaioVeicolo;
	}

	public List<ValueBean> getIdDocAllegatiOrdinati() {
		return idDocAllegatiOrdinati;
	}

	public void setIdDocAllegatiOrdinati(List<ValueBean> idDocAllegatiOrdinati) {
		this.idDocAllegatiOrdinati = idDocAllegatiOrdinati;
	}

	public List<EmendamentoXmlBean> getIdUDEmendamentiOrdinati() {
		return idUDEmendamentiOrdinati;
	}

	public void setIdUDEmendamentiOrdinati(List<EmendamentoXmlBean> idUDEmendamentiOrdinati) {
		this.idUDEmendamentiOrdinati = idUDEmendamentiOrdinati;
	}

	public List<PeriziaXmlBean> getListaPerizie() {
		return listaPerizie;
	}

	public void setListaPerizie(List<PeriziaXmlBean> listaPerizie) {
		this.listaPerizie = listaPerizie;
	}

	public SezioneCache getSezioneCacheAttributiDinamici() {
		return sezioneCacheAttributiDinamici;
	}

	public void setSezioneCacheAttributiDinamici(SezioneCache sezioneCacheAttributiDinamici) {
		this.sezioneCacheAttributiDinamici = sezioneCacheAttributiDinamici;
	}

	public Date getDataEsecutivita() {
		return dataEsecutivita;
	}

	public void setDataEsecutivita(Date dataEsecutivita) {
		this.dataEsecutivita = dataEsecutivita;
	}

	public Date getDtInizioPubblAlboBK() {
		return dtInizioPubblAlboBK;
	}

	public void setDtInizioPubblAlboBK(Date dtInizioPubblAlboBK) {
		this.dtInizioPubblAlboBK = dtInizioPubblAlboBK;
	}

	public String getNroGgPubblAlboBK() {
		return nroGgPubblAlboBK;
	}

	public void setNroGgPubblAlboBK(String nroGgPubblAlboBK) {
		this.nroGgPubblAlboBK = nroGgPubblAlboBK;
	}

	public String getFormaPubblAlboBK() {
		return formaPubblAlboBK;
	}

	public void setFormaPubblAlboBK(String formaPubblAlboBK) {
		this.formaPubblAlboBK = formaPubblAlboBK;
	}

	public Integer getDaEliminare() {
		return daEliminare;
	}

	public void setDaEliminare(Integer daEliminare) {
		this.daEliminare = daEliminare;
	}

	public Integer getFlgSoloSetDatiRichPubbl() {
		return flgSoloSetDatiRichPubbl;
	}

	public void setFlgSoloSetDatiRichPubbl(Integer flgSoloSetDatiRichPubbl) {
		this.flgSoloSetDatiRichPubbl = flgSoloSetDatiRichPubbl;
	}

	public Integer getFlgFirstCallPreCompleteTask() {
		return flgFirstCallPreCompleteTask;
	}

	public void setFlgFirstCallPreCompleteTask(Integer flgFirstCallPreCompleteTask) {
		this.flgFirstCallPreCompleteTask = flgFirstCallPreCompleteTask;
	}

	public List<ValueBean> getIdDocTypeAllegati() {
		return idDocTypeAllegati;
	}

	public void setIdDocTypeAllegati(List<ValueBean> idDocTypeAllegati) {
		this.idDocTypeAllegati = idDocTypeAllegati;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getEsitoTask() {
		return esitoTask;
	}

	public void setEsitoTask(String esitoTask) {
		this.esitoTask = esitoTask;
	}

	public String getMsgEsitoTask() {
		return msgEsitoTask;
	}

	public void setMsgEsitoTask(String msgEsitoTask) {
		this.msgEsitoTask = msgEsitoTask;
	}

	public String getOggettoOmissis() {
		return oggettoOmissis;
	}

	public void setOggettoOmissis(String oggettoOmissis) {
		this.oggettoOmissis = oggettoOmissis;
	}

	public String getIdRicercatoreVisuraSUE() {
		return idRicercatoreVisuraSUE;
	}

	public void setIdRicercatoreVisuraSUE(String idRicercatoreVisuraSUE) {
		this.idRicercatoreVisuraSUE = idRicercatoreVisuraSUE;
	}

	public String getIdRichPubblicazione() {
		return idRichPubblicazione;
	}

	public void setIdRichPubblicazione(String idRichPubblicazione) {
		this.idRichPubblicazione = idRichPubblicazione;
	}

	public String getIdAnnPubblicazione() {
		return idAnnPubblicazione;
	}

	public void setIdAnnPubblicazione(String idAnnPubblicazione) {
		this.idAnnPubblicazione = idAnnPubblicazione;
	}

	public String getMotivoAnnPubblicazione() {
		return motivoAnnPubblicazione;
	}

	public void setMotivoAnnPubblicazione(String motivoAnnPubblicazione) {
		this.motivoAnnPubblicazione = motivoAnnPubblicazione;
	}

	public String getIdProrogaPubblicazione() {
		return idProrogaPubblicazione;
	}

	public void setIdProrogaPubblicazione(String idProrogaPubblicazione) {
		this.idProrogaPubblicazione = idProrogaPubblicazione;
	}

	public Date getDataAProrogaPubblicazione() {
		return dataAProrogaPubblicazione;
	}

	public void setDataAProrogaPubblicazione(Date dataAProrogaPubblicazione) {
		this.dataAProrogaPubblicazione = dataAProrogaPubblicazione;
	}

	public String getGiorniDurataProrogaPubblicazione() {
		return giorniDurataProrogaPubblicazione;
	}

	public void setGiorniDurataProrogaPubblicazione(String giorniDurataProrogaPubblicazione) {
		this.giorniDurataProrogaPubblicazione = giorniDurataProrogaPubblicazione;
	}

	public String getMotivoProrogaPubblicazione() {
		return motivoProrogaPubblicazione;
	}

	public void setMotivoProrogaPubblicazione(String motivoProrogaPubblicazione) {
		this.motivoProrogaPubblicazione = motivoProrogaPubblicazione;
	}

	public String getIdRettificaPubblicazione() {
		return idRettificaPubblicazione;
	}

	public void setIdRettificaPubblicazione(String idRettificaPubblicazione) {
		this.idRettificaPubblicazione = idRettificaPubblicazione;
	}

	public String getMotivoRettificaPubblicazione() {
		return motivoRettificaPubblicazione;
	}

	public void setMotivoRettificaPubblicazione(String motivoRettificaPubblicazione) {
		this.motivoRettificaPubblicazione = motivoRettificaPubblicazione;
	}

	public Date getDataAdozione() {
		return dataAdozione;
	}

	public void setDataAdozione(Date dataAdozione) {
		this.dataAdozione = dataAdozione;
	}

	public String getNotePubblicazione() {
		return notePubblicazione;
	}

	public void setNotePubblicazione(String notePubblicazione) {
		this.notePubblicazione = notePubblicazione;
	}

	public String getIdCasellaMittente() {
		return idCasellaMittente;
	}

	public void setIdCasellaMittente(String idCasellaMittente) {
		this.idCasellaMittente = idCasellaMittente;
	}

	public String getOpzProtAutoInIterFirma() {
		return opzProtAutoInIterFirma;
	}

	public void setOpzProtAutoInIterFirma(String opzProtAutoInIterFirma) {
		this.opzProtAutoInIterFirma = opzProtAutoInIterFirma;
	}

	public String getOpzRegAutoInIterFirma() {
		return opzRegAutoInIterFirma;
	}

	public void setOpzRegAutoInIterFirma(String opzRegAutoInIterFirma) {
		this.opzRegAutoInIterFirma = opzRegAutoInIterFirma;
	}

	public String getDesRegistroRegAutoInIterFirma() {
		return desRegistroRegAutoInIterFirma;
	}

	public void setDesRegistroRegAutoInIterFirma(String desRegistroRegAutoInIterFirma) {
		this.desRegistroRegAutoInIterFirma = desRegistroRegAutoInIterFirma;
	}

	public String getCodCategoriaRegAutoInIterFirma() {
		return codCategoriaRegAutoInIterFirma;
	}

	public void setCodCategoriaRegAutoInIterFirma(String codCategoriaRegAutoInIterFirma) {
		this.codCategoriaRegAutoInIterFirma = codCategoriaRegAutoInIterFirma;
	}

	public String getIdUoRegPostIterFirma() {
		return idUoRegPostIterFirma;
	}

	public void setIdUoRegPostIterFirma(String idUoRegPostIterFirma) {
		this.idUoRegPostIterFirma = idUoRegPostIterFirma;
	}

	public Integer getFlgEmailAutoTermineIterFirmaReg() {
		return flgEmailAutoTermineIterFirmaReg;
	}

	public void setFlgEmailAutoTermineIterFirmaReg(Integer flgEmailAutoTermineIterFirmaReg) {
		this.flgEmailAutoTermineIterFirmaReg = flgEmailAutoTermineIterFirmaReg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<FogliXlsDestinatari> getFogliXlsDestinatari() {
		return fogliXlsDestinatari;
	}

	public void setFogliXlsDestinatari(List<FogliXlsDestinatari> fogliXlsDestinatari) {
		this.fogliXlsDestinatari = fogliXlsDestinatari;
	}

	public String getIdOperRegistrazione() {
		return idOperRegistrazione;
	}

	public void setIdOperRegistrazione(String idOperRegistrazione) {
		this.idOperRegistrazione = idOperRegistrazione;
	}

	public String getTimestampGetData() {
		return timestampGetData;
	}

	public void setTimestampGetData(String timestampGetData) {
		this.timestampGetData = timestampGetData;
	}

	public String getIdFoglioRegMassiva() {
		return idFoglioRegMassiva;
	}

	public void setIdFoglioRegMassiva(String idFoglioRegMassiva) {
		this.idFoglioRegMassiva = idFoglioRegMassiva;
	}

	public String getNroRigaFoglioRegMassiva() {
		return nroRigaFoglioRegMassiva;
	}

	public void setNroRigaFoglioRegMassiva(String nroRigaFoglioRegMassiva) {
		this.nroRigaFoglioRegMassiva = nroRigaFoglioRegMassiva;
	}
	
	public String getBodyEmail() {
		return bodyEmail;
	}

	public void setBodyEmail(String bodyEmail) {
		this.bodyEmail = bodyEmail;
	}

	public String getTipoAvvioProcConcADSP() {
		return tipoAvvioProcConcADSP;
	}

	public void setTipoAvvioProcConcADSP(String tipoAvvioProcConcADSP) {
		this.tipoAvvioProcConcADSP = tipoAvvioProcConcADSP;
	}

	public String getIdDocAvvioProcConcADSP() {
		return idDocAvvioProcConcADSP;
	}

	public void setIdDocAvvioProcConcADSP(String idDocAvvioProcConcADSP) {
		this.idDocAvvioProcConcADSP = idDocAvvioProcConcADSP;
	}

	public String getSceltaGiorniTermineProcConcADSP() {
		return sceltaGiorniTermineProcConcADSP;
	}

	public void setSceltaGiorniTermineProcConcADSP(String sceltaGiorniTermineProcConcADSP) {
		this.sceltaGiorniTermineProcConcADSP = sceltaGiorniTermineProcConcADSP;
	}

	public String getFlgTerminiModificatiProcConcADSP() {
		return flgTerminiModificatiProcConcADSP;
	}

	public void setFlgTerminiModificatiProcConcADSP(String flgTerminiModificatiProcConcADSP) {
		this.flgTerminiModificatiProcConcADSP = flgTerminiModificatiProcConcADSP;
	}

	public String getAzioneScollegaDaIstanzaSUAPadre() {
		return azioneScollegaDaIstanzaSUAPadre;
	}

	public void setAzioneScollegaDaIstanzaSUAPadre(String azioneScollegaDaIstanzaSUAPadre) {
		this.azioneScollegaDaIstanzaSUAPadre = azioneScollegaDaIstanzaSUAPadre;
	}

	public Integer getFlgInviataMailExtraSistema() {
		return flgInviataMailExtraSistema;
	}

	public void setFlgInviataMailExtraSistema(Integer flgInviataMailExtraSistema) {
		this.flgInviataMailExtraSistema = flgInviataMailExtraSistema;
	}

	public List<ConcessioneXmlInBean> getListaConcessioni() {
		return listaConcessioni;
	}

	public void setListaConcessioni(List<ConcessioneXmlInBean> listaConcessioni) {
		this.listaConcessioni = listaConcessioni;
	}
}
