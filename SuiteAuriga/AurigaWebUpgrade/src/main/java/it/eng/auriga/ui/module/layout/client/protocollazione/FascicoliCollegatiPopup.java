/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class FascicoliCollegatiPopup extends ModalWindow {
		
	protected FascicoliCollegatiPopup _window;
	protected DynamicForm _form;
	protected FascicoliCollegatiItem fascicoliCollegatiItem;
	protected Record record;
	protected Canvas portletLayout;
	protected DetailToolStripButton editButton;
	protected DetailToolStripButton saveButton;
	
	public FascicoliCollegatiPopup(Record pRecord){
		
		super("fascicoliCollegati", true);
		
		_window = this;

		record = pRecord;		
				
		if(record.getAttributeAsString("presenzaFascCollegati") != null && "1".equals(record.getAttributeAsString("presenzaFascCollegati"))) {
			setTitle("Fascicoli collegati a " + getEstremiRecord(record));
		} else {
			setTitle("Collega fascicoli a " + getEstremiRecord(record));
		}
		
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
		
		fascicoliCollegatiItem = new FascicoliCollegatiItem();
		fascicoliCollegatiItem.setName("listaFascicoliCollegati");
		fascicoliCollegatiItem.setShowTitle(false);
		fascicoliCollegatiItem.setCanEdit(true);
		
		_form.setFields(new FormItem[]{fascicoliCollegatiItem});		
		
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
				if(fascicoliCollegatiItem.validate()) {
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("FascicoliCollegatiDataSource");
					final Record lRecord = new Record(_form.getValues());
					lGwtRestDataSource.addData(lRecord, new DSCallback() {
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if(response.getStatus() == DSResponse.STATUS_SUCCESS) {					
								Layout.addMessage(new MessageBean("Fascicoli collegati modificati con successo", "", MessageType.INFO));
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("FascicoliCollegatiDataSource");
								Record lRecordToLoad = new Record();
								lRecordToLoad.setAttribute("idFolder", record.getAttribute("idUdFolder"));
								lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										Record lDetailRecord = response.getData()[0];
										RecordList listaFascicoliCollegati = lDetailRecord.getAttributeAsRecordList("listaFascicoliCollegati");
										_form.editRecord(lDetailRecord);
										if(listaFascicoliCollegati != null && listaFascicoliCollegati.getLength() > 0) {
											_window.setTitle("Fascicoli collegati a " + getEstremiRecord(record));
											viewMode();
										} else {
											_window.setTitle("Collega fascicoli a " + getEstremiRecord(record));
											editMode();
										}												
										onSaveButtonClick(listaFascicoliCollegati);																										
									}
								});								
							} else {
								Layout.addMessage(new MessageBean("Si è verificato un errore durante il salvataggio dei fascicoli collegati", "", MessageType.ERROR));
							}									
						}
					});					
				}
			}   
		}); 

		// Creo la TOOLSTRIP e aggiungo i bottoni	        
		ToolStrip detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
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
		
		setWidth("1170");
		
		setBody(mainLayout);
				
		setIcon("buttons/key.png");
	        		
		if(record != null) {			
			GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("FascicoliCollegatiDataSource");
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idFolder", record.getAttribute("idUdFolder"));
			lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Record lDetailRecord = response.getData()[0];
					RecordList listaFascicoliCollegati = lDetailRecord.getAttributeAsRecordList("listaFascicoliCollegati");					
					_form.editRecord(lDetailRecord);									
					if(listaFascicoliCollegati != null && listaFascicoliCollegati.getLength() > 0) {
						viewMode();															
					} else {
						if(record != null && (record.getAttributeAsBoolean("abilModificaDati") || record.getAttributeAsBoolean("GestioneCollegamentiFolder"))) {
							editMode();
							int count = fascicoliCollegatiItem.getTotalMembers();
							if (count == 0) {
								fascicoliCollegatiItem.onClickNewButton();
							}
						} else {
							viewMode();
						}						
					}
					
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
		if(record != null && (record.getAttributeAsBoolean("abilModificaDati") || record.getAttributeAsBoolean("GestioneCollegamentiFolder")) ) {
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
	
	public void onSaveButtonClick(RecordList listaFascicoliCollegati) {
	}
	
	public String getEstremiRecord(Record record) {
		String estremi = "Fascicolo ";
		
		if (record.getAttributeAsString("annoFascicolo") != null && !"".equals(record.getAttributeAsString("annoFascicolo"))) {
			estremi += record.getAttributeAsString("annoFascicolo") + " ";
		}
		if(record.getAttributeAsString("nroFascicolo") != null && !"".equals(record.getAttributeAsString("nroFascicolo"))) {
			estremi += "N° " + record.getAttributeAsString("nroFascicolo") + " ";
		}
		if (record.getAttributeAsString("nroSottofascicolo") != null && !"".equals(record.getAttributeAsString("nroSottofascicolo"))) {
			estremi += "/" + record.getAttributeAsString("nroSottofascicolo") + " ";
		}
		if(record.getAttributeAsDate("tsApertura") != null) {
			estremi += "del " + DateUtil.format((Date) record.getAttributeAsDate("tsApertura"));
		}
		return estremi;
	}
}