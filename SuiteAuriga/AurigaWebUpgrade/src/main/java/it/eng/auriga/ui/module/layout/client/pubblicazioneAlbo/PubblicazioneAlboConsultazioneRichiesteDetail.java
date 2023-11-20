/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility.OpenEditorCallback;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.ScanUtility;
import it.eng.auriga.ui.module.layout.client.ScanUtility.ScanCallback;
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.DocumentItem;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiGridItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.ApponiTimbroWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.DocumentiCollegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.DocumentiCollegatiPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.MittenteProtEntrataItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.MittenteProtInternaItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.NoteMancanzaFilePopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowPageSelectionCallback;
import it.eng.auriga.ui.module.layout.client.protocollazione.SelezionaGeneraDaModelloWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.StampaEtichettaPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.VisualizzaFatturaWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.VisualizzaVersioniFileWindow;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaEtichettaUtil;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaTimbroBean;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaTimbroWindow;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.auriga.ui.module.layout.client.timbra.TimbraWindow;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroCopertinaUtil;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedDateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class PubblicazioneAlboConsultazioneRichiesteDetail extends CustomDetail {
	
	private PubblicazioneAlboConsultazioneRichiesteDetail instance;

	// Sezione Dati atto
 	private DetailSection detailSectionAtto;
	private DynamicForm attoForm;
	private RadioGroupItem statoAttoItem;
	private SelectItem tipoRegNumItem;
	private ExtendedTextItem siglaRegNumItem;
	private ExtendedNumericItem annoRegNumItem;
	private ExtendedNumericItem nroRegNumItem;
	private ExtendedDateItem tsRegistrazioneItem;
	private DateItem dataAdozioneItem;
	private DateItem dataEsecutivitaItem;
	private SelectItem tipoItem;
	private DocumentiCollegatiItem documentiCollegatiItem;
	private ImgButtonItem istanzeInElencoButton;

	//Sezione Mittente/Richiedente
 	private DetailSection detailSectionMittenteRichiedente;
    private DynamicForm mittenteRichiedenteForm;
	private RadioGroupItem tipoMittenteItem;
	private MittenteProtEntrataItem mittenteEsternoItem;
	private MittenteProtInternaItem mittenteInternoItem;

	//Sezione Oggetto
 	private DetailSection detailSectionOggetto;
    private DynamicForm oggettoForm;
    private CKEditorItem oggettoHtmlItem;

    //Sezione File Primario
 	private DetailSection detailSectionPrimario;
    private DynamicForm filePrimarioForm;
	private ExtendedTextItem nomeFilePrimarioItem;
	private FileUploadItemWithFirmaAndMimeType uploadFilePrimarioItem;
	private NestedFormItem filePrimarioButtons;	
	private ImgButtonItem showNoteMancanzaFileButton;
	private ImgButtonItem previewButton;
	private ImgButtonItem previewEditButton;
	private ImgButtonItem editButton;
	private ImgButtonItem fileFirmatoDigButton;
	private ImgButtonItem firmaNonValidaButton;
	private ImgButtonItem fileMarcatoTempButton;
	private ImgButtonItem generaDaModelloButton;
	private ImgButtonItem altreOpButton;
	private TextItem improntaItem;
	private CheckboxItem flgSostituisciVerPrecItem;
	private CheckboxItem flgOriginaleCartaceoItem;
	private CheckboxItem flgCopiaSostitutivaItem;
	private CheckboxItem flgTimbraFilePostRegItem;
	private CheckboxItem flgNoPubblPrimarioItem;
	private AllegatiItem filePrimarioVerPubblItem;
	private CheckboxItem flgDatiSensibiliItem;
	private DocumentItem filePrimarioOmissisItem;
	private SelectItem docPressoCentroPostaItem;
	
	//Sezione Allegati
 	private DetailSection detailSectionAllegati;
    private DynamicForm fileAllegatiForm;
// 	private AllegatiItem fileAllegatiItem;
    protected CanvasItem fileAllegatiItem;

 	//Sezione Periodo di Pubblicazione
 	private DetailSection detailSectionPeriodoPubblicazione;
 	private DynamicForm periodoPubblicazioneForm;
 	private ExtendedDateItem dataInizioPubblicazioneItem;
	private ExtendedDateItem dataFinePubblicazioneItem;
	private ExtendedNumericItem giorniPubblicazioneItem;
	private DynamicForm rettificaForm;
	private TextAreaItem motivoRettificaPubblicazioneItem;
	
 	//Sezione Forma di Pubblicazione
 	private DetailSection detailSectionFormaPubblicazione;
    private DynamicForm formaPubblicazioneForm;
	private RadioGroupItem formaPubblicazioneItem;
	
	//Sezione note di Pubblicazione
	private DetailSection detailSectionNotePubblicazione;
    private DynamicForm notePubblicazioneForm;
	private TextAreaItem notePubblicazioneItem;
	
	// HiddenItem
	private HiddenItem idUdFolderHiddenItem;
	private HiddenItem idRichPubblHiddenItem;
	private HiddenItem isRettificaHiddenItem;	
	private HiddenItem abilModificabileHiddenItem;
	private HiddenItem abilProrogaPubblicazioneHiddenItem;
	private HiddenItem abilAnnullamentoPubblicazioneHiddenItem;
	private HiddenItem abilRettificaPubblicazioneHiddenItem; 
			
	private HiddenItem oggettoItem;
	private HiddenItem idProcessHiddenItem;
	private HiddenItem idDocPrimarioHiddenItem;
	private HiddenItem idAttachEmailPrimarioItem;	
	private HiddenItem uriFilePrimarioItem;
	private HiddenItem infoFileItem;
	private HiddenItem remoteUriFilePrimarioItem;
	private HiddenItem isDocPrimarioChangedItem;
	private HiddenItem nomeFilePrimarioOrigItem;
	private HiddenItem nomeFilePrimarioTifItem;
	private HiddenItem uriFilePrimarioTifItem;
	private HiddenItem nomeFileVerPreFirmaItem;
	private HiddenItem uriFileVerPreFirmaItem;
	private HiddenItem improntaVerPreFirmaItem;
	private HiddenItem infoFileVerPreFirmaItem;
	private HiddenItem nroLastVerItem;
	private HiddenItem noteMancanzaFileItem;
	private HiddenItem flgTimbratoFilePostRegItem;
	private HiddenItem attivaTimbroTipologiaItem;
	private HiddenItem opzioniTimbroItem;
		
	// Mappa dei valori originali caricati in maschera
	private  Map valuesOrig;
		
	// Tipologia documentale
	protected String tipoDocumento;
		
	public PubblicazioneAlboConsultazioneRichiesteDetail(String nomeEntita) {
		
		super(nomeEntita);
		
		instance = this;
				
		createDetailSectionAtto();
		
		createDetailSectionMittente();
		
		createDetailSectionOggetto();
	
		createDetailSectionPrimario();
		
		createDetailSectionAllegati();
		
		createDetailSectionPeriodoPubblicazione();
		
		createDetailSectionFormaPubblicazione();
		
		createDetailSectionNotePubblicazione();
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(50);

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(detailSectionAtto);
		lVLayout.addMember(detailSectionMittenteRichiedente);	
		lVLayout.addMember(detailSectionOggetto);
		lVLayout.addMember(detailSectionPrimario);		
		lVLayout.addMember(detailSectionAllegati);
		lVLayout.addMember(detailSectionPeriodoPubblicazione);
		lVLayout.addMember(detailSectionFormaPubblicazione);
		lVLayout.addMember(detailSectionNotePubblicazione);
		
		addMember(lVLayout);
		addMember(lVLayoutSpacer);
	}
	

    protected void createDetailSectionAtto() {		
	   createFormAtto();
	   detailSectionAtto = new DetailSection(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_detailSectionAtto_title(), true, true, false, attoForm);		
    }

    protected void createDetailSectionMittente() {		
		createFormMittenteRichiedente();
		detailSectionMittenteRichiedente = new DetailSection(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_detailSectionMittenteRichiedente_title(), true, true, false, mittenteRichiedenteForm);		
	}
    
    protected void createDetailSectionOggetto() {		
		createFormOggetto();
		detailSectionOggetto = new DetailSection(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_detailSectionOggetto_title(), true, true, true, oggettoForm);		
	}
    
    protected void createDetailSectionPrimario() {
    	createFormFilePrimario();
		detailSectionPrimario = new DetailSection(I18NUtil.getMessages().protocollazione_detail_filePrimarioForm_title(), true, true, false, filePrimarioForm);
	}

    protected void createDetailSectionAllegati() {
		createFormAllegati();
		detailSectionAllegati = new DetailSection(I18NUtil.getMessages().protocollazione_detail_fileAllegatiForm_title(), true, true, false, fileAllegatiForm);
	}
    
    protected void createDetailSectionPeriodoPubblicazione() {
		createFormPeriodoPubblicazione();
		createFormRettifica();		
		detailSectionPeriodoPubblicazione = new DetailSection(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_detailSectionPeriodoPubblicazione_title(), true, true, false, periodoPubblicazioneForm, rettificaForm);
	}
    
    protected void createDetailSectionFormaPubblicazione() {
		createFormFormaPubblicazione();
		detailSectionFormaPubblicazione = new DetailSection(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_detailSectionFormaPubblicazione_title(), true, true, false, formaPubblicazioneForm);
	}
    
    protected void createDetailSectionNotePubblicazione() {
		createFormNotePubblicazione();
		detailSectionNotePubblicazione = new DetailSection(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_detailSectionNotePubblicazione_title(), true, true, false, notePubblicazioneForm);
	}
    
    protected void createFormRettifica() {
    	rettificaForm = new DynamicForm();
    	rettificaForm.setValuesManager(vm);
    	rettificaForm.setHeight("5");
    	rettificaForm.setPadding(5);
    	rettificaForm.setWrapItemTitles(false);
    	rettificaForm.setNumCols(10);
    	rettificaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
    	
    	motivoRettificaPubblicazioneItem = new TextAreaItem("motivoRettificaPubblicazione", "Motivo rettifica"); 
    	motivoRettificaPubblicazioneItem.setWidth(530);
    	motivoRettificaPubblicazioneItem.setColSpan(7);
    	motivoRettificaPubblicazioneItem.setStartRow(true);
    	motivoRettificaPubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isRettificaHiddenItem.getValue() != null && (Boolean) isRettificaHiddenItem.getValue();
			}
		});
    	motivoRettificaPubblicazioneItem.setAttribute("obbligatorio", true);
    	motivoRettificaPubblicazioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRettificaHiddenItem.getValue() != null && (Boolean) isRettificaHiddenItem.getValue();					
			}
		}));	
	          
    	rettificaForm.setFields(motivoRettificaPubblicazioneItem);
    }
    
    protected void createFormFormaPubblicazione() {
    	formaPubblicazioneForm = new DynamicForm();
    	formaPubblicazioneForm.setValuesManager(vm);
    	formaPubblicazioneForm.setHeight("5");
    	formaPubblicazioneForm.setPadding(5);
    	formaPubblicazioneForm.setWrapItemTitles(false);
    	formaPubblicazioneForm.setNumCols(10);
    	formaPubblicazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
    	
    	// Forma pubblicazione : INTEGRALE/PER ESTRATTO
    	LinkedHashMap<String, String> formaPubblicazioneMap = new LinkedHashMap<String, String>();  
    	formaPubblicazioneMap.put("INTEGRALE","INTEGRALE");  
    	formaPubblicazioneMap.put("PER_ESTRATTO","PER ESTRATTO");  
    	formaPubblicazioneItem = new RadioGroupItem("formaPubblicazione"); 
    	formaPubblicazioneItem.setTitle(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_formaPubblicazioneItem_title());
    	formaPubblicazioneItem.setValueMap(formaPubblicazioneMap); 
    	formaPubblicazioneItem.setVertical(false);
    	formaPubblicazioneItem.setWrap(false);	
    	formaPubblicazioneItem.setTitleOrientation(TitleOrientation.LEFT);
    	formaPubblicazioneItem.setWidth(120);
    	formaPubblicazioneItem.setDefaultValue("INTEGRALE");
    	        
    	formaPubblicazioneForm.setFields(formaPubblicazioneItem);
    }
    
    protected void createFormNotePubblicazione() {
    	notePubblicazioneForm = new DynamicForm();
    	notePubblicazioneForm.setValuesManager(vm);
    	notePubblicazioneForm.setHeight("5");
    	notePubblicazioneForm.setPadding(5);
    	notePubblicazioneForm.setWrapItemTitles(false);
    	notePubblicazioneForm.setNumCols(10);
    	notePubblicazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
    	
    	notePubblicazioneItem = new TextAreaItem("notePubblicazione","");
    	notePubblicazioneItem.setAlign(Alignment.CENTER);	
    	notePubblicazioneItem.setLength(4000);
    	notePubblicazioneItem.setHeight(150);
    	notePubblicazioneItem.setWidth(600);
    	notePubblicazioneItem.setShowTitle(false);
    	
    	notePubblicazioneForm.setFields(notePubblicazioneItem);
    }
    
    protected void createFormPeriodoPubblicazione() {
    	
    	periodoPubblicazioneForm = new DynamicForm();
    	periodoPubblicazioneForm.setValuesManager(vm);
    	periodoPubblicazioneForm.setHeight("5");
    	periodoPubblicazioneForm.setPadding(5);
    	periodoPubblicazioneForm.setWrapItemTitles(false);
    	periodoPubblicazioneForm.setNumCols(10);
    	periodoPubblicazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
    	
		// Data inizio pubblicazione
    	dataInizioPubblicazioneItem = new ExtendedDateItem("dataInizioPubblicazione", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_dataInizioPubblicazioneItem_title());		
    	dataInizioPubblicazioneItem.setRequired(true);		
		dataInizioPubblicazioneItem.setStartRow(true);		
		dataInizioPubblicazioneItem.setDefaultValue(getDefaultValueDataInizioPubbl());
		dataInizioPubblicazioneItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(final ChangedEvent event) {
				manageOnChangedPeriodoPubbl("dataInizioPubblicazione");
			}
		});
		
		// Giorni pubblicazione
		giorniPubblicazioneItem = new ExtendedNumericItem("giorniPubblicazione", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_giorniPubblicazioneItem_title(), false);		
		giorniPubblicazioneItem.setRequired(true);
		giorniPubblicazioneItem.setColSpan(1);
		giorniPubblicazioneItem.setLength(7);
		giorniPubblicazioneItem.setWidth(100);
		giorniPubblicazioneItem.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(final ChangedEvent event) {
				manageOnChangedPeriodoPubbl("giorniPubblicazione");
			}
		});
		
		
		// Data fine pubblicazione
    	dataFinePubblicazioneItem = new ExtendedDateItem("dataFinePubblicazione", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_dataFinePubblicazioneItem_title());		
//    	dataFinePubblicazioneItem.setWidth(100);
//    	dataFinePubblicazioneItem.setCanEdit(true);
    	dataFinePubblicazioneItem.addChangedHandler(new ChangedHandler() {
    		
			@Override
			public void onChanged(final ChangedEvent event) {
				manageOnChangedPeriodoPubbl("dataFinePubblicazione");
			}
		});
    	CustomValidator lPeriodoPubblicazioneValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				Date dataInizioPubb = dataInizioPubblicazioneItem.getValueAsDate();
		    	Date dataFinePubb = dataFinePubblicazioneItem.getValueAsDate();
		    	if(dataInizioPubb != null && dataFinePubb != null) {
		    		Integer differenceDays = CalendarUtil.getDaysBetween(dataInizioPubb, dataFinePubb);
		    		if(isConteggiaInteroGiornoCorrenteXPeriodoPubbl()) {
		    			return (differenceDays >= 0);
		    		} else {
		    			return (differenceDays > 0);
		    		}
		    	}		    	
		    	return true;
			}
		};
		if(isConteggiaInteroGiornoCorrenteXPeriodoPubbl()) {
			lPeriodoPubblicazioneValidator.setErrorMessage("La data di fine pubblicazione non può essere antecedente a quella di inizio pubblicazione");
		} else {
			lPeriodoPubblicazioneValidator.setErrorMessage("La data di fine pubblicazione deve essere successiva a quella di inizio pubblicazione");
		}		
		//TODO La data di fine pubblicazione non può essere una data passata
		dataFinePubblicazioneItem.setValidators(lPeriodoPubblicazioneValidator);
    	
		periodoPubblicazioneForm.setFields(dataInizioPubblicazioneItem, giorniPubblicazioneItem, dataFinePubblicazioneItem);
    }
    
    private Date getDefaultValueDataInizioPubbl() {
    	Integer giorniDelay = Integer.parseInt(AurigaLayout.getParametroDB("GG_DELAY_INIZIO_PUBBL_ATTO") != null && !"".equals(AurigaLayout.getParametroDB("GG_DELAY_INIZIO_PUBBL_ATTO")) ? AurigaLayout.getParametroDB("GG_DELAY_INIZIO_PUBBL_ATTO") : "0");
    	Date dataInizioPubbl = new Date();
    	CalendarUtil.addDaysToDate(dataInizioPubbl, giorniDelay);
    	return dataInizioPubbl;
	}
    
    private void manageOnChangedPeriodoPubbl(String fieldName) {
    	Integer giorniPubblicazione = null;
    	if(giorniPubblicazioneItem.getValueAsString() != null && !"".equals(giorniPubblicazioneItem.getValueAsString())) {
    		giorniPubblicazione = Integer.parseInt(giorniPubblicazioneItem.getValueAsString());
		}   
    	Date dataInizioPubbl = dataInizioPubblicazioneItem.getValueAsDate();
    	Date dataFinePubbl = dataFinePubblicazioneItem.getValueAsDate();
    	if(fieldName != null) {
			if("dataInizioPubblicazione".equals(fieldName)) {
				if(dataInizioPubbl != null && giorniPubblicazione != null) {
					calcolaDataFinePubbl(dataInizioPubbl, giorniPubblicazione);
					dataFinePubblicazioneItem.validate();
				} else if(dataInizioPubbl != null && dataFinePubbl != null) {
					if(dataFinePubblicazioneItem.validate()) {
						calcolaGiorniPubbl(dataInizioPubbl, dataFinePubbl);
					}
				}
			} else if("giorniPubblicazione".equals(fieldName)) {
				if(dataInizioPubbl != null && giorniPubblicazione != null) {
					calcolaDataFinePubbl(dataInizioPubbl, giorniPubblicazione);
					dataFinePubblicazioneItem.validate();
				}
			} else if("dataFinePubblicazione".equals(fieldName)) {
				if(dataInizioPubbl != null && dataFinePubbl != null) {
					if(dataFinePubblicazioneItem.validate()) {
						calcolaGiorniPubbl(dataInizioPubbl, dataFinePubbl);
					}
			    } 
			}
    	}
    }
    
    private void calcolaDataFinePubbl(Date dataInizioPubbl, Integer giorniPubblicazione) {
    	if(dataInizioPubbl != null && giorniPubblicazione != null) {
			Date dataFinePubbl = dataInizioPubbl;
			if(isConteggiaInteroGiornoCorrenteXPeriodoPubbl()) {
        		CalendarUtil.addDaysToDate(dataFinePubbl, giorniPubblicazione - 1);
        	} else {
        		CalendarUtil.addDaysToDate(dataFinePubbl, giorniPubblicazione);
        	}
        	dataFinePubblicazioneItem.setValue(dataFinePubbl); 
		}
    }
    
    private void calcolaGiorniPubbl(Date dataInizioPubbl, Date dataFinePubbl) {
    	if(dataInizioPubbl != null && dataFinePubbl != null) {
    		Integer differenceDays = CalendarUtil.getDaysBetween(dataInizioPubbl, dataFinePubbl);
    		if(isConteggiaInteroGiornoCorrenteXPeriodoPubbl()) {
    			if(differenceDays >= 0) {
    				giorniPubblicazioneItem.setValue("" + (differenceDays + 1));
    			}
			} else {
				if(differenceDays > 0) {
					giorniPubblicazioneItem.setValue("" + differenceDays);
				}
			}
	    }
    }

    private boolean showIstanzeCollegateButton() {
    	return tipoItem != null && tipoItem.getValue() != null && !"".equals(tipoItem.getValue()) && !"".equals(AurigaLayout.getParametroDB("ID_DOC_TYPE_ELENCO_ISTANZE_CONC")) && tipoItem.getValue().equals(AurigaLayout.getParametroDB("ID_DOC_TYPE_ELENCO_ISTANZE_CONC"))? true : false;
    }
    
	protected void createFormAllegati() {
		createAllegatiItem();
		fileAllegatiForm = new DynamicForm();
		fileAllegatiForm.setValuesManager(vm);
		fileAllegatiForm.setWidth("100%");
		fileAllegatiForm.setPadding(5);
		fileAllegatiForm.setFields(fileAllegatiItem);
	}
    
    protected void createFormAtto() {
    	
    	attoForm = new DynamicForm();
		attoForm.setValuesManager(vm);
		attoForm.setHeight("5");
		attoForm.setPadding(5);
		attoForm.setWrapItemTitles(false);
		attoForm.setNumCols(10);
		attoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
    	
		CustomValidator attoPresenteValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if (!(statoAttoItem.getValue() != null && statoAttoItem.getValueAsString().equals("da_caricare"))) {
					String tipoReg = tipoRegNumItem != null && tipoRegNumItem.getValueAsString() != null ? tipoRegNumItem.getValueAsString() : "";
					String sigla = "";
					if (tipoReg.equals("R") || tipoReg.equals("PP")) {
						sigla = siglaRegNumItem != null && siglaRegNumItem.getValue() != null ? (String) siglaRegNumItem.getValue() : "";
					} else {
						sigla = tipoReg;
					}
					String numero = nroRegNumItem != null && nroRegNumItem.getValueAsString() != null ? nroRegNumItem.getValueAsString() : "";
					String anno = annoRegNumItem != null && annoRegNumItem.getValueAsString() != null ? annoRegNumItem.getValueAsString() : "";									
					if (("PG".equals(tipoReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno)) {
						return idUdFolderHiddenItem.getValue() != null && !"".equals(idUdFolderHiddenItem.getValue());
					}
				}
				return true;
			}
		};
		attoPresenteValidator.setErrorMessage("Atto non presente a sistema");

		// Stato atto : da caricare/gia' presente a sistema
        LinkedHashMap<String, String> statoAttoMap = new LinkedHashMap<String, String>();  
        statoAttoMap.put("da_caricare", "da caricare");  
        statoAttoMap.put("presente", "già presente a sistema");  
        statoAttoItem = new RadioGroupItem("statoAtto"); 
        statoAttoItem.setTitle(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_statoAttoItem_title());
        statoAttoItem.setValueMap(statoAttoMap); 
        statoAttoItem.setVertical(false);
        statoAttoItem.setWrap(false);	
        statoAttoItem.setTitleOrientation(TitleOrientation.LEFT);
        statoAttoItem.setWidth(120);
        statoAttoItem.setColSpan(2);
		statoAttoItem.setStartRow(true);
		if(AurigaLayout.isAttivoModuloProt()) {
			statoAttoItem.setDefaultValue("presente");
		} else {
			statoAttoItem.setDefaultValue("da_caricare");
		}
		statoAttoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUdFolder", (String) null);
				if (event != null && event.getValue() != null && event.getValue().equals("presente")) {
					tsRegistrazioneItem.setCanEdit(false);	
					recuperaIdUdAtto(new ServiceCallback<String>() {

						@Override
						public void execute(String idUdAtto) {
							if(idUdAtto != null && !"".equals(idUdAtto)) {
								loadDettaglio(idUdAtto, new ServiceCallback<Record>() {

									@Override
									public void execute(Record recordDettaglio) {
										if (recordDettaglio != null) {											
											recordDettaglio.setAttribute("dataAdozione", recordDettaglio.getAttribute("tsRegistrazione"));
											editRecord(recordDettaglio);
											markForRedraw();
											setCanEdit(true);
										}
									}
								});
							} else {
								Record lRecord = new Record();		
								lRecord.setAttribute("statoAtto", statoAttoItem.getValue());
								lRecord.setAttribute("tipoRegNum", tipoRegNumItem.getValue());
								lRecord.setAttribute("siglaRegNum", siglaRegNumItem.getValue());
								lRecord.setAttribute("nroRegNum", nroRegNumItem.getValue());
								lRecord.setAttribute("annoRegNum", annoRegNumItem.getValue());											
								editNewRecord(lRecord.toMap());
								markForRedraw();
								setCanEdit(true);
							}
						}

						@Override
						public void manageError() {
							event.getForm().markForRedraw();
						}
					});
					
				} else {
					tsRegistrazioneItem.setCanEdit(true);
				}
				if(AurigaLayout.isAttivoModuloProt() && statoAttoItem.getValue() != null) {
					if(statoAttoItem.getValue().equals("da_caricare")) {
						editNewRecord();
						statoAttoItem.setValue("da_caricare");
						tipoRegNumItem.setDefaultValue("R");
						newMode();
						mittenteEsternoItem.setCanEdit(true);
						mittenteInternoItem.setCanEdit(true);
						tipoMittenteItem.setCanEdit(true);
						uploadFilePrimarioItem.setCanEdit(true);
						nomeFilePrimarioItem.setCanEdit(true);
						if(fileAllegatiItem instanceof AllegatiGridItem) {
							((AllegatiGridItem) fileAllegatiItem).setCanEdit(true);
						} else {
							((AllegatiItem)fileAllegatiItem).setCanEdit(true);
						}
					} else if(statoAttoItem.getValue().equals("presente")) {
						tipoRegNumItem.setDefaultValue("PG");
						mittenteEsternoItem.setCanEdit(false);
						mittenteInternoItem.setCanEdit(false);
						tipoMittenteItem.setCanEdit(false);
						tsRegistrazioneItem.setCanEdit(false);
						uploadFilePrimarioItem.setCanEdit(false);
						nomeFilePrimarioItem.setCanEdit(false);
						if(fileAllegatiItem instanceof AllegatiGridItem) {
							((AllegatiGridItem) fileAllegatiItem).canEditOnlyOmissisMode();
						} else {
							((AllegatiItem)fileAllegatiItem).canEditOnlyOmissisMode();
						}						
					}
				}
				attoForm.markForRedraw();
			}
		});
		statoAttoItem.setValidators(attoPresenteValidator);		
		
		// **********************************************
		// Estremi di registrazione
		// **********************************************
		// Tipo
		GWTRestDataSource tipoDS = new GWTRestDataSource("LoadComboTipoDocCollegatoDataSource", "key", FieldType.TEXT);
		tipoDS.extraparam.put("finalita", "PUBBLICAZIONE");
		tipoRegNumItem = new SelectItem("tipoRegNum", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_tipoRegistrazioneItem_title());
		tipoRegNumItem.setRequired(true);		
		tipoRegNumItem.setOptionDataSource(tipoDS);
		tipoRegNumItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				Criterion[] criterias = new Criterion[1];
				if(AurigaLayout.isAttivoModuloProt() && statoAttoItem.getValue().equals("da_caricare")) {
					criterias[0] = new Criterion("key", OperatorId.IEQUALS, "R");
				}
				return new AdvancedCriteria(OperatorId.AND, criterias);
			}
		});
		tipoRegNumItem.setAutoFetchData(true);
		tipoRegNumItem.setDisplayField("value");
		tipoRegNumItem.setValueField("key");
		tipoRegNumItem.setDefaultValue("PG");
		tipoRegNumItem.setWidth(110);
		tipoRegNumItem.setWrapTitle(false);
		tipoRegNumItem.setColSpan(1);
		tipoRegNumItem.setStartRow(true);
		tipoRegNumItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUdFolder", (String) null);
				if (statoAttoItem.getValue() != null && statoAttoItem.getValue().equals("presente")) {
					recuperaIdUdAtto(new ServiceCallback<String>() {

						@Override
						public void execute(String idUdAtto) {
							if(idUdAtto != null && !"".equals(idUdAtto)) {
								loadDettaglio(idUdAtto, new ServiceCallback<Record>() {

									@Override
									public void execute(Record recordDettaglio) {
										if (recordDettaglio != null) {
											recordDettaglio.setAttribute("dataAdozione", recordDettaglio.getAttribute("tsRegistrazione"));
											editRecord(recordDettaglio);
											markForRedraw();
											setCanEdit(true);
										}
									}
								});
							} else {
								Record lRecord = new Record();		
								lRecord.setAttribute("statoAtto", statoAttoItem.getValue());
								lRecord.setAttribute("tipoRegNum", tipoRegNumItem.getValue());
								lRecord.setAttribute("siglaRegNum", siglaRegNumItem.getValue());
								lRecord.setAttribute("nroRegNum", nroRegNumItem.getValue());
								lRecord.setAttribute("annoRegNum", annoRegNumItem.getValue());											
								editNewRecord(lRecord.toMap());
								markForRedraw();
								setCanEdit(true);
							}
						}

						@Override
						public void manageError() {
							event.getForm().markForRedraw();
						}
					});
				}
			}
		});
		tipoRegNumItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg") : "";
				String datiCollegamento = form.getValueAsString("datiCollegamento") != null ? form.getValueAsString("datiCollegamento") : "";				
				if(!"".equals(estremiReg) || !"".equals(datiCollegamento)) {
					return estremiReg  + " " + datiCollegamento;
				}
				return null;
			}
		});
		
//		// Sigla Registro
		siglaRegNumItem = new ExtendedTextItem("siglaRegNum", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_siglaRegistrazioneItem_title());
		siglaRegNumItem.setWidth(110);
		siglaRegNumItem.setColSpan(1);
		siglaRegNumItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return tipoRegNumItem.getValueAsString() != null && 
						("R".equalsIgnoreCase(tipoRegNumItem.getValueAsString()) || "PP".equalsIgnoreCase(tipoRegNumItem.getValueAsString()));
			}
		}));	
		siglaRegNumItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoRegNumItem.getValueAsString() != null && 
						("R".equalsIgnoreCase(tipoRegNumItem.getValueAsString()) || "PP".equalsIgnoreCase(tipoRegNumItem.getValueAsString()));
			}
		});		
		siglaRegNumItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUdFolder", (String) null);
				if (statoAttoItem.getValue() != null && statoAttoItem.getValue().equals("presente")) {
					recuperaIdUdAtto(new ServiceCallback<String>() {

						@Override
						public void execute(String idUdAtto) {
							if(idUdAtto != null && !"".equals(idUdAtto)) {
								loadDettaglio(idUdAtto, new ServiceCallback<Record>() {

									@Override
									public void execute(Record recordDettaglio) {
										if (recordDettaglio != null) {
											recordDettaglio.setAttribute("dataAdozione", recordDettaglio.getAttribute("tsRegistrazione"));
											editRecord(recordDettaglio);
											markForRedraw();
											setCanEdit(true);
										}
									}
								});
							} else {
								Record lRecord = new Record();		
								lRecord.setAttribute("statoAtto", statoAttoItem.getValue());
								lRecord.setAttribute("tipoRegNum", tipoRegNumItem.getValue());
								lRecord.setAttribute("siglaRegNum", siglaRegNumItem.getValue());
								lRecord.setAttribute("nroRegNum", nroRegNumItem.getValue());
								lRecord.setAttribute("annoRegNum", annoRegNumItem.getValue());											
								editNewRecord(lRecord.toMap());
								markForRedraw();
								setCanEdit(true);
							}
						}

						@Override
						public void manageError() {
							event.getForm().markForRedraw();
						}
					});
				}
			}
		});
		
		// Anno
		annoRegNumItem = new ExtendedNumericItem("annoRegNum", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_annoRegistrazioneItem_title());
		annoRegNumItem.setRequired(true);		
		annoRegNumItem.setColSpan(1);
		annoRegNumItem.setLength(4);
		annoRegNumItem.setWidth(70);
		annoRegNumItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUdFolder", (String) null);
				if (statoAttoItem.getValue() != null && statoAttoItem.getValue().equals("presente")) {
					recuperaIdUdAtto(new ServiceCallback<String>() {

						@Override
						public void execute(String idUdAtto) {
							if(idUdAtto != null && !"".equals(idUdAtto)) {
								loadDettaglio(idUdAtto, new ServiceCallback<Record>() {

									@Override
									public void execute(Record recordDettaglio) {
										if (recordDettaglio != null) {
											recordDettaglio.setAttribute("dataAdozione", recordDettaglio.getAttribute("tsRegistrazione"));
											editRecord(recordDettaglio);
											markForRedraw();
											setCanEdit(true);
										}
									}
								});
							} else {
								Record lRecord = new Record();		
								lRecord.setAttribute("statoAtto", statoAttoItem.getValue());
								lRecord.setAttribute("tipoRegNum", tipoRegNumItem.getValue());
								lRecord.setAttribute("siglaRegNum", siglaRegNumItem.getValue());
								lRecord.setAttribute("nroRegNum", nroRegNumItem.getValue());
								lRecord.setAttribute("annoRegNum", annoRegNumItem.getValue());											
								editNewRecord(lRecord.toMap());
								markForRedraw();
								setCanEdit(true);
							}
						}

						@Override
						public void manageError() {
							event.getForm().markForRedraw();
						}
					});
				}
			}
		});
		annoRegNumItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg") : "";
				String datiCollegamento = form.getValueAsString("datiCollegamento") != null ? form.getValueAsString("datiCollegamento") : "";				
				if(!"".equals(estremiReg) || !"".equals(datiCollegamento)) {
					return estremiReg  + " " + datiCollegamento;
				}
				return null;
			}
		});
		
		// Numero
		nroRegNumItem = new ExtendedNumericItem("nroRegNum", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_numeroRegistrazioneItem_title(), false);		
		nroRegNumItem.setRequired(true);
		nroRegNumItem.setColSpan(1);
		nroRegNumItem.setLength(7);
		nroRegNumItem.setWidth(110);
		nroRegNumItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUdFolder", (String) null);
				if (statoAttoItem.getValue() != null && statoAttoItem.getValue().equals("presente")) {
					recuperaIdUdAtto(new ServiceCallback<String>() {

						@Override
						public void execute(String idUdAtto) {
							if(idUdAtto != null && !"".equals(idUdAtto)) {
								loadDettaglio(idUdAtto, new ServiceCallback<Record>() {

									@Override
									public void execute(Record recordDettaglio) {
										if (recordDettaglio != null) {
											recordDettaglio.setAttribute("dataAdozione", recordDettaglio.getAttribute("tsRegistrazione"));
											editRecord(recordDettaglio);
											markForRedraw();
											setCanEdit(true);
										}
									}
								});
							} else {
								Record lRecord = new Record();		
								lRecord.setAttribute("statoAtto", statoAttoItem.getValue());
								lRecord.setAttribute("tipoRegNum", tipoRegNumItem.getValue());
								lRecord.setAttribute("siglaRegNum", siglaRegNumItem.getValue());
								lRecord.setAttribute("nroRegNum", nroRegNumItem.getValue());
								lRecord.setAttribute("annoRegNum", annoRegNumItem.getValue());											
								editNewRecord(lRecord.toMap());
								markForRedraw();
								setCanEdit(true);
							}
						}

						@Override
						public void manageError() {
							event.getForm().markForRedraw();
						}
					});
				}
				
			}
		});	
		nroRegNumItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {

				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg") : "";
				String datiCollegamento = form.getValueAsString("datiCollegamento") != null ? form.getValueAsString("datiCollegamento") : "";				
				if(!"".equals(estremiReg) || !"".equals(datiCollegamento)) {
					return estremiReg  + " " + datiCollegamento;
				}
				return null;
			}
		});
		
		// BOTTONI : seleziona dall'archivio
		ImgButtonItem lookupArchivioButton = new ImgButtonItem("lookupArchivioButton", "lookup/archivio.png", "Seleziona dall'archivio");	
		lookupArchivioButton.setEndRow(false);
		lookupArchivioButton.setColSpan(1);		
		lookupArchivioButton.addIconClickHandler(new IconClickHandler() {			
			@Override
			public void onIconClick(IconClickEvent event) {
									
				DocumentoCollegatoLookupArchivio lookupArchivioPopup = new DocumentoCollegatoLookupArchivio(attoForm.getValuesAsRecord());
				lookupArchivioPopup.show();				
			}
		});	  
		lookupArchivioButton.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return statoAttoItem.getValue() != null && statoAttoItem.getValueAsString().equals("presente");
			}
		});			
		
		// Data atto 
		tsRegistrazioneItem = new ExtendedDateItem("tsRegistrazione", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_dataAttoItem_title());
		tsRegistrazioneItem.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		tsRegistrazioneItem.setRequired(true);		
		tsRegistrazioneItem.setStartRow(true);
		tsRegistrazioneItem.setCanEdit(statoAttoItem.getValue() != null && statoAttoItem.getValueAsString().equals("da_caricare"));
		tsRegistrazioneItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				
				String dataAdozione = dataAdozioneItem != null && dataAdozioneItem.getValueAsDate() != null ?
						DateUtil.format(dataAdozioneItem.getValueAsDate()) : null;
				if(dataAdozione == null || "".equalsIgnoreCase(dataAdozione)) {
					dataAdozioneItem.setValue(tsRegistrazioneItem != null && tsRegistrazioneItem.getValueAsDate() != null ?
							tsRegistrazioneItem.getValueAsDate() : null);
				}
				attoForm.markForRedraw();
			}
		});
		
		dataAdozioneItem = new DateItem("dataAdozione", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_dataAdozioneItem_title());	
		dataAdozioneItem.setCanEdit(statoAttoItem.getValue() != null && (statoAttoItem.getValueAsString().equals("da_caricare") || statoAttoItem.getValueAsString().equals("presente")));

		// Data esecutività
		dataEsecutivitaItem = new DateItem("dataEsecutivita", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_dataEsecutivitaItem_title());			
		
		// Tipo atto
		GWTRestDataSource tipoAttoDS = new GWTRestDataSource("LoadComboTipoAttoDataSource", "idTipoDocumento", FieldType.TEXT, true);
		tipoItem = new SelectItem("tipo", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_tipoAttoItem_title()) {
			
			@Override
			public void clearSelect() {
				super.clearSelect();
				if(showIstanzeCollegateButton()) {
					istanzeInElencoButton.show();
				} else {
					istanzeInElencoButton.hide();
				}
			}
			
		};
		tipoItem.setRequired(true);		
		tipoItem.setOptionDataSource(tipoAttoDS);
		tipoItem.setAutoFetchData(true);
		tipoItem.setDisplayField("value");
		tipoItem.setValueField("key");
		tipoItem.setWidth(260);
		tipoItem.setWrapTitle(false);
		tipoItem.setColSpan(4);
		tipoItem.setClearable(true);
		tipoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				String giorniPubblicazione = event.getItem().getSelectedRecord().getAttributeAsString("giorniPubblicazione");
				giorniPubblicazioneItem.setValue(Integer.parseInt(giorniPubblicazione));
				manageOnChangedPeriodoPubbl("giorniPubblicazione");
				if(showIstanzeCollegateButton()) {
					istanzeInElencoButton.show();
				} else {
					istanzeInElencoButton.hide();
				}
			}
		});
		
		
		documentiCollegatiItem = new DocumentiCollegatiItem();		
		documentiCollegatiItem.setName("listaDocumentiCollegati");
		documentiCollegatiItem.setShowTitle(false);
		documentiCollegatiItem.setHidden(true);

		istanzeInElencoButton = new ImgButtonItem("istanzeInElencoButton", "buttons/addlink.png", "Istanze in elenco");
		istanzeInElencoButton.setAlwaysEnabled(true);
		istanzeInElencoButton.setColSpan(1);
		istanzeInElencoButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record record = new Record(vm.getValues());
				if(mode!=null && !mode.equals("view")) {
					record.setAttribute("abilGestioneCollegamentiUD", true);
				} else {
					record.setAttribute("abilGestioneCollegamentiUD", false);
				}
				DocumentiCollegatiPopup documentiCollegatiPopup = new DocumentiCollegatiPopup(record) {
					
					@Override
					public boolean showIstanzeInElencoPopup() {
						return true;
					}

					@Override
					public void onSaveButtonClick(RecordList listaDocumentiCollegati) {
//						if (listaDocumentiCollegati != null && listaDocumentiCollegati.getLength() > 0) {
							Record record = new Record(vm.getValues());
//							Record istanzeCollegate = new Record();
							record.setAttribute("listaDocumentiCollegati", listaDocumentiCollegati);
//							documentiCollegatiItem.setValue(listaDocumentiCollegati);
							attoForm.setValues(record.toMap());
//						} else {
//						}
						attoForm.markForRedraw();
					}
				};
				documentiCollegatiPopup.setTitle("Istanze in elenco");
			}
		});
		istanzeInElencoButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {					
				return showIstanzeCollegateButton();
			}
		});
		
		idUdFolderHiddenItem = new HiddenItem("idUdFolder");
		idRichPubblHiddenItem = new HiddenItem("idRichPubbl");
		isRettificaHiddenItem = new HiddenItem("isRettifica");		
		abilModificabileHiddenItem = new HiddenItem("abilModificabile");
		abilProrogaPubblicazioneHiddenItem = new HiddenItem("abilProrogaPubblicazione");
		abilAnnullamentoPubblicazioneHiddenItem = new HiddenItem("abilAnnullamentoPubblicazione");
		abilRettificaPubblicazioneHiddenItem = new HiddenItem("abilRettificaPubblicazione");
		
		attoForm.setItems(statoAttoItem,
				tipoRegNumItem, 
				siglaRegNumItem, 
				annoRegNumItem, 
				nroRegNumItem,
				lookupArchivioButton,
				tsRegistrazioneItem,
				dataAdozioneItem,
				dataEsecutivitaItem, 
				tipoItem,
				documentiCollegatiItem,
				istanzeInElencoButton,
				// hidden
				idUdFolderHiddenItem,
				idRichPubblHiddenItem,
				isRettificaHiddenItem,
				abilModificabileHiddenItem,
				abilProrogaPubblicazioneHiddenItem,
				abilAnnullamentoPubblicazioneHiddenItem,
				abilRettificaPubblicazioneHiddenItem
		);
    }
    
    protected boolean showMittenteInternoItem() {
    	String tipoMittente = tipoMittenteItem.getValueAsString();
		return isAbilMittenteInt() && tipoMittente != null && tipoMittente.equals("I");
    }
    
    protected boolean showMittenteEsternoItem() {
    	String tipoMittente = tipoMittenteItem.getValueAsString();
		return isAbilMittenteEst() && tipoMittente != null && tipoMittente.equals("E");
    }

    protected void createFormMittenteRichiedente() {
    	
    	mittenteRichiedenteForm = new DynamicForm();
    	mittenteRichiedenteForm.setValuesManager(vm);
    	mittenteRichiedenteForm.setHeight("5");
    	mittenteRichiedenteForm.setPadding(5);
    	mittenteRichiedenteForm.setWrapItemTitles(false);
    	mittenteRichiedenteForm.setNumCols(10);
    	mittenteRichiedenteForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
    	
    	mittenteInternoItem =  new MittenteProtInternaItem() {
	    	
    		@Override
	    	public boolean getFlgSoloInOrganigramma() {
	    		return true;
	    	};
	    	
	    	@Override
	    	public String getFinalitaLookupOrganigramma() {
	    		return "SEL_RICH_PUBBL";
	    	};
	    	
	    	@Override
	    	public String getFinalitaOrganigramma() {
	    		return "SEL_RICH_PUBBL";
	    	}
	    	
	    	@Override
	    	public boolean isObbligatorio() {
	    		return true;
	    	}
	    	
	    	@Override
	    	public boolean isRichiestaPubblicazioneAlbo() {
	    		return true;
	    	}
	    	
	    	@Override
			public boolean skipValidation() {
				if(showMittenteInternoItem()) {
					return super.skipValidation(); //TODO Verificare se quando chiamo super.skipValidation() mi torna true quando sono su un altro tab
				}
				return true;
			}
	    	
	    	@Override
			public Boolean getShowRemoveButton() {
				return false;
			}
	    };
	    mittenteInternoItem.setNotReplicable(true);
	    mittenteInternoItem.setStartRow(true);
	    mittenteInternoItem.setShowTitle(false);
	    mittenteInternoItem.setColSpan(10);
	    mittenteInternoItem.setName("mittenteRichiedenteInterno");
	    mittenteInternoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showMittenteInternoItem()) {
					mittenteInternoItem.setAttribute("obbligatorio", true);
				} else {
					mittenteInternoItem.setAttribute("obbligatorio", false);
				}
				//mittenteInternoItem.storeValue(form.getValueAsRecordList(mittenteInternoItem.getName()));				
				return showMittenteInternoItem();
			}
		});
	    
	    mittenteEsternoItem = new MittenteProtEntrataItem() {
	    	
	    	@Override
			public boolean skipValidation() {
				if(showMittenteEsternoItem()) {
					return super.skipValidation(); //TODO Verificare se quando chiamo super.skipValidation() mi torna true quando sono su un altro tab
				}
				return true;
			}
	    	
	    	@Override
			public Boolean getShowRemoveButton() {
				return false;
			}
	    };
	    mittenteEsternoItem.setNotReplicable(true);
	    mittenteEsternoItem.setStartRow(true);
	    mittenteEsternoItem.setShowTitle(false);
	    mittenteEsternoItem.setColSpan(10);
	    mittenteEsternoItem.setName("mittenteRichiedenteEsterno");
	    mittenteEsternoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showMittenteEsternoItem()) {
					mittenteEsternoItem.setAttribute("obbligatorio", true);
				} else {
					mittenteEsternoItem.setAttribute("obbligatorio", false);
				}
				//mittenteEsternoItem.storeValue(form.getValueAsRecordList(mittenteEsternoItem.getName()));				
				return showMittenteEsternoItem();
			}
		});

    	// Tipo mittente :  interno/esterno
        LinkedHashMap<String, String> tipoMittenteMap = new LinkedHashMap<String, String>();  
        tipoMittenteMap.put("I", "interno");  
        tipoMittenteMap.put("E", "esterno");  
        tipoMittenteItem = new RadioGroupItem("tipoMittente"); 
        tipoMittenteItem.setTitle(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_tipoMittenteItem_title());
        tipoMittenteItem.setValueMap(tipoMittenteMap); 
        tipoMittenteItem.setVertical(false);
        tipoMittenteItem.setWrap(false);	
        tipoMittenteItem.setTitleOrientation(TitleOrientation.LEFT);
        tipoMittenteItem.setWidth(120);
        tipoMittenteItem.setStartRow(true);	
        if (isAbilMittenteEst() && isAbilMittenteInt()) {
        	tipoMittenteItem.setDefaultValue("I");
		} else if(isAbilMittenteEst()) {
			tipoMittenteItem.setDefaultValue("E");
		} else if(isAbilMittenteInt()) {
			tipoMittenteItem.setDefaultValue("I");
		}
        tipoMittenteItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mittenteRichiedenteForm.markForRedraw();
			}
		});
       
		mittenteRichiedenteForm.addDrawHandler(new DrawHandler() {
			
			@Override
			public void onDraw(DrawEvent event) {
				if(isAbilMittenteInt() && !isAbilMittenteEst()) {
					tipoMittenteItem.setValueDisabled("E", true);
				} else if(isAbilMittenteEst() && !isAbilMittenteInt()) {
					tipoMittenteItem.setValueDisabled("I", true);
				}
			}
		});

        mittenteRichiedenteForm.setFields(tipoMittenteItem, mittenteEsternoItem, mittenteInternoItem);	
    }
    
	protected void createFormOggetto() {
		
		oggettoForm = new DynamicForm();
		oggettoForm.setValuesManager(vm);
		oggettoForm.setWidth100();
		oggettoForm.setPadding(5);
		oggettoForm.setWrapItemTitles(false);
		oggettoForm.setNumCols(20);
		oggettoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		oggettoForm.setHeight(1);
		
		oggettoItem = new HiddenItem("oggetto");
		
		oggettoHtmlItem = new CKEditorItem("oggettoHtml", getLengthOggettoHtmlItem(), "restricted", 4, -1, "", getUpperCaseOggettoHtmlItem(), false);
		oggettoHtmlItem.setShowTitle(false);
		oggettoHtmlItem.setColSpan(20);
		oggettoHtmlItem.setWidth("100%");	
		oggettoHtmlItem.setRequired(true);
		oggettoHtmlItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if(oggettoHtmlItem.hasDatiSensibili()) {
					formaPubblicazioneItem.setValue("PER_ESTRATTO");
				}
			}
		});
		
		oggettoForm.setFields(oggettoItem, oggettoHtmlItem);			
	}
	
	public boolean hasDatiSensibili() {
		if (isAttivaModalitaAllegatiGrid()) {
			if(((AllegatiGridItem) fileAllegatiItem).isFlgOmissisChecked()) {
				return true;
			}
		} else {
			if(((AllegatiItem)fileAllegatiItem).isFlgOmissisChecked()) {
				return true;
			}
		}
		if(flgDatiSensibiliItem.getValueAsBoolean() != null && flgDatiSensibiliItem.getValueAsBoolean()) {
			return true;
		}
		if (oggettoHtmlItem.hasDatiSensibili()) {
			return true;
		}
		return false;
	}
	
	@Override
	public void editNewRecord() {
		
		valuesOrig = null; 
				
		super.editNewRecord();
	}
	
	@Override
	public void editNewRecord(Map initialValues) {
		
		valuesOrig = initialValues;
		
		super.editNewRecord(initialValues);
		
		if (tipoItem != null) {
			if (initialValues != null && initialValues.get("tipo") != null && !"".equalsIgnoreCase((String) initialValues.get("tipo")) && 
				initialValues.get("nomeTipo") != null && !"".equalsIgnoreCase((String) initialValues.get("nomeTipo"))){			
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put((String)initialValues.get("tipo"), (String)initialValues.get("nomeTipo"));
				tipoItem.setValueMap(valueMap);
				tipoItem.setValue((String)initialValues.get("tipo"));			
			}
		}
		
		if (oggettoHtmlItem != null) {
			oggettoHtmlItem.setValue((String) initialValues.get("oggettoHtml"));
		}
		
		if(flgDatiSensibiliItem != null && flgDatiSensibiliItem.getValueAsBoolean() != null && flgDatiSensibiliItem.getValueAsBoolean()) {
			formaPubblicazioneItem.setValue("PER_ESTRATTO");
		} else if(fileAllegatiItem != null) {
			if(fileAllegatiItem instanceof AllegatiGridItem) {
				if(((AllegatiGridItem) fileAllegatiItem).isFlgOmissisChecked()) {
					formaPubblicazioneItem.setValue("PER_ESTRATTO");
				}
			} else {
				if(((AllegatiItem)fileAllegatiItem).isFlgOmissisChecked()) {
					formaPubblicazioneItem.setValue("PER_ESTRATTO");
				} 
			}
		}
	}
	
	@Override
	public void editRecord(Record record) {
		
		valuesOrig = record.toMap();
		
		super.editRecord(record);

		if (tipoItem != null) {
			if (record.getAttribute("tipo") != null && !"".equalsIgnoreCase(record.getAttribute("tipo")) && 
				record.getAttribute("nomeTipo") != null && !"".equalsIgnoreCase(record.getAttribute("nomeTipo"))){
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("tipo"), record.getAttribute("nomeTipo"));
				tipoItem.setValueMap(valueMap);
				tipoItem.setValue(record.getAttribute("tipo"));			
			}
		}

		if(oggettoHtmlItem != null) {
			oggettoHtmlItem.setValue(record.getAttribute("oggettoHtml"));
		}
		
		if(flgDatiSensibiliItem != null && flgDatiSensibiliItem.getValueAsBoolean() != null && flgDatiSensibiliItem.getValueAsBoolean()) {
			formaPubblicazioneItem.setValue("PER_ESTRATTO");
		} else if(fileAllegatiItem != null) {
			if(fileAllegatiItem instanceof AllegatiGridItem) {
				if(((AllegatiGridItem) fileAllegatiItem).isFlgOmissisChecked()) {
					formaPubblicazioneItem.setValue("PER_ESTRATTO");
				}
			} else {
				if(((AllegatiItem)fileAllegatiItem).isFlgOmissisChecked()) {
					formaPubblicazioneItem.setValue("PER_ESTRATTO");
				} 
			}
		}
	}
	
	@Override
	public Record getRecordToSave() {
		
		Record lRecordToSave = new Record();
		
		addFormValues(lRecordToSave, attoForm);
		addFormValues(lRecordToSave, mittenteRichiedenteForm);
		addFormValues(lRecordToSave, oggettoForm);
		addFormValues(lRecordToSave, filePrimarioForm);
		addFormValues(lRecordToSave, fileAllegatiForm);
		addFormValues(lRecordToSave, periodoPubblicazioneForm);
		addFormValues(lRecordToSave, rettificaForm);
		addFormValues(lRecordToSave, formaPubblicazioneForm);
		addFormValues(lRecordToSave, notePubblicazioneForm);
		
		lRecordToSave.setAttribute("oggettoHtml", oggettoHtmlItem != null ? oggettoHtmlItem.getValue() : null);
		if(tipoItem.getValue() != null && !"".equalsIgnoreCase((String)tipoItem.getValue())) {
			lRecordToSave.setAttribute("nomeTipo", tipoItem.getDisplayValue());
		}
		return lRecordToSave;
	}
	
	@Override
	public boolean customValidate() {
		boolean valid = super.customValidate();
		if(oggettoHtmlItem != null) {
			valid = oggettoHtmlItem.validate() && valid;
		}
		return valid;
	}

	public class DocumentoCollegatoLookupArchivio extends LookupArchivioPopup {

		public DocumentoCollegatoLookupArchivio(Record record) {
			super(record, true);			
		}

		public DocumentoCollegatoLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, true);			
		}

		@Override
		public String getWindowTitle() {
			return "Seleziona atto da pubblicare";
		}
		
		@Override
		public String getFinalita() {			
			return "RICH_PUBBLICAZIONE";
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

	public void setFormValuesFromRecordArchivio(Record record) {
		attoForm.clearErrors(true);	
		String idUdAtto = record.getAttribute("idUdFolder");
		if(idUdAtto != null && !"".equals(idUdAtto)) {
			loadDettaglio(idUdAtto, new ServiceCallback<Record>() {

				@Override
				public void execute(Record recordDettaglio) {
					if (recordDettaglio != null) {
						recordDettaglio.setAttribute("dataAdozione", recordDettaglio.getAttribute("tsRegistrazione"));
						editRecord(recordDettaglio);
						markForRedraw();
						setCanEdit(true);
					}
				}
			});
		} else {
			editNewRecord();
			markForRedraw();
			setCanEdit(true);
		}
	}	
	
	public int getLengthOggettoHtmlItem() {
		String length = AurigaLayout.getParametroDB("OGGETTO_ATTI_LENGHT");
		return length != null && !"".equals(length) ? Integer.parseInt(length) : 4000;
	}

	public boolean getUpperCaseOggettoHtmlItem() {
		Boolean uppercase = AurigaLayout.getParametroDBAsBoolean("UPPERCASE_OGGETTO_ATTO");
		return uppercase != null && uppercase;
	}
	
	protected void createAllegatiItem() {
		
		if (isAttivaModalitaAllegatiGrid()) {
			fileAllegatiItem = new AllegatiGridItem("listaAllegati", "listaAllegatiProt") {

				@Override
				public boolean isGrigliaEditabile() {
					return true;
				}

				@Override
				public boolean getShowVersioneOmissis() {
					return true;
				}

				@Override
				public String getFlgTipoProvProtocollo() {
					return getFlgTipoProv();
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
				public boolean isHideAcquisisciDaScannerInAltreOperazioniButton() {
					return !enableAcquisisciDaScannerMenuItem();
				};

				@Override
				public boolean sonoInMail() {
					return isFromEmail();
				};

				@Override
				public void clickTrasformaInPrimario(int index) {
					clickTrasfInPrimario(index);
				};

				@Override
				public boolean sonoModificaVisualizza() {
					return !mode.equals("new");
				}

				@Override
				public boolean canBeEditedByApplet() {
					return canEditByApplet();
				}

				@Override
				public boolean isAttivaTimbraturaFilePostRegAllegato() {
					return isAttivaTimbraturaFilePostReg();
				}

				@Override
				public boolean getShowGeneraDaModello() {
					return showGeneraDaModelloInAllegatiItem();
				}

				@Override
				public Record getRecordCaricaModelloAllegato(String idModello, String tipoModello) {
					return getRecordCaricaModelloInAllegati(idModello, tipoModello);
				}

				@Override
				public void manageChangedFileAllegati() {
					manageChangedFileInAllegatiItem();
				}

				@Override
				public void manageChangedFileAllegatiParteDispositivo() {
					manageChangedFileParteDispositivoInAllegatiItem();
				}

				@Override
				public boolean isFormatoAmmesso(InfoFileRecord info) {
					return isFormatoAmmessoFileAllegato(info);
				}

				@Override
				public boolean isAttivaTimbroTipologia() {
					return isAttivaTimbroTipologiaProtocollazione();
				}

				@Override
				public boolean isAttivaVociBarcode() {

					String idUd = (idUdFolderHiddenItem.getValue() != null) ? String.valueOf(idUdFolderHiddenItem.getValue())
							: null;
					return idUd != null && !"".equals(idUd);
				}

				@Override
				public String getIdDocFilePrimario() {

					String idDocPrimario = (idDocPrimarioHiddenItem.getValue() != null)
							? String.valueOf(idDocPrimarioHiddenItem.getValue())
							: null;
					return idDocPrimario;
				}

				@Override
				public String getFinalitaImportaAllegatiMultiLookupArchivio() {
					return "IMPORTA_UD";
				}

				@Override
				public String getImportaFileDocumentiButtonTitle() {
					return I18NUtil.getMessages().protocollazione_detail_importaDocumentiDaAltriDocumenti_title();
				}

				@Override
				public boolean getShowTimbraBarcodeMenuOmissis() {
					return true;
				}

				@Override
				public String getIdUd() {
					return idUdFolderHiddenItem.getValue() != null ? String.valueOf(idUdFolderHiddenItem.getValue()) : null;
				}

				@Override
				public boolean getShowFlgParteDispositivo() {
					return showFlgParteDispositivoInAllegatiItem();
				}

				@Override
				public boolean getShowFlgNoPubblAllegato() {
					return showFlgNoPubblInAllegatiItem();
				}

				@Override
				public boolean getShowFlgSostituisciVerPrec() {
					return showFlgSostituisciVerPrecItem();
				}

				@Override
				public boolean getShowImportaFileDaDocumenti() {
					return showImportaFileDaDocumentiInAllegatiItem();
				}

				@Override
				public void onRecordSelected(Record record) {
					// TODO Auto-generated method stub
					
				}
			};
			fileAllegatiItem.setShowTitle(false);
			fileAllegatiItem.setHeight(200);
			fileAllegatiItem.addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					if(((AllegatiGridItem) fileAllegatiItem).isFlgOmissisChecked()) {
						formaPubblicazioneItem.setValue("PER_ESTRATTO");
					}
					fileAllegatiForm.markForRedraw();
				}
				
			});
		} else {
			fileAllegatiItem = new AllegatiItem() {
				
				@Override
				public boolean abilitaModificaFlgEsclusionePubblicazione() {
					return true;
				}
				
				@Override
				public boolean getShowVersioneOmissis() {
					return true;
				}
				
				@Override
				public boolean showAltreOp() {
					return true;
				}
				@Override
				public boolean showAltreOpOmissis() {
					return true;
				}
				@Override
				public String getFlgTipoProvProtocollo() {
					return getFlgTipoProv();
				}
				
				public HashSet<String> getTipiModelliAttiAllegati() {
					return getTipiModelliAttiInAllegati();
				}
				
				@Override
				public boolean isObbligatorioFile() {
					return isObbligatorioFileInAllegati();
				}

				public boolean showAcquisisciDaScanner() {
					return enableAcquisisciDaScannerMenuItem();
				};

				public boolean sonoInMail() {
					return isFromEmail();
				};

				public void clickTrasformaInPrimario(int index) {
					clickTrasfInPrimario(index);
				};

				public boolean sonoModificaVisualizza() {
					return !mode.equals("new");
				}

				public boolean canBeEditedByApplet() {
					return canEditByApplet();
				}

				public boolean showGeneraDaModello() {
					return showGeneraDaModelloInAllegatiItem();
				}
				
				@Override
				public boolean isAttivaTimbraturaFilePostRegAllegato() {
					return isAttivaTimbraturaFilePostReg();
				}

				@Override
				public Record getRecordCaricaModelloAllegato(String idModello, String tipoModello) {
					return getRecordCaricaModelloInAllegati(idModello, tipoModello);
				}

				public void manageChangedFileAllegati() {
					manageChangedFileInAllegatiItem();
				}

				public void manageChangedFileAllegatiParteDispositivo() {
					manageChangedFileParteDispositivoInAllegatiItem();
				}

				public boolean isFormatoAmmesso(InfoFileRecord info) {
					return isFormatoAmmessoFileAllegato(info);
				}
				
				@Override
				public boolean isAttivaTimbroTipologia() {
					return isAttivaTimbroTipologiaProtocollazione();
				}
				
				@Override
				public boolean isAttivaVociBarcode() {
					
					String idUd = (idUdFolderHiddenItem.getValue() != null) ? String.valueOf(idUdFolderHiddenItem.getValue()) : null;
					return idUd != null && !"".equals(idUd);
				}
				
				@Override
				public String getIdDocFilePrimario() {
					
					String idDocPrimario = (idDocPrimarioHiddenItem.getValue() != null) ? String.valueOf(idDocPrimarioHiddenItem.getValue()) : null;
					return idDocPrimario;
				}
				
				@Override
				public String getFinalitaImportaAllegatiMultiLookupArchivio() {
					return  "IMPORTA_UD";
				}
				
				@Override
				public String getImportaFileDocumentiBtnTitle() {
					return  I18NUtil.getMessages().protocollazione_detail_importaDocumentiDaAltriDocumenti_title();
				}
				
				@Override
				public boolean showTimbraBarcodeMenuOmissis() {
					return true;
				}
				
				@Override
				public String getIdUd() {
					return idUdFolderHiddenItem.getValue() != null ? String.valueOf(idUdFolderHiddenItem.getValue()) : null;
				}
			};
			((AllegatiItem)fileAllegatiItem).setName("listaAllegati");
			((AllegatiItem)fileAllegatiItem).setShowTitle(false);
			((AllegatiItem)fileAllegatiItem).setShowFlgParere(showFlgParteDispositivoInAllegatiItem());
			((AllegatiItem)fileAllegatiItem).setShowFlgParteDispositivo(showFlgParteDispositivoInAllegatiItem());
			((AllegatiItem)fileAllegatiItem).setShowFlgNoPubblAllegato(showFlgNoPubblInAllegatiItem());
			((AllegatiItem)fileAllegatiItem).setShowFlgSostituisciVerPrec(showFlgSostituisciVerPrecItem());
			((AllegatiItem)fileAllegatiItem).setShowImportaFileDaDocumenti(showImportaFileDaDocumentiInAllegatiItem());
			((AllegatiItem)fileAllegatiItem).addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					if(((AllegatiItem)fileAllegatiItem).isFlgOmissisChecked()) {
						formaPubblicazioneItem.setValue("PER_ESTRATTO");
					} 
					fileAllegatiForm.markForRedraw();
				}
				
			});
		}
		
	}

	/**
	 * Metodo che indica il tipo di provenienza della registrazione
	 * 
	 */
	public String getFlgTipoProv() {
		return null;
	}
	
	/**
	 * Metodo che restituisce l'identificativo del processo, da passare alla
	 * sezione degli allegati
	 * 
	 */	
	public String getIdProcessTask() {
		return (String) idProcessHiddenItem.getValue();
	}
	
	/**
	 * Metodo che restituisce il tipo del processo, da passare alla sezione
	 * degli allegati
	 * 
	 */
	public String getIdProcessTypeTask() {
		return null;
	}
	
	/**
	 * Metodo che restituisce l'identificativo del task corrente, da passare
	 * alla sezione degli allegati
	 * 
	 */
	public String getIdTaskCorrente() {
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
	
	/**
	 * Metodo che indica se il file negli allegati è obbligatorio, o se basta specificare uno tra tipo e descrizione
	 * 
	 */
	public boolean isObbligatorioFileInAllegati() {
		return false;
	}
	
	/**
	 * Metodo che indica se è abilitata o meno l'acquisizione da scanner
	 * 
	 */
	public boolean enableAcquisisciDaScannerMenuItem() {
		return !isFromEmail() && showUploadFilePrimario();
	}
	
	/**
	 * Metodo che indica se provengo da una mail in arrivo (protocollata in
	 * entrata)
	 * 
	 */
	public boolean isFromEmail() {
		return false;
	}
	
	/**
	 * Metodo che indica se mostrare o meno il bottone di upload del file
	 * primario (abilita/disabilita anche l'acquisizione da scanner)
	 * 
	 */
	public boolean showUploadFilePrimario() {
		return true;
	}

	/**
	 * Metodo che scambia l'allegato della posizione passata in input con il
	 * file primario
	 * 
	 */
	protected void clickTrasfInPrimario(int index) {

		// ottavio : da fare ......
	}
	
	public boolean canEditByApplet() {
		return isProtocollazioneDetailBozze() || isProtocollazioneDetailAtti();
	}

	/**
	 * Metodo che indica se mi trovo nella maschera di una bozza
	 * 
	 */
	public boolean isProtocollazioneDetailBozze() {
		return false;
	}
	
	public boolean isProtocollazioneDetailAtti() {
		return false;
	}
	
	/**
	 * Metodo che indica se mostrare o meno il bottone "Genera da modello" nella
	 * sezione "Allegati"
	 * 
	 */
	public boolean showGeneraDaModelloInAllegatiItem() {
		return false;
	}
	
	public boolean isAttivaTimbraturaFilePostReg() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_FILE_POST_REG") && !isTaskDetail();
	}
	
	/**
	 * Metodo che indica se la maschera è utilizzata all'interno di un iter
	 * procedurale
	 * 
	 */
	public boolean isTaskDetail() {
		return false;
	}

	/**
	 * Metodo che viene richiamato quando cambia il contenuto (si fa il
	 * confronto delle impronte dei file) o il nome di un file allegato
	 * 
	 */
	public void manageChangedFileInAllegatiItem() {

	}
	
	/**
	 * Metodo che viene richiamato quando cambia il contenuto (si fa il
	 * confronto delle impronte dei file) o il nome di un file allegato che è
	 * parte dispositivo
	 * 
	 */
	public void manageChangedFileParteDispositivoInAllegatiItem() {

	}

	/**
	 * Metodo che indica se il file allegato è in un formato ammesso
	 * 
	 */
	public boolean isFormatoAmmessoFileAllegato(InfoFileRecord info) {
		return true;
	}
	
	/**
	 * Metodo che indica se sono da attivare le voci di menù per la stampa dei barcode ( Su A4 ed Etichetta )
	 * con tipologia
	 */
	public boolean isAttivaTimbroTipologiaProtocollazione(){
		return vm.getValue("attivaTimbroTipologia") != null && ((Boolean) vm.getValue("attivaTimbroTipologia"));
	}
	
	/**
	 * Metodo che indica se mostrare o meno il check "parte disp." nella sezione
	 * "Allegati"
	 * 
	 */
	public boolean showFlgParteDispositivoInAllegatiItem() {
		return isTaskDetail() || isProtocollazioneDetailAtti() || isProtocollazioneDetailBozze(); // ATTI
	}
	
	/**
	 * Metodo che indica se mostrare o meno il check "escludi pubbl." nella sezione
	 * "Allegati"
	 * 
	 */
	public boolean showFlgNoPubblInAllegatiItem() {
		return true;
	}
	
	/**
	 * Metodo che indica se mostrare o meno il check "sostituisci alla ver.
	 * prec"
	 * 
	 */
	public boolean showFlgSostituisciVerPrecItem() {
		return false; // BOZZE
	}

	/**
	 * Metodo che indica se mostrare o meno il bottone "Importa file da altri
	 * documenti" nella sezione "Allegati"
	 * 
	 */
	public boolean showImportaFileDaDocumentiInAllegatiItem() {
		if (getFlgTipoProv() != null && "E".equals(getFlgTipoProv())) { // PROT. ENTRATA E REG. FATTURE
			return false;
		}
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
		String idUd = (idUdFolderHiddenItem.getValue() != null) ? String.valueOf(idUdFolderHiddenItem.getValue()) : null;
		modelloRecord.setAttribute("idUd", idUd);
		/*
		if (attributiAddDocDetails != null) {
			modelloRecord.setAttribute("valori", getAttributiDinamiciDoc());
			modelloRecord.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
		}
		*/
		return modelloRecord;
	}
	
	/**
	 * Metodo che crea il form del file primario
	 * 
	 */
	protected void createFormFilePrimario() {

		filePrimarioForm = new DynamicForm();
		filePrimarioForm.setValuesManager(vm);
		filePrimarioForm.setWidth100();
		filePrimarioForm.setPadding(5);
		filePrimarioForm.setNumCols(14);
		filePrimarioForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		filePrimarioForm.setWrapItemTitles(false);

		idDocPrimarioHiddenItem = new HiddenItem("idDocPrimario");
		idAttachEmailPrimarioItem = new HiddenItem("idAttachEmailPrimario");
		uriFilePrimarioItem = new HiddenItem("uriFilePrimario");
		infoFileItem = new HiddenItem("infoFile");
		remoteUriFilePrimarioItem = new HiddenItem("remoteUriFilePrimario");
		isDocPrimarioChangedItem = new HiddenItem("isDocPrimarioChanged");
		isDocPrimarioChangedItem.setDefaultValue(false);
		nomeFilePrimarioOrigItem = new HiddenItem("nomeFilePrimarioOrig");
		nomeFilePrimarioTifItem = new HiddenItem("nomeFilePrimarioTif");
		uriFilePrimarioTifItem = new HiddenItem("uriFilePrimarioTif");		
		nomeFileVerPreFirmaItem = new HiddenItem("nomeFileVerPreFirma");
		uriFileVerPreFirmaItem = new HiddenItem("uriFileVerPreFirma");
		improntaVerPreFirmaItem = new HiddenItem("improntaVerPreFirma");
		infoFileVerPreFirmaItem = new HiddenItem("infoFileVerPreFirma");
		nroLastVerItem = new HiddenItem("nroLastVer");
		noteMancanzaFileItem = new HiddenItem("noteMancanzaFile");
		
		// file primario
		nomeFilePrimarioItem = new ExtendedTextItem("nomeFilePrimario", getTitleNomeFilePrimario());
		nomeFilePrimarioItem.setStartRow(true);
		// nomeFilePrimarioItem.setColSpan(4);
		nomeFilePrimarioItem.setWidth(getWidthNomeFilePrimario());

		nomeFilePrimarioItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (nomeFilePrimarioItem.getValue() == null
						|| ((String) nomeFilePrimarioItem.getValue()).trim().equals("")) {
					clickEliminaFile();
				} else if (filePrimarioForm.getValueAsString("nomeFilePrimarioOrig") != null
						&& !"".equals(filePrimarioForm.getValueAsString("nomeFilePrimarioOrig"))
						&& !nomeFilePrimarioItem.getValue()
								.equals(filePrimarioForm.getValueAsString("nomeFilePrimarioOrig"))) {
					manageChangedFilePrimario();
				}
				if(filePrimarioForm != null) {
					filePrimarioForm.markForRedraw();
				}
				if(filePrimarioButtons != null) {
					filePrimarioButtons.markForRedraw();
				}
				manageChangedPrimario();
			}
		});

		if(showFilePrimarioForm()) {
			List<CustomValidator> filePrimarioValidators = new ArrayList<CustomValidator>();
	
			if(AurigaLayout.getParametroDBAsBoolean("FILE_REG_IN_UNICA_SOLUZIONE") && !isProtocollazioneDetailBozze()) {
				CustomValidator lRequiredIfAllegatiPresenti = new CustomValidator() {
					
					@Override
					protected boolean condition(Object value) {
						boolean isValorizzatoPrimario = (value != null && !"".equals(value)) && uriFilePrimarioItem.getValue() != null
								&& !"".equals(uriFilePrimarioItem.getValue());
						RecordList listaAllegati = fileAllegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");
						boolean isValorizzatoAllegati = listaAllegati != null && listaAllegati.get(0).getAttribute("uriFileAllegato") != null && !"".equals(listaAllegati.get(0).getAttributeAsString("uriFileAllegato"));
						if (!isValorizzatoPrimario && isValorizzatoAllegati) {
							return false;
						} 
						return true;
					}
				};
				lRequiredIfAllegatiPresenti.setErrorMessage("File primario obbligatorio se presenti allegati");
				filePrimarioValidators.add(lRequiredIfAllegatiPresenti);
			}

			if (isRequiredFilePrimario()) {
				nomeFilePrimarioItem.setAttribute("obbligatorio", true);
				CustomValidator lRequiredFilePrimarioValidator = new CustomValidator() {
	
					@Override
					protected boolean condition(Object value) {
						return (value != null && !"".equals(value)) && uriFilePrimarioItem.getValue() != null
								&& !"".equals(uriFilePrimarioItem.getValue());
					}
				};
				lRequiredFilePrimarioValidator.setErrorMessage("Campo obbligatorio");
				filePrimarioValidators.add(lRequiredFilePrimarioValidator);
			}
	
			CustomValidator lFormatoAmmessoFilePrimarioValidator = new CustomValidator() {
	
				@Override
				protected boolean condition(Object value) {
					InfoFileRecord infoFile = filePrimarioForm.getValue("infoFile") != null
							? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
					return isFormatoAmmessoFilePrimario(infoFile);
				}
			};
			lFormatoAmmessoFilePrimarioValidator.setErrorMessage("Formato non ammesso");
			filePrimarioValidators.add(lFormatoAmmessoFilePrimarioValidator);
	
			nomeFilePrimarioItem.setValidators(filePrimarioValidators.toArray(new CustomValidator[filePrimarioValidators.size()]));
		}
		
		uploadFilePrimarioItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				filePrimarioForm.setValue("nomeFilePrimario", displayFileName);
				filePrimarioForm.setValue("uriFilePrimario", uri);
				filePrimarioForm.setValue("nomeFilePrimarioTif", "");
				filePrimarioForm.setValue("uriFilePrimarioTif", "");
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
				filePrimarioForm.setValue("nomeFileVerPreFirma", displayFileName);
				filePrimarioForm.setValue("uriFileVerPreFirma", uri);
				changedEventAfterUpload(displayFileName, uri);
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
				if(filePrimarioForm != null) {
					filePrimarioForm.markForRedraw();
				}
				if(filePrimarioButtons != null) {
					filePrimarioButtons.markForRedraw();
				}
				manageChangedPrimario();
				uploadFilePrimarioItem.getCanvas().redraw();

			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				filePrimarioForm.setValue("infoFile", info);
				filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
				filePrimarioForm.setValue("infoFileVerPreFirma", info);		
				String nomeFilePrimario = filePrimarioForm.getValueAsString("nomeFilePrimario");
				String nomeFilePrimarioOrig = filePrimarioForm.getValueAsString("nomeFilePrimarioOrig");
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())
						|| (nomeFilePrimario != null && !"".equals(nomeFilePrimario) && nomeFilePrimarioOrig != null
								&& !"".equals(nomeFilePrimarioOrig)
								&& !nomeFilePrimario.equals(nomeFilePrimarioOrig))) {
					manageChangedFilePrimario();
				}
				if (!isFormatoAmmessoFilePrimario(info)) {
					GWTRestDataSource.printMessage(new MessageBean(getFormatoNonAmmessoFilePrimarioWarning(),
							"", MessageType.ERROR));
					
					/*
					 * Visto l'errore dovuto al fatto che il file non è nel formato richiesto
					 * rimuovo le informazioni associate al file primario di cui si è tentato l'inserimento.
					 * In questo modo all'aggiornamento della grafica non viene riempita la text e non vengono
					 * abilitati i pulsanti di firma, etc. (normalmente abilitati nel caso di file in 
					 * formato corretto) 
					 */
					clickEliminaFile();
				}
				if (info.isFirmato() && !info.isFirmaValida()) {
					GWTRestDataSource.printMessage(new MessageBean(
							"Il file presenta una firma non valida alla data odierna", "", MessageType.WARNING));
				}
				if(filePrimarioForm != null) {
					filePrimarioForm.markForRedraw();
				}
				if(filePrimarioButtons != null) {
					filePrimarioButtons.markForRedraw();
				}
				manageChangedPrimario();
			}
		});
		uploadFilePrimarioItem.setColSpan(1);
		uploadFilePrimarioItem.setAttribute("nascosto", !showUploadFilePrimario());
		uploadFilePrimarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				uploadFilePrimarioItem.setAttribute("nascosto", !showUploadFilePrimario());
				return showUploadFilePrimario();
			}
		});

		createFilePrimarioButtons();
		
		improntaItem = new TextItem("impronta", "Impronta") {
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		improntaItem.setWidth(350);
		improntaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				improntaItem.setCanEdit(false);
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					String impronta = lInfoFileRecord != null && lInfoFileRecord.getImpronta() != null ? lInfoFileRecord.getImpronta() : "";
					improntaItem.setValue(impronta);
					return impronta != null && !"".equals(impronta) && AurigaLayout.getParametroDBAsBoolean("SHOW_IMPRONTA_FILE");
				} else {
					improntaItem.setValue("");
					return false;
				}
			}
		});
		
		flgSostituisciVerPrecItem = new CheckboxItem("flgSostituisciVerPrec", "sostituisci alla ver. prec");
		flgSostituisciVerPrecItem.setWidth("*");
		flgSostituisciVerPrecItem.setDefaultValue(false);
		flgSostituisciVerPrecItem.setVisible(false);

		flgOriginaleCartaceoItem = new CheckboxItem("flgOriginaleCartaceo", "originale cartaceo");
		flgOriginaleCartaceoItem.setValue(false);
		flgOriginaleCartaceoItem.setColSpan(1);
		flgOriginaleCartaceoItem.setWidth("*");
		// flgOriginaleCartaceoItem.setLabelAsTitle(true);
		flgOriginaleCartaceoItem.setShowTitle(true);
		flgOriginaleCartaceoItem.setWrapTitle(false);
		flgOriginaleCartaceoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgOriginaleCartaceoECopiaSostitutivaPrimario();
			}
		});
		flgOriginaleCartaceoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				filePrimarioForm.markForRedraw();
				manageChangedPrimario();
			}
		});

		flgCopiaSostitutivaItem = new CheckboxItem("flgCopiaSostitutiva", "copia sostitutiva");
		flgCopiaSostitutivaItem.setValue(false);
		flgCopiaSostitutivaItem.setColSpan(1);
		flgCopiaSostitutivaItem.setWidth("*");
		// flgCopiaSostitutivaItem.setLabelAsTitle(true);
		flgCopiaSostitutivaItem.setShowTitle(true);
		flgCopiaSostitutivaItem.setWrapTitle(false);
		flgCopiaSostitutivaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgOriginaleCartaceoECopiaSostitutivaPrimario() && (flgOriginaleCartaceoItem.getValue() != null
						&& (Boolean) flgOriginaleCartaceoItem.getValue());
			}
		});
		flgCopiaSostitutivaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				manageChangedPrimario();
			}
		});
				
		flgTimbratoFilePostRegItem = new HiddenItem("flgTimbratoFilePostReg");
		attivaTimbroTipologiaItem = new HiddenItem("attivaTimbroTipologia");		
		opzioniTimbroItem = new HiddenItem("opzioniTimbro");
		
		flgTimbraFilePostRegItem = new CheckboxItem("flgTimbraFilePostReg", I18NUtil.getMessages().protocollazione_flgTimbraFilePostReg());
		flgTimbraFilePostRegItem.setValue(false);
		flgTimbraFilePostRegItem.setColSpan(1);
		flgTimbraFilePostRegItem.setWidth("*");
		flgTimbraFilePostRegItem.setShowTitle(true);
		flgTimbraFilePostRegItem.setWrapTitle(false);
		flgTimbraFilePostRegItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgTimbraFilePostReg();
			}
		});
		flgTimbraFilePostRegItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					//Verifico se il file è timbrabile
					if (canBeTimbrato(lInfoFileRecord)) {
						if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaApponiTimbro")) {
							showOpzioniTimbratura();
						}
					} else {
						Layout.addMessage(new MessageBean("Il formato del file non ne consente la timbratura", "", MessageType.WARNING));
						flgTimbraFilePostRegItem.setValue(false);
					}
				}
			}
		});
		
		flgNoPubblPrimarioItem = new CheckboxItem("flgNoPubblPrimario", I18NUtil.getMessages().protocollazione_flg_no_pubbl());
		flgNoPubblPrimarioItem.setValue(false);
		flgNoPubblPrimarioItem.setColSpan(1);
		flgNoPubblPrimarioItem.setWidth("*");
		flgNoPubblPrimarioItem.setShowTitle(true);
		flgNoPubblPrimarioItem.setWrapTitle(false);
		flgNoPubblPrimarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgNoPubblPrimarioItem();
			}
		});
		flgNoPubblPrimarioItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				filePrimarioForm.markForRedraw();
			}
		});

		filePrimarioVerPubblItem = new AllegatiItem() {

			@Override
			public String getFlgTipoProvProtocollo() {
				return getFlgTipoProv();
			}
			
			@Override
			public boolean showUpload() {
				return showUploadFilePrimario();
			}

			@Override
			public boolean showAcquisisciDaScanner() {
				return showUploadFilePrimario();
			}

			@Override
			public boolean canBeEditedByApplet() {
				return true;
			}

			@Override
			public boolean showGeneraDaModello() {
				return showGeneraDaModelloButton();
			}
		
			@Override
			public boolean getShowVersioneOmissis() {
				return true;
			}

			@Override
			public void caricaModelloAllegato(String idModello, String tipoModello, String flgConvertiInPdf,
					final ServiceCallback<Record> callback) {

				final GWTRestDataSource lGeneraDaModelloDataSource = new GWTRestDataSource("GeneraDaModelloDataSource");
				lGeneraDaModelloDataSource.addParam("flgConvertiInPdf", flgConvertiInPdf);
				lGeneraDaModelloDataSource.addParam("flgMostraDatiSensibili", "false");
				lGeneraDaModelloDataSource.executecustom("caricaModello",
						getRecordCaricaModello(idModello, tipoModello), new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									if (callback != null) {
										callback.execute(response.getData()[0]);
									}
								}
							}
						});
			}

			@Override
			public String getFixedTipoAllegato() {
				return tipoDocumento;
			}

			@Override
			public boolean showTipoAllegato() {
				return false;
			}

			@Override
			public boolean showDescrizioneFileAllegato() {
				return false;
			}

			@Override
			public String getTitleNomeFileAllegato() {
				return FrontendUtil.getRequiredFormItemTitle("Vers. per pubbl.");
			}

			@Override
			public Integer getNomeFileWidth() {
				return 250;
			}

			@Override
			public boolean isHideTimbraInAltreOperazioniButton() {
				return true;
			}

			@Override
			public Boolean validate() {
				if (showFlgNoPubblPrimarioItem() && flgNoPubblPrimarioItem.getValueAsBoolean() != null
						&& flgNoPubblPrimarioItem.getValueAsBoolean()) {
					return super.validate();
				}
				return true;
			}
			
			@Override
			public Boolean valuesAreValid() {
				if (showFlgNoPubblPrimarioItem() && flgNoPubblPrimarioItem.getValueAsBoolean() != null
						&& flgNoPubblPrimarioItem.getValueAsBoolean()) {
					return super.valuesAreValid();
				}
				return true;				
			}
			
			@Override
			public String getIdUd() {
				return idUdFolderHiddenItem.getValue() != null ? String.valueOf(idUdFolderHiddenItem.getValue()) : null;
			}
		};
		filePrimarioVerPubblItem.setName("listaFilePrimarioVerPubbl");
		filePrimarioVerPubblItem.setStartRow(false);
		filePrimarioVerPubblItem.setColSpan(5);
		filePrimarioVerPubblItem.setShowFlgParteDispositivo(false);
		filePrimarioVerPubblItem.setShowFlgNoPubblAllegato(false);
		filePrimarioVerPubblItem.setShowTitle(false);
		filePrimarioVerPubblItem.setNotReplicable(true);
		filePrimarioVerPubblItem.setAttribute("obbligatorio", true);
		filePrimarioVerPubblItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgNoPubblPrimarioItem() && flgNoPubblPrimarioItem.getValueAsBoolean() != null
						&& flgNoPubblPrimarioItem.getValueAsBoolean();
			}
		});
		
		flgDatiSensibiliItem = new CheckboxItem("flgDatiSensibili", getTitleFlgDatiSensibili() + "&nbsp;&nbsp;&nbsp;");
		flgDatiSensibiliItem.setValue(false);
		flgDatiSensibiliItem.setColSpan(1);
		flgDatiSensibiliItem.setWidth("*");
		// flgDatiSensibiliItem.setLabelAsTitle(true);
		flgDatiSensibiliItem.setShowTitle(true);
		flgDatiSensibiliItem.setWrapTitle(false);
		flgDatiSensibiliItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showVersioneOmissis();
			}
		});
		flgDatiSensibiliItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if(flgDatiSensibiliItem.getValueAsBoolean() != null && flgDatiSensibiliItem.getValueAsBoolean()) {
					formaPubblicazioneItem.setValue("PER_ESTRATTO");
				} 
				filePrimarioForm.markForRedraw();
			}
		});

		filePrimarioOmissisItem = new DocumentItem() {
			
			@Override
			protected boolean showAltreOperazioni() {
				return true;
			}

			@Override
			public String getTipoDocumento() {
				return tipoDocumento;
			}
			
			@Override
			public String getFlgTipoProvProtocollo() {
				return getFlgTipoProv();
			}
			
			@Override
			public String getIdProcGeneraDaModello() {
				return null; // qui non lo devo passare, come nel generaDaModello del file primario
			}
			
			@Override
			public Date getDataRifValiditaFirma() {
				if (tsRegistrazioneItem.getValueAsDate() != null) {
					return tsRegistrazioneItem.getValueAsDate();					
				}
				
				return null;
			}
			
			@Override
			public int getWidth() {
				return 250;
			}
			
			@Override
			public boolean showUploadItem() {
				return instance.showUploadFilePrimario();
			}

			@Override
			public boolean showAcquisisciDaScannerMenuItem() {
				return instance.showUploadFilePrimario();
			}

			@Override
			public boolean canBeEditedByApplet() {
				return true;
			}

			@Override
			public boolean showGeneraDaModelloButton() {
				return instance.showGeneraDaModelloButton();
			}
			
			@Override
			public boolean hideTimbraMenuItems() {
				return false;
			}
			
			@Override
			public boolean showFlgSostituisciVerPrecItem() {
				return instance.showFlgSostituisciVerPrecItem();
			}
			
			@Override
			public boolean isAttivaTimbraturaFilePostReg() {
				return instance.isAttivaTimbraturaFilePostReg();
			}

			@Override
			public Boolean validate() {
				if (showVersioneOmissis() && flgDatiSensibiliItem.getValueAsBoolean() != null && flgDatiSensibiliItem.getValueAsBoolean()) {
					return super.validate();
				}
				return true;
			}
			
			@Override
			public Boolean valuesAreValid() {
				if (showVersioneOmissis() && flgDatiSensibiliItem.getValueAsBoolean() != null && flgDatiSensibiliItem.getValueAsBoolean()) {
					return super.valuesAreValid();
				}
				return true;				
			}
			
			@Override
			public void manageOnChangedDocument() {
				instance.manageChangedContenuti();
			}

			@Override
			public void manageOnChangedDocumentFile() {
				instance.manageChangedFilePrimarioOmissis();
			}
			
			@Override
			public Record getRecordCaricaModello(String idModello, String tipoModello) {
				return instance.getRecordCaricaModello(idModello, tipoModello);
			}
			
			@Override
			public GWTRestDataSource getGeneraDaModelloDataSource(String idModello, String tipoModello, String flgConvertiInPdf) {
				final GWTRestDataSource lGeneraDaModelloDataSource = new GWTRestDataSource("GeneraDaModelloDataSource");				
				lGeneraDaModelloDataSource.addParam("flgConvertiInPdf", flgConvertiInPdf);
				lGeneraDaModelloDataSource.addParam("flgMostraDatiSensibili", "false");
				return lGeneraDaModelloDataSource;
			}
			
			@Override
			public boolean isFormatoAmmesso(InfoFileRecord info) {	
				return instance.isFormatoAmmessoFilePrimario(info);
			}
		
			@Override
			public boolean enableAcquisisciDaScannerMenu() {
				return enableAcquisisciDaScannerMenuItem();
			}
			
			@Override
			public boolean showTimbraBarcodeMenuItems() {
				return true;
			}
			
			@Override
			public boolean isAttivaTimbroTipologia() {
				return isAttivaTimbroTipologiaProtocollazione();
			}
			
			@Override
			public boolean getEditingProtocollo() {
				return mode != null && (mode.equals("new") || (mode.equals("edit")));
			}
		};
		filePrimarioOmissisItem.setStartRow(false);
		filePrimarioOmissisItem.setName("filePrimarioOmissis");
		filePrimarioOmissisItem.setTitle(I18NUtil.getMessages().protocollazione_detail_nomeFileOmissisAllegatoItem_title());
		filePrimarioOmissisItem.setShowTitle(true);
		filePrimarioOmissisItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (showVersioneOmissis() && flgDatiSensibiliItem.getValueAsBoolean() != null && flgDatiSensibiliItem.getValueAsBoolean()) {
					filePrimarioOmissisItem.setAttribute("obbligatorio", true);
					filePrimarioOmissisItem.setTitle(FrontendUtil.getRequiredFormItemTitle((I18NUtil.getMessages().protocollazione_detail_nomeFileOmissisAllegatoItem_title())));
					filePrimarioOmissisItem.setRequired(true);
					return true;
				} else {
					filePrimarioOmissisItem.setAttribute("obbligatorio", false);
					filePrimarioOmissisItem.setTitle((I18NUtil.getMessages().protocollazione_detail_nomeFileOmissisAllegatoItem_title()));
					filePrimarioOmissisItem.setRequired(false);
					return false;				
				}
			}
		});
		
		SpacerItem spacerDocPressoCentroPostaItem = new SpacerItem();
		spacerDocPressoCentroPostaItem.setColSpan(1);
		spacerDocPressoCentroPostaItem.setWidth(10);
		spacerDocPressoCentroPostaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDocPressoCentroPostaItem();
			}
		});
		
		docPressoCentroPostaItem = new SelectItem("docPressoCentroPosta", "Doc. presso Centro Posta");
		LinkedHashMap<String, String> lMapDocPressoCentroPosta = new LinkedHashMap<String, String>();
		lMapDocPressoCentroPosta.put("SI", "Si");
		lMapDocPressoCentroPosta.put("NO", "No");
		docPressoCentroPostaItem.setValueMap(lMapDocPressoCentroPosta);
		docPressoCentroPostaItem.setWidth(120);
		docPressoCentroPostaItem.setWrapTitle(false);
		docPressoCentroPostaItem.setAllowEmptyValue(true);	
		if(isRequiredDocPressoCentroPostaItem()) {
			docPressoCentroPostaItem.setRequired(true);
		}		
		docPressoCentroPostaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDocPressoCentroPostaItem();
			}
		});
		
		List<FormItem> filePrimarioFields = new ArrayList<FormItem>();
		filePrimarioFields.add(idDocPrimarioHiddenItem);
		filePrimarioFields.add(idAttachEmailPrimarioItem);
		filePrimarioFields.add(uriFilePrimarioItem);
		filePrimarioFields.add(infoFileItem);
		filePrimarioFields.add(remoteUriFilePrimarioItem);
		filePrimarioFields.add(isDocPrimarioChangedItem);
		filePrimarioFields.add(nomeFilePrimarioOrigItem);
		filePrimarioFields.add(nomeFilePrimarioTifItem);
		filePrimarioFields.add(uriFilePrimarioTifItem);
		filePrimarioFields.add(nomeFileVerPreFirmaItem);
		filePrimarioFields.add(uriFileVerPreFirmaItem);
		filePrimarioFields.add(improntaVerPreFirmaItem);
		filePrimarioFields.add(infoFileVerPreFirmaItem);
		filePrimarioFields.add(nroLastVerItem);
		filePrimarioFields.add(noteMancanzaFileItem);		
		filePrimarioFields.add(nomeFilePrimarioItem);
		filePrimarioFields.add(uploadFilePrimarioItem);
		filePrimarioFields.add(filePrimarioButtons);
		filePrimarioFields.add(improntaItem);
		filePrimarioFields.add(flgSostituisciVerPrecItem);
		filePrimarioFields.add(flgOriginaleCartaceoItem);
		filePrimarioFields.add(flgCopiaSostitutivaItem);
		filePrimarioFields.add(flgTimbratoFilePostRegItem);
		filePrimarioFields.add(attivaTimbroTipologiaItem);
		filePrimarioFields.add(opzioniTimbroItem);		
		filePrimarioFields.add(flgTimbraFilePostRegItem);
		filePrimarioFields.add(flgNoPubblPrimarioItem);
		filePrimarioFields.add(filePrimarioVerPubblItem);
		filePrimarioFields.add(flgDatiSensibiliItem);
		filePrimarioFields.add(filePrimarioOmissisItem);
		filePrimarioFields.add(spacerDocPressoCentroPostaItem);
		filePrimarioFields.add(docPressoCentroPostaItem);

		filePrimarioForm.setFields(filePrimarioFields.toArray(new FormItem[filePrimarioFields.size()]));
	}
	
	public void recuperaIdUdAtto(final ServiceCallback<String> callback) {
		String tipoReg = tipoRegNumItem != null && tipoRegNumItem.getValueAsString() != null ? tipoRegNumItem.getValueAsString() : "";
		String sigla = "";
		if (tipoReg.equals("R") || tipoReg.equals("PP")) {
			sigla = siglaRegNumItem != null && siglaRegNumItem.getValue() != null ? (String) siglaRegNumItem.getValue() : "";
		}
		String numero = nroRegNumItem != null && nroRegNumItem.getValueAsString() != null ? nroRegNumItem.getValueAsString() : "";
		String anno = annoRegNumItem != null && annoRegNumItem.getValueAsString() != null ? annoRegNumItem.getValueAsString() : "";									
		if (("PG".equals(tipoReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno)) {
			Record lRecord = new Record();			
			lRecord.setAttribute("tipoRegNum", tipoReg);
			lRecord.setAttribute("siglaRegNum", sigla);
			lRecord.setAttribute("nroRegNum", numero);
			lRecord.setAttribute("annoRegNum", anno);			
			final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource");
			lNuovaPropostaAtto2CompletaDataSource.performCustomOperation("recuperaIdUdAttoDaPubblicare", lRecord, new DSCallback() {							
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						if(callback != null) {
							callback.execute(response.getData()[0].getAttributeAsString("idUdFolder"));
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

	public void loadDettaglio(String idUd, final ServiceCallback<Record> callback) {
		loadDettaglio(idUd, null, false, callback);
	}
	
	public void loadDettaglioAfterSave(String idUd, String idRichPubbl, final ServiceCallback<Record> callback) {
		loadDettaglio(idUd, idRichPubbl, true, callback);
	}
	
	private void loadDettaglio(String idUd, String idRichPubbl, final Boolean afterSave, final ServiceCallback<Record> callback) {
		Record lRecord = new Record();
		lRecord.setAttribute("idUdFolder", idUd);
		lRecord.setAttribute("idRichPubbl", idRichPubbl);
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource");
		lNuovaPropostaAtto2CompletaDataSource.getData(lRecord, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (response.getData() != null) {
						Record dettaglio = response.getData()[0];
						if ((afterSave && dettaglio != null) || (dettaglio != null && dettaglio.getAttribute("abilModificabile") != null && dettaglio.getAttributeAsBoolean("abilModificabile"))) {
							if(callback != null) {
								callback.execute(response.getData()[0]);
							}	
						} else {
							Layout.addMessage(new MessageBean("Non sei abilitato a richiedere la pubblicazione di atti del tipo di quello indicato", "", MessageType.ERROR));
						}
					} 
				}
			}
		});
	}
	/**
	 * Metodo che restituisce la label del nome file primario
	 * 
	 */
	public String getTitleNomeFilePrimario() {
		return I18NUtil.getMessages().protocollazione_detail_nomeFilePrimarioItem_title();
	}
	
	/**
	 * Metodo che restituisce la larghezza del campo nome file primario
	 * 
	 */
	public int getWidthNomeFilePrimario() {
		return 250;
	}


	/**
	 * Metodo che viene richiamato quando cambia il file primario
	 * 
	 */
	public void manageChangedPrimario() {

	}
	
	// Cancellazione del file
	public void clickEliminaFile() {

			nomeFilePrimarioItem.setValue("");
			uriFilePrimarioItem.setValue("");
			nomeFilePrimarioTifItem.setValue("");
			uriFilePrimarioTifItem.setValue("");
			infoFileItem.clearValue();
			if(filePrimarioForm != null) {
				filePrimarioForm.markForRedraw();
			}
			if(filePrimarioButtons != null) {
				filePrimarioButtons.markForRedraw();
			}
			manageChangedPrimario();
			uploadFilePrimarioItem.getCanvas().redraw();
			if (generaDaModelloButton != null) {
				generaDaModelloButton.redraw();
			}
			remoteUriFilePrimarioItem.setValue(false);
			nomeFileVerPreFirmaItem.setValue("");
			uriFileVerPreFirmaItem.setValue("");
			improntaVerPreFirmaItem.setValue("");
			infoFileVerPreFirmaItem.clearValue();
			idAttachEmailPrimarioItem.clearValue();
		}
	
	/**
	 * Metodo che viene richiamato quando cambia il contenuto (si fa il
	 * confronto delle impronte dei file) o il nome del file primario
	 * 
	 */
	public void manageChangedFilePrimario() {

		filePrimarioForm.setValue("isDocPrimarioChanged", true);
		filePrimarioForm.redraw();		
		if (showFlgSostituisciVerPrecItem()) {
			try {
				Record recordOrig = valuesOrig != null ? new Record(valuesOrig) : null;
				InfoFileRecord precInfo = recordOrig != null && recordOrig.getAttributeAsObject("infoFile") != null
						? new InfoFileRecord(recordOrig.getAttributeAsObject("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				InfoFileRecord info = filePrimarioForm.getValue("infoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("infoFile"))
						: null;
				String impronta = info != null ? info.getImpronta() : null;
				if (flgSostituisciVerPrecItem != null) {
					if (precImpronta != null && impronta != null && !impronta.equals(precImpronta)) {
						flgSostituisciVerPrecItem.show();
					} else {
						flgSostituisciVerPrecItem.hide();
					}
				}
			} catch (Exception e) {
			}
		}
		if(isAttivaTimbraturaFilePostReg() && showFlgTimbraFilePostReg()) {
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
			if (canBeTimbrato(lInfoFileRecord) && !lInfoFileRecord.isFirmato()) {
				if (AurigaLayout.getParametroDBAsBoolean("FORZA_TIMBRATURA_FILE_POST_REG")) {
					if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaApponiTimbro")) {
						boolean flgTimbraFilePostRegValue = Boolean.parseBoolean(filePrimarioForm.getValueAsString("flgTimbraFilePostReg"));
						
	//					if (!(flgTimbraFilePostRegItem.getValue() != null && (Boolean) flgTimbraFilePostRegItem.getValue())) {
						if (!flgTimbraFilePostRegValue) {
							showOpzioniTimbratura();
						} else {
							SC.ask("Vuoi verificare/modificare le opzioni di timbratura per il nuovo file ?",
									new BooleanCallback() {
	
										@Override
										public void execute(Boolean value) {
											if (value) {
												showOpzioniTimbratura();
											}
										}
									});
						}
					}
					flgTimbraFilePostRegItem.setValue(true);
				} else {
					flgTimbraFilePostRegItem.setValue(false);
				}
			} else {
				flgTimbraFilePostRegItem.setValue(false);
			}
		}
	}
	
	/**
	 * Metodo che indica se mostrare o meno il form del file primario nella
	 * sezione "Contenuti"
	 * 
	 */
	public boolean showFilePrimarioForm() {
		return true;
	}

	/**
	 * Metodo che indica se è obbligatorio o meno inserire il file primario per
	 * la registrazione
	 * 
	 */
	public boolean isRequiredFilePrimario() {
		return true;
	}
	
	/**
	 * Metodo che indica se il file primario è in un formato ammesso
	 * 
	 */
	public boolean isFormatoAmmessoFilePrimario(InfoFileRecord info) {
		return true;
	}
	
	protected void changedEventAfterUpload(final String displayFileName, final String uri) {

		ChangedEvent lChangedEventDisplay = new ChangedEvent(nomeFilePrimarioItem.getJsObj()) {

			public DynamicForm getForm() {
				return filePrimarioForm;
			};

			@Override
			public FormItem getItem() {
				return nomeFilePrimarioItem;
			}

			@Override
			public Object getValue() {
				return displayFileName;
			}
		};
		ChangedEvent lChangedEventUri = new ChangedEvent(uriFilePrimarioItem.getJsObj()) {

			public DynamicForm getForm() {
				return filePrimarioForm;
			};

			@Override
			public FormItem getItem() {
				return uriFilePrimarioItem;
			}

			@Override
			public Object getValue() {
				return uri;
			}
		};
		nomeFilePrimarioItem.fireEvent(lChangedEventDisplay);
		uriFilePrimarioItem.fireEvent(lChangedEventUri);
	}

	/**
	 * Metodo che indica il messaggio di warning da mostrare quando il file primario è in un formato non ammesso
	 * 
	 */
	public String getFormatoNonAmmessoFilePrimarioWarning() {
		return "Il file primario risulta in un formato non ammesso";
	}
	
	/**
	 * Metodo che crea i bottoni delle azioni su file primario
	 * 
	 */
	protected void createFilePrimarioButtons() {

		filePrimarioButtons = new NestedFormItem("filePrimarioButtons");
		filePrimarioButtons.setWidth(10);
		filePrimarioButtons.setOverflow(Overflow.VISIBLE);
		filePrimarioButtons.setNumCols(7);
		filePrimarioButtons.setColWidths(16, 16, 16, 16, 16, 16, 16);
		filePrimarioButtons.setColSpan(1);
		
		showNoteMancanzaFileButton = new ImgButtonItem("showNoteMancanzaFileButton", "pratiche/task/note.png", "Informazioni sulla mancanza del file");
		showNoteMancanzaFileButton.setAlwaysEnabled(true);
		showNoteMancanzaFileButton.setColSpan(1);
		showNoteMancanzaFileButton.setIconWidth(16);
		showNoteMancanzaFileButton.setIconHeight(16);
		showNoteMancanzaFileButton.setIconVAlign(VerticalAlignment.BOTTOM);
		showNoteMancanzaFileButton.setAlign(Alignment.LEFT);
		showNoteMancanzaFileButton.setWidth(16);
		showNoteMancanzaFileButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean hasFilePrimario = filePrimarioForm.getValue("uriFilePrimario") != null && !"".equals(filePrimarioForm.getValue("uriFilePrimario"));
				return !hasFilePrimario && noteMancanzaFileItem.getValue() != null && !"".equals(noteMancanzaFileItem.getValue());
			}
		});
		showNoteMancanzaFileButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				NoteMancanzaFilePopup noteMancanzaFilePopup = new NoteMancanzaFilePopup("Informazioni sulla mancanza del file", (String) noteMancanzaFileItem.getValue(), editing) {
					
					@Override
					public void onClickOkButton(String noteMancanzaFile) {
						noteMancanzaFileItem.setValue(noteMancanzaFile);
					}
					
					@Override
					public void onClickAnnullaButton() {
									
					}
				};
				noteMancanzaFilePopup.show();				
			}
		});

		previewButton = new ImgButtonItem("previewButton", "file/preview.png",I18NUtil.getMessages().protocollazione_detail_previewButton_prompt());
		previewButton.setAlwaysEnabled(true);
		previewButton.setColSpan(1);
		previewButton.setIconWidth(16);
		previewButton.setIconHeight(16);
		previewButton.setIconVAlign(VerticalAlignment.BOTTOM);
		previewButton.setAlign(Alignment.LEFT);
		previewButton.setWidth(16);
		previewButton.setRedrawOnChange(true);
		previewButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					return PreviewWindow.canBePreviewed(lInfoFileRecord);
				}
				return false;

			}
		});
		previewButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickPreviewFile();
			}
		});

		previewEditButton = new ImgButtonItem("previewEditButton", "file/previewEdit.png",
				I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt());
		previewEditButton.setAlwaysEnabled(true);
		previewEditButton.setColSpan(1);
		previewEditButton.setIconWidth(16);
		previewEditButton.setIconHeight(16);
		previewEditButton.setIconVAlign(VerticalAlignment.BOTTOM);
		previewEditButton.setAlign(Alignment.LEFT);
		previewEditButton.setWidth(16);
		previewEditButton.setRedrawOnChange(true);
		previewEditButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")
						&& (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals(""))) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					return lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
				}
				return false;
			}
		});
		previewEditButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickPreviewEditFile();
			}
		});

		editButton = new ImgButtonItem("editButton", "file/editDoc.png", "Modifica documento");
		editButton.setAlwaysEnabled(false);
		editButton.setColSpan(1);
		editButton.setIconWidth(16);
		editButton.setIconHeight(16);
		editButton.setIconVAlign(VerticalAlignment.BOTTOM);
		editButton.setAlign(Alignment.LEFT);
		editButton.setWidth(16);
		editButton.setRedrawOnChange(true);
		editButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showEditButton();
			}
		});
		editButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickEditFile();
			}
		});

		fileFirmatoDigButton = new ImgButtonItem("fileFirmatoDigButton", "firma/firma.png",
				I18NUtil.getMessages().protocollazione_detail_fileFirmatoDigButton_prompt());
		fileFirmatoDigButton.setAlwaysEnabled(true);
		fileFirmatoDigButton.setColSpan(1);
		fileFirmatoDigButton.setIconWidth(16);
		fileFirmatoDigButton.setIconHeight(16);
		fileFirmatoDigButton.setIconVAlign(VerticalAlignment.BOTTOM);
		fileFirmatoDigButton.setAlign(Alignment.LEFT);
		fileFirmatoDigButton.setWidth(16);
		fileFirmatoDigButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && lInfoFileRecord.isFirmaValida();
				}
				return false;
			}
		});
		fileFirmatoDigButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageFileFirmatoButtonClick();
			}
		});

		firmaNonValidaButton = new ImgButtonItem("firmaNonValidaButton", "firma/firmaNonValida.png",I18NUtil.getMessages().protocollazione_detail_firmaNonValidaButton_prompt());
		firmaNonValidaButton.setAlwaysEnabled(true);
		firmaNonValidaButton.setColSpan(1);
		firmaNonValidaButton.setIconWidth(16);
		firmaNonValidaButton.setIconHeight(16);
		firmaNonValidaButton.setIconVAlign(VerticalAlignment.BOTTOM);
		firmaNonValidaButton.setAlign(Alignment.LEFT);
		firmaNonValidaButton.setWidth(16);
		firmaNonValidaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && !lInfoFileRecord.isFirmaValida();
				}
				return false;
			}
		});
		firmaNonValidaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageFileFirmatoButtonClick();
			}
		});
		
		fileMarcatoTempButton = new ImgButtonItem("fileMarcatoTempButton", "marca/marcaNonValida.png", I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt());
		fileMarcatoTempButton.setAlwaysEnabled(true);
		fileMarcatoTempButton.setColSpan(1);
		fileMarcatoTempButton.setIconWidth(16);
		fileMarcatoTempButton.setIconHeight(16);
		fileMarcatoTempButton.setIconVAlign(VerticalAlignment.BOTTOM);
		fileMarcatoTempButton.setAlign(Alignment.LEFT);
		fileMarcatoTempButton.setWidth(16);
		fileMarcatoTempButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					if (lInfoFileRecord != null && lInfoFileRecord.getInfoFirmaMarca() != null && lInfoFileRecord.getInfoFirmaMarca().getDataOraMarcaTemporale() != null) {
						if (lInfoFileRecord.getInfoFirmaMarca().isMarcaTemporaleNonValida()) {
							fileMarcatoTempButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt());
							fileMarcatoTempButton.setIcon("marcaTemporale/marcaTemporaleNonValida.png");
							return true;
						} else {
							Date dataOraMarcaTemporale = lInfoFileRecord.getInfoFirmaMarca().getDataOraMarcaTemporale();
							DateTimeFormat display_datetime_format = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
							fileMarcatoTempButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaValida_prompt(display_datetime_format.format(dataOraMarcaTemporale)));
							fileMarcatoTempButton.setIcon("marcaTemporale/marcaTemporaleValida.png");
							return true;
						}
					} else {
						return false;
					}
				}
				return false;
			}
		});
		
		altreOpButton = new ImgButtonItem("altreOpButton", "buttons/altreOp.png",
				I18NUtil.getMessages().altreOpButton_prompt());
		altreOpButton.setAlwaysEnabled(true);
		altreOpButton.setColSpan(1);
		altreOpButton.setIconWidth(16);
		altreOpButton.setIconHeight(16);
		altreOpButton.setIconVAlign(VerticalAlignment.BOTTOM);
		altreOpButton.setAlign(Alignment.LEFT);
		altreOpButton.setWidth(16);
		altreOpButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {

				final Menu altreOpMenu = new Menu();
				altreOpMenu.setOverflow(Overflow.VISIBLE);
				altreOpMenu.setShowIcons(true);
				altreOpMenu.setSelectionType(SelectionStyle.SINGLE);
				altreOpMenu.setCanDragRecordsOut(false);
				altreOpMenu.setWidth("*");
				altreOpMenu.setHeight("*");

				for (MenuItem item : createAltreOpMenuItems()) {
					altreOpMenu.addItem(item);
				}
				
//				if() {     TODO: timbro					
					buildMenuBarcodeEtichetta(altreOpMenu);
//				}
					
				final InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("infoFile"));
				if (lInfoFileRecord != null && Layout.isPrivilegioAttivo("SCC")) {
					String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
					MenuItem timbroConformitaCustomAllegatoMenuItem = new MenuItem(labelConformitaCustom, "file/copiaConformeCustom.png");
					timbroConformitaCustomAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
					timbroConformitaCustomAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									clickTimbraDatiSegnatura("COPIA_CONFORME_CUSTOM");
								}
							});

					altreOpMenu.addItem(timbroConformitaCustomAllegatoMenuItem);

				}
				
				altreOpMenu.showContextMenu();
			}
		});

		
		filePrimarioButtons.setNestedFormItems(showNoteMancanzaFileButton, previewButton, previewEditButton, editButton, fileFirmatoDigButton,
				firmaNonValidaButton, fileMarcatoTempButton, altreOpButton);
	}
	
	/**
	 * Metodo che indica se mostrare o meno i check "originale cartaceo" e
	 * "copia sostitutiva" del file primario
	 * 
	 */
	public boolean showFlgOriginaleCartaceoECopiaSostitutivaPrimario() {
		return false;
	}

	/**
	 * Viene verificato se il file da timbrare in fase di post registrazione è convertibile in pdf.
	 */
	public static boolean canBeTimbrato(InfoFileRecord lInfoFileRecord) {
		if (lInfoFileRecord == null)
			return false;
		else {
			String mimetype = lInfoFileRecord.getMimetype() != null ? lInfoFileRecord.getMimetype() : "";
			if (mimetype != null) {
				if (mimetype.equals("application/pdf") || mimetype.startsWith("image") || lInfoFileRecord.isConvertibile()) {
					return true;
				} else
					return false;
			} else
				return false;
		}
	}
	
	public void showOpzioniTimbratura() {

		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro");
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro");
		String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina");

		Record record = new Record();
		record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
		record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
		record.setAttribute("tipoPaginaPref", tipoPaginaPref);

		record.setAttribute("nomeFile", filePrimarioForm.getValueAsString("nomeFilePrimario"));
		record.setAttribute("uriFile", filePrimarioForm.getValueAsString("uriFilePrimario"));
		record.setAttribute("remote", filePrimarioForm.getValueAsString("remoteUriFilePrimario")); // remoteUri
		InfoFileRecord infoFile = filePrimarioForm.getValue("infoFile") != null
				? new InfoFileRecord(filePrimarioForm.getValue("infoFile"))
				: null;
		if (infoFile != null) {
			record.setAttribute("infoFile", infoFile);
		}

		ApponiTimbroWindow apponiTimbroWindow = new ApponiTimbroWindow(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				filePrimarioForm.setValue("opzioniTimbro", object);
			}
		});
		apponiTimbroWindow.show();

	}	
	
	/**
	 * Metodo che indica se mostrare o meno il check "apponi timbro" del file primario
	 * 
	 */
	public boolean showFlgTimbraFilePostReg() {
		/*
		Il check deve apparire quando c'è il file, NON è firmato digitalmente & se:
	    a) si sta facendo una nuova registrazione
	    b) si sta facendo una nuova registrazione come copia
	    c) si sta facendo una nuova registrazione da modello
	    d) si sta protocollando una bozza
	    e) si sta modificando una registrazione e si è fatto upload/scansione di un nuovo file OR se il file era già presente e la stored non indicava che il file era timbrato (il caso d può ricadere in questo).
	    */ 
		if(isAttivaTimbraturaFilePostReg()) {
			boolean isEditing = mode != null && (mode.equals("new") || (mode.equals("edit"))) && showUploadFilePrimario();
			String flgTipoProv = getFlgTipoProv();			
			String uriFilePrimario = (String) filePrimarioForm.getValue("uriFilePrimario");
			InfoFileRecord infoFilePrimario = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
//			String mimetype = infoFilePrimario != null && infoFilePrimario.getMimetype() != null ? infoFilePrimario.getMimetype() : "";
			// se sto facendo una nuova registrazione o una variazione di una già esistente, e se il file c'è e non è firmato digitalmente
			if (flgTipoProv != null && !"".equals(flgTipoProv) && isEditing && 
				uriFilePrimario != null && !"".equals(uriFilePrimario) && infoFilePrimario != null && !infoFilePrimario.isFirmato() /*&& !mimetype.startsWith("image")*/) {
				String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
				boolean isChanged = filePrimarioForm.getValue("isDocPrimarioChanged") != null && (Boolean) filePrimarioForm.getValue("isDocPrimarioChanged");
				boolean flgTimbratoFilePostReg = filePrimarioForm.getValue("flgTimbratoFilePostReg") != null && (Boolean) filePrimarioForm.getValue("flgTimbratoFilePostReg");
				boolean isNewOrChanged = idDocPrimario == null || "".equals(idDocPrimario) || isChanged;
				// se il file è nuovo oppure era già presente e la stored non indicava che il file era timbrato 
				if (isNewOrChanged || !flgTimbratoFilePostReg) {  
					return true;
				}				
			}
		}
		// Nel caso in cui il check non è visibile il valore va settato sempre a false
		filePrimarioForm.setValue("flgTimbraFilePostReg",false);
		return false;		
	}	
	
	/**
	 * Metodo che indica se mostrare o meno il check "escludi pubbl." del file
	 * primario
	 * 
	 */
	public boolean showFlgNoPubblPrimarioItem() {
		return false;
	}
	
	/**
	 * Metodo che indica se mostrare o meno il bottone "Genera da modello" del
	 * file primario
	 * 
	 */
	public boolean showGeneraDaModelloButton() {
		// lo faccio vedere solo se ho il tipo documento valorizzato e non è un documento in entrata
		return tipoDocumento != null && !"".equals(tipoDocumento) && !(getFlgTipoProv() != null && "E".equals(getFlgTipoProv()));
//		return false;
	}
	
	/**
	 * Metodo che indica se mostrare o meno il check "dati sensibili" e la versione con omissis del file primario
	 * 
	 */
	public boolean showVersioneOmissis() {
		return true; //AurigaLayout.getParametroDBAsBoolean("SHOW_VERS_CON_OMISSIS") && !showFlgNoPubblPrimarioItem();
	}
	
	/**
	 * Metodo che indica se nel conteggio dei giorni di pubblicazione il giorno di pubblicazione viene sempre considerato come 1 giorno intero, altrimenti no
	 * 
	 */
	public boolean isConteggiaInteroGiornoCorrenteXPeriodoPubbl() {
		return AurigaLayout.getParametroDBAsBoolean("CONTEGGIA_INTERO_GIORNO_CORRENTE_X_PERIODO_PUBBL");
	}
	
	/**
	 * Metodo che costruisce il record per la chiamata al servizio che genera il
	 * file primario da modello iniettando i valori presenti in maschera
	 * 
	 */
	public Record getRecordCaricaModello(String idModello, String tipoModello) {
		final Record modelloRecord = new Record();
		modelloRecord.setAttribute("idModello", idModello);
		modelloRecord.setAttribute("tipoModello", tipoModello);
		String idProcess = (String) idProcessHiddenItem.getValue();
		modelloRecord.setAttribute("processId", getIdProcessTask() != null ? getIdProcessTask() : idProcess);
		
		/*
		if (attributiAddDocDetails != null) {
			modelloRecord.setAttribute("valori", getAttributiDinamiciDoc());
			modelloRecord.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
		}
		*/
		
		return modelloRecord;
	}	
	
	/**
	 * Metodo che indica se mostrare o meno il campo "Doc. presso Centro Posta"
	 * 
	 */
	public boolean showDocPressoCentroPostaItem() {
		return AurigaLayout.getParametroDB("DOC_PRESSO_CENTRO_POSTA") != null && !"".equals(AurigaLayout.getParametroDB("DOC_PRESSO_CENTRO_POSTA"));
	}
	
	/**
	 * Metodo che indica se è obbligatorio il campo "Doc. presso Centro Posta"
	 * 
	 */
	public boolean isRequiredDocPressoCentroPostaItem() {
		return showDocPressoCentroPostaItem() && "mandatory".equalsIgnoreCase(AurigaLayout.getParametroDB("DOC_PRESSO_CENTRO_POSTA"));
	}
	
	public void clickPreviewFile() {

		final Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord.getAttribute("idUdFolder");
		String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
		addToRecent(idUd, idDocPrimario);
		final String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		final String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		final Boolean remoteUri = Boolean.valueOf(filePrimarioForm.getValueAsString("remoteUriFilePrimario"));
		final InfoFileRecord info = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute("remoteUri", remoteUri);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>(
					"VisualizzaFatturaDataSource");
			if (info != null && info.isFirmato() && info.getTipoFirma().startsWith("CAdES")) {
				lGwtRestService.addParam("sbustato", "true");
			} else {
				lGwtRestService.addParam("sbustato", "false");
			}
			lGwtRestService.call(lRecordFattura, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					if (object.getAttribute("html") != null && !"".equals(object.getAttribute("html"))) {
						VisualizzaFatturaWindow lVisualizzaFatturaWindow = new VisualizzaFatturaWindow(display, object);
						lVisualizzaFatturaWindow.show();
					} else {
						preview(detailRecord, idUd, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			preview(detailRecord, idUd, display, uri, remoteUri, info);
		}
	}

	public void addToRecent(String idUd, String idDoc) {

		if (idUd != null && !"".equals(idUd) && idDoc != null && !"".equals(idDoc)) {
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AddToRecentDataSource");
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

				}
			});
		}
	}
	
	public void preview(final Record detailRecord, String idUd, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		
		// Callback per la gestione della versione con omisses
		PreviewWindowPageSelectionCallback lWindowPageSelectionCallback = new PreviewWindowPageSelectionCallback() {
			
			@Override
			public void executeSalvaVersConOmissis(Record record) {
				if (showVersioneOmissis()) {
					String uri = record.getAttribute("uri");
					String displayFileName = record.getAttribute("fileName");
					Record infoFile = record.getAttributeAsRecord("infoFile");
					filePrimarioForm.setValue("flgDatiSensibili", true);
					flgDatiSensibiliItem.setValue(true);
					formaPubblicazioneItem.setValue("PER_ESTRATTO");
					filePrimarioForm.markForRedraw();
					filePrimarioOmissisItem.updateAfterUpload(displayFileName, uri, infoFile);
				}
			}
			
			@Override
			public void executeSalva(Record record) {
				String uriFile = record.getAttribute("uri");
				String nomeFile = record.getAttribute("fileName");
				InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				filePrimarioForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedFilePrimario();
				}
				filePrimarioForm.setValue("nomeFilePrimario", nomeFile);
				filePrimarioForm.setValue("uriFilePrimario", uriFile);
				filePrimarioForm.setValue("nomeFilePrimarioTif", "");
				filePrimarioForm.setValue("uriFilePrimarioTif", "");
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
				filePrimarioForm.setValue("nomeFileVerPreFirma", nomeFile);
				filePrimarioForm.setValue("uriFileVerPreFirma", uriFile);
				filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
				filePrimarioForm.setValue("infoFileVerPreFirma", info);
				if(filePrimarioForm != null) {
					filePrimarioForm.markForRedraw();
				}
				if(filePrimarioButtons != null) {
					filePrimarioButtons.markForRedraw();
				}
				manageChangedPrimario();
				changedEventAfterUpload(nomeFile, uriFile);				
			}
			
			@Override
			public void executeOnError() {				
			}
		};
		
		boolean isViewMode = !filePrimarioButtons.isEditing() && !filePrimarioOmissisItem.getEditing();
		boolean enableOptionToSaveInOmissisForm = showVersioneOmissis();		
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display, lWindowPageSelectionCallback, isViewMode, enableOptionToSaveInOmissisForm);
		// PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display);
		// in realtà il costruttore effettua anche lo show, quindi non è
		// necessario inizializzare una variabile
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, remoteUri,
		// info, "FileToExtractBean", display);
	}


    protected boolean showEditButton() {
	
    	final String display = filePrimarioForm.getValueAsString("nomeFileVerPreFirma");
    	final String uri = filePrimarioForm.getValueAsString("uriFileVerPreFirma");
    	if (canEditByApplet() && uri != null && !uri.equals("")) {
    		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_EDITING_FILE")) {
    			if (uri != null && !uri.equals("")) {
    				String estensione = null;					
    				/**
    				 * File corrente non firmato
    				 */
    				Object infoFile = filePrimarioForm.getValue("infoFile");
    				
				
    				if (infoFile != null) {
    					InfoFileRecord lInfoFileRecord = new InfoFileRecord(infoFile);
    					if (lInfoFileRecord.getCorrectFileName() != null && !lInfoFileRecord.getCorrectFileName().trim().equals("")) {
    						estensione = lInfoFileRecord.getCorrectFileName().substring(lInfoFileRecord.getCorrectFileName().lastIndexOf(".") + 1);
    					}
    					if (estensione == null || estensione.equals("") || estensione.equalsIgnoreCase("p7m")) {
    						estensione = display.substring(display.lastIndexOf(".") + 1);
    					}
					
    					if (estensione.equalsIgnoreCase("p7m")) {
    						String displaySbustato = display.substring(0, display.lastIndexOf("."));
    						estensione = displaySbustato.substring(displaySbustato.lastIndexOf(".") + 1);
    					}
					
    					for (String lString : Layout.getGenericConfig().getEditableExtension()) {
    						if (lString.equals(estensione)) {
    							return true;
    						}
    					}
    				}
    				
    				/**
    				 * File pre versione di quello firmato
    				 */
				
    				Object infoFilePreVer = filePrimarioForm.getValue("infoFileVerPreFirma");
				
    				if(infoFilePreVer != null) {
    					InfoFileRecord lInfoFilePreVerRecord = new InfoFileRecord(infoFilePreVer);
    					if (lInfoFilePreVerRecord.getCorrectFileName() != null && !lInfoFilePreVerRecord.getCorrectFileName().trim().equals("")) {
    						estensione = lInfoFilePreVerRecord.getCorrectFileName().substring(lInfoFilePreVerRecord.getCorrectFileName().lastIndexOf(".") + 1);
    					}
					
    					if (estensione == null || estensione.equals("") || estensione.equalsIgnoreCase("p7m")) {
    						estensione = display.substring(display.lastIndexOf(".") + 1);
					
    					}
					
    					if (estensione.equalsIgnoreCase("p7m")) {
    						String displaySbustato = display.substring(0, display.lastIndexOf("."));
    						estensione = displaySbustato.substring(displaySbustato.lastIndexOf(".") + 1);
    					}
					
    					for (String lString : Layout.getGenericConfig().getEditableExtension()) {
    						if (lString.equals(estensione)) {
    							return true;
    						}
    					}
				
    				} 			
				
    				return false;
			
    			} else {
    				return false;
    			}
		
    		} else {
    			return false;
    		}
	
    	} else {
    		return false;
    	}
   }

   public void clickPreviewEditFile() {

		final Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord.getAttribute("idUdFolder");
		String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
		addToRecent(idUd, idDocPrimario);
		final String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		final String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		final Boolean remoteUri = Boolean.valueOf(filePrimarioForm.getValueAsString("remoteUriFilePrimario"));
		final InfoFileRecord info = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute("remoteUri", remoteUri);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>(
					"VisualizzaFatturaDataSource");
			if (info != null && info.isFirmato() && info.getTipoFirma().startsWith("CAdES")) {
				lGwtRestService.addParam("sbustato", "true");
			} else {
				lGwtRestService.addParam("sbustato", "false");
			}
			lGwtRestService.call(lRecordFattura, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {

					if (object.getAttribute("html") != null && !"".equals(object.getAttribute("html"))) {
						VisualizzaFatturaWindow lVisualizzaFatturaWindow = new VisualizzaFatturaWindow(display, object);
						lVisualizzaFatturaWindow.show();
					} else {
						previewWithJPedal(detailRecord, idUd, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			previewWithJPedal(detailRecord, idUd, display, uri, remoteUri, info);
		}
	}
   
   
   protected void clickEditFile() {
		
		final Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord.getAttribute("idUdFolder");
		String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
		addToRecent(idUd, idDocPrimario);
		final String display = filePrimarioForm.getValueAsString("nomeFileVerPreFirma");
		final String uri = filePrimarioForm.getValueAsString("uriFileVerPreFirma");
		final Boolean remoteUri = Boolean.valueOf(filePrimarioForm.getValueAsString("remoteUriFilePrimario"));		
		final InfoFileRecord info = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
		if (info.isFirmato()) {
			SC.ask("Modificando il file perderai la/le firme già apposte. Vuoi comunque procedere?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {

					if (value){
						InfoFileRecord infoPreFirma = new InfoFileRecord(filePrimarioForm.getValue("infoFileVerPreFirma"));
						editFile(infoPreFirma, display, uri, remoteUri);
					}
				}
			});
		} else {
			editFile(info, display, uri, remoteUri);
		}
	}

   private void editFile(InfoFileRecord info, String display, String docStorageUri, Boolean remoteUri) {

		String estensione = null;
		if (info.getCorrectFileName() != null && !info.getCorrectFileName().trim().equals("")) {
			estensione = info.getCorrectFileName().substring(info.getCorrectFileName().lastIndexOf(".") + 1);
		}
		if (estensione == null || estensione.equals("") || estensione.equalsIgnoreCase("p7m")) {
			estensione = display.substring(display.lastIndexOf(".") + 1);
		}
		String impronta = filePrimarioForm.getValueAsString("improntaVerPreFirma");
		if (impronta == null || "".equals(impronta)) {
			impronta = info.getImpronta();
		}
		if (estensione.equalsIgnoreCase("p7m")) {
			Record lRecordDaSbustare = new Record();
			lRecordDaSbustare.setAttribute("displayFilename", display);
			lRecordDaSbustare.setAttribute("uri", docStorageUri);
			lRecordDaSbustare.setAttribute("remoteUri", remoteUri);
			new GWTRestService<Record, Record>("SbustaFileService").call(lRecordDaSbustare,
					new ServiceCallback<Record>() {

						@Override
						public void execute(final Record lRecordSbustato) {
							String displaySbustato = lRecordSbustato.getAttribute("displayFilename");
							String uriSbustato = lRecordSbustato.getAttribute("uri");
							String estensioneSbustato = displaySbustato.substring(displaySbustato.lastIndexOf(".") + 1);
							String improntaSbustato = lRecordSbustato.getAttribute("impronta");
							openEditFileWindow(displaySbustato, uriSbustato, false, estensioneSbustato,improntaSbustato);
						}
					});
		} else {
			openEditFileWindow(display, docStorageUri, remoteUri, estensione, impronta);
		}
	}
   
   public void openEditFileWindow(String display, String docStorageUri, Boolean remoteUri, String estensione, String impronta) {

		OpenEditorUtility.openEditor(display, docStorageUri, remoteUri, estensione, impronta, new OpenEditorCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, String record) {
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				filePrimarioForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedFilePrimario();
				}
				filePrimarioForm.setValue("nomeFilePrimario", nomeFileFirmato);
				filePrimarioForm.setValue("uriFilePrimario", uriFileFirmato);
				filePrimarioForm.setValue("nomeFilePrimarioTif", "");
				filePrimarioForm.setValue("uriFilePrimarioTif", "");
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
				filePrimarioForm.setValue("nomeFileVerPreFirma", nomeFileFirmato);
				filePrimarioForm.setValue("uriFileVerPreFirma", uriFileFirmato);
				filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
				filePrimarioForm.setValue("infoFileVerPreFirma", info);
				if(filePrimarioForm != null) {
					filePrimarioForm.markForRedraw();
				}
				if(filePrimarioButtons != null) {
					filePrimarioButtons.markForRedraw();
				}
				manageChangedPrimario();
				changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);
			}
		});
	}

   public void previewWithJPedal(final Record detailRecord, String idUd, final String display, final String uri,
			final Boolean remoteUri, InfoFileRecord info) {

		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), info.getMimetype(), idUd,
				rotazioneTimbroPref,posizioneTimbroPref);
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("LoadTimbraDefault");
		lGwtRestService.call(lFileDaTimbrareBean, new ServiceCallback<Record>() {

			@Override
			public void execute(Record lOpzioniTimbro) {
				boolean timbroEnabled = detailRecord != null && detailRecord.getAttribute("flgTipoProv") != null
						&& !"".equals(detailRecord.getAttribute("flgTipoProv"))
						&& detailRecord.getAttribute("idUdFolder") != null && !"".equals(detailRecord.getAttribute("idUdFolder"));
				PreviewDocWindow previewDocWindow = new PreviewDocWindow(uri, display, remoteUri, timbroEnabled,
						lOpzioniTimbro) {

					@Override
					public void uploadCallBack(String filePdf, String uriPdf, String record) {
						InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null
								? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
						String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
						InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
						filePrimarioForm.setValue("infoFile", info);
						if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
							manageChangedFilePrimario();
						}
						filePrimarioForm.setValue("nomeFilePrimario", filePdf);
						filePrimarioForm.setValue("uriFilePrimario", uriPdf);
						filePrimarioForm.setValue("nomeFilePrimarioTif", "");
						filePrimarioForm.setValue("uriFilePrimarioTif", "");
						filePrimarioForm.setValue("remoteUriFilePrimario", false);
						filePrimarioForm.setValue("nomeFileVerPreFirma", filePdf);
						filePrimarioForm.setValue("uriFileVerPreFirma", uriPdf);
						filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
						filePrimarioForm.setValue("infoFileVerPreFirma", info);
						if(filePrimarioForm != null) {
							filePrimarioForm.markForRedraw();
						}
						if(filePrimarioButtons != null) {
							filePrimarioButtons.markForRedraw();
						}
						manageChangedPrimario();
						changedEventAfterUpload(filePdf, uriPdf);
					}
				};
				previewDocWindow.show();
			}
		});
	}
   
   protected void manageFileFirmatoButtonClick() {

		final InfoFileRecord infoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("infoFile"));
		final String uriFilePrimario = filePrimarioForm.getValueAsString("uriFilePrimario");
		MenuItem informazioniFirmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_dettaglioCertificazione_prompt(), "buttons/detail.png");
		informazioniFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				generaFileFirmaAndPreview(infoFileRecord, uriFilePrimario, false);
			}
		});
		MenuItem stampaFileCertificazioneMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_stampaFileCertificazione_prompt(),
				"protocollazione/stampaEtichetta.png");
		stampaFileCertificazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				generaFileFirmaAndPreview(infoFileRecord, uriFilePrimario, true);
			}
		});
		final Menu fileFirmatoMenu = new Menu();
		fileFirmatoMenu.setOverflow(Overflow.VISIBLE);
		fileFirmatoMenu.setShowIcons(true);
		fileFirmatoMenu.setSelectionType(SelectionStyle.SINGLE);
		fileFirmatoMenu.setCanDragRecordsOut(false);
		fileFirmatoMenu.setWidth("*");
		fileFirmatoMenu.setHeight("*");
		fileFirmatoMenu.addItem(informazioniFirmaMenuItem);
		fileFirmatoMenu.addItem(stampaFileCertificazioneMenuItem);
		fileFirmatoMenu.showContextMenu();
	}
   
   private void generaFileFirmaAndPreview(final InfoFileRecord infoFilePrimario, String uriFilePrimario,boolean aggiungiFirma) {

		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFilePrimario);
		record.setAttribute("uriAttach", uriFilePrimario);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.extraparam.put("aggiungiFirma", Boolean.toString(aggiungiFirma));
		if(!isProtocollazioneDetailAtti() && !isTaskDetail()) {			
			
			/*
			if (dataProtocolloItem.getValueAsDate() != null) {
				String dataRiferimento = DateUtil.format(dataProtocolloItem.getValueAsDate());
				lGwtRestDataSource.extraparam.put("dataRiferimento", dataRiferimento);
			}
			*/
		}
		lGwtRestDataSource.executecustom("stampaFileCertificazione", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record data = response.getData()[0];
				InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
				String filename = infoFile.getCorrectFileName();
				String uri = data.getAttribute("tempFileOut");
				// String url = (GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename) + "&url=" + URL.encode(uri));
				preview(null, null, filename, uri, false, infoFile);
			}
		});
	}   
   
   public void generaDaModello() {
		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource(
				"LoadComboGeneraDaModelloDataSource", new String[] { "codice", "descrizione" }, true);
		lGwtRestDataSource.addParam("idTipoDocumento", tipoDocumento);
		lGwtRestDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				RecordList data = response.getDataAsRecordList();
				if (data.getLength() > 1) {
					SelezionaGeneraDaModelloWindow generaDaModelloWindow = new SelezionaGeneraDaModelloWindow(
							data) {

						@Override
						public void caricaModelloSelezionato(String idModello, String tipoModello,
								String flgConvertiInPdf) {
							caricaModello(idModello, tipoModello, flgConvertiInPdf);
						}
					};
					generaDaModelloWindow.show();
				} else if (data.getLength() == 1) {
					String idModello = data.get(0).getAttribute("idModello");
					if (idModello != null && !"".equals(idModello)) {
						caricaModello(idModello, data.get(0).getAttribute("tipoModello"), data.get(0).getAttribute("flgConvertiInPdf"));
					}
				} else {
					Layout.addMessage(new MessageBean("Nessun modello da caricare!", "", MessageType.INFO));
				}

			}
		});
	}

   
   /**
	 * Metodo che implementa l'azione del bottone "Genera da modello" del file
	 * primario
	 * 
	 */
	public void caricaModello(String idModello, String tipoModello, String flgConvertiInPdf) {
		final GWTRestDataSource lGeneraDaModelloDataSource = new GWTRestDataSource("GeneraDaModelloDataSource");
		lGeneraDaModelloDataSource.addParam("flgConvertiInPdf", flgConvertiInPdf);
		lGeneraDaModelloDataSource.executecustom("caricaModello", getRecordCaricaModello(idModello, tipoModello),
				new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							manageAfterCaricaModello(response.getData()[0]);
						}
					}
				});
	}

	/**
	 * Metodo di callback del servizio di generazione del modello con i valori
	 * iniettati, che carica a maschera il nuovo file primario
	 * 
	 */
	public void manageAfterCaricaModello(Record record) {
		String nomeFilePrimario = record.getAttribute("nomeFilePrimario");
		String uriFilePrimario = record.getAttribute("uriFilePrimario");
		filePrimarioForm.setValue("nomeFilePrimario", nomeFilePrimario);
		filePrimarioForm.setValue("uriFilePrimario", uriFilePrimario);
		filePrimarioForm.setValue("nomeFilePrimarioTif", "");
		filePrimarioForm.setValue("uriFilePrimarioTif", "");
		filePrimarioForm.setValue("remoteUriFilePrimario", false);
		filePrimarioForm.setValue("uriFileVerPreFirma", uriFilePrimario);
		filePrimarioForm.setValue("nomeFileVerPreFirma", nomeFilePrimario);
		changedEventAfterUpload(record.getAttribute("nomeFilePrimario"), record.getAttribute("uriFilePrimario"));
		InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
		filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
		InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null
				? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
		String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
		filePrimarioForm.setValue("infoFile", info);
		filePrimarioForm.setValue("infoFileVerPreFirma", info);		
		String nomeFilePrimarioOrig = filePrimarioForm.getValueAsString("nomeFilePrimarioOrig");
		if (precImpronta == null || !precImpronta.equals(info.getImpronta())
				|| (nomeFilePrimario != null && !"".equals(nomeFilePrimario) && nomeFilePrimarioOrig != null
						&& !"".equals(nomeFilePrimarioOrig) && !nomeFilePrimario.equals(nomeFilePrimarioOrig))) {
			manageChangedFilePrimario();
		}
		if (!isFormatoAmmessoFilePrimario(info)) {
			GWTRestDataSource.printMessage(new MessageBean(getFormatoNonAmmessoFilePrimarioWarning(), "", MessageType.ERROR));
			
			/*
			 * Visto l'errore dovuto al fatto che il file non è nel formato richiesto
			 * rimuovo le informazioni associate al file primario di cui si è tentato l'inserimento.
			 * In questo modo all'aggiornamento della grafica non viene riempita la text e non vengono
			 * abilitati i pulsanti di firma, etc. (normalmente abilitati nel caso di file in 
			 * formato corretto) 
			 */
			clickEliminaFile();
		}
		if (info.isFirmato() && !info.isFirmaValida()) {
			GWTRestDataSource.printMessage(new MessageBean("Il file presenta una firma non valida alla data odierna",
					"", MessageType.WARNING));
		}
		if(filePrimarioForm != null) {
			filePrimarioForm.markForRedraw();
		}
		if(filePrimarioButtons != null) {
			filePrimarioButtons.markForRedraw();
		}
		manageChangedPrimario();
		if (showEditButton()) {
			clickEditFile();
		} else {
			clickPreviewFile();
		} 
	}

	
	
	/**
	 * 
	 * Metodo richiamato per la costruzione del menu per la stampa dei barcode
	 * su A4 & Etichette, con segnatura o tipologia, con posizione o senza.
	 */
	protected void buildMenuBarcodeEtichetta(Menu altreOpMenu){

		
		/**
		 * TIMBRA 
		 * Visibile solo se è presente l'uri del file Primario
		 */
		
		//Variabile che gestisce l'aggiunta della voce unica o del sottomenu "Timbra"
		boolean flgAddSubMenuTimbra = false;
				
		MenuItem timbraMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraMenuItem(), "file/timbra.gif");
		final InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("infoFile"));
		MenuItem timbraDatiSegnaturaMenuItem = null;
		MenuItem timbraDatiTipologiaMenuItem = null;
		MenuItem timbroConformitaOriginaleMenuItem = null;
		MenuItem timbroCopiaConformeMenuItem = null;
		if (lInfoFileRecord != null) {
			Menu timbraSubMenu = new Menu();
			timbraDatiSegnaturaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraDatiSegnaturaMenuItem(), "file/timbra.gif");
			timbraDatiSegnaturaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickTimbraDatiSegnatura("");
				}
			});
			timbraDatiSegnaturaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
				
				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("") && lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
				}
			});
			timbraSubMenu.addItem(timbraDatiSegnaturaMenuItem);
			
			if(isAttivaTimbroTipologiaProtocollazione()){
				timbraDatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraDatiTipologiaMenuItem());
				timbraDatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickTimbraDatiTipologia();					
					}
				});
				timbraDatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
					
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return isAttivaTimbroTipologiaProtocollazione() && (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) && lInfoFileRecord.isConvertibile();
					}
				});
				
				
				flgAddSubMenuTimbra=true;
				timbraSubMenu.addItem(timbraDatiTipologiaMenuItem);
			}
			
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
				if (lInfoFileRecord != null && lInfoFileRecord.getMimetype() != null && 
						(lInfoFileRecord.getMimetype().equals("application/pdf") || lInfoFileRecord.getMimetype().startsWith("image"))) {
					timbroConformitaOriginaleMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroConformitaOriginaleMenuItem(), "file/timbra.gif");
					timbroConformitaOriginaleMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

						@Override
						public boolean execute(Canvas target, Menu menu, MenuItem item) {
							return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("") && lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
						}
					});
					timbroConformitaOriginaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							clickTimbraDatiSegnatura("CONFORMITA_ORIG_CARTACEO");
						}
					});
					
					flgAddSubMenuTimbra=true;
					timbraSubMenu.addItem(timbroConformitaOriginaleMenuItem);
				}
			}
			
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA") && lInfoFileRecord != null) {
				flgAddSubMenuTimbra=true;
				
				timbroCopiaConformeMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem(), "file/timbra.gif");
				timbroCopiaConformeMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("") && lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
					}
				});
				
				Menu sottoMenuCopiaConformeItem = new Menu();
				
				MenuItem timbroCopiaConformeStampaItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_stampa(), "file/timbra.gif");	
				timbroCopiaConformeStampaItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickTimbraDatiSegnatura("CONFORMITA_ORIG_DIGITALE_STAMPA");
					}
				});
				sottoMenuCopiaConformeItem.addItem(timbroCopiaConformeStampaItem);
				
				MenuItem timbroCopiaConformeDigitaleItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_suppDigitale(), "file/timbra.gif");	
				timbroCopiaConformeDigitaleItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickTimbraDatiSegnatura("CONFORMITA_ORIG_DIGITALE_SUPP_DIG");
					}
				});
				sottoMenuCopiaConformeItem.addItem(timbroCopiaConformeDigitaleItem);
				
				timbroCopiaConformeMenuItem.setSubmenu(sottoMenuCopiaConformeItem);				
				timbraSubMenu.addItem(timbroCopiaConformeMenuItem);
			}
	
			timbraMenuItem.setSubmenu(timbraSubMenu);
			
		}
		timbraMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("") && lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			}
		});
		
		
		/**
		 * BARCODE SU A4
		 */
		MenuItem barcodeA4MenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4MenuItem() , "protocollazione/barcode.png");
	
		Menu barcodeA4SubMenu = new Menu();
		
		//Dati segnatura 
		MenuItem barcodeA4NrDocumentoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4NrDocumentoMenuItem() ,"protocollazione/nr_documento.png");
		barcodeA4NrDocumentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeDatiSegnatura("","");
			}
		});
		MenuItem barcodeA4NrDocumentoPoisizioneMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4NrDocumentoPoisizioneMenuItem() ,"protocollazione/nr_documento_posizione.png");
		barcodeA4NrDocumentoPoisizioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeDatiSegnatura("","P");
			}
		});
		MenuItem barcodeA4DatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4DatiTipologiaMenuItem());
		barcodeA4DatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeDatiTipologia("T");
			}
		});
		barcodeA4DatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
			
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return isAttivaTimbroTipologiaProtocollazione() /*&& uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")*/;
			}
		});
		
		barcodeA4SubMenu.setItems(barcodeA4NrDocumentoMenuItem, barcodeA4NrDocumentoPoisizioneMenuItem, barcodeA4DatiTipologiaMenuItem);
		barcodeA4MenuItem.setSubmenu(barcodeA4SubMenu);
		
		/**
		 * BARCODE SU A4 MULTPIPLI
		 */
		MenuItem barcodeA4MultipliMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4MultipliMenuItem(), "blank.png");
		
		Menu barcodeMultipliA4SubMenu = new Menu();
		
		//Dati segnatura
		MenuItem barcodeMultipliA4NrDocumentoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliA4NrDocumentoMenuItem(),"protocollazione/nr_documento.png");
		barcodeMultipliA4NrDocumentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeMultipli("","");
			}
		});
		MenuItem barcodeMultipliA4NrDocPosMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliA4NrDocPosMenuItem() ,"protocollazione/nr_documento_posizione.png");
		barcodeMultipliA4NrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeMultipli("","P");
			}
		});
		MenuItem barcodeMultipliA4DatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliA4DatiTipologiaMenuItem());
		barcodeMultipliA4DatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeMultipli("T","");
			}
		});
		barcodeMultipliA4DatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
			
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return isAttivaTimbroTipologiaProtocollazione() /*&& uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")*/;
			}
		});
		
		barcodeMultipliA4SubMenu.setItems(barcodeMultipliA4NrDocumentoMenuItem, barcodeMultipliA4NrDocPosMenuItem, barcodeMultipliA4DatiTipologiaMenuItem);
		barcodeA4MultipliMenuItem.setSubmenu(barcodeMultipliA4SubMenu);
		
		/**
		 * BARCODE SU ETICHETTA
		 */
		MenuItem barcodeEtichettaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaMenuItem(), "protocollazione/stampaEtichetta.png");
		
		Menu barcodeEtichettaSubMenu = new Menu();
		MenuItem barcodeEtichettaNrDocMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaNrDocMenuItem(),"protocollazione/nr_documento.png");
		barcodeEtichettaNrDocMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeDatiSegnatura("E","");
			}
		});
		MenuItem barcodeEtichettaNrDocPosMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaNrDocPosMenuItem(),"protocollazione/nr_documento_posizione.png");
		barcodeEtichettaNrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeDatiSegnatura("E","P");
			}
		});
		
		MenuItem barcodeEtichettaDatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaDatiTipologiaMenuItem());
		barcodeEtichettaDatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeDatiTipologia("E");
			}
		});
		barcodeEtichettaDatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
			
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return isAttivaTimbroTipologiaProtocollazione() /*&& uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")*/;
			}
		});
		
		barcodeEtichettaSubMenu.setItems(barcodeEtichettaNrDocMenuItem, barcodeEtichettaNrDocPosMenuItem,barcodeEtichettaDatiTipologiaMenuItem);
		barcodeEtichettaMenuItem.setSubmenu(barcodeEtichettaSubMenu);
		
		/**
		 * BARCODE SU ETICHETTA MULTIPLI
		 */
		
		MenuItem barcodeEtichettaMultiploMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaMultiploMenuItem());
		Menu barcodeMultipliEtichettaSubMenu = new Menu();
		
		MenuItem barcodeMultipliEtichettaNrDocMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliEtichettaNrDocMenuItem(),"protocollazione/nr_documento.png");
		barcodeMultipliEtichettaNrDocMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeSuEtichettaMultipli("","");
			}
		});
		MenuItem barcodeMultipliEtichettaNrDocPosMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliEtichettaNrDocPosMenuItem(),"protocollazione/nr_documento_posizione.png");
		barcodeMultipliEtichettaNrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeSuEtichettaMultipli("","P");
			}
		});
		MenuItem barcodeMultipliEtichettaDatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliEtichettaDatiTipologiaMenuItem());
		barcodeMultipliEtichettaDatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeSuEtichettaMultipli("T","");
			}
		});
		barcodeMultipliEtichettaDatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
			
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return isAttivaTimbroTipologiaProtocollazione() /*&& uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")*/;
			}
		});
		
		barcodeMultipliEtichettaSubMenu.setItems(barcodeMultipliEtichettaNrDocMenuItem, barcodeMultipliEtichettaNrDocPosMenuItem,barcodeMultipliEtichettaDatiTipologiaMenuItem);
		barcodeEtichettaMultiploMenuItem.setSubmenu(barcodeMultipliEtichettaSubMenu);
		
		String idUd = new Record(vm.getValues()).getAttribute("idUdFolder");
		if (idUd != null && !"".equals(idUd)) {
			// Se ho piu voci aggiungo il sottoMenu Timbra
			if (flgAddSubMenuTimbra) {
				altreOpMenu.addItem(timbraMenuItem);
				// Se ho un unica voce la aggiungo ad altreOperazioni con voce "Timbra"
			} else {
				if(timbraDatiSegnaturaMenuItem!=null) {
					timbraDatiSegnaturaMenuItem.setTitle("Timbra");
					altreOpMenu.addItem(timbraDatiSegnaturaMenuItem);
				}
			}

			if (AurigaLayout.getParametroDBAsBoolean("SHOW_BARCODE_MENU")) {
				altreOpMenu.addItem(barcodeA4MenuItem);
				altreOpMenu.addItem(barcodeA4MultipliMenuItem);
				altreOpMenu.addItem(barcodeEtichettaMenuItem);
				altreOpMenu.addItem(barcodeEtichettaMultiploMenuItem);
			}
		}
	
	}
	

	/**
	 * Metodo che crea le voci del menu associato al bottone "Altre operazioni"
	 * del file primario
	 * 
	 */
	protected List<MenuItem> createAltreOpMenuItems() {


		MenuItem acquisisciDaScannerMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_acquisisciDaScannerMenuItem_prompt(), "file/scanner.png");
		acquisisciDaScannerMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return enableAcquisisciDaScannerMenuItem();
			}
		});
		acquisisciDaScannerMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickAcquisisciDaScanner();
			}
		});

		MenuItem firmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_firmaMenuItem_prompt(),
				"file/mini_sign.png");
		firmaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals(""));
			}
		});
		firmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickFirma();
			}
		});

		MenuItem downloadMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(), "file/download_manager.png");
		InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("infoFile"));
		if (lInfoFileRecord != null) {
			// Se è firmato P7M
			if (lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
				Menu showFirmatoAndSbustato = new Menu();
				MenuItem firmato = new MenuItem(
						I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
				firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadFile();
					}
				});
				MenuItem sbustato = new MenuItem(
						I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
				sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadFileSbustato();
					}
				});
				showFirmatoAndSbustato.setItems(firmato, sbustato);
				downloadMenuItem.setSubmenu(showFirmatoAndSbustato);
			} else {
				downloadMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadFile();
					}
				});
			}
		}
		downloadMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals(""));
			}
		});

		MenuItem eliminaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_eliminaMenuItem_prompt(),
				"file/editdelete.png");
		eliminaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				// se è valorizzato il file e non è vuoto
				if (uriFilePrimarioItem.getValue() != null && !"".equals(uriFilePrimarioItem.getValue())) {
					// se sono in una mail
					// if (isFromEmail()) {
					// // se il file non ha il mimetype (ovvero è di un formato
					// non ammesso) mostro l'elimina
					// if (lInfoFileRecord.getMimetype()== null ||
					// lInfoFileRecord.getMimetype().trim().equals("")){
					// return true;
					// }
					// // se idAttach è vuoto (eventuale body) mostro l'elimina
					// if (idAttachEmailPrimarioItem.getValue() == null ||
					// idAttachEmailPrimarioItem.getValue().equals("")){
					// return true;
					// }
					// // altrimenti non lo mostro
					// else return false;
					// }
					return true;
				}
				return false;
			}
		});
		eliminaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickEliminaFile();
			}
		});

		MenuItem visualizzaVersioniMenuItem = new MenuItem("Visualizza versioni", "file/version.png");
		visualizzaVersioniMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				// Se è valorizzato il file
				boolean isVisible = false;
				Integer nroLastVers = filePrimarioForm != null
						&& filePrimarioForm.getValueAsString("nroLastVer") != null
						&& !"".equals(filePrimarioForm.getValueAsString("nroLastVer"))
								? new Integer(filePrimarioForm.getValueAsString("nroLastVer")) : null;
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					if (nroLastVers != null && nroLastVers > 1) {
						isVisible = true;
					}
				}
				return isVisible;
			}
		});
		visualizzaVersioniMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				/*
				String estremi = getTipoEstremiDocumento();
				String idDocIn = filePrimarioForm.getValueAsString("idDocPrimario");
				Record recordProtocollo = protocolloGeneraleForm.getValuesAsRecord();
				visualizzaVersioni(idDocIn, "FP", null, estremi, recordProtocollo);
				*/
			}
		});

		List<MenuItem> altreOpMenuItems = new ArrayList<MenuItem>();
		altreOpMenuItems.add(visualizzaVersioniMenuItem);
		altreOpMenuItems.add(downloadMenuItem);
		if (filePrimarioButtons.isEditing()) {
			altreOpMenuItems.add(acquisisciDaScannerMenuItem);
			altreOpMenuItems.add(firmaMenuItem);
		}
		
		if (!mode.equals("view") && (filePrimarioButtons.isEditing() || isFromEmail())
				&& nomeFilePrimarioItem.getCanEdit()) {
			altreOpMenuItems.add(eliminaMenuItem);
		}

		return altreOpMenuItems;
	
	}

	/**
	 * Metodo che viene richiamato quando cambia qualcosa nel form dell'oggetto
	 * 
	 */
	public void manageChangedContenuti() {
	}
	
	public void manageChangedFilePrimarioOmissis() {
		
	}
	
	private Boolean isAbilMittenteInt() {
		return AurigaLayout.isPrivilegioAttivo("PUB/RIC/INT;I");
	}
	
	private Boolean isAbilMittenteEst() {
		return AurigaLayout.isPrivilegioAttivo("PUB/RIC/EST;I");
	}
	
	public String getTitleFlgDatiSensibili() {
		String labelFlgVerOmissis = AurigaLayout.getParametroDB("LABEL_FLG_VER_OMISSIS");
		if(labelFlgVerOmissis != null && !"".equals(labelFlgVerOmissis)) {
			return labelFlgVerOmissis.toLowerCase();
		}		
		return "ver. con omissis per pubblicazione";
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		setCanEdit(canEdit, attoForm);
		setCanEdit(canEdit, mittenteRichiedenteForm);
		setCanEdit(canEdit, oggettoForm);
		setCanEdit(canEdit, periodoPubblicazioneForm);
		setCanEdit(canEdit, rettificaForm);
		setCanEdit(canEdit, formaPubblicazioneForm);
		setCanEdit(canEdit, notePubblicazioneForm);
		if(canEdit && mode != null && (mode.equals("edit") || mode.equals("new")) && (statoAttoItem.getValue() != null && statoAttoItem.getValue().equals("presente"))) {
			if (mode != null && mode.equals("edit")) {
				setCanEdit(false, attoForm);
			}
			tsRegistrazioneItem.setCanEdit(false);
			setCanEdit(false, mittenteRichiedenteForm);
			uploadFilePrimarioItem.setCanEdit(false);
			nomeFilePrimarioItem.setCanEdit(false);
			flgDatiSensibiliItem.setCanEdit(true);
			filePrimarioOmissisItem.setCanEdit(true);
			if(fileAllegatiItem instanceof AllegatiGridItem) {
				((AllegatiGridItem) fileAllegatiItem).canEditOnlyOmissisMode();
			} else {
				((AllegatiItem)fileAllegatiItem).canEditOnlyOmissisMode();
			}
		} else {
			setCanEdit(canEdit, filePrimarioForm);
			setCanEdit(canEdit, fileAllegatiForm);
		}
	}
	
	public void clickAcquisisciDaScanner() {

		ScanUtility.openScan(new ScanCallback() {

			@Override
			public void execute(final String filePdf, final String uriPdf, String fileTif, String uriTif,
					String record) {
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				filePrimarioForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedFilePrimario();
				}
				filePrimarioForm.setValue("nomeFilePrimario", filePdf);
				filePrimarioForm.setValue("uriFilePrimario", uriPdf);
				filePrimarioForm.setValue("nomeFilePrimarioTif", fileTif);
				filePrimarioForm.setValue("uriFilePrimarioTif", uriTif);
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
				filePrimarioForm.setValue("nomeFileVerPreFirma", filePdf);
				filePrimarioForm.setValue("uriFileVerPreFirma", uriPdf);
				filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
				filePrimarioForm.setValue("infoFileVerPreFirma", info);
				if(filePrimarioForm != null) {
					filePrimarioForm.markForRedraw();
				}
				if(filePrimarioButtons != null) {
					filePrimarioButtons.markForRedraw();
				}
				manageChangedPrimario();
				changedEventAfterUpload(filePdf, uriPdf);
			}
		});
	}

	public void clickFirma() {

		String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
		// FirmaWindow firmaWindow = new FirmaWindow(uri, display,
		// lInfoFileRecord) {
		// @Override
		// public void firmaCallBack(String nomeFileFirmato, String
		// uriFileFirmato, String record) {
		// InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") !=
		// null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) :
		// null;
		// String precImpronta = precInfo != null ? precInfo.getImpronta() :
		// null;
		// InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
		// filePrimarioForm.setValue("infoFile", info);
		// if(precImpronta == null || !precImpronta.equals(info.getImpronta()))
		// {
		// manageChangedFilePrimario();
		// }
		// filePrimarioForm.setValue("nomeFilePrimario", nomeFileFirmato);
		// filePrimarioForm.setValue("uriFilePrimario", uriFileFirmato);
		// filePrimarioForm.setValue("nomeFilePrimarioTif", "");
		// filePrimarioForm.setValue("uriFilePrimarioTif", "");
		// filePrimarioForm.setValue("remoteUriFilePrimario", false);
		// if(filePrimarioForm != null) {
		//	 filePrimarioForm.markForRedraw();
		// }
		// if(filePrimarioButtons != null) {
		// 	 filePrimarioButtons.markForRedraw();
		// }
		// manageChangedPrimario();
		// changedEventAfterUpload(nomeFileFirmato,uriFileFirmato);
		// }
		// };
		// firmaWindow.show();
		FirmaUtility.firmaMultipla(uri, display, lInfoFileRecord, new FirmaCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				filePrimarioForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedFilePrimario();
				}
				filePrimarioForm.setValue("nomeFilePrimario", nomeFileFirmato);
				filePrimarioForm.setValue("uriFilePrimario", uriFileFirmato);
				filePrimarioForm.setValue("nomeFilePrimarioTif", "");
				filePrimarioForm.setValue("uriFilePrimarioTif", "");
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
				if(filePrimarioForm != null) {
					filePrimarioForm.markForRedraw();
				}
				if(filePrimarioButtons != null) {
					filePrimarioButtons.markForRedraw();
				}
				manageChangedPrimario();
				changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);
			}
		});
	}
	
	/**
	 * TIMBRA DATI SEGNATURA - DmpkRegistrazionedocGettimbrodigreg
	 */
	public void clickTimbraDatiSegnatura(String finalita) {
		String nomeFile = filePrimarioForm.getValueAsString("nomeFilePrimario");
		String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		String remote = filePrimarioForm.getValueAsString("remoteUriFilePrimario");		
		InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
		Record detailRecord = new Record(vm.getValues());
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUdFolder") : null;
		String idDocPrimario = filePrimarioForm != null ? filePrimarioForm.getValueAsString("idDocPrimario") : null;
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina") != null ?
				AurigaLayout.getImpostazioneTimbro("tipoPagina") : "";
						
		/*Controllo introdotto per la nuova modalità di timbratura per i file firmati che devono saltare la scelta della preferenza*/
		boolean skipSceltaTimbratura = AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniTimbroSegnatura");
		boolean flgBustaPdfTimbratura = false;
		
		if(precInfo.isFirmato() && AurigaLayout.getParametroDBAsBoolean("ATTIVA_BUSTA_PDF_FILE_FIRMATO") && !finalita.equalsIgnoreCase("CONFORMITA_ORIG_DIGITALE_STAMPA")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = true;
		}
		
		if(finalita.equalsIgnoreCase("COPIA_CONFORME_CUSTOM")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = false;
		}
		
		if(skipSceltaTimbratura){
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDocPrimario);
			record.setAttribute("nomeFile", nomeFile);
			record.setAttribute("uri", uri);
			record.setAttribute("remote", remote);
			record.setAttribute("mimetype", precInfo.getMimetype());
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("skipScelteOpzioniCopertina", "true");
			if (finalita.equals("CONFORMITA_ORIG_CARTACEO")) {
				record.setAttribute("tipoPagina", "tutte");
			}else {
				record.setAttribute("tipoPagina", tipoPaginaPref);
			}
			record.setAttribute("finalita", finalita);
				
			if(flgBustaPdfTimbratura) {
				TimbroUtil.callStoreLoadFilePerTimbroConBusta(record);
			}else {
				TimbroUtil.buildDatiSegnatura(record);
			}
		}else{
				
			FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, nomeFile, Boolean.valueOf(remote), precInfo.getMimetype(), idUd, idDocPrimario, rotazioneTimbroPref,posizioneTimbroPref);
			lFileDaTimbrareBean.setAttribute("finalita", finalita);
			lFileDaTimbrareBean.setAttribute("tipoPagina", tipoPaginaPref);
			lFileDaTimbrareBean.setAttribute("skipScelteOpzioniCopertina", "false");
			TimbraWindow lTimbraWindow = new TimbraWindow("timbra", true, lFileDaTimbrareBean);
			lTimbraWindow.show();
		}
	}
	
	/**
	 * TIMBRA DATI SEGNATURA - DmpkRegistrazionedocGettimbrospecxtipo
	 */
	public void clickTimbraDatiTipologia() {
		
		String idDocPrimario = new Record(vm.getValues()).getAttribute("idDocPrimario");
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		if(AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniCopertina")){
			
			Record record = new Record();
			record.setAttribute("idDoc", idDocPrimario);
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("skipScelteOpzioniCopertina", "true");
			
			TimbroCopertinaUtil.buildDatiTipologia(record);
			
		}else{
			
			Record copertinaTimbroRecord = new Record();
			copertinaTimbroRecord.setAttribute("idDoc", idDocPrimario);
			copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "T");
			copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
			copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
		
			CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
			CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,false,"","T","");
			copertinaTimbroWindow.show();
		}
	}
	
	
	//*************** OPERAZIONE DA DETTAGLIO UD - BARCODE SU A4 & BARCODE SU ETICHETTA ( Dati segnatura & Dati tipologia ) ***************
	
	/**
	 *  DATI SEGNATURA - DmpkRegistrazionedocGettimbrodigreg
	 *  @param provenienza = B: barcode & E: etichetta
	 */
	public void clickBarcodeDatiSegnatura(String tipologia,String posizione) {
		
		if(tipologia != null && !"".equals(tipologia) && "E".equals(tipologia)){
			
			clickBarcodeEtichettaDatiSegnatura(posizione);
		}else{
		
			Record detailRecord = new Record(vm.getValues());
			String idUd = detailRecord != null ? detailRecord.getAttribute("idUdFolder") : null;
			
			String numeroAllegato = null;
			if(posizione != null && "P".equals(posizione)){
				numeroAllegato = "0";
			}
			
			String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
			String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";		
			
			if(AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniCopertina")){
				
				Record record = new Record();
				record.setAttribute("idUd", idUd);
				record.setAttribute("numeroAllegato", numeroAllegato);
				record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
				record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
				record.setAttribute("skipScelteOpzioniCopertina", "true");
				
				TimbroCopertinaUtil.buildDatiSegnatura(record);
				
			}else{
				
				Record copertinaTimbroRecord = new Record();
				copertinaTimbroRecord.setAttribute("idUd", idUd);
				copertinaTimbroRecord.setAttribute("numeroAllegato", numeroAllegato);
				copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "");
				copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
				copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
				copertinaTimbroRecord.setAttribute("provenienza", "A");
				copertinaTimbroRecord.setAttribute("posizionale", posizione);
				
				CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
				CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean);
				copertinaTimbroWindow.show();
			}
		}
	}
	
	/**
	 *  DATI TIPOLOGIA - DmpkRegistrazionedocGettimbrospecxtipo
	 */
	public void clickBarcodeDatiTipologia(String tipo){
		
		if(tipo != null && !"".equals(tipo) && "E".equals(tipo)){
			
			clickBarcodeEtichettaDatiTipologia();
		}else{
		
			Record detailRecord = new Record(vm.getValues());
			String idDocPrimario = detailRecord != null ? detailRecord.getAttribute("idDocPrimario") : null;
			String numeroAllegato = "";
			
			String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
			String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
			
			
			if(AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniCopertina")){
				
				Record record = new Record();
				record.setAttribute("idDoc", idDocPrimario);
				record.setAttribute("numeroAllegato", numeroAllegato);
				record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
				record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
				record.setAttribute("skipScelteOpzioniCopertina", "true");
				
				TimbroCopertinaUtil.buildDatiTipologia(record);
				
			}else{
				
				Record copertinaTimbroRecord = new Record();
				copertinaTimbroRecord.setAttribute("idDoc", idDocPrimario);
				copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "T");
				copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
				copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
				
				CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
				CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,false,"","T","");
				copertinaTimbroWindow.show();
			}
		}
	}
	
	/**
	 * Barcode multipli su A4 - Dati segnatura & tipologia 
	 */
	public void clickBarcodeMultipli(String tipo,String posizionale){
		
		Record detailRecord = new Record(vm.getValues());
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUdFolder") : null;
		String idDocPrimario = new Record(vm.getValues()).getAttribute("idDocPrimario");
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
				
		String numeroAllegato = null;
		if(posizionale != null && "P".equals(posizionale)){
			numeroAllegato = "0";
		}
				
		if(tipo != null && !"".equals(tipo) && "T".equals(tipo)){
			
			Record copertinaTimbroRecord = new Record();
			copertinaTimbroRecord.setAttribute("idDoc", idDocPrimario);
			copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "T");
			copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
			copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
			
			CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
			CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,true,"","T","");
			copertinaTimbroWindow.show();
		}else{
			
			Record copertinaTimbroRecord = new Record();
			copertinaTimbroRecord.setAttribute("idUd", idUd);
			copertinaTimbroRecord.setAttribute("numeroAllegato", numeroAllegato);
			copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "");
			copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
			copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
			copertinaTimbroRecord.setAttribute("provenienza", "P");
			copertinaTimbroRecord.setAttribute("posizionale", posizionale);
			
			CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
			CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,true,"","",posizionale);
			copertinaTimbroWindow.show();
		}
	}
	
	/**
	 * Barcode multipli su etichetta
	 */
	public void clickBarcodeSuEtichettaMultipli(String tipo,String posizione) {
			
		Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUdFolder") : null;
		
		final Record record = new Record();
		record.setAttribute("idUd", idUd);
		record.setAttribute("isMultiple", "true");
		if(tipo != null && "T".equals(tipo)){
			String idDocPrimario = detailRecord.getAttribute("idDocPrimario");
			record.setAttribute("tipologia", "T");
			record.setAttribute("idDoc", idDocPrimario);
		}
		
		if(posizione != null && "P".equals(posizione)){
			record.setAttribute("nrAllegato", "0");
		}
		record.setAttribute("posizione", posizione);
		
		/**
		 * Se non è presente la stampante per le etichette predefinita, allora propone la scelta
		 */
		if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null
				&& !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
			
			record.setAttribute("nomeStampante", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
			
			StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(record);
			stampaEtichettaPopup.show();
		}else{
			PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {
					
				@Override
				public void execute(String nomeStampante) {

					record.setAttribute("nomeStampante", nomeStampante);					
					StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(record);
					stampaEtichettaPopup.show();
				}
			}, null);
		}
	}
	
	/**
	 * Barcode su etichetta - Dati segnatura
	 */
	public void clickBarcodeEtichettaDatiSegnatura(String posizione) {
		
		Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUdFolder") : null;
		
		final String nrAllegato = posizione != null && "P".equals(posizione) ? "0" : null;
		
		final Record recordToPrint = new Record();
		recordToPrint.setAttribute("idUd", idUd);
		recordToPrint.setAttribute("numeroAllegato", nrAllegato);
		recordToPrint.setAttribute("nomeStampantePred", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
	
		/**
		 * Se non è presente la stampante per i barcode su etichette predefinita, allora propone la scelta
		 */
		if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null
				&& !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
			CopertinaEtichettaUtil.stampaEtichettaDatiSegnatura(recordToPrint);
		}else{
			PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {
				
				@Override
				public void execute(String nomeStampante) {
					CopertinaEtichettaUtil.stampaEtichettaDatiSegnatura(recordToPrint);
				}
			}, null);
		}
	}
	
	/**
	 * Barcode su etichetta - Dati tipologia
	 */
	public void clickBarcodeEtichettaDatiTipologia() {
		
		Record detailRecord = new Record(vm.getValues());
		final String idDocPrimario = detailRecord != null ? detailRecord.getAttribute("idDocPrimario") : null;
		
		final Record recordToPrint = new Record();
		recordToPrint.setAttribute("idDoc", idDocPrimario);
		recordToPrint.setAttribute("numeroAllegato", "");
		recordToPrint.setAttribute("nomeStampantePred", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
	
		/**
		 * Se non è presente la stampante per i barcode su etichette predefinita, allora propone la scelta
		 */
		if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null	&& !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
			CopertinaEtichettaUtil.stampaEtichettaDatiTipologia(recordToPrint);
		} else {
			PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {
				
				@Override
				public void execute(String nomeStampante) {
					CopertinaEtichettaUtil.stampaEtichettaDatiTipologia(recordToPrint);
				}
			}, null);
		}
	}
	
	// Download del file
		public void clickDownloadFile() {

			String idUd = new Record(vm.getValues()).getAttribute("idUdFolder");
			String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
			addToRecent(idUd, idDocPrimario);
			String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
			String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", display);
			lRecord.setAttribute("uri", uri);
			lRecord.setAttribute("sbustato", "false");
			lRecord.setAttribute("remoteUri", filePrimarioForm.getValueAsString("remoteUriFilePrimario"));
			DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
		}

		// Download del file firmato sbustato
		public void clickDownloadFileSbustato() {

			String idUd = new Record(vm.getValues()).getAttribute("idUdFolder");
			String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
			addToRecent(idUd, idDocPrimario);
			String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
			String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", display);
			lRecord.setAttribute("uri", uri);
			lRecord.setAttribute("sbustato", "true");
			lRecord.setAttribute("remoteUri", filePrimarioForm.getValueAsString("remoteUriFilePrimario"));
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
			lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
			DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
		}

		
		/**
		 * Metodo che visualizza le versioni del file salvate
		 * 
		 */
		public static void visualizzaVersioni(String idDoc, String tipoFile, String nroAllegato, String estremi,
				Record recordProtocollo) {

			String title = "";
			if (tipoFile != null) {
				if (tipoFile.equals("FP")) {
					title = "Versioni del file primario";
					if(estremi != null && !"".equals(estremi)) {
						title += " del documento: " + estremi;
					}
				} else if (tipoFile.equals("A")) {
					title = "Versioni del file allegato N° " + nroAllegato;
					if(estremi != null && !"".equals(estremi)) {
						title += " del documento: " + estremi;
					}
				}  else if (tipoFile.equals("AO")) {
					title = "Versioni del file allegato (con omissis) N° " + nroAllegato;
					if(estremi != null && !"".equals(estremi)) {
						title += " del documento: " + estremi;
					}
				} else if (tipoFile.equals("DI")) {
					title = "Versioni del documento di istruttoria N° " + nroAllegato;
				}
			} else {
				title = "Versioni del file";
			}
			new VisualizzaVersioniFileWindow(idDoc, title, recordProtocollo);
		}
	
	public boolean isAttivaModalitaAllegatiGrid() {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_GRID_ALLEGATI_IN_BO_ALBO")) {
			return true;
		}
		return false;
	}

}
