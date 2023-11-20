/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.HStack;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedDateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class ProrogaTerminePubblicazionePopup extends ModalWindow {
	
	private ProrogaTerminePubblicazionePopup window;
	private ValuesManager vm;
	
	// DynamicForm
	private DynamicForm datiProrogaTerminePubblicazioneForm; 
	 
	// DetailSection
	private DetailSection datiProrogaTerminePubblicazioneSection;
		
	// HStack
	private HStack _buttons;
			
	// DateItem
	private DateItem dataInizioPubblicazioneItem;
	private ExtendedDateItem dataAProrogaPubblicazioneItem;
	
	// NumericItem
	private ExtendedNumericItem giorniDurataProrogaPubblicazioneItem;
	
	// TextItem 
	private TextAreaItem motivoProrogaPubblicazioneItem;
	
	// Button
	private Button confermaButton;
	private Button annullaButton;

	
	public ProrogaTerminePubblicazionePopup(String title , boolean canEdit, Record record){
		
		super("richiesta_proroga_termine_pubblicazione_popup", true);
		window = this;
		vm = new ValuesManager();
		setTitle(title);
		setIcon("buttons/proroga_termine_pubblicazione.png");
		setAutoCenter(true);
		setAlign(Alignment.CENTER);
		setTop(50);
		setHeight(210);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);	
		createDatiProrogaTerminePubblicazioneSection();
		createButton();
		addItem(datiProrogaTerminePubblicazioneSection);
		addItem(_buttons);
		editRecord(record);
		setCanEdit(canEdit);
		show();		
	}
	
	/**
	 * Metodo che crea la sezione "Dati proroga termine Pubblicazione"
	 * 
	 */
	private void createDatiProrogaTerminePubblicazioneSection() {
		
		datiProrogaTerminePubblicazioneForm = new DynamicForm();
		datiProrogaTerminePubblicazioneForm.setValuesManager(vm);
		datiProrogaTerminePubblicazioneForm.setNumCols(10);
		datiProrogaTerminePubblicazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		datiProrogaTerminePubblicazioneForm.setPadding(5);
		datiProrogaTerminePubblicazioneForm.setWrapItemTitles(false);
		datiProrogaTerminePubblicazioneForm.setKeepInParentRect(true);
		datiProrogaTerminePubblicazioneForm.setWidth100();
		datiProrogaTerminePubblicazioneForm.setHeight100();
		datiProrogaTerminePubblicazioneForm.setCellPadding(5);
		datiProrogaTerminePubblicazioneForm.setAlign(Alignment.CENTER);
		datiProrogaTerminePubblicazioneForm.setOverflow(Overflow.VISIBLE);
		datiProrogaTerminePubblicazioneForm.setTop(50);
		
		dataInizioPubblicazioneItem = new DateItem("dataInizioPubblicazione", "Data inizio");
		dataInizioPubblicazioneItem.setCanEdit(false);				
		
		giorniDurataProrogaPubblicazioneItem = new ExtendedNumericItem("giorniDurataProrogaPubblicazione", "Giorni pubblicazione"); 
		giorniDurataProrogaPubblicazioneItem.setRequired(true);
		giorniDurataProrogaPubblicazioneItem.setLength(7); 
		giorniDurataProrogaPubblicazioneItem.setWidth(100);
		giorniDurataProrogaPubblicazioneItem.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(final ChangedEvent event) {
				manageOnChangedPeriodoPubbl("giorniDurataProrogaPubblicazione");
			}
		});
		
		dataAProrogaPubblicazioneItem = new ExtendedDateItem("dataAProrogaPubblicazione", "Data fine");
		dataAProrogaPubblicazioneItem.setRequired(true);
		dataAProrogaPubblicazioneItem.addChangedHandler(new ChangedHandler() {
    		
			@Override
			public void onChanged(final ChangedEvent event) {
				manageOnChangedPeriodoPubbl("dataAProrogaPubblicazione");
			}
		});
    	CustomValidator lPeriodoPubblicazioneValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				Date dataInizioPubb = dataInizioPubblicazioneItem.getValueAsDate();
		    	Date dataFinePubb = dataAProrogaPubblicazioneItem.getValueAsDate();
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
		dataAProrogaPubblicazioneItem.setValidators(lPeriodoPubblicazioneValidator);
    			
		motivoProrogaPubblicazioneItem = new TextAreaItem("motivoProrogaPubblicazione", "Motivo proroga"); 
		motivoProrogaPubblicazioneItem.setWidth(530);
		motivoProrogaPubblicazioneItem.setColSpan(7);
		motivoProrogaPubblicazioneItem.setStartRow(true);
		motivoProrogaPubblicazioneItem.setRequired(true);
	    
	    datiProrogaTerminePubblicazioneForm.setFields( 
	    		dataInizioPubblicazioneItem,
	    		dataAProrogaPubblicazioneItem,
	    		giorniDurataProrogaPubblicazioneItem,
	    		motivoProrogaPubblicazioneItem
       );
	    
	    datiProrogaTerminePubblicazioneSection = new DetailSection("Proroga pubblicazione", true, true, false, datiProrogaTerminePubblicazioneForm);
	    datiProrogaTerminePubblicazioneSection.setHeight(100);
	}
	
	/**
	 * Metodo che indica se nel conteggio dei giorni di pubblicazione il giorno di pubblicazione viene sempre considerato come 1 giorno intero, altrimenti no
	 * 
	 */
	public boolean isConteggiaInteroGiornoCorrenteXPeriodoPubbl() {
		return AurigaLayout.getParametroDBAsBoolean("CONTEGGIA_INTERO_GIORNO_CORRENTE_X_PERIODO_PUBBL");
	}
	
	private void manageOnChangedPeriodoPubbl(String fieldName) {
    	Integer giorniPubblicazione = null;
    	if(giorniDurataProrogaPubblicazioneItem.getValueAsString() != null && !"".equals(giorniDurataProrogaPubblicazioneItem.getValueAsString())) {
    		giorniPubblicazione = Integer.parseInt(giorniDurataProrogaPubblicazioneItem.getValueAsString());
		}   
    	Date dataInizioPubbl = dataInizioPubblicazioneItem.getValueAsDate();
    	Date dataFinePubbl = dataAProrogaPubblicazioneItem.getValueAsDate();
    	if(fieldName != null) {
			if("dataInizioPubblicazione".equals(fieldName)) {
				if(dataInizioPubbl != null && giorniPubblicazione != null) {
					calcolaDataFinePubbl(dataInizioPubbl, giorniPubblicazione);
					dataAProrogaPubblicazioneItem.validate();
				} else if(dataInizioPubbl != null && dataFinePubbl != null) {
					if(dataAProrogaPubblicazioneItem.validate()) {
						calcolaGiorniPubbl(dataInizioPubbl, dataFinePubbl);
					}
				}
			} else if("giorniDurataProrogaPubblicazione".equals(fieldName)) {
				if(dataInizioPubbl != null && giorniPubblicazione != null) {
					calcolaDataFinePubbl(dataInizioPubbl, giorniPubblicazione);
					dataAProrogaPubblicazioneItem.validate();
				}
			} else if("dataAProrogaPubblicazione".equals(fieldName)) {
				if(dataInizioPubbl != null && dataFinePubbl != null) {
					if(dataAProrogaPubblicazioneItem.validate()) {
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
        	dataAProrogaPubblicazioneItem.setValue(dataFinePubbl); 
		}
    }
    
    private void calcolaGiorniPubbl(Date dataInizioPubbl, Date dataFinePubbl) {
    	if(dataInizioPubbl != null && dataFinePubbl != null) {
    		Integer differenceDays = CalendarUtil.getDaysBetween(dataInizioPubbl, dataFinePubbl);
    		if(isConteggiaInteroGiornoCorrenteXPeriodoPubbl()) {
    			if(differenceDays >= 0) {
    				giorniDurataProrogaPubblicazioneItem.setValue("" + (differenceDays + 1));
    			}
			} else {
				if(differenceDays > 0) {
					giorniDurataProrogaPubblicazioneItem.setValue("" + differenceDays);
				}
			}
	    }
    }
	
	private void createButton(){
		confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(datiProrogaTerminePubblicazioneForm.validate()) {
					
					final Record formRecord = new Record(vm.getValues());
					onClickOkButton(formRecord, new DSCallback() {			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {							
							window.markForDestroy();
						}
					});					
				}
			}
		});	
		
		annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {	
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {				
				window.markForDestroy();	
			}
		});
		_buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		_buttons.setAutoDraw(false);
	}
	
	
	private void editRecord(Record record) {
		if (record!=null){
			vm.setValue("dataInizioPubblicazione",record.getAttribute("dataInizioPubblicazione"));
			vm.setValue("dataAProrogaPubblicazione", record.getAttribute("dataAProrogaPubblicazione"));
			vm.setValue("giorniDurataProrogaPubblicazione", record.getAttribute("giorniDurataProrogaPubblicazione"));
		}
	}

	private void setCanEdit(Boolean canEdit) {
		if(canEdit) {
			confermaButton.show();
			annullaButton.show();
		} else {
			confermaButton.hide();
			annullaButton.hide();
		}
		for (DynamicForm form : vm.getMembers()) {
			setCanEdit(canEdit, form);
		}
		
		// Questi campi sono sempre read-only		
		dataInizioPubblicazioneItem.setCanEdit(false);
		dataInizioPubblicazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		dataInizioPubblicazioneItem.setTabIndex(-1);
	}
	
	private void setCanEdit(boolean canEdit, DynamicForm form) {
		if (form != null) {
			for (FormItem item : form.getFields()) {
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
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
	
	public abstract void onClickOkButton(Record object, DSCallback callback);
}
