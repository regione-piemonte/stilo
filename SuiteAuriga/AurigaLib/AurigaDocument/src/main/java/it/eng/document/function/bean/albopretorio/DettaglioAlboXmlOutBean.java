/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.AllegatiOutBean;
import it.eng.document.function.bean.AltreUbicazioniBean;
import it.eng.document.function.bean.AltriRiferimentiBean;
import it.eng.document.function.bean.AssegnatariOutBean;
import it.eng.document.function.bean.AssessoreProponenteBean;
import it.eng.document.function.bean.AttiRichiestiXMLBean;
import it.eng.document.function.bean.ClassFascTitolarioOutBean;
import it.eng.document.function.bean.CollocazioneFisica;
import it.eng.document.function.bean.ConsigliereProponenteBean;
import it.eng.document.function.bean.ContraentiOutBean;
import it.eng.document.function.bean.DatiContabiliXmlBean;
import it.eng.document.function.bean.DatiSpesaAnnualiXDetPersonaleXmlBean;
import it.eng.document.function.bean.DestInvioCCOutBean;
import it.eng.document.function.bean.DestNotificaAttoXmlBean;
import it.eng.document.function.bean.DestinatariOutBean;
import it.eng.document.function.bean.DirRespRegTecnicaBean;
import it.eng.document.function.bean.DocCollegatoOutBean;
import it.eng.document.function.bean.DocumentoCollegato;
import it.eng.document.function.bean.EmailProvBean;
import it.eng.document.function.bean.FilePrimarioOutBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.FolderCustom;
import it.eng.document.function.bean.KeyValueBean;
import it.eng.document.function.bean.MittentiDocumentoOutBean;
import it.eng.document.function.bean.MovimentiContabiliSICRAXmlBean;
import it.eng.document.function.bean.MovimentiContabiliaXmlBean;
import it.eng.document.function.bean.RegEmergenzaOutBean;
import it.eng.document.function.bean.RegNumPrincipale;
import it.eng.document.function.bean.RespDiConcertoBean;
import it.eng.document.function.bean.RespSpesaBean;
import it.eng.document.function.bean.RespVistiConformitaBean;
import it.eng.document.function.bean.ScrivaniaDirProponenteBean;
import it.eng.document.function.bean.ScrivaniaEstensoreBean;
import it.eng.document.function.bean.SoggettoEsternoBean;
import it.eng.document.function.bean.TipoProvenienza;
import it.eng.document.function.bean.ValueBean;

@XmlRootElement
public class DettaglioAlboXmlOutBean implements Serializable {

	private static final long serialVersionUID = -483078932590126871L;

	@XmlVariabile(nome = "#IdDocType", tipo = TipoVariabile.SEMPLICE)
	private String tipoDocumento;

	@XmlVariabile(nome = "#NomeDocType", tipo = TipoVariabile.SEMPLICE)
	private String nomeTipoDocumento;

	@XmlVariabile(nome = "#FlgTipoDocConVie", tipo = TipoVariabile.SEMPLICE)
	private Flag flgTipoDocConVie;

	@XmlVariabile(nome = "#FlgOggettoNonObblig", tipo = TipoVariabile.SEMPLICE)
	private Flag flgOggettoNonObblig;

	@XmlVariabile(nome = "#FlgTipoProtModulo", tipo = TipoVariabile.SEMPLICE)
	private String flgTipoProtModulo;

	@XmlVariabile(nome = "RowidDoc", tipo = TipoVariabile.SEMPLICE)
	private String rowidDoc;

	@XmlVariabile(nome = "#IdProcess", tipo = TipoVariabile.SEMPLICE)
	private String idProcess;

	@XmlVariabile(nome = "#EstremiProcess", tipo = TipoVariabile.SEMPLICE)
	private String estremiProcess;

	@XmlVariabile(nome = "#RegNumPrincipale", tipo = TipoVariabile.NESTED)
	private RegNumPrincipale estremiRegistrazione;

	@XmlVariabile(nome = "#DesOgg", tipo = TipoVariabile.SEMPLICE)
	private String oggetto;

	@XmlVariabile(nome = "#FlgTipoProv", tipo = TipoVariabile.SEMPLICE)
	private TipoProvenienza flgTipoProv;

	@XmlVariabile(nome = "#TsArrivo", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataArrivo;

	@XmlVariabile(nome = "#DtStesura", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataStesura;

	@XmlVariabile(nome = "#IdDocPrimario", tipo = TipoVariabile.SEMPLICE)
	private String idDocPrimario;

	@XmlVariabile(nome = "#FilePrimario", tipo = TipoVariabile.NESTED)
	private FilePrimarioOutBean filePrimario;

	@XmlVariabile(nome = "#EscludiPubblPrimario", tipo = TipoVariabile.SEMPLICE)
	private Flag flgNoPubblPrimario;

	@XmlVariabile(nome = "#DatiSensibiliPrimario", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDatiSensibiliPrimario;

	@XmlVariabile(nome = "PRESSO_CENTRO_POSTA", tipo = TipoVariabile.SEMPLICE)
	private String docPressoCentroPosta;

	@XmlVariabile(nome = "#@MittentiEsterni", tipo = TipoVariabile.LISTA)
	private List<MittentiDocumentoOutBean> mittenti;

	@XmlVariabile(nome = "#@Allegati", tipo = TipoVariabile.LISTA)
	private List<AllegatiOutBean> allegati;

	@XmlVariabile(nome = "#@DocProcessFolder", tipo = TipoVariabile.LISTA)
	private List<AllegatiOutBean> documentiProcFolder;

	@XmlVariabile(nome = "#LivRiservatezza", tipo = TipoVariabile.SEMPLICE)
	private String livelloRiservatezza;

	@XmlVariabile(nome = "#DesLivRiservatezza", tipo = TipoVariabile.SEMPLICE)
	private String descrizioneRiservatezza;

	@XmlVariabile(nome = "#NoteUD", tipo = TipoVariabile.SEMPLICE)
	private String note;
	
	@XmlVariabile(nome = "NOTE_MANCANZA_FILE", tipo = TipoVariabile.SEMPLICE)
	private String noteMancanzaFile;
	
	@XmlVariabile(nome = "#LivEvidenza", tipo = TipoVariabile.SEMPLICE)
	private String priorita;

	@XmlVariabile(nome = "#DesLivEvidenza", tipo = TipoVariabile.SEMPLICE)
	private String descrizionePrioritaRiservatezza;

	@XmlVariabile(nome = "#CodMezzoTrasm", tipo = TipoVariabile.SEMPLICE)
	private String messoTrasmissione;

	@XmlVariabile(nome = "#NroRaccomandata", tipo = TipoVariabile.SEMPLICE)
	private String nroRaccomandata;

	@XmlVariabile(nome = "#DtRaccomandata", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtRaccomandata;

	@XmlVariabile(nome = "#NroListaRaccomandata", tipo = TipoVariabile.SEMPLICE)
	private String nroListaRaccomandata;

	@XmlVariabile(nome = "#DtTermineRiservatezza", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date termineRiservatezza;

	@XmlVariabile(nome = "#CollocazioneFisica", tipo = TipoVariabile.NESTED)
	private CollocazioneFisica collocazioneFisica;

	@XmlVariabile(nome = "#RifDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	private String rifDocRicevuto;

	@XmlVariabile(nome = "#EstremiRegDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	private String estremiRegDocRicevuto;

	@XmlVariabile(nome = "#AnnoDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	private String annoDocRicevuto;

	@XmlVariabile(nome = "#DtDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtDocRicevuto;

	@XmlVariabile(nome = "#@DestinatariEsterni", tipo = TipoVariabile.LISTA)
	private List<DestinatariOutBean> destinatari;

	@XmlVariabile(nome = "#RegEmergenza", tipo = TipoVariabile.NESTED)
	private RegEmergenzaOutBean regEmergenza;

	@XmlVariabile(nome = "#RegNumDocPrecedente", tipo = TipoVariabile.NESTED)
	private DocCollegatoOutBean docCollegato;

	@XmlVariabile(nome = "#@ClassFascTitolario", tipo = TipoVariabile.LISTA)
	private List<ClassFascTitolarioOutBean> classifichefascicoli;

	@XmlVariabile(nome = "#@FolderCustom", tipo = TipoVariabile.LISTA)
	private List<FolderCustom> folderCustom;

	@XmlVariabile(nome = "#PresentiAssPreselMitt", tipo = TipoVariabile.SEMPLICE)
	private Boolean flgPresentiAssPreselMitt;

	@XmlVariabile(nome = "#@Assegnatari", tipo = TipoVariabile.LISTA)
	private List<AssegnatariOutBean> assegnatari;

	@XmlVariabile(nome = "#@DestInvioCC", tipo = TipoVariabile.LISTA)
	private List<DestInvioCCOutBean> destInvioCC;

	@XmlVariabile(nome = "#@UOCoinvolte", tipo = TipoVariabile.LISTA)
	private List<DestInvioCCOutBean> altreUoCoinvolte;	

	@XmlVariabile(nome = "#AnnAtto.IdProcessType", tipo = TipoVariabile.SEMPLICE)
	private String idTipoProcRevocaAtto;

	@XmlVariabile(nome = "#AnnAtto.NomeProcessType", tipo = TipoVariabile.SEMPLICE)
	private String nomeTipoProcRevocaAtto;

	@XmlVariabile(nome = "#AnnAtto.IdDefFlussoWF", tipo = TipoVariabile.SEMPLICE)
	private String idDefFlussoWFRevocaAtto;

	@XmlVariabile(nome = "#AnnAtto.IdDocTypeProposta", tipo = TipoVariabile.SEMPLICE)
	private String idTipoDocPropostaRevocaAtto;

	@XmlVariabile(nome = "#AnnAtto.NomeDocTypeProposta", tipo = TipoVariabile.SEMPLICE)
	private String nomeTipoDocPropostaRevocaAtto;

	@XmlVariabile(nome = "#AnnAtto.SiglaProposta", tipo = TipoVariabile.SEMPLICE)
	private String siglaPropostaRevocaAtto;

	@XmlVariabile(nome = "#ConDatiAnnullati", tipo = TipoVariabile.SEMPLICE)
	private Boolean conDatiAnnullati;

	@XmlVariabile(nome = "#ListaDatiAnnullati", tipo = TipoVariabile.SEMPLICE)
	private String listaDatiAnnullati;

	@XmlVariabile(nome = "#EmailProv", tipo = TipoVariabile.NESTED)
	private EmailProvBean emailProv;

	@XmlVariabile(nome = "#IdEmailArrivo", tipo = TipoVariabile.SEMPLICE)
	private String idEmailArrivo;

	@XmlVariabile(nome = "#Casella.IsPEC", tipo = TipoVariabile.SEMPLICE)
	private Boolean casellaIsPEC;

	@XmlVariabile(nome = "#EmailArrivoInteroperabile", tipo = TipoVariabile.SEMPLICE)
	private Boolean emailArrivoInteroperabile;

	@XmlVariabile(nome = "#InviataPEC", tipo = TipoVariabile.SEMPLICE)
	private Boolean emailInviataFlgPEC;

	@XmlVariabile(nome = "#InviataPEO", tipo = TipoVariabile.SEMPLICE)
	private Boolean emailInviataFlgPEO;

	@XmlVariabile(nome = "#FlgInteroperabilita", tipo = TipoVariabile.SEMPLICE)
	private Boolean flgInteroperabilita;

	@XmlVariabile(nome = "#IdUserCtrlAmmissib", tipo = TipoVariabile.SEMPLICE)
	private String idUserCtrlAmmissib;

	@XmlVariabile(nome = "#ConfermaAssegnazione.IdUser", tipo = TipoVariabile.SEMPLICE)
	private String idUserConfermaAssegnazione;

	@XmlVariabile(nome = "#ConfermaAssegnazione.DesUser", tipo = TipoVariabile.SEMPLICE)
	private String desUserConfermaAssegnazione;

	@XmlVariabile(nome = "#ConfermaAssegnazione.Username", tipo = TipoVariabile.SEMPLICE)
	private String usernameConfermaAssegnazione;

	@XmlVariabile(nome = "#RicEccezioniInterop", tipo = TipoVariabile.SEMPLICE)
	private String ricEccezioniInterop;

	@XmlVariabile(nome = "#RicAggiornamentiInterop", tipo = TipoVariabile.SEMPLICE)
	private String ricAggiornamentiInterop;

	@XmlVariabile(nome = "#RicAnnullamentiInterop", tipo = TipoVariabile.SEMPLICE)
	private String ricAnnullamentiInterop;

	@XmlVariabile(nome = "#PresenzaDocCollegati", tipo = TipoVariabile.SEMPLICE)
	private String presenzaDocCollegati;

	@XmlVariabile(nome = "#EstremiTrasm", tipo = TipoVariabile.SEMPLICE)
	private String estremiTrasm;

	@XmlVariabile(nome = "#CodStatoDett", tipo = TipoVariabile.SEMPLICE)
	private String codStatoDett;

	@XmlVariabile(nome = "#InviataMailInteroperabile", tipo = TipoVariabile.SEMPLICE)
	private Boolean inviataMailInteroperabile;

	@XmlVariabile(nome = "#RegNumSecondaria.Tipo", tipo = TipoVariabile.SEMPLICE)
	private String tipoRegNumerazioneSecondaria;

	@XmlVariabile(nome = "#RegNumSecondaria.Sigla", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegNumerazioneSecondaria;

	@XmlVariabile(nome = "#RegNumSecondaria.Anno", tipo = TipoVariabile.SEMPLICE)
	private String annoRegNumerazioneSecondaria;

	@XmlVariabile(nome = "#RegNumSecondaria.Nro", tipo = TipoVariabile.SEMPLICE)
	private String numeroRegNumerazioneSecondaria;

	@XmlVariabile(nome = "#RegNumSecondaria.SubNro", tipo = TipoVariabile.SEMPLICE)
	private String subNroRegNumerazioneSecondaria;

	@XmlVariabile(nome = "#RegNumSecondaria.TsRegistrazione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataRegistrazioneNumerazioneSecondaria;

	@XmlVariabile(nome = "#RegNumSecondaria.IdUDAttoAutAnnullamento", tipo = TipoVariabile.SEMPLICE)
	private String idUdAttoAutAnnRegNumerazioneSecondaria;

	@XmlVariabile(nome = "#RegNumSecondaria.DesGruppo", tipo = TipoVariabile.SEMPLICE)
	private String regNumSecondariaDesGruppo;

	@XmlVariabile(nome = "#IdUOProponente", tipo = TipoVariabile.SEMPLICE)
	private String idUOProponente;

	@XmlVariabile(nome = "#LivelliUOProponente", tipo = TipoVariabile.SEMPLICE)
	private String codUOProponente;

	@XmlVariabile(nome = "#DesUOProponente", tipo = TipoVariabile.SEMPLICE)
	private String desUOProponente;

	@XmlVariabile(nome = "#DesDirProponente", tipo = TipoVariabile.SEMPLICE)
	private String desDirProponente;

	@XmlVariabile(nome = "#IdUserRespProc", tipo = TipoVariabile.SEMPLICE)
	private String idUserRespProc;

	@XmlVariabile(nome = "#DesUserRespProc", tipo = TipoVariabile.SEMPLICE)
	private String desUserRespProc;

	@XmlVariabile(nome = "#DataAvvioIterAtto", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAvvioIterAtto;

	@XmlVariabile(nome = "#FunzionarioIstruttoreAtto", tipo = TipoVariabile.SEMPLICE)
	private String funzionarioIstruttoreAtto;

	@XmlVariabile(nome = "#ResonsabileAtto", tipo = TipoVariabile.SEMPLICE)
	private String responsabileAtto;

	@XmlVariabile(nome = "#DirettoreFirmatarioAtto", tipo = TipoVariabile.SEMPLICE)
	private String direttoreFirmatarioAtto;

	@XmlVariabile(nome = "#DataFirmaAtto", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFirmaAtto;

	@XmlVariabile(nome = "#FunzionarioFirmaAtto", tipo = TipoVariabile.SEMPLICE)
	private String funzionarioFirmaAtto;

	@XmlVariabile(nome = "#DtPubblicazione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPubblicazione;

	@XmlVariabile(nome = "#LogIterAtto", tipo = TipoVariabile.SEMPLICE)
	private String logIterAtto;

	@XmlVariabile(nome = "#StatoConservazione", tipo = TipoVariabile.SEMPLICE)
	private String statoConservazione;

	@XmlVariabile(nome = "#TsInvioInConservazione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataInvioInConservazione;

	@XmlVariabile(nome = "#@Contraenti", tipo = TipoVariabile.LISTA)
	private List<ContraentiOutBean> contraenti;

	@XmlVariabile(nome = "#IsCompilazioneModulo", tipo = TipoVariabile.SEMPLICE)
	private Flag flgCompilazioneModulo;

	// Proposta atto
	@XmlVariabile(nome = "#IsPropostaAtto", tipo = TipoVariabile.SEMPLICE)
	private Flag flgPropostaAtto;

	@XmlVariabile(nome = "SceltaIterPropostaDD", tipo = TipoVariabile.SEMPLICE)
	private String sceltaIterPropostaDD;

	@XmlVariabile(nome = "FlgRichParereRevisori", tipo = TipoVariabile.SEMPLICE)
	private String flgRichParereRevisori;

	@XmlVariabile(nome = "SiglaAttoRifSubImpegno", tipo = TipoVariabile.SEMPLICE)
	private String siglaAttoRifSubImpegno;

	@XmlVariabile(nome = "NumeroAttoRifSubImpegno", tipo = TipoVariabile.SEMPLICE)
	private String numeroAttoRifSubImpegno;

	@XmlVariabile(nome = "AnnoAttoRifSubImpegno", tipo = TipoVariabile.SEMPLICE)
	private String annoAttoRifSubImpegno;

	@XmlVariabile(nome = "DataAttoRifSubImpegno", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataAttoRifSubImpegno;
	
	@XmlVariabile(nome = "#@Esibenti", tipo = TipoVariabile.LISTA)
	private List<SoggettoEsternoBean> esibenti;

	@XmlVariabile(nome = "#@Interessati", tipo = TipoVariabile.LISTA)
	private List<SoggettoEsternoBean> interessati;

	@XmlVariabile(nome = "#@AltreUbicazioni", tipo = TipoVariabile.LISTA)
	private List<AltreUbicazioniBean> altreUbicazioni;

	@XmlVariabile(nome = "#@RelazioniVsUD", tipo = TipoVariabile.LISTA)
	private List<DocumentoCollegato> documentiCollegati;

	@XmlVariabile(nome = "#@AltriRiferimenti", tipo = TipoVariabile.LISTA)
	private List<AltriRiferimentiBean> altriRiferimenti;

	@XmlVariabile(nome = "#SezionaleOnlyOne", tipo = TipoVariabile.SEMPLICE)
	private String sezionaleOnlyOne;

	@XmlVariabile(nome = "#NumeroOnlyOne", tipo = TipoVariabile.SEMPLICE)
	private String numeroOnlyOne;

	@XmlVariabile(nome = "#AnnoOnlyOne", tipo = TipoVariabile.SEMPLICE)
	private String annoOnlyOne;

	@XmlVariabile(nome = "FLG_CARICAMENTO_PG_PREGRESSO_DA_GUI", tipo = TipoVariabile.SEMPLICE)
	private Flag flgCaricamentoPGPregressoDaGUI;

	@XmlVariabile(nome = "#Segnatura", tipo = TipoVariabile.SEMPLICE)
	private String segnatura;

	@XmlVariabile(nome = "CodCategoriaRegPrimariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String codCategoriaRegPrimariaRisposta;

	@XmlVariabile(nome = "SiglaRegistroRegPrimariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegistroRegPrimariaRisposta;

	@XmlVariabile(nome = "DesRegistroRegPrimariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String desRegistroRegPrimariaRisposta;

	@XmlVariabile(nome = "CategoriaRepertorioRegPrimariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String categoriaRepertorioRegPrimariaRisposta;

	@XmlVariabile(nome = "CodCategoriaRegSecondariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String codCategoriaRegSecondariaRisposta;

	@XmlVariabile(nome = "SiglaRegistroRegSecondariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegistroRegSecondariaRisposta;

	@XmlVariabile(nome = "DesRegistroRegSecondariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String desRegistroRegSecondariaRisposta;

	@XmlVariabile(nome = "CategoriaRepertorioRegSecondariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String categoriaRepertorioRegSecondariaRisposta;

	// ***************** Richieste accesso atti *******************//

	@XmlVariabile(nome = "#IdScrivaniaRespIstruttoria", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaRespIstruttoria;

	@XmlVariabile(nome = "#DesScrivaniaRespIstruttoria", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaRespIstruttoria;

	@XmlVariabile(nome = "#LivelliUOScrivaniaRespIstruttoria", tipo = TipoVariabile.SEMPLICE)
	private String codUOScrivaniaRespIstruttoria;

	@XmlVariabile(nome = "#CodStatoRichAccessoAtti", tipo = TipoVariabile.SEMPLICE)
	private String codStatoRichAccessoAtti;

	@XmlVariabile(nome = "#DesStatoRichAccessoAtti", tipo = TipoVariabile.SEMPLICE)
	private String desStatoRichAccessoAtti;

	@XmlVariabile(nome = "#IsRichiestaAccessoAtti", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichiestaAccessoAtti;

	@XmlVariabile(nome = "#@AttiRichiesti", tipo = TipoVariabile.LISTA)
	private List<AttiRichiestiXMLBean> attiRichiesti;

	@XmlVariabile(nome = "#@Deleganti", tipo = TipoVariabile.LISTA)
	private List<SoggettoEsternoBean> deleganti;

	@XmlVariabile(nome = "#@IncaricatiRitiro", tipo = TipoVariabile.LISTA)
	private List<SoggettoEsternoBean> incaricatiRitiro;

	@XmlVariabile(nome = "#RichAccessoAttiConRichInterno", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichAccessoAttiConRichInterno;

	@XmlVariabile(nome = "MOTIVO_RICHIESTA_ACCESSO_ATTI", tipo = TipoVariabile.SEMPLICE)
	private String motivoRichiestaAccessoAtti;

	@XmlVariabile(nome = "DETTAGLI_RICHIESTA_ACCESSO_ATTI", tipo = TipoVariabile.SEMPLICE)
	private String dettagliRichiestaAccessoAtti;

	@XmlVariabile(nome = "#DataAppuntamento", tipo = TipoVariabile.SEMPLICE)
	private String dataAppuntamento;

	@XmlVariabile(nome = "#OrarioAppuntamento", tipo = TipoVariabile.SEMPLICE)
	private String orarioAppuntamento;

	@XmlVariabile(nome = "#ProvenienzaAppuntamento", tipo = TipoVariabile.SEMPLICE)
	private String provenienzaAppuntamento;

	@XmlVariabile(nome = "#DataPrelievo", tipo = TipoVariabile.SEMPLICE)
	private String dataPrelievo;

	@XmlVariabile(nome = "#DataRestituzionePrelievo", tipo = TipoVariabile.SEMPLICE)
	private String dataRestituzionePrelievo;

	@XmlVariabile(nome = "#RestituzionePrelievoAttestataDa", tipo = TipoVariabile.SEMPLICE)
	private String restituzionePrelievoAttestataDa;

	@XmlVariabile(nome = "#IdNodoDefaultRicercaAtti", tipo = TipoVariabile.SEMPLICE)
	private String idNodoDefaultRicercaAtti;	

	@XmlVariabile(nome = "#IdUDTrasmessaInUscitaCon", tipo = TipoVariabile.SEMPLICE)
	private String idUDTrasmessaInUscitaCon;

	@XmlVariabile(nome = "#EstremiUDTrasmessaInUscitaCon", tipo = TipoVariabile.SEMPLICE)
	private String estremiUDTrasmessaInUscitaCon;

	// ***************** Nuova proposta atto 2 *******************//

	@XmlVariabile(nome = "FLG_DET_ANN_REVOCA", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDettRevocaAtto;
	
	@XmlVariabile(nome = "COD_PROPOSTA_SIST_CONT", tipo = TipoVariabile.SEMPLICE)
	private String codPropostaSistContabile;

	@XmlVariabile(nome = "#IsDelibera", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDelibera;

	@XmlVariabile(nome = "#ShowDirigentiProponenti", tipo = TipoVariabile.SEMPLICE)
	private String showDirigentiProponenti;

	@XmlVariabile(nome = "#ShowAssessori", tipo = TipoVariabile.SEMPLICE)
	private String showAssessori;

	@XmlVariabile(nome = "#ShowConsiglieri", tipo = TipoVariabile.SEMPLICE)
	private String showConsiglieri;

	@XmlVariabile(nome = "#IdScrivaniaAdottante", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaAdottante;

	@XmlVariabile(nome = "#DesScrivaniaAdottante", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaAdottante;

	@XmlVariabile(nome = "#LivelliUOScrivaniaAdottante", tipo = TipoVariabile.SEMPLICE)
	private String codUOScrivaniaAdottante;

	@XmlVariabile(nome = "#FlgAdottanteAncheRespProc", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAdottanteAncheRespProc;

	@XmlVariabile(nome = "#FlgAdottanteAncheRUP", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAdottanteAncheRUP;

	@XmlVariabile(nome = "#IdScrivaniaRespProc", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaRespProc;

	@XmlVariabile(nome = "#DesScrivaniaRespProc", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaRespProc;

	@XmlVariabile(nome = "#LivelliUOScrivaniaRespProc", tipo = TipoVariabile.SEMPLICE)
	private String codUOScrivaniaRespProc;

	@XmlVariabile(nome = "#FlgRespProcAncheRUP", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRespProcAncheRUP;

	@XmlVariabile(nome = "#IdScrivaniaRUP", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaRUP;

	@XmlVariabile(nome = "#DesScrivaniaRUP", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaRUP;

	@XmlVariabile(nome = "#LivelliUOScrivaniaRUP", tipo = TipoVariabile.SEMPLICE)
	private String codUOScrivaniaRUP;

	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_DIR_SUP", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichiediVistoDirettore;

	@XmlVariabile(nome = "#@RespDiConcerto", tipo = TipoVariabile.LISTA)
	private List<RespDiConcertoBean> responsabiliDiConcerto;

	@XmlVariabile(nome = "#@DirProponenti", tipo = TipoVariabile.LISTA)
	private List<ScrivaniaDirProponenteBean> dirigentiProponenti;

	@XmlVariabile(nome = "#@AssessoriProponenti", tipo = TipoVariabile.LISTA)
	private List<AssessoreProponenteBean> assessoriProponenti;

	@XmlVariabile(nome = "#@ConsiglieriProponenti", tipo = TipoVariabile.LISTA)
	private List<ConsigliereProponenteBean> consiglieriProponenti;

	@XmlVariabile(nome = "#@Estensori", tipo = TipoVariabile.LISTA)
	private List<ScrivaniaEstensoreBean> estensori;

	@XmlVariabile(nome = "#@RespSpesa", tipo = TipoVariabile.LISTA)
	private List<RespSpesaBean> responsabiliSpesa;

	@XmlVariabile(nome = "#IdUOCompSpesa", tipo = TipoVariabile.SEMPLICE)
	private String idUOCompSpesa;

	@XmlVariabile(nome = "#LivelliUOCompSpesa", tipo = TipoVariabile.SEMPLICE)
	private String codUOCompSpesa;

	@XmlVariabile(nome = "#DesUOCompSpesa", tipo = TipoVariabile.SEMPLICE)
	private String desUOCompSpesa;

	@XmlVariabile(nome = "#FlgAdottanteUnicoRespSpesa", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAdottanteUnicoRespSpesa;

	@XmlVariabile(nome = "RIFERIMENTI_NORMATIVI", tipo = TipoVariabile.LISTA)
	private List<ValueBean> riferimentiNormativi;

	@XmlVariabile(nome = "CIG", tipo = TipoVariabile.LISTA)
	private List<ValueBean> listaCIG;

	@XmlVariabile(nome = "ATTI_PRESUPPOSTI", tipo = TipoVariabile.SEMPLICE)
	private String attiPresupposti;

	@XmlVariabile(nome = "MOTIVAZIONI_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String motivazioniAtto;
	
	@XmlVariabile(nome = "PREMESSA_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String premessaAtto;

	@XmlVariabile(nome = "DISPOSITIVO_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String dispositivoAtto;

	@XmlVariabile(nome = "LOGHI_DISPOSITIVO_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String loghiDispositivoAtto;

	@XmlVariabile(nome = "FLG_PUBBL_ALLEGATI_SEPARATA", tipo = TipoVariabile.SEMPLICE)
	private Flag flgPubblicaAllegatiSeparati;
	
	@XmlVariabile(nome = "FLG_ATTO_CON_DATI_RISERVATI", tipo = TipoVariabile.SEMPLICE)
	private String flgPrivacy;
	
	@XmlVariabile(nome = "FLG_PUBBL_ALBO", tipo = TipoVariabile.SEMPLICE)
	private String flgPubblAlbo;
	
	@XmlVariabile(nome = "NRO_GIORNI_PUBBL_ALBO", tipo = TipoVariabile.SEMPLICE)
	private String numGiorniPubblAlbo;

	@XmlVariabile(nome = "TIPO_DECORRENZA_PUBBL_ALBO", tipo = TipoVariabile.SEMPLICE)
	private String tipoDecorrenzaPubblAlbo;
	
	@XmlVariabile(nome = "PUBBL_ALBO_DAL", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPubblAlboDal;

	@XmlVariabile(nome = "FLG_URGENTE_PUBBL_ALBO", tipo = TipoVariabile.SEMPLICE)
	private Flag flgUrgentePubblAlbo;
	
	@XmlVariabile(nome = "PUBBL_ALBO_ENTRO", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPubblAlboEntro;
	
	@XmlVariabile(nome = "FLG_PUBBL_IN_TRASPARENZA", tipo = TipoVariabile.SEMPLICE)
	private String flgPubblAmmTrasp;
	
	@XmlVariabile(nome = "SEZ1_PUBBL_IN_TRASPARENZA", tipo = TipoVariabile.SEMPLICE)
	private String sezionePubblAmmTrasp;
	
	@XmlVariabile(nome = "SEZ2_PUBBL_IN_TRASPARENZA", tipo = TipoVariabile.SEMPLICE)
	private String sottoSezionePubblAmmTrasp;
		
	@XmlVariabile(nome = "FLG_PUBBL_BUR", tipo = TipoVariabile.SEMPLICE)
	private String flgPubblBUR;
	
	@XmlVariabile(nome = "ANNO_TERMINE_PUBBL_BUR", tipo = TipoVariabile.SEMPLICE)
	private String annoTerminePubblBUR;
	
	@XmlVariabile(nome = "TIPO_DECORRENZA_PUBBL_BUR", tipo = TipoVariabile.SEMPLICE)
	private String tipoDecorrenzaPubblBUR;
	
	@XmlVariabile(nome = "PUBBL_BUR_DAL", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPubblBURDal;
	
	@XmlVariabile(nome = "FLG_URGENTE_PUBBL_BUR", tipo = TipoVariabile.SEMPLICE)
	private Flag flgUrgentePubblBUR;
	
	@XmlVariabile(nome = "PUBBL_BUR_ENTRO", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPubblBUREntro;
	
	@XmlVariabile(nome = "#DataEsecutivita", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtEsecutivita;
	
	@XmlVariabile(nome = "FLG_IMMEDIATAMENTE_ESEGUIBILE", tipo = TipoVariabile.SEMPLICE)
	private Flag flgImmediatamenteEseg;
	
	@XmlVariabile(nome = "MOTIVI_IE", tipo = TipoVariabile.SEMPLICE)
	private String motiviImmediatamenteEseguibile;

	@XmlVariabile(nome = "IND_EMAIL_DEST_NOTIFICA_ATTO", tipo = TipoVariabile.LISTA)
	private List<DestNotificaAttoXmlBean> listaDestNotificaAtto;
	

	@XmlVariabile(nome = "PRENOT_SPESA_DI_CORR", tipo = TipoVariabile.SEMPLICE)
	private String prenotazioneSpesaSIBDiCorrente;

	@XmlVariabile(nome = "MOD_INVIO_CONT_CORR", tipo = TipoVariabile.SEMPLICE)
	private String modalitaInvioDatiSpesaARagioneriaCorrente;

	@XmlVariabile(nome = "DATI_CONTABILI_CORR_AMC", tipo = TipoVariabile.LISTA)
	private List<DatiContabiliXmlBean> listaDatiContabiliSIBCorrente;

	@XmlVariabile(nome = "DATI_CONTABILI_CORR_AUR", tipo = TipoVariabile.LISTA)
	private List<DatiContabiliXmlBean> listaInvioDatiSpesaCorrente;

	@XmlVariabile(nome = "XLS_DATI_CONT_CORR", tipo = TipoVariabile.SEMPLICE)
	private String fileXlsCorrente;

	@XmlVariabile(nome = "NOTE_CONT_CORR", tipo = TipoVariabile.SEMPLICE)
	private String noteCorrente;

	@XmlVariabile(nome = "MOD_INVIO_CONT_CCAP", tipo = TipoVariabile.SEMPLICE)
	private String modalitaInvioDatiSpesaARagioneriaContoCapitale;

	@XmlVariabile(nome = "DATI_CONTABILI_CCAP_AMC", tipo = TipoVariabile.LISTA)
	private List<DatiContabiliXmlBean> listaDatiContabiliSIBContoCapitale;

	@XmlVariabile(nome = "DATI_CONTABILI_CCAP_AUR", tipo = TipoVariabile.LISTA)
	private List<DatiContabiliXmlBean> listaInvioDatiSpesaContoCapitale;

	@XmlVariabile(nome = "XLS_DATI_CONT_CCAP", tipo = TipoVariabile.SEMPLICE)
	private String fileXlsContoCapitale;

	@XmlVariabile(nome = "NOTE_CONT_CCAP", tipo = TipoVariabile.SEMPLICE)
	private String noteContoCapitale;

	@XmlVariabile(nome = "DATI_SPESA_ANNUALI_X_DET_PERSONALE", tipo = TipoVariabile.LISTA)
	private List<DatiSpesaAnnualiXDetPersonaleXmlBean> listaDatiSpesaAnnualiXDetPersonale;

	@XmlVariabile(nome = "DATI_SPESA_ANNUA_X_DET_PERS_COD_CAPITOLO", tipo = TipoVariabile.SEMPLICE)
	private String capitoloDatiSpesaAnnuaXDetPers;

	@XmlVariabile(nome = "DATI_SPESA_ANNUA_X_DET_PERS_ARTICOLO", tipo = TipoVariabile.SEMPLICE)
	private String articoloDatiSpesaAnnuaXDetPers;

	@XmlVariabile(nome = "DATI_SPESA_ANNUAX_DET_PERS_NRO_VOCE_PEG", tipo = TipoVariabile.SEMPLICE)
	private String numeroDatiSpesaAnnuaXDetPers;

	@XmlVariabile(nome = "DATI_SPESA_ANNUA_X_DET_PERS_IMPORTO", tipo = TipoVariabile.SEMPLICE)
	private String importoDatiSpesaAnnuaXDetPers;

	@XmlVariabile(nome = "OGGETTO_HTML", tipo = TipoVariabile.SEMPLICE)
	private String oggettoHtml;

	@XmlVariabile(nome = "TESTO_PROPOSTA_REVISIONE_ORGANIGRAMMA", tipo = TipoVariabile.SEMPLICE)
	private String testoPropostaRevisioneOrganigramma;

	// ***************** Nuova proposta completa *******************//
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_TIPO_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String tipoAttoEmendamento;		
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_ATTO_SIGLA_REG", tipo = TipoVariabile.SEMPLICE)
	private String siglaAttoEmendamento;
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_ATTO_NRO", tipo = TipoVariabile.SEMPLICE)
	private String numeroAttoEmendamento;	
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_ATTO_ANNO", tipo = TipoVariabile.SEMPLICE)
	private String annoAttoEmendamento;
	
	@XmlVariabile(nome = "EMENDAMENTO_SUB_SU_EM_NRO", tipo = TipoVariabile.SEMPLICE)
	private String numeroEmendamento;		
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_FILE", tipo = TipoVariabile.SEMPLICE)
	private String flgEmendamentoSuFile;	
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_ALLEGATO_NRO", tipo = TipoVariabile.SEMPLICE)
	private String numeroAllegatoEmendamento;	
	
	@XmlVariabile(nome = "EMENDAMENTO_INTEGRALE_ALLEGATO", tipo = TipoVariabile.SEMPLICE)
	private Flag flgEmendamentoIntegraleAllegato;	
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_PAGINA", tipo = TipoVariabile.SEMPLICE)
	private String numeroPaginaEmendamento;
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_RIGA", tipo = TipoVariabile.SEMPLICE)
	private String numeroRigaEmendamento;	
	 
	@XmlVariabile(nome = "EMENDAMENTO_EFFETTO", tipo = TipoVariabile.SEMPLICE)
	private String effettoEmendamento;	
	
	@XmlVariabile(nome = "TASK_RESULT_2_INIZIATIVA_PROP_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String iniziativaPropAtto;

	@XmlVariabile(nome = "TASK_RESULT_2_ATTO_MERO_INDIRIZZO", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAttoMeroIndirizzo;
	
	@XmlVariabile(nome = "TASK_RESULT_2_MODIFICA_REGOLAMENTO", tipo = TipoVariabile.SEMPLICE)
	private Flag flgModificaRegolamento;
	
	@XmlVariabile(nome = "TASK_RESULT_2_MODIFICA_STATUTO", tipo = TipoVariabile.SEMPLICE)
	private Flag flgModificaStatuto;
	
	@XmlVariabile(nome = "TASK_RESULT_2_DEL_NOMINA", tipo = TipoVariabile.SEMPLICE)	
	private Flag flgNomina;
	
	@XmlVariabile(nome = "TASK_RESULT_2_RATIFICA_DEL_URGENZA", tipo = TipoVariabile.SEMPLICE)	
	private Flag flgRatificaDeliberaUrgenza;
	
	@XmlVariabile(nome = "TASK_RESULT_2_ATTO_URGENTE", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAttoUrgente;
	
	@XmlVariabile(nome = "CIRCOSCRIZIONE_PROPONENTE", tipo = TipoVariabile.LISTA)
	private List<KeyValueBean> circoscrizioniProponenti;
	
	@XmlVariabile(nome = "TASK_RESULT_2_TIPO_INTERPELLANZA", tipo = TipoVariabile.SEMPLICE)
	private String tipoInterpellanza;
	
	@XmlVariabile(nome = "TASK_RESULT_2_TIPO_ORD_MOBILITA", tipo = TipoVariabile.SEMPLICE)
	private String tipoOrdMobilita;
	
	@XmlVariabile(nome = "INIZIO_VLD_ORDINANZA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInizioVldOrdinanza;
	
	@XmlVariabile(nome = "FINE_VLD_ORDINANZA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFineVldOrdinanza;
	
	@XmlVariabile(nome = "TIPO_LUOGO_ORD_MOBILITA", tipo = TipoVariabile.SEMPLICE)
	private String tipoLuogoOrdMobilita;
	
	@XmlVariabile(nome = "LUOGO_ORD_MOBILITA", tipo = TipoVariabile.SEMPLICE)
	private String luogoOrdMobilita;
	
	@XmlVariabile(nome = "CIRCOSCRIZIONE_ORD_MOBILITA", tipo = TipoVariabile.LISTA)
	private List<KeyValueBean> circoscrizioniOrdMobilita;
		
	@XmlVariabile(nome = "TASK_RESULT_2_DET_RIACCERT", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDetRiaccert;

	@XmlVariabile(nome = "TASK_RESULT_2_AFFIDAMENTO", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAffidamento;

	@XmlVariabile(nome = "TASK_RESULT_2_FLG_CORTE_CONTI", tipo = TipoVariabile.SEMPLICE)
	private Flag flgCorteConti;
	
	@XmlVariabile(nome = "TASK_RESULT_2_TIPO_ATTO_IN_DEL_PEG", tipo = TipoVariabile.SEMPLICE)
	private String tipoAttoInDelPeg;

	@XmlVariabile(nome = "TIPO_AFFIDAMENTO", tipo = TipoVariabile.SEMPLICE)
	private String tipoAffidamento;
	
	@XmlVariabile(nome = "MATERIA_NATURA_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String materiaNaturaAtto;
	
	@XmlVariabile(nome = "MATERIA_NATURA_ATTO_Decode", tipo = TipoVariabile.SEMPLICE)
	private String desMateriaNaturaAtto;
	
	@XmlVariabile(nome = "FLG_LLPP", tipo = TipoVariabile.SEMPLICE)
	private String flgLLPP;
	
	@XmlVariabile(nome = "ANNO_PROGETTO_LLPP", tipo = TipoVariabile.SEMPLICE)
	private String annoProgettoLLPP;	
	
	@XmlVariabile(nome = "NRO_PROGETTO_LLPP", tipo = TipoVariabile.SEMPLICE)
	private String numProgettoLLPP;	
	
	@XmlVariabile(nome = "FLG_BENI_SERVIZI", tipo = TipoVariabile.SEMPLICE)
	private String flgBeniServizi;
	
	@XmlVariabile(nome = "ANNO_PROGETTO_BENI_SERVIZI", tipo = TipoVariabile.SEMPLICE)
	private String annoProgettoBeniServizi;	
	
	@XmlVariabile(nome = "NRO_PROGETTO_BENI_SERVIZI", tipo = TipoVariabile.SEMPLICE)
	private String numProgettoBeniServizi;
	
	@XmlVariabile(nome = "#CdCUOProponente", tipo = TipoVariabile.SEMPLICE)
	private String cdCUOProponente;

	@XmlVariabile(nome = "#FlgVistoDirSupUOProponente", tipo = TipoVariabile.SEMPLICE)
	private Flag flgVistoDirSupUOProponente;

	@XmlVariabile(nome = "#IdScrivaniaDirRespRegTecnica", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaDirRespRegTecnica;

	@XmlVariabile(nome = "#LivelliUOScrivaniaDirRespRegTecnica", tipo = TipoVariabile.SEMPLICE)
	private String livelliUOScrivaniaDirRespRegTecnica;

	@XmlVariabile(nome = "#DesScrivaniaDirRespRegTecnica", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaDirRespRegTecnica;

	@XmlVariabile(nome = "#FlgDirAncheRespProc", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDirAncheRespProc;

	@XmlVariabile(nome = "#FlgDirAncheRUP", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDirAncheRUP;

	@XmlVariabile(nome = "#@AltriDirRespTecnica", tipo = TipoVariabile.LISTA)
	private List<DirRespRegTecnicaBean> altriDirRespTecnica;

	@XmlVariabile(nome = "#IdAssessoreProponente", tipo = TipoVariabile.SEMPLICE)
	private String idAssessoreProponente;

	@XmlVariabile(nome = "#DesAssessoreProponente", tipo = TipoVariabile.SEMPLICE)
	private String desAssessoreProponente;

	@XmlVariabile(nome = "#@AltriAssessori", tipo = TipoVariabile.LISTA)
	private List<AssessoreProponenteBean> altriAssessori;

	@XmlVariabile(nome = "#IdConsigliereProponente", tipo = TipoVariabile.SEMPLICE)
	private String idConsigliereProponente;

	@XmlVariabile(nome = "#DesConsigliereProponente", tipo = TipoVariabile.SEMPLICE)
	private String desConsigliereProponente;

	@XmlVariabile(nome = "#@AltriConsiglieri", tipo = TipoVariabile.LISTA)
	private List<ConsigliereProponenteBean> altriConsiglieri;
		
	@XmlVariabile(nome = "#IdScrivaniaDirProponente", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaDirProponente;
	
	@XmlVariabile(nome = "#LivelliUOScrivaniaDirProponente", tipo = TipoVariabile.SEMPLICE)
	private String livelliUOScrivaniaDirProponente;
	
	@XmlVariabile(nome = "#DesScrivaniaDirProponente", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaDirProponente;
	
	@XmlVariabile(nome = "#@AltriDirProponenti", tipo = TipoVariabile.LISTA)
	private List<ScrivaniaDirProponenteBean> altriDirProponenti;
	
	@XmlVariabile(nome = "#IdCoordinatoreCompetenteCirc", tipo = TipoVariabile.SEMPLICE)
	private String idCoordinatoreCompCirc;
	
	@XmlVariabile(nome = "#DesCoordinatoreCompetenteCirc", tipo = TipoVariabile.SEMPLICE)
	private String desCoordinatoreCompCirc;

	@XmlVariabile(nome = "#@RespVistiConformita", tipo = TipoVariabile.LISTA)
	private List<RespVistiConformitaBean> respVistiConformita;

	@XmlVariabile(nome = "#IdScrivaniaEstensoreMain", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaEstensoreMain;

	@XmlVariabile(nome = "#LivelliUOScrivaniaEstensoreMain", tipo = TipoVariabile.SEMPLICE)
	private String livelliUOScrivaniaEstensoreMain;

	@XmlVariabile(nome = "#DesScrivaniaEstensoreMain", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaEstensoreMain;

	@XmlVariabile(nome = "#@AltriEstensori", tipo = TipoVariabile.LISTA)
	private List<ScrivaniaEstensoreBean> altriEstensori;

	@XmlVariabile(nome = "#IdScrivaniaIstruttoreMain", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaIstruttoreMain;

	@XmlVariabile(nome = "#LivelliUOScrivaniaIstruttoreMain", tipo = TipoVariabile.SEMPLICE)
	private String livelliUOScrivaniaIstruttoreMain;

	@XmlVariabile(nome = "#DesScrivaniaIstruttoreMain", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaIstruttoreMain;

	@XmlVariabile(nome = "#@AltriIstruttori", tipo = TipoVariabile.LISTA)
	private List<ScrivaniaEstensoreBean> altriIstruttori;
	
	@XmlVariabile(nome = "COD_CIRCOSCRIZIONE_X_PARERE", tipo = TipoVariabile.LISTA)
	private List<KeyValueBean> parereCircoscrizioni;

	@XmlVariabile(nome = "COD_COMMISSIONE_X_PARERE", tipo = TipoVariabile.LISTA)
	private List<KeyValueBean> parereCommissioni;
	
	@XmlVariabile(nome = "#DirigenteResponsabileContabilia", tipo = TipoVariabile.SEMPLICE)	
	private String dirigenteResponsabileContabilia;
	
	@XmlVariabile(nome = "#CentroResponsabilitaContabilia", tipo = TipoVariabile.SEMPLICE)	
	private String centroResponsabilitaContabilia;
	
	@XmlVariabile(nome = "#CentroCostoContabilia", tipo = TipoVariabile.SEMPLICE)	
	private String centroCostoContabilia;
	
	@XmlVariabile(nome = "MOVIMENTO_CONTABILIA", tipo = TipoVariabile.LISTA)
	private List<MovimentiContabiliaXmlBean> listaMovimentiContabilia;
	
	@XmlVariabile(nome = "MOVIMENTO_SICRA", tipo = TipoVariabile.LISTA)
	private List<MovimentiContabiliSICRAXmlBean> listaMovimentiContabiliSICRA;

	// ***************** Getter and Setter *******************//

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

	public Flag getFlgTipoDocConVie() {
		return flgTipoDocConVie;
	}

	public void setFlgTipoDocConVie(Flag flgTipoDocConVie) {
		this.flgTipoDocConVie = flgTipoDocConVie;
	}

	public Flag getFlgOggettoNonObblig() {
		return flgOggettoNonObblig;
	}

	public void setFlgOggettoNonObblig(Flag flgOggettoNonObblig) {
		this.flgOggettoNonObblig = flgOggettoNonObblig;
	}

	public String getFlgTipoProtModulo() {
		return flgTipoProtModulo;
	}

	public void setFlgTipoProtModulo(String flgTipoProtModulo) {
		this.flgTipoProtModulo = flgTipoProtModulo;
	}

	public String getRowidDoc() {
		return rowidDoc;
	}

	public void setRowidDoc(String rowidDoc) {
		this.rowidDoc = rowidDoc;
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

	public RegNumPrincipale getEstremiRegistrazione() {
		return estremiRegistrazione;
	}

	public void setEstremiRegistrazione(RegNumPrincipale estremiRegistrazione) {
		this.estremiRegistrazione = estremiRegistrazione;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public TipoProvenienza getFlgTipoProv() {
		return flgTipoProv;
	}

	public void setFlgTipoProv(TipoProvenienza flgTipoProv) {
		this.flgTipoProv = flgTipoProv;
	}

	public Date getDataArrivo() {
		return dataArrivo;
	}

	public void setDataArrivo(Date dataArrivo) {
		this.dataArrivo = dataArrivo;
	}

	public Date getDataStesura() {
		return dataStesura;
	}

	public void setDataStesura(Date dataStesura) {
		this.dataStesura = dataStesura;
	}

	public String getIdDocPrimario() {
		return idDocPrimario;
	}

	public void setIdDocPrimario(String idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}

	public FilePrimarioOutBean getFilePrimario() {
		return filePrimario;
	}

	public void setFilePrimario(FilePrimarioOutBean filePrimario) {
		this.filePrimario = filePrimario;
	}

	public Flag getFlgNoPubblPrimario() {
		return flgNoPubblPrimario;
	}

	public void setFlgNoPubblPrimario(Flag flgNoPubblPrimario) {
		this.flgNoPubblPrimario = flgNoPubblPrimario;
	}

	public Flag getFlgDatiSensibiliPrimario() {
		return flgDatiSensibiliPrimario;
	}

	public void setFlgDatiSensibiliPrimario(Flag flgDatiSensibiliPrimario) {
		this.flgDatiSensibiliPrimario = flgDatiSensibiliPrimario;
	}

	public String getDocPressoCentroPosta() {
		return docPressoCentroPosta;
	}

	public void setDocPressoCentroPosta(String docPressoCentroPosta) {
		this.docPressoCentroPosta = docPressoCentroPosta;
	}

	public List<MittentiDocumentoOutBean> getMittenti() {
		return mittenti;
	}

	public void setMittenti(List<MittentiDocumentoOutBean> mittenti) {
		this.mittenti = mittenti;
	}

	public List<AllegatiOutBean> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<AllegatiOutBean> allegati) {
		this.allegati = allegati;
	}

	public List<AllegatiOutBean> getDocumentiProcFolder() {
		return documentiProcFolder;
	}

	public void setDocumentiProcFolder(List<AllegatiOutBean> documentiProcFolder) {
		this.documentiProcFolder = documentiProcFolder;
	}

	public String getLivelloRiservatezza() {
		return livelloRiservatezza;
	}

	public void setLivelloRiservatezza(String livelloRiservatezza) {
		this.livelloRiservatezza = livelloRiservatezza;
	}

	public String getDescrizioneRiservatezza() {
		return descrizioneRiservatezza;
	}

	public void setDescrizioneRiservatezza(String descrizioneRiservatezza) {
		this.descrizioneRiservatezza = descrizioneRiservatezza;
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

	public String getPriorita() {
		return priorita;
	}

	public void setPriorita(String priorita) {
		this.priorita = priorita;
	}

	public String getDescrizionePrioritaRiservatezza() {
		return descrizionePrioritaRiservatezza;
	}

	public void setDescrizionePrioritaRiservatezza(String descrizionePrioritaRiservatezza) {
		this.descrizionePrioritaRiservatezza = descrizionePrioritaRiservatezza;
	}

	public String getMessoTrasmissione() {
		return messoTrasmissione;
	}

	public void setMessoTrasmissione(String messoTrasmissione) {
		this.messoTrasmissione = messoTrasmissione;
	}

	public String getNroRaccomandata() {
		return nroRaccomandata;
	}

	public void setNroRaccomandata(String nroRaccomandata) {
		this.nroRaccomandata = nroRaccomandata;
	}

	public Date getDtRaccomandata() {
		return dtRaccomandata;
	}

	public void setDtRaccomandata(Date dtRaccomandata) {
		this.dtRaccomandata = dtRaccomandata;
	}

	public String getNroListaRaccomandata() {
		return nroListaRaccomandata;
	}

	public void setNroListaRaccomandata(String nroListaRaccomandata) {
		this.nroListaRaccomandata = nroListaRaccomandata;
	}

	public Date getTermineRiservatezza() {
		return termineRiservatezza;
	}

	public void setTermineRiservatezza(Date termineRiservatezza) {
		this.termineRiservatezza = termineRiservatezza;
	}

	public CollocazioneFisica getCollocazioneFisica() {
		return collocazioneFisica;
	}

	public void setCollocazioneFisica(CollocazioneFisica collocazioneFisica) {
		this.collocazioneFisica = collocazioneFisica;
	}

	public String getRifDocRicevuto() {
		return rifDocRicevuto;
	}

	public void setRifDocRicevuto(String rifDocRicevuto) {
		this.rifDocRicevuto = rifDocRicevuto;
	}

	public String getEstremiRegDocRicevuto() {
		return estremiRegDocRicevuto;
	}

	public void setEstremiRegDocRicevuto(String estremiRegDocRicevuto) {
		this.estremiRegDocRicevuto = estremiRegDocRicevuto;
	}

	public String getAnnoDocRicevuto() {
		return annoDocRicevuto;
	}

	public void setAnnoDocRicevuto(String annoDocRicevuto) {
		this.annoDocRicevuto = annoDocRicevuto;
	}

	public Date getDtDocRicevuto() {
		return dtDocRicevuto;
	}

	public void setDtDocRicevuto(Date dtDocRicevuto) {
		this.dtDocRicevuto = dtDocRicevuto;
	}

	public List<DestinatariOutBean> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<DestinatariOutBean> destinatari) {
		this.destinatari = destinatari;
	}

	public RegEmergenzaOutBean getRegEmergenza() {
		return regEmergenza;
	}

	public void setRegEmergenza(RegEmergenzaOutBean regEmergenza) {
		this.regEmergenza = regEmergenza;
	}

	public DocCollegatoOutBean getDocCollegato() {
		return docCollegato;
	}

	public void setDocCollegato(DocCollegatoOutBean docCollegato) {
		this.docCollegato = docCollegato;
	}

	public List<ClassFascTitolarioOutBean> getClassifichefascicoli() {
		return classifichefascicoli;
	}

	public void setClassifichefascicoli(List<ClassFascTitolarioOutBean> classifichefascicoli) {
		this.classifichefascicoli = classifichefascicoli;
	}

	public List<FolderCustom> getFolderCustom() {
		return folderCustom;
	}

	public void setFolderCustom(List<FolderCustom> folderCustom) {
		this.folderCustom = folderCustom;
	}

	public Boolean getFlgPresentiAssPreselMitt() {
		return flgPresentiAssPreselMitt;
	}

	public void setFlgPresentiAssPreselMitt(Boolean flgPresentiAssPreselMitt) {
		this.flgPresentiAssPreselMitt = flgPresentiAssPreselMitt;
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

	public List<DestInvioCCOutBean> getAltreUoCoinvolte() {
		return altreUoCoinvolte;
	}

	public void setAltreUoCoinvolte(List<DestInvioCCOutBean> altreUoCoinvolte) {
		this.altreUoCoinvolte = altreUoCoinvolte;
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

	public EmailProvBean getEmailProv() {
		return emailProv;
	}

	public void setEmailProv(EmailProvBean emailProv) {
		this.emailProv = emailProv;
	}

	public String getIdEmailArrivo() {
		return idEmailArrivo;
	}

	public void setIdEmailArrivo(String idEmailArrivo) {
		this.idEmailArrivo = idEmailArrivo;
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

	public Boolean getFlgInteroperabilita() {
		return flgInteroperabilita;
	}

	public void setFlgInteroperabilita(Boolean flgInteroperabilita) {
		this.flgInteroperabilita = flgInteroperabilita;
	}

	public String getIdUserCtrlAmmissib() {
		return idUserCtrlAmmissib;
	}

	public void setIdUserCtrlAmmissib(String idUserCtrlAmmissib) {
		this.idUserCtrlAmmissib = idUserCtrlAmmissib;
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

	public String getRicEccezioniInterop() {
		return ricEccezioniInterop;
	}

	public void setRicEccezioniInterop(String ricEccezioniInterop) {
		this.ricEccezioniInterop = ricEccezioniInterop;
	}

	public String getRicAggiornamentiInterop() {
		return ricAggiornamentiInterop;
	}

	public void setRicAggiornamentiInterop(String ricAggiornamentiInterop) {
		this.ricAggiornamentiInterop = ricAggiornamentiInterop;
	}

	public String getRicAnnullamentiInterop() {
		return ricAnnullamentiInterop;
	}

	public void setRicAnnullamentiInterop(String ricAnnullamentiInterop) {
		this.ricAnnullamentiInterop = ricAnnullamentiInterop;
	}

	public String getPresenzaDocCollegati() {
		return presenzaDocCollegati;
	}

	public void setPresenzaDocCollegati(String presenzaDocCollegati) {
		this.presenzaDocCollegati = presenzaDocCollegati;
	}

	public String getEstremiTrasm() {
		return estremiTrasm;
	}

	public void setEstremiTrasm(String estremiTrasm) {
		this.estremiTrasm = estremiTrasm;
	}

	public String getCodStatoDett() {
		return codStatoDett;
	}

	public void setCodStatoDett(String codStatoDett) {
		this.codStatoDett = codStatoDett;
	}

	public Boolean getInviataMailInteroperabile() {
		return inviataMailInteroperabile;
	}

	public void setInviataMailInteroperabile(Boolean inviataMailInteroperabile) {
		this.inviataMailInteroperabile = inviataMailInteroperabile;
	}

	public String getTipoRegNumerazioneSecondaria() {
		return tipoRegNumerazioneSecondaria;
	}

	public void setTipoRegNumerazioneSecondaria(String tipoRegNumerazioneSecondaria) {
		this.tipoRegNumerazioneSecondaria = tipoRegNumerazioneSecondaria;
	}

	public String getSiglaRegNumerazioneSecondaria() {
		return siglaRegNumerazioneSecondaria;
	}

	public void setSiglaRegNumerazioneSecondaria(String siglaRegNumerazioneSecondaria) {
		this.siglaRegNumerazioneSecondaria = siglaRegNumerazioneSecondaria;
	}

	public String getAnnoRegNumerazioneSecondaria() {
		return annoRegNumerazioneSecondaria;
	}

	public void setAnnoRegNumerazioneSecondaria(String annoRegNumerazioneSecondaria) {
		this.annoRegNumerazioneSecondaria = annoRegNumerazioneSecondaria;
	}

	public String getNumeroRegNumerazioneSecondaria() {
		return numeroRegNumerazioneSecondaria;
	}

	public void setNumeroRegNumerazioneSecondaria(String numeroRegNumerazioneSecondaria) {
		this.numeroRegNumerazioneSecondaria = numeroRegNumerazioneSecondaria;
	}

	public String getSubNroRegNumerazioneSecondaria() {
		return subNroRegNumerazioneSecondaria;
	}

	public void setSubNroRegNumerazioneSecondaria(String subNroRegNumerazioneSecondaria) {
		this.subNroRegNumerazioneSecondaria = subNroRegNumerazioneSecondaria;
	}

	public Date getDataRegistrazioneNumerazioneSecondaria() {
		return dataRegistrazioneNumerazioneSecondaria;
	}

	public void setDataRegistrazioneNumerazioneSecondaria(Date dataRegistrazioneNumerazioneSecondaria) {
		this.dataRegistrazioneNumerazioneSecondaria = dataRegistrazioneNumerazioneSecondaria;
	}

	public String getIdUdAttoAutAnnRegNumerazioneSecondaria() {
		return idUdAttoAutAnnRegNumerazioneSecondaria;
	}

	public void setIdUdAttoAutAnnRegNumerazioneSecondaria(String idUdAttoAutAnnRegNumerazioneSecondaria) {
		this.idUdAttoAutAnnRegNumerazioneSecondaria = idUdAttoAutAnnRegNumerazioneSecondaria;
	}

	public String getRegNumSecondariaDesGruppo() {
		return regNumSecondariaDesGruppo;
	}

	public void setRegNumSecondariaDesGruppo(String regNumSecondariaDesGruppo) {
		this.regNumSecondariaDesGruppo = regNumSecondariaDesGruppo;
	}

	public String getIdUOProponente() {
		return idUOProponente;
	}

	public void setIdUOProponente(String idUOProponente) {
		this.idUOProponente = idUOProponente;
	}

	public String getCodUOProponente() {
		return codUOProponente;
	}

	public void setCodUOProponente(String codUOProponente) {
		this.codUOProponente = codUOProponente;
	}

	public String getDesUOProponente() {
		return desUOProponente;
	}

	public void setDesUOProponente(String desUOProponente) {
		this.desUOProponente = desUOProponente;
	}

	public String getDesDirProponente() {
		return desDirProponente;
	}

	public void setDesDirProponente(String desDirProponente) {
		this.desDirProponente = desDirProponente;
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

	public String getDirettoreFirmatarioAtto() {
		return direttoreFirmatarioAtto;
	}

	public void setDirettoreFirmatarioAtto(String direttoreFirmatarioAtto) {
		this.direttoreFirmatarioAtto = direttoreFirmatarioAtto;
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

	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public String getLogIterAtto() {
		return logIterAtto;
	}

	public void setLogIterAtto(String logIterAtto) {
		this.logIterAtto = logIterAtto;
	}

	public String getStatoConservazione() {
		return statoConservazione;
	}

	public void setStatoConservazione(String statoConservazione) {
		this.statoConservazione = statoConservazione;
	}

	public Date getDataInvioInConservazione() {
		return dataInvioInConservazione;
	}

	public void setDataInvioInConservazione(Date dataInvioInConservazione) {
		this.dataInvioInConservazione = dataInvioInConservazione;
	}

	

	public List<ContraentiOutBean> getContraenti() {
		return contraenti;
	}

	public void setContraenti(List<ContraentiOutBean> contraenti) {
		this.contraenti = contraenti;
	}

	public Flag getFlgCompilazioneModulo() {
		return flgCompilazioneModulo;
	}

	public void setFlgCompilazioneModulo(Flag flgCompilazioneModulo) {
		this.flgCompilazioneModulo = flgCompilazioneModulo;
	}

	public Flag getFlgPropostaAtto() {
		return flgPropostaAtto;
	}

	public void setFlgPropostaAtto(Flag flgPropostaAtto) {
		this.flgPropostaAtto = flgPropostaAtto;
	}

	public String getSceltaIterPropostaDD() {
		return sceltaIterPropostaDD;
	}

	public void setSceltaIterPropostaDD(String sceltaIterPropostaDD) {
		this.sceltaIterPropostaDD = sceltaIterPropostaDD;
	}

	public String getFlgRichParereRevisori() {
		return flgRichParereRevisori;
	}

	public void setFlgRichParereRevisori(String flgRichParereRevisori) {
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

	

	public List<SoggettoEsternoBean> getEsibenti() {
		return esibenti;
	}

	public void setEsibenti(List<SoggettoEsternoBean> esibenti) {
		this.esibenti = esibenti;
	}

	public List<SoggettoEsternoBean> getInteressati() {
		return interessati;
	}

	public void setInteressati(List<SoggettoEsternoBean> interessati) {
		this.interessati = interessati;
	}

	public List<AltreUbicazioniBean> getAltreUbicazioni() {
		return altreUbicazioni;
	}

	public void setAltreUbicazioni(List<AltreUbicazioniBean> altreUbicazioni) {
		this.altreUbicazioni = altreUbicazioni;
	}

	public List<DocumentoCollegato> getDocumentiCollegati() {
		return documentiCollegati;
	}

	public void setDocumentiCollegati(List<DocumentoCollegato> documentiCollegati) {
		this.documentiCollegati = documentiCollegati;
	}

	public List<AltriRiferimentiBean> getAltriRiferimenti() {
		return altriRiferimenti;
	}

	public void setAltriRiferimenti(List<AltriRiferimentiBean> altriRiferimenti) {
		this.altriRiferimenti = altriRiferimenti;
	}

	public String getSezionaleOnlyOne() {
		return sezionaleOnlyOne;
	}

	public void setSezionaleOnlyOne(String sezionaleOnlyOne) {
		this.sezionaleOnlyOne = sezionaleOnlyOne;
	}

	public String getNumeroOnlyOne() {
		return numeroOnlyOne;
	}

	public void setNumeroOnlyOne(String numeroOnlyOne) {
		this.numeroOnlyOne = numeroOnlyOne;
	}

	public String getAnnoOnlyOne() {
		return annoOnlyOne;
	}

	public void setAnnoOnlyOne(String annoOnlyOne) {
		this.annoOnlyOne = annoOnlyOne;
	}

	public Flag getFlgCaricamentoPGPregressoDaGUI() {
		return flgCaricamentoPGPregressoDaGUI;
	}

	public void setFlgCaricamentoPGPregressoDaGUI(Flag flgCaricamentoPGPregressoDaGUI) {
		this.flgCaricamentoPGPregressoDaGUI = flgCaricamentoPGPregressoDaGUI;
	}

	public String getSegnatura() {
		return segnatura;
	}

	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}

	

	public String getIdScrivaniaRespIstruttoria() {
		return idScrivaniaRespIstruttoria;
	}

	public void setIdScrivaniaRespIstruttoria(String idScrivaniaRespIstruttoria) {
		this.idScrivaniaRespIstruttoria = idScrivaniaRespIstruttoria;
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

	public String getDesScrivaniaRespIstruttoria() {
		return desScrivaniaRespIstruttoria;
	}

	public void setDesScrivaniaRespIstruttoria(String desScrivaniaRespIstruttoria) {
		this.desScrivaniaRespIstruttoria = desScrivaniaRespIstruttoria;
	}

	public String getCodUOScrivaniaRespIstruttoria() {
		return codUOScrivaniaRespIstruttoria;
	}

	public void setCodUOScrivaniaRespIstruttoria(String codUOScrivaniaRespIstruttoria) {
		this.codUOScrivaniaRespIstruttoria = codUOScrivaniaRespIstruttoria;
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

	public Flag getFlgRichiestaAccessoAtti() {
		return flgRichiestaAccessoAtti;
	}

	public void setFlgRichiestaAccessoAtti(Flag flgRichiestaAccessoAtti) {
		this.flgRichiestaAccessoAtti = flgRichiestaAccessoAtti;
	}

	public List<AttiRichiestiXMLBean> getAttiRichiesti() {
		return attiRichiesti;
	}

	public void setAttiRichiesti(List<AttiRichiestiXMLBean> attiRichiesti) {
		this.attiRichiesti = attiRichiesti;
	}

	public List<SoggettoEsternoBean> getDeleganti() {
		return deleganti;
	}

	public void setDeleganti(List<SoggettoEsternoBean> deleganti) {
		this.deleganti = deleganti;
	}

	public List<SoggettoEsternoBean> getIncaricatiRitiro() {
		return incaricatiRitiro;
	}

	public void setIncaricatiRitiro(List<SoggettoEsternoBean> incaricatiRitiro) {
		this.incaricatiRitiro = incaricatiRitiro;
	}

	public Flag getFlgRichAccessoAttiConRichInterno() {
		return flgRichAccessoAttiConRichInterno;
	}

	public void setFlgRichAccessoAttiConRichInterno(Flag flgRichAccessoAttiConRichInterno) {
		this.flgRichAccessoAttiConRichInterno = flgRichAccessoAttiConRichInterno;
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

	public String getDataPrelievo() {
		return dataPrelievo;
	}

	public void setDataPrelievo(String dataPrelievo) {
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

	

	public String getIdUDTrasmessaInUscitaCon() {
		return idUDTrasmessaInUscitaCon;
	}

	public void setIdUDTrasmessaInUscitaCon(String idUDTrasmessaInUscitaCon) {
		this.idUDTrasmessaInUscitaCon = idUDTrasmessaInUscitaCon;
	}

	public String getEstremiUDTrasmessaInUscitaCon() {
		return estremiUDTrasmessaInUscitaCon;
	}

	public void setEstremiUDTrasmessaInUscitaCon(String estremiUDTrasmessaInUscitaCon) {
		this.estremiUDTrasmessaInUscitaCon = estremiUDTrasmessaInUscitaCon;
	}

	public Flag getFlgDettRevocaAtto() {
		return flgDettRevocaAtto;
	}

	public void setFlgDettRevocaAtto(Flag flgDettRevocaAtto) {
		this.flgDettRevocaAtto = flgDettRevocaAtto;
	}

	public String getCodPropostaSistContabile() {
		return codPropostaSistContabile;
	}

	public void setCodPropostaSistContabile(String codPropostaSistContabile) {
		this.codPropostaSistContabile = codPropostaSistContabile;
	}

	public Flag getFlgDelibera() {
		return flgDelibera;
	}

	public void setFlgDelibera(Flag flgDelibera) {
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

	public String getIdScrivaniaAdottante() {
		return idScrivaniaAdottante;
	}

	public void setIdScrivaniaAdottante(String idScrivaniaAdottante) {
		this.idScrivaniaAdottante = idScrivaniaAdottante;
	}

	public String getDesScrivaniaAdottante() {
		return desScrivaniaAdottante;
	}

	public void setDesScrivaniaAdottante(String desScrivaniaAdottante) {
		this.desScrivaniaAdottante = desScrivaniaAdottante;
	}

	public String getCodUOScrivaniaAdottante() {
		return codUOScrivaniaAdottante;
	}

	public void setCodUOScrivaniaAdottante(String codUOScrivaniaAdottante) {
		this.codUOScrivaniaAdottante = codUOScrivaniaAdottante;
	}

	public Flag getFlgAdottanteAncheRespProc() {
		return flgAdottanteAncheRespProc;
	}

	public void setFlgAdottanteAncheRespProc(Flag flgAdottanteAncheRespProc) {
		this.flgAdottanteAncheRespProc = flgAdottanteAncheRespProc;
	}

	public Flag getFlgAdottanteAncheRUP() {
		return flgAdottanteAncheRUP;
	}

	public void setFlgAdottanteAncheRUP(Flag flgAdottanteAncheRUP) {
		this.flgAdottanteAncheRUP = flgAdottanteAncheRUP;
	}

	public String getIdScrivaniaRespProc() {
		return idScrivaniaRespProc;
	}

	public void setIdScrivaniaRespProc(String idScrivaniaRespProc) {
		this.idScrivaniaRespProc = idScrivaniaRespProc;
	}

	public String getDesScrivaniaRespProc() {
		return desScrivaniaRespProc;
	}

	public void setDesScrivaniaRespProc(String desScrivaniaRespProc) {
		this.desScrivaniaRespProc = desScrivaniaRespProc;
	}

	public String getCodUOScrivaniaRespProc() {
		return codUOScrivaniaRespProc;
	}

	public void setCodUOScrivaniaRespProc(String codUOScrivaniaRespProc) {
		this.codUOScrivaniaRespProc = codUOScrivaniaRespProc;
	}

	public Flag getFlgRespProcAncheRUP() {
		return flgRespProcAncheRUP;
	}

	public void setFlgRespProcAncheRUP(Flag flgRespProcAncheRUP) {
		this.flgRespProcAncheRUP = flgRespProcAncheRUP;
	}

	public String getIdScrivaniaRUP() {
		return idScrivaniaRUP;
	}

	public void setIdScrivaniaRUP(String idScrivaniaRUP) {
		this.idScrivaniaRUP = idScrivaniaRUP;
	}

	public String getDesScrivaniaRUP() {
		return desScrivaniaRUP;
	}

	public void setDesScrivaniaRUP(String desScrivaniaRUP) {
		this.desScrivaniaRUP = desScrivaniaRUP;
	}

	public String getCodUOScrivaniaRUP() {
		return codUOScrivaniaRUP;
	}

	public void setCodUOScrivaniaRUP(String codUOScrivaniaRUP) {
		this.codUOScrivaniaRUP = codUOScrivaniaRUP;
	}

	public Flag getFlgRichiediVistoDirettore() {
		return flgRichiediVistoDirettore;
	}

	public void setFlgRichiediVistoDirettore(Flag flgRichiediVistoDirettore) {
		this.flgRichiediVistoDirettore = flgRichiediVistoDirettore;
	}

	public List<RespDiConcertoBean> getResponsabiliDiConcerto() {
		return responsabiliDiConcerto;
	}

	public void setResponsabiliDiConcerto(List<RespDiConcertoBean> responsabiliDiConcerto) {
		this.responsabiliDiConcerto = responsabiliDiConcerto;
	}

	public List<ScrivaniaDirProponenteBean> getDirigentiProponenti() {
		return dirigentiProponenti;
	}

	public void setDirigentiProponenti(List<ScrivaniaDirProponenteBean> dirigentiProponenti) {
		this.dirigentiProponenti = dirigentiProponenti;
	}

	public List<AssessoreProponenteBean> getAssessoriProponenti() {
		return assessoriProponenti;
	}

	public void setAssessoriProponenti(List<AssessoreProponenteBean> assessoriProponenti) {
		this.assessoriProponenti = assessoriProponenti;
	}

	public List<ConsigliereProponenteBean> getConsiglieriProponenti() {
		return consiglieriProponenti;
	}

	public void setConsiglieriProponenti(List<ConsigliereProponenteBean> consiglieriProponenti) {
		this.consiglieriProponenti = consiglieriProponenti;
	}

	public List<ScrivaniaEstensoreBean> getEstensori() {
		return estensori;
	}

	public void setEstensori(List<ScrivaniaEstensoreBean> estensori) {
		this.estensori = estensori;
	}

	public List<RespSpesaBean> getResponsabiliSpesa() {
		return responsabiliSpesa;
	}

	public void setResponsabiliSpesa(List<RespSpesaBean> responsabiliSpesa) {
		this.responsabiliSpesa = responsabiliSpesa;
	}

	public String getIdUOCompSpesa() {
		return idUOCompSpesa;
	}

	public void setIdUOCompSpesa(String idUOCompSpesa) {
		this.idUOCompSpesa = idUOCompSpesa;
	}

	public String getCodUOCompSpesa() {
		return codUOCompSpesa;
	}

	public void setCodUOCompSpesa(String codUOCompSpesa) {
		this.codUOCompSpesa = codUOCompSpesa;
	}

	public String getDesUOCompSpesa() {
		return desUOCompSpesa;
	}

	public void setDesUOCompSpesa(String desUOCompSpesa) {
		this.desUOCompSpesa = desUOCompSpesa;
	}

	public Flag getFlgAdottanteUnicoRespSpesa() {
		return flgAdottanteUnicoRespSpesa;
	}

	public void setFlgAdottanteUnicoRespSpesa(Flag flgAdottanteUnicoRespSpesa) {
		this.flgAdottanteUnicoRespSpesa = flgAdottanteUnicoRespSpesa;
	}

	public List<ValueBean> getRiferimentiNormativi() {
		return riferimentiNormativi;
	}

	public void setRiferimentiNormativi(List<ValueBean> riferimentiNormativi) {
		this.riferimentiNormativi = riferimentiNormativi;
	}

	public List<ValueBean> getListaCIG() {
		return listaCIG;
	}

	public void setListaCIG(List<ValueBean> listaCIG) {
		this.listaCIG = listaCIG;
	}

	public String getAttiPresupposti() {
		return attiPresupposti;
	}

	public void setAttiPresupposti(String attiPresupposti) {
		this.attiPresupposti = attiPresupposti;
	}

	public String getMotivazioniAtto() {
		return motivazioniAtto;
	}

	public void setMotivazioniAtto(String motivazioniAtto) {
		this.motivazioniAtto = motivazioniAtto;
	}
		
	public String getPremessaAtto() {
		return premessaAtto;
	}
	
	public void setPremessaAtto(String premessaAtto) {
		this.premessaAtto = premessaAtto;
	}

	public String getDispositivoAtto() {
		return dispositivoAtto;
	}

	public void setDispositivoAtto(String dispositivoAtto) {
		this.dispositivoAtto = dispositivoAtto;
	}

	public String getLoghiDispositivoAtto() {
		return loghiDispositivoAtto;
	}

	public void setLoghiDispositivoAtto(String loghiDispositivoAtto) {
		this.loghiDispositivoAtto = loghiDispositivoAtto;
	}
	
	public Flag getFlgPubblicaAllegatiSeparati() {
		return flgPubblicaAllegatiSeparati;
	}

	public void setFlgPubblicaAllegatiSeparati(Flag flgPubblicaAllegatiSeparati) {
		this.flgPubblicaAllegatiSeparati = flgPubblicaAllegatiSeparati;
	}

	public String getFlgPrivacy() {
		return flgPrivacy;
	}

	public void setFlgPrivacy(String flgPrivacy) {
		this.flgPrivacy = flgPrivacy;
	}

	public String getFlgPubblAlbo() {
		return flgPubblAlbo;
	}

	public void setFlgPubblAlbo(String flgPubblAlbo) {
		this.flgPubblAlbo = flgPubblAlbo;
	}

	public String getNumGiorniPubblAlbo() {
		return numGiorniPubblAlbo;
	}

	public void setNumGiorniPubblAlbo(String numGiorniPubblAlbo) {
		this.numGiorniPubblAlbo = numGiorniPubblAlbo;
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

	public Flag getFlgUrgentePubblAlbo() {
		return flgUrgentePubblAlbo;
	}

	public void setFlgUrgentePubblAlbo(Flag flgUrgentePubblAlbo) {
		this.flgUrgentePubblAlbo = flgUrgentePubblAlbo;
	}

	public Date getDataPubblAlboEntro() {
		return dataPubblAlboEntro;
	}

	public void setDataPubblAlboEntro(Date dataPubblAlboEntro) {
		this.dataPubblAlboEntro = dataPubblAlboEntro;
	}

	public String getFlgPubblAmmTrasp() {
		return flgPubblAmmTrasp;
	}

	public void setFlgPubblAmmTrasp(String flgPubblAmmTrasp) {
		this.flgPubblAmmTrasp = flgPubblAmmTrasp;
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

	public Flag getFlgUrgentePubblBUR() {
		return flgUrgentePubblBUR;
	}

	public void setFlgUrgentePubblBUR(Flag flgUrgentePubblBUR) {
		this.flgUrgentePubblBUR = flgUrgentePubblBUR;
	}

	public Date getDataPubblBUREntro() {
		return dataPubblBUREntro;
	}

	public void setDataPubblBUREntro(Date dataPubblBUREntro) {
		this.dataPubblBUREntro = dataPubblBUREntro;
	}
	
	public Date getDtEsecutivita() {
		return dtEsecutivita;
	}

	public void setDtEsecutivita(Date dtEsecutivita) {
		this.dtEsecutivita = dtEsecutivita;
	}

	public Flag getFlgImmediatamenteEseg() {
		return flgImmediatamenteEseg;
	}

	public void setFlgImmediatamenteEseg(Flag flgImmediatamenteEseg) {
		this.flgImmediatamenteEseg = flgImmediatamenteEseg;
	}
	
	public String getMotiviImmediatamenteEseguibile() {
		return motiviImmediatamenteEseguibile;
	}

	public void setMotiviImmediatamenteEseguibile(String motiviImmediatamenteEseguibile) {
		this.motiviImmediatamenteEseguibile = motiviImmediatamenteEseguibile;
	}

	public List<DestNotificaAttoXmlBean> getListaDestNotificaAtto() {
		return listaDestNotificaAtto;
	}

	public void setListaDestNotificaAtto(List<DestNotificaAttoXmlBean> listaDestNotificaAtto) {
		this.listaDestNotificaAtto = listaDestNotificaAtto;
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

	public List<DatiContabiliXmlBean> getListaDatiContabiliSIBCorrente() {
		return listaDatiContabiliSIBCorrente;
	}

	public void setListaDatiContabiliSIBCorrente(List<DatiContabiliXmlBean> listaDatiContabiliSIBCorrente) {
		this.listaDatiContabiliSIBCorrente = listaDatiContabiliSIBCorrente;
	}

	public List<DatiContabiliXmlBean> getListaInvioDatiSpesaCorrente() {
		return listaInvioDatiSpesaCorrente;
	}

	public void setListaInvioDatiSpesaCorrente(List<DatiContabiliXmlBean> listaInvioDatiSpesaCorrente) {
		this.listaInvioDatiSpesaCorrente = listaInvioDatiSpesaCorrente;
	}

	public String getFileXlsCorrente() {
		return fileXlsCorrente;
	}

	public void setFileXlsCorrente(String fileXlsCorrente) {
		this.fileXlsCorrente = fileXlsCorrente;
	}

	public String getNoteCorrente() {
		return noteCorrente;
	}

	public void setNoteCorrente(String noteCorrente) {
		this.noteCorrente = noteCorrente;
	}

	public String getModalitaInvioDatiSpesaARagioneriaContoCapitale() {
		return modalitaInvioDatiSpesaARagioneriaContoCapitale;
	}

	public void setModalitaInvioDatiSpesaARagioneriaContoCapitale(String modalitaInvioDatiSpesaARagioneriaContoCapitale) {
		this.modalitaInvioDatiSpesaARagioneriaContoCapitale = modalitaInvioDatiSpesaARagioneriaContoCapitale;
	}

	public List<DatiContabiliXmlBean> getListaDatiContabiliSIBContoCapitale() {
		return listaDatiContabiliSIBContoCapitale;
	}

	public void setListaDatiContabiliSIBContoCapitale(List<DatiContabiliXmlBean> listaDatiContabiliSIBContoCapitale) {
		this.listaDatiContabiliSIBContoCapitale = listaDatiContabiliSIBContoCapitale;
	}

	public List<DatiContabiliXmlBean> getListaInvioDatiSpesaContoCapitale() {
		return listaInvioDatiSpesaContoCapitale;
	}

	public void setListaInvioDatiSpesaContoCapitale(List<DatiContabiliXmlBean> listaInvioDatiSpesaContoCapitale) {
		this.listaInvioDatiSpesaContoCapitale = listaInvioDatiSpesaContoCapitale;
	}

	public String getFileXlsContoCapitale() {
		return fileXlsContoCapitale;
	}

	public void setFileXlsContoCapitale(String fileXlsContoCapitale) {
		this.fileXlsContoCapitale = fileXlsContoCapitale;
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

	public void setListaDatiSpesaAnnualiXDetPersonale(List<DatiSpesaAnnualiXDetPersonaleXmlBean> listaDatiSpesaAnnualiXDetPersonale) {
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

	public String getOggettoHtml() {
		return oggettoHtml;
	}

	public void setOggettoHtml(String oggettoHtml) {
		this.oggettoHtml = oggettoHtml;
	}

	public String getTestoPropostaRevisioneOrganigramma() {
		return testoPropostaRevisioneOrganigramma;
	}

	public void setTestoPropostaRevisioneOrganigramma(String testoPropostaRevisioneOrganigramma) {
		this.testoPropostaRevisioneOrganigramma = testoPropostaRevisioneOrganigramma;
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

	public Flag getFlgEmendamentoIntegraleAllegato() {
		return flgEmendamentoIntegraleAllegato;
	}

	public void setFlgEmendamentoIntegraleAllegato(Flag flgEmendamentoIntegraleAllegato) {
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

	public String getIniziativaPropAtto() {
		return iniziativaPropAtto;
	}

	public void setIniziativaPropAtto(String iniziativaPropAtto) {
		this.iniziativaPropAtto = iniziativaPropAtto;
	}
	
	public Flag getFlgAttoMeroIndirizzo() {
		return flgAttoMeroIndirizzo;
	}

	public void setFlgAttoMeroIndirizzo(Flag flgAttoMeroIndirizzo) {
		this.flgAttoMeroIndirizzo = flgAttoMeroIndirizzo;
	}

	public Flag getFlgModificaRegolamento() {
		return flgModificaRegolamento;
	}

	public void setFlgModificaRegolamento(Flag flgModificaRegolamento) {
		this.flgModificaRegolamento = flgModificaRegolamento;
	}
	
	public Flag getFlgModificaStatuto() {
		return flgModificaStatuto;
	}

	public void setFlgModificaStatuto(Flag flgModificaStatuto) {
		this.flgModificaStatuto = flgModificaStatuto;
	}

	public Flag getFlgNomina() {
		return flgNomina;
	}

	public void setFlgNomina(Flag flgNomina) {
		this.flgNomina = flgNomina;
	}

	public Flag getFlgRatificaDeliberaUrgenza() {
		return flgRatificaDeliberaUrgenza;
	}

	public void setFlgRatificaDeliberaUrgenza(Flag flgRatificaDeliberaUrgenza) {
		this.flgRatificaDeliberaUrgenza = flgRatificaDeliberaUrgenza;
	}

	public Flag getFlgAttoUrgente() {
		return flgAttoUrgente;
	}

	public void setFlgAttoUrgente(Flag flgAttoUrgente) {
		this.flgAttoUrgente = flgAttoUrgente;
	}
	
	public List<KeyValueBean> getCircoscrizioniProponenti() {
		return circoscrizioniProponenti;
	}

	public void setCircoscrizioniProponenti(List<KeyValueBean> circoscrizioniProponenti) {
		this.circoscrizioniProponenti = circoscrizioniProponenti;
	}
	
	public String getTipoInterpellanza() {
		return tipoInterpellanza;
	}

	public void setTipoInterpellanza(String tipoInterpellanza) {
		this.tipoInterpellanza = tipoInterpellanza;
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

	public String getLuogoOrdMobilita() {
		return luogoOrdMobilita;
	}

	public void setLuogoOrdMobilita(String luogoOrdMobilita) {
		this.luogoOrdMobilita = luogoOrdMobilita;
	}

	public List<KeyValueBean> getCircoscrizioniOrdMobilita() {
		return circoscrizioniOrdMobilita;
	}

	public void setCircoscrizioniOrdMobilita(List<KeyValueBean> circoscrizioniOrdMobilita) {
		this.circoscrizioniOrdMobilita = circoscrizioniOrdMobilita;
	}

	public Flag getFlgDetRiaccert() {
		return flgDetRiaccert;
	}

	public void setFlgDetRiaccert(Flag flgDetRiaccert) {
		this.flgDetRiaccert = flgDetRiaccert;
	}

	public Flag getFlgAffidamento() {
		return flgAffidamento;
	}

	public void setFlgAffidamento(Flag flgAffidamento) {
		this.flgAffidamento = flgAffidamento;
	}
	
	public Flag getFlgCorteConti() {
		return flgCorteConti;
	}

	public void setFlgCorteConti(Flag flgCorteConti) {
		this.flgCorteConti = flgCorteConti;
	}

	public String getTipoAttoInDelPeg() {
		return tipoAttoInDelPeg;
	}

	public void setTipoAttoInDelPeg(String tipoAttoInDelPeg) {
		this.tipoAttoInDelPeg = tipoAttoInDelPeg;
	}
	
	public String getTipoAffidamento() {
		return tipoAffidamento;
	}

	public void setTipoAffidamento(String tipoAffidamento) {
		this.tipoAffidamento = tipoAffidamento;
	}

	public String getMateriaNaturaAtto() {
		return materiaNaturaAtto;
	}

	public void setMateriaNaturaAtto(String materiaNaturaAtto) {
		this.materiaNaturaAtto = materiaNaturaAtto;
	}

	public String getDesMateriaNaturaAtto() {
		return desMateriaNaturaAtto;
	}

	public void setDesMateriaNaturaAtto(String desMateriaNaturaAtto) {
		this.desMateriaNaturaAtto = desMateriaNaturaAtto;
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

	public String getCdCUOProponente() {
		return cdCUOProponente;
	}

	public void setCdCUOProponente(String cdCUOProponente) {
		this.cdCUOProponente = cdCUOProponente;
	}

	public Flag getFlgVistoDirSupUOProponente() {
		return flgVistoDirSupUOProponente;
	}

	public void setFlgVistoDirSupUOProponente(Flag flgVistoDirSupUOProponente) {
		this.flgVistoDirSupUOProponente = flgVistoDirSupUOProponente;
	}

	public String getIdScrivaniaDirRespRegTecnica() {
		return idScrivaniaDirRespRegTecnica;
	}

	public void setIdScrivaniaDirRespRegTecnica(String idScrivaniaDirRespRegTecnica) {
		this.idScrivaniaDirRespRegTecnica = idScrivaniaDirRespRegTecnica;
	}

	public String getLivelliUOScrivaniaDirRespRegTecnica() {
		return livelliUOScrivaniaDirRespRegTecnica;
	}

	public void setLivelliUOScrivaniaDirRespRegTecnica(String livelliUOScrivaniaDirRespRegTecnica) {
		this.livelliUOScrivaniaDirRespRegTecnica = livelliUOScrivaniaDirRespRegTecnica;
	}

	public String getDesScrivaniaDirRespRegTecnica() {
		return desScrivaniaDirRespRegTecnica;
	}

	public void setDesScrivaniaDirRespRegTecnica(String desScrivaniaDirRespRegTecnica) {
		this.desScrivaniaDirRespRegTecnica = desScrivaniaDirRespRegTecnica;
	}

	public Flag getFlgDirAncheRespProc() {
		return flgDirAncheRespProc;
	}

	public void setFlgDirAncheRespProc(Flag flgDirAncheRespProc) {
		this.flgDirAncheRespProc = flgDirAncheRespProc;
	}

	public Flag getFlgDirAncheRUP() {
		return flgDirAncheRUP;
	}

	public void setFlgDirAncheRUP(Flag flgDirAncheRUP) {
		this.flgDirAncheRUP = flgDirAncheRUP;
	}

	public List<DirRespRegTecnicaBean> getAltriDirRespTecnica() {
		return altriDirRespTecnica;
	}

	public void setAltriDirRespTecnica(List<DirRespRegTecnicaBean> altriDirRespTecnica) {
		this.altriDirRespTecnica = altriDirRespTecnica;
	}

	public String getIdAssessoreProponente() {
		return idAssessoreProponente;
	}

	public void setIdAssessoreProponente(String idAssessoreProponente) {
		this.idAssessoreProponente = idAssessoreProponente;
	}

	public String getDesAssessoreProponente() {
		return desAssessoreProponente;
	}

	public void setDesAssessoreProponente(String desAssessoreProponente) {
		this.desAssessoreProponente = desAssessoreProponente;
	}

	public List<AssessoreProponenteBean> getAltriAssessori() {
		return altriAssessori;
	}

	public void setAltriAssessori(List<AssessoreProponenteBean> altriAssessori) {
		this.altriAssessori = altriAssessori;
	}

	public String getIdConsigliereProponente() {
		return idConsigliereProponente;
	}

	public void setIdConsigliereProponente(String idConsigliereProponente) {
		this.idConsigliereProponente = idConsigliereProponente;
	}

	public String getDesConsigliereProponente() {
		return desConsigliereProponente;
	}

	public void setDesConsigliereProponente(String desConsigliereProponente) {
		this.desConsigliereProponente = desConsigliereProponente;
	}

	public List<ConsigliereProponenteBean> getAltriConsiglieri() {
		return altriConsiglieri;
	}

	public void setAltriConsiglieri(List<ConsigliereProponenteBean> altriConsiglieri) {
		this.altriConsiglieri = altriConsiglieri;
	}

	public String getIdScrivaniaDirProponente() {
		return idScrivaniaDirProponente;
	}

	public void setIdScrivaniaDirProponente(String idScrivaniaDirProponente) {
		this.idScrivaniaDirProponente = idScrivaniaDirProponente;
	}

	public String getLivelliUOScrivaniaDirProponente() {
		return livelliUOScrivaniaDirProponente;
	}

	public void setLivelliUOScrivaniaDirProponente(String livelliUOScrivaniaDirProponente) {
		this.livelliUOScrivaniaDirProponente = livelliUOScrivaniaDirProponente;
	}

	public String getDesScrivaniaDirProponente() {
		return desScrivaniaDirProponente;
	}

	public void setDesScrivaniaDirProponente(String desScrivaniaDirProponente) {
		this.desScrivaniaDirProponente = desScrivaniaDirProponente;
	}

	public List<ScrivaniaDirProponenteBean> getAltriDirProponenti() {
		return altriDirProponenti;
	}

	public void setAltriDirProponenti(List<ScrivaniaDirProponenteBean> altriDirProponenti) {
		this.altriDirProponenti = altriDirProponenti;
	}

	public String getIdCoordinatoreCompCirc() {
		return idCoordinatoreCompCirc;
	}

	public void setIdCoordinatoreCompCirc(String idCoordinatoreCompCirc) {
		this.idCoordinatoreCompCirc = idCoordinatoreCompCirc;
	}

	public String getDesCoordinatoreCompCirc() {
		return desCoordinatoreCompCirc;
	}

	public void setDesCoordinatoreCompCirc(String desCoordinatoreCompCirc) {
		this.desCoordinatoreCompCirc = desCoordinatoreCompCirc;
	}

	public List<RespVistiConformitaBean> getRespVistiConformita() {
		return respVistiConformita;
	}

	public void setRespVistiConformita(List<RespVistiConformitaBean> respVistiConformita) {
		this.respVistiConformita = respVistiConformita;
	}

	public String getIdScrivaniaEstensoreMain() {
		return idScrivaniaEstensoreMain;
	}

	public void setIdScrivaniaEstensoreMain(String idScrivaniaEstensoreMain) {
		this.idScrivaniaEstensoreMain = idScrivaniaEstensoreMain;
	}

	public String getLivelliUOScrivaniaEstensoreMain() {
		return livelliUOScrivaniaEstensoreMain;
	}

	public void setLivelliUOScrivaniaEstensoreMain(String livelliUOScrivaniaEstensoreMain) {
		this.livelliUOScrivaniaEstensoreMain = livelliUOScrivaniaEstensoreMain;
	}

	public String getDesScrivaniaEstensoreMain() {
		return desScrivaniaEstensoreMain;
	}

	public void setDesScrivaniaEstensoreMain(String desScrivaniaEstensoreMain) {
		this.desScrivaniaEstensoreMain = desScrivaniaEstensoreMain;
	}

	public List<ScrivaniaEstensoreBean> getAltriEstensori() {
		return altriEstensori;
	}

	public void setAltriEstensori(List<ScrivaniaEstensoreBean> altriEstensori) {
		this.altriEstensori = altriEstensori;
	}

	public String getIdScrivaniaIstruttoreMain() {
		return idScrivaniaIstruttoreMain;
	}

	public void setIdScrivaniaIstruttoreMain(String idScrivaniaIstruttoreMain) {
		this.idScrivaniaIstruttoreMain = idScrivaniaIstruttoreMain;
	}

	public String getLivelliUOScrivaniaIstruttoreMain() {
		return livelliUOScrivaniaIstruttoreMain;
	}

	public void setLivelliUOScrivaniaIstruttoreMain(String livelliUOScrivaniaIstruttoreMain) {
		this.livelliUOScrivaniaIstruttoreMain = livelliUOScrivaniaIstruttoreMain;
	}

	public String getDesScrivaniaIstruttoreMain() {
		return desScrivaniaIstruttoreMain;
	}

	public void setDesScrivaniaIstruttoreMain(String desScrivaniaIstruttoreMain) {
		this.desScrivaniaIstruttoreMain = desScrivaniaIstruttoreMain;
	}

	public List<ScrivaniaEstensoreBean> getAltriIstruttori() {
		return altriIstruttori;
	}

	public void setAltriIstruttori(List<ScrivaniaEstensoreBean> altriIstruttori) {
		this.altriIstruttori = altriIstruttori;
	}

	public List<KeyValueBean> getParereCircoscrizioni() {
		return parereCircoscrizioni;
	}

	public void setParereCircoscrizioni(List<KeyValueBean> parereCircoscrizioni) {
		this.parereCircoscrizioni = parereCircoscrizioni;
	}

	public List<KeyValueBean> getParereCommissioni() {
		return parereCommissioni;
	}

	public void setParereCommissioni(List<KeyValueBean> parereCommissioni) {
		this.parereCommissioni = parereCommissioni;
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

	public List<MovimentiContabiliSICRAXmlBean> getListaMovimentiContabiliSICRA() {
		return listaMovimentiContabiliSICRA;
	}

	public void setListaMovimentiContabiliSICRA(List<MovimentiContabiliSICRAXmlBean> listaMovimentiContabiliSICRA) {
		this.listaMovimentiContabiliSICRA = listaMovimentiContabiliSICRA;
	}

}