/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.Layout;

public class CarrelloListGrid extends ListGrid {

	private Layout layout;
	
	public CarrelloListGrid(Layout pLayout) {
		// TODO Auto-generated constructor stub
		
		layout = pLayout;
		
		setHeight100();
		setWidth100();
		setBackgroundColor("white");
		setMargin(0);
		setShowEdges(false);
		setAutoFetchData(true);
		setShowAllRecords(true);	
		setDataSource(new MultiLookupDataSource());
		setSelectionType(SelectionStyle.NONE);
		setCanHover(false);
		setShowHover(false);
		setShowHeader(true);
		setLeaveScrollbarGap(false);
		setShowAllRecords(true);		
		setShowHeaderContextMenu(false);
		setCanSort(false);
		setCanAutoFitFields(false);
		setCanResizeFields(false);
		setCanReorderFields(false);
		setSortField("nome");
		setSortDirection(SortDirection.ASCENDING);		
		setOverflow(Overflow.AUTO);		
		
//		setCanRemoveRecords(true);
//		setRemoveIcon("buttons/remove.png");
			
		setDataSource(new MultiLookupGridDS());
		
		ListGridField icona = new ListGridField("icona");
		icona.setAlign(Alignment.CENTER);
		icona.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		icona.setWidth(25);		
		icona.setAttribute("custom", true);	
		icona.setShowHover(true);		
		icona.setCanReorder(false);
		icona.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(value != null) {
					return "<div align=\"center\"><img src=\"images/" + value + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";					
				}
				return null;
			}
		});		
		
		ListGridField nome = new ListGridField("nome", "Nome");
		nome.setAlign(Alignment.CENTER);
		nome.setCellAlign(Alignment.LEFT);
		
		ListGridField path = new ListGridField("path", "Selezionato da");
		path.setAlign(Alignment.CENTER);
		path.setCellAlign(Alignment.LEFT);
		
		ListGridField removeButton = new ListGridField("removeButton");		
		Button removeAllButton = new Button();
		removeAllButton.setIcon("buttons/remove.png");
		removeAllButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				
				removeAllRecords();
			}
		});		
		removeButton.setHeaderButtonProperties(removeAllButton);
		removeButton.setAlign(Alignment.CENTER);
		removeButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		removeButton.setWidth(25);		
		removeButton.setAttribute("custom", true);	
		removeButton.setShowHover(true);		
		removeButton.setCanReorder(false);
		removeButton.setCanEdit(false);
		removeButton.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {							
				return "<div style=\"cursor:pointer\" align=\"center\"><img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
			}
		});		
		removeButton.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				event.cancel();
				final ListGridRecord record = getRecord(event.getRecordNum());
				getDataSource().removeData(record);
				if(layout != null && getDataAsRecordList().getLength() == 0) {
					layout.hide();
				}
			}
		});			 
		
		if(layout != null) {
			layout.setVisible(false);
		}
		
		setFields(icona, nome, path, removeButton);
		
	}	
	
	public boolean addRecord(Record record) {
		boolean trovato = false;
		for(int i = 0; i < getDataAsRecordList().getLength(); i++) {
			Record listRecord = getDataAsRecordList().get(i);
			if(listRecord.getAttributeAsString("id").equals(record.getAttributeAsString("id")) && listRecord.getAttributeAsString("idFolderApp").equals(record.getAttributeAsString("idFolderApp"))) {
				trovato = true;
				break;
			}
		}
		if(!trovato) {
			getDataSource().addData(record);										
		}
		if(layout != null) {
			layout.show();
		}
		return !trovato;		
	}
	
	public void removeAllRecords() {
		for(int i = 0; i < getDataAsRecordList().getLength(); i++) {
			Record record = getDataAsRecordList().get(i);
			getDataSource().removeData(record);
		}		
		if(layout != null) {
			layout.hide();
		}
	}
	
	public class MultiLookupGridDS extends DataSource {

		public MultiLookupGridDS() {

			DataSourceTextField id = new DataSourceTextField("id");
			id.setPrimaryKey(true);
			id.setHidden(true);
			
			DataSourceTextField icona = new DataSourceTextField("icona");
			icona.setHidden(true);			
			
			DataSourceTextField nome = new DataSourceTextField("nome");
			nome.setHidden(true);
			
			DataSourceTextField idFolderApp = new DataSourceTextField("idFolderApp");
			idFolderApp.setPrimaryKey(true);
			idFolderApp.setHidden(true);
			
			DataSourceTextField path = new DataSourceTextField("path");
			path.setHidden(true);
						
			setFields(id, icona, nome, idFolderApp, path);
			
			setClientOnly(true);   
			
		}

	}
	
}
