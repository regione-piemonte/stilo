/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;

public abstract class ListaFileItem extends GridItem {
	
	private ListaFileItem instance = this;
	
	private ControlListGridField selezionaButtonField;
	private ListGridField nomeFileAllegato;
	private ListGridField idDocAllegato;
	private ListGridField nroAllegato;
	private ListGridField uriFileAllegato;
	private ListGridField infoFile;
	private ListGridField remoteUri;
	
	public ListaFileItem(String name) {
		super(name, "lista_file");
		
		setHeight("100%");
		
		setShowPreference(true);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		setCanEdit(false);
		setShowEditButtons(true);
		
		selezionaButtonField = new ControlListGridField("selezionaButton");  
		selezionaButtonField.setAttribute("custom", true);	
		selezionaButtonField.setShowHover(true);		
		selezionaButtonField.setCanDragResize(false);
		selezionaButtonField.setWidth(50);
		selezionaButtonField.setCanReorder(false);
		selezionaButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {		
				return buildImgButtonHtml("buttons/seleziona.png", "left");
			}
		});
		selezionaButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return "Seleziona file";					
			}
		});		
		selezionaButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				onRecordSelected(event.getRecord());				
			}
		});
		
		
		nomeFileAllegato = new ListGridField("nomeFileAllegato", "Nome");
		nomeFileAllegato.setEscapeHTML(true);
		nomeFileAllegato.setCanSort(true);
		
		idDocAllegato = new ListGridField("idDocAllegato");
		idDocAllegato.setHidden(true); 
		idDocAllegato.setCanHide(false);
	
		nroAllegato = new ListGridField("nroAllegato");
		nroAllegato.setHidden(true); 
		nroAllegato.setCanHide(false);
		
		uriFileAllegato = new ListGridField("uriFileAllegato");
		uriFileAllegato.setHidden(true); 
		uriFileAllegato.setCanHide(false);	
		
		infoFile = new ListGridField("infoFile");
		infoFile.setHidden(true); 
		infoFile.setCanHide(false);
		
		remoteUri = new ListGridField("remoteUri");
		remoteUri.setHidden(true); 
		remoteUri.setCanHide(false);

		setGridFields(selezionaButtonField, nomeFileAllegato, idDocAllegato, nroAllegato, uriFileAllegato, infoFile, remoteUri);
		
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setEditable(canEdit);
		super.setCanEdit(true);
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if(member instanceof ToolStripButton) {
					if(isEditable() && isShowEditButtons()) {
						member.show();	
					} else {
						member.hide();						
					}
				}	
			}
		}
	}
	
	@Override
	public ListGrid buildGrid() {
		final ListGrid grid = super.buildGrid();
		grid.setCanExpandRecords(false);
		grid.setShowAllRecords(true);  
		grid.setCanDrag(false);
		grid.setDragDataAction(DragDataAction.NONE);
		grid.setCanEdit(false);
		grid.setCanReorderRecords(false);
		grid.setCanAcceptDroppedRecords(false);
		
		grid.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if (event.getRecord() != null) {
					onRecordSelected(event.getRecord());
				}
			}
		});
		
		return grid;		
	}

	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {

	}
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields(null, fields);
	}		
	

	
	public void sort(String sortField) {
		if (grid != null) {
			grid.sort(sortField);
		}
	}
	
	@Override
	public HandlerRegistration addClickHandler(com.smartgwt.client.widgets.form.fields.events.ClickHandler handler) {
		return super.addClickHandler(handler);
	}
	
	abstract void onRecordSelected(Record record);
	
	
}