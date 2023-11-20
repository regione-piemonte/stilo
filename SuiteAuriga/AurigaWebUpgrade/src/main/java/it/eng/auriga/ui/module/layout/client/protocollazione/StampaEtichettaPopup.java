/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.JSONDateFormat;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility.StampaEtichettaCallback;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.SezioneContenuti;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class StampaEtichettaPopup extends ModalWindow {

	private StampaEtichettaPopup _window;

	private ValuesManager vm;
	private VLayout layout;

	private DynamicForm mDynamicForm;
	private HiddenItem nomeStampante;
	private NumericItem nroEtichette;
	private SelectItem notazioneCopiaOriginale;
	private CheckboxItem stampaBarcodeItem; 
	private NumericItem nroAllegati;	
	private NumericItem nrAllegato;
	private CheckboxItem flgPrimarioItem;
	private CheckboxItem flgAllegatiItem;
	private CheckboxItem flgRicevutaXMittenteItem;
	private CheckboxItem flgSegnRegPrincipaleItem;
	private CheckboxItem flgSegnRegSecondariaItem;
		
	private JSONEncoder encoder;

	public StampaEtichettaPopup(final Record detailRecord) {

		super(getNomeEntita(detailRecord), true);		

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		_window = this;

		vm = new ValuesManager();

		encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);

		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);

		if(isMultipla(detailRecord)){

			setTitle("Stampa etichetta multipla");

			setWidth(500);
			setHeight(125);

			buildLayoutMultiplo();
			
			Button stampaMultiEtichettaButton = new Button("Stampa");
			stampaMultiEtichettaButton.setIcon("ok.png");
			stampaMultiEtichettaButton.setIconSize(16);
			stampaMultiEtichettaButton.setAutoFit(false);
			stampaMultiEtichettaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
				
				@Override
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
					onClickStampaMultiEtichetta(detailRecord, isBottoniStampaEtichetteHybridDisattivi());							
				}
			});
			
			_buttons.addMember(stampaMultiEtichettaButton);
		

		} else if(isSingoloAllegato(detailRecord)){

			setTitle("Stampa etichetta singolo allegato");

			setWidth(500);

			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ETICHETTA_ORIG_COPIA")){
				setHeight(155);
			} else {
				setHeight(125);
			}

			buildLayoutSingoloAllegato(detailRecord);
			
			Button stampaEtichettaSingoloAllegatoButton = new Button("Stampa");
			stampaEtichettaSingoloAllegatoButton.setIcon("ok.png");
			stampaEtichettaSingoloAllegatoButton.setIconSize(16);
			stampaEtichettaSingoloAllegatoButton.setAutoFit(false);
			stampaEtichettaSingoloAllegatoButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

				@Override
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

					onClickStampaSingolaEtichetta(detailRecord, isBottoniStampaEtichetteHybridDisattivi());
				}
			});
			_buttons.addMember(stampaEtichettaSingoloAllegatoButton);
				
			if (!isBottoniStampaEtichetteHybridDisattivi()) {
				Button impostazioniButton = new Button("Sel. stampante");
				impostazioniButton.setIcon("protocollazione/settings.png");
				impostazioniButton.setIconSize(16);
				impostazioniButton.setAutoFit(false);
				impostazioniButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						selezionaStampante();
					}
				});

				_buttons.addMember(impostazioniButton);
			}
			
		} else {

			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_CARTACEO")) {
				setTitle("Timbra" + " " + getEstremiRecord(detailRecord));
			} else {		
				setTitle(I18NUtil.getMessages().protocollazione_detail_stampaEtichettaButton_prompt() + " " + getEstremiRecord(detailRecord));
			}

			setWidth(500);
			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ETICHETTA_ORIG_COPIA")){
				setHeight(270);
			} else {
				setHeight(240);
			}

			buildLayoutStandard(detailRecord);

			Button stampaEtichettaButton = new Button("Stampa");
			stampaEtichettaButton.setIcon("ok.png");
			stampaEtichettaButton.setIconSize(16);
			stampaEtichettaButton.setAutoFit(false);
			stampaEtichettaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
				
				@Override
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
					
					onClickStampaEtichetta(detailRecord, isBottoniStampaEtichetteHybridDisattivi());
				}
			});
			_buttons.addMember(stampaEtichettaButton);
				
			if (!isBottoniStampaEtichetteHybridDisattivi()) {
				Button impostazioniButton = new Button("Sel. stampante");
				impostazioniButton.setIcon("protocollazione/settings.png");
				impostazioniButton.setIconSize(16);
				impostazioniButton.setAutoFit(false);
				impostazioniButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
						selezionaStampante();
					}
				});
				_buttons.addMember(impostazioniButton);
			}
			
		}

		Button annullaButton = new Button("Annulla");
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				_window.markForDestroy();
			}
		});

		_buttons.addMember(annullaButton);


		setAlign(Alignment.CENTER);
		setTop(50);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);

		setIcon("blank.png");
	}


	protected static String getNomeEntita(Record detailRecord) {
		if(isMultipla(detailRecord)) {
			return "opzioniStampaEtichettaMultipla";
		} else if(isSingoloAllegato(detailRecord)) {
			return "opzioniStampaEtichettaSingoloAllegato";
		} else {
			return "opzioniStampaEtichetta";
		}
	}

	protected static boolean isMultipla(Record detailRecord) {
		return detailRecord != null && detailRecord.getAttributeAsString("isMultiple") != null && 
				"true".equalsIgnoreCase(detailRecord.getAttributeAsString("isMultiple")) ? true : false;
	}

	protected static boolean isSingoloAllegato(Record detailRecord) { 
		return detailRecord != null && detailRecord.getAttributeAsString("isSingoloAllegato") != null && 
				"true".equalsIgnoreCase(detailRecord.getAttributeAsString("isSingoloAllegato")) ? true : false;
	}	

	private void buildLayoutMultiplo(){

		layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);

		DynamicForm mDynamicForm = new DynamicForm();
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setKeepInParentRect(true);
		mDynamicForm.setWidth100();
		mDynamicForm.setHeight100();
		mDynamicForm.setNumCols(4);
		mDynamicForm.setColWidths(1, 1, "*", "*");
		mDynamicForm.setCellPadding(5);
		mDynamicForm.setWrapItemTitles(false);
		
		nroAllegati = new NumericItem("nroAllegati", I18NUtil.getMessages().protocollazione_stampaEtichetteNrAllegato());
		nroAllegati.setWidth(80);
		nroAllegati.setRequired(true);
		nroAllegati.setStartRow(true);

		mDynamicForm.setItems(nroAllegati);

		layout.addMember(mDynamicForm);
	}

	private void buildLayoutSingoloAllegato(final Record detailRecord) {

		layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);

		final DynamicForm mDynamicForm = new DynamicForm();
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setKeepInParentRect(true);
		mDynamicForm.setWidth100();
		mDynamicForm.setHeight100();
		mDynamicForm.setNumCols(15);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicForm.setCellPadding(5);
		mDynamicForm.setWrapItemTitles(false);

		List<FormItem> listaFormItems = new ArrayList<FormItem>();

		nomeStampante = new HiddenItem("nomeStampante");
		nomeStampante.setValue(AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
		listaFormItems.add(nomeStampante);

		nroEtichette = new NumericItem("nroEtichette", "N.ro copie");
		nroEtichette.setRequired(true);
		nroEtichette.setStartRow(true);
		nroEtichette.setDefaultValue(1);
		listaFormItems.add(nroEtichette);

		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ETICHETTA_ORIG_COPIA")){

			notazioneCopiaOriginale = new SelectItem("notazioneCopiaOriginale", "Apponi dicitura");
			LinkedHashMap<String, String> notazioneCopiaOriginaleValueMap = new LinkedHashMap<String, String>();
			notazioneCopiaOriginaleValueMap.put("ORIGINALE", "Originale");
			notazioneCopiaOriginaleValueMap.put("COPIA", "Copia");
			notazioneCopiaOriginale.setValueMap(notazioneCopiaOriginaleValueMap);
			notazioneCopiaOriginale.setStartRow(true);
			notazioneCopiaOriginale.setAllowEmptyValue(true);	
			notazioneCopiaOriginale.setWidth(250);
			notazioneCopiaOriginale.setDefaultValue(AurigaLayout.getImpostazioneStampa("notazioneCopiaOriginale"));

			listaFormItems.add(notazioneCopiaOriginale);
		}

		/******************************************
		 *  Validator per il campo numero allegato
		 ******************************************/
		CustomValidator nAllegatoCustomValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(value != null){
					if (Integer.parseInt((String)value) == 0 || (detailRecord != null &&
							detailRecord.getAttributeAsRecordList("listaAllegati") != null && 
							detailRecord.getAttributeAsRecordList("listaAllegati").getLength() >= Integer.parseInt((String)value) 
							&& value != null)) {
						return true;
					}
				}
				return false;
			}
		};
		
		nAllegatoCustomValidator.setErrorMessage("Allegato inesistente");
		
		nrAllegato = new NumericItem("nrAllegato", "Allegato N°");
		nrAllegato.setRequired(true);
		nrAllegato.setStartRow(true);	
		nrAllegato.setValidators(nAllegatoCustomValidator);

		listaFormItems.add(nrAllegato);

		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setColSpan(1);
		spacerItem.setStartRow(true);
		listaFormItems.add(spacerItem);

		stampaBarcodeItem = new CheckboxItem("stampaBarcode", "");
		stampaBarcodeItem.setTitle(AurigaLayout.showEtichettaPerBarcode() ? "barcode" : "Stampa barcode");
		stampaBarcodeItem.setStartRow(false);
		if(detailRecord.getAttributeAsBoolean("flgHideBarcode") != null && detailRecord.getAttributeAsBoolean("flgHideBarcode")) {
			stampaBarcodeItem.setDefaultValue(false);
		} else {
			stampaBarcodeItem.setDefaultValue(AurigaLayout.getImpostazioneStampaAsBoolean("stampaBarcode"));
		}
		stampaBarcodeItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		if(!AurigaLayout.showEtichettaPerBarcode()){
			listaFormItems.add(stampaBarcodeItem);
		}

		mDynamicForm.setItems(listaFormItems.toArray(new FormItem[listaFormItems.size()]));
		
		final DynamicForm mDynamicFormEtichettePer = new DynamicForm();
		mDynamicFormEtichettePer.setValuesManager(vm);
		mDynamicFormEtichettePer.setKeepInParentRect(true);
		mDynamicFormEtichettePer.setWidth100();
		mDynamicFormEtichettePer.setHeight100();
		mDynamicFormEtichettePer.setNumCols(15);
		mDynamicFormEtichettePer.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicFormEtichettePer.setCellPadding(5);
		mDynamicFormEtichettePer.setWrapItemTitles(false);

		List<FormItem> listaFormEtichettePerItems = new ArrayList<FormItem>();
		
		Record impostazioniStampa = AurigaLayout.getImpostazioneStampa();
		
		CustomValidator etichettePerValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				Record recordEtichettePer = new Record(mDynamicFormEtichettePer.getValues());
				boolean flgStampaBarcode = recordEtichettePer.getAttributeAsBoolean("stampaBarcode") != null ? recordEtichettePer.getAttributeAsBoolean("stampaBarcode") : false;
				boolean flgSegnRegPrincipale = recordEtichettePer.getAttributeAsBoolean("flgSegnRegPrincipale") != null 
						? recordEtichettePer.getAttributeAsBoolean("flgSegnRegPrincipale") : false;			
				boolean flgSegnRegSecondaria = recordEtichettePer.getAttributeAsBoolean("flgSegnRegSecondaria") != null 
						? recordEtichettePer.getAttributeAsBoolean("flgSegnRegSecondaria") : false;	
						
				if(!AurigaLayout.showEtichettaPerBarcode() && showSegnaturaRegSec(detailRecord)){
					return flgSegnRegPrincipale || flgSegnRegSecondaria;
				} else if(AurigaLayout.showEtichettaPerBarcode() && !showSegnaturaRegSec(detailRecord)) {
					return flgSegnRegPrincipale || flgStampaBarcode;
				} else if(AurigaLayout.showEtichettaPerBarcode() && showSegnaturaRegSec(detailRecord)){
					return flgSegnRegPrincipale || flgSegnRegSecondaria || flgStampaBarcode;
				} else if(!AurigaLayout.showEtichettaPerBarcode() && !showSegnaturaRegSec(detailRecord)){
					return flgSegnRegPrincipale;
				}
				return false;
				
			}
		};
		etichettePerValidator.setErrorMessage("Obbligatorio selezionare almeno una opzione");
		
		flgSegnRegPrincipaleItem = new CheckboxItem("flgSegnRegPrincipale", "segnatura reg.principale (Prot.Gen. se presente)");
		flgSegnRegPrincipaleItem.setStartRow(true);
		flgSegnRegPrincipaleItem.setDefaultValue(impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgSegnRegPrincipale") : false);
		flgSegnRegPrincipaleItem.setValidators(etichettePerValidator);
		
		flgSegnRegSecondariaItem = new CheckboxItem("flgSegnRegSecondaria", "segnatura reg.secondaria (repertorio)");
		flgSegnRegSecondariaItem.setStartRow(true);
		flgSegnRegSecondariaItem.setDefaultValue(impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgSegnRegSecondaria") : false);
		flgSegnRegSecondariaItem.setValidators(etichettePerValidator);
		
		if(AurigaLayout.showEtichettaPerBarcode()){
			stampaBarcodeItem.setStartRow(true);
			stampaBarcodeItem.setValidators(etichettePerValidator);
			if(showSegnaturaRegSec(detailRecord)){
				listaFormEtichettePerItems.add(flgSegnRegPrincipaleItem);
				listaFormEtichettePerItems.add(flgSegnRegSecondariaItem);
				listaFormEtichettePerItems.add(stampaBarcodeItem);
			} else {
				listaFormEtichettePerItems.add(flgSegnRegPrincipaleItem);
				listaFormEtichettePerItems.add(stampaBarcodeItem);
			}
		} else {
			if(showSegnaturaRegSec(detailRecord)){
				listaFormEtichettePerItems.add(flgSegnRegPrincipaleItem);
				listaFormEtichettePerItems.add(flgSegnRegSecondariaItem);
			} else {
				listaFormEtichettePerItems.add(flgSegnRegPrincipaleItem);
			}
		}
		
		mDynamicFormEtichettePer.setItems(listaFormEtichettePerItems.toArray(new FormItem[listaFormEtichettePerItems.size()]));

		SezioneContenuti sottoSezioneEtichettePer = new SezioneContenuti("Etichette per :", true, true, mDynamicFormEtichettePer) {

			@Override
			public String getHeaderTitleStyle() {
				return it.eng.utility.Styles.formTitle;
			}
		};

		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();

		layout.setMembers(mDynamicForm, sottoSezioneEtichettePer, spacerLayout);
	}

	private void buildLayoutStandard(final Record detailRecord) {

		layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);

		mDynamicForm = new DynamicForm();
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setKeepInParentRect(true);
		mDynamicForm.setWidth100();
		mDynamicForm.setHeight100();
		mDynamicForm.setNumCols(15);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicForm.setCellPadding(5);
		mDynamicForm.setWrapItemTitles(false);

		List<FormItem> listaFormItems = new ArrayList<FormItem>();

		nomeStampante = new HiddenItem("nomeStampante");
		nomeStampante.setValue(AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
		listaFormItems.add(nomeStampante);

		nroEtichette = new NumericItem("nroEtichette", "N.ro copie");
		nroEtichette.setStartRow(true);
		nroEtichette.setRequired(true);
		nroEtichette.setDefaultValue(1);
		listaFormItems.add(nroEtichette);

		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ETICHETTA_ORIG_COPIA")){

			notazioneCopiaOriginale = new SelectItem("notazioneCopiaOriginale", "Apponi dicitura");
			LinkedHashMap<String, String> notazioneCopiaOriginaleValueMap = new LinkedHashMap<String, String>();
			notazioneCopiaOriginaleValueMap.put("ORIGINALE", "Originale");
			notazioneCopiaOriginaleValueMap.put("COPIA", "Copia");
			notazioneCopiaOriginale.setValueMap(notazioneCopiaOriginaleValueMap);
			notazioneCopiaOriginale.setStartRow(true);
			notazioneCopiaOriginale.setAllowEmptyValue(true);			
			notazioneCopiaOriginale.setWidth(250);
			notazioneCopiaOriginale.setDefaultValue(AurigaLayout.getImpostazioneStampa("notazioneCopiaOriginale"));

			listaFormItems.add(notazioneCopiaOriginale);
		}

		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setColSpan(1);
		spacerItem.setStartRow(true);
		listaFormItems.add(spacerItem);
		
		stampaBarcodeItem = new CheckboxItem("stampaBarcode", "");
		stampaBarcodeItem.setTitle(AurigaLayout.showEtichettaPerBarcode() ? "barcode" : "Stampa barcode");
		stampaBarcodeItem.setStartRow(false);
		if(detailRecord.getAttributeAsBoolean("flgHideBarcode") != null && detailRecord.getAttributeAsBoolean("flgHideBarcode")) {
			stampaBarcodeItem.setDefaultValue(false);
		} else {
			stampaBarcodeItem.setDefaultValue(AurigaLayout.getImpostazioneStampaAsBoolean("stampaBarcode"));
		}
		stampaBarcodeItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		if(!AurigaLayout.showEtichettaPerBarcode()){
			listaFormItems.add(stampaBarcodeItem);
		}

		mDynamicForm.setItems(listaFormItems.toArray(new FormItem[listaFormItems.size()]));

		final DynamicForm mDynamicFormEtichettePer = new DynamicForm();
		mDynamicFormEtichettePer.setValuesManager(vm);
		mDynamicFormEtichettePer.setKeepInParentRect(true);
		mDynamicFormEtichettePer.setWidth100();
		mDynamicFormEtichettePer.setHeight100();
		mDynamicFormEtichettePer.setNumCols(15);
		mDynamicFormEtichettePer.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicFormEtichettePer.setCellPadding(5);
		mDynamicFormEtichettePer.setWrapItemTitles(false);

		List<FormItem> listaFormEtichettePerItems = new ArrayList<FormItem>();

		CustomValidator primaColonnaValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				Record recordEtichettePer = new Record(mDynamicFormEtichettePer.getValues());
				boolean stampaBarcode = recordEtichettePer.getAttributeAsBoolean("stampaBarcode") != null ? recordEtichettePer.getAttributeAsBoolean("stampaBarcode") : false;
				boolean flgSegnRegPrincipale = recordEtichettePer.getAttributeAsBoolean("flgSegnRegPrincipale") != null 
						? recordEtichettePer.getAttributeAsBoolean("flgSegnRegPrincipale") : false;			
				boolean flgSegnRegSecondaria = recordEtichettePer.getAttributeAsBoolean("flgSegnRegSecondaria") != null 
						? recordEtichettePer.getAttributeAsBoolean("flgSegnRegSecondaria") : false;
				boolean flgRicevutaXMittente = recordEtichettePer.getAttributeAsBoolean("flgRicevutaXMittente") != null ? recordEtichettePer.getAttributeAsBoolean("flgRicevutaXMittente") : false;					
				
				if(AurigaLayout.showEtichettaPerBarcode() && showSegnaturaRegSec(detailRecord)){
					return flgSegnRegPrincipale || flgSegnRegSecondaria || stampaBarcode;
				} else if(!AurigaLayout.showEtichettaPerBarcode() && showSegnaturaRegSec(detailRecord)){
					return flgSegnRegPrincipale || flgSegnRegSecondaria || flgRicevutaXMittente;
				} else if(AurigaLayout.showEtichettaPerBarcode() && !showSegnaturaRegSec(detailRecord)){ 
				    return  flgSegnRegPrincipale || stampaBarcode || flgRicevutaXMittente;
				} else if(!AurigaLayout.showEtichettaPerBarcode() && !showSegnaturaRegSec(detailRecord)){ 
				    return  flgSegnRegPrincipale || flgRicevutaXMittente;
				}
				return false;
			}
		};
		primaColonnaValidator.setErrorMessage("Obbligatorio selezionare almeno una opzione");
		
		CustomValidator secondaColonnaValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				Record recordEtichettePer = new Record(mDynamicFormEtichettePer.getValues());
				boolean flgPrimario = recordEtichettePer.getAttributeAsBoolean("flgPrimario") != null ? recordEtichettePer.getAttributeAsBoolean("flgPrimario") : false;
				boolean flgAllegati = recordEtichettePer.getAttributeAsBoolean("flgAllegati") != null ? recordEtichettePer.getAttributeAsBoolean("flgAllegati") : false;
				boolean flgRicevutaXMittente = recordEtichettePer.getAttributeAsBoolean("flgRicevutaXMittente") != null ? recordEtichettePer.getAttributeAsBoolean("flgRicevutaXMittente") : false;			
				
				if(AurigaLayout.showEtichettaPerBarcode() && showSegnaturaRegSec(detailRecord) && flgRicevutaXMittenteItem != null){
					return flgPrimario || flgAllegati || flgRicevutaXMittente;
				} else {
					return flgPrimario || flgAllegati;
				}
			}
		};
		secondaColonnaValidator.setErrorMessage("Obbligatorio selezionare almeno una opzione");
		
		if(AurigaLayout.showEtichettaPerBarcode()){
			stampaBarcodeItem.setValidators(primaColonnaValidator);
		}

		Record impostazioniStampa = AurigaLayout.getImpostazioneStampa();
		
		flgSegnRegPrincipaleItem = new CheckboxItem("flgSegnRegPrincipale", "segnatura reg.principale (Prot.Gen. se presente)");
		flgSegnRegPrincipaleItem.setStartRow(true);
		flgSegnRegPrincipaleItem.setDefaultValue(impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgSegnRegPrincipale") : false);
		flgSegnRegPrincipaleItem.setValidators(primaColonnaValidator);
		
		flgSegnRegSecondariaItem = new CheckboxItem("flgSegnRegSecondaria", "segnatura reg.secondaria (repertorio)");
		flgSegnRegSecondariaItem.setStartRow(true);
		flgSegnRegSecondariaItem.setDefaultValue(impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgSegnRegSecondaria") : false);
		flgSegnRegSecondariaItem.setValidators(primaColonnaValidator);
		
		flgPrimarioItem = new CheckboxItem("flgPrimario", "documento principale");
		flgPrimarioItem.setStartRow(false);
		flgPrimarioItem.setDefaultValue(impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgPrimario") : false);		
		flgPrimarioItem.setValidators(secondaColonnaValidator);
		
		flgAllegatiItem = new CheckboxItem("flgAllegati", "allegati");
		flgAllegatiItem.setStartRow(false);
		flgAllegatiItem.setDefaultValue(impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgAllegati") : false);		
		flgAllegatiItem.setValidators(secondaColonnaValidator);
		
		Boolean isFromSportello = detailRecord != null && detailRecord.getAttributeAsString("mezzoTrasmissione") != null && 
				"CM".equalsIgnoreCase(detailRecord.getAttributeAsString("mezzoTrasmissione")) ? true : false;
		Boolean isFromPregresso = detailRecord != null && detailRecord.getAttributeAsString("mezzoTrasmissione") != null && 
				"PREGR".equalsIgnoreCase(detailRecord.getAttributeAsString("mezzoTrasmissione")) ? true : false;
		if(showFlgRicevutaXMittente(isFromSportello,isFromPregresso)){
			flgRicevutaXMittenteItem = new CheckboxItem("flgRicevutaXMittente", "ricevuta per esibente");
			flgRicevutaXMittenteItem.setStartRow(true);
			flgRicevutaXMittenteItem.setDefaultValue(impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgRicevutaXMittente") : true);			
		}
		
		SpacerItem spacer = new SpacerItem();
		spacer.setColSpan(1);
		
		if(AurigaLayout.showEtichettaPerBarcode()){
			stampaBarcodeItem.setStartRow(true);
			if(showSegnaturaRegSec(detailRecord)){
				listaFormEtichettePerItems.add(flgSegnRegPrincipaleItem);
				listaFormEtichettePerItems.add(flgPrimarioItem);
				listaFormEtichettePerItems.add(flgSegnRegSecondariaItem);
				listaFormEtichettePerItems.add(flgAllegatiItem);
				listaFormEtichettePerItems.add(stampaBarcodeItem);
				if(flgRicevutaXMittenteItem != null){
					flgRicevutaXMittenteItem.setStartRow(false);
					flgRicevutaXMittenteItem.setValidators(secondaColonnaValidator);
					listaFormEtichettePerItems.add(flgRicevutaXMittenteItem);
				}
			} else {
				listaFormEtichettePerItems.add(flgSegnRegPrincipaleItem);
				listaFormEtichettePerItems.add(flgPrimarioItem);
				listaFormEtichettePerItems.add(stampaBarcodeItem);
				listaFormEtichettePerItems.add(flgAllegatiItem);
				if(flgRicevutaXMittenteItem != null){
					flgRicevutaXMittenteItem.setStartRow(true);
					flgRicevutaXMittenteItem.setValidators(primaColonnaValidator);
					listaFormEtichettePerItems.add(flgRicevutaXMittenteItem);
				}
			}
		} else {
			if(showSegnaturaRegSec(detailRecord)){
				listaFormEtichettePerItems.add(flgSegnRegPrincipaleItem);
				listaFormEtichettePerItems.add(flgPrimarioItem);
				listaFormEtichettePerItems.add(flgSegnRegSecondariaItem);
				listaFormEtichettePerItems.add(flgAllegatiItem);
				if(flgRicevutaXMittenteItem != null){
					flgRicevutaXMittenteItem.setValidators(primaColonnaValidator);
					listaFormEtichettePerItems.add(flgRicevutaXMittenteItem);
				}
			} else {
				listaFormEtichettePerItems.add(flgSegnRegPrincipaleItem);
				listaFormEtichettePerItems.add(flgPrimarioItem);
				if(flgRicevutaXMittenteItem == null){
					spacer.setStartRow(true);
					listaFormEtichettePerItems.add(spacer);
					listaFormEtichettePerItems.add(flgAllegatiItem);
				}else{
					flgRicevutaXMittenteItem.setValidators(primaColonnaValidator);
					listaFormEtichettePerItems.add(flgRicevutaXMittenteItem);
					listaFormEtichettePerItems.add(flgAllegatiItem);
				}
			}
		}

		mDynamicFormEtichettePer.setItems(listaFormEtichettePerItems.toArray(new FormItem[listaFormEtichettePerItems.size()]));

		SezioneContenuti sottoSezioneEtichettePer = new SezioneContenuti("Etichette per :", false, true, mDynamicFormEtichettePer) {

			@Override
			public String getHeaderTitleStyle() {
				return it.eng.utility.Styles.formTitle;
			}
		};

		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();

		layout.setMembers(mDynamicForm, sottoSezioneEtichettePer, spacerLayout);

	}

	public String getEstremiRecord(Record detailRecord) {

		String estremi = "";
		if (detailRecord.getAttributeAsString("tipoProtocollo") != null && !"".equals(detailRecord.getAttributeAsString("tipoProtocollo"))) {
			if ("NI".equals(detailRecord.getAttributeAsString("tipoProtocollo"))) {
				estremi += "bozza ";
			} else if ("PP".equals(detailRecord.getAttributeAsString("tipoProtocollo"))) {
				estremi += "Prot. ";
			} else {
				estremi += detailRecord.getAttributeAsString("tipoProtocollo") + " ";
			}
		}
		if (detailRecord.getAttributeAsString("siglaProtocollo") != null && !"".equals(detailRecord.getAttributeAsString("siglaProtocollo"))) {
			estremi += detailRecord.getAttributeAsString("siglaProtocollo") + " ";
		}
		if (detailRecord.getAttribute("nroProtocollo") != null && !"".equals(detailRecord.getAttribute("nroProtocollo"))) {
			estremi += detailRecord.getAttribute("nroProtocollo") + " ";
		}
		if (detailRecord.getAttributeAsString("subProtocollo") != null && !"".equals(detailRecord.getAttributeAsString("subProtocollo"))) {
			estremi += "sub " + detailRecord.getAttributeAsString("subProtocollo") + " ";
		}
		if (detailRecord.getAttributeAsDate("dataProtocollo") != null) {
			estremi += "del " + DateUtil.format(detailRecord.getAttributeAsDate("dataProtocollo"));
		}
		return estremi;
	}

	protected void stampaEtichetta(Record record, final boolean stampaSuPdf) {

		Layout.showWaitPopup("Stampa etichetta in corso...");
		
		boolean stampaBarcode = record.getAttributeAsBoolean("stampaBarcode") != null && record.getAttributeAsBoolean("stampaBarcode");
		record.setAttribute("flgHideBarcode", !stampaBarcode);

		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PreparaValoriEtichettaDatasource");
		lGwtRestService.call(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				Layout.hideWaitPopup();
				final String nomeStampante = object.getAttribute("nomeStampante");
				final Record[] etichette = object.getAttributeAsRecordArray("etichette");
				final String numCopie = object.getAttribute("nroEtichette");
				if (stampaSuPdf) {
					StampaEtichettaUtility.stampaEtichettaInPdf(etichette, numCopie, new StampaEtichettaCallback() {

						@Override
						public void execute() {
							_window.markForDestroy();

						}
					});
				} else {					
					StampaEtichettaUtility.stampaEtichetta("", "", nomeStampante, "", etichette, numCopie, new StampaEtichettaCallback() {
						
						@Override
						public void execute() {
							_window.markForDestroy();
							
						}
					});
				}
			}

			@Override
			public void manageError() {
				Layout.hideWaitPopup();
				Layout.addMessage(new MessageBean("Impossibile stampare l'etichetta", "", MessageType.ERROR));
			}

		});
	}

	protected void stampaEtichettaSingoloAllegato(Record record, final boolean stampaSuPdf) {

		Layout.showWaitPopup("Stampa etichetta in corso...");

		boolean stampaBarcode = record.getAttributeAsBoolean("stampaBarcode") != null && record.getAttributeAsBoolean("stampaBarcode");
		record.setAttribute("flgHideBarcode", !stampaBarcode);

		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PreparaValoriEtichettaDatasource");
		lGwtRestService.performCustomOperation("getEtichettaSingoloAllegato", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Layout.hideWaitPopup();
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {	
					Record object = response.getData()[0];
					final String nomeStampante = object.getAttribute("nomeStampante");
					final Record[] etichette = object.getAttributeAsRecordArray("etichette");
					final String numCopie = object.getAttribute("nroEtichette");
					if (stampaSuPdf) {
						StampaEtichettaUtility.stampaEtichettaInPdf(etichette, numCopie, new StampaEtichettaCallback() {
							
							@Override
							public void execute() {
								_window.markForDestroy();
								
							}
						});
					} else {
						StampaEtichettaUtility.stampaEtichetta("", "", nomeStampante, "", etichette, numCopie, new StampaEtichettaCallback() {
							
							@Override
							public void execute() {
								_window.markForDestroy();
								
							}
						});
					}
				}else{
					Layout.addMessage(new MessageBean("Impossibile stampare l'etichetta", "", MessageType.ERROR));
				}
			}

		},new DSRequest());
	}

	protected void stampaEtichettaMulti(final Record record, final boolean stampaSuPdf) {

		if(record.getAttribute("tipologia") != null && "T".equals(record.getAttribute("tipologia"))) {		

			Layout.showWaitPopup("Stampa etichetta in corso...");
			Record lRecord = new Record(vm.getValues());
			lRecord.setAttribute("idUd", record.getAttribute("idUd"));
			lRecord.setAttribute("nrAllegato", record.getAttribute("nrAllegato"));	
			lRecord.setAttribute("barcodePraticaPregressa", record.getAttribute("barcodePraticaPregressa"));
			lRecord.setAttribute("idFolder", record.getAttribute("idFolder"));
			lRecord.setAttribute("sezionePratica", record.getAttribute("sezionePratica"));
			lRecord.setAttribute("idDoc", record.getAttribute("idDoc"));		
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PreparaValoriEtichettaDatasource");
			lGwtRestService.addParam("isMultiple", "true");
			lGwtRestService.addParam("provenienza", record.getAttribute("provenienza"));
			lGwtRestService.addParam("nroEtichette", lRecord.getAttributeAsString("nroAllegati"));
			lGwtRestService.performCustomOperation("getEtichettaDatiTipologia",lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					Layout.hideWaitPopup();
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {	
						Record object = response.getData()[0];
						final Record[] etichette = object.getAttributeAsRecordArray("etichette");
						final String numCopie = object.getAttributeAsString("nrAllegato");
						if (stampaSuPdf) {
							StampaEtichettaUtility.stampaEtichettaInPdf(etichette, numCopie, new StampaEtichettaCallback() {
								
								@Override
								public void execute() {
	
								}
							});
						} else {
							StampaEtichettaUtility.stampaEtichetta("", "", record.getAttribute("nomeStampante"), "", etichette, numCopie, new StampaEtichettaCallback() {
								
								@Override
								public void execute() {
	
								}
							});
	
							
						}
					}else{
						Layout.addMessage(new MessageBean("Impossibile stampare l'etichetta", "", MessageType.ERROR));
					}
				}

			},new DSRequest());
		} else {

			Layout.showWaitPopup("Stampa etichetta in corso...");
			Record lRecord = new Record(vm.getValues());
			lRecord.setAttribute("idUd", record.getAttribute("idUd"));
			lRecord.setAttribute("nrAllegato", record.getAttribute("nrAllegato"));
			lRecord.setAttribute("barcodePraticaPregressa", record.getAttribute("barcodePraticaPregressa"));
			lRecord.setAttribute("idFolder", record.getAttribute("idFolder"));
			lRecord.setAttribute("sezionePratica", record.getAttribute("sezionePratica"));
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PreparaValoriEtichettaDatasource");
			lGwtRestService.addParam("isMultiple", "true");
			lGwtRestService.addParam("provenienza", record.getAttribute("provenienza"));
			lGwtRestService.addParam("posizione", record.getAttribute("posizione"));
			lGwtRestService.addParam("nroEtichette", lRecord.getAttributeAsString("nroAllegati"));
			lGwtRestService.performCustomOperation("getEtichettaDatiSegnatura",lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					Layout.hideWaitPopup();
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {	
						Record object = response.getData()[0];
						final Record[] etichette = object.getAttributeAsRecordArray("etichette");
						final String numCopie = object.getAttributeAsString("nrAllegato");
						StampaEtichettaUtility.stampaEtichetta("", "", record.getAttribute("nomeStampante"), "", etichette, numCopie, new StampaEtichettaCallback() {

							@Override
							public void execute() {

							}
						});
					}else{
						Layout.addMessage(new MessageBean("Impossibile stampare l'etichetta", "", MessageType.ERROR));
					}
				}

			},new DSRequest());
		}
	}

	// Avvio la selezione della stampante, passando il nome della stampante corrente
	protected void selezionaStampante() {
		PrinterScannerUtility.printerScanner(vm.getValueAsString("nomeStampante"), new PrinterScannerCallback() {

			@Override
			public void execute(String nomeStampante) {
				vm.setValue("nomeStampante", nomeStampante);
			}
		}, null);
	}
	
	protected boolean showSegnaturaRegSec(Record record){
		return record != null && 
			  (record.getAttributeAsString("numeroNumerazioneSecondaria") != null
			  && !"".equals(record.getAttributeAsString("numeroNumerazioneSecondaria"))) &&
			  (record.getAttributeAsString("tipoNumerazioneSecondaria") != null
			  && !"NI".equals(record.getAttributeAsString("tipoNumerazioneSecondaria")));
	}
	
	protected boolean showFlgRicevutaXMittente(Boolean isFromSportello,Boolean isFromPregresso){
		return isFromSportello || isFromPregresso;
	}
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}
	
	private void onClickStampaMultiEtichetta(final Record detailRecord, boolean stampaSuPdf) {
		if(vm.validate()){

			String nomeStampante = detailRecord.getAttributeAsString("nomeStampante");
			String provenienza = detailRecord != null && detailRecord.getAttributeAsString("provenienza") != null
					&& "A".equals(detailRecord.getAttributeAsString("provenienza")) ? 
					detailRecord.getAttributeAsString("provenienza")  : "";
			String tipologia = detailRecord.getAttributeAsString("tipologia");
			String posizione = detailRecord.getAttributeAsString("posizione");
			
			String barcodePraticaPregressa = detailRecord.getAttribute("barcodePraticaPregressa");
			String idFolder = detailRecord.getAttribute("idFolder");
			String sezionePratica = detailRecord.getAttribute("sezionePratica");
			
			Record record = new Record();
			record.setAttribute("idUd", detailRecord.getAttribute("idUd"));
			record.setAttribute("nrAllegato", detailRecord.getAttributeAsString("nrAllegato"));
			record.setAttribute("nomeStampante", nomeStampante);
			record.setAttribute("provenienza", provenienza);
			record.setAttribute("tipologia", tipologia);
			record.setAttribute("posizione", posizione);
			record.setAttribute("barcodePraticaPregressa", barcodePraticaPregressa);
			record.setAttribute("idFolder", idFolder);
			record.setAttribute("sezionePratica", sezionePratica);
			record.setAttribute("idDoc", detailRecord.getAttribute("idDoc"));
			
			stampaEtichettaMulti(record, stampaSuPdf);
		}
	}
	
	private void onClickStampaSingolaEtichetta(final Record detailRecord, boolean stampaSuPdf) {
		if(vm.validate()){						
			Record record = new Record(vm.getValues());
			record.setAttribute("idUd", detailRecord.getAttribute("idUd"));
			record.setAttribute("listaAllegati", detailRecord.getAttributeAsRecordList("listaAllegati"));						
			stampaEtichettaSingoloAllegato(record, stampaSuPdf);
		}
	}
	
	private void onClickStampaEtichetta (final Record detailRecord, boolean stampaSuPdf) {
		if(vm.validate()){

			Record record = new Record(vm.getValues());
			record.setAttribute("idUd", detailRecord.getAttribute("idUd"));
			record.setAttribute("listaAllegati", detailRecord.getAttributeAsRecordList("listaAllegati"));

			stampaEtichetta(record,stampaSuPdf);						
		}
	}
	
	private boolean isBottoniStampaEtichetteHybridDisattivi () {
		return AurigaLayout.isBottoniStampaEtichetteHybridDisattivi();
	}
	
}