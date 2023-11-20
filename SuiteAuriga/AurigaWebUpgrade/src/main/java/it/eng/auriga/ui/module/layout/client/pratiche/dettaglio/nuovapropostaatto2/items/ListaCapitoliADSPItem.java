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
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;

public abstract class ListaCapitoliADSPItem extends GridItem {
	
	private ListaCapitoliADSPItem instance = this;
	
	private ListGridField capitolo1;
	private ListGridField capitolo2;
	private ListGridField descrizioneVoce;
//	private ListGridField descrizioneProgramma;
	private ListGridField pdcFinanziario;
	private ListGridField programma;
	private ListGridField missione;
	private ListGridField keyCapitolo;
	
	public ListaCapitoliADSPItem(String name) {
		super(name, "lista_capitoli_adsp");
		
		setHeight("100%");
		
		setShowPreference(true);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		setCanEdit(false);
		setShowEditButtons(true);
		
		keyCapitolo = new ListGridField("keyCapitolo");
		keyCapitolo.setHidden(true);
		keyCapitolo.setCanHide(false); 
		
		capitolo1 = new ListGridField("capitolo1", "Capitolo");
		capitolo1.setCanSort(true);
		
		capitolo2 = new ListGridField("capitolo2", "Conto + CdR");
		capitolo2.setCanSort(true);
		
		descrizioneVoce = new ListGridField("descrizioneVoce", "Descrizione voce");
		descrizioneVoce.setCanSort(true);
		
//		descrizioneProgramma = new ListGridField("descrizioneProgramma", "Descrizione programma");
//		descrizioneProgramma.setCanSort(true);		
		
		pdcFinanziario = new ListGridField("pdcFinanziario", "PdC finanziario");
		pdcFinanziario.setCanSort(true);
		
		programma = new ListGridField("programma", "Programma");
		programma.setCanSort(true);
		
		missione = new ListGridField("missione", "Missione");
		missione.setCanSort(true);
		
//		setGridFields(keyCapitolo, capitolo1, capitolo2, descrizioneVoce, descrizioneProgramma);
		setGridFields(keyCapitolo, capitolo1, capitolo2, descrizioneVoce, pdcFinanziario, programma, missione);
		
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
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("cercaCapitoliButton")){
						((ToolStripButton) member).show();
						}
					}	
				}
			}
		}
		
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();			
		ToolStripButton cercaCapitoliButton = new ToolStripButton();   
		cercaCapitoliButton.setIcon("search.png");   
		cercaCapitoliButton.setIconSize(16);
		cercaCapitoliButton.setPrefix("cercaCapitoliButton");
		cercaCapitoliButton.setPrompt("Cerca capitoli");
		cercaCapitoliButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickCercaCapitoliButton();   	
			}   
		}); 
		
		buttons.add(cercaCapitoliButton);		
		
		return buttons;
	}
	
	protected abstract void onClickCercaCapitoliButton();

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
		
		ControlListGridField selezionaButtonField = new ControlListGridField("selezionaButton");  
		selezionaButtonField.setAttribute("custom", true);	
		selezionaButtonField.setShowHover(true);		
		selezionaButtonField.setCanReorder(false);
		selezionaButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				return buildImgButtonHtml("buttons/seleziona.png");
			}
		});
		selezionaButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return "Seleziona capitolo";					
			}
		});		
		selezionaButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				onRecordSelected(event.getRecord());				
			}
		});	
		buttonsList.add(selezionaButtonField);						
		
		return buttonsList;
	
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