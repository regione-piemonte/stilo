/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;

public abstract class ListaAnagraficaSicraItem extends GridItem {
	
	private ListaAnagraficaSicraItem instance = this;
	
	private ListGridField ragSocialeCognomeNome;
	private ListGridField denominazione;
	private ListGridField nome;
	private ListGridField cognome;
	private ListGridField codiceFiscale;
	private ListGridField partitaIva;
	private ListGridField indirizzo;
	
	public ListaAnagraficaSicraItem(String name) {
		super(name, "lista_anagrafica_sicra");
		
		setHeight("100%");
		
		setShowPreference(true);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		setCanEdit(false);
		setShowEditButtons(true);	
		
		denominazione = new ListGridField("denominazione");
		denominazione.setHidden(true);
		denominazione.setCanHide(false); 
		denominazione.setCanSort(false);
		
		nome = new ListGridField("nome");
		nome.setHidden(true);
		nome.setCanHide(false); 
		nome.setCanSort(false);
		
		cognome = new ListGridField("cognome");
		cognome.setHidden(true);
		cognome.setCanHide(false); 
		cognome.setCanSort(false);
		
		ragSocialeCognomeNome = new ListGridField("ragSocialeCognomeNome", "Rag. soc./Cognome e nome");
		ragSocialeCognomeNome.setCanSort(true);
		
		codiceFiscale = new ListGridField("codiceFiscale", "CF");
		codiceFiscale.setCanSort(true);
		
		partitaIva = new ListGridField("partitaIva", "P. IVA");
		partitaIva.setCanSort(true);
		
		indirizzo = new ListGridField("indirizzo", "Indirizzo");
		indirizzo.setCanSort(true);
		
		setGridFields(denominazione, nome, cognome, ragSocialeCognomeNome, codiceFiscale, partitaIva, indirizzo);
		
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
	
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();					
		return buttonsList;
	}
	
	public void sort(String sortField) {
		if (grid != null) {
			grid.sort(sortField);
		}
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(false);
	}
	
	@Override
	public HandlerRegistration addClickHandler(com.smartgwt.client.widgets.form.fields.events.ClickHandler handler) {
		return super.addClickHandler(handler);
	}
	
	abstract void onRecordSelected(Record record);
	
	
}