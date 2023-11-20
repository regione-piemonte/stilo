/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DettaglioRichiestaPubblicazioneWindow extends ModalWindow {

	protected DettaglioRichiestaPubblicazioneWindow _window;

	private PubblicazioneAlboConsultazioneRichiesteDetail detail;

	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton editButton;
	protected DetailToolStripButton saveButton;
	protected DetailToolStripButton reloadDetailButton;
	protected DetailToolStripButton undoButton;
	protected String mode;

	public DettaglioRichiestaPubblicazioneWindow(Record recordDettaglio) {
	
		super("pubblicazione_albo_dettaglio", true);

		_window = this;
		
		setTitle("Dettaglio richiesta pubblicazione");
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource", "idUdFolder", FieldType.TEXT); 			  

		detail = new PubblicazioneAlboConsultazioneRichiesteDetail("pubblicazione_albo_dettaglio");
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

		saveButton = new DetailToolStripButton("Salva", "buttons/save.png");
		saveButton.addClickHandler(new ClickHandler() { 
			
			@Override
			public void onClick(ClickEvent event) { 
				onSaveButtonClick();				
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
		
		detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); //push all buttons to the right 
		detailToolStrip.addButton(editButton);
		detailToolStrip.addButton(saveButton);
		detailToolStrip.addButton(reloadDetailButton);
		detailToolStrip.addButton(undoButton);		
		
		VLayout detailLayout = new VLayout();  
		detailLayout.setOverflow(Overflow.HIDDEN);		
		setOverflow(Overflow.AUTO);    			
		
		detailLayout.setMembers(detail, detailToolStrip);		
		
		detailLayout.setHeight100();
		detailLayout.setWidth100();		
		setBody(detailLayout);
		
		if(recordDettaglio != null) {
			detail.editRecord(recordDettaglio);
		}
		viewMode();
		setHeight(670);
		setWidth(1060);
		
        setIcon("menu/pubblicazione_albo_consultazione_richieste.png");   
        show();
	}
	
	public void visualizzaDettaglio(Record record) {
		detail.editRecord(record);	
		viewMode();		
	}	
	
	public void modificaDettaglio(Record record) {
		detail.editRecord(record);	
		editMode();		
	}	
	
	public void onSaveButtonClick() {
		final Record record = detail.getRecordToSave();
		if(detail.validate()) {
			realSave(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
	
	protected void realSave(final Record record) {
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
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSave_message(getTipoEstremiRecord(object)), "", MessageType.INFO));								
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
	
	public void viewMode() {
		Record record = new Record(detail.getValuesManager().getValues());
		setTitle(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_view_title(getTipoEstremiRecord(record), DateUtil.format(record.getAttributeAsDate("dataInizioPubblicazione"))));
		this.mode = "view";
		detail.setCanEdit(false);			
		detail.viewMode();
		saveButton.hide();
		reloadDetailButton.hide();
		undoButton.hide();	
		if(record.getAttributeAsBoolean("abilModificabile") && isAbilToMod()) {
			editButton.show();
		} else {
			editButton.hide();
		}
	}

	public void editMode() {
		Record record = new Record(detail.getValuesManager().getValues());
		setTitle(I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_edit_title(getTipoEstremiRecord(record), DateUtil.format(record.getAttributeAsDate("dataInizioPubblicazione"))));
		this.mode = "edit";
		detail.setCanEdit(true);		
		detail.editMode();
		editButton.hide();
		saveButton.show();
		reloadDetailButton.show();
		undoButton.show();
	}
	
	@Override
	public void manageOnCloseClick() {
		
		if(getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}	
	}
}