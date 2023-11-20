/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.DocumentItem;
import it.eng.auriga.ui.module.layout.client.common.items.SelectItemValoriDizionario;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskFlussoInterface;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.CIGItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.DatiContabiliStoriciWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.DirigenteAdottanteItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.DirigentiConcertoItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.EstensoreItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ListaDatiContabiliSIBItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ListaInvioDatiSpesaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ResponsabileDiProcedimentoItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ResponsabileUnicoProvvedimentoItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ResponsabiliPEGItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.RiferimentiNormativiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiGridItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.SaveModelloAction;
import it.eng.auriga.ui.module.layout.client.protocollazione.SaveModelloWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.SelezionaUOItem;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.IDatiSensibiliItem;
import it.eng.utility.ui.module.layout.client.common.IDocumentItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class NuovaPropostaAtto2Detail extends CustomDetail {
		
	protected static String _TAB_DATI_SCHEDA_ID = "DATI_SCHEDA";
	protected static String _TAB_DATI_SPESA_ID = "DATI_SPESA";
	protected static String _TAB_DATI_SPESA_CORRENTE_ID = "DATI_SPESA_CORRENTE";
	protected static String _TAB_DATI_SPESA_CONTO_CAPITALE_ID = "DATI_SPESA_CONTO_CAPITALE";
//	protected static String _TAB_DATI_SPESA_PERSONALE_ID = "DATI_SPESA_PERSONALE";
	
	protected static String _FLG_SPESA_SI = "SI";
	protected static String _FLG_SPESA_SI_SENZA_VLD_RIL_IMP = "SI, senza validazione/rilascio impegni";
	protected static String _FLG_SPESA_NO = "NO";
	
	protected static String _PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_A = "uff. competente per la definizione della spesa";
	protected static String _PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_B = "uff. Bilancio Centrale (Ragioneria)";
	protected static String _MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B1 = "compilazione griglia";
	protected static String _MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B2 = "xls importabile in SIB";
	protected static String _MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B3 = "associazione impegni su SIB a cura del proponente";
	protected static String _MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B4 = "registrazione su SIB";
	
	protected static String _MANDATORY = "mandatory"; 
	protected static String _OPTIONAL = "optional";
	
	protected static String _STRUTT_COMP_DEF_DATI_CONT_SEMPRE_UGUALE_UO_PROPONENTE = "sempre_uguale_uo_proponente";
	protected static String _STRUTT_COMP_DEF_DATI_CONT_DEFAULT_UGUALE_UO_PROPONENTE = "default_uguale_uo_proponente";
	
	protected NuovaPropostaAtto2Detail instance;
	
	protected String tipoDocumento;
	protected String rowidDoc;
	
	protected VLayout mainLayout;
	protected ToolStrip mainToolStrip;
	protected GWTRestDataSource modelliDS;
	protected SelectItem modelliSelectItem;
	protected ListGrid modelliPickListProperties;
	protected SaveModelloWindow saveModelloWindow;
	protected ToolStripButton salvaComeModelloButton;

	protected TabSet tabSet;
	protected Tab tabDatiScheda;
	protected Tab tabDatiSpesa;
	protected Tab tabDatiSpesaCorrente;
	protected Tab tabDatiSpesaContoCapitale;
//	protected Tab tabDatiSpesaPersonale;
	
	protected boolean toSaveAndReloadTask;
	
	/*******************
	 * TAB DATI SCHEDA *
	 *******************/

	/* Hidden */
	protected DynamicForm hiddenForm;
	protected HiddenItem idUdHiddenItem;
	protected HiddenItem idUdNuovoComeCopiaHiddenItem;
	protected HiddenItem prefKeyModelloHiddenItem;	
	protected HiddenItem prefNameModelloHiddenItem;
	protected HiddenItem useridModelloHiddenItem;
	protected HiddenItem idUoModelloHiddenItem;
	protected HiddenItem tipoDocumentoHiddenItem; 
	protected HiddenItem nomeTipoDocumentoHiddenItem; 
	protected HiddenItem rowidDocHiddenItem;
	protected HiddenItem idDocPrimarioHiddenItem; 
	protected HiddenItem nomeFilePrimarioHiddenItem;
	protected HiddenItem uriFilePrimarioHiddenItem;
	protected HiddenItem remoteUriFilePrimarioHiddenItem;
	protected HiddenItem infoFilePrimarioHiddenItem;
	protected HiddenItem isChangedFilePrimarioHiddenItem;
	protected HiddenItem idDocPrimarioOmissisHiddenItem; 
	protected HiddenItem nomeFilePrimarioOmissisHiddenItem;
	protected HiddenItem uriFilePrimarioOmissisHiddenItem;
	protected HiddenItem remoteUriFilePrimarioOmissisHiddenItem;
	protected HiddenItem infoFilePrimarioOmissisHiddenItem;		
	protected HiddenItem isChangedFilePrimarioOmissisHiddenItem;
	protected HiddenItem idPropostaAMCHiddenItem;
	
	/* Dati scheda - Registrazione */
	protected NuovaPropostaAtto2DetailSection detailSectionRegistrazione;
	protected DynamicForm registrazioneForm;
	protected ImgItem iconaTipoRegistrazioneItem; 
	protected TextItem siglaRegistrazioneItem;
	protected NumericItem numeroRegistrazioneItem;
	protected DateTimeItem dataRegistrazioneItem;
	protected TextItem desUserRegistrazioneItem;
	protected TextItem desUORegistrazioneItem;
	protected ImgItem iconaTipoRegProvvisoriaItem; 
	protected TextItem siglaRegProvvisoriaItem;
	protected NumericItem numeroRegProvvisoriaItem;
	protected DateTimeItem dataRegProvvisoriaItem;
	protected TextItem desUserRegProvvisoriaItem;
	protected TextItem desUORegProvvisoriaItem;
	
	/* Dati scheda - Pubblicazione */
	protected NuovaPropostaAtto2DetailSection detailSectionPubblicazione;
	protected DynamicForm pubblicazioneForm;
	protected DateItem dataInizioPubblicazioneItem;
	protected NumericItem giorniPubblicazioneItem;
		
	/* Dati scheda - Ruoli */
	protected NuovaPropostaAtto2DetailSection detailSectionRuoli;
	protected DynamicForm ruoliForm;
	protected HiddenItem codUfficioProponenteItem;
	protected HiddenItem desUfficioProponenteItem;
	protected SelezionaUOItem listaUfficioProponenteItem;
	protected SelectItem ufficioProponenteItem;
	protected DirigenteAdottanteItem listaAdottanteItem;
	protected ResponsabileDiProcedimentoItem listaRdPItem;
	protected ResponsabileUnicoProvvedimentoItem listaRUPItem;
	protected CheckboxItem flgRichiediVistoDirettoreItem;
	
	/* Dati scheda - Proposta di concerto con */
	protected NuovaPropostaAtto2DetailSection detailSectionPropostaConcertoCon;
	protected DynamicForm propostaConcertoConForm;
	protected DirigentiConcertoItem listaDirigentiConcertoItem;
	
	/* Dati scheda - Estensori */
	protected NuovaPropostaAtto2DetailSection detailSectionEstensori;
	protected DynamicForm estensoriForm;
	protected EstensoreItem listaEstensoriItem;
	
	/* Dati scheda - CIG */
	protected NuovaPropostaAtto2DetailSection detailSectionCIG;
	protected DynamicForm CIGForm;
	protected CIGItem listaCIGItem;
	
	/* Dati scheda - Oggetto */
	protected NuovaPropostaAtto2DetailSection detailSectionOggetto;
	protected DynamicForm oggettoForm;
	protected HiddenItem oggettoItem;
	protected CKEditorItem oggettoHtmlItem;
	
	/* Dati scheda - Specificità del provvedimento */
	protected NuovaPropostaAtto2DetailSection detailSectionCaratteristicheProvvedimento;
	protected DynamicForm caratteristicheProvvedimentoForm1;
	protected DynamicForm caratteristicheProvvedimentoForm2;
	protected DynamicForm caratteristicheProvvedimentoForm3;
	protected CheckboxItem flgDeterminaAContrarreTramiteProceduraGaraItem;
	protected CheckboxItem flgDeterminaAggiudicaProceduraGaraItem;
	protected CheckboxItem flgDeterminaRimodulazioneSpesaGaraAggiudicataItem;
	protected CheckboxItem flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem;

	protected HiddenItem idUdAttoDeterminaAContrarreItem;
	protected SelectItem categoriaRegAttoDeterminaAContrarreItem;
	protected ExtendedTextItem siglaAttoDeterminaAContrarreItem;
	protected ExtendedNumericItem numeroAttoDeterminaAContrarreItem;
	protected AnnoItem annoAttoDeterminaAContrarreItem;
		
	protected RadioGroupItem flgSpesaItem;
	protected CheckboxItem flgRichVerificaDiBilancioCorrenteItem; 
	protected CheckboxItem flgRichVerificaDiBilancioContoCapitaleItem; 
	protected CheckboxItem flgRichVerificaDiContabilitaItem;
	protected CheckboxItem flgPresaVisioneContabilitaItem;
	
	/* Dati scheda - Riferimenti normativi */
	protected NuovaPropostaAtto2DetailSection detailSectionRiferimentiNormativi;
	protected DynamicForm riferimentiNormativiForm;
	protected RiferimentiNormativiItem listaRiferimentiNormativiItem;
	
	/* Dati scheda - Atti presupposti */
	protected NuovaPropostaAtto2DetailSection detailSectionAttiPresupposti;
	protected DynamicForm attiPresuppostiForm;
//	protected AttiPresuppostiItem listaAttiPresuppostiItem;
	protected CKEditorItem attiPresuppostiItem;
		
	/* Dati scheda - Motivazioni */
	protected NuovaPropostaAtto2DetailSection detailSectionMotivazioni;
	protected DynamicForm motivazioniForm;
	protected CKEditorItem motivazioniItem;
	
	/* Dati scheda - Dispositivo */
	protected NuovaPropostaAtto2DetailSection detailSectionDispositivo;
	protected DynamicForm dispositivoForm;
	protected CKEditorItem dispositivoItem;
	protected SelectItemValoriDizionario loghiAggiuntiviDispositivoItem;
	
	/* Dati scheda - Allegati */
	protected NuovaPropostaAtto2DetailSection detailSectionAllegati;	
	protected DynamicForm allegatiForm;
	protected AllegatiItem listaAllegatiItem;
	
	/******************
	 * TAB DATI SPESA *
	 ******************/

	/* Dati spesa - Ruoli */
	protected NuovaPropostaAtto2DetailSection detailSectionRuoliSpesa;
	protected DynamicForm ruoliSpesaForm1;
	protected DynamicForm ruoliSpesaForm2;
	protected CheckboxItem flgAdottanteUnicoRespPEGItem;	
	protected ResponsabiliPEGItem listaResponsabiliPEGItem;
	protected HiddenItem codUfficioDefinizioneSpesaItem;
	protected HiddenItem desUfficioDefinizioneSpesaItem;
	protected SelezionaUOItem listaUfficioDefinizioneSpesaItem;
	
	/* Dati spesa - Opzioni */
	protected NuovaPropostaAtto2DetailSection detailSectionOpzioniSpesa;
	protected DynamicForm opzioniSpesaForm;		
	protected CheckboxItem flgSpesaCorrenteItem;
	protected CheckboxItem flgImpegniCorrenteGiaValidatiItem;
	protected CheckboxItem flgSpesaContoCapitaleItem;
	protected CheckboxItem flgImpegniContoCapitaleGiaRilasciatiItem;
	protected CheckboxItem flgSoloSubImpSubCronoItem;
	protected CheckboxItem flgConVerificaContabilitaItem;
	protected CheckboxItem flgRichiediParereRevisoriContabiliItem;
	
	protected HashSet<String> vociPEGNoVerifDisp = new HashSet<String>();
	
	/***************************
	 * TAB DATI SPESA CORRENTE *
	 ***************************/

	protected NuovaPropostaAtto2DetailSection detailSectionOpzioniSpesaCorrente;	
	protected DynamicForm opzioniSpesaCorrenteForm1;
	protected CheckboxItem flgDisattivaAutoRequestDatiContabiliSIBCorrenteItem;
	protected DynamicForm opzioniSpesaCorrenteForm2;
	protected RadioGroupItem prenotazioneSpesaSIBDiCorrenteItem;
	protected RadioGroupItem modalitaInvioDatiSpesaARagioneriaCorrenteItem;
	
	/* Dati spesa corrente - Impegni e accertamenti */
	protected NuovaPropostaAtto2DetailSection detailSectionDatiContabiliSIBCorrente;	
	protected DynamicForm datiContabiliSIBCorrenteForm;
	protected ListaDatiContabiliSIBItem listaDatiContabiliSIBCorrenteItem;
	
	/* Dati spesa corrente - Compilazione griglia */
	protected NuovaPropostaAtto2DetailSection detailSectionInvioDatiSpesaCorrente;	
	protected DynamicForm invioDatiSpesaCorrenteForm;
	protected ListaInvioDatiSpesaItem listaInvioDatiSpesaCorrenteItem;
	
	/* Dati spesa corrente - Upload xls importabile in SIB */
	protected NuovaPropostaAtto2DetailSection detailSectionFileXlsCorrente;	
	protected DynamicForm fileXlsCorrenteForm;
	protected DocumentItem fileXlsCorrenteItem;
	protected HiddenItem nomeFileTracciatoXlsCorrenteItem;
	protected HiddenItem uriFileTracciatoXlsCorrenteItem;
	protected ImgButtonItem scaricaTracciatoXlsCorrenteButton;
	
	/* Dati spesa corrente - Allegati */
	protected NuovaPropostaAtto2DetailSection detailSectionAllegatiCorrente;	
	protected DynamicForm allegatiCorrenteForm;
	protected AllegatiItem listaAllegatiCorrenteItem;
	
	/* Dati spesa corrente - Note */
	protected NuovaPropostaAtto2DetailSection detailSectionNoteCorrente;	
	protected DynamicForm noteCorrenteForm;
	protected CKEditorItem noteCorrenteItem;
		
	/*********************************
	 * TAB DATI SPESA CONTO CAPITALE *
	 *********************************/

	protected NuovaPropostaAtto2DetailSection detailSectionOpzioniSpesaContoCapitale;	
	protected DynamicForm opzioniSpesaContoCapitaleForm1;	
	protected CheckboxItem flgDisattivaAutoRequestDatiContabiliSIBContoCapitaleItem;
	protected DynamicForm opzioniSpesaContoCapitaleForm2;	
	protected RadioGroupItem modalitaInvioDatiSpesaARagioneriaContoCapitaleItem;
	
	/* Dati spesa conto capitale - Impegni e accertamenti */
	protected NuovaPropostaAtto2DetailSection detailSectionDatiContabiliSIBContoCapitale;	
	protected DynamicForm datiContabiliSIBContoCapitaleForm;
	protected ListaDatiContabiliSIBItem listaDatiContabiliSIBContoCapitaleItem;
	
	/* Dati spesa conto capitale - Compilazione griglia */
	protected NuovaPropostaAtto2DetailSection detailSectionInvioDatiSpesaContoCapitale;	
	protected DynamicForm invioDatiSpesaContoCapitaleForm;
	protected ListaInvioDatiSpesaItem listaInvioDatiSpesaContoCapitaleItem;
	
	/* Dati spesa conto capitale - Upload xls importabile in SIB */
	protected NuovaPropostaAtto2DetailSection detailSectionFileXlsContoCapitale;	
	protected DynamicForm fileXlsContoCapitaleForm;
	protected DocumentItem fileXlsContoCapitaleItem;
	protected HiddenItem nomeFileTracciatoXlsContoCapitaleItem;
	protected HiddenItem uriFileTracciatoXlsContoCapitaleItem;
	protected ImgButtonItem scaricaTracciatoXlsContoCapitaleButton;
	
	/* Dati spesa conto capitale - Allegati */
	protected NuovaPropostaAtto2DetailSection detailSectionAllegatiContoCapitale;	
	protected DynamicForm allegatiContoCapitaleForm;
	protected AllegatiItem listaAllegatiContoCapitaleItem;
	
	/* Dati spesa conto capitale - Note */
	protected NuovaPropostaAtto2DetailSection detailSectionNoteContoCapitale;	
	protected DynamicForm noteContoCapitaleForm;
	protected CKEditorItem noteContoCapitaleItem;
	
	/****************************
	 * TAB DATI SPESA PERSONALE *
	 ****************************/

	/* Dati spesa personale - Spesa anno corrente ed eventuali successivi */
//	protected NuovaPropostaAtto2DetailSection detailSectionSpesaAnnoCorrenteESuccessivi;	
//	protected DatiSpesaAnnualiXDetPersonaleItem listaDatiSpesaAnnualiXDetPersonaleItem;
		
	/* Dati spesa personale - Spesa annua */
//	protected NuovaPropostaAtto2DetailSection detailSectionSpesaAnnua;	
//	protected FilteredSelectItem capitoloDatiSpesaAnnuaXDetPersItem;
//	protected SelectItem articoloDatiSpesaAnnuaXDetPersItem;
//	protected SelectItem numeroDatiSpesaAnnuaXDetPersItem;
//	protected ExtendedNumericItem importoDatiSpesaAnnuaXDetPersItem;
	
	/******************************
	 * TAB ATTRIBUTI DINAMICI DOC *
	 ******************************/

	protected LinkedHashMap<String, String> attributiAddDocTabs;
	protected HashMap<String, VLayout> attributiAddDocLayouts;
	protected HashMap<String, AttributiDinamiciDetail> attributiAddDocDetails;
	protected Map<String, Object> attributiDinamiciDocDaCopiare;
	
	public NuovaPropostaAtto2Detail(String nomeEntita) {
		this(nomeEntita, null, null);
	}
	
	public NuovaPropostaAtto2Detail(String nomeEntita, LinkedHashMap<String, String> attributiAddDocTabs) {
		this(nomeEntita, attributiAddDocTabs, null);
	}
	
	public NuovaPropostaAtto2Detail(String nomeEntita, LinkedHashMap<String, String> attributiAddDocTabs, String idTipoDoc) {
		
		super(nomeEntita);

		instance = this;
		this.attributiAddDocTabs = attributiAddDocTabs != null ? attributiAddDocTabs : new LinkedHashMap<String, String>();
		this.tipoDocumento = idTipoDoc;
		
		setPaddingAsLayoutMargin(false);		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		if(!skipSuperBuild()) {
			build();
		}
	}
	
	public boolean skipSuperBuild() {
		return false;
	}
	
	public void build() {
		
		createMainLayout();
		setMembers(mainLayout);

		enableDisableTabs();
		showHideSections();		
	}
	
	public boolean isAvvioPropostaAtto() {
		return false;
	}	
	
	public boolean isAttivaRequestMovimentiDaAMC() {
		return false;
	}
	
	public boolean isAttivaSalvataggioMovimentiDaAMC() {
		return false;
	}	
	
	public boolean isEseguibile() {
		return editing;
	}
	
	public boolean isDatiSpesaEditabili() {
		return false;
	}
	
	/*
	public boolean isEsitoTaskSelezionatoOk() {
		return false;
	}
	*/
	
	public String getMessaggioTab(String tabID) {
		return null;
	}

	/**
	 * Metodo per costruire il layout della maschera
	 * 
	 */
	protected void createMainLayout() {
		
		createMainToolStrip();
		createTabSet();
		
		mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();		
		mainLayout.addMember(mainToolStrip);		
		mainLayout.addMember(tabSet);
		
		if (!showMainToolStrip()) {
			mainToolStrip.hide();
		}
	}
	
	/**
	 * Metodo che indica se mostrare o meno la barra superiore
	 * 
	 */
	public boolean showMainToolStrip() {
		return showModelliSelectItem();
	}
	
	/**
	 * Metodo per costruire la barra superiore contenente la select dei modelli
	 * 
	 */
	protected void createMainToolStrip() {

		createModelliSelectItem();
		createSalvaComeModelloButton();
		
		mainToolStrip = new ToolStrip();
		mainToolStrip.setBackgroundColor("transparent");
		mainToolStrip.setBackgroundImage("blank.png");
		mainToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		mainToolStrip.setBorder("0px");
		mainToolStrip.setWidth100();
		mainToolStrip.setHeight(30);
		mainToolStrip.setRedrawOnResize(true);
		
		modelliSelectItem.setVisible(showModelliSelectItem());
		salvaComeModelloButton.setVisible(showModelliSelectItem());
		
		mainToolStrip.addFormItem(modelliSelectItem);
		mainToolStrip.addButton(salvaComeModelloButton);
		mainToolStrip.addFill();
	}
	
	/**
	 * Metodo che indica se mostrare o meno la select dei modelli
	 * 
	 */
	public boolean showModelliSelectItem() {
		return isAvvioPropostaAtto();
	}
	
	/**
	 * Metodo che indica se è possibile rimuovere il modello dalla select
	 * 
	 */
	public boolean isAbilToRemoveModello(Record record) {
		return (record.getAttribute("key") != null && !"".equals(record.getAttributeAsString("key"))) && 
			   (record.getAttributeAsBoolean("flgAbilDel") != null && record.getAttributeAsBoolean("flgAbilDel"));
	}

	/**
	 * Metodo per costruire la select dei modelli (per il settaggio dei valori
	 * dei campi a maschera)
	 * 
	 */
	protected void createModelliSelectItem() throws IllegalStateException {

		modelliDS = new GWTRestDataSource("ModelliDataSource", "key", FieldType.TEXT);
		modelliDS.addParam("prefKey", nomeEntita + ".modelli");
		modelliDS.addParam("isAbilToPublic", Layout.isPrivilegioAttivo("MRP") ? "1" : "0");

		modelliSelectItem = new SelectItem("modelli");
		modelliSelectItem.setValueField("key");
		modelliSelectItem.setDisplayField("displayValue");
		modelliSelectItem.setTitle("<b>" + I18NUtil.getMessages().protocollazione_detail_modelliSelectItem_title() + "</b>");
		modelliSelectItem.setCachePickListResults(false);
		modelliSelectItem.setRedrawOnChange(true);
		modelliSelectItem.setShowOptionsFromDataSource(true);
		modelliSelectItem.setOptionDataSource(modelliDS);
		modelliSelectItem.setAllowEmptyValue(true);

		ListGridField modelliRemoveField = new ListGridField("remove");
		modelliRemoveField.setType(ListGridFieldType.ICON);
		modelliRemoveField.setWidth(18);
		modelliRemoveField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isAbilToRemoveModello(record)) {
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				}
				return null;
			}
		});
		modelliRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(final RecordClickEvent event) {
				if (isAbilToRemoveModello(event.getRecord())) {
					SC.ask(I18NUtil.getMessages().gestioneModelli_cancellazione_ask(), new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value) {
								final String key = event.getRecord().getAttribute("key");
								modelliDS.removeData(event.getRecord(), new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										String value = (String) modelliSelectItem.getValue();
										if (key != null && value != null && key.equals(value)) {
											modelliSelectItem.setValue((String) null);
										}
									} 
								});
							}
						}
					});
				}	
			}   
		});
		ListGridField modelliKeyField = new ListGridField("key");
		modelliKeyField.setHidden(true);
		ListGridField modelliDisplayValueField = new ListGridField("displayValue");
		modelliDisplayValueField.setWidth("100%");
		modelliSelectItem.setPickListFields(modelliRemoveField, modelliKeyField, modelliDisplayValueField);

		modelliPickListProperties = new ListGrid();
		modelliPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		modelliPickListProperties.setShowHeader(false);
		// apply the selected preference from the SelectItem
		modelliPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {				
				boolean isRemoveField = isAbilToRemoveModello(event.getRecord()) && event.getColNum() == 0;
				if(!isRemoveField) {									
					String userid = (String) event.getRecord().getAttribute("userid");
					String prefName = (String) event.getRecord().getAttribute("prefName");
					if (prefName != null && !"".equals(prefName)) {
						AdvancedCriteria criteria = new AdvancedCriteria();
						criteria.addCriteria("prefName", OperatorId.EQUALS, prefName);
						criteria.addCriteria("flgIncludiPubbl", userid.startsWith("PUBLIC.") ? "1" : "0");
						if (userid.startsWith("UO.")) {
							String idUo = (String) event.getRecord().getAttribute("idUo");
							criteria.addCriteria("idUo", idUo);
						}
						modelliDS.addParam("isControllaValiditaIndirizzoMezzoTrasm",  "1"); 					
						modelliDS.fetchData(criteria, new DSCallback() {
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Record[] data = response.getData();
									if (data.length != 0) {
										final Record recordModello = data[0];
										Record record = new Record(JSON.decode(recordModello.getAttribute("value")));	
										editNewRecordFromModello(record.toMap(), recordModello);
										newMode();
									}
								}
							}
						});
					}					
				}
			}
		});

		modelliSelectItem.setPickListProperties(modelliPickListProperties);
	}
	
	public void editNewRecordFromModello(Map valuesFromModello, Record recordModello) {
		if(recordModello != null) {
			valuesFromModello.put("prefKeyModello", recordModello.getAttribute("prefKey"));
			valuesFromModello.put("prefNameModello", recordModello.getAttribute("prefName"));
			valuesFromModello.put("useridModello", recordModello.getAttribute("userid"));
			valuesFromModello.put("idUoModello", recordModello.getAttribute("idUo"));
		}		
		RecordList listaUfficioProponente = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaUfficioProponente") : null;
		valuesFromModello.put("listaUfficioProponente", listaUfficioProponente);
		valuesFromModello.put("ufficioProponente", getValueAsString("ufficioProponente"));
		valuesFromModello.put("codUfficioProponente", getValueAsString("codUfficioProponente"));
		valuesFromModello.put("desUfficioProponente", getValueAsString("desUfficioProponente"));
		editNewRecord(valuesFromModello);
	}

	/**
	 * Metodo per costruire la finestra di salvataggio del modello
	 * 
	 */
	protected void createSaveModelloWindow(String nomeEntita) {

		if (saveModelloWindow == null) {
			SaveModelloAction saveModelloAction = new SaveModelloAction(modelliDS, modelliSelectItem) {

				@Override
				public Map getMapValuesForAdd() {
					Record lRecordToSave = getRecordToSave();
					Map values = lRecordToSave != null ? lRecordToSave.toMap() : vm.getValues();
					removeValuesToSkipInModello(values);		
					if (attributiAddDocDetails != null) {
						values.put("valori", getAttributiDinamiciDocForLoad());
					}
					return values;
				}

				@Override
				public Map getMapValuesForUpdate() {
					Record lRecordToSave = getRecordToSave();
					Map values = lRecordToSave != null ? lRecordToSave.toMap() : vm.getValues();
					removeValuesToSkipInModello(values);  
					if (attributiAddDocDetails != null) {
						values.put("valori", getAttributiDinamiciDocForLoad());
					}
					return values;
				}
			};
			saveModelloWindow = new SaveModelloWindow(
					I18NUtil.getMessages().protocollazione_detail_salvaComeModelloButton_prompt(), nomeEntita,
					saveModelloAction) {

				@Override
				public boolean isAbilToSavePublic() {
					return Layout.isPrivilegioAttivo("MRP");
				}
				
				@Override
				public boolean isTrasmissioneAtti() {
					return false;
				}
			};
		}
	}
	
	public void removeValuesToSkipInModello(Map<String, Object> values) {
		removeValuesToSkipInCopia(values, false);
	}
	
	public void removeValuesToSkipInNuovoComeCopia(Map<String, Object> values) {
		values.put("idUdNuovoComeCopia", values.get("idUd"));
		removeValuesToSkipInCopia(values, true);
	}
	
	public void removeValuesToSkipInCopia(Map<String, Object> values, boolean isNuovoComeCopia) {
		values.remove("idUd");
		values.remove("rowidDoc");
		values.remove("idDocPrimario");		
		values.remove("nomeFilePrimario");
		values.remove("uriFilePrimario");
		values.remove("remoteUriFilePrimario");
		values.remove("infoFilePrimario");
		values.remove("isChangedFilePrimario");
		values.remove("idDocPrimarioOmissis");
		values.remove("nomeFilePrimarioOmissis");
		values.remove("uriFilePrimarioOmissis");
		values.remove("remoteUriFilePrimarioOmissis");
		values.remove("infoFilePrimarioOmissis");
		values.remove("isChangedFilePrimarioOmissis");
		values.remove("idPropostaAMC");		
		values.remove("siglaRegistrazione");
		values.remove("numeroRegistrazione");
		values.remove("dataRegistrazione");
		values.remove("desUserRegistrazione");		
		values.remove("desUORegistrazione");		
		values.remove("siglaRegProvvisoria");		
		values.remove("numeroRegProvvisoria");		
		values.remove("dataRegProvvisoria");		
		values.remove("desUserRegProvvisoria");		
		values.remove("desUORegProvvisoria");	
		values.remove("listaUfficioProponente");
		values.remove("codUfficioProponente");
		values.remove("desUfficioProponente");
		values.remove("ufficioProponente");
		values.remove("listaAllegati");		
		values.remove("listaDatiContabiliSIBCorrente");
		values.remove("listaInvioDatiSpesaCorrente");
		values.remove("listaAllegatiCorrente");
		values.remove("listaDatiContabiliSIBContoCapitale");
		values.remove("listaInvioDatiSpesaContoCapitale");
		values.remove("listaAllegatiContoCapitale");		
	}

	/**
	 * Metodo per costruire il bottone "Salva come modello"
	 * 
	 */
	protected void createSalvaComeModelloButton() {

		salvaComeModelloButton = new ToolStripButton(
				I18NUtil.getMessages().protocollazione_detail_salvaComeModelloButton_prompt(),
				"buttons/template_save.png");
		salvaComeModelloButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickSalvaComeModello();
			}
		});
	}

	/**
	 * Metodo che implementa l'azione del bottone "Salva come modello"
	 * 
	 */
	public void clickSalvaComeModello() {

		createSaveModelloWindow(nomeEntita);
		if ((!saveModelloWindow.isDrawn()) || (!saveModelloWindow.isVisible())) {
			saveModelloWindow.clearValues();
			saveModelloWindow.selezionaModello(modelliSelectItem.getSelectedRecord());
			saveModelloWindow.redrawForms();
			saveModelloWindow.redraw();
			saveModelloWindow.show();
		}
	}
	
	/**
	 * Metodo per costruire il TabSet
	 * 
	 */
	protected void createTabSet() throws IllegalStateException {

		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		tabSet.setWidth100();
		tabSet.setBorder("0px");
		tabSet.setCanFocus(false);
		tabSet.setTabIndex(-1);
		tabSet.setPaneMargin(0);
		
		createTabDatiScheda();
//		tabSet.addTab(tabDatiScheda);
		
		// Intanto per le determine senza spesa nascondo tutti gli altri tab
		createTabDatiSpesa();
//		tabSet.addTab(tabDatiSpesa);
		
		if(!isAvvioPropostaAtto() && isAttivoSIB()) {
			
			createTabDatiSpesaCorrente();
//			tabSet.addTab(tabDatiSpesaCorrente);
			
			createTabDatiSpesaContoCapitale();
//			tabSet.addTab(tabDatiSpesaContoCapitale);
			
//			createTabDatiSpesaPersonale();
//			tabSet.addTab(tabDatiSpesaPersonale);
		}
	}

	/**
	 * Metodo per costruire il pane associato ad un tab generico
	 * 
	 */
	protected VLayout createTabPane(VLayout layout) {

		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		
		VLayout layoutTab = new VLayout();
		layoutTab.setWidth100();
		layoutTab.setHeight100();
		layoutTab.addMember(layout);
		layoutTab.addMember(spacerLayout);
		layoutTab.setRedrawOnResize(true);
		
		return layoutTab;
	}

	/**
	 * Metodo per costruire il tab "Dati scheda"
	 * 
	 */
	protected void createTabDatiScheda() {

		tabDatiScheda = new Tab("<b>" + I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiScheda_prompt() + "</b>");
		tabDatiScheda.setAttribute("tabID", _TAB_DATI_SCHEDA_ID);
		tabDatiScheda.setPrompt(I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiScheda_prompt());
		tabDatiScheda.setPane(createTabPane(getLayoutDatiScheda()));
	}

	/**
	 * Metodo che restituisce il layout del tab "Dati scheda"
	 * 
	 */
	public VLayout getLayoutDatiScheda() {

		VLayout layoutDatiScheda = new VLayout(5);
		
		createHiddenForm();
		layoutDatiScheda.addMember(hiddenForm);
		
		createDetailSectionRegistrazione();
		detailSectionRegistrazione.setVisible(false);
		layoutDatiScheda.addMember(detailSectionRegistrazione);
		
		createDetailSectionPubblicazione();
		detailSectionPubblicazione.setVisible(false);
		layoutDatiScheda.addMember(detailSectionPubblicazione);

		createDetailSectionRuoli();
		layoutDatiScheda.addMember(detailSectionRuoli);

		createDetailSectionPropostaConcertoCon();
		layoutDatiScheda.addMember(detailSectionPropostaConcertoCon);	
		
		createDetailSectionEstensori();
		layoutDatiScheda.addMember(detailSectionEstensori);			

		createDetailSectionCIG();
		layoutDatiScheda.addMember(detailSectionCIG);
		
		createDetailSectionOggetto();
		layoutDatiScheda.addMember(detailSectionOggetto);
		
		createDetailSectionCaratteristicheProvvedimento();
		layoutDatiScheda.addMember(detailSectionCaratteristicheProvvedimento);
		
		createDetailSectionRiferimentiNormativi();			
		layoutDatiScheda.addMember(detailSectionRiferimentiNormativi);
		
		createDetailSectionAttiPresupposti();			
		layoutDatiScheda.addMember(detailSectionAttiPresupposti);
		
		createDetailSectionMotivazioni();			
		layoutDatiScheda.addMember(detailSectionMotivazioni);
		
		createDetailSectionDispositivo();			
		layoutDatiScheda.addMember(detailSectionDispositivo);
				
		// se non sono in avvio	mostro anche gli allegati	
		if(!isAvvioPropostaAtto()) {		
			createDetailSectionAllegati();			
			layoutDatiScheda.addMember(detailSectionAllegati);					
		}
	
		return layoutDatiScheda;
	}
	
	protected void createHiddenForm() {
		
		hiddenForm = new DynamicForm();
		hiddenForm.setValuesManager(vm);
//		hiddenForm.setHeight(0);
		hiddenForm.setOverflow(Overflow.HIDDEN);
		hiddenForm.setTabSet(tabSet);
		hiddenForm.setTabID(_TAB_DATI_SCHEDA_ID);
		
		idUdHiddenItem = new HiddenItem("idUd");
		idUdNuovoComeCopiaHiddenItem = new HiddenItem("idUdNuovoComeCopia");
		prefKeyModelloHiddenItem = new HiddenItem("prefKeyModello");
		prefNameModelloHiddenItem = new HiddenItem("prefNameModello");
		useridModelloHiddenItem = new HiddenItem("useridModello");
		idUoModelloHiddenItem = new HiddenItem("idUoModello");
		tipoDocumentoHiddenItem = new HiddenItem("tipoDocumento");
		nomeTipoDocumentoHiddenItem = new HiddenItem("nomeTipoDocumento");
		rowidDocHiddenItem = new HiddenItem("rowidDoc");
		idDocPrimarioHiddenItem = new HiddenItem("idDocPrimario");		
		nomeFilePrimarioHiddenItem = new HiddenItem("nomeFilePrimario");
		uriFilePrimarioHiddenItem = new HiddenItem("uriFilePrimario");
		remoteUriFilePrimarioHiddenItem = new HiddenItem("remoteUriFilePrimario");
		infoFilePrimarioHiddenItem = new HiddenItem("infoFilePrimario");
		isChangedFilePrimarioHiddenItem = new HiddenItem("isChangedFilePrimario");
		idDocPrimarioOmissisHiddenItem = new HiddenItem("idDocPrimarioOmissis");
		nomeFilePrimarioOmissisHiddenItem = new HiddenItem("nomeFilePrimarioOmissis");
		uriFilePrimarioOmissisHiddenItem = new HiddenItem("uriFilePrimarioOmissis");
		remoteUriFilePrimarioOmissisHiddenItem = new HiddenItem("remoteUriFilePrimarioOmissis");
		infoFilePrimarioOmissisHiddenItem = new HiddenItem("infoFilePrimarioOmissis");
		isChangedFilePrimarioOmissisHiddenItem = new HiddenItem("isChangedFilePrimarioOmissis");
		idPropostaAMCHiddenItem = new HiddenItem("idPropostaAMC");	
		
		hiddenForm.setFields(
			idUdHiddenItem, 
			idUdNuovoComeCopiaHiddenItem,
			prefKeyModelloHiddenItem,
			prefNameModelloHiddenItem,
			useridModelloHiddenItem,
			idUoModelloHiddenItem,
			tipoDocumentoHiddenItem, 
			nomeTipoDocumentoHiddenItem, 
			rowidDocHiddenItem,
			idDocPrimarioHiddenItem, 
			nomeFilePrimarioHiddenItem,
			uriFilePrimarioHiddenItem,
			remoteUriFilePrimarioHiddenItem,
			infoFilePrimarioHiddenItem,
			isChangedFilePrimarioHiddenItem,
			idDocPrimarioOmissisHiddenItem, 
			nomeFilePrimarioOmissisHiddenItem,
			uriFilePrimarioOmissisHiddenItem,
			remoteUriFilePrimarioOmissisHiddenItem,
			infoFilePrimarioOmissisHiddenItem,		
			isChangedFilePrimarioOmissisHiddenItem,
			idPropostaAMCHiddenItem	
		);
	}
	
	public boolean showDetailSectionRegistrazione() {
		return !"".equals(getValueAsString("numeroRegistrazione")) || !"".equals(getValueAsString("numeroRegProvvisoria"));
	}
	
	protected void createDetailSectionRegistrazione() {
		
		createRegistrazioneForm();
		
		detailSectionRegistrazione = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionRegistrazione_title(), true, true, true, registrazioneForm);
	}
	
	public boolean showDesUORegistrazioneItem() {
		return true;
	}
	
	protected void createRegistrazioneForm() {

		registrazioneForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		registrazioneForm.setValuesManager(vm);
		registrazioneForm.setWidth100();
		registrazioneForm.setPadding(5);
		registrazioneForm.setWrapItemTitles(false);
		registrazioneForm.setNumCols(20);
		registrazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		registrazioneForm.setTabSet(tabSet);
		registrazioneForm.setTabID(_TAB_DATI_SCHEDA_ID);
		
		//Numerazione atto
		
		iconaTipoRegistrazioneItem = new ImgItem("iconaTipoRegistrazione", "menu/iter_atti.png", I18NUtil.getMessages().nuovaPropostaAtto2_detail_iconaTipoRegistrazione_prompt());
		iconaTipoRegistrazioneItem.setColSpan(1);
		iconaTipoRegistrazioneItem.setIconWidth(16);
		iconaTipoRegistrazioneItem.setIconHeight(16);
		iconaTipoRegistrazioneItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaTipoRegistrazioneItem.setAlign(Alignment.LEFT);
		iconaTipoRegistrazioneItem.setWidth(16);
		iconaTipoRegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegistrazione"));
			}
		});

		siglaRegistrazioneItem = new TextItem("siglaRegistrazione",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_siglaRegistrazione_title());
		siglaRegistrazioneItem.setWidth(100);
		siglaRegistrazioneItem.setColSpan(1);
		siglaRegistrazioneItem.setShowTitle(false);
		siglaRegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegistrazione"));
			}
		});

		numeroRegistrazioneItem = new NumericItem("numeroRegistrazione",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_numeroRegistrazione_title());
		numeroRegistrazioneItem.setColSpan(1);
		numeroRegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegistrazione"));
			}
		});
		
		dataRegistrazioneItem = new DateTimeItem("dataRegistrazione",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_dataRegistrazione_title());
		dataRegistrazioneItem.setColSpan(1);
		dataRegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegistrazione"));
			}
		});

		desUserRegistrazioneItem = new TextItem("desUserRegistrazione",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_desUserRegistrazione_title());
		desUserRegistrazioneItem.setWidth(250);
		desUserRegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegistrazione")) && !"".equals(getValueAsString("desUserRegistrazione"));
			}
		});

		desUORegistrazioneItem = new TextItem("desUORegistrazione",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_desUORegistrazione_title());
		desUORegistrazioneItem.setWidth(250);
		desUORegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegistrazione")) && !"".equals(getValueAsString("desUORegistrazione")) && showDesUORegistrazioneItem();				 
			}
		});
		
		// Numerazione provvisoria di proposta atto
		
		iconaTipoRegProvvisoriaItem = new ImgItem("iconaTipoRegProvvisoria", "protocollazione/provvisoria.png", I18NUtil.getMessages().nuovaPropostaAtto2_detail_iconaTipoRegProvvisoria_prompt());
		iconaTipoRegProvvisoriaItem.setColSpan(1);
		iconaTipoRegProvvisoriaItem.setIconWidth(16);
		iconaTipoRegProvvisoriaItem.setIconHeight(16);
		iconaTipoRegProvvisoriaItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaTipoRegProvvisoriaItem.setAlign(Alignment.LEFT);
		iconaTipoRegProvvisoriaItem.setWidth(16);
		iconaTipoRegProvvisoriaItem.setStartRow(true);		
		iconaTipoRegProvvisoriaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegProvvisoria"));
			}
		});

		siglaRegProvvisoriaItem = new TextItem("siglaRegProvvisoria",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_siglaRegProvvisoria_title());
		siglaRegProvvisoriaItem.setWidth(100);
		siglaRegProvvisoriaItem.setColSpan(1);
		siglaRegProvvisoriaItem.setShowTitle(false);
		siglaRegProvvisoriaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegProvvisoria"));
			}
		});

		numeroRegProvvisoriaItem = new NumericItem("numeroRegProvvisoria",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_numeroRegProvvisoria_title());
		numeroRegProvvisoriaItem.setColSpan(1);
		numeroRegProvvisoriaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegProvvisoria"));
			}
		});

		dataRegProvvisoriaItem = new DateTimeItem("dataRegProvvisoria",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_dataRegProvvisoria_title());
		dataRegProvvisoriaItem.setColSpan(1);
		dataRegProvvisoriaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegProvvisoria"));
			}
		});

		desUserRegProvvisoriaItem = new TextItem("desUserRegProvvisoria",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_desUserRegProvvisoria_title());
		desUserRegProvvisoriaItem.setWidth(250);
		desUserRegProvvisoriaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegProvvisoria")) && !"".equals(getValueAsString("desUserRegProvvisoria")) && "".equals(getValueAsString("numeroRegistrazione"));				
			}
		});

		desUORegProvvisoriaItem = new TextItem("desUORegProvvisoria",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_desUORegProvvisoria_title());
		desUORegProvvisoriaItem.setWidth(250);
		desUORegProvvisoriaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegProvvisoria")) && !"".equals(getValueAsString("desUORegProvvisoria")) && "".equals(getValueAsString("numeroRegistrazione")) && showDesUORegistrazioneItem();				
			}
		});

		registrazioneForm.setFields(
			// Numerazione atto
			iconaTipoRegistrazioneItem, 
			siglaRegistrazioneItem,
			numeroRegistrazioneItem,
			dataRegistrazioneItem,
			desUserRegistrazioneItem,
			desUORegistrazioneItem,
			// Numerazione provvisoria di proposta atto
			iconaTipoRegProvvisoriaItem, 
			siglaRegProvvisoriaItem,
			numeroRegProvvisoriaItem,
			dataRegProvvisoriaItem,
			desUserRegProvvisoriaItem,
			desUORegProvvisoriaItem
		);
	}
	
	public boolean showDetailSectionPubblicazione() {
		return false;
	}
		
	public Date getDataInizioPubblicazioneValue() {
		return null;
	}
	
	public String getGiorniPubblicazioneValue() {
		return null;
	}
	
	protected void createDetailSectionPubblicazione() {
		
		createPubblicazioneForm();
		
		detailSectionPubblicazione = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionPubblicazione_title(), true, true, true, pubblicazioneForm);
	}
	
	protected void createPubblicazioneForm() {
		
		pubblicazioneForm = new DynamicForm();
		pubblicazioneForm.setValuesManager(vm);
		pubblicazioneForm.setWidth100();
		pubblicazioneForm.setPadding(5);
		pubblicazioneForm.setWrapItemTitles(false);
		pubblicazioneForm.setNumCols(20);
		pubblicazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		pubblicazioneForm.setTabSet(tabSet);
		pubblicazioneForm.setTabID(_TAB_DATI_SCHEDA_ID);
		
		dataInizioPubblicazioneItem = new DateItem("dataInizioPubblicazione", I18NUtil.getMessages().nuovaPropostaAtto2_detail_dataInizioPubblicazione_title());
		dataInizioPubblicazioneItem.setColSpan(1);
		dataInizioPubblicazioneItem.setAttribute("obbligatorio", true);
		dataInizioPubblicazioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return showDetailSectionPubblicazione();
			}
		}));
		
		giorniPubblicazioneItem = new NumericItem("giorniPubblicazione", I18NUtil.getMessages().nuovaPropostaAtto2_detail_giorniPubblicazione_title(), false);
		giorniPubblicazioneItem.setColSpan(1);
		giorniPubblicazioneItem.setAttribute("obbligatorio", true);
		giorniPubblicazioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return showDetailSectionPubblicazione();
			}
		}));
				
		pubblicazioneForm.setFields(dataInizioPubblicazioneItem, giorniPubblicazioneItem);			
	}
	
	
	protected void createDetailSectionRuoli() {
		
		createRuoliForm();
		
		detailSectionRuoli = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionRuoli_title(), true, true, true, ruoliForm);
	}
	
	public boolean isAbilToSelUffPropEsteso() {
		return Layout.isPrivilegioAttivo("APE");
	}
	
	public boolean showUfficioProponenteItem() {
		return isAvvioPropostaAtto();
	}
	
	public LinkedHashMap<String, String> getUfficioProponenteValueMap() {
		LinkedHashMap<String, String> uoProponenteAttiValueMap = AurigaLayout.getUoProponenteAttiValueMap();
		LinkedHashMap<String, String> ufficioProponenteValueMap = new LinkedHashMap<String, String>();
		for (String key : uoProponenteAttiValueMap.keySet()) {	
			String idUoProponente = (key != null && key.startsWith("UO")) ? key.substring(2) : key;
			ufficioProponenteValueMap.put(idUoProponente, uoProponenteAttiValueMap.get(key));			
		}
		return ufficioProponenteValueMap;
	}
	
	protected void createRuoliForm() {
		
		ruoliForm = new DynamicForm();
		ruoliForm.setValuesManager(vm);
		ruoliForm.setWidth100();
		ruoliForm.setPadding(5);
		ruoliForm.setWrapItemTitles(false);
		ruoliForm.setNumCols(20);
		ruoliForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ruoliForm.setTabSet(tabSet);
		ruoliForm.setTabID(_TAB_DATI_SCHEDA_ID);
		
		codUfficioProponenteItem = new HiddenItem("codUfficioProponente");
		desUfficioProponenteItem = new HiddenItem("desUfficioProponente");
		
		if (isAbilToSelUffPropEsteso()) {	
			
			listaUfficioProponenteItem = new SelezionaUOItem() {
				
				@Override
				public int getSelectItemOrganigrammaWidth() {
					return 650;
				}
				
				@Override
				public boolean skipValidation() {
					if(showUfficioProponenteItem()) {
						return super.skipValidation();
					}
					return true;
				}
				
				@Override
				protected VLayout creaVLayout() {
					VLayout lVLayout = super.creaVLayout();
					lVLayout.setWidth100();
					lVLayout.setPadding(11);
					lVLayout.setMargin(4);
					lVLayout.setIsGroup(true);
					lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_ufficioProponente_title()) + "</span>");
					return lVLayout;
				}
				
				@Override
				public Boolean getShowRemoveButton() {
					return false;
				};
				
				@Override
				public void manageChangedUoSelezionata() {
					if(listaAdottanteItem != null) {
						listaAdottanteItem.resetAfterChangedParams();
					}
					if(listaEstensoriItem != null) {
						listaEstensoriItem.resetAfterChangedParams();
					}
					setUfficioDefinizioneSpesaFromUoProponente();						
				}
			};
			listaUfficioProponenteItem.setName("listaUfficioProponente");
			listaUfficioProponenteItem.setShowTitle(false);
			listaUfficioProponenteItem.setColSpan(20);
			listaUfficioProponenteItem.setNotReplicable(true);
			listaUfficioProponenteItem.setAttribute("obbligatorio", true);
			listaUfficioProponenteItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return showUfficioProponenteItem();
				}
			});
			
		} else {
			
			ufficioProponenteItem = new SelectItem("ufficioProponente", I18NUtil.getMessages().nuovaPropostaAtto2_detail_ufficioProponente_title()) {
				
				@Override
				public void onOptionClick(Record record) {
					super.onOptionClick(record);
					String descrizione = record.getAttribute("descrizione");
					if(descrizione != null && !"".equals(descrizione)) {
						ruoliForm.setValue("codUfficioProponente", descrizione.substring(0, descrizione.indexOf(" - ")));
						ruoliForm.setValue("desUfficioProponente", descrizione.substring(descrizione.indexOf(" - ") + 3));
					} else {
						ruoliForm.setValue("codUfficioProponente", "");
						ruoliForm.setValue("desUfficioProponente", "");			
					}
				}
				
				@Override
				protected void clearSelect() {
					super.clearSelect();
					ruoliForm.setValue("codUfficioProponente", "");
					ruoliForm.setValue("desUfficioProponente", "");				
				};
				
				@Override
				public void setValue(String value) {
					super.setValue(value);
					if (value == null || "".equals(value)) {
						ruoliForm.setValue("codUfficioProponente", "");
						ruoliForm.setValue("desUfficioProponente", "");	
					}
	            }
			};
			ufficioProponenteItem.setWidth(500);
			ufficioProponenteItem.setDisplayField("descrizione");
			ufficioProponenteItem.setValueField("idUo");
			ufficioProponenteItem.setValueMap(getUfficioProponenteValueMap());
			if (AurigaLayout.getSelezioneUoProponenteAttiValueMap().size() == 1) {
				String key = AurigaLayout.getSelezioneUoProponenteAttiValueMap().keySet().toArray(new String[1])[0];
				String value = AurigaLayout.getSelezioneUoProponenteAttiValueMap().get(key);
				ufficioProponenteItem.setValue((key != null && key.startsWith("UO")) ? key.substring(2) : key);
				if(value != null && !"".equals(value)) {
					codUfficioProponenteItem.setValue(value.substring(0, value.indexOf(" - ")));
					desUfficioProponenteItem.setValue(value.substring(value.indexOf(" - ") + 3));
				}
			}
			ufficioProponenteItem.setAllowEmptyValue(true);
			ufficioProponenteItem.setAttribute("obbligatorio", true);
			ufficioProponenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
				
				@Override
				public boolean execute(FormItem formItem, Object value) {
					return showUfficioProponenteItem();
				}
			}));
			ufficioProponenteItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return showUfficioProponenteItem();
				}
			});
			ufficioProponenteItem.addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					if(listaAdottanteItem != null) {
						listaAdottanteItem.resetAfterChangedParams();
					}
					if(listaEstensoriItem != null) {
						listaEstensoriItem.resetAfterChangedParams();
					}
					setUfficioDefinizioneSpesaFromUoProponente();	
				}
			});
			
		} 
		
		listaAdottanteItem = new DirigenteAdottanteItem() {
			
			@Override
			public String getIdUdAttoDaAnn() {
				return getIdUdAttoDaAnnullare();
			}
			
			@Override
			public String getUoProponenteCorrente() {
				return getIdUoProponente();
			}
		
			@Override
			public void manageOnChangedFlgAdottanteAncheRdP(boolean value) {
				ruoliForm.markForRedraw();
			}
		
			@Override
			public void manageOnChangedFlgAdottanteAncheRUP(boolean value) {
				if(value) {
					listaRdPItem.clearFlgRdPAncheRUP();
				}
				ruoliForm.markForRedraw();
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);		
				lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_dirigenteAdottante_title()) + "</span>");
				return lVLayout;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return false;
			};
		};
		listaAdottanteItem.setName("listaAdottante");
		listaAdottanteItem.setShowTitle(false);
		listaAdottanteItem.setColSpan(20);
		listaAdottanteItem.setNotReplicable(true);
		listaAdottanteItem.setAttribute("obbligatorio", true);
		
		listaRdPItem = new ResponsabileDiProcedimentoItem() {
			
			@Override
			public void manageOnChangedFlgRdPAncheRUP(boolean value) {
				if(value) {
					listaAdottanteItem.clearFlgAdottanteAncheRUP();
				}
				ruoliForm.markForRedraw();
			}
			
			@Override
			public boolean skipValidation() {
				boolean flgAdottanteAncheRdP = false;
				RecordList listaAdottante = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaAdottante") : null;
				if(listaAdottante != null && listaAdottante.getLength() > 0) {
					flgAdottanteAncheRdP = listaAdottante.get(0).getAttributeAsBoolean("flgAdottanteAncheRdP") != null && listaAdottante.get(0).getAttributeAsBoolean("flgAdottanteAncheRdP");
				}
				if(!flgAdottanteAncheRdP) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);	
				lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_responsabileProcedimento_title()) + "</span>");
				return lVLayout;
			}
		};
		listaRdPItem.setName("listaRdP");
		listaRdPItem.setShowTitle(false);
		listaRdPItem.setColSpan(20);
		listaRdPItem.setNotReplicable(true);
		listaRdPItem.setAttribute("obbligatorio", true);
		listaRdPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(AurigaLayout.getParametroDBAsBoolean("FORZA_RDP_UGUALE_ADOTTANTE")) {
					return false;
				}
				boolean flgAdottanteAncheRdP = false;
				RecordList listaAdottante = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaAdottante") : null;
				if(listaAdottante != null && listaAdottante.getLength() > 0) {
					flgAdottanteAncheRdP = listaAdottante.get(0).getAttributeAsBoolean("flgAdottanteAncheRdP") != null && listaAdottante.get(0).getAttributeAsBoolean("flgAdottanteAncheRdP");
				}
				return !flgAdottanteAncheRdP;	
			}
		});			
		
		listaRUPItem = new ResponsabileUnicoProvvedimentoItem() {
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + I18NUtil.getMessages().nuovaPropostaAtto2_detail_responsabileUnicoProvvedimento_title() + "</span>");
				return lVLayout;
			}
		};
		listaRUPItem.setName("listaRUP");
		listaRUPItem.setShowTitle(false);
		listaRUPItem.setColSpan(20);
		listaRUPItem.setNotReplicable(true);
		listaRUPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(AurigaLayout.getParametroDBAsBoolean("FORZA_RUP_UGUALE_ADOTTANTE")) {
					return false;
				}
				boolean flgAdottanteAncheRdP = false;
				boolean flgAdottanteAncheRUP = false;
				RecordList listaAdottante = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaAdottante") : null;
				if(listaAdottante != null && listaAdottante.getLength() > 0) {
					flgAdottanteAncheRdP = listaAdottante.get(0).getAttributeAsBoolean("flgAdottanteAncheRdP") != null && listaAdottante.get(0).getAttributeAsBoolean("flgAdottanteAncheRdP");
					flgAdottanteAncheRUP = listaAdottante.get(0).getAttributeAsBoolean("flgAdottanteAncheRUP") != null && listaAdottante.get(0).getAttributeAsBoolean("flgAdottanteAncheRUP");
				}
				boolean flgRdPAncheRUP = false;
				if(!flgAdottanteAncheRdP) {
					RecordList listaRdP = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaRdP") : null;
					if(listaRdP != null && listaRdP.getLength() > 0) {
						flgRdPAncheRUP = listaRdP.get(0).getAttributeAsBoolean("flgRdPAncheRUP") != null && listaRdP.get(0).getAttributeAsBoolean("flgRdPAncheRUP");
					}
				}
				return !flgAdottanteAncheRUP && !flgRdPAncheRUP;					
			}
		});
		
		flgRichiediVistoDirettoreItem = new CheckboxItem("flgRichiediVistoDirettore", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgRichiediVistoDirettore_title());
		flgRichiediVistoDirettoreItem.setDefaultValue(false);
		flgRichiediVistoDirettoreItem.setStartRow(true);
		flgRichiediVistoDirettoreItem.setColSpan(20);
		flgRichiediVistoDirettoreItem.setWidth("*");
		flgRichiediVistoDirettoreItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_VISTO_DIRETTORE_IN_ITER_ATTI");
			}
		});
		
		ruoliForm.setFields(
			codUfficioProponenteItem,
			desUfficioProponenteItem,				
			isAbilToSelUffPropEsteso() ? listaUfficioProponenteItem : ufficioProponenteItem,
			listaAdottanteItem,
			listaRdPItem, 
			listaRUPItem,
			flgRichiediVistoDirettoreItem
		);		
	}
	
	public boolean showDetailSectionPropostaConcertoCon() {
		if(AurigaLayout.isAttivoClienteCMMI()) {
			return true;			
		}
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_RESP_CONCERTO_IN_ITER_ATTI");
	}
	
	protected void createDetailSectionPropostaConcertoCon() {
		
		createPropostaConcertoConForm();
		
		detailSectionPropostaConcertoCon = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionPropostaConcertoCon_title(), true, false, false, propostaConcertoConForm);
	}
	
	protected void createPropostaConcertoConForm() {
		
		propostaConcertoConForm = new DynamicForm();
		propostaConcertoConForm.setValuesManager(vm);
		propostaConcertoConForm.setWidth100();
		propostaConcertoConForm.setPadding(5);
		propostaConcertoConForm.setWrapItemTitles(false);
		propostaConcertoConForm.setNumCols(20);
		propostaConcertoConForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		propostaConcertoConForm.setTabSet(tabSet);
		propostaConcertoConForm.setTabID(_TAB_DATI_SCHEDA_ID);
		
		listaDirigentiConcertoItem = new DirigentiConcertoItem();
		listaDirigentiConcertoItem.setName("listaDirigentiConcerto");
		listaDirigentiConcertoItem.setStartRow(true);
		listaDirigentiConcertoItem.setShowTitle(false);
		listaDirigentiConcertoItem.setColSpan(20);
		
		propostaConcertoConForm.setFields(
			listaDirigentiConcertoItem			
		);		
	}	
	
	public boolean showDetailSectionEstensori() {	
		if(AurigaLayout.isAttivoClienteCMMI()) {
			return false;
		}		
		String estensoriIterAtti = AurigaLayout.getParametroDB("ESTENSORI_ITER_ATTI");
		return estensoriIterAtti != null && !"".equals(estensoriIterAtti);		
	}
	
	public boolean isRequiredDetailSectionEstensori() {
		if(AurigaLayout.isAttivoClienteCMMI()) {
			return false;
		}		
		String estensoriIterAtti = AurigaLayout.getParametroDB("ESTENSORI_ITER_ATTI");
		return estensoriIterAtti != null && _MANDATORY.equalsIgnoreCase(estensoriIterAtti);		
	}
		
	protected void createDetailSectionEstensori() {
		
		createEstensoriForm();
		
		detailSectionEstensori = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionEstensori_title(), true, true, isRequiredDetailSectionEstensori(), estensoriForm);
	}
	
	protected void createEstensoriForm() {
		
		estensoriForm = new DynamicForm();
		estensoriForm.setValuesManager(vm);
		estensoriForm.setWidth100();
		estensoriForm.setPadding(5);
		estensoriForm.setWrapItemTitles(false);
		estensoriForm.setNumCols(20);
		estensoriForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		estensoriForm.setTabSet(tabSet);
		estensoriForm.setTabID(_TAB_DATI_SCHEDA_ID);
		
		listaEstensoriItem = new EstensoreItem() {
			
			@Override
			public String getUoProponenteCorrente() {
				return getIdUoProponente();
			}			
		};
		listaEstensoriItem.setName("listaEstensori");
		listaEstensoriItem.setStartRow(true);
		listaEstensoriItem.setShowTitle(false);
		listaEstensoriItem.setColSpan(20);
		if(isRequiredDetailSectionEstensori()) {
			listaEstensoriItem.setAttribute("obbligatorio", true);			
		}	
		
		estensoriForm.setFields(
			listaEstensoriItem			
		);		
	}	
	
	public boolean showDetailSectionCIG() {
		if(AurigaLayout.isAttivoClienteCMMI()) {
			return true;
		}		
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_CIG_IN_ITER_ATTI");		
	}
		
	protected void createDetailSectionCIG() {
		
		createCIGForm();
		
		detailSectionCIG = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionCIG_title(), true, true, false, CIGForm);
	}
	
	protected void createCIGForm() {
		
		CIGForm = new DynamicForm();
		CIGForm.setValuesManager(vm);
		CIGForm.setWidth100();
		CIGForm.setPadding(5);
		CIGForm.setWrapItemTitles(false);
		CIGForm.setNumCols(20);
		CIGForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		CIGForm.setTabSet(tabSet);
		CIGForm.setTabID(_TAB_DATI_SCHEDA_ID);
		
		listaCIGItem = new CIGItem();
		listaCIGItem.setName("listaCIG");
		listaCIGItem.setShowTitle(false);
		listaCIGItem.setColSpan(20);
				
		CIGForm.setFields(listaCIGItem);			
	}
	
	protected void createDetailSectionOggetto() {
		
		createOggettoForm();
		
		detailSectionOggetto = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionOggetto_title(), true, true, true, oggettoForm);
	}
	
	protected void createOggettoForm() {
		
		oggettoForm = new DynamicForm();
		oggettoForm.setValuesManager(vm);
		oggettoForm.setWidth100();
		oggettoForm.setPadding(5);
		oggettoForm.setWrapItemTitles(false);
		oggettoForm.setNumCols(20);
		oggettoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		oggettoForm.setTabSet(tabSet);
		oggettoForm.setTabID(_TAB_DATI_SCHEDA_ID);
		
		oggettoItem = new HiddenItem("oggetto");
//		oggettoItem.setRequired(true);
//		oggettoItem.setShowTitle(false);
//		oggettoItem.setColSpan(20);
//		oggettoItem.setLength(4000);
//		oggettoItem.setHeight(60);
//		oggettoItem.setWidth("100%");
		
		oggettoHtmlItem = new CKEditorItem("oggettoHtml", 4000, "restricted");
		oggettoHtmlItem.setRequired(true);
		oggettoHtmlItem.setShowTitle(false);
		oggettoHtmlItem.setColSpan(20);
		oggettoHtmlItem.setWidth("100%");
				
		oggettoForm.setFields(oggettoItem, oggettoHtmlItem);			
	}
		
	protected void createDetailSectionCaratteristicheProvvedimento() {
		
		createCaratteristicheProvvedimentoForm();
		
		detailSectionCaratteristicheProvvedimento = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionCaratteristicheProvvedimento_title(), true, true, true, caratteristicheProvvedimentoForm1, caratteristicheProvvedimentoForm2, caratteristicheProvvedimentoForm3);
	}
	
	protected String getTitleFlgSpesa() {			
		return I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgSpesa_title();
	}
	
	protected String getTitleAttoDeterminaAContrarre() {
		return I18NUtil.getMessages().nuovaPropostaAtto2_detail_attoDeterminaAContrarre_title();
	}
	
	protected void createCaratteristicheProvvedimentoForm() {
		
		caratteristicheProvvedimentoForm1 = new DynamicForm();
		caratteristicheProvvedimentoForm1.setValuesManager(vm);
		caratteristicheProvvedimentoForm1.setWidth100();
		caratteristicheProvvedimentoForm1.setPadding(5);
		caratteristicheProvvedimentoForm1.setWrapItemTitles(false);
		caratteristicheProvvedimentoForm1.setNumCols(20);
		caratteristicheProvvedimentoForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		caratteristicheProvvedimentoForm1.setTabSet(tabSet);
		caratteristicheProvvedimentoForm1.setTabID(_TAB_DATI_SCHEDA_ID);
		
		flgDeterminaAContrarreTramiteProceduraGaraItem = new CheckboxItem("flgDeterminaAContrarreTramiteProceduraGara", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgDeterminaAContrarreTramiteProceduraGara_title());
		flgDeterminaAContrarreTramiteProceduraGaraItem.setDefaultValue(false);
		flgDeterminaAContrarreTramiteProceduraGaraItem.setStartRow(true);
		flgDeterminaAContrarreTramiteProceduraGaraItem.setColSpan(10);
		flgDeterminaAContrarreTramiteProceduraGaraItem.setWidth("*");
		flgDeterminaAContrarreTramiteProceduraGaraItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(final ChangeEvent event) {
				if(!isAvvioPropostaAtto()) {
					if (event.getValue() != null && (Boolean) event.getValue()) {					
						if(isDeterminaPersonale()) {
							toSaveAndReloadTask = true;							
						}
					}
				}
			}
			
		});
		flgDeterminaAContrarreTramiteProceduraGaraItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgDeterminaAggiudicaProceduraGaraItem.setValue(false);
					flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setValue(false);	
					flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setValue(false);								
				}
				if(!_FLG_SPESA_SI.equalsIgnoreCase(getValueAsString("flgSpesa"))) {
					flgSpesaItem.setValue(_FLG_SPESA_SI);
					flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
				}
				caratteristicheProvvedimentoForm1.markForRedraw();
				caratteristicheProvvedimentoForm2.markForRedraw();
				caratteristicheProvvedimentoForm3.markForRedraw();
				enableDisableTabs();
				showHideSections();
				if(toSaveAndReloadTask) {
					toSaveAndReloadTask = false;
					saveAndReloadTask();
				}
			}
		});
		
		flgDeterminaAggiudicaProceduraGaraItem = new CheckboxItem("flgDeterminaAggiudicaProceduraGara", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgDeterminaAggiudicaProceduraGara_title());
		flgDeterminaAggiudicaProceduraGaraItem.setDefaultValue(false);
		flgDeterminaAggiudicaProceduraGaraItem.setStartRow(true);
		flgDeterminaAggiudicaProceduraGaraItem.setColSpan(10);
		flgDeterminaAggiudicaProceduraGaraItem.setWidth("*");
		flgDeterminaAggiudicaProceduraGaraItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(final ChangeEvent event) {
				if(!isAvvioPropostaAtto()) {
					if (event.getValue() != null && (Boolean) event.getValue()) {					
						if(isDeterminaPersonale()) {
							toSaveAndReloadTask = true;							
						}
					}
				}
			}
			
		});
		flgDeterminaAggiudicaProceduraGaraItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgDeterminaAContrarreTramiteProceduraGaraItem.setValue(false);
					flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setValue(false);			
					flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setValue(false);				
				} 
				flgSpesaItem.clearValue();
				flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
				caratteristicheProvvedimentoForm1.markForRedraw();
				caratteristicheProvvedimentoForm2.markForRedraw();
				caratteristicheProvvedimentoForm3.markForRedraw();
				enableDisableTabs();	
				showHideSections();
				if(toSaveAndReloadTask) {
					toSaveAndReloadTask = false;
					saveAndReloadTask();
				}
			}
		});

		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem = new CheckboxItem("flgDeterminaRimodulazioneSpesaGaraAggiudicata", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgDeterminaRimodulazioneSpesaGaraAggiudicata_title());
		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setDefaultValue(false);
		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setStartRow(true);
		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setColSpan(10);
		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setWidth("*");
		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(final ChangeEvent event) {
				if(!isAvvioPropostaAtto()) {
					if (event.getValue() != null && (Boolean) event.getValue()) {					
						if(isDeterminaPersonale()) {
							toSaveAndReloadTask = true;							
						}
					}
				}
			}
			
		});
		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgDeterminaAContrarreTramiteProceduraGaraItem.setValue(false);
					flgDeterminaAggiudicaProceduraGaraItem.setValue(false);			
					flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setValue(false);					
				}
				if(!_FLG_SPESA_SI.equalsIgnoreCase(getValueAsString("flgSpesa"))) {
					flgSpesaItem.setValue(_FLG_SPESA_SI);
					flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
				}
				caratteristicheProvvedimentoForm1.markForRedraw();
				caratteristicheProvvedimentoForm2.markForRedraw();
				caratteristicheProvvedimentoForm3.markForRedraw();
				enableDisableTabs();			
				showHideSections();
				if(toSaveAndReloadTask) {
					toSaveAndReloadTask = false;
					saveAndReloadTask();
				}
			}
		});
		
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem = new CheckboxItem("flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro_title());
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setDefaultValue(false);
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setStartRow(true);
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setColSpan(10);
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setWidth("*");
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(final ChangeEvent event) {
				if(!isAvvioPropostaAtto()) {
					toSaveAndReloadTask = true;					
				}
			}
			
		});
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgDeterminaAContrarreTramiteProceduraGaraItem.setValue(false);
					flgDeterminaAggiudicaProceduraGaraItem.setValue(false);		
					flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setValue(false);					
				}
				if(!_FLG_SPESA_SI.equalsIgnoreCase(getValueAsString("flgSpesa"))) {
					flgSpesaItem.setValue(_FLG_SPESA_SI);
					flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
				}
				caratteristicheProvvedimentoForm1.markForRedraw();
				caratteristicheProvvedimentoForm2.markForRedraw();
				caratteristicheProvvedimentoForm3.markForRedraw();
				enableDisableTabs();
				showHideSections();
				if(toSaveAndReloadTask) {
					toSaveAndReloadTask = false;
					saveAndReloadTask();
				}
			}
		});
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_DET_PERS_EXTRA_AMC");
			}
		});
		
		CustomValidator attoDeterminaAContrarreRequiredValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				
				if(getValueAsBoolean("flgDeterminaAggiudicaProceduraGara") || getValueAsBoolean("flgDeterminaRimodulazioneSpesaGaraAggiudicata")) {					
					String categoriaReg = categoriaRegAttoDeterminaAContrarreItem.getValueAsString() != null ? categoriaRegAttoDeterminaAContrarreItem.getValueAsString() : "";
					String sigla = siglaAttoDeterminaAContrarreItem.getValueAsString() != null ? siglaAttoDeterminaAContrarreItem.getValueAsString() : "";
					String numero = numeroAttoDeterminaAContrarreItem.getValueAsString() != null ? numeroAttoDeterminaAContrarreItem.getValueAsString() : "";
					String anno = annoAttoDeterminaAContrarreItem.getValueAsString() != null ? annoAttoDeterminaAContrarreItem.getValueAsString() : "";														
					return ("PG".equals(categoriaReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno);
				}
				return true;
			}
		};
		attoDeterminaAContrarreRequiredValidator.setErrorMessage("Campo obbligatorio");
		
		CustomValidator attoDeterminaAContrarreEstremiProtCompletiValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {				
				String categoriaReg = categoriaRegAttoDeterminaAContrarreItem.getValueAsString() != null ? categoriaRegAttoDeterminaAContrarreItem.getValueAsString() : "";
				if("PG".equals(categoriaReg)) {
					String numero = numeroAttoDeterminaAContrarreItem.getValueAsString() != null ? numeroAttoDeterminaAContrarreItem.getValueAsString() : "";
					String anno = annoAttoDeterminaAContrarreItem.getValueAsString() != null ? annoAttoDeterminaAContrarreItem.getValueAsString() : "";									
					return (!"".equals(numero) && !"".equals(anno)) || ("".equals(numero) && "".equals(anno));
				}
				return true;
			}
		};
		attoDeterminaAContrarreEstremiProtCompletiValidator.setErrorMessage("Estremi di protocollo incompleti: inserire numero e anno");		
		
		CustomValidator attoDeterminaAContrarreEstremiRegRepertorioCompletiValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {				
				String categoriaReg = categoriaRegAttoDeterminaAContrarreItem.getValueAsString() != null ? categoriaRegAttoDeterminaAContrarreItem.getValueAsString() : "";
				if("R".equals(categoriaReg)) {
					String sigla = siglaAttoDeterminaAContrarreItem.getValueAsString() != null ? siglaAttoDeterminaAContrarreItem.getValueAsString() : "";
					String numero = numeroAttoDeterminaAContrarreItem.getValueAsString() != null ? numeroAttoDeterminaAContrarreItem.getValueAsString() : "";
					String anno = annoAttoDeterminaAContrarreItem.getValueAsString() != null ? annoAttoDeterminaAContrarreItem.getValueAsString() : "";									
					return (!"".equals(sigla) && !"".equals(numero) && !"".equals(anno)) || ("".equals(sigla) && "".equals(numero) && "".equals(anno));
				}
				return true;
			}
		};
		attoDeterminaAContrarreEstremiRegRepertorioCompletiValidator.setErrorMessage("Estremi di registrazione repertorio incompleti: inserire sigla, numero e anno");		
		
		CustomValidator attoDeterminaAContrarreEsistenteValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				String categoriaReg = categoriaRegAttoDeterminaAContrarreItem.getValueAsString() != null ? categoriaRegAttoDeterminaAContrarreItem.getValueAsString() : "";
				String sigla = siglaAttoDeterminaAContrarreItem.getValueAsString() != null ? siglaAttoDeterminaAContrarreItem.getValueAsString() : "";
				String numero = numeroAttoDeterminaAContrarreItem.getValueAsString() != null ? numeroAttoDeterminaAContrarreItem.getValueAsString() : "";
				String anno = annoAttoDeterminaAContrarreItem.getValueAsString() != null ? annoAttoDeterminaAContrarreItem.getValueAsString() : "";
				if(("PG".equals(categoriaReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno)) {				
					String idUd = idUdAttoDeterminaAContrarreItem.getValue() != null ? String.valueOf(idUdAttoDeterminaAContrarreItem.getValue()) : null;
					return idUd != null && !"".equals(idUd);
				}
				return true;
			}
		};
		attoDeterminaAContrarreEsistenteValidator.setErrorMessage("Atto non presente in Auriga");		
		
		final TitleItem titleAttoDeterminaAContrarre = new TitleItem(getTitleAttoDeterminaAContrarre(), true);
		titleAttoDeterminaAContrarre.setValidators(attoDeterminaAContrarreRequiredValidator, attoDeterminaAContrarreEstremiProtCompletiValidator, attoDeterminaAContrarreEstremiRegRepertorioCompletiValidator, attoDeterminaAContrarreEsistenteValidator);
		titleAttoDeterminaAContrarre.setRowSpan(3);
		titleAttoDeterminaAContrarre.setColSpan(1);
		titleAttoDeterminaAContrarre.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(getValueAsBoolean("flgDeterminaAggiudicaProceduraGara") || getValueAsBoolean("flgDeterminaRimodulazioneSpesaGaraAggiudicata")) {
					titleAttoDeterminaAContrarre.setAttribute("obbligatorio", true);
					titleAttoDeterminaAContrarre.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleAttoDeterminaAContrarre()));
				} else {
					titleAttoDeterminaAContrarre.setAttribute("obbligatorio", false);
					titleAttoDeterminaAContrarre.setTitle(getTitleAttoDeterminaAContrarre());
				}
				return true;
			}
		});
		
		idUdAttoDeterminaAContrarreItem = new HiddenItem("idUdAttoDeterminaAContrarre");
		
		categoriaRegAttoDeterminaAContrarreItem = new SelectItem("categoriaRegAttoDeterminaAContrarre");
		categoriaRegAttoDeterminaAContrarreItem.setShowTitle(false);
		LinkedHashMap<String, String> categoriaRegAttoDeterminaAContrarreValueMap = new LinkedHashMap<String, String>();
		categoriaRegAttoDeterminaAContrarreValueMap.put("PG", "Prot. Generale");
		categoriaRegAttoDeterminaAContrarreValueMap.put("R", "Repertorio");		
		categoriaRegAttoDeterminaAContrarreItem.setValueMap(categoriaRegAttoDeterminaAContrarreValueMap);
		categoriaRegAttoDeterminaAContrarreItem.setDefaultValue("R");
		categoriaRegAttoDeterminaAContrarreItem.setWidth(150);
		categoriaRegAttoDeterminaAContrarreItem.setWrapTitle(false);
		categoriaRegAttoDeterminaAContrarreItem.setRowSpan(3);
		categoriaRegAttoDeterminaAContrarreItem.setColSpan(1);
		categoriaRegAttoDeterminaAContrarreItem.setAllowEmptyValue(false);
		categoriaRegAttoDeterminaAContrarreItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				caratteristicheProvvedimentoForm1.markForRedraw();
			}
		});
		
		siglaAttoDeterminaAContrarreItem = new ExtendedTextItem("siglaAttoDeterminaAContrarre", I18NUtil.getMessages().nuovaPropostaAtto2_detail_siglaAttoDeterminaAContrarre_title());
		siglaAttoDeterminaAContrarreItem.setWidth(100);
		siglaAttoDeterminaAContrarreItem.setRowSpan(3);
		siglaAttoDeterminaAContrarreItem.setColSpan(1);
		siglaAttoDeterminaAContrarreItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				caratteristicheProvvedimentoForm1.clearErrors(true);
				caratteristicheProvvedimentoForm1.setValue("idUdAttoDeterminaAContrarre", "");
				recuperaIdUdAttoDeterminaAContrarre(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idUdAttoDeterminaAContrarre) {
						if(idUdAttoDeterminaAContrarre != null && !"".equals(idUdAttoDeterminaAContrarre)) {
							caratteristicheProvvedimentoForm1.setValue("idUdAttoDeterminaAContrarre", idUdAttoDeterminaAContrarre);							
						}
						caratteristicheProvvedimentoForm1.markForRedraw();
					}
					
					@Override
					public void manageError() {
						caratteristicheProvvedimentoForm1.markForRedraw();
					}
				});
			}
		});
		siglaAttoDeterminaAContrarreItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "R".equals(getValueAsString("categoriaRegAttoDeterminaAContrarre"));
			}
		});
		
		numeroAttoDeterminaAContrarreItem = new ExtendedNumericItem("numeroAttoDeterminaAContrarre",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_numeroAttoDeterminaAContrarre_title());
		numeroAttoDeterminaAContrarreItem.setRowSpan(3);
		numeroAttoDeterminaAContrarreItem.setColSpan(1);
		numeroAttoDeterminaAContrarreItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				caratteristicheProvvedimentoForm1.clearErrors(true);
				caratteristicheProvvedimentoForm1.setValue("idUdAttoDeterminaAContrarre", "");
				recuperaIdUdAttoDeterminaAContrarre(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idUdAttoDeterminaAContrarre) {
						if(idUdAttoDeterminaAContrarre != null && !"".equals(idUdAttoDeterminaAContrarre)) {
							caratteristicheProvvedimentoForm1.setValue("idUdAttoDeterminaAContrarre", idUdAttoDeterminaAContrarre);							
						}
						caratteristicheProvvedimentoForm1.markForRedraw();
					}
					
					@Override
					public void manageError() {
						caratteristicheProvvedimentoForm1.markForRedraw();
					}
				});
			}
		});		
		
		annoAttoDeterminaAContrarreItem = new AnnoItem("annoAttoDeterminaAContrarre",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_annoAttoDeterminaAContrarre_title());
		annoAttoDeterminaAContrarreItem.setRowSpan(3);
		annoAttoDeterminaAContrarreItem.setColSpan(1);
		annoAttoDeterminaAContrarreItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				caratteristicheProvvedimentoForm1.clearErrors(true);
				caratteristicheProvvedimentoForm1.setValue("idUdAttoDeterminaAContrarre", "");
				recuperaIdUdAttoDeterminaAContrarre(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idUdAttoDeterminaAContrarre) {
						if(idUdAttoDeterminaAContrarre != null && !"".equals(idUdAttoDeterminaAContrarre)) {
							caratteristicheProvvedimentoForm1.setValue("idUdAttoDeterminaAContrarre", idUdAttoDeterminaAContrarre);							
						}
						caratteristicheProvvedimentoForm1.markForRedraw();
					}
					
					@Override
					public void manageError() {
						caratteristicheProvvedimentoForm1.markForRedraw();
					}
				});
			}
		});
		
		ImgButtonItem visualizzaDettAttoDeterminaAContrarreButton = new ImgButtonItem("visualizzaDettAttoDeterminaAContrarreButton", "buttons/detail.png", "Visualizza dettaglio atto");
		visualizzaDettAttoDeterminaAContrarreButton.setAlwaysEnabled(true);
		visualizzaDettAttoDeterminaAContrarreButton.setEndRow(false);
		visualizzaDettAttoDeterminaAContrarreButton.setColSpan(1);
		visualizzaDettAttoDeterminaAContrarreButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {	
				String idUd = idUdAttoDeterminaAContrarreItem.getValue() != null ? String.valueOf(idUdAttoDeterminaAContrarreItem.getValue()) : null;				
				String categoriaReg = categoriaRegAttoDeterminaAContrarreItem.getValueAsString() != null ? categoriaRegAttoDeterminaAContrarreItem.getValueAsString() : "";
				String sigla = siglaAttoDeterminaAContrarreItem.getValueAsString() != null ? siglaAttoDeterminaAContrarreItem.getValueAsString() : "";
				String numero = numeroAttoDeterminaAContrarreItem.getValueAsString() != null ? numeroAttoDeterminaAContrarreItem.getValueAsString() : "";
				String anno = annoAttoDeterminaAContrarreItem.getValueAsString() != null ? annoAttoDeterminaAContrarreItem.getValueAsString() : "";									
				Record lRecord = new Record();
				lRecord.setAttribute("idUd", idUd);
				String title = "";
				if("PG".equals(categoriaReg)) {
					title = "Dettaglio atto Prot. " + numero + "/" + anno;
				} else {
					title = "Dettaglio atto " + sigla + " " + numero + "/" + anno;					
				}
				new DettaglioRegProtAssociatoWindow(lRecord, title);
			}
		});		
		visualizzaDettAttoDeterminaAContrarreButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String idUd = idUdAttoDeterminaAContrarreItem.getValue() != null ? String.valueOf(idUdAttoDeterminaAContrarreItem.getValue()) : null;
				String categoriaReg = categoriaRegAttoDeterminaAContrarreItem.getValueAsString() != null ? categoriaRegAttoDeterminaAContrarreItem.getValueAsString() : "";
				String sigla = siglaAttoDeterminaAContrarreItem.getValueAsString() != null ? siglaAttoDeterminaAContrarreItem.getValueAsString() : "";
				String numero = numeroAttoDeterminaAContrarreItem.getValueAsString() != null ? numeroAttoDeterminaAContrarreItem.getValueAsString() : "";
				String anno = annoAttoDeterminaAContrarreItem.getValueAsString() != null ? annoAttoDeterminaAContrarreItem.getValueAsString() : "";									
				if (("PG".equals(categoriaReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno)) {
					return idUd != null && !"".equals(idUd);	
				}
				return false;
			}
		});
		
		ImgButtonItem lookupArchivioAttoDeterminaAContrarreButton = new ImgButtonItem("lookupArchivioAttoDeterminaAContrarreButton", "lookup/archivio.png", "Seleziona da archivio");
		lookupArchivioAttoDeterminaAContrarreButton.setEndRow(false);
		lookupArchivioAttoDeterminaAContrarreButton.setColSpan(1);
		lookupArchivioAttoDeterminaAContrarreButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {						
				AttoDeterminaAContrarreLookupArchivio lookupArchivioPopup = new AttoDeterminaAContrarreLookupArchivio(caratteristicheProvvedimentoForm1.getValuesAsRecord(), "/");
				lookupArchivioPopup.show();
			}
		});				
		
		caratteristicheProvvedimentoForm1.setFields(
			flgDeterminaAContrarreTramiteProceduraGaraItem, 
			flgDeterminaAggiudicaProceduraGaraItem, 
			flgDeterminaRimodulazioneSpesaGaraAggiudicataItem,
			flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem,
			titleAttoDeterminaAContrarre, idUdAttoDeterminaAContrarreItem, categoriaRegAttoDeterminaAContrarreItem, siglaAttoDeterminaAContrarreItem, numeroAttoDeterminaAContrarreItem, annoAttoDeterminaAContrarreItem, visualizzaDettAttoDeterminaAContrarreButton, lookupArchivioAttoDeterminaAContrarreButton
		);		

		caratteristicheProvvedimentoForm2 = new DynamicForm();
		caratteristicheProvvedimentoForm2.setValuesManager(vm);
		caratteristicheProvvedimentoForm2.setWidth100();
		caratteristicheProvvedimentoForm2.setPadding(5);
		caratteristicheProvvedimentoForm2.setWrapItemTitles(false);
		caratteristicheProvvedimentoForm2.setNumCols(20);
		caratteristicheProvvedimentoForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		caratteristicheProvvedimentoForm2.setTabSet(tabSet);
		caratteristicheProvvedimentoForm2.setTabID(_TAB_DATI_SCHEDA_ID);
		
		flgSpesaItem = new RadioGroupItem("flgSpesa", getTitleFlgSpesa());
		flgSpesaItem.setStartRow(true);
		flgSpesaItem.setValueMap(_FLG_SPESA_SI, _FLG_SPESA_SI_SENZA_VLD_RIL_IMP, _FLG_SPESA_NO);
		flgSpesaItem.setVertical(false);
		flgSpesaItem.setWrap(false);
		flgSpesaItem.setRequired(true);
		flgSpesaItem.setShowDisabled(false);
		flgSpesaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(getValueAsBoolean("flgDeterminaAContrarreTramiteProceduraGara") || getValueAsBoolean("flgDeterminaRimodulazioneSpesaGaraAggiudicata") || isDeterminaPersonale()) {
					item.setDisabled(true);
					item.setValue(_FLG_SPESA_SI);					
				} else {
					item.setDisabled(false);					
				}
				return true;
			}
		});
		flgSpesaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				caratteristicheProvvedimentoForm3.markForRedraw();
				enableDisableTabs();
			}
		});
		
		caratteristicheProvvedimentoForm2.setFields(flgSpesaItem);	
		
		caratteristicheProvvedimentoForm3 = new DynamicForm();
		caratteristicheProvvedimentoForm3.setValuesManager(vm);
		caratteristicheProvvedimentoForm3.setWidth100();
		caratteristicheProvvedimentoForm3.setPadding(5);
		caratteristicheProvvedimentoForm3.setWrapItemTitles(false);
		caratteristicheProvvedimentoForm3.setNumCols(20);
		caratteristicheProvvedimentoForm3.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		caratteristicheProvvedimentoForm3.setTabSet(tabSet);
		caratteristicheProvvedimentoForm3.setTabID(_TAB_DATI_SCHEDA_ID);
		caratteristicheProvvedimentoForm3.setHeight(1);
		
		CustomValidator richVerificaDiValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(_FLG_SPESA_SI_SENZA_VLD_RIL_IMP.equalsIgnoreCase(getValueAsString("flgSpesa"))) {
					return getValueAsBoolean("flgRichVerificaDiBilancioCorrente") || getValueAsBoolean("flgRichVerificaDiBilancioContoCapitale") || getValueAsBoolean("flgRichVerificaDiContabilita");
				}
				return true;
			}
		};
		richVerificaDiValidator.setErrorMessage(I18NUtil.getMessages().nuovaPropostaAtto2_detail_richVerificaDiValidator_errorMessage());
		
		TitleItem richVerificaDiTitleItem = new TitleItem(FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_richVerificaDi_title(), true));
		richVerificaDiTitleItem.setAttribute("obbligatorio", true);
		richVerificaDiTitleItem.setValidators(richVerificaDiValidator);
		richVerificaDiTitleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return _FLG_SPESA_SI_SENZA_VLD_RIL_IMP.equalsIgnoreCase(getValueAsString("flgSpesa"));
			}
		});
		
		flgRichVerificaDiBilancioCorrenteItem = new CheckboxItem("flgRichVerificaDiBilancioCorrente", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgRichVerificaDiBilancioCorrente_title());
		flgRichVerificaDiBilancioCorrenteItem.setDefaultValue(false);
		flgRichVerificaDiBilancioCorrenteItem.setColSpan(1);
		flgRichVerificaDiBilancioCorrenteItem.setWidth("*");
		flgRichVerificaDiBilancioCorrenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return _FLG_SPESA_SI_SENZA_VLD_RIL_IMP.equalsIgnoreCase(getValueAsString("flgSpesa"));
			}
		});
		
		flgRichVerificaDiBilancioContoCapitaleItem = new CheckboxItem("flgRichVerificaDiBilancioContoCapitale", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgRichVerificaDiBilancioContoCapitale_title());
		flgRichVerificaDiBilancioContoCapitaleItem.setDefaultValue(false);
		flgRichVerificaDiBilancioContoCapitaleItem.setColSpan(1);
		flgRichVerificaDiBilancioContoCapitaleItem.setWidth("*");
		flgRichVerificaDiBilancioContoCapitaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return _FLG_SPESA_SI_SENZA_VLD_RIL_IMP.equalsIgnoreCase(getValueAsString("flgSpesa"));
			}
		});
		
		flgRichVerificaDiContabilitaItem = new CheckboxItem("flgRichVerificaDiContabilita", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgRichVerificaDiContabilita_title());
		flgRichVerificaDiContabilitaItem.setDefaultValue(true);
		flgRichVerificaDiContabilitaItem.setColSpan(1);
		flgRichVerificaDiContabilitaItem.setWidth("*");
		flgRichVerificaDiContabilitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return _FLG_SPESA_SI_SENZA_VLD_RIL_IMP.equalsIgnoreCase(getValueAsString("flgSpesa"));
			}
		});
		
		flgPresaVisioneContabilitaItem = new CheckboxItem("flgPresaVisioneContabilita", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgPresaVisioneContabilita_title());
		flgPresaVisioneContabilitaItem.setDefaultValue(false);
		flgPresaVisioneContabilitaItem.setStartRow(true);
		flgPresaVisioneContabilitaItem.setColSpan(1);
		flgPresaVisioneContabilitaItem.setWidth("*");
		flgPresaVisioneContabilitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return _FLG_SPESA_NO.equalsIgnoreCase(getValueAsString("flgSpesa"));
			}
		});

		caratteristicheProvvedimentoForm3.setFields(
			richVerificaDiTitleItem, flgRichVerificaDiBilancioCorrenteItem, flgRichVerificaDiBilancioContoCapitaleItem, flgRichVerificaDiContabilitaItem,
			flgPresaVisioneContabilitaItem
		);	
	}
	
	protected void createDetailSectionRiferimentiNormativi() {
		
		createRiferimentiNormativiForm();
		
		detailSectionRiferimentiNormativi = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionRiferimentiNormativi_title(), true, true, !isAvvioPropostaAtto(), riferimentiNormativiForm);
	}
	
	protected void createRiferimentiNormativiForm() {
		
		riferimentiNormativiForm = new DynamicForm();
		riferimentiNormativiForm.setValuesManager(vm);
		riferimentiNormativiForm.setWidth100();
		riferimentiNormativiForm.setPadding(5);
		riferimentiNormativiForm.setWrapItemTitles(false);
		riferimentiNormativiForm.setNumCols(20);
		riferimentiNormativiForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		riferimentiNormativiForm.setTabSet(tabSet);
		riferimentiNormativiForm.setTabID(_TAB_DATI_SCHEDA_ID);
		
		listaRiferimentiNormativiItem = new RiferimentiNormativiItem();
		listaRiferimentiNormativiItem.setName("listaRiferimentiNormativi");
		listaRiferimentiNormativiItem.setShowTitle(false);
		listaRiferimentiNormativiItem.setColSpan(20);
		if(!isAvvioPropostaAtto()) {
			listaRiferimentiNormativiItem.setAttribute("obbligatorio", true);
		}
		
		riferimentiNormativiForm.setFields(listaRiferimentiNormativiItem);			
	}
	
	protected void createDetailSectionAttiPresupposti() {
		
		createAttiPresuppostiForm();
		
		detailSectionAttiPresupposti = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionAttiPresupposti_title(), true, true, !isAvvioPropostaAtto(), attiPresuppostiForm);
	}
	
	protected void createAttiPresuppostiForm() {
		
		attiPresuppostiForm = new DynamicForm();
		attiPresuppostiForm.setValuesManager(vm);
		attiPresuppostiForm.setWidth100();
		attiPresuppostiForm.setPadding(5);
		attiPresuppostiForm.setWrapItemTitles(false);
		attiPresuppostiForm.setNumCols(20);
		attiPresuppostiForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		attiPresuppostiForm.setTabSet(tabSet);
		attiPresuppostiForm.setTabID(_TAB_DATI_SCHEDA_ID);
		
//		listaAttiPresuppostiItem = new AttiPresuppostiItem();
//		listaAttiPresuppostiItem.setName("listaAttiPresupposti");
//		listaAttiPresuppostiItem.setShowTitle(false);
//		listaAttiPresuppostiItem.setColSpan(20);
//		if(!isAvvioPropostaAtto()) {
//			listaAttiPresuppostiItem.setAttribute("obbligatorio", true);
//		}
//		
//		attiPresuppostiForm.setFields(listaAttiPresuppostiItem);			
		
		attiPresuppostiItem = new CKEditorItem("attiPresupposti");
		attiPresuppostiItem.setShowTitle(false);
		attiPresuppostiItem.setColSpan(20);
		attiPresuppostiItem.setWidth("100%");
		if(!isAvvioPropostaAtto()) {
			attiPresuppostiItem.setRequired(true);
		}
		
		attiPresuppostiForm.setFields(attiPresuppostiItem);	
	}
	
	protected void createDetailSectionMotivazioni() {
		
		createMotivazioniForm();
		
		detailSectionMotivazioni = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionMotivazioni_title(), true, true, !isAvvioPropostaAtto(), motivazioniForm);
	}
	
	protected void createMotivazioniForm() {
		
		motivazioniForm = new DynamicForm();
		motivazioniForm.setValuesManager(vm);
		motivazioniForm.setWidth100();
		motivazioniForm.setPadding(5);
		motivazioniForm.setWrapItemTitles(false);
		motivazioniForm.setNumCols(20);
		motivazioniForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		motivazioniForm.setTabSet(tabSet);
		motivazioniForm.setTabID(_TAB_DATI_SCHEDA_ID);
		
		motivazioniItem = new CKEditorItem("motivazioni");
		motivazioniItem.setShowTitle(false);
		motivazioniItem.setColSpan(20);
		motivazioniItem.setWidth("100%");
		if(!isAvvioPropostaAtto()) {
			motivazioniItem.setRequired(true);
		}
				
		motivazioniForm.setFields(motivazioniItem);		
	}
	
	protected void createDetailSectionDispositivo() {
		
		createDispositivoForm();
		
		detailSectionDispositivo = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionDispositivo_title(), true, true, !isAvvioPropostaAtto(), dispositivoForm);
	}
	
	public boolean showLoghiAggiuntiviDispositivo() {
		return true;
	}
	
	protected void createDispositivoForm() {
		
		dispositivoForm = new DynamicForm();
		dispositivoForm.setValuesManager(vm);
		dispositivoForm.setWidth100();
		dispositivoForm.setPadding(5);
		dispositivoForm.setWrapItemTitles(false);
		dispositivoForm.setNumCols(20);
		dispositivoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		dispositivoForm.setTabSet(tabSet);
		dispositivoForm.setTabID(_TAB_DATI_SCHEDA_ID);
		
		dispositivoItem = new CKEditorItem("dispositivo");
		dispositivoItem.setShowTitle(false);
		dispositivoItem.setColSpan(20);
		dispositivoItem.setWidth("100%");
		if(!isAvvioPropostaAtto()) {
			dispositivoItem.setRequired(true);
		}
		
		loghiAggiuntiviDispositivoItem = new SelectItemValoriDizionario("loghiAggiuntiviDispositivo", I18NUtil.getMessages().nuovaPropostaAtto2_detail_loghiAggiuntiviDispositivo_title(), "LOGHI_X_TEMPLATE_DOC");
		loghiAggiuntiviDispositivoItem.setStartRow(true);
		loghiAggiuntiviDispositivoItem.setWidth(500);
		loghiAggiuntiviDispositivoItem.setAllowEmptyValue(true);
		loghiAggiuntiviDispositivoItem.setDefaultToFirstOption(false);
		loghiAggiuntiviDispositivoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showLoghiAggiuntiviDispositivo();
			}
		});
		
		dispositivoForm.setFields(dispositivoItem, loghiAggiuntiviDispositivoItem);			
	}
	
	protected void createDetailSectionAllegati() {
		
		createAllegatiForm();
		
		detailSectionAllegati = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionAllegati_title(), true, false, false, allegatiForm);
	}
	
	protected void createAllegatiForm() {
		
		allegatiForm = new DynamicForm();
		allegatiForm.setValuesManager(vm);
		allegatiForm.setWidth100();
		allegatiForm.setPadding(5);
		allegatiForm.setWrapItemTitles(false);
		allegatiForm.setNumCols(20);
		allegatiForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		allegatiForm.setTabSet(tabSet);
		allegatiForm.setTabID(_TAB_DATI_SCHEDA_ID);
		
		listaAllegatiItem = new AllegatiItem() {

			@Override
			public String getIdProcess() {
				return getIdProcessTask();
			}

			@Override
			public String getIdProcessType() {
				return getIdProcessTypeTask();
			}

			@Override
			public String getIdTaskCorrenteAllegati() {
				return getIdTaskCorrente();
			}

			@Override
			public HashSet<String> getTipiModelliAttiAllegati() {
				return getTipiModelliAttiInAllegati();
			}
			
			@Override
			public boolean isObbligatorioFile() {
				return isObbligatorioFileInAllegati();
			}

			@Override
			public boolean showGeneraDaModello() {
				return true;
			}

			@Override
			public Record getRecordCaricaModelloAllegato(String idModello, String tipoModello) {
				return getRecordCaricaModelloInAllegati(idModello, tipoModello);
			}
			
			@Override
			public String getTitleFlgParteDispositivo() {
				return I18NUtil.getMessages().nuovaPropostaAtto2_detail_allegati_flgParteIntegrante_title();
			}
			
			@Override
			public Integer getWidthDescrizioneFileAllegato() {
				return 250;
			}
			
			@Override
			public Integer getWidthNomeFileAllegato() {
				return 250;
			}
			
			@Override
			public boolean showTimbraBarcodeMenuOmissis() {
				return true;
			}
			
			@Override
			public boolean getShowVersioneOmissis() {
				return true;
			}
			
			@Override
			public Record getDetailRecord() {
				return getDetailRecordInAllegati();
			}
		};
		listaAllegatiItem.setName("listaAllegati");
		listaAllegatiItem.setShowTitle(false);
		listaAllegatiItem.setShowFlgParteDispositivo(true);
		listaAllegatiItem.setShowFlgNoPubblAllegato(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ESCL_PUBBL_FILE_IN_ATTI"));
		listaAllegatiItem.setColSpan(20);
		
		allegatiForm.setFields(listaAllegatiItem);				
	}
		
	public boolean showTabDatiSpesa() {
		return _FLG_SPESA_SI.equalsIgnoreCase(getValueAsString("flgSpesa"));
	}
	
	public boolean showTabDatiSpesaCorrente() {
		return showTabDatiSpesa() && isAttivoSIB() && !isDeterminaPersonale() && getValueAsBoolean("flgSpesaCorrente");
	}
	
	public boolean showTabDatiSpesaContoCapitale() {
		return showTabDatiSpesa() && isAttivoSIB() && !isDeterminaPersonale() && getValueAsBoolean("flgSpesaContoCapitale");
	}
	
//	public boolean showTabDatiSpesaPersonale() {
//		return isDeterminaPersonale();
//	}
	
	public void saveAndReloadTask() {
		
	}
	
	protected String getTabDatiSpesaPrompt() {
		return I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiSpesa_prompt();
	}
	
	/**
	 * Metodo per costruire il tab "Dati di spesa"
	 * 
	 */
	protected void createTabDatiSpesa() {

		tabDatiSpesa = new Tab("<b>" + getTabDatiSpesaPrompt() + "</b>");
		tabDatiSpesa.setAttribute("tabID", _TAB_DATI_SPESA_ID);
		tabDatiSpesa.setPrompt(getTabDatiSpesaPrompt());
		tabDatiSpesa.setPane(createTabPane(getLayoutDatiSpesa()));
	}

	/**
	 * Metodo che restituisce il layout del tab "Dati di spesa"
	 * 
	 */
	public VLayout getLayoutDatiSpesa() {

		VLayout layoutDatiSpesa = new VLayout(5);
		
		createDetailSectionRuoliSpesa();
		layoutDatiSpesa.addMember(detailSectionRuoliSpesa);

		createDetailSectionOpzioniSpesa();
		layoutDatiSpesa.addMember(detailSectionOpzioniSpesa);

		return layoutDatiSpesa;
	}
	
	protected void createDetailSectionRuoliSpesa() {
		
		createRuoliSpesaForm();
		
		detailSectionRuoliSpesa = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionRuoliSpesa_title(), true, true, true, ruoliSpesaForm1, ruoliSpesaForm2);
	}
		
	public boolean showUfficioDefinizioneSpesa() { 
		if(AurigaLayout.isAttivoClienteCMMI()) {
			return true;
		}		
		String struttCompDefDatiCont = AurigaLayout.getParametroDB("STRUTT_COMP_DEF_DATI_CONT");
		return struttCompDefDatiCont == null || !_STRUTT_COMP_DEF_DATI_CONT_SEMPRE_UGUALE_UO_PROPONENTE.equalsIgnoreCase(struttCompDefDatiCont);
	}
	
	protected String getTitleUfficioDefinizioneSpesa() {
		return I18NUtil.getMessages().nuovaPropostaAtto2_detail_ufficioDefinizioneSpesa_title();
	}
	
	protected void setUfficioDefinizioneSpesaFromUoProponente() {
		if(!AurigaLayout.isAttivoClienteCMMI()) {
			if(listaUfficioDefinizioneSpesaItem != null) {
				String struttCompDefDatiCont = AurigaLayout.getParametroDB("STRUTT_COMP_DEF_DATI_CONT");
				if(struttCompDefDatiCont != null) {
					if(_STRUTT_COMP_DEF_DATI_CONT_SEMPRE_UGUALE_UO_PROPONENTE.equalsIgnoreCase(struttCompDefDatiCont) 
						|| (_STRUTT_COMP_DEF_DATI_CONT_DEFAULT_UGUALE_UO_PROPONENTE.equalsIgnoreCase(struttCompDefDatiCont) && !listaUfficioDefinizioneSpesaItem.hasValue())) {
						Record lRecordUfficioDefinizioneSpesa = new Record();
						if(isAbilToSelUffPropEsteso()) {
							RecordList listaUfficioProponente = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaUfficioProponente") : null;
							if(listaUfficioProponente != null && listaUfficioProponente.getLength() > 0) {
								lRecordUfficioDefinizioneSpesa.setAttribute("idUo", listaUfficioProponente.get(0).getAttribute("idUo"));
								lRecordUfficioDefinizioneSpesa.setAttribute("codRapido", listaUfficioProponente.get(0).getAttribute("codRapido"));
								lRecordUfficioDefinizioneSpesa.setAttribute("descrizione", listaUfficioProponente.get(0).getAttribute("descrizione"));
								lRecordUfficioDefinizioneSpesa.setAttribute("descrizioneEstesa", listaUfficioProponente.get(0).getAttribute("descrizione"));
								lRecordUfficioDefinizioneSpesa.setAttribute("organigramma", listaUfficioProponente.get(0).getAttribute("organigramma"));					
							}
						} else {
							lRecordUfficioDefinizioneSpesa.setAttribute("idUo", getValueAsString("ufficioProponente"));
							lRecordUfficioDefinizioneSpesa.setAttribute("codRapido", getValueAsString("codUfficioProponente"));
							lRecordUfficioDefinizioneSpesa.setAttribute("descrizione", getValueAsString("desUfficioProponente"));
							lRecordUfficioDefinizioneSpesa.setAttribute("descrizioneEstesa", getValueAsString("desUfficioProponente"));						
							lRecordUfficioDefinizioneSpesa.setAttribute("organigramma", "UO" + getValueAsString("ufficioProponente"));
						}
						codUfficioDefinizioneSpesaItem.setValue(lRecordUfficioDefinizioneSpesa.getAttribute("codRapido"));
						desUfficioDefinizioneSpesaItem.setValue(lRecordUfficioDefinizioneSpesa.getAttribute("descrizione"));
						RecordList listaUfficioDefinizioneSpesa = new RecordList();
						listaUfficioDefinizioneSpesa.add(lRecordUfficioDefinizioneSpesa);
						listaUfficioDefinizioneSpesaItem.drawAndSetValue(listaUfficioDefinizioneSpesa);					
					}	
				}
			}
		}
	}
	
	protected void createRuoliSpesaForm() {
		
		ruoliSpesaForm1 = new DynamicForm();
		ruoliSpesaForm1.setValuesManager(vm);
		ruoliSpesaForm1.setWidth100();
		ruoliSpesaForm1.setPadding(5);
		ruoliSpesaForm1.setWrapItemTitles(false);
		ruoliSpesaForm1.setNumCols(20);
		ruoliSpesaForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ruoliSpesaForm1.setTabSet(tabSet);
		ruoliSpesaForm1.setTabID(_TAB_DATI_SPESA_ID);
		
		flgAdottanteUnicoRespPEGItem = new CheckboxItem("flgAdottanteUnicoRespPEG", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgAdottanteUnicoRespPEG_title());
		flgAdottanteUnicoRespPEGItem.setDefaultValue(true);
		flgAdottanteUnicoRespPEGItem.setStartRow(true);
		flgAdottanteUnicoRespPEGItem.setColSpan(1);
		flgAdottanteUnicoRespPEGItem.setWidth("*");
		flgAdottanteUnicoRespPEGItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				ruoliSpesaForm1.markForRedraw();
			}
		});
		
		listaResponsabiliPEGItem = new ResponsabiliPEGItem() {
			
			@Override
			public boolean skipValidation() {
				if(showTabDatiSpesa() && !getValueAsBoolean("flgAdottanteUnicoRespPEG")) {
					return super.skipValidation(); //TODO VERIFICARE se quando chiamo super.skipValidation() mi torna true quando sono su un altro tab
				}
				return true;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_responsabiliPEG_title()) + "</span>");	
				return lVLayout;
			}
		};
		listaResponsabiliPEGItem.setName("listaResponsabiliPEG");
		listaResponsabiliPEGItem.setStartRow(true);
		listaResponsabiliPEGItem.setShowTitle(false);
		listaResponsabiliPEGItem.setColSpan(20);
		listaResponsabiliPEGItem.setAttribute("obbligatorio", true);
		listaResponsabiliPEGItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showTabDatiSpesa() && !getValueAsBoolean("flgAdottanteUnicoRespPEG");
			}
		});
		
		ruoliSpesaForm1.setFields(
			flgAdottanteUnicoRespPEGItem, 
			listaResponsabiliPEGItem
		);		
				
		ruoliSpesaForm2 = new DynamicForm();
		ruoliSpesaForm2.setValuesManager(vm);
		ruoliSpesaForm2.setWidth100();
		ruoliSpesaForm2.setPadding(5);
		ruoliSpesaForm2.setWrapItemTitles(false);
		ruoliSpesaForm2.setNumCols(20);
		ruoliSpesaForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ruoliSpesaForm2.setTabSet(tabSet);
		ruoliSpesaForm2.setTabID(_TAB_DATI_SPESA_ID);
		
		codUfficioDefinizioneSpesaItem = new HiddenItem("codUfficioDefinizioneSpesa");
		desUfficioDefinizioneSpesaItem = new HiddenItem("desUfficioDefinizioneSpesa");
		
		listaUfficioDefinizioneSpesaItem = new SelezionaUOItem() {
			
			@Override
			public int getSelectItemOrganigrammaWidth() {
				return 650;
			}
			
			@Override
			public boolean skipValidation() {
				if(showTabDatiSpesa() && showUfficioDefinizioneSpesa()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);				
				lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleUfficioDefinizioneSpesa()) + "</span>");	
				return lVLayout;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return false;
			};
		};
		listaUfficioDefinizioneSpesaItem.setName("listaUfficioDefinizioneSpesa");
		listaUfficioDefinizioneSpesaItem.setStartRow(true);
		listaUfficioDefinizioneSpesaItem.setShowTitle(false);
		listaUfficioDefinizioneSpesaItem.setColSpan(20);
		listaUfficioDefinizioneSpesaItem.setNotReplicable(true);
		listaUfficioDefinizioneSpesaItem.setAttribute("obbligatorio", true);
		listaUfficioDefinizioneSpesaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showTabDatiSpesa() && showUfficioDefinizioneSpesa();
			}
		});
		
		ruoliSpesaForm2.setFields(
			codUfficioDefinizioneSpesaItem,
			desUfficioDefinizioneSpesaItem,
			listaUfficioDefinizioneSpesaItem
		);		
	}
	
	public boolean showDetailSectionOpzioniSpesa() {
		if(AurigaLayout.isAttivoClienteCMMI()) {
			return !isDeterminaPersonale();
		}		
		return false;
	}	
	
	protected void createDetailSectionOpzioniSpesa() {
		
		createOpzioniSpesaForm();
		
		detailSectionOpzioniSpesa = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionOpzioniSpesa_title(), true, true, true, opzioniSpesaForm);
	}
	
	protected void createOpzioniSpesaForm() {
		
		opzioniSpesaForm = new DynamicForm();
		opzioniSpesaForm.setValuesManager(vm);
		opzioniSpesaForm.setWidth100();
		opzioniSpesaForm.setPadding(5);
		opzioniSpesaForm.setWrapItemTitles(false);
		opzioniSpesaForm.setNumCols(20);
		opzioniSpesaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		opzioniSpesaForm.setTabSet(tabSet);
		opzioniSpesaForm.setTabID(_TAB_DATI_SPESA_ID);
				
		CustomValidator tipoSpesaValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(showTabDatiSpesa() && showDetailSectionOpzioniSpesa()) {
					return getValueAsBoolean("flgSpesaCorrente") || getValueAsBoolean("flgSpesaContoCapitale") || getValueAsBoolean("flgSoloSubImpSubCrono");
				}
				return true;
			}
		};
		tipoSpesaValidator.setErrorMessage(I18NUtil.getMessages().nuovaPropostaAtto2_detail_tipoSpesaValidator_errorMessage());
		
		TitleItem tipoSpesaTitleItem = new TitleItem(FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_tipoSpesa_title(), true));
		tipoSpesaTitleItem.setAttribute("obbligatorio", true);
		tipoSpesaTitleItem.setValidators(tipoSpesaValidator);
		
		flgSpesaCorrenteItem = new CheckboxItem("flgSpesaCorrente", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgSpesaCorrente_title());
		flgSpesaCorrenteItem.setDefaultValue(false);
		flgSpesaCorrenteItem.setColSpan(1);
		flgSpesaCorrenteItem.setWidth("*");
		flgSpesaCorrenteItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				opzioniSpesaForm.markForRedraw();
				enableDisableTabs();
			}
		});
		
		
		flgImpegniCorrenteGiaValidatiItem = new CheckboxItem("flgImpegniCorrenteGiaValidati", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgImpegniCorrenteGiaValidati_title());
		flgImpegniCorrenteGiaValidatiItem.setDefaultValue(false);
		flgImpegniCorrenteGiaValidatiItem.setColSpan(1);
		flgImpegniCorrenteGiaValidatiItem.setWidth("*");
		flgImpegniCorrenteGiaValidatiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return getValueAsBoolean("flgSpesaCorrente");
			}
		});
		
		flgSpesaContoCapitaleItem = new CheckboxItem("flgSpesaContoCapitale", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgSpesaContoCapitale_title());
		flgSpesaContoCapitaleItem.setDefaultValue(false);
		flgSpesaContoCapitaleItem.setColSpan(1);
		flgSpesaContoCapitaleItem.setWidth("*");
		flgSpesaContoCapitaleItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {		
				opzioniSpesaForm.markForRedraw();
				enableDisableTabs();
			}
		});
		
		flgImpegniContoCapitaleGiaRilasciatiItem = new CheckboxItem("flgImpegniContoCapitaleGiaRilasciati", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgImpegniContoCapitaleGiaRilasciati_title());
		flgImpegniContoCapitaleGiaRilasciatiItem.setDefaultValue(false);
		flgImpegniContoCapitaleGiaRilasciatiItem.setColSpan(1);
		flgImpegniContoCapitaleGiaRilasciatiItem.setWidth("*");
		flgImpegniContoCapitaleGiaRilasciatiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return getValueAsBoolean("flgSpesaContoCapitale");
			}
		});
		flgImpegniContoCapitaleGiaRilasciatiItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if(modalitaInvioDatiSpesaARagioneriaContoCapitaleItem != null) {
					modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setValueMap(buildModalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap());
				}		
			}
		});		
		
		flgSoloSubImpSubCronoItem = new CheckboxItem("flgSoloSubImpSubCrono", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgSoloSubImpSubCrono_title());
		flgSoloSubImpSubCronoItem.setDefaultValue(false);
		flgSoloSubImpSubCronoItem.setStartRow(true);
		flgSoloSubImpSubCronoItem.setColSpan(20);
		flgSoloSubImpSubCronoItem.setWidth("*");	
		flgSoloSubImpSubCronoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				opzioniSpesaForm.markForRedraw();
				if(modalitaInvioDatiSpesaARagioneriaContoCapitaleItem != null) {
					modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setValueMap(buildModalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap());
				}	
			}
		});
		flgSoloSubImpSubCronoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String labelSubSpesaAtti = AurigaLayout.getParametroDB("LABEL_SUB_SPESA_ATTI");
				if(labelSubSpesaAtti != null && !"".equals(labelSubSpesaAtti)) {
					flgSoloSubImpSubCronoItem.setTitle(labelSubSpesaAtti);
					return true;
				}
				return false;
			}
		});
		
		flgConVerificaContabilitaItem = new CheckboxItem("flgConVerificaContabilita", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgConVerificaContabilita_title());
		flgConVerificaContabilitaItem.setDefaultValue(true);
		flgConVerificaContabilitaItem.setStartRow(true);
		flgConVerificaContabilitaItem.setColSpan(20);
		flgConVerificaContabilitaItem.setWidth("*");	
		flgConVerificaContabilitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(getValueAsBoolean("flgSoloSubImpSubCrono")) {
					item.setDisabled(true);
					item.setValue(true);
				} else {
					item.setDisabled(false);
				}
				return true;
			}
		});
		
		flgRichiediParereRevisoriContabiliItem = new CheckboxItem("flgRichiediParereRevisoriContabili", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgRichiediParereRevisoriContabili_title());
		flgRichiediParereRevisoriContabiliItem.setDefaultValue(false);
		flgRichiediParereRevisoriContabiliItem.setStartRow(true);
		flgRichiediParereRevisoriContabiliItem.setColSpan(20);
		flgRichiediParereRevisoriContabiliItem.setWidth("*");
		flgRichiediParereRevisoriContabiliItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_REVISORI_CONT_IN_ITER_ATTI");
			}
		});
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setColSpan(1);
		spacerItem.setWidth(20);
		
		opzioniSpesaForm.setFields(
			tipoSpesaTitleItem, flgSpesaCorrenteItem, flgImpegniCorrenteGiaValidatiItem, spacerItem, flgSpesaContoCapitaleItem, flgImpegniContoCapitaleGiaRilasciatiItem, spacerItem, flgSoloSubImpSubCronoItem,
			flgConVerificaContabilitaItem, 
			flgRichiediParereRevisoriContabiliItem
		);			
	}
	
	/**
	 * Metodo per costruire il tab "Dati spesa corrente"
	 * 
	 */
	protected void createTabDatiSpesaCorrente() {

		tabDatiSpesaCorrente = new Tab("<b>" + I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiSpesaCorrente_prompt() + "</b>");
		tabDatiSpesaCorrente.setAttribute("tabID", _TAB_DATI_SPESA_CORRENTE_ID);
		tabDatiSpesaCorrente.setPrompt(I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiSpesaCorrente_prompt());
		tabDatiSpesaCorrente.setPane(createTabPane(getLayoutDatiSpesaCorrente()));	
	}
	
	/**
	 * Metodo che restituisce il layout del tab "Dati spesa corrente"
	 * 
	 */
	protected VLayout getLayoutDatiSpesaCorrente() {
		
		VLayout layoutDatiSpesaCorrente = new VLayout(5);

		createDetailSectionOpzioniSpesaCorrente();
		layoutDatiSpesaCorrente.addMember(detailSectionOpzioniSpesaCorrente);
		
		createDetailSectionDatiContabiliSIBCorrente();
		detailSectionDatiContabiliSIBCorrente.setVisible(false);		
		layoutDatiSpesaCorrente.addMember(detailSectionDatiContabiliSIBCorrente);
		
		createDetailSectionInvioDatiSpesaCorrente();
		detailSectionInvioDatiSpesaCorrente.setVisible(false);		
		layoutDatiSpesaCorrente.addMember(detailSectionInvioDatiSpesaCorrente);
		
		createDetailSectionFileXlsCorrente();
		detailSectionFileXlsCorrente.setVisible(false);		
		layoutDatiSpesaCorrente.addMember(detailSectionFileXlsCorrente);
		
//		createDetailSectionAllegatiCorrente();
//		layoutDatiSpesaCorrente.addMember(detailSectionAllegatiCorrente);
		
		createDetailSectionNoteCorrente();
		layoutDatiSpesaCorrente.addMember(detailSectionNoteCorrente);
		
		return layoutDatiSpesaCorrente;
	}
	
	protected void createDetailSectionOpzioniSpesaCorrente() {
		
		createOpzioniSpesaCorrenteForm();
		
		detailSectionOpzioniSpesaCorrente = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionOpzioniSpesaCorrente_title(), true, true, true, opzioniSpesaCorrenteForm1, opzioniSpesaCorrenteForm2);
	}
	
	protected void createOpzioniSpesaCorrenteForm() {
		
		opzioniSpesaCorrenteForm1 = new DynamicForm();
		opzioniSpesaCorrenteForm1.setValuesManager(vm);
		opzioniSpesaCorrenteForm1.setWidth100();
		opzioniSpesaCorrenteForm1.setPadding(5);
		opzioniSpesaCorrenteForm1.setWrapItemTitles(false);
		opzioniSpesaCorrenteForm1.setNumCols(20);
		opzioniSpesaCorrenteForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		opzioniSpesaCorrenteForm1.setTabSet(tabSet);
		opzioniSpesaCorrenteForm1.setTabID(_TAB_DATI_SPESA_CORRENTE_ID);
		
		flgDisattivaAutoRequestDatiContabiliSIBCorrenteItem = new CheckboxItem("flgDisattivaAutoRequestDatiContabiliSIBCorrente", "disattiva caricamento automatico da SIB all'apertura del task");
		flgDisattivaAutoRequestDatiContabiliSIBCorrenteItem.setDefaultValue(false);
		flgDisattivaAutoRequestDatiContabiliSIBCorrenteItem.setStartRow(true);
		flgDisattivaAutoRequestDatiContabiliSIBCorrenteItem.setColSpan(10);
		flgDisattivaAutoRequestDatiContabiliSIBCorrenteItem.setWidth("*");
		
		opzioniSpesaCorrenteForm1.setFields(flgDisattivaAutoRequestDatiContabiliSIBCorrenteItem);
		
		opzioniSpesaCorrenteForm2 = new DynamicForm();
		opzioniSpesaCorrenteForm2.setValuesManager(vm);
		opzioniSpesaCorrenteForm2.setWidth100();
		opzioniSpesaCorrenteForm2.setPadding(5);
		opzioniSpesaCorrenteForm2.setWrapItemTitles(false);
		opzioniSpesaCorrenteForm2.setNumCols(20);
		opzioniSpesaCorrenteForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		opzioniSpesaCorrenteForm2.setTabSet(tabSet);
		opzioniSpesaCorrenteForm2.setTabID(_TAB_DATI_SPESA_CORRENTE_ID);
		
		prenotazioneSpesaSIBDiCorrenteItem = new RadioGroupItem("prenotazioneSpesaSIBDiCorrente", I18NUtil.getMessages().nuovaPropostaAtto2_detail_prenotazioneSpesaSIBDi_title());
		prenotazioneSpesaSIBDiCorrenteItem.setStartRow(true);
		LinkedHashMap<String, String> prenotazioneSpesaSIBDiCorrenteValueMap = new LinkedHashMap<String, String>();
		prenotazioneSpesaSIBDiCorrenteValueMap.put(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_A, I18NUtil.getMessages().nuovaPropostaAtto2_detail_prenotazioneSpesaSIBDi_opzioneA());
		prenotazioneSpesaSIBDiCorrenteValueMap.put(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_B, I18NUtil.getMessages().nuovaPropostaAtto2_detail_prenotazioneSpesaSIBDi_opzioneB());		
		prenotazioneSpesaSIBDiCorrenteItem.setValueMap(prenotazioneSpesaSIBDiCorrenteValueMap);
		prenotazioneSpesaSIBDiCorrenteItem.setVertical(false);
		prenotazioneSpesaSIBDiCorrenteItem.setWrap(false);
		prenotazioneSpesaSIBDiCorrenteItem.setDefaultValue(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_A);
		prenotazioneSpesaSIBDiCorrenteItem.setAttribute("obbligatorio", true);
		prenotazioneSpesaSIBDiCorrenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return showTabDatiSpesaCorrente() && isDatiSpesaEditabili();
			}
		}));
		prenotazioneSpesaSIBDiCorrenteItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_SPESA_CORRENTE_ID);
				showHideSections();
			}
		});
		
		modalitaInvioDatiSpesaARagioneriaCorrenteItem = new RadioGroupItem("modalitaInvioDatiSpesaARagioneriaCorrente", I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_title());
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setStartRow(true);
		LinkedHashMap<String, String> modalitaInvioDatiSpesaARagioneriaCorrenteValueMap = new LinkedHashMap<String, String>();
		modalitaInvioDatiSpesaARagioneriaCorrenteValueMap.put(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B1, I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB1());
		modalitaInvioDatiSpesaARagioneriaCorrenteValueMap.put(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B2, I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB2());		
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setValueMap(modalitaInvioDatiSpesaARagioneriaCorrenteValueMap);
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setVertical(false);
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setWrap(false);
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setAttribute("obbligatorio", true);
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setDefaultValue(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B1);
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return showTabDatiSpesaCorrente() && getValueAsString("prenotazioneSpesaSIBDiCorrente").equals(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_B) && isDatiSpesaEditabili();
			}
		}));
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return getValueAsString("prenotazioneSpesaSIBDiCorrente").equals(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_B);
			}
		});
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_SPESA_CORRENTE_ID);
				showHideSections();
			}
		});
		
		opzioniSpesaCorrenteForm2.setFields(
			prenotazioneSpesaSIBDiCorrenteItem,
			modalitaInvioDatiSpesaARagioneriaCorrenteItem
		);	
	}
	
	public boolean showDetailSectionDatiContabiliSIBCorrente() {
		return getValueAsString("prenotazioneSpesaSIBDiCorrente").equals(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_A) || isPresentiDatiContabiliSIBCorrente();
	}
		
	protected void createDetailSectionDatiContabiliSIBCorrente() {

		createDatiContabiliSIBCorrenteForm();
		
		detailSectionDatiContabiliSIBCorrente = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionDatiContabiliSIBCorrente_title(), true, true, false, datiContabiliSIBCorrenteForm);
	}
	
	public void refreshListaDatiContabiliSIBCorrente(final ServiceCallback<Record> callback) {
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
		if(isAttivaRequestMovimentiDaAMC()) {
			lNuovaPropostaAtto2DataSource.addParam("flgAttivaRequestMovimentiDaAMC", "true");
		}	
		lNuovaPropostaAtto2DataSource.performCustomOperation("getListaDatiContabiliSIBCorrente", getRecordToSave(), new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					listaDatiContabiliSIBCorrenteItem.setData(record.getAttributeAsRecordList("listaDatiContabiliSIBCorrente"));
					if(record.getAttribute("errorMessageDatiContabiliSIBCorrente") != null && !"".equals(record.getAttribute("errorMessageDatiContabiliSIBCorrente"))) {
						listaDatiContabiliSIBCorrenteItem.setGridEmptyMessage(record.getAttribute("errorMessageDatiContabiliSIBCorrente"));
					} else {
						listaDatiContabiliSIBCorrenteItem.setGridEmptyMessage(I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabiliSIB_emptyMessage());
					}					
					redrawTabForms(_TAB_DATI_SPESA_CORRENTE_ID);
					if(callback != null) {
						callback.execute(record);
					}
				} 				
			}
		}, new DSRequest());
	}
	
	protected void createDatiContabiliSIBCorrenteForm() {
		
		datiContabiliSIBCorrenteForm = new DynamicForm();
		datiContabiliSIBCorrenteForm.setValuesManager(vm);
		datiContabiliSIBCorrenteForm.setWidth100();
		datiContabiliSIBCorrenteForm.setPadding(5);
		datiContabiliSIBCorrenteForm.setWrapItemTitles(false);
		datiContabiliSIBCorrenteForm.setNumCols(20);
		datiContabiliSIBCorrenteForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		datiContabiliSIBCorrenteForm.setTabSet(tabSet);
		datiContabiliSIBCorrenteForm.setTabID(_TAB_DATI_SPESA_CORRENTE_ID);
		
		listaDatiContabiliSIBCorrenteItem = new ListaDatiContabiliSIBItem("listaDatiContabiliSIBCorrente") {
			
			@Override
			public boolean isShowDatiStoriciButton() {
				return isModalitaGrigliaCorrente() && isPresentiDatiContabiliSIBCorrente() && isPresentiDatiStoriciCorrente();
			}
			
			public void onClickDatiStoriciButton() {
				DatiContabiliStoriciWindow lDatiContabiliStoriciWindow = new DatiContabiliStoriciWindow("datiContabiliStoriciWindow", invioDatiSpesaCorrenteForm.getValueAsRecordList("listaInvioDatiSpesaCorrente")) {
					
					@Override
					public String getSIBDataSourceName() {
						return "DatiContabiliSIBDataSource";
					}
				};
				lDatiContabiliStoriciWindow.show();
			}
			
			public void onClickRefreshListButton() {
				refreshListaDatiContabiliSIBCorrente(new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						showHideSections();						
						afterShow();
						controllaTotali(false);
					}
				});
			}
		};
		listaDatiContabiliSIBCorrenteItem.setGridEmptyMessage(I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabiliSIB_emptyMessage());
		listaDatiContabiliSIBCorrenteItem.setStartRow(true);
		listaDatiContabiliSIBCorrenteItem.setShowTitle(false);
		listaDatiContabiliSIBCorrenteItem.setHeight(245);		
		listaDatiContabiliSIBCorrenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showDetailSectionDatiContabiliSIBCorrente();
			}
		});
		
		datiContabiliSIBCorrenteForm.setFields(listaDatiContabiliSIBCorrenteItem);	
	}

	public boolean showDetailSectionInvioDatiSpesaCorrente() {
		return isModalitaGrigliaCorrente() && !isPresentiDatiContabiliSIBCorrente();
	}
	
	protected void createDetailSectionInvioDatiSpesaCorrente() {
		
		createInvioDatiSpesaCorrenteForm();
		
		detailSectionInvioDatiSpesaCorrente = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionInvioDatiSpesaCorrente_title(), true, true, false, invioDatiSpesaCorrenteForm);
	}
	
	protected void createInvioDatiSpesaCorrenteForm() {
		
		invioDatiSpesaCorrenteForm = new DynamicForm();
		invioDatiSpesaCorrenteForm.setValuesManager(vm);
		invioDatiSpesaCorrenteForm.setWidth100();
		invioDatiSpesaCorrenteForm.setPadding(5);
		invioDatiSpesaCorrenteForm.setWrapItemTitles(false);
		invioDatiSpesaCorrenteForm.setNumCols(20);
		invioDatiSpesaCorrenteForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		invioDatiSpesaCorrenteForm.setTabSet(tabSet);
		invioDatiSpesaCorrenteForm.setTabID(_TAB_DATI_SPESA_CORRENTE_ID);
		
		listaInvioDatiSpesaCorrenteItem = new ListaInvioDatiSpesaItem("listaInvioDatiSpesaCorrente") {
			
			@Override
			public String[] getCIGValueMap() {				
				return buildCIGValueMap();
			}
			
			@Override
			public HashSet<String> getVociPEGNoVerifDisp() {
				return vociPEGNoVerifDisp;
			}
			
			@Override
			public String getSIBDataSourceName() {
				return "DatiContabiliSIBDataSource";
			}
			
			@Override
			public boolean isGrigliaEditabile() {
				return true;
			}
			
			public void onClickRefreshListButton() {
				refreshListaDatiContabiliSIBCorrente(new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						showHideSections();						
						afterShow();
						controllaTotali(false);
					}
				});														
			}
		};
		listaInvioDatiSpesaCorrenteItem.setStartRow(true);
		listaInvioDatiSpesaCorrenteItem.setShowTitle(false);
		listaInvioDatiSpesaCorrenteItem.setHeight(245);		
		listaInvioDatiSpesaCorrenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDetailSectionInvioDatiSpesaCorrente();
			}
		});
		
		invioDatiSpesaCorrenteForm.setFields(listaInvioDatiSpesaCorrenteItem);	
	}
	
	public boolean showDetailSectionFileXlsCorrente() {
		return getValueAsString("prenotazioneSpesaSIBDiCorrente").equals(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_B) && getValueAsString("modalitaInvioDatiSpesaARagioneriaCorrente").equals(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B2); 
	}
	
	protected void createDetailSectionFileXlsCorrente() {
		
		createFileXlsCorrenteForm();
		
		detailSectionFileXlsCorrente = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionFileXlsCorrente_title(), true, true, true, fileXlsCorrenteForm);
	}
	
	protected void createFileXlsCorrenteForm() {
		
		fileXlsCorrenteForm = new DynamicForm();
		fileXlsCorrenteForm.setValuesManager(vm);
		fileXlsCorrenteForm.setWidth100();
		fileXlsCorrenteForm.setPadding(5);
		fileXlsCorrenteForm.setWrapItemTitles(false);
		fileXlsCorrenteForm.setNumCols(20);
		fileXlsCorrenteForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		fileXlsCorrenteForm.setTabSet(tabSet);
		fileXlsCorrenteForm.setTabID(_TAB_DATI_SPESA_CORRENTE_ID);
		
		fileXlsCorrenteItem = new DocumentItem() {
			
			@Override
			public int getWidth() {
				return 250;
			}
			
			@Override
			public boolean showVisualizzaVersioniMenuItem() {
				return false;
			}
			
			@Override
			public boolean showAcquisisciDaScannerMenuItem() {
				return false;
			}
			
			@Override
			public boolean showFirmaMenuItem() {
				return false;
			}
			
			@Override
			public boolean isFormatoAmmesso(InfoFileRecord info) {	
				String correctName = info != null ? info.getCorrectFileName() : "";
				return correctName.toLowerCase().endsWith(".xls") || correctName.toLowerCase().endsWith(".xlsx");
			}
		};
		fileXlsCorrenteItem.setStartRow(true);
		fileXlsCorrenteItem.setName("fileXlsCorrente");
		fileXlsCorrenteItem.setShowTitle(false);
		fileXlsCorrenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDetailSectionFileXlsCorrente();
			}
		});
		/*
		fileXlsCorrenteItem.setAttribute("obbligatorio", true);
		fileXlsCorrenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return showTabDatiSpesaCorrente() && showDetailSectionFileXlsCorrente() && isDatiSpesaEditabili() && isEsitoTaskSelezionatoOk();
			}
		}));
		*/
		
		nomeFileTracciatoXlsCorrenteItem = new HiddenItem("nomeFileTracciatoXlsCorrente");
		uriFileTracciatoXlsCorrenteItem = new HiddenItem("uriFileTracciatoXlsCorrente");
		
		scaricaTracciatoXlsCorrenteButton = new ImgButtonItem("scaricaTracciatoXlsCorrenteButton", "file/download_manager.png", "Scarica tracciato");
		scaricaTracciatoXlsCorrenteButton.setAlwaysEnabled(true);
		scaricaTracciatoXlsCorrenteButton.setColSpan(1);
		scaricaTracciatoXlsCorrenteButton.setIconWidth(16);
		scaricaTracciatoXlsCorrenteButton.setIconHeight(16);
		scaricaTracciatoXlsCorrenteButton.setIconVAlign(VerticalAlignment.BOTTOM);
		scaricaTracciatoXlsCorrenteButton.setAlign(Alignment.LEFT);
		scaricaTracciatoXlsCorrenteButton.setWidth(16);
		scaricaTracciatoXlsCorrenteButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				if(uriFileTracciatoXlsCorrenteItem.getValue() != null && !"".equals((String) uriFileTracciatoXlsCorrenteItem.getValue())) {
					Record lRecord = new Record();
					if(nomeFileTracciatoXlsCorrenteItem.getValue() != null && !"".equals((String) nomeFileTracciatoXlsCorrenteItem.getValue())) {
						lRecord.setAttribute("displayFilename", nomeFileTracciatoXlsCorrenteItem.getValue());
					} else {
						lRecord.setAttribute("displayFilename", "Tracciato_SIB.xls");
					}
					lRecord.setAttribute("uri", (String) uriFileTracciatoXlsCorrenteItem.getValue());
					lRecord.setAttribute("sbustato", "false");
					lRecord.setAttribute("remoteUri", true);
					DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
				} else {
					AurigaLayout.addMessage(new MessageBean("Nessun tracciato disponibile", "", MessageType.ERROR));
				}
			}
		});
		scaricaTracciatoXlsCorrenteButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDetailSectionFileXlsCorrente();
			}
		});
		
		fileXlsCorrenteForm.setFields(fileXlsCorrenteItem, nomeFileTracciatoXlsCorrenteItem, uriFileTracciatoXlsCorrenteItem, scaricaTracciatoXlsCorrenteButton);	
	}
	
	protected void createDetailSectionAllegatiCorrente() {
		
		createAllegatiCorrenteForm();
		
		detailSectionAllegatiCorrente = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionAllegatiCorrente_title(), true, false, false, allegatiCorrenteForm);
	}
	
	protected void createAllegatiCorrenteForm() {
		
		allegatiCorrenteForm = new DynamicForm();
		allegatiCorrenteForm.setValuesManager(vm);
		allegatiCorrenteForm.setWidth100();
		allegatiCorrenteForm.setPadding(5);
		allegatiCorrenteForm.setWrapItemTitles(false);
		allegatiCorrenteForm.setNumCols(20);
		allegatiCorrenteForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		allegatiCorrenteForm.setTabSet(tabSet);
		allegatiCorrenteForm.setTabID(_TAB_DATI_SPESA_CORRENTE_ID);
		
		listaAllegatiCorrenteItem = new AllegatiItem() {

			@Override
			public String getIdProcess() {
				return getIdProcessTask();
			}

			@Override
			public String getIdProcessType() {
				return getIdProcessTypeTask();
			}

			@Override
			public String getIdTaskCorrenteAllegati() {
				return getIdTaskCorrente();
			}

			@Override
			public HashSet<String> getTipiModelliAttiAllegati() {
				return getTipiModelliAttiInAllegati();
			}
			
			@Override
			public boolean isObbligatorioFile() {
				return isObbligatorioFileInAllegati();
			}

			@Override
			public boolean showGeneraDaModello() {
				return true;
			}

			@Override
			public Record getRecordCaricaModelloAllegato(String idModello, String tipoModello) {
				return getRecordCaricaModelloInAllegati(idModello, tipoModello);
			}
			
			@Override
			public String getTitleFlgParteDispositivo() {
				return I18NUtil.getMessages().nuovaPropostaAtto2_detail_allegati_flgParteIntegrante_title();
			}
			
			@Override
			public Integer getWidthDescrizioneFileAllegato() {
				return 250;
			}
			
			@Override
			public Integer getWidthNomeFileAllegato() {
				return 250;
			}
			
			@Override
			public boolean getShowVersioneOmissis() {
				return true;
			}
		};
		listaAllegatiCorrenteItem.setName("listaAllegatiCorrente");
		listaAllegatiCorrenteItem.setShowTitle(false);
		listaAllegatiCorrenteItem.setShowFlgParteDispositivo(true);
		listaAllegatiCorrenteItem.setShowFlgNoPubblAllegato(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ESCL_PUBBL_FILE_IN_ATTI"));
		listaAllegatiCorrenteItem.setColSpan(20);
		
		allegatiCorrenteForm.setFields(listaAllegatiCorrenteItem);				
	}
	
	protected void createDetailSectionNoteCorrente() {
		
		createNoteCorrenteForm();
		
		detailSectionNoteCorrente = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionNoteCorrente_title(), true, true, false, noteCorrenteForm);
	}
	
	protected void createNoteCorrenteForm() {
		
		noteCorrenteForm = new DynamicForm();
		noteCorrenteForm.setValuesManager(vm);
		noteCorrenteForm.setWidth100();
		noteCorrenteForm.setPadding(5);
		noteCorrenteForm.setWrapItemTitles(false);
		noteCorrenteForm.setNumCols(20);
		noteCorrenteForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		noteCorrenteForm.setTabSet(tabSet);
		noteCorrenteForm.setTabID(_TAB_DATI_SPESA_CORRENTE_ID);
		
		noteCorrenteItem = new CKEditorItem("noteCorrente");
		noteCorrenteItem.setShowTitle(false);
		noteCorrenteItem.setColSpan(20);
		noteCorrenteItem.setWidth("100%");
				
		noteCorrenteForm.setFields(noteCorrenteItem);			
	}

	/**
	 * Metodo per costruire il tab "Dati spesa in conto capitale"
	 * 
	 */
	protected void createTabDatiSpesaContoCapitale() {

		tabDatiSpesaContoCapitale = new Tab("<b>" + I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiSpesaContoCapitale_prompt() + "</b>");
		tabDatiSpesaContoCapitale.setAttribute("tabID", _TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		tabDatiSpesaContoCapitale.setPrompt(I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiSpesaContoCapitale_prompt());
		tabDatiSpesaContoCapitale.setPane(createTabPane(getLayoutDatiSpesaContoCapitale()));
	}
	
	/**
	 * Metodo che restituisce il layout del tab "Dati spesa in conto capitale"
	 * 
	 */
	protected VLayout getLayoutDatiSpesaContoCapitale() {
		
		VLayout layoutDatiSpesaContoCapitale = new VLayout(5);
		
		createDetailSectionOpzioniSpesaContoCapitale();
		layoutDatiSpesaContoCapitale.addMember(detailSectionOpzioniSpesaContoCapitale);
		
		createDetailSectionDatiContabiliSIBContoCapitale();
		detailSectionDatiContabiliSIBContoCapitale.setVisible(false);		
		layoutDatiSpesaContoCapitale.addMember(detailSectionDatiContabiliSIBContoCapitale);
		
		createDetailSectionInvioDatiSpesaContoCapitale();
		detailSectionInvioDatiSpesaContoCapitale.setVisible(false);		
		layoutDatiSpesaContoCapitale.addMember(detailSectionInvioDatiSpesaContoCapitale);
		
		createDetailSectionFileXlsContoCapitale();
		detailSectionFileXlsContoCapitale.setVisible(false);		
		layoutDatiSpesaContoCapitale.addMember(detailSectionFileXlsContoCapitale);
		
//		createDetailSectionAllegatiContoCapitale();
//		layoutDatiSpesaContoCapitale.addMember(detailSectionAllegatiContoCapitale);
		
		createDetailSectionNoteContoCapitale();
		layoutDatiSpesaContoCapitale.addMember(detailSectionNoteContoCapitale);
		
		return layoutDatiSpesaContoCapitale;
	}
	
	protected void createDetailSectionOpzioniSpesaContoCapitale() {
		
		createOpzioniSpesaContoCapitaleForm();
		
		detailSectionOpzioniSpesaContoCapitale = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionOpzioniSpesaContoCapitale_title(), true, true, true, opzioniSpesaContoCapitaleForm1, opzioniSpesaContoCapitaleForm2);
	}
	
	public LinkedHashMap<String, String> buildModalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap() {
		LinkedHashMap<String, String> modalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap = new LinkedHashMap<String, String>();
		modalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap.put(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B1, I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB1());
		modalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap.put(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B2, I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB2());		
		if(getValueAsBoolean("flgImpegniContoCapitaleGiaRilasciati")) {
			modalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap.put(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B3, I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB3());					
		}
		if(getValueAsBoolean("flgSoloSubImpSubCrono")) {
			modalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap.put(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B4, I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB4());					
		}
		return modalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap;
	}
	
	protected void createOpzioniSpesaContoCapitaleForm() {
		
		opzioniSpesaContoCapitaleForm1 = new DynamicForm();
		opzioniSpesaContoCapitaleForm1.setValuesManager(vm);
		opzioniSpesaContoCapitaleForm1.setWidth100();
		opzioniSpesaContoCapitaleForm1.setPadding(5);
		opzioniSpesaContoCapitaleForm1.setWrapItemTitles(false);
		opzioniSpesaContoCapitaleForm1.setNumCols(20);
		opzioniSpesaContoCapitaleForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		opzioniSpesaContoCapitaleForm1.setTabSet(tabSet);
		opzioniSpesaContoCapitaleForm1.setTabID(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		
		flgDisattivaAutoRequestDatiContabiliSIBContoCapitaleItem = new CheckboxItem("flgDisattivaAutoRequestDatiContabiliSIBContoCapitale", "disattiva caricamento automatico da SIB all'apertura del task");
		flgDisattivaAutoRequestDatiContabiliSIBContoCapitaleItem.setDefaultValue(false);
		flgDisattivaAutoRequestDatiContabiliSIBContoCapitaleItem.setStartRow(true);
		flgDisattivaAutoRequestDatiContabiliSIBContoCapitaleItem.setColSpan(10);
		flgDisattivaAutoRequestDatiContabiliSIBContoCapitaleItem.setWidth("*");		
		
		opzioniSpesaContoCapitaleForm1.setFields(flgDisattivaAutoRequestDatiContabiliSIBContoCapitaleItem);
		
		opzioniSpesaContoCapitaleForm2 = new DynamicForm();
		opzioniSpesaContoCapitaleForm2.setValuesManager(vm);
		opzioniSpesaContoCapitaleForm2.setWidth100();
		opzioniSpesaContoCapitaleForm2.setPadding(5);
		opzioniSpesaContoCapitaleForm2.setWrapItemTitles(false);
		opzioniSpesaContoCapitaleForm2.setNumCols(20);
		opzioniSpesaContoCapitaleForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		opzioniSpesaContoCapitaleForm2.setTabSet(tabSet);
		opzioniSpesaContoCapitaleForm2.setTabID(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem = new RadioGroupItem("modalitaInvioDatiSpesaARagioneriaContoCapitale", I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_title());
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setStartRow(true);		
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setValueMap(buildModalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap());
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setVertical(false);
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setWrap(false);
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setAttribute("obbligatorio", true);
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setDefaultValue(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B1);
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return showTabDatiSpesaContoCapitale() && isDatiSpesaEditabili();
			}
		}));
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
				showHideSections();
			}
		});
	
		opzioniSpesaContoCapitaleForm2.setFields(modalitaInvioDatiSpesaARagioneriaContoCapitaleItem);	
	}
	
	public boolean showDetailSectionDatiContabiliSIBContoCapitale() {
		return isPresentiDatiContabiliSIBContoCapitale();
	}
	
	protected void createDetailSectionDatiContabiliSIBContoCapitale() {

		createDatiContabiliSIBContoCapitaleForm();
		
		detailSectionDatiContabiliSIBContoCapitale = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionDatiContabiliSIBContoCapitale_title(), true, true, false, datiContabiliSIBContoCapitaleForm);
	}
	
	public void refreshListaDatiContabiliSIBContoCapitale(final ServiceCallback<Record> callback) {
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
		if(isAttivaRequestMovimentiDaAMC()) {
			lNuovaPropostaAtto2DataSource.addParam("flgAttivaRequestMovimentiDaAMC", "true");
		}	
		lNuovaPropostaAtto2DataSource.performCustomOperation("getListaDatiContabiliSIBContoCapitale", getRecordToSave(), new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					listaDatiContabiliSIBContoCapitaleItem.setData(record.getAttributeAsRecordList("listaDatiContabiliSIBContoCapitale"));
					if(record.getAttribute("errorMessageDatiContabiliSIBContoCapitale") != null && !"".equals(record.getAttribute("errorMessageDatiContabiliSIBContoCapitale"))) {						
						listaDatiContabiliSIBContoCapitaleItem.setGridEmptyMessage(record.getAttribute("errorMessageDatiContabiliSIBContoCapitale"));
					} else {
						listaDatiContabiliSIBContoCapitaleItem.setGridEmptyMessage(I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabiliSIB_emptyMessage());						
					}
					redrawTabForms(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
					if(callback != null) {
						callback.execute(record);
					}					
				} 				
			}
		}, new DSRequest());
	}
	
	protected void createDatiContabiliSIBContoCapitaleForm() {
		
		datiContabiliSIBContoCapitaleForm = new DynamicForm();
		datiContabiliSIBContoCapitaleForm.setValuesManager(vm);
		datiContabiliSIBContoCapitaleForm.setWidth100();
		datiContabiliSIBContoCapitaleForm.setPadding(5);
		datiContabiliSIBContoCapitaleForm.setWrapItemTitles(false);
		datiContabiliSIBContoCapitaleForm.setNumCols(20);
		datiContabiliSIBContoCapitaleForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		datiContabiliSIBContoCapitaleForm.setTabSet(tabSet);
		datiContabiliSIBContoCapitaleForm.setTabID(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		
		listaDatiContabiliSIBContoCapitaleItem = new ListaDatiContabiliSIBItem("listaDatiContabiliSIBContoCapitale") {
			
			@Override
			public boolean isShowDatiStoriciButton() {
				return isModalitaGrigliaContoCapitale() && isPresentiDatiContabiliSIBContoCapitale() && isPresentiDatiStoriciContoCapitale();
			}
			
			public void onClickDatiStoriciButton() {
				DatiContabiliStoriciWindow lDatiContabiliStoriciWindow = new DatiContabiliStoriciWindow("datiContabiliStoriciWindow", invioDatiSpesaContoCapitaleForm.getValueAsRecordList("listaInvioDatiSpesaContoCapitale")) {
					
					@Override
					public String getSIBDataSourceName() {
						return "DatiContabiliSIBDataSource";
					}
				};
				lDatiContabiliStoriciWindow.show();
			}
			
			public void onClickRefreshListButton() {
				refreshListaDatiContabiliSIBContoCapitale(new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						showHideSections();
						afterShow();
						controllaTotali(false);
					}
				});
			}
		};
		listaDatiContabiliSIBContoCapitaleItem.setGridEmptyMessage(I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabiliSIB_emptyMessage());
		listaDatiContabiliSIBContoCapitaleItem.setStartRow(true);
		listaDatiContabiliSIBContoCapitaleItem.setShowTitle(false);
		listaDatiContabiliSIBContoCapitaleItem.setHeight(245);		
		listaDatiContabiliSIBContoCapitaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showDetailSectionDatiContabiliSIBContoCapitale();
			}
		});
		
		datiContabiliSIBContoCapitaleForm.setFields(listaDatiContabiliSIBContoCapitaleItem);	
	}
	
	public boolean showDetailSectionInvioDatiSpesaContoCapitale() {
		return isModalitaGrigliaContoCapitale() && !isPresentiDatiContabiliSIBContoCapitale();
	}
	
	protected void createDetailSectionInvioDatiSpesaContoCapitale() {
		
		createInvioDatiSpesaContoCapitaleForm();
		
		detailSectionInvioDatiSpesaContoCapitale = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionInvioDatiSpesaContoCapitale_title(), true, true, false, invioDatiSpesaContoCapitaleForm);
	}
	
	protected void createInvioDatiSpesaContoCapitaleForm() {
		
		invioDatiSpesaContoCapitaleForm = new DynamicForm();
		invioDatiSpesaContoCapitaleForm.setValuesManager(vm);
		invioDatiSpesaContoCapitaleForm.setWidth100();
		invioDatiSpesaContoCapitaleForm.setPadding(5);
		invioDatiSpesaContoCapitaleForm.setWrapItemTitles(false);
		invioDatiSpesaContoCapitaleForm.setNumCols(20);
		invioDatiSpesaContoCapitaleForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		invioDatiSpesaContoCapitaleForm.setTabSet(tabSet);
		invioDatiSpesaContoCapitaleForm.setTabID(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		
		listaInvioDatiSpesaContoCapitaleItem = new ListaInvioDatiSpesaItem("listaInvioDatiSpesaContoCapitale") {
			
			@Override
			public String[] getCIGValueMap() {				
				return buildCIGValueMap();
			}
			
			@Override
			public HashSet<String> getVociPEGNoVerifDisp() {
				return vociPEGNoVerifDisp;
			}
			
			@Override
			public String getSIBDataSourceName() {
				return "DatiContabiliSIBDataSource";
			}
			
			@Override
			public boolean isGrigliaEditabile() {
				return true;
			}
			
			public void onClickRefreshListButton() {
				refreshListaDatiContabiliSIBContoCapitale(new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						showHideSections();
						afterShow();
						controllaTotali(false);
					}
				});
			}
		};
		listaInvioDatiSpesaContoCapitaleItem.setStartRow(true);
		listaInvioDatiSpesaContoCapitaleItem.setShowTitle(false);
		listaInvioDatiSpesaContoCapitaleItem.setHeight(245);		
		listaInvioDatiSpesaContoCapitaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDetailSectionInvioDatiSpesaContoCapitale();
			}
		});
		
		invioDatiSpesaContoCapitaleForm.setFields(listaInvioDatiSpesaContoCapitaleItem);	
	}
	
	public boolean showDetailSectionFileXlsContoCapitale() {
		return getValueAsString("modalitaInvioDatiSpesaARagioneriaContoCapitale").equals(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B2);
	}
	
	protected void createDetailSectionFileXlsContoCapitale() {
		
		createFileXlsContoCapitaleForm();
		
		detailSectionFileXlsContoCapitale = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionFileXlsContoCapitale_title(), true, true, true, fileXlsContoCapitaleForm);
	}
	
	protected void createFileXlsContoCapitaleForm() {
		
		fileXlsContoCapitaleForm = new DynamicForm();
		fileXlsContoCapitaleForm.setValuesManager(vm);
		fileXlsContoCapitaleForm.setWidth100();
		fileXlsContoCapitaleForm.setPadding(5);
		fileXlsContoCapitaleForm.setWrapItemTitles(false);
		fileXlsContoCapitaleForm.setNumCols(20);
		fileXlsContoCapitaleForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		fileXlsContoCapitaleForm.setTabSet(tabSet);
		fileXlsContoCapitaleForm.setTabID(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		
		fileXlsContoCapitaleItem = new DocumentItem() {
			
			@Override
			public int getWidth() {
				return 250;
			}
			
			@Override
			public boolean showVisualizzaVersioniMenuItem() {
				return false;
			}
			
			@Override
			public boolean showAcquisisciDaScannerMenuItem() {
				return false;
			}
			
			@Override
			public boolean showFirmaMenuItem() {
				return false;
			}		
			
			@Override
			public boolean isFormatoAmmesso(InfoFileRecord info) {	
				String correctName = info != null ? info.getCorrectFileName() : "";
				return correctName.toLowerCase().endsWith(".xls") || correctName.toLowerCase().endsWith(".xlsx");
			}
		};
		fileXlsContoCapitaleItem.setStartRow(true);
		fileXlsContoCapitaleItem.setName("fileXlsContoCapitale");
		fileXlsContoCapitaleItem.setShowTitle(false);
		fileXlsContoCapitaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDetailSectionFileXlsContoCapitale();
			}
		});
		/*
		fileXlsContoCapitaleItem.setAttribute("obbligatorio", true);
		fileXlsContoCapitaleItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return showTabDatiSpesaContoCapitale() && showDetailSectionFileXlsContoCapitale() && isDatiSpesaEditabili() && isEsitoTaskSelezionatoOk();
			}
		}));
		*/
		
		nomeFileTracciatoXlsContoCapitaleItem = new HiddenItem("nomeFileTracciatoXlsContoCapitale");
		uriFileTracciatoXlsContoCapitaleItem = new HiddenItem("uriFileTracciatoXlsContoCapitale");
		
		scaricaTracciatoXlsContoCapitaleButton = new ImgButtonItem("scaricaTracciatoXlsContoCapitaleButton", "file/download_manager.png", "Scarica tracciato");
		scaricaTracciatoXlsContoCapitaleButton.setAlwaysEnabled(true);
		scaricaTracciatoXlsContoCapitaleButton.setColSpan(1);
		scaricaTracciatoXlsContoCapitaleButton.setIconWidth(16);
		scaricaTracciatoXlsContoCapitaleButton.setIconHeight(16);
		scaricaTracciatoXlsContoCapitaleButton.setIconVAlign(VerticalAlignment.BOTTOM);
		scaricaTracciatoXlsContoCapitaleButton.setAlign(Alignment.LEFT);
		scaricaTracciatoXlsContoCapitaleButton.setWidth(16);
		scaricaTracciatoXlsContoCapitaleButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				if(uriFileTracciatoXlsContoCapitaleItem.getValue() != null && !"".equals((String) uriFileTracciatoXlsContoCapitaleItem.getValue())) {
					Record lRecord = new Record();
					if(nomeFileTracciatoXlsContoCapitaleItem.getValue() != null && !"".equals((String) nomeFileTracciatoXlsContoCapitaleItem.getValue())) {
						lRecord.setAttribute("displayFilename", nomeFileTracciatoXlsContoCapitaleItem.getValue());
					} else {
						lRecord.setAttribute("displayFilename", "Tracciato_SIB.xls");
					}
					lRecord.setAttribute("uri", (String) uriFileTracciatoXlsContoCapitaleItem.getValue());
					lRecord.setAttribute("sbustato", "false");
					lRecord.setAttribute("remoteUri", true);
					DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
				} else {
					AurigaLayout.addMessage(new MessageBean("Nessun tracciato disponibile", "", MessageType.ERROR));
				}
			}
		});
		scaricaTracciatoXlsContoCapitaleButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDetailSectionFileXlsContoCapitale();
			}
		});
		
		fileXlsContoCapitaleForm.setFields(fileXlsContoCapitaleItem, nomeFileTracciatoXlsContoCapitaleItem, uriFileTracciatoXlsContoCapitaleItem, scaricaTracciatoXlsContoCapitaleButton);	
	}
	
	
	protected void createDetailSectionAllegatiContoCapitale() {
		
		createAllegatiContoCapitaleForm();
		
		detailSectionAllegatiContoCapitale = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionAllegatiContoCapitale_title(), true, false, false, allegatiContoCapitaleForm);
	}
	
	protected void createAllegatiContoCapitaleForm() {
		
		allegatiContoCapitaleForm = new DynamicForm();
		allegatiContoCapitaleForm.setValuesManager(vm);
		allegatiContoCapitaleForm.setWidth100();
		allegatiContoCapitaleForm.setPadding(5);
		allegatiContoCapitaleForm.setWrapItemTitles(false);
		allegatiContoCapitaleForm.setNumCols(20);
		allegatiContoCapitaleForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		allegatiContoCapitaleForm.setTabSet(tabSet);
		allegatiContoCapitaleForm.setTabID(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		
		listaAllegatiContoCapitaleItem = new AllegatiItem() {

			@Override
			public String getIdProcess() {
				return getIdProcessTask();
			}

			@Override
			public String getIdProcessType() {
				return getIdProcessTypeTask();
			}

			@Override
			public String getIdTaskCorrenteAllegati() {
				return getIdTaskCorrente();
			}

			@Override
			public HashSet<String> getTipiModelliAttiAllegati() {
				return getTipiModelliAttiInAllegati();
			}
			
			@Override
			public boolean isObbligatorioFile() {
				return isObbligatorioFileInAllegati();
			}

			@Override
			public boolean showGeneraDaModello() {
				return true;
			}

			@Override
			public Record getRecordCaricaModelloAllegato(String idModello, String tipoModello) {
				return getRecordCaricaModelloInAllegati(idModello, tipoModello);
			}
			
			@Override
			public String getTitleFlgParteDispositivo() {
				return I18NUtil.getMessages().nuovaPropostaAtto2_detail_allegati_flgParteIntegrante_title();
			}
			
			@Override
			public Integer getWidthDescrizioneFileAllegato() {
				return 250;
			}
			
			@Override
			public Integer getWidthNomeFileAllegato() {
				return 250;
			}
			
			@Override
			public boolean getShowVersioneOmissis() {
				return true;
			}
		};
		listaAllegatiContoCapitaleItem.setName("listaAllegatiContoCapitale");
		listaAllegatiContoCapitaleItem.setShowTitle(false);
		listaAllegatiContoCapitaleItem.setShowFlgParteDispositivo(true);
		listaAllegatiContoCapitaleItem.setShowFlgNoPubblAllegato(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ESCL_PUBBL_FILE_IN_ATTI"));
		listaAllegatiContoCapitaleItem.setColSpan(20);
		
		allegatiContoCapitaleForm.setFields(listaAllegatiContoCapitaleItem);				
	}
	
	protected void createDetailSectionNoteContoCapitale() {
		
		createNoteContoCapitaleForm();
		
		detailSectionNoteContoCapitale = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionNoteContoCapitale_title(), true, true, false, noteContoCapitaleForm);
	}
	
	protected void createNoteContoCapitaleForm() {
		
		noteContoCapitaleForm = new DynamicForm();
		noteContoCapitaleForm.setValuesManager(vm);
		noteContoCapitaleForm.setWidth100();
		noteContoCapitaleForm.setPadding(5);
		noteContoCapitaleForm.setWrapItemTitles(false);
		noteContoCapitaleForm.setNumCols(20);
		noteContoCapitaleForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		noteContoCapitaleForm.setTabSet(tabSet);
		noteContoCapitaleForm.setTabID(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		
		noteContoCapitaleItem = new CKEditorItem("noteContoCapitale");
		noteContoCapitaleItem.setShowTitle(false);
		noteContoCapitaleItem.setColSpan(20);
		noteContoCapitaleItem.setWidth("100%");
				
		noteContoCapitaleForm.setFields(noteContoCapitaleItem);			
	}
	
	/**
	 * Metodo per costruire il tab "Dati spesa personale"
	 * 
	 */
//	protected void createTabDatiSpesaPersonale() {
//
//		tabDatiSpesaPersonale = new Tab("<b>" + I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiSpesaPersonale_prompt() + "</b>");
//		tabDatiSpesaPersonale.setAttribute("tabID", _TAB_DATI_SPESA_PERSONALE_ID);
//		tabDatiSpesaPersonale.setPrompt(I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiSpesaPersonale_prompt());
//		tabDatiSpesaPersonale.setPane(createTabPane(getLayoutDatiSpesaPersonale()));
//	}
	
	/**
	 * Metodo che restituisce il layout del tab "Dati spesa personale"
	 * 
	 */
//	protected VLayout getLayoutDatiSpesaPersonale() {
//		
//		VLayout layoutDatiSpesaPersonale = new VLayout(5);
//
//		createDetailSectionSpesaAnnoCorrenteESuccessivi();
//		layoutDatiSpesaPersonale.addMember(detailSectionSpesaAnnoCorrenteESuccessivi);
//		
//		createDetailSectionSpesaAnnua();
//		layoutDatiSpesaPersonale.addMember(detailSectionSpesaAnnua);
//		
//		return layoutDatiSpesaPersonale;
//	}
	
//	protected void createDetailSectionSpesaAnnoCorrenteESuccessivi() {
//		
//		createSpesaAnnoCorrenteESuccessiviForm();
//		
//		detailSectionSpesaAnnoCorrenteESuccessivi = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionSpesaAnnoCorrenteESuccessivi_title(), true, true, false, spesaAnnoCorrenteESuccessiviForm);
//	}
	
//	protected void createSpesaAnnoCorrenteESuccessiviForm() {
//		
//		spesaAnnoCorrenteESuccessiviForm = new DynamicForm();
//		spesaAnnoCorrenteESuccessiviForm.setValuesManager(vm);
//		spesaAnnoCorrenteESuccessiviForm.setWidth100();
//		spesaAnnoCorrenteESuccessiviForm.setPadding(5);
//		spesaAnnoCorrenteESuccessiviForm.setWrapItemTitles(false);
//		spesaAnnoCorrenteESuccessiviForm.setNumCols(20);
//		spesaAnnoCorrenteESuccessiviForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
//		spesaAnnoCorrenteESuccessiviForm.setTabSet(tabSet);
//		spesaAnnoCorrenteESuccessiviForm.setTabID(_TAB_DATI_SPESA_PERSONALE_ID);
//		
//		...
//				
//		spesaAnnoCorrenteESuccessiviForm.setFields(listaDatiSpesaAnnualiXDetPersonaleItem);			
//	}
	
//	protected void createDetailSectionSpesaAnnua() {
//		
//		createSpesaAnnuaForm();
//		
//		detailSectionSpesaAnnua = new NuovaPropostaAtto2DetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionSpesaAnnua_title(), true, true, false, spesaAnnuaForm);
//	}
	
//	protected void createSpesaAnnuaForm() {
//		
//		spesaAnnuaForm = new DynamicForm();
//		spesaAnnuaForm.setValuesManager(vm);
//		spesaAnnuaForm.setWidth100();
//		spesaAnnuaForm.setPadding(5);
//		spesaAnnuaForm.setWrapItemTitles(false);
//		spesaAnnuaForm.setNumCols(20);
//		spesaAnnuaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
//		spesaAnnuaForm.setTabSet(tabSet);
//		spesaAnnuaForm.setTabID(_TAB_DATI_SPESA_PERSONALE_ID);
//		
//		...
//		
//		spesaAnnuaForm.setFields(noteContoCapitaleItem);			
//	}
	
	public void openIfHasValueSections() {		
		Set<DetailSection> sections = new HashSet<DetailSection>();
		for(DynamicForm form : getTabForms(_TAB_DATI_SCHEDA_ID)) {
			if(form.getDetailSection() != null) {
				sections.add(form.getDetailSection());
			}
		}			
		if(showTabDatiSpesa()) {
			for(DynamicForm form : getTabForms(_TAB_DATI_SPESA_ID)) {
				if(form.getDetailSection() != null) {
					sections.add(form.getDetailSection());
				}
			}
			if(showTabDatiSpesaCorrente()) {
				for(DynamicForm form : getTabForms(_TAB_DATI_SPESA_CORRENTE_ID)) {
					if(form.getDetailSection() != null) {
						sections.add(form.getDetailSection());
					}
				}
			}
			if(showTabDatiSpesaContoCapitale()) {
				for(DynamicForm form : getTabForms(_TAB_DATI_SPESA_CONTO_CAPITALE_ID)) {
					if(form.getDetailSection() != null) {
						sections.add(form.getDetailSection());
					}
				}
			}
//			if(showTabDatiSpesaPersonale()) {
//				for(DynamicForm form : getTabForms(_TAB_DATI_SPESA_PERSONALE_ID)) {
//					if(form.getDetailSection() != null) {
//						sections.add(form.getDetailSection());
//					}
//				}
//			}
			for(DetailSection section : sections) {
				section.openIfhasValue();
			}
		}
	}
	
	@Override
	public void editNewRecord() {
		
		super.editNewRecord();
		
		if(ufficioProponenteItem != null) {
			if (AurigaLayout.getSelezioneUoProponenteAttiValueMap().size() == 1) {
				String key = AurigaLayout.getSelezioneUoProponenteAttiValueMap().keySet().toArray(new String[1])[0];
				String value = AurigaLayout.getSelezioneUoProponenteAttiValueMap().get(key);
				ufficioProponenteItem.setValue((key != null && key.startsWith("UO")) ? key.substring(2) : key);
				if(value != null && !"".equals(value)) {
					codUfficioProponenteItem.setValue(value.substring(0, value.indexOf(" - ")));
					desUfficioProponenteItem.setValue(value.substring(value.indexOf(" - ") + 3));
				}
			}
		}
		
		vociPEGNoVerifDisp = new HashSet<String>();
		
		if(modalitaInvioDatiSpesaARagioneriaContoCapitaleItem != null) {
			modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setValueMap(buildModalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap());
		}
		
		enableDisableTabs();
		showHideSections();
	}
	
	@Override
	public void editNewRecord(Map initialValues) {
		
		manageLoadSelectInEditNewRecord(initialValues, ufficioProponenteItem, "ufficioProponente", new String[] {"codUfficioProponente", "desUfficioProponente"}, " - ", "idSv");
		
		super.editNewRecord(initialValues);
		
		if(ufficioProponenteItem != null) {
			if(ufficioProponenteItem.getValue() == null || "".equals(ufficioProponenteItem.getValue())) {
				if (AurigaLayout.getSelezioneUoProponenteAttiValueMap().size() == 1) {
					String key = AurigaLayout.getSelezioneUoProponenteAttiValueMap().keySet().toArray(new String[1])[0];
					String value = AurigaLayout.getSelezioneUoProponenteAttiValueMap().get(key);
					ufficioProponenteItem.setValue((key != null && key.startsWith("UO")) ? key.substring(2) : key);
					if(value != null && !"".equals(value)) {
						codUfficioProponenteItem.setValue(value.substring(0, value.indexOf(" - ")));
						desUfficioProponenteItem.setValue(value.substring(value.indexOf(" - ") + 3));
					}
				}
			}
		}
		
		vociPEGNoVerifDisp = new HashSet<String>();
		
		if(modalitaInvioDatiSpesaARagioneriaContoCapitaleItem != null) {
			modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setValueMap(buildModalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap());
			modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setValue((String) initialValues.get("modalitaInvioDatiSpesaARagioneriaContoCapitale"));
		}

		if (oggettoHtmlItem != null) {
			oggettoHtmlItem.setValue((String) initialValues.get("oggettoHtml"));
		}
		if(attiPresuppostiItem != null) {
			attiPresuppostiItem.setValue((String) initialValues.get("attiPresupposti"));
		}
		if(motivazioniItem != null) {
			motivazioniItem.setValue((String) initialValues.get("motivazioni"));
		}
		if(dispositivoItem != null) {
			dispositivoItem.setValue((String) initialValues.get("dispositivo"));
		}
		
		enableDisableTabs();
		showHideSections();		
		
//		openIfHasValueSections();
		
		if(detailSectionPropostaConcertoCon != null) {
			detailSectionPropostaConcertoCon.openIfhasValue();
		}
	}
		
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, ufficioProponenteItem, "ufficioProponente", new String[] {"codUfficioProponente", "desUfficioProponente"}, " - ", "idSv");
		
		super.editRecord(record);
		
		vociPEGNoVerifDisp = new HashSet<String>();
		if(record.getAttributeAsRecordList("listaVociPEGNoVerifDisp") != null) {
			for(int i = 0; i < record.getAttributeAsRecordList("listaVociPEGNoVerifDisp").getLength(); i++) {
				vociPEGNoVerifDisp.add(record.getAttributeAsRecordList("listaVociPEGNoVerifDisp").get(i).getAttribute("key"));
			}
		}
		
		if(modalitaInvioDatiSpesaARagioneriaContoCapitaleItem != null) {
			modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setValueMap(buildModalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap());
			modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setValue(record.getAttribute("modalitaInvioDatiSpesaARagioneriaContoCapitale"));
		}
		
		// Quando sono in un task i dati di pubblicazione vengono caricati dalla CallExecAtt (vedi TaskNuovaPropostaAtto2Detail)
		if(dataInizioPubblicazioneItem != null) {
			dataInizioPubblicazioneItem.setValue(getDataInizioPubblicazioneValue());
		}
		if(giorniPubblicazioneItem != null) {
			giorniPubblicazioneItem.setValue(getGiorniPubblicazioneValue());
		}
		
		// Devo settare manualmente i valori dei CKEditor
		if(oggettoHtmlItem != null) {
			oggettoHtmlItem.setValue(record.getAttribute("oggettoHtml"));
		}
		if(attiPresuppostiItem != null) {
			attiPresuppostiItem.setValue(record.getAttribute("attiPresupposti"));
		}
		if(motivazioniItem != null) {
			motivazioniItem.setValue(record.getAttribute("motivazioni"));
		}
		if(dispositivoItem != null) {
			dispositivoItem.setValue(record.getAttribute("dispositivo"));
		}
		if(noteCorrenteItem != null) {
			noteCorrenteItem.setValue(record.getAttribute("noteCorrente"));
		}
		if(noteContoCapitaleItem != null) {
			noteContoCapitaleItem.setValue(record.getAttribute("noteContoCapitale"));
		}
		
		enableDisableTabs();
		showHideSections();
		
		if(listaDatiContabiliSIBCorrenteItem != null) {
			if(record.getAttribute("errorMessageDatiContabiliSIBCorrente") != null && !"".equals(record.getAttribute("errorMessageDatiContabiliSIBCorrente"))) {
				listaDatiContabiliSIBCorrenteItem.setGridEmptyMessage(record.getAttribute("errorMessageDatiContabiliSIBCorrente"));
			} else {
				listaDatiContabiliSIBCorrenteItem.setGridEmptyMessage(I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabiliSIB_emptyMessage());
			}
		}
		
		if(listaDatiContabiliSIBContoCapitaleItem != null) {
			if(record.getAttribute("errorMessageDatiContabiliSIBContoCapitale") != null && !"".equals(record.getAttribute("errorMessageDatiContabiliSIBContoCapitale"))) {						
				listaDatiContabiliSIBContoCapitaleItem.setGridEmptyMessage(record.getAttribute("errorMessageDatiContabiliSIBContoCapitale"));
			} else {
				listaDatiContabiliSIBContoCapitaleItem.setGridEmptyMessage(I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabiliSIB_emptyMessage());						
			}
		}
		
//		openIfHasValueSections();
		
		if(detailSectionPropostaConcertoCon != null) {
			detailSectionPropostaConcertoCon.openIfhasValue();
		}	
		if(detailSectionAllegati != null) {
			detailSectionAllegati.openIfhasValue();
		}
		if(detailSectionAllegatiCorrente != null) {
			detailSectionAllegatiCorrente.openIfhasValue();
		}
		if(detailSectionAllegatiContoCapitale != null) {
			detailSectionAllegatiContoCapitale.openIfhasValue();
		}
	}
	
	/**
	 * Metodo che abilita/disabilita i tab
	 * 
	 */
	public void enableDisableTabs() {
		
		if(tabDatiSpesa != null) {
			if(showTabDatiSpesa()) {			
				tabSet.enableTab(tabDatiSpesa);			
			} else {
				tabSet.disableTab(tabDatiSpesa);			
			}
		}
		if(!isAvvioPropostaAtto()) {
			if(tabDatiSpesaCorrente != null) {
				if(showTabDatiSpesaCorrente()) {
					tabSet.enableTab(tabDatiSpesaCorrente);			
				} else {
					tabSet.disableTab(tabDatiSpesaCorrente);							
				}
			}
			if(tabDatiSpesaContoCapitale != null) {
				if(showTabDatiSpesaContoCapitale()) {
					tabSet.enableTab(tabDatiSpesaContoCapitale);
				} else {
					tabSet.disableTab(tabDatiSpesaContoCapitale);
				}
			}
//			if(tabDatiSpesaPersonale != null) {
//				if(showTabDatiSpesaPersonale()) {
//					tabSet.enableTab(tabDatiSpesaPersonale);
//				} else {
//					tabSet.disableTab(tabDatiSpesaPersonale);
//				}
//			}
		}
	}
	
	/**
	 * Metodo che mostra/nasconde le sezioni
	 * 
	 */
	public void showHideSections() {
		
		if(detailSectionRegistrazione != null) {			
			if(showDetailSectionRegistrazione()) {
				detailSectionRegistrazione.show();
			} else {
				detailSectionRegistrazione.hide();	
			}
		}
		
		if(detailSectionPubblicazione != null) {			
			if(showDetailSectionPubblicazione()) {
				detailSectionPubblicazione.show();
			} else {
				detailSectionPubblicazione.hide();	
			}
		}
		
		if(detailSectionPropostaConcertoCon != null) {
			if(showDetailSectionPropostaConcertoCon()) {				
				detailSectionPropostaConcertoCon.show();
			} else {
				detailSectionPropostaConcertoCon.hide();	
			}
		}
		
		if(detailSectionEstensori != null) {
			if(showDetailSectionEstensori()) {				
				detailSectionEstensori.show();
			} else {
				detailSectionEstensori.hide();	
			}
		}
		
		if(detailSectionCIG != null) {
			if(showDetailSectionCIG()) {				
				detailSectionCIG.show();
			} else {
				detailSectionCIG.hide();	
			}
		}
		
		if(detailSectionOpzioniSpesa != null) {
			if(showDetailSectionOpzioniSpesa()) {				
				detailSectionOpzioniSpesa.show();
			} else {
				detailSectionOpzioniSpesa.hide();	
			}
		}
		
		if(detailSectionDatiContabiliSIBCorrente != null) {
			if(showDetailSectionDatiContabiliSIBCorrente()) {				
				detailSectionDatiContabiliSIBCorrente.show();
			} else {
				detailSectionDatiContabiliSIBCorrente.hide();	
			}
		}
		
		if(detailSectionInvioDatiSpesaCorrente != null) {			
			if(showDetailSectionInvioDatiSpesaCorrente()) {				
				detailSectionInvioDatiSpesaCorrente.show();
			} else {
				detailSectionInvioDatiSpesaCorrente.hide();	
			}
		}
		
		if(detailSectionFileXlsCorrente != null) {			
			if(showDetailSectionFileXlsCorrente()) {								
				detailSectionFileXlsCorrente.show();
			} else {
				detailSectionFileXlsCorrente.hide();	
			}
		}
		
		if(detailSectionDatiContabiliSIBContoCapitale != null) {
			if(showDetailSectionDatiContabiliSIBContoCapitale()) {
				detailSectionDatiContabiliSIBContoCapitale.show();
			} else {
				detailSectionDatiContabiliSIBContoCapitale.hide();	
			}
		}
		
		if(detailSectionInvioDatiSpesaContoCapitale != null) {
			if(showDetailSectionInvioDatiSpesaContoCapitale()) {
				detailSectionInvioDatiSpesaContoCapitale.show();
			} else {
				detailSectionInvioDatiSpesaContoCapitale.hide();	
			}
		}
		
		if(detailSectionFileXlsContoCapitale != null) {
			if(showDetailSectionFileXlsContoCapitale()) {
				detailSectionFileXlsContoCapitale.show();
			} else {
				detailSectionFileXlsContoCapitale.hide();	
			}
		}
	}
	
	public DynamicForm[] getTabForms(String tabID) {
		
		ArrayList<DynamicForm> listaTabForms = new ArrayList<DynamicForm>();
		for(DynamicForm form : vm.getMembers()) {
			if(form.getTabID() != null && form.getTabID().equalsIgnoreCase(tabID)) {
				listaTabForms.add(form);
			}
		}
		return listaTabForms.toArray(new DynamicForm[listaTabForms.size()]);
	}
	
	public void addTabValues(Record record, String tabID) {
		
		for(DynamicForm form : getTabForms(tabID)) {
			addFormValues(record, form);
		}
	}
	
	public void redrawTabForms(String tabID) {
		
		for(DynamicForm form : getTabForms(tabID)) {
			form.markForRedraw();
		}
	}
	
	public Record getRecordToSave() {
		
		final Record lRecordToSave = new Record();
		
		// Dati scheda
		addTabValues(lRecordToSave, _TAB_DATI_SCHEDA_ID);
		
		if(getValueAsDate("dataInizioPubblicazione") == null) {
			lRecordToSave.setAttribute("dataInizioPubblicazione", getDataInizioPubblicazioneValue());
		}
		if("".equals(getValueAsString("giorniPubblicazione"))) {
			lRecordToSave.setAttribute("giorniPubblicazione", getGiorniPubblicazioneValue());
		}
		
		if(isAbilToSelUffPropEsteso()) {
			RecordList listaUfficioProponente = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaUfficioProponente") : null;
			if(listaUfficioProponente != null && listaUfficioProponente.getLength() > 0) {
				lRecordToSave.setAttribute("ufficioProponente", listaUfficioProponente.get(0).getAttribute("idUo"));		
				lRecordToSave.setAttribute("codUfficioProponente", listaUfficioProponente.get(0).getAttribute("codRapido"));
				lRecordToSave.setAttribute("desUfficioProponente", listaUfficioProponente.get(0).getAttribute("descrizione"));
			}
		}
		
		// Setto i valori dei CKEditor
		lRecordToSave.setAttribute("oggettoHtml", oggettoHtmlItem != null ? oggettoHtmlItem.getValue() : null);
		lRecordToSave.setAttribute("attiPresupposti", attiPresuppostiItem != null ? attiPresuppostiItem.getValue() : null);
		lRecordToSave.setAttribute("motivazioni", motivazioniItem != null ? motivazioniItem.getValue() : null);
		lRecordToSave.setAttribute("dispositivo", dispositivoItem != null ? dispositivoItem.getValue() : null);		
		
		// Dati di spesa
		if(showTabDatiSpesa()) {
			addTabValues(lRecordToSave, _TAB_DATI_SPESA_ID);
			RecordList listaUfficioDefinizioneSpesa = ruoliSpesaForm2 != null ? ruoliSpesaForm2.getValueAsRecordList("listaUfficioDefinizioneSpesa") : null;
			if(listaUfficioDefinizioneSpesa != null && listaUfficioDefinizioneSpesa.getLength() > 0) {
				lRecordToSave.setAttribute("ufficioDefinizioneSpesa", listaUfficioDefinizioneSpesa.get(0).getAttribute("idUo"));				
				lRecordToSave.setAttribute("codUfficioDefinizioneSpesa", listaUfficioDefinizioneSpesa.get(0).getAttribute("codRapido"));
				lRecordToSave.setAttribute("desUfficioDefinizioneSpesa", listaUfficioDefinizioneSpesa.get(0).getAttribute("descrizione"));				
			}
			if(showTabDatiSpesaCorrente()) {
				addTabValues(lRecordToSave, _TAB_DATI_SPESA_CORRENTE_ID);				
				lRecordToSave.setAttribute("noteCorrente", noteCorrenteItem != null ? noteCorrenteItem.getValue() : null);
			}
			if(showTabDatiSpesaContoCapitale()) {
				addTabValues(lRecordToSave, _TAB_DATI_SPESA_CONTO_CAPITALE_ID);	
				lRecordToSave.setAttribute("noteContoCapitale", noteContoCapitaleItem != null ? noteContoCapitaleItem.getValue() : null);
			}
//			if(showTabDatiSpesaPersonale()) {
//				addTabValues(lRecordToSave, _TAB_DATI_SPESA_PERSONALE_ID);
//			}
		}
				
		// Attributi dinamici doc
		if (attributiAddDocDetails != null) {
			lRecordToSave.setAttribute("rowidDoc", rowidDoc);
			lRecordToSave.setAttribute("valori", getAttributiDinamiciDoc());
			lRecordToSave.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
			lRecordToSave.setAttribute("colonneListe", getColonneListeAttributiDinamiciDoc());
		}
		
		lRecordToSave.setAttribute("flgDatiSensibili", hasDatiSensibili());
		
		return lRecordToSave;
	}
	
	public boolean isTaskDetail() {
		return instance instanceof TaskFlussoInterface;
	}
	
	public String getIdUoProponente() {
		
		if(isAbilToSelUffPropEsteso()) {
			RecordList listaUfficioProponente = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaUfficioProponente") : null;
			if(listaUfficioProponente != null && listaUfficioProponente.getLength() > 0) {
				return listaUfficioProponente.get(0).getAttribute("idUo");
			}
		} else {
			return getValueAsString("ufficioProponente");
		}
		return null;
	}
	
	protected String getIdUd() {
		return getValueAsString("idUd");
	}
	
	protected String getIdUdAttoDaAnnullare() {
		return null;
	}
	
	protected String getIdProcessTask() {
		return null;
	}

	protected String getIdProcessTypeTask() {
		return null;
	}
	
	protected String getIdTaskCorrente() {
		return null;
	}
	
	/**
	 * Metodo che restituisce la mappa dei modelli relativi agli atti associati
	 * al task, da passare alla sezione degli allegati
	 * 
	 */
	public HashSet<String> getTipiModelliAttiInAllegati() {
		return null;
	}
	
	public boolean isObbligatorioFileInAllegati() {
		return true;
	}
	
	/**
	 * Metodo che costruisce il record per la chiamata al servizio che genera
	 * l'allegato da modello iniettando i valori presenti in maschera
	 * 
	 */
	public Record getRecordCaricaModelloInAllegati(String idModello, String tipoModello) {
		
		final Record modelloRecord = new Record();
		modelloRecord.setAttribute("idModello", idModello);
		modelloRecord.setAttribute("tipoModello", tipoModello);
		modelloRecord.setAttribute("idUd", getIdUd());
		if (attributiAddDocDetails != null) {
			modelloRecord.setAttribute("valori", getAttributiDinamiciDoc());
			modelloRecord.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
			modelloRecord.setAttribute("colonneListe", getColonneListeAttributiDinamiciDoc());
		}		
		return modelloRecord;
	}
		
	public boolean hasPrimarioDatiSensibili() {
		for (DynamicForm form : vm.getMembers()) {
			for (FormItem item : form.getFields()) {
				if (item != null && item instanceof IDatiSensibiliItem) {
					if((item instanceof AllegatiItem) || (item instanceof AllegatiGridItem)) {
						// per vedere se ho dati sensibili non considero gli allegati ma solo il file priamrio
						continue;
					} else if(((IDatiSensibiliItem)item).hasDatiSensibili()) {
						return true;
					}
				}
			}
		}
//		if (attributiAddDocTabs != null) {
//			for (String key : attributiAddDocTabs.keySet()) {
//				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null && attributiAddDocDetails.get(key).hasDatiSensibili()) {
//					return true;
//				}
//			}
//		}
		return false;		 		
	}
	
	@Override
	public boolean hasDatiSensibili() {
		if(super.hasDatiSensibili()) {
			return true;
		}		
//		if (attributiAddDocTabs != null) {
//			for (String key : attributiAddDocTabs.keySet()) {
//				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null && attributiAddDocDetails.get(key).hasDatiSensibili()) {
//					return true;
//				}
//			}
//		}
		return false;		 		
	}
	
	public String getValueAsString(String fieldName) {
		return vm.getValue(fieldName) != null ? (String) vm.getValue(fieldName) : "";
	}
	
	public Date getValueAsDate(String fieldName) {
		return vm.getValue(fieldName) != null ? (Date) vm.getValue(fieldName) : null;
	}
	
	public boolean getValueAsBoolean(String fieldName) {
		return vm.getValue(fieldName) != null ? (Boolean) vm.getValue(fieldName) : false;
	}
	
	@Override
	public boolean customValidate() {
		
		boolean valid = super.customValidate();
		
		// Faccio la validazione dei CKEditor obbligatori
		if(oggettoHtmlItem != null) {
			valid = oggettoHtmlItem.validate() && valid;
		}
		if(!isAvvioPropostaAtto()) {
			if(attiPresuppostiItem != null) {
				valid = attiPresuppostiItem.validate() && valid;
			}
			if(motivazioniItem != null) {
				valid = motivazioniItem.validate() && valid;
			}
			if(dispositivoItem != null) {
				valid = dispositivoItem.validate() && valid;
			}
		}
		
		if(showDetailSectionCIG() && listaCIGItem != null && listaCIGItem.getEditing()) {
			HashSet<String> setCodiciCIG = new HashSet<String>();
			RecordList listaCIG = CIGForm.getValueAsRecordList("listaCIG");
			if(listaCIG != null) {
				for(int i=0; i < listaCIG.getLength(); i++) {
					if(listaCIG.get(i).getAttribute("codiceCIG") != null &&	!"".equals(listaCIG.get(i).getAttribute("codiceCIG"))) {
						if(setCodiciCIG.contains(listaCIG.get(i).getAttribute("codiceCIG"))) {
							CIGForm.setFieldErrors("listaCIG", "Uno o più CIG risultano uguali");
							valid = false;
							break;
						} else {
							setCodiciCIG.add(listaCIG.get(i).getAttribute("codiceCIG"));
						}
					}
				}				
			}
		}		 			
				
//		if (filePrimarioForm != null) {
//			if (filePrimarioForm.getValue("uriFilePrimario") != null
//					&& !"".equals(filePrimarioForm.getValue("uriFilePrimario"))) {
//				InfoFileRecord infoFilePrimario = filePrimarioForm.getValue("infoFile") != null
//						? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
//				if (infoFilePrimario == null || infoFilePrimario.getMimetype() == null || infoFilePrimario.getMimetype().equals("")) {
//					Map<String, String> errors = new HashMap<String, String>();
//					errors.put("nomeFilePrimario", "Formato file non riconosciuto");
//					filePrimarioForm.setErrors(errors);
//					valid = false;
//				}
//				if (infoFilePrimario.getBytes() <= 0) {
//					Map<String, String> errors = new HashMap<String, String>();
//					errors.put("nomeFilePrimario", "Il file ha dimensione 0");
//					filePrimarioForm.setErrors(errors);
//					valid = false;
//				}
//			}
//		}
		
		if (attributiAddDocDetails != null) {
			for (String key : attributiAddDocDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddDocDetails.get(key);
				if(!detail.customValidate()) {
					valid = false;
				}
				for (DynamicForm form : detail.getForms()) {
					form.clearErrors(true);
					valid = form.validate() && valid;
					for (FormItem item : form.getFields()) {
						if (item instanceof ReplicableItem) {
							ReplicableItem lReplicableItem = (ReplicableItem) item;
							boolean itemValid = lReplicableItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lReplicableItem != null && lReplicableItem.getForm() != null && lReplicableItem.getForm().getDetailSection() != null) {
									lReplicableItem.getForm().getDetailSection().open();
								}
							}
						} else if (item instanceof IDocumentItem) {
							IDocumentItem lIDocumentItem = (IDocumentItem) item;
							boolean itemValid = lIDocumentItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lIDocumentItem != null && lIDocumentItem.getForm() != null && lIDocumentItem.getForm().getDetailSection() != null) {
									lIDocumentItem.getForm().getDetailSection().open();
								}
							}
						} else if (item instanceof CKEditorItem) {
							CKEditorItem lCKEditorItem = (CKEditorItem) item;
							boolean itemValid = lCKEditorItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lCKEditorItem != null && lCKEditorItem.getForm() != null && lCKEditorItem.getForm().getDetailSection() != null) {
									lCKEditorItem.getForm().getDetailSection().open();
								}
							}
						} else {
							boolean itemValid = item.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(item != null && item.getForm() != null && item.getForm().getDetailSection() != null) {
									item.getForm().getDetailSection().open();
								}
							}
						}
					}
				}
			}
		}
		return valid;
	}
	
	public void salvaAttributiDinamiciDoc(boolean flgIgnoreObblig, String rowidDoc, String activityNameWF, String esitoActivityWF, final ServiceCallback<Record> callback) {
		
		if (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0) {
			Record lRecordDoc = new Record();
			lRecordDoc.setAttribute("rowId", rowidDoc);
			lRecordDoc.setAttribute("valori", getAttributiDinamiciDoc());
			lRecordDoc.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
//			lRecordDoc.setAttribute("colonneListe", getColonneListeAttributiDinamiciDoc());
			lRecordDoc.setAttribute("activityNameWF", activityNameWF);
			lRecordDoc.setAttribute("esitoActivityWF", esitoActivityWF);
			GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("AttributiDinamiciDocDatasource");
			if (flgIgnoreObblig) {
				lGWTRestService.addParam("flgIgnoreObblig", "1");
			}
			lGWTRestService.call(lRecordDoc, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					if (callback != null) {
						callback.execute(object);
					}
				}
			});
		} else {
			if (callback != null) {
				callback.execute(new Record());
			}
		}
	}
	
	public void manageAttributiAddSenzaCategoria(RecordList attributiAddSenzaCategoria, Map mappaDettAttrLista, Map mappaValoriAttrLista, Map mappaVariazioniAttrLista) {
		
	}
	
	public void caricaAttributiDinamiciDoc(String nomeFlussoWF, String processNameWF, String activityName, String idTipoDoc, String rowidDoc) {
		
		attributiAddDocLayouts = new HashMap<String, VLayout>();
		attributiAddDocDetails = new HashMap<String, AttributiDinamiciDetail>();
		if (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0) {
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
			lGwtRestService.addParam("nomeFlussoWF", nomeFlussoWF);
			lGwtRestService.addParam("processNameWF", processNameWF);
			lGwtRestService.addParam("activityNameWF", activityName);
			Record lAttributiDinamiciRecord = new Record();
			lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_DOCUMENTS");
			lAttributiDinamiciRecord.setAttribute("rowId", rowidDoc);
			lAttributiDinamiciRecord.setAttribute("tipoEntita", idTipoDoc);
			lGwtRestService.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					ricaricaTabSet();
					RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
					if (attributiAdd != null && !attributiAdd.isEmpty()) {						
						RecordList attributiAddSenzaCategoria = new RecordList();												
						HashMap<String, RecordList> mappaAttributiAddCategoria = new HashMap<String, RecordList>();						
						for (int i = 0; i < attributiAdd.getLength(); i++) {
							Record attr = attributiAdd.get(i);
							if (attr.getAttribute("categoria") != null && !"".equals(attr.getAttribute("categoria"))) {
								if(!mappaAttributiAddCategoria.containsKey(attr.getAttribute("categoria"))) {
									mappaAttributiAddCategoria.put(attr.getAttribute("categoria"), new RecordList());
								} 
								mappaAttributiAddCategoria.get(attr.getAttribute("categoria")).add(attr);
							} else {
								attributiAddSenzaCategoria.add(attr);
							}
						}						
						manageAttributiAddSenzaCategoria(attributiAddSenzaCategoria, object.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object.getAttributeAsMap("mappaVariazioniAttrLista"));						
						for (final String key : attributiAddDocTabs.keySet()) {							
							RecordList attributiAddCategoria = mappaAttributiAddCategoria.get(key) != null ? mappaAttributiAddCategoria.get(key) : new RecordList();							
							if (!attributiAddCategoria.isEmpty()) {
								AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
										.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
										.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null, tabSet, key);
								detail.setCanEdit(new Boolean(isEseguibile()));
								String messaggioTab = getMessaggioTab(key);
								if (messaggioTab != null && !"".equals(messaggioTab)) {
									Label labelMessaggioTab = new Label(messaggioTab);
									labelMessaggioTab.setAlign(Alignment.LEFT);
									labelMessaggioTab.setWidth100();
									labelMessaggioTab.setHeight(2);
									labelMessaggioTab.setPadding(5);
									detail.addMember(labelMessaggioTab, 0);
								}
								attributiAddDocDetails.put(key, detail);
								VLayout layout = new VLayout();
								layout.setHeight100();
								layout.setWidth100();
								layout.setMembers(detail);
								attributiAddDocLayouts.put(key, layout);
								VLayout layoutTab = new VLayout();
								layoutTab.addMember(layout);
								Tab tab = new Tab("<b>" + attributiAddDocTabs.get(key) + "</b>");
								tab.setAttribute("tabID", key);
								tab.setPrompt(attributiAddDocTabs.get(key));
								tab.setPane(layoutTab);
								tabSet.addTab(tab);
							}
						}						
					}
					afterCaricaAttributiDinamiciDoc();
				}
			});
		} else {
			ricaricaTabSet();
			afterCaricaAttributiDinamiciDoc();
		}
	}
	
	protected void ricaricaTabSet() {
		if(isDeterminaPersonale()) {
			if(!isAvvioPropostaAtto() && isAttivoSIB()) {
				tabSet.setTabs(tabDatiScheda, tabDatiSpesa/*, tabDatiSpesaPersonale*/);
			} else {
				tabSet.setTabs(tabDatiScheda, tabDatiSpesa);
			}												
		} else {
			if(!isAvvioPropostaAtto() && isAttivoSIB()) {
				tabSet.setTabs(tabDatiScheda, tabDatiSpesa, tabDatiSpesaCorrente, tabDatiSpesaContoCapitale);
			} else {
				tabSet.setTabs(tabDatiScheda, tabDatiSpesa);
			}
		}		
	}
		
	/**
	 * Metodo che viene richiamato alla fine del caricamento degli attributi
	 * dinamici del documento
	 * 
	 */
	protected void afterCaricaAttributiDinamiciDoc() {
		if (attributiDinamiciDocDaCopiare != null) {
			setAttributiDinamiciDoc(attributiDinamiciDocDaCopiare);
			attributiDinamiciDocDaCopiare = null;
		}
		enableDisableTabs();
	}


	/**
	 * Metodo per settare i valori degli attributi dinamici associati al
	 * documento dopo l'azione del bottone "Nuova protocollazione come copia"
	 * 
	 */
	protected void setAttributiDinamiciDocDaCopiare(Map<String, Object> attributiDinamiciDocDaCopiare) {
		this.attributiDinamiciDocDaCopiare = attributiDinamiciDocDaCopiare;
		if (attributiDinamiciDocDaCopiare != null) {
			setAttributiDinamiciDoc(attributiDinamiciDocDaCopiare);
		}
	}

	/**
	 * Metodo per settare i valori degli attributi dinamici associati al
	 * documento
	 * 
	 */
	protected void setAttributiDinamiciDoc(Map<String, Object> valori) {
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					attributiAddDocDetails.get(key).editNewRecord(valori);
				}
			}
		}
	}

	/**
	 * Metodo per il recupero da maschera dei valori degli attributi dinamici 
	 * associati al documento, nella modalità per il caricamento dei dati a maschera:
	 * gli attributi LISTA hanno i valori interni mappati con i NOMI delle colonne 
	 * 
	 */
	public Map<String, Object> getAttributiDinamiciDocForLoad() {
		Map<String, Object> attributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (attributiDinamiciDoc == null) {
						attributiDinamiciDoc = new HashMap<String, Object>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, 
					// i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					attributiDinamiciDoc.putAll(detailRecord.toMap());
				}
			}
		}
		return attributiDinamiciDoc;
	}
	
	/**
	 * Metodo per il recupero da maschera dei valori degli attributi dinamici
	 * associati al documento, nella modalità per il salvataggio dei dati da maschera:
	 * gli attributi LISTA hanno i valori interni mappati con i NUMERI delle colonne
	 * 
	 */
	public Map<String, Object> getAttributiDinamiciDoc() {
		Map<String, Object> attributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (attributiDinamiciDoc == null) {
						attributiDinamiciDoc = new HashMap<String, Object>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, 
					// i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					attributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaValori(detailRecord));
				}
			}
		}
		return attributiDinamiciDoc;
	}

	/**
	 * Metodo per il recupero da maschera dei tipi degli attributi dinamici
	 * associati al documento
	 * 
	 */
	public Map<String, String> getTipiAttributiDinamiciDoc() {
		Map<String, String> tipiAttributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (tipiAttributiDinamiciDoc == null) {
						tipiAttributiDinamiciDoc = new HashMap<String, String>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal
					// vm, i valori degli attributi lista non li prende
					// correttamente
					// final Record detailRecord = new
					// Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					tipiAttributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaTipiValori(detailRecord));
				}
			}
		}
		return tipiAttributiDinamiciDoc;
	}
	
	/**
	 * Metodo per il recupero da maschera delle mappe delle colonne degli attributi dinamici
	 * di tipo LISTA associati al documento
	 * 
	 */
	public Map<String, String> getColonneListeAttributiDinamiciDoc() {
		Map<String, String> mappaColonneListaAttributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (mappaColonneListaAttributiDinamiciDoc == null) {
						mappaColonneListaAttributiDinamiciDoc = new HashMap<String, String>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal
					// vm, i valori degli attributi lista non li prende
					// correttamente
					// final Record detailRecord = new
					// Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					mappaColonneListaAttributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaColonneListe(detailRecord));
				}
			}
		}
		return mappaColonneListaAttributiDinamiciDoc;
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		
		super.setCanEdit(canEdit);
		
		setCanEdit(false, registrazioneForm); // i campi della sezione "Registrazione" devono essere sempre read-only
				
		if(siglaRegistrazioneItem != null) {
			siglaRegistrazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			siglaRegistrazioneItem.setTabIndex(-1);
		}
		
		if(numeroRegistrazioneItem != null) {
			numeroRegistrazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			numeroRegistrazioneItem.setTabIndex(-1);
		}
		
		if(dataRegistrazioneItem != null) {
			dataRegistrazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			dataRegistrazioneItem.setTabIndex(-1);
		}
		
		if(desUserRegistrazioneItem != null) {
			desUserRegistrazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			desUserRegistrazioneItem.setTabIndex(-1);
		}
		
		if(desUORegistrazioneItem != null) {
			desUORegistrazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			desUORegistrazioneItem.setTabIndex(-1);
		}
		
		if(siglaRegProvvisoriaItem != null) {
			siglaRegProvvisoriaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			siglaRegProvvisoriaItem.setTabIndex(-1);
		}
		
		if(numeroRegProvvisoriaItem != null) {
			numeroRegProvvisoriaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			numeroRegProvvisoriaItem.setTabIndex(-1);
		}
		
		if(dataRegProvvisoriaItem != null) {
			dataRegProvvisoriaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			dataRegProvvisoriaItem.setTabIndex(-1);
		}
		
		if(desUserRegProvvisoriaItem != null) {
			desUserRegProvvisoriaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			desUserRegProvvisoriaItem.setTabIndex(-1);
		}
		
		if(desUORegProvvisoriaItem != null) {
			desUORegProvvisoriaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			desUORegProvvisoriaItem.setTabIndex(-1);
		}

		if (getUfficioProponenteValueMap().size() == 1) {
			if (ufficioProponenteItem != null) {
				ufficioProponenteItem.setCanEdit(false);
				ufficioProponenteItem.setTabIndex(-1);
			}
		}
	}
	
	public void recuperaIdUdAttoDeterminaAContrarre(final ServiceCallback<String> callback) {
		String categoriaReg = categoriaRegAttoDeterminaAContrarreItem.getValueAsString() != null ? categoriaRegAttoDeterminaAContrarreItem.getValueAsString() : "";
		String sigla = siglaAttoDeterminaAContrarreItem.getValueAsString() != null ? siglaAttoDeterminaAContrarreItem.getValueAsString() : "";
		String numero = numeroAttoDeterminaAContrarreItem.getValueAsString() != null ? numeroAttoDeterminaAContrarreItem.getValueAsString() : "";
		String anno = annoAttoDeterminaAContrarreItem.getValueAsString() != null ? annoAttoDeterminaAContrarreItem.getValueAsString() : "";									
		if (("PG".equals(categoriaReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno)) {
			Record lRecord = new Record();			
			lRecord.setAttribute("categoriaRegAttoDeterminaAContrarre", categoriaReg);
			lRecord.setAttribute("siglaAttoDeterminaAContrarre", sigla);
			lRecord.setAttribute("numeroAttoDeterminaAContrarre", numero);
			lRecord.setAttribute("annoAttoDeterminaAContrarre", anno);			
			final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
			lNuovaPropostaAtto2DataSource.performCustomOperation("recuperaIdUdAttoDeterminaAContrarre", getRecordToSave(), new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						if(callback != null) {
							callback.execute(response.getData()[0].getAttributeAsString("idUdAttoDeterminaAContrarre"));
						} 
					} else {
						if(callback != null) {
							callback.execute(null);
						} 
					}
				}
			});
		}		
	}	

	public boolean isDeterminaPersonale() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_DET_PERS_EXTRA_AMC") && getValueAsBoolean("flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro");
	}
			
	public boolean isModalitaGrigliaCorrente() {
		return getValueAsString("prenotazioneSpesaSIBDiCorrente").equals(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_B) && getValueAsString("modalitaInvioDatiSpesaARagioneriaCorrente").equals(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B1);
	}
	
	public boolean isPresentiDatiContabiliSIBCorrente() {
		return datiContabiliSIBCorrenteForm != null && datiContabiliSIBCorrenteForm.getValueAsRecordList("listaDatiContabiliSIBCorrente") != null && datiContabiliSIBCorrenteForm.getValueAsRecordList("listaDatiContabiliSIBCorrente").getLength() > 0;
	}
	
	public boolean isPresentiDatiStoriciCorrente() {
		return invioDatiSpesaCorrenteForm != null && invioDatiSpesaCorrenteForm.getValueAsRecordList("listaInvioDatiSpesaCorrente") != null && invioDatiSpesaCorrenteForm.getValueAsRecordList("listaInvioDatiSpesaCorrente").getLength() > 0;
	}
	
	public boolean isModalitaGrigliaContoCapitale() {
		return getValueAsString("modalitaInvioDatiSpesaARagioneriaContoCapitale").equals(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B1);
	}
	
	public boolean isPresentiDatiContabiliSIBContoCapitale() {
		return datiContabiliSIBContoCapitaleForm != null && datiContabiliSIBContoCapitaleForm.getValueAsRecordList("listaDatiContabiliSIBContoCapitale") != null && datiContabiliSIBContoCapitaleForm.getValueAsRecordList("listaDatiContabiliSIBContoCapitale").getLength() > 0;
	}
	
	public boolean isPresentiDatiStoriciContoCapitale() {
		return invioDatiSpesaContoCapitaleForm != null && invioDatiSpesaContoCapitaleForm.getValueAsRecordList("listaInvioDatiSpesaContoCapitale") != null && invioDatiSpesaContoCapitaleForm.getValueAsRecordList("listaInvioDatiSpesaContoCapitale").getLength() > 0;
	}
	
	public Record getDetailRecordInAllegati() {
		Record lRecordAllegato = new Record();
		lRecordAllegato.setAttribute("idUd", getValueAsString("idUd"));
		lRecordAllegato.setAttribute("siglaProtocollo", !"".equals(getValueAsString("siglaRegistrazione")) ? getValueAsString("siglaRegistrazione") : getValueAsString("siglaRegProvvisoria"));
		lRecordAllegato.setAttribute("nroProtocollo", !"".equals(getValueAsString("numeroRegistrazione")) ? getValueAsString("numeroRegistrazione") : getValueAsString("numeroRegProvvisoria"));
		lRecordAllegato.setAttribute("dataProtocollo", getValueAsDate("dataRegistrazione") != null ? getValueAsDate("dataRegistrazione") : getValueAsDate("dataRegProvvisoria"));
		lRecordAllegato.setAttribute("desUserProtocollo", !"".equals(getValueAsString("desUserRegistrazione")) ? getValueAsString("desUserRegistrazione") : getValueAsString("desUserRegProvvisoria"));
		lRecordAllegato.setAttribute("desUOProtocollo", !"".equals(getValueAsString("desUORegistrazione")) ? getValueAsString("desUORegistrazione") : getValueAsString("desUORegProvvisoria"));
		return lRecordAllegato;
	}
	
	public String getIdModDispositivo() {
		return null;
	}
	
	public String getNomeModDispositivo() {
		return null;
	}
	
	public String getDisplayFilenameModDispositivo() {
		return null;
	}
	
	public void anteprimaProvvedimento(String nomeFilePrimario, String uriFilePrimario, InfoFileRecord infoFilePrimario, final String nomeFilePrimarioOmissis, final String uriFilePrimarioOmissis, final InfoFileRecord infoFilePrimarioOmissis) {
		if(uriFilePrimario != null && !"".equals(uriFilePrimario) && infoFilePrimario != null && infoFilePrimario.isFirmato()) {
			if(uriFilePrimarioOmissis != null && !"".equals(uriFilePrimarioOmissis)) {
				new PreviewWindow(uriFilePrimario, true, infoFilePrimario, "FileToExtractBean",	nomeFilePrimario) {
					
					@Override
					public void manageCloseClick() {
						super.manageCloseClick();
						new PreviewWindow(uriFilePrimarioOmissis, true, infoFilePrimarioOmissis, "FileToExtractBean", nomeFilePrimarioOmissis);
					};
				};		
			} else {
				new PreviewWindow(uriFilePrimario, true, infoFilePrimario, "FileToExtractBean", nomeFilePrimario);		
			} 
		} else {
			anteprimaDispositivoDaModello(hasPrimarioDatiSensibili());
		}
	}
	
	public void anteprimaDispositivoDaModello(boolean hasDatiSensibili) {
		if(hasDatiSensibili) {
			generaDispositivoDaModelloVersIntegrale(new ServiceCallback<Record>() {
				
				@Override
				public void execute(final Record recordPreview) {
					generaDispositivoDaModelloVersConOmissis(new ServiceCallback<Record>() {
						
						@Override
						public void execute(final Record recordPreviewOmissis) {
							previewWithCallback(recordPreview, new ServiceCallback<Record>() {
						
								@Override
								public void execute(Record object) {
									preview(recordPreviewOmissis);
								}
							});
						}
					});
				}
			});
		} else {
			generaDispositivoDaModelloVersIntegrale(new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record recordPreview) {
					preview(recordPreview);
				}
			});
		}
	}
	
	public void generaDispositivoDaModelloVersIntegrale() {
		generaDispositivoDaModelloVersIntegrale(null);
	}
	
	public void generaDispositivoDaModelloVersIntegrale(final ServiceCallback<Record> callback) {
		generaDispositivoDaModello(true, callback);
	}
	
	public void generaDispositivoDaModelloVersConOmissis() {
		generaDispositivoDaModelloVersConOmissis(null);
	}
	
	public void generaDispositivoDaModelloVersConOmissis(final ServiceCallback<Record> callback) {
		generaDispositivoDaModello(false, callback);
	}
	
	public void generaDispositivoDaModello(boolean flgMostraDatiSensibili, final ServiceCallback<Record> callback) {
		
		Record record = getRecordToSave();
		record.setAttribute("idModello", getIdModDispositivo());
		record.setAttribute("nomeModello", getNomeModDispositivo());
		record.setAttribute("displayFilenameModello", getDisplayFilenameModDispositivo());
		record.setAttribute("flgMostraDatiSensibili", flgMostraDatiSensibili);
		
		Layout.showWaitPopup("Generazione anteprima provvedimento in corso...");				
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
		lNuovaPropostaAtto2DataSource.executecustom("generaDispositivoDaModello", record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordPreview = response.getData()[0];
					if(callback != null) {
						callback.execute(recordPreview);
					}
				}				
			}		
		});
	}
	
	public String getIdModAppendice() {
		return null;
	}
	
	public String getNomeModAppendice() {
		return null;
	}
	
	public void anteprimaDatiSpesaDaModello() {
		generaDatiSpesaDaModello(new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record recordPreview) {
				preview(recordPreview);
			}
		});
	}
	
	public void generaDatiSpesaDaModello(final ServiceCallback<Record> callback) {
		
		Record record = getRecordToSave();
		record.setAttribute("idModello", getIdModAppendice());
		record.setAttribute("nomeModello", getNomeModAppendice());
		record.setAttribute("displayFilenameModello", "");
		
		Layout.showWaitPopup("Generazione anteprima dati di spesa in corso...");				
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
		lNuovaPropostaAtto2DataSource.executecustom("generaDatiSpesaDaModello", record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordPreview = response.getData()[0];
					if(callback != null) {
						callback.execute(recordPreview);
					}
				}				
			}		
		});
	}
	
	public void preview(final Record recordPreview) {
		previewWithCallback(recordPreview, null);
	}
	
	public void previewWithCallback(final Record recordPreview, final ServiceCallback<Record> closeCallback) {
		if (recordPreview.getAttribute("nomeFile") != null && recordPreview.getAttribute("nomeFile").endsWith(".pdf")) {
			new PreviewWindow(recordPreview.getAttribute("uri"), false, new InfoFileRecord(recordPreview.getAttributeAsRecord("infoFile")), "FileToExtractBean", recordPreview.getAttribute("nomeFile")) {
			
				@Override
				public void manageCloseClick() {
					super.manageCloseClick();
					if(closeCallback != null) {
						closeCallback.execute(recordPreview);
					}
				};
			};
		} else {
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", recordPreview.getAttribute("nomeFile"));
			lRecord.setAttribute("uri", recordPreview.getAttribute("uri"));
			lRecord.setAttribute("sbustato", "false");
			lRecord.setAttribute("remoteUri", "false");
			DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
			if(closeCallback != null) {
				closeCallback.execute(recordPreview);
			}
		}
	}
	
	public void controllaTotali(boolean afterLoadDati) {
		//TODO Errore nella somma dei fl 
		//Sarebbe preferibile fare le somme utilizzando gli interi: basta moltiplicare per 100 gli importi, togliendo la virgola prima delle due cifre decimali. 
		//Una volta fatta la somma bisogna riaggiungere la virgola.		
		String pattern = "#,##0.00";
		float totaleEntrateAurigaCorrente = 0;
		float totaleUsciteAurigaCorrente = 0;
		float totaleEntrateSIBCorrente = 0;
		float totaleUsciteSIBCorrente = 0;
		float totaleEntrateAurigaContoCapitale = 0;
		float totaleUsciteAurigaContoCapitale = 0;
		float totaleEntrateSIBContoCapitale = 0;
		float totaleUsciteSIBContoCapitale = 0;
		if(listaInvioDatiSpesaCorrenteItem != null && listaInvioDatiSpesaCorrenteItem.getValueAsRecordList() != null) {
			for(int i = 0; i < listaInvioDatiSpesaCorrenteItem.getValueAsRecordList().getLength(); i++) {
				Record record = listaInvioDatiSpesaCorrenteItem.getValueAsRecordList().get(i);
				String flgEntrataUscita = record.getAttribute("flgEntrataUscita") != null ? record.getAttribute("flgEntrataUscita") : "";
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				if("E".equals(flgEntrataUscita)) {
					totaleEntrateAurigaCorrente += importo;
				} else if("U".equals(flgEntrataUscita)) {
					totaleUsciteAurigaCorrente += importo;
				}
			}
		}
		if(listaDatiContabiliSIBCorrenteItem != null && listaDatiContabiliSIBCorrenteItem.getValueAsRecordList() != null) {
			for(int i = 0; i < listaDatiContabiliSIBCorrenteItem.getValueAsRecordList().getLength(); i++) {
				Record record = listaDatiContabiliSIBCorrenteItem.getValueAsRecordList().get(i);
				String flgEntrataUscita = record.getAttribute("flgEntrataUscita") != null ? record.getAttribute("flgEntrataUscita") : "";
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				if("E".equals(flgEntrataUscita)) {
					totaleEntrateSIBCorrente += importo;
				} else if("U".equals(flgEntrataUscita)) {
					totaleUsciteSIBCorrente += importo;
				}
			}
		}
		if(listaInvioDatiSpesaContoCapitaleItem != null && listaInvioDatiSpesaContoCapitaleItem.getValueAsRecordList() != null) {
			for(int i = 0; i < listaInvioDatiSpesaContoCapitaleItem.getValueAsRecordList().getLength(); i++) {
				Record record = listaInvioDatiSpesaContoCapitaleItem.getValueAsRecordList().get(i);
				String flgEntrataUscita = record.getAttribute("flgEntrataUscita") != null ? record.getAttribute("flgEntrataUscita") : "";
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				if("E".equals(flgEntrataUscita)) {
					totaleEntrateAurigaContoCapitale += importo;
				} else if("U".equals(flgEntrataUscita)) {
					totaleUsciteAurigaContoCapitale += importo;
				}
			}
		}
		if(listaDatiContabiliSIBContoCapitaleItem != null && listaDatiContabiliSIBContoCapitaleItem.getValueAsRecordList() != null) {
			for(int i = 0; i < listaDatiContabiliSIBContoCapitaleItem.getValueAsRecordList().getLength(); i++) {
				Record record = listaDatiContabiliSIBContoCapitaleItem.getValueAsRecordList().get(i);
				String flgEntrataUscita = record.getAttribute("flgEntrataUscita") != null ? record.getAttribute("flgEntrataUscita") : "";
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				if("E".equals(flgEntrataUscita)) {
					totaleEntrateSIBContoCapitale += importo;
				} else if("U".equals(flgEntrataUscita)) {
					totaleUsciteSIBContoCapitale += importo;
				}
			}
		}
		
		totaleEntrateAurigaCorrente = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleEntrateAurigaCorrente))).floatValue();
		totaleUsciteAurigaCorrente = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleUsciteAurigaCorrente))).floatValue();
		totaleEntrateSIBCorrente = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleEntrateSIBCorrente))).floatValue();
		totaleUsciteSIBCorrente = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleUsciteSIBCorrente))).floatValue();
		totaleEntrateAurigaContoCapitale = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleEntrateAurigaContoCapitale))).floatValue();
		totaleUsciteAurigaContoCapitale = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleUsciteAurigaContoCapitale))).floatValue();
		totaleEntrateSIBContoCapitale = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleEntrateSIBContoCapitale))).floatValue();
		totaleUsciteSIBContoCapitale = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleUsciteSIBContoCapitale))).floatValue();
		
		ArrayList<String> warningMessages = new ArrayList<String>();
		if((isModalitaGrigliaCorrente() && totaleUsciteAurigaCorrente > 0 && totaleUsciteSIBCorrente > 0 && totaleUsciteAurigaCorrente != totaleUsciteSIBCorrente) || 
		   (isModalitaGrigliaContoCapitale() && totaleUsciteAurigaContoCapitale > 0 && totaleUsciteSIBContoCapitale > 0 && totaleUsciteAurigaContoCapitale != totaleUsciteSIBContoCapitale)) {
			warningMessages.add("la spesa indicata su SIB non coincide con quella compilata in Auriga");
		} 
		if((isModalitaGrigliaCorrente() && totaleEntrateAurigaCorrente > 0 && totaleEntrateSIBCorrente > 0 && totaleEntrateAurigaCorrente != totaleEntrateSIBCorrente) || 
		   (isModalitaGrigliaContoCapitale() && totaleEntrateAurigaContoCapitale > 0 && totaleEntrateSIBContoCapitale > 0 && totaleEntrateAurigaContoCapitale != totaleEntrateSIBContoCapitale)) {
			warningMessages.add("il totale di entrate indicato in SIB non coincide con quello delle entrate indicate in Auriga");
		}
		if(afterLoadDati) {
			if((isModalitaGrigliaCorrente() && totaleUsciteAurigaCorrente > 0 && totaleEntrateAurigaCorrente > 0 && totaleUsciteAurigaCorrente != totaleEntrateAurigaCorrente) || 
			   (isModalitaGrigliaContoCapitale() && totaleUsciteAurigaContoCapitale > 0 && totaleEntrateAurigaContoCapitale > 0 && totaleUsciteAurigaContoCapitale != totaleEntrateAurigaContoCapitale)) {
				warningMessages.add("il totale di entrate e uscite indicati in Auriga non coincidono come in genere dovrebbe essere");
			} 
			if((totaleUsciteSIBCorrente > 0 && totaleEntrateSIBCorrente > 0 && totaleUsciteSIBCorrente != totaleEntrateSIBCorrente) || 
			   (totaleUsciteSIBContoCapitale > 0 && totaleEntrateSIBContoCapitale > 0 && totaleUsciteSIBContoCapitale != totaleEntrateSIBContoCapitale)) {
				warningMessages.add("il totale di entrate e uscite indicati in SIB non coincidono come in genere dovrebbe essere");
			}
		}
		if(warningMessages.size() > 0) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("<ul>");
			for(int i = 0; i < warningMessages.size(); i++) {
				buffer.append("<li>" + warningMessages.get(i) + "</li>");			
			}
			buffer.append("</ul>");			
			SC.warn(buffer.toString());
		}
	}
	
	@Override
	public void clearTabErrors() {
		clearTabErrors(tabSet);
	}
	
	@Override
	public void showTabErrors() {
		showTabErrors(tabSet);
	}
	
	@Override
	public void showTabErrors(TabSet tabSet) {
		super.showTabErrors(tabSet);
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					attributiAddDocDetails.get(key).showTabErrors(tabSet);
				}
			}
		}
	}	
	
	public String[] buildCIGValueMap() {
		if(showDetailSectionCIG()) {
			List<String> listaCodCIG = new ArrayList<String>();
			RecordList listaCIG = CIGForm.getValueAsRecordList("listaCIG");
			for(int i=0; i < listaCIG.getLength(); i++) {
				if(listaCIG.get(i).getAttribute("codiceCIG") != null &&
						!"".equals(listaCIG.get(i).getAttribute("codiceCIG"))) {
					listaCodCIG.add(listaCIG.get(i).getAttribute("codiceCIG"));
					if(!listaCodCIG.contains(listaCIG.get(i).getAttribute("codiceCIG"))) {
						listaCodCIG.add(listaCIG.get(i).getAttribute("codiceCIG"));
					}
				}
			}
			return listaCodCIG.toArray(new String[listaCodCIG.size()]);
		}
		return null;
	}
	
	public boolean isAttivoSIB() {
		String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");
		return lSistAMC != null && "SIB".equalsIgnoreCase(lSistAMC);
	}
		  
	public class NuovaPropostaAtto2DetailSection extends DetailSection {

		public NuovaPropostaAtto2DetailSection(String pTitle, boolean pCanCollapse, boolean pShowOpen, boolean pIsRequired, DynamicForm... forms) {
			super(pTitle, pCanCollapse, pShowOpen, pIsRequired, forms);
		}

		@Override
		public boolean showFirstCanvasWhenEmptyAfterOpen() {
			return true;
		}
	}
	
	public void afterShow() {
		
	}
	
	@Override
	protected void onDestroy() {
		if(saveModelloWindow != null) {
			saveModelloWindow.destroy();
		}
		if(modelliDS != null) {
			modelliDS.destroy();
		}
		super.onDestroy();
	}
	
	private void setFormValuesFromRecordArchivio(Record record) {
		caratteristicheProvvedimentoForm1.clearErrors(true);
		caratteristicheProvvedimentoForm1.setValue("idUdAttoDeterminaAContrarre", record.getAttribute("idUdFolder"));
		String segnaturaXOrd = record.getAttribute("segnaturaXOrd");	
		if(segnaturaXOrd != null) {
			StringSplitterClient st = new StringSplitterClient(segnaturaXOrd, "-");						
			if(st.getTokens()[0] != null) {
				if("1".equals(st.getTokens()[0])) {
					caratteristicheProvvedimentoForm1.setValue("categoriaRegAttoDeterminaAContrarre", "PG");							
				} else if("4".equals(st.getTokens()[0])) {
					caratteristicheProvvedimentoForm1.setValue("categoriaRegAttoDeterminaAContrarre", "R");						
				}
			}
			caratteristicheProvvedimentoForm1.setValue("siglaAttoDeterminaAContrarre", st.getTokens()[1] != null ? st.getTokens()[1].trim() : null);
			caratteristicheProvvedimentoForm1.setValue("annoAttoDeterminaAContrarre", st.getTokens()[2] != null ? st.getTokens()[2].trim() : null);
			caratteristicheProvvedimentoForm1.setValue("numeroAttoDeterminaAContrarre", st.getTokens()[3] != null ? st.getTokens()[3].trim() : null);
		}
		caratteristicheProvvedimentoForm1.markForRedraw();
	}	
	
	public class AttoDeterminaAContrarreLookupArchivio extends LookupArchivioPopup {

		public AttoDeterminaAContrarreLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, true);
		}
		
		@Override
		public String getWindowTitle() {
			return "Seleziona da archivio";
		}
		
		@Override
		public String getFinalita() {
			return "SEL_ATTI";
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordArchivio(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
	}
	
}
