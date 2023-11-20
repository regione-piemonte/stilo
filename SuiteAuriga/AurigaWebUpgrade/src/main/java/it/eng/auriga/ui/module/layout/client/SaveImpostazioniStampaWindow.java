/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.PortScannerUtility.PortScannerCallback;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.SezioneContenuti;
import it.eng.utility.Styles;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class SaveImpostazioniStampaWindow extends ModalWindow {
	
	private ValuesManager vm;
	
	private SezioneContenuti sottoSezioneEtichettePer;
	private SezioneContenuti sezioneStampaEtichette;
	private SezioneContenuti sezioneStampaStandard;
	private SezioneContenuti sezioneTimbraturaCartaceo;
	
	private DynamicForm mDynamicFormStampaEtichette;
	private TextItem stampanteEtichetteItem;
	private FormItemIcon selezionaStampanteEtichettaItem;
	private CheckboxItem stampaEtichettaAutoRegItem;
	private CheckboxItem skipSceltaOpzStampaItem;
	private SelectItem notazioneCopiaOriginaleItem;
	private CheckboxItem stampaBarcodeItem;
	
	private DynamicForm mDynamicFormEtichettePer;
	
	private CheckboxItem flgSegnRegPrincipaleItem;
	private CheckboxItem flgSegnRegSecondariaItem;
	private CheckboxItem flgPrimarioItem;
	private CheckboxItem flgAllegatiItem;
	private CheckboxItem flgRicevutaXMittenteItem;
	
	private DynamicForm mDynamicFormStampaStandard;
	private TextItem stampanteStandardItem;
	private FormItemIcon selezionaStampanteStandardItem;
	private CheckboxItem skipSceltaStampanteOpzStampaItem;
	
	private DynamicForm mDynamicFormTimbraturaCartaceo;
	private TextItem portaStampanteTimbraturaCartaceoItem;
	private FormItemIcon selezionaPortaStampanteTimbraturaCartaceoItem;
	
	public SaveImpostazioniStampaWindow() {

		super("config_utente_impostazioniStampa", true);
		
		this.vm = new ValuesManager();

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		setTitle(I18NUtil.getMessages().configUtenteMenuImpostazioniStampa_title());
		setIcon("postaElettronica/print_file.png");

		setWidth(600);
		setHeight(200);
		
		stampaBarcodeItem = new CheckboxItem("stampaBarcode", "");
		stampaBarcodeItem.setTitle(AurigaLayout.showEtichettaPerBarcode() ? "barcode" : "Stampa barcode");
		stampaBarcodeItem.setStartRow(false);
		stampaBarcodeItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicFormStampaEtichette.markForRedraw();
			}
		});
		
		buildFormEtichetta();
		
		buildFormEtichettePer();
		
		buildFormStampaOrdinaria();
		
		buildFormTimbraturaCartaceo();
		
		sottoSezioneEtichettePer = new SezioneContenuti("Etichette per :", false, true, mDynamicFormEtichettePer) {
			
			@Override
			public String getHeaderTitleStyle() {
				return it.eng.utility.Styles.formTitle;
			}
		};

		sezioneStampaEtichette = new SezioneContenuti("Stampa etichette", true, true, mDynamicFormStampaEtichette, sottoSezioneEtichettePer);
		
		sezioneStampaStandard = new SezioneContenuti("Stampa ordinaria", true, true, mDynamicFormStampaStandard);
		
		sezioneTimbraturaCartaceo = new SezioneContenuti("Timbratura cartaceo", true, true, mDynamicFormTimbraturaCartaceo);
		
		Button okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				mDynamicFormStampaEtichette.clearErrors(true);
				mDynamicFormEtichettePer.clearErrors(true);
				mDynamicFormStampaStandard.clearErrors(true);
				mDynamicFormTimbraturaCartaceo.clearErrors(true);
				if(vm.validate()) {
					manageOnOkButtonClick(new Record(vm.getValues()));
					markForDestroy();
				}
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(okButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();

		layout.addMember(sezioneStampaEtichette);
		layout.addMember(sezioneStampaStandard);
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_CARTACEO")) {
			layout.addMember(sezioneTimbraturaCartaceo);
		}
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				markForDestroy();
			}
		});

		addItem(portletLayout);

	}

	public void clearValues() {
		mDynamicFormStampaEtichette.clearValues();
		mDynamicFormStampaStandard.clearValues();
		mDynamicFormEtichettePer.clearValues();
		mDynamicFormTimbraturaCartaceo.clearValues();
	}

	public void setValues(Record values) {
		if (values != null) {
			mDynamicFormStampaEtichette.editRecord(values);
			mDynamicFormStampaStandard.editRecord(values);
			mDynamicFormEtichettePer.editRecord(values);
			mDynamicFormTimbraturaCartaceo.editRecord(values);
		} else {
			mDynamicFormStampaEtichette.editNewRecord();
			mDynamicFormStampaStandard.editNewRecord();
			Map<String, Object> initialValuesEtichettePer = new HashMap<String, Object>();
			initialValuesEtichettePer.put("flgPrimario", true);
			initialValuesEtichettePer.put("flgAllegati", true);
			initialValuesEtichettePer.put("flgRicevutaXMittente", true);
			mDynamicFormEtichettePer.editNewRecord(initialValuesEtichettePer);
			mDynamicFormTimbraturaCartaceo.editNewRecord();
		}
		mDynamicFormStampaEtichette.clearErrors(true);
		mDynamicFormStampaStandard.clearErrors(true);
		mDynamicFormEtichettePer.clearErrors(true);
		mDynamicFormTimbraturaCartaceo.clearErrors(true);
	}

	public void manageOnOkButtonClick(Record values) {

	}
	
	private void buildFormEtichetta() {
		
		mDynamicFormStampaEtichette = new DynamicForm();
		mDynamicFormStampaEtichette.setKeepInParentRect(true);
		mDynamicFormStampaEtichette.setWrapItemTitles(false);
		mDynamicFormStampaEtichette.setWidth100();
		mDynamicFormStampaEtichette.setHeight100();
		mDynamicFormStampaEtichette.setNumCols(5);
		mDynamicFormStampaEtichette.setColWidths(10, 10, 10, 10, "*");
		mDynamicFormStampaEtichette.setPadding(7);
		mDynamicFormStampaEtichette.setAlign(Alignment.LEFT);
		mDynamicFormStampaEtichette.setTop(50);
		mDynamicFormStampaEtichette.setValuesManager(vm);
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setColSpan(1);
		spacerItem.setStartRow(true);
		
		selezionaStampanteEtichettaItem = new FormItemIcon();
		selezionaStampanteEtichettaItem.setSrc("buttons/lookup.png");
		selezionaStampanteEtichettaItem.setHeight(16);
		selezionaStampanteEtichettaItem.setWidth(16);
		selezionaStampanteEtichettaItem.setPrompt("Seleziona stampante");
		selezionaStampanteEtichettaItem.setNeverDisable(true);
		selezionaStampanteEtichettaItem.setCursor(Cursor.POINTER);
		selezionaStampanteEtichettaItem.setAttribute("cellStyle", Styles.formCellClickable);
		selezionaStampanteEtichettaItem.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				PrinterScannerUtility.printerScanner(mDynamicFormStampaEtichette.getValueAsString("stampanteEtichette"), new PrinterScannerCallback() {

					@Override
					public void execute(String nomeStampante) {
						mDynamicFormStampaEtichette.setValue("stampanteEtichette", nomeStampante);
					}
				}, null);
			}
		});
		
		selezionaStampanteEtichettaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !AurigaLayout.isBottoniStampaEtichetteHybridDisattivi();
			}
		});
		
		stampanteEtichetteItem = new TextItem("stampanteEtichette", setTitleAlign("Stampante etichette", 150, false));
		stampanteEtichetteItem.setStartRow(true);
		stampanteEtichetteItem.setIcons(selezionaStampanteEtichettaItem);
		stampanteEtichetteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !AurigaLayout.isBottoniStampaEtichetteHybridDisattivi();
			}
		});
		
		stampaEtichettaAutoRegItem = new CheckboxItem("stampaEtichettaAutoReg", I18NUtil.getMessages().configUtenteMenuStampaEtichettaAutoReg_title());
		stampaEtichettaAutoRegItem.setStartRow(false);
		stampaEtichettaAutoRegItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicFormStampaEtichette.markForRedraw();
			}
		});
		
		skipSceltaOpzStampaItem = new CheckboxItem("skipSceltaOpzStampa", "Salta scelta opzioni stampa");
		skipSceltaOpzStampaItem.setStartRow(false);
		skipSceltaOpzStampaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicFormStampaEtichette.markForRedraw();
			}
		});
		
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ETICHETTA_ORIG_COPIA")){

			notazioneCopiaOriginaleItem = new SelectItem("notazioneCopiaOriginale", "Apponi dicitura");
			LinkedHashMap<String, String> notazioneCopiaOriginaleValueMap = new LinkedHashMap<String, String>();
			notazioneCopiaOriginaleValueMap.put("ORIGINALE", "Originale");
			notazioneCopiaOriginaleValueMap.put("COPIA", "Copia");
			notazioneCopiaOriginaleItem.setValueMap(notazioneCopiaOriginaleValueMap);
			notazioneCopiaOriginaleItem.setStartRow(true);
			notazioneCopiaOriginaleItem.setAllowEmptyValue(true);		
			notazioneCopiaOriginaleItem.setWidth(250);
			
			if(AurigaLayout.showEtichettaPerBarcode()){
				mDynamicFormStampaEtichette.setItems(
						stampanteEtichetteItem,
						notazioneCopiaOriginaleItem,
						spacerItem,	stampaEtichettaAutoRegItem,
						spacerItem, skipSceltaOpzStampaItem
				);	
			}else{
				mDynamicFormStampaEtichette.setItems(
						stampanteEtichetteItem,
						notazioneCopiaOriginaleItem,
						spacerItem,	stampaEtichettaAutoRegItem,
						spacerItem, skipSceltaOpzStampaItem,
						spacerItem, stampaBarcodeItem
				);	
			}	
		} else {			
			if(AurigaLayout.showEtichettaPerBarcode()){
				mDynamicFormStampaEtichette.setItems(
						stampanteEtichetteItem,
						spacerItem, stampaEtichettaAutoRegItem,
						spacerItem, skipSceltaOpzStampaItem
				);
			}else{
				mDynamicFormStampaEtichette.setItems(
						stampanteEtichetteItem,
						spacerItem, stampaEtichettaAutoRegItem,
						spacerItem, skipSceltaOpzStampaItem,
						spacerItem, stampaBarcodeItem
				);
			}
		}
	}
	
	private void buildFormEtichettePer() {
		
		mDynamicFormEtichettePer = new DynamicForm();
		mDynamicFormEtichettePer.setKeepInParentRect(true);
		mDynamicFormEtichettePer.setWrapItemTitles(false);
		mDynamicFormEtichettePer.setWidth100();
		mDynamicFormEtichettePer.setHeight100();
		mDynamicFormEtichettePer.setNumCols(3);
		mDynamicFormEtichettePer.setColWidths(10, 10, 10, 10, "*");
		mDynamicFormEtichettePer.setPadding(7);
		mDynamicFormEtichettePer.setAlign(Alignment.LEFT);
		mDynamicFormEtichettePer.setTop(50);
		mDynamicFormEtichettePer.setValuesManager(vm);
		
		CustomValidator etichettePerValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				Record recordEtichettePer = new Record(mDynamicFormEtichettePer.getValues());
				boolean flgPrimario = recordEtichettePer.getAttributeAsBoolean("flgPrimario") != null ?
						recordEtichettePer.getAttributeAsBoolean("flgPrimario") : false;
				boolean flgAllegati = recordEtichettePer.getAttributeAsBoolean("flgAllegati") != null ?
						recordEtichettePer.getAttributeAsBoolean("flgAllegati") : false;
				boolean flgRicevutaXMittente = recordEtichettePer.getAttributeAsBoolean("flgRicevutaXMittente") != null ?
						recordEtichettePer.getAttributeAsBoolean("flgRicevutaXMittente") : false;				
				boolean flgSegnRegPrincipale = recordEtichettePer.getAttributeAsBoolean("flgSegnRegPrincipale") != null ?
						recordEtichettePer.getAttributeAsBoolean("flgSegnRegPrincipale") : false;
				boolean flgSegnRegSecondaria = recordEtichettePer.getAttributeAsBoolean("flgSegnRegSecondaria") != null ? 
						recordEtichettePer.getAttributeAsBoolean("flgSegnRegSecondaria") : false;			
				
				if(AurigaLayout.showEtichettaPerBarcode()){
					Record recordStampaEtichette = new Record(mDynamicFormStampaEtichette.getValues());
					boolean stampaBarcode = recordStampaEtichette.getAttributeAsBoolean("stampaBarcode") != null ? recordStampaEtichette.getAttributeAsBoolean("stampaBarcode") : false;
					
					return ( (flgSegnRegPrincipale || flgSegnRegSecondaria || stampaBarcode) && 
							( flgPrimario || flgAllegati || flgRicevutaXMittente) );
				} else {
					return ( (flgSegnRegPrincipale || flgSegnRegSecondaria) && 
							( flgPrimario || flgAllegati || flgRicevutaXMittente) );
				}
			}
		};
		etichettePerValidator.setErrorMessage(AurigaLayout.showEtichettaPerBarcode() ? "Obbligatorio selezionare almeno una opzione da entrambe le colonne" : 
			"Obbligatorio selezionare almeno una opzione");
		
		flgSegnRegPrincipaleItem = new CheckboxItem("flgSegnRegPrincipale", "segnatura reg.principale (Prot.Gen. se presente)");
		flgSegnRegPrincipaleItem.setStartRow(true);
		flgSegnRegPrincipaleItem.setValidators(etichettePerValidator);
		
		flgSegnRegSecondariaItem = new CheckboxItem("flgSegnRegSecondaria", "segnatura reg.secondaria (repertorio)");
		flgSegnRegSecondariaItem.setStartRow(true);
		flgSegnRegSecondariaItem.setValidators(etichettePerValidator);
		
		flgPrimarioItem = new CheckboxItem("flgPrimario", "documento principale");
		flgPrimarioItem.setStartRow(false);
		flgPrimarioItem.setValidators(etichettePerValidator);
				
		flgAllegatiItem = new CheckboxItem("flgAllegati", "allegati");
		flgAllegatiItem.setStartRow(false);
		flgAllegatiItem.setValidators(etichettePerValidator);
		
		flgRicevutaXMittenteItem = new CheckboxItem("flgRicevutaXMittente", "ricevuta per esibente");
		flgRicevutaXMittenteItem.setStartRow(false);
		flgRicevutaXMittenteItem.setValidators(etichettePerValidator);
		
		SpacerItem spacerBarcodeHide = new SpacerItem();
		spacerBarcodeHide.setColSpan(1);
		
		if(AurigaLayout.showEtichettaPerBarcode()){
			stampaBarcodeItem.setStartRow(true);
			stampaBarcodeItem.setValidators(etichettePerValidator);
			mDynamicFormEtichettePer.setItems(
					flgSegnRegPrincipaleItem,
					flgPrimarioItem,
					flgSegnRegSecondariaItem,
					flgAllegatiItem,
					stampaBarcodeItem,
					flgRicevutaXMittenteItem
			);
		} else {
			spacerBarcodeHide.setStartRow(true);
			mDynamicFormEtichettePer.setItems(
					flgSegnRegPrincipaleItem,
					flgPrimarioItem,
					flgSegnRegSecondariaItem,
					flgAllegatiItem,
					spacerBarcodeHide,
					flgRicevutaXMittenteItem
			);
		}
	}
	
	private void buildFormStampaOrdinaria() {
		 
		mDynamicFormStampaStandard = new DynamicForm();
		mDynamicFormStampaStandard.setKeepInParentRect(true);
		mDynamicFormStampaStandard.setWrapItemTitles(false);
		mDynamicFormStampaStandard.setWidth100();
		mDynamicFormStampaStandard.setHeight100();
		mDynamicFormStampaStandard.setNumCols(5);
		mDynamicFormStampaStandard.setColWidths(10, 10, 10, 10, "*");
		mDynamicFormStampaStandard.setPadding(7);
		mDynamicFormStampaStandard.setAlign(Alignment.LEFT);
		mDynamicFormStampaStandard.setTop(50);
		mDynamicFormStampaStandard.setValuesManager(vm);
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setColSpan(1);
		spacerItem.setStartRow(true);
		
		selezionaStampanteStandardItem = new FormItemIcon();
		selezionaStampanteStandardItem.setSrc("buttons/lookup.png");
		selezionaStampanteStandardItem.setHeight(16);
		selezionaStampanteStandardItem.setWidth(16);
		selezionaStampanteStandardItem.setPrompt("Seleziona stampante");
		selezionaStampanteStandardItem.setNeverDisable(true);
		selezionaStampanteStandardItem.setCursor(Cursor.POINTER);
		selezionaStampanteStandardItem.setAttribute("cellStyle", Styles.formCellClickable);
		selezionaStampanteStandardItem.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				PrinterScannerUtility.printerScanner(mDynamicFormStampaStandard.getValueAsString("stampanteStandard"), new PrinterScannerCallback() {

					@Override
					public void execute(String nomeStampante) {
						mDynamicFormStampaStandard.setValue("stampanteStandard", nomeStampante);
					}
				}, null);
			}
		});
		
		selezionaStampanteStandardItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !AurigaLayout.isDisattivaJnlp();
			}
		});
		
		stampanteStandardItem = new TextItem("stampanteStandard", setTitleAlign("Stampante ordinaria", 150, false));
		stampanteStandardItem.setStartRow(true);
		stampanteStandardItem.setIcons(selezionaStampanteStandardItem);
		stampanteStandardItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !AurigaLayout.isDisattivaJnlp();
			}
		});
		
		skipSceltaStampanteOpzStampaItem = new CheckboxItem("skipSceltaStampanteOpzStampa", "Salta scelta stampante e opzioni stampa prima di stampare");
		skipSceltaStampanteOpzStampaItem.setStartRow(false);
		
		mDynamicFormStampaStandard.setItems(stampanteStandardItem, spacerItem, skipSceltaStampanteOpzStampaItem);
	}
	
	private void buildFormTimbraturaCartaceo() {
		 
		mDynamicFormTimbraturaCartaceo = new DynamicForm();
		mDynamicFormTimbraturaCartaceo.setKeepInParentRect(true);
		mDynamicFormTimbraturaCartaceo.setWrapItemTitles(false);
		mDynamicFormTimbraturaCartaceo.setWidth100();
		mDynamicFormTimbraturaCartaceo.setHeight100();
		mDynamicFormTimbraturaCartaceo.setNumCols(5);
		mDynamicFormTimbraturaCartaceo.setColWidths(10, 10, 10, 10, "*");
		mDynamicFormTimbraturaCartaceo.setPadding(7);
		mDynamicFormTimbraturaCartaceo.setAlign(Alignment.LEFT);
		mDynamicFormTimbraturaCartaceo.setTop(50);
		mDynamicFormTimbraturaCartaceo.setValuesManager(vm);
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setColSpan(1);
		spacerItem.setStartRow(true);
		
		selezionaPortaStampanteTimbraturaCartaceoItem = new FormItemIcon();
		selezionaPortaStampanteTimbraturaCartaceoItem.setSrc("buttons/lookup.png");
		selezionaPortaStampanteTimbraturaCartaceoItem.setHeight(16);
		selezionaPortaStampanteTimbraturaCartaceoItem.setWidth(16);
		selezionaPortaStampanteTimbraturaCartaceoItem.setPrompt("Seleziona porta");
		selezionaPortaStampanteTimbraturaCartaceoItem.setNeverDisable(true);
		selezionaPortaStampanteTimbraturaCartaceoItem.setCursor(Cursor.POINTER);
		selezionaPortaStampanteTimbraturaCartaceoItem.setAttribute("cellStyle", Styles.formCellClickable);
		selezionaPortaStampanteTimbraturaCartaceoItem.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				PortScannerUtility.portScanner(mDynamicFormStampaStandard.getValueAsString("portaStampanteTimbraturaCartaceo"), new PortScannerCallback() {

					@Override
					public void execute(String nomePorta) {
						mDynamicFormTimbraturaCartaceo.setValue("portaStampanteTimbraturaCartaceo", nomePorta);
					}
				}, null);
			}
		});
		
		selezionaPortaStampanteTimbraturaCartaceoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !AurigaLayout.isDisattivaJnlp();
			}
		});
		
		portaStampanteTimbraturaCartaceoItem = new TextItem("portaStampanteTimbraturaCartaceo", setTitleAlign("Porta timbratrice", 150, false));
		portaStampanteTimbraturaCartaceoItem.setStartRow(true);
		portaStampanteTimbraturaCartaceoItem.setIcons(selezionaPortaStampanteTimbraturaCartaceoItem);
		portaStampanteTimbraturaCartaceoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !AurigaLayout.isDisattivaJnlp();
			}
		});
		
		mDynamicFormTimbraturaCartaceo.setItems(portaStampanteTimbraturaCartaceoItem);
	}
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
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

}