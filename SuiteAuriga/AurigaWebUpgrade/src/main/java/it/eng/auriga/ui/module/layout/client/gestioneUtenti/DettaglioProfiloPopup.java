/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;



public class DettaglioProfiloPopup extends ModalWindow{

	protected DettaglioProfiloPopup _window;

	protected DettaglioProfiloDetail detail;
	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton editButton;
	protected DetailToolStripButton saveButton;
	protected DetailToolStripButton reloadDetailButton;
	protected DetailToolStripButton undoButton;
	protected DetailToolStripButton deleteButton;
	
	protected boolean canEditProfilo;
	
	protected String mode;

	public DettaglioProfiloPopup() {
		this(true);
	}
	
	public DettaglioProfiloPopup(boolean canEditProfilo) {
		
		super("dettaglio_profilo.detail", true);

		_window = this;
		
		this.canEditProfilo = canEditProfilo;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		//TODO
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaDettaglioProfiloDataSource", "idProfilo", FieldType.TEXT); 
		
		detail = new DettaglioProfiloDetail("dettaglio_profilo.detail");
		detail.setHeight100();
		detail.setDataSource(lGwtRestDataSource);
		
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

		saveButton = new DetailToolStripButton(I18NUtil.getMessages().saveButton_prompt(), "buttons/save.png");
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
		
		deleteButton = new DetailToolStripButton(I18NUtil.getMessages().deleteButton_prompt(), "buttons/delete.png");
		deleteButton.addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {   
				Record record = new Record(detail.getValuesManager().getValues());	
				final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaDettaglioProfiloDataSource", "idProfilo", FieldType.TEXT);
				lGwtRestDataSource.getData(record, new DSCallback() {															
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							lGwtRestDataSource.removeData(record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS){
										final Record record = response.getData()[0];
										if(record.getAttribute("warning") != null && !"".equals(record.getAttribute("warning"))) {
											SC.ask(record.getAttribute("warning"), new BooleanCallback() {
												
												@Override
												public void execute(Boolean value) {
													if(value != null && value) {
														lGwtRestDataSource.addParam("flgIgnoreWarning", "1");
														lGwtRestDataSource.removeData(record, new DSCallback() {
															@Override
															public void execute(DSResponse response, Object rawData, DSRequest request) {
																if (response.getStatus() == DSResponse.STATUS_SUCCESS){
																	detail.setIdProfilo(null);
																	manageOnCloseClick();												
																}
															}													
														});
													}
												}
											});
										} else {
											detail.setIdProfilo(null);
											manageOnCloseClick();	
										}
									}
								}
							});	
						} 				
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
		detailToolStrip.addButton(deleteButton);		

		VLayout detailLayout = new VLayout();  
		detailLayout.setOverflow(Overflow.HIDDEN);		
		setOverflow(Overflow.AUTO);    			
		
		detailLayout.setMembers(detail, detailToolStrip);		
		
		detailLayout.setHeight100();
		detailLayout.setWidth100();			
		setBody(detailLayout);
		
		newMode();
		
        setIcon("buttons/profilo.png");   
	}
	
	public void nuovoDettaglio() {
		setTitle("Nuovo profilo");
		detail.editNewRecord();	
		newMode();		
	}	
	
	public void visualizzaDettaglio(Record record) {
		setTitle("Dettaglio profilo " + getTipoEstremiRecord(record));
		detail.editRecord(record);	
		viewMode();		
	}	
	
	public void modificaDettaglio(Record record) {
		setTitle("Modifica profilo " + getTipoEstremiRecord(record));
		detail.editRecord(record);	
		editMode();		
	}	
	
	public void onSaveButtonClick() {
		final Record record = new Record(detail.getValuesManager().getValues());
		if(detail.validate()) {
				realSave(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
	
	protected void realSave(final Record record) {
		DSOperationType saveOperationType = detail.getValuesManager().getSaveOperationType();		
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
		DSCallback callback = new DSCallback() {								
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record savedRecord = response.getData()[0];
					try {
						manageLoadDetail(savedRecord, detail.getRecordNum(), new DSCallback() {							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								viewMode();		
								Layout.hideWaitPopup();
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSave_message(getTipoEstremiRecord(response.getData()[0])), "", MessageType.INFO));								
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
			if((saveOperationType!=null && saveOperationType.equals(DSOperationType.ADD)) || record.getAttribute("idProfilo")==null || record.getAttribute("idProfilo").equals("")) {			
				detail.getDataSource().addData(record, callback);
			} else {
				detail.getDataSource().updateData(record, callback);
			}
		} catch(Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		((GWTRestDataSource)detail.getDataSource()).getData(record, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					Record record = response.getData()[0];
					detail.editRecord(record, recordNum);	
					detail.getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				} 				
			}
		});
	}	
	
	public String getTipoEstremiRecord(Record record) {		
		return record.getAttribute("nomeProfilo");
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
		
		return Layout.isPrivilegioAttivo("SIC/PR;M");
	}
	
	public boolean isAbilToDel() {
		
		return Layout.isPrivilegioAttivo("SIC/PR;FC");
	}
	
	
	public void newMode() {
		this.mode = "new";
		detail.setCanEdit(true);		
		editButton.hide();
		saveButton.show();
		reloadDetailButton.hide();
		undoButton.hide();	
		deleteButton.hide();
	}

	public void viewMode() {
		this.mode = "view";
		detail.setCanEdit(false);			
		saveButton.hide();
		reloadDetailButton.hide();
		undoButton.hide();		
		if(canEditProfilo && isAbilToMod()) {
			editButton.show();					
		} else {
			editButton.hide();
		}
		if(canEditProfilo && isAbilToDel()) {
			deleteButton.show();
		} else {
			deleteButton.hide();
		}
	}

	public void editMode() {
		this.mode = "edit";
		detail.setCanEdit(true);		
		editButton.hide();
		saveButton.show();
		reloadDetailButton.show();
		undoButton.show();
		if(canEditProfilo && isAbilToDel()) {
			deleteButton.show();
		} else {
			deleteButton.hide();
		}
	}
	
	@Override
	public void manageOnCloseClick() {
		if(getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}	
		afterManageOnCloseClick(detail.getIdProfilo());
	}
	
	
	public void afterManageOnCloseClick(String idProfilo) {		
		
	}

}
