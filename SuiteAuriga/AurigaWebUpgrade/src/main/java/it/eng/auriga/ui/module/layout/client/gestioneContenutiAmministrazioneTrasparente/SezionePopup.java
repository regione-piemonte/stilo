/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
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
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.NuovoRepertorioInternoWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedDateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class SezionePopup extends ModalWindow {

	protected SezionePopup _window;
	
	protected ValuesManager vm;

	protected DetailSection detailSectionTitolo;
	protected DynamicForm titoloForm;
	protected TextItem titoloItem;	
	protected ImgButtonItem cronologiaOperazioniButton;
	
	protected DetailSection detailSectionStato;
	protected DynamicForm statoForm;
	protected TextItem statoRichPubblItem;
	protected TextAreaItem motivoRigettoItem;
	
	protected DetailSection detailSectionAuotorizzRich;
	protected DynamicForm auotorizzRichForm;
	protected HiddenItem flgAbilAuotorizzRichItem;
	protected RadioGroupItem statoAuotorizzRichItem;
	protected TextAreaItem motivoRigettoAuotorizzRichItem;
		
	protected DetailSection detailSectionDescrizione;
	protected DynamicForm descrizioneForm;
	protected ImgButtonItem cronologiaOperazioniDescrizioneButton;
	protected CKEditorItem descrizioneItem;
	protected CheckboxItem flgTestiHtmlUgualiItem;
	
	protected DetailSection detailSectionDescrizioneDettaglio;
	protected DynamicForm descrizioneDettaglioForm;
	protected CKEditorItem descrizioneDettaglioItem;
	
	//Sezione Periodo di Pubblicazione
	protected DetailSection detailSectionPeriodoPubblicazione;
	protected DynamicForm periodoPubblicazioneForm;
	protected ExtendedDateItem dataInizioPubblicazioneItem;
	protected ExtendedDateItem dataFinePubblicazioneItem;
	
	//Sezione opzioni aggiuntive
	protected DetailSection detailSectionOpzioniAggiuntive;
	protected DynamicForm opzioniAggiuntiveForm;
	protected CheckboxItem flgContenutiPaginaDedicataItem;
	protected CheckboxItem flgExportOpenDataItem;
	protected CheckboxItem flgMostraDatiAggiornamentoItem;
	
	//Sezione Definizione colonne
	protected DetailSection detailSectionDefinizioneColonne;
	protected DynamicForm definizioneColonneForm;
	protected InfoStrutturaTabellaItem infoStrutturaTabellaItem; 
	
	//Sezione Visualizzazione dati tabella
	protected DetailSection detailSectionVisualizzaDatiTabella;
	protected DynamicForm visualizzaDatiTabellaForm; 
	protected ImgButtonItem previewContenutiTabellaItem;
	
	protected DetailSection detailSectionDocumento;
	protected DynamicForm documentoForm;
	protected RadioGroupItem statoDocumentoItem;
	protected TextItem nomeFileItem;
	protected FileUploadItemWithFirmaAndMimeType uploadFileItem;
	protected ImgButtonItem showPreviewItem;
	protected ImgButtonItem showDettUdDaCaricareItem;
	protected ImgButtonItem creaDocumentoButtonItem;
	protected ImgButtonItem showDettUdPresenteItem;
	protected SelectItem tipoRegNumItem;
	protected ExtendedTextItem siglaRegNumItem;
	protected ExtendedNumericItem annoRegNumItem;
	protected ExtendedNumericItem nroRegNumItem;
	protected ExtendedNumericItem nroAllegatoItem;
	protected ImgButtonItem selezionaFileButton;
	protected ImgButtonItem lookupArchivioButton;
	protected HiddenItem idUdHiddenItem;
	
	/*INFO DOCUMENTO*/
//	protected HiddenItem displayFileHiddenItem;
	protected HiddenItem uriFileHiddenItem;
	protected HiddenItem flgFileFirmatoHiddenItem;
	protected HiddenItem mimeTypeFileHiddenItem;
	protected HiddenItem improntaFileHiddenItem;
	protected HiddenItem algoritmoImprontaHiddenItem;
	protected HiddenItem encodingImprontaHiddenItem;
	protected HiddenItem idDocFileHiddenItem;
	protected HiddenItem nroAllegatoHiddenItem;
	
	protected HiddenItem idContenutoHiddenItem;
	protected HiddenItem tipoContenutoHiddenItem;	
	protected HiddenItem idSezioneHiddenItem;
	protected HiddenItem nroOrdineInSezHiddenItem;

	protected Button confermaButton;
	protected Button annullaButton;
	protected Button chiudiButton;
	protected Button modificaButton;
	
	private static final String TIPO_CONTENUTO_FINE_SEZIONE = "fine_sezione";
	private static final String TIPO_CONTENUTO_PARAGRAFO = "paragrafo";
	private static final String TIPO_CONTENUTO_FILE_SEMPLICE = "file_semplice";
	private static final String TIPO_CONTENUTO_DOCUMENTO_COMPLESSO = "documento_complesso";
	private static final String TIPO_CONTENUTO_TABELLA = "tabella";
	private static final String TIPO_CONTENUTO_TITOLO_SEZIONE = "titolo_sezione";
	private static final String TIPO_CONTENUTO_HEADER = "header";
	private static final String TIPO_CONTENUTO_RIF_NORMATIVI = "rif_norm";
	
	private String tipoContenuto;
	private String mode;
	private boolean modify;
	private Boolean flgToAbil;
	private Record recordDettaglio;
	private Record recordDettaglioSalvato;
	private CustomLayout layout;
	private boolean isAbilToModify;
	
	private Record fileDocumento;
	
	public SezionePopup(CustomLayout layout, Integer numOrdine, String idSezione, Boolean flgToAbil,
			String tipoContenuto, boolean modify, Record recordDettaglio) {

		super("sezione", true);

		this.modify = modify;
		this.flgToAbil = flgToAbil;
		this.isAbilToModify = recordDettaglio != null && recordDettaglio.getAttribute("abilitazioni_modifica") != null ? recordDettaglio.getAttributeAsBoolean("abilitazioni_modifica") : true;
		
		this.recordDettaglio = recordDettaglio;
		this.layout = layout;
		
		_window = this;
		
		vm = new ValuesManager();
		
		tipoContenutoHiddenItem = new HiddenItem("tipoContenuto");
		if(tipoContenuto==null || "".equalsIgnoreCase(tipoContenuto)) {
			tipoContenuto = (String) tipoContenutoHiddenItem.getValue();
		}			
		nroOrdineInSezHiddenItem = new HiddenItem("nroOrdineInSez");
		if(numOrdine!=null ) {
			nroOrdineInSezHiddenItem.setValue(numOrdine);
		}			
		idSezioneHiddenItem = new HiddenItem("idSezione");
		if(idSezione!=null && !"".equalsIgnoreCase(idSezione)) {
			idSezioneHiddenItem.setValue(idSezione);
		}	
		
		setTipologiaContenuto(tipoContenuto);
		setCustomTitle();
		setCustomIcon();
		
		setAutoCenter(true);
		setHeight(700);
		setWidth(1000);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		createDetailSectionTitolo();
		createDetailSectionStato();
		createDetailSectionAuotorizzRich();
		createDetailSectionDocumento();
		createDetailSectionDescrizione();
		createDetailSectionDescrizioneDettaglio();
		createDetailSectionPubblicazione();
		createSezioneOpzioniAggiuntive();
		createSezioneDefinizioneColonne();
		createSezioneVisualizzaDatiTabella();
		  			
		SpacerItem spacer = new SpacerItem();
		spacer.setWidth("100%");
		spacer.setStartRow(true);
			
		confermaButton = new Button("Salva");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				confermaButton.focusAfterGroup();
				if (validate()) {
					Record recToSave = getRecordToSave();
					if(isDocumentoConAllegati()) {					
						if(statoDocumentoItem.getValueAsString().equalsIgnoreCase("da_caricare")) {
							creaDocumentoButtonItem.validate();
						}
						onClickOkButton(recToSave);	
					} else {		
						onClickOkButton(recToSave);
					}
				}
			}
		});
		
		modificaButton = new Button("Modifica");
		modificaButton.setIcon("buttons/modify.png");
		modificaButton.setIconSize(16);
		modificaButton.setAutoFit(false);
		modificaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				_window.modify = true;
				setEditMode();
			}
		});
		
		annullaButton = new Button("Annulla");
		annullaButton.setIcon("buttons/undo.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				editRecordModificaContenuto(recordDettaglioSalvato);
				setViewMode();
			}
		});
		
		chiudiButton = new Button("Chiudi");
		chiudiButton.setIcon("annulla.png");
		chiudiButton.setIconSize(16);
		chiudiButton.setAutoFit(false);
		chiudiButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				_window.markForDestroy();
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(confermaButton);
		_buttons.addMember(modificaButton);
		_buttons.addMember(chiudiButton);
		_buttons.addMember(annullaButton);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.AUTO);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
						
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_TITOLO_SEZIONE) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_FILE_SEMPLICE) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_TABELLA)) {
			portletLayout.addMember(detailSectionTitolo);	
		}		
		portletLayout.addMember(detailSectionStato);
		portletLayout.addMember(detailSectionAuotorizzRich);		
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_FILE_SEMPLICE)) {
			portletLayout.addMember(detailSectionDocumento);
		}		
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_HEADER) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_RIF_NORMATIVI) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_FILE_SEMPLICE) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_PARAGRAFO)) {
			portletLayout.addMember(detailSectionDescrizione);
		}	
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO)){
			portletLayout.addMember(detailSectionDescrizioneDettaglio);
		}				
		portletLayout.addMember(detailSectionPeriodoPubblicazione);		
		portletLayout.addMember(detailSectionOpzioniAggiuntive);				
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_TABELLA)){
			portletLayout.addMember(detailSectionDefinizioneColonne);
			portletLayout.addMember(detailSectionVisualizzaDatiTabella);			
		}
			
		
		portletLayout.addMember(spacerLayout);
		portletLayout.addMember(_buttons);
						
		modificaButton.hide();
		if (recordDettaglioSalvato == null) {
			annullaButton.hide();
		}
		setBody(portletLayout);
	}
		
	private void setCustomIcon()  {
	
		String icon;
		
		switch(tipoContenuto) {
		case TIPO_CONTENUTO_DOCUMENTO_COMPLESSO:
			icon = "buttons/documentoConAllegati.png";
		break;
		case TIPO_CONTENUTO_FILE_SEMPLICE:
			icon = "buttons/documentoSemplice.png";
			break;
		case TIPO_CONTENUTO_FINE_SEZIONE:
			icon = "buttons/fineSezione.png";
			break;
		case TIPO_CONTENUTO_PARAGRAFO:
			icon = "buttons/paragrafo.png";
			break;
		case TIPO_CONTENUTO_TABELLA:
			icon = "buttons/tabella.png";
			break;
		case TIPO_CONTENUTO_TITOLO_SEZIONE:
			icon = "buttons/titoloSezione.png";
			break;		
		case TIPO_CONTENUTO_HEADER:
			icon = "buttons/header.png";
			break;					
		case TIPO_CONTENUTO_RIF_NORMATIVI:
			icon = "buttons/riferimentiNormativi.png";
			break;
			
		default:
			icon = "";
		}
		
		setIcon(icon);
	}
	
	private void setCustomTitle() {
		String title;
		
		switch(tipoContenuto) {
		case TIPO_CONTENUTO_DOCUMENTO_COMPLESSO:
			title = I18NUtil.getMessages().contenuti_amministrazione_trasparente_documentoConAllegatiMenuItem_title();
		break;
		case TIPO_CONTENUTO_FILE_SEMPLICE:
			title = I18NUtil.getMessages().contenuti_amministrazione_trasparente_documentoSempliceMenuItem_title();
			break;
		case TIPO_CONTENUTO_FINE_SEZIONE:
			title = I18NUtil.getMessages().contenuti_amministrazione_trasparente_fineSezioneMenuItem_title();
			break;
		case TIPO_CONTENUTO_PARAGRAFO:
			title = I18NUtil.getMessages().contenuti_amministrazione_trasparente_paragrafoMenuItem_title();
			break;
		case TIPO_CONTENUTO_TABELLA:
			title = I18NUtil.getMessages().contenuti_amministrazione_trasparente_tabellaMenuItem_title();
			break;
		case TIPO_CONTENUTO_TITOLO_SEZIONE:
			title = I18NUtil.getMessages().contenuti_amministrazione_trasparente_titoloSezioneMenuItem_title();
			break;		
		case TIPO_CONTENUTO_HEADER:
			title = I18NUtil.getMessages().contenuti_amministrazione_trasparente_headerMenuItem_title();
			break;					
		case TIPO_CONTENUTO_RIF_NORMATIVI:
			title = I18NUtil.getMessages().contenuti_amministrazione_trasparente_rifNormativiMenuItem_title();
			break;
			
		default:
			title = "";
		}
		
		setTitle(title);		
	}

	private void createDetailSectionDescrizioneDettaglio() {
	   	
    	descrizioneDettaglioForm = new DynamicForm();
    	descrizioneDettaglioForm.setValuesManager(vm);
    	descrizioneDettaglioForm.setHeight("5");
    	descrizioneDettaglioForm.setPadding(5);
    	descrizioneDettaglioForm.setWrapItemTitles(false);
    	descrizioneDettaglioForm.setNumCols(10);
    	descrizioneDettaglioForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
    	
    	if(flgTestiHtmlUgualiItem.getValueAsBoolean()) {
			descrizioneDettaglioForm.setVisible(true);
		} else {
			descrizioneDettaglioForm.setVisible(false);
		}
		
		descrizioneDettaglioItem = new CKEditorItem("testoHtmlDettaglio","TRASPARENZA");
		descrizioneDettaglioItem.setShowTitle(false);
		descrizioneDettaglioItem.setColSpan(20);
		descrizioneDettaglioItem.setWidth("100%");
		
		descrizioneDettaglioForm.setFields(descrizioneDettaglioItem);
		
		detailSectionDescrizioneDettaglio = new DetailSection("DESCRIZIONE IN MASCHERA DA DETTAGLIO", false, true, false, descrizioneDettaglioForm);					
	}

	private void setTipologiaContenuto(String tipoSezione) {
		tipoContenutoHiddenItem = new HiddenItem("tipoContenuto");
		tipoContenutoHiddenItem.setValue(tipoSezione);
		
		this.tipoContenuto = tipoSezione;
	}

	protected Record getRecordToSave() {
		Record record = new Record(vm.getValues());
		
		record.setAttribute("testoHtmlSezione", descrizioneItem != null ? descrizioneItem.getValue() : null);
    	record.setAttribute("htmlContenuto", descrizioneItem != null ? descrizioneItem.getValue() : null);   			
		record.setAttribute("testoHtmlDettaglio", descrizioneDettaglioItem != null ? descrizioneDettaglioItem.getValue() : null);
		
		return record;
	}

	protected boolean validate() {	
		boolean vmValidate = true;
				
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_TITOLO_SEZIONE) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_FILE_SEMPLICE) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_TABELLA)) {
			vmValidate = vmValidate && titoloForm.validate();
		}
		
		vmValidate = vmValidate && auotorizzRichForm.validate();		
		
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_FILE_SEMPLICE)) {
			vmValidate = vmValidate && documentoForm.validate();
			if(statoDocumentoItem.getValueAsString().equalsIgnoreCase("presente") && "".equalsIgnoreCase((String) idDocFileHiddenItem.getValue())){
				return false;
			}
		}				
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_FILE_SEMPLICE) ||
				tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_PARAGRAFO)) {
			vmValidate = vmValidate && descrizioneItem.validate();
		}	
				
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_HEADER) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_RIF_NORMATIVI) ) {			
			// Se sono in EDIT non faccio il controllo di validata', altrimenti in NEW lo faccio
			if (this.modify){
				vmValidate = vmValidate;
			}
			else{
				vmValidate = vmValidate && descrizioneItem.validate();
			}				
		}
		
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO)){
			vmValidate = vmValidate && descrizioneDettaglioItem.validate();
		}
		
		vmValidate = vmValidate && periodoPubblicazioneForm.validate();
		vmValidate = vmValidate && detailSectionOpzioniAggiuntive.validate();	
		
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_TABELLA)) {
			vmValidate = vmValidate && definizioneColonneForm.validate();
			vmValidate = vmValidate && infoStrutturaTabellaItem.validate();
		}
		return vmValidate;
	}
	
	protected boolean isDocumentoConAllegati() {
		return tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO);
	}

	private void createDetailSectionPubblicazione() {   	
		
    	periodoPubblicazioneForm = new DynamicForm();
    	periodoPubblicazioneForm.setValuesManager(vm);
    	periodoPubblicazioneForm.setHeight("5");
    	periodoPubblicazioneForm.setPadding(5);
    	periodoPubblicazioneForm.setWrapItemTitles(false);
    	periodoPubblicazioneForm.setNumCols(10);
    	periodoPubblicazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
    	
		// Data inizio pubblicazione
    	dataInizioPubblicazioneItem = new ExtendedDateItem("dtInizioPubblicazione", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_dataInizioPubblicazioneItem_title());		
    	dataInizioPubblicazioneItem.setRequired(true);		
		dataInizioPubblicazioneItem.setStartRow(true);		
		dataInizioPubblicazioneItem.setDefaultValue(getDefaultValueDataInizioPubbl());

    	dataFinePubblicazioneItem = new ExtendedDateItem("dtFinePubblicazione", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_dataFinePubblicazioneItem_title());		

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
			lPeriodoPubblicazioneValidator.setErrorMessage("La data di fine pubblicazione non puÃ² essere antecedente a quella di inizio pubblicazione");
		} else {
			lPeriodoPubblicazioneValidator.setErrorMessage("La data di fine pubblicazione deve essere successiva a quella di inizio pubblicazione");
		}		
		//TODO La data di fine pubblicazione non puÃ² essere una data passata
		dataFinePubblicazioneItem.setValidators(lPeriodoPubblicazioneValidator);
    	
		periodoPubblicazioneForm.setFields(dataInizioPubblicazioneItem, dataFinePubblicazioneItem);
		
		detailSectionPeriodoPubblicazione = new DetailSection(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_detailSectionPeriodoPubblicazione_title(), false, true, false, periodoPubblicazioneForm);		
	}

	private void createSezioneDefinizioneColonne() {   	
		
		definizioneColonneForm = new DynamicForm();
		definizioneColonneForm.setValuesManager(vm);
		definizioneColonneForm.setHeight("5");
		definizioneColonneForm.setPadding(5);
		definizioneColonneForm.setWrapItemTitles(false);
		definizioneColonneForm.setNumCols(10);
		definizioneColonneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
    	
		infoStrutturaTabellaItem = new InfoStrutturaTabellaItem();
		infoStrutturaTabellaItem.setName("infoStrutturaTabella");
		infoStrutturaTabellaItem.setShowTitle(false);
		infoStrutturaTabellaItem.setObbligatorio(true);

		definizioneColonneForm.setFields(infoStrutturaTabellaItem);
		
		detailSectionDefinizioneColonne = new DetailSection(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_detailSectionDefinizioneColonne_title(), false, true, false, definizioneColonneForm);
		detailSectionDefinizioneColonne.setViewReplicableItemHeight(300);
	}
	
	private void createSezioneVisualizzaDatiTabella() {
		visualizzaDatiTabellaForm = new DynamicForm();
		visualizzaDatiTabellaForm.setValuesManager(vm);
		visualizzaDatiTabellaForm.setHeight("5");
		visualizzaDatiTabellaForm.setPadding(5);
		visualizzaDatiTabellaForm.setWrapItemTitles(false);
		visualizzaDatiTabellaForm.setNumCols(10);
		visualizzaDatiTabellaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		previewContenutiTabellaItem = new ImgButtonItem("previewTabella", "visualizzaDati.png", "Accedi ai contenuti tabella");
		previewContenutiTabellaItem.setShowTitle(true);
		previewContenutiTabellaItem.setTitle("Accedi ai contenuti tabella");
		previewContenutiTabellaItem.setIcon("visualizzaDati.png");
		previewContenutiTabellaItem.setStartRow(true);
		previewContenutiTabellaItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickPreviewContenutiTabella();
			}
		});
		
		previewContenutiTabellaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return true;
			}
		});
		
		visualizzaDatiTabellaForm.setFields(previewContenutiTabellaItem);
		
		detailSectionVisualizzaDatiTabella = new DetailSection("", false, true, false, visualizzaDatiTabellaForm);
	}

	private void createDetailSectionDocumento() {
		documentoForm = new DynamicForm();
		documentoForm.setValuesManager(vm);
		documentoForm.setHeight("5");
		documentoForm.setPadding(5);
		documentoForm.setWrapItemTitles(false);
		documentoForm.setNumCols(15);
		documentoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*");

		CustomValidator attoPresenteValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (!(statoDocumentoItem.getValue() != null
						&& statoDocumentoItem.getValueAsString().equals("da_caricare"))) {
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
						return idUdHiddenItem.getValue() != null && !"".equals(idUdHiddenItem.getValue());
					}
				}
				return true;
			}
		};
		attoPresenteValidator.setErrorMessage("Documento non presente a sistema");

		// Stato atto : da caricare/gia' presente a sistema
		LinkedHashMap<String, String> statoDocumentoMap = new LinkedHashMap<String, String>();
		statoDocumentoMap.put("da_caricare", "da caricare");
		statoDocumentoMap.put("presente", "già  presente a sistema");
		statoDocumentoItem = new RadioGroupItem("statoDocumento");
		statoDocumentoItem.setValueMap(statoDocumentoMap);
		statoDocumentoItem.setVertical(false);
		statoDocumentoItem.setWrap(false);
		statoDocumentoItem.setShowTitle(false);
		statoDocumentoItem.setWidth(120);
		statoDocumentoItem.setColSpan(10);
		statoDocumentoItem.setStartRow(true);
		statoDocumentoItem.setDefaultValue("presente");	
		statoDocumentoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				nomeFileItem.setValue("");
				uriFileHiddenItem.setValue("");
				tipoRegNumItem.setValue("");
				siglaRegNumItem.setValue("");
				nroRegNumItem.setValue("");
				annoRegNumItem.setValue("");
				idUdHiddenItem.setValue("");
				idDocFileHiddenItem.setValue("");
				nroAllegatoHiddenItem.setValue("");
				nroAllegatoItem.setValue("");
				fileDocumento = null;

				documentoForm.markForRedraw();
			}
		});
		statoDocumentoItem.setValidators(attoPresenteValidator);
		
		/***********************************************************************************************************************************************************/
		/*				L'UPLOAD VIENE MOSTRATO SOLO PER LA SEZIONE FILE SEMPLICE																				   */
		/***********************************************************************************************************************************************************/
		CustomValidator lNomeFileValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				return (value != null && !"".equals(value)) && uriFileHiddenItem.getValue() != null && !"".equals(uriFileHiddenItem.getValue());
			}
		};
		lNomeFileValidator.setErrorMessage("Attenzione, file non caricato correttamente");

		nomeFileItem = new TextItem("displayFile", "Nome file");
		nomeFileItem.setShowTitle(true);
		nomeFileItem.setWidth(280);
		nomeFileItem.setStartRow(true);
		nomeFileItem.setColSpan(1);
		nomeFileItem.setAlign(Alignment.LEFT);
		nomeFileItem.setValidators(lNomeFileValidator);
		nomeFileItem.setAttribute("obbligatorio", true);
		nomeFileItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return statoDocumentoItem.getValueAsString()!=null && "da_caricare".equalsIgnoreCase(statoDocumentoItem.getValueAsString());
			}
		}));
		nomeFileItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return statoDocumentoItem.getValueAsString()!=null && "da_caricare".equalsIgnoreCase(statoDocumentoItem.getValueAsString());
			}
		});	

		uploadFileItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				documentoForm.clearErrors(true);
				documentoForm.setValue("displayFile", displayFileName);
				documentoForm.setValue("uriFile", uri);
				changedEventAfterUpload(displayFileName, uri, documentoForm, nomeFileItem, uriFileHiddenItem);
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
				uploadFileItem.getCanvas().redraw();
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				flgFileFirmatoHiddenItem.setValue(info.isFirmato());
				mimeTypeFileHiddenItem.setValue(info.getMimetype());
				improntaFileHiddenItem.setValue(info.getImpronta());
			}
		});
		uploadFileItem.setColSpan(1);
		uploadFileItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				uploadFileItem.setAttribute("nascosto", !(statoDocumentoItem.getValueAsString()!=null && "da_caricare".equalsIgnoreCase(statoDocumentoItem.getValueAsString())));				
				return statoDocumentoItem.getValueAsString()!=null && "da_caricare".equalsIgnoreCase(statoDocumentoItem.getValueAsString());
			}
		});	
		
		showPreviewItem = new ImgButtonItem("", "file/preview.png", "Anteprima file");
		showPreviewItem.setColSpan(1);
		showPreviewItem.setIconWidth(16);
		showPreviewItem.setIconHeight(16);
		showPreviewItem.setIconVAlign(VerticalAlignment.BOTTOM);
		showPreviewItem.setAlign(Alignment.LEFT);
		showPreviewItem.setWidth(16);
		showPreviewItem.setRedrawOnChange(true);
		showPreviewItem.setAlwaysEnabled(true);
		showPreviewItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record record = new Record();
				record.setAttribute("uri", uriFileHiddenItem.getValue());
				record.setAttribute("displayFilnename", nomeFileItem.getValue());

				final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ContenutoFoglioImportatoDataSource");
				lGwtRestDataSource.executecustom("calcolaInfoFile", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							InfoFileRecord infoFile = new InfoFileRecord(record);
							PreviewControl.switchPreview((String) uriFileHiddenItem.getValue(), false, infoFile, "FileToExtractBean",  nomeFileItem.getValueAsString());
						}
					}
				});
			}
		});
		showPreviewItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {								
				return uriFileHiddenItem.getValue() != null && !"".equalsIgnoreCase((String) uriFileHiddenItem.getValue()) &&
						statoDocumentoItem.getValueAsString()!=null && "da_caricare".equalsIgnoreCase(statoDocumentoItem.getValueAsString());
			}
		});
		
		showDettUdDaCaricareItem = new ImgButtonItem("showDettUdDaCaricare", "buttons/detail.png", "Dettaglio Documento");
		showDettUdDaCaricareItem.setColSpan(1);
		showDettUdDaCaricareItem.setIconWidth(16);
		showDettUdDaCaricareItem.setIconHeight(16);
		showDettUdDaCaricareItem.setIconVAlign(VerticalAlignment.BOTTOM);
		showDettUdDaCaricareItem.setAlign(Alignment.LEFT);
		showDettUdDaCaricareItem.setWidth(16);
		showDettUdDaCaricareItem.setRedrawOnChange(true);
		showDettUdDaCaricareItem.setAlwaysEnabled(true);
		showDettUdDaCaricareItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageDettaglioUD((String)idUdHiddenItem.getValue());
			}
		});
		showDettUdDaCaricareItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {								
				return idUdHiddenItem.getValue() != null && !"".equalsIgnoreCase((String) idUdHiddenItem.getValue())  && statoDocumentoItem.getValue() != null
						&& statoDocumentoItem.getValueAsString().equals("da_caricare") && idDocFileHiddenItem.getValue() != null && !"".equalsIgnoreCase((String) idDocFileHiddenItem.getValue());
			}
		});
				
		/***********************************************************************************************************************************************************/
		/*				LA CREAZIONE DEL REPERTORIO VIENE MOSTRATA SOLO PER IL DOCUMENTO COMPLESSO																			   */
		/***********************************************************************************************************************************************************/
		creaDocumentoButtonItem = new ImgButtonItem("creaDocumentoButton", "buttons/new.png", "Crea documento");
		creaDocumentoButtonItem.setTitle("Crea documento");
		creaDocumentoButtonItem.setStartRow(true);
		creaDocumentoButtonItem.setColSpan(1);
		creaDocumentoButtonItem.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {

				manageOnClickCreaDocumento();
			}
		});
		creaDocumentoButtonItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return statoDocumentoItem.getValue() != null
						&& statoDocumentoItem.getValueAsString().equals("da_caricare");
			}
		});
		CustomValidator idDocFilePresenteValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				return idDocFileHiddenItem != null && idDocFileHiddenItem.getValue() != null && !"".equalsIgnoreCase((String) idDocFileHiddenItem.getValue());
			}
		};
		idDocFilePresenteValidator.setErrorMessage("Il documento deve essere presente a sistema prima di procedere");
		creaDocumentoButtonItem.setValidators(idDocFilePresenteValidator);
		
		showDettUdPresenteItem = new ImgButtonItem("showDettUdPresente", "buttons/detail.png", "Dettaglio Documento");
		showDettUdPresenteItem.setColSpan(1);
		showDettUdPresenteItem.setIconWidth(16);
		showDettUdPresenteItem.setIconHeight(16);
		showDettUdPresenteItem.setIconVAlign(VerticalAlignment.BOTTOM);
		showDettUdPresenteItem.setAlign(Alignment.LEFT);
		showDettUdPresenteItem.setWidth(16);
		showDettUdPresenteItem.setRedrawOnChange(true);
		showDettUdPresenteItem.setAlwaysEnabled(true);
		showDettUdPresenteItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageDettaglioUD((String)idUdHiddenItem.getValue());
			}
		});
		showDettUdPresenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {								
				return idUdHiddenItem.getValue() != null && !"".equalsIgnoreCase((String) idUdHiddenItem.getValue()) && statoDocumentoItem.getValue() != null
						&& statoDocumentoItem.getValueAsString().equals("presente");
			}
		});
				
		
		// **********************************************
		// Estremi di registrazione
		// **********************************************
		// Tipo
		GWTRestDataSource tipoDS = new GWTRestDataSource("LoadComboTipoDocCollegatoDataSource", "key", FieldType.TEXT);
		tipoDS.extraparam.put("finalita", "PUBBLICAZIONE");
		tipoRegNumItem = new SelectItem("tipoRegNum",
				I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_tipoRegistrazioneItem_title());
		tipoRegNumItem.setOptionDataSource(tipoDS);
		tipoRegNumItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
			
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				Criterion[] criterias = new Criterion[1];
				if (AurigaLayout.isAttivoModuloProt() && statoDocumentoItem.getValue().equals("da_caricare")) {
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
		tipoRegNumItem.setAttribute("obbligatorio", true);
		tipoRegNumItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return statoDocumentoItem.getValueAsString()!=null && "presente".equalsIgnoreCase(statoDocumentoItem.getValueAsString());
			}
		}));
		tipoRegNumItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUd", (String) null);
				if (statoDocumentoItem.getValue() != null && statoDocumentoItem.getValue().equals("presente")) {
					manageRicercaDocumento();
				}
			}
		});
		tipoRegNumItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg") : "";
				if (!"".equals(estremiReg)) {
					return estremiReg;
				}
				return null;
			}
		});
		tipoRegNumItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return statoDocumentoItem.getValueAsString()!=null && "presente".equalsIgnoreCase(statoDocumentoItem.getValueAsString());
			}
		});

        // Sigla Registro
		siglaRegNumItem = new ExtendedTextItem("siglaRegNum", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_siglaRegistrazioneItem_title());
		siglaRegNumItem.setWidth(110);
		siglaRegNumItem.setColSpan(1);
		siglaRegNumItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return tipoRegNumItem.getValueAsString() != null
						&& ("R".equalsIgnoreCase(tipoRegNumItem.getValueAsString())
								|| "PP".equalsIgnoreCase(tipoRegNumItem.getValueAsString()));
			}
		}));
		siglaRegNumItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoRegNumItem.getValueAsString() != null
						&& statoDocumentoItem.getValueAsString()!=null && "presente".equalsIgnoreCase(statoDocumentoItem.getValueAsString()) &&
								("R".equalsIgnoreCase(tipoRegNumItem.getValueAsString())
								|| "PP".equalsIgnoreCase(tipoRegNumItem.getValueAsString()));
			}
		});
		siglaRegNumItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUd", (String) null);
				if (statoDocumentoItem.getValue() != null && statoDocumentoItem.getValue().equals("presente")) {
					manageRicercaDocumento();
				}
			}
		});

		// Anno
		annoRegNumItem = new ExtendedNumericItem("annoRegNum",I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_annoRegistrazioneItem_title());
		annoRegNumItem.setColSpan(1);
		annoRegNumItem.setLength(4);
		annoRegNumItem.setWidth(70);
		annoRegNumItem.setAttribute("obbligatorio", true);
		annoRegNumItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return statoDocumentoItem.getValueAsString()!=null && "presente".equalsIgnoreCase(statoDocumentoItem.getValueAsString());
			}
		}));
		annoRegNumItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUd", (String) null);
				if (statoDocumentoItem.getValue() != null && statoDocumentoItem.getValue().equals("presente")) {
					manageRicercaDocumento();
				}
			}
		});
		annoRegNumItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg"): "";
				if (!"".equals(estremiReg)) {
					return estremiReg;
				}
				return null;
			}
		});
		annoRegNumItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return statoDocumentoItem.getValueAsString()!=null && "presente".equalsIgnoreCase(statoDocumentoItem.getValueAsString());
			}
		});

		// Numero
		nroRegNumItem = new ExtendedNumericItem("nroRegNum", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_numeroRegistrazioneItem_title(), false);
		nroRegNumItem.setColSpan(1);
		nroRegNumItem.setLength(7);
		nroRegNumItem.setWidth(110);
		nroRegNumItem.setAttribute("obbligatorio", true);
		nroRegNumItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return statoDocumentoItem.getValueAsString()!=null && "presente".equalsIgnoreCase(statoDocumentoItem.getValueAsString());
			}
		}));
		nroRegNumItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUd", (String) null);
				if (statoDocumentoItem.getValue() != null && statoDocumentoItem.getValue().equals("presente")) {
					manageRicercaDocumento();
					}

			}
		});
		nroRegNumItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String estremiReg = form.getValueAsString("estremiReg") != null ? form.getValueAsString("estremiReg"): "";
				if (!"".equals(estremiReg)) {
					return estremiReg;
				}
				return null;
			}
		});
		nroRegNumItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return statoDocumentoItem.getValueAsString()!=null && "presente".equalsIgnoreCase(statoDocumentoItem.getValueAsString());
			}
		});
		
		// Numero
		nroAllegatoItem = new ExtendedNumericItem("nroAllegato", I18NUtil.getMessages().contenuti_amministrazione_trasparente_detail_nroAllegato_title(), false);
		nroAllegatoItem.setColSpan(1);
		nroAllegatoItem.setCanEdit(false);
		nroAllegatoItem.setLength(4);
		nroAllegatoItem.setWidth(110);
		nroAllegatoItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return idUdHiddenItem.getValue() != null && !"".equalsIgnoreCase((String) idUdHiddenItem.getValue()) && statoDocumentoItem.getValue() != null
						&& statoDocumentoItem.getValueAsString().equals("presente") && nroAllegatoHiddenItem.getValue() != null && tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_FILE_SEMPLICE);
			}
		});
		
		selezionaFileButton = new ImgButtonItem("selezionaFileButton", "archivio/file.png", "Seleziona file");
		selezionaFileButton.setEndRow(false);
		selezionaFileButton.setColSpan(1);
		selezionaFileButton.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {

				ListaAllegatiDocumentoPopup listaAllegatiDocumentoPopup = new ListaAllegatiDocumentoPopup() {


					@Override
					public void manageRecordSelection(Record record) {
						fileDocumento.setAttribute("idDoc", record.getAttribute("idDocAllegato"));
						fileDocumento.setAttribute("nroAllegato", record.getAttribute("nroAllegato")); /*TODO_CAPIRE QUALCE CAMPO PASSARE*/
						editRecordEstremi(fileDocumento);
						markForRedraw();
					}
				}; 
				listaAllegatiDocumentoPopup.initContent(fileDocumento);
				listaAllegatiDocumentoPopup.show();	
			}
		});
		selezionaFileButton.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return statoDocumentoItem.getValue() != null
						&& statoDocumentoItem.getValueAsString().equals("presente") && nroRegNumItem.getCanEdit() && fileDocumento!=null;
//						nroAllegatoHiddenItem.getValue() != null && !"".equalsIgnoreCase((String) nroAllegatoHiddenItem.getValue());
			}
		});
		

		// BOTTONI : seleziona dall'archivio
		lookupArchivioButton = new ImgButtonItem("lookupArchivioButton", "lookup/archivio.png", "Seleziona dall'archivio");
		lookupArchivioButton.setEndRow(false);
		lookupArchivioButton.setColSpan(1);
		lookupArchivioButton.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {

				DocumentoCollegatoLookupArchivio lookupArchivioPopup = new DocumentoCollegatoLookupArchivio(documentoForm.getValuesAsRecord());
				lookupArchivioPopup.show();
			}
		});
		lookupArchivioButton.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return statoDocumentoItem.getValue() != null
						&& statoDocumentoItem.getValueAsString().equals("presente") && nroRegNumItem.getCanEdit();
			}
		});

		idUdHiddenItem = new HiddenItem("idUd");
		uriFileHiddenItem = new HiddenItem("uriFile");
		flgFileFirmatoHiddenItem = new HiddenItem("flgFileFirmato");
		mimeTypeFileHiddenItem = new HiddenItem("mimeTypeFile");
		improntaFileHiddenItem = new HiddenItem("improntaFile");
		encodingImprontaHiddenItem = new HiddenItem("encodingImpronta");
		algoritmoImprontaHiddenItem = new HiddenItem("algoritmoImpronta");
		idDocFileHiddenItem = new HiddenItem("idDocFile");
		nroAllegatoHiddenItem = new HiddenItem("nroAllegatoHidden");
		idContenutoHiddenItem = new HiddenItem("idContenuto");
		
		List<FormItem> items = new ArrayList<FormItem>();		
		items.add(statoDocumentoItem); 
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO)){
			items.add(creaDocumentoButtonItem);
		}else {
			items.add(nomeFileItem);
			items.add(uploadFileItem);
			items.add(showPreviewItem);
			items.add(showDettUdDaCaricareItem);			
		}
		items.add(tipoRegNumItem); 
		items.add(siglaRegNumItem); 
		items.add(annoRegNumItem); 
		items.add(nroRegNumItem); 
		items.add(nroAllegatoItem); 
		items.add(selezionaFileButton); 
		items.add(lookupArchivioButton); 
		items.add(showDettUdPresenteItem);		
		items.add(idUdHiddenItem); 
		items.add(uriFileHiddenItem); 
		items.add(flgFileFirmatoHiddenItem); 
		items.add(mimeTypeFileHiddenItem); 
		items.add(improntaFileHiddenItem); 
		items.add(algoritmoImprontaHiddenItem); 
		items.add(encodingImprontaHiddenItem); 
		items.add(idDocFileHiddenItem); 
		items.add(nroAllegatoHiddenItem);
		items.add(idContenutoHiddenItem); 
		items.add(tipoContenutoHiddenItem); 
		items.add(idSezioneHiddenItem); 
		items.add(nroOrdineInSezHiddenItem); 
			
		documentoForm.setFields(items.toArray(new FormItem[items.size()]));
		
		detailSectionDocumento = new DetailSection("DATI DOCUMENTO", false, true, false, documentoForm);		
	}

	private void manageDettaglioUD(String idUd) {
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		new DettaglioRegProtAssociatoWindow(lRecord, "Dettaglio prot. NÂ° " + nroRegNumItem.getValueAsString() + "/" + annoRegNumItem.getValueAsString());
	}

	protected void manageOnClickCreaDocumento() {
		
		String nomeTipoDocumento = AurigaLayout.getParametroDB("SIGLA_REPERTORIO_TRASP_AMM");

		Map<String, Object> initialValues = new HashMap<String, Object>();
		initialValues.put("repertorio", "Trasparenza Amministrativa");
		
		NuovoRepertorioInternoWindow lNuovoRepertorioWindow = new NuovoRepertorioInternoWindow(null, "Repertorio interno", initialValues) {
			
			@Override
			public void afterRegistra(Record record) {
				editRecordEstremiFromRepertorio(record);				
			}
		};
		lNuovoRepertorioWindow.show();
	}

	protected void editRecordEstremiFromRepertorio(Record record) {
		tipoRegNumItem.setValue(record.getAttribute("tipoProtocollo"));
		siglaRegNumItem.setValue(record.getAttribute("siglaProtocollo"));
		nroRegNumItem.setValue(record.getAttribute("nroProtocollo"));
		annoRegNumItem.setValue(record.getAttribute("annoProtocollo"));
		idUdHiddenItem.setValue(record.getAttribute("idUd"));
		idDocFileHiddenItem.setValue(record.getAttribute("idDocPrimario"));			
		nroAllegatoItem.setValue(record.getAttribute("nroAllegato"));		
		nroAllegatoHiddenItem.setValue(record.getAttribute("nroAllegato"));	
		statoDocumentoItem.setValue("presente");
		markForRedraw();
		
	}

	private void createDetailSectionDescrizione() {   
					
    	descrizioneForm = new DynamicForm();
    	descrizioneForm.setValuesManager(vm);
    	descrizioneForm.setHeight("5");
    	descrizioneForm.setPadding(5);
    	descrizioneForm.setWrapItemTitles(false);
    	descrizioneForm.setNumCols(10);
    	descrizioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
    	
    	cronologiaOperazioniDescrizioneButton = new ImgButtonItem("cronologiaOperazioniDescrizione",
				"protocollazione/operazioniEffettuate.png",
				"Cronologia operazioni");
    	cronologiaOperazioniDescrizioneButton.setAlwaysEnabled(true);
    	cronologiaOperazioniDescrizioneButton.setColSpan(1);
    	cronologiaOperazioniDescrizioneButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				new CronologiaOperazioniWindow((String) idContenutoHiddenItem.getValue());
			}
		});
    	cronologiaOperazioniDescrizioneButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return idContenutoHiddenItem != null && (String) idContenutoHiddenItem.getValue() != null
						&& !"".equalsIgnoreCase((String) idContenutoHiddenItem.getValue());
			}
		});
    	
		descrizioneItem = new CKEditorItem("htmlContenuto", "TRASPARENZA");
		descrizioneItem.setShowTitle(false);
		descrizioneItem.setColSpan(20);
		descrizioneItem.setWidth("100%");
		descrizioneItem.setRequired(true);
		
		flgTestiHtmlUgualiItem = new CheckboxItem("flgTestiHtmlUguali", "differenzia descrizione in maschera di dettaglio");
		flgTestiHtmlUgualiItem.setColSpan(6);
		flgTestiHtmlUgualiItem.setStartRow(true);
		flgTestiHtmlUgualiItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				if(flgTestiHtmlUgualiItem.getValueAsBoolean()) {
					descrizioneDettaglioForm.setVisible(true);
				} else {
					descrizioneDettaglioForm.setVisible(false);
				}				
			}
		});	
		
		SpacerItem spacer = new SpacerItem();
		spacer.setWidth("100%");
		spacer.setStartRow(true);		

		List<FormItem> items = new ArrayList<FormItem>();
		items.add(cronologiaOperazioniDescrizioneButton);
		items.add(descrizioneItem); 
		
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO)) {
			items.add(spacer); 
			items.add(flgTestiHtmlUgualiItem); 
		}
			
		descrizioneForm.setFields(items.toArray(new FormItem[items.size()]));
		
		detailSectionDescrizione = new DetailSection((tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_PARAGRAFO) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_HEADER) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_RIF_NORMATIVI))  ? "" : "DESCRIZIONE IN PAGINA", false, true, false, descrizioneForm);			
	}

	private void createSezioneOpzioniAggiuntive() {
		opzioniAggiuntiveForm = new DynamicForm();
		opzioniAggiuntiveForm.setValuesManager(vm);
		opzioniAggiuntiveForm.setWidth100();
		opzioniAggiuntiveForm.setNumCols(20);
		opzioniAggiuntiveForm.setColWidths(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,"*");
		opzioniAggiuntiveForm.setCellPadding(2);
		opzioniAggiuntiveForm.setWrapItemTitles(false);
		
		flgContenutiPaginaDedicataItem = new CheckboxItem("flgPaginaDedicata", "Visualizzazione contenuti in pagina dedicata");
		flgContenutiPaginaDedicataItem.setColSpan(6);
		flgContenutiPaginaDedicataItem.setStartRow(true);
		
		flgExportOpenDataItem = new CheckboxItem("flgExportOpenData", "Tasto di export open data a fine sezione");
		flgExportOpenDataItem.setColSpan(6);
		flgExportOpenDataItem.setStartRow(true);
		
		flgMostraDatiAggiornamentoItem= new CheckboxItem("flgMostraDatiAggiornamento", "Mostra dati ultimo aggiornamento");
		flgMostraDatiAggiornamentoItem.setColSpan(6);
		flgMostraDatiAggiornamentoItem.setStartRow(true);
		
		List<FormItem> items = new ArrayList<FormItem>();				 
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_TITOLO_SEZIONE)) {
			items.add(flgContenutiPaginaDedicataItem); 
			items.add(flgExportOpenDataItem); 
		}
		items.add(flgMostraDatiAggiornamentoItem);
			
		opzioniAggiuntiveForm.setFields(items.toArray(new FormItem[items.size()]));
		
		detailSectionOpzioniAggiuntive = new DetailSection("", false, true, false, opzioniAggiuntiveForm);
	}

	private void createDetailSectionTitolo() {   	
    	titoloForm = new DynamicForm();
    	titoloForm.setValuesManager(vm);
    	titoloForm.setHeight("5");
    	titoloForm.setPadding(5);
    	titoloForm.setWrapItemTitles(false);
    	titoloForm.setNumCols(10);
    	titoloForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
    	
    	titoloItem = new TextItem("titoloContenuto");
		titoloItem.setStartRow(true);
		titoloItem.setRequired(true);	
		titoloItem.setShowTitle(false);
		titoloItem.setWidth(780);
		
		cronologiaOperazioniButton = new ImgButtonItem("cronologiaOperazioni",
				"protocollazione/operazioniEffettuate.png",
				"Cronologia operazioni");
		cronologiaOperazioniButton.setAlwaysEnabled(true);
		cronologiaOperazioniButton.setColSpan(1);
		cronologiaOperazioniButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				new CronologiaOperazioniWindow((String) idContenutoHiddenItem.getValue());
			}
		});
		cronologiaOperazioniButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return idContenutoHiddenItem != null && (String) idContenutoHiddenItem.getValue() != null
						&& !"".equalsIgnoreCase((String) idContenutoHiddenItem.getValue());
			}
		});
				
		titoloForm.setFields(titoloItem, cronologiaOperazioniButton);
		
		detailSectionTitolo = new DetailSection("TITOLO", false, true, false, titoloForm);		
	}
	
	private void createDetailSectionStato() {   	
    	statoForm = new DynamicForm();
    	statoForm.setValuesManager(vm);
    	statoForm.setHeight("5");
    	statoForm.setPadding(5);
    	statoForm.setWrapItemTitles(false);
    	statoForm.setNumCols(10);
    	statoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
    	
    	statoRichPubblItem = new TextItem("statoRichPubbl", "Stato") {
    		
    		@Override
			public void setCanEdit(Boolean canEdit) {
    			super.setCanEdit(false);
    		}
    	};
    	statoRichPubblItem.setShowTitle(false);
		statoRichPubblItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return statoRichPubblItem.getValue() != null && !"".equals(statoRichPubblItem.getValue());
			}
		});
    	
    	motivoRigettoItem = new TextAreaItem("motivoRigetto", "Motivo respingimento") {
    		
    		@Override
			public void setCanEdit(Boolean canEdit) {
    			super.setCanEdit(false);
    		}
    	};
    	motivoRigettoItem.setStartRow(true);
    	motivoRigettoItem.setTitleOrientation(TitleOrientation.TOP);
    	motivoRigettoItem.setLength(4000);
    	motivoRigettoItem.setHeight(82);
    	motivoRigettoItem.setWidth(780);
    	motivoRigettoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return statoRichPubblItem.getValue() != null && "respinta".equals(statoRichPubblItem.getValue());
			}
		});
		
		statoForm.setFields(statoRichPubblItem, motivoRigettoItem);
		
		detailSectionStato = new DetailSection("STATO", false, true, false, statoForm);			
	}
	
	private void createDetailSectionAuotorizzRich() {   	
    	auotorizzRichForm = new DynamicForm();
    	auotorizzRichForm.setValuesManager(vm);
    	auotorizzRichForm.setHeight("5");
    	auotorizzRichForm.setPadding(5);
    	auotorizzRichForm.setWrapItemTitles(false);
    	auotorizzRichForm.setNumCols(10);
    	auotorizzRichForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
    	
    	flgAbilAuotorizzRichItem = new HiddenItem("flgAbilAuotorizzRich");
    	    	
    	LinkedHashMap<String, String> statoAuotorizzRichValueMap = new LinkedHashMap<String, String>();
    	statoAuotorizzRichValueMap.put("autorizzo", "autorizzo");
    	statoAuotorizzRichValueMap.put("respingo", "respingo");
    	statoAuotorizzRichValueMap.put("", "da autorizzare");
		
		statoAuotorizzRichItem = new RadioGroupItem("statoAuotorizzRich", "Richiesta di pubblicazione");   
		statoAuotorizzRichItem.setValueMap(statoAuotorizzRichValueMap);
		statoAuotorizzRichItem.setShowTitle(false);
		statoAuotorizzRichItem.setVertical(false);
		statoAuotorizzRichItem.setWrap(false);
		statoAuotorizzRichItem.setDefaultValue("da autorizzare");		
    	statoAuotorizzRichItem.setAttribute("obbligatorio", true);
    	statoAuotorizzRichItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return mode != null && "edit".equals(mode) && 
					   flgAbilAuotorizzRichItem.getValue() != null && "1".equals(flgAbilAuotorizzRichItem.getValue());
			}
		});
    	statoAuotorizzRichItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return  mode != null && "edit".equals(mode) && 
						flgAbilAuotorizzRichItem.getValue() != null && "1".equals(flgAbilAuotorizzRichItem.getValue());
			}
		}));	
    	statoAuotorizzRichItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				auotorizzRichForm.markForRedraw();				
			}
		});
    	
    	motivoRigettoAuotorizzRichItem = new TextAreaItem("motivoRigettoAuotorizzRich", "Motivo rigetto");
    	motivoRigettoAuotorizzRichItem.setStartRow(true);
    	motivoRigettoAuotorizzRichItem.setShowTitle(false);
    	motivoRigettoAuotorizzRichItem.setHint("Motivo rigetto");
    	motivoRigettoAuotorizzRichItem.setShowHintInField(true);
    	motivoRigettoAuotorizzRichItem.setLength(4000);
    	motivoRigettoAuotorizzRichItem.setHeight(82);
    	motivoRigettoAuotorizzRichItem.setWidth(780);
    	motivoRigettoAuotorizzRichItem.setAttribute("obbligatorio", true);
    	motivoRigettoAuotorizzRichItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return  mode != null && "edit".equals(mode) && 
						flgAbilAuotorizzRichItem.getValue() != null && "1".equals(flgAbilAuotorizzRichItem.getValue()) && 
						statoAuotorizzRichItem.getValue() != null && "respingo".equals(statoAuotorizzRichItem.getValue());
			}
		});
    	motivoRigettoAuotorizzRichItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return mode != null && "edit".equals(mode) && 
					   flgAbilAuotorizzRichItem.getValue() != null && "1".equals(flgAbilAuotorizzRichItem.getValue()) && 
					   statoAuotorizzRichItem.getValue() != null && "respingo".equals(statoAuotorizzRichItem.getValue());
			}
		}));
		
    	auotorizzRichForm.setFields(flgAbilAuotorizzRichItem, statoAuotorizzRichItem, motivoRigettoAuotorizzRichItem);
		
		detailSectionAuotorizzRich = new DetailSection("RICHIESTA DI PUBBLICAZIONE", false, true, true, auotorizzRichForm);		
	}
	
    private Date getDefaultValueDataInizioPubbl() {
    	String defaultDecorPubblAmmTrasp = AurigaLayout.getParametroDB("DEFAULT_DECOR_PUBBL_AMM_TRASP");
    	Date dataInizioPubbl = new Date();
    	if(defaultDecorPubblAmmTrasp == null || defaultDecorPubblAmmTrasp.equals("") || defaultDecorPubblAmmTrasp.equalsIgnoreCase("oggi")) {
    		// tengo la data di oggi;
    	} else if(defaultDecorPubblAmmTrasp.equalsIgnoreCase("domani")) {
    		// aggiungo 1 giorno
    		CalendarUtil.addDaysToDate(dataInizioPubbl, 1);    		
    	} else {
    		// aggiungo <GG_DELAY_INIZIO_PUBBL_ATTO> giorni
    		String giorniDelayInizioPubblAtto = AurigaLayout.getParametroDB("GG_DELAY_INIZIO_PUBBL_ATTO");	    	
	    	CalendarUtil.addDaysToDate(dataInizioPubbl, Integer.parseInt(giorniDelayInizioPubblAtto != null ? giorniDelayInizioPubblAtto : "1"));	    	
    	}
    	return dataInizioPubbl;
	}

    /**
	 * Metodo che indica se nel conteggio dei giorni di pubblicazione il giorno di pubblicazione viene sempre considerato come 1 giorno intero, altrimenti no
	 * 
	 */
	public boolean isConteggiaInteroGiornoCorrenteXPeriodoPubbl() {
		return AurigaLayout.getParametroDBAsBoolean("CONTEGGIA_INTERO_GIORNO_CORRENTE_X_PERIODO_PUBBL");
	}
	
	public void loadDettaglio(String idUd, final ServiceCallback<Record> callback) {
		loadDettaglio(idUd, null, false, callback);
	}
	
	public void loadDettaglioAfterSave(String idUd, String idRichPubbl, final ServiceCallback<Record> callback) {
		loadDettaglio(idUd, idRichPubbl, true, callback);
	}
	
	private void loadDettaglio(String idUd, String idRichPubbl, final Boolean afterSave, final ServiceCallback<Record> callback) {
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("caricaInfoDocumento", lRecord, new DSCallback() {	
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (response.getData() != null) {
						Record infoDocumento = response.getData()[0];
						if (infoDocumento != null) {
							if(callback != null) {
								callback.execute(response.getData()[0]);
							}	
						}
					} 
				}
			}
		});
	}
	
	public void editRecordEstremi(Record record) {
		tipoRegNumItem.setValue(record.getAttribute("tipoRegNum"));
		siglaRegNumItem.setValue(record.getAttribute("siglaRegNum"));
		nroRegNumItem.setValue(record.getAttribute("nroRegNum"));
		annoRegNumItem.setValue(record.getAttribute("annoRegNum"));
		idUdHiddenItem.setValue(record.getAttribute("idUd"));
		idDocFileHiddenItem.setValue(record.getAttribute("idDoc"));
		nroAllegatoItem.setValue(record.getAttribute("nroAllegato")); 
		nroAllegatoHiddenItem.setValue(record.getAttribute("nroAllegato")); 
		statoDocumentoItem.setValue("presente");
		markForRedraw();
	}
	
	public void recuperaIdUd(final ServiceCallback<String> callback) {
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
							String idUd = response.getData()[0].getAttributeAsString("idUdFolder");
							idUdHiddenItem.setValue(idUd);
							callback.execute(idUd);
						} 
					} else {
						if(callback != null) {
							callback.execute(null);
						} 
					}
				}
			});
		}else {
			fileDocumento = null;
			callback.execute(null);
		}
	}
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {}
		}
		super.onDestroy();
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
			return "Seleziona documento";
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
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}
	
	protected void changedEventAfterUpload(final String displayFileName, final String uri, final DynamicForm formFile, final TextItem nomeFileItem,
			final HiddenItem uriItem) {
		ChangedEvent lChangedEventDisplay = new ChangedEvent(nomeFileItem.getJsObj()) {

			@Override
			public DynamicForm getForm() {
				return formFile;
			};

			@Override
			public FormItem getItem() {
				return nomeFileItem;
			}

			@Override
			public Object getValue() {
				return displayFileName;
			}
		};
		ChangedEvent lChangedEventUri = new ChangedEvent(uriItem.getJsObj()) {

			@Override
			public DynamicForm getForm() {
				return formFile;
			};

			@Override
			public FormItem getItem() {
				return uriItem;
			}

			@Override
			public Object getValue() {
				return uri;
			}
		};
		nomeFileItem.fireEvent(lChangedEventDisplay);
		uriItem.fireEvent(lChangedEventUri);
	}

	public void setFormValuesFromRecordArchivio(Record record) {
		documentoForm.clearErrors(true);	
		String idUd = (String) record.getAttribute("idUdFolder");
		if (idUd != null && !"".equals(idUd)) {
			loadDettaglio(idUd, new ServiceCallback<Record>() {
				@Override
				public void execute(final Record recordEstremiDocumento) {
					if (recordEstremiDocumento != null) {
						RecordList listaAllegati = recordEstremiDocumento.getAttributeAsRecordList("listaAllegati");
						if(listaAllegati!=null && listaAllegati.getLength()>0 && tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_FILE_SEMPLICE)) {
							fileDocumento = recordEstremiDocumento;
							
							ListaAllegatiDocumentoPopup listaAllegatiDocumentoPopup = new ListaAllegatiDocumentoPopup() {


								@Override
								public void manageRecordSelection(Record record) {
									recordEstremiDocumento.setAttribute("idDoc", record.getAttribute("idDocAllegato"));
									recordEstremiDocumento.setAttribute("nroAllegato", record.getAttribute("nroAllegato")); /*TODO_CAPIRE QUALCE CAMPO PASSARE*/
									editRecordEstremi(recordEstremiDocumento);
									markForRedraw();
								}
							}; 
							listaAllegatiDocumentoPopup.initContent(recordEstremiDocumento);							
							listaAllegatiDocumentoPopup.show();	
							
						}else {
							fileDocumento = null;
							recordEstremiDocumento.setAttribute("nroAllegato", "");
							editRecordEstremi(recordEstremiDocumento);
							markForRedraw();
						}
					}
				}
			});
		} else {
			markForRedraw();
		}
	}	
	
	public void editRecordModificaContenuto(Record record) {		
	    this.mode="edit";
	    recordDettaglioSalvato = new Record(record.toMap());
		if(record.getAttribute("tipoContenuto").equalsIgnoreCase(TIPO_CONTENUTO_FILE_SEMPLICE)) {
			record.setAttribute("statoDocumento", "presente");
		}
		if(record.getAttribute("tipoContenuto").equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO) &&
				record.getAttributeAsBoolean("flgTestiHtmlUguali")) {
			descrizioneDettaglioForm.setVisible(true);
		}	
		if(record.getAttribute("tipoContenuto").equalsIgnoreCase(TIPO_CONTENUTO_FILE_SEMPLICE) || record.getAttribute("tipoContenuto").equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO)) {
			if(detailSectionTitolo != null) {
				detailSectionTitolo.setRequired(true);				
			}			
			if(descrizioneItem != null) {
				descrizioneItem.setRequired(false);
			}
		}
		if(record.getAttribute("statoRichPubbl") != null && !"".equals(record.getAttribute("statoRichPubbl"))) {
			if(detailSectionStato != null) {
				detailSectionStato.show();
			}
		} else {
			if(detailSectionStato != null) {
				detailSectionStato.hide();
			}
		}
		// se controllo qui il mode non va bene perchè sarà sempre edit, anche quando apro in modalità view, perche il setViewMode() viene chiamato dopo
		// quindi dovrò gestire il fatto di nascondere o mostrare la sezione con flgAbilAuotorizzRich = 1 nel setViewMode() e nel setEditMode()
		/*
		if(mode != null && "edit".equals(mode) && record.getAttribute("flgAbilAuotorizzRich") != null && "1".equals(record.getAttribute("flgAbilAuotorizzRich"))) {
			if(detailSectionAuotorizzRich != null) {
				detailSectionAuotorizzRich.show();
			}
		} else {
			if(detailSectionAuotorizzRich != null) {
				detailSectionAuotorizzRich.hide();
			}
		}
		*/	
		if(detailSectionAuotorizzRich != null) {
			detailSectionAuotorizzRich.hide();
		}
		vm.editRecord(record);
		markForRedraw();
	}
	
	public void editRecordNuovoContenuto(String tipoContenuto, String idSezione, Integer nroOrdineInSez) {		
		editRecordNuovoContenuto(tipoContenuto, idSezione, nroOrdineInSez, null);
	}
	
	public void editRecordNuovoContenuto(String tipoContenuto, String idSezione, Integer nroOrdineInSez, String idUd) {		
	    this.mode="new";
		Record record = new Record();
		record.setAttribute("tipoContenuto", tipoContenuto);
		record.setAttribute("idSezione", idSezione);
		record.setAttribute("nroOrdineInSez", nroOrdineInSez);
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_FILE_SEMPLICE)) {
			statoDocumentoItem.setValue("presente");
		}
		if(tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_FILE_SEMPLICE) || tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO)) {
			if(detailSectionTitolo != null) {
				detailSectionTitolo.setRequired(true);	
			}				
			if(descrizioneItem != null) {
				descrizioneItem.setRequired(false);
			}
		}		
		if(detailSectionStato != null) {
			detailSectionStato.hide();
		}
		if(detailSectionAuotorizzRich != null) {
			detailSectionAuotorizzRich.hide();
		}
		vm.editNewRecord(record);		
		if (tipoContenuto.equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO) && idUd != null && !"".equals(idUd)) {
			if(detailSectionDocumento != null) {
				detailSectionDocumento.hide();
			}
			loadDettaglio(idUd, new ServiceCallback<Record>() {
				@Override
				public void execute(Record recordEstremiDocumento) {
					if (recordEstremiDocumento != null) {
						editRecordEstremi(recordEstremiDocumento);						
						markForRedraw();
					}
				}
			});
		} else {			
			markForRedraw();
		}
	}

	public void setViewMode() {
	    this.mode="view";
	    this.modify = false;	
	    visualizzaDatiTabellaForm.redraw();
	    setCanEdit(false, titoloForm);
	    setCanEdit(false, statoForm);
	    setCanEdit(false, auotorizzRichForm); 
	    setCanEdit(false, descrizioneForm);		
		setCanEdit(false, descrizioneDettaglioForm);		
		setCanEdit(false, periodoPubblicazioneForm);
		setCanEdit(false, opzioniAggiuntiveForm);		
		infoStrutturaTabellaItem.setCanEdit(false);
		setViewModeFormDocumento();
		confermaButton.hide();
		annullaButton.hide();
		if(flgToAbil != null && flgToAbil && isAbilToModify) {
			modificaButton.show();
		} else {
			modificaButton.hide();
		}
		chiudiButton.show();
		if(detailSectionAuotorizzRich != null) {
			detailSectionAuotorizzRich.hide();
		}
	}

	private void setViewModeFormDocumento() {
		statoDocumentoItem.setCanEdit(false);
		nomeFileItem.setCanEdit(false);
		uploadFileItem.setCanEdit(false);
		creaDocumentoButtonItem.setCanEdit(false);
		tipoRegNumItem.setCanEdit(false);
		siglaRegNumItem.setCanEdit(false);
		annoRegNumItem.setCanEdit(false);
		nroRegNumItem.setCanEdit(false);
		showDettUdPresenteItem.setCanEdit(true);
		showDettUdDaCaricareItem.setCanEdit(true);
		showPreviewItem.setCanEdit(true);
	}
	
	public void setEditMode() {
	    this.mode="edit";
	    visualizzaDatiTabellaForm.redraw();
	    setCanEdit(true, titoloForm);
	    setCanEdit(false, statoForm);
	    setCanEdit(true, auotorizzRichForm);
	    setCanEdit(true, descrizioneForm);		
		setCanEdit(true, descrizioneDettaglioForm);		
		setCanEdit(true, periodoPubblicazioneForm);
		setCanEdit(true, opzioniAggiuntiveForm);	    
		infoStrutturaTabellaItem.setCanEdit(true);
		setEditModeFormDocumento();
		confermaButton.show();
		if (recordDettaglioSalvato != null) {
			annullaButton.show();
		}
		modificaButton.hide();
		chiudiButton.hide();
		if(detailSectionAuotorizzRich != null) {
			if(flgAbilAuotorizzRichItem.getValue() != null && "1".equals(flgAbilAuotorizzRichItem.getValue())) {
				detailSectionAuotorizzRich.show();
			} else {
				detailSectionAuotorizzRich.hide();
			}
		}
	}

	private void setEditModeFormDocumento() {
		statoDocumentoItem.setCanEdit(true);
		nomeFileItem.setCanEdit(true);
		uploadFileItem.setCanEdit(true);
		creaDocumentoButtonItem.setCanEdit(true);
		tipoRegNumItem.setCanEdit(true);
		siglaRegNumItem.setCanEdit(true);
		annoRegNumItem.setCanEdit(true);
		nroRegNumItem.setCanEdit(true);
		showDettUdPresenteItem.setCanEdit(true);
		showDettUdDaCaricareItem.setCanEdit(true);
		showPreviewItem.setCanEdit(true);
	}
	
	public void setCanEdit(boolean canEdit) {
		for (DynamicForm form : vm.getMembers()) {
			setCanEdit(canEdit, form);
		}
	}

	public void setCanEdit(boolean canEdit, DynamicForm form) {
		if (form != null) {
			form.setEditing(canEdit);
			for (FormItem item : form.getFields()) {
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
//					if (item instanceof DateItem || item instanceof DateTimeItem) {
//						TextItem textItem = new TextItem();
//						textItem.setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.readonlyItem);
//						if (item instanceof DateItem) {
//							((DateItem) item).setTextFieldProperties(textItem);
//						} else if (item instanceof DateTimeItem) {
//							((DateTimeItem) item).setTextFieldProperties(textItem);
//						}
//					} else {
//						item.setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.readonlyItem);
//					}
					item.setCanEdit(canEdit);
					item.redraw();
				}
				if (item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
			}
		}
	}
	
	public void clickPreviewContenutiTabella() {
				
		if (mode != null && mode.equalsIgnoreCase("view")){
			new SceltaContenutiDaVisualizzarePopup() {
				
				@Override
				public void onClickOkButton(Record object) {
					
					String flgRecordFuoriPubbl = null;
					String statoContenuti = object.getAttribute("statoContenuti");
					if (statoContenuti!=null && statoContenuti.equalsIgnoreCase("anche_fuori_corso")){
						flgRecordFuoriPubbl = "1";
					}
					final Record lRecord = new Record(vm.getValues());
					lRecord.setAttribute("flgRecordFuoriPubbl", flgRecordFuoriPubbl);
					
					loadContenutiDaVisualizzare(lRecord, new ServiceCallback<Record>() {
						@Override
						public void execute(final Record recordContenutiDaVisualizzare) {
							if (recordContenutiDaVisualizzare != null) {
								
								// Leggo il valore  FlgAbilAuotorizzRichOut
								getFlgAbilAutorizzRich(lRecord, new ServiceCallback<Record>() {
									@Override
									public void execute(Record recordFlgAbilAutorizzRich) {
										if (recordFlgAbilAutorizzRich != null) {
											String flgAbilAutorizzRich = recordFlgAbilAutorizzRich.getAttribute("flgAbilAuotorizzRich");
											// se FlgAbilAuotorizzRichOut=1 => mode = "edit".
											// se FlgAbilAuotorizzRichOut=0 => mode = "view" 
											String mode = "";
											if (flgAbilAutorizzRich!=null && flgAbilAutorizzRich.equalsIgnoreCase("1"))
												mode = "edit";
											else
												mode = "view";
											    
											previewContenutiDaVisualizzare(recordContenutiDaVisualizzare, lRecord, mode);	
										}
									}
								});
							}
						}
					});
				}
			};
		} 
		else{
			SC.warn("Per accedere ai contenuti devi prima salvare o annullare le modifiche premendo uno dei due tasti in basso.");
		}
	}
	
	private void loadContenutiDaVisualizzare(final Record lRecord, final ServiceCallback<Record> callback) {
		
		final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lGWTRestDataSource.executecustom("leggiDatiContTabella", lRecord, new DSCallback() {						
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (response.getData() != null) {
						Record infoDatiContTabella = response.getData()[0];
						if (infoDatiContTabella != null) {
							if(callback != null) {
								Record result = response.getData()[0];
								if (result != null) {
									result.setAttribute("flgRecordFuoriPubbl", lRecord.getAttribute("flgRecordFuoriPubbl"));
								}
								callback.execute(result);
							}	
						}
					} 
				}
			}
		});
	}
	
	private void previewContenutiDaVisualizzare(Record recordContenutiDaVisualizzare, Record listRecord, String mode){
		// da fare ....
		//Record vmValues = new Record(vm.getValues());
		//String idContenuto = vmValues.getAttribute("idContenuto");
		
		String idContenuto = listRecord.getAttribute("idContenuto");
		
		String flgRecordFuoriPubbl = recordContenutiDaVisualizzare.getAttribute("flgRecordFuoriPubbl");
		
		Record attr = new Record();
		attr.setAttribute("nome", "tabellaAmmTrasparente");
		attr.setAttribute("flgRecordFuoriPubbl", flgRecordFuoriPubbl);
		attr.setAttribute("idContenuto", idContenuto);
		
		RecordList datiDettLista = recordContenutiDaVisualizzare.getAttributeAsRecordList("listaDettColonnaAttributo");
		RecordList valoriAttrLista = recordContenutiDaVisualizzare.getAttributeAsRecordList("valoriAttrLista");
		String nroRecTotali = recordContenutiDaVisualizzare.getAttribute("nroRecTotali");
		
		Record values = new Record();
		values.setAttribute("tabellaAmmTrasparente", valoriAttrLista);
				
		DatiContenutiTabellaPopup popup = new DatiContenutiTabellaPopup(attr, datiDettLista, null, mode, nroRecTotali);
		popup.setValues(values);
		popup.show();
	}
	
	private void onClickOkButton(final Record record){
		
		Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
		
		final GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		
		if(modify){
			/*Sono in editMode*/
			lDataSource.updateData(record, new DSCallback() {
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record result = response.getData()[0];
						documentoForm.setValue("idContenuto", result.getAttribute("idContenuto"));
						record.setAttribute("idContenuto", result.getAttribute("idContenuto"));
						Layout.addMessage(new MessageBean("Sezione modificata con successo", "", MessageType.INFO));
						if(layout != null) {
							((ContenutiAmmTraspLayout) layout).reloadList();
						}
						reloadDetail();
					} else {
						Layout.hideWaitPopup();
					}
				}
			});
		} else {
			/*Sono in viewMode, alla pressione del tasto OK non devo fare nulla*/
			if(recordDettaglio!=null) {
				markForDestroy();
				return;
			}
			/*Sono in newMode*/
			lDataSource.addData(record, new DSCallback() {
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record result = response.getData()[0];
						documentoForm.setValue("idContenuto", result.getAttribute("idContenuto"));
						record.setAttribute("idContenuto", result.getAttribute("idContenuto"));
						Layout.addMessage(new MessageBean("Sezione aggiunta con successo", "", MessageType.INFO));
						if(layout != null) {
							((ContenutiAmmTraspLayout) layout).reloadList();
						}
						reloadDetail();
						afterInserimento();
					} else {
						Layout.hideWaitPopup();
					}
				}
			});	
		}
	}
	
	private void reloadDetail() {
		Record record = new Record();
		String idContenuto = documentoForm.getValueAsString("idContenuto");
		record.setAttribute("idContenuto", idContenuto);
		
		final GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lDataSource.getData(record, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record recordDettaglio = response.getData()[0];
					editRecordModificaContenuto(recordDettaglio);
					setViewMode();
				}
				Layout.hideWaitPopup();
			}
		});
	}
	
	protected void afterInserimento() {
		
	}
	
	private void getFlgAbilAutorizzRich(final Record lRecord, final ServiceCallback<Record> callback) {
		
		final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lGWTRestDataSource.executecustom("leggiFlgAbilAutorizzRich", lRecord, new DSCallback() {						
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (response.getData() != null) {
						Record infoDatiContTabella = response.getData()[0];
						if (infoDatiContTabella != null) {
							if(callback != null) {
								Record result = response.getData()[0];
								callback.execute(result);
							}	
						}
					} 
				}
			}
		});
		
	}
	
	
	/**
	 * 
	 */
	public void manageRicercaDocumento() {
		recuperaIdUd(new ServiceCallback<String>() {

			@Override
			public void execute(String idUd) {
				if (idUd != null && !"".equals(idUd)) {
					loadDettaglio(idUd, new ServiceCallback<Record>() {
						@Override
						public void execute(final Record recordEstremiDocumento) {
							if (recordEstremiDocumento != null) {
								RecordList listaAllegati = recordEstremiDocumento.getAttributeAsRecordList("listaAllegati");
								if(listaAllegati!=null && listaAllegati.getLength()>0) {
									fileDocumento = recordEstremiDocumento;
									
									ListaAllegatiDocumentoPopup listaAllegatiDocumentoPopup = new ListaAllegatiDocumentoPopup() {


										@Override
										public void manageRecordSelection(Record record) {
											recordEstremiDocumento.setAttribute("idDoc", record.getAttribute("idDocAllegato"));
											recordEstremiDocumento.setAttribute("nroAllegato", record.getAttribute("nroAllegato")); /*TODO_CAPIRE QUALCE CAMPO PASSARE*/
											editRecordEstremi(recordEstremiDocumento);
											markForRedraw();
										}
									}; 
									listaAllegatiDocumentoPopup.initContent(recordEstremiDocumento);
									listaAllegatiDocumentoPopup.show();	
									
								}else {
									fileDocumento = null;
									recordEstremiDocumento.setAttribute("nroAllegato", "");
									editRecordEstremi(recordEstremiDocumento);
									markForRedraw();
								}
							}
						}
					});
				}else {
					idDocFileHiddenItem.setValue("");
					nroAllegatoHiddenItem.setValue("");
					fileDocumento = null;
				}
			}
		});
	}
	
	
	
	
}