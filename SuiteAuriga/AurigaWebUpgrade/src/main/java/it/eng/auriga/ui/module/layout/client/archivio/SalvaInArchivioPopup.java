/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public abstract class SalvaInArchivioPopup extends ModalWindow {

	private SalvaInArchivioPopup _window;
	
//	private ArchivioLayout portletLayout;
	private ArchivioDetail detail;
	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton editButton;
	protected DetailToolStripButton saveButton;
	protected DetailToolStripButton reloadDetailButton;
	protected DetailToolStripButton undoButton;
	protected DetailToolStripButton lookupButton;
	
	protected String mode;	
	
	public SalvaInArchivioPopup(final Record record) {
			
		super("archivio.detail", true);
		
		_window = this;
		
		String nroFascicolo = "";
		if (record.getAttribute("nroFascicolo")!=null)
			 nroFascicolo = record.getAttribute("nroFascicolo");
		
		String nroSottofascicolo = "";
		if (record.getAttribute("nroSottofascicolo")!=null)
			nroSottofascicolo = record.getAttribute("nroSottofascicolo");
		
		if(nroFascicolo != null && !"".equals(nroFascicolo)) {
			if(nroSottofascicolo != null && !"".equals(nroSottofascicolo)) {
				setTitle("Nuovo inserto");
			}else{
				setTitle("Nuovo sotto-fascicolo");
			}
		} else {
			setTitle("Nuovo fascicolo");
		}
				
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
							
//		portletLayout = new ArchivioLayout("FASCICOLA_UD", true, null, null, true) {
//			@Override
//			public void lookupBack(Record record) {
//				
//				manageLookupBack(record);
//				_window.markForDestroy();
//			}
//		};
//		portletLayout.setLookup(true);
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT); 			  
		
		detail = new ArchivioDetail("archivio.detail");
		detail.setHeight100();
		detail.setDataSource(lGwtRestDataSource);
		
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

		lookupButton = new DetailToolStripButton(I18NUtil.getMessages().selezionaButton_prompt(), "buttons/seleziona.png");   
		lookupButton.addClickHandler(new ClickHandler() {   
			public void onClick(ClickEvent event) {   
				Record record = new Record(detail.getValuesManager().getValues());
				manageLookupBack(record);
				_window.markForDestroy();			
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
		detailToolStrip.addButton(lookupButton);

		VLayout detailLayout = new VLayout();  
		detailLayout.setOverflow(Overflow.HIDDEN);		
		setOverflow(Overflow.AUTO);    			
		
		detailLayout.setMembers(detail, detailToolStrip);		
		
		detailLayout.setHeight100();
		detailLayout.setWidth100();		
		setBody(detailLayout);
		
		newMode();
		
//		portletLayout.setHeight100();
//		portletLayout.setWidth100();		
//		setBody(portletLayout);
//	
//		portletLayout.newMode();								
		
        setIcon("menu/archivio.png");              
	}
	
	public void onSaveButtonClick() {
		if(detail.validate()) {
			realSave(detail.getRecordToSave());
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
			if((saveOperationType!=null && saveOperationType.equals(DSOperationType.ADD)) || record.getAttribute("idUdFolder")==null || record.getAttribute("idUdFolder").equals("")) {			
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
		String estremi = "";
		if(record.getAttributeAsString("annoFascicolo") != null && !"".equals(record.getAttributeAsString("annoFascicolo"))) {
			estremi += record.getAttributeAsString("annoFascicolo") + " ";
		}	
		if(record.getAttributeAsString("indiceClassifica") != null && !"".equals(record.getAttributeAsString("indiceClassifica"))) {
			estremi += record.getAttributeAsString("indiceClassifica") + " ";
		}	
		if(record.getAttributeAsString("nroFascicolo") != null && !"".equals(record.getAttributeAsString("nroFascicolo"))) {						
			estremi += "N° " + record.getAttributeAsString("nroFascicolo");
			if(record.getAttributeAsString("nroSottofascicolo") != null && !"".equals(record.getAttributeAsString("nroSottofascicolo"))) {
				estremi += "/" + record.getAttributeAsString("nroSottofascicolo");
			}
			if(record.getAttributeAsString("nroInserto") != null && !"".equals(record.getAttributeAsString("nroInserto"))) {
				estremi += "/" + record.getAttributeAsString("nroInserto");
			}
			estremi += " ";
		}
		if(record.getAttributeAsString("nome") != null && !"".equals(record.getAttributeAsString("nome"))) {
			estremi += record.getAttributeAsString("nome");						
		}		
		return estremi;
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
	
	public void newMode() {
		this.mode = "new";
		detail.newMode();
		detail.setCanEdit(true);		
		editButton.hide();
		saveButton.show();
		reloadDetailButton.hide();
		undoButton.hide();	
		lookupButton.hide();
	}

	public void viewMode() {
		this.mode = "view";
		detail.viewMode();
		detail.setCanEdit(false);			
		saveButton.hide();
		reloadDetailButton.hide();
		undoButton.hide();
		Record record = new Record(detail.getValuesManager().getValues());
		if(record.getAttributeAsBoolean("abilModificaDati")) {
			editButton.show();
		} else {
			editButton.hide();
		}		
		if(record.getAttributeAsBoolean("flgSelXFinalita") != null && record.getAttributeAsBoolean("flgSelXFinalita")) {			
			lookupButton.show();
		} else {
			lookupButton.hide();
		}	
	}

	public void editMode() {
		this.mode = "edit";
		detail.editMode();
		detail.setCanEdit(true);		
		editButton.hide();
		saveButton.show();
		reloadDetailButton.show();
		undoButton.show();
		lookupButton.hide();
	}
	
	public void nuovoDettaglio(Map initialValues) {
//		((ArchivioDetail)portletLayout.getDetail()).editNewRecord(initialValues);		
		detail.editNewRecord(initialValues);
		detail.newMode();
		detail.refreshTabIndex();		
	}	
	
	@Override
	public void manageOnCloseClick() {
		
		if(getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}	
	}
	
	public abstract void manageLookupBack(Record record);
}
