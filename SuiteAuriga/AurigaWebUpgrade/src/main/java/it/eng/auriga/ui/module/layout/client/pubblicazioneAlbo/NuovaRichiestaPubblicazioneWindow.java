/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.pubblicazioneAlbo;

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class NuovaRichiestaPubblicazioneWindow extends ModalWindow {

	protected NuovaRichiestaPubblicazioneWindow _window;

	public PubblicazioneAlboConsultazioneRichiesteDetail detail;

	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton editButton;
	protected DetailToolStripButton saveButton;
	protected DetailToolStripButton reloadDetailButton;
	protected DetailToolStripButton undoButton;
	protected DetailToolStripButton newButton;
	protected String mode;
	protected Boolean isRettifica;
	protected Boolean isModificaPubblicazione;
	protected PubblicazioneAlboRicercaPubblicazioniLayout lPubblicazioneAlboRicercaPubblicazioniLayout;

	public NuovaRichiestaPubblicazioneWindow() {
		this(null, null, null);
	}
	
	public NuovaRichiestaPubblicazioneWindow(Map initialValues, CustomLayout layout) {
		this(initialValues, layout, null);
	}
	
	public NuovaRichiestaPubblicazioneWindow(Map initialValues, CustomLayout layout, final ServiceCallback<Record> reloadCallback) {
	
		super("pubblicazione_albo_nuova_richiesta", true);

		_window = this;
		
		if(layout instanceof PubblicazioneAlboRicercaPubblicazioniLayout) {
			lPubblicazioneAlboRicercaPubblicazioniLayout = (PubblicazioneAlboRicercaPubblicazioniLayout) layout;
		}
		
		isRettifica = initialValues != null && initialValues.get("isRettifica") != null && 
				(Boolean) initialValues.get("isRettifica") ? true : false;
		
		isModificaPubblicazione = initialValues != null && initialValues.get("isModificaPubblicazione") != null && 
				(Boolean) initialValues.get("isModificaPubblicazione") ? true : false;
		
		if(isRettifica) {
			setTitle("Rettifica pubblicazione");
		} else if (isModificaPubblicazione) {
			setTitle("Modifica pubblicazione");
		} else {
			setTitle(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_new_title());
		}
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource", "idUdFolder", FieldType.TEXT); 			  

		detail = new PubblicazioneAlboConsultazioneRichiesteDetail("pubblicazione_albo_nuova_richiesta");
		detail.setDataSource(lGwtRestDataSource);
		detail.setHeight100();

		/*
		 * BUTTON DETAIL
		 */

		editButton = new DetailToolStripButton(I18NUtil.getMessages().modifyButton_prompt(), "buttons/modify.png");
		editButton.addClickHandler(new ClickHandler() { 
			
			@Override
			public void onClick(ClickEvent event) {   
				editMode();        		
			}   
		}); 

		saveButton = new DetailToolStripButton("Invia richiesta di pubblicazione", "buttons/save.png");
		saveButton.addClickHandler(new ClickHandler() { 
			
			@Override
			public void onClick(ClickEvent event) { 
				onSaveButtonClick(reloadCallback);				
			}   
		}); 		

		reloadDetailButton = new DetailToolStripButton(I18NUtil.getMessages().reloadDetailButton_prompt(), "buttons/reloadDetail.png");
		reloadDetailButton.addClickHandler(new ClickHandler() {  
			
			@Override
			public void onClick(ClickEvent event) {  
				reload(new DSCallback() {					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {						
						editMode();
					}
				});				
			}   
		}); 

		undoButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
		undoButton.addClickHandler(new ClickHandler() {   
			
			@Override
			public void onClick(ClickEvent event) {   
				reload(new DSCallback() {					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {						
						viewMode();				
					}
				});				
			}   
		}); 
		
		newButton = new DetailToolStripButton(I18NUtil.getMessages().newButton_prompt(), "buttons/new.png");
		newButton.addClickHandler(new ClickHandler() {   
			
			@Override
			public void onClick(ClickEvent event) { 
				nuovoDettaglio();		
			}   
		}); 

		detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); //push all buttons to the right 
		detailToolStrip.addButton(editButton);
		detailToolStrip.addButton(saveButton);
		detailToolStrip.addButton(reloadDetailButton);
		detailToolStrip.addButton(undoButton);		
		detailToolStrip.addButton(newButton);		

		VLayout detailLayout = new VLayout();  
		detailLayout.setOverflow(Overflow.HIDDEN);		
		setOverflow(Overflow.AUTO);    			
		
		detailLayout.setMembers(detail, detailToolStrip);		
		
		detailLayout.setHeight100();
		detailLayout.setWidth100();		
		setBody(detailLayout);
		
		if(initialValues != null) {
			detail.editNewRecord(initialValues);
		}
		newMode();
		setHeight(670);
		setWidth(1060);
		
        setIcon("menu/pubblicazione_albo_nuova_richiesta.png");  
        
        afterLoadDetail();
        
        show();
	}
	
	public void nuovoDettaglio() {
		detail.editNewRecord();	
		newMode();		
	}	
	
	public void visualizzaDettaglio(Record record) {
		detail.editRecord(record);	
		viewMode();		
	}	
	
	public void modificaDettaglio(Record record) {
		detail.editRecord(record);	
		editMode();		
	}	
	
	public void onSaveButtonClick(final ServiceCallback<Record> reloadCallback) {
		final Record record = detail.getRecordToSave();
		if(detail.validate()) {
			realSave(record, reloadCallback);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
	
	protected void realSave(final Record record, final ServiceCallback<Record> reloadCallback) {
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
		DSCallback callback = new DSCallback() {					
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record savedRecord = response.getData()[0];
					// dopo il salvataggio idRichPubbl viene ricalcolato con la nuova dataInizioPubblicazione salvata 
					try {
						detail.loadDettaglioAfterSave(savedRecord.getAttributeAsString("idUdFolder"), savedRecord.getAttributeAsString("idRichPubbl"), new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								detail.editRecord(object);
								detail.getValuesManager().clearErrors(true);
								viewMode();		
								Layout.hideWaitPopup();
								if(isRettifica) {
									Layout.addMessage(new MessageBean("Rettifica avvenuta con successo", "", MessageType.INFO));
									if(getIsModal()) {
										markForDestroy();
									} else {
										Layout.removePortlet(getNomeEntita());
									}	
									if(lPubblicazioneAlboRicercaPubblicazioniLayout != null) {
										lPubblicazioneAlboRicercaPubblicazioniLayout.reloadList();
									}
								} else if (isModificaPubblicazione){
									Layout.addMessage(new MessageBean("Modifica avvenuta con successo", "", MessageType.INFO));
									if(getIsModal()) {
										markForDestroy();
									} else {
										Layout.removePortlet(getNomeEntita());
									}	
									if(lPubblicazioneAlboRicercaPubblicazioniLayout != null) {
										lPubblicazioneAlboRicercaPubblicazioniLayout.reloadList();
									}
								} else {
									Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSave_message(getTipoEstremiRecord(object)), "", MessageType.INFO));	
								}
								if(reloadCallback != null) {
									reloadCallback.execute(object);
									//Chiudo la finestra
									_window.markForDestroy();
								}
							}
						});
											
					} catch(Exception e) {
						Layout.hideWaitPopup();
					}					
				} else {
					Layout.hideWaitPopup();
				}
			}
		};
		try {
			if(record.getAttribute("idUdFolder")==null || record.getAttribute("idUdFolder").equals("")) {			
				detail.getDataSource().addData(record, callback);
			} else {
				detail.getDataSource().updateData(record, callback);
			}
		} catch(Exception e) {
			Layout.hideWaitPopup();
		}
	}
		
	public String getTipoEstremiRecord(Record record) {		
		String tipoReg = record.getAttribute("tipoRegNum") != null ? record.getAttribute("tipoRegNum") : "";
		String sigla = "";
		if (tipoReg.equals("R") || tipoReg.equals("PP")) {
			sigla = record.getAttribute("siglaRegNum") != null ? record.getAttribute("siglaRegNum") : "";
		} else {
			sigla = tipoReg;
		}
		String numero = record.getAttribute("nroRegNum") != null ? record.getAttribute("nroRegNum") : "";;
		String anno = record.getAttribute("annoRegNum") != null ? record.getAttribute("annoRegNum") : "";;										
		return sigla + " " + numero + "/" + anno;
	}
	
	public void reload(final DSCallback callback) {
		if(this.mode.equals("new")) {
			detail.editNewRecord();
			detail.getValuesManager().clearErrors(true);
		} else {
			Record record = new Record(detail.getValuesManager().getValues());			
			((GWTRestDataSource)detail.getDataSource()).getData(record, new DSCallback() {		
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						detail.editRecord(record);	
						detail.getValuesManager().clearErrors(true);
						callback.execute(response, null, new DSRequest());
					} 				
				}
			});	
		}		
	}
	
	public boolean isAbilToMod() {		
		return (Layout.isPrivilegioAttivo("PUB/RIC/INT;M") || Layout.isPrivilegioAttivo("PUB/RIC/EST;M"));
	}
	
	public void newMode() {
		if(isRettifica) {
			setTitle("Rettifica pubblicazione");
		} else if (isModificaPubblicazione) {
			setTitle(getTitlePubblToEdit());
		} else {
			setTitle(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_new_title());
		}
//		setTitle(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_new_title());
		this.mode = "new";
		detail.setCanEdit(true);		
		detail.newMode();
		editButton.hide();
		saveButton.setTitle("Invia richiesta di pubblicazione");
		saveButton.show();		
		reloadDetailButton.hide();
		undoButton.hide();	
		newButton.hide();
	}

	public void viewMode() {
		Record record = new Record(detail.getValuesManager().getValues());
		setTitle(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_view_title(getTipoEstremiRecord(record), DateUtil.format(record.getAttributeAsDate("dataInizioPubblicazione"))));
		this.mode = "view";
		detail.setCanEdit(false);			
		detail.viewMode();
		saveButton.hide();
		reloadDetailButton.hide();
		undoButton.hide();		
//		if(record.getAttributeAsBoolean("abilModificabile") && isAbilToMod()) {
//			editButton.show();
//		} else {
		if(record.getAttributeAsBoolean("abilModifica") && isAbilToMod()) {
			editButton.show();
		} else {
			editButton.hide();
		}
		newButton.show();
	}

	public void editMode() {
		Record record = new Record(detail.getValuesManager().getValues());
		setTitle(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_edit_title(getTipoEstremiRecord(record), DateUtil.format(record.getAttributeAsDate("dataInizioPubblicazione"))));
		this.mode = "edit";
		detail.setCanEdit(true);		
		detail.editMode();
		editButton.hide();
		saveButton.setTitle("Salva");
		saveButton.show();
		reloadDetailButton.show();
		undoButton.show();
		newButton.hide();		
	}
	
	@Override
	public void manageOnCloseClick() {
		
		if(getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}	
	}
	
	public void afterLoadDetail() {
		
	}
	
	protected String getTitlePubblToEdit() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_detail_edit_title(getTipoEstremiRecord(record), DateUtil.format(record.getAttributeAsDate("dataInizioPubblicazione")));
	}
}
