/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
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
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;

public class ListaUoAbilitateContenutiAmmTraspItem extends GridItem {
	
	protected ListaUoAbilitateContenutiAmmTraspItem instance = this;

	protected ListGridField flgSelectedField;
	protected ListGridField idOggettoPrivField;
	protected ListGridField tipoOggettoPrivField;
	protected ListGridField codiceOggettoPrivField;
	protected ListGridField denominazioneOggettoPrivField;
	protected ListGridField listaPrivilegiOggettoPrivField;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
	
	protected GridMultiToolStripButton cancellaRigheMultiButton;
	
	public ListaUoAbilitateContenutiAmmTraspItem(String name) {
		
		super(name, "lista_uo_abilitate_contenuti_amministrazione_trasparente");
		
		setGridPkField("idOggettoPriv");
		
		// Nascoste
		flgSelectedField                   = new ListGridField("flgSelected");                   flgSelectedField.setHidden(true);                    flgSelectedField.setCanHide(false);                    flgSelectedField.setCanSort(true);                    flgSelectedField.setCanEdit(false);				
		idOggettoPrivField                 = new ListGridField("idOggettoPriv");                 idOggettoPrivField.setHidden(true);                  idOggettoPrivField.setCanHide(false);                  idOggettoPrivField.setCanSort(true);                  idOggettoPrivField.setCanEdit(false);				
		tipoOggettoPrivField               = new ListGridField("tipoOggettoPriv");               tipoOggettoPrivField.setHidden(true);                tipoOggettoPrivField.setCanHide(false);                tipoOggettoPrivField.setCanSort(true);                tipoOggettoPrivField.setCanEdit(false);
		listaPrivilegiOggettoPrivField     = new ListGridField("listaPrivilegiOggettoPrivField");listaPrivilegiOggettoPrivField.setHidden(true);      listaPrivilegiOggettoPrivField.setCanHide(false);      listaPrivilegiOggettoPrivField.setCanSort(true);      listaPrivilegiOggettoPrivField.setCanEdit(false);
		
		// Visibili
		codiceOggettoPrivField          = new ListGridField("codiceOggettoPriv" , "Livelli");                codiceOggettoPrivField.setCanSort(true);           codiceOggettoPrivField.setCanHide(false);              
		denominazioneOggettoPrivField   = new ListGridField("denominazioneOggettoPriv",  "Denominazione");   denominazioneOggettoPrivField.setCanSort(true);    denominazioneOggettoPrivField.setCanEdit(false);		
		
		setGridFields(  // Nascoste
				        flgSelectedField,
				        idOggettoPrivField,
				        tipoOggettoPrivField,
				        listaPrivilegiOggettoPrivField,
				        
				        // Visibili
				        codiceOggettoPrivField,
				        denominazioneOggettoPrivField				        
		             );
	}
	
	@Override
	protected GridMultiToolStripButton[] getGridMultiselectButtons() {
		List<GridMultiToolStripButton> gridMultiselectButtons = new ArrayList<GridMultiToolStripButton>();
		
		if (cancellaRigheMultiButton == null) {
			cancellaRigheMultiButton = new GridMultiToolStripButton("buttons/delete2.png", grid, "Cancella strutture", false) {
					
					@Override
					public boolean toShow() {
						return true;
					}

					@Override
					public void doSomething() {
						cancellaRigheMulti();
					}
				};
		}
		gridMultiselectButtons.add(cancellaRigheMultiButton);
		
		return gridMultiselectButtons.toArray(new GridMultiToolStripButton[gridMultiselectButtons.size()]);
	}
				
	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
		grid.setCanDragRecordsOut(true);  
		grid.setCanAcceptDroppedRecords(true);  
		grid.setDragDataAction(DragDataAction.MOVE); 
		grid.setCanResizeFields(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);
		return grid;
	}

	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();	
		ToolStripButton addButton = new ToolStripButton();   
		addButton.setIcon("buttons/add.png");   
		addButton.setIconSize(16);
		addButton.setPrefix("Aggiungi uo");
		addButton.setPrompt("Aggiungi uo");
		
		final Record filterValues = null;
		final boolean flgSelezioneSingola = false;
		
		addButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				onClickAddButton(filterValues, flgSelezioneSingola);  
			}   
		});  
		buttons.add(addButton);

		return buttons;
	}
	
	public void onClickAddButton(Record filterValues, Boolean flgSelezioneSingola) {
		OrganigrammaLookupOrganigramma lookupOrganigramma = new OrganigrammaLookupOrganigramma(filterValues, flgSelezioneSingola);
		lookupOrganigramma.show();		
	}
	
	public class OrganigrammaLookupOrganigramma extends LookupOrganigrammaContenutiAmmTraspPopup {

		public OrganigrammaLookupOrganigramma(Record filterValues, Boolean flgSelezioneSingola) {
			super(filterValues, flgSelezioneSingola, new Integer(0));
		}

		@Override
		public void manageLookupBack(Record record) {
			setValuesFromRecord(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {
			setValuesFromRecord(record);
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
		}				
		
		@Override
		public void afterManageOnCloseClick(boolean flg) {
		}		
	}
	
	public void setValuesFromRecord(Record record) {
		
		// Prendo i dati restituiti dalla lookup
		Record recordToInsert = new Record();
		recordToInsert.setAttribute("flgSelected",                  "1");
		recordToInsert.setAttribute("tipoOggettoPriv",               getTipoOggettoPriv());
		recordToInsert.setAttribute("idOggettoPriv",                 record.getAttribute("idUoSvUt"));				
		recordToInsert.setAttribute("codiceOggettoPriv",             record.getAttribute("codRapidoUo"));		
		recordToInsert.setAttribute("denominazioneOggettoPriv",      record.getAttribute("descrUoSvUt"));
		
		// Aggiungo i dati a quelli gia' presenti
		instance.addData(recordToInsert);						
	}

	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();					
		if(isShowEditButtons()) {
			if(detailButtonField == null) {
				detailButtonField = buildDetailButtonField();					
			}
			buttonsList.add(detailButtonField);
			if(isShowModifyButton()) {
				if(modifyButtonField == null) {
					modifyButtonField = buildModifyButtonField();					
				}
				buttonsList.add(modifyButtonField);
			}
			if(isShowDeleteButton()) {
				if(deleteButtonField == null) {
					deleteButtonField = buildDeleteButtonField();					
				}
				buttonsList.add(deleteButtonField);
			}
		} else {
			if(detailButtonField == null) {
				detailButtonField = buildDetailButtonField();					
			}
			buttonsList.add(detailButtonField);
		}
		return buttonsList;
	}
	
	protected ControlListGridField buildDetailButtonField() {
		ControlListGridField detailButtonField = new ControlListGridField("detailButton");  
		detailButtonField.setAttribute("custom", true);	
		detailButtonField.setShowHover(true);		
		detailButtonField.setCanReorder(false);
		detailButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(!isShowEditButtons() || !isEditable()) {
					return buildImgButtonHtml("buttons/detail.png");
				}
				return null;
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(!isShowEditButtons() || !isEditable()) {			
					return I18NUtil.getMessages().detailButton_prompt();
				}
				return null;
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(!isShowEditButtons() || !isEditable()) {
					event.cancel();
					onClickDetailButton(event.getRecord());		
				}
			}
		});		
		return detailButtonField;
	}
	
	protected ControlListGridField buildModifyButtonField() {
		ControlListGridField modifyButtonField = new ControlListGridField("modifyButton");  
		modifyButtonField.setAttribute("custom", true);	
		modifyButtonField.setShowHover(true);		
		modifyButtonField.setCanReorder(false);
		modifyButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons()) {
					return buildImgButtonHtml("buttons/modify.png");
				}
				return null;
			}
		});
		modifyButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons()) {
					return I18NUtil.getMessages().modifyButton_prompt();
				}
				return null;
			}
		});		
		modifyButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons()) {
					event.cancel();
					onClickModifyButton(event.getRecord());
				}
			}
		});		
		return modifyButtonField;
	}
	
	public ControlListGridField buildDeleteButtonField() {
		ControlListGridField deleteButtonField = new ControlListGridField("deleteButton");  
		deleteButtonField.setAttribute("custom", true);	
		deleteButtonField.setShowHover(true);		
		deleteButtonField.setCanReorder(false);
		deleteButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons()) {
					return buildImgButtonHtml("buttons/delete.png");
				}
				return null;
			}
		});
		deleteButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons()) {
					return I18NUtil.getMessages().deleteButton_prompt();	
				}
				return null;
			}
		});		
		deleteButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons()) {
					event.cancel();
					onClickDeleteButton(event.getRecord());
				}
			}
		});			 
		return deleteButtonField;
	}
	
	public void onClickDetailButton(final ListGridRecord record) {		
	}
	
	public void onClickModifyButton(final ListGridRecord record) {		
	}
	
	public void onClickDeleteButton(ListGridRecord record) {
		grid.deselectAllRecords();
		removeData(record);
	}
	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {
		if("listaPrivilegiOggettoPriv".equalsIgnoreCase(field.getName())) {
			field.setCanEdit(isEditable());
		} else {
			field.setCanEdit(false);
		}
	}	
	
	@Override
	public void setGridFields(ListGridField... fields) {
		setGridFields(getNomeLista(), fields);
	}
	
	protected String getTipoOggettoPriv(){
		return "UO";
	}
	
	protected void cancellaRigheMulti() {
		
		SC.ask("Vuoi cancellare le strutture selezionate ? ", new BooleanCallback() {					
			@Override
			public void execute(Boolean value) {				
				if(value) {
					RecordList listaSelectedRecords = new RecordList();
					for (int i = 0; i < grid.getSelectedRecords().length; i++) {
						listaSelectedRecords.add(grid.getSelectedRecords()[i]);
					}					
					// Cancello tutti i record selezionati
					for (int i = 0; i < listaSelectedRecords.getLength(); i++) {
						Record recordSelected = listaSelectedRecords.get(i);
						removeData(recordSelected);
					}
					grid.deselectAllRecords();												
				}
			}
		}); 
	}
}