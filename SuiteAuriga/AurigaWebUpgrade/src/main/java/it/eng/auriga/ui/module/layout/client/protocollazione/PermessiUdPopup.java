/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class PermessiUdPopup extends ModalWindow {
		
	protected PermessiUdPopup _window;
	
	protected DynamicForm _form;
	protected HiddenItem idUdHiddenItem;
	protected PermessiItem permessiUdItem;
	
	protected Record record;
	
	protected Canvas portletLayout;
	
	protected DetailToolStripButton editButton;
	protected DetailToolStripButton saveButton;
	
	public PermessiUdPopup(Record pRecord){
		
		super("permessi_UD", true);
		
		_window = this;

		record = pRecord;
		
		setTitle("Permessi del documento");
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);						
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(8);
		_form.setColWidths(120,1,1,1,1,1,"*","*");		
		_form.setCellPadding(5);		
		_form.setWrapItemTitles(false);		
		
		idUdHiddenItem = new HiddenItem("idUd");
		
		permessiUdItem = new PermessiItem(false);		
		permessiUdItem.setName("listaACL");
		permessiUdItem.setShowTitle(false);
		permessiUdItem.setCanEdit(true);
		permessiUdItem.setAttribute("obbligatorio", true);
		
		_form.setFields(new FormItem[]{idUdHiddenItem, permessiUdItem});		
		
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
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("PermessiUdDataSource");
						Record lRecord = new Record(_form.getValues());
						lGwtRestDataSourceProtocollo.addData(lRecord, new DSCallback() {
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if(response.getStatus() == DSResponse.STATUS_SUCCESS) {					
									Layout.addMessage(new MessageBean("Permessi modificati con successo", "", MessageType.INFO));
									viewMode();
								} else {
									Layout.addMessage(new MessageBean("Si è verificato un errore durante il salvataggio dei permessi", "", MessageType.ERROR));
								}									
							}
						});			
					}
				});	
			}   
		}); 

		// Creo la TOOLSTRIP e aggiungo i bottoni	        
		ToolStrip detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		//detailToolStrip.addButton(backButton);
		detailToolStrip.addFill(); //push all buttons to the right 
		detailToolStrip.addButton(editButton);	
		detailToolStrip.addButton(saveButton);	
		 		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout mainLayout = new VLayout();  
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		mainLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(_form);
		
		mainLayout.addMember(layout);		
		mainLayout.addMember(detailToolStrip);
		
		setBody(mainLayout);
				
		setIcon("buttons/key.png");
	        		
		if(record != null) {			
			GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("PermessiUdDataSource");
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", record.getAttribute("idUd"));
			lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Record lDetailRecord = response.getData()[0];
					RecordList listaACL = lDetailRecord.getAttributeAsRecordList("listaACL");
					if(listaACL != null && listaACL.getLength() > 0) {
						_form.editRecord(lDetailRecord);						
					}		
					viewMode();					
					_window.show();					
				}
			});
		} else {		 
			viewMode();			
			_window.show();
		}
	}
	
	public void viewMode() {
		setCanEdit(false);
		if(record != null && record.getAttributeAsBoolean("abilModificaDati")) {
			editButton.show();
		} else { 
			editButton.hide();
		}
		saveButton.hide();
	}

	
	public void editMode() {
		setCanEdit(true);
		editButton.hide();
		saveButton.show();
	}
	
	public void setCanEdit(boolean canEdit) {
		for(FormItem item : _form.getFields()) {	 		
			if(!(item instanceof HeaderItem) && 
			   !(item instanceof ImgButtonItem) && 
			   !(item instanceof TitleItem)) {										
				item.setCanEdit(canEdit);	
				item.redraw();				
			}		
			if(item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
				item.setCanEdit(canEdit);
				item.redraw();
			}
		}				
	}

	public void onClickOkButton(DSCallback callback) {
		
	}
	
}
