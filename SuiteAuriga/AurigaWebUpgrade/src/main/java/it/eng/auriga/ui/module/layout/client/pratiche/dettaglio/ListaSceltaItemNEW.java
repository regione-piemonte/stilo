/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;

public abstract class ListaSceltaItemNEW extends CanvasItem {
	
	protected VLayout layout;
	protected ListGrid grid; 

	protected String title;
	protected String keyFieldName;
	protected String displayFieldName;
	protected GWTRestDataSource datasource;
	
	protected String valueToSelect;
	
	public ListaSceltaItemNEW(String title, String keyFieldName, String displayFieldName, GWTRestDataSource datasource) {	
		this.title = title;
		this.keyFieldName = keyFieldName;
		this.displayFieldName = displayFieldName;
		this.datasource = datasource;
		createComponent();
	}

	protected void createComponent() {
		setEndRow(true);
		setStartRow(true);
		setShowTitle(false);	
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			@Override
			public void onInit(FormItem item) {
				
				layout = new VLayout();	
				layout.setWidth100();
				layout.setBorder("1px solid #5c7c9d");	
				layout.setRedrawOnResize(true);
				layout.setPadding(5);
				
				buildGrid();
								
				((CanvasItem)item).setCanvas(layout);		
				
				//Setto lo shouldSaveValue per gestire lo store dei valori
				setShouldSaveValue(true);
				
				addShowValueHandler(new ShowValueHandler() {
					@Override
					public void onShowValue(final ShowValueEvent event) {						
						valueToSelect = event.getDataValue() != null ? (String) event.getDataValue() : "";
						buildGrid();
					}
				});	
			}
		});
	}
	
	public void buildGrid() {
		
		if(grid != null) {
			grid.markForDestroy();
		}
		
		grid = new ListGrid();  
		grid.setWidth100();
		grid.setHeight(150);
		grid.setAutoFetchData(true);
		grid.setDataSource(datasource);
		grid.setShowHeader(true);
		grid.setAlternateRecordStyles(false);
		grid.setLeaveScrollbarGap(false);		
		grid.setKeepInParentRect(true);
		grid.setLoadingMessage(null);
		grid.setDataFetchMode(FetchMode.BASIC);		
		grid.setShowEmptyMessage(false);
		grid.setSelectionType(SelectionStyle.SINGLE);
		grid.setShowRollOver(false);		
		grid.setCanReorderFields(false);
		grid.setCanResizeFields(false);
		grid.setCanReorderRecords(false);
		grid.setCanHover(true);		
		grid.setCanGroupBy(false);	
		grid.setCanSort(false);
		grid.setShowHeaderContextMenu(false);
		grid.setNoDoubleClicks(true); 
		grid.setVirtualScrolling(true);      
		grid.setCanEdit(false);		
		grid.addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				storeValue(event.getRecord().getAttribute(keyFieldName));
				manageOnRecordClick(event.getRecord());		
			}
		});
		grid.addDataArrivedHandler(new DataArrivedHandler() {			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				try {
					if(grid != null && grid.getDataAsRecordList() != null && grid.getDataAsRecordList().getLength() > 0) {
						if(valueToSelect != null) {
							if(!"".equals(valueToSelect)) {
								for(int i = 0; i < grid.getDataAsRecordList().getLength(); i++) {
									final Record record = grid.getDataAsRecordList().get(i);
									if(record.getAttribute(keyFieldName).equals(valueToSelect)) {
										valueToSelect = null;
										grid.selectSingleRecord(grid.getRecordIndex(record));										
										break;
									}
								}
							} else {
								grid.deselectAllRecords();							
							}
						} 
					} 					
				} catch(Exception e) {}
			} 
		}); 		
		
		ListGridField keyField = new ListGridField(keyFieldName);
		keyField.setHidden(true);
		keyField.setCanHide(false);
		
		ListGridField displayField = new ListGridField(displayFieldName, title);
		
		grid.setFields(keyField, displayField);
		
		layout.setMembers(grid);				
	}
	
	public void seleziona(Record record) {
		grid.selectSingleRecord(record);
	}
	
	public abstract void manageOnRecordClick(Record record);
	
	public void carica() {
		buildGrid();
	}
	
};

