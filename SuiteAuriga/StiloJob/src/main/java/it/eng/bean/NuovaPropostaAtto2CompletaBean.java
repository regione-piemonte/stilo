/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.document.function.bean.DatiSpesaAnnualiXDetPersonaleXmlBean;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import java.util.Date;
import java.util.List;
import java.util.Map;
import it.eng.document.function.bean.*;

public class NuovaPropostaAtto2CompletaBean
{
  private String idUd;
  private String idDominio;
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
  private String uriDocGeneratoFormatoOdt;
  private String idTipoProcRevocaAtto;
  private String nomeTipoProcRevocaAtto;
  private String idDefFlussoWFRevocaAtto;
  private String idTipoDocPropostaRevocaAtto;
  private String nomeTipoDocPropostaRevocaAtto;
  private String siglaPropostaRevocaAtto;
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
  private String numeroEmendamentoRif;
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
  private Date dataInizioPubblicazione;
  private String giorniPubblicazione;
  private String tipoAttoEmendamento;
  private String siglaAttoEmendamento;
  private String numeroAttoEmendamento;
  private String annoAttoEmendamento;
  private String numeroEmendamento;
  private String flgEmendamentoSuFile;
  private String numeroAllegatoEmendamento;
  private Boolean flgEmendamentoIntegraleAllegato;
  private String numeroPaginaEmendamento;
  private String numeroRigaEmendamento;
  private String effettoEmendamento;
  private String iniziativaProposta;
  private Boolean flgAttoMeroIndirizzo;
  private Boolean flgModificaRegolamento;
  private Boolean flgModificaStatuto;
  private Boolean flgNomina;
  private Boolean flgRatificaDeliberaUrgenza;
  private Boolean flgAttoUrgente;
  private List<SimpleKeyValueBean> listaCircoscrizioni;
  private String tipoInterpellanza;
  private String tipoOrdMobilita;
  private Date dataInizioVldOrdinanza;
  private Date dataFineVldOrdinanza;
  private String tipoLuogoOrdMobilita;
  private String luogoOrdMobilita;
  private List<SimpleKeyValueBean> listaCircoscrizioniOrdMobilita;
  private String ufficioProponente;
  private String codUfficioProponente;
  private String desUfficioProponente;
  private List<SelezionaUOBean> listaUfficioProponente;
  private List<DirigenteAdottanteBean> listaAdottante;
  private String centroDiCosto;
  private List<DirigenteDiConcertoBean> listaDirigentiConcerto;
  private List<DirigenteRespRegTecnicaBean> listaDirRespRegTecnica;
  private List<AltriDirRespRegTecnicaBean> listaAltriDirRespRegTecnica;
  private List<RdPCompletaBean> listaRdP;
  private List<RUPCompletaBean> listaRUP;
  private List<AssessoreBean> listaAssessori;
  private List<AssessoreBean> listaAltriAssessori;
  private List<ConsigliereBean> listaConsiglieri;
  private List<ConsigliereBean> listaAltriConsiglieri;
  private List<DirigenteProponenteBean> listaDirigentiProponenti;
  private List<DirigenteProponenteBean> listaAltriDirigentiProponenti;
  private List<CoordinatoreCompCircBean> listaCoordinatoriCompCirc;
  private Boolean flgRichiediVistoDirettore;
  private List<ResponsabileVistiConformitaBean> listaRespVistiConformita;
  private List<EstensoreBean> listaEstensori;
  private List<EstensoreBean> listaAltriEstensori;
  private List<IstruttoreBean> listaIstruttori;
  private List<IstruttoreBean> listaAltriIstruttori;
  private List<SimpleKeyValueBean> listaParereCircoscrizioni;
  private List<SimpleKeyValueBean> listaParereCommissioni;
  private String oggetto;
  private String oggettoHtml;
  private List<AttoRiferimentoBean> listaAttiRiferimento;
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
  private String flgSpesa;
  private Boolean flgCorteConti;
  private Boolean flgLiqContestualeImpegno;
  private Boolean flgLiqContestualeAltriAspettiRilCont;
  private Boolean flgCompQuadroFinRagDec;
  private Boolean flgNuoviImpAcc;
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
  private Boolean flgFondiEuropeiPON;
  private String flgLLPP;
  private String annoProgettoLLPP;
  private String numProgettoLLPP;
  private String flgBeniServizi;
  private String annoProgettoBeniServizi;
  private String numProgettoBeniServizi;
  private Boolean flgDecretoReggio;
  private Boolean flgAvvocatura;
  private List<DestVantaggioBean> listaDestVantaggio;
  private Boolean flgAdottanteUnicoRespPEG;
  private List<ResponsabilePEGBean> listaResponsabiliPEG;
  private String ufficioDefinizioneSpesa;
  private String codUfficioDefinizioneSpesa;
  private String desUfficioDefinizioneSpesa;
  private List<SelezionaUOBean> listaUfficioDefinizioneSpesa;
  private String opzAssCompSpesa;
  private Boolean flgRichVerificaDiBilancioCorrente;
  private Boolean flgRichVerificaDiBilancioContoCapitale;
  private Boolean flgRichVerificaDiContabilita;
  private Boolean flgConVerificaContabilita;
  private Boolean flgRichiediParereRevisoriContabili;
  private List<CIGBean> listaCIG;
  private String idPropostaAMC;
  private Boolean flgDettRevocaAtto;
  private Boolean flgDatiSensibili;
  private List<SimpleKeyValueBean> listaVociPEGNoVerifDisp;
  private List<SimpleValueBean> listaRiferimentiNormativi;
  private String attiPresupposti;
  private String motivazioni;
  private String premessa;
  private String dispositivo;
  private String loghiAggiuntiviDispositivo;
  private Boolean flgPubblicaAllegatiSeparati;
  private String flgPrivacy;
  private String flgPubblAlbo;
  private String numGiorniPubblAlbo;
  private String tipoDecorrenzaPubblAlbo;
  private Date dataPubblAlboDal;
  private Boolean flgUrgentePubblAlbo;
  private Date dataPubblAlboEntro;
  private String flgPubblAmmTrasp;
  private String sezionePubblAmmTrasp;
  private String sottoSezionePubblAmmTrasp;
  private String flgPubblBUR;
  private String annoTerminePubblBUR;
  private String tipoDecorrenzaPubblBUR;
  private Date dataPubblBURDal;
  private Boolean flgUrgentePubblBUR;
  private Date dataPubblBUREntro;
  private String flgPubblNotiziario;
  private Date dataEsecutivitaDal;
  private Boolean flgImmediatamenteEseguibile;
  private String motiviImmediatamenteEseguibile;
  private String listaDestNotificaAtto;
  private String prenotazioneSpesaSIBDiCorrente;
  private String modalitaInvioDatiSpesaARagioneriaCorrente;
  private List<DatiContabiliBean> listaDatiContabiliSIBCorrente;
  private String errorMessageDatiContabiliSIBCorrente;
  private List<DatiContabiliBean> listaInvioDatiSpesaCorrente;
  private String nomeFileTracciatoXlsCorrente;
  private String uriFileTracciatoXlsCorrente;
  private String noteCorrente;
  private String modalitaInvioDatiSpesaARagioneriaContoCapitale;
  private List<DatiContabiliBean> listaDatiContabiliSIBContoCapitale;
  private String errorMessageDatiContabiliSIBContoCapitale;
  private List<DatiContabiliBean> listaInvioDatiSpesaContoCapitale;
  private String nomeFileTracciatoXlsContoCapitale;
  private String uriFileTracciatoXlsContoCapitale;
  private String noteContoCapitale;
  private List<DatiSpesaAnnualiXDetPersonaleXmlBean> listaDatiSpesaAnnualiXDetPersonale;
  private String capitoloDatiSpesaAnnuaXDetPers;
  private String articoloDatiSpesaAnnuaXDetPers;
  private String numeroDatiSpesaAnnuaXDetPers;
  private String importoDatiSpesaAnnuaXDetPers;
  private String eventoSIB;
  private String esitoEventoSIB;
  private Date dataEventoSIB;
  private String errMsgEventoSIB;
  private String idUoDirAdottanteSIB;
  private String codUoDirAdottanteSIB;
  private String desUoDirAdottanteSIB;
  private String eventoContabilia;
  private String esitoEventoContabilia;
  private Date dataEventoContabilia;
  private String errMsgEventoContabilia;
  private String dirigenteResponsabileContabilia;
  private String centroResponsabilitaContabilia;
  private String centroCostoContabilia;
  private List<MovimentiContabiliaXmlBean> listaMovimentiContabilia;
  private String errorMessageMovimentiContabilia;
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
  private Map<String, Object> valori;
  private Map<String, String> tipiValori;
  private Map<String, String> colonneListe;
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
  private Boolean flgAppendiceDaUnire;
  private String idModAppendice;
  private String nomeModAppendice;
  private String uriModAppendice;
  private String tipoModAppendice;
  private Boolean flgMostraDatiSensibili;
  private Boolean flgMostraOmissisBarrati;
  private String idUoAlboReggio;
  private String desDirezioneProponente;
  private List<EmendamentoBean> listaEmendamenti;

  public String getIdUd()
  {
    return this.idUd;
  }

  public void setIdUd(String idUd) {
    this.idUd = idUd;
  }

  public String getIdDominio()
  {
    return this.idDominio;
  }

  public void setIdDominio(String idDominio) {
    this.idDominio = idDominio;
  }

  public String getTipoDocumento() {
    return this.tipoDocumento;
  }

  public void setTipoDocumento(String tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public String getNomeTipoDocumento() {
    return this.nomeTipoDocumento;
  }

  public void setNomeTipoDocumento(String nomeTipoDocumento) {
    this.nomeTipoDocumento = nomeTipoDocumento;
  }

  public String getRowidDoc() {
    return this.rowidDoc;
  }

  public void setRowidDoc(String rowidDoc) {
    this.rowidDoc = rowidDoc;
  }

  public String getIdDocPrimario() {
    return this.idDocPrimario;
  }

  public void setIdDocPrimario(String idDocPrimario) {
    this.idDocPrimario = idDocPrimario;
  }

  public String getNomeFilePrimario() {
    return this.nomeFilePrimario;
  }

  public void setNomeFilePrimario(String nomeFilePrimario) {
    this.nomeFilePrimario = nomeFilePrimario;
  }

  public String getUriFilePrimario() {
    return this.uriFilePrimario;
  }

  public void setUriFilePrimario(String uriFilePrimario) {
    this.uriFilePrimario = uriFilePrimario;
  }

  public Boolean getRemoteUriFilePrimario() {
    return this.remoteUriFilePrimario;
  }

  public void setRemoteUriFilePrimario(Boolean remoteUriFilePrimario) {
    this.remoteUriFilePrimario = remoteUriFilePrimario;
  }

  public MimeTypeFirmaBean getInfoFilePrimario() {
    return this.infoFilePrimario;
  }

  public void setInfoFilePrimario(MimeTypeFirmaBean infoFilePrimario) {
    this.infoFilePrimario = infoFilePrimario;
  }

  public Boolean getIsChangedFilePrimario() {
    return this.isChangedFilePrimario;
  }

  public void setIsChangedFilePrimario(Boolean isChangedFilePrimario) {
    this.isChangedFilePrimario = isChangedFilePrimario;
  }

  public String getIdDocPrimarioOmissis() {
    return this.idDocPrimarioOmissis;
  }

  public void setIdDocPrimarioOmissis(String idDocPrimarioOmissis) {
    this.idDocPrimarioOmissis = idDocPrimarioOmissis;
  }

  public String getNomeFilePrimarioOmissis() {
    return this.nomeFilePrimarioOmissis;
  }

  public void setNomeFilePrimarioOmissis(String nomeFilePrimarioOmissis) {
    this.nomeFilePrimarioOmissis = nomeFilePrimarioOmissis;
  }

  public String getUriFilePrimarioOmissis() {
    return this.uriFilePrimarioOmissis;
  }

  public void setUriFilePrimarioOmissis(String uriFilePrimarioOmissis) {
    this.uriFilePrimarioOmissis = uriFilePrimarioOmissis;
  }

  public Boolean getRemoteUriFilePrimarioOmissis() {
    return this.remoteUriFilePrimarioOmissis;
  }

  public void setRemoteUriFilePrimarioOmissis(Boolean remoteUriFilePrimarioOmissis) {
    this.remoteUriFilePrimarioOmissis = remoteUriFilePrimarioOmissis;
  }

  public MimeTypeFirmaBean getInfoFilePrimarioOmissis() {
    return this.infoFilePrimarioOmissis;
  }

  public void setInfoFilePrimarioOmissis(MimeTypeFirmaBean infoFilePrimarioOmissis) {
    this.infoFilePrimarioOmissis = infoFilePrimarioOmissis;
  }

  public Boolean getIsChangedFilePrimarioOmissis() {
    return this.isChangedFilePrimarioOmissis;
  }

  public void setIsChangedFilePrimarioOmissis(Boolean isChangedFilePrimarioOmissis) {
    this.isChangedFilePrimarioOmissis = isChangedFilePrimarioOmissis;
  }

  public String getUriDocGeneratoFormatoOdt() {
    return this.uriDocGeneratoFormatoOdt;
  }

  public void setUriDocGeneratoFormatoOdt(String uriDocGeneratoFormatoOdt) {
    this.uriDocGeneratoFormatoOdt = uriDocGeneratoFormatoOdt;
  }

  public String getIdTipoProcRevocaAtto() {
    return this.idTipoProcRevocaAtto;
  }

  public void setIdTipoProcRevocaAtto(String idTipoProcRevocaAtto) {
    this.idTipoProcRevocaAtto = idTipoProcRevocaAtto;
  }

  public String getNomeTipoProcRevocaAtto() {
    return this.nomeTipoProcRevocaAtto;
  }

  public void setNomeTipoProcRevocaAtto(String nomeTipoProcRevocaAtto) {
    this.nomeTipoProcRevocaAtto = nomeTipoProcRevocaAtto;
  }

  public String getIdDefFlussoWFRevocaAtto() {
    return this.idDefFlussoWFRevocaAtto;
  }

  public void setIdDefFlussoWFRevocaAtto(String idDefFlussoWFRevocaAtto) {
    this.idDefFlussoWFRevocaAtto = idDefFlussoWFRevocaAtto;
  }

  public String getIdTipoDocPropostaRevocaAtto() {
    return this.idTipoDocPropostaRevocaAtto;
  }

  public void setIdTipoDocPropostaRevocaAtto(String idTipoDocPropostaRevocaAtto) {
    this.idTipoDocPropostaRevocaAtto = idTipoDocPropostaRevocaAtto;
  }

  public String getNomeTipoDocPropostaRevocaAtto() {
    return this.nomeTipoDocPropostaRevocaAtto;
  }

  public void setNomeTipoDocPropostaRevocaAtto(String nomeTipoDocPropostaRevocaAtto) {
    this.nomeTipoDocPropostaRevocaAtto = nomeTipoDocPropostaRevocaAtto;
  }

  public String getSiglaPropostaRevocaAtto() {
    return this.siglaPropostaRevocaAtto;
  }

  public void setSiglaPropostaRevocaAtto(String siglaPropostaRevocaAtto) {
    this.siglaPropostaRevocaAtto = siglaPropostaRevocaAtto;
  }

  public String getIdTipoProcEmendamento() {
    return this.idTipoProcEmendamento;
  }

  public void setIdTipoProcEmendamento(String idTipoProcEmendamento) {
    this.idTipoProcEmendamento = idTipoProcEmendamento;
  }

  public String getNomeTipoProcEmendamento() {
    return this.nomeTipoProcEmendamento;
  }

  public void setNomeTipoProcEmendamento(String nomeTipoProcEmendamento) {
    this.nomeTipoProcEmendamento = nomeTipoProcEmendamento;
  }

  public String getIdDefFlussoWFEmendamento() {
    return this.idDefFlussoWFEmendamento;
  }

  public void setIdDefFlussoWFEmendamento(String idDefFlussoWFEmendamento) {
    this.idDefFlussoWFEmendamento = idDefFlussoWFEmendamento;
  }

  public String getIdTipoDocPropostaEmendamento() {
    return this.idTipoDocPropostaEmendamento;
  }

  public void setIdTipoDocPropostaEmendamento(String idTipoDocPropostaEmendamento) {
    this.idTipoDocPropostaEmendamento = idTipoDocPropostaEmendamento;
  }

  public String getNomeTipoDocPropostaEmendamento() {
    return this.nomeTipoDocPropostaEmendamento;
  }

  public void setNomeTipoDocPropostaEmendamento(String nomeTipoDocPropostaEmendamento) {
    this.nomeTipoDocPropostaEmendamento = nomeTipoDocPropostaEmendamento;
  }

  public String getSiglaPropostaEmendamento() {
    return this.siglaPropostaEmendamento;
  }

  public void setSiglaPropostaEmendamento(String siglaPropostaEmendamento) {
    this.siglaPropostaEmendamento = siglaPropostaEmendamento;
  }

  public String getTipoAttoRifEmendamento() {
    return this.tipoAttoRifEmendamento;
  }

  public void setTipoAttoRifEmendamento(String tipoAttoRifEmendamento) {
    this.tipoAttoRifEmendamento = tipoAttoRifEmendamento;
  }

  public String getSiglaAttoRifEmendamento() {
    return this.siglaAttoRifEmendamento;
  }

  public void setSiglaAttoRifEmendamento(String siglaAttoRifEmendamento) {
    this.siglaAttoRifEmendamento = siglaAttoRifEmendamento;
  }

  public String getNumeroAttoRifEmendamento() {
    return this.numeroAttoRifEmendamento;
  }

  public void setNumeroAttoRifEmendamento(String numeroAttoRifEmendamento) {
    this.numeroAttoRifEmendamento = numeroAttoRifEmendamento;
  }

  public String getAnnoAttoRifEmendamento() {
    return this.annoAttoRifEmendamento;
  }

  public void setAnnoAttoRifEmendamento(String annoAttoRifEmendamento) {
    this.annoAttoRifEmendamento = annoAttoRifEmendamento;
  }

  public String getNumeroEmendamentoRif() {
    return this.numeroEmendamentoRif;
  }

  public void setNumeroEmendamentoRif(String numeroEmendamentoRif) {
    this.numeroEmendamentoRif = numeroEmendamentoRif;
  }

  public String getCategoriaRegAvvio() {
    return this.categoriaRegAvvio;
  }

  public void setCategoriaRegAvvio(String categoriaRegAvvio) {
    this.categoriaRegAvvio = categoriaRegAvvio;
  }

  public String getSiglaRegAvvio() {
    return this.siglaRegAvvio;
  }

  public void setSiglaRegAvvio(String siglaRegAvvio) {
    this.siglaRegAvvio = siglaRegAvvio;
  }

  public String getSiglaRegistrazione() {
    return this.siglaRegistrazione;
  }

  public void setSiglaRegistrazione(String siglaRegistrazione) {
    this.siglaRegistrazione = siglaRegistrazione;
  }

  public String getNumeroRegistrazione() {
    return this.numeroRegistrazione;
  }

  public void setNumeroRegistrazione(String numeroRegistrazione) {
    this.numeroRegistrazione = numeroRegistrazione;
  }

  public Date getDataRegistrazione() {
    return this.dataRegistrazione;
  }

  public void setDataRegistrazione(Date dataRegistrazione) {
    this.dataRegistrazione = dataRegistrazione;
  }

  public String getAnnoRegistrazione() {
    return this.annoRegistrazione;
  }

  public void setAnnoRegistrazione(String annoRegistrazione) {
    this.annoRegistrazione = annoRegistrazione;
  }

  public String getDesUserRegistrazione() {
    return this.desUserRegistrazione;
  }

  public void setDesUserRegistrazione(String desUserRegistrazione) {
    this.desUserRegistrazione = desUserRegistrazione;
  }

  public String getDesUORegistrazione() {
    return this.desUORegistrazione;
  }

  public void setDesUORegistrazione(String desUORegistrazione) {
    this.desUORegistrazione = desUORegistrazione;
  }

  public String getEstremiRepertorioPerStruttura() {
    return this.estremiRepertorioPerStruttura;
  }

  public void setEstremiRepertorioPerStruttura(String estremiRepertorioPerStruttura) {
    this.estremiRepertorioPerStruttura = estremiRepertorioPerStruttura;
  }

  public String getSiglaRegProvvisoria() {
    return this.siglaRegProvvisoria;
  }

  public void setSiglaRegProvvisoria(String siglaRegProvvisoria) {
    this.siglaRegProvvisoria = siglaRegProvvisoria;
  }

  public String getNumeroRegProvvisoria() {
    return this.numeroRegProvvisoria;
  }

  public void setNumeroRegProvvisoria(String numeroRegProvvisoria) {
    this.numeroRegProvvisoria = numeroRegProvvisoria;
  }

  public Date getDataRegProvvisoria() {
    return this.dataRegProvvisoria;
  }

  public void setDataRegProvvisoria(Date dataRegProvvisoria) {
    this.dataRegProvvisoria = dataRegProvvisoria;
  }

  public String getAnnoRegProvvisoria() {
    return this.annoRegProvvisoria;
  }

  public void setAnnoRegProvvisoria(String annoRegProvvisoria) {
    this.annoRegProvvisoria = annoRegProvvisoria;
  }

  public String getDesUserRegProvvisoria() {
    return this.desUserRegProvvisoria;
  }

  public void setDesUserRegProvvisoria(String desUserRegProvvisoria) {
    this.desUserRegProvvisoria = desUserRegProvvisoria;
  }

  public String getDesUORegProvvisoria() {
    return this.desUORegProvvisoria;
  }

  public void setDesUORegProvvisoria(String desUORegProvvisoria) {
    this.desUORegProvvisoria = desUORegProvvisoria;
  }

  public String getIdDocPrimarioLiquidazione() {
    return this.idDocPrimarioLiquidazione;
  }

  public void setIdDocPrimarioLiquidazione(String idDocPrimarioLiquidazione) {
    this.idDocPrimarioLiquidazione = idDocPrimarioLiquidazione;
  }

  public String getIdUdLiquidazione() {
    return this.idUdLiquidazione;
  }

  public void setIdUdLiquidazione(String idUdLiquidazione) {
    this.idUdLiquidazione = idUdLiquidazione;
  }

  public String getCodTipoLiquidazioneXContabilia() {
    return this.codTipoLiquidazioneXContabilia;
  }

  public void setCodTipoLiquidazioneXContabilia(String codTipoLiquidazioneXContabilia) {
    this.codTipoLiquidazioneXContabilia = codTipoLiquidazioneXContabilia;
  }

  public String getSiglaRegLiquidazione() {
    return this.siglaRegLiquidazione;
  }

  public void setSiglaRegLiquidazione(String siglaRegLiquidazione) {
    this.siglaRegLiquidazione = siglaRegLiquidazione;
  }

  public String getAnnoRegLiquidazione() {
    return this.annoRegLiquidazione;
  }

  public void setAnnoRegLiquidazione(String annoRegLiquidazione) {
    this.annoRegLiquidazione = annoRegLiquidazione;
  }

  public String getNroRegLiquidazione() {
    return this.nroRegLiquidazione;
  }

  public void setNroRegLiquidazione(String nroRegLiquidazione) {
    this.nroRegLiquidazione = nroRegLiquidazione;
  }

  public Date getDataAdozioneLiquidazione() {
    return this.dataAdozioneLiquidazione;
  }

  public void setDataAdozioneLiquidazione(Date dataAdozioneLiquidazione) {
    this.dataAdozioneLiquidazione = dataAdozioneLiquidazione;
  }

  public String getEstremiAttoLiquidazione() {
    return this.estremiAttoLiquidazione;
  }

  public void setEstremiAttoLiquidazione(String estremiAttoLiquidazione) {
    this.estremiAttoLiquidazione = estremiAttoLiquidazione;
  }

  public Date getDataInizioPubblicazione() {
    return this.dataInizioPubblicazione;
  }

  public void setDataInizioPubblicazione(Date dataInizioPubblicazione) {
    this.dataInizioPubblicazione = dataInizioPubblicazione;
  }

  public String getGiorniPubblicazione() {
    return this.giorniPubblicazione;
  }

  public void setGiorniPubblicazione(String giorniPubblicazione) {
    this.giorniPubblicazione = giorniPubblicazione;
  }

  public String getTipoAttoEmendamento() {
    return this.tipoAttoEmendamento;
  }

  public void setTipoAttoEmendamento(String tipoAttoEmendamento) {
    this.tipoAttoEmendamento = tipoAttoEmendamento;
  }

  public String getSiglaAttoEmendamento() {
    return this.siglaAttoEmendamento;
  }

  public void setSiglaAttoEmendamento(String siglaAttoEmendamento) {
    this.siglaAttoEmendamento = siglaAttoEmendamento;
  }

  public String getNumeroAttoEmendamento() {
    return this.numeroAttoEmendamento;
  }

  public void setNumeroAttoEmendamento(String numeroAttoEmendamento) {
    this.numeroAttoEmendamento = numeroAttoEmendamento;
  }

  public String getAnnoAttoEmendamento() {
    return this.annoAttoEmendamento;
  }

  public void setAnnoAttoEmendamento(String annoAttoEmendamento) {
    this.annoAttoEmendamento = annoAttoEmendamento;
  }

  public String getNumeroEmendamento() {
    return this.numeroEmendamento;
  }

  public void setNumeroEmendamento(String numeroEmendamento) {
    this.numeroEmendamento = numeroEmendamento;
  }

  public String getFlgEmendamentoSuFile() {
    return this.flgEmendamentoSuFile;
  }

  public void setFlgEmendamentoSuFile(String flgEmendamentoSuFile) {
    this.flgEmendamentoSuFile = flgEmendamentoSuFile;
  }

  public String getNumeroAllegatoEmendamento() {
    return this.numeroAllegatoEmendamento;
  }

  public void setNumeroAllegatoEmendamento(String numeroAllegatoEmendamento) {
    this.numeroAllegatoEmendamento = numeroAllegatoEmendamento;
  }

  public Boolean getFlgEmendamentoIntegraleAllegato() {
    return this.flgEmendamentoIntegraleAllegato;
  }

  public void setFlgEmendamentoIntegraleAllegato(Boolean flgEmendamentoIntegraleAllegato) {
    this.flgEmendamentoIntegraleAllegato = flgEmendamentoIntegraleAllegato;
  }

  public String getNumeroPaginaEmendamento() {
    return this.numeroPaginaEmendamento;
  }

  public void setNumeroPaginaEmendamento(String numeroPaginaEmendamento) {
    this.numeroPaginaEmendamento = numeroPaginaEmendamento;
  }

  public String getNumeroRigaEmendamento() {
    return this.numeroRigaEmendamento;
  }

  public void setNumeroRigaEmendamento(String numeroRigaEmendamento) {
    this.numeroRigaEmendamento = numeroRigaEmendamento;
  }

  public String getEffettoEmendamento() {
    return this.effettoEmendamento;
  }

  public void setEffettoEmendamento(String effettoEmendamento) {
    this.effettoEmendamento = effettoEmendamento;
  }

  public String getIniziativaProposta() {
    return this.iniziativaProposta;
  }

  public void setIniziativaProposta(String iniziativaProposta) {
    this.iniziativaProposta = iniziativaProposta;
  }

  public Boolean getFlgAttoMeroIndirizzo() {
    return this.flgAttoMeroIndirizzo;
  }

  public void setFlgAttoMeroIndirizzo(Boolean flgAttoMeroIndirizzo) {
    this.flgAttoMeroIndirizzo = flgAttoMeroIndirizzo;
  }

  public Boolean getFlgModificaRegolamento() {
    return this.flgModificaRegolamento;
  }

  public void setFlgModificaRegolamento(Boolean flgModificaRegolamento) {
    this.flgModificaRegolamento = flgModificaRegolamento;
  }

  public Boolean getFlgModificaStatuto() {
    return this.flgModificaStatuto;
  }

  public void setFlgModificaStatuto(Boolean flgModificaStatuto) {
    this.flgModificaStatuto = flgModificaStatuto;
  }

  public Boolean getFlgNomina() {
    return this.flgNomina;
  }

  public void setFlgNomina(Boolean flgNomina) {
    this.flgNomina = flgNomina;
  }

  public Boolean getFlgRatificaDeliberaUrgenza() {
    return this.flgRatificaDeliberaUrgenza;
  }

  public void setFlgRatificaDeliberaUrgenza(Boolean flgRatificaDeliberaUrgenza) {
    this.flgRatificaDeliberaUrgenza = flgRatificaDeliberaUrgenza;
  }

  public Boolean getFlgAttoUrgente() {
    return this.flgAttoUrgente;
  }

  public void setFlgAttoUrgente(Boolean flgAttoUrgente) {
    this.flgAttoUrgente = flgAttoUrgente;
  }

  public List<SimpleKeyValueBean> getListaCircoscrizioni() {
    return this.listaCircoscrizioni;
  }

  public void setListaCircoscrizioni(List<SimpleKeyValueBean> listaCircoscrizioni) {
    this.listaCircoscrizioni = listaCircoscrizioni;
  }

  public String getTipoInterpellanza() {
    return this.tipoInterpellanza;
  }

  public void setTipoInterpellanza(String tipoInterpellanza) {
    this.tipoInterpellanza = tipoInterpellanza;
  }

  public String getTipoOrdMobilita() {
    return this.tipoOrdMobilita;
  }

  public void setTipoOrdMobilita(String tipoOrdMobilita) {
    this.tipoOrdMobilita = tipoOrdMobilita;
  }

  public Date getDataInizioVldOrdinanza() {
    return this.dataInizioVldOrdinanza;
  }

  public void setDataInizioVldOrdinanza(Date dataInizioVldOrdinanza) {
    this.dataInizioVldOrdinanza = dataInizioVldOrdinanza;
  }

  public Date getDataFineVldOrdinanza() {
    return this.dataFineVldOrdinanza;
  }

  public void setDataFineVldOrdinanza(Date dataFineVldOrdinanza) {
    this.dataFineVldOrdinanza = dataFineVldOrdinanza;
  }

  public String getTipoLuogoOrdMobilita() {
    return this.tipoLuogoOrdMobilita;
  }

  public void setTipoLuogoOrdMobilita(String tipoLuogoOrdMobilita) {
    this.tipoLuogoOrdMobilita = tipoLuogoOrdMobilita;
  }

  public String getLuogoOrdMobilita()
  {
    return this.luogoOrdMobilita;
  }

  public void setLuogoOrdMobilita(String luogoOrdMobilita) {
    this.luogoOrdMobilita = luogoOrdMobilita;
  }

  public List<SimpleKeyValueBean> getListaCircoscrizioniOrdMobilita() {
    return this.listaCircoscrizioniOrdMobilita;
  }

  public void setListaCircoscrizioniOrdMobilita(List<SimpleKeyValueBean> listaCircoscrizioniOrdMobilita) {
    this.listaCircoscrizioniOrdMobilita = listaCircoscrizioniOrdMobilita;
  }

  public String getUfficioProponente() {
    return this.ufficioProponente;
  }

  public void setUfficioProponente(String ufficioProponente) {
    this.ufficioProponente = ufficioProponente;
  }

  public String getCodUfficioProponente() {
    return this.codUfficioProponente;
  }

  public void setCodUfficioProponente(String codUfficioProponente) {
    this.codUfficioProponente = codUfficioProponente;
  }

  public String getDesUfficioProponente() {
    return this.desUfficioProponente;
  }

  public void setDesUfficioProponente(String desUfficioProponente) {
    this.desUfficioProponente = desUfficioProponente;
  }

  public List<SelezionaUOBean> getListaUfficioProponente() {
    return this.listaUfficioProponente;
  }

  public void setListaUfficioProponente(List<SelezionaUOBean> listaUfficioProponente) {
    this.listaUfficioProponente = listaUfficioProponente;
  }

  public List<DirigenteAdottanteBean> getListaAdottante() {
    return this.listaAdottante;
  }

  public void setListaAdottante(List<DirigenteAdottanteBean> listaAdottante) {
    this.listaAdottante = listaAdottante;
  }

  public String getCentroDiCosto() {
    return this.centroDiCosto;
  }

  public void setCentroDiCosto(String centroDiCosto) {
    this.centroDiCosto = centroDiCosto;
  }

  public List<DirigenteDiConcertoBean> getListaDirigentiConcerto() {
    return this.listaDirigentiConcerto;
  }

  public void setListaDirigentiConcerto(List<DirigenteDiConcertoBean> listaDirigentiConcerto) {
    this.listaDirigentiConcerto = listaDirigentiConcerto;
  }

  public List<DirigenteRespRegTecnicaBean> getListaDirRespRegTecnica() {
    return this.listaDirRespRegTecnica;
  }

  public void setListaDirRespRegTecnica(List<DirigenteRespRegTecnicaBean> listaDirRespRegTecnica) {
    this.listaDirRespRegTecnica = listaDirRespRegTecnica;
  }

  public List<AltriDirRespRegTecnicaBean> getListaAltriDirRespRegTecnica() {
    return this.listaAltriDirRespRegTecnica;
  }

  public void setListaAltriDirRespRegTecnica(List<AltriDirRespRegTecnicaBean> listaAltriDirRespRegTecnica) {
    this.listaAltriDirRespRegTecnica = listaAltriDirRespRegTecnica;
  }

  public List<RdPCompletaBean> getListaRdP() {
    return this.listaRdP;
  }

  public void setListaRdP(List<RdPCompletaBean> listaRdP) {
    this.listaRdP = listaRdP;
  }

  public List<RUPCompletaBean> getListaRUP() {
    return this.listaRUP;
  }

  public void setListaRUP(List<RUPCompletaBean> listaRUP) {
    this.listaRUP = listaRUP;
  }

  public List<AssessoreBean> getListaAssessori() {
    return this.listaAssessori;
  }

  public void setListaAssessori(List<AssessoreBean> listaAssessori) {
    this.listaAssessori = listaAssessori;
  }

  public List<AssessoreBean> getListaAltriAssessori() {
    return this.listaAltriAssessori;
  }

  public void setListaAltriAssessori(List<AssessoreBean> listaAltriAssessori) {
    this.listaAltriAssessori = listaAltriAssessori;
  }

  public List<ConsigliereBean> getListaConsiglieri() {
    return this.listaConsiglieri;
  }

  public void setListaConsiglieri(List<ConsigliereBean> listaConsiglieri) {
    this.listaConsiglieri = listaConsiglieri;
  }

  public List<ConsigliereBean> getListaAltriConsiglieri() {
    return this.listaAltriConsiglieri;
  }

  public void setListaAltriConsiglieri(List<ConsigliereBean> listaAltriConsiglieri) {
    this.listaAltriConsiglieri = listaAltriConsiglieri;
  }

  public List<DirigenteProponenteBean> getListaDirigentiProponenti() {
    return this.listaDirigentiProponenti;
  }

  public void setListaDirigentiProponenti(List<DirigenteProponenteBean> listaDirigentiProponenti) {
    this.listaDirigentiProponenti = listaDirigentiProponenti;
  }

  public List<DirigenteProponenteBean> getListaAltriDirigentiProponenti() {
    return this.listaAltriDirigentiProponenti;
  }

  public void setListaAltriDirigentiProponenti(List<DirigenteProponenteBean> listaAltriDirigentiProponenti) {
    this.listaAltriDirigentiProponenti = listaAltriDirigentiProponenti;
  }

  public List<CoordinatoreCompCircBean> getListaCoordinatoriCompCirc() {
    return this.listaCoordinatoriCompCirc;
  }

  public void setListaCoordinatoriCompCirc(List<CoordinatoreCompCircBean> listaCoordinatoriCompCirc) {
    this.listaCoordinatoriCompCirc = listaCoordinatoriCompCirc;
  }

  public Boolean getFlgRichiediVistoDirettore() {
    return this.flgRichiediVistoDirettore;
  }

  public void setFlgRichiediVistoDirettore(Boolean flgRichiediVistoDirettore) {
    this.flgRichiediVistoDirettore = flgRichiediVistoDirettore;
  }

  public List<ResponsabileVistiConformitaBean> getListaRespVistiConformita() {
    return this.listaRespVistiConformita;
  }

  public void setListaRespVistiConformita(List<ResponsabileVistiConformitaBean> listaRespVistiConformita) {
    this.listaRespVistiConformita = listaRespVistiConformita;
  }

  public List<EstensoreBean> getListaEstensori() {
    return this.listaEstensori;
  }

  public void setListaEstensori(List<EstensoreBean> listaEstensori) {
    this.listaEstensori = listaEstensori;
  }

  public List<EstensoreBean> getListaAltriEstensori() {
    return this.listaAltriEstensori;
  }

  public void setListaAltriEstensori(List<EstensoreBean> listaAltriEstensori) {
    this.listaAltriEstensori = listaAltriEstensori;
  }

  public List<IstruttoreBean> getListaIstruttori() {
    return this.listaIstruttori;
  }

  public void setListaIstruttori(List<IstruttoreBean> listaIstruttori) {
    this.listaIstruttori = listaIstruttori;
  }

  public List<IstruttoreBean> getListaAltriIstruttori() {
    return this.listaAltriIstruttori;
  }

  public void setListaAltriIstruttori(List<IstruttoreBean> listaAltriIstruttori) {
    this.listaAltriIstruttori = listaAltriIstruttori;
  }

  public List<SimpleKeyValueBean> getListaParereCircoscrizioni() {
    return this.listaParereCircoscrizioni;
  }

  public void setListaParereCircoscrizioni(List<SimpleKeyValueBean> listaParereCircoscrizioni) {
    this.listaParereCircoscrizioni = listaParereCircoscrizioni;
  }

  public List<SimpleKeyValueBean> getListaParereCommissioni() {
    return this.listaParereCommissioni;
  }

  public void setListaParereCommissioni(List<SimpleKeyValueBean> listaParereCommissioni) {
    this.listaParereCommissioni = listaParereCommissioni;
  }

  public String getOggetto() {
    return this.oggetto;
  }

  public void setOggetto(String oggetto) {
    this.oggetto = oggetto;
  }

  public String getOggettoHtml() {
    return this.oggettoHtml;
  }

  public void setOggettoHtml(String oggettoHtml) {
    this.oggettoHtml = oggettoHtml;
  }

  public List<AttoRiferimentoBean> getListaAttiRiferimento() {
    return this.listaAttiRiferimento;
  }

  public void setListaAttiRiferimento(List<AttoRiferimentoBean> listaAttiRiferimento) {
    this.listaAttiRiferimento = listaAttiRiferimento;
  }

  public String getOggLiquidazione()
  {
    return this.oggLiquidazione;
  }

  public void setOggLiquidazione(String oggLiquidazione) {
    this.oggLiquidazione = oggLiquidazione;
  }

  public Date getDataScadenzaLiquidazione() {
    return this.dataScadenzaLiquidazione;
  }

  public void setDataScadenzaLiquidazione(Date dataScadenzaLiquidazione) {
    this.dataScadenzaLiquidazione = dataScadenzaLiquidazione;
  }

  public String getUrgenzaLiquidazione() {
    return this.urgenzaLiquidazione;
  }

  public void setUrgenzaLiquidazione(String urgenzaLiquidazione) {
    this.urgenzaLiquidazione = urgenzaLiquidazione;
  }

  public Boolean getFlgLiqXUffCassa() {
    return this.flgLiqXUffCassa;
  }

  public void setFlgLiqXUffCassa(Boolean flgLiqXUffCassa) {
    this.flgLiqXUffCassa = flgLiqXUffCassa;
  }

  public String getImportoAnticipoCassa() {
    return this.importoAnticipoCassa;
  }

  public void setImportoAnticipoCassa(String importoAnticipoCassa) {
    this.importoAnticipoCassa = importoAnticipoCassa;
  }

  public Date getDataDecorrenzaContratto() {
    return this.dataDecorrenzaContratto;
  }

  public void setDataDecorrenzaContratto(Date dataDecorrenzaContratto) {
    this.dataDecorrenzaContratto = dataDecorrenzaContratto;
  }

  public String getAnniDurataContratto() {
    return this.anniDurataContratto;
  }

  public void setAnniDurataContratto(String anniDurataContratto) {
    this.anniDurataContratto = anniDurataContratto;
  }

  public Boolean getFlgAffidamento() {
    return this.flgAffidamento;
  }

  public void setFlgAffidamento(Boolean flgAffidamento) {
    this.flgAffidamento = flgAffidamento;
  }

  public Boolean getFlgDeterminaAContrarreTramiteProceduraGara() {
    return this.flgDeterminaAContrarreTramiteProceduraGara;
  }

  public void setFlgDeterminaAContrarreTramiteProceduraGara(Boolean flgDeterminaAContrarreTramiteProceduraGara) {
    this.flgDeterminaAContrarreTramiteProceduraGara = flgDeterminaAContrarreTramiteProceduraGara;
  }

  public Boolean getFlgDeterminaAggiudicaProceduraGara() {
    return this.flgDeterminaAggiudicaProceduraGara;
  }

  public void setFlgDeterminaAggiudicaProceduraGara(Boolean flgDeterminaAggiudicaProceduraGara) {
    this.flgDeterminaAggiudicaProceduraGara = flgDeterminaAggiudicaProceduraGara;
  }

  public Boolean getFlgDeterminaRimodulazioneSpesaGaraAggiudicata() {
    return this.flgDeterminaRimodulazioneSpesaGaraAggiudicata;
  }

  public void setFlgDeterminaRimodulazioneSpesaGaraAggiudicata(Boolean flgDeterminaRimodulazioneSpesaGaraAggiudicata) {
    this.flgDeterminaRimodulazioneSpesaGaraAggiudicata = flgDeterminaRimodulazioneSpesaGaraAggiudicata;
  }

  public Boolean getFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro() {
    return this.flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro;
  }

  public void setFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro(Boolean flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)
  {
    this.flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro = flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro;
  }

  public Boolean getFlgDeterminaRiaccertamento() {
    return this.flgDeterminaRiaccertamento;
  }

  public void setFlgDeterminaRiaccertamento(Boolean flgDeterminaRiaccertamento) {
    this.flgDeterminaRiaccertamento = flgDeterminaRiaccertamento;
  }

  public Boolean getFlgDeterminaAccertRadiaz() {
    return this.flgDeterminaAccertRadiaz;
  }

  public void setFlgDeterminaAccertRadiaz(Boolean flgDeterminaAccertRadiaz) {
    this.flgDeterminaAccertRadiaz = flgDeterminaAccertRadiaz;
  }

  public Boolean getFlgDeterminaVariazBil() {
    return this.flgDeterminaVariazBil;
  }

  public void setFlgDeterminaVariazBil(Boolean flgDeterminaVariazBil) {
    this.flgDeterminaVariazBil = flgDeterminaVariazBil;
  }

  public Boolean getFlgVantaggiEconomici() {
    return this.flgVantaggiEconomici;
  }

  public void setFlgVantaggiEconomici(Boolean flgVantaggiEconomici) {
    this.flgVantaggiEconomici = flgVantaggiEconomici;
  }

  public String getFlgSpesa() {
    return this.flgSpesa;
  }

  public void setFlgSpesa(String flgSpesa) {
    this.flgSpesa = flgSpesa;
  }

  public Boolean getFlgCorteConti() {
    return this.flgCorteConti;
  }

  public void setFlgCorteConti(Boolean flgCorteConti) {
    this.flgCorteConti = flgCorteConti;
  }

  public Boolean getFlgLiqContestualeImpegno() {
    return this.flgLiqContestualeImpegno;
  }

  public void setFlgLiqContestualeImpegno(Boolean flgLiqContestualeImpegno) {
    this.flgLiqContestualeImpegno = flgLiqContestualeImpegno;
  }

  public Boolean getFlgLiqContestualeAltriAspettiRilCont() {
    return this.flgLiqContestualeAltriAspettiRilCont;
  }

  public void setFlgLiqContestualeAltriAspettiRilCont(Boolean flgLiqContestualeAltriAspettiRilCont) {
    this.flgLiqContestualeAltriAspettiRilCont = flgLiqContestualeAltriAspettiRilCont;
  }

  public Boolean getFlgCompQuadroFinRagDec() {
    return this.flgCompQuadroFinRagDec;
  }

  public void setFlgCompQuadroFinRagDec(Boolean flgCompQuadroFinRagDec) {
    this.flgCompQuadroFinRagDec = flgCompQuadroFinRagDec;
  }

  public Boolean getFlgNuoviImpAcc() {
    return this.flgNuoviImpAcc;
  }

  public void setFlgNuoviImpAcc(Boolean flgNuoviImpAcc) {
    this.flgNuoviImpAcc = flgNuoviImpAcc;
  }

  public Boolean getFlgInsMovARagioneria() {
    return this.flgInsMovARagioneria;
  }

  public void setFlgInsMovARagioneria(Boolean flgInsMovARagioneria) {
    this.flgInsMovARagioneria = flgInsMovARagioneria;
  }

  public Boolean getFlgPresaVisioneContabilita() {
    return this.flgPresaVisioneContabilita;
  }

  public void setFlgPresaVisioneContabilita(Boolean flgPresaVisioneContabilita) {
    this.flgPresaVisioneContabilita = flgPresaVisioneContabilita;
  }

  public Boolean getFlgSpesaCorrente() {
    return this.flgSpesaCorrente;
  }

  public void setFlgSpesaCorrente(Boolean flgSpesaCorrente) {
    this.flgSpesaCorrente = flgSpesaCorrente;
  }

  public Boolean getFlgImpegniCorrenteGiaValidati() {
    return this.flgImpegniCorrenteGiaValidati;
  }

  public void setFlgImpegniCorrenteGiaValidati(Boolean flgImpegniCorrenteGiaValidati) {
    this.flgImpegniCorrenteGiaValidati = flgImpegniCorrenteGiaValidati;
  }

  public Boolean getFlgSpesaContoCapitale() {
    return this.flgSpesaContoCapitale;
  }

  public void setFlgSpesaContoCapitale(Boolean flgSpesaContoCapitale) {
    this.flgSpesaContoCapitale = flgSpesaContoCapitale;
  }

  public Boolean getFlgImpegniContoCapitaleGiaRilasciati() {
    return this.flgImpegniContoCapitaleGiaRilasciati;
  }

  public void setFlgImpegniContoCapitaleGiaRilasciati(Boolean flgImpegniContoCapitaleGiaRilasciati) {
    this.flgImpegniContoCapitaleGiaRilasciati = flgImpegniContoCapitaleGiaRilasciati;
  }

  public Boolean getFlgSoloSubImpSubCrono() {
    return this.flgSoloSubImpSubCrono;
  }

  public void setFlgSoloSubImpSubCrono(Boolean flgSoloSubImpSubCrono) {
    this.flgSoloSubImpSubCrono = flgSoloSubImpSubCrono;
  }

  public String getTipoAttoInDeliberaPEG() {
    return this.tipoAttoInDeliberaPEG;
  }

  public void setTipoAttoInDeliberaPEG(String tipoAttoInDeliberaPEG) {
    this.tipoAttoInDeliberaPEG = tipoAttoInDeliberaPEG;
  }

  public String getTipoAffidamento() {
    return this.tipoAffidamento;
  }

  public void setTipoAffidamento(String tipoAffidamento) {
    this.tipoAffidamento = tipoAffidamento;
  }

  public String getNormRifAffidamento() {
    return this.normRifAffidamento;
  }

  public void setNormRifAffidamento(String normRifAffidamento) {
    this.normRifAffidamento = normRifAffidamento;
  }

  public String getRespAffidamento() {
    return this.respAffidamento;
  }

  public void setRespAffidamento(String respAffidamento) {
    this.respAffidamento = respAffidamento;
  }

  public String getMateriaTipoAtto() {
    return this.materiaTipoAtto;
  }

  public void setMateriaTipoAtto(String materiaTipoAtto) {
    this.materiaTipoAtto = materiaTipoAtto;
  }

  public String getDesMateriaTipoAtto() {
    return this.desMateriaTipoAtto;
  }

  public void setDesMateriaTipoAtto(String desMateriaTipoAtto) {
    this.desMateriaTipoAtto = desMateriaTipoAtto;
  }

  public Boolean getFlgFondiEuropeiPON() {
    return this.flgFondiEuropeiPON;
  }

  public void setFlgFondiEuropeiPON(Boolean flgFondiEuropeiPON) {
    this.flgFondiEuropeiPON = flgFondiEuropeiPON;
  }

  public String getFlgLLPP() {
    return this.flgLLPP;
  }

  public void setFlgLLPP(String flgLLPP) {
    this.flgLLPP = flgLLPP;
  }

  public String getAnnoProgettoLLPP() {
    return this.annoProgettoLLPP;
  }

  public void setAnnoProgettoLLPP(String annoProgettoLLPP) {
    this.annoProgettoLLPP = annoProgettoLLPP;
  }

  public String getNumProgettoLLPP() {
    return this.numProgettoLLPP;
  }

  public void setNumProgettoLLPP(String numProgettoLLPP) {
    this.numProgettoLLPP = numProgettoLLPP;
  }

  public String getFlgBeniServizi() {
    return this.flgBeniServizi;
  }

  public void setFlgBeniServizi(String flgBeniServizi) {
    this.flgBeniServizi = flgBeniServizi;
  }

  public String getAnnoProgettoBeniServizi() {
    return this.annoProgettoBeniServizi;
  }

  public void setAnnoProgettoBeniServizi(String annoProgettoBeniServizi) {
    this.annoProgettoBeniServizi = annoProgettoBeniServizi;
  }

  public String getNumProgettoBeniServizi() {
    return this.numProgettoBeniServizi;
  }

  public void setNumProgettoBeniServizi(String numProgettoBeniServizi) {
    this.numProgettoBeniServizi = numProgettoBeniServizi;
  }

  public Boolean getFlgDecretoReggio() {
    return this.flgDecretoReggio;
  }

  public void setFlgDecretoReggio(Boolean flgDecretoReggio) {
    this.flgDecretoReggio = flgDecretoReggio;
  }

  public Boolean getFlgAvvocatura() {
    return this.flgAvvocatura;
  }

  public void setFlgAvvocatura(Boolean flgAvvocatura) {
    this.flgAvvocatura = flgAvvocatura;
  }

  public List<DestVantaggioBean> getListaDestVantaggio() {
    return this.listaDestVantaggio;
  }

  public void setListaDestVantaggio(List<DestVantaggioBean> listaDestVantaggio) {
    this.listaDestVantaggio = listaDestVantaggio;
  }

  public Boolean getFlgAdottanteUnicoRespPEG() {
    return this.flgAdottanteUnicoRespPEG;
  }

  public void setFlgAdottanteUnicoRespPEG(Boolean flgAdottanteUnicoRespPEG) {
    this.flgAdottanteUnicoRespPEG = flgAdottanteUnicoRespPEG;
  }

  public List<ResponsabilePEGBean> getListaResponsabiliPEG() {
    return this.listaResponsabiliPEG;
  }

  public void setListaResponsabiliPEG(List<ResponsabilePEGBean> listaResponsabiliPEG) {
    this.listaResponsabiliPEG = listaResponsabiliPEG;
  }

  public String getUfficioDefinizioneSpesa() {
    return this.ufficioDefinizioneSpesa;
  }

  public void setUfficioDefinizioneSpesa(String ufficioDefinizioneSpesa) {
    this.ufficioDefinizioneSpesa = ufficioDefinizioneSpesa;
  }

  public String getCodUfficioDefinizioneSpesa() {
    return this.codUfficioDefinizioneSpesa;
  }

  public void setCodUfficioDefinizioneSpesa(String codUfficioDefinizioneSpesa) {
    this.codUfficioDefinizioneSpesa = codUfficioDefinizioneSpesa;
  }

  public String getDesUfficioDefinizioneSpesa() {
    return this.desUfficioDefinizioneSpesa;
  }

  public void setDesUfficioDefinizioneSpesa(String desUfficioDefinizioneSpesa) {
    this.desUfficioDefinizioneSpesa = desUfficioDefinizioneSpesa;
  }

  public List<SelezionaUOBean> getListaUfficioDefinizioneSpesa() {
    return this.listaUfficioDefinizioneSpesa;
  }

  public void setListaUfficioDefinizioneSpesa(List<SelezionaUOBean> listaUfficioDefinizioneSpesa) {
    this.listaUfficioDefinizioneSpesa = listaUfficioDefinizioneSpesa;
  }

  public String getOpzAssCompSpesa() {
    return this.opzAssCompSpesa;
  }

  public void setOpzAssCompSpesa(String opzAssCompSpesa) {
    this.opzAssCompSpesa = opzAssCompSpesa;
  }

  public Boolean getFlgRichVerificaDiBilancioCorrente() {
    return this.flgRichVerificaDiBilancioCorrente;
  }

  public void setFlgRichVerificaDiBilancioCorrente(Boolean flgRichVerificaDiBilancioCorrente) {
    this.flgRichVerificaDiBilancioCorrente = flgRichVerificaDiBilancioCorrente;
  }

  public Boolean getFlgRichVerificaDiBilancioContoCapitale() {
    return this.flgRichVerificaDiBilancioContoCapitale;
  }

  public void setFlgRichVerificaDiBilancioContoCapitale(Boolean flgRichVerificaDiBilancioContoCapitale) {
    this.flgRichVerificaDiBilancioContoCapitale = flgRichVerificaDiBilancioContoCapitale;
  }

  public Boolean getFlgRichVerificaDiContabilita() {
    return this.flgRichVerificaDiContabilita;
  }

  public void setFlgRichVerificaDiContabilita(Boolean flgRichVerificaDiContabilita) {
    this.flgRichVerificaDiContabilita = flgRichVerificaDiContabilita;
  }

  public Boolean getFlgConVerificaContabilita() {
    return this.flgConVerificaContabilita;
  }

  public void setFlgConVerificaContabilita(Boolean flgConVerificaContabilita) {
    this.flgConVerificaContabilita = flgConVerificaContabilita;
  }

  public Boolean getFlgRichiediParereRevisoriContabili() {
    return this.flgRichiediParereRevisoriContabili;
  }

  public void setFlgRichiediParereRevisoriContabili(Boolean flgRichiediParereRevisoriContabili) {
    this.flgRichiediParereRevisoriContabili = flgRichiediParereRevisoriContabili;
  }

  public List<CIGBean> getListaCIG() {
    return this.listaCIG;
  }

  public void setListaCIG(List<CIGBean> listaCIG) {
    this.listaCIG = listaCIG;
  }

  public String getIdPropostaAMC() {
    return this.idPropostaAMC;
  }

  public void setIdPropostaAMC(String idPropostaAMC) {
    this.idPropostaAMC = idPropostaAMC;
  }

  public Boolean getFlgDettRevocaAtto() {
    return this.flgDettRevocaAtto;
  }

  public void setFlgDettRevocaAtto(Boolean flgDettRevocaAtto) {
    this.flgDettRevocaAtto = flgDettRevocaAtto;
  }

  public Boolean getFlgDatiSensibili() {
    return this.flgDatiSensibili;
  }

  public void setFlgDatiSensibili(Boolean flgDatiSensibili) {
    this.flgDatiSensibili = flgDatiSensibili;
  }

  public List<SimpleKeyValueBean> getListaVociPEGNoVerifDisp() {
    return this.listaVociPEGNoVerifDisp;
  }

  public void setListaVociPEGNoVerifDisp(List<SimpleKeyValueBean> listaVociPEGNoVerifDisp) {
    this.listaVociPEGNoVerifDisp = listaVociPEGNoVerifDisp;
  }

  public List<SimpleValueBean> getListaRiferimentiNormativi() {
    return this.listaRiferimentiNormativi;
  }

  public void setListaRiferimentiNormativi(List<SimpleValueBean> listaRiferimentiNormativi) {
    this.listaRiferimentiNormativi = listaRiferimentiNormativi;
  }

  public String getAttiPresupposti() {
    return this.attiPresupposti;
  }

  public void setAttiPresupposti(String attiPresupposti) {
    this.attiPresupposti = attiPresupposti;
  }

  public String getMotivazioni() {
    return this.motivazioni;
  }

  public void setMotivazioni(String motivazioni) {
    this.motivazioni = motivazioni;
  }

  public String getPremessa() {
    return this.premessa;
  }

  public void setPremessa(String premessa) {
    this.premessa = premessa;
  }

  public String getDispositivo() {
    return this.dispositivo;
  }

  public void setDispositivo(String dispositivo) {
    this.dispositivo = dispositivo;
  }

  public String getLoghiAggiuntiviDispositivo() {
    return this.loghiAggiuntiviDispositivo;
  }

  public void setLoghiAggiuntiviDispositivo(String loghiAggiuntiviDispositivo) {
    this.loghiAggiuntiviDispositivo = loghiAggiuntiviDispositivo;
  }

  public Boolean getFlgPubblicaAllegatiSeparati() {
    return this.flgPubblicaAllegatiSeparati;
  }

  public void setFlgPubblicaAllegatiSeparati(Boolean flgPubblicaAllegatiSeparati) {
    this.flgPubblicaAllegatiSeparati = flgPubblicaAllegatiSeparati;
  }

  public String getFlgPrivacy()
  {
    return this.flgPrivacy;
  }

  public void setFlgPrivacy(String flgPrivacy) {
    this.flgPrivacy = flgPrivacy;
  }

  public String getFlgPubblAlbo() {
    return this.flgPubblAlbo;
  }

  public void setFlgPubblAlbo(String flgPubblAlbo) {
    this.flgPubblAlbo = flgPubblAlbo;
  }

  public String getNumGiorniPubblAlbo() {
    return this.numGiorniPubblAlbo;
  }

  public void setNumGiorniPubblAlbo(String numGiorniPubblAlbo) {
    this.numGiorniPubblAlbo = numGiorniPubblAlbo;
  }

  public String getTipoDecorrenzaPubblAlbo() {
    return this.tipoDecorrenzaPubblAlbo;
  }

  public void setTipoDecorrenzaPubblAlbo(String tipoDecorrenzaPubblAlbo) {
    this.tipoDecorrenzaPubblAlbo = tipoDecorrenzaPubblAlbo;
  }

  public Date getDataPubblAlboDal() {
    return this.dataPubblAlboDal;
  }

  public void setDataPubblAlboDal(Date dataPubblAlboDal) {
    this.dataPubblAlboDal = dataPubblAlboDal;
  }

  public Boolean getFlgUrgentePubblAlbo() {
    return this.flgUrgentePubblAlbo;
  }

  public void setFlgUrgentePubblAlbo(Boolean flgUrgentePubblAlbo) {
    this.flgUrgentePubblAlbo = flgUrgentePubblAlbo;
  }

  public Date getDataPubblAlboEntro() {
    return this.dataPubblAlboEntro;
  }

  public void setDataPubblAlboEntro(Date dataPubblAlboEntro) {
    this.dataPubblAlboEntro = dataPubblAlboEntro;
  }

  public String getFlgPubblAmmTrasp() {
    return this.flgPubblAmmTrasp;
  }

  public void setFlgPubblAmmTrasp(String flgPubblAmmTrasp) {
    this.flgPubblAmmTrasp = flgPubblAmmTrasp;
  }

  public String getSezionePubblAmmTrasp() {
    return this.sezionePubblAmmTrasp;
  }

  public void setSezionePubblAmmTrasp(String sezionePubblAmmTrasp) {
    this.sezionePubblAmmTrasp = sezionePubblAmmTrasp;
  }

  public String getSottoSezionePubblAmmTrasp() {
    return this.sottoSezionePubblAmmTrasp;
  }

  public void setSottoSezionePubblAmmTrasp(String sottoSezionePubblAmmTrasp) {
    this.sottoSezionePubblAmmTrasp = sottoSezionePubblAmmTrasp;
  }

  public String getFlgPubblBUR() {
    return this.flgPubblBUR;
  }

  public void setFlgPubblBUR(String flgPubblBUR) {
    this.flgPubblBUR = flgPubblBUR;
  }

  public String getAnnoTerminePubblBUR() {
    return this.annoTerminePubblBUR;
  }

  public void setAnnoTerminePubblBUR(String annoTerminePubblBUR) {
    this.annoTerminePubblBUR = annoTerminePubblBUR;
  }

  public String getTipoDecorrenzaPubblBUR() {
    return this.tipoDecorrenzaPubblBUR;
  }

  public void setTipoDecorrenzaPubblBUR(String tipoDecorrenzaPubblBUR) {
    this.tipoDecorrenzaPubblBUR = tipoDecorrenzaPubblBUR;
  }

  public Date getDataPubblBURDal() {
    return this.dataPubblBURDal;
  }

  public void setDataPubblBURDal(Date dataPubblBURDal) {
    this.dataPubblBURDal = dataPubblBURDal;
  }

  public Boolean getFlgUrgentePubblBUR() {
    return this.flgUrgentePubblBUR;
  }

  public void setFlgUrgentePubblBUR(Boolean flgUrgentePubblBUR) {
    this.flgUrgentePubblBUR = flgUrgentePubblBUR;
  }

  public Date getDataPubblBUREntro() {
    return this.dataPubblBUREntro;
  }

  public void setDataPubblBUREntro(Date dataPubblBUREntro) {
    this.dataPubblBUREntro = dataPubblBUREntro;
  }

  public String getFlgPubblNotiziario() {
    return this.flgPubblNotiziario;
  }

  public void setFlgPubblNotiziario(String flgPubblNotiziario) {
    this.flgPubblNotiziario = flgPubblNotiziario;
  }

  public Date getDataEsecutivitaDal() {
    return this.dataEsecutivitaDal;
  }

  public void setDataEsecutivitaDal(Date dataEsecutivitaDal) {
    this.dataEsecutivitaDal = dataEsecutivitaDal;
  }

  public Boolean getFlgImmediatamenteEseguibile() {
    return this.flgImmediatamenteEseguibile;
  }

  public void setFlgImmediatamenteEseguibile(Boolean flgImmediatamenteEseguibile) {
    this.flgImmediatamenteEseguibile = flgImmediatamenteEseguibile;
  }

  public String getMotiviImmediatamenteEseguibile() {
    return this.motiviImmediatamenteEseguibile;
  }

  public void setMotiviImmediatamenteEseguibile(String motiviImmediatamenteEseguibile) {
    this.motiviImmediatamenteEseguibile = motiviImmediatamenteEseguibile;
  }

  public String getListaDestNotificaAtto() {
    return this.listaDestNotificaAtto;
  }

  public void setListaDestNotificaAtto(String listaDestNotificaAtto) {
    this.listaDestNotificaAtto = listaDestNotificaAtto;
  }

  public String getPrenotazioneSpesaSIBDiCorrente() {
    return this.prenotazioneSpesaSIBDiCorrente;
  }

  public void setPrenotazioneSpesaSIBDiCorrente(String prenotazioneSpesaSIBDiCorrente) {
    this.prenotazioneSpesaSIBDiCorrente = prenotazioneSpesaSIBDiCorrente;
  }

  public String getModalitaInvioDatiSpesaARagioneriaCorrente() {
    return this.modalitaInvioDatiSpesaARagioneriaCorrente;
  }

  public void setModalitaInvioDatiSpesaARagioneriaCorrente(String modalitaInvioDatiSpesaARagioneriaCorrente) {
    this.modalitaInvioDatiSpesaARagioneriaCorrente = modalitaInvioDatiSpesaARagioneriaCorrente;
  }

  public List<DatiContabiliBean> getListaDatiContabiliSIBCorrente() {
    return this.listaDatiContabiliSIBCorrente;
  }

  public void setListaDatiContabiliSIBCorrente(List<DatiContabiliBean> listaDatiContabiliSIBCorrente) {
    this.listaDatiContabiliSIBCorrente = listaDatiContabiliSIBCorrente;
  }

  public String getErrorMessageDatiContabiliSIBCorrente() {
    return this.errorMessageDatiContabiliSIBCorrente;
  }

  public void setErrorMessageDatiContabiliSIBCorrente(String errorMessageDatiContabiliSIBCorrente) {
    this.errorMessageDatiContabiliSIBCorrente = errorMessageDatiContabiliSIBCorrente;
  }

  public List<DatiContabiliBean> getListaInvioDatiSpesaCorrente() {
    return this.listaInvioDatiSpesaCorrente;
  }

  public void setListaInvioDatiSpesaCorrente(List<DatiContabiliBean> listaInvioDatiSpesaCorrente) {
    this.listaInvioDatiSpesaCorrente = listaInvioDatiSpesaCorrente;
  }

  public String getNomeFileTracciatoXlsCorrente()
  {
    return this.nomeFileTracciatoXlsCorrente;
  }

  public void setNomeFileTracciatoXlsCorrente(String nomeFileTracciatoXlsCorrente) {
    this.nomeFileTracciatoXlsCorrente = nomeFileTracciatoXlsCorrente;
  }

  public String getUriFileTracciatoXlsCorrente() {
    return this.uriFileTracciatoXlsCorrente;
  }

  public void setUriFileTracciatoXlsCorrente(String uriFileTracciatoXlsCorrente) {
    this.uriFileTracciatoXlsCorrente = uriFileTracciatoXlsCorrente;
  }

  public String getNoteCorrente()
  {
    return this.noteCorrente;
  }

  public void setNoteCorrente(String noteCorrente) {
    this.noteCorrente = noteCorrente;
  }

  public String getModalitaInvioDatiSpesaARagioneriaContoCapitale() {
    return this.modalitaInvioDatiSpesaARagioneriaContoCapitale;
  }

  public void setModalitaInvioDatiSpesaARagioneriaContoCapitale(String modalitaInvioDatiSpesaARagioneriaContoCapitale) {
    this.modalitaInvioDatiSpesaARagioneriaContoCapitale = modalitaInvioDatiSpesaARagioneriaContoCapitale;
  }

  public List<DatiContabiliBean> getListaDatiContabiliSIBContoCapitale() {
    return this.listaDatiContabiliSIBContoCapitale;
  }

  public void setListaDatiContabiliSIBContoCapitale(List<DatiContabiliBean> listaDatiContabiliSIBContoCapitale) {
    this.listaDatiContabiliSIBContoCapitale = listaDatiContabiliSIBContoCapitale;
  }

  public String getErrorMessageDatiContabiliSIBContoCapitale() {
    return this.errorMessageDatiContabiliSIBContoCapitale;
  }

  public void setErrorMessageDatiContabiliSIBContoCapitale(String errorMessageDatiContabiliSIBContoCapitale) {
    this.errorMessageDatiContabiliSIBContoCapitale = errorMessageDatiContabiliSIBContoCapitale;
  }

  public List<DatiContabiliBean> getListaInvioDatiSpesaContoCapitale() {
    return this.listaInvioDatiSpesaContoCapitale;
  }

  public void setListaInvioDatiSpesaContoCapitale(List<DatiContabiliBean> listaInvioDatiSpesaContoCapitale) {
    this.listaInvioDatiSpesaContoCapitale = listaInvioDatiSpesaContoCapitale;
  }

  public String getNomeFileTracciatoXlsContoCapitale()
  {
    return this.nomeFileTracciatoXlsContoCapitale;
  }

  public void setNomeFileTracciatoXlsContoCapitale(String nomeFileTracciatoXlsContoCapitale) {
    this.nomeFileTracciatoXlsContoCapitale = nomeFileTracciatoXlsContoCapitale;
  }

  public String getUriFileTracciatoXlsContoCapitale() {
    return this.uriFileTracciatoXlsContoCapitale;
  }

  public void setUriFileTracciatoXlsContoCapitale(String uriFileTracciatoXlsContoCapitale) {
    this.uriFileTracciatoXlsContoCapitale = uriFileTracciatoXlsContoCapitale;
  }

  public String getNoteContoCapitale()
  {
    return this.noteContoCapitale;
  }

  public void setNoteContoCapitale(String noteContoCapitale) {
    this.noteContoCapitale = noteContoCapitale;
  }

  public List<DatiSpesaAnnualiXDetPersonaleXmlBean> getListaDatiSpesaAnnualiXDetPersonale() {
    return this.listaDatiSpesaAnnualiXDetPersonale;
  }

  public void setListaDatiSpesaAnnualiXDetPersonale(List<DatiSpesaAnnualiXDetPersonaleXmlBean> listaDatiSpesaAnnualiXDetPersonale)
  {
    this.listaDatiSpesaAnnualiXDetPersonale = listaDatiSpesaAnnualiXDetPersonale;
  }

  public String getCapitoloDatiSpesaAnnuaXDetPers() {
    return this.capitoloDatiSpesaAnnuaXDetPers;
  }

  public void setCapitoloDatiSpesaAnnuaXDetPers(String capitoloDatiSpesaAnnuaXDetPers) {
    this.capitoloDatiSpesaAnnuaXDetPers = capitoloDatiSpesaAnnuaXDetPers;
  }

  public String getArticoloDatiSpesaAnnuaXDetPers() {
    return this.articoloDatiSpesaAnnuaXDetPers;
  }

  public void setArticoloDatiSpesaAnnuaXDetPers(String articoloDatiSpesaAnnuaXDetPers) {
    this.articoloDatiSpesaAnnuaXDetPers = articoloDatiSpesaAnnuaXDetPers;
  }

  public String getNumeroDatiSpesaAnnuaXDetPers() {
    return this.numeroDatiSpesaAnnuaXDetPers;
  }

  public void setNumeroDatiSpesaAnnuaXDetPers(String numeroDatiSpesaAnnuaXDetPers) {
    this.numeroDatiSpesaAnnuaXDetPers = numeroDatiSpesaAnnuaXDetPers;
  }

  public String getImportoDatiSpesaAnnuaXDetPers() {
    return this.importoDatiSpesaAnnuaXDetPers;
  }

  public void setImportoDatiSpesaAnnuaXDetPers(String importoDatiSpesaAnnuaXDetPers) {
    this.importoDatiSpesaAnnuaXDetPers = importoDatiSpesaAnnuaXDetPers;
  }

  public String getEventoSIB() {
    return this.eventoSIB;
  }

  public void setEventoSIB(String eventoSIB) {
    this.eventoSIB = eventoSIB;
  }

  public String getEsitoEventoSIB() {
    return this.esitoEventoSIB;
  }

  public void setEsitoEventoSIB(String esitoEventoSIB) {
    this.esitoEventoSIB = esitoEventoSIB;
  }

  public Date getDataEventoSIB() {
    return this.dataEventoSIB;
  }

  public void setDataEventoSIB(Date dataEventoSIB) {
    this.dataEventoSIB = dataEventoSIB;
  }

  public String getErrMsgEventoSIB() {
    return this.errMsgEventoSIB;
  }

  public void setErrMsgEventoSIB(String errMsgEventoSIB) {
    this.errMsgEventoSIB = errMsgEventoSIB;
  }

  public String getIdUoDirAdottanteSIB() {
    return this.idUoDirAdottanteSIB;
  }

  public void setIdUoDirAdottanteSIB(String idUoDirAdottanteSIB) {
    this.idUoDirAdottanteSIB = idUoDirAdottanteSIB;
  }

  public String getCodUoDirAdottanteSIB() {
    return this.codUoDirAdottanteSIB;
  }

  public void setCodUoDirAdottanteSIB(String codUoDirAdottanteSIB) {
    this.codUoDirAdottanteSIB = codUoDirAdottanteSIB;
  }

  public String getDesUoDirAdottanteSIB() {
    return this.desUoDirAdottanteSIB;
  }

  public void setDesUoDirAdottanteSIB(String desUoDirAdottanteSIB) {
    this.desUoDirAdottanteSIB = desUoDirAdottanteSIB;
  }

  public String getEventoContabilia() {
    return this.eventoContabilia;
  }

  public void setEventoContabilia(String eventoContabilia) {
    this.eventoContabilia = eventoContabilia;
  }

  public String getEsitoEventoContabilia() {
    return this.esitoEventoContabilia;
  }

  public void setEsitoEventoContabilia(String esitoEventoContabilia) {
    this.esitoEventoContabilia = esitoEventoContabilia;
  }

  public Date getDataEventoContabilia() {
    return this.dataEventoContabilia;
  }

  public void setDataEventoContabilia(Date dataEventoContabilia) {
    this.dataEventoContabilia = dataEventoContabilia;
  }

  public String getErrMsgEventoContabilia() {
    return this.errMsgEventoContabilia;
  }

  public void setErrMsgEventoContabilia(String errMsgEventoContabilia) {
    this.errMsgEventoContabilia = errMsgEventoContabilia;
  }

  public String getDirigenteResponsabileContabilia() {
    return this.dirigenteResponsabileContabilia;
  }

  public void setDirigenteResponsabileContabilia(String dirigenteResponsabileContabilia) {
    this.dirigenteResponsabileContabilia = dirigenteResponsabileContabilia;
  }

  public String getCentroResponsabilitaContabilia() {
    return this.centroResponsabilitaContabilia;
  }

  public void setCentroResponsabilitaContabilia(String centroResponsabilitaContabilia) {
    this.centroResponsabilitaContabilia = centroResponsabilitaContabilia;
  }

  public String getCentroCostoContabilia() {
    return this.centroCostoContabilia;
  }

  public void setCentroCostoContabilia(String centroCostoContabilia) {
    this.centroCostoContabilia = centroCostoContabilia;
  }

  public List<MovimentiContabiliaXmlBean> getListaMovimentiContabilia() {
    return this.listaMovimentiContabilia;
  }

  public void setListaMovimentiContabilia(List<MovimentiContabiliaXmlBean> listaMovimentiContabilia) {
    this.listaMovimentiContabilia = listaMovimentiContabilia;
  }

  public String getErrorMessageMovimentiContabilia() {
    return this.errorMessageMovimentiContabilia;
  }

  public void setErrorMessageMovimentiContabilia(String errorMessageMovimentiContabilia) {
    this.errorMessageMovimentiContabilia = errorMessageMovimentiContabilia;
  }

  public String getEventoSICRA() {
    return this.eventoSICRA;
  }

  public void setEventoSICRA(String eventoSICRA) {
    this.eventoSICRA = eventoSICRA;
  }

  public String getEsitoEventoSICRA() {
    return this.esitoEventoSICRA;
  }

  public void setEsitoEventoSICRA(String esitoEventoSICRA) {
    this.esitoEventoSICRA = esitoEventoSICRA;
  }

  public Date getDataEventoSICRA() {
    return this.dataEventoSICRA;
  }

  public void setDataEventoSICRA(Date dataEventoSICRA) {
    this.dataEventoSICRA = dataEventoSICRA;
  }

  public String getErrMsgEventoSICRA() {
    return this.errMsgEventoSICRA;
  }

  public void setErrMsgEventoSICRA(String errMsgEventoSICRA) {
    this.errMsgEventoSICRA = errMsgEventoSICRA;
  }

  public List<MovimentiContabiliSICRABean> getListaInvioMovimentiContabiliSICRA() {
    return this.listaInvioMovimentiContabiliSICRA;
  }

  public void setListaInvioMovimentiContabiliSICRA(List<MovimentiContabiliSICRABean> listaInvioMovimentiContabiliSICRA) {
    this.listaInvioMovimentiContabiliSICRA = listaInvioMovimentiContabiliSICRA;
  }

  public List<MovimentiContabiliSICRABean> getListaMovimentiSICRAToDelete() {
    return this.listaMovimentiSICRAToDelete;
  }

  public void setListaMovimentiSICRAToDelete(List<MovimentiContabiliSICRABean> listaMovimentiSICRAToDelete) {
    this.listaMovimentiSICRAToDelete = listaMovimentiSICRAToDelete;
  }

  public List<MovimentiContabiliSICRABean> getListaMovimentiSICRAToInsert() {
    return this.listaMovimentiSICRAToInsert;
  }

  public void setListaMovimentiSICRAToInsert(List<MovimentiContabiliSICRABean> listaMovimentiSICRAToInsert) {
    this.listaMovimentiSICRAToInsert = listaMovimentiSICRAToInsert;
  }

  public String getEsitoSetMovimentiAttoSICRA() {
    return this.esitoSetMovimentiAttoSICRA;
  }

  public void setEsitoSetMovimentiAttoSICRA(String esitoSetMovimentiAttoSICRA) {
    this.esitoSetMovimentiAttoSICRA = esitoSetMovimentiAttoSICRA;
  }

  public String getMessaggioWarning() {
    return this.messaggioWarning;
  }

  public void setMessaggioWarning(String messaggioWarning) {
    this.messaggioWarning = messaggioWarning;
  }

  public String getCodXSalvataggioConWarning() {
    return this.codXSalvataggioConWarning;
  }

  public void setCodXSalvataggioConWarning(String codXSalvataggioConWarning) {
    this.codXSalvataggioConWarning = codXSalvataggioConWarning;
  }

  public Map<String, Object> getValori() {
    return this.valori;
  }

  public void setValori(Map<String, Object> valori) {
    this.valori = valori;
  }

  public Map<String, String> getTipiValori() {
    return this.tipiValori;
  }

  public void setTipiValori(Map<String, String> tipiValori) {
    this.tipiValori = tipiValori;
  }

  public Map<String, String> getColonneListe() {
    return this.colonneListe;
  }

  public void setColonneListe(Map<String, String> colonneListe) {
    this.colonneListe = colonneListe;
  }

  public String getIdProcess() {
    return this.idProcess;
  }

  public void setIdProcess(String idProcess) {
    this.idProcess = idProcess;
  }

  public String getIdModello() {
    return this.idModello;
  }

  public void setIdModello(String idModello) {
    this.idModello = idModello;
  }

  public String getNomeModello() {
    return this.nomeModello;
  }

  public void setNomeModello(String nomeModello) {
    this.nomeModello = nomeModello;
  }

  public String getDisplayFilenameModello() {
    return this.displayFilenameModello;
  }

  public void setDisplayFilenameModello(String displayFilenameModello) {
    this.displayFilenameModello = displayFilenameModello;
  }

  public String getUriModello() {
    return this.uriModello;
  }

  public void setUriModello(String uriModello) {
    this.uriModello = uriModello;
  }

  public String getTipoModello() {
    return this.tipoModello;
  }

  public void setTipoModello(String tipoModello) {
    this.tipoModello = tipoModello;
  }

  public String getIdModCopertina() {
    return this.idModCopertina;
  }

  public void setIdModCopertina(String idModCopertina) {
    this.idModCopertina = idModCopertina;
  }

  public String getNomeModCopertina() {
    return this.nomeModCopertina;
  }

  public void setNomeModCopertina(String nomeModCopertina) {
    this.nomeModCopertina = nomeModCopertina;
  }

  public String getUriModCopertina() {
    return this.uriModCopertina;
  }

  public void setUriModCopertina(String uriModCopertina) {
    this.uriModCopertina = uriModCopertina;
  }

  public String getTipoModCopertina() {
    return this.tipoModCopertina;
  }

  public void setTipoModCopertina(String tipoModCopertina) {
    this.tipoModCopertina = tipoModCopertina;
  }

  public String getIdModCopertinaFinale() {
    return this.idModCopertinaFinale;
  }

  public void setIdModCopertinaFinale(String idModCopertinaFinale) {
    this.idModCopertinaFinale = idModCopertinaFinale;
  }

  public String getNomeModCopertinaFinale() {
    return this.nomeModCopertinaFinale;
  }

  public void setNomeModCopertinaFinale(String nomeModCopertinaFinale) {
    this.nomeModCopertinaFinale = nomeModCopertinaFinale;
  }

  public String getUriModCopertinaFinale() {
    return this.uriModCopertinaFinale;
  }

  public void setUriModCopertinaFinale(String uriModCopertinaFinale) {
    this.uriModCopertinaFinale = uriModCopertinaFinale;
  }

  public String getTipoModCopertinaFinale() {
    return this.tipoModCopertinaFinale;
  }

  public void setTipoModCopertinaFinale(String tipoModCopertinaFinale) {
    this.tipoModCopertinaFinale = tipoModCopertinaFinale;
  }

  public Boolean getFlgAppendiceDaUnire() {
    return this.flgAppendiceDaUnire;
  }

  public void setFlgAppendiceDaUnire(Boolean flgAppendiceDaUnire) {
    this.flgAppendiceDaUnire = flgAppendiceDaUnire;
  }

  public String getIdModAppendice() {
    return this.idModAppendice;
  }

  public void setIdModAppendice(String idModAppendice) {
    this.idModAppendice = idModAppendice;
  }

  public String getNomeModAppendice() {
    return this.nomeModAppendice;
  }

  public void setNomeModAppendice(String nomeModAppendice) {
    this.nomeModAppendice = nomeModAppendice;
  }

  public String getUriModAppendice() {
    return this.uriModAppendice;
  }

  public void setUriModAppendice(String uriModAppendice) {
    this.uriModAppendice = uriModAppendice;
  }

  public String getTipoModAppendice() {
    return this.tipoModAppendice;
  }

  public void setTipoModAppendice(String tipoModAppendice) {
    this.tipoModAppendice = tipoModAppendice;
  }

  public Boolean getFlgMostraDatiSensibili() {
    return this.flgMostraDatiSensibili;
  }

  public void setFlgMostraDatiSensibili(Boolean flgMostraDatiSensibili) {
    this.flgMostraDatiSensibili = flgMostraDatiSensibili;
  }

  public Boolean getFlgMostraOmissisBarrati() {
    return this.flgMostraOmissisBarrati;
  }

  public void setFlgMostraOmissisBarrati(Boolean flgMostraOmissisBarrati) {
    this.flgMostraOmissisBarrati = flgMostraOmissisBarrati;
  }

  public String getDesDirezioneProponente() {
    return this.desDirezioneProponente;
  }

  public void setDesDirezioneProponente(String desDirezioneProponente) {
    this.desDirezioneProponente = desDirezioneProponente;
  }

  public List<EmendamentoBean> getListaEmendamenti()
  {
    return this.listaEmendamenti;
  }

  public void setListaEmendamenti(List<EmendamentoBean> listaEmendamenti) {
    this.listaEmendamenti = listaEmendamenti;
  }

  public String getIdUoAlboReggio()
  {
    return this.idUoAlboReggio;
  }

  public void setIdUoAlboReggio(String idUoAlboReggio) {
    this.idUoAlboReggio = idUoAlboReggio;
  }
}