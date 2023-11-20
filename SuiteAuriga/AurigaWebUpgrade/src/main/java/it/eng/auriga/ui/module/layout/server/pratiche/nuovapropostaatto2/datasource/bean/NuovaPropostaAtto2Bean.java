/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.SelezionaScrivaniaBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.SelezionaUOBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.document.function.bean.CIGCUPBean;
import it.eng.document.function.bean.DatiSpesaAnnualiXDetPersonaleXmlBean;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class NuovaPropostaAtto2Bean {
	
	/*******************
	 * TAB DATI SCHEDA *
	 *******************/

	/* Hidden */
	private String idUd;
	private String idUdNuovoComeCopia;
	private String prefKeyModello;
	private String prefNameModello;
	private String useridModello;
	private String idUoModello;	
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
	private Boolean flgDatiSensibili;
	
	/*Revoca Atto*/
	private String idTipoProcRevocaAtto;	
	private String nomeTipoProcRevocaAtto;	
	private String idDefFlussoWFRevocaAtto;
	private String idTipoDocPropostaRevocaAtto;	
	private String nomeTipoDocPropostaRevocaAtto;		
	private String siglaPropostaRevocaAtto;	
			
	/* Dati scheda - Registrazione */
	private String categoriaRegAvvio;
	private String siglaRegAvvio;		
	private String siglaRegistrazione;
	private String numeroRegistrazione;
	private Date dataRegistrazione;
	private String annoRegistrazione;
	private String desUserRegistrazione;
	private String desUORegistrazione;
	private String siglaRegProvvisoria;
	private String numeroRegProvvisoria;
	private Date dataRegProvvisoria;
	private String annoRegProvvisoria;
	private String desUserRegProvvisoria;
	private String desUORegProvvisoria;
	
	/* Dati scheda - Dati di pubblicazione */
	private Date dataInizioPubblicazione;
	private String giorniPubblicazione;

	/* Dati scheda - Ruoli */
	private String ufficioProponente; // (idUO)
	private String codUfficioProponente;
	private String desUfficioProponente;
	private List<SelezionaUOBean> listaUfficioProponente;	
	private List<DirigenteAdottanteBean> listaAdottante;
	private List<ResponsabileDiProcedimentoBean> listaRdP;
	private List<SelezionaScrivaniaBean> listaRUP;
	private Boolean flgRichiediVistoDirettore;
	
	/* Dati scheda - Proposta di concerto con */
	private List<DirigenteDiConcertoBean> listaDirigentiConcerto; // colonne: dirigenteConcerto (idSV), codUoDirigenteConcerto, desDirigenteConcerto e flgDirigenteConcertoFirmatario
	
	/* Dati scheda - Proponenti */
//	private List<DirigenteProponenteBean> listaDirigentiProponenti;
//	private List<AssessoreBean> listaAssessori;
//	private List<ConsigliereBean> listaConsiglieri;
	
	/* Dati scheda - Estensori */
	private List<EstensoreBean> listaEstensori;
	
	/* Dati scheda - CIG */
	private List<CIGCUPBean> listaCIG;
	
	/* Dati scheda - Oggetto */
	private String oggetto;
	private String oggettoHtml;
	
	/* Dati scheda - Specificità del provvedimento */
	private Boolean flgDeterminaAContrarreTramiteProceduraGara;
	private Boolean flgDeterminaAggiudicaProceduraGara;
	private Boolean flgDeterminaRimodulazioneSpesaGaraAggiudicata;
	private Boolean flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro;
	private String idUdAttoDeterminaAContrarre;
	private String categoriaRegAttoDeterminaAContrarre;
	private String siglaAttoDeterminaAContrarre;
	private String numeroAttoDeterminaAContrarre;
	private String annoAttoDeterminaAContrarre;
	
	private String flgSpesa; // valori SI/NO
	private Boolean flgRichVerificaDiBilancioCorrente;
	private Boolean flgRichVerificaDiBilancioContoCapitale;
	private Boolean flgRichVerificaDiContabilita;
	private Boolean flgPresaVisioneContabilita;
	
	/* Dati scheda - Atti presupposti */
//	private List<SimpleValueBean> listaAttiPresupposti; // colonne: attoPresupposto
	private String attiPresupposti;
	
	/* Dati scheda - Riferimenti normativi */
	private List<SimpleValueBean> listaRiferimentiNormativi; // colonne: riferimentoNormativo
	
	/* Dati scheda - Motivazioni */
	private String motivazioni;
	
	/* Dati scheda - Dispositivo */
	private String dispositivo;
	private String loghiAggiuntiviDispositivo;
	
	/* Dati scheda - Allegati */
	private List<AllegatoProtocolloBean> listaAllegati; // colonne: le stesse degli allegati in Protocollo
	
	/* Altri dati per generazione modello */
	private String modelloDispositivoDesDirezioneProponente;
	private Boolean modelloDispositivoFlgRespDiConcertoDiAltreDirezioni;
	private String modelloDispositivoDesDirezioneRespDiConcerto;
	private String modelloDispositivoDesRUP;
	private String modelloDispositivoDesRdP;
	private String modelloDispositivoDesDirezioneRUP;
	private List<SimpleValueBean> listaDirezioniConcertoModelloDispositivo;
	private List<SimpleValueBean> listaResponsabiliPEGModelloDispositivo;
	private List<FirmatariModelloDispositivoBean> listaFirmatariModelloDispositivo;
	private String estremiPropostaAttoPerModello;
	private String dataPropostaAttoPerModello;
	
	private Boolean flgDettRevocaAtto;
	private String idPropostaAMC;
			
	/******************
	 * TAB DATI SPESA *
	 ******************/

	/* Dati spesa - Ruoli */
	private Boolean flgAdottanteUnicoRespPEG;	
	private List<ResponsabilePEGBean> listaResponsabiliPEG; // responsabilePEG (idSV), codUoResponsabilePEG e desResponsabilePEG
	private String ufficioDefinizioneSpesa; // (idUO)
	private String codUfficioDefinizioneSpesa;
	private String desUfficioDefinizioneSpesa;
	private List<SelezionaUOBean> listaUfficioDefinizioneSpesa;
	
	/* Dati spesa - Opzioni */
	private Boolean flgSpesaCorrente;
	private Boolean flgImpegniCorrenteGiaValidati;
	private Boolean flgSpesaContoCapitale;
	private Boolean flgImpegniContoCapitaleGiaRilasciati;
	private Boolean flgSoloSubImpSubCrono;
	private Boolean flgConVerificaContabilita;
	private Boolean flgRichiediParereRevisoriContabili;
	
	private List<SimpleKeyValueBean> listaVociPEGNoVerifDisp;
	
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

	/*********************************
	 * PER AGGIORNAMENTO ATTO SU SIB *
	 *********************************/
	
	private String eventoSIB;
	private String esitoEventoSIB;
	private Date dataEventoSIB;
	private String errMsgEventoSIB;
	private String idUoDirAdottanteSIB;
	private String codUoDirAdottanteSIB;
	private String desUoDirAdottanteSIB;
	
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
	private String idModAppendice;
	private String nomeModAppendice;
	private String uriModAppendice;
	private String tipoModAppendice;
	private Boolean flgMostraDatiSensibili;
	
	/**************************************
	 * PER PUBBLICAZIONE IN ALBO PRETORIO *
	 **************************************/
	
	private String desDirezioneProponente;
	private AllegatoProtocolloBean allegatoVistoContabile;
	/**************************************
	 * ID TIPO DOC ALBO PRETORIO *
	 **************************************/
	private String idTipoDocAlbo;
	
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
	public Boolean getFlgDatiSensibili() {
		return flgDatiSensibili;
	}
	public void setFlgDatiSensibili(Boolean flgDatiSensibili) {
		this.flgDatiSensibili = flgDatiSensibili;
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
	public List<SelezionaUOBean> getListaUfficioProponente() {
		return listaUfficioProponente;
	}
	public void setListaUfficioProponente(List<SelezionaUOBean> listaUfficioProponente) {
		this.listaUfficioProponente = listaUfficioProponente;
	}
	public List<DirigenteAdottanteBean> getListaAdottante() {
		return listaAdottante;
	}
	public void setListaAdottante(List<DirigenteAdottanteBean> listaAdottante) {
		this.listaAdottante = listaAdottante;
	}
	public List<ResponsabileDiProcedimentoBean> getListaRdP() {
		return listaRdP;
	}
	public void setListaRdP(List<ResponsabileDiProcedimentoBean> listaRdP) {
		this.listaRdP = listaRdP;
	}	
	public List<SelezionaScrivaniaBean> getListaRUP() {
		return listaRUP;
	}
	public void setListaRUP(List<SelezionaScrivaniaBean> listaRUP) {
		this.listaRUP = listaRUP;
	}
	public Boolean getFlgRichiediVistoDirettore() {
		return flgRichiediVistoDirettore;
	}
	public void setFlgRichiediVistoDirettore(Boolean flgRichiediVistoDirettore) {
		this.flgRichiediVistoDirettore = flgRichiediVistoDirettore;
	}
	public List<DirigenteDiConcertoBean> getListaDirigentiConcerto() {
		return listaDirigentiConcerto;
	}
	public void setListaDirigentiConcerto(List<DirigenteDiConcertoBean> listaDirigentiConcerto) {
		this.listaDirigentiConcerto = listaDirigentiConcerto;
	}
//	public List<DirigenteProponenteBean> getListaDirigentiProponenti() {
//		return listaDirigentiProponenti;
//	}
//	public void setListaDirigentiProponenti(List<DirigenteProponenteBean> listaDirigentiProponenti) {
//		this.listaDirigentiProponenti = listaDirigentiProponenti;
//	}
//	public List<AssessoreBean> getListaAssessori() {
//		return listaAssessori;
//	}
//	public void setListaAssessori(List<AssessoreBean> listaAssessori) {
//		this.listaAssessori = listaAssessori;
//	}
//	public List<ConsigliereBean> getListaConsiglieri() {
//		return listaConsiglieri;
//	}
//	public void setListaConsiglieri(List<ConsigliereBean> listaConsiglieri) {
//		this.listaConsiglieri = listaConsiglieri;
//	}
	public List<EstensoreBean> getListaEstensori() {
		return listaEstensori;
	}
	public void setListaEstensori(List<EstensoreBean> listaEstensori) {
		this.listaEstensori = listaEstensori;
	}
	public List<CIGCUPBean> getListaCIG() {
		return listaCIG;
	}
	public void setListaCIG(List<CIGCUPBean> listaCIG) {
		this.listaCIG = listaCIG;
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
	public String getIdUdAttoDeterminaAContrarre() {
		return idUdAttoDeterminaAContrarre;
	}
	public void setIdUdAttoDeterminaAContrarre(String idUdAttoDeterminaAContrarre) {
		this.idUdAttoDeterminaAContrarre = idUdAttoDeterminaAContrarre;
	}
	public String getCategoriaRegAttoDeterminaAContrarre() {
		return categoriaRegAttoDeterminaAContrarre;
	}
	public void setCategoriaRegAttoDeterminaAContrarre(String categoriaRegAttoDeterminaAContrarre) {
		this.categoriaRegAttoDeterminaAContrarre = categoriaRegAttoDeterminaAContrarre;
	}
	public String getSiglaAttoDeterminaAContrarre() {
		return siglaAttoDeterminaAContrarre;
	}
	public void setSiglaAttoDeterminaAContrarre(String siglaAttoDeterminaAContrarre) {
		this.siglaAttoDeterminaAContrarre = siglaAttoDeterminaAContrarre;
	}
	public String getNumeroAttoDeterminaAContrarre() {
		return numeroAttoDeterminaAContrarre;
	}
	public void setNumeroAttoDeterminaAContrarre(String numeroAttoDeterminaAContrarre) {
		this.numeroAttoDeterminaAContrarre = numeroAttoDeterminaAContrarre;
	}
	public String getAnnoAttoDeterminaAContrarre() {
		return annoAttoDeterminaAContrarre;
	}
	public void setAnnoAttoDeterminaAContrarre(String annoAttoDeterminaAContrarre) {
		this.annoAttoDeterminaAContrarre = annoAttoDeterminaAContrarre;
	}
	public String getFlgSpesa() {
		return flgSpesa;
	}
	public void setFlgSpesa(String flgSpesa) {
		this.flgSpesa = flgSpesa;
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
	public Boolean getFlgPresaVisioneContabilita() {
		return flgPresaVisioneContabilita;
	}
	public void setFlgPresaVisioneContabilita(Boolean flgPresaVisioneContabilita) {
		this.flgPresaVisioneContabilita = flgPresaVisioneContabilita;
	}
//	public List<SimpleValueBean> getListaAttiPresupposti() {
//		return listaAttiPresupposti;
//	}
//	public void setListaAttiPresupposti(List<SimpleValueBean> listaAttiPresupposti) {
//		this.listaAttiPresupposti = listaAttiPresupposti;
//	}
	public String getAttiPresupposti() {
		return attiPresupposti;
	}
	public void setAttiPresupposti(String attiPresupposti) {
		this.attiPresupposti = attiPresupposti;
	}
	public List<SimpleValueBean> getListaRiferimentiNormativi() {
		return listaRiferimentiNormativi;
	}
	public void setListaRiferimentiNormativi(List<SimpleValueBean> listaRiferimentiNormativi) {
		this.listaRiferimentiNormativi = listaRiferimentiNormativi;
	}
	public String getMotivazioni() {
		return motivazioni;
	}
	public void setMotivazioni(String motivazioni) {
		this.motivazioni = motivazioni;
	}
	public String getDispositivo() {
		return dispositivo;
	}
	public void setDispositivo(String dispositivo) {
		this.dispositivo = dispositivo;
	}
	public String getLoghiAggiuntiviDispositivo() {
		return loghiAggiuntiviDispositivo;
	}
	public void setLoghiAggiuntiviDispositivo(String loghiAggiuntiviDispositivo) {
		this.loghiAggiuntiviDispositivo = loghiAggiuntiviDispositivo;
	}
	public List<AllegatoProtocolloBean> getListaAllegati() {
		return listaAllegati;
	}
	public String getModelloDispositivoDesDirezioneProponente() {
		return modelloDispositivoDesDirezioneProponente;
	}
	public void setModelloDispositivoDesDirezioneProponente(String modelloDispositivoDesDirezioneProponente) {
		this.modelloDispositivoDesDirezioneProponente = modelloDispositivoDesDirezioneProponente;
	}
	public Boolean getModelloDispositivoFlgRespDiConcertoDiAltreDirezioni() {
		return modelloDispositivoFlgRespDiConcertoDiAltreDirezioni;
	}
	public void setModelloDispositivoFlgRespDiConcertoDiAltreDirezioni(Boolean modelloDispositivoFlgRespDiConcertoDiAltreDirezioni) {
		this.modelloDispositivoFlgRespDiConcertoDiAltreDirezioni = modelloDispositivoFlgRespDiConcertoDiAltreDirezioni;
	}
	public String getModelloDispositivoDesDirezioneRespDiConcerto() {
		return modelloDispositivoDesDirezioneRespDiConcerto;
	}
	public void setModelloDispositivoDesDirezioneRespDiConcerto(String modelloDispositivoDesDirezioneRespDiConcerto) {
		this.modelloDispositivoDesDirezioneRespDiConcerto = modelloDispositivoDesDirezioneRespDiConcerto;
	}
	public String getModelloDispositivoDesRUP() {
		return modelloDispositivoDesRUP;
	}
	public void setModelloDispositivoDesRUP(String modelloDispositivoDesRUP) {
		this.modelloDispositivoDesRUP = modelloDispositivoDesRUP;
	}
	public String getModelloDispositivoDesRdP() {
		return modelloDispositivoDesRdP;
	}
	public void setModelloDispositivoDesRdP(String modelloDispositivoDesRdP) {
		this.modelloDispositivoDesRdP = modelloDispositivoDesRdP;
	}
	public String getModelloDispositivoDesDirezioneRUP() {
		return modelloDispositivoDesDirezioneRUP;
	}
	public void setModelloDispositivoDesDirezioneRUP(String modelloDispositivoDesDirezioneRUP) {
		this.modelloDispositivoDesDirezioneRUP = modelloDispositivoDesDirezioneRUP;
	}
	public List<SimpleValueBean> getListaDirezioniConcertoModelloDispositivo() {
		return listaDirezioniConcertoModelloDispositivo;
	}
	public void setListaDirezioniConcertoModelloDispositivo(List<SimpleValueBean> listaDirezioniConcertoModelloDispositivo) {
		this.listaDirezioniConcertoModelloDispositivo = listaDirezioniConcertoModelloDispositivo;
	}
	public List<SimpleValueBean> getListaResponsabiliPEGModelloDispositivo() {
		return listaResponsabiliPEGModelloDispositivo;
	}
	public void setListaResponsabiliPEGModelloDispositivo(List<SimpleValueBean> listaResponsabiliPEGModelloDispositivo) {
		this.listaResponsabiliPEGModelloDispositivo = listaResponsabiliPEGModelloDispositivo;
	}
	public List<FirmatariModelloDispositivoBean> getListaFirmatariModelloDispositivo() {
		return listaFirmatariModelloDispositivo;
	}
	public void setListaFirmatariModelloDispositivo(List<FirmatariModelloDispositivoBean> listaFirmatariModelloDispositivo) {
		this.listaFirmatariModelloDispositivo = listaFirmatariModelloDispositivo;
	}	
	public String getEstremiPropostaAttoPerModello() {
		return estremiPropostaAttoPerModello;
	}
	public void setEstremiPropostaAttoPerModello(String estremiPropostaAttoPerModello) {
		this.estremiPropostaAttoPerModello = estremiPropostaAttoPerModello;
	}
	public String getDataPropostaAttoPerModello() {
		return dataPropostaAttoPerModello;
	}
	public void setDataPropostaAttoPerModello(String dataPropostaAttoPerModello) {
		this.dataPropostaAttoPerModello = dataPropostaAttoPerModello;
	}
	public Boolean getFlgDettRevocaAtto() {
		return flgDettRevocaAtto;
	}
	public void setFlgDettRevocaAtto(Boolean flgDettRevocaAtto) {
		this.flgDettRevocaAtto = flgDettRevocaAtto;
	}
	public String getIdPropostaAMC() {
		return idPropostaAMC;
	}
	public void setIdPropostaAMC(String idPropostaAMC) {
		this.idPropostaAMC = idPropostaAMC;
	}
	public void setListaAllegati(List<AllegatoProtocolloBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
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
	public List<SimpleKeyValueBean> getListaVociPEGNoVerifDisp() {
		return listaVociPEGNoVerifDisp;
	}
	public void setListaVociPEGNoVerifDisp(List<SimpleKeyValueBean> listaVociPEGNoVerifDisp) {
		this.listaVociPEGNoVerifDisp = listaVociPEGNoVerifDisp;
	}
	public Boolean getFlgDisattivaAutoRequestDatiContabiliSIBCorrente() {
		return flgDisattivaAutoRequestDatiContabiliSIBCorrente;
	}
	public void setFlgDisattivaAutoRequestDatiContabiliSIBCorrente(Boolean flgDisattivaAutoRequestDatiContabiliSIBCorrente) {
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
	public Boolean getFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale() {
		return flgDisattivaAutoRequestDatiContabiliSIBContoCapitale;
	}
	public void setFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale(Boolean flgDisattivaAutoRequestDatiContabiliSIBContoCapitale) {
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
	public Boolean getFlgMostraDatiSensibili() {
		return flgMostraDatiSensibili;
	}
	public void setFlgMostraDatiSensibili(Boolean flgMostraDatiSensibili) {
		this.flgMostraDatiSensibili = flgMostraDatiSensibili;
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
	public String getIdTipoDocAlbo() {
		return idTipoDocAlbo;
	}
	public void setIdTipoDocAlbo(String idTipoDocAlbo) {
		this.idTipoDocAlbo = idTipoDocAlbo;
	}
}