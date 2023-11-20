/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
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

public class DocumentiCollegatiPopup extends ModalWindow {
		
	protected DocumentiCollegatiPopup _window;
	
	protected DynamicForm _form;
	protected HiddenItem idUdHiddenItem;
	protected HiddenItem idRichPubblHiddenItem;
	protected DocumentiCollegatiItem documentiCollegatiItem;
	
	protected Record record;
	
	protected Canvas portletLayout;
	
	protected DetailToolStripButton editButton;
	protected DetailToolStripButton annullaButton;
	protected DetailToolStripButton saveButton;
	
	public DocumentiCollegatiPopup(Record pRecord){
		
		super("documentiCollegati", true);
		
		_window = this;

		record = pRecord;		
				
		if(record.getAttributeAsString("presenzaDocCollegati") != null && "1".equals(record.getAttributeAsString("presenzaDocCollegati"))) {
			setTitle("Documenti collegati a " + getEstremiRecord(record));
		} else {
			setTitle("Collega documenti a " + getEstremiRecord(record));
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
		
		idUdHiddenItem = new HiddenItem("idUd");
		
		idRichPubblHiddenItem = new HiddenItem("idRichPubblHidden");
		
		documentiCollegatiItem = new DocumentiCollegatiItem() {
			@Override
			public boolean isIstanzeInElencoPopup() {
				
				return showIstanzeInElencoPopup();
			}
		};		
		documentiCollegatiItem.setName("listaDocumentiCollegati");
		documentiCollegatiItem.setShowTitle(false);
		documentiCollegatiItem.setCanEdit(true);
		
		_form.setFields(new FormItem[]{idUdHiddenItem, documentiCollegatiItem, idRichPubblHiddenItem});		
		
		editButton = new DetailToolStripButton(I18NUtil.getMessages().modifyButton_prompt(), "buttons/modify.png");
		editButton.addClickHandler(new ClickHandler() {   
			
			@Override
			public void onClick(ClickEvent event) {   
				editMode();	
			}   
		}); 
		
		annullaButton = new DetailToolStripButton(getPopupUndoTitle(), "buttons/undo.png"); 
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				//Chiudo la finestra
				markForDestroy();
			}
		});
		
		saveButton = new DetailToolStripButton(getPopupSaveTitle(), getPopupSaveImage());
		saveButton.addClickHandler(new ClickHandler() {   
			
			@Override
			public void onClick(ClickEvent event) {   
				if (showIstanzeInElencoPopup()) {
					if (documentiCollegatiItem.validate()) {
						final Record lRecord = new Record(_form.getValues());
						RecordList listaDocumentiCollegati = lRecord.getAttributeAsRecordList("listaDocumentiCollegati");
//						if (listaDocumentiCollegati != null && listaDocumentiCollegati.getLength() > 0) {
//							viewMode();
//						} else {
//							editMode();
//						}
						onSaveButtonClick(listaDocumentiCollegati);
//						manageOnCloseClick();
						closePortlet();
					}
				} else {
					if (documentiCollegatiItem.validate()) {
						GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource(
								"DocumentiCollegatiDataSource");
						final Record lRecord = new Record(_form.getValues());
						lGwtRestDataSourceProtocollo.addData(lRecord, new DSCallback() {
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Layout.addMessage(new MessageBean("Documenti collegati modificati con successo", "",
											MessageType.INFO));
									GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource(
											"DocumentiCollegatiDataSource");
									Record lRecordToLoad = new Record();
									lRecordToLoad.setAttribute("idUd", record.getAttribute("idUd"));
									lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											Record lDetailRecord = response.getData()[0];
											RecordList listaDocumentiCollegati = lDetailRecord
													.getAttributeAsRecordList("listaDocumentiCollegati");
											_form.editRecord(lDetailRecord);
											if (listaDocumentiCollegati != null
													&& listaDocumentiCollegati.getLength() > 0) {
												_window.setTitle("Documenti collegati a " + getEstremiRecord(record));
												viewMode();
											} else {
												_window.setTitle("Collega documenti a " + getEstremiRecord(record));
												editMode();
											}
											onSaveButtonClick(listaDocumentiCollegati);
										}
									});
								} else {
									Layout.addMessage(new MessageBean(
											"Si è verificato un errore durante il salvataggio dei documenti collegati",
											"", MessageType.ERROR));
								}
							}
						});
					} 
				}
			}   
		}); 

		// Creo la TOOLSTRIP e aggiungo i bottoni	        
		ToolStrip detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		//detailToolStrip.addButton(backButton);
		detailToolStrip.addFill(); //push all buttons to the right 
		detailToolStrip.addButton(saveButton);
		if(!showIstanzeInElencoPopup()) {
			detailToolStrip.addButton(editButton);	
		}
		detailToolStrip.addButton(annullaButton);
		 		
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
			if (showIstanzeInElencoPopup()) {
				RecordList listaDocumentiCollegati = record.getAttributeAsRecordList("listaDocumentiCollegati");
				if (listaDocumentiCollegati != null && listaDocumentiCollegati.getLength() > 0) {
//					Record istanzeCollegate = new Record();
					record.setAttribute("listaDocumentiCollegati", listaDocumentiCollegati);
					_form.setValues(record.toMap());
					if (record != null && (record.getAttributeAsBoolean("abilModificaDati")
							|| record.getAttributeAsBoolean("abilGestioneCollegamentiUD"))) {
						editMode();
						int count = documentiCollegatiItem.getTotalMembers();
						if (count == 0) {
							documentiCollegatiItem.onClickNewButton();
						}
					} else {
						viewMode();
					}
				} else {
					if (record != null && (record.getAttributeAsBoolean("abilModificaDati")
							|| record.getAttributeAsBoolean("abilGestioneCollegamentiUD"))) {
						editMode();
						int count = documentiCollegatiItem.getTotalMembers();
						if (count == 0) {
							documentiCollegatiItem.onClickNewButton();
						}
					} else {
						viewMode();
					}
				}
				_window.show();
			} else {
				GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("DocumentiCollegatiDataSource");
				Record lRecordToLoad = new Record();
				lRecordToLoad.setAttribute("idUd", record.getAttribute("idUd"));
				lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Record lDetailRecord = response.getData()[0];
						RecordList listaDocumentiCollegati = lDetailRecord
								.getAttributeAsRecordList("listaDocumentiCollegati");
						_form.editRecord(lDetailRecord);
						if (listaDocumentiCollegati != null && listaDocumentiCollegati.getLength() > 0) {
							viewMode();
						} else {
							if (record != null && (record.getAttributeAsBoolean("abilModificaDati")
									|| record.getAttributeAsBoolean("abilGestioneCollegamentiUD"))) {
								editMode();
								int count = documentiCollegatiItem.getTotalMembers();
								if (count == 0) {
									documentiCollegatiItem.onClickNewButton();
								}
							} else {
								viewMode();
							}
						}
						_window.show();
					}
				});
			}
		} else {		 
			viewMode();			
			_window.show();
		}
	}
	
	public void viewMode() {
		setCanEdit(false);
		if(record != null && (record.getAttributeAsBoolean("abilModificaDati") || record.getAttributeAsBoolean("abilGestioneCollegamentiUD"))) {
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
	
	public void onSaveButtonClick(RecordList listaDocumentiCollegati) {
		
	}
	
	public String getEstremiRecord(Record record) {
		String estremi = "";
		if (record.getAttributeAsString("tipoProtocollo") != null && !"".equals(record.getAttributeAsString("tipoProtocollo"))) {
			if ("NI".equals(record.getAttributeAsString("tipoProtocollo"))) {
				estremi += "bozza ";
			} else if ("PP".equals(record.getAttributeAsString("tipoProtocollo"))) {
				estremi += "Prot. ";
			} else {
				estremi += record.getAttributeAsString("tipoProtocollo") + " ";
			}
		}
		if (record.getAttributeAsString("siglaProtocollo") != null && !"".equals(record.getAttributeAsString("siglaProtocollo"))) {
			estremi += record.getAttributeAsString("siglaProtocollo") + " ";
		}
		if(record.getAttributeAsString("nroProtocollo") != null && !"".equals(record.getAttributeAsString("nroProtocollo"))) {
			estremi += record.getAttributeAsString("nroProtocollo") + " ";
		}
		if (record.getAttributeAsString("subProtocollo") != null && !"".equals(record.getAttributeAsString("subProtocollo"))) {
			estremi += "sub " + record.getAttributeAsString("subProtocollo") + " ";
		}
		if(record.getAttributeAsDate("dataProtocollo") != null) {
			estremi += "del " + DateUtil.format((Date) record.getAttributeAsDate("dataProtocollo"));
		}
		return estremi;
	}

	public boolean showIstanzeInElencoPopup() {
		return false;
	}

	public String getPopupSaveTitle() {
		return showIstanzeInElencoPopup() ? "OK" : I18NUtil.getMessages().saveButton_prompt();
	}
	
	public String getPopupSaveImage() {
		return showIstanzeInElencoPopup() ? "ok.png" : "buttons/save.png";
	}
	
	public String getPopupUndoTitle() {
		return showIstanzeInElencoPopup() ? "Chiudi" : "Annulla";
	}
	
	public String getPopupUndoImage() {
		return showIstanzeInElencoPopup() ? "delete.png" : "buttons/undo.png";
	}
	
}
