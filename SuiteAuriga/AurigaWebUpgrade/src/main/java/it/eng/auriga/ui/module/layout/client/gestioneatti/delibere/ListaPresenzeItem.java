/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridFieldIfFunction;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
/**
 * 
 * @author DANCRIST
 *
 */

public class ListaPresenzeItem extends GridItem {
	
	private ListaPresenzeItem instance = this;
	
	protected ListGridField idUser;
	protected ListGridField denominazione;
	protected ListGridField delegato;
	protected ListGridField ruolo;
	protected ListGridField flgExtra;
	protected ListGridField flgPresenza;

	protected String idSeduta;
	
	protected ControlListGridField removeButtonField;

	public ListaPresenzeItem(String name,String tipologiaSessione, String idSeduta) {
		
		super(name, "lista_presenze");
		
		this.idSeduta = idSeduta;
		
		setGridPkField("idUser");
		setShowPreference(true);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		setCanEdit(true);  
		
		idUser = new ListGridField("idUser");
		idUser.setHidden(true);
		idUser.setCanHide(false);
		idUser.setCanSort(true);
		idUser.setCanEdit(false);
		
		denominazione = new ListGridField("denominazione", "Cognome e nome");
		denominazione.setCanSort(true);
		denominazione.setCanEdit(false);
		
		delegato = new ListGridField("delegato", "Delegato");
		delegato.setCanSort(true);
		GWTRestDataSource delegatoDS = new GWTRestDataSource("LoadComboDelegatoSedutaDataSource");
		delegatoDS.addParam("tipo_sessione", tipologiaSessione);		
		SelectItem delegatoItem = new SelectItem();
		delegatoItem.setValueField("key");
		delegatoItem.setDisplayField("value");
		delegatoItem.setOptionDataSource(delegatoDS);
		delegatoItem.setAutoFetchData(false);
		delegatoItem.setAlwaysFetchMissingValues(true);
		delegatoItem.setFetchMissingValues(true);
		delegatoItem.setStartRow(false);
		delegatoItem.setColSpan(1);
		delegatoItem.setWidth(200);	
		delegatoItem.setAllowEmptyValue(true);
		delegatoItem.setRedrawOnChange(true);
		delegato.setCellAlign(Alignment.LEFT);
		delegato.setEditorProperties(delegatoItem);
		delegato.setCanEdit(true);
		delegato.setAttribute("custom", true);
		delegato.setShowHover(true);
		delegato.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDenominazioneDelegato(record);
			}
		});
		delegato.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDenominazioneDelegato(record);
			}
		});
		
		ruolo = new ListGridField("ruoloSeduta", "Ruolo in seduta");
		ruolo.setCanSort(true);
		ruolo.setCanEdit(true);
		GWTRestDataSource tipoRuoloDS = new GWTRestDataSource("LoadComboTipoRuoloSedutaDataSource");
		tipoRuoloDS.addParam("tipo_sessione", tipologiaSessione);		
		SelectItem tipoRuoloItem = new SelectItem("tipoRuolo");
		tipoRuoloItem.setValueField("key");
		tipoRuoloItem.setDisplayField("value");
		tipoRuoloItem.setOptionDataSource(tipoRuoloDS);
		tipoRuoloItem.setAutoFetchData(false);
		tipoRuoloItem.setAlwaysFetchMissingValues(true);
		tipoRuoloItem.setFetchMissingValues(true);
		tipoRuoloItem.setStartRow(false);
		tipoRuoloItem.setColSpan(7);
		tipoRuoloItem.setWidth(200);
		tipoRuoloItem.setAllowEmptyValue(true);
		ruolo.setEditorProperties(tipoRuoloItem); 
		ruolo.setEmptyCellValue("<i>Seleziona...</i>");

		flgExtra = new ListGridField("flgExtra", "Extra");
		flgExtra.setType(ListGridFieldType.ICON);
		flgExtra.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgExtra.setAlign(Alignment.CENTER);
		flgExtra.setWrap(false);
		flgExtra.setWidth(30);
		flgExtra.setAttribute("custom", true);
		flgExtra.setShowHover(true);
		flgExtra.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgExtra = (String) record.getAttribute("flgExtra");
				if (flgExtra != null && "1".equalsIgnoreCase(flgExtra)) {
					return buildIconHtml("public.png");
				}
				return null;
			}
		});
		flgExtra.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgExtra = (String) record.getAttribute("flgExtra");
				if (flgExtra != null && "1".equalsIgnoreCase(flgExtra)) {
					return "Extra componenti commissioni convocate";
				}
				return null;
			}
		});
		
		flgPresenza = new ListGridField("flgPresenza", "Presenza");
		flgPresenza.setAttribute("custom", true);
		flgPresenza.setCanSort(true);
		flgPresenza.setCanEdit(true);
		flgPresenza.setRequired(true);
		flgPresenza.setCellFormatter(new CellFormatter() {
		
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				LinkedHashMap<String, String> flgPresenzaValueMap = getFlgPresenzaValueMap();
				if(flgPresenzaValueMap != null && flgPresenzaValueMap.containsKey(record.getAttribute("flgPresenza"))) {
					return flgPresenzaValueMap.get(record.getAttribute("flgPresenza"));
				}
				return null;								
			}
		});
		flgPresenza.setEmptyCellValue("<i>Seleziona...</i>");		

		setGridFields(
				idUser,
				denominazione,
				delegato,
				ruolo,
				flgPresenza,
				flgExtra
		);
	}
	
	public LinkedHashMap<String, String> getFlgPresenzaValueMap() {
		LinkedHashMap<String, String> flgPresenzaValueMap = new LinkedHashMap<String, String>();
		flgPresenzaValueMap.put("2", "presente dopo appello");
		flgPresenzaValueMap.put("1", "presente");
		flgPresenzaValueMap.put("0", "assente");	
		return flgPresenzaValueMap;
	}
	
	@Override
	public ListGrid buildGrid() {	
		
		ListGrid grid = super.buildGrid();		
		grid.setCanDragRecordsOut(true);  
		grid.setCanAcceptDroppedRecords(true);  
		grid.setDragDataAction(DragDataAction.MOVE); 
		grid.setCanResizeFields(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);
		grid.setEditByCell(true);
	    grid.setEditorCustomizer(new ListGridEditorCustomizer() {
			
			@Override
			public FormItem getEditor(ListGridEditorContext context) {
				if(context.getEditField().getName().equals("flgPresenza")) {
					RadioGroupItem radioPresenzaItem = new RadioGroupItem("radioPresenza");
					radioPresenzaItem.setShowTitle(false);
					radioPresenzaItem.setValueMap(getFlgPresenzaValueMap());
					radioPresenzaItem.setVertical(true);
					radioPresenzaItem.setWrap(false);
					radioPresenzaItem.setDefaultValue("2");
					return radioPresenzaItem; 
				} else if(context.getEditField().getName().equals("delegato")) {
					String flgExtra = context.getEditedRecord().getAttribute("flgExtra");
					if(flgExtra != null && "1".equalsIgnoreCase(flgExtra)) {
						TextItem lTextItem = new TextItem();
						lTextItem.setCanEdit(false);
						return lTextItem;
					}
				}
				return null;				
			}
		});	

		return grid;
	}	
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields("lista_presenze", fields);
	}
	
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();					

		if(removeButtonField == null) {
			removeButtonField = buildRemoveButtonField();
		}
		buttonsList.add(removeButtonField);
		
		return buttonsList;
	}
	
	protected ControlListGridField buildRemoveButtonField() {
		
		ControlListGridField removeButtonField = new ControlListGridField("removeButton");  
		removeButtonField.setAttribute("custom", true);	
		removeButtonField.setShowHover(true);		
		removeButtonField.setCanReorder(false);
		removeButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttributeAsString("flgExtra") != null && "1".equals(record.getAttributeAsString("flgExtra"))) {
					return buildImgButtonHtml("buttons/remove.png");
				}
				return null;
			}
		});
		removeButtonField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(record.getAttributeAsString("flgExtra") != null && "1".equals(record.getAttributeAsString("flgExtra"))) {
					return "Rimuovi";
				}
				return null;
			}
		});		
		removeButtonField.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				if(event.getRecord().getAttributeAsString("flgExtra") != null && "1".equals(event.getRecord().getAttributeAsString("flgExtra"))) {					
					onClickRemoveButton(event.getRecord());
				}
			}
		});	
		removeButtonField.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return true;
			}
		});
		
		return removeButtonField;
	}

	public void onClickRemoveButton(final ListGridRecord record) {
		
		instance.removeData(record);
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> editButtons = new ArrayList<ToolStripButton>();	
		
		ToolStripButton addComponentiExtraButton = new ToolStripButton();   
		addComponentiExtraButton.setIcon("buttons/new.png");   
		addComponentiExtraButton.setIconSize(16);
		addComponentiExtraButton.setPrefix("aggiungi_extra");
		addComponentiExtraButton.setPrompt("Aggiungi componenti extra commissioni convocate");
		addComponentiExtraButton.addClickHandler(new ClickHandler() {	

			@Override
			public void onClick(ClickEvent event) {
				
				SceltaComponentiExtraCommissioniPopup sceltaComponentiExtraCommissioniPopup = new SceltaComponentiExtraCommissioniPopup(idSeduta) {
					
					@Override
					public void manageOnOkButtonClick(ListGridRecord[] selectedRecords) {
						for(int i = 0; i < selectedRecords.length; i++) {
							ListGridRecord listGridRecord = selectedRecords[i];
							listGridRecord.setAttribute("flgExtra", "1");
							listGridRecord.setAttribute("flgPresenza", "2");
							addData(listGridRecord);
						}
					}					
				};
				sceltaComponentiExtraCommissioniPopup.show();
			}   
		});
	
		if(showAddComponentiExtraButton()) {
			editButtons.add(addComponentiExtraButton);
		}

		return editButtons;
	}
	
	public boolean showAddComponentiExtraButton() {
		return true;
	}

	protected String getDenominazioneDelegato(ListGridRecord record) {
		String ret = "";
		if (record.getAttribute("delegato") != null && !record.getAttribute("delegato").equalsIgnoreCase("")){
			String[] listaValoriString = record.getAttribute("delegato").split("\\|\\*\\|");
			List<String> listaValoriList  = Arrays.asList(listaValoriString);
			String nomeDelegato = null;
			if (listaValoriList.size() > 1){
				nomeDelegato = listaValoriList.get(1).trim();
			} else {
				nomeDelegato = record.getAttribute("decodificaDelegato");
			}
			ret = nomeDelegato;
		}
		return ret;
	}
		
}